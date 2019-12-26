/**
 * SIP2-like WebService to SIP2 bridge
 * 
 * SOAP Fault for SIP2 Protocol level errors
 * 
 * @author Matthew J. Dovey (www.ceridwen.com)
 */

package com.ceridwen.sip2client.faults;

import java.util.logging.Logger;

/**
 *
 * SOAP Fault for SIP2 Protocol level errors
 * 
 */
public class SIP2Fault extends Fault {
  
  public enum ConditionType {
    UNEXPECTED_RESPONSE,
    RETRIES_EXCEEDED,
    CHECKSUM_ERROR,
    SEQUENCE_ERROR,
    REQUEST_NOT_UNDERSTOOD,
    MANDATORY_FIELD_OMMITTED,
    INVALID_FIELD_LENGTH,
    UNSUPPORTED_MESSAGE,
    CHECKIN_DISABLED,
    CHECKOUT_DISABLED,
    NO_BLOCK_NOT_SUPPORTED,
    SIP2_SERVER_OFFLINE, 
    PATRON_STATUS_UPDATE_DISABLED
  }
  
  private final ConditionType condition;
  
  /**
   * Constructor
   * 
   * @param condition SIP2 error condition
   * @param message   message describing error
   * @param debug     return additional debugging information
   */
  public SIP2Fault(ConditionType condition, String message, Boolean debug) {
    super(message, debug);
    this.condition = condition;
  }

  /**
   * Constructor
   * 
   * @param condition SIP2 error condition
   * @param message   message describing error
   * @param throwable exception causing error
   * @param debug     return additional debugging information
   */
  public SIP2Fault(ConditionType condition, String message, Throwable throwable, Boolean debug) {
    super(message, throwable, debug);
    this.condition = condition;
  }

  /**
   * Return SIP2 error condition in SOAP fault message
   * 
   * @return  SIP2 error condition
   */
  public ConditionType getCondition() {
    final String LOG_METHOD_NAME = "getCondition";    
    Logger.getLogger(SIP2Fault.class.getName()).entering(SIP2Fault.class.getName(), LOG_METHOD_NAME);

    Logger.getLogger(SIP2Fault.class.getName()).exiting(SIP2Fault.class.getName(), LOG_METHOD_NAME, condition);
    return condition;
  }
}
