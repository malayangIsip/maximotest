package config;

import static executionEngine.DriverScript.OR;
import static executionEngine.DriverScript.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import executionEngine.DriverScript;
import utility.Log;

public class LocationCustomMetrages extends ActionKeywords {
	
	static WebDriverWait wait = new WebDriverWait(driver, 3);
	static String startMetrage = "137.380";
	static String endMetrage = "137.380";
	static String wonum = null;
	static String srnum = null;

	public static void locationCustomMetrages(String object, String data) {
		Log.info("Start Location Custom....");
		displayInNewLocation();
		displayInModifiedLocation();
		boundaryTesting();
		crossoverToWOFromAssetWithLocation();
		crossoverToWOFromLocation();
		crossoverToSRFromAssetWithLocation();
		crossoverToSRFromLocation();
		generateWOFromSR();
		crossoverToWOFromAssetWithLocationNonLinear();
		crossoverToWOFromLocationNonLinear();
		crossoverToSRFromAssetWithLocationNonLinear();
		crossoverToSRFromLocationNonLinear();
//		generateWOFromPM() TODO	
//		generateWOFromSR() TODO	
	}
	
	static void displayInNewLocation() {
		try {
			waitFor5();
			Log.info("Start displayInNewLocation....");
//			Create new location
			driver.findElement(By.xpath(OR.getProperty("btn_New"))).click();
			waitForElementDisplayed("txtbx_Type", "1");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Type"))).sendKeys("OPERATING");
			driver.findElement(By.xpath("//input[contains(@aria-label, 'Location description')]")).sendKeys("locationCustomMetrages testing 1");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Discipline"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Discipline"))).sendKeys("TRACK");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Region"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Region"))).sendKeys("Central");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Area"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Area"))).sendKeys("Wellington");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Line"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Line"))).sendKeys("JVILL");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).sendKeys("97.569");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).sendKeys("98.568");
			save("1", "1");
			assertValue1("Start Metrage", "97.569");
			assertValue1("End Metrage", "98.568");
			waitFor();
			Log.info("End displayInNewLocation....");
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
	
	static void displayInModifiedLocation() {
		try {
			waitFor5();
			Log.info("Start displayInModifiedLocation()....");
//			driver.findElement(By.xpath(OR.getProperty("tab_ListView"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys("9000000");
			enter("txtbx_QuickSearch","1");
//			waitForElementDisplayed("lnk_EnterRailWearReadings", "1");
			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))), "9000000"));
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).sendKeys("97.569");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).sendKeys("98.568");
			save("1", "1");
			assertValue1("Start Metrage", "97.569");
			assertValue1("End Metrage", "98.568");
//			revert old metrages
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).sendKeys("388.000");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).sendKeys("388.000");
			save("1", "1");
			assertValue1("Start Metrage", "388.000");
			assertValue1("End Metrage", "388.000");
			waitFor();
			Log.info("End displayInModifiedLocation()....");
		}catch (AssertionError ae) {
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
	
	static void boundaryTesting() {
		try {
			waitFor5();
			Log.info("Start boundaryTesting()....");
//			Create new location
			driver.findElement(By.xpath(OR.getProperty("btn_New"))).click();
			waitForElementDisplayed("txtbx_Type", "1");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Type"))).sendKeys("OPERATING");
			driver.findElement(By.xpath("//input[contains(@aria-label, 'Location description')]")).click();
			driver.findElement(By.xpath("//input[contains(@aria-label, 'Location description')]")).sendKeys("locationCustomMetrages testing 2");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Discipline"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Discipline"))).sendKeys("TRACK");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Region"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Region"))).sendKeys("Central");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Area"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Area"))).sendKeys("Wellington");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Line"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Line"))).sendKeys("JVILL");
//			< .999
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).sendKeys("97.569");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).sendKeys("98.568");
			save("1", "1");
			assertValue1("Start Metrage", "97.569");
			assertValue1("End Metrage", "98.568");
//			> .999
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).sendKeys("97.1098");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).sendKeys("98.1258");
			save("1", "1");
			assertValue1("Start Metrage", "97.110");
			assertValue1("End Metrage", "98.126");
//			no and only 1 decimal value
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).sendKeys("98");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).sendKeys("98.1");
			save("1", "1");
			assertValue1("Start Metrage", "98.000");
			assertValue1("End Metrage", "98.100");
//			negative values
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).sendKeys("-98.900");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).sendKeys("-90.100");
			save("1", "1");
			assertValue1("Start Metrage", "-98.900");
			assertValue1("End Metrage", "-90.100");
//			start is greater than end
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).sendKeys("98.900");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).sendKeys("90.100");
			save("1", "1");
			assertValue1("Start Metrage", "98.900");
			assertValue1("End Metrage", "90.100");
			waitFor();
			Log.info("End boundaryTesting()....");
		} catch(AssertionError ae){
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
	
	static void crossoverToWOFromAssetWithLocation() {
		try {
			waitFor5();
			Log.info("Start crossoverToWOFromAssetWithLocation()....");
//			driver.findElement(By.xpath(OR.getProperty("tab_ListView"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys("9000004");
			enter("txtbx_QuickSearch","1");
			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))), "9000004"));
			startMetrage = driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).getAttribute("value");
			endMetrage = driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).getAttribute("value");
//		    Create a WO
			hover("hvr_Create", "lnk_CreateWO");	
			waitFor();
			Log.info("Wait for txtbx_WONUM to display...");
			wonum = driver.switchTo().activeElement().getAttribute("value");
			Log.info("txtbx_WONUM = "+wonum);
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
//			Open the WO created
			Log.info("Open create WO..."+wonum);
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_WO");
			waitForElementDisplayed("txtbx_QuickSearch", "1");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(wonum);
			enter("txtbx_QuickSearch","1");
//			waitForElementDisplayed("txtbx_WONUM", "1");
			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))), wonum));
			assertValue1("Start Metrage", startMetrage);
			assertValue1("End Metrage", endMetrage);
//			Check that asset is not populated since Location 9000004 has more that one asset references. 
//			*if a location has only one asset attached to it then the asset field in WO should be auto-populated with that asset #.
			Log.info("Check assetnum is null...");
			isNull("txtbx_AssetNum", "True");
//			Populate asset with 1000249(or any asset attached to the Location).
			Log.info("Populate asset with 1000249 and save...");
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys("1000249");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Description"))).sendKeys("crossoverToWOFromAssetWithLocation");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Worktype"))).sendKeys("CM");
			driver.findElement(By.xpath(OR.getProperty("txtbx_ActivityType"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_ActivityType"))).sendKeys("Drainage");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Priority"))).sendKeys("4");
			driver.findElement(By.xpath(OR.getProperty("txtbx_FinancialYear"))).sendKeys("2017");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPoint"))).sendKeys("137");			
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPoint"))).sendKeys("137");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).sendKeys("100");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).sendKeys("400");
			save("1", "1");
//			metrages changed to new reference points since it's a linear asset else will get asset's metrages
			assertValue1("Start Metrage", "137.100");
			assertValue1("End Metrage", "137.400");
			waitFor();
			Log.info("End crossoverToWOFromAssetWithLocation()....");
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

	static void crossoverToWOFromLocation() {
		try {
			waitFor5();
			Log.info("Start crossoverToWOFromLocation()....");
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_WO");
//		    Create WO
			createWO("crossoverToWOFromLocation", "CM");
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))).sendKeys("9000004");
			save("1", "1");
			assertValue1("Start Metrage", startMetrage);
			assertValue1("End Metrage", endMetrage);
			waitFor();
			Log.info("End crossoverToWOFromLocation()....");
		}catch (AssertionError ae){
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
	
	static void crossoverToSRFromAssetWithLocation() {
		try {
			waitFor5();
			Log.info("Start crossoverToSRFromAssetWithLocation()....");
//          Go To Location
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_Asset", "1");
			hover("hvr_Asset","lnk_Location");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys("9000004");
			enter("txtbx_QuickSearch","1");
			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))), "9000004"));
			startMetrage = driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).getAttribute("value");
			endMetrage = driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).getAttribute("value");
//		    Create a SR
			hover("hvr_Create", "lnk_CreateSR");	
			waitForElementDisplayed("txtbx_SRNUM", "1");
			srnum = driver.switchTo().activeElement().getAttribute("value");
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
//			Open the SR created
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_SR");
			waitForElementDisplayed("txtbx_QuickSearch", "1");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(srnum);
			enter("txtbx_QuickSearch","1");
//			waitForElementDisplayed("txtbx_WONUM", "1");
			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))), srnum));
			assertValue1("Start Metrage", startMetrage);
			assertValue1("End Metrage", endMetrage);
//			Check that asset is not populated since Location 9000004 has more that one asset references. 
//			*if a location has only one asset attached to it then the asset field in WO should be auto-populated with that asset #.
			isNull("txtbx_AssetNum", "True");
			Log.info("Populate assetnum to 1000249...");
//			Populate asset with 1000249(or any asset attached to the Location).
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys("1000249");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPoint"))).sendKeys("137");			
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPoint"))).sendKeys("137");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).sendKeys("100");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).sendKeys("400");
			driver.findElement(By.xpath(OR.getProperty("txtbx_ReporterType"))).sendKeys("KRNETWORK");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Summary"))).sendKeys("crossoverToSRFromAssetWithLocation");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Priority"))).sendKeys("7");
			driver.findElement(By.xpath(OR.getProperty("txtbx_ActivityType"))).sendKeys("Drainage");
			save("1", "1");
//			metrages changed to reference points since it's a linear asset else will get asset's metrages
			assertValue1("Start Metrage", "137.100");
			assertValue1("End Metrage", "137.400");
			waitFor();
			Log.info("End crossoverToSRFromAssetWithLocation()....");
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

	
	static void crossoverToSRFromLocation() {
		try {
			waitFor5();
			Log.info("Start crossoverToSRFromLocation()....");
//			Go to SR
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_SR");
//		    Create SR
			createSR("crossoverToSRFromLocation", "KRNETWORK");
//			Change asset to linear as createSR uses nonlinear
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
//			Populate location
			driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))).sendKeys("9000004");
			save("1", "1");
			srnum = driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))).getAttribute("value");
			assertValue1("Start Metrage", startMetrage);
			assertValue1("End Metrage", endMetrage);
			waitFor();
			Log.info("End crossoverToSRFromLocation()....");
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
	
	static void generateWOFromSR() {
		try {
			waitFor5();
			Log.info("Start generateWOFromSR()....");
//          Go To Location
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_SR");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(srnum);
			enter("txtbx_QuickSearch","1");
			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))), srnum));
//		    Create a WO
			hover("hvr_Create", "lnk_CreateWO");	
			waitForElementDisplayed("titlebar_message", "1");
//			Go to related records - WO
			driver.findElement(By.xpath(OR.getProperty("tab_RelatedRecord"))).click();
			waitForElementDisplayed("tbl_Related_WO", "1");
			isEmpty("tbl_Related_WO", "False");
			wonum = driver.findElement(By.xpath(OR.getProperty("txtbx_Related_WO_row1"))).getAttribute("value");
			driver.findElement(By.xpath(OR.getProperty("btn_Related_Chevron_Row1"))).click();
			driver.findElement(By.xpath(OR.getProperty("lnk_Related_WO"))).click();
			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))), wonum));
			assertValue1("Start Metrage", startMetrage);
			assertValue1("End Metrage", endMetrage);
			driver.findElement(By.xpath(OR.getProperty("btn_Return"))).click();
			waitFor();
			Log.info("End generateWOFromSR()....");
		} catch(AssertionError ae){
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
	
	static void crossoverToWOFromAssetWithLocationNonLinear() {
		try {
			waitFor5();
			Log.info("Start crossoverToWOFromAssetWithLocationNonLinear()....");
//			Go To Location
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_Asset", "1");
			hover("hvr_Asset","lnk_Location");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys("9000004");
			enter("txtbx_QuickSearch","1");
			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))), "9000004"));
			startMetrage = driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).getAttribute("value");
			endMetrage = driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).getAttribute("value");
//		    Create a WO
			hover("hvr_Create", "lnk_CreateWO");	
			waitFor();
			wonum = driver.switchTo().activeElement().getAttribute("value");
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
//			Open the WO created
			Log.info("Open create WO..."+wonum);
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_WO");
			waitForElementDisplayed("txtbx_QuickSearch", "1");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(wonum);
			enter("txtbx_QuickSearch","1");
//			waitForElementDisplayed("txtbx_WONUM", "1");
			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))), wonum));
			assertValue1("Start Metrage", startMetrage);
			assertValue1("End Metrage", endMetrage);
//			Check that asset is not populated since Location 9000004 has more that one asset references. 
//			*if a location has only one asset attached to it then the asset field in WO should be auto-populated with that asset #.
			Log.info("Check assetnum is null...");
			isNull("txtbx_AssetNum", "True");
//			Populate asset with 1000249(or any asset attached to the Location).
			Log.info("Populate asset with 1000249 and save...");
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys("2049762");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Description"))).sendKeys("crossoverToWOFromAssetWithLocation");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Worktype"))).sendKeys("CM");
			driver.findElement(By.xpath(OR.getProperty("txtbx_ActivityType"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_ActivityType"))).sendKeys("Electrical code");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Priority"))).sendKeys("4");
			driver.findElement(By.xpath(OR.getProperty("txtbx_FinancialYear"))).sendKeys("2017");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPoint"))).sendKeys("137");			
//			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPoint"))).sendKeys("137");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).sendKeys("100");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).sendKeys("400");
			Log.info("Asset 1000249 saving...");
			save("1", "1");
//			metrages changed to new reference points since it's a nonlinear asset else will get asset's metrages
			assertValue1("Start Metrage", "137.590");
			assertValue1("End Metrage", "137.590");
			waitFor();
			Log.info("End crossoverToWOFromAssetWithLocationNonLinear()....");
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

	static void crossoverToWOFromLocationNonLinear() {
		try {
			waitFor5();
			Log.info("Start crossoverToWOFromLocationNonLinear()....");
//		    Create WO
			createWO("crossoverToWOFromLocationNonLinear", "CM");
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))).sendKeys("9000004");
			save("1", "1");
			assertValue1("Start Metrage", startMetrage);
			assertValue1("End Metrage", endMetrage);
			waitFor();
			Log.info("End crossoverToWOFromLocationNonLinear()....");
		}catch (AssertionError ae){
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
	
	static void crossoverToSRFromAssetWithLocationNonLinear() {
		try {
			waitFor5();
			Log.info("Start crossoverToSRFromAssetWithLocationNonLinear()....");
//          Go To Location
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_Asset", "1");
			hover("hvr_Asset","lnk_Location");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys("9000004");
			enter("txtbx_QuickSearch","1");
			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))), "9000004"));
//		    Create a SR
			hover("hvr_Create", "lnk_CreateSR");	
			waitFor();
			srnum = driver.switchTo().activeElement().getAttribute("value");
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
//			Open the SR created
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_SR");
			waitForElementDisplayed("txtbx_QuickSearch", "1");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(srnum);
			enter("txtbx_QuickSearch","1");
//			waitForElementDisplayed("txtbx_WONUM", "1");
			wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))), srnum));
			assertValue1("Start Metrage", startMetrage);
			assertValue1("End Metrage", endMetrage);
//			Check that asset is not populated since Location 9000004 has more that one asset references. 
//			*if a location has only one asset attached to it then the asset field in WO should be auto-populated with that asset #.
			isNull("txtbx_AssetNum", "True");
//			Populate asset with 1000249(or any asset attached to the Location).
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys("2049762");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPoint"))).sendKeys("137");			
//			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPoint"))).sendKeys("137");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).sendKeys("100");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).sendKeys("400");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_ReporterType"))).sendKeys("KRNETWORK");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Summary"))).sendKeys("crossoverToSRFromAssetWithLocation");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Priority"))).sendKeys("7");
			driver.findElement(By.xpath(OR.getProperty("txtbx_ActivityType"))).sendKeys("Electrical code");
			save("1", "1");
//			metrages changed to reference points since it's a linear asset else will get asset's metrages
			assertValue1("Start Metrage", "137.590");
			assertValue1("End Metrage", "137.590");
			waitFor();
			Log.info("End crossoverToSRFromAssetWithLocationNonLinear()....");
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

	
	static void crossoverToSRFromLocationNonLinear() {
		try {
			waitFor5();
			Log.info("Start crossoverToSRFromLocationNonLinear()....");
//			Go to SR
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_SR");
			waitForElementDisplayed("btn_New", "1");
//		    Create SR
			createSR("crossoverToSRFromLocationNonLinear", "KRNETWORK");
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))).sendKeys("9000004");
			save("1", "1");
			srnum = driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))).getAttribute("value");
			assertValue1("Start Metrage", startMetrage);
			assertValue1("End Metrage", endMetrage);
			waitFor();
			Log.info("End crossoverToSRFromLocationNonLinear()....");
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
	
//	TODO
	static void generateWOFromPM() {
		try {
		} catch(AssertionError ae){
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
