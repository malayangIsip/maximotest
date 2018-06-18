package config;

import static executionEngine.Base.action;
import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;

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
 * WO As MXLEADHAND
 */
public class Shakedown3 extends TestAutomation {
    static String testCase = "";
    static String testName = "Shakedown3";
 	
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
    public void logout() {
    	logout(testName, testCase);

    }
    
    @Test(groups = {"smoke"})
    void createWOasMXleadhandUser() {
    	testCase = "createWOasMXleadhandUser";
//    	extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
 		
 		try {
  			action.login("mxleadhand");
 			action.goToWOPage();
 			
 			action.clickNew();
 			action.input("txtbx_Description", "Shakedown 3");
 			action.input("txtbx_AssetNum", "1000014");
 			action.input("txtbx_Worktype", "AMREN");
 			action.input("txtbx_Priority", "4");
 			action.getActivityType();	
 			action.isNull("txtbx_ActivityType", false);
 			action.click("btn_FinancialYear");
 			action.clickFirstValueFromList();
 			action.input("txtbx_ScheduledStart", action.getDate().plusWeeks(3).toString());
 			action.save();
	
 			action.click("tab_Plans");	
 			action.click("tab_Materials");
 			action.click("btn_NewRow_Materials");
 					
 			action.input("txtbx_Item", "1028752");

 			action.getStoreroom();
 			action.save();
 			
 			action.route();
 			action.verifyAlert("Track Renewal WO cannot"); 
 			action.clickClose();
 			
 			action.click("tab_Main");
 			action.storeValue("txtbx_WONUM", "WONUM");
	
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
    
    @Test(groups = {"smoke"}, dependsOnMethods={"createWOasMXleadhandUser"})
    void changeWOStatus() {
    	testCase = "changeWOStatus";
//    	extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
 		
 		try {
 			action.login("mxsvcemgr1");
 			action.goToWOPage();
    	
 			action.quickSearch(action.getStoredValue("WONUM"));
 			
			action.assertValue("txtbx_Status", "CREATE");
			action.changeStatus("ready to plan");
			action.assertValue("txtbx_Status", "RTP");
			action.changeStatus("planned");
			action.assertValue("txtbx_Status", "PLAN");
			
			Log.info("Route WO...");
			action.routeWF("EngrRev");
			Assert.assertEquals(action.inWorkflow(), true);
 			action.clickOK();
 			action.assertValue("txtbx_Status", "ENGREV");
 			Assert.assertFalse(action.getWFApprover().isEmpty());
 			action.storeValue("txtbx_WONUM", "WONUM");
  			
  			action.approveWOWF(action.getStoredValue("WONUM"));
  			Assert.assertEquals(action.inWorkflow(), false);

 		} catch(AssertionError ae){
			Log.error("Assertion failed changeWOStatus--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found changeWOStatus--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception changeWOStatus--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
    }

//
//    @Test(dependsOnMethods={"changeWOStatus"})
//    void approveWO() {
//    	testCase = "Shakedown3 approveWO";
//    	extentTest.log(LogStatus.INFO, testCase);
// 		Log.startTestCase(testCase);
// 		
// 		try {
// 			Log.info("Start approveWO........................");
// 			action.login("mxsvcemgr1");
// 			action.approveWF(action.getStoredValue("WONUM"));
// 			
// 			Assert.assertEquals(action.inWorkflow(), false);
//			
// 			Log.info("END approveWO........................");
// 		} catch(AssertionError ae){
//			Log.error("Assertion failed createSRasMXPLANUser--- " + ae.getMessage());
//			extentTest.log(LogStatus.ERROR, ae.getMessage());
//	    	Base.bResult = false;
// 			Assert.fail();
//	    } catch (NoSuchElementException e) {
//	    	Log.error("Element not found createSRasMXPLANUser--- " + e.getMessage());
//	    	extentTest.log(LogStatus.ERROR, e.getMessage());
//	    	Base.bResult = false;
// 			Assert.fail();
//	    } catch (Exception e) {
//	    	Log.error("Exception createSRasMXPLANUser--- " + e.getMessage());
//	    	extentTest.log(LogStatus.ERROR, e.getMessage());
//	    	Base.bResult = false;
// 			Assert.fail();
//	    }	
//    }

}
