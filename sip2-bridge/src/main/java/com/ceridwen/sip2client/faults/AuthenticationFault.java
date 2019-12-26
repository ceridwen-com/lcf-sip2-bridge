/**
 * SIP2-like WebService to SIP2 bridge
 * 
 * SOAP Fault for SIP2 Authentication errors
 * 
 * @author Matthew J. Dovey (www.ceridwen.com)
 */

package com.ceridwen.sip2client.faults;

/**
 *
 * SOAP Fault for SIP2 Authentication errors
 * 
 */ 
public class AuthenticationFault extends Fault {

  /**
   * Constructor
   * 
   * @param message message describing error
   * @param debug   return additional debugging information
   */
  public AuthenticationFault(String message, Boolean debug) {
    super(message, debug);
  }

}
