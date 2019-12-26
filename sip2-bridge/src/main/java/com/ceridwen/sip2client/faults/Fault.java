/**
 * SIP2-like WebService to SIP2 bridge
 * 
 * Abstract SOAP Fault
 * 
 * @author Matthew J. Dovey (www.ceridwen.com)
 */

package com.ceridwen.sip2client.faults;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * 
 * Abstract SOAP Fault
 * 
 */
public abstract class Fault extends Exception {
  
  protected final boolean debug;
   
  /**
   * Constructor
   * 
   * @param message message describing error
   * @param debug   return additional debugging information
   */
  public Fault(String message, Boolean debug) {
    super(message);
    if (debug != null) {
      this.debug = debug;
    } else {
      this.debug = false;
    }
  }

  /**
   * Constructor
   * 
   * @param message   message describing error
   * @param throwable exception causing error
   * @param debug     return additional debugging information
   */
  public Fault(String message, Throwable throwable, Boolean debug) {
    super(message, throwable);
    if (debug != null) {
      this.debug = debug;
    } else {
      this.debug = false;
    }
  }
  
  /**
   * Return stack trace in SOAP fault message in debug mode
   * 
   * @return  stack trace (debug == true) or null (debug == false)
   */
  public String getStack()
  {
    final String LOG_METHOD_NAME = "getStack";    
    Logger.getLogger(Fault.class.getName()).entering(Fault.class.getName(), LOG_METHOD_NAME);

    String stack = null;
    if (debug) {
      StringWriter writer = new StringWriter();
      this.printStackTrace(new PrintWriter(writer));
      stack = writer.toString();
    }
    
    Logger.getLogger(Fault.class.getName()).exiting(Fault.class.getName(), LOG_METHOD_NAME, stack);
    return stack;
  }
  
}
