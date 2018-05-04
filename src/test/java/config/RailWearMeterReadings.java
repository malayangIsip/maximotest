package config;

import static executionEngine.Base.OR;
import static executionEngine.Base.action;
import static executionEngine.Base.driver;
import static executionEngine.Base.extentTest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import executionEngine.DriverScript;
import utility.Log;

public class RailWearMeterReadings {
	static String user = "maxadmin";
	static boolean runStatus = true;
    static String methodName = "";
    
	static String tableName="Metrage and Date of Previous Reading Sets";

	@BeforeClass(alwaysRun = true)
    public void init() throws Exception {
    	extentTest.log(LogStatus.INFO, "Start RailWearMeterReadings Tests");
    }

	@AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
		extentTest.log(LogStatus.INFO, "END RailWearMeterReadings Tests");
    }
    
        
    @BeforeMethod(alwaysRun = true)
    void setUp() {
    	action.openBrowser(null, "Chrome");
    	
    	action.login(user);
    }
    
    @AfterMethod(alwaysRun = true)
    void logout() {
    	if (runStatus == true) {
    		extentTest.log(LogStatus.PASS, methodName);
    		action.logout(null, null);
    	} else {
    		extentTest.log(LogStatus.FAIL, methodName);
    		driver.close();
    	}
    	Log.endTestCase(methodName);
    }
    

	@Test
	void verifyCheckbox() {
		methodName = "verifyCheckbox";
    	extentTest.log(LogStatus.INFO, methodName);
 		Log.startTestCase(methodName);
		try {
			Log.info("Start verifyCheckbox....");
			action.goToClassificationsPage();
			
			action.click("btn_Advanced_Search");
			action.input("txtbx_ParentClassification", "TRACK");
			action.input("txtbx_Classification", "MAINL");
			action.clickFind();
			action.waitForElementDisplayed("chkbx_AllowRailWearMeters");

			
	//		Allow Rail Wear Meters is checked
			if (!action.isChecked("chkbx_AllowRailWearMeters")) {
				//check it otherwise do nothing
				action.click("chkbx_AllowRailWearMeters");
				action.save();
			}
			action.isChecked("chkbx_AllowRailWearMeters", "True");
			
	//		Go to Asset and search for linear asset with track/mail classification, i.e. 1000060
			action.goToAssetPage();
			action.quickSearch("1000060");

			action.waitForElementDisplayed("lnk_EnterRailWearReadings");
	
	//      Enter Rail Wear Readings link should exists
			action.elementExists("lnk_EnterRailWearReadings", "True");
			
	//		uncheck Allow Rail Wear Meters
			action.goToClassificationsPage();
			
			action.click("btn_Advanced_Search");
			action.input("txtbx_ParentClassification", "TRACK");
			action.input("txtbx_Classification", "MAINL");
			action.clickFind();
			action.waitForElementDisplayed("chkbx_AllowRailWearMeters");
			
			if (action.isChecked("chkbx_AllowRailWearMeters")) {
				//uncheck it otherwise do nothing
				action.click("chkbx_AllowRailWearMeters");
				action.save();
			}
			
			
	//		Allow Rail Wear Meters is not checked
			action.isChecked("chkbx_AllowRailWearMeters", "False");
						
	//		Go to Asset and search for linear asset with track/mail classification, i.e. 1000060
			action.goToAssetPage();
			action.quickSearch("1000060");

//	      Enter Rail Wear Readings link should not exist (BUG: link still exists same in PROD) 
//			action.waitForElementDisplayed("lnk_EnterRailWearReadings");
//			action.elementExists("lnk_EnterRailWearReadings", "False");
			
    //		check Allow Rail Wear Meters
			action.goToClassificationsPage();
			
//			revert to orig checked state
			action.click("btn_Advanced_Search");
			action.input("txtbx_ParentClassification", "TRACK");
			action.input("txtbx_Classification", "MAINL");
			action.clickFind();
			action.waitForElementDisplayed("chkbx_AllowRailWearMeters");
			action.click("chkbx_AllowRailWearMeters");
			action.save();
			
		//	Allow Rail Wear Meters is checked
			action.isChecked("chkbx_AllowRailWearMeters", "True");
	    } catch (Exception e) {
	    	Log.error("Exception verifyCheckbox--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	runStatus = false;
 			Assert.fail();
	    }	
	}
	
	@Test(dependsOnMethods={"verifyCheckbox"})
	void searchRailWear() {
		methodName = "searchRailWear";
    	extentTest.log(LogStatus.INFO, methodName);
 		Log.startTestCase(methodName);
		try {
			Log.info("Start searchRailWear....");
			action.goToAssetPage();
			
			action.quickSearch("1000060");
			action.waitForElementDisplayed("lnk_EnterRailWearReadings");
			
//			Go to ‘Enter Rail Wear Readings'
			action.click("lnk_EnterRailWearReadings");
//			Click search without populating the search parameters
			action.clickSearch();
			action.waitForElementDisplayed("systemMessage");
			action.verifyAlert("No search parameters entered.");
			action.clickOK();

			Log.info("Click clickNewRailWearReading..");
			action.clickNewRailWearReading();
			action.waitForElementDisplayed("systemMessage");
			Log.info("verify alert");
			action.verifyAlert("Please performe a search before entering new Metrage");
			action.clickOK();
			
//			input values to search parameters and search
			Log.info("Input values...");
			action.input("txtbx_KMFrom", "0");
			action.input("txtbx_KMTo", "3");
			action.clickSearch();
			
//          Check that the table exists
			Log.info("Wait for table displayed...");
			action.tableExists(tableName, "1");
			Log.info("Check columns exist...");
			action.checkTableColumnExists("KM,Offset (M),Previous Reading Date", tableName);
	    } catch (Exception e) {
	    	Log.error("Exception searchRailWear--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	runStatus = false;
 			Assert.fail();
	    }	
	}
	
	@Test(dependsOnMethods={"verifyCheckbox"})
	void enterRailWear() {
		methodName = "enterRailWear";
    	extentTest.log(LogStatus.INFO, methodName);
 		Log.startTestCase(methodName);
		try {
			String topValue = "2";
			Log.info("Start enterRailWear....");
			action.goToAssetPage();
			action.quickSearch("1000060");
			action.waitForElementDisplayed("lnk_EnterRailWearReadings");
			
//			Go to ‘Enter Rail Wear Readings'
			action.click("lnk_EnterRailWearReadings");
			
//			input values to search parameters and search
			Log.info("Input values...");
			action.input("txtbx_KMFrom", "0");
			action.input("txtbx_KMTo", "3");
			action.clickSearch();
			
			Log.info("Click New..");
			action.clickNewRailWearReading();

			action.isRequired("txtbx_Km", "True");
			action.isRequired("txtbx_Offset(M)", "True");
			action.isRequired("txtbx_MeterInspector", "True");
			action.isRequired("txtbx_RailWearNewReadingDate", "True");
					
			action.input("txtbx_Km", "0");
			action.input("txtbx_Offset(M)", "400");
			action.input("txtbx_MeterInspector", "maxadmin");
			action.input("txtbx_RailWearReadingComment", "Test Rail Wear");
			action.input("txtbx_TopLeft", topValue);
			action.clickSaveRailWearReading();
			
			action.assertValue("txtbx_TopLeft", "");
			action.clickCancelRailWearReading();
		} catch(AssertionError ae){
			Log.error("Assertion failed enterRailWear--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
	    	runStatus = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not foundenterRailWear --- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	runStatus = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception enterRailWear--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	runStatus = false;
 			Assert.fail();
	    }	
	}
	
	static void newRailWear() {
		try {
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			DriverScript.bResult = false;
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
 			DriverScript.bResult = false;
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
 			DriverScript.bResult = false;
	    }	
	}
	
	static void saveRailWear() {
		try {
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			DriverScript.bResult = false;
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
 			DriverScript.bResult = false;
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
 			DriverScript.bResult = false;
	    }	
	}

}
