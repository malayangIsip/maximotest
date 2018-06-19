package config;

import static executionEngine.Base.OR;
import static executionEngine.Base.action;
import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;

import org.apache.commons.lang3.exception.ExceptionUtils;
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
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import executionEngine.Base;
import executionEngine.TestAutomation;
import utility.Log;
import utility.TestResultListener;

//@Listeners(TestResultListener.class)
public class TestLog extends TestAutomation {
    static String testCase = "";
    static String testName = "TestLog";

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
   	
    	Base.bResult = true;
    }
    
    @AfterMethod
    public void logout() throws Exception {
    	logout(testName, testCase);
    }
    
    @Test
    void testing() throws Exception {
		testCase = "testing";
		Log.startTestCase(testCase);
		try {
			Log.info(">>>>>>>>>>>>>>>>>>>>>>>");
			action.login("maxadmin");
			action.goToWOPage();
			action.clickNew();
			action.clickPlansTab();
			
			action.click("tab_Services");
			action.click("btn_NewRow_Services");
			
			action.click("btn_SelectStandardService");
//			action.waitElementExists("icon_Filter");
			Thread.sleep(2000);
			driver.findElement(By.xpath(OR.getProperty("icon_Filter"))).click();;
//			action.click("icon_Filter"); for some reason this does not work maybe because element's aria-disabled property is set to true
			action.input("txtbx_Search_Code", "hirail4");
			action.clickFilter();
			action.click("lst_SelectStandardService");
			action.clickOK();
			
//			action.isRequired("txtbx_Service", true);

		} catch (Exception e) {
	    	Log.error("Exception testing --- " + e.getMessage());
	    	extentTest.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
  
    public void goToWOPage() throws Exception {
		goToHomePage();
   }
    
    public void goToHomePage() throws Exception {
		click("lnk_Home");
  }
    
    public void click(String object) throws Exception {
			Log.info("Clicking on Webelement "+ object);
			waitElementExists(object);
			elementClickable(object);
			driver.findElement(By.xpath(OR.getProperty(object))).click();
            Thread.sleep(1000);

	}
    
    public void waitElementExists(String object) throws Exception {
			Log.info("Checks Webelement exists: "+ object);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));
			waitForElementDisplayed(object);
	}
    
    private void elementClickable(String object) throws Exception {
		Log.info("Checks Webelement is clickable: "+ object);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));

   }
    
    private boolean waitForElementDisplayed(String object) throws Exception{
		int i = 0;
		Log.info("Waiting for element to display");
		while (!elementDisplayed(By.xpath(OR.getProperty(object)))&&(i <= 60)){
			System.out.println("Searching for element..."+i);
			Thread.sleep(1000);
			i++;
		}
		
		if (i > 60){
			System.out.println(object+": Element not located in a reasonable timeframe");
	    	Base.bResult = false;
 			Assert.fail();
			throw new Exception(object+": Element not located in a reasonable timeframe");
		//	return false;
		} else{
			Thread.sleep(2000);
			return true;
		}
	}
    
    private boolean elementDisplayed(By xpath) {
		try {
			if(xpath != null) {
				WebElement ane = driver.findElement(xpath);
				if (ane != null && ane.isDisplayed()) {
					System.out.println("Element xpath: " + xpath + " was found");
					ane=null;
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println("Element Not Found.");	
//			extentTest.log(LogStatus.ERROR, e.getMessage());
			return false;
		}
		System.out.println("Element Not Found.");	
		return false;
	}
}
