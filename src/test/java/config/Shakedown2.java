package config;

import static executionEngine.Base.action;
import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;
import static org.testng.Assert.assertEquals;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import executionEngine.Base;
import executionEngine.TestAutomation;
import utility.Log;

/*
 * 011.02.1 SR As MXPLAN
 */
public class Shakedown2 extends TestAutomation {
	static String testName = "Shakedown2";
    static String testCase = "";

    @BeforeClass
    public void init() {
    	Log.startTest(testName);
    	extentTest = extent.startTest(testName);
    }

    @Override
	@AfterClass
    public void tearDown() {
    	extent.endTest(extentTest);
		Log.endTest(testName);
		
		super.tearDown();
    }
    
    @Override    
    @BeforeMethod
    public void setUp() {
    	action.openBrowser("Chrome");
    	
    	Base.bResult = true;
    }
    
    @Override  
    @AfterMethod
    public void logout() throws Exception {
    	logout(testName, testCase);

    }

    @Test(groups = {"smoke"})
    void createSRasMXPLANUser() {
    	testCase = "createSRasMXPLANUser";
//    	extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
 		
 		try {
 			action.login("mxplan");
 			action.goToSRPage();
 			
 			action.createSR(testCase, "KRNETWORK");
 			
 			Log.info("Route SR...");
 			action.route();
 			Assert.assertEquals(action.inWorkflow(), false);
 			action.clickOK();
 			
 			action.assertValue("txtbx_Status", "NEW");
 			action.goToCreateWO();
 			
 			action.click("tab_RelatedRecord");
 			action.isEmpty("tbl_Related_WO", false);
 			
 			Log.info("Check the first Related WO ");
 			action.goToRelatedWO(1);
 			action.assertValue("txtbx_Status", "CREATE");
 			
 			action.changeStatus("canceled");
 			action.clickReturn();
 			
 			Log.info("Create more than 1 WO from SR should return error...");
 			action.goToCreateWO();
 			action.goToCreateWO();
 			action.verifyAlert("BMXZZ1009E");
 			action.clickOK();
 			
 			Log.info("Check the second Related WO ");
 			action.goToRelatedWO(2);
			
 			action.input("txtbx_Worktype", "CM");
 			action.click("txtbx_ActivityType");	
 			action.click("btn_ActivityType");	
 			action.clickFirstValueFromList();	 
 									
 			action.isNull("txtbx_ActivityType", false);
 			action.input("txtbx_ScheduledStart", action.getDate().plusWeeks(3).toString());
 			action.save();
 			
 			action.isNull("txtbx_GLAccount", false);
 			
 			action.route();
 			action.verifyAlert("The Work Order has zero planned cost");
 			action.click("btn_Close");
 			
 			action.clickPlansTab();
 			action.click("btn_NewRow_Labour");
 			
 			action.input("txtbx_Trade", "LEVEL1");
 			action.input("txtbx_Quantity", "2");
 			action.input("txtbx_PersonHours", "2");
 			action.save();
 			
 			action.assertValue("txtbx_Labour_Rate", "35.00");
 			action.assertValue("txtbx_LineCost", "140.00");
 			
 			action.clickMainTab();
 			action.routeWF("dfa");
 			
 			Assert.assertEquals(action.inWorkflow(), true);
 			action.clickOK();
 			action.assertValue("txtbx_Status", "PLAN");
 			Assert.assertFalse(action.getWFApprover().isEmpty());
 			action.storeValue("txtbx_WONUM", "WONUM");
  			
  			action.approveWF();
  			Assert.assertEquals(action.inWorkflow(), false);

 		} catch(AssertionError ae){
			Log.error("Assertion failed createSRasMXPLANUser--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found createSRasMXPLANUser--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception createSRasMXPLANUser--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
    }
	
//	@Test(dependsOnMethods={"createSRasMXPLANUser"})
//    void approveWO() {
//	   	testCase = "Shakedown2 approveWO";
//	   	extentTest.log(LogStatus.INFO, testCase);
//		Log.startTestCase(testCase);
//	 		
//		try {
//			Log.info("Start approveWO........................");
//			action.login("mxareamgr2");
//			action.approveWF(action.getStoredValue("WONUM"));
//			
//			Assert.assertEquals(action.inWorkflow(), false);
//	 			
//			Log.info("END approveWO........................");
//	    } catch(AssertionError ae){
//			Log.error("Assertion failed approveWO--- " + ae.getMessage());
//			extentTest.log(LogStatus.ERROR, ae.getMessage());
//	    	Base.bResult = false;
//			Assert.fail();
//	    } catch (NoSuchElementException e) {
//	    	Log.error("Element not found approveWO--- " + e.getMessage());
//	    	extentTest.log(LogStatus.ERROR, e.getMessage());
//	    	Base.bResult = false;
//			Assert.fail();
//	    } catch (Exception e) {
//	    	Log.error("Exception approveWO--- " + e.getMessage());
//	    	extentTest.log(LogStatus.ERROR, e.getMessage());
//	    	Base.bResult = false;
//			Assert.fail();
//	    }	
//	}
		
	@Test(groups = {"smoke"}, dependsOnMethods={"createSRasMXPLANUser"})
	void verifyWOasMxleadhandUser() {
	   	testCase = "verifyWOasMxleadhandUser";
//	   	extentTest.log(LogStatus.INFO, testCase);
		Log.startTestCase(testCase);
	 		
		try {
			action.login("mxleadhand");
			action.goToWOPage();
			action.quickSearch(action.getStoredValue("WONUM"));
			
			action.changeStatus("scheduled");
			action.assertValue("txtbx_Status", "SCHED");
			
			action.changeStatus("in progress");
			action.assertValue("txtbx_Status", "INPRG");
			
			action.isDisabled("lnk_SetSchedDate", true);
			action.click("lnk_Complete");
			action.clickOK();
			action.verifyAlert("BMXZZ03222EN");
			action.clickClose();
			action.assertValue("txtbx_Status", "COMP");

	    } catch(AssertionError ae){
			Log.error("Assertion failed verifyWOasMxleadhandUser--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
	    	Base.bResult = false;
			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found verifyWOasMxleadhandUser--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception verifyWOasMxleadhandUser--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
			Assert.fail();
	    }	
	 }
		
		
}
