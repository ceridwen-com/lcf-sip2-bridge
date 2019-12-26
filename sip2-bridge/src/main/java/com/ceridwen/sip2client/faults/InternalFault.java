/**
 * SIP2-like WebService to SIP2 bridge
 * 
 * SOAP Fault for SIP2 Connection level errors
 * 
 * @author Matthew J. Dovey (www.ceridwen.com)
 */

package com.ceridwen.sip2client.faults;

/**
 *
 * SOAP Fault for unexpected internal errors
 */
public class InternalFault extends Fault {
  
   /**
   * Constructor
   * 
   * @param message   message describing error
   * @param throwable exception causing error
   * @param debug     return additional debugging information
   */
 public InternalFault(String message, Throwable throwable, Boolean debug) {
    super(message, throwable, debug);
  }
}