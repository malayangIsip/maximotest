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

public class LocationsPageConfigurations extends TestAutomation {
    static String testCase = "";	
    static String testName = "LocationsPageConfigurations";
	
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
    public void loginAsMaxAdmin() {
    	testCase = "loginAsMaxAdmin";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("maxadmin");
			action.goToLocationPage();
			
			action.click("btn_Advanced_Search");
			action.click("btn_Discipline");
			action.checkValueExists("ELECTRICAL, SIGNALS, STRUCTURES, TELECOMS, TRACK, TRACTION", true);
			action.clickOK();
			
			action.click("btn_Area");
			action.checkValueExists("Auckland Metro, Greater Auckland, East & West, Christchurch, Dunedin, Greymouth, Hamilton East, Hamilton South, Multiple, Palmerston North, Wellington", true);
			action.clickOK();
			action.clickCancel();

			action.clickNew();
			action.click("btn_Save");
			action.waitElementExists("msg_Popup");
			action.verifyAlert("BMXAA7998E");
			action.clickOK();
			
			action.click("btn_Discipline");
			action.checkValueExists("ELECTRICAL, SIGNALS, STRUCTURES, TELECOMS, TRACK, TRACTION", true);
			action.clickCancel();
			
			action.click("btn_Region");
			action.checkValueExists("Central, Northern, Southern, Multiple", true);
			action.clickCancel();
			
			action.click("btn_Area");
			action.checkValueExists("Multiple", true);
			action.clickCancel();
			
			action.input("txtbx_Type", "OPERATING");
			action.input("txtbx_Description", testCase);
			action.input("txtbx_Discipline", "SIGNALS");
			action.input("txtbx_Region", "Southern");
			action.click("btn_Area");
			action.checkValueExists("Christchurch, Dunedin, Greymouth, Multiple", true);
			action.clickCancel();

			action.input("txtbx_Area", "Dunedin");
			action.input("txtbx_Line", "NOLINE");
			action.save();
			
			action.storeValue("txtbx_Location", "LOCATION");
			action.click("lnk_Delete");
			action.clickYes();
			
			String[][] toSearch = new String[][] {{"txtbx_Location", action.getStoredValue("LOCATION")}};
			action.advancedSearch(toSearch);
			action.verifyAlert("BMXAA4186E");

	    } catch (Exception e) {
	    	Log.error("Exception loginAsMaxAdmin--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
}
