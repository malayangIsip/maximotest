package executionEngine;


import static executionEngine.Base.driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import config.ActionKeywords;
import utility.Constants;
import utility.Log;


public class Base {
	public static WebDriver driver;
	public static Properties OR;
	public static ActionKeywords action;
	public static Log Log;
	
	public static int retryLimit = 2;
	public static boolean bResult;
	
	WebDriverWait wait;
	
	public static String storedValue;
	public static String WONUM = null;
	public static String ASSETNUM = null;
	public static String PRNUM = null;
	public static String PONUM = null;
	public static String Storeroom = null;
	public static String PMNUM = null;
	public static String RouteNum= null;
	public static String JPNum= null;
	public static String Template = null;
	public static String SRNUM = null;
	public static String LOCATION = null;
	public static String POINT = null;
	public static String WFApprover = null;
	public static String VENDOR = null;
	
	//reporting
	public static ExtentReports extent;
	public static ExtentTest extentTest;
	
	
	@BeforeSuite
	public void setup() throws Exception {
		DOMConfigurator.configure("log4j.xml");
		String Path_OR = Constants.Path_OR;
		FileInputStream fs = new FileInputStream(Path_OR);
		OR = new Properties(System.getProperties());
		OR.load(fs);
	
		extent = new ExtentReports(Constants.extentReportFile, true);
		
		action = new ActionKeywords();
		
	}
	
	@AfterSuite
    public void tearDown() throws Exception {
        driver.quit();
        
//        // close report.
//     	extent.endTest(extentTest);
     // writing everything to document.
     	extent.flush();
     	
    }
	
}
