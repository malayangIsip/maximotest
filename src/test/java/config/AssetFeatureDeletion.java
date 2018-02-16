package config;

import static executionEngine.DriverScript.OR;
import static executionEngine.DriverScript.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import executionEngine.DriverScript;
import utility.Log;

public class AssetFeatureDeletion extends ActionKeywords {

	static WebDriverWait wait = new WebDriverWait(driver, 3);
	
	public static void assetFeatureDeletion(String object, String data) {
//		applyGapsAndOverlaps("SLEEPERS");
//		deleteFeatureAsAdmin("1000000");
		deleteFeatureAsNonAdmin("1000000");
	}
	
	static void applyGapsAndOverlaps(String feature) {
		try {
			Log.info("Start applyGapsAndOverlaps..................");
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_Asset", "1");
			hover("hvr_Asset","lnk_Feature");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(feature);
			enter("txtbx_QuickSearch","1");
			waitForElementDisplayed("txtbx_Feature", "1");

			boolean checked = driver.findElement(By.xpath(OR.getProperty("chkbx_ApplyGapsandOverlaps"))).getAttribute("aria-checked").equals("true");
			if (!checked) {
				driver.findElement(By.xpath(OR.getProperty("chkbx_ApplyGapsandOverlaps"))).click();
			}
			
			Log.info("END applyGapsAndOverlaps..................");
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
 			DriverScript.bResult = false;
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
 			DriverScript.bResult = false;
	    }	
	}
	
	static void deleteFeatureAsAdmin(String asset) {
		try {
			Log.info("Start deleteFeature admin....................");
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_Asset", "1");
			hover("hvr_Asset","lnk_Asset");
			waitForElementDisplayed("txtbx_QuickSearch", "1");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(asset);
			enter("txtbx_QuickSearch","1");
			waitForElementDisplayed("tab_Features", "1");
			driver.findElement(By.xpath(OR.getProperty("tab_Features"))).click();
			waitForElementDisplayed("tbl_Features", "1");
//			delete non-gaps & overlaps enabled feature
			driver.findElement(By.xpath(OR.getProperty("txtbx_Search_Code"))).sendKeys("TSR");
			enter("txtbx_Search_Code","1");
			driver.findElement(By.xpath(OR.getProperty("btn_DeleteDetails_Row1"))).click();
			elementDisplayed(By.xpath("//a[contains(id,'_tdrow_[C:9]_toggleimage-ti[R:0]') and @title='Undo Delete']"));
//		undelete
			driver.findElement(By.xpath(OR.getProperty("btn_DeleteDetails_Row1"))).click();
			elementDisplayed(By.xpath("//a[contains(id,'_tdrow_[C:9]_toggleimage-ti[R:0]') and @title='Mark Row for Delete']"));
			save("", "");
			
//			delete gaps & overlaps enabled feature
			driver.findElement(By.xpath(OR.getProperty("txtbx_Search_Code"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Search_Code"))).sendKeys("SLEEPERS");
			enter("txtbx_Search_Code","1");
			driver.findElement(By.xpath(OR.getProperty("btn_DeleteDetails_Row1"))).click();
			elementDisplayed(By.xpath("//a[contains(id,'_tdrow_[C:9]_toggleimage-ti[R:0]') and @title='Undo Delete']"));
//		undelete
			driver.findElement(By.xpath(OR.getProperty("btn_DeleteDetails_Row1"))).click();
			elementDisplayed(By.xpath("//a[contains(id,'_tdrow_[C:9]_toggleimage-ti[R:0]') and @title='Mark Row for Delete']"));
			save("", "");
//			logout("","");
			
			Log.info("END deleteFeature admin....................");
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
	
	static void deleteFeatureAsNonAdmin(String asset) {
		try {
			Log.info("Start deleteFeature non-admin..................");
//			login as non-admin
			logout("1", "1");
			openBrowser("", "Mozilla");
			driver.findElement(By.xpath(".//*[@id='username']")).sendKeys("mxfldeng");
			driver.findElement(By.xpath(".//*[@id='password']")).sendKeys("Kiwirail123");
			driver.findElement(By.xpath(".//*[@id='loginbutton']")).click();
			waitForElementDisplayed("lnk_Home", "1");
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_Asset", "1");
			hover("hvr_Asset","lnk_Asset");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(asset);
			enter("txtbx_QuickSearch","1");
			waitForElementDisplayed("tab_Features", "1");
			driver.findElement(By.xpath(OR.getProperty("tab_Features"))).click();
			waitForElementDisplayed("tbl_Features", "1");
//			delete non-gaps & overlaps enabled feature
			driver.findElement(By.xpath(OR.getProperty("txtbx_Search_Code"))).sendKeys("KMPOST");
			enter("txtbx_Search_Code","1");
			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty("btn_DeleteDetails_Row1"))).click();
			verifyAlert("msg_Popup", "The asset-feature is used as a reference to define ASSETFEATURE."); 
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			save("", "");

			Log.info("Delete gaps & overlaps enabled feature.................");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Search_Code"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Search_Code"))).sendKeys("SLEEPERS");
			enter("txtbx_Search_Code","1");
//			driver.findElement(By.xpath(OR.getProperty("btn_DeleteDetails_Row1"))).click();
			Assert.assertFalse(existsElement(By.xpath("//a[contains(id,'_tdrow_[C:9]_toggleimage-ti[R:0]')]")));
			
			Log.info("END deleteFeature non-admin..................");
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
