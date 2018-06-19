package config;

import static executionEngine.Base.action;
import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;

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

public class PMPageConfigurations extends TestAutomation {
    static String testCase = "";	
    static String testName = "PMPageConfigurations";
	
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
    
    @Test(alwaysRun = true)
    public void testPM() {
    	testCase = "testPM";
    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("maxadmin");
			action.goToPMPage();
			
			action.clickNew();
			action.click("btn_Worktype");
			action.checkValueExists("INSP, PM", true);
			action.input("txtbx_Lookup_Value_Search", "PM");
			action.click("btn_Filter");
			action.clickFirstValueFromList();
			
			action.isReadOnly("txtbx_WorkOrderStatus", true);
			action.isReadOnly("txtbx_LastStartDate", true);
			action.isReadOnly("txtbx_LastCompletionDate", true);
			action.isReadOnly("txtbx_EarliestNextDueDate", true);
			action.isReadOnly("txtbx_Sponsor", true);
			action.isReadOnly("txtbx_CreatedBy", true);
			action.isReadOnly("txtbx_RequestedBy", true);
			
			action.click("btn_Worktype");
			action.input("txtbx_Lookup_Value_Search", "INSP");
			action.click("btn_Filter");
			action.clickFirstValueFromList();
			
			action.isReadOnly("txtbx_WorkOrderStatus", true);
			action.isReadOnly("txtbx_LastStartDate", true);
			action.isReadOnly("txtbx_LastCompletionDate", true);
			action.isReadOnly("txtbx_EarliestNextDueDate", true);
			action.isReadOnly("txtbx_Sponsor", true);
			action.isReadOnly("txtbx_CreatedBy", true);
			action.isReadOnly("txtbx_RequestedBy", true);

	    } catch (Exception e) {
	    	Log.error("Exception testPM--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
}
