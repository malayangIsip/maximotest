package executionEngine;

import static executionEngine.DriverScript.OR;
import static executionEngine.DriverScript.driver;
import static executionEngine.DriverScript.extentTest;
import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import config.ActionKeywords;
import config.Actions;
import config.Constants;
import pageObjects.WOPage;
import utility.*;

public class WOUnitTests extends ActionKeywords {	
	public static WOPage  woPage;
	
	LocalDate ngayon = getDate();
     //add 2 week to the current date
//     LocalDate next2Week = ngayon.plus(2, ChronoUnit.WEEKS);
	
	public WOUnitTests() {
		woPage = new WOPage(driver);
	}
	
	@Test
    //	[MAX-1012] Inventory Item required Dates should synch with Scheduled Start Date 
	public void _schedDateButton() throws Exception {
		try {
			createWOwithPlans("Test _schedDateButton", "CM");
			waitFor();
			Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty("lnk_SetSchedDate"))).isEnabled());
			
			//if scheduled start date is in the past and the work order is not approved, the button is available but when pressed an error is displayed
			driver.findElement(By.xpath(OR.getProperty("txtbx_ScheduledStart"))).sendKeys(ngayon.minus(2, ChronoUnit.WEEKS).toString());
			Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty("lnk_SetSchedDate"))).isEnabled());
			driver.findElement(By.xpath(OR.getProperty("lnk_SetSchedDate"))).click();
			verifyAlert("msg_Popup", "The Scheduled Start Date cannot be empty");
			driver.findElement(By.xpath(OR.getProperty("btn_Close"))).click();
			waitFor();
			
			//If Scheduled Start date is empty the button is greyed out
			driver.findElement(By.xpath(OR.getProperty("txtbx_ScheduledStart"))).clear();
			Assert.assertFalse(driver.findElement(By.xpath(OR.getProperty("lnk_SetSchedDate"))).isEnabled());
				
			//if scheduled start date is in the future and the work order is not approved, the button is available and when pressed updates all material required dates to the scheduled start date, the screen refreshes on save. If there are no materials to update, no changes occur
			driver.findElement(By.xpath(OR.getProperty("txtbx_ScheduledStart"))).sendKeys(ngayon.plus(2, ChronoUnit.WEEKS).toString());
			driver.findElement(By.xpath(OR.getProperty("lnk_SetSchedDate"))).click();   
			save("1","1");
			waitFor();
			String materialsRequiredDate = driver.findElement(By.xpath(OR.getProperty("txtbx_ScheduledStart"))).getAttribute("value");
			String WOSchedStartDate = driver.findElement(By.xpath(OR.getProperty("txtbx_RequiredDate"))).getAttribute("value");
			Assert.assertEquals(materialsRequiredDate, WOSchedStartDate);
			
			//If Work Order is approved the button is greyed out
			//Added assert in WorkorderWorkflow.class
		} catch(AssertionError ae){
			Log.error("JUNIT _schedDateButton --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
		}
	}
	
	public void openApp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:/Users/mme9310/Documents/lib/chromedriver.exe");
		
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		 
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
	    capabilities.setBrowserName("chrome");
	    
		driver=new ChromeDriver(chromeOptions);
		DesiredCapabilities dc = new DesiredCapabilities();
		
		int implicitWaitTime=(1);
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		Log.info("Navigating to URL");
		driver.get(Constants.URL);

		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
	}
	
	@Before
	public void setUp() throws Exception {
		openApp();
	}
	
	@After
	public void quit() throws IOException, InterruptedException {
		driver.quit();
	}	
}