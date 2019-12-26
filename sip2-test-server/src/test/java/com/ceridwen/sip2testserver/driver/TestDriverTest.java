/*
 * Test SIP2 Server
 * 
 * @author Matthew J. Dovey (www.ceridwen.com)
 */
package com.ceridwen.sip2testserver.driver;

import com.ceridwen.circulation.SIP.messages.ACSStatus;
import com.ceridwen.circulation.SIP.messages.BlockPatron;
import com.ceridwen.circulation.SIP.messages.CheckIn;
import com.ceridwen.circulation.SIP.messages.CheckInResponse;
import com.ceridwen.circulation.SIP.messages.CheckOut;
import com.ceridwen.circulation.SIP.messages.CheckOutResponse;
import com.ceridwen.circulation.SIP.messages.EndPatronSession;
import com.ceridwen.circulation.SIP.messages.EndSessionResponse;
import com.ceridwen.circulation.SIP.messages.FeePaid;
import com.ceridwen.circulation.SIP.messages.FeePaidResponse;
import com.ceridwen.circulation.SIP.messages.Hold;
import com.ceridwen.circulation.SIP.messages.HoldResponse;
import com.ceridwen.circulation.SIP.messages.ItemInformation;
import com.ceridwen.circulation.SIP.messages.ItemInformationResponse;
import com.ceridwen.circulation.SIP.messages.ItemStatusUpdate;
import com.ceridwen.circulation.SIP.messages.ItemStatusUpdateResponse;
import com.ceridwen.circulation.SIP.messages.PatronEnable;
import com.ceridwen.circulation.SIP.messages.PatronEnableResponse;
import com.ceridwen.circulation.SIP.messages.PatronInformation;
import com.ceridwen.circulation.SIP.messages.PatronInformationResponse;
import com.ceridwen.circulation.SIP.messages.PatronStatusRequest;
import com.ceridwen.circulation.SIP.messages.PatronStatusResponse;
import com.ceridwen.circulation.SIP.messages.Renew;
import com.ceridwen.circulation.SIP.messages.RenewAll;
import com.ceridwen.circulation.SIP.messages.RenewAllResponse;
import com.ceridwen.circulation.SIP.messages.RenewResponse;
import com.ceridwen.circulation.SIP.messages.SCStatus;
import com.ceridwen.sip2testserver.SIPServerContextListener;
import java.util.Date;
import javax.servlet.ServletContextEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *  Test SIP2 Server backend driver tests
 */
public class TestDriverTest {
  TestDriver driver;
  
  /**
   * Constructor
   */ 
  public TestDriverTest() {
  }
  
  /**
   * Test setup
   */
  @BeforeClass
  public static void setUpClass() {
  }
  
  /**
   * Test teardown
   */
  @AfterClass
  public static void tearDownClass() {
  }
  
  /**
   * Test setup - initialise driver
   */
  @Before
  public void setUp() {
    driver = (TestDriver)new TestDriverFactory().getDriver();
  }
  
  /**
   * Test teardown
   */
  @After
  public void tearDown() {
  }

  /**
   * Test of BlockPatron method, of class TestDriver.
   * 
   * @throws Exception  exception error
   */
  @Test
  public void testBlockPatron() throws Exception {
    System.out.println("BlockPatron");
    BlockPatron request = new BlockPatron();
    PatronStatusResponse expResult = new PatronStatusResponse();
    PatronStatusResponse result = driver.BlockPatron(request);
    result.setTransactionDate(new Date());
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    assertEquals(expResult.encode(), result.encode());
  }

  /**
   * Test of CheckIn method, of class TestDriver.
   * 
   * @throws Exception  exception error
   */
  @Test
  public void testCheckIn() throws Exception {
    System.out.println("CheckIn");
    CheckIn request = new CheckIn();
    CheckInResponse expResult = new CheckInResponse();
    CheckInResponse result = driver.CheckIn(request);
    result.setTransactionDate(new Date());
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    assertEquals(expResult.encode(), result.encode());
  }

  /**
   * Test of CheckOut method, of class TestDriver.
   * 
   * @throws Exception  exception error
   */
  @Test
  public void testCheckOut() throws Exception {
    System.out.println("CheckOut");
    CheckOut request = new CheckOut();
    CheckOutResponse expResult = new CheckOutResponse();
    CheckOutResponse result = driver.CheckOut(request);
    result.setTransactionDate(new Date());
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    assertEquals(expResult.encode(), result.encode());
  }

  /**
   * Test of EndPatronSession method, of class TestDriver.
   * 
   * @throws Exception  exception error
   */
  @Test
  public void testEndPatronSession() throws Exception {
    System.out.println("EndPatronSession");
    EndPatronSession request = new EndPatronSession();
    EndSessionResponse expResult = new EndSessionResponse();
    EndSessionResponse result = driver.EndPatronSession(request);
    result.setTransactionDate(new Date());
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    assertEquals(expResult.encode(), result.encode());
  }

  /**
   * Test of FeePaid method, of class TestDriver.
   * 
   * @throws Exception  exception error
   */
  @Test
  public void testFeePaid() throws Exception {
    System.out.println("FeePaid");
    FeePaid request = new FeePaid();
    FeePaidResponse expResult = new FeePaidResponse();
    FeePaidResponse result = driver.FeePaid(request);
    result.setTransactionDate(new Date());
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    assertEquals(expResult.encode(), result.encode());
 }

  /**
   * Test of Hold method, of class TestDriver.
   * 
   * @throws Exception  exception error
   */
  @Test
  public void testHold() throws Exception {
    System.out.println("Hold");
    Hold request = new Hold();
    HoldResponse expResult = new HoldResponse();
    HoldResponse result = driver.Hold(request);
    result.setTransactionDate(new Date());
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    assertEquals(expResult.encode(), result.encode());
  }

  /**
   * Test of ItemInformation method, of class TestDriver.
   * 
   * @throws Exception  exception error
   */
  @Test
  public void testItemInformation() throws Exception {
    System.out.println("ItemInformation");
    ItemInformation request = new ItemInformation();
    ItemInformationResponse expResult = new ItemInformationResponse();
    ItemInformationResponse result = driver.ItemInformation(request);
    result.setTransactionDate(new Date());
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    assertEquals(expResult.encode(), result.encode());
  }

  /**
   * Test of ItemStatusUpdate method, of class TestDriver.
   * 
   * @throws Exception  exception error
   */
  @Test
  public void testItemStatusUpdate() throws Exception {
    System.out.println("ItemStatusUpdate");
    ItemStatusUpdate request = new ItemStatusUpdate();
    ItemStatusUpdateResponse expResult = new ItemStatusUpdateResponse();
    ItemStatusUpdateResponse result = driver.ItemStatusUpdate(request);
    result.setTransactionDate(new Date());
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    assertEquals(expResult.encode(), result.encode());
  }

  /**
   * Test of PatronEnable method, of class TestDriver.
   * 
   * @throws Exception  exception error
   */
  @Test
  public void testPatronEnable() throws Exception {
    System.out.println("PatronEnable");
    PatronEnable request = new PatronEnable();
    PatronEnableResponse expResult = new PatronEnableResponse();
    PatronEnableResponse result = driver.PatronEnable(request);
    result.setTransactionDate(new Date());
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    assertEquals(expResult.encode(), result.encode());
  }

  /**
   * Test of PatronInformation method, of class TestDriver.
   * 
   * @throws Exception  exception error
   */
  @Test
  public void testPatronInformation() throws Exception {
    System.out.println("PatronInformation");
    PatronInformation request = new PatronInformation();
    request.setPatronIdentifier("12345");
    request.getSummary().setChargedItems(true);
    request.setStartItem(1);
    request.setEndItem(2);
    request.setPatronPassword("password");
    PatronInformationResponse expResult = new PatronInformationResponse();
    PatronInformationResponse result = driver.PatronInformation(request);
    result.setTransactionDate(new Date());
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    expResult.setChargedItemsCount(99);
    expResult.setFineItemsCount(99);
    expResult.setHoldItemsCount(99);
    expResult.setOverdueItemsCount(99);
    expResult.setRecallItemsCount(99);
    expResult.setUnavailableHoldsCount(99);
    expResult.setChargedItemsLimit(99);
    expResult.setHoldItemsLimit(99);
    expResult.setOverdueItemsLimit(99);
    expResult.setValidPatronPassword(true);
    expResult.setPatronIdentifier(request.getPatronIdentifier());
    expResult.setPersonalName("Name for id 12345");
    expResult.setChargedItems(new String[]{"Charge 1", "Charge 2"});
    assertEquals(expResult.encode(), result.encode());
    
    request.getSummary().setFineItems(true);
    result = driver.PatronInformation(request);
    result.setTransactionDate(new Date());
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    expResult.setChargedItems(null);
    expResult.setFineItems(new String[]{"Fine 1", "Fine 2"});
    assertEquals(expResult.encode(), result.encode());

    request.getSummary().setHoldItems(true);
    result.setTransactionDate(new Date());
    result = driver.PatronInformation(request);
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    expResult.setFineItems(null);
    expResult.setHoldItems(new String[]{"Hold 1", "Hold 2"});
    assertEquals(expResult.encode(), result.encode());

    request.getSummary().setOverdueItems(true);
    result = driver.PatronInformation(request);
    result.setTransactionDate(new Date());
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    expResult.setHoldItems(null);
    expResult.setOverdueItems(new String[]{"Overdue Item 1", "Overdue Item 2"});
    assertEquals(expResult.encode(), result.encode());

    request.getSummary().setRecallItems(true);
    result.setTransactionDate(new Date());
    result = driver.PatronInformation(request);
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    expResult.setOverdueItems(null);
    expResult.setRecallItems(new String[]{"Recall 1", "Recall 2"});
    assertEquals(expResult.encode(), result.encode());

    request.getSummary().setUnavaibleHolds(true);
    result.setTransactionDate(new Date());
    result = driver.PatronInformation(request);
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    expResult.setRecallItems(null);
    expResult.setUnavailableHoldItems(new String[]{"Unavailable Hold 1", "Unavailable Hold 2"});
    assertEquals(expResult.encode(), result.encode());
  }

  /**
   * Test of PatronStatus method, of class TestDriver.
   * 
   * @throws Exception  exception error
   */
  @Test
  public void testPatronStatus() throws Exception {
    System.out.println("PatronStatus");
    PatronStatusRequest request = new PatronStatusRequest();
    PatronStatusResponse expResult = new PatronStatusResponse();
    PatronStatusResponse result = driver.PatronStatus(request);
    result.setTransactionDate(new Date());
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    assertEquals(expResult.encode(), result.encode());
  }

  /**
   * Test of Renew method, of class TestDriver.
   * 
   * @throws Exception  exception error
   */
  @Test
  public void testRenew() throws Exception {
    System.out.println("Renew");
    Renew request = new Renew();
    RenewResponse expResult = new RenewResponse();
    RenewResponse result = driver.Renew(request);
    result.setTransactionDate(new Date());
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    assertEquals(expResult.encode(), result.encode());
  }

  /**
   * Test of RenewAll method, of class TestDriver.
   * 
   * @throws Exception  exception error
   */
  @Test
  public void testRenewAll() throws Exception {
    System.out.println("RenewAll");
    RenewAll request = new RenewAll();
    RenewAllResponse expResult = new RenewAllResponse();
    RenewAllResponse result = driver.RenewAll(request);
    result.setTransactionDate(new Date());
    expResult.setTransactionDate(result.getTransactionDate()); //sync time stamps
    assertEquals(expResult.encode(), result.encode());
  }
 
  /**
   * Test of Status method, of class TestDriver.
   * 
   * @throws Exception  exception error
   */
  @Test
  public void testStatus() throws Exception {
    System.out.println("Status");
    SCStatus request = new SCStatus();
    ACSStatus expResult = new ACSStatus();
    ACSStatus result = driver.Status(expResult, request);
    assertEquals(expResult.encode(), result.encode());
  }
  
  /**
   * Test of initialize and destroy methods, of class IPServerContextListener.
   * 
   * @throws Exception  exception error
   */
  @Test
  public void testSIPServerContextListener() throws Exception {
    System.out.println("SIPServerContextListener");
    SIPServerContextListener listener;
    listener = new SIPServerContextListener();
    
    listener.SIP2_Bind_IP = "localhost";
    listener.SIP2_Bind_Port = 55131;
    listener.SIP2_Checksum_Mode = true;
    listener.SIP2_Server_Name = "testcase";
    listener.SIP2_Server_Backend = "com.ceridwen.sip2testserver.driver.TestDriverFactory";
        
    listener.contextInitialized(null);    
    listener.contextDestroyed(null);
  }
          

}
