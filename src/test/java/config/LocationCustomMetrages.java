package config;

import static executionEngine.Base.OR;
import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;
import static executionEngine.Base.action;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
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

public class LocationCustomMetrages extends TestAutomation {
	static String startMetrage = "137.380";
	static String endMetrage = "137.380";
	static String wonum = null;
	static String srnum = null;
	
	static String user = "maxadmin";
    static String testCase = "";
    static String testName = "LocationCustomMetrages";
    
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
    public void setUp() throws Exception {
    	action.openBrowser("Chrome");
    	action.login(user);
    	
    	Base.bResult = true;
    }
    
    @Override  
    @AfterMethod
    public void logout() {
    	logout(testName, testCase);

    }
    
    @Test(priority=1)
	void displayInNewLocation() {
		testCase = "displayInNewLocation";
//		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
//			Create new location
			action.goToLocationPage();
			
			action.clickNew();

			action.input("txtbx_Type", "OPERATING");
			driver.findElement(By.xpath("//input[contains(@aria-label, 'Location description')]")).sendKeys("locationCustomMetrages testing 1");
			action.input("txtbx_Discipline", "TRACK");
			action.input("txtbx_Region", "Central");
			action.input("txtbx_Area", "Wellington");
			action.input("txtbx_Line", "JVILL");
			action.input("txtbx_StartMetrage", "97.569");
			action.input("txtbx_EndMetrage", "98.568");
			action.save();
			action.assertValueOnLabel("Start Metrage", "97.569");
			action.assertValueOnLabel("End Metrage", "98.568");
		}catch(AssertionError ae){
			Log.error("AssertionError --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("NoSuchElementException --- " + e.getMessage());
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
		
    @Test(priority=2)
	void displayInModifiedLocation() {
		testCase = "displayInModifiedLocation";
//		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
			action.goToLocationPage();
			
			action.quickSearch("9000000");

			action.waitElementExists("txtbx_Location");
//			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))), "9000000"));
			action.input("txtbx_StartMetrage", "97.569");
			action.input("txtbx_EndMetrage", "98.568");
			action.save();
			action.assertValueOnLabel("Start Metrage", "97.569");
			action.assertValueOnLabel("End Metrage", "98.568");
//			revert old metrages
			action.input("txtbx_StartMetrage", "388.000");
			action.input("txtbx_EndMetrage", "388.000");
			action.save();
			action.assertValueOnLabel("Start Metrage", "388.000");
			action.assertValueOnLabel("End Metrage", "388.000");
		}catch (AssertionError ae) {
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
	
    @Test(priority=3)
	void boundaryTesting() {
		testCase = "boundaryTesting";
//		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
			action.goToLocationPage();
			
//			Create new location
			action.clickNew();
			action.input("txtbx_Type", "OPERATING");
			driver.findElement(By.xpath("//input[contains(@aria-label, 'Location description')]")).click();
			driver.findElement(By.xpath("//input[contains(@aria-label, 'Location description')]")).sendKeys("locationCustomMetrages testing 2");
			action.input("txtbx_Discipline", "TRACK");
			action.input("txtbx_Region", "Central");
			action.input("txtbx_Area", "Wellington");
			action.input("txtbx_Line", "JVILL");
//			< .999
			action.input("txtbx_StartMetrage", "97.569");
			action.input("txtbx_EndMetrage", "98.568");
			action.save();
			action.assertValueOnLabel("Start Metrage", "97.569");
			action.assertValueOnLabel("End Metrage", "98.568");
//			> .999
			action.input("txtbx_StartMetrage", "97.1098");
			action.input("txtbx_EndMetrage", "98.1258");
			action.save();
			action.assertValueOnLabel("Start Metrage", "97.110");
			action.assertValueOnLabel("End Metrage", "98.126");
//			no and only 1 decimal value
			action.input("txtbx_StartMetrage", "98");
			action.input("txtbx_EndMetrage", "98.1");
			action.save();
			action.assertValueOnLabel("Start Metrage", "98.000");
			action.assertValueOnLabel("End Metrage", "98.100");
//			negative values
			action.input("txtbx_StartMetrage", "-98.900");
			action.input("txtbx_EndMetrage", "-90.100");
			action.save();
			action.assertValueOnLabel("Start Metrage", "-98.900");
			action.assertValueOnLabel("End Metrage", "-90.100");
//			start is greater than end
			action.input("txtbx_StartMetrage", "98.900");
			action.input("txtbx_EndMetrage", "90.100");
			action.save();
			action.assertValueOnLabel("Start Metrage", "98.900");
			action.assertValueOnLabel("End Metrage", "90.100");
		} catch(AssertionError ae){
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
    
	@Test(priority=4)
	void crossoverToWOFromAssetWithLocation() {
		testCase = "crossoverToWOFromAssetWithLocation";
//		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
			action.goToLocationPage();
			
			action.quickSearch("9000004");
			action.waitElementExists("txtbx_Location");
			startMetrage = action.getAttributeValue("txtbx_StartMetrage");
			endMetrage = action.getAttributeValue("txtbx_EndMetrage");
			
//		    Create a WO
			action.hover("hvr_Create", "lnk_CreateWO");	
			
			Log.info("Wait for txtbx_WONUM to display...");
			action.waitElementExists("btn_OK");
			wonum = driver.switchTo().activeElement().getAttribute("value");
			Log.info("txtbx_WONUM = "+wonum);
			action.clickOK();
			
//			Open the WO created
			Log.info("Open create WO..."+wonum);
			action.goToWOPage();
			
			action.quickSearch(wonum);

			action.waitElementExists("txtbx_WONUM");
//			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))), wonum));
			action.assertValueOnLabel("Start Metrage", startMetrage);
			action.assertValueOnLabel("End Metrage", endMetrage);
			
//			Check that asset is not populated since Location 9000004 has more that one asset references. 
//			*if a location has only one asset attached to it then the asset field in WO should be auto-populated with that asset #.
			Log.info("Check assetnum is null...");
			action.isNull("txtbx_AssetNum", true);
			
//			Populate asset with 1000249(or any asset attached to the Location).
			Log.info("Populate asset with 1000249 and save...");
			action.input("txtbx_AssetNum", "1000249");
			action.input("txtbx_Description", "crossoverToWOFromAssetWithLocation");
			action.input("txtbx_Worktype", "CM");
			action.input("txtbx_ActivityType", "Drainage");
			action.input("txtbx_Priority", "4");
			action.input("txtbx_FinancialYear", String.valueOf(action.getDate().getYear()));
			action.input("txtbx_StartRefPoint", "137");			
			action.input("txtbx_EndRefPoint", "137");
			action.input("txtbx_StartRefPointOffset", "100");
			action.input("txtbx_EndRefPointOffset", "400");
			action.save();
//			metrages changed to new reference points since it's a linear asset else will get asset's metrages
			action.assertValueOnLabel("Start Metrage", "137.100");
			action.assertValueOnLabel("End Metrage", "137.400");
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

	@Test(priority=5)
	void crossoverToWOFromLocation() {
		testCase = "crossoverToWOFromLocation";
//		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
			action.goToWOPage();

//		    Create WO
			action.createWO("crossoverToWOFromLocation", "CM");
			action.clear("txtbx_AssetNum");
			action.input("txtbx_Location", "9000004");
			action.save();
			action.assertValueOnLabel("Start Metrage", "137.380");
			action.assertValueOnLabel("End Metrage", "137.380");
		}catch (AssertionError ae){
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
	
	@Test(priority=6)
	void crossoverToSRFromAssetWithLocation() {
		testCase = "crossoverToSRFromAssetWithLocation";
//		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
			action.goToLocationPage();
			
			action.quickSearch("9000004");
			action.waitElementExists("txtbx_Location");
//			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))), "9000004"));
			startMetrage = action.getAttributeValue("txtbx_StartMetrage");
			endMetrage = action.getAttributeValue("txtbx_EndMetrage");
			
//		    Create a SR
			action.hover("hvr_Create", "lnk_CreateSR");	
			action.waitElementExists("txtbx_SRNUM");
			srnum = driver.switchTo().activeElement().getAttribute("value");
			action.clickOK();
			
//			Open the SR created
			action.goToSRPage();
			action.quickSearch(srnum);

			action.waitElementExists("txtbx_SRNUM");
//			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))), srnum));
			action.assertValueOnLabel("Start Metrage", startMetrage);
			action.assertValueOnLabel("End Metrage", endMetrage);
//			Check that asset is not populated since Location 9000004 has more that one asset references. 
//			*if a location has only one asset attached to it then the asset field in WO should be auto-populated with that asset #.
			action.isNull("txtbx_AssetNum", true);
			
			Log.info("Populate assetnum to 1000249...");
//			Populate asset with 1000249(or any asset attached to the Location).
			action.input("txtbx_AssetNum", "1000249");
			action.click("txtbx_Location"); //to display metrages
			action.input("txtbx_StartRefPoint", "137");			
			action.input("txtbx_EndRefPoint", "137");
			action.input("txtbx_StartRefPointOffset", "100");
			action.input("txtbx_EndRefPointOffset", "400");
			action.input("txtbx_ReporterType", "KRNETWORK");
			action.input("txtbx_Summary", "crossoverToSRFromAssetWithLocation");
			action.input("txtbx_Priority", "7");
			action.input("txtbx_ActivityType", "Drainage");
			action.save();
			
//			metrages changed to reference points since it's a linear asset else will get asset's metrages
			action.assertValueOnLabel("Start Metrage", "137.100");
			action.assertValueOnLabel("End Metrage", "137.400");
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

	@Test(priority=7)
	void crossoverToSRFromLocation() {
		testCase = "crossoverToSRFromLocation";
//		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
//			Go to SR
			action.goToSRPage();
			
//		    Create SR
			action.createSR("crossoverToSRFromLocation", "KRNETWORK");
			
//			Change asset to linear as createSR uses nonlinear
			action.clear("txtbx_AssetNum");
			
//			Populate location
			action.input("txtbx_Location", "9000004");
			action.save();
			srnum = action.getAttributeValue("txtbx_SRNUM");
			
			action.assertValueOnLabel("Start Metrage", startMetrage);
			action.assertValueOnLabel("End Metrage", endMetrage);

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
	
	@Test(priority=8, dependsOnMethods={"crossoverToSRFromLocation"})
	void generateWOFromSR() {
		testCase = "generateWOFromSR";
//		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
			action.goToSRPage();

			action.quickSearch(srnum);

			action.waitElementExists("txtbx_SRNUM");
//			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))), srnum));

//		    Create a WO
			action.hover("hvr_Create", "lnk_CreateWO");	
//			action.waitElementExists("titlebar_message");
			
//			Go to related records - WO
			action.click("tab_RelatedRecord");
			action.waitElementExists("tbl_Related_WO");
			action.isEmpty("tbl_Related_WO", false);
			wonum = action.getAttributeValue("txtbx_Related_WO_row1");
			action.click("btn_Related_Chevron_Row1");
			action.click("lnk_Related_WO");
			
			action.waitElementExists("txtbx_WONUM");
//			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))), wonum));
			action.assertValueOnLabel("Start Metrage", startMetrage);
			action.assertValueOnLabel("End Metrage", endMetrage);
			action.click("btn_Return");
		} catch(AssertionError ae){
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
	
	@Test(priority=9)
	void crossoverToWOFromAssetWithLocationNonLinear() {
		testCase = "crossoverToWOFromAssetWithLocationNonLinear";
//		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
//			Go To Location
			action.goToLocationPage();
			action.quickSearch("9000004");

//			action.waitElementExists("txtbx_Location");
//			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))), "9000004"));
			startMetrage = action.getAttributeValue("txtbx_StartMetrage");
			endMetrage = action.getAttributeValue("txtbx_EndMetrage");
			
//		    Create a WO
			action.hover("hvr_Create", "lnk_CreateWO");	
			action.waitElementExists("btn_OK");
			wonum = driver.switchTo().activeElement().getAttribute("value");
			action.clickOK();
			
//			Open the WO created
			Log.info("Open create WO..."+wonum);
			action.goToWOPage();
			
			action.quickSearch(wonum);
			action.waitElementExists("txtbx_WONUM");
//			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))), wonum));
			action.assertValueOnLabel("Start Metrage", startMetrage);
			action.assertValueOnLabel("End Metrage", endMetrage);
			
//			Check that asset is not populated since Location 9000004 has more that one asset references. 
//			*if a location has only one asset attached to it then the asset field in WO should be auto-populated with that asset #.
			Log.info("Check assetnum is null...");
			action.isNull("txtbx_AssetNum", true);
			
//			Populate asset with 2049762(or any asset attached to the Location).
			Log.info("Populate asset with 2049762 and save...");
			action.input("txtbx_AssetNum", "2049762");
			action.input("txtbx_Description", "crossoverToWOFromAssetWithLocation");
			action.input("txtbx_Worktype", "CM");
			action.input("txtbx_ActivityType", "Electrical code");
			action.input("txtbx_Priority", "4");
			action.input("txtbx_FinancialYear", String.valueOf(action.getDate().getYear()));
			Log.info("Asset 2049762 saving...");
			action.save();
			
//			metrages changed to new reference points since it's a nonlinear asset else will get asset's metrages
			action.assertValueOnLabel("Start Metrage", "137.590");
			action.assertValueOnLabel("End Metrage", "137.590");
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

	@Test(priority=10)
	void crossoverToWOFromLocationNonLinear() {
		testCase = "crossoverToWOFromLocationNonLinear";
//		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
			action.goToWOPage();
			
//		    Create WO
			action.createWO("crossoverToWOFromLocationNonLinear", "CM");
			action.clear("txtbx_AssetNum");
			action.input("txtbx_Location", "9000004");
			action.save();
			action.assertValueOnLabel("Start Metrage", startMetrage);
			action.assertValueOnLabel("End Metrage", endMetrage);
		}catch (AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
	
	@Test(priority=11)
	void crossoverToSRFromAssetWithLocationNonLinear() {
		testCase = "crossoverToSRFromAssetWithLocationNonLinear";
//		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
			action.goToLocationPage();
			
			action.quickSearch("9000004");
			
			action.waitElementExists("txtbx_Location");
//			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))), "9000004"));

//		    Create a SR
			action.hover("hvr_Create", "lnk_CreateSR");	
			action.waitFor();
			srnum = driver.switchTo().activeElement().getAttribute("value");
			action.clickOK();
			
//			Open the SR created
			action.goToSRPage();
			action.quickSearch(srnum);
			
			action.waitElementExists("txtbx_SRNUM");
//			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))), srnum));
			action.assertValueOnLabel("Start Metrage", startMetrage);
			action.assertValueOnLabel("End Metrage", endMetrage);
			
//			Check that asset is not populated since Location 9000004 has more that one asset references. 
//			*if a location has only one asset attached to it then the asset field in WO should be auto-populated with that asset #.
			action.isNull("txtbx_AssetNum", true);
			
//			Populate asset with 1000249(or any asset attached to the Location).
			action.input("txtbx_AssetNum", "2049762");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPoint"))).sendKeys("137");			
//			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPoint"))).sendKeys("137");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).sendKeys("100");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).sendKeys("400");
			action.click("txtbx_Location");
			action.input("txtbx_ReporterType", "KRNETWORK");
			action.input("txtbx_Summary", "crossoverToSRFromAssetWithLocation");
			action.input("txtbx_Priority", "7");
			action.input("txtbx_ActivityType", "Electrical code");
			action.save();
			
//			metrages changed to reference points since it's a linear asset else will get asset's metrages
			action.assertValueOnLabel("Start Metrage", "137.590");
			action.assertValueOnLabel("End Metrage", "137.590");

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

	
	@Test(priority=12)
	void crossoverToSRFromLocationNonLinear() {
		testCase = "crossoverToSRFromLocationNonLinear";
//		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
//			Go to SR
			action.goToSRPage();
			
//		    Create SR
			action.createSR("crossoverToSRFromLocationNonLinear", "KRNETWORK");
			action.clear("txtbx_AssetNum");
			action.input("txtbx_Location", "9000004");
			action.save();
			
			srnum = action.getAttributeValue("txtbx_SRNUM");
			action.assertValueOnLabel("Start Metrage", startMetrage);
			action.assertValueOnLabel("End Metrage", endMetrage);

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
	
//	TODO
	static void generateWOFromPM() {
		testCase = "generateWOFromPM";
//		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
		} catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
}
