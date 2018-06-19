package config;

import static executionEngine.Base.OR;
import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;
import static executionEngine.Base.action;

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

public class CrossoverLocationsAsset1 extends TestAutomation {
	
	static String assetnum = null;
	static String locationnum = null;
	static String user = "maxadmin";
	
    static String testCase = "";
    static String testName = "CrossoverLocationsAsset1";

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
	void relateAssetToLocation() {
		testCase = "relateAssetToLocation";
//		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);

			try {
//				Create Location
				action.login(user);
				action.goToLocationPage();
				
				action.createLocation(testCase);
				locationnum = action.getAttributeValue("txtbx_Location");
				
//				Create Asset
				action.goToHomePage();
				action.goToAssetPage();

				action.createAsset(testCase, false);
				assetnum = action.getAttributeValue("txtbx_AssetNum");

				
				Log.info("Assign Location to Asset.........");
				action.click("lnk_MoveModifyAssets");

				action.input("txtbx_ToLocation", locationnum);
				action.clickOK();
				action.verifyAlert("Asset "+assetnum+" in site NETWORK was moved successfully.");
				action.clickOK();
				action.save();
				
				Log.info("Verify relationship in Location app");
				action.goToHomePage();
				action.goToLocationPage();

				action.quickSearch(locationnum);

				action.clickAssetsTab();
				action.rowsDisplayed1("Assets", "1");
				Log.info("Assetnm="+ assetnum);
				Log.info("Location="+ locationnum);
			}catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				Base.bResult = false;
	 			Assert.fail();
		    } catch (NoSuchElementException e) {
		    	Log.error("Element not found --- " + e.getMessage());
		    	extentTest.log(LogStatus.ERROR, e.getMessage());
		    	Base.bResult = false;
	 			Assert.fail();
		    } catch (Exception e) {
		    	Log.error("Exception --- " + e.getMessage());
		    	extentTest.log(LogStatus.ERROR, e.getMessage());
		    	Base.bResult = false;
	 			Assert.fail();
		    }	
	}
	
	@Test(dependsOnMethods={"relateAssetToLocation"})
	void verifyLocationInWO() {
		testCase = "verifyLocationInWO";
//		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
			action.login(user);
//			Create WO and populate asset(remove pre-populated assetnum)
			action.goToWOPage();
			
			action.createWO(testCase, "CM");
			action.clear("txtbx_AssetNum");
			action.input("txtbx_Location", locationnum);
			action.save();

//			Verify fields - assetnum should display the related asset
			action.assertValueOnLabel("Asset", assetnum);
			action.assertValueOnLabel("Location", locationnum);
			
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
}


}
