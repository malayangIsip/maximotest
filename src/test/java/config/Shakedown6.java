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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import executionEngine.Base;
import executionEngine.TestAutomation;
import utility.Log;

/*
 * 011.07.1 Asset WF
 */
public class Shakedown6 extends TestAutomation {
    static String testCase = "";
    static String testName = "Shakedown6";
	
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
    

    /*
     * Scenario 1: User is auto-approver; Asset has no parent; non-linear
     * Scenario 2: User is auto-approver; Asset has no parent; linear
     * Scenario 3: User is auto-approver; Asset has active parent; non-linear
     * Scenario 4: User is auto-approver; Asset has active parent; linear
     * 
     * Scenario 5: User not auto-approver; Asset has no parent; non-linear; approve
     * Scenario 6: User not auto-approver; Asset has no parent; non-linear; Do not approve
     * Scenario 7: User not auto-approver; Asset has no parent; linear; approve
     * Scenario 8: User not auto-approver; Asset has no parent; linear; Do not approve
     * 
     * Scenario 9: User not auto-approver; Asset has parent; non-linear; approve
     * Scenario 10: User not auto-approver; Asset has parent; non-linear; Do not approve
     * Scenario 11: User not auto-approver; Asset has parent; linear; approve
     * Scenario 12: User not auto-approver; Asset has parent; linear; Do not approve
     
     * Scenario 13: Area and Region are Multiple
     */
    
    @DataProvider
 	public Object[][] data() {
 		return new Object[][] {// {"mme9310", "Shakedown6 scenario1", false, false, null}, 
// 								{"mme9310", "Shakedown6 scenario2", false, true, null},
// 								{"mme9310", "Shakedown6 scenario3", true, false, null},
// 								{"mme9310", "Shakedown6 scenario4", true, true, null}, 
 								{"mxfldeng", "scenario5", false, false, true}, 
 								{"mxfldeng", "scenario6", false, false, false},
 								{"mxfldeng", "scenario7", false, true, true},
 								{"mxfldeng", "scenario8", false, true, false},
 								{"mxfldeng", "scenario9", true, false, true},
 								{"mxfldeng", "scenario10", true, false, false},
 								{"mxfldeng", "scenario11", true, true, true},
 								{"mxfldeng", "scenario12", true, true, false} 
 		};

 	}
    
	@Test(groups = {"smoke"}, dataProvider = "data")
    void test(String user, String desc, boolean hasParent, boolean isLinear, Boolean approve) throws Exception {
    	testCase = desc;
//    	extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
 		
 		try {
 			action.login(user);
 			action.goToAssetPage();
 			
 			if (isLinear) {
 				action.createLinearAsset(desc, hasParent);
 			} else {
 				action.createAsset(desc, hasParent);
 			}
 			
 			action.route();
			
 			if (approve == null) {
 				//user is approver
				//TODO if originator is an asset approver doesn't seem to be working
// 	 			Assert.assertEquals(action.inWorkflow(), false);
 			} else if (approve == true) {
  				Assert.assertEquals(action.inWorkflow(), true);
 				action.clickOK();
 				Assert.assertFalse(action.getWFApprover().isEmpty());
 				action.storeValue("txtbx_AssetNum", "ASSETNUM");
  			
 				Log.info("Approve asset="+ action.getStoredValue("ASSETNUM"));
 				action.approveWF();
 				
 				action.assertValue("txtbx_Status", "ACTIVE");
 				Assert.assertEquals(action.inWorkflow(), false);
 			} else if (approve == false) {
 				Assert.assertEquals(action.inWorkflow(), true);
 				action.clickOK();
 				Assert.assertFalse(action.getWFApprover().isEmpty());
 				action.storeValue("txtbx_AssetNum", "ASSETNUM");
 				Log.info("Approve asset >>>>>>>>>"+ action.getStoredValue("ASSETNUM"));
 				action.loginApprover();
 				action.openWFAssignment();
 				action.click("radio_WF_NotApproveAsset");
 				action.clickOK();
 	 			
 	 			action.assertValue("txtbx_Status", "PROPOSED");
 	 			Assert.assertEquals(action.inWorkflow(), false);
 			}

 		} catch(AssertionError ae){
			Log.error("Assertion failed Shakedown6--- " + ae.getMessage());
//			extentTest.log(LogStatus.ERROR, ae.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception Shakedown6--- " + e.getMessage());
//	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
    }	
	
	 /*
     * Scenario: Area and Region are Multiple
     */
    @Test(groups = {"smoke"})
    void multipleAreaAndRegion() throws Exception {
    	testCase = "multipleAreaAndRegion";
//    	extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
 		
 		try {
 			action.login("mxfldeng");
 			action.goToAssetPage();
 			
 			action.clickNew();
 			
 			action.input("txtbx_Description", testCase);
 			action.click("txtbx_Linear");
 			action.input("txtbx_Discipline", "Signals");
 			action.input("txtbx_Region", "Multiple");
 			action.input("txtbx_Area", "Multiple");
 			action.input("txtbx_Line", "Noline");
 			action.input("txtbx_LRM", "METRES");
 			action.input("txtbx_AssetStartMeasure", "1");
 			action.input("txtbx_AssetEndMeasure", "10");
 			//add parent asset
 			action.click("btn_Parent_chevron");
			action.click("lnk_SelectValue");
			action.clickFirstValueFromList();
			
			action.save();
			action.assertValue("txtbx_Status", "PROPOSED");
			
			action.route();
			action.verifyAlert("Asset with 'Multiple' as a value in Region or Area cannot pass the work flow.");
			action.clickClose();
			
			action.input("txtbx_Region", "Northern");
			action.save();
			
			action.route();
			action.verifyAlert("Asset with 'Multiple' as a value in Region or Area cannot pass the work flow.");
			action.clickClose();

			action.input("txtbx_Region", "Multiple");
			action.input("txtbx_Area", "Auckland Metro");
			action.save();
			
			action.route();
			action.verifyAlert("Asset with 'Multiple' as a value in Region or Area cannot pass the work flow.");
			action.clickClose();
			
			action.input("txtbx_Region", "Northern");
			action.save();
		
			action.route();
		
			action.assertValue("txtbx_Status", "PROPOSED");
			
			Assert.assertEquals(action.inWorkflow(), true);
			action.clickOK();
			Assert.assertFalse(action.getWFApprover().isEmpty());
			action.storeValue("txtbx_AssetNum", "ASSETNUM");
			
			Log.info("Approve asset="+ action.getStoredValue("ASSETNUM"));
			action.approveWF();

			action.assertValue("txtbx_Status", "ACTIVE");

 		} catch(AssertionError ae){
			Log.error("Assertion failed multipleAreaAndRegion--- " + ae.getMessage());
//			extentTest.log(LogStatus.ERROR, ae.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception multipleAreaAndRegion--- " + e.getMessage());
//	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
    }	
   
}
