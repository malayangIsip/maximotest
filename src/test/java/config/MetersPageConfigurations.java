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

public class MetersPageConfigurations extends TestAutomation {
	static boolean runStatus;
    static String testCase = "";	
    static String testName = "MetersPageConfigurations";
	
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
    public void testMeters() {
    	testCase = "testMeters";
    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("maxadmin");
			action.goMetersPage();

			action.click("btn_Advanced_Search");
			action.click("btn_MeterType");
			action.checkValueExists("CHARACTERISTIC, CONTINUOUS, GAUGE", true);
			action.clickOK();
			
			action.input("txtbx_Meter", "APRON");
			action.clickFind();
			
			action.clickNew();
			action.input("txtbx_Meter", "CONFMETER");
			action.input("txtbx_Description", testCase);
			action.input("txtbx_MeterType", "GAUGE");
			action.save();
			
			action.click("lnk_Delete");
			action.click("btn_Yes");
			
			action.click("btn_Advanced_Search");
			action.input("txtbx_Meter", "CONFMETER");
			action.clickFind();
			
			action.verifyAlert("BMXAA4186E");
			action.clickOK();
	
	    } catch (Exception e) {
	    	Log.error("Exception testMeters--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
}
