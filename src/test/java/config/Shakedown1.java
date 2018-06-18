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
 * SR As MX155 User
 */
public class Shakedown1 extends TestAutomation {
	static String testName = "Shakedown1";
    static String testCase = "";
    
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
    public void logout() {
    	logout(testName, testCase);

    }

    @Test(groups = {"smoke"})
    void createSRasMX155User() {
    	testCase = "createSRasMX155User";
//    	extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
 		
 		try {
 			action.login("mx155");
 			action.goToSRPage();
 			
 			action.clickNew();
 			
 			action.input("txtbx_ReporterType", "Emergency");
 			action.input("txtbx_Summary", testCase);
 			action.input("txtbx_AssetNum", "1000014");
 			action.input("txtbx_Priority", "2");
 			action.input("txtbx_ActivityType", "drainage");
 			action.save();
 			Log.info("SR ="+action.getAttributeValue("txtbx_SRNUM"));
 			
 			Log.info("Route SR...");
 			action.route();
		
 			action.assertValue("txtbx_Status", "INPROG");

 			action.click("tab_RelatedRecord");
 			action.isEmpty("tbl_Related_WO", false);
 			action.goToRelatedWO(1);
 			action.assertValue("txtbx_Status", "APPR");
 			action.assertValue("txtbx_Worktype", "EM");
 			
 			WONUM = action.getAttributeValue("txtbx_WONUM");
 			action.click("btn_Return");
 		} catch(AssertionError ae){
			Log.error("Assertion failed createSRasMX155User--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found createSRasMX155User--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception createSRasMX155User--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
    }
    
    @Test(groups = {"smoke"}, dependsOnMethods={"createSRasMX155User"})
    void verifySRasMxleadhandUser() {
    	testCase = "verifySRasMxleadhandUser";
//    	extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
 		
 		try {
 			action.login("mxleadhand");
 			action.goToWOPage();
 			
 			action.quickSearch(WONUM);
 			action.click("lnk_Complete");	
 			action.clickOK();
 			action.clickClose();
			
 			action.assertValue("txtbx_Status", "COMP");
 			
 			action.click("tab_RelatedRecord");
 			action.goToRelatedSR(0);
 			action.assertValue("txtbx_Status", "RESOLVED");
 			action.click("btn_Return");
    } catch(AssertionError ae){
		Log.error("Assertion failed verifySRasMxleadhandUser--- " + ae.getMessage());
		extentTest.log(LogStatus.ERROR, ae.getMessage());
    	Base.bResult = false;
		Assert.fail();
    } catch (NoSuchElementException e) {
    	Log.error("Element not found verifySRasMxleadhandUser--- " + e.getMessage());
    	extentTest.log(LogStatus.ERROR, e.getMessage());
    	Base.bResult = false;
		Assert.fail();
    } catch (Exception e) {
    	Log.error("Exception verifySRasMxleadhandUser--- " + e.getMessage());
    	extentTest.log(LogStatus.ERROR, e.getMessage());
    	Base.bResult = false;
		Assert.fail();
    }	
  }
}
