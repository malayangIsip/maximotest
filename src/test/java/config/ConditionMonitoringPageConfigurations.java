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

public class ConditionMonitoringPageConfigurations extends TestAutomation {
	String testName = "ConditionMonitoringPageConfigurations Tests";
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
			action.goConditionMonitoringPage();
			
			String[][] toSearch = new String[][] {{"txtbx_Point", "2001"}};
			action.advancedSearch(toSearch);
		
			action.clickNew();
			action.input("txtbx_Description", testCase);
			
			action.click("btn_Location_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_Search_Code", "ABBOTSFORD");
			action.click("btn_Filter");
			action.clickFirstValueFromList();
			
			action.click("btn_Meter_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_Search_Code", "Apron");
			action.click("btn_Filter");
			action.clickFirstValueFromList();
			
			action.save();
			action.storeValue("txtbx_Point", "POINT");
				
			action.clickNew();
			action.input("txtbx_Description", testCase);
			
			action.click("btn_Location_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_Search_Code", "ABBOTSFORD");
			action.click("btn_Filter");
			action.clickFirstValueFromList();

			action.click("btn_Meter_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_Search_Code", "Apron");
			action.click("btn_Filter");
			action.clickFirstValueFromList();
			action.verifyAlert("BMXAA2837E - A record with location 9000000 and meter name APRON already exists");
			action.clickOK();
			action.clickCancel();
			
			action.clickListViewTab();
			action.clickNo();
				
			toSearch = new String[][] {{"txtbx_Point", action.getStoredValue("POINT")}};
			action.advancedSearch(toSearch);
			
			action.click("lnk_Delete");
			action.clickYes();
			
			action.advancedSearch(toSearch);
			action.verifyAlert("BMXAA4186E");
			action.clickOK();
			
			action.clickNew();
			action.input("txtbx_Description", testCase);
			
			action.click("btn_Asset_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_Search_Code", "NIMT MainL, Wellington - Auckland");
			action.click("btn_Filter");
			action.clickFirstValueFromList();
			
			action.click("btn_Meter_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_Search_Code", "Apron");
			action.click("btn_Filter");
			action.clickFirstValueFromList();
			
			action.save();
			action.storeValue("txtbx_Point", "POINT");

			action.clickNew();
			action.input("txtbx_Description", testCase);
			
			action.click("btn_Asset_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_Search_Code", "NIMT MainL, Wellington - Auckland");
			action.click("btn_Filter");
			action.clickFirstValueFromList();
			
			action.click("btn_Meter_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_Search_Code", "Apron");
			action.click("btn_Filter");
			action.clickFirstValueFromList();
			action.verifyAlert("BMXAA2828E");
			action.clickOK();
			action.clickCancel();
			
			action.clickListViewTab();
			action.clickNo();

			toSearch = new String[][] {{"txtbx_Point", action.getStoredValue("POINT")}};
			action.advancedSearch(toSearch);
			
			action.click("lnk_Delete");
			action.clickYes();

			action.advancedSearch(toSearch);
			action.verifyAlert("BMXAA4186E");
			action.clickOK();

	    } catch (Exception e) {
	    	Log.error("Exception loginAsMaxAdmin--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
}
