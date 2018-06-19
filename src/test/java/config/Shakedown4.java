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

/*
 * WO As MXFLDENG Linear
 */
public class Shakedown4 extends TestAutomation {
    static String testCase = "";
    static String testName = "Shakedown4";
    
    static String WONUM = null;
    
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
    
    @Test(groups = {"smoke"})
    void createLinearWO() {
    	testCase = "createLinearWO";
//    	extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
 		
 		try {
			
 			action.login("mxfldeng");
 			action.goToWOPage();
 			
 			action.clickNew();
 			action.input("txtbx_Description", "Shakedown 5");
 			action.input("txtbx_AssetNum", "1000009");
 			action.input("txtbx_Worktype", "CM");
 			action.input("txtbx_Priority", "8");
 			action.getActivityType();
 			action.isNull("txtbx_ActivityType", false);
 			action.input("txtbx_ScheduledStart", action.getDate().plusWeeks(3).toString());
 			action.click("btn_Save");
 			
 			action.verifyAlert("This section of track does not physically exist");
 			action.clickOK();
 			
 			action.input("txtbx_StartRefPoint", "3");
 			action.input("txtbx_EndRefPoint", "3");
 			action.input("txtbx_StartRefPointOffset", "0");
 			action.input("txtbx_EndRefPointOffset", "0");
 			action.click("btn_Save");
 			
 			action.route();
 			action.verifyAlert("The Work Order has zero planned cost. Please review");
 			action.clickClose();
 			
 			action.click("tab_Plans");
 			action.click("tab_Materials");
 			action.click("btn_NewRow_Materials");
 			action.input("txtbx_Item", "1028752");
 			action.getStoreroom();
 			action.save();
 			
 			action.route();
 			action.clickOK();
 			Assert.assertEquals(action.inWorkflow(), true);
 			action.clickOK();
 			
 		} catch(AssertionError ae){
			Log.error("Assertion failed createLinearWO--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found createLinearWO--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception createLinearWO--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
    }	
	

}
