/*
 * Test SIP2 Server
 * 
 * @author Matthew J. Dovey (www.ceridwen.com)
 */
package com.ceridwen.sip2testserver;

import com.ceridwen.circulation.SIP.netty.server.SIPDaemon;
import com.ceridwen.circulation.SIP.netty.server.driver.DriverFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Initiate Test SIP2 Server within J2EE context
 */
public class SIPServerContextListener implements ServletContextListener {
  
  /**
   * 
   * Configuration options
   * 
   */ 
  @Resource(name="SIP2_Server_Name") public String SIP2_Server_Name = "Test SIP2 Server";
  @Resource(name="SIP2_Bind_IP")  public String SIP2_Bind_IP = "localhost";
  @Resource(name="SIP2_Bind_Port")  public Integer SIP2_Bind_Port = 55123;
  @Resource(name="SIP2_Server_Backend") public String SIP2_Server_Backend = "com.ceridwen.sip2testserver.driver.TestDriverFactory";
  @Resource(name="SIP2_Checksum_Mode") public Boolean SIP2_Checksum_Mode = true;
  
  
  private SIPDaemon server;

  /**
   * Start SIP2 Server daemon
   */
  private void startSIPServer() {
    final String LOG_METHOD_NAME = "startSIPServer";    
    Logger.getLogger(SIPServerContextListener.class.getName()).entering(SIPServerContextListener.class.getName(), LOG_METHOD_NAME);
    
    try { 
      // Attempt stop just in case it is running already
      stopSIPServer();
      server = new SIPDaemon(SIP2_Server_Name, SIP2_Bind_IP, SIP2_Bind_Port, (DriverFactory)Class.forName(SIP2_Server_Backend).newInstance(), SIP2_Checksum_Mode);
      server.start();
    } catch (Exception ex) {
      Logger.getLogger(SIPServerContextListener.class.getName()).logp(Level.SEVERE, SIPServerContextListener.class.getName(), "Error initialising backend driver", LOG_METHOD_NAME, ex);
    }
    
    Logger.getLogger(SIPServerContextListener.class.getName()).exiting(SIPServerContextListener.class.getName(), LOG_METHOD_NAME);
  }
  
  /**
   * Stop SIP2 Server daemon
   */
  private void stopSIPServer() {
    final String LOG_METHOD_NAME = "stopSIPServer";    
    Logger.getLogger(SIPServerContextListener.class.getName()).entering(SIPServerContextListener.class.getName(), LOG_METHOD_NAME);

    if (server != null) {
      server.stop();
      server = null;
    }
    
    Logger.getLogger(SIPServerContextListener.class.getName()).exiting(SIPServerContextListener.class.getName(), LOG_METHOD_NAME);
  }  

  /**
   * Called on servlet context initialisation
   * 
   * @param sce   Servlet Context Event
   */
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    final String LOG_METHOD_NAME = "contextInitialized";    
    Logger.getLogger(SIPServerContextListener.class.getName()).entering(SIPServerContextListener.class.getName(), LOG_METHOD_NAME);

    this.startSIPServer();
    
    Logger.getLogger(SIPServerContextListener.class.getName()).exiting(SIPServerContextListener.class.getName(), LOG_METHOD_NAME);
  }

  /**
   * Called on servlet context destroyed
   * 
   * @param sce   Servlet Context Event
   */
  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    final String LOG_METHOD_NAME = "contextDestroyed";    
    Logger.getLogger(SIPServerContextListener.class.getName()).entering(SIPServerContextListener.class.getName(), LOG_METHOD_NAME, new Object[]{sce});

    this.stopSIPServer();
    
    Logger.getLogger(SIPServerContextListener.class.getName()).exiting(SIPServerContextListener.class.getName(), LOG_METHOD_NAME);
  }
  
}
