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

public class SRWorkflow extends TestAutomation {
    static String testCase = "";	
    static String testName = "SRWorkflow";
	
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
    
    @Test(groups = {"workflow"})
    public void verifySRWorkflow() {
    	testCase = "verifySRWorkflow";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("mxfldeng");
			action.goToSRPage();
			
			String[][] toSearch = new String[][] {{"txtbx_ReporterType", "HEAT"}, {"txtbx_Status", "NEW"}};
			action.advancedSearch(toSearch);
			action.clickFirstValueFromList();
	
			action.route();
			action.verifyAlert("Heat Alarm Activation SRs cannot be entered into Workflow");
			action.clickClose();
			
			action.clickListViewTab();
			
			toSearch = new String[][] {{"txtbx_ReporterType", "KRNETWORK"}, {"txtbx_Status", "INPROG"}};
			action.advancedSearch(toSearch);
			action.clickFirstValueFromList();

			action.route();
			action.verifyAlert("SR can only be submitted to workflow when its status is NEW.");
			action.clickClose();

		} catch(AssertionError ae){
			Log.error("Assertion failed verifySRWorkflow--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found verifySRWorkflow--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception verifySRWorkflow--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test(groups = {"workflow"})
    public void workflowSRas155User() {
    	testCase = "workflowSRas155User";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try {  
			action.login("mx155");
			action.goToSRPage();
			
			action.createSR(testCase, "KRNETWORK");
			
			action.assertValue("txtbx_Status", "NEW");
			
			//route SR not priority 1
			action.route();
			action.assertValue("txtbx_Status", "NEW");
			
			action.input("txtbx_Priority", "1");
			action.input("txtbx_ActivityType", "drainage");
			action.save();
			
			action.route();
			action.assertValue("txtbx_Status", "INPROG");
			action.clickRelatedRecordTab();
			action.isEmpty("tbl_Related_WO", false);
			
			action.goToRelatedWO(1);
			action.assertValue("txtbx_Status", "APPR");
			action.assertValue("txtbx_Worktype", "EM");
			action.clickReturn();

		}catch(AssertionError ae){
			Log.error("Assertion failed workflowSRas155User--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found workflowSRas155User--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception workflowSRas155User--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test(groups = {"workflow"})
    public void workflowSRasNon155User() {
    	testCase = "workflowSRasNon155User";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try {  
			action.login("mxfldeng");
			action.goToSRPage();
			action.createSR(testCase, "KRNETWORK");
			action.assertValue("txtbx_Status", "NEW");
			action.assertValue("txtbx_Source", "MANUAL");
			
			action.route();
			action.assertValue("txtbx_Status", "NEW");
			action.clickRelatedRecordTab();
			action.isEmpty("tbl_Related_WO", true);
		}catch(AssertionError ae){
			Log.error("Assertion failed workflowSRasNon155User--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found workflowSRasNon155User--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception workflowSRasNon155User--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
}
