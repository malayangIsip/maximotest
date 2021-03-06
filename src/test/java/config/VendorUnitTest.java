package config;

import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;
import static executionEngine.Base.action;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import executionEngine.Base;
import executionEngine.TestAutomation;
import utility.Log;

public class VendorUnitTest extends TestAutomation {
	static String userAdmin = "mme9310";

    static String testCase = "";
    static String testName = "Vendor";
	
    static String WONUM = null;
    static String VENDOR = null;
    
    String newDesc = "Test validate Vendor - inactivate Vendor";

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
//    	action.openBrowser("Chrome");
    	
    	Base.bResult = true;
    }
    
    @AfterMethod
    public void logout() throws Exception {
    	logout(testName, testCase);
    }
    
    
	@DataProvider
 	public Object[][] data() {
 		return new Object[][] { //{ "mxfldeng", "CAP"}, 
 								{"mxfldeng", "CM"} };
 	}
	
	/*MAX-1002 - Validation of Vendor on WO workflow only*/
	@Test(dataProvider = "data")
	public void testVendor(String user, String worktype) throws Exception {
		noVendorValidationOnSave(user, worktype);
		validateInvalidVendorOnWF(user, worktype);
		validateVendorOnWF(user, worktype);
		noVendorValidationOnChangeStatus();
	}
	
	void noVendorValidationOnSave(String user, String worktype) throws Exception {
		testCase = "noVendorValidationOnSave " + worktype;
		Log.startTestCase(testCase);
		try {
			Log.info("Should only validate Vendor status if WO is in unapproved status");
			action.openBrowser("Chrome");
	    	action.login(user);
			action.goToWOPage();

			action.createWOwithServicePlans(testCase, worktype);
			action.storeValue("txtbx_Vendor", "VENDOR");
			action.storeValue("txtbx_WONUM", "WONUM");
			WONUM = action.getStoredValue("WONUM");
			VENDOR = action.getStoredValue("VENDOR");
			
			Log.info("Deactivate Vendor and try to save WO - should generate error");
			action.deactivateVendor(VENDOR, true);

			action.openBrowser("Chrome");
			action.login(user);
			action.goToWOPage();
			action.quickSearch(WONUM);
			
			Log.info("Try to save edit the WO and save");
			action.input("txtbx_Description", newDesc);
			action.save();
	    } catch (Throwable e) {
	    	action.deactivateVendor(VENDOR, false); //reactivate when error
	    	Log.error("Exception --- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}	
		
	void validateInvalidVendorOnWF(String user, String worktype) throws Exception {
		testCase = "validateInvalidVendorOnWF";
		Log.startTestCase(testCase);

		try {
			action.openBrowser("Chrome");
	    	action.login(user);
			action.goToWOPage();
			
			action.quickSearch(WONUM);
			
			Log.info("change status to PLAN if Renewal WO");
			if (worktype.equals("CAP") || worktype.equals("AMREN")) {
				driver.close();
				action.changeWOStatusToPlan(WONUM, worktype); 
				action.assertValue("txtbx_Status", "PLAN");
			}
				
			Log.info("Reactivate Vendor");
			action.deactivateVendor(VENDOR, false);

			action.openBrowser("Chrome");
			action.login(user);
			action.goToWOPage();
			action.quickSearch(WONUM);
			action.waitElementExists("txtbx_WONUM");
				
			Log.info("Try to route the WO - should be success");
			Log.info("Click btn_Route..................");
			action.route();
			action.clickOK();
			action.assertValue("txtbx_Status", "PLAN");
			Assert.assertEquals(action.inWorkflow(), true);
				
			Log.info("Deactivate vendor after route and try to approve WO........");
			action.deactivateVendor(VENDOR, true);
			
			action.openBrowser("Chrome");
			action.login(user);
			action.goToWOPage();
			action.quickSearch(WONUM);
			action.waitElementExists("txtbx_WONUM");
			Assert.assertFalse(action.getWFApprover().isEmpty()); //calling getWFApprover() stores WFApprover
	  		action.storeValue("txtbx_WONUM", "WONUM"); 
	  	
			action.loginApprover();
			action.openWFAssignment();
			action.clickOK();
			action.verifyAlert("This Work Order cannot be approved because either one or more Materials or Services have an invalid vendor. Please rectify and resubmit to workflow.");
			action.clickClose();
			
			Assert.assertEquals(action.inWorkflow(), false);
			action.clickOK();
			Assert.assertTrue(action.getWFApprover().isEmpty());
			action.assertValue("txtbx_Status", "PLAN");
			Log.info("Verify email notification sent to WF initiator>>>>>>>>>>>>>");

		} catch (Throwable e) {
			action.deactivateVendor(VENDOR, false); //reactivate when error
		   	Log.error("Exception --- " + e.getMessage());
		   	extentTest.log(LogStatus.ERROR, e.getMessage());
		   	Base.bResult = false;
	 		Assert.fail();
		}
	}
	
	
	void validateVendorOnWF(String user, String worktype) throws Exception {
		testCase = "validateVendorOnWF";
		Log.startTestCase(testCase);

		try {
			Log.info("Check that Vendor is deactivated................");
			action.deactivateVendor(VENDOR, true);
			
			action.openBrowser("Chrome");
	    	action.login(user);
			action.goToWOPage();
			action.quickSearch(WONUM);
			
			Log.info("Try to route the WO - should generate error");
			action.route();
			action.verifyAlert("This Work Order cannot be work flowed because either one or more Materials or Services have an invalid vendor");
			driver.close();
		
			Log.info("Reactivate Vendor............");
			action.deactivateVendor(VENDOR, false);

			action.openBrowser("Chrome");
			action.login(user);
			action.goToWOPage();
			action.quickSearch(WONUM);
			action.waitElementExists("txtbx_WONUM");
			
			Log.info("Try to route the WO - should be success");
			action.route();
			action.clickOK();
			action.assertValue("txtbx_Status", "PLAN");
			Assert.assertEquals(action.inWorkflow(), true);
			action.clickOK();
			
			action.approveWOWF(WONUM);
	    } catch (Throwable e) {
	    	action.deactivateVendor(VENDOR, false); //reactivate when error
	    	Log.error("Exception --- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }
	}
		
	void noVendorValidationOnChangeStatus() throws Exception {
		testCase = "noVendorValidationOnChangeStatus";
		Log.startTestCase(testCase);
		
		try {
			Log.info("Deactivate Vendor");
			action.deactivateVendor(VENDOR, true);
			
			action.openBrowser("Chrome");
			action.login(userAdmin);
			action.goToWOPage();
			
			action.quickSearch(WONUM);
			
			Log.info(WONUM+" Completing WO should not validate Vendor Status - Vendor still in inactive state");
			action.changeStatus("completed");
			Thread.sleep(3000);
			
			Log.info(WONUM+" Closing WO should not validate Vendor Status - Vendor still in inactive state");
			action.changeStatus("closed");
			
			Log.info("Reactivate Vendor"); 
			action.deactivateVendor(VENDOR, false);
	    } catch (Throwable e) {
	    	action.deactivateVendor(VENDOR, false); //reactivate when error
	    	Log.error("Exception --- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}

}
