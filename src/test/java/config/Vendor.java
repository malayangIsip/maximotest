package config;

import static executionEngine.Base.driver;
import static executionEngine.Base.extentTest;
import static executionEngine.Base.OR;
import static executionEngine.Base.action;
import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import executionEngine.DriverScript;
import utility.Log;

public class Vendor {
	static String user = "maxadmin";
	
	static boolean runStatus = true;
    static String methodName = "";
	
    static String WONUM = null;
    static String VENDOR = null;
    

    @BeforeClass(alwaysRun = true)
    public void init() throws Exception {
    	extentTest.log(LogStatus.INFO, "Start Vendor unit Tests");
    }

	@AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
		extentTest.log(LogStatus.INFO, "END Vendor unit Tests");
    }
    
        
    @BeforeMethod(alwaysRun = true)
    void setUp() {
    	action.openBrowser(null, "Chrome");
   }
    
    @AfterMethod(alwaysRun = true)
    void logout() {
    	if (runStatus == true) {
    		extentTest.log(LogStatus.PASS, methodName);
//    		action.logout(null, null);
    		driver.close();
    	} else {
    		extentTest.log(LogStatus.FAIL, methodName);
    		driver.close();
    	}
    }
    
	@DataProvider
 	public Object[][] data() {
 		return new Object[][] { { "mxfldeng", "CM"}, {"mxfldeng", "CAP"} };
 	}
	
	/*MAX-1002 - Validation of Vendor on WO workflow only*/
	@Test(dataProvider = "data")
    void noVendorValidationOnSave(String user, String worktype) {
		methodName = "noVendorValidationOnSave";
		
		try {
//			Should only validate Vendor status if WO is in unapproved status
			Log.info("Start noVendorValidationOnSave..................");
	    	
	    	action.login(null, user);
			action.goToWOPage();

			action.createWOwithMaterialPlans("Test validate Vendor", worktype);
			action.storeValue("txtbx_Vendor", "VENDOR");
			action.storeValue("txtbx_WONUM", "WONUM");
			WONUM = action.getStoredValue("WONUM");
			VENDOR = action.getStoredValue("VENDOR");
			driver.close();
			
//			Deactivate Vendor and try to save WO - should generate error
			action.deactivateVendor(VENDOR, true);

			action.openBrowser(null ,"Chrome");
			action.login(null, user);
			action.goToWOPage();
			action.quickSearch(WONUM);
			
//			Try to save edit the WO and save
			action.input("txtbx_Description", " Test validate Vendor - inactivate Vendor");
			action.save();

			Log.info("End noVendorValidationOnSave..................");
		} catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
	    	runStatus = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
	    	runStatus = false;
 			Assert.fail();
	    }	
	}
	
   
    @Test(dependsOnMethods={"noVendorValidationOnSave"})
	void validateVendorOnWF() {
		methodName = "validteVendorOnWF";
		String worktype = null;

		try {
			Log.info("Start validteVendorOnWF..................");
	    	
	    	action.login(null, user);
			action.goToWOPage();
			
			action.quickSearch(WONUM);
			action.waitForElementDisplayed("txtbx_WONUM");
			worktype = action.getAttributeValue("txtbx_Worktype");
			
//			change status to PLAN if Renewal WO
			if (worktype.equals("CAP") || worktype.equals("AMREN")) {
				driver.close();
				action.changeWOStatusToPlan(WONUM, worktype); 
				action.assertValue2("txtbx_Status", "PLAN");
			}
			
//			Try to route the WO - should generate error
			Log.info("Click btn_Route..................");
			action.route();
			action.verifyAlert("msg_Popup", "This Work Order cannot be work flowed because either one or more Materials or Services have an invalid vendor");
			driver.close();
		
//			Reactivate Vendor
			action.deactivateVendor(VENDOR, false);

			action.openBrowser(null ,"Chrome");
			action.login(null, user);
			action.goToWOPage();
			action.quickSearch(WONUM);
			action.waitForElementDisplayed("txtbx_WONUM");
			
//			Try to route the WO - should be success
			Log.info("Click btn_Route..................");
			action.route();
			action.clickOK();
			action.assertValue2("txtbx_Status", "PLAN");
			
			action.approveWF(WONUM);
			Log.info("END validteVendorOnWF..................");
		} catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
	    	runStatus = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
	    	runStatus = false;
 			Assert.fail();
	    }
	}
    
	@Test(dependsOnMethods={"validateVendorOnWF"})
	void noVendorValidationOnChangeStatus() {
		methodName = "noVendorValidationOnChangeStatus";

		try {
			Log.info("Start noVendorValidationOnChangeStatus..................");
	    	
	    	action.login(null, user);
			action.goToWOPage();
			
			action.quickSearch(WONUM);

			Log.info("Completing WO should not validate Vendor Status - Vendor still in inactive state");
			action.changeStatus("txtbx_Status", "completed");
			
			Log.info("Closing WO should not validate Vendor Status - Vendor still in inactive state");
			action.changeStatus("txtbx_Status", "closed");
			
//			Reactivate Vendor 
			action.deactivateVendor(VENDOR, false);
			Log.info("END noVendorValidationOnChangeStatus..................");
		} catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
	    	runStatus = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
	    	runStatus = false;
 			Assert.fail();
	    }	
	}
}

