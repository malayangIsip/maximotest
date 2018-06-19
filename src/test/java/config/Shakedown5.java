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
 * PR As MXFLDENG
 */
public class Shakedown5 extends TestAutomation {
    static String testCase = "";
    static String testName = "Shakedown5";
	
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
    void createPRasMXFLDENG() {
    	testCase = "createPRasMXFLDENG";
//    	extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
 		
 		try {
 			action.login("mxfldeng");
 			action.goToPRPage();
 			
 			action.clickNew();
 			action.input("txtbx_Description", "Shakedown 5");
 			action.click("btn_WO_chevron");
 			action.click("lnk_Related_WO");
 			action.click("btn_Advanced_Search");
 			action.input("txtbx_Worktype", "CM");
 			action.input("txtbx_Status", "APPR");
 			action.input("txtbx_Discipline", "TRACK");
 			action.input("txtbx_Area", "Dunedin");
 	 		action.clickFind();
 	 		action.clickFirstValueFromList();
 	 		action.clickReturnWithValue();
  	 		action.input("txtbx_Company", "DUMMY");
 			
  	 		action.click("tab_PRLines");
  	 		action.click("btn_NewRow");
  	 		action.click("btn_Item_chevron");
  	 		action.click("lnk_SelectValue");
  	 		action.clickFirstValueFromList();
  	 		action.input("txtbx_OrderUnit", "EA");
  	 		action.input("txtbx_UnitCost", "100");
  	 		action.input("txtbx_RequiredDate", action.getDate().plusWeeks(3).toString());
  	 		if (action.isReadOnly("txtbx_ConversionFactor") == false) {
  	 			action.input("txtbx_ConversionFactor", "1");
  	 		}
 			action.save();
 			
 			action.route();
 			
 			Assert.assertEquals(action.inWorkflow(), true);
 			action.clickOK();
 			action.assertValue("txtbx_Status", "WAPPR");
 			Assert.assertFalse(action.getWFApprover().isEmpty());
  			action.storeValue("txtbx_PRNUM", "PRNUM");
  			
  			action.approveWF();
  			action.assertValue("txtbx_Status", "CLOSE");

 		} catch(AssertionError ae){
			Log.error("Assertion failed createPRasMXFLDENG--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found createPRasMXFLDENG--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception createPRasMXFLDENG--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
    }
      
}
