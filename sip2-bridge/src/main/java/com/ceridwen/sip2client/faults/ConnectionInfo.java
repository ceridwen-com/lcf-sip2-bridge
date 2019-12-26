/**
 * SIP2-like WebService to SIP2 bridge
 * 
 * SIP2 Connection information
 * 
 * @author Matthew J. Dovey (www.ceridwen.com)
 */
package com.ceridwen.sip2client.faults;

import com.ceridwen.circulation.SIP.transport.SSLSocketConnection;
import com.ceridwen.circulation.SIP.transport.SocketConnection;
import java.io.Serializable;


/**
 * Class for returning connection information in SOAP fault
 * 
 */
public class ConnectionInfo implements Serializable {

  private String host;
  private int port;
  private int connectionTimeout;
  private int idleTimeout;
  private int retryAttempts;
  private int retryWait;
  private boolean ssl;
 
  /**
   * Mask non-applicable constructor
   */  
  private ConnectionInfo() {
  }
  
  /**
   * Constructor
   * 
   * @param connection  Connection class
   */
  public ConnectionInfo(SocketConnection connection) {
    host = connection.getHost();
    port = connection.getPort();
    connectionTimeout = connection.getConnectionTimeout();
    idleTimeout = connection.getIdleTimeout();
    retryAttempts = connection.getRetryAttempts();
    retryWait = connection.getRetryWait();
    ssl = (connection instanceof SSLSocketConnection);
  }

  /**
   * 
   * @return  SIP2 Server IP
   */
  public String getHost() {
    return host;
  }

  /**
   * 
   * @return  SIP2 Server port
   */
  public int getPort() {
    return port;
  }

  /**
   * 
   * @return  Connection timeout
   */
  public int getConnectionTimeout() {
    return connectionTimeout;
  }

  /**
   * 
   * @return  idle timeout
   */
  public int getIdleTimeout() {
    return idleTimeout;
  }

  /**
   * 
   * @return  number of retry attempts
   */
  public int getRetryAttempts() {
    return retryAttempts;
  }

  /**
   * 
   * @return  wait time between retries
   */
  public int getRetryWait() {
    return retryWait;
  }

  /**
   * 
   * @return  connect over SSL
   */
  public boolean isSSL() {
    return ssl;
  }

}
