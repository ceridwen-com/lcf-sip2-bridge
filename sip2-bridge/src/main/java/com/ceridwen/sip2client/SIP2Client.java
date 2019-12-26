/**
 * SIP2-like WebService to SIP2 bridge
 * 
 * @author Matthew J. Dovey (www.ceridwen.com)
 */
package com.ceridwen.sip2client;

import com.ceridwen.circulation.SIP.exceptions.ChecksumError;
import com.ceridwen.circulation.SIP.exceptions.InvalidFieldLength;
import com.ceridwen.circulation.SIP.exceptions.MandatoryFieldOmitted;
import com.ceridwen.circulation.SIP.exceptions.MessageNotUnderstood;
import com.ceridwen.circulation.SIP.exceptions.RetriesExceeded;
import com.ceridwen.circulation.SIP.exceptions.SequenceError;
import com.ceridwen.circulation.SIP.messages.ACSStatus;
import com.ceridwen.circulation.SIP.messages.BlockPatron;
import com.ceridwen.circulation.SIP.messages.CheckIn;
import com.ceridwen.circulation.SIP.messages.CheckOut;
import com.ceridwen.circulation.SIP.messages.Login;
import com.ceridwen.circulation.SIP.messages.LoginResponse;
import com.ceridwen.circulation.SIP.messages.Message;
import com.ceridwen.circulation.SIP.messages.PatronEnable;
import com.ceridwen.circulation.SIP.messages.Renew;
import com.ceridwen.circulation.SIP.messages.RenewAll;
import com.ceridwen.circulation.SIP.messages.SCStatus;
import com.ceridwen.circulation.SIP.transport.Connection;
import com.ceridwen.circulation.SIP.transport.SSLSocketConnection;
import com.ceridwen.circulation.SIP.transport.SocketConnection;
import com.ceridwen.circulation.SIP.types.enumerations.ProtocolVersion;
import com.ceridwen.circulation.SIP.types.enumerations.StatusCode;
import com.ceridwen.circulation.SIP.types.flagfields.SupportedMessages;
import com.ceridwen.sip2client.faults.AuthenticationFault;
import com.ceridwen.sip2client.faults.ConnectionFault;
import com.ceridwen.sip2client.faults.InternalFault;
import com.ceridwen.sip2client.faults.SIP2Fault;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

  /**
   * 
   * SIP2 Client Code
   * 
   * */
public class SIP2Client {
  private final String ip;
  private final int port;
  private final boolean ssl;
  private final String user;
  private final String password;  
  private final int connectionTimeout;
  private final int idleTimeout;
  private final int retryAttempts;
  private final int retryWait;
  private final boolean debug;
  
  /**
   * Constructor
   * 
   * @param ip                ip address of SIP2 server
   * @param port              port of SIP2 server
   * @param ssl               true is SIP2 server running over SSL
   * @param connectionTimeout connection timeout
   * @param idleTimeout       idle timeout
   * @param retryAttempts     number of retries permitted
   * @param retryWait         wait between retries
   * @param user              SIP2 Login request username
   * @param password          SIP2 Login request password
   * @param debug             return additional information in SOAP2 faults
   */
  public SIP2Client(String ip, Integer port, Boolean ssl, Integer connectionTimeout, Integer idleTimeout, Integer retryAttempts, Integer retryWait, String user, String password, Boolean debug) {
    final String LOG_METHOD_NAME = "SIP2Client"; 
    Logger.getLogger(SIP2Client.class.getName()).entering(SIP2Client.class.getName(), LOG_METHOD_NAME, new Object[]{ip, port, ssl, connectionTimeout, idleTimeout, retryAttempts, retryWait, user, password, debug});

    this.ssl = ObjectUtils.defaultIfNull(ssl, false);
    this.ip = ObjectUtils.defaultIfNull(ip, "localhost");
    this.port = ObjectUtils.defaultIfNull(port, 12345);
    this.connectionTimeout = ObjectUtils.defaultIfNull(connectionTimeout, 1000);
    this.idleTimeout = ObjectUtils.defaultIfNull(idleTimeout,1000);
    this.retryAttempts = ObjectUtils.defaultIfNull(retryAttempts, 3);
    this.retryWait = ObjectUtils.defaultIfNull(retryWait, 500);
    this.user = ObjectUtils.defaultIfNull(user, "");
    this.password = ObjectUtils.defaultIfNull(password, "");
    this.debug = ObjectUtils.defaultIfNull(debug, false);

    Logger.getLogger(SIP2Client.class.getName()).exiting(SIP2Client.class.getName(), LOG_METHOD_NAME);
  }
  
  /**
   * Do complete SIP2 message interchange
   * 
   * @param <T>                   Expected SIP2 response Message Type
   * @param request               SIP2 request
   * @param responseType          Expected SIP2 response Message Type
   * @param supportedMessageCheck message check against Status supported messages 
   * @return                      SIP2 response
   * @throws ConnectionFault      Connection error
   * @throws AuthenticationFault  SIP2 Login authentication failed
   * @throws SIP2Fault            SIP2 protocol error
   * @throws InternalFault        Unexpected error 
   */
  public  <T extends Message> T handleSIPMessage(Message request, Class<T> responseType, Predicate<SupportedMessages> supportedMessageCheck) throws ConnectionFault, AuthenticationFault, SIP2Fault, InternalFault {
    final String LOG_METHOD_NAME = "handleSIPMessage"; 
    Logger.getLogger(SIP2Client.class.getName()).entering(SIP2Client.class.getName(), LOG_METHOD_NAME, new Object[]{request, responseType, supportedMessageCheck});

    SocketConnection connection = null;
    ACSStatus statusResponse;
    T response;

    try {
      connection = this.connect();

      this.SIPLogin(user, password, connection);

      statusResponse = this.SIPStatus(connection);
      
      this.validateRequest(request, statusResponse, supportedMessageCheck);

      response = this.sendSIPMessage(request, connection, responseType);

      this.disconnect(connection);
    } catch (ConnectionFault | AuthenticationFault | SIP2Fault fault) {
      this.disconnect(connection);
      Logger.getLogger(SIP2Client.class.getName()).throwing(SIP2Client.class.getName(), LOG_METHOD_NAME, fault);
      throw fault;
    } catch (Exception ex) {
      Logger.getLogger(SIP2Client.class.getName()).log(Level.SEVERE, "Unexpected error: " + ex.getLocalizedMessage(), ex);
      this.disconnect(connection);
      InternalFault fault;
      fault = new InternalFault("Unexpected error: " + ex.getLocalizedMessage(), ex, debug);
      Logger.getLogger(SIP2Client.class.getName()).throwing(SIP2Client.class.getName(), LOG_METHOD_NAME, fault);
      throw fault;
    }

    Logger.getLogger(SIP2Client.class.getName()).exiting(SIP2Client.class.getName(), LOG_METHOD_NAME, response);
    return response;    
  }

  /**
   * Initiate SIP2 connection
   * 
   * @return  connection
   * @throws ConnectionFault  connection failed
   */
  private SocketConnection connect() throws ConnectionFault {
    final String LOG_METHOD_NAME = "connect"; 
    Logger.getLogger(SIP2Client.class.getName()).entering(SIP2Client.class.getName(), LOG_METHOD_NAME);

    SocketConnection connection;
    
    if (ssl) {
      connection = new SSLSocketConnection();
    } else {
      connection = new SocketConnection();
    }

    connection.setHost(ip);
    connection.setPort(port);
    connection.setConnectionTimeout(connectionTimeout);
    connection.setIdleTimeout(idleTimeout);
    connection.setRetryAttempts(retryAttempts);
    connection.setRetryWait(retryWait);

    try {
      connection.connect();
    } catch (Exception ex) {
      ConnectionFault fault;
      fault = new ConnectionFault(ex.getLocalizedMessage(), ex, connection, debug);
      Logger.getLogger(SIP2Client.class.getName()).throwing(SIP2Client.class.getName(), LOG_METHOD_NAME, fault);
      throw fault;
    }
    
    Logger.getLogger(SIP2Client.class.getName()).exiting(SIP2Client.class.getName(), LOG_METHOD_NAME, connection);
    return connection;
  }

  /**
   * Do SIP2 Login
   * 
   * @param loginUser             Login username
   * @param loginPassword         Login password
   * @param connection            SIP2 connection
   * @throws AuthenticationFault  Authentication failed
   * @throws SIP2Fault            SIP2 protocol error
   */
  private void SIPLogin(String loginUser, String loginPassword, Connection connection) throws AuthenticationFault, SIP2Fault {
    final String LOG_METHOD_NAME = "SIPLogin"; 
    Logger.getLogger(SIP2Client.class.getName()).entering(SIP2Client.class.getName(), LOG_METHOD_NAME, new Object[]{loginUser, loginPassword, connection});
         
    if (StringUtils.isEmpty(loginUser) && StringUtils.isEmpty(loginPassword)) {
      return;
    }
    
    Login login = new Login();
    
    login.setLoginUserId(loginUser);
    login.setLoginPassword(loginPassword);
    
    LoginResponse response = this.sendSIPMessage(login, connection, LoginResponse.class);
    
    if (!response.isOk()) {
      AuthenticationFault fault;
      fault = new AuthenticationFault("Login failed", debug);
      Logger.getLogger(SIP2Client.class.getName()).throwing(SIP2Client.class.getName(), LOG_METHOD_NAME, fault);
      throw fault;
    }
    
    Logger.getLogger(SIP2Client.class.getName()).exiting(SIP2Client.class.getName(), LOG_METHOD_NAME);
  }

  /**
   * Do SIP2 Status request 
   * 
   * @param connection            SIP2 connection
   * @return                      SIP2 Status response
   * @throws SIP2Fault            SIP2 protocol error
   */
  private ACSStatus SIPStatus(Connection connection) throws SIP2Fault {
    final String LOG_METHOD_NAME = "SIPStatus"; 
    Logger.getLogger(SIP2Client.class.getName()).entering(SIP2Client.class.getName(), LOG_METHOD_NAME, new Object[]{connection});

    SCStatus status = new SCStatus();
    
    status.setProtocolVersion(ProtocolVersion.VERSION_2_00);
    status.setStatusCode(StatusCode.OK);
    
    ACSStatus response = this.sendSIPMessage(status, connection, ACSStatus.class);
    
    Logger.getLogger(SIP2Client.class.getName()).exiting(SIP2Client.class.getName(), LOG_METHOD_NAME);
    return response;
  }

  /**
   * Validate SIP2 request message against supported messages in SIP2 status response
   * 
   * @param request               SIP2 request message
   * @param statusResponse        SIP2 status
   * @param supportedMessageCheck message check against Status supported messages
   * @throws SIP2Fault            SIP2 protocol error
   */
  private void validateRequest(Message request, ACSStatus statusResponse, Predicate<SupportedMessages> supportedMessageCheck) throws SIP2Fault {
    final String LOG_METHOD_NAME = "validateRequest"; 
    Logger.getLogger(SIP2Client.class.getName()).entering(SIP2Client.class.getName(), LOG_METHOD_NAME, new Object[]{request, statusResponse, supportedMessageCheck});

    // Check request has been initialised
    if (request == null) {
      throw generateSIP2Fault(SIP2Fault.ConditionType.REQUEST_NOT_UNDERSTOOD, "Request is invalid", LOG_METHOD_NAME);      
    }
    
    // Check that SIP2 server reports as online
    if (!statusResponse.isOnlineStatus()) {
      throw generateSIP2Fault(SIP2Fault.ConditionType.SIP2_SERVER_OFFLINE, "SIP2 Server is offline", LOG_METHOD_NAME);            
    }      

    // Check if SIP2 server supports request message
    if (!supportedMessageCheck.test(statusResponse.getSupportedMessages())) {
      throw generateSIP2Fault(SIP2Fault.ConditionType.UNSUPPORTED_MESSAGE, request.getClass().getSimpleName() + " not supported by SIP2 server", LOG_METHOD_NAME);   
    }
    
    // If message is CheckIn
    // Is check in enabled on SIP2 server
    // If no block parameter set does SIP2 server support offline modes
    if (request.getClass() == CheckIn.class) {
      checkPermittedOperation(statusResponse, ACSStatus::isCheckInOk, (CheckIn)request, CheckIn::isNoBlock);
    }
    
    // If message is CheckOut
    // Is check out enabled on SIP2 server
    // If no block parameter set does SIP2 server support offline modes
    if (request.getClass() == CheckOut.class) {
      checkPermittedOperation(statusResponse, ACSStatus::isCheckOutOk, (CheckOut)request, CheckOut::isNoBlock);  
    }

    // If message is Renew or RenewAll
    // Is check out enabled on SIP2 server
    if ((request.getClass() == Renew.class || request.getClass() == RenewAll.class) && (!statusResponse.isCheckOutOk())) {
        throw generateSIP2Fault(SIP2Fault.ConditionType.CHECKOUT_DISABLED, "SIP2 server does not allow check out requests", LOG_METHOD_NAME);   
    }
    
    // If message is BlockPatron or PatronEnable
    // Is status update enabled on SIP2 server
    if ((request.getClass() == BlockPatron.class || request.getClass() == PatronEnable.class) && (!statusResponse.isStatusUpdateOk())) {
        throw generateSIP2Fault(SIP2Fault.ConditionType.PATRON_STATUS_UPDATE_DISABLED, "SIP2 server does not allow patron status updates", LOG_METHOD_NAME);   
    }

    Logger.getLogger(SIP2Client.class.getName()).exiting(SIP2Client.class.getName(), LOG_METHOD_NAME);
  }

  /**
   * check if CheckIn or CheckOut permitted
   * 
   * @param <T>                       type of request message (CheckIn or CheckOut)
   * @param statusResponse            ACS Status response
   * @param checkOperationPermitted   method to check operation permitted (isCheckInOK or isCheckOutOK 
   * @param request                   request message to check
   * @param checkNoBlock              isNoBlock method
   * @throws SIP2Fault                SIP2Fault if request message is not permitted by server
   */
  private <T extends Message> void checkPermittedOperation(ACSStatus statusResponse, Predicate<ACSStatus> checkOperationPermitted, T request, Function<T, Boolean> checkNoBlock) throws SIP2Fault {
    final String LOG_METHOD_NAME = "isPermittedOperation";    
    Logger.getLogger(SIP2Client.class.getName()).entering(SIP2Client.class.getName(), LOG_METHOD_NAME);
    
    if (checkOperationPermitted.test(statusResponse))  {
      boolean offlineOk = statusResponse.isOfflineOk();
      boolean noBlock = ObjectUtils.defaultIfNull(checkNoBlock.apply(request), false);     
      if (noBlock && !offlineOk) {
        throw generateSIP2Fault(SIP2Fault.ConditionType.NO_BLOCK_NOT_SUPPORTED, "SIP2 server does not allow no block requests", LOG_METHOD_NAME);
      }
    } else {
      throw generateSIP2Fault(SIP2Fault.ConditionType.CHECKIN_DISABLED, "SIP2 server does not allow " + ((request instanceof CheckOut)?"check out":"check in") + " requests", LOG_METHOD_NAME);
    }
    
    Logger.getLogger(SIP2Client.class.getName()).exiting(SIP2Client.class.getName(), LOG_METHOD_NAME);
  }
  
  /**
   * Send and receive SIP2 message
   * 
   * @param <T>           Expected SIP2 response type
   * @param request       SIP2 request message
   * @param connection    SIP2 connection
   * @param responseType  Expected SIP2 response type
   * @return              SIP2 response
   * @throws SIP2Fault    SIP2 protocol error
   */
  private <T extends Message> T sendSIPMessage(Message request, Connection connection, Class<T> responseType) throws SIP2Fault {
    final String LOG_METHOD_NAME = "sendSIPMessage"; 
    Logger.getLogger(SIP2Client.class.getName()).entering(SIP2Client.class.getName(), LOG_METHOD_NAME, new Object[]{request, connection});

    Message response;
    
    try {
      response = connection.send(request);
      
      if (!responseType.isInstance(response)) {
        throw generateSIP2Fault(SIP2Fault.ConditionType.UNEXPECTED_RESPONSE, "Unexpected response: expected " + responseType.getSimpleName() + ", got " + response.getClass().getSimpleName(), LOG_METHOD_NAME);
      }      
    } catch (RetriesExceeded ex) { 
      throw generateSIP2Fault(SIP2Fault.ConditionType.RETRIES_EXCEEDED, ex, LOG_METHOD_NAME);
    } catch (ChecksumError ex) {
      throw generateSIP2Fault(SIP2Fault.ConditionType.CHECKSUM_ERROR, ex, LOG_METHOD_NAME);
    } catch (SequenceError ex) {
      throw generateSIP2Fault(SIP2Fault.ConditionType.SEQUENCE_ERROR, ex, LOG_METHOD_NAME);
    } catch (MessageNotUnderstood ex) {
      throw generateSIP2Fault(SIP2Fault.ConditionType.REQUEST_NOT_UNDERSTOOD, ex, LOG_METHOD_NAME);
    } catch (MandatoryFieldOmitted ex) {
      throw generateSIP2Fault(SIP2Fault.ConditionType.MANDATORY_FIELD_OMMITTED, ex, LOG_METHOD_NAME);
    } catch (InvalidFieldLength ex) {
      throw generateSIP2Fault(SIP2Fault.ConditionType.INVALID_FIELD_LENGTH, ex, LOG_METHOD_NAME);
    }
    
    Logger.getLogger(SIP2Client.class.getName()).exiting(SIP2Client.class.getName(), LOG_METHOD_NAME, response);
    return (T)response;
  }

  /**
   * Helper method for generating SIP2Faults
   * 
   * @param conditionType SIP2Fault condition
   * @param ex            Underlying exception
   * @param methodName    calling method name (for logging thrown exception)
   * @return              Populated SIP2Fault
   */
  private SIP2Fault generateSIP2Fault(SIP2Fault.ConditionType conditionType, Exception ex, final String methodName)  {
    SIP2Fault fault;
    fault = new SIP2Fault(conditionType, ex.getClass().getSimpleName() + ": " + ex.getLocalizedMessage(), ex, debug);
    Logger.getLogger(SIP2Client.class.getName()).throwing(SIP2Client.class.getName(), methodName, fault);
    return fault;  }

  /**
   * Helper method for generating SIP2Faults
   * 
   * @param conditionType SIP2Fault condition
   * @param message       Message to include in fault
   * @param methodName    calling method name (for logging thrown exception)
   * @return              Populated SIP2Fault
   */
  private SIP2Fault generateSIP2Fault(SIP2Fault.ConditionType conditionType, String message, final String methodName)  {
    SIP2Fault fault;
    fault = new SIP2Fault(conditionType, message, debug);
    Logger.getLogger(SIP2Client.class.getName()).throwing(SIP2Client.class.getName(), methodName, fault);
    return fault;
  }
  
  /**
   * Close SIP2 connection
   * 
   * @param connection  SIP2 connection
   */
  private void disconnect(Connection connection) {
    final String LOG_METHOD_NAME = "disconnect"; 
    Logger.getLogger(SIP2Client.class.getName()).entering(SIP2Client.class.getName(), LOG_METHOD_NAME, connection);

    try {
      if (connection != null) {
        connection.disconnect();
      }
    } catch (Exception ex) {      
      Logger.getLogger(SIP2Client.class.getName()).logp(Level.WARNING, SIP2Client.class.getName(), LOG_METHOD_NAME, "Unexpected error whilst disconnecting", ex);
    }

    Logger.getLogger(SIP2Client.class.getName()).exiting(SIP2Client.class.getName(), LOG_METHOD_NAME);
  }
  
}
