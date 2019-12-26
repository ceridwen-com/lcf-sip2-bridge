/*
 * Test SIP2 Server
 * 
 * @author Matthew J. Dovey (www.ceridwen.com)
 */
package com.ceridwen.sip2testserver.driver;

import com.ceridwen.circulation.SIP.messages.ACSStatus;
import com.ceridwen.circulation.SIP.messages.CheckInResponse;
import com.ceridwen.circulation.SIP.messages.CheckOutResponse;
import com.ceridwen.circulation.SIP.messages.EndSessionResponse;
import com.ceridwen.circulation.SIP.messages.FeePaidResponse;
import com.ceridwen.circulation.SIP.messages.HoldResponse;
import com.ceridwen.circulation.SIP.messages.ItemInformationResponse;
import com.ceridwen.circulation.SIP.messages.ItemStatusUpdateResponse;
import com.ceridwen.circulation.SIP.messages.LoginResponse;
import com.ceridwen.circulation.SIP.messages.PatronEnableResponse;
import com.ceridwen.circulation.SIP.messages.PatronInformationResponse;
import com.ceridwen.circulation.SIP.messages.PatronStatusRequest;
import com.ceridwen.circulation.SIP.messages.PatronStatusResponse;
import com.ceridwen.circulation.SIP.messages.RenewAllResponse;
import com.ceridwen.circulation.SIP.messages.RenewResponse;
import com.ceridwen.circulation.SIP.messages.SCStatus;
import com.ceridwen.circulation.SIP.netty.server.driver.AbstractDriver;
import com.ceridwen.circulation.SIP.netty.server.driver.operation.BlockPatronOperation;
import com.ceridwen.circulation.SIP.netty.server.driver.operation.CheckInOperation;
import com.ceridwen.circulation.SIP.netty.server.driver.operation.CheckOutOperation;
import com.ceridwen.circulation.SIP.netty.server.driver.operation.EndPatronSessionOperation;
import com.ceridwen.circulation.SIP.netty.server.driver.operation.FeePaidOperation;
import com.ceridwen.circulation.SIP.netty.server.driver.operation.HoldOperation;
import com.ceridwen.circulation.SIP.netty.server.driver.operation.ItemInformationOperation;
import com.ceridwen.circulation.SIP.netty.server.driver.operation.ItemStatusUpdateOperation;
import com.ceridwen.circulation.SIP.netty.server.driver.operation.LoginOperation;
import com.ceridwen.circulation.SIP.netty.server.driver.operation.PatronEnableOperation;
import com.ceridwen.circulation.SIP.netty.server.driver.operation.PatronInformationOperation;
import com.ceridwen.circulation.SIP.netty.server.driver.operation.PatronStatusOperation;
import com.ceridwen.circulation.SIP.netty.server.driver.operation.RenewAllOperation;
import com.ceridwen.circulation.SIP.netty.server.driver.operation.RenewOperation;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Test SIP2 Server backend driver
 */
public class TestDriver extends AbstractDriver
  implements  BlockPatronOperation,
              CheckInOperation,
              CheckOutOperation,
              EndPatronSessionOperation,
              FeePaidOperation,
              HoldOperation,
              ItemInformationOperation,
              ItemStatusUpdateOperation,
              LoginOperation,
              PatronEnableOperation,
              PatronInformationOperation,
              PatronStatusOperation,
              RenewAllOperation,
              RenewOperation
{

  /**
   * Add any additional status response parameters
   * 
   * @param status  SIP2 Status pre-populated with defaults
   * @param msg     SCStatus request
   * @return        ACSStatus response
   */
  @Override
  public ACSStatus Status(ACSStatus status, SCStatus msg) {
    final String LOG_METHOD_NAME = "Status";    
    Logger.getLogger(TestDriver.class.getName()).entering(TestDriver.class.getName(), LOG_METHOD_NAME, new Object[]{status, msg});

    status.setACSRenewalPolicy(false);
    status.setCheckInOk(true);
    status.setCheckOutOk(true);
    status.setOfflineOk(false);
    status.setStatusUpdateOk(true);
    
    Logger.getLogger(TestDriver.class.getName()).exiting(TestDriver.class.getName(), LOG_METHOD_NAME, status);
    return status;
  }

  /**
   * Handle BlockPatron request
   * 
   * @param msg   Incoming SIP2 request
   * @return      SIP2 response
   */
  @Override
  public PatronStatusResponse BlockPatron(
          com.ceridwen.circulation.SIP.messages.BlockPatron msg) {
    final String LOG_METHOD_NAME = "BlockPatron";    
    Logger.getLogger(TestDriver.class.getName()).entering(TestDriver.class.getName(), LOG_METHOD_NAME, new Object[]{msg});
    
    PatronStatusResponse response = new PatronStatusResponse();
    
    Logger.getLogger(TestDriver.class.getName()).exiting(TestDriver.class.getName(), LOG_METHOD_NAME, response);
    return response;
  }

  /**
   * Handle CheckIn request
   * 
   * @param msg   Incoming SIP2 request
   * @return      SIP2 response
   */
  @Override
  public CheckInResponse CheckIn(
          com.ceridwen.circulation.SIP.messages.CheckIn msg) {
    final String LOG_METHOD_NAME = "CheckIn";    
    Logger.getLogger(TestDriver.class.getName()).entering(TestDriver.class.getName(), LOG_METHOD_NAME, new Object[]{msg});

    CheckInResponse response = new CheckInResponse();
    
    Logger.getLogger(TestDriver.class.getName()).exiting(TestDriver.class.getName(), LOG_METHOD_NAME, response);
    return response;
  }

  /**
   * Handle CheckOut request
   * 
   * @param msg   Incoming SIP2 request
   * @return      SIP2 response
   */
  @Override
  public CheckOutResponse CheckOut(
          com.ceridwen.circulation.SIP.messages.CheckOut msg) {
    final String LOG_METHOD_NAME = "CheckOut";    
    Logger.getLogger(TestDriver.class.getName()).entering(TestDriver.class.getName(), LOG_METHOD_NAME, new Object[]{msg});
    
    CheckOutResponse response = new CheckOutResponse();
    
    Logger.getLogger(TestDriver.class.getName()).exiting(TestDriver.class.getName(), LOG_METHOD_NAME, response);
    return response;
  }

  /**
   * Handle EndPatronSession request
   * 
   * @param msg   Incoming SIP2 request
   * @return      SIP2 response
   */
  @Override
  public EndSessionResponse EndPatronSession(
          com.ceridwen.circulation.SIP.messages.EndPatronSession msg) {
    final String LOG_METHOD_NAME = "EndPatronSession";    
    Logger.getLogger(TestDriver.class.getName()).entering(TestDriver.class.getName(), LOG_METHOD_NAME, new Object[]{msg});

    EndSessionResponse response = new EndSessionResponse();
    
    Logger.getLogger(TestDriver.class.getName()).exiting(TestDriver.class.getName(), LOG_METHOD_NAME, response);
    return response;
  }

  /**
   * Handle FeePaid request
   * 
   * @param msg   Incoming SIP2 request
   * @return      SIP2 response
   */
  @Override
  public FeePaidResponse FeePaid(
          com.ceridwen.circulation.SIP.messages.FeePaid msg) {
    final String LOG_METHOD_NAME = "FeePaid";    
    Logger.getLogger(TestDriver.class.getName()).entering(TestDriver.class.getName(), LOG_METHOD_NAME, new Object[]{msg});

    FeePaidResponse response = new FeePaidResponse();
    
    Logger.getLogger(TestDriver.class.getName()).exiting(TestDriver.class.getName(), LOG_METHOD_NAME, response);
    return response;
  }

  /**
   * Handle Hold request
   * 
   * @param msg   Incoming SIP2 request
   * @return      SIP2 response
   */
  @Override
  public HoldResponse Hold(com.ceridwen.circulation.SIP.messages.Hold msg) {
    final String LOG_METHOD_NAME = "Hold";    
    Logger.getLogger(TestDriver.class.getName()).entering(TestDriver.class.getName(), LOG_METHOD_NAME, new Object[]{msg});

    HoldResponse response = new HoldResponse();

    Logger.getLogger(TestDriver.class.getName()).exiting(TestDriver.class.getName(), LOG_METHOD_NAME, response);
    return response;
  }

  @Override
  /**
   * Handle ItemInformation request
   * 
   * @param msg   Incoming SIP2 request
   * @return      SIP2 response
   */
  public ItemInformationResponse ItemInformation(
          com.ceridwen.circulation.SIP.messages.ItemInformation msg) {
    final String LOG_METHOD_NAME = "ItemInformation";    
    Logger.getLogger(TestDriver.class.getName()).entering(TestDriver.class.getName(), LOG_METHOD_NAME, new Object[]{msg});

    ItemInformationResponse response = new ItemInformationResponse();
    
    Logger.getLogger(TestDriver.class.getName()).exiting(TestDriver.class.getName(), LOG_METHOD_NAME, response);
    return response;
  }

  /**
   * Handle ItemStatusUpdate request
   * 
   * @param msg   Incoming SIP2 request
   * @return      SIP2 response
   */
  @Override
  public ItemStatusUpdateResponse ItemStatusUpdate(
          com.ceridwen.circulation.SIP.messages.ItemStatusUpdate msg) {
    final String LOG_METHOD_NAME = "ItemStatusUpdate";    
    Logger.getLogger(TestDriver.class.getName()).entering(TestDriver.class.getName(), LOG_METHOD_NAME, new Object[]{msg});

    ItemStatusUpdateResponse response = new ItemStatusUpdateResponse();
    
    Logger.getLogger(TestDriver.class.getName()).exiting(TestDriver.class.getName(), LOG_METHOD_NAME, response);
    return response;
  }

  /**
   * Handle LoginResponse request
   * 
   * @param msg   Incoming SIP2 request
   * @return      SIP2 response
   */
  @Override
  public LoginResponse Login(com.ceridwen.circulation.SIP.messages.Login msg) {
    final String LOG_METHOD_NAME = "Login";    
    Logger.getLogger(TestDriver.class.getName()).entering(TestDriver.class.getName(), LOG_METHOD_NAME, new Object[]{msg});

    LoginResponse response = new LoginResponse();
    
    Logger.getLogger(TestDriver.class.getName()).exiting(TestDriver.class.getName(), LOG_METHOD_NAME, response);
    return response;
  }

  /**
   * Handle PatronEnable request
   * 
   * @param msg   Incoming SIP2 request
   * @return      SIP2 response
   */
  @Override
  public PatronEnableResponse PatronEnable(
          com.ceridwen.circulation.SIP.messages.PatronEnable msg) {
    final String LOG_METHOD_NAME = "PatronEnable";    
    Logger.getLogger(TestDriver.class.getName()).entering(TestDriver.class.getName(), LOG_METHOD_NAME, new Object[]{msg});

    PatronEnableResponse response = new PatronEnableResponse();
    
    Logger.getLogger(TestDriver.class.getName()).exiting(TestDriver.class.getName(), LOG_METHOD_NAME, response);
    return response;
  }

  /**
   * Handle PatronInformation request
   * 
   * @param msg   Incoming SIP2 request
   * @return      SIP2 response
   */
  @Override
  public PatronInformationResponse PatronInformation(
          com.ceridwen.circulation.SIP.messages.PatronInformation msg) {
    final String LOG_METHOD_NAME = "PatronInformation";    
    Logger.getLogger(TestDriver.class.getName()).entering(TestDriver.class.getName(), LOG_METHOD_NAME, new Object[]{msg});

    PatronInformationResponse response = new PatronInformationResponse();  

    if (msg.getSummary() != null) {
      if (msg.getSummary().isChargedItems()) {
        response.setChargedItems(generateTestSummaryData(msg.getStartItem(), msg.getEndItem(), "Charge"));
      }
      if (msg.getSummary().isFineItems()) {
        response.setFineItems(generateTestSummaryData(msg.getStartItem(), msg.getEndItem(), "Fine"));
      }
      if (msg.getSummary().isHoldItems()) {
        response.setHoldItems(generateTestSummaryData(msg.getStartItem(), msg.getEndItem(), "Hold"));
      }
      if (msg.getSummary().isOverdueItems()) {
        response.setOverdueItems(generateTestSummaryData(msg.getStartItem(), msg.getEndItem(), "Overdue Item"));
      }
      if (msg.getSummary().isRecallItems()) {
        response.setRecallItems(generateTestSummaryData(msg.getStartItem(), msg.getEndItem(), "Recall"));
      }
      if (msg.getSummary().isUnavaibleHolds()) {
        response.setUnavailableHoldItems(generateTestSummaryData(msg.getStartItem(), msg.getEndItem(), "Unavailable Hold"));
      }      
    }
    
    response.setChargedItemsCount(99);
    response.setFineItemsCount(99);
    response.setHoldItemsCount(99);
    response.setOverdueItemsCount(99);
    response.setRecallItemsCount(99);
    response.setUnavailableHoldsCount(99);
    response.setChargedItemsLimit(99);
    response.setHoldItemsLimit(99);
    response.setOverdueItemsLimit(99);

    response.setPatronIdentifier(msg.getPatronIdentifier());
    response.setPersonalName("Name for id " + msg.getPatronIdentifier());
    
    if (StringUtil.isNullOrEmpty(msg.getPatronPassword()) || !msg.getPatronPassword().equals("password")) {
        response.setValidPatronPassword(false);
    } else {
        response.setValidPatronPassword(true);        
    }

    Logger.getLogger(TestDriver.class.getName()).exiting(TestDriver.class.getName(), LOG_METHOD_NAME, response);
    return response;
  }

  private String[] generateTestSummaryData(Integer startItem, Integer endItem, String type) {
    ArrayList<String> items = new ArrayList<>();
    int start = ObjectUtils.defaultIfNull(startItem, 1);
    int end = ObjectUtils.defaultIfNull(endItem, 0);
    for (int i = start; i <= end; i++ ) {
      items.add(type + " " + i);
    }
    return items.toArray(new String[]{});
  }

  /**
   * Handle PatronStatus request
   * 
   * @param msg   Incoming SIP2 request
   * @return      SIP2 response
   */
  @Override
  public PatronStatusResponse PatronStatus(PatronStatusRequest msg) {
    final String LOG_METHOD_NAME = "PatronStatus";    
    Logger.getLogger(TestDriver.class.getName()).entering(TestDriver.class.getName(), LOG_METHOD_NAME, new Object[]{msg});

    PatronStatusResponse response = new PatronStatusResponse();
    
    Logger.getLogger(TestDriver.class.getName()).exiting(TestDriver.class.getName(), LOG_METHOD_NAME, response);
    return response;
  }

  /**
   * Handle Renew request
   * 
   * @param msg   Incoming SIP2 request
   * @return      SIP2 response
   */
  @Override
  public RenewResponse Renew(com.ceridwen.circulation.SIP.messages.Renew msg) {
    final String LOG_METHOD_NAME = "Renew";    
    Logger.getLogger(TestDriver.class.getName()).entering(TestDriver.class.getName(), LOG_METHOD_NAME, new Object[]{msg});

    RenewResponse response = new RenewResponse();
    
    Logger.getLogger(TestDriver.class.getName()).exiting(TestDriver.class.getName(), LOG_METHOD_NAME, response);
    return response;
  }

  /**
   * Handle RenewAll request
   * 
   * @param msg   Incoming SIP2 request
   * @return      SIP2 response
   */
  @Override
  public RenewAllResponse RenewAll(
          com.ceridwen.circulation.SIP.messages.RenewAll msg) {
    final String LOG_METHOD_NAME = "RenewAll";    
    Logger.getLogger(TestDriver.class.getName()).entering(TestDriver.class.getName(), LOG_METHOD_NAME, new Object[]{msg});

    RenewAllResponse response = new RenewAllResponse();
    
    Logger.getLogger(TestDriver.class.getName()).exiting(TestDriver.class.getName(), LOG_METHOD_NAME, response);
    return response;
  }

}
