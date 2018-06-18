package config;

import static executionEngine.Base.OR;
import static executionEngine.Base.action;
import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import executionEngine.Base;
import executionEngine.TestAutomation;
import utility.Log;

public class WOAssetLocRoute extends TestAutomation {
	static String user = "mxplan";

    static String testCase = "";
    static String testName = "WOAssetLocRoute";
    
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
    
    @Override  
    @AfterMethod
    public void logout() {
    	logout(testName, testCase);

    }


	@DataProvider
 	public Object[][] asset() {
 		return new Object[][] { {"1000014", "CM"} };
 	}
    
    @Test(dataProvider = "asset")
    /*053.01. Any WO that is submitted for approval via the WO workflow must reference an Asset, Location or Route*/
	void scenario1(String asset, String worktype) {
		testCase = "scenario1";
    	extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
	    	action.login("maxadmin");
			action.goToWOPage();
			action.createWOwithPlans("WOAssetLocRoute scenario1", worktype);
						
//			remove reference to asset, location or route
			Log.info("remove reference to asset, location or route");
			action.click("tab_Main");
			action.clear("txtbx_AssetNum");
			action.clear("txtbx_Location");
			action.save();
			action.route();
			
			action.verifyAlert("This Work Order cannot be submitted for approval as it does not reference an Asset, Location or Route, or one or more of its children or tasks does not reference an Asset, Location or Route."); 
			action.clickClose();

//          populate only assetnum  
			Log.info("populate only assetnum");
			action.input("txtbx_AssetNum", asset);
			action.save();
			action.routeWF("dfa");
			
//			verify routed in workflow
			Log.info("verify routed in workflow");
			Assert.assertEquals(action.inWorkflow(), true);
			action.clickOK();
            action.stopWF();

//          populate only location 
            Log.info("populate only location ");
            action.changeStatus("Create");

            action.clear("txtbx_AssetNum");
            action.input("txtbx_Location", "9002086");
            action.save();
            
            action.routeWF("dfa");

//			verify routed in workflow
			Log.info("verify routed in workflow");
			Assert.assertEquals(action.inWorkflow(), true);
			action.clickOK();
            action.stopWF();
            
            Log.info("populate only route");
            action.changeStatus("Create");

            action.clear("txtbx_AssetNum");
            action.clear("txtbx_Location");
            action.click("lnk_ApplyRoute");
            action.waitElementExists("tbl_Routes");
            driver.findElement(By.xpath("//td[contains(@id,'_tdrow_[C:0]-c[R:0]')]")).click();
            action.waitElementExists("btn_Close");
            action.clickClose();
            
            Log.info("Verify table not empty...");
            action.click("tab_Plans");
            action.waitElementExists("tbl_WO_Child");
            action.tableNotEmpty("Children of Work Order", true);

            Log.info("Verify routed in workflow");
            action.click("tab_Main");
            action.routeWF("dfa");
            action.waitElementExists("icon_Routed");

            Assert.assertEquals(action.inWorkflow(), true);
			action.clickOK();
            action.stopWF();
		} catch(AssertionError ae){
			Log.error("Assertion failed scenario1--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found scenario1--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception scenario1--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
	
    @Test(dataProvider = "asset")
	/*053.02. Any WO that is a child of a WO being submitted for approval must have an Asset, Location or Route on it*/
	void scenario2Task(String asset, String worktype) {
    	testCase = "scenario2Task";
    	extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
      		action.login(user);
      		action.goToWOPage();
      		action.createWOwithPlans("WOAssetLocRoute scenario2", worktype);

      		Log.info("create task WO");
			action.click("btn_NewRow_TasksWO");
			
			List<WebElement> assetLabel = driver.findElements(By.xpath("//label[text()='Asset:']"));
			String assetFor = assetLabel.get(1).getAttribute("for");
			String assetVal = driver.findElement(By.xpath("//input[@id='\" + assetFor + \"']")).getAttribute("value");
			
			Assert.assertTrue(assetVal.equals(asset));
			action.save();
			
			Log.info("Clear asset");
			Log.info("Go to Task WO");
			action.clickViewDetailsRow1();
			action.click("btn_ReferenceWO_chevron");
			action.click("lnk_ActivitiesAndTasks");
			action.clear("txtbx_AssetNum");
			action.save();
			action.click("btn_Return");
			
            Log.info("route to workflow");
            action.route();
            action.verifyAlert("This Work Order cannot be submitted for approval as it does not reference an Asset, Location or Route, or one or more of its children or tasks does not reference an Asset, Location or Route."); 
			action.clickClose();
			
			Log.info("populate asset");
			Log.info("Go to Task WO");
			action.clickViewDetailsRow1();
			action.click("btn_ReferenceWO_chevron");
			action.click("lnk_ActivitiesAndTasks");

			action.input("txtbx_AssetNum", "1000014");
			Assert.assertTrue(action.getAttributeValue("txtbx_AssetNum").equals("1000014"));
	        action.save();
	        action.clickReturn();
	        
	        Log.info("route to workflow");
            action.routeWF("dfa");
            
			Log.info("verify routed in workflow");
			Assert.assertEquals(action.inWorkflow(), true);
			action.clickOK();
            action.stopWF();
		}catch(AssertionError ae){
			Log.error("Assertion failed scenario2Task--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found scenario2Task--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception scenario2Task--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
	

    @Test(dataProvider = "asset")
	/*053.02. Any WO that is a child of a WO being submitted for approval must have an Asset, Location or Route on it*/
	void scenario2Child(String asset, String worktype) {
    	testCase = "scenario2Child";
    	extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
			action.login(user);
			action.goToWOPage();
			
			action.createWOwithPlans("WOAssetLocRoute scenario2", worktype);

			Log.info("create Child WO");
			action.waitElementExists("btn_NewRow_ChildrenWO");
			action.click("btn_NewRow_ChildrenWO");
//			action.waitElementExists("txtbx_WOChildDescription", "1");
//			action.input("txtbx_WOChildDescription", "WOAssetLocRoute scenario2 Child");
			
			List<WebElement> assetLabel = driver.findElements(By.xpath("//label[text()='Asset:']"));
			String assetFor = assetLabel.get(0).getAttribute("for");
			String assetVal = driver.findElement(By.xpath("//input[@id='\" + assetFor + \"']")).getAttribute("value");
			
			Assert.assertEquals(assetVal, "");
			Log.info("route to workflow");
            action.route();
            action.verifyAlert("This Work Order cannot be submitted for approval as it does not reference an Asset, Location or Route, or one or more of its children or tasks does not reference an Asset, Location or Route."); 
            action.clickClose();

            Log.info("Populate asset");
            //open drilldown
            action.clickViewDetailsRow1();
            action.waitElementExists(By.xpath("//input[@id='\" + assetFor + \"']"));
			driver.findElement(By.xpath("//input[@id='\" + assetFor + \"']")).sendKeys("1000014");
			Assert.assertTrue(driver.findElement(By.xpath("//input[@id='\" + assetFor + \"']")).getAttribute("value").equals("1000014"));
			action.save();
			
            Log.info("route to workflow");
            action.routeWF("dfa");
            
            Log.info("verify routed in workflow");
			Assert.assertEquals(action.inWorkflow(), true);
			action.clickOK();
            action.stopWF();
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
	
    @Test
	/*053.02. WOs that do not get approved via this mechanism (e.g. PMs and 155 high priority SR generated WOs) will not be subjected to these requirements*/
	void scenario3() {
		testCase = "scenario3";
    	extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
 		WebDriverWait wait = new WebDriverWait(driver, 5);
 		 		
		try {
			action.login("mx155");
			action.goToSRPage();
			
			action.createSR("WOAssetLocRoute scenario3", "KRNETWORK");

			Log.info("clear asset and location");
			action.clear("txtbx_AssetNum");
			action.clear("txtbx_Location");

			Log.info("Set priority to 1");
			action.input("txtbx_Priority", "1");
			Assert.assertTrue(action.getAttributeValue("txtbx_Priority").equals("1"));
			action.input("txtbx_ReporterType", "EMERGENCY");
			action.save();
			
			Log.info("route to workflow");
            action.route();
            action.assertValue("txtbx_Status", "INPROG");
//            wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath(OR.getProperty("txtbx_Status"))), "INPROG"));
           
            Log.info("Verify generated WO...");
            action.click("tab_RelatedRecord");
            action.isEmpty("tbl_Related_WO", false);
            action.click("btn_Related_Chevron_Row1");
            action.click("lnk_Related_WO");
            action.assertValue("txtbx_Status", "APPR");
            action.assertValue("txtbx_Worktype", "EM");
            action.clickReturn();
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
	
	void _moveTo(String object) throws Exception {
		  Log.info("scrolling down to field............");
		  WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
		  Actions actions = new Actions(driver);
		  actions.moveToElement(element).perform();
		  Thread.sleep(500); 
	  }
	
	
	void _testScroll(String asset) {
		try {
//			waitFor5();
//      		Log.info("Start testScroll......................");
//      		driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
//			waitElementExists("hvr_WO", "1");
//			hover("hvr_WO","lnk_WO");
//			waitForElementDisplayed("btn_New", "1");
//
//			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys("3168386");
//			enter("txtbx_QuickSearch");
//			waitForElementDisplayed("tab_Plans", "1");
//			driver.findElement(By.xpath(OR.getProperty("tab_Plans"))).click();
//
//			Log.info("create task WO");
//			waitForElementDisplayed("btn_NewRow_TasksWO", "1");
//			driver.findElement(By.xpath(OR.getProperty("btn_NewRow_TasksWO"))).click();
//			waitForElementDisplayed("txtbx_WOTaskDescription", "1");
//			waitFor();
//			driver.findElement(By.xpath(OR.getProperty("txtbx_WOTaskDescription"))).sendKeys("WOAssetLocRoute scenario2 Task");
//			                                         
////       	    scrollDown("btn_ReferenceWO_chevron");
//			
////			driver.findElement(By.xpath(OR.getProperty("btn_ReferenceWO_chevron"))).click();
//	
//			List<WebElement> assetLabel = driver.findElements(By.xpath("//label[text()='Asset:']"));
//			String assetFor = assetLabel.get(1).getAttribute("for");
//			String assetVal = driver.findElement(By.xpath("//input[@id='" + assetFor + "']")).getAttribute("value");
//
//			Log.info("asset value = "+assetVal);
//			
//			Log.info("End testScroll.......................");
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
	    }	
	}
}
