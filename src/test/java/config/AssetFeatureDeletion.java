package config;

import static executionEngine.Base.OR;
import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;
import static executionEngine.Base.action;

import org.openqa.selenium.By;
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

public class AssetFeatureDeletion extends TestAutomation {
    static String testCase = "";
    static String testName = "AssetFeatureDeletion";
    
	static String user = "maxadmin";

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
	void applyGapsAndOverlaps() {
		String feature = "SLEEPERS";
		testCase = "applyGapsAndOverlaps()";
//		extentTest.log(LogStatus.INFO, testCase);
		Log.startTestCase(testCase);
		try {
			action.login(user);
			action.goToFeaturePage();

			action.quickSearch(feature);
			action.waitElementExists("txtbx_Feature");

			boolean checked = driver.findElement(By.xpath(OR.getProperty("chkbx_ApplyGapsandOverlaps"))).getAttribute("aria-checked").equals("true");
			if (!checked) {
				action.click("chkbx_ApplyGapsandOverlaps");
			}
			
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- ");
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception --- ");
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
	
    @Test
	void deleteFeatureAsAdmin() {
		String asset = "1000001";
		testCase = "deleteFeatureAsAdmin()";
//		extentTest.log(LogStatus.INFO, testCase);
		Log.startTestCase(testCase);
		try {
			action.login(user);
			action.goToAssetPage();

			action.quickSearch(asset);
			action.click("tab_Features");
			
			Log.info("Delete non-gaps & overlaps enabled feature...");
			action.input("txtbx_Search_Code", "TSR");
			action.enter("txtbx_Search_Code");
			
			action.scrollDown("btn_DeleteDetails_Row1");
			action.click("btn_DeleteDetails_Row1");
			
			Log.info("Undelete...");
			action.click("btn_UndeleteDetails_Row1");
			
			Log.info("Delete button should dispLAy...");
			action.waitElementExists("btn_DeleteDetails_Row1");
			action.save();
			
			Log.info("Delete gaps & overlaps enabled feature...");
			action.input("txtbx_Search_Code", "SLEEPERS");
			action.enter("txtbx_Search_Code");
			Thread.sleep(10000); //TODO temp - wait for page to refresh 
			
			action.scrollDown("btn_DeleteDetails_Row1");
			action.click("btn_DeleteDetails_Row1");
			
			Log.info("Undelete...");
			action.click("btn_UndeleteDetails_Row1");

			Log.info("Delete button should dispLAY...");
			action.waitElementExists("btn_DeleteDetails_Row1");
			action.save();
		} catch (NoSuchElementException e) {
	    	Log.error("Element not found --- ");
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception --- ");
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
	
    @Test
	static void deleteFeatureAsNonAdmin() {
		String asset = "1000001";
		testCase = "deleteFeatureAsNonAdmin()";
//		extentTest.log(LogStatus.INFO, testCase);
		Log.startTestCase(testCase);
		try {
			action.login("mxfldeng");
			action.goToAssetPage();
			
			action.quickSearch(asset);
			action.click("tab_Features");
			action.waitElementExists("tbl_Features");
			
			Log.info("Delete non-gaps & overlaps enabled feature...");
			action.input("txtbx_Search_Code", "KMPOST");
			action.input("txtbx_Search_Label", "10");
			action.enter("txtbx_Search_Label");
			
			action.scrollDown("btn_DeleteDetails_Row1");
			action.click("btn_DeleteDetails_Row1");
			action.verifyAlert("The asset-feature is used as a reference to define ASSETFEATURE."); 
			action.click("btn_OK");
			action.save();

			Log.info("Delete gaps & overlaps enabled feature.................");
			action.input("txtbx_Search_Code", "SLEEPERS");
			action.enter("txtbx_Search_Code");
			
			action.scrollDown("btn_DeleteDetails_Row1");
			action.click("btn_DeleteDetails_Row1");
			action.waitElementExists("btn_DeleteDetails_Row1");
			Assert.assertFalse(action.existsElement("btn_DeleteDetails_Row1"));
			
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- ");
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception --- ");
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}

}
