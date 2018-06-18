package config;

import static executionEngine.Base.OR;
import static executionEngine.Base.action;
import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;

import org.openqa.selenium.By;
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

public class WFUnitTests extends TestAutomation {
	String testName = "WFUnitTests Tests";
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
    public void logout() {
    	logout(testName, testCase);

    }

    
//    @Test
    public void testEQWOs() {
    	testCase = "testEQWOs";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("mxareamgr1");
			action.goToAssetPage();

	    } catch (Exception e) {
	    	Log.error("Exception testEQWOs--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @DataProvider
 	public Object[][] activityType() {
 		return new Object[][] { //{"TA-SH2: HEWLETTS ROAD, MT MAUNGANUI", "PWO1033"}, 
 								{"TA-SH27: WAHAROA", "PWO1033"} };
 	}
	
    /*
     * TA WO < 100k
     */
    @Test(groups = {"unitTest"}, alwaysRun = true, dataProvider = "activityType")
    public void testTAWOs(String activityType, String approver) {
    	testCase = "testTAWOs - "+activityType;
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("mxfldeng");
			action.goToWOPage();
			
			action.createWOwithMaterialPlans(testCase, "CAP");
			action.clickMainTab();
			action.click("btn_ActivityType");
			action.input("txtbx_Lookup_Value_Search", activityType);
			action.click("btn_Filter");
			action.clickFirstValueFromList();
			action.assertValue("txtbx_ActivityType", activityType);
			action.isNull("txtbx_GLAccount", false);
			action.save();
			action.storeValue("txtbx_WONUM", "WONUM");
			
			driver.close();
			action.changeWOStatusToPlan(action.getStoredValue("WONUM"), "CAP");
			
			driver.close();
			action.openBrowser("Chrome");
			action.login("mxfldeng");
			action.goToWOPage();
			action.quickSearch(action.getStoredValue("WONUM"));
			
			Log.info("Check if routed to correct approver");
			action.route();
			action.clickOK();
			;
//			Assert.assertEquals(action.inWorkflow(), true);
//			String wfApprover = driver.findElement(By.xpath("//table[contains(@summary,'Workflow Assignments')]/tbody/tr[4]/td[2]")).getText();
			Assert.assertEquals(approver.toUpperCase(), action.getWFApprover().toUpperCase()); 
//	 		action.clickOK();
	 		 		
	 		action.assertValue("txtbx_Status", "PLAN");
	 		
			action.approveWOWF(action.getStoredValue("WONUM"));

	    } catch (Exception e) {
	    	Log.error("Exception testTAWOs--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test(groups = {"unitTest"}, dependsOnMethods = {"testTAWOs"})
    public void testPOGeneratedByTAWO() {
    	testCase = "testPOGeneratedByWO";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("mme9310");
			action.goToPOPage();
			
			String[][] toSearch = new String[][] {{"txtbx_Status", "APPR"}, {"txtbx_WONUM", action.getStoredValue("WONUM")}};
			action.advancedSearch(toSearch);
			
//			action.quickSearch("48923099");

			action.storeValue("txtbx_PONUM", "PONUM");

			Log.info("Revise PO "+action.getStoredValue("PONUM"));
			action.click("lnk_RevisePO");
			action.input("txtbx_PODescription2", testCase);
			action.clickOK();
			
			action.assertValue("txtbx_Status", "PNDREV");
			
			action.clickPOLinesTab();
			action.clickNewRow();
			action.click("btn_Item_chevron");
	 		action.click("lnk_SelectValue");
	 		action.clickFirstValueFromList();
	 		
	 		action.input("txtbx_OrderUnit", "EA");
	 		action.input("txtbx_UnitCost", "60000");
	 		action.input("txtbx_RequiredDate", action.getDate().plusWeeks(3).toString());
	 		action.getStoreroom();
	 		
	 		if (action.isReadOnly("txtbx_ConversionFactor") == false) {
 	 			 action.input("txtbx_ConversionFactor", "1");
 	 		}
	 		action.save();
	 		
	 		action.route();
	 		Assert.assertEquals(action.inWorkflow(), true);
	 		action.clickOK();
	 	 	Assert.assertFalse(action.getWFApprover().isEmpty());
	 	 	
	 		Log.info("Check if routed to correct approver");
	 		Assert.assertEquals("PWO1033".toUpperCase(), action.getWFApprover().toUpperCase()); 

	    } catch (Exception e) {
	    	Log.error("Exception testPOGeneratedByWO--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}		
    
    @Test(groups = {"unitTest"}, dependsOnMethods = {"testTAWOs"})
    public void testColdstartPRwithTAWO() {
    	testCase = "testColdstartPRwithTAWO";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("mme9310");
			action.goToPRPage();
			
			action.createPRwithLines(testCase);
			Log.info("Assign a TA WO");
			action.click("tab_PR");
			action.input("txtbx_WONUM", action.getStoredValue("WONUM"));
			action.save();
			
			action.route();
	 		Assert.assertEquals(action.inWorkflow(), true);
	 		action.clickOK();
	 	 	Assert.assertFalse(action.getWFApprover().isEmpty());
	 	 	
	 		Log.info("Check if routed to correct approver");
	 		Assert.assertEquals("PWO1033".toUpperCase(), action.getWFApprover().toUpperCase()); 

	    } catch (Exception e) {
	    	Log.error("Exception testColdstartPRwithTAWO--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }		
    }

}
