/*
 * Test SIP2 Server
 * 
 * @author Matthew J. Dovey (www.ceridwen.com)
 */
package com.ceridwen.sip2testserver.driver;

import com.ceridwen.circulation.SIP.netty.server.driver.Driver;
import com.ceridwen.circulation.SIP.netty.server.driver.DriverFactory;
import java.util.logging.Logger;

/**
 * Test SIP2 Server backend driver factory
 */
public class TestDriverFactory implements DriverFactory {

  /**
   * Initialise SIP2 Server backend driver
   * 
   * @return   SIP2 Server backend driver
   */
  @Override
  public Driver getDriver() {
    final String LOG_METHOD_NAME = "getDriver";    
    Logger.getLogger(TestDriverFactory.class.getName()).entering(TestDriverFactory.class.getName(), LOG_METHOD_NAME);

    Driver driver = new TestDriver();
    
    Logger.getLogger(TestDriverFactory.class.getName()).exiting(TestDriverFactory.class.getName(), LOG_METHOD_NAME, driver);
    return driver;
  }
  
}