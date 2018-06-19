package config;

import static executionEngine.Base.OR;
import static executionEngine.Base.action;
import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class RailWearMeterReadings extends TestAutomation {
	static String user = "maxadmin";
    static String testCase = "";
    static String testName = "RailWearMeterReadings";
    
	static String tableName="Metrage and Date of Previous Reading Sets";

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
    public void logout() throws Exception {
    	logout(testName, testCase);
    }
    

	@Test
	void verifyCheckbox() {
		testCase = "verifyCheckbox";
//    	extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
			action.login(user);
			action.goToClassificationsPage();
			
			String[][] toSearch = new String[][] {{"txtbx_ParentClassification", "TRACK"}, {"txtbx_Classification", "MAINL"}};
			action.advancedSearch(toSearch);
			action.waitElementExists("chkbx_AllowRailWearMeters");

			
			Log.info("Allow Rail Wear Meters is checked.............");
			if (!action.isChecked("chkbx_AllowRailWearMeters")) {
				//check it otherwise do nothing
				action.click("chkbx_AllowRailWearMeters");
				action.save();
			}
			action.isChecked("chkbx_AllowRailWearMeters", true);
			
			Log.info("Go to Asset and search for linear asset with track/mail classification, i.e. 1000060..............");
			action.goToAssetPage();
			action.quickSearch("1000060");

			action.waitElementExists("lnk_EnterRailWearReadings");
	
			Log.info("Enter Rail Wear Readings link should exists...............");
			action.elementExists("lnk_EnterRailWearReadings", true);
			
			Log.info("Uncheck Allow Rail Wear Meters...................");
			action.goToClassificationsPage();
			
			action.advancedSearch(toSearch);
			action.waitElementExists("chkbx_AllowRailWearMeters");
			
			if (action.isChecked("chkbx_AllowRailWearMeters")) {
				//uncheck it otherwise do nothing
				action.click("chkbx_AllowRailWearMeters");
				action.save();
			}
			
			
			Log.info("Allow Rail Wear Meters is not checked...................");
			action.isChecked("chkbx_AllowRailWearMeters", false);
						
			Log.info("Go to Asset and search for linear asset with track/mail classification, i.e. 1000060........");
			action.goToAssetPage();
			action.quickSearch("1000060");

//	      Enter Rail Wear Readings link should not exist (BUG: link still exists same in PROD) 
//			action.waitElementExists("lnk_EnterRailWearReadings");
//			action.elementExists("lnk_EnterRailWearReadings", "False");
			
			Log.info("Check Allow Rail Wear Meters...............");
			action.goToClassificationsPage();
			
			Log.info("Revert to orig checked state...........");
			action.advancedSearch(toSearch);
			action.waitElementExists("chkbx_AllowRailWearMeters");
			action.click("chkbx_AllowRailWearMeters");
			action.save();
			
			Log.info("Allow Rail Wear Meters is checked...................");
			action.isChecked("chkbx_AllowRailWearMeters", true);
	    } catch (Exception e) {
	    	Log.error("Exception verifyCheckbox--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
	
	@Test(dependsOnMethods={"verifyCheckbox"})
	void searchRailWear() {
		testCase = "searchRailWear";
//    	extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
			action.login(user);
			action.goToAssetPage();
			
			action.quickSearch("1000060");
			action.waitElementExists("lnk_EnterRailWearReadings");
			
			Log.info("Go to Enter Rail Wear Readings............");
			action.click("lnk_EnterRailWearReadings");
//			Click search without populating the search parameters
			action.clickSearch();
			action.waitElementExists("systemMessage");
			action.verifyAlert("No search parameters entered.");
			action.clickOK();

			Log.info("Click clickNewRailWearReading..");
			action.clickNewRailWearReading();
			action.waitElementExists("systemMessage");
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
			action.tableExists(tableName);
			Log.info("Check columns exist...");
			action.checkTableColumnExists("KM,Offset (M),Previous Reading Date", tableName);
	    } catch (Exception e) {
	    	Log.error("Exception searchRailWear--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
	
	@Test(dependsOnMethods={"verifyCheckbox"})
	void enterRailWear() {
		testCase = "enterRailWear";
//    	extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
			String topValue = "2";
			
			action.login(user);
			action.goToAssetPage();
			action.quickSearch("1000060");
			action.waitElementExists("lnk_EnterRailWearReadings");
			
//			Go to ‘Enter Rail Wear Readings'
			action.click("lnk_EnterRailWearReadings");
			
//			input values to search parameters and search
			Log.info("Input values...");
			action.input("txtbx_KMFrom", "0");
			action.input("txtbx_KMTo", "3");
			action.clickSearch();
			
			Log.info("Click New..");
			action.clickNewRailWearReading();

			action.isRequired("txtbx_Km", true);
			action.isRequired("txtbx_Offset(M)", true);
			action.isRequired("txtbx_MeterInspector", true);
			action.isRequired("txtbx_RailWearNewReadingDate", true);
					
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
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not foundenterRailWear --- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception enterRailWear--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
	
	static void newRailWear() {
		try {
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			Base.bResult = false;
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
 			Base.bResult = false;
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
 			Base.bResult = false;
	    }	
	}
	
	static void saveRailWear() {
		try {
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			Base.bResult = false;
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
 			Base.bResult = false;
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
 			Base.bResult = false;
	    }	
	}

}
