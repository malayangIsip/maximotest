package config;

import static executionEngine.DriverScript.OR;
import static executionEngine.DriverScript.driver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import executionEngine.DriverScript;
import utility.Log;

public class WOAssetLocRoute extends ActionKeywords {
	
	static WebDriverWait wait = new WebDriverWait(driver, 5);

	public static void woAssetLocRoute() {
//		testScroll("1000014");
		
		scenario1("1000014");
		scenario2Task("1000014");
		scenario2Child("1000014");
//		scenario3();
	}
	
    /*053.01. Any WO that is submitted for approval via the WO workflow must reference an Asset, Location or Route*/
	static void scenario1(String asset) {
		try {
			waitFor5();
			Log.info("Start WO scenario1........................");
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_WO");
			waitForElementDisplayed("btn_New", "1");
			createWOwithPlans("WOAssetLocRoute scenario1", "CM");
//			remove reference to asset, location or route
			Log.info("remove reference to asset, location or route");
			driver.findElement(By.xpath(OR.getProperty("tab_Main"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))).clear();
			save("1", "1");
			driver.findElement(By.xpath(OR.getProperty("btn_Route"))).click();
			verifyAlert("msg_Popup", "This Work Order cannot be submitted for approval as it does not reference an Asset, Location or Route, or one or more of its children or tasks does not reference an Asset, Location or Route."); 
			driver.findElement(By.xpath(OR.getProperty("btn_Close"))).click();
			waitFor();
//          populate only assetnum  
			Log.info("populate only assetnum");
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys(asset);
			save("1", "1");
			driver.findElement(By.xpath(OR.getProperty("btn_Route"))).click();
			waitForElementDisplayed("radio_WF_DFA", "1");
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
//			verify routed in workflow
			Log.info("verify routed in workflow");
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(OR.getProperty("hvr_Workflow")))));
            hover("hvr_Workflow","lnk_WFAssignment");
            isEmpty("tbl_WFAssignment", "False");
            driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(OR.getProperty("hvr_Workflow")))));
//          populate only location 
            Log.info("populate only location ");
            hover("hvr_Workflow","stop_Workflow");
            driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
            waitFor();
//            wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(OR.getProperty("titlebar_message"))), "Process KR_WO stopped."));
            changeStatus("", "Create");
            waitFor();
            driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
            driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))).sendKeys("9002086");
//            driver.findElement(By.xpath(OR.getProperty("btn_Yes"))).click();
            waitFor();
            save("1", "1");
            driver.findElement(By.xpath(OR.getProperty("btn_Route"))).click();
			waitForElementDisplayed("radio_WF_DFA", "1");
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
//			verify routed in workflow
			Log.info("verify routed in workflow");
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(OR.getProperty("hvr_Workflow")))));
            hover("hvr_Workflow","lnk_WFAssignment");
            isEmpty("tbl_WFAssignment", "False");
            driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(OR.getProperty("hvr_Workflow")))));
            hover("hvr_Workflow","stop_Workflow");
            driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
            waitFor();
//            wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(OR.getProperty("titlebar_message"))), "Process KR_WO stopped."));
            changeStatus("", "Create");
            driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
            driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))).clear();
//          populate only route
            Log.info("populate only route");
            driver.findElement(By.xpath(OR.getProperty("lnk_ApplyRoute"))).click();
            waitForElementDisplayed("tbl_Routes", "1");
            driver.findElement(By.xpath("//td[contains(@id,'_tdrow_[C:0]-c[R:0]')]")).click();
            waitForElementDisplayed("btn_Close", "1");
            driver.findElement(By.xpath(OR.getProperty("btn_Close"))).click();
            waitFor();
            driver.findElement(By.xpath(OR.getProperty("tab_Plans"))).click();
            waitForElementDisplayed("tbl_WO_Child", "1");
            Log.info("Verify table not empty...");
            tableNotEmpty("Children of Work Order", "True");
            waitFor();
            driver.findElement(By.xpath(OR.getProperty("tab_Main"))).click();
            waitForElementDisplayed("btn_Route", "1");
            driver.findElement(By.xpath(OR.getProperty("btn_Route"))).click();
			waitForElementDisplayed("radio_WF_DFA", "1");
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			Thread.sleep(3000);
//			verify routed in workflow
			Log.info("verify routed in workflow");
			waitForElementDisplayed("icon_Routed", "1");
			scrollUp("hvr_Workflow","");
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(OR.getProperty("hvr_Workflow")))));
			hover("hvr_Workflow","lnk_WFAssignment");
            isEmpty("tbl_WFAssignment", "False");
            driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
            Log.info("End WO scenario1........................");
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
	
	/*053.02. Any WO that is a child of a WO being submitted for approval must have an Asset, Location or Route on it*/
	static void scenario2Task(String asset) {
		try {
			waitFor5();
      		Log.info("Start scenario2Task.......................");
      		driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_WO");
			waitForElementDisplayed("btn_New", "1");
			createWOwithPlans("WOAssetLocRoute scenario2", "CM");
//			--------------
//			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys("2796197");
//			enter("txtbx_QuickSearch","1");
//			waitForElementDisplayed("tab_Plans", "1");
//			driver.findElement(By.xpath(OR.getProperty("tab_Plans"))).click();
//			-----------------
			Log.info("create task WO");
			waitForElementDisplayed("btn_NewRow_TasksWO", "1");
			driver.findElement(By.xpath(OR.getProperty("btn_NewRow_TasksWO"))).click();
			waitForElementDisplayed("txtbx_WOTaskDescription", "1");
			waitFor();
			driver.findElement(By.xpath(OR.getProperty("txtbx_WOTaskDescription"))).sendKeys("WOAssetLocRoute scenario2 Task");

//			scrollDown("txtbx_AssetNum");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).click();
//			waitForElementDisplayed("txtbx_AssetNum", "1");
			
			List<WebElement> assetLabel = driver.findElements(By.xpath("//label[text()='Asset:']"));
			String assetFor = assetLabel.get(1).getAttribute("for");
			String assetVal = driver.findElement(By.xpath("//input[@id='" + assetFor + "']")).getAttribute("value");
			
			Assert.assertTrue(assetVal.equals(asset));
			save("1", "1");
			Log.info("Clear asset");
			Log.info("Go to Task WO");
			driver.findElement(By.xpath(OR.getProperty("btn_ReferenceWO_chevron"))).click();
			driver.findElement(By.xpath(OR.getProperty("lnk_ActivitiesAndTasks"))).click();
			waitForElementDisplayed("btn_Return", "1");
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
	        save("1", "1");
	        Thread.sleep(1000);
	        driver.findElement(By.xpath(OR.getProperty("btn_Return"))).click();
	        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath(OR.getProperty("app_name")), "Work Order Tracking"));
            Log.info("route to workflow");
            driver.findElement(By.xpath(OR.getProperty("btn_Route"))).click();
            Thread.sleep(1000);
            verifyAlert("msg_Popup", "This Work Order cannot be submitted for approval as it does not reference an Asset, Location or Route, or one or more of its children or tasks does not reference an Asset, Location or Route."); 
			driver.findElement(By.xpath(OR.getProperty("btn_Close"))).click();
			waitFor();
			Log.info("populate asset");
			Log.info("Go to Task WO");
			driver.findElement(By.xpath(OR.getProperty("btn_ReferenceWO_chevron"))).click();
			driver.findElement(By.xpath(OR.getProperty("lnk_ActivitiesAndTasks"))).click();
			waitForElementDisplayed("btn_Return", "1");
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys("1000014");
			Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).getAttribute("value").equals("1000014"));
	        save("1", "1");
	        Thread.sleep(1000);
	        driver.findElement(By.xpath(OR.getProperty("btn_Return"))).click();
	        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath(OR.getProperty("app_name")), "Work Order Tracking"));
            Log.info("route to workflow");
            driver.findElement(By.xpath(OR.getProperty("btn_Route"))).click();            
			waitForElementDisplayed("radio_WF_DFA", "1");
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			Log.info("verify routed in workflow");
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(OR.getProperty("hvr_Workflow")))));
            hover("hvr_Workflow","lnk_WFAssignment");
            isEmpty("tbl_WFAssignment", "False");
            driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
            Log.info("End scenario2Task.......................");
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
	

	/*053.02. Any WO that is a child of a WO being submitted for approval must have an Asset, Location or Route on it*/
	static void scenario2Child(String asset) {
		try {
			waitFor5();
//			String assetVal="//input[@id=(//label[text()='Asset:'][1]/@for)]";
			
			Log.info("Start scenario2Child...............................");
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_WO");
			waitForElementDisplayed("btn_New", "1");
			createWOwithPlans("WOAssetLocRoute scenario2", "CM");
			Log.info("create Child WO");
			waitForElementDisplayed("btn_NewRow_ChildrenWO", "1");
			driver.findElement(By.xpath(OR.getProperty("btn_NewRow_ChildrenWO"))).click();
			waitForElementDisplayed("txtbx_WOChildDescription", "1");
			waitFor();
			driver.findElement(By.xpath(OR.getProperty("txtbx_WOChildDescription"))).sendKeys("WOAssetLocRoute scenario2 Child");
			
			List<WebElement> assetLabel = driver.findElements(By.xpath("//label[text()='Asset:']"));
			String assetFor = assetLabel.get(0).getAttribute("for");
			String assetVal = driver.findElement(By.xpath("//input[@id='" + assetFor + "']")).getAttribute("value");
			
			Assert.assertEquals(assetVal, "");
			Log.info("route to workflow");
            driver.findElement(By.xpath(OR.getProperty("btn_Route"))).click();
            verifyAlert("msg_Popup", "This Work Order cannot be submitted for approval as it does not reference an Asset, Location or Route, or one or more of its children or tasks does not reference an Asset, Location or Route."); 
			driver.findElement(By.xpath(OR.getProperty("btn_Close"))).click();
			waitFor();
            Log.info("populate asset");
			driver.findElement(By.xpath("//input[@id='" + assetFor + "']")).sendKeys("1000014");
			Assert.assertTrue(driver.findElement(By.xpath("//input[@id='" + assetFor + "']")).getAttribute("value").equals("1000014"));
			save("1", "1");
            Log.info("route to workflow");
            driver.findElement(By.xpath(OR.getProperty("btn_Route"))).click();            
			waitForElementDisplayed("radio_WF_DFA", "1");
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			Log.info("verify routed in workflow");
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(OR.getProperty("hvr_Workflow")))));
            hover("hvr_Workflow","lnk_WFAssignment");
            isEmpty("tbl_WFAssignment", "False");
            driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
            Log.info("End scenario2Child...............................");
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
	
	/*053.02. WOs that do not get approved via this mechanism (e.g. PMs and 155 high priority SR generated WOs) will not be subjected to these requirements*/
	static void scenario3() {
		try {
			waitFor5();
			Log.info("Start WO scenario3................................");
			logout("1", "1");
			openBrowser("", "Mozilla");
			driver.findElement(By.xpath(".//*[@id='username']")).sendKeys("mx155");
			driver.findElement(By.xpath(".//*[@id='password']")).sendKeys("Kiwirail123");
			driver.findElement(By.xpath(".//*[@id='loginbutton']")).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_SR");
			waitForElementDisplayed("btn_New", "1");
			createSR("WOAssetLocRoute scenario3", "KRNETWORK");
			waitFor();
			Log.info("clear asset");
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
			Log.info("clear location");
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
			Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).getAttribute("value").equals(""));
			Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty("txtbx_Location"))).getAttribute("value").equals(""));
			Log.info("Set priority to 1");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Priority"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Priority"))).sendKeys("1");
			Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty("txtbx_Priority"))).getAttribute("value").equals("1"));
			driver.findElement(By.xpath(OR.getProperty("txtbx_ReporterType"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_ReporterType"))).sendKeys("EMERGENCY");
			save("1", "1");
			Log.info("route to workflow");
            driver.findElement(By.xpath(OR.getProperty("btn_Route"))).click();
            waitFor5();
            wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_Status"))), "INPROG"));
            Log.info("Verify generated WO...");
            driver.findElement(By.xpath(OR.getProperty("tab_RelatedRecord"))).click();
            isEmpty("tbl_Related_WO", "False");
            driver.findElement(By.xpath(OR.getProperty("btn_Related_Chevron_Row1"))).click();
            driver.findElement(By.xpath(OR.getProperty("lnk_Related_WO"))).click();
            assertValue("txtbx_Status", "APPR");
            assertValue("txtbx_Worktype", "EM");
            driver.findElement(By.xpath(OR.getProperty("btn_Return"))).click();
            Log.info("End WO scenario3................................");
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
	
	static void scrollDown(String object) throws Exception {
		  Log.info("scrolling down to field............");
//		  WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
//		  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
//		  Thread.sleep(500); 
//		  element.click();
		  
		  JavascriptExecutor js = (JavascriptExecutor) driver;
		  js.executeScript("javascript:window.scrollBy(250, 350)");
		  Thread.sleep(2000); 
	  }
	
	static void moveTo(String object) throws Exception {
		  Log.info("scrolling down to field............");
		  WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
		  Actions actions = new Actions(driver);
		  actions.moveToElement(element).perform();
		  Thread.sleep(500); 
	  }
	
	
	static void testScroll(String asset) {
		try {
			waitFor5();
      		Log.info("Start testScroll......................");
      		driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_WO");
			waitForElementDisplayed("btn_New", "1");

			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys("3168386");
			enter("txtbx_QuickSearch","1");
			waitForElementDisplayed("tab_Plans", "1");
			driver.findElement(By.xpath(OR.getProperty("tab_Plans"))).click();

			Log.info("create task WO");
			waitForElementDisplayed("btn_NewRow_TasksWO", "1");
			driver.findElement(By.xpath(OR.getProperty("btn_NewRow_TasksWO"))).click();
			waitForElementDisplayed("txtbx_WOTaskDescription", "1");
			waitFor();
			driver.findElement(By.xpath(OR.getProperty("txtbx_WOTaskDescription"))).sendKeys("WOAssetLocRoute scenario2 Task");
			                                         
//       	    scrollDown("btn_ReferenceWO_chevron");
			
//			driver.findElement(By.xpath(OR.getProperty("btn_ReferenceWO_chevron"))).click();
	
			List<WebElement> assetLabel = driver.findElements(By.xpath("//label[text()='Asset:']"));
			String assetFor = assetLabel.get(1).getAttribute("for");
			String assetVal = driver.findElement(By.xpath("//input[@id='" + assetFor + "']")).getAttribute("value");

			Log.info("asset value = "+assetVal);
			
			Log.info("End testScroll.......................");
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
