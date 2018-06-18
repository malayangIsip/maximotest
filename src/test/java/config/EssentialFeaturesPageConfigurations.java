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

public class EssentialFeaturesPageConfigurations extends TestAutomation {
    static String testCase = "";	
    static String testName = "EssentialFeaturesPageConfigurations";
	
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
    public void testEssentialFeatures() {
    	testCase = "testEssentialFeatures";
    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("maxadmin");
			action.goEssentialFeaturesPage();
			
			action.clickNew();
			action.click("btn_Asset_chevronES");
			action.click("lnk_SelectValue");
			action.click("lnk_List_Code");
			action.click("btn_Location_chevron");
			action.click("lnk_SelectValue");
			
			action.input("txtbx_Search_Code", "ABBOTSFORD");
			action.click("btn_Filter1");
			action.click("lnk_List_Code");
			action.click("btn_Feature");
			action.click("lnk_List_CodeES");
			action.click("btn_Event");
			action.click("lnk_List_CodeES");
			action.click("btn_Gang_chevron");
			action.click("lnk_SelectValue");
			action.click("lnk_List_Code");
			action.save();
			
			action.click("lnk_Delete");
			action.click("btn_Yes");

	    } catch (Exception e) {
	    	Log.error("Exception testEssentialFeatures--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
}
