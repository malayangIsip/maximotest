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
import executionEngine.Base;
import executionEngine.TestAutomation;
import utility.Log;
import utility.RetryAnalyzer;

public class PRWorkflow extends TestAutomation {
    static String testCase = "";	
    static String testName = "PRWorkflow";
	
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
    
    @AfterMethod
    public void logout() throws Exception {
    	logout(testName, testCase);

    }
    
    
    @Test(groups = {"workflow"}, alwaysRun = true)
    public void createPRwithValidWO() {
    	testCase = "createPRwithValidWO";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  

			action.login("mxfldeng");
			action.goToPRPage();
			action.createPRwithLines(testCase);
	
	 	 	action.storeValue("txtbx_WONUM", "WONUM");
	 	 	action.storeValue("txtbx_PRNUM", "PRNUM");

	    } catch (Exception e) {
	    	Log.error("Exception createPRwithValidWO--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test(groups = {"workflow"}, dependsOnMethods= {"createPRwithValidWO"})
    public void closeReferencedWO() {
    	testCase = "closeReferencedWO";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  

			action.login("maxadmin");
			action.goToWOPage();
			action.quickSearch(action.getStoredValue("WONUM"));
			
			Log.info("WO status = "+action.getAttributeValue("txtbx_Status"));
			if (!action.getAttributeValue("txtbx_Status").equalsIgnoreCase("COMP") && !action.getAttributeValue("txtbx_Status").equalsIgnoreCase("CLOSED")) {
				action.changeStatus("completed"); 
			}		
			if (action.getAttributeValue("txtbx_Status").equalsIgnoreCase("COMP")) {
				action.changeStatus("closed");
			}
			action.assertValue("txtbx_Status", "Close");

		} catch(AssertionError ae){
			Log.error("Assertion failed closeReferencedWO--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found closeReferencedWO--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception closeReferencedWO--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test(groups = {"workflow"}, dependsOnMethods= {"closeReferencedWO"})
    public void routePR() {
    	testCase = "routePR";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  

			action.login("mxfldeng");
			action.goToPRPage();
			action.quickSearch(action.getStoredValue("PRNUM"));
		
			Log.info("Route PR with closed WO..............");
			action.route();
			action.verifyAlert("Cannot continue the process, the associated WO is not in one of the following Statuses: APPR, INPRG, SCHED, WMATL, WSCH, WTACCESS or COMP");
			action.clickClose();
			
			Log.info("Route PR with unapproved WO..............");
			action.click("btn_WO_chevron");
	 		action.click("lnk_Related_WO");
	 		action.clickListViewTab();
	 		action.advancedSearch(new String[][] {{"txtbx_Status", "CREATE"}});
		 	action.clickFirstValueFromList();
	 	 	action.clickReturnWithValue();
	 	 	action.verifyAlert("BMXAA4191E");
	 	 	action.clickOK();
	 	 	
	 	 	action.clickListViewTab();
	 	 	action.advancedSearch(new String[][] {{"txtbx_Status", "APPR"}, {"txtbx_Description2", "Shakedown"}});
		 	action.clickFirstValueFromList();
	 	 	action.clickReturnWithValue();
	 	 	
	 	 	action.save();
	 	 	
	 	 	action.route();
	 	 	action.assertValue("txtbx_Status", "WAPPR");
	 	 	Assert.assertEquals(action.inWorkflow(), true);
	 	 	action.clickOK();
	 	 	Assert.assertFalse(action.getWFApprover().isEmpty());
  			action.storeValue("txtbx_PRNUM", "PRNUM");
	 	 	action.approveWF();
	 	 	
	 	 	action.assertValue("txtbx_Status", "CLOSE");
	 	 	Assert.assertEquals(action.inWorkflow(), false);

		} catch(AssertionError ae){
			Log.error("Assertion failed routePR--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found routePR--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception routePR--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test(groups = {"workflow"}, dependsOnMethods= {"routePR"})
    public void duplicateClosedPR() {
    	testCase = "duplicateClosedPR";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("mxfldeng");
			action.goToPRPage();
			action.quickSearch(action.getStoredValue("PRNUM"));
			
			action.duplicate();
			Log.info("Populate Required date");
			action.clickPRLinesTab();
			action.clickViewDetailsRow1();
			action.input("txtbx_RequiredDate", action.getDate().plusWeeks(3).toString());
			action.save();
			
			action.route();
	 	 	action.assertValue("txtbx_Status", "WAPPR");
	 	 	Assert.assertEquals(action.inWorkflow(), true);
	 	 	action.clickOK();
	 	 	Assert.assertFalse(action.getWFApprover().isEmpty());
  			action.storeValue("txtbx_PRNUM", "PRNUM");
	 	 	action.approveWF();
	 	 	
	 	 	action.assertValue("txtbx_Status", "CLOSE");
	 	 	Assert.assertEquals(action.inWorkflow(), false);

		} catch(AssertionError ae){
			Log.error("Assertion failed duplicateClosedPR--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found duplicateClosedPR--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception duplicateClosedPR--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}

}





