package config;

import static executionEngine.DriverScript.OR;
import static executionEngine.DriverScript.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import executionEngine.DriverScript;
import utility.Log;

public class CrossoverLocationsAsset1 extends ActionKeywords {

	static WebDriverWait wait = new WebDriverWait(driver, 3);
	static String assetnum = null;
	static String locationnum = null;

	public static void crossoverLocationsAsset1(String object, String data) {
		relateAssetToLocation();
		verifyLocationInWO();
    }
	
	static void relateAssetToLocation() {
			try {
//				Create Location
				driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
				waitForElementDisplayed("hvr_Asset", "1");
				hover("hvr_Asset","lnk_Location");
				waitForElementDisplayed("btn_New", "1");
				createLocation("", "crossoverLocationsAsset");
				locationnum = driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))).getAttribute("value");
//				Create Asset
				driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
				waitForElementDisplayed("hvr_Asset", "1");
				hover("hvr_Asset","lnk_Asset");
				waitForElementDisplayed("btn_New", "1");
				createAsset("", "crossoverLocationsAsset");
				assetnum = driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).getAttribute("value");
				Log.info("Assign Location to Asset");
//				Assign Location to Asset
				driver.findElement(By.xpath(OR.getProperty("lnk_MoveModifyAssets"))).click();
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[contains(@id,'_tdrow_[C:7]_txt-tb[R:0]')]"))));
				driver.findElement(By.xpath("//input[contains(@id,'_tdrow_[C:7]_txt-tb[R:0]')]")).sendKeys(locationnum);
				driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
				verifyAlert("msg_Popup", "Asset "+assetnum+" in site NETWORK was moved successfully.");
				driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
				waitFor();
				save("1", "1");
				Log.info("Verify relationship in Location app");
//				Verify relationship in Location app
				driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
				waitForElementDisplayed("hvr_Asset", "1");
				hover("hvr_Asset","lnk_Location");
				driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(locationnum);
				enter("txtbx_QuickSearch","1");
				wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))), locationnum));
				driver.findElement(By.xpath(OR.getProperty("tab_Assets"))).click();
				rowsDisplayed1("Assets", "1");
				Log.info("Assetnm="+ assetnum);
				Log.info("Location="+ locationnum);
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
	
	static void verifyLocationInWO() {
		try {
//			Create WO and populate asset(remove pre-populated assetnum)
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_WO");
			waitForElementDisplayed("btn_New", "1");
			createWO("crossoverLocationsAsset", "CM");
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))).sendKeys(locationnum);
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).click();
			waitFor();
			save("1", "1");
//			Verify fields - assetnum should display the related asset
			assertValue1("Asset", assetnum);
			assertValue1("Location", locationnum);			
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
