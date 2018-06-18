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

public class Planning extends TestAutomation {
	static boolean runStatus;
    static String testCase = "";	
    static String testName = "Planning";
	
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
    
    @Test
    public void createPRwithValidWO() {
    	testCase = "createPRwithValidWO";
    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("mxfldeng");
			action.goToPRPage();

	    } catch (Exception e) {
	    	Log.error("Exception createPRwithValidWO--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}


}
