package config;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static executionEngine.DriverScript.OR;
import static executionEngine.DriverScript.driver;
import static executionEngine.DriverScript.extentTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;


import executionEngine.DriverScript;
import pageObjects.WOPage;
import utility.Log;
import utility.Utils;

public class ActionKeywords {
	public static WOPage  woPage;
//	static String password = "sirius";
//	static String password = "Kiwirail123";
	
//	static String code = null;
	static String duplicateMsg = "BMXAA4131E";
	static String saveMsg = "Record has been saved.";
	static String changeStatusMsg = "BMXAA4591I";
	
	public ActionKeywords() {
		woPage = new WOPage(driver);
	}
			
	@SuppressWarnings("deprecation")
	public static void openBrowser(String object,String data){		
		Log.info("Opening Browser...");
		try{	
			Log.info("Try opening browser");
			if(data.equals("Mozilla")) {
				Log.info("Mozilla is the browser");

				System.setProperty("webdriver.gecko.driver", "C:/Users/mme9310/Documents/lib/geckodriver.exe");
				
			    DesiredCapabilities cap = DesiredCapabilities.firefox();
			    cap.setBrowserName("firefox");
			    cap.setCapability("marionette", true);
			    
			    FirefoxProfile profile = new FirefoxProfile();
			    profile.setPreference("network.proxy.type", 4);
			    driver = new FirefoxDriver(profile);
//			    driver = new RemoteWebDriver(new URL("http://10.96.101.46:4444/wd/hub"), cap);
								
				Log.info("Mozilla browser started");				
			} else if(data.equals("IE")) {
				//Dummy Code, implement your own code
				driver=new InternetExplorerDriver();
				Log.info("IE browser started");
			} else if(data.equals("Chrome")) {
				Log.info("Chrome is the browser");
				System.setProperty("webdriver.chrome.driver", "C:/Users/mme9310/Documents/lib/chromedriver.exe");
				
				ChromeOptions chromeOptions = new ChromeOptions();
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			    capabilities.setBrowserName("chrome");
			    
				driver=new ChromeDriver(chromeOptions);
				Log.info("Chrome browser started");
			} else if(data.equals("Chrome--headless")) {
				Log.info("Chrome is the browser--headless");
				System.setProperty("webdriver.chrome.driver", "C:/Users/mme9310/Documents/lib/chromedriver.exe");
				
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("--headless");
				 
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			    capabilities.setBrowserName("chrome");
			    
				driver=new ChromeDriver(chromeOptions);
				Log.info("Chrome browser--headless started");
			}
			
			int implicitWaitTime=(1);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			navigate();
		}catch (Exception e){
			Log.info("Not able to open the Browser --- " + e.getMessage());
			extentTest.log(LogStatus.ERROR, "Not able to open the Browser --- " +e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void navigate(){
		try{
			Log.info("Navigating to URL");
			driver.get(Constants.URL);
//			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//			assertEquals("Welcome to MAX", driver.getTitle());			
//			assertEquals("Welcome to MAXTDMO", driver.getTitle());
		}catch(Exception e){
			Log.info("Not able to navigate --- " + e.getMessage());
			extentTest.log(LogStatus.ERROR, e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void login(String object, String data){
		try{
			String password = null;
			
			Log.info("Login: "+ data);
//			String[] temp = data.split(",");
			if (data.equalsIgnoreCase("maxadmin")) {
//				data = "mme9310";
				password="maxadmin";
			} else {
				password="Kiwirail123";
//				password="sirius";
			}
			Log.info("Password: "+ password);
			driver.findElement(By.xpath(".//*[contains(@id, 'username')]")).sendKeys(data.trim());
			driver.findElement(By.xpath(".//*[contains(@id, 'password')]")).sendKeys(password);
			driver.findElement(By.xpath(".//*[@id='loginbutton']")).click();
			waitFor();
		 }catch(Exception e){
 			Log.error("Not able to click --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	}
	
	public static void loginApprover(String object, String data){
		try{
			String password = null;
			
			if (data.equalsIgnoreCase("maxadmin")) {
				data = "mme9310";
				password = "Kepler09242";	
//				password="maxadmin";
			} else {
				password="Kiwirail123";
			}
			Log.info("Login Approver: "+ DriverScript.WFApprover);
			driver.findElement(By.xpath(".//*[contains(@id, 'username')]")).sendKeys(DriverScript.WFApprover.toLowerCase());
			driver.findElement(By.xpath(".//*[contains(@id, 'password')]")).sendKeys(password);
			driver.findElement(By.xpath(".//*[@id='loginbutton']")).click();
			waitFor();
		}catch(Exception e){
 			Log.error("Not able to click --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
       	}
	}
	
	public static void logout(String object, String data){
		try{
			Log.info("Logging out");
			driver.findElement(By.id("titlebar_hyperlink_8-lbsignout_image")).click();
//			driver.close();
//			ActionKeywords.closeBrowser("","");
		 }catch(Exception e){
 			Log.error("Not able to click --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
        }
	}

	public static void enter(String object, String data){
		try{
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			waitFor();
		}catch(Exception e){
 			Log.error("Not able to click --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
        }
	}
	
	static void click(String object) {
		click(object, null);
	}
	
	public static void click(String object, String data){
		try{
			Log.info("Clicking on Webelement "+ object);
			elementClickable(object);
			driver.findElement(By.xpath(OR.getProperty(object))).click();
            Thread.sleep(2000);
		 }catch(Exception e){
 			Log.error("Not able to click --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	}
	
	public static void hover(String object, String data){
		try{
			Log.info("Hover on Webelement "+ object);
//			waitElementExists(object);
			waitForElementDisplayed(object);
			WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
	        Actions action = new Actions(driver);	
	        action.moveToElement(element).build().perform();
	        driver.findElement(By.xpath(OR.getProperty(data))).click();	
	        waitFor();
		 }catch(Exception e){
 			Log.error("Not able to hover --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	} 
	
//	tobe deleted
    static void _ok(){
		try{
			Log.info("Click OK button");
	        driver.findElement(By.xpath("//button[@type='button' and contains (., 'OK')]")).click();
			waitFor();
		 }catch(Exception e){
 			Log.error("Not able to click OK --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	} 
	
//	public static void hoverSubmenu(String object, String data){
//		try{
//			Log.info("Hover on Webelement "+ object);
//			 Consumer < By > hover = (By by) - > {
//			        action.moveToElement(driver.findElement(by))
//			              .perform();
//			    };
//
//			    @Test
//			    public void hoverTest() {
//			        driver.get("https://www.bootply.com/render/6FC76YQ4Nh");
//
//			        hover.accept(By.linkText("Dropdown"));
//			        hover.accept(By.linkText("Dropdown Link 5"));
//			        hover.accept(By.linkText("Dropdown Submenu Link 5.4"));
//			        hover.accept(By.linkText("Dropdown Submenu Link 5.4.1"));
//			    }
//			
//		 }catch(Exception e){
// 			Log.error("Not able to hover --- " + e.getMessage());
// 			DriverScript.bResult = false;
//         }
//	} 
//	
	
//	TODO duplicate
	public static void storeValue1(String object, String data){
		String code = data;
		Log.info("storedValue value : " + code);
		if (code.equalsIgnoreCase("WONUM")) {
			DriverScript.WONUM = driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getAttribute("value");
			DriverScript.storedValue = DriverScript.WONUM;
		} else if (code.equalsIgnoreCase("ASSETNUM")) {
			DriverScript.ASSETNUM = driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getAttribute("value");
			DriverScript.storedValue = DriverScript.ASSETNUM;
		} else if (code.equalsIgnoreCase("PRNUM")) {
			DriverScript.PRNUM = driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getAttribute("value");
			DriverScript.storedValue = DriverScript.PRNUM;
		} else if (code.equalsIgnoreCase("PONUM")) {
			DriverScript.PONUM = driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getAttribute("value");
			DriverScript.storedValue = DriverScript.PONUM;
		} else if (code.equalsIgnoreCase("Storeroom")) {
			DriverScript.Storeroom = driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getAttribute("value");
			DriverScript.storedValue = DriverScript.Storeroom;
		} else if (code.equalsIgnoreCase("PMNUM")) {
			DriverScript.PMNUM = driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getAttribute("value");
			DriverScript.storedValue = DriverScript.PMNUM;
		} else if (code.equalsIgnoreCase("RouteNum")) {
			DriverScript.RouteNum = driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getAttribute("value");
			DriverScript.storedValue = DriverScript.RouteNum;
		} else if (code.equalsIgnoreCase("JPNum")) {
			DriverScript.JPNum = driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getAttribute("value");
			DriverScript.storedValue = DriverScript.JPNum;
		} else if (code.equalsIgnoreCase("Template")) {
			DriverScript.Template = driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getAttribute("value");
			DriverScript.storedValue = DriverScript.Template;
		} else if (code.equalsIgnoreCase("SRNUM")) {
			DriverScript.SRNUM = driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getAttribute("value");
			DriverScript.storedValue = DriverScript.SRNUM;
		} else if (code.equalsIgnoreCase("LOCATION")) {
			DriverScript.LOCATION = driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getAttribute("value");
			DriverScript.storedValue = DriverScript.LOCATION;
		} else if (code.equalsIgnoreCase("POINT")) {
			DriverScript.POINT = driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getAttribute("value");
			DriverScript.storedValue = DriverScript.POINT;
		} else if (code.equalsIgnoreCase("WFApprover")) {
//			DriverScript.WFApprover = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			DriverScript.WFApprover = driver.findElement(By.xpath("//table[contains(@summary,'Workflow Assignments')]/tbody/tr[4]/td[2]")).getText();
			DriverScript.storedValue = DriverScript.WFApprover;
		}
		Log.info("DriverScript.storedValue : " + DriverScript.storedValue);
	}
	
	public static void storeValue(String object, String data){
		String code = data;
		Log.info("storedValue value : " + code);
		if (code.equalsIgnoreCase("WONUM")) {
			DriverScript.WONUM = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			DriverScript.storedValue = DriverScript.WONUM;
		} else if (code.equalsIgnoreCase("ASSETNUM")) {
			DriverScript.ASSETNUM = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			DriverScript.storedValue = DriverScript.ASSETNUM;
		} else if (code.equalsIgnoreCase("PRNUM")) {
			DriverScript.PRNUM = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			DriverScript.storedValue = DriverScript.PRNUM;
		} else if (code.equalsIgnoreCase("PONUM")) {
			DriverScript.PONUM = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			DriverScript.storedValue = DriverScript.PONUM;
		} else if (code.equalsIgnoreCase("Storeroom")) {
			DriverScript.Storeroom = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			DriverScript.storedValue = DriverScript.Storeroom;
		} else if (code.equalsIgnoreCase("PMNUM")) {
			DriverScript.PMNUM = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			DriverScript.storedValue = DriverScript.PMNUM;
		} else if (code.equalsIgnoreCase("RouteNum")) {
			DriverScript.RouteNum = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			DriverScript.storedValue = DriverScript.RouteNum;
		} else if (code.equalsIgnoreCase("JPNum")) {
			DriverScript.JPNum = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			DriverScript.storedValue = DriverScript.JPNum;
		} else if (code.equalsIgnoreCase("Template")) {
			DriverScript.Template = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			DriverScript.storedValue = DriverScript.Template;
		} else if (code.equalsIgnoreCase("SRNUM")) {
			DriverScript.SRNUM = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			DriverScript.storedValue = DriverScript.SRNUM;
		} else if (code.equalsIgnoreCase("LOCATION")) {
			DriverScript.LOCATION = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			DriverScript.storedValue = DriverScript.LOCATION;
		} else if (code.equalsIgnoreCase("POINT")) {
			DriverScript.POINT = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			DriverScript.storedValue = DriverScript.POINT;
		} else if (code.equalsIgnoreCase("WFApprover")) {
//			DriverScript.WFApprover = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			DriverScript.WFApprover = driver.findElement(By.xpath("//table[contains(@summary,'Workflow Assignments')]/tbody/tr[4]/td[2]")).getText();
			DriverScript.storedValue = DriverScript.WFApprover;
		}
		Log.info("DriverScript.storedValue : " + DriverScript.storedValue);
	}
	
	public static void retrieveValue(String object, String data){
		String code = data;
		
		if (code.equalsIgnoreCase("WONUM")) {
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(DriverScript.WONUM);
		} else if (code.equalsIgnoreCase("ASSETNUM")) {
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(DriverScript.ASSETNUM);
		} else if (code.equalsIgnoreCase("PRNUM")) {
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(DriverScript.PRNUM);
		} else if (code.equalsIgnoreCase("PONUM")) {
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(DriverScript.PONUM);
		} else if (code.equalsIgnoreCase("Storeroom")) {
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(DriverScript.Storeroom);
		} else if (code.equalsIgnoreCase("RouteNum")) {
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(DriverScript.RouteNum);
		} else if (code.equalsIgnoreCase("PMNUM")) {
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(DriverScript.PMNUM);
		} else if (code.equalsIgnoreCase("JPNum")) {
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(DriverScript.JPNum);
		} else if (code.equalsIgnoreCase("Template")) {
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(DriverScript.Template);
		} else if (code.equalsIgnoreCase("SRNUM")) {
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(DriverScript.SRNUM);
		} else if (code.equalsIgnoreCase("LOCATION")) {
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(DriverScript.LOCATION);
		} else if (code.equalsIgnoreCase("POINT")) {
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(DriverScript.POINT);
		} else if (code.equalsIgnoreCase("WFApprover")) {
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(DriverScript.WFApprover);
		} 
		
		Log.info("DriverScript.retrievedValue : " +DriverScript.storedValue);
	}
	
//	TODO duplicate
	public static void retrieveValue1(String object, String data){
		String code = data;
		
		if (code.equalsIgnoreCase("WONUM")) {
			driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(DriverScript.WONUM);
		} else if (code.equalsIgnoreCase("ASSETNUM")) {
			driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(DriverScript.ASSETNUM);
		} else if (code.equalsIgnoreCase("PRNUM")) {
			driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(DriverScript.PRNUM);
		} else if (code.equalsIgnoreCase("PONUM")) {
			driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(DriverScript.PONUM);
		} else if (code.equalsIgnoreCase("Storeroom")) {
			driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(DriverScript.Storeroom);
		} else if (code.equalsIgnoreCase("RouteNum")) {
			driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(DriverScript.RouteNum);
		} else if (code.equalsIgnoreCase("PMNUM")) {
			driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(DriverScript.PMNUM);
		} else if (code.equalsIgnoreCase("JPNum")) {
			driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(DriverScript.JPNum);
		} else if (code.equalsIgnoreCase("Template")) {
			driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(DriverScript.Template);
		} else if (code.equalsIgnoreCase("SRNUM")) {
			driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(DriverScript.SRNUM);
		} else if (code.equalsIgnoreCase("LOCATION")) {
			driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(DriverScript.LOCATION);
		} else if (code.equalsIgnoreCase("POINT")) {
			driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(DriverScript.POINT);
		} else if (code.equalsIgnoreCase("WFApprover")) {
			driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(DriverScript.WFApprover);
		} 
		
		Log.info("DriverScript.retrievedValue : " +DriverScript.storedValue);
	}
	
//	TODO duplicate
	public static void assertValue1(String object, String data){
		try{
			String ele = null;
			if (driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getText() != null &&
					!driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getText().equals("") && 
					!driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getText().isEmpty()) {
				ele = driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getText();
			} else {
				ele = driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getAttribute("value");
			}
			
			ele = StringUtils.remove(ele, ",");	
			Log.info("get assertValue: object="+ele.toUpperCase()+" data:"+data.toUpperCase());
			waitFor();
			Assert.assertTrue(ele.toUpperCase().trim().equals(data.toUpperCase()), "Assertion failed.");
		 } catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
		 	DriverScript.bResult = false;
	     } catch(Exception e){
	 		Log.error("Assertion failed --- " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, e.getMessage());
	 		DriverScript.bResult = false;
	     } 
	}
	
	public static void assertValue(String object, String data){
		try{
			String ele = null;
			if (driver.findElement(By.xpath(OR.getProperty(object))).getText() != null &&
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().equals("") && 
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().isEmpty()) {
				ele = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			} else {
				ele = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			}
			
			ele = StringUtils.remove(ele, ",");	
			data = StringUtils.remove(data, ",");	
			Log.info("get assertValue: object="+ele.toUpperCase()+" data:"+data.toUpperCase());
			
			waitFor();
			Assert.assertTrue(ele.toUpperCase().trim().equals(data.toUpperCase().trim()), "Assertion failed.");
		 } catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
		 	DriverScript.bResult = false;
	     } catch(Exception e){
	 		Log.error("Assertion failed --- " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, e.getMessage());
	 		DriverScript.bResult = false;
	     } 
	}
	
	public static void assertValue2(String object, String data){
		try{
			String ele = null;
			if (driver.findElement(By.xpath(OR.getProperty(object))).getText() != null &&
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().equals("") && 
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().isEmpty()) {
				ele = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			} else {
				ele = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			}
			
			ele = StringUtils.remove(ele, ",");	
			List<String> txtToCheck1 =  new ArrayList<String>();
			txtToCheck1 = Arrays.asList(ele.toUpperCase());
			List<String> txtToCheck =  new ArrayList<String>();
			txtToCheck = Arrays.asList(data.trim().toUpperCase().split("[\\s]*,[\\s]*"));
//			Log.info("get assertValue: object="+ele.toUpperCase()+" data:"+data.toUpperCase());
			Assert.assertTrue(CollectionUtils.containsAny(txtToCheck1, txtToCheck), "Assertion failed.");
		 } catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
		 	DriverScript.bResult = false;
	     } catch(Exception e){
	 		Log.error("Assertion failed --- " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, e.getMessage());
	 		DriverScript.bResult = false;
	     } 
	}
	
	public static void assertCode(String object, String data){
		try{
			String ele = null;
			if (driver.findElement(By.xpath(OR.getProperty(object))).getText() != null &&
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().equals("") && 
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().isEmpty()) {
				ele = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			} else {
				ele = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			}
			
			ele = StringUtils.remove(ele, ",");	
			data = getStoredValue(data);
			Log.info("get assertValue: object="+ele.toUpperCase()+" data:"+data.toUpperCase());
			waitFor();
			Assert.assertTrue(ele.toUpperCase().trim().equals(data.toUpperCase()), "Assertion failed.");
		 } catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
		 	DriverScript.bResult = false;
	     } catch(Exception e){
	 		Log.error("Assertion failed --- " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, e.getMessage());
	 		DriverScript.bResult = false;
	     } 
	}
	
	static String getStoredValue(String data) {
			String code = null;
			switch (data.toUpperCase()) {
            	case "WONUM":
            		code = DriverScript.WONUM;
            		break;
            	case "ASSETNUM":
            		code = DriverScript.ASSETNUM;
            		break;
	            case "PRNUM":
	            	code = DriverScript.PRNUM;
	            	break;
	            case "PONUM":
	            	code = DriverScript.PONUM;
	            	break;
	            case "STOREROOM":
	            	code = DriverScript.Storeroom;
	            	break;
	            case "PMNUM":
	            	code = DriverScript.PMNUM;
	            	break;
	            case "ROUTENUM":
	            	code = DriverScript.RouteNum;
	            	break;
	            case "JPNUM":
	            	code = DriverScript.JPNum;
	            	break;
	            case "TEMPLATE":
	            	code = DriverScript.Template;
	            	break;	 
	            case "SRNUM":
	            	code = DriverScript.SRNUM;
	            	break;
	            case "LOCATION":
	            	code = DriverScript.LOCATION;
	            	break;
	            case "POINT":
	            	code = DriverScript.POINT;
	            	break;
	            case "WFAPPROVER":
	            	code = DriverScript.WFApprover;
	            	break;	 	 
			    }
			return code;
	}
	
	
	public static void fieldValuesEqual(String object, String data){
		try{
			String field1 = String.valueOf(driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			String field2 = String.valueOf(driver.findElement(By.xpath(OR.getProperty(data))).getAttribute("value"));
			Assert.assertEquals(field1.trim().toLowerCase(), field2.trim().toLowerCase());
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false;
		 }catch(Exception e){
 			Log.error("Fields not Equal --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	}
	
	public static void isNull(String object, String data){
		try{
			String val = String.valueOf(driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value").equals(""));
			Assert.assertEquals(val.toLowerCase(), data.toLowerCase());
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false; 
		}catch(Exception e){
 			Log.error("Object is null --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	}
	
	public static void isEmpty(String object, String data){
		try{
			Boolean val = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("displayrows").equals("0");
			Assert.assertEquals(String.valueOf(val).toLowerCase(), data.toLowerCase());
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false; 
		}catch(Exception e){
 			Log.error("Object is empty --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	}

	public static void isReadOnly(String object, String data){
		 try{
			String val = "false"; 
    		if (driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("readonly") != null) {
				val = "true";
			}
			Assert.assertEquals(val, data.toLowerCase());
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false;
		 }catch(Exception e){
 			Log.error("Object is readonly --- " +e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	}
	
	public static void isDisabled(String object, String data){
		 try{
			String val = "false"; 
			waitForElementDisplayed(object);
	    	if (driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("active") != null) {
			    val = "true";
			}
			Assert.assertEquals(val, data.toLowerCase()); 
			Thread.sleep(2000);
    	}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false; 
		}catch(Exception e){
			Log.error("Object is disabled --- " +e.getMessage());
			extentTest.log(LogStatus.ERROR, e.getMessage());
			DriverScript.bResult = false;
        }
	}
		
	public static void isRequired(String object, String data){
		try{
			String val = "false"; 
    		if (driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("aria-required") != null) {
				val = "true";
			}
			Assert.assertEquals(val, data.toLowerCase());
   	    }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false;	
		}catch(Exception e){
 			Log.error("Object is required --- " +e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
        }
	}
		
	public static void isChecked(String object, String data){
		try{
			String val = String.valueOf(driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("aria-checked").equals("true"));
			Assert.assertEquals(val.toLowerCase(), data.toLowerCase());
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false; 
		}catch(Exception e){
 			Log.error("Object is checked--- " +e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
        }
	}
	
	boolean isChecked(String object){
		boolean val = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("aria-checked").equals("true");
		if (val) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void isTicked(String object, String data){
		try{
			Log.error("Is Ticked ="+ driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked"));
			String val = String.valueOf(driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked").equals("true"));
			Log.error("Is Ticked val ="+ val);
			Assert.assertEquals(val.toLowerCase(), data.toLowerCase());
  	    }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false; 
		}catch(Exception e){
 			Log.error("Object is ticked --- " +e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
        }
	}
	
	public static void isValid(String object, String data){
		try{
			String val = "true"; 
    		if (driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("aria-invalid") != null) {
				val = "false";
			}
			Assert.assertEquals(val, data.toLowerCase());
  	    }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false; 
		}catch(Exception e){
 			Log.error("Object is ticked --- " +e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
        }
	}
		
	public static void rowsDisplayed(String object, String data){
		try{
			int val = Integer.valueOf(driver.findElement(By.xpath("//table[contains(@id,'_tbod-tbd')]")).getAttribute("displayrows"));
			Assert.assertEquals(val, Integer.valueOf(data).intValue());
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false;	
		 }catch(Exception e){
 			Log.error("rowsDisplayed ---" + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	}
	
	public static void rowsDisplayed1(String object, String data){
		try{
			int val = Integer.valueOf(driver.findElement(By.xpath("//table[contains(@summary,'"+object+"')]")).getAttribute("displayrows"));
			Assert.assertEquals(val, Integer.valueOf(data).intValue());
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false;	
		 }catch(Exception e){
 			Log.error("rowsDisplayed ---" + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	}
	
	public static void tableNotEmpty(String object, String data){
		try{
			int val = Integer.valueOf(driver.findElement(By.xpath("//table[contains(@summary,'"+object+"')]")).getAttribute("displayrows"));
			Assert.assertEquals(Boolean.valueOf(val > 0).toString().toLowerCase(), data.toLowerCase());
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false;	
		 }catch(Exception e){
 			Log.error("tableNotEmpty ---" + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	}
	
	public static void rowsPerPage(String object, String data){
		try{
			int val = Integer.valueOf(driver.findElement(By.xpath("//table[@id='lookup_page1_tbod-tbd']")).getAttribute("rowsperpage"));
			Assert.assertEquals(val, Integer.valueOf(data).intValue());
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false;	
		 }catch(Exception e){
 			Log.error("Total rows per page ---" + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	}
	
	public static void rowsPerPage1(String object, String data){
		try{
			int val = Integer.valueOf(driver.findElement(By.xpath("//table[contains(@summary,'"+object+"')]")).getAttribute("rowsperpage"));
			Assert.assertEquals(val, Integer.valueOf(data).intValue());
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false;	
		 }catch(Exception e){
 			Log.error("Total rows per page ---" + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	}
	
	public static void totalRowCount(String object, String data){
		try{
			List<WebElement> ddOpts = driver.findElements(By.xpath("//table[@id='lookup_page1_tbod-tbd']/tbody/tr[contains(@id='lookup_page1_tbod_tdrow-tr')]"));
			int rowCount = ddOpts.size();
			Assert.assertEquals(rowCount, Integer.valueOf(data).intValue());
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false;
		 }catch(Exception e){
 			Log.error("Total row count ---" +e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	}
	

	public static void lineCostNotZero(String object, String data){
		try{
			Assert.assertFalse(driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value").equals("0.00"), "Zero line cost.");
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false;
		 }catch(Exception e){
 			Log.error("Zero line cost --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	}
	
	public static void waitElementExists(String object){
		try{
			Log.info("Checks Webelement exists: "+ object);
			WebDriverWait wait = new WebDriverWait(driver, 3);
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath(OR.getProperty(object)))));
		 }catch(Exception e){
 			Log.error("Element does not exists --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	}
	
	public static void explicitlyWait(String object, int sec){
		try{
			Log.info("Checks Webelement exists: "+ object);
			(new WebDriverWait(driver, sec)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(object))));			
		}catch(Exception e){
			Log.error("Element does not exists --- " + e.getMessage());
			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
		}
	}
	
	public static void checkSaveDone(String object, String data){
		try{
			Log.info("Checks saving is done: "+ object);
			WebDriverWait wait = new WebDriverWait(driver, 3);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='ROUTEWF__-tbb_image']")));
			Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value").equals(data));
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false;
		 }catch(Exception e){
 			Log.error("Save not done --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	}
	
	
//to verify if element exists; for negative testing
	static boolean existsElement(String id) {
	    try {
//	        driver.findElement(By.xpath(OR.getProperty(id)));
	    	driver.findElement(By.xpath("//label[text()='"+id+"']"));
	    } catch (NoSuchElementException e) {
	        return false;
	    }
	    return true;
	}
	
	static boolean existsElement(By xpath) {
	    try {
	    	driver.findElement(xpath);
	    } catch (NoSuchElementException e) {
	        return false;
	    }
	    return true;
	}
	
	public static void elementExists(String object, String data) { 
	    try {
	    	Log.info("Checks if element exists: "+ object);
	    	if (data.toLowerCase().equals("true")) {
			    Assert.assertTrue(existsElement(By.xpath(OR.getProperty(object))));
			} else {
				Assert.assertFalse(existsElement(By.xpath(OR.getProperty(object))));
			}
	    }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false;
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
	    }
	}
	
	public static void elementExist(String object, String data) { 
	    try {
	    	Log.info("Checks if element exists: "+ object);
	    	Assert.assertEquals(String.valueOf(existsElement(object)), data.toLowerCase());
	    }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false;
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
	    }
	}
	
	public static LocalDate getDate() {
		LocalDate today = LocalDate.now();
		return today;
	}
	
	/**
	 * This method verifies if an element is being displayed and returns true/false
	 */
	static boolean elementDisplayed(By xpath) {
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
			extentTest.log(LogStatus.ERROR, e.getMessage());
			return false;
		}
		System.out.println("Element Not Found.");	
		return false;
	}
	
	/**
	 * This method waits for an element to be displayed before clicking on it
	 */
	public static boolean waitForElementDisplayed(String object, String data) throws Exception{
		int i = 0;
		while (!elementDisplayed(By.xpath(OR.getProperty(object)))&&(i <= 30)){
			System.out.println("Searching for element..."+i);
			Thread.sleep(500);
			i++;
		}
		
		if (i > 30){
			System.out.println("FAIL: Element not located in a reasonable timeframe");
			extentTest.log(LogStatus.ERROR, "Element not located in a reasonable timeframe");
			throw new Exception("FAIL: Element not located in a reasonable timeframe");
		//	return false;
		} else{
			Thread.sleep(2000);
			return true;
		}
	}
	
	static boolean waitForElementDisplayed(String data) throws Exception{
		int i = 0;
		Log.info("Waiting for element to display");
		while (!elementDisplayed(By.xpath(OR.getProperty(data)))&&(i <= 120)){
			System.out.println("Searching for element..."+i);
			Thread.sleep(1000);
			i++;
		}
		
		
		if (i > 120){
			System.out.println("FAIL: Element not located in a reasonable timeframe");
			extentTest.log(LogStatus.ERROR, "Element not located in a reasonable timeframe");
			throw new Exception("FAIL: Element not located in a reasonable timeframe");
		//	return false;
		} else{
			Thread.sleep(2000);
			return true;
		}
	}
	
	static boolean waitForListViewDisplayed(String object, String data) throws Exception{
		int i = 0;
		Log.info("Waiting for element to display");
		while (!elementDisplayed(By.xpath("//table[contains(@summary,"+data+")]"))&&(i <= 120)){
			System.out.println("Searching for element..."+i);
			Thread.sleep(1000);
			i++;
		}
		
		
		if (i > 120){
			System.out.println("FAIL: Element not located in a reasonable timeframe");
			extentTest.log(LogStatus.ERROR, "Element not located in a reasonable timeframe");
			throw new Exception("FAIL: Element not located in a reasonable timeframe");
		//	return false;
		} else{
			Thread.sleep(2000);
			return true;
		}
	}
	
	public static void tableExists(String object, String data) throws Exception{
		try {
	    	Log.info("Checks table exists: "+ object);
	    	Assert.assertTrue(elementDisplayed(By.xpath("//table[contains(@summary,'"+object+"')]")));
	    }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false;
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
	    }
	}
	
    public static void elementClickable(String object){
		try{
			Log.info("Checks Webelement is clickable: "+ object);
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
		 }catch(Exception e){
 			Log.error("Element does not exists --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	}
    
    public static void waitConfirmation(String object, String data){
		try{
			Log.info("waitConfirmation: "+ object);
			WebDriverWait wait = new WebDriverWait(driver, 3000);
	        wait.until(ExpectedConditions.alertIsPresent());
//	        driver.switchTo().alert().accept();
		 }catch(Exception e){
 			Log.error("Element does not exists --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
         }
	}
		
	public static void input(String object, String data){
		try{
			if (object == "txtbx_ScheduledStart") {
				LocalDate ngayon = getDate();
			    //add 2 week to the current date
			    LocalDate next2Week = ngayon.plus(2, ChronoUnit.WEEKS);
				data = next2Week.toString();
			}
			
			Log.info("Entering the text in " + object);
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
		 }catch(Exception e){
			Log.error("Not able to Enter value --- " + e.getMessage());
			extentTest.log(LogStatus.ERROR, e.getMessage());
			DriverScript.bResult = false;
		 }
	}
	
	public static void clear(String object, String data){
		try{
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			waitFor();
		}catch(Exception e){
			 Log.error(e.getMessage());
			 extentTest.log(LogStatus.ERROR, e.getMessage());
			 DriverScript.bResult = false;
		}
	}
	
	public static void snooze(String object, String sec) throws Exception{
		try{
			Log.info("Snooze...");
			Thread.sleep(Integer.parseInt(sec));
		 }catch(Exception e){
			 Log.error(e.getMessage());
			 extentTest.log(LogStatus.ERROR, e.getMessage());
			 DriverScript.bResult = false;
         }
	}

	public static void waitFor() throws Exception{
		try{
			Log.info("Wait...");
			Thread.sleep(3000);
		}catch(Exception e){
			 Log.error("Not able to Wait --- " + e.getMessage());
			 extentTest.log(LogStatus.ERROR, e.getMessage());
			 DriverScript.bResult = false;
        }
	}
	
	public static void waitFor5() throws Exception{
		try{
  			 Thread.sleep(5000);
	    }catch(Exception e){
			 Log.error("Not able to Wait --- " + e.getMessage());
			 extentTest.log(LogStatus.ERROR, e.getMessage());
			 DriverScript.bResult = false;
       	}
	}
	
	public static void waitFor5(String object, String data) throws Exception{
		try{
  			 Thread.sleep(1000);
	    }catch(Exception e){
			 Log.error("Not able to Wait --- " + e.getMessage());
			 extentTest.log(LogStatus.ERROR, e.getMessage());
			 DriverScript.bResult = false;
       	}
	}
	
	public static void closeBrowser(String object, String data){
		try{
			Log.info("Closing the browser");
			driver.quit();
		}catch(Exception e){
			 Log.error("Not able to Close the Browser --- " + e.getMessage());
			 extentTest.log(LogStatus.ERROR, e.getMessage());
			 DriverScript.bResult = false;
       	}
	}
	
	public static void takeScreenshot(String object, String data){
		try {
			Log.info("Taking Screenshot..");
			Utils.takeScreenshot(driver, data);
		} catch (Exception e) {
			Log.error("Not able to take screenshot --- " + e.getMessage());
			extentTest.log(LogStatus.ERROR, e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void switchWindow(String object, String data){
		try {
			String mainWindow = driver.getWindowHandle();
		    for (String handle : driver.getWindowHandles()) {
		        if (!handle.equals(mainWindow)) {
		            driver.switchTo().window(handle);
		            break;
		        }
		    }
		} catch (Exception e) {
			Log.error("switchWindow --- " + e.getMessage());
			extentTest.log(LogStatus.ERROR, e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void verifyAlert(String object, String data){
		try{
//			Thread.sleep(2000);
			waitForElementDisplayed(object);
			if(isAlertPresent()){
	            driver.switchTo().alert();
	            driver.switchTo().alert().accept();
	            driver.switchTo().defaultContent();
	            Log.info("Alert: " + driver.switchTo().defaultContent());
	        }
			Log.info("get assertValue object.." +driver.findElement(By.xpath(OR.getProperty(object))).getText());
			Log.info("get assertValue data.." +data);
			Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty(object))).getText().contains(data), "Assertion failed.");
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false;	
		}catch(Exception e){
	 		Log.error("Verify Alert: " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, e.getMessage());
	 		DriverScript.bResult = false;
	    }
	}
	
	public static boolean isAlertPresent(){
        try{
            driver.switchTo().alert();
            return true;
        }
        catch(Exception e){
            return false;
        }
	}
	
    public static void clickOK(String object, String data){
        try{
        	driver.switchTo().alert();
            driver.switchTo().alert().accept();
        }
        catch(Exception e){
//TODO
        }   
      }
    
	public static void isApprover(String object, String data){
		try{
			Log.info("isApprover " + object);
			int rowCount = (driver.findElements(By.xpath("//table[contains(@id,'People') and contains(@summary,'People')]/tbody/tr")).size());
			Log.info("rowCount " + rowCount);
			for (int i=3;i<=rowCount;i+=2){
				String sValue = null;
//				sValue = driver.findElement(By.xpath(".//*[@id='portletbody_8914']/table/tbody/tr["+i+"]/td[3]")).getText();
				sValue = driver.findElement(By.xpath("//table[contains(@summary,'Inbox / Assignments')]/tbody/tr["+i+"]/td[4]")).getText();
				Log.info("value " + sValue);
				Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty(object))).getText().equalsIgnoreCase(sValue), "Assertion failed.");
				break;
			}
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false;	
		}catch(Exception e){
			 Log.error(e.getMessage());
			 extentTest.log(LogStatus.ERROR, e.getMessage());
			 DriverScript.bResult = false;
		}
	}
	
	public static void openWFAssignment(String object, String data){
		try{
			String val = DriverScript.storedValue;
			Log.info("Stored value = " + val);
			Log.info("openWFAssignment = " + object);
//			waitForElementDisplayed();
			int rowCount = (driver.findElements(By.xpath("//table[contains(@summary,'Inbox / Assignments')]/tbody/tr")).size());
			boolean foundValue = false;
			
			for (int i=3;i<=rowCount;i+=2){
				String sValue = null;
				sValue = driver.findElement(By.xpath("//table[contains(@summary,'Inbox / Assignments')]/tbody/tr["+i+"]/td[3]")).getText();
//				Log.info("value " + sValue);
//				Log.info("data " + val);
				if(sValue.contains(val)){
					driver.findElement(By.xpath("//table[contains(@summary,'Inbox / Assignments')]/tbody/tr["+i+"]/td[9]/a/img")).click();
					Log.info("clicking image for <break> " + sValue);
					waitFor();
					foundValue = true;
				    break;
				} else {
					By nextPage = By.xpath("//a[text()='Next Page']");
					while (!foundValue && elementDisplayed(nextPage)){
						driver.findElement(nextPage).click();
						sValue = driver.findElement(By.xpath("//table[contains(@summary,'Inbox / Assignments')]/tbody/tr["+i+"]/td[3]")).getText();
//						Log.info("value " + sValue);
						if(sValue.endsWith(val)){
							driver.findElement(By.xpath("//table[contains(@summary,'Inbox / Assignments')]/tbody/tr["+i+"]/td[9]/a/img")).click();
							Log.info("clicking image for <break> " + sValue);
							waitFor();
							foundValue = true;
						    break;
						}
					}
				}	
			}
			if (foundValue=false){
				Log.info(val + " not found in approver's Inbox");
			}
			waitForElementDisplayed("btn_OK");
		}catch(Exception e){
			 Log.error("WFAssignment not found --- " + e.getMessage());
			 extentTest.log(LogStatus.ERROR, e.getMessage());
			 DriverScript.bResult = false;
		}
	}
	
 
      public static void expectedRows(String object, String data){
		     try{
		    	 String dir = null;
		    	 switch (object.toLowerCase()) {
		            case "children":
		            	 dir = "Children of Work Order";
		            	 break;
		            case "tasks":
		            	 dir = "Tasks for Work Order";
		            	 break;
		            case "labour":
		            	 dir = "Labour";
		            	 break;
		            case "materials":
		            	 dir = "Materials";
		            	 break;
		            case "services":
		            	 dir = "Services";
		            	 break;
		            case "tools":
		            	 dir = "Tools";
		            	 break;
		        }
		    		    	 
			    List<WebElement> ddOpts = driver.findElements(By.xpath("//table[contains(@summary,'"+dir+"')]//tr[contains(@class,'tablerow')]"));
//			    
//			    Log.info("ddOpts.toString() =  " + ddOpts.toString());
//			    Log.info("object =  " + object.toLowerCase());
//			    Log.info("is empty row? =  " + ddOpts.isEmpty());
//				Log.info("dir =  " + dir);
				int rowCount = ddOpts.size();
				Assert.assertEquals(String.valueOf(rowCount), data.trim());
		     }catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
				DriverScript.bResult = false;
			 }catch(Exception e){
				Log.error("Table does not exists --- " +e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
			 }
	  }
	 
	  public static void checkStatusExists(String object, String data){
		  try{
			    List<String> statusToCheck =  new ArrayList<String>();
			    statusToCheck = Arrays.asList(object.trim().toUpperCase().split("[\\s]*,[\\s]*"));
				Log.info("check status exists: " + object);
				List<WebElement> ddOpts = driver.findElements(By.xpath("//ul[@id='menu0']/li[*]"));
				
				Log.info("is empty row? =  " + ddOpts.isEmpty());
				int rowCount = ddOpts.size();
				Log.info("rowCount " + rowCount);
				List<String> statusList = new ArrayList<String>();
				String strValue = null;
				for(int i=1;i<=rowCount;i++)
				{
				    strValue = driver.findElement(By.xpath("//ul[@id='menu0']//child::li["+i+"]")).getText();
				    statusList.add(strValue.toUpperCase());
				}
				Collections.sort(statusList);
				Collections.sort(statusToCheck);
				Log.info("statusList = " + statusList.toString());
				Log.info("statusToCheck = " + statusToCheck.toString());
//				Assert.assertTrue(CollectionUtils.isEqualCollection(statusList, statusToCheck));
				if (data.toLowerCase().equals("true")) {
				    Assert.assertTrue(CollectionUtils.isEqualCollection(statusList, statusToCheck));
				} else {
					Assert.assertFalse(CollectionUtils.containsAny(statusList, statusToCheck));
				}
		  }catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
				DriverScript.bResult = false;
		  }catch(Exception e){
				Log.error("Check status exists --- " + e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
		  }
	  }
	  
	  public static void checkValueExists(String object, String data){
		  try{
			    List<String> valueToCheck =  new ArrayList<String>();
			    valueToCheck = Arrays.asList(object.trim().toUpperCase().split("[\\s]*,[\\s]*"));
				Log.info("check value exists: " + object);
				
				List<WebElement> ddOpts = null;
				String strPath = null;
				//main page
				if (driver.findElements(By.xpath("//table[@id='lookup_page1_tbod-tbd']//td[contains(@id,'lookup_page1_tdrow_[C:0]-c')]")).size() > 0) {
					//main page
					ddOpts = driver.findElements(By.xpath("//table[@id='lookup_page1_tbod-tbd']//td[contains(@id,'lookup_page1_tdrow_[C:0]-c')]"));
					strPath = "//table[@id='lookup_page1_tbod-tbd']//td[contains(@id,'lookup_page1_tdrow_[C:0]-c[R:";
					Log.info("is empty row 1? =  " + ddOpts.isEmpty());
				} else {
					//search page
					ddOpts = driver.findElements(By.xpath("//table[@id='lookup_page2_tbod-tbd']//td[contains(@id,'lookup_page2_tdrow_[C:1]-c')]"));
					strPath = "//table[@id='lookup_page2_tbod-tbd']//td[contains(@id,'lookup_page2_tdrow_[C:1]-c[R:";
					Log.info("is empty row 2? =  " + ddOpts.isEmpty());
				}

				Log.info("is empty row? =  " + ddOpts.isEmpty());
				int rowCount = ddOpts.size();
				Log.info("rowCount " + rowCount);
				List<String> valueList = new ArrayList<String>();
				String strValue = null;
				for(int i=0;i<=rowCount-1;i++)
				{
				    strValue = driver.findElement(By.xpath(strPath+i+"]')]")).getText();
				    valueList.add(strValue.toUpperCase());
				}
				Collections.sort(valueList);
				Collections.sort(valueToCheck);
				Log.info("valueList = " + valueList.toString());
				Log.info("valueToCheck = " + valueToCheck.toString());
//				Assert.assertTrue(CollectionUtils.isEqualCollection(valueList, valueToCheck));
//				Assert.assertTrue(valueList.containsAll(valueToCheck) && valueToCheck.containsAll(valueList));
				if (data.toLowerCase().equals("true")) {
				    Assert.assertTrue(CollectionUtils.isEqualCollection(valueList, valueToCheck));
				} else {
					Assert.assertFalse(CollectionUtils.containsAny(valueList, valueToCheck));
				}
   	     }catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
				DriverScript.bResult = false;
		 }catch(Exception e){
			    Log.error("Check value exists --- " + e.getMessage());
			    extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
		 }
	  }
	  
	  public static void checkValueExistsInSelectValue(String object, String data){
		  try{
			    List<String> valueToCheck =  new ArrayList<String>();
			    valueToCheck = Arrays.asList(object.trim().toUpperCase().split("[\\s]*,[\\s]*"));
				Log.info("check value exists: " + object);
				
				List<WebElement> ddOpts = null;
				String strPath = null;
				//main page
//				if (driver.findElements(By.xpath("//table[@id='lookup_page1_tbod-tbd']//td[contains(@id,'lookup_page1_tdrow_[C:0]-c')]")).size() > 0) {
//					//main page
//					ddOpts = driver.findElements(By.xpath("//table[@id='lookup_page1_tbod-tbd']//td[contains(@id,'lookup_page1_tdrow_[C:0]-c')]"));
//					strPath = "//table[@id='lookup_page1_tbod-tbd']//td[contains(@id,'lookup_page1_tdrow_[C:0]-c[R:";
//					Log.info("is empty row 1? =  " + ddOpts.isEmpty());
//				} else {
//					//search page
//					ddOpts = driver.findElements(By.xpath("//table[@id='lookup_page2_tbod-tbd']//td[contains(@id,'lookup_page2_tdrow_[C:1]-c')]"));
//					strPath = "//table[@id='lookup_page2_tbod-tbd']//td[contains(@id,'lookup_page2_tdrow_[C:1]-c[R:";
//					Log.info("is empty row 2? =  " + ddOpts.isEmpty());
//				}
				
				ddOpts = driver.findElements(By.xpath("//table[@id='lookup_page_tbod-tbd']//td[contains(@id,'lookup_page1_tdrow_[C:1]-c')]"));
				strPath = "//table[@id='lookup_page1_tbod-tbd']//td[contains(@id,'lookup_page1_tdrow_[C:1]-c[R:";
				Log.info("is empty row? =  " + ddOpts.isEmpty());

				int rowCount = ddOpts.size();
				Log.info("rowCount " + rowCount);
				List<String> valueList = new ArrayList<String>();
				String strValue = null;
				for(int i=0;i<=rowCount-1;i++)
				{
				    strValue = driver.findElement(By.xpath(strPath+i+"]')]")).getText();
				    valueList.add(strValue.toUpperCase());
				}
				Collections.sort(valueList);
				Collections.sort(valueToCheck);
				Log.info("valueList = " + valueList.toString());
				Log.info("valueToCheck = " + valueToCheck.toString());
//				Assert.assertTrue(CollectionUtils.isEqualCollection(valueList, valueToCheck));
//				Assert.assertTrue(valueList.containsAll(valueToCheck) && valueToCheck.containsAll(valueList));
				if (data.toLowerCase().equals("true")) {
				    Assert.assertTrue(CollectionUtils.isEqualCollection(valueList, valueToCheck));
				} else {
					Assert.assertFalse(CollectionUtils.containsAny(valueList, valueToCheck));
				}
   	     }catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
				DriverScript.bResult = false;
		 }catch(Exception e){
			    Log.error("Check value exists --- " + e.getMessage());
			    extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
		 }
	  }
	  
	  public static void checkTopMostDrilldown(String object, String data){
		  try{
			    if (object.equalsIgnoreCase("Assets")) {
//				    Log.info("Parent = " + driver.findElements(By.xpath("//div[@id='asset_tree_treecontainer']//a[2]")).get(0).getText());
				    Assert.assertTrue(driver.findElements(By.xpath("//div[@id='asset_tree_treecontainer']//a[2]")).get(0).getText().toUpperCase().startsWith(DriverScript.ASSETNUM.toUpperCase()));
//				    Assert.assertTrue(data.toUpperCase().equals(driver.findElements(By.xpath("//div[@id='locations_tree_treecontainer']//a[*]")).get(0).getText().toUpperCase()));
			    } else if (object.equalsIgnoreCase("locations")) {
//			        Log.info("Parent = " + driver.findElements(By.xpath("//div[@id='locations_tree_treecontainer']//a[2]")).get(0).getText());
			        Assert.assertTrue(driver.findElements(By.xpath("//div[@id='locations_tree_treecontainer']//a[2]")).get(0).getText().toUpperCase().startsWith(data.toUpperCase()));
//			        Assert.assertTrue(data.toUpperCase().equals(driver.findElements(By.xpath("//div[@id='locations_tree_treecontainer']//a[*]")).get(0).getText().toUpperCase()));
		        } else {
		        	 Log.info("No assertion made.");				        
		        }
   	      }catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
				DriverScript.bResult = false;	    
          }catch(Exception e){
				Log.error("check topmost drilldown --- " +e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
		 }
	  }
	  
	  public static void checkColumnExists(String object, String data){
		  try{
			    List<String> colToCheck =  new ArrayList<String>();
			    colToCheck = Arrays.asList(object.trim().toUpperCase().split("[\\s]*,[\\s]*"));
								List<WebElement> ddOpts = driver.findElements(By.xpath("//tr[@id='lookup_page1_tbod_ttrow-tr']/th[contains(@id,'lookup_page1_ttrow_[C:')]"));
				
				Log.info("is empty column? =  " + ddOpts.isEmpty());
				int colCount = ddOpts.size();
				Log.info("colCount = " + colCount);
				List<String> colList = new ArrayList<String>();
				String strValue = null;
				for(int i=0;i<colCount;i++)
				 {                                                                                   
				    strValue = driver.findElement(By.xpath("//span[@id='lookup_page1_ttrow_[C:"+i+"]_ttitle-lb']")).getText();
				    colList.add(strValue.toUpperCase());
				 }
				Collections.sort(colList);
				Collections.sort(colToCheck);
				Log.info("colList = " + colList.toString());
				Log.info("colToCheck = " + colToCheck.toString());
				if (data.toLowerCase().equals("true")) {
				    Assert.assertTrue(colList.containsAll(colToCheck));
				} else {
					Assert.assertFalse(CollectionUtils.containsAny(colList, colToCheck));
				}
		     }catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
				DriverScript.bResult = false;
			 }catch(Exception e){
				Log.error("Check column exists ---" + e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
			 }
	  }
	  
	  public static void checkColumnExistsListView(String object, String data){
		  try{
			    List<String> colToCheck =  new ArrayList<String>();
			    colToCheck = Arrays.asList(object.trim().toUpperCase().split("[\\s]*,[\\s]*"));
				List<WebElement> ddOpts = driver.findElements(By.xpath("//tr[contains(@id,'_tbod_ttrow-tr')]/th[contains(@id,'_ttrow_[C:')]"));
				
				Log.info("is empty column? =  " + ddOpts.isEmpty());
				int colCount = ddOpts.size();
				Log.info("colCount = " + colCount);
				List<String> colList = new ArrayList<String>();
				String strValue = null;
				By path;
//				for(int i=1;i<colCount-1;i++)
				int i = 1;
				while (i < colCount)
				{  
					path = By.xpath(".//*[contains(@id,'_ttrow_[C:"+i+"]_ttitle-lb')]");
				    if (existsElement(path)) {
					    strValue = driver.findElement(path).getText();
				        colList.add(strValue.toUpperCase());
				        Log.info("col = " + strValue.toUpperCase());
				    } //else {
//				    	Log.info("colCount is empty = " +i);
//				    }
				    i++;
				}
				Collections.sort(colList);
				Collections.sort(colToCheck);
				Log.info("colList = " + colList.toString());
				Log.info("colToCheck = " + colToCheck.toString());
				if (data.toLowerCase().equals("true")) {
				    Assert.assertTrue(colList.containsAll(colToCheck));
				} else {
					Assert.assertFalse(CollectionUtils.containsAny(colList, colToCheck));
				}
		     }catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
				DriverScript.bResult = false;
			 }catch(Exception e){
				Log.error("Check column exists ---" + e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
			 }
	  }
	  
//	  this method is duplicate of checkColumnExistsListView - TODO check for files referencing this method and change before deleting
	  public static void checkColumnExists1(String object, String data){
		  try{
			    List<String> colToCheck =  new ArrayList<String>();
			    colToCheck = Arrays.asList(object.trim().toUpperCase().split("[\\s]*,[\\s]*"));
				List<WebElement> ddOpts = driver.findElements(By.xpath("//tr[contains(@id,'_tbod_ttrow-tr')]/th[contains(@id,'_ttrow_[C:')]"));
				
				Log.info("is empty column? =  " + ddOpts.isEmpty());
				int colCount = ddOpts.size();
				Log.info("colCount = " + colCount);
				List<String> colList = new ArrayList<String>();
				String strValue = null;
				By path;
//				for(int i=1;i<colCount-1;i++)
				int i = 1;
				while (i < colCount)
				{  
					path = By.xpath(".//*[contains(@id,'_ttrow_[C:"+i+"]_ttitle-lb')]");
				    if (existsElement(path)) {
					    strValue = driver.findElement(path).getText();
				        colList.add(strValue.toUpperCase());
				        Log.info("col = " + strValue.toUpperCase());
				    } //else {
//				    	Log.info("colCount is empty = " +i);
//				    }
				    i++;
				}
				Collections.sort(colList);
				Collections.sort(colToCheck);
				Log.info("colList = " + colList.toString());
				Log.info("colToCheck = " + colToCheck.toString());
				if (data.toLowerCase().equals("true")) {
				    Assert.assertTrue(colList.containsAll(colToCheck));
				} else {
					Assert.assertFalse(CollectionUtils.containsAny(colList, colToCheck));
				}
		     }catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
				DriverScript.bResult = false;
			 }catch(Exception e){
				Log.error("Check column exists ---" + e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
			 }
	  }
	  
	  public static void checkTableColumnExists(String object, String data){
		  try{
			    List<String> colToCheck =  new ArrayList<String>();
			    colToCheck = Arrays.asList(object.trim().toUpperCase().split("[\\s]*,[\\s]*"));
				List<WebElement> ddOpts = driver.findElements(By.xpath("//table[contains(@summary,'"+data+"')]/tbody/tr[1]/th[contains(@id,'_ttrow_[C:')]"));

				Log.info("is empty column? =  " + ddOpts.isEmpty());
				int colCount = ddOpts.size();
				Log.info("colCount = " + colCount);
				List<String> colList = new ArrayList<String>();
				String strValue = null;
				By path;
				int i = 1;
				while (i < colCount)
				{  
					path = By.xpath(".//*[contains(@id,'_ttrow_[C:"+i+"]_ttitle-lb')]");
				    if (existsElement(path)) {
					    strValue = driver.findElement(path).getText();
				        colList.add(strValue.toUpperCase());
				        Log.info("col = " + strValue.toUpperCase());
				    } //else {
//				    	Log.info("colCount is empty = " +i);
//				    }
				    i++;
				}
				Collections.sort(colList);
				Collections.sort(colToCheck);
				Log.info("colList = " + colList.toString());
				Log.info("colToCheck = " + colToCheck.toString());
				Assert.assertTrue(colList.containsAll(colToCheck));
		     }catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
				DriverScript.bResult = false;
			 }catch(Exception e){
				Log.error("Check column exists ---" + e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
			 }
	  }
	  
	  public static void fieldExists(String object, String data){
		 try{
   		    Log.info("fieldExists = " + By.xpath("//label[contains(., '"+object+"')]").toString());
			if (data.toLowerCase().equals("true")) {
			    Assert.assertTrue(existsElement(By.xpath("//label[contains(., '"+object+"')]")));
			} else {                                         
				Assert.assertFalse(existsElement(By.xpath("//label[contains(., "+object+")]")));
			}                                                          
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			DriverScript.bResult = false;
		 }catch(Exception e){
			Log.error("Check field exists ---" + e.getMessage());
			extentTest.log(LogStatus.ERROR, e.getMessage());
			DriverScript.bResult = false;
		 }
	  }
//	  //duplicate of input -- testing if possible
//	  public static void input1(String object, String data){
//			try{
//				Log.info("Entering the text in " + object);
//				driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).clear();
//				driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(data);
////				waitFor();
//			 }catch(Exception e){
//				Log.error("Not able to Enter value --- " + e.getMessage());
//				DriverScript.bResult = false;
//			 }
//		}
	  	  
	  public static void copyMessage(String object, String data) {
		  try{
			  driver.findElement(By.id(OR.getProperty(object))).getText();
		  } catch(Exception e){
				 Log.error("copyMessage --- " +e.getMessage());
				 extentTest.log(LogStatus.ERROR, e.getMessage());
				 DriverScript.bResult = false;
		  }
	  }
	  
//	  public static void checkTotalCost(String object, String data) {
//		  try{
////			  compare value of est cost field to the value from view cost total row
//		  } catch(Exception e){
//				 Log.error("Total Cost Discrepancy --- " +e.getMessage());
//				 DriverScript.bResult = false;
//		  }
//	  }
	  
	  public static void scrollDown(String object, String data) throws Exception {
		  Log.info("scrolling down to field..");
		  WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
		  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		  Thread.sleep(5000); 
	  }
	  
	  public static void scrollUp(String object, String data) throws Exception {
		  Log.info("scrolling up to field..");
		  WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
		  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		  Thread.sleep(500); 
	  }
	  
	  public static void routeWF(String object, String data){
		  try{
			  driver.findElement(By.xpath(OR.getProperty("btn_Route"))).click();
			  if (data.toLowerCase().equals("dfa")) {
				  driver.findElement(By.xpath(OR.getProperty("radio_WF_DFA"))).click();
			  } else {
				  driver.findElement(By.xpath(OR.getProperty("radio_WF_EngrRev"))).click();
			  }
			  driver.findElement(By.xpath(OR.getProperty("btn_Route"))).click();
			  driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			  waitFor();			  
		  } catch(Exception e){
			  Log.error("route WF --- " +e.getMessage());
			  extentTest.log(LogStatus.ERROR, e.getMessage());
			  DriverScript.bResult = false;
		  }
	  }	
	  
	  public static void stopWF(String object, String data){
		  try{
			  driver.findElement(By.xpath(OR.getProperty("hvr_Workflow"))).click();
			  driver.findElement(By.xpath(OR.getProperty("stop_Workflow"))).click();
			  driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			  waitFor();			  
		  } catch(Exception e){
			  Log.error("stop WF --- " +e.getMessage());
			  extentTest.log(LogStatus.ERROR, e.getMessage());
			  DriverScript.bResult = false;
		  }
	  }	
	  
	  public static void createSR(String object, String data){
		  try{
			  Log.info("Create SR....");
			  driver.findElement(By.xpath(OR.getProperty("btn_New"))).click();
			  waitFor();			  
			  driver.findElement(By.xpath(OR.getProperty("txtbx_ReporterType"))).sendKeys(data.trim());
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Summary"))).sendKeys(object.trim());
			  driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys("1000014");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Priority"))).sendKeys("4");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_ActivityType"))).sendKeys("Destress");
			  waitFor();
			  driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
			  waitFor();
//			  storeValue(driver.findElement(By.xpath(OR.getProperty("txtbx_ReporterType"))).getAttribute(data), "WONUM");
		  } catch(Exception e){
				 Log.error("Create SR --- " +e.getMessage());
				 extentTest.log(LogStatus.ERROR, e.getMessage());
				 DriverScript.bResult = false;
		  }
	  }	
	  
	  public static void createWO(String object, String data){
		  try{  
			  String activityType = "Drainage";
			  String priority = "4";
			  if (data.equals("CAP")) {
				  activityType="Destress";
				  priority="18";
			  }
			  LocalDate ngayon = getDate();
		      //add 2 week to the current date
		      LocalDate next2Week = ngayon.plus(2, ChronoUnit.WEEKS);
		      
			  waitFor5();
			  driver.findElement(By.xpath(OR.getProperty("btn_New"))).click();
			  waitFor5();			  
			  driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys("1000014");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Description"))).sendKeys(object.trim());
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Worktype"))).sendKeys(data.trim());
			  driver.findElement(By.xpath(OR.getProperty("txtbx_ActivityType"))).click();
			  driver.findElement(By.xpath(OR.getProperty("txtbx_ActivityType"))).sendKeys(activityType);
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Priority"))).sendKeys(priority);
			  driver.findElement(By.xpath(OR.getProperty("txtbx_FinancialYear"))).sendKeys(String.valueOf(ngayon.getYear()));
			  driver.findElement(By.xpath(OR.getProperty("txtbx_ScheduledStart"))).sendKeys(next2Week.toString());
			  driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
			  waitFor();
		  } catch(AssertionError ae){
				Log.error(ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
		  } catch(Exception e){
				 Log.error("CreateWO --- " +e.getMessage());
				 extentTest.log(LogStatus.ERROR, e.getMessage());
				 DriverScript.bResult = false;
		  }
     }	
	  
	  public static void createWOwithLinear(String object, String data){
		  try{  
			  String activityType = "Drainage";
			  String priority = "4";
			  if (data.equals("CAP")) {
				  activityType="Destress";
				  priority="18";
			  }
			  LocalDate ngayon = getDate();
		      //add 2 week to the current date
		      LocalDate next2Week = ngayon.plus(2, ChronoUnit.WEEKS);
		      
		      waitFor5();
			  driver.findElement(By.xpath(OR.getProperty("btn_New"))).click();
			  waitFor5();			  
			  driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys("1000047");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Description"))).sendKeys(object.trim());
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Worktype"))).sendKeys(data.trim());
			  
			  WebDriverWait wait = new WebDriverWait(driver, 15);
			  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty("txtbx_ActivityType"))));
			    
			  driver.findElement(By.xpath(OR.getProperty("txtbx_ActivityType"))).click();
			  
			  System.out.println("Click txtbx_ActivityType");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_ActivityType"))).sendKeys(activityType);
			  
			  System.out.println("SendKeys txtbx_ActivityType");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Priority"))).sendKeys(priority);
			  driver.findElement(By.xpath(OR.getProperty("txtbx_FinancialYear"))).sendKeys(String.valueOf(ngayon.getYear()));
			  driver.findElement(By.xpath(OR.getProperty("txtbx_ScheduledStart"))).sendKeys(next2Week.toString());
			  driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPoint"))).sendKeys("12");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPoint"))).sendKeys("12");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).sendKeys("0");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).sendKeys("1");
			  waitFor();
			  driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
			  waitFor();
		  } catch(Exception e){
				 Log.error("CreateWO --- " +e.getMessage());
				 extentTest.log(LogStatus.ERROR, e.getMessage());
				 DriverScript.bResult = false;
		  }
     }	
	  
	  public static void createWOwithPlans(String object, String data){
		  try{  
			  Log.info("Generating WONUM...");
			  String activityType = "Drainage";
			  String priority = "4";
			  if (data.equals("CAP")) {
				  activityType="Destress";
				  priority="18";
			  }
				
			  LocalDate ngayon = getDate();
		      //add 2 week to the current date
		      LocalDate next2Week = ngayon.plus(2, ChronoUnit.WEEKS);
		      
		      click("btn_New", null);
			  waitFor();	
			  input("txtbx_Priority", priority);
			  input("txtbx_FinancialYear", String.valueOf(ngayon.getYear()));
			  input("txtbx_ScheduledStart", next2Week.toString());
			  input("txtbx_AssetNum", "1000014");
			  input("txtbx_Description", object.trim());
			  input("txtbx_Worktype", data.trim());
			  click("txtbx_ActivityType", null);
			  input("txtbx_ActivityType", activityType);
			  
			  //for some reason these fields auto clears
//			  input("txtbx_Description", object.trim());
//			  input("txtbx_Priority", priority);
//			  input("txtbx_ActivityType", activityType);
			  save("1","1");
			  Log.info("Generated WONUM = " + driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).getAttribute("value"));
				 
			  click("tab_Plans", null);
			  waitForElementDisplayed("btn_NewRow_Labour");
//			  add labour plan
			  click("btn_NewRow_Labour", null);
			  waitFor();
			  input("txtbx_Trade", "LEVEL1");
			  input("txtbx_Quantity", "2");
			  input("txtbx_PersonHours", "5");
			  Log.info("Added labour plan...");
//			  add material plan
			  click("tab_Materials", null);
			  waitForElementDisplayed("btn_NewRow_Materials");
			  click("btn_NewRow_Materials", null);
			  waitFor();
//			  item 1027419 cost = 788.34
			  input("txtbx_Item", "1027419");
			  input("txtbx_Quantity", "2");
			  getStoreroom();
			  input("txtbx_DeliveryAddress", "Dummy address");
			  save("1","1");
			  waitFor();
			  Log.info("Added material plan...");
//			  Go back to Labour Plan
			  click("tab_Labour", null);
			  waitFor();
		  } catch(Exception e){
				 Log.error("CreateWOwithPlans --- " +e.getMessage());
				 extentTest.log(LogStatus.ERROR, e.getMessage());
				 DriverScript.bResult = false;
		  }
     }	
	  
	  public static void createWOwithMaterialPlans(String object, String data){
		  try{  
			  Log.info("Generating WONUM...");
			  String activityType = "Drainage";
			  String priority = "4";
			  if (data.equals("CAP")) {
				  activityType="Destress";
				  priority="18";
			  }
			  LocalDate ngayon = getDate();
		      //add 2 week to the current date
		      LocalDate next2Week = ngayon.plus(2, ChronoUnit.WEEKS);
				
			  driver.findElement(By.xpath(OR.getProperty("btn_New"))).click();
			  waitFor();	
			  driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys("1000014");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Description"))).sendKeys(object.trim());
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Worktype"))).sendKeys(data.trim());
			  driver.findElement(By.xpath(OR.getProperty("txtbx_ActivityType"))).click();
			  driver.findElement(By.xpath(OR.getProperty("txtbx_ActivityType"))).sendKeys(activityType);
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Priority"))).sendKeys(priority);
			  driver.findElement(By.xpath(OR.getProperty("txtbx_FinancialYear"))).sendKeys(String.valueOf(ngayon.getYear()));
			  driver.findElement(By.xpath(OR.getProperty("txtbx_ScheduledStart"))).sendKeys(next2Week.toString());
			  
			  waitFor();
			  Log.info("Generated WONUM = " + driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).getAttribute("value"));
				 
//			  driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
//			  Log.info("Saving...");
			  driver.findElement(By.xpath(OR.getProperty("tab_Plans"))).click();
			  waitForElementDisplayed("tab_Materials");
//		      add materialplan
			  driver.findElement(By.xpath(OR.getProperty("tab_Materials"))).click();
			  waitForElementDisplayed("btn_NewRow_Materials");
			  driver.findElement(By.xpath(OR.getProperty("btn_NewRow_Materials"))).click();
//			  item 1027419 cost = 788.34
			  driver.findElement(By.xpath(OR.getProperty("btn_LineType"))).click();
			  driver.findElement(By.xpath(OR.getProperty("option_Material"))).click();
			  waitFor();
			  driver.findElement(By.xpath(OR.getProperty("txtbx_ItemDescription"))).clear();
			  driver.findElement(By.xpath(OR.getProperty("txtbx_ItemDescription"))).sendKeys("material");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_OrderUnit"))).clear();
			  driver.findElement(By.xpath(OR.getProperty("txtbx_OrderUnit"))).sendKeys("EA");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_UnitCost"))).clear();
			  driver.findElement(By.xpath(OR.getProperty("txtbx_UnitCost"))).sendKeys("10");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_CommodityGroup"))).sendKeys("1000MAT");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_CommodityCode"))).sendKeys("8031");
			  driver.findElement(By.xpath(OR.getProperty("btn_Vendor_chevron"))).click();
			  waitFor();
			  driver.findElement(By.xpath(OR.getProperty("lnk_SelectValue"))).click();
			  waitFor();
			  driver.findElement(By.xpath(OR.getProperty("lnk_List_Code"))).click();
			  Log.info("Added material plan...");
//			  Go back to Labour Plan
//			  driver.findElement(By.xpath(OR.getProperty("tab_Labour"))).click();
			  save("1","1");
			  waitFor();
		  } catch(Exception e){
				 Log.error("createWOwithMaterialPlans --- " +e.getMessage());
				 extentTest.log(LogStatus.ERROR, e.getMessage());
				 DriverScript.bResult = false;
		  }
     }	
	  
	  public static void createPO(String object, String data){
		  try{
			  driver.findElement(By.xpath(OR.getProperty("btn_New"))).click();
			  waitFor();			   
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Description"))).sendKeys(data.trim());
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Company"))).sendKeys("DUMMY");
			  waitFor();
			  driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
			  waitFor();
		  } catch(Exception e){
				 Log.error("Create PO --- " +e.getMessage());
				 extentTest.log(LogStatus.ERROR, e.getMessage());
				 DriverScript.bResult = false;
		  }
	  }
	  
//	  TODO - not tested
	  public static void createPR(String object, String data){
		  try{
			  driver.findElement(By.xpath(OR.getProperty("btn_New"))).click();
			  waitFor();			   
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Description"))).sendKeys(data.trim());
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Company"))).sendKeys("706170");
			  waitFor();
			  driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
			  waitFor();
		  } catch(Exception e){
				 Log.error("Create PR --- " +e.getMessage());
				 extentTest.log(LogStatus.ERROR, e.getMessage());
				 DriverScript.bResult = false;
		  }
	  }
	  
	  public static void createAsset(String object, String data){
		  try{
			  Log.info("Creating Asset");
			  driver.findElement(By.xpath(OR.getProperty("btn_New"))).click();
			  waitForElementDisplayed("btn_New", "1");			   
			  driver.findElement(By.xpath("//input[contains(@aria-label, 'Asset description')]")).sendKeys(data.trim());
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Discipline"))).sendKeys("TRACK");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Region"))).sendKeys("Central");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Area"))).sendKeys("Wellington");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Line"))).sendKeys("JVILL");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).sendKeys("95.500");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).sendKeys("96.600");
			  save("1", "1");
			  assertValue1("Start Metrage", "95.500");
			  assertValue1("End Metrage", "96.600");
		  }catch(AssertionError ae){
			  Log.error("Assertion failed --- " + ae.getMessage());
			  extentTest.log(LogStatus.ERROR, ae.getMessage());
			  extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			  extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
			  DriverScript.bResult = false;
		  } catch (NoSuchElementException e) {
		      Log.error("Element not found --- " + e.getMessage());
		      extentTest.log(LogStatus.ERROR, e.getMessage());
	 		  DriverScript.bResult = false;
		  } catch (Exception e) {
		      Log.error("Create Asset --- " + e.getMessage());
		      extentTest.log(LogStatus.ERROR, e.getMessage());
	 		  DriverScript.bResult = false;
		  }
	  }
	  
	  public static void createLocation(String object, String data){
		  try {
//				Create new location
			    Log.info("Creating Location");
				driver.findElement(By.xpath(OR.getProperty("btn_New"))).click();
				driver.findElement(By.xpath(OR.getProperty("txtbx_Type"))).sendKeys("OPERATING");
				driver.findElement(By.xpath("//input[contains(@aria-label, 'Location description')]")).sendKeys(data.trim());
				driver.findElement(By.xpath(OR.getProperty("txtbx_Discipline"))).sendKeys("TRACK");
				driver.findElement(By.xpath(OR.getProperty("txtbx_Region"))).click();
				driver.findElement(By.xpath(OR.getProperty("txtbx_Region"))).sendKeys("Central");
				driver.findElement(By.xpath(OR.getProperty("txtbx_Area"))).click();
				driver.findElement(By.xpath(OR.getProperty("txtbx_Area"))).sendKeys("Wellington");
				driver.findElement(By.xpath(OR.getProperty("txtbx_Line"))).click();
				driver.findElement(By.xpath(OR.getProperty("txtbx_Line"))).sendKeys("JVILL");
				driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).click();
				driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).sendKeys("97.569");
				driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).sendKeys("98.568");
				save("1", "1");
				assertValue1("Start Metrage", "97.569");
				assertValue1("End Metrage", "98.568");
			}catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
				DriverScript.bResult = false;
		    } catch (NoSuchElementException e) {
		    	Log.error("Element not found --- " + e.getMessage());
		    	extentTest.log(LogStatus.ERROR, e.getMessage());
	 			DriverScript.bResult = false;
		    } catch (Exception e) {
		    	Log.error("Create Location --- " + e.getMessage());
		    	extentTest.log(LogStatus.ERROR, e.getMessage());
	 			DriverScript.bResult = false;
		    }	
	  }
	  
	  public static void createClassification(String object, String data){
		  try {
//				Create new Classification
			    Log.info("Creating Classification");
				driver.findElement(By.xpath(OR.getProperty("btn_New"))).click();
				driver.findElement(By.xpath(OR.getProperty("txtbx_Type"))).sendKeys("OPERATING");
				driver.findElement(By.xpath("//input[contains(@aria-label, 'Location description')]")).sendKeys(data.trim());
				driver.findElement(By.xpath(OR.getProperty("txtbx_Discipline"))).sendKeys("TRACK");
				driver.findElement(By.xpath(OR.getProperty("txtbx_Region"))).sendKeys("Central");
				driver.findElement(By.xpath(OR.getProperty("txtbx_Area"))).sendKeys("Wellington");
				driver.findElement(By.xpath(OR.getProperty("txtbx_Line"))).sendKeys("JVILL");
				driver.findElement(By.xpath(OR.getProperty("txtbx_StartMetrage"))).sendKeys("97.569");
				driver.findElement(By.xpath(OR.getProperty("txtbx_EndMetrage"))).sendKeys("98.568");
				save("1", "1");
				assertValue1("Start Metrage", "97.569");
				assertValue1("End Metrage", "98.568");
			}catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
				DriverScript.bResult = false;
		    } catch (NoSuchElementException e) {
		    	Log.error("Element not found --- " + e.getMessage());
		    	extentTest.log(LogStatus.ERROR, e.getMessage());
	 			DriverScript.bResult = false;
		    } catch (Exception e) {
		    	Log.error("Create Location --- " + e.getMessage());
		    	extentTest.log(LogStatus.ERROR, e.getMessage());
	 			DriverScript.bResult = false;
		    }	
	  }
	  
	  public static void createJobPlan(String object, String data){
		  try{
			  Log.info("Create Job Plan...");
			  driver.findElement(By.xpath(OR.getProperty("btn_New"))).click();
			  waitFor();			   
			  driver.findElement(By.xpath("//input[@id=(//label[text()='Job Plan description']/@for)]")).sendKeys(data);
			  driver.findElement(By.xpath(OR.getProperty("btn_NewRow_Labour"))).click();
			  waitForElementDisplayed("txtbx_Trade", "1");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Trade"))).clear();
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Trade"))).sendKeys("LEVEL1");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Quantity"))).clear();
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Quantity"))).sendKeys("2");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_PersonHours"))).clear();
			  driver.findElement(By.xpath(OR.getProperty("txtbx_PersonHours"))).sendKeys("5");
			  Log.info("Added labour plan...");
//			  add material plan
			  driver.findElement(By.xpath(OR.getProperty("tab_Materials"))).click();
			  waitForElementDisplayed("btn_NewRow_Materials");
			  driver.findElement(By.xpath(OR.getProperty("btn_NewRow_Materials"))).click();
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Item"))).clear();
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Item"))).sendKeys("1027419");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Quantity"))).clear();
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Quantity"))).sendKeys("2");
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Storeroom"))).clear();
			  driver.findElement(By.xpath(OR.getProperty("txtbx_Storeroom"))).sendKeys("W600");
//			  driver.findElement(By.xpath(OR.getProperty("txtbx_ConditionCode"))).sendKeys("NEW");
			  Log.info("Added material plan...");
//			  Go back to Labour Plan
			  driver.findElement(By.xpath(OR.getProperty("tab_Labour"))).click();
			  save("1","1");
		  } catch(Exception e){
				 Log.error("Create PO --- " +e.getMessage());
				 extentTest.log(LogStatus.ERROR, e.getMessage());
				 DriverScript.bResult = false;
		  }
	  }
	  
	  public static boolean inWorkflow() {
		  boolean inWorkflow = false;
		  try {
			hover("hvr_Workflow", "lnk_WFAssignment");
			waitForElementDisplayed("tbl_WFAssignment");
			if(driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0")) {
				inWorkflow = false;
			} else { inWorkflow = true; }
		  } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		  return inWorkflow;
	  }
	  
	  
	  public static void addValue(String object, String data){
		  try{
			  String objectVal = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			  Log.info("objectVal = "+objectVal);
			  Log.info("data = "+data);
			  double objectInt = StringUtil.toLong(objectVal);
			  double dataInt = Double.valueOf(data);
			  double newVal = (objectInt * dataInt) + objectInt;
			  Log.info("objectInt = "+objectInt);
			  Log.info("dataInt = "+dataInt);
			  Log.info("add value total = "+newVal);
			  driver.findElement(By.xpath(OR.getProperty(object))).clear();
			  Log.info("Cleared object");
			  driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(String.valueOf(newVal));
			  waitFor();
		  } catch(Exception e){
				 Log.error("Add value --- " +e.getMessage());
				 extentTest.log(LogStatus.ERROR, e.getMessage());
				 DriverScript.bResult = false;
		  }
	  }
 
	  public static void changeStatus(String object, String data){
		  try{
			String status = null;
			String oldStatus = null;
			switch (data.toLowerCase()) {
				case "approved":
					status = "APPR";
					break;
				case "canceled":
					status = "CAN";
				    break;
				case "completed":
					status = "COMP";
				    break;
				case "field complete":
					status = "FIELDCOMP";
				    break;
				case "scheduled":
					status = "SCHED";
				    break;
				case "in progress":
					status = "INPRG";
				    break;
				case "planned":
					status = "PLAN";
				    break;
				case "ready to plan":
					status = "RTP";
				    break;
				case "resolved":
					status = "RESOLVED";
				    break;  
				case "closed":
					status = "CLOSE";
				    break; 
				case "new":
					status = "NEW";
				    break; 
				case "active":
					status = "ACTIVE";
				    break;  
				case "not commissioned":
					status = "NOTCOM";
				    break;       
				case "create":
					status = "CREATE";
				    break;     
			}
              oldStatus = driver.findElement(By.xpath(OR.getProperty("txtbx_Status"))).getAttribute("value");  
			  driver.findElement(By.xpath("//li[contains(@id,'_STATUS_OPTION')]")).click();
			  waitFor();
//              driver.findElement(By.xpath("//img[contains(@title,'Drop-down image')]")).click();
			  driver.findElement(By.xpath("//input[@id=(//label[text()='New Status:']/@for)]")).click();
              Thread.sleep(500);
			  Log.info("clicked new status Drop-down ");
              driver.findElement(By.xpath("//span[contains(@id,'"+status+"_OPTION_a_tnode')]")).click();
              waitFor();
              Log.info("clicked new status "+status);
              driver.findElement(By.xpath("//button[@type='button' and contains(., 'OK')]")).click();
			  waitFor();
			  Log.info("titlebar msg = " +driver.findElement(By.xpath(OR.getProperty("titlebar_message"))).getText());
			  Log.info("changeStatusMsg = " +changeStatusMsg);
//			  if(waitForElementDisplayed("titlebar_message")){
//					Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty("titlebar_message"))).getText().trim().contains("status changed"), "Assertion failed - change status failed");
//			  }
			  Assert.assertTrue(waitForElementDisplayed("titlebar_message"));
			  Log.info("old status = " +oldStatus);
			  Log.info("new status = " +driver.findElement(By.xpath(OR.getProperty("txtbx_Status"))).getAttribute("value"));
			  Assert.assertFalse(driver.findElement(By.xpath(OR.getProperty("txtbx_Status"))).getAttribute("value").trim().toUpperCase().equals(oldStatus), "Assertion failed - status not changed");
		  }catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
				DriverScript.bResult = false;	
		  } catch(Exception e) {
				 Log.error("changeStatus --- " +e.getMessage());
				 extentTest.log(LogStatus.ERROR, e.getMessage());
				 DriverScript.bResult = false;
		  }
     }	
	  
	  public static void duplicate(String object, String data){
			try{
				waitForElementDisplayed("btn_Duplicate", null);
				driver.findElement(By.xpath(OR.getProperty("btn_Duplicate"))).click();
				waitFor();
				
//				Log.info("titlebar_message path =  "+ OR.getProperty("titlebar_message"));
//				Log.info(driver.findElement(By.xpath(OR.getProperty("titlebar_message"))).getText());
				if(waitForElementDisplayed("titlebar_message")){
					Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty("titlebar_message"))).getText().trim().startsWith(duplicateMsg), "Assertion failed.");
					save("1","1");
				}
			}catch(AssertionError ae){
				Log.error("duplicate failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
				DriverScript.bResult = false;	
			}catch(Exception e){
		 		Log.error("Verify Alert: duplicate failed --- " + e.getMessage());
		 		extentTest.log(LogStatus.ERROR, e.getMessage());
		 		DriverScript.bResult = false;
		    }
	  }
	  
	  public static void duplicatePR(String object, String data){
			try{
				waitForElementDisplayed("btn_Duplicate", null);
				driver.findElement(By.xpath(OR.getProperty("btn_Duplicate"))).click();
				waitFor();
				
				if(waitForElementDisplayed("titlebar_message")){
					Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty("titlebar_message"))).getText().trim().startsWith(duplicateMsg), "Assertion failed.");
					if (object.equals("PR") && !data.equals("")) {
						driver.findElement(By.xpath(OR.getProperty("txtbx_RequiredDate"))).sendKeys(data);
					} else {
						save("1","1");
					}
				}
			}catch(AssertionError ae){
				Log.error("duplicate failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
				DriverScript.bResult = false;	
			}catch(Exception e){
		 		Log.error("Verify Alert: duplicate failed --- " + e.getMessage());
		 		extentTest.log(LogStatus.ERROR, e.getMessage());
		 		DriverScript.bResult = false;
		    }
	  }
	  
	  

	  public static void save(String object, String data){
			try{
				driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
				Thread.sleep(1000);
				
//				Log.info("titlebar_message path =  "+ OR.getProperty("titlebar_message"));
//				Log.info(driver.findElement(By.xpath(OR.getProperty("titlebar_message"))).getText());
				if(waitForElementDisplayed("titlebar_message")){
					Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty("titlebar_message"))).getText().trim().contains(saveMsg), "Assertion failed.");
		        }
				Thread.sleep(2000);
			 }catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
				DriverScript.bResult = false;	
			 }catch(Exception e){
		 		Log.error("Verify Alert: Assertion failed --- " + e.getMessage());
		 		extentTest.log(LogStatus.ERROR, e.getMessage());
		 		DriverScript.bResult = false;
		     }
	}  
	  
	public static void getStoreroom() {
		try {
			driver.findElement(By.xpath(OR.getProperty("btn_Storeroom_chevron"))).click();
			driver.findElement(By.xpath(OR.getProperty("lnk_SelectValue"))).click();
			waitFor();
			driver.findElement(By.xpath(OR.getProperty("lnk_List_Code"))).click();
			waitFor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	  
	  /*
	 static void hover(String object, String data){
			try{
				Log.info("Hover on Webelement "+ object);
				waitElementExists(object);
				WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
		        Actions action = new Actions(driver);	
		        action.moveToElement(element).build().perform();
		        driver.findElement(By.xpath(OR.getProperty(data))).click();	
			 }catch(Exception e){
	 			Log.error("Not able to hover --- " + e.getMessage());
	 			DriverScript.bResult = false;
	         	}
			} 
	  */
	  public static void GoTo(String object, String data){
		     try{
		    	 String dir = null;
		    	 switch (object.toLowerCase()) {
		            case "assets":
		            	 dir = "Children of Work Order";
		            	 break;
		            case "tasks":
		            	 dir = "Tasks for Work Order";
		            	 break;
		            case "labour":
		            	 dir = "Labour";
		            	 break;
		            case "materials":
		            	 dir = "Materials";
		            	 break;
		            case "services":
		            	 dir = "Services";
		            	 break;
		            case "tools":
		            	 dir = "Tools";
		            	 break;
		        }
		    		    	 
			    List<WebElement> ddOpts = driver.findElements(By.xpath("//table[contains(@summary,'"+dir+"')]//tr[contains(@class,'tablerow')]"));
//			    
//			    Log.info("ddOpts.toString() =  " + ddOpts.toString());
//			    Log.info("object =  " + object.toLowerCase());
//			    Log.info("is empty row? =  " + ddOpts.isEmpty());
//				Log.info("dir =  " + dir);
				int rowCount = ddOpts.size();
				Assert.assertEquals(String.valueOf(rowCount), data.trim());
		     }catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				extentTest.log(LogStatus.INFO, "Actual Value: " + driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value"));
				DriverScript.bResult = false;
			 }catch(Exception e){
				Log.error("Table does not exists --- " +e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
			 }
	  }  
	  
	  public static void captureSystemMessage(String object, String filename) {
			
			Log.info("Capturing the text in the System Message");
			
			WebElement divMsgBox = driver.findElement(By.xpath(".//label[@id='" + object+ "']"));

			String info = divMsgBox.getText();
			String idForLog = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
			File file = new File(filename + ".txt");

			try {
				FileWriter fw = new FileWriter(file, true);
			    String lineSeparator = System.getProperty("line.separator");

				fw.append(idForLog + lineSeparator);

				String[] output = info.split("\n");

				for (int i = 0; i <= output.length - 1; i++) {
					fw.append(output[i]);
					fw.append(lineSeparator);
				}
				fw.append(lineSeparator + lineSeparator);

				fw.flush();
				fw.close();
			} catch (Exception e) {
				Log.error("Unable to capture sytem message ---- " +e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
			}
		}
	  
	  public static void logMessage(String object, String data) {
			
			Log.info("Create Message");
			
			String code = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			String divMsgBox = data;
			String idForLog = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
			File file = new File("LoggedMessages" + ".txt");

			try {
				FileWriter fw = new FileWriter(file, true);
			    String lineSeparator = System.getProperty("line.separator");

				fw.append(idForLog + lineSeparator);

				String[] output = divMsgBox.split("\n");

				fw.append("To check: "+ code);
				fw.append(lineSeparator);
				for (int i = 0; i <= output.length - 1; i++) {
					fw.append(output[i]);
					fw.append(lineSeparator);
				}
				fw.append(lineSeparator + lineSeparator);

				fw.flush();
				fw.close();
			} catch (Exception e) {
				Log.error("Unable to capture sytem message ---- " +e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
			}
		}
	  
	  public static void whereClause(String object, String data){
		  try{
			  driver.findElement(By.xpath("//img[@alt='Advanced Search menu']")).click();
			  driver.findElement(By.xpath("//span[@id='menu0_SEARCHWHER_OPTION_a_tnode']")).click();
			  waitForElementDisplayed("txtarea_whereClause");
			  driver.findElement(By.xpath(OR.getProperty("txtarea_whereClause"))).clear();
			  driver.findElement(By.xpath(OR.getProperty("txtarea_whereClause"))).sendKeys(data.trim());
			  waitForElementDisplayed("btn_Find");
			  driver.findElement(By.xpath(OR.getProperty("btn_Find"))).click();
			  waitFor();
		  } catch(Exception e){
			  Log.error("whereClause --- " +e.getMessage());
			  extentTest.log(LogStatus.ERROR, e.getMessage());
			  DriverScript.bResult = false;
		  }
      }	
	  
	  public static void clearWhereClause(String object, String data){
		  try{                       
			  driver.findElement(By.xpath("//a[contains(.,'Advanced Search')]")).click();
			  driver.findElement(By.xpath("//button[contains(.,'Revise')]")).click();
			  driver.findElement(By.xpath("//span[contains(.,'Clear Query and Fields')]")).click();
//			  waitFor();
		  } catch(Exception e){
			  Log.error("clearWhereClause --- " +e.getMessage());
			  extentTest.log(LogStatus.ERROR, e.getMessage());
			  DriverScript.bResult = false;
		  }
      }
	  
	  public static void populateDate(String object, String data){
		  try{
			  String petsa = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
			  if (data.equals("")) {
				  Log.info("populateDate" + new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()));
			      driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(petsa);
			  } else {
				  driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(data);
			  }
		  } catch(Exception e){
			  Log.error("populateDate --- " +e.getMessage());
			  extentTest.log(LogStatus.ERROR, e.getMessage());
			  DriverScript.bResult = false;
		  }
     }	
	  
	  public static String getAttributeValue(String element) {
		String value = null;
	  	try {
	  		value = driver.findElement(By.xpath(OR.getProperty(element))).getAttribute("value");
	  	} catch(Exception e){
 			Log.error(e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
       	}
		return value;
	  }
 
	  public static void changeWOStatusToPlan(String object, String data){
			try{
				Log.info("Change WO status to PLAN = "+data);
				String user="mxeng1";
				if (data.equals("AMREN")) {
					user="mxsvcemgr1";
				}
				
				openBrowser("1","Mozilla");
                waitFor();
				login("",user);
				hover("hvr_WO", "lnk_WO");
				driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(object);
				enter("txtbx_QuickSearch","1");
				waitFor();

				changeStatus("","ready to plan");
				changeStatus("","planned");
				save("1","1");
			 }catch(Exception e){
	 			Log.error("Not able to click --- " + e.getMessage());
	 			extentTest.log(LogStatus.ERROR, e.getMessage());
	 			DriverScript.bResult = false;
	       	 }
	   }
	  
	  public static void workorderWorkflow(String object, String data) {
		  WorkorderWorkflow.workorderWorkflow(object, data);
	  }
	  
	  public static void workorderWorkflowWithChildren(String object, String data) {
		  WorkorderWorkflowWithChildren.workorderWorkflowWithChildren(object, data);
	  }  
	  
	  public static void locationCustomMetrages(String object, String data) {
		  LocationCustomMetrages.locationCustomMetrages(object, data);
	  }
	  
	  public static void railWearMeterReadings(String object, String data) {
		  RailWearMeterReadings.railWearMeterReadings(object, data);
	  }

	  public static void crossoverLocationsAsset(String object, String data) {
		  CrossoverLocationsAsset1.crossoverLocationsAsset1(object, data);
	  }
	  
	  public static void WOClassificationAttributes(String object, String data) {
		  WOClassificationAttributes.woClassificationAttributes(object, data);
	  }
	  
	  public static void AssetFeatureDeletion(String object, String data) {
		  AssetFeatureDeletion.assetFeatureDeletion(object, data);
	  }
	  
	  public static void PreventSRsAndWOsWhereNOTRACKexist(String object, String data) {
		  PreventSRsAndWOsWhereNOTRACKexist.preventSRsAndWOsWhereNOTRACKexist();
	  }
	  
	  public static void WOAssetLocRoute(String object, String data) {
		  WOAssetLocRoute.woAssetLocRoute();
	  }
	  
	  public static void WOTotalCosts(String object, String data) {
		  WOTotalCosts.WOTotalCosts();
	  }
	  
	  public static void ServiceItemPlannedCostChange(String object, String data) {
		  ServiceItemPlannedCostChange.serviceItemPlannedCostChange();
	  }
	  
	  public static void Classifications(String object, String data) {
		  Classifications.classifications();
	  }
	  
	  public static void LocationSystems(String object, String data) {
		  LocationSystems.locationSystems();
	  }
	  
	  public static void Vendor(String object, String data) {
		  Vendor.vendor();;
	  }
	  
	  public static void UnitTests(String object, String data) throws Exception {
		  WOUnitTests.unitTests();
	  }
}