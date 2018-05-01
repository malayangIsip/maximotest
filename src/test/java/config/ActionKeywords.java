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

import static executionEngine.Base.OR;
import static executionEngine.Base.action;
import static executionEngine.Base.driver;
import static executionEngine.Base.extentTest;
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
import utility.Constants;
import pageObjects.WOPage;
import utility.Log;
import utility.Utils;

public class ActionKeywords {
	public static WOPage  woPage;
//	static String password = "sirius";
//	static String password = "Kiwirail123";
	
	static String duplicateMsg = "BMXAA4131E";
	static String saveMsg = "Record has been saved.";
	static String changeStatusMsg = "BMXAA4591I";
	
	public ActionKeywords() {
//		woPage = new WOPage(driver);
	}
			
	@SuppressWarnings("deprecation")
	public static void openBrowser(String object,String data){		
		Log.info("Opening Browser...");
		try{	
			Log.info("Try opening browser");
			if(data.equals("Mozilla")) {
				Log.info("Mozilla is the browser");

//				System.setProperty("webdriver.gecko.driver", "C:/Users/mme9310/Documents/lib/geckodriver.exe");
				System.setProperty("webdriver.gecko.driver", Constants.Path_Lib + "geckodriver.exe");
				
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
//				System.setProperty("webdriver.chrome.driver", "C:/Users/mme9310/Documents/lib/chromedriver.exe");
				System.setProperty("webdriver.gecko.driver", Constants.Path_Lib + "chromedriver.exe");
				
				ChromeOptions chromeOptions = new ChromeOptions();
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			    capabilities.setBrowserName("chrome");
			    
				driver=new ChromeDriver(chromeOptions);
				Log.info("Chrome browser started");
			} else if(data.equals("Chrome--headless")) {
				Log.info("Chrome is the browser--headless");
//				System.setProperty("webdriver.chrome.driver", "C:/Users/mme9310/Documents/lib/chromedriver.exe");
				System.setProperty("webdriver.gecko.driver", Constants.Path_Lib + "chromedriver.exe");
				
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
			Assert.fail();
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
			Thread.sleep(5000);
			driver.findElement(By.xpath(".//*[contains(@id, 'username')]")).sendKeys(data.trim());
			driver.findElement(By.xpath(".//*[contains(@id, 'password')]")).sendKeys(password);
			click("btn_LogIn");
		 }catch(Exception e){
 			Log.error("Exception --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
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
			click("btn_LogIn");
		}catch(Exception e){
 			Log.error("Not able to loginApprover --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
       	}
	}
	
	public static void logout(String object, String data){
		try{
			Log.info("Logging out");
//			driver.findElement(By.id("titlebar_hyperlink_8-lbsignout_image")).click();
			driver.close();
		 }catch(Exception e){
 			Log.error("Exception --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
        }
	}

	public static void enter(String object){
		try{
			waitForElementDisplayed(object);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
		}catch(Exception e){
 			Log.error("Not able to enter --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
        }
	}
	
	public static void clear(String object){
		try{
			waitForElementDisplayed(object);
			driver.findElement(By.xpath(OR.getProperty(object))).clear();;
		}catch(Exception e){
 			Log.error("Not able to clear --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
        }
	}
	
	static void click(String object) {
		click(object, null);
	}
	
	public static void click(String object, String data){
		try{
			Log.info("Clicking on Webelement "+ object);
			waitForElementDisplayed(object);
			elementClickable(object);
			driver.findElement(By.xpath(OR.getProperty(object))).click();
            Thread.sleep(500);
		 }catch(Exception e){
 			Log.error("Not able to click --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
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
//	        driver.findElement(By.xpath(OR.getProperty(data))).click();	
	        click(data);
//	        waitFor();
		 }catch(Exception e){
 			Log.error("Not able to hover --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
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
 			Assert.fail();
         }
	} 
	
	
//	TODO duplicate
	public static void _storeValue1(String object, String data){
		String code = data;
		String xpath = "//input[@id=(//label[contains(., '"+object+"')]/@for)]";
		Log.info("storedValue value : " + code);
		if (code.equalsIgnoreCase("WONUM")) {
			getAttributeValue(By.xpath(xpath));
//			DriverScript.WONUM = driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).getAttribute("value");
			DriverScript.storedValue = DriverScript.WONUM;
		} else if (code.equalsIgnoreCase("ASSETNUM")) {
			getAttributeValue(By.xpath(xpath));
			DriverScript.storedValue = DriverScript.ASSETNUM;
		} else if (code.equalsIgnoreCase("PRNUM")) {
			getAttributeValue(By.xpath(xpath));
			DriverScript.storedValue = DriverScript.PRNUM;
		} else if (code.equalsIgnoreCase("PONUM")) {
			getAttributeValue(By.xpath(xpath));
			DriverScript.storedValue = DriverScript.PONUM;
		} else if (code.equalsIgnoreCase("Storeroom")) {
			getAttributeValue(By.xpath(xpath));
			DriverScript.storedValue = DriverScript.Storeroom;
		} else if (code.equalsIgnoreCase("PMNUM")) {
			getAttributeValue(By.xpath(xpath));
			DriverScript.storedValue = DriverScript.PMNUM;
		} else if (code.equalsIgnoreCase("RouteNum")) {
			getAttributeValue(By.xpath(xpath));
			DriverScript.storedValue = DriverScript.RouteNum;
		} else if (code.equalsIgnoreCase("JPNum")) {
			getAttributeValue(By.xpath(xpath));
			DriverScript.storedValue = DriverScript.JPNum;
		} else if (code.equalsIgnoreCase("Template")) {
			getAttributeValue(By.xpath(xpath));
			DriverScript.storedValue = DriverScript.Template;
		} else if (code.equalsIgnoreCase("SRNUM")) {
			getAttributeValue(By.xpath(xpath));
			DriverScript.storedValue = DriverScript.SRNUM;
		} else if (code.equalsIgnoreCase("LOCATION")) {
			getAttributeValue(By.xpath(xpath));
			DriverScript.storedValue = DriverScript.LOCATION;
		} else if (code.equalsIgnoreCase("POINT")) {
			getAttributeValue(By.xpath(xpath));
			DriverScript.storedValue = DriverScript.POINT;
		} else if (code.equalsIgnoreCase("WFApprover")) {
			DriverScript.WFApprover = driver.findElement(By.xpath("//table[contains(@summary,'Workflow Assignments')]/tbody/tr[4]/td[2]")).getText();
			DriverScript.storedValue = DriverScript.WFApprover;
		}
		Log.info("DriverScript.storedValue : " + DriverScript.storedValue);
	}
	
	public static void storeValue(String object, String data){
		String code = data;
		Log.info("storedValue value : " + code);
		if (code.equalsIgnoreCase("WONUM")) {
			DriverScript.WONUM = getAttributeValue(object);
			DriverScript.storedValue = DriverScript.WONUM;
		} else if (code.equalsIgnoreCase("ASSETNUM")) {
			DriverScript.ASSETNUM = getAttributeValue(object);
			DriverScript.storedValue = DriverScript.ASSETNUM;
		} else if (code.equalsIgnoreCase("PRNUM")) {
			DriverScript.PRNUM = getAttributeValue(object);
			DriverScript.storedValue = DriverScript.PRNUM;
		} else if (code.equalsIgnoreCase("PONUM")) {
			DriverScript.PONUM = getAttributeValue(object);
			DriverScript.storedValue = DriverScript.PONUM;
		} else if (code.equalsIgnoreCase("Storeroom")) {
			DriverScript.Storeroom = getAttributeValue(object);
			DriverScript.storedValue = DriverScript.Storeroom;
		} else if (code.equalsIgnoreCase("PMNUM")) {
			DriverScript.PMNUM = getAttributeValue(object);
			DriverScript.storedValue = DriverScript.PMNUM;
		} else if (code.equalsIgnoreCase("RouteNum")) {
			DriverScript.RouteNum = getAttributeValue(object);
			DriverScript.storedValue = DriverScript.RouteNum;
		} else if (code.equalsIgnoreCase("JPNum")) {
			DriverScript.JPNum = getAttributeValue(object);
			DriverScript.storedValue = DriverScript.JPNum;
		} else if (code.equalsIgnoreCase("Template")) {
			DriverScript.Template = getAttributeValue(object);
			DriverScript.storedValue = DriverScript.Template;
		} else if (code.equalsIgnoreCase("SRNUM")) {
			DriverScript.SRNUM = getAttributeValue(object);
			DriverScript.storedValue = DriverScript.SRNUM;
		} else if (code.equalsIgnoreCase("LOCATION")) {
			DriverScript.LOCATION = getAttributeValue(object);
			DriverScript.storedValue = DriverScript.LOCATION;
		} else if (code.equalsIgnoreCase("POINT")) {
			DriverScript.POINT = getAttributeValue(object);
			DriverScript.storedValue = DriverScript.POINT;
		} else if (code.equalsIgnoreCase("Vendor")) {
			DriverScript.VENDOR = getAttributeValue(object);
			DriverScript.storedValue = DriverScript.VENDOR;
		} else if (code.equalsIgnoreCase("WFApprover")) {
			DriverScript.WFApprover = driver.findElement(By.xpath("//table[contains(@summary,'Workflow Assignments')]/tbody/tr[4]/td[2]")).getText();
			DriverScript.storedValue = DriverScript.WFApprover;
		}
		Log.info("DriverScript.storedValue : " + DriverScript.storedValue);
	}
	
	public static void retrieveValue(String object, String data){
		String code = data;
		
		if (code.equalsIgnoreCase("WONUM")) {
			input(object, DriverScript.WONUM);
		} else if (code.equalsIgnoreCase("ASSETNUM")) {
			input(object, DriverScript.ASSETNUM);
		} else if (code.equalsIgnoreCase("PRNUM")) {
			input(object, DriverScript.PRNUM);
		} else if (code.equalsIgnoreCase("PONUM")) {
			input(object, DriverScript.PONUM);
		} else if (code.equalsIgnoreCase("Storeroom")) {
			input(object, DriverScript.Storeroom);
		} else if (code.equalsIgnoreCase("RouteNum")) {
			input(object, DriverScript.RouteNum);
		} else if (code.equalsIgnoreCase("PMNUM")) {
			input(object, DriverScript.PMNUM);
		} else if (code.equalsIgnoreCase("JPNum")) {
			input(object, DriverScript.JPNum);
		} else if (code.equalsIgnoreCase("Template")) {
			input(object, DriverScript.Template);
		} else if (code.equalsIgnoreCase("SRNUM")) {
			input(object, DriverScript.SRNUM);
		} else if (code.equalsIgnoreCase("LOCATION")) {
			input(object, DriverScript.LOCATION);
		} else if (code.equalsIgnoreCase("POINT")) {
			input(object, DriverScript.POINT);
		} else if (code.equalsIgnoreCase("WFApprover")) {
			input(object, DriverScript.WFApprover);
		} 
		
		Log.info("DriverScript.retrievedValue : " +DriverScript.storedValue);
	}

	/*
//	TODO duplicate
	public static void _retrieveValue1(String object, String data){
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
*/	
//	TODO duplicate
	public static void assertValue1(String object, String data){
		try{
			String ele = null;
			String xpath = "//input[@id=(//label[contains(., '"+object+"')]/@for)]";
			if (driver.findElement(By.xpath(xpath)).getText() != null &&
					!driver.findElement(By.xpath(xpath)).getText().equals("") && 
					!driver.findElement(By.xpath(xpath)).getText().isEmpty()) {
				ele = driver.findElement(By.xpath(xpath)).getText();
			} else {
				ele = getAttributeValue(By.xpath(xpath));
			}
			
			ele = StringUtils.remove(ele, ",");	
			Log.info("get assertValue: object="+ele.toUpperCase()+" data:"+data.toUpperCase());
			waitFor();
			Assert.assertTrue(ele.toUpperCase().trim().equals(data.toUpperCase()), "Assertion failed.");
		 } catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
		 	DriverScript.bResult = false;
		 	Assert.fail();
	     } catch(Exception e){
	 		Log.error("Exception --- " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, e.getMessage());
	 		DriverScript.bResult = false;
	 		Assert.fail();
	     } 
	}
	
	public static void assertValue(String object, String data){
		try{
			String ele = null;
			waitForElementDisplayed(object);
			if (driver.findElement(By.xpath(OR.getProperty(object))).getText() != null &&
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().equals("") && 
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().isEmpty()) {
				ele = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			} else {
				ele = getAttributeValue(object);
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
		 	DriverScript.bResult = false;
		 	Assert.fail();
	     } catch(Exception e){
	 		Log.error("Exception --- " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, e.getMessage());
	 		DriverScript.bResult = false;
	 		Assert.fail();
	     } 
	}
	
	public static void assertValue2(String object, String data){
		try{
			String ele = null;
			waitForElementDisplayed(object);
			if (driver.findElement(By.xpath(OR.getProperty(object))).getText() != null &&
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().equals("") && 
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().isEmpty()) {
				ele = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			} else {
				ele = getAttributeValue(object);
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
		 	DriverScript.bResult = false;
		 	Assert.fail();
	     } catch(Exception e){
	 		Log.error("Exception --- " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, e.getMessage());
	 		DriverScript.bResult = false;
	 		Assert.fail();
	     } 
	}
	
	public static void assertCode(String object, String data){
		try{
			String ele = null;
			waitForElementDisplayed(object);
			if (driver.findElement(By.xpath(OR.getProperty(object))).getText() != null &&
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().equals("") && 
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().isEmpty()) {
				ele = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			} else {
				ele = getAttributeValue(object);
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
		 	DriverScript.bResult = false;
		 	Assert.fail();
	     } catch(Exception e){
	 		Log.error("Exception --- " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, e.getMessage());
	 		DriverScript.bResult = false;
	 		Assert.fail();
	     } 
	}
	
	public static String getStoredValue(String data) {
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
	            case "VENDOR":
	            	code = DriverScript.VENDOR;
	            	break;
			    }
			return code;
	}
	
	
	public static void fieldValuesEqual(String object, String data){
		try{
			waitForElementDisplayed(object);
			waitForElementDisplayed(data);
			String field1 = String.valueOf(getAttributeValue(object));
			String field2 = String.valueOf(getAttributeValue(data));
			Assert.assertEquals(field1.trim().toLowerCase(), field2.trim().toLowerCase());
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false;
			Assert.fail();
		 }catch(Exception e){
 			Log.error("Fields not Equal --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
         }
	}
	
	public static void isNull(String object, String data){
		try{
			waitForElementDisplayed(object);
			String val = String.valueOf(getAttributeValue(object).equals(""));
			Assert.assertEquals(val.toLowerCase(), data.toLowerCase());
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false; 
			Assert.fail();
		}catch(Exception e){
 			Log.error("Object is null --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
         }
	}
	
	public static void isEmpty(String object, String data){
		try{
			waitForElementDisplayed(object);
			Boolean val = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("displayrows").equals("0");
			Assert.assertEquals(String.valueOf(val).toLowerCase(), data.toLowerCase());
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false; 
			Assert.fail();
		}catch(Exception e){
 			Log.error("Object is empty --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
         }
	}

	public static void isReadOnly(String object, String data){
		 try{
			String val = "false"; 
			waitForElementDisplayed(object);
    		if (driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("readonly") != null) {
				val = "true";
			}
			Assert.assertEquals(val, data.toLowerCase());
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false;
			Assert.fail();
		 }catch(Exception e){
 			Log.error("Object is readonly --- " +e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
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
			DriverScript.bResult = false; 
			Assert.fail();
		}catch(Exception e){
			Log.error("Object is disabled --- " +e.getMessage());
			extentTest.log(LogStatus.ERROR, e.getMessage());
			DriverScript.bResult = false;
			Assert.fail();
        }
	}
		
	public static void isRequired(String object, String data){
		try{
			String val = "false"; 
			waitForElementDisplayed(object);
    		if (driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("aria-required") != null) {
				val = "true";
			}
			Assert.assertEquals(val, data.toLowerCase());
   	    }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false;	
			Assert.fail();
		}catch(Exception e){
 			Log.error("Object is required --- " +e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
        }
	}
		
	public static void isChecked(String object, String data){
		try{
			waitForElementDisplayed(object);
			String val = String.valueOf(driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("aria-checked").equals("true"));
			Assert.assertEquals(val.toLowerCase(), data.toLowerCase());
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false; 
			Assert.fail();
		}catch(Exception e){
 			Log.error("Object is checked--- " +e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
        }
	}
	
	public boolean isChecked(String object) throws Exception{
		waitForElementDisplayed(object);
		boolean val = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("aria-checked").equals("true");
		if (val) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void isTicked(String object, String data){
		try{
			waitForElementDisplayed(object);
			Log.error("Is Ticked ="+ driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked"));
			String val = String.valueOf(driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked").equals("true"));
			Log.error("Is Ticked val ="+ val);
			Assert.assertEquals(val.toLowerCase(), data.toLowerCase());
  	    }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false; 
			Assert.fail();
		}catch(Exception e){
 			Log.error("Object is ticked --- " +e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
        }
	}
	
	public static void isValid(String object, String data){
		try{
			String val = "true"; 
			waitForElementDisplayed(object);
    		if (driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("aria-invalid") != null) {
				val = "false";
			}
			Assert.assertEquals(val, data.toLowerCase());
  	    }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false; 
			Assert.fail();
		}catch(Exception e){
 			Log.error("Exception --- " +e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
        }
	}
	
//	public boolean isVendorActive() throws Exception{
		//should be in the materials tab to call this method
//		waitForElementDisplayed(object);
//		boolean val = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("aria-checked").equals("true");
//		if (val) {
//			return true;
//		} else {
//			return false;
//		}
//	}
		
	public static void rowsDisplayed(String object, String data){
		try{
			object ="//table[contains(@id,'_tbod-tbd')]";
			waitForElementDisplayed(object);
			int val = Integer.valueOf(driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("displayrows"));
			Assert.assertEquals(val, Integer.valueOf(data).intValue());
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false;	
			Assert.fail();
		 }catch(Exception e){
 			Log.error("Exception ---" + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
         }
	}
	
	public static void rowsDisplayed1(String object, String data){
		try{
			String path = "//table[contains(@summary,'"+object+"')]";
			waitForElementDisplayed(By.xpath(path));
			int val = Integer.valueOf(driver.findElement(By.xpath(path)).getAttribute("displayrows"));
			Assert.assertEquals(val, Integer.valueOf(data).intValue());
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false;	
			Assert.fail();
		 }catch(Exception e){
 			Log.error("Exception ---" + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
         }
	}
	
	public static void tableNotEmpty(String object, String data){
		try{
			String path = "//table[contains(@summary,'"+object+"')]";
			waitForElementDisplayed(By.xpath(path));
			int val = Integer.valueOf(driver.findElement(By.xpath(path)).getAttribute("displayrows"));
			Assert.assertEquals(Boolean.valueOf(val > 0).toString().toLowerCase(), data.toLowerCase());
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false;
			Assert.fail();
		 }catch(Exception e){
 			Log.error("Exception ---" + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
         }
	}
	
	public static void rowsPerPage(String object, String data){
		try{
			String path = "//table[@id='lookup_page1_tbod-tbd']";
			waitForElementDisplayed(By.xpath(path));
			int val = Integer.valueOf(driver.findElement(By.xpath(path)).getAttribute("rowsperpage"));
			Assert.assertEquals(val, Integer.valueOf(data).intValue());
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false;	
			Assert.fail();
		 }catch(Exception e){
 			Log.error("Exception ---" + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
         }
	}
	
	public static void rowsPerPage1(String object, String data){
		try{
			String path = "//table[contains(@summary,'"+object+"')]";
			waitForElementDisplayed(By.xpath(path));
			int val = Integer.valueOf(driver.findElement(By.xpath(path)).getAttribute("rowsperpage"));
			Assert.assertEquals(val, Integer.valueOf(data).intValue());
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false;
			Assert.fail();
		 }catch(Exception e){
 			Log.error("Exception ---" + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
         }
	}
	
	public static void totalRowCount(String object, String data){
		try{
			String path = "//table[@id='lookup_page1_tbod-tbd']/tbody/tr[contains(@id='lookup_page1_tbod_tdrow-tr')]";
			waitForElementDisplayed(By.xpath(path));
			List<WebElement> ddOpts = driver.findElements(By.xpath(path));
			int rowCount = ddOpts.size();
			Assert.assertEquals(rowCount, Integer.valueOf(data).intValue());
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false;
			Assert.fail();
		 }catch(Exception e){
 			Log.error("Total row count ---" +e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
         }
	}
	

	public static void lineCostNotZero(String object, String data){
		try{
			waitForElementDisplayed(object);
			Assert.assertFalse(getAttributeValue(object).equals("0.00"), "Zero line cost.");
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false;
			Assert.fail();
		 }catch(Exception e){
 			Log.error("Zero line cost --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
         }
	}
	
	public static void waitElementExists(String object){
		try{
			Log.info("Checks Webelement exists: "+ object);
			WebDriverWait wait = new WebDriverWait(driver, 3);
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath(OR.getProperty(object)))));
		 }catch(Exception e){
 			Log.error("Exception --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
         }
	}
	
	public static void explicitlyWait(String object, int sec){
		try{
			Log.info("Checks Webelement exists: "+ object);
			(new WebDriverWait(driver, sec)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(object))));			
		}catch(Exception e){
			Log.error("Exception --- " + e.getMessage());
			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
		}
	}
	
	public static void checkSaveDone(String object, String data){
		try{
			Log.info("Checks saving is done: "+ object);
			WebDriverWait wait = new WebDriverWait(driver, 3);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='ROUTEWF__-tbb_image']")));
			Assert.assertTrue(getAttributeValue(object).equals(data));
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false;
			Assert.fail();
		 }catch(Exception e){
 			Log.error("Exception --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
         }
	}
	
	
//to verify if element exists; for negative testing
	public static boolean existsElement(String id) {
	    try {
//	        driver.findElement(By.xpath(OR.getProperty(id)));
	    	driver.findElement(By.xpath("//label[text()='"+id+"']"));
	    } catch (NoSuchElementException e) {
	        return false;
	    }
	    return true;
	}
	
	public static boolean existsElement(By xpath) {
	    try {
	    	driver.findElement(xpath);
	    } catch (NoSuchElementException e) {
	        return false;
	    }
	    return true;
	}
	
	public static void elementExists(String object, String data) throws InterruptedException { 
	    try {
	    	Log.info("Checks if element exists: "+ object);
	    	if (data.toLowerCase().equals("true")) {
			    Assert.assertTrue(existsElement(By.xpath(OR.getProperty(object))));
			} else {
				Assert.assertFalse(existsElement(By.xpath(OR.getProperty(object))));
			}
	    	Thread.sleep(2000);
	    }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			DriverScript.bResult = false;
			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
	    }
	}
	
	public static void elementExist(String object, String data) { 
	    try {
	    	Log.info("Checks if element exists: "+ object);
	    	Assert.assertEquals(String.valueOf(existsElement(object)), data.toLowerCase());
	    }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			DriverScript.bResult = false;
			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
	    }
	}
	
	public static LocalDate getDate() {
		LocalDate today = LocalDate.now();
		return today;
	}
	
	/**
	 * This method verifies if an element is being displayed and returns true/false
	 */
	public static boolean elementDisplayed(By xpath) {
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
	
	public static boolean waitForElementDisplayed(String data) throws Exception{
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
	
	public static boolean waitForElementDisplayed(By xpath) throws Exception{
		int i = 0;
		Log.info("Waiting for element to display");
		while (!elementDisplayed(xpath)&&(i <= 120)){
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
	
	public static boolean waitForListViewDisplayed(String object, String data) throws Exception{
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
	    	String path = "//table[contains(@summary,'"+object+"')]";
	    	waitForElementDisplayed(By.xpath(path));
	    	Assert.assertTrue(elementDisplayed(By.xpath(path)));
	    }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false;
			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
	    }
	}
	
    public static void elementClickable(String object){
		try{
			Log.info("Checks Webelement is clickable: "+ object);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
		 }catch(Exception e){
 			Log.error("Exception --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
         }
	}
    
    public static void waitConfirmation(String object, String data){
		try{
			Log.info("waitConfirmation: "+ object);
			WebDriverWait wait = new WebDriverWait(driver, 3000);
	        wait.until(ExpectedConditions.alertIsPresent());
//	        driver.switchTo().alert().accept();
		 }catch(Exception e){
 			Log.error("Exception --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
 			DriverScript.bResult = false;
 			Assert.fail();
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
			
			waitForElementDisplayed(object);
			Log.info("Entering the text in " + object);
			click(object);
			clear(object);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
		 }catch(Exception e){
			Log.error("Not able to Enter value --- " + e.getMessage());
			extentTest.log(LogStatus.ERROR, e.getMessage());
			DriverScript.bResult = false;
			Assert.fail();
		 }
	}
	
	public static void clear(String object, String data){
		try{
			waitForElementDisplayed(object);
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
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
			Log.error("Exception switchWindow --- " + e.getMessage());
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
			DriverScript.bResult = false;	
		}catch(Exception e){
	 		Log.error("Exception Verify Alert: " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, e.getMessage());
	 		DriverScript.bResult = false;
	    }
	}
		
	public static void verifyAlert(String msg){
		try{
			String element = "msg_Popup";
			waitForElementDisplayed(element);
			if(isAlertPresent()){
	            driver.switchTo().alert();
	            driver.switchTo().alert().accept();
	            driver.switchTo().defaultContent();
	            Log.info("Alert: " + driver.switchTo().defaultContent());
	        }
			Log.info("get assertValue object.." +driver.findElement(By.xpath(OR.getProperty(element))).getText());
			Log.info("get assertValue data.." +msg);
			Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty(element))).getText().contains(msg), "Assertion failed.");
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + msg.toUpperCase().trim());
			DriverScript.bResult = false;	
		}catch(Exception e){
	 		Log.error("Exception Verify Alert: " + e.getMessage());
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
	
//    public static void clickOK(String object, String data){
//        try{
//        	driver.switchTo().alert();
//            driver.switchTo().alert().accept();
//        }
//        catch(Exception e){
////TODO
//        }   
//    }
    
    public static void clickNew(){
        try{
        	click("btn_New");
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
			DriverScript.bResult = false;	
			Assert.fail();
		}catch(Exception e){
			 Log.error(e.getMessage());
			 extentTest.log(LogStatus.ERROR, e.getMessage());
			 DriverScript.bResult = false;
			 Assert.fail();
		}
	}
	
	public static void openWFAssignment(String object, String data){
		try{
			String val = DriverScript.storedValue;
			String path = "//table[contains(@summary,'Inbox / Assignments')]/tbody/tr";
			Log.info("Stored value = " + val);
			Log.info("openWFAssignment");
//			waitForElementDisplayed();
			waitForElementDisplayed(By.xpath(path));
			int rowCount = (driver.findElements(By.xpath(path)).size());
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
						waitFor();
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
		}catch(Exception e){
			 Log.error("Exception WFAssignment not found --- " + e.getMessage());
			 extentTest.log(LogStatus.ERROR, e.getMessage());
			 DriverScript.bResult = false;
			 Assert.fail();
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
				DriverScript.bResult = false;
				Assert.fail();
			 }catch(Exception e){
				Log.error("Exception Table does not exists --- " +e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
				Assert.fail();
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
				DriverScript.bResult = false;
				Assert.fail();
		  }catch(Exception e){
				Log.error("Exception Check status exists --- " + e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
				Assert.fail();
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
				DriverScript.bResult = false;
				Assert.fail();
		 }catch(Exception e){
			    Log.error("Exception Check value exists --- " + e.getMessage());
			    extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
				Assert.fail();
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
				DriverScript.bResult = false;
				Assert.fail();
		 }catch(Exception e){
			    Log.error("Exception Check value exists --- " + e.getMessage());
			    extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
				Assert.fail();
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
				DriverScript.bResult = false;	
				Assert.fail();
          }catch(Exception e){
				Log.error("Exception check topmost drilldown --- " +e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
				Assert.fail();
		 }
	  }
	  
	  public static void checkColumnExists(String object, String data){
		  try{
			    List<String> colToCheck =  new ArrayList<String>();
			    colToCheck = Arrays.asList(object.trim().toUpperCase().split("[\\s]*,[\\s]*"));
			    String path = "//tr[@id='lookup_page1_tbod_ttrow-tr']/th[contains(@id,'lookup_page1_ttrow_[C:')]";
			    waitForElementDisplayed(By.xpath(path));
				List<WebElement> ddOpts = driver.findElements(By.xpath(path));
				
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
				DriverScript.bResult = false;
				Assert.fail();
			 }catch(Exception e){
				Log.error("Exception Check column exists ---" + e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
				Assert.fail();
			 }
	  }
	  
	  public static void checkColumnExistsListView(String object, String data){
		  try{
			    List<String> colToCheck =  new ArrayList<String>();
			    colToCheck = Arrays.asList(object.trim().toUpperCase().split("[\\s]*,[\\s]*"));
			    String xpath = "//tr[contains(@id,'_tbod_ttrow-tr')]/th[contains(@id,'_ttrow_[C:')]";
			    waitForElementDisplayed(By.xpath(xpath));
				List<WebElement> ddOpts = driver.findElements(By.xpath(xpath));
				
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
				DriverScript.bResult = false;
				Assert.fail();
			 }catch(Exception e){
				Log.error("Exception Check column exists ---" + e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
				Assert.fail();
			 }
	  }
	  
//	  this method is duplicate of checkColumnExistsListView - TODO check for files referencing this method and change before deleting
	  public static void checkColumnExists1(String object, String data){
		  try{
			    List<String> colToCheck =  new ArrayList<String>();
			    colToCheck = Arrays.asList(object.trim().toUpperCase().split("[\\s]*,[\\s]*"));
			    String xpath = "//tr[contains(@id,'_tbod_ttrow-tr')]/th[contains(@id,'_ttrow_[C:')]";
			    waitForElementDisplayed(By.xpath(xpath));
				List<WebElement> ddOpts = driver.findElements(By.xpath(xpath));
				
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
				DriverScript.bResult = false;
				Assert.fail();
			 }catch(Exception e){
				Log.error("Exception Check column exists ---" + e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
				Assert.fail();
			 }
	  }
	  
	  public static void checkTableColumnExists(String object, String data){
		  try{
			    List<String> colToCheck =  new ArrayList<String>();
			    colToCheck = Arrays.asList(object.trim().toUpperCase().split("[\\s]*,[\\s]*"));
			    String xpath = "//table[contains(@summary,'"+data+"')]/tbody/tr[1]/th[contains(@id,'_ttrow_[C:')]";
			    waitForElementDisplayed(By.xpath(xpath));
				List<WebElement> ddOpts = driver.findElements(By.xpath(xpath));

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
				DriverScript.bResult = false;
				Assert.fail();
			 }catch(Exception e){
				Log.error("Exception checkTableColumnExists ---" + e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
				Assert.fail();
			 }
	  }
	  
	  public static void fieldExists(String object, String data){
		 try{
			String xpath = "//label[contains(., '"+object+"')]";
		    waitForElementDisplayed(By.xpath(xpath));
   		    Log.info("fieldExists = " + By.xpath(xpath).toString());
			if (data.toLowerCase().equals("true")) {
			    Assert.assertTrue(existsElement(By.xpath(xpath)));
			} else {                                         
				Assert.assertFalse(existsElement(By.xpath(xpath)));
			}                                                          
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			DriverScript.bResult = false;
			Assert.fail();
		 }catch(Exception e){
			Log.error("Exception Check field exists ---" + e.getMessage());
			extentTest.log(LogStatus.ERROR, e.getMessage());
			DriverScript.bResult = false;
			Assert.fail();
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
			  waitForElementDisplayed(object);
			  driver.findElement(By.id(OR.getProperty(object))).getText();
		  } catch(Exception e){
				 Log.error("Exception copyMessage --- " +e.getMessage());
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
	  public static void scrollDown(String object) throws Exception {
		  Log.info("scrolling down to field..");
		  waitForElementDisplayed(object);
		  WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
		  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		  Thread.sleep(5000); 
	  }
	  
	  public static void scrollDown(String object, String data) throws Exception {
		  Log.info("scrolling down to field..");
		  waitForElementDisplayed(object);
		  WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
		  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		  Thread.sleep(5000); 
	  }
	  
	  public static void scrollUp(String object, String data) throws Exception {
		  Log.info("scrolling up to field..");
		  waitForElementDisplayed(object);
		  WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
		  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		  Thread.sleep(500); 
	  }
	  
//	  public static void scrollRight(String object, String data) throws Exception {
//		  Log.info("scrolling right to field..");
//		  WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
//		  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
//		  Thread.sleep(5000); 
//	  }
//	  
//	  public static void scrollLeft(String object, String data) throws Exception {
//		  Log.info("scrolling left to field..");
//		  WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
//		  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
//		  Thread.sleep(5000); 
//	  }
//	  
	  public static void routeWF(String object, String data){
		  try{
			  waitForElementDisplayed("btn_Route");
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
			  Log.error("Exception route WF --- " +e.getMessage());
			  extentTest.log(LogStatus.ERROR, e.getMessage());
			  DriverScript.bResult = false;
			  Assert.fail();
		  }
	  }	
	  
	  public static void stopWF(String object, String data){
		  try{
			  waitForElementDisplayed("hvr_Workflow");
			  driver.findElement(By.xpath(OR.getProperty("hvr_Workflow"))).click();
			  driver.findElement(By.xpath(OR.getProperty("stop_Workflow"))).click();
			  driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			  waitFor();			  
		  } catch(Exception e){
			  Log.error("Exception stop WF --- " +e.getMessage());
			  extentTest.log(LogStatus.ERROR, e.getMessage());
			  DriverScript.bResult = false;
			  Assert.fail();
		  }
	  }	
	  
	  public static void stopWF(){
		  try{
			  waitForElementDisplayed("hvr_Workflow");
			  driver.findElement(By.xpath(OR.getProperty("hvr_Workflow"))).click();
			  driver.findElement(By.xpath(OR.getProperty("stop_Workflow"))).click();
			  driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			  waitFor();			  
		  } catch(Exception e){
			  Log.error("Exception stop WF --- " +e.getMessage());
			  extentTest.log(LogStatus.ERROR, e.getMessage());
			  DriverScript.bResult = false;
			  Assert.fail();
		  }
	  }	
	  
	  public static void createSR(String object, String data){
		  try{
			  Log.info("Create SR....");
			  waitForElementDisplayed("btn_New");
			  click("btn_New");
			  input("txtbx_ReporterType", data.trim());
			  input("txtbx_Summary", object.trim());
			  input("txtbx_AssetNum", "1000014");
			  input("txtbx_Priority", "4");
			  input("txtbx_ActivityType", "Destress");
			  save(null, null);
		  } catch(Exception e){
			  Log.error("Exception Create SR --- " +e.getMessage());
			  extentTest.log(LogStatus.ERROR, e.getMessage());
			  DriverScript.bResult = false;
			  Assert.fail();
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
		      
		      waitForElementDisplayed("btn_New");
			  click("btn_New");
			  waitForElementDisplayed("txtbx_AssetNum");			  
			  input("txtbx_AssetNum", "1000014");
			  input("txtbx_Description", object.trim());
			  input("txtbx_Worktype", data.trim());
			  click("txtbx_ActivityType");
			  input("txtbx_ActivityType", activityType);
			  input("txtbx_Priority", priority);
			  input("txtbx_FinancialYear", String.valueOf(ngayon.getYear()));
			  input("txtbx_ScheduledStart", next2Week.toString());
			  save(null, null);
		  } catch(AssertionError ae){
			  Log.error(ae.getMessage());
			  extentTest.log(LogStatus.ERROR, ae.getMessage());
			  Assert.fail();
		  } catch(Exception e){
			  Log.error("Exception CreateWO --- " +e.getMessage());
			  extentTest.log(LogStatus.ERROR, e.getMessage());
			  DriverScript.bResult = false;
			  Assert.fail();
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
		      
		      waitForElementDisplayed("btn_New");
		      click("btn_New");
			  waitForElementDisplayed("txtbx_AssetNum");		  
			  input("txtbx_AssetNum", "1000047");
			  input("txtbx_Description", object.trim());
			  input("txtbx_Worktype", data.trim());
			  
			  WebDriverWait wait = new WebDriverWait(driver, 15);
			  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty("txtbx_ActivityType"))));
			    
			  click("txtbx_ActivityType");
			  
			  System.out.println("Click txtbx_ActivityType");
			  input("txtbx_ActivityType", activityType);
			  
			  System.out.println("SendKeys txtbx_ActivityType");
			  input("txtbx_Priority", priority);
			  input("txtbx_FinancialYear", String.valueOf(ngayon.getYear()));
			  input("txtbx_ScheduledStart", next2Week.toString());
			  input("txtbx_StartRefPoint", "12");
			  input("txtbx_EndRefPoint", "12");
			  input("txtbx_StartRefPointOffset", "0");
			  input("txtbx_EndRefPointOffset", "1");
			  save(null, null);
		  } catch(Exception e){
			 Log.error("Exception CreateWO --- " +e.getMessage());
			 extentTest.log(LogStatus.ERROR, e.getMessage());
			 DriverScript.bResult = false;
			 Assert.fail();
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
		      
		      waitForElementDisplayed("btn_New");
		      clickNew();
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
			  save(null, null);
			  Log.info("Generated WONUM = " + getAttributeValue("txtbx_WONUM"));
				 
			  click("tab_Plans", null);
			  waitForElementDisplayed("btn_NewRow_Labour");
//			  add labour plan
			  click("btn_NewRow_Labour", null);
			  waitForElementDisplayed("txtbx_Trade");
			  input("txtbx_Trade", "LEVEL1");
			  input("txtbx_Quantity", "2");
			  input("txtbx_PersonHours", "5");
			  Log.info("Added labour plan...");
//			  add material plan
			  click("tab_Materials", null);
			  waitForElementDisplayed("btn_NewRow_Materials");
			  click("btn_NewRow_Materials", null);
			  waitForElementDisplayed("txtbx_Item");
//			  item 1027419 cost = 788.34
			  input("txtbx_Item", "1027419");
			  input("txtbx_Quantity", "2");
			  getStoreroom();
			  input("txtbx_DeliveryAddress", "Dummy address");
			  save(null, null);

			  Log.info("Added material plan...");
//			  Go back to Labour Plan
			  click("tab_Labour", null);
			  waitFor();
		  } catch(Exception e){
				 Log.error("Exception CreateWOwithPlans --- " +e.getMessage());
				 extentTest.log(LogStatus.ERROR, e.getMessage());
				 DriverScript.bResult = false;
				 Assert.fail();
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
			
		      waitForElementDisplayed("btn_New");
		      click("btn_New");
			  waitForElementDisplayed("txtbx_AssetNum");
			  input("txtbx_AssetNum", "1000014");
			  input("txtbx_Description", object.trim());
			  input("txtbx_Worktype", data.trim());
			  click("txtbx_ActivityType");
			  input("txtbx_ActivityType", activityType);
			  input("txtbx_Priority", priority);
			  input("txtbx_FinancialYear", String.valueOf(ngayon.getYear()));
			  input("txtbx_ScheduledStart", next2Week.toString());

			  Log.info("Generated WONUM = " + getAttributeValue("txtbx_WONUM"));
				 
//			  driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
//			  Log.info("Saving...");
			  click("tab_Plans");
			  waitForElementDisplayed("tab_Materials");
//		      add materialplan
			  click("tab_Materials");
			  waitForElementDisplayed("btn_NewRow_Materials");
			  click("btn_NewRow_Materials");
//			  item 1027419 cost = 788.34
			  waitForElementDisplayed("btn_LineType");
			  click("btn_LineType");
			  waitForElementDisplayed("option_Material");
			  click("option_Material");
			  waitForElementDisplayed("txtbx_ItemDescription");
			  clear("txtbx_ItemDescription");
			  input("txtbx_ItemDescription", "material");
			  clear("txtbx_OrderUnit");
			  input("txtbx_OrderUnit", "EA");
			  clear("txtbx_UnitCost");
			  input("txtbx_UnitCost", "10");
			  input("txtbx_CommodityGroup", "1000MAT");
			  input("txtbx_CommodityCode", "8031");
			  click("btn_Vendor_chevron");
			  waitForElementDisplayed("lnk_SelectValue");
			  click("lnk_SelectValue");
			  waitForElementDisplayed("lnk_List_Code");
			  click("lnk_List_Code");
			  Log.info("Added material plan...");
//			  Go back to Labour Plan
//			  driver.findElement(By.xpath(OR.getProperty("tab_Labour"))).click();
			  save();
		  } catch(Exception e){
			 Log.error("Exception createWOwithMaterialPlans --- " +e.getMessage());
			 extentTest.log(LogStatus.ERROR, e.getMessage());
			 DriverScript.bResult = false;
			 Assert.fail();
		  }
     }	
	  
	  public static void createPO(String object, String data){
		  try{
			  waitForElementDisplayed("btn_New");
			  click("btn_New");
			  waitForElementDisplayed("txtbx_Description");			   
			  input("txtbx_Description", data.trim());
			  input("txtbx_Company", "DUMMY");
			  save();
		  } catch(Exception e){
				 Log.error("Exception Create PO --- " +e.getMessage());
				 extentTest.log(LogStatus.ERROR, e.getMessage());
				 DriverScript.bResult = false;
				 Assert.fail();
		  }
	  }
	  
//	  TODO - not tested
	  public static void createPR(String object, String data){
		  try{
			  waitForElementDisplayed("btn_New");
			  click("btn_New");
			  waitForElementDisplayed("txtbx_Description");			   
			  input("txtbx_Description", data.trim());
			  input("txtbx_Company", "706170");
			  save();
		  } catch(Exception e){
			  Log.error("Exception Create PR --- " +e.getMessage());
			  extentTest.log(LogStatus.ERROR, e.getMessage());
			  DriverScript.bResult = false;
			  Assert.fail();
		  }
	  }
	  
	  public static void createAsset(String object, String data){
		  try{
			  waitForElementDisplayed("btn_New");
			  Log.info("Creating Asset");
			  click("btn_New");
			  waitForElementDisplayed("txtbx_Discipline");			   
			  driver.findElement(By.xpath("//input[contains(@aria-label, 'Asset description')]")).sendKeys(data.trim());
			  input("txtbx_Discipline", "TRACK");
			  input("txtbx_Region", "Central");
			  input("txtbx_Area", "Wellington");
			  input("txtbx_Line", "JVILL");
			  input("txtbx_StartMetrage", "95.500");
			  input("txtbx_EndMetrage", "96.600");
			  save();
			  assertValue1("Start Metrage", "95.500");
			  assertValue1("End Metrage", "96.600");
		  }catch(AssertionError ae){
			  Log.error("Assertion failed --- " + ae.getMessage());
			  extentTest.log(LogStatus.ERROR, ae.getMessage());
			  extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			  DriverScript.bResult = false;
			  Assert.fail();
		  } catch (NoSuchElementException e) {
		      Log.error("Element not found --- " + e.getMessage());
		      extentTest.log(LogStatus.ERROR, e.getMessage());
	 		  DriverScript.bResult = false;
	 		 Assert.fail();
		  } catch (Exception e) {
		      Log.error("Exception Create Asset --- " + e.getMessage());
		      extentTest.log(LogStatus.ERROR, e.getMessage());
	 		  DriverScript.bResult = false;
	 		 Assert.fail();
		  }
	  }
	  
	  public static void createLocation(String data){
		  try {
//				Create new location
			    waitForElementDisplayed("btn_New");
			    Log.info("Creating Location");
			    click("btn_New");
				waitForElementDisplayed("txtbx_Type");
				input("txtbx_Type", "OPERATING");
				driver.findElement(By.xpath("//input[contains(@aria-label, 'Location description')]")).sendKeys(data.trim());
				input("txtbx_Discipline", "TRACK");
				input("txtbx_Region", "Central");
				input("txtbx_Area", "Wellington");
				input("txtbx_Line", "JVILL");
				input("txtbx_StartMetrage", "97.569");
				input("txtbx_EndMetrage", "98.568");
				save();
				assertValue1("Start Metrage", "97.569");
				assertValue1("End Metrage", "98.568");
			}catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				DriverScript.bResult = false;
				Assert.fail();
		    } catch (NoSuchElementException e) {
		    	Log.error("Element not found --- " + e.getMessage());
		    	extentTest.log(LogStatus.ERROR, e.getMessage());
	 			DriverScript.bResult = false;
	 			Assert.fail();
		    } catch (Exception e) {
		    	Log.error("Exception Create Location --- " + e.getMessage());
		    	extentTest.log(LogStatus.ERROR, e.getMessage());
	 			DriverScript.bResult = false;
	 			Assert.fail();
		    }	
	  }
	  
	  public static void createClassification(String object, String data){
		  try {
//				Create new Classification
			    waitForElementDisplayed("btn_New");
			    Log.info("Creating Classification");
			    click("btn_New");
				waitForElementDisplayed("txtbx_Type");
				input("txtbx_Type", "OPERATING");
				driver.findElement(By.xpath("//input[contains(@aria-label, 'Location description')]")).sendKeys(data.trim());
				input("txtbx_Discipline", "TRACK");
				input("txtbx_Region", "Central");
				input("txtbx_Area", "Wellington");
				input("txtbx_Line", "JVILL");
				input("txtbx_StartMetrage", "97.569");
				input("txtbx_EndMetrage", "98.568");
				save();
				assertValue1("Start Metrage", "97.569");
				assertValue1("End Metrage", "98.568");
			}catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				DriverScript.bResult = false;
				Assert.fail();
		    } catch (NoSuchElementException e) {
		    	Log.error("Element not found --- " + e.getMessage());
		    	extentTest.log(LogStatus.ERROR, e.getMessage());
	 			DriverScript.bResult = false;
	 			Assert.fail();
		    } catch (Exception e) {
		    	Log.error("Exception Create Location --- " + e.getMessage());
		    	extentTest.log(LogStatus.ERROR, e.getMessage());
	 			DriverScript.bResult = false;
	 			Assert.fail();
		    }	
	  }
	  
	  public static void createJobPlan(String object, String data){
		  try{
			  Log.info("Create Job Plan...");
			  waitForElementDisplayed("btn_New");
			  click("btn_New");
			  waitForElementDisplayed("btn_NewRow_Labour");		   
			  driver.findElement(By.xpath("//input[@id=(//label[text()='Job Plan description']/@for)]")).sendKeys(data);
			  click("btn_NewRow_Labour");
			  waitForElementDisplayed("txtbx_Trade");
			  input("txtbx_Trade", "LEVEL1");
			  input("txtbx_Quantity", "2");
			  input("txtbx_PersonHours", "5");
			  Log.info("Added labour plan...");
//			  add material plan
			  click("tab_Materials");
			  waitForElementDisplayed("btn_NewRow_Materials");
			  click("btn_NewRow_Materials");
			  waitForElementDisplayed("txtbx_Item");
			  input("txtbx_Item", "1027419");
			  input("txtbx_Quantity", "2");
			  input("txtbx_Storeroom", "W600");
//			  driver.findElement(By.xpath(OR.getProperty("txtbx_ConditionCode"))).sendKeys("NEW");
			  Log.info("Added material plan...");
//			  Go back to Labour Plan
			  waitForElementDisplayed("tab_Labour");
			  click("tab_Labour");
			  save();
		  } catch(Exception e){
				 Log.error("Exception Create JobPlan --- " +e.getMessage());
				 extentTest.log(LogStatus.ERROR, e.getMessage());
				 DriverScript.bResult = false;
				 Assert.fail();
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
			  waitForElementDisplayed(object);
			  String objectVal = getAttributeValue(object);
//			  Log.info("objectVal = "+objectVal);
//			  Log.info("data = "+data);
			  double objectInt = StringUtil.toLong(objectVal);
			  double dataInt = Double.valueOf(data);
			  double newVal = (objectInt * dataInt) + objectInt;
			  clear(object);
			  Log.info("Cleared object");
			  input(object, String.valueOf(newVal));
		  } catch(Exception e){
				 Log.error("Exception Add value --- " +e.getMessage());
				 extentTest.log(LogStatus.ERROR, e.getMessage());
				 DriverScript.bResult = false;
				 Assert.fail();
		  }
	  }
 
	  public void changeStatus(String object, String data){
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
			  String statusOption = "//li[contains(@id,'_STATUS_OPTION')]";
			  waitForElementDisplayed(By.xpath(statusOption));	
              oldStatus = getAttributeValue(object);  
			  driver.findElement(By.xpath(statusOption)).click();
			  
			  String newStatus = "//input[@id=(//label[text()='New Status:']/@for)]";
			  waitForElementDisplayed(By.xpath(newStatus));
			  driver.findElement(By.xpath(newStatus)).click();
              Thread.sleep(500);
			  Log.info("clicked new status Drop-down ");
              driver.findElement(By.xpath("//span[contains(@id,'"+status+"_OPTION_a_tnode')]")).click();
              waitFor();
              Log.info("clicked new status "+status);
              waitForElementDisplayed("btn_OK");
              click("btn_OK");
              
//              if status is changed to completed 
              if (status == "COMP") {
            	  action.verifyAlert("Status change(s) completed successfully.");
            	  driver.switchTo().activeElement().click();
              } else {
            	  waitForElementDisplayed("titlebar_message");
//            	  Log.info("titlebar msg = " +driver.findElement(By.xpath(OR.getProperty("titlebar_message"))).getText());
//            	  Log.info("changeStatusMsg = " +changeStatusMsg);
            	  Assert.assertTrue(waitForElementDisplayed("titlebar_message"));
              }
			  Log.info("old status = " +oldStatus);
			  Log.info("new status = " +getAttributeValue(object));
			  Assert.assertFalse(getAttributeValue(object).trim().toUpperCase().equals(oldStatus), "Assertion failed - status not changed");
		  }catch(AssertionError ae){
				Log.error("Assertion failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				DriverScript.bResult = false;	
				Assert.fail();
		  } catch(Exception e) {
				 Log.error("Exception changeStatus --- " +e.getMessage());
				 extentTest.log(LogStatus.ERROR, e.getMessage());
				 DriverScript.bResult = false;
				 Assert.fail();
		  }
     }	
	  
	  public static void duplicate(String object, String data){
			try{
				waitForElementDisplayed("btn_Duplicate");
				click("btn_Duplicate");
				waitForElementDisplayed("titlebar_message");
				
//				Log.info("titlebar_message path =  "+ OR.getProperty("titlebar_message"));
//				Log.info(driver.findElement(By.xpath(OR.getProperty("titlebar_message"))).getText());
				if(waitForElementDisplayed("titlebar_message")){
					Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty("titlebar_message"))).getText().trim().startsWith(duplicateMsg), "Assertion failed.");
					save();
				}
			}catch(AssertionError ae){
				Log.error("duplicate failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				DriverScript.bResult = false;	
				Assert.fail();
			}catch(Exception e){
		 		Log.error("Exception duplicate failed --- " + e.getMessage());
		 		extentTest.log(LogStatus.ERROR, e.getMessage());
		 		DriverScript.bResult = false;
		 		Assert.fail();
		    }
	  }
	  
	  public static void duplicatePR(String object, String data){
			try{
				waitForElementDisplayed("btn_Duplicate");
				click("btn_Duplicate");
				waitForElementDisplayed("titlebar_message");
				
				if(waitForElementDisplayed("titlebar_message")){
					Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty("titlebar_message"))).getText().trim().startsWith(duplicateMsg), "Assertion failed.");
					if (object.equals("PR") && !data.equals("")) {
						input("txtbx_RequiredDate", data);
					} else {
						save();
					}
				}
			}catch(AssertionError ae){
				Log.error("duplicate failed --- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				DriverScript.bResult = false;	
				Assert.fail();
			}catch(Exception e){
		 		Log.error("Exception --- " + e.getMessage());
		 		extentTest.log(LogStatus.ERROR, e.getMessage());
		 		DriverScript.bResult = false;
		 		Assert.fail();
		    }
	  }
	  
	  public static void save() {
		  save(null, null);
	  }

	  public static void save(String object, String data){
			try{
				waitForElementDisplayed("btn_Save");
				click("btn_Save");
				waitForElementDisplayed("titlebar_message");
				
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
				DriverScript.bResult = false;
				Assert.fail();
			 }catch(Exception e){
		 		Log.error("Exception --- " + e.getMessage());
		 		extentTest.log(LogStatus.ERROR, e.getMessage());
		 		DriverScript.bResult = false;
		 		Assert.fail();
		     }
	}  
	  
	public static void getStoreroom() {
		try {
			waitForElementDisplayed("btn_Storeroom_chevron");
			click("btn_Storeroom_chevron");
			click("lnk_SelectValue");
			waitForElementDisplayed("lnk_List_Code");
			click("lnk_List_Code");
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
		    	String xpath = "//table[contains(@summary,'"+dir+"')]//tr[contains(@class,'tablerow')]";  	
		    	waitForElementDisplayed(By.xpath(xpath));
			    List<WebElement> ddOpts = driver.findElements(By.xpath(xpath));
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
				DriverScript.bResult = false;
				Assert.fail();
			 }catch(Exception e){
				Log.error("Exception Table does not exists --- " +e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				DriverScript.bResult = false;
				Assert.fail();
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
			
			String code = getAttributeValue(object);
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
			  String advancedSearchMenu = "//img[@alt='Advanced Search menu']";
			  String searchOption = "//span[@id='menu0_SEARCHWHER_OPTION_a_tnode']";
			  waitForElementDisplayed(By.xpath(advancedSearchMenu));
			  driver.findElement(By.xpath(advancedSearchMenu)).click();
			  waitForElementDisplayed(By.xpath(searchOption));
			  driver.findElement(By.xpath(searchOption)).click();
			  waitForElementDisplayed("txtarea_whereClause");
			  clear("txtarea_whereClause");
			  input("txtarea_whereClause", data.trim());
			  waitForElementDisplayed("btn_Find");
			  click("btn_Find");
			  waitFor();
		  } catch(Exception e){
			  Log.error("Exception whereClause --- " +e.getMessage());
			  extentTest.log(LogStatus.ERROR, e.getMessage());
			  DriverScript.bResult = false;
			  Assert.fail();
		  }
      }	
	  
	  public static void clearWhereClause(String object, String data){
		  try{       
			  waitForElementDisplayed("btn_Advanced_Search");
			  click("btn_Advanced_Search");
			  click("btn_Revise");
			  driver.findElement(By.xpath("//span[contains(.,'Clear Query and Fields')]")).click();
//			  waitFor();
		  } catch(Exception e){
			  Log.error("clearWhereClause --- " +e.getMessage());
			  extentTest.log(LogStatus.ERROR, e.getMessage());
			  DriverScript.bResult = false;
			  Assert.fail();
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
			  Assert.fail();
		  }
     }	
	  
	  public static String getAttributeValue(String element) {
		String value = null;
	  	try {
	  		waitForElementDisplayed(element);
	  		value = driver.findElement(By.xpath(OR.getProperty(element))).getAttribute("value");
	  	} catch(Exception e){
 			Log.error(e.getMessage());
 			extentTest.log(LogStatus.ERROR, e.getMessage());
       	}
		return value;
	  }
	  
	  public static String getAttributeValue(By xpath) {
			String value = null;
		  	try {
		  		waitForElementDisplayed(xpath);
		  		value = driver.findElement(xpath).getAttribute("value");
		  	} catch(Exception e){
	 			Log.error(e.getMessage());
	 			extentTest.log(LogStatus.ERROR, e.getMessage());
	       	}
			return value;
		  }
 
	  public void changeWOStatusToPlan(String object, String data){
			try{
				Log.info("Change WO status to PLAN = "+object +" " +data);
				String user="mxeng1";
				if (data.equals("AMREN")) {
					user="mxsvcemgr1";
				}
				
				openBrowser("1","Chrome");
				login(null, user);
				action.goToWOPage(); 
				action.quickSearch(object);

				changeStatus("txtbx_Status", "ready to plan");
				changeStatus("txtbx_Status", "planned");
				save();
			 }catch(Exception e){
	 			Log.error("Not able to changeWOStatusToPlan --- " + e.getMessage());
	 			extentTest.log(LogStatus.ERROR, e.getMessage());
	 			DriverScript.bResult = false;
	 			Assert.fail();
	       	 }
	   }
	  
	  public void goToHomePage() {
			try {
				waitForElementDisplayed("lnk_Home");
				click("lnk_Home");
			} catch (Exception e) {
				Log.error("goToWOPage --- " + e.getMessage());
	 			extentTest.log(LogStatus.ERROR, e.getMessage());
	 			Assert.fail();
			}
			
	  }
	  
	  public void goToWorkflowAssignment() {
			try {
				waitForElementDisplayed("hvr_Workflow");
				scrollDown("hvr_Workflow");
				hover("hvr_Workflow", "lnk_WFAssignment");
				waitForElementDisplayed("tbl_WFAssignment");
			} catch (Exception e) {
				Log.error("goToWOPage --- " + e.getMessage());
	 			extentTest.log(LogStatus.ERROR, e.getMessage());
	 			Assert.fail();
			}
			
	  }
	  
	  public void approveWF(String wonum) {
		try { 
		  action.goToWOPage();
		  action.quickSearch(wonum);
		  
		  boolean inWorkflow = true;
		  int wfLevel = 1;
		  
		  action.hover("hvr_Workflow", "lnk_WFAssignment");
		 
  		  action.waitForElementDisplayed("tbl_WFAssignment");

		  inWorkflow = driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0");
		  DriverScript.WFApprover = driver.findElement(By.xpath("//table[contains(@summary,'Workflow Assignments')]/tbody/tr[4]/td[2]")).getText();
		  
		  Log.info("IsNull table approver =" +driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0"));
		  action.clickOK();
		  
		  while (!inWorkflow) {
//			  close current driver
			  driver.close();
			  action.openBrowser(null, "Chrome");
			  action.loginApprover("dummy", "dummy");
			  
			  Log.info("WF Approval Level " +wfLevel+ " Approver = "+DriverScript.WFApprover);
			  action.openWFAssignment("dummy", "dummy");
//			  waitFor();
			  action.waitForElementDisplayed("btn_OK");
			  action.clickOK();

			  action.hover("hvr_Workflow", "lnk_WFAssignment");
			  action.waitForElementDisplayed("tbl_WFAssignment");
			  inWorkflow = driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0");
			  if (!inWorkflow) {
				  DriverScript.WFApprover = driver.findElement(By.xpath("//table[contains(@summary,'Workflow Assignments')]/tbody/tr[4]/td[2]")).getText();
				  Log.info("IsNull table approver =" +driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0"));
				  
			  }
			  action.clickOK();
			  wfLevel++;
		  }
		  action.assertValue2("txtbx_Status", "APPR,WMATL");
	    } catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
			Assert.fail();
	    }	
   }	  
	  
	  public void deactivateVendor(String vendor, boolean status) {
		try {
			boolean vendorStatus = false;
 
			Log.info("activateVendor Vendor..................");
			openBrowser("1","Chrome");
			login(null, "maxadmin");
			action.goToCompaniesPage();
			
			action.quickSearch(vendor);
			
			Log.info("Check chkbx_DisqualifyVendor..................");
			action.waitForElementDisplayed("chkbx_DisqualifyVendor"); 
			vendorStatus = action.isChecked("chkbx_DisqualifyVendor");
			
			Log.info("is DisqualifyVendor checked? = "+vendor +" " +vendorStatus);
			
			if (vendorStatus != status) {			
				action.click("chkbx_DisqualifyVendor");
				action.save();
			}	
			
			Log.info("is DisqualifyVendor checked = "+vendor +" " +action.isChecked("chkbx_DisqualifyVendor"));
			driver.close();
	    } catch (AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
			Assert.fail();
	    }	
	 }
  
	  public void goToWOPage() {
			try {
				goToHomePage();
				waitForElementDisplayed("hvr_WO");
				scrollDown("hvr_WO");
				hover("hvr_WO", "lnk_WO");
			} catch (Exception e) {
				Log.error("goToWOPage --- " + e.getMessage());
	 			extentTest.log(LogStatus.ERROR, e.getMessage());
	 			Assert.fail();
			}
			
	  }

	  public void goToSRPage() {
			try {
				goToHomePage();
				waitForElementDisplayed("hvr_WO");
				scrollDown("hvr_WO");
				hover("hvr_WO", "lnk_SR");
			} catch (Exception e) {
				Log.error("goToWOPage --- " + e.getMessage());
	 			extentTest.log(LogStatus.ERROR, e.getMessage());
	 			Assert.fail();
			}
			
	  }
	  public void goToAssetPage() {
			try {
				goToHomePage();
				waitForElementDisplayed("hvr_Asset");
				scrollDown("hvr_Asset");
				hover("hvr_Asset", "lnk_Asset");
			} catch (Exception e) {
				Log.error("goToWOPage --- " + e.getMessage());
	 			extentTest.log(LogStatus.ERROR, e.getMessage());
	 			Assert.fail();
			}
			
	  }
	  
	  public void goToFeaturePage() {
			try {
				goToHomePage();
				waitForElementDisplayed("hvr_Asset");
				scrollDown("hvr_Asset");
				hover("hvr_Asset", "lnk_Feature");
			} catch (Exception e) {
				Log.error("goToFeaturePage --- " + e.getMessage());
	 			extentTest.log(LogStatus.ERROR, e.getMessage());
	 			Assert.fail();
			}
			
	  }
	  
	  public void goToLocationPage() {
			try {
				goToHomePage();
				waitForElementDisplayed("hvr_Asset");
				scrollDown("hvr_Asset");
				hover("hvr_Asset", "lnk_Location");
			} catch (Exception e) {
				Log.error("goToLocationPage --- " + e.getMessage());
	 			extentTest.log(LogStatus.ERROR, e.getMessage());
	 			Assert.fail();
			}
			
	  }
	  
	  public void goToCompaniesPage() {
			try {
				goToHomePage();
				waitForElementDisplayed("hvr_Purchasing");
				scrollDown("hvr_Purchasing");
				hover("hvr_Purchasing", "lnk_Companies");
			} catch (Exception e) {
				Log.error("goToCompaniesPage --- " + e.getMessage());
	 			extentTest.log(LogStatus.ERROR, e.getMessage());
	 			Assert.fail();
			}
			
	  }
		
	  public void quickSearch(String q) throws Exception {
			waitForElementDisplayed("txtbx_QuickSearch");
			input("txtbx_QuickSearch", q);
			enter("txtbx_QuickSearch");
			Thread.sleep(3000);
		}
		
		public void clickFirstSearchedValue() {
			try {
				waitForElementDisplayed("lnk_List_Code");
				click("lnk_List_Code");
			} catch (Exception e) {
				Log.error("Not able to click --- " + e.getMessage());
	 			extentTest.log(LogStatus.ERROR, e.getMessage());
	 			extentTest.log(LogStatus.INFO, "Element: lnk_List_Code");
	 			Assert.fail();
			}
			
		} 
		
		public void route() {
			click("btn_Route");
		}
		
		public void clickOK() throws Exception {
			try {
				waitForElementDisplayed("btn_OK");
				click("btn_OK");
				Thread.sleep(2000);
			} catch (NoSuchElementException e) {
				Log.error("Element not found --- " + e.getMessage());
				extentTest.log(LogStatus.ERROR, e.getMessage());
				extentTest.log(LogStatus.INFO, "Element: btn_OK");
				Assert.fail();
			}
		}
		
		public void clickActiveElement() {
			driver.switchTo().activeElement().click();
		}	
		
//	  public void workorderWorkflow(String object, String data) {
//		  WorkorderWorkflow.workorderWorkflow(object, data);
//	  }
	  
//	  public static void workorderWorkflowWithChildren(String object, String data) {
//		  WorkorderWorkflowWithChildren.workorderWorkflowWithChildren(object, data);
//	  }  
	  
//	  public static void locationCustomMetrages(String object, String data) {
//		  LocationCustomMetrages.locationCustomMetrages(object, data);
//	  }
//	  
//	  public static void railWearMeterReadings(String object, String data) {
//		  RailWearMeterReadings.railWearMeterReadings(object, data);
//	  }
//
//	  public static void crossoverLocationsAsset(String object, String data) {
//		  CrossoverLocationsAsset1.crossoverLocationsAsset1(object, data);
//	  }
//	  
//	  public static void WOClassificationAttributes(String object, String data) {
//		  WOClassificationAttributes.woClassificationAttributes(object, data);
//	  }
	  
//	  public static void AssetFeatureDeletion(String object, String data) {
//		  AssetFeatureDeletion.assetFeatureDeletion(object, data);
//	  }
	  
//	  public static void PreventSRsAndWOsWhereNOTRACKexist(String object, String data) {
//		  PreventSRsAndWOsWhereNOTRACKexist.preventSRsAndWOsWhereNOTRACKexist();
//	  }
//	  
//	  public static void WOAssetLocRoute(String object, String data) {
//		  WOAssetLocRoute.woAssetLocRoute();
//	  }
//	  
//	  public static void WOTotalCosts(String object, String data) {
//		  WOTotalCosts.WOTotalCosts();
//	  }
//	  
//	  public static void ServiceItemPlannedCostChange(String object, String data) {
//		  ServiceItemPlannedCostChange.serviceItemPlannedCostChange();
//	  }
//	  
//	  public static void Classifications(String object, String data) {
//		  Classifications.classifications();
//	  }
//	  
//	  public static void LocationSystems(String object, String data) {
//		  LocationSystems.locationSystems();
//	  }
//	  
//	  public static void Vendor(String object, String data) {
//		  Vendor.vendor();;
//	  }
//	  
//	  public void UnitTests(String object, String data) throws Exception {
//		  WOUnitTests.unitTests();
//	  }
}