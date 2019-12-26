/**
 * SIP2-like WebService to SIP2 bridge
 * 
 * SOAP Fault for SIP2 Connection level errors
 * 
 * @author Matthew J. Dovey (www.ceridwen.com)
 */

package com.ceridwen.sip2client.faults;

import com.ceridwen.circulation.SIP.transport.SocketConnection;
import java.util.logging.Logger;


/**
 *
 * SOAP Fault for SIP2 Connection level errors
 *  
 */
public class ConnectionFault extends Fault  {

  private final ConnectionInfo connectionInfo;
  
  /**
   * Constructor
   * 
   * @param message     message describing error
   * @param throwable   exception causing error
   * @param connection  connection class
   * @param debug       return additional debugging information
   */
  public ConnectionFault(String message, Throwable throwable, SocketConnection connection, Boolean debug) {
    super(message, throwable, debug);
    this.connectionInfo = new ConnectionInfo(connection);
  }

  /**
   * Return connection information in SOAP fault message
   * 
   * @return  connection information (debug == true) or null (debug == false)
   */

  public ConnectionInfo getConnectionInfo() {
    final String LOG_METHOD_NAME = "getConnectionInfo";    
    Logger.getLogger(ConnectionFault.class.getName()).entering(ConnectionFault.class.getName(), LOG_METHOD_NAME);

    ConnectionInfo info = null;
    
    if (this.debug) {
      info = this.connectionInfo;
    }
    
    Logger.getLogger(ConnectionFault.class.getName()).exiting(ConnectionFault.class.getName(), LOG_METHOD_NAME, info);
    return info;
  }
}
