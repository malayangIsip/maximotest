package config;

import static executionEngine.DriverScript.OR;
import static executionEngine.DriverScript.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import executionEngine.DriverScript;
import utility.Log;

public class Vendor extends ActionKeywords  {

    static WebDriverWait wait = new WebDriverWait(driver, 3);
	
	public static void vendor() {
		validateVendor();
	}
	
	/*MAX-1002 - Validation of Vendor on WO save*/
	static void validateVendor() {
		try {
//			Should only validate Vendor status if WO is in unapproved status
			Log.info("Start validateVendor..................");
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_WO");
			waitFor();
			createWOwithMaterialPlans("Test validate Vendor", "CM");
			storeValue("txtbx_WONUM", "WONUM");
//			Route the WO
			changeStatus("", "PLANNED");
			routeWF("", "DFA");
			stopWF("", "");
//			Deactivate Vendor and try to save WO - should generate error
			Log.info("Disqualify Vendor..................");
			driver.findElement(By.xpath(OR.getProperty("btn_Vendor_chevron"))).click();
			Log.info("Click btn_Vendor_chevron..................");
			waitFor(); 
			driver.findElement(By.xpath("//span[text()='Go To Companies']")).click();
			Log.info("Click Go To Companies..................");
			waitFor(); 
			driver.findElement(By.xpath(OR.getProperty("chkbx_DisqualifyVendor"))).click();
			Log.info("Click chkbx_DisqualifyVendor..................");
			save("1","1");
			driver.findElement(By.xpath(OR.getProperty("btn_ReturnValue"))).click();
			Log.info("Click btn_ReturnValue..................");
			waitForElementDisplayed("txtbx_WONUM", "1");
//			Try to save edit the WO and save
			Log.info("Click btn_Save..................");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Description"))).sendKeys(" - inactivate Vendor");
			driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
			verifyAlert("msg_Popup", "is not valid");
			click("btn_OK", null);
//			Try to route the WO
			Log.info("Click btn_Route..................");
			driver.findElement(By.xpath(OR.getProperty("btn_Route"))).click();
			verifyAlert("msg_Popup", "is not valid");
			click("btn_OK", null);
			waitFor();
//			Reactivate Vendor
			Log.info("Reactivate Vendor..................");
			driver.findElement(By.xpath(OR.getProperty("btn_Vendor_chevron"))).click();
			waitFor();
			driver.findElement(By.xpath("//span[text()='Go To Companies']")).click();
//			waitForElementDisplayed("txtbx_company", "1");
			waitFor(); 
			driver.findElement(By.xpath(OR.getProperty("chkbx_DisqualifyVendor"))).click();
			Log.info("Click chkbx_DisqualifyVendor to reactivate..................");
			save("1","1");
			driver.findElement(By.xpath(OR.getProperty("btn_ReturnValue"))).click();
			waitForElementDisplayed("txtbx_WONUM", "1");
			Log.info("Reopen WO, the app does not recognize the new Vendor status...");
			logout("1","1");
			openBrowser("1", "Mozilla");
			login("1","maxadmin");
			Log.info("Start validateVendor..................");
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_WO");
			waitFor();
			waitForElementDisplayed("txtbx_QuickSearch", "1");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(DriverScript.WONUM);
			enter("txtbx_QuickSearch", "1");
			waitFor();
//			Approved WO should not validate Vendor Status
			changeStatus("", "approved");
//			Deactivate Vendor and try to save WO - should generate error
			Log.info("Click tab_Plans..................");
			driver.findElement(By.xpath(OR.getProperty("tab_Plans"))).click();
  		    waitForElementDisplayed("tab_Materials");
  		    Log.info("Click tab_Materials.................."); 		   
			driver.findElement(By.xpath(OR.getProperty("tab_Materials"))).click();
			Log.info("Click details arrowdown..................");
			waitForElementDisplayed("btn_ViewDetails_Row1");
			driver.findElement(By.xpath(OR.getProperty("btn_ViewDetails_Row1"))).click();
			waitForElementDisplayed("btn_Vendor_chevron");
			Log.info("Deactivate Vendor..................");
			driver.findElement(By.xpath(OR.getProperty("btn_Vendor_chevron"))).click();
			waitFor();
			driver.findElement(By.xpath("//span[text()='Go To Companies']")).click();
//			waitForElementDisplayed("txtbx_company", "1");
			waitFor();
			driver.findElement(By.xpath(OR.getProperty("chkbx_DisqualifyVendor"))).click();
			save("1","1");
			driver.findElement(By.xpath(OR.getProperty("btn_Return"))).click();
			Log.info("Reopen WO, the app does not recognize the new Vendor status...");
			logout("1","1");
			openBrowser("1", "Mozilla");
			login("1","maxadmin");
			Log.info("Start validateVendor..................");
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_WO");
			waitFor();
			waitForElementDisplayed("txtbx_QuickSearch", "1");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(DriverScript.WONUM);
			enter("txtbx_QuickSearch", "1");
			waitFor();
//			Try to save edit the WO and save
			waitForElementDisplayed("txtbx_Description");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Description"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Description"))).sendKeys("Test validate Vendor - reactivate Vendor");
			save("1","1");
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
 			DriverScript.bResult = false;
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
 			DriverScript.bResult = false;
	    }	
	}
}

