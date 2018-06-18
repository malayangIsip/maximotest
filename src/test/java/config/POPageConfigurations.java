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

public class POPageConfigurations extends TestAutomation {
    static String testCase = "";	
    static String testName = "POPageConfigurations";
	
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
    
    @Test(alwaysRun = true)
    public void testPO() {
    	testCase = "testPO";
    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("maxadmin");
			action.goToPOPage();
			
			String where = "(status = 'APPR' and ponum like '48%' and historyflag = 0 and REVISIONNUM=0 and statusdate >=  '2015-05-14') and ponum not in (select ponum from po where REVISIONNUM > 0)";
			action.whereClause(where);
			action.clickFirstValueFromList();
			
			action.click("lnk_ChangeStatus");
			action.click("txtbx_NewStatus");
			action.checkStatusExists("Waiting on Approval", false);
			action.clickCancel();
			
			action.click("tab_ListView");
			action.createPO(testCase);
			
			action.click("lnk_ChangeStatus");
			action.click("txtbx_NewStatus");
			action.checkStatusExists("Approved, Canceled, In Progress", true);
			action.clickCancel();
	
	    } catch (Exception e) {
	    	Log.error("Exception testPO--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
}
