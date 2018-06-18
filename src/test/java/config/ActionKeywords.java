package config;

import java.io.File;
import java.io.FileWriter;
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
import static executionEngine.Base.bResult;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.util.StringUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import executionEngine.Base;
import utility.Constants;
import utility.Log;

public class ActionKeywords {

	static String duplicateMsg = "BMXAA4131E";
	static String saveMsg = "Record has been saved.";
	static String changeStatusMsg = "BMXAA4591I";
	static String wfStartMsg = "Process_KR_WO started.";
	static String wfStopMsg = "Process_KR_WO stopped.";
	
	WebDriverWait wait;
	
	public static LocalDate getDate() {
		LocalDate today = LocalDate.now();
		return today;
	}
	
	public ActionKeywords() {
//		woPage = new WOPage(driver);
	}
	
	@SuppressWarnings("deprecation")
	public void openBrowser(String browser) {		
		Log.info("Opening Browser...");
		try{	
			Log.info("Try opening browser");
			if(browser.equals("Mozilla")) {
				Log.info("Mozilla is the browser");
				System.setProperty("webdriver.gecko.driver", Constants.Path_Lib + "geckodriver.exe");
				
			    DesiredCapabilities cap = DesiredCapabilities.firefox();
			    cap.setBrowserName("firefox");
			    cap.setCapability("marionette", true);
			    
			    FirefoxProfile profile = new FirefoxProfile();
			    profile.setPreference("network.proxy.type", 4);
			    driver = new FirefoxDriver(profile);
//			    driver = new RemoteWebDriver(new URL("http://10.96.101.46:4444/wd/hub"), cap);
			
			} else if(browser.equals("IE")) {
				//Dummy Code, implement your own code
				driver=new InternetExplorerDriver();

			} else if(browser.equals("Chrome")) {
				Log.info("Chrome is the browser");
				System.setProperty("webdriver.gecko.driver", Constants.Path_Lib + "chromedriver.exe");
				
				ChromeOptions chromeOptions = new ChromeOptions();
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			    capabilities.setBrowserName("chrome");
			    
				driver=new ChromeDriver(chromeOptions);

			} else if(browser.equals("Chrome--headless")) {
				Log.info("Chrome is the browser--headless");
				System.setProperty("webdriver.gecko.driver", Constants.Path_Lib + "chromedriver.exe");
				
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("--headless");
				 
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			    capabilities.setBrowserName("chrome");
			    
				driver=new ChromeDriver(chromeOptions);
			}
			
			int implicitWaitTime=(1);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			
			wait = new WebDriverWait(driver, 60);
			navigate();
		}catch (Exception e){
			Log.info("Exception Not able to open the Browser --- " + e.getMessage());
			extentTest.log(LogStatus.ERROR, "Not able to open the Browser --- " +e.getMessage());
			bResult = false;
			Assert.fail();
		}
	}
	
	public void navigate() throws Exception {
		Log.info("Navigating to URL");
		driver.get(Constants.URL);
	}
	
	public void login(String user) throws Exception {
		String password = null;
		
		Log.info("Login: "+ user);
		if (user.equalsIgnoreCase("maxadmin")) {
			password="maxadmin";
		} else {
			password="Kiwirail123";
//				password="sirius";
		}
		Log.info("Password: "+ password);
		Thread.sleep(5000);
		driver.findElement(By.xpath(".//*[contains(@id, 'username')]")).sendKeys(user.trim());
		driver.findElement(By.xpath(".//*[contains(@id, 'password')]")).sendKeys(password);
		click("btn_LogIn");
	}
	
	public void loginApprover() throws Exception {
		driver.close();
		action.openBrowser("Chrome");
		login(Base.WFApprover.toLowerCase());
	}
	
	public void enter(String object) throws Exception {
		waitElementExists(object);
		driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
	}
	
	public void clear(String object) throws Exception {
		waitElementExists(object);
		driver.findElement(By.xpath(OR.getProperty(object))).clear();
	}
	
	public void click(String object) throws Exception {
		Log.info("Clicking on Webelement "+ object);
		waitElementExists(object);
		elementClickable(object);
		driver.findElement(By.xpath(OR.getProperty(object))).click();
        Thread.sleep(1000);
	}
	
	public void click(By object) throws Exception{
		Log.info("Clicking on Webelement "+ object.toString());
		waitElementExists(object);
		elementClickable(object);
		driver.findElement(object).click();
        Thread.sleep(1000);
	}
	
	public void hover(String mainMenu, String subMenu) throws Exception {
		Log.info("Hover on Webelement "+ mainMenu);
		waitElementExists(mainMenu);
		WebElement element = driver.findElement(By.xpath(OR.getProperty(mainMenu)));
        Actions action = new Actions(driver);	
        action.moveToElement(element).build().perform();
        click(subMenu);
	} 
	
	public void hover(String mainMenu, String subMenu1, String subMenu2) throws Exception {
		Log.info("Hover on Webelement "+ mainMenu);
		waitElementExists(mainMenu);
		Actions builder = new Actions(driver); 
        WebElement main = driver.findElement(By.xpath(OR.getProperty(mainMenu)));
        builder.moveToElement(main).build().perform();
        Thread.sleep(500); //add a wait
        WebElement sub1=  driver.findElement(By.xpath(OR.getProperty(subMenu1))); //Find the submenu
        builder.moveToElement(sub1).click().build().perform();
        click(subMenu2);
	} 
	
	
	public void storeValue(String object, String data) throws Exception{
		String code = data;
		Log.info("to store value : " + code);
		action.waitElementExists(object);
		if (code.equalsIgnoreCase("WONUM")) {
			Base.WONUM = getAttributeValue(object);
			Base.storedValue = Base.WONUM;
		} else if (code.equalsIgnoreCase("ASSETNUM")) {
			Base.ASSETNUM = getAttributeValue(object);
			Base.storedValue = Base.ASSETNUM;
		} else if (code.equalsIgnoreCase("PRNUM")) {
			Base.PRNUM = getAttributeValue(object);
			Base.storedValue = Base.PRNUM;
		} else if (code.equalsIgnoreCase("PONUM")) {
			Base.PONUM = getAttributeValue(object);
			Base.storedValue = Base.PONUM;
		} else if (code.equalsIgnoreCase("Storeroom")) {
			Base.Storeroom = getAttributeValue(object);
			Base.storedValue = Base.Storeroom;
		} else if (code.equalsIgnoreCase("PMNUM")) {
			Base.PMNUM = getAttributeValue(object);
			Base.storedValue = Base.PMNUM;
		} else if (code.equalsIgnoreCase("RouteNum")) {
			Base.RouteNum = getAttributeValue(object);
			Base.storedValue = Base.RouteNum;
		} else if (code.equalsIgnoreCase("JPNum")) {
			Base.JPNum = getAttributeValue(object);
			Base.storedValue = Base.JPNum;
		} else if (code.equalsIgnoreCase("Template")) {
			Base.Template = getAttributeValue(object);
			Base.storedValue = Base.Template;
		} else if (code.equalsIgnoreCase("SRNUM")) {
			Base.SRNUM = getAttributeValue(object);
			Base.storedValue = Base.SRNUM;
		} else if (code.equalsIgnoreCase("LOCATION")) {
			Base.LOCATION = getAttributeValue(object);
			Base.storedValue = Base.LOCATION;
		} else if (code.equalsIgnoreCase("POINT")) {
			Base.POINT = getAttributeValue(object);
			Base.storedValue = Base.POINT;
		} else if (code.equalsIgnoreCase("Vendor")) {
			Base.VENDOR = getAttributeValue(object);
			Base.storedValue = Base.VENDOR;
		} else if (code.equalsIgnoreCase("WFApprover")) {
			Base.WFApprover = driver.findElement(By.xpath("//table[contains(@summary,'Workflow Assignments')]/tbody/tr[4]/td[2]")).getText();
			Base.storedValue = Base.WFApprover;
		}
		Log.info("Base.storedValue : " + Base.storedValue);
	}
	
	public void retrieveValue(String object, String data) throws Exception {
		String code = data;
		
		if (code.equalsIgnoreCase("WONUM")) {
			input(object, Base.WONUM);
		} else if (code.equalsIgnoreCase("ASSETNUM")) {
			input(object, Base.ASSETNUM);
		} else if (code.equalsIgnoreCase("PRNUM")) {
			input(object, Base.PRNUM);
		} else if (code.equalsIgnoreCase("PONUM")) {
			input(object, Base.PONUM);
		} else if (code.equalsIgnoreCase("Storeroom")) {
			input(object, Base.Storeroom);
		} else if (code.equalsIgnoreCase("RouteNum")) {
			input(object, Base.RouteNum);
		} else if (code.equalsIgnoreCase("PMNUM")) {
			input(object, Base.PMNUM);
		} else if (code.equalsIgnoreCase("JPNum")) {
			input(object, Base.JPNum);
		} else if (code.equalsIgnoreCase("Template")) {
			input(object, Base.Template);
		} else if (code.equalsIgnoreCase("SRNUM")) {
			input(object, Base.SRNUM);
		} else if (code.equalsIgnoreCase("LOCATION")) {
			input(object, Base.LOCATION);
		} else if (code.equalsIgnoreCase("POINT")) {
			input(object, Base.POINT);
		} else if (code.equalsIgnoreCase("WFApprover")) {
			input(object, Base.WFApprover);
		} 
		
		Log.info("Base.retrievedValue : " +Base.storedValue);
	}

	/*
	 * Compare 2 values - accepts object OR name and expected value
	 */
	/* public static void assertValue(String object, String data){
		try{
			String ele = null;
			waitElementExists(object);
			if (driver.findElement(By.xpath(OR.getProperty(object))).getText() != null &&
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().equals("") && 
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().isEmpty()) {
				ele = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			} else {
				ele = getAttributeValue(object);
			}
			
			ele = StringUtils.remove(ele, ",");	
			data = StringUtils.remove(data, ",");	
			
			Thread.sleep(500);
			Assert.assertTrue(ele.toUpperCase().trim().equals(data.toUpperCase().trim()), "Object "+object+"; Expected="+data +" Actual="+ele);
		 } catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
		 	bResult = false;
		 	Assert.fail();
	     } catch(Exception e){
	 		Log.error("Exception assertValue --- " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, e.getMessage());
	 		bResult = false;
	 		Assert.fail();
	     } 
	} */
	
	/*
	 * Compare value list - accepts object OR name and expected value list
	 * Seaparate list values with ;(semi-colon)
	 */
	public void assertValue(String object, String data){
		try{
			String ele = null;
			waitElementExists(object);
			Thread.sleep(1000);
			if (driver.findElement(By.xpath(OR.getProperty(object))).getText() != null &&
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().equals("") && 
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().isEmpty()) {
				ele = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			} else {
				ele = getAttributeValue(object);
			}
			
//			ele = StringUtils.remove(ele, ",");	
			List<String> actual =  new ArrayList<String>();
			actual = Arrays.asList(ele.toUpperCase());
			List<String> expected =  new ArrayList<String>();
			expected = Arrays.asList(data.trim().toUpperCase().split("[\\s]*;[\\s]*"));
			Log.info("Actual Value: "+actual);
			Log.info("Expected Value: "+expected);
			Assert.assertTrue(CollectionUtils.containsAny(actual, expected), "Expected="+data +"; Actual="+ele);
		 } catch(AssertionError ae){
			Log.error(object +": Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, object +": assertValue--- "+ae.getMessage());
		 	bResult = false;
		 	Assert.fail();
	     } catch(Exception e){
	 		Log.error(object +": Exception assertValue2 --- " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, object +": assertValue--- "+e.getMessage());
	 		bResult = false;
	 		Assert.fail();
	     } 
	}
	
	/*
	 * Compare 2 values - accepts object label/name and expected value
	 */
	public void assertValueOnLabel(String labelName, String expectedValue){
		try{
			String ele = null;
			String xpath = "//input[@id=(//label[contains(., '"+labelName+"')]/@for)]";
			waitForElementDisplayed(By.xpath(xpath));
						
			if (driver.findElement(By.xpath(xpath)).getText() != null &&
					!driver.findElement(By.xpath(xpath)).getText().equals("") && 
					!driver.findElement(By.xpath(xpath)).getText().isEmpty()) {
				ele = driver.findElement(By.xpath(xpath)).getText();
			} else {
				ele = getAttributeValue(By.xpath(xpath));
			}
			
//			ele = StringUtils.remove(ele, ",");	
			Thread.sleep(500);
			Assert.assertTrue(ele.toUpperCase().trim().equals(expectedValue.toUpperCase()), "Expected="+expectedValue +"; Actual="+ele);
		 } catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, labelName +": assertValueOnLabel--- "+ae.getMessage());
		 	bResult = false;
		 	Assert.fail();
	     } catch(Exception e){
	 		Log.error("Exception assertValueOnLabel --- " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, labelName +": assertValueOnLabel--- "+e.getMessage());
	 		bResult = false;
	 		Assert.fail();
	     } 
	}

	
	/* TODO combine with assertValue
	 * Compares value of an object code usually in list table or value list
	 */
	public void assertCode(String object, String data){
		try{
			String ele = null;
			waitElementExists(object);
			if (driver.findElement(By.xpath(OR.getProperty(object))).getText() != null &&
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().equals("") && 
					!driver.findElement(By.xpath(OR.getProperty(object))).getText().isEmpty()) {
				ele = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			} else {
				ele = getAttributeValue(object);
			}
			
//			ele = StringUtils.remove(ele, ",");	
			data = getStoredValue(data);
			Log.info("get assertValue: object="+ele.toUpperCase()+" data:"+data.toUpperCase());
			waitFor();
			Assert.assertTrue(ele.toUpperCase().trim().equals(data.toUpperCase()), "Expected="+data +"; Actual="+ele);
		 } catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, object +": assertCode--- "+ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
		 	bResult = false;
		 	Assert.fail();
	     } catch(Exception e){
	 		Log.error("Exception assertCode --- " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, object +": assertCode--- "+e.getMessage());
	 		bResult = false;
	 		Assert.fail();
	     } 
	}
	
	public static String getStoredValue(String data) throws Exception {
			String code = null;
			switch (data.toUpperCase()) {
            	case "WONUM":
            		code = Base.WONUM;
            		break;
            	case "ASSETNUM":
            		code = Base.ASSETNUM;
            		break;
	            case "PRNUM":
	            	code = Base.PRNUM;
	            	break;
	            case "PONUM":
	            	code = Base.PONUM;
	            	break;
	            case "STOREROOM":
	            	code = Base.Storeroom;
	            	break;
	            case "PMNUM":
	            	code = Base.PMNUM;
	            	break;
	            case "ROUTENUM":
	            	code = Base.RouteNum;
	            	break;
	            case "JPNUM":
	            	code = Base.JPNum;
	            	break;
	            case "TEMPLATE":
	            	code = Base.Template;
	            	break;	 
	            case "SRNUM":
	            	code = Base.SRNUM;
	            	break;
	            case "LOCATION":
	            	code = Base.LOCATION;
	            	break;
	            case "POINT":
	            	code = Base.POINT;
	            	break;
	            case "WFAPPROVER":
	            	code = Base.WFApprover;
	            	break;	
	            case "VENDOR":
	            	code = Base.VENDOR;
	            	break;
			    }
			return code;
	}
	
	
	public void fieldValuesEqual(String fieldVal1, String fieldVal2){
		try{
			waitElementExists(fieldVal1);
			waitElementExists(fieldVal2);
			String field1 = String.valueOf(getAttributeValue(fieldVal1));
			String field2 = String.valueOf(getAttributeValue(fieldVal2));
			Assert.assertEquals(field1.trim().toLowerCase(), field2.trim().toLowerCase());
		 }catch(AssertionError ae){
			Log.error("Assertion failed fieldValuesEqual--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, fieldVal1 +", "+fieldVal2+" : fieldValuesEqual--- " + ae.getMessage());
			bResult = false;
			Assert.fail();
		 }catch(Exception e){
 			Log.error("Exception Fields not Equal --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, fieldVal1 +", "+fieldVal2+" : fieldValuesEqual--- " + e.getMessage());
 			bResult = false;
 			Assert.fail();
         }
	}
	
	public void isNull(String object, boolean status){
		try{
			waitElementExists(object);
			String val = String.valueOf(getAttributeValue(object).equals(""));
			Assert.assertEquals(val.toLowerCase(), String.valueOf(status).toLowerCase(), "Expected="+status +" Actual="+val);
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, object +": isNull--- "+ae.getMessage());
			bResult = false; 
			Assert.fail();
		}catch(Exception e){
 			Log.error("Exception Object is null --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, object +": isNull--- "+e.getMessage());
 			bResult = false;
 			Assert.fail();
         }
	}
	
	public void isEmpty(String object, boolean status){
		try{
			waitElementExists(object);
			Boolean val = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("displayrows").equals("0");
			Assert.assertEquals(String.valueOf(val).toLowerCase(), String.valueOf(status).toLowerCase(), "Expected="+status +" Actual="+val);
		}catch(AssertionError ae){
			Log.error(object +": Assertion failed isEmpty " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, object +": isEmpty--- "+ae.getMessage());
			bResult = false; 
			Assert.fail();
		}catch(Exception e){
 			Log.error(object +": Exception Object is empty --- " + e.getMessage());
 			extentTest.log(LogStatus.ERROR, object +": isEmpty--- "+e.getMessage());
 			bResult = false;
 			Assert.fail();
        }
	}
	
	public boolean isEmpty(String object) throws Exception{
		waitElementExists(object);
		boolean isEmpty = false; 
   		if (driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("displayrows").equals("0")) {
   			isEmpty = true;
        }
		return isEmpty;
	}

	public void isReadOnly(String object, boolean status){
		 try{
			String val = "false"; 
			waitElementExists(object);
    		if (driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("readonly") != null) {
				val = "true";
			}
			Assert.assertEquals(val, String.valueOf(status).toLowerCase(), "Expected="+status +"; Actual="+val);
		 }catch(AssertionError ae){
			Log.error(object +": Assertion failed isReadOnly --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, object +": isReadOnly--- "+ae.getMessage());
			bResult = false;
			Assert.fail();
		 }catch(Exception e){
 			Log.error(object +": Exception Object is readonly --- " +e.getMessage());
 			extentTest.log(LogStatus.ERROR, object +": isReadOnly--- "+e.getMessage());
 			bResult = false;
 			Assert.fail();
         }
	}
	
	public boolean isReadOnly(String object) throws Exception{
		waitElementExists(object);
		boolean isReadOnly = false; 
   		if (driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("readonly") != null) {
   			isReadOnly = true;
        }
		return isReadOnly;
	}
	
	public void isDisabled(String object, boolean status){
		 try{
			String val = "false"; 
			waitElementExists(object);
	    	if (driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("active") != null) {
			    val = "true";
			}
			Assert.assertEquals(val, String.valueOf(status).toLowerCase(), "Expected="+status +"; Actual="+val); 
			Thread.sleep(2000);
    	}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, object +": isDisabled---- "+ae.getMessage());
			bResult = false; 
			Assert.fail();
		}catch(Exception e){
			Log.error("Exception Object is disabled --- " +e.getMessage());
			extentTest.log(LogStatus.ERROR, object +": isDisabled---- "+e.getMessage());
			bResult = false;
			Assert.fail();
        }
	}
	
	public void isDisabled(WebElement element, boolean status){
		Log.info("Element disabled... "+ element);
		 try{
			 String val = "false"; 
				waitElementExists(element);
		    	if (element.getAttribute("active") != null) {
				    val = "true";
				}
				Assert.assertEquals(val, String.valueOf(status).toLowerCase(), "Expected="+status +"; Actual="+val); 
				Thread.sleep(2000);
		 }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, element +": isDisabled--- "+ae.getMessage());
			bResult = false;
			Assert.fail();
		}catch(Exception e){
			Log.error("Exception Object is disabled --- " +e.getMessage());
			extentTest.log(LogStatus.ERROR, element +": isDisabled--- "+e.getMessage());
			bResult = false;
			Assert.fail();
       }
	}
		
	public void isRequired(String object, boolean status){
		try{
			String val = "false"; 
			waitElementExists(object);
    		if (driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("aria-required") != null) {
				val = "true";
			}
			Assert.assertEquals(val, String.valueOf(status).toLowerCase(), "Expected="+status +"; Actual="+val);
   	    }catch(AssertionError ae){
			Log.error("Assertion failed isRequired--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, object +": isRequired-- "+ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + String.valueOf(status));
			bResult = false;	
			Assert.fail();
		}catch(Exception e){
 			Log.error("Exception isRequired --- " +e.getMessage());
 			extentTest.log(LogStatus.ERROR, object +": isRequired--- "+e.getMessage());
 			bResult = false;
 			Assert.fail();
        }
	}
		
	public void isChecked(String object, boolean status){
		try{
			waitElementExists(object);
			String val = String.valueOf(driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("aria-checked").equals("true"));
			Assert.assertEquals(val.toLowerCase(), String.valueOf(status).toLowerCase(), "Expected="+status +"; Actual="+val);
		}catch(AssertionError ae){
			Log.error("Assertion failed isChecked--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, object +": isChecked--- "+ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + String.valueOf(status));
			bResult = false; 
			Assert.fail();
		}catch(Exception e){
 			Log.error("Exception Object is checked--- " +e.getMessage());
 			extentTest.log(LogStatus.ERROR, object +": isChecked--- "+e.getMessage());
 			bResult = false;
 			Assert.fail();
        }
	}
	
	public boolean isChecked(String object) throws Exception{
		waitElementExists(object);
		boolean val = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("aria-checked").equals("true");
		if (val) {
			return true;
		} else {
			return false;
		}
	}
	
	public void isTicked(String object, boolean status){
		try{
			waitElementExists(object);
			Log.error("Is Ticked ="+ driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked"));
			String val = String.valueOf(driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked").equals("true"));
			Log.error("Is Ticked val ="+ val);
			Assert.assertEquals(val.toLowerCase(), String.valueOf(status).toLowerCase(), "Expected="+status +"; Actual="+val);
  	    }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, object +": isTicked--- "+ae.getMessage());
			bResult = false; 
			Assert.fail();
		}catch(Exception e){
 			Log.error("Exception Object is ticked --- " +e.getMessage());
 			extentTest.log(LogStatus.ERROR, object +": isTicked--- "+e.getMessage());
 			bResult = false;
 			Assert.fail();
        }
	}
	
	public void isValid(String object, boolean status){
		try{
			String val = "true"; 
			waitElementExists(object);
    		if (driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("aria-invalid") != null) {
				val = "false";
			}
			Assert.assertEquals(val, String.valueOf(status).toLowerCase(), "Expected="+status +" Actual="+val);
  	    }catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, object +": isValid--- "+ae.getMessage());
			bResult = false; 
			Assert.fail();
		}catch(Exception e){
 			Log.error("Exception isValid --- " +e.getMessage());
 			extentTest.log(LogStatus.ERROR, object +": isValid--- "+e.getMessage());
 			bResult = false;
 			Assert.fail();
        }
	}
		
	public void rowsDisplayed(int expectedRows) {
		try{
//			String object ="//table[contains(@id,'_tbod-tbd')]";
			waitElementExists("tbl_ListView");
			int val = Integer.valueOf(driver.findElement(By.xpath(OR.getProperty("tbl_ListView"))).getAttribute("displayrows"));
			Assert.assertEquals(val, expectedRows, "Expected="+expectedRows +"; Actual="+val);
		 }catch(AssertionError ae){
			Log.error("Assertion failed rowsDisplayed --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, expectedRows +": rowsDisplayed--- " + ae.getMessage()); 
			bResult = false;	
			Assert.fail();
		 }catch(Exception e){
 			Log.error("Exception rowsDisplayed ---" + e.getMessage());
 			extentTest.log(LogStatus.ERROR, expectedRows +": rowsDisplayed--- " + e.getMessage());
 			bResult = false;
 			Assert.fail();
         }
	}
	
	public void rowsDisplayed1(String tableName, String numRows){
		try{
			String path = "//table[contains(@summary,'"+tableName+"')]";
			waitElementExists(By.xpath(path));
			int val = Integer.valueOf(driver.findElement(By.xpath(path)).getAttribute("displayrows"));
			Assert.assertEquals(val, Integer.valueOf(numRows).intValue(), "Expected="+numRows +"; Actual="+val);
		 }catch(AssertionError ae){
			Log.error("Assertion failed rowsDisplayed1 --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, tableName +": rowsDisplayed1--- "+ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + numRows.toUpperCase().trim());
			bResult = false;	
			Assert.fail();
		 }catch(Exception e){
 			Log.error("Exception rowsDisplayed1 ---" + e.getMessage());
 			extentTest.log(LogStatus.ERROR, tableName +": rowsDisplayed1--- "+e.getMessage());
 			bResult = false;
 			Assert.fail();
         }
	}
	
	public void tableNotEmpty(String object, boolean status){
		try{
			String path = "//table[contains(@summary,'"+object+"')]";
			waitElementExists(By.xpath(path));
			int val = Integer.valueOf(driver.findElement(By.xpath(path)).getAttribute("displayrows"));
			Assert.assertEquals(Boolean.valueOf(val > 0).booleanValue(), status);
		 }catch(AssertionError ae){
			Log.error("Assertion failed tableNotEmpty--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, object +": tableNotEmpty--- "+ae.getMessage());
			bResult = false;
			Assert.fail();
		 }catch(Exception e){
 			Log.error("Exception tableNotEmpty---" + e.getMessage());
 			extentTest.log(LogStatus.ERROR, object +": tableNotEmpty--- "+e.getMessage());
 			bResult = false;
 			Assert.fail();
         }
	}
	
	public void rowsPerPage(int rowsperpage){
		try{
			String path = "//table[@id='lookup_page1_tbod-tbd']";
			waitElementExists(By.xpath(path));
			int val = Integer.valueOf(driver.findElement(By.xpath(path)).getAttribute("rowsperpage"));
			Assert.assertEquals(val, rowsperpage, "Expected="+rowsperpage +"; Actual="+val);
		 }catch(AssertionError ae){
			Log.error("Assertion failed rowsPerPage--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, rowsperpage +": rowsPerPage-- " +ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + rowsperpage);
			bResult = false;	
			Assert.fail();
		 }catch(Exception e){
 			Log.error("Exception rowsPerPage---" + e.getMessage());
 			extentTest.log(LogStatus.ERROR, rowsperpage +": rowsPerPage-- " +e.getMessage());
 			bResult = false;
 			Assert.fail();
         }
	}
	
	public void rowsPerPage1(String object, String numRows){
		try{
			String path = "//table[contains(@summary,'"+object+"')]";
			waitElementExists(By.xpath(path));
			int val = Integer.valueOf(driver.findElement(By.xpath(path)).getAttribute("rowsperpage"));
			Assert.assertEquals(val, Integer.valueOf(numRows).intValue(), "Expected="+numRows +" Actual="+val);
		 }catch(AssertionError ae){
			Log.error("Assertion failed rowsPerPage1--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, object +": rowsPerPage1--- "+ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + numRows.toUpperCase().trim());
			bResult = false;
			Assert.fail();
		 }catch(Exception e){
 			Log.error("Exception rowsPerPage1---" + e.getMessage());
 			extentTest.log(LogStatus.ERROR, object +": rowsPerPage1--- "+e.getMessage());
 			bResult = false;
 			Assert.fail();
         }
	}
	
	public void totalRowCount(String object, String numRows){
		try{
			String path = "//table[@id='lookup_page1_tbod-tbd']/tbody/tr[contains(@id='lookup_page1_tbod_tdrow-tr')]";
			waitElementExists(By.xpath(path));
			List<WebElement> ddOpts = driver.findElements(By.xpath(path));
			int rowCount = ddOpts.size();
			Assert.assertEquals(rowCount, Integer.valueOf(numRows).intValue(), "Expected="+numRows +" Actual="+rowCount);
		 }catch(AssertionError ae){
			Log.error("Assertion failed totalRowCount--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, object +": totalRowCount--- "+ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + numRows.toUpperCase().trim());
			bResult = false;
			Assert.fail();
		 }catch(Exception e){
 			Log.error("Exception Total row count ---" +e.getMessage());
 			extentTest.log(LogStatus.ERROR, object +": totalRowCount--- "+e.getMessage());
 			bResult = false;
 			Assert.fail();
         }
	}
	

	public void tableExists(String object) throws Exception{
		try {
	    	Log.info("Checks table exists: "+ object);
	    	String path = "//table[contains(@summary,'"+object+"')]";
	    	waitElementExists(By.xpath(path));
	    	Assert.assertTrue(elementDisplayed(By.xpath(path)));
	    }catch(AssertionError ae){
			Log.error("Assertion failed tableExists--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, object +": tableExists--- "+ae.getMessage());
			bResult = false;
			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found tableExists--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, object +": tableExists--- "+e.getMessage());
 			bResult = false;
 			Assert.fail();
	    }
	}
	
   
    public void waitConfirmation(String object, String data) throws Exception {
		Log.info("waitConfirmation: "+ object);
//			WebDriverWait wait = new WebDriverWait(driver, 3000);
	    wait.until(ExpectedConditions.alertIsPresent());
//	        driver.switchTo().alert().accept();
	}
    
	public void input(String object, String data) throws Exception {
		waitElementExists(object);
		Log.info("Entering the text in " + object);
		click(object);
		clear(object);
		driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
		Thread.sleep(500);
	}
	
		
	public void clear(String object, String data) throws Exception {
		waitElementExists(object);
		driver.findElement(By.xpath(OR.getProperty(object))).clear();
	}
	
	public static void snooze(String object, String sec) throws Exception{
		Log.info("Snooze...");
		Thread.sleep(Integer.parseInt(sec));

	}

	public static void waitFor() throws Exception {
		Log.info("Wait...");
		Thread.sleep(3000);
	}
	
	public static void waitFor5() throws Exception{
		Thread.sleep(5000);
	}
	
	public void takeScreenshot(String sTestCaseName) throws Exception {
		Log.info("Taking Screenshot..");
		try{
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(Constants.Path_Screenshot + sTestCaseName +".jpg"));	
		} catch (Exception e){
			Log.error("Class Utils | Method takeScreenshot | Exception occured while capturing ScreenShot : "+e.getMessage());
			throw new Exception();
		}
	}
	
	public static void switchWindow(String object, String data) throws Exception{
		String mainWindow = driver.getWindowHandle();
	    for (String handle : driver.getWindowHandles()) {
	        if (!handle.equals(mainWindow)) {
	            driver.switchTo().window(handle);
	            break;
	        }
	    }
	}

	public void verifyAlert(String msg){
		try{
			String element = "msg_Popup";
			waitElementExists(element);
			if(isAlertPresent()){
	            driver.switchTo().alert();
	            driver.switchTo().alert().accept();
	            driver.switchTo().defaultContent();
	            Log.info("Alert: " + driver.switchTo().defaultContent());
	        }
			Log.info("get assertValue object.." +driver.findElement(By.xpath(OR.getProperty(element))).getText());
			Log.info("get assertValue data.." +msg);
			Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty(element))).getText().contains(msg), "Assertion failed.");
		} catch(AssertionError ae){
			Log.error("Assertion failed verifyAlert--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, "verifyAlert--- " + ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + msg.toUpperCase().trim());
			bResult = false;
			Assert.fail();
		} catch(Exception e){
	 		Log.error("Exception Verify Alert: " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, "verifyAlert--- " +e.getMessage());
	 		bResult = false;
	 		Assert.fail();
	    }
	}
	
	public boolean isAlertPresent(){
		boolean foundAlert = false;
	    wait = new WebDriverWait(driver, 10);
	    try {
	        wait.until(ExpectedConditions.alertIsPresent());
	        foundAlert = true;
	        Log.info("Alert found>>>>>>>>>>>>>>>>>>>>>>");
	    } catch (TimeoutException eTO) {
	        foundAlert = false;
	    }
	    return foundAlert;
	}
		
	public void clickFilter() throws Exception{
       	click("btn_Filter"); 
    }
	
    public void clickFind() throws Exception{
       	click("btn_Find"); 
    }
    
    public void clickNew() throws Exception{
       	click("btn_New");
    }

    public void clickCancel() throws Exception{
       	click("btn_Cancel");
    }
    
    public void clickNewRailWearReading() throws Exception{
       	click("btn_NewRailWearReading");
    }
    
    public void clickSaveRailWearReading() throws Exception{
       	click("btn_SaveRailWearReading");
    }
    
    public void clickCancelRailWearReading() throws Exception{
       	click("btn_CancelRailWearReading");
    }
    
	public void isApprover(String object, String data){
		try{
			Log.info("isApprover " + object);
			String assignmentPath = "//table[contains(@id,'People') and contains(@summary,'People')]/tbody/tr";
			waitForElementDisplayed(By.xpath(assignmentPath));
			int rowCount = (driver.findElements(By.xpath(assignmentPath)).size());
			
			waitForElementDisplayed(By.xpath("//table[contains(@summary,'Inbox / Assignments')]"));
			
			Log.info("rowCount " + rowCount);
			for (int i=3;i<=rowCount;i+=2){
				String sValue = null;
				sValue = driver.findElement(By.xpath("//table[contains(@summary,'Inbox / Assignments')]/tbody/tr["+i+"]/td[4]")).getText();
				Log.info("value " + sValue);
				Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty(object))).getText().equalsIgnoreCase(sValue), "Assertion failed.");
				break;
			}
		}catch(AssertionError ae){
			Log.error("Assertion failed isApprover--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, object +": isApprover--- "+ae.getMessage());
			extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
			bResult = false;	
			Assert.fail();
		}catch(Exception e){
			 Log.error(e.getMessage());
			 extentTest.log(LogStatus.ERROR, object +": isApprover--- "+e.getMessage());
			 bResult = false;
			 Assert.fail();
		}
	}
	
	public void openWFAssignment() throws Exception {
		String val = Base.storedValue;
		String path = "//table[contains(@summary,'Inbox / Assignments')]/tbody/tr";
		Log.info("Stored value = " + val);
		Log.info("openWFAssignment");
		waitElementExists(By.xpath(path));
		int rowCount = (driver.findElements(By.xpath(path)).size());
		Log.info("Assignment list = "+rowCount);
		boolean foundValue = false;
		boolean isNextPage = false;
		By nextPage = By.xpath("//a[text()='Next Page']");
			
		do {
			for (int i=3;i<=rowCount;i+=2) {
				String sValue = null;
				sValue = driver.findElement(By.xpath("//table[contains(@summary,'Inbox / Assignments')]/tbody/tr["+i+"]/td[3]")).getText();

				if(sValue.contains(val)) {
					driver.findElement(By.xpath("//table[contains(@summary,'Inbox / Assignments')]/tbody/tr["+i+"]/td[9]/a/img")).click();
					Log.info("clicking image for <break> " + sValue);
					waitFor();
					foundValue = true;
					break;
				} 
			}
			if (elementDisplayed(nextPage)) {
				isNextPage = true;
				driver.findElement(nextPage).click();
				waitFor();
			} else {
				isNextPage = false;
			}
		} while (!foundValue && isNextPage);

		if (foundValue=false){
			Log.error(val+": Exception WFAssignment not found");
	   	    extentTest.log(LogStatus.ERROR, val+": Exception WFAssignment not found");
			bResult = false;
			Assert.fail();
			throw new Exception(val+": Exception WFAssignment not found");
		}
	}
	
	public void openWFAssignmentTOBEDELETED(String object, String data) throws Exception {
			String val = Base.storedValue;
			String path = "//table[contains(@summary,'Inbox / Assignments')]/tbody/tr";
			Log.info("Stored value = " + val);
			Log.info("openWFAssignment");
			waitElementExists(By.xpath(path));
			int rowCount = (driver.findElements(By.xpath(path)).size());
			Log.info("Assignment list = "+rowCount);
			boolean foundValue = false;
			
			for (int i=3;i<=rowCount;i+=2){
				String sValue = null;
				sValue = driver.findElement(By.xpath("//table[contains(@summary,'Inbox / Assignments')]/tbody/tr["+i+"]/td[3]")).getText();

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
	}
	
 
      public void expectedRows(String object, String data){
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
		    	
		    	action.tableExists(dir);
			    List<WebElement> ddOpts = driver.findElements(By.xpath("//table[contains(@summary,'"+dir+"')]//tr[contains(@class,'tablerow')]"));
//			    
//			    Log.info("ddOpts.toString() =  " + ddOpts.toString());
//			    Log.info("object =  " + object.toLowerCase());
//			    Log.info("is empty row? =  " + ddOpts.isEmpty());
//				Log.info("dir =  " + dir);
				int rowCount = ddOpts.size();
				Assert.assertEquals(String.valueOf(rowCount), data.trim());
		     }catch(AssertionError ae){
				Log.error("Assertion failed expectedRows--- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, object +": expectedRows--- "+ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + data.toUpperCase().trim());
				bResult = false;
				Assert.fail();
			 }catch(Exception e){
				Log.error("Exception expectedRows --- " +e.getMessage());
				extentTest.log(LogStatus.ERROR, object +": expectedRows--- "+e.getMessage());
				bResult = false;
				Assert.fail();
			 }
	  }
	 
	  public static void checkStatusExists(String object, boolean status){
		  try{
			    List<String> statusToCheck =  new ArrayList<String>();
			    statusToCheck = Arrays.asList(object.trim().toUpperCase().split("[\\s]*,[\\s]*"));
				Log.info("check status exists: " + object);
				
				String menu = "//ul[@id='menu0']/li[*]";
				
				action.waitForElementDisplayed(By.xpath(menu));
				List<WebElement> ddOpts = driver.findElements(By.xpath(menu));
				
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
				if (status) {
				    Assert.assertTrue(CollectionUtils.isEqualCollection(statusList, statusToCheck));
				} else {
					Assert.assertFalse(CollectionUtils.containsAny(statusList, statusToCheck));
				}
		  }catch(AssertionError ae){
				Log.error("Assertion failed checkStatusExists--- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, object +": checkStatusExists--- "+ae.getMessage());
				bResult = false;
				Assert.fail();
		  }catch(Exception e){
				Log.error("Exception checkStatusExists --- " + e.getMessage());
				extentTest.log(LogStatus.ERROR, object +": checkStatusExists--- "+e.getMessage());
				bResult = false;
				Assert.fail();
		  }
	  }
	  
	  public void checkValueExists(String object, boolean status){
		  try{
			    List<String> valueToCheck =  new ArrayList<String>();
			    valueToCheck = Arrays.asList(object.trim().toUpperCase().split("[\\s]*,[\\s]*"));
				Log.info("check value exists: " + valueToCheck);
				
				List<WebElement> ddOpts = null;
				String strPath = null;
				//main page
				String mainPath = "//table[@id='lookup_page1_tbod-tbd']//td[contains(@id,'lookup_page1_tdrow_[C:0]-c')]";
				String searchPath = "//table[@id='lookup_page2_tbod-tbd']//td[contains(@id,'lookup_page2_tdrow_[C:1]-c')]";
				
				Thread.sleep(2000);
				if (driver.findElements(By.xpath(mainPath)).size() > 0) {
					//main page
					ddOpts = driver.findElements(By.xpath(mainPath));
					strPath = "//table[@id='lookup_page1_tbod-tbd']//td[contains(@id,'lookup_page1_tdrow_[C:0]-c[R:";
					Log.info("is empty row 1? =  " + ddOpts.isEmpty());
				} else {
					//search page
					ddOpts = driver.findElements(By.xpath(searchPath));
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
				if (status) {
				    Assert.assertTrue(CollectionUtils.isEqualCollection(valueList, valueToCheck));
				} else {
					Assert.assertFalse(CollectionUtils.containsAny(valueList, valueToCheck));
				}
   	     }catch(AssertionError ae){
				Log.error("Assertion failed checkValueExists--- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, object +": checkValueExists--- "+ae.getMessage());
				bResult = false;
				Assert.fail();
		 }catch(Exception e){
			    Log.error("Exception checkValueExists --- " + e.getMessage());
			    extentTest.log(LogStatus.ERROR, object +": checkValueExists--- "+e.getMessage());
				bResult = false;
				Assert.fail();
		 }
	  }
	  
//	  TODO - not used
	  public static void checkValueExistsInSelectValue(String object, boolean status){
		  try{
			    List<String> valueToCheck =  new ArrayList<String>();
			    valueToCheck = Arrays.asList(object.trim().toUpperCase().split("[\\s]*,[\\s]*"));
				Log.info("check value exists: " + object);
				
				List<WebElement> ddOpts = null;
				String strPath = null;
				
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
				if (status) {
				    Assert.assertTrue(CollectionUtils.isEqualCollection(valueList, valueToCheck));
				} else {
					Assert.assertFalse(CollectionUtils.containsAny(valueList, valueToCheck));
				}
   	     }catch(AssertionError ae){
				Log.error("Assertion failed checkValueExistsInSelectValue--- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, object +": checkValueExistsInSelectValue--- "+ae.getMessage());
				bResult = false;
				Assert.fail();
		 }catch(Exception e){
			    Log.error("Exception checkValueExistsInSelectValue --- " + e.getMessage());
			    extentTest.log(LogStatus.ERROR, object +": checkValueExistsInSelectValue--- "+e.getMessage());
				bResult = false;
				Assert.fail();
		 }
	  }
	  
	  public void checkTopMostDrilldown(String object, String assetnum){
		  try{
			    if (object.equalsIgnoreCase("Assets")) {
				    String assetPath = "//div[@id='asset_tree_treecontainer']//a[2]";
			    	action.waitForElementDisplayed(By.xpath(assetPath));
			    	Assert.assertTrue(driver.findElements(By.xpath(assetPath)).get(0).getText().toUpperCase().startsWith(assetnum.toUpperCase()));
			    } else if (object.equalsIgnoreCase("locations")) {
			    	String locationPath = "//div[@id='locations_tree_treecontainer']//a[2]";
			    	action.waitForElementDisplayed(By.xpath(locationPath));
			    	Assert.assertTrue(driver.findElements(By.xpath(locationPath)).get(0).getText().toUpperCase().startsWith("KIWIRAIL"));
		        } else {
		        	 Log.info("No assertion made.");				        
		        }
   	      }catch(AssertionError ae){
				Log.error("Assertion failed checkTopMostDrilldown--- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, object +": checkTopMostDrilldown--- "+ae.getMessage());
				bResult = false;	
				Assert.fail();
          }catch(Exception e){
				Log.error("Exception checkTopMostDrilldown--- " +e.getMessage());
				extentTest.log(LogStatus.ERROR, object +": checkTopMostDrilldown--- "+e.getMessage());
				bResult = false;
				Assert.fail();
		 }
	  }
	  
	  public void checkColumnExists(String object, boolean status){
		  try{
			    List<String> colToCheck =  new ArrayList<String>();
			    colToCheck = Arrays.asList(object.trim().toUpperCase().split("[\\s]*,[\\s]*"));
			    String path = "//tr[@id='lookup_page1_tbod_ttrow-tr']/th[contains(@id,'lookup_page1_ttrow_[C:')]";
			    waitElementExists(By.xpath(path));
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
				if (status) {
				    Assert.assertTrue(colList.containsAll(colToCheck));
				} else {
					Assert.assertFalse(CollectionUtils.containsAny(colList, colToCheck));
				}
		     }catch(AssertionError ae){
				Log.error("Assertion failed checkColumnExists--- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, object +": checkColumnExists--- "+ae.getMessage());
				bResult = false;
				Assert.fail();
			 }catch(Exception e){
				Log.error("Exception checkColumnExists ---" + e.getMessage());
				extentTest.log(LogStatus.ERROR, object +": checkColumnExists--- "+e.getMessage());
				bResult = false;
				Assert.fail();
			 }
	  }
	  
	  public void checkColumnExistsListView(String object, boolean status){
		  try{
			    List<String> colToCheck =  new ArrayList<String>();
			    colToCheck = Arrays.asList(object.trim().toUpperCase().split("[\\s]*,[\\s]*"));
			    String xpath = "//tr[contains(@id,'_tbod_ttrow-tr')]/th[contains(@id,'_ttrow_[C:')]";
			    waitElementExists(By.xpath(xpath));
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
				    if (elementExists(path)) {
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
				if (status) {
				    Assert.assertTrue(colList.containsAll(colToCheck));
				} else {
					Assert.assertFalse(CollectionUtils.containsAny(colList, colToCheck));
				}
		     }catch(AssertionError ae){
				Log.error("Assertion failed checkColumnExistsListView--- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, object +": checkColumnExistsListView--- "+ae.getMessage());
				bResult = false;
				Assert.fail();
			 }catch(Exception e){
				Log.error("Exception checkColumnExistsListView ---" + e.getMessage());
				extentTest.log(LogStatus.ERROR, object +": checkColumnExistsListView--- "+e.getMessage());
				bResult = false;
				Assert.fail();
			 }
	  }
	  
//	  this method is duplicate of checkColumnExistsListView - TODO check for files referencing this method and change before deleting
	  public void checkColumnExists1(String object, boolean status){
		  try{
			    List<String> colToCheck =  new ArrayList<String>();
			    colToCheck = Arrays.asList(object.trim().toUpperCase().split("[\\s]*,[\\s]*"));
			    String xpath = "//tr[contains(@id,'_tbod_ttrow-tr')]/th[contains(@id,'_ttrow_[C:')]";
			    waitElementExists(By.xpath(xpath));
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
				    if (elementExists(path)) {
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
				if (status) {
				    Assert.assertTrue(colList.containsAll(colToCheck));
				} else {
					Assert.assertFalse(CollectionUtils.containsAny(colList, colToCheck));
				}
		     }catch(AssertionError ae){
				Log.error("Assertion failed checkColumnExists1--- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, object +": checkColumnExists1--- "+ae.getMessage());
				bResult = false;
				Assert.fail();
			 }catch(Exception e){
				Log.error("Exception checkColumnExists1---" + e.getMessage());
				extentTest.log(LogStatus.ERROR, object +": checkColumnExists1--- "+e.getMessage());
				bResult = false;
				Assert.fail();
			 }
	  }
	  
	  /*
	   * Checks if a table column exists via the column name and table name
	   */
	  public void checkTableColumnExists(String columnList, String tableName){
		  try{
			    List<String> colToCheck =  new ArrayList<String>();
			    colToCheck = Arrays.asList(columnList.trim().toUpperCase().split("[\\s]*,[\\s]*"));
//			    String xpath = "//table[contains(@summary,'"+tableName+"')]/tbody/tr[1]/th[contains(@id,'_ttrow_[C:')]";
			    String xpath = "//table[contains(@summary,'"+tableName+"')]/tbody/tr[1]/th";
			    waitElementExists(By.xpath(xpath));
				List<WebElement> ddOpts = driver.findElements(By.xpath(xpath));

				Log.info("is empty column? =  " + ddOpts.isEmpty());
				int colCount = ddOpts.size();
				Log.info("colCount = " + colCount);
				List<String> colList = new ArrayList<String>();
				String strValue = null;
				By path;
				int i = 1;
				while (i <= colCount)
				{  
					path = By.xpath("//table[contains(@summary,'"+tableName+"')]/tbody/tr[1]/th["+i+"]");
//					path = By.xpath("//table[contains(@summary,'"+tableName+"')]/tbody/tr[1]/th[contains(@id,'_ttrow_[C:'"+i+")]");
					if (elementExists(path)) {
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
				Log.error("Assertion failed checkTableColumnExists--- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, tableName +": checkTableColumnExists--- "+ae.getMessage());
				extentTest.log(LogStatus.INFO, "Expected Value: " + columnList.toUpperCase().trim());
				bResult = false;
				Assert.fail();
			 }catch(Exception e){
				Log.error("Exception checkTableColumnExists ---" + e.getMessage());
				extentTest.log(LogStatus.ERROR, tableName +": checkTableColumnExists--- "+e.getMessage());
				bResult = false;
				Assert.fail();
			 }
	  }
	  
	  public void checkTableColumnNotExists(String columnList, String tableName){
		  try{
			    List<String> colToCheck =  new ArrayList<String>();
			    colToCheck = Arrays.asList(columnList.trim().toUpperCase().split("[\\s]*,[\\s]*"));
			    String xpath = "//table[contains(@summary,'"+tableName+"')]/tbody/tr[1]/th[contains(@id,'_ttrow_[C:')]";
			    waitElementExists(By.xpath(xpath));
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
				    if (elementExists(path)) {
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
				Assert.assertFalse(colList.containsAll(colToCheck));
		     }catch(AssertionError ae){
				Log.error("Assertion failed checkTableColumnNotExists--- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, tableName +": checkTableColumnNotExists--- "+ae.getMessage());
				bResult = false;
				Assert.fail();
			 }catch(Exception e){
				Log.error("Exception checkTableColumnExists ---" + e.getMessage());
				extentTest.log(LogStatus.ERROR, tableName +": checkTableColumnNotExists--- "+e.getMessage());
				bResult = false;
				Assert.fail();
			 }
	  }
	  
	  public void checkTableNotEmpty(String tableName, boolean status) {
			try{
				WebElement table = driver.findElement(By.xpath("//table[contains(@summary,'"+tableName+"')]"));
				waitElementExists(table);
				int val = Integer.valueOf(table.getAttribute("displayrows"));
				Assert.assertEquals(val > 0, status);
			 }catch(AssertionError ae){
				Log.error("Assertion failed checkTableNotEmpty--- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, tableName +": checkTableNotEmpty-- "+ae.getMessage());
				extentTest.log(LogStatus.INFO, "Element: " + tableName);
				Assert.fail();
			 }catch(Exception e){
	 			Log.error("Exception checkTableNotEmpty ---" + e.getMessage());
	 			extentTest.log(LogStatus.ERROR, tableName +": checkTableNotEmpty--- "+e.getMessage());
	 			extentTest.log(LogStatus.INFO, "Element: " + tableName);
	 			Assert.fail();
	         }
		}
	  
	  public void scroll(WebElement element) throws Exception {
		  Log.info("scrolling down to field..");
		  waitElementExists(element);
		  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		  Thread.sleep(5000); 
	 }
	 
	 /*
	  * Checks if a field exists via label name 
	  */
	 public void fieldExists(String labelName, boolean status){
		 try{
			String xpath = "//label[contains(., '"+labelName+"')]"; 
   		    Log.info("Check fieldExists = " + By.xpath(xpath).toString());
	    		    	
	    	if (status) {
	    		List<WebElement> ane = driver.findElements(By.xpath(xpath));
				Assert.assertTrue(!ane.isEmpty());  //TODO check regardless of location and index
//			    Assert.assertTrue(elementExists(By.xpath(xpath)));
			} else {  
				Assert.assertFalse(elementExists(By.xpath(xpath)));
			}                                                          
		 }catch(AssertionError ae){
			Log.error(labelName +": Assertion failed fieldExists--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, labelName +": fieldExists--- "+ae.getMessage());
			bResult = false;
			Assert.fail();
		 }catch(Exception e){
			Log.error(labelName +": Exception fieldExists ---" + e.getMessage());
			extentTest.log(LogStatus.ERROR, labelName +": fieldExists--- "+e.getMessage());
			bResult = false;
			Assert.fail();
		 }
	  }
	 
	  public void buttonExists(String buttonName, boolean status){
		 try{
			String xpath = "//button[@type='button' and contains (., '"+buttonName+"')]"; 
   		    Log.info("Check buttonExists = " + By.xpath(xpath).toString());
   		    waitForElementDisplayed(By.xpath(xpath));
   		    
   		    if (status) {
			    Assert.assertTrue(elementExists(By.xpath(xpath)));
			} else {                                         
				Assert.assertFalse(elementExists(By.xpath(xpath)));
			}                                                          
		 }catch(AssertionError ae){
			Log.error("Assertion failed buttonExists--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, buttonName +": buttonExists--- "+ae.getMessage());
			bResult = false;
			Assert.fail();
		 }catch(Exception e){
			Log.error("Exception fieldExists ---" + e.getMessage());
			extentTest.log(LogStatus.ERROR, buttonName +": buttonExists--- "+e.getMessage());
			bResult = false;
			Assert.fail();
		 }
	  }
	  	  
	  public void copyMessage(String object) throws Exception{
		  waitElementExists(object);
		  driver.findElement(By.id(OR.getProperty(object))).getText();
	  }

	  public void scrollDown(String object) throws Exception{
		  Log.info("scrolling down to field..");
		  waitElementExists(object);
		  WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
		  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		  Thread.sleep(2000); 
	  }
	  
	  public static void routeWF(String routeTo) throws Exception{
		  action.route();
		  if (routeTo.toLowerCase().equals("dfa")) {
			  action.click("radio_WF_DFA");
		  } else {
			  action.click("radio_WF_EngrRev");
		  }
		  action.clickOK();
	  }	
	  
	  public static void stopWF() throws Exception {
		  action.click("hvr_Workflow");
		  action.click("stop_Workflow");
		  action.clickOK();
	  }	
	  
	  /*
	   * Use this format for the input variable:
	   * String[][] toSearch = new String[][] {{"txtbx_ReporterType", "HEAT"}, {"txtbx_Status", "NEW"}};
	   */
	  public void advancedSearch(String[][] objectValue) throws Exception{
		  action.click("btn_Advanced_Search");
		  Log.info("Clearing search fields.....");
		  click("btn_Revise");
		  driver.findElement(By.xpath("//span[contains(.,'Clear Query and Fields')]")).click();
		  Thread.sleep(1000);
		  for (int i=0; i < objectValue.length; i++) {
			  String[] temp = Arrays.toString(objectValue[i]).split(",");
			  action.input(temp[0].substring(1), temp[1].replace("]", ""));
		  }
		  action.clickFind();
		  wait = new WebDriverWait(driver, 60);
		  wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(OR.getProperty("btn_Find"))));
	  }		
	  
	  public void createSR(String summary, String reporterType) throws Exception {
		  Log.info("Create SR....");
		  action.clickNew();
		  input("txtbx_ReporterType", reporterType.trim());
		  input("txtbx_Summary", summary.trim());
		  input("txtbx_AssetNum", "1000014");
		  input("txtbx_Priority", "4");
		  input("txtbx_ActivityType", "Destress");
		  save();
		  Log.info("Generated SR...." + action.getAttributeValue("txtbx_SRNUM"));
	  }	
	  
	  public void createWO(String desc, String worktype) throws Exception {
		  String activityType = "Drainage";
		  String priority = "4";
		  if (worktype.equals("CAP")) {
			  activityType="Destress";
			  priority="18";
		  }
		  LocalDate ngayon = getDate();
		  //add 2 week to the current date
		  LocalDate next2Week = ngayon.plus(2, ChronoUnit.WEEKS);

		  clickNew();
		  input("txtbx_Description", desc.trim());
		  input("txtbx_AssetNum", "1000014");
		  input("txtbx_Worktype", worktype.trim());
		  click("txtbx_ActivityType");
		  input("txtbx_ActivityType", activityType);
		  input("txtbx_Priority", priority);
		  input("txtbx_FinancialYear", String.valueOf(ngayon.getYear()));
		  input("txtbx_ScheduledStart", next2Week.toString());
		  save();
		  Log.info("Generated WONUM = " + getAttributeValue("txtbx_WONUM"));
     }	
	  
	  public void createWOwithLinear(String desc, String worktype) throws Exception {
		  String activityType = "Drainage";
		  String priority = "4";
		  if (worktype.equals("CAP")) {
			  activityType="Destress";
			  priority="18";
		  }
		  LocalDate ngayon = getDate();
		  //add 2 week to the current date
		  LocalDate next2Week = ngayon.plus(2, ChronoUnit.WEEKS);

		  click("btn_New");

		  input("txtbx_AssetNum", "1000047");
		  input("txtbx_Description", desc.trim());
		  input("txtbx_Worktype", worktype.trim());

		  //			  WebDriverWait wait = new WebDriverWait(driver, 15);
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
		  save();
		  Log.info("Generated WONUM = " + getAttributeValue("txtbx_WONUM"));
     }	
	  
	  public void createWOwithPlans(String desc, String worktype) throws Exception {
		  Log.info("Generating WONUM...");
		  String activityType = "Drainage";
		  String priority = "4";
		  if (worktype.equals("CAP")) {
			  activityType="Destress";
			  priority="18";
		  }

		  LocalDate ngayon = getDate();
		  //add 2 week to the current date
		  LocalDate next2Week = ngayon.plus(2, ChronoUnit.WEEKS);

		  clickNew();
		  input("txtbx_Priority", priority);
		  input("txtbx_FinancialYear", String.valueOf(ngayon.getYear()));
		  input("txtbx_ScheduledStart", next2Week.toString());
		  input("txtbx_AssetNum", "1000014");
		  input("txtbx_Description", desc.trim());
		  input("txtbx_Worktype", worktype.trim());
		  click("txtbx_ActivityType");
		  input("txtbx_ActivityType", activityType);

		  save();
		  Log.info("Generated WONUM = " + getAttributeValue("txtbx_WONUM"));

		  action.clickPlansTab();
		  //			  add labour plan
		  click("btn_NewRow_Labour");
		  input("txtbx_Trade", "LEVEL1");
		  input("txtbx_Quantity", "2");
		  input("txtbx_PersonHours", "5");
		  Log.info("Added labour plan...");
		  //			  add material plan
		  action.clickMaterialsTab();
		  click("btn_NewRow_Materials");
		  input("txtbx_Item", "1027419");
		  input("txtbx_Quantity", "2");
		  getStoreroom();
		  input("txtbx_DeliveryAddress", "Dummy address");
		  save();

		  Log.info("Added material plan...");
		  //			  Go back to Labour Plan
		  click("tab_Labour");
     }	
	  
	  public void createWOwithServicePlans(String desc, String worktype) throws Exception {
		  Log.info("Generating WONUM...");
		  String activityType = "Drainage";
		  String priority = "4";
		  if (worktype.equals("CAP")) {
			  activityType="Destress";
			  priority="18";
		  }

		  LocalDate ngayon = getDate();
		  //add 2 week to the current date
		  LocalDate next2Week = ngayon.plus(2, ChronoUnit.WEEKS);

		  clickNew();
		  input("txtbx_Priority", priority);
		  input("txtbx_FinancialYear", String.valueOf(ngayon.getYear()));
		  input("txtbx_ScheduledStart", next2Week.toString());
		  input("txtbx_AssetNum", "1000014");
		  input("txtbx_Description", desc.trim());
		  input("txtbx_Worktype", worktype.trim());
		  click("txtbx_ActivityType");
		  input("txtbx_ActivityType", activityType);

		  save();
		  Log.info("Generated WONUM = " + getAttributeValue("txtbx_WONUM"));

		  action.clickPlansTab();
		  action.clickServicesTab();
		  click("btn_NewRow_Services");
		  input("txtbx_ServiceDescription", "service 1");
		  input("txtbx_OrderUnit", "EA");
		  input("txtbx_UnitCost", "30");
		  input("txtbx_CommodityCode", "7000");
		  action.getVendor();
		  input("txtbx_DeliveryAddress", "Dummy address");
		  save();

		  Log.info("Added service plan...");
     }	
	  
	  public void createWOwithMaterialPlans(String desc, String worktype) throws Exception {
		  Log.info("Generating WONUM...");
		  String activityType = "Drainage";
		  String priority = "4";
		  if (worktype.equals("CAP")) {
			  activityType="Destress";
			  priority="18";
		  }
		  LocalDate ngayon = getDate();
		  //add 2 week to the current date
		  LocalDate next2Week = ngayon.plus(2, ChronoUnit.WEEKS);

		  action.clickNew();
		  input("txtbx_AssetNum", "1000014");
		  input("txtbx_Description", desc.trim());
		  input("txtbx_Worktype", worktype.trim());
		  click("txtbx_ActivityType");
		  input("txtbx_ActivityType", activityType);
		  input("txtbx_Priority", priority);
		  input("txtbx_FinancialYear", String.valueOf(ngayon.getYear()));
		  input("txtbx_ScheduledStart", next2Week.toString());

		  Log.info("Generated WONUM = " + getAttributeValue("txtbx_WONUM"));

		  //			  driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
		  //			  Log.info("Saving...");
		  action.clickPlansTab();
		  //		      add materialplan
		  action.clickMaterialsTab();
		  click("btn_NewRow_Materials");
		  //			  item 1027419 cost = 788.34
		  click("btn_LineType");
		  click("option_Material");
		  input("txtbx_ItemDescription", "material");
		  input("txtbx_OrderUnit", "EA");
		  input("txtbx_UnitCost", "300000");
		  //			  input("txtbx_CommodityGroup", "1000MAT");
		  input("txtbx_CommodityCode", "8031");
		  action.getVendor();
		  Log.info("Added material plan...");
		  //			  Go back to Labour Plan
		  //			  driver.findElement(By.xpath(OR.getProperty("tab_Labour"))).click();
		  save();
     }	
	  
	  public void createPO(String desc) throws Exception {
		  action.clickNew();

		  input("txtbx_Description", desc.trim());
		  input("txtbx_Company", "DUMMY");
		  save();
		  Log.info("Generated PO: "+getAttributeValue("txtbx_PONUM"));
	  }
	  
	  public void createPR(String desc) throws Exception {
		  Log.info("Creating PR without lines");
		  action.clickNew();
		  action.input("txtbx_Description", desc);
		  action.click("btn_WO_chevron");
		  action.click("lnk_Related_WO");
		  action.advancedSearch(new String[][] {{"txtbx_Description", "Shakedown"}});
		  action.clickFirstValueFromList();
		  action.clickReturnWithValue();
		  action.input("txtbx_Company", "DUMMY");
		  action.save();
		  action.assertValue("txtbx_Status", "WAPPR");

		  action.route();
		  action.verifyAlert("Cannot continue the process, the PR doesn't have PRLine or has PRLine with Zero Line Cost.");
		  action.clickClose();
		  Log.info("END Generated PR >>>"+action.getAttributeValue("txtbx_PRNUM"));
	  }
	  
	  public void createPRwithLines(String desc) throws Exception {
		  Log.info("Creating PR with lines");
		  action.clickNew();
		  action.input("txtbx_Description", desc);
		  action.click("btn_WO_chevron");
		  action.click("lnk_Related_WO");

		  String[][] toSearch = new String[][] {{"txtbx_Description2", "Shakedown"}, {"txtbx_Status", "APPR"}};
		  action.advancedSearch(toSearch);
		  action.clickFirstValueFromList();
		  action.clickReturnWithValue();
		  action.input("txtbx_Company", "DUMMY");
		  action.save();

		  action.route();
		  action.verifyAlert("Cannot continue the process, the PR doesn't have PRLine or has PRLine with Zero Line Cost.");
		  action.clickClose();

		  action.click("tab_PRLines");
		  action.click("btn_NewRow");
		  action.click("btn_Item_chevron");
		  action.click("lnk_SelectValue");
		  action.clickFirstValueFromList();
		  action.input("txtbx_OrderUnit", "EA");
		  action.input("txtbx_UnitCost", "100");
		  action.input("txtbx_RequiredDate", action.getDate().plusWeeks(3).toString());
		  if (action.isReadOnly("txtbx_ConversionFactor") == false) {
			  action.input("txtbx_ConversionFactor", "1");
		  }

		  action.save();
		  action.assertValue("txtbx_Status", "WAPPR");

		  Log.info("END Generated PR >>>"+action.getAttributeValue("txtbx_PRNUM"));
	  }
	  
	  public void createLinearAsset(String desc, boolean hasParent) throws Exception {
		  Log.info("Creating linear asset");
		  action.clickNew();
		  input("txtbx_AssetDescription", desc.trim());
		  click("txtbx_Linear");
		  input("txtbx_Discipline", "Signals");
		  input("txtbx_Region", "Northern");
		  input("txtbx_Area", "Auckland Metro");
		  input("txtbx_Line", "Noline");
		  action.click("btn_Save");  //don't call save() as this needs to verify alert
		  action.verifyAlert("BMXAA6141E");
		  action.clickOK();

		  input("txtbx_LRM", "METRES");
		  input("txtbx_AssetStartMeasure", "1");
		  input("txtbx_AssetEndMeasure", "10");

		  if (hasParent) {
			  action.click("btn_Parent_chevron");
			  action.click("lnk_SelectValue");
			  action.clickFirstValueFromList();
		  }
		  save();

		  action.assertValue("txtbx_Status", "PROPOSED");
		  Log.info("Generated Asset: "+getAttributeValue("txtbx_AssetNum"));
	  }
	  
	  public void createAsset(String desc, boolean hasParent) throws Exception {
		  Log.info("Creating non-linear asset");
		  action.clickNew();
		  input("txtbx_AssetDescription", desc.trim());
		  input("txtbx_Discipline", "TRACK");
		  input("txtbx_Region", "Central");
		  input("txtbx_Area", "Wellington");
		  input("txtbx_Line", "JVILL");
		  input("txtbx_StartMetrage", "95.500");
		  input("txtbx_EndMetrage", "96.600");

		  if (hasParent) {
			  action.click("btn_Parent_chevron");
			  action.click("lnk_SelectValue");
			  action.clickFirstValueFromList();
		  }
		  save();

		  action.assertValue("txtbx_Status", "PROPOSED");
		  Log.info("Generated Asset: "+getAttributeValue("txtbx_AssetNum"));
	  }
	  
	  public void createLocation(String desc) throws Exception {
		  Log.info("Creating Location");
		  action.clickNew();
		  input("txtbx_Type", "OPERATING");
		  input("txtbx_LocationDescription", desc);
		  input("txtbx_Discipline", "TRACK");
		  input("txtbx_Region", "Central");
		  input("txtbx_Area", "Wellington");
		  input("txtbx_Line", "JVILL");
		  input("txtbx_StartMetrage", "97.569");
		  input("txtbx_EndMetrage", "98.568");
		  save();
		  assertValueOnLabel("Start Metrage", "97.569");
		  assertValueOnLabel("End Metrage", "98.568");
		  Log.info("Generated Location: "+getAttributeValue("txtbx_Location"));
	  }
	  
	  public void createClassification(String desc) throws Exception {
		  Log.info("Creating Classification");
		  action.clickNew();
		  input("txtbx_Type", "OPERATING");
		  input("txtbx_ClassificationDescription", desc.trim());
		  input("txtbx_Discipline", "TRACK");
		  input("txtbx_Region", "Central");
		  input("txtbx_Area", "Wellington");
		  input("txtbx_Line", "JVILL");
		  input("txtbx_StartMetrage", "97.569");
		  input("txtbx_EndMetrage", "98.568");
		  save();
		  assertValueOnLabel("Start Metrage", "97.569");
		  assertValueOnLabel("End Metrage", "98.568");
		  Log.info("Generated Classification: "+getAttributeValue("txtbx_Classification"));
	  }
	  
	  public void createJobPlan(String desc) throws Exception {
		  Log.info("Create Job Plan...");
		  clickNew();
		  input("txtbx_JobPlanDescription", desc);

		  click("btn_NewRow_Labour");
		  input("txtbx_Trade", "LEVEL1");
		  input("txtbx_Quantity", "2");
		  input("txtbx_PersonHours", "5");
		  Log.info("Added labour plan...");
		  //			  add material plan
		  clickMaterialsTab();
		  click("btn_NewRow_Materials");

		  input("txtbx_Item", "1027419");
		  input("txtbx_Quantity", "2");
		  action.getStoreroom();
		  //			  driver.findElement(By.xpath(OR.getProperty("txtbx_ConditionCode"))).sendKeys("NEW");
		  Log.info("Added material plan...");
		  //			  Go back to Labour Plan
		  action.clickLabourTab();
		  save();
		  Log.info("Generated JobPlan: "+getAttributeValue("txtbx_JobPlan"));
	  }
	  
	  /*
	   * Will return true if successfully routed to workflow
	   */
	  public boolean inWorkflow() throws Exception {
		  boolean inWorkflow = false;
 		  scrollDown("hvr_Workflow");
			hover("hvr_Workflow", "lnk_WFAssignment");
			waitElementExists("tbl_WFAssignment");
			if(driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0")) {
				inWorkflow = false;
			} else { inWorkflow = true; }
		  return inWorkflow;
	  }
	  
	  
	  public void addValue(String object, String data) throws Exception {
		  String objectVal = getAttributeValue(object);
		  double objectInt = StringUtil.toLong(objectVal);
		  double dataInt = Double.valueOf(data);
		  double newVal = (objectInt * dataInt) + objectInt;
		  clear(object);
		  Log.info("Cleared object");
		  input(object, String.valueOf(newVal));
	  }
 
	  public void changeStatus(String toStatus) throws Exception {
		  String object = "txtbx_Status";
		  String status = null;
		  String oldStatus = null;
		  switch (toStatus.toLowerCase()) {
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
		  oldStatus = getAttributeValue(object);

		  //            if status is changed to complete/close 
		  if (status == "COMP") {
			  click("lnk_Complete");
		  } else if (status == "CLOSE") {
			  click("lnk_Close");
		  } else {
			  action.click("lnk_ChangeStatus");
			  action.click("txtbx_NewStatus");
			  Log.info("clicked new status Drop-down.......................");
			  action.click(By.xpath("//span[contains(@id,'"+status+"_OPTION_a_tnode')]"));
			  Log.info("clicked new status......................... "+status);
		  }

		  Thread.sleep(1000);
		  action.clickOK();
		  wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(OR.getProperty("dialog_Wait"))));

		  if (action.elementExists("systemMessage")) {
			  Log.info("Alert >>>>>>");
			  driver.switchTo().activeElement().click();
			  Thread.sleep(1000);
			  wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(OR.getProperty("dialog_Wait"))));
		  }

		  Log.info("old status = " +oldStatus);
		  Log.info("new status = " +getAttributeValue(object));
		  Assert.assertTrue(getAttributeValue(object).trim().toUpperCase().equals(status), "Expected= "+status+ "; Actual="+getAttributeValue(object));
	  }	
	  
	  /*
	   * Manually populate Required date after duplicate WO/PO/PR
	   */
	  public void duplicate() throws Exception {
		  click("btn_Duplicate");
		  waitElementExists("titlebar_message");

		  Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty("titlebar_message"))).getText().trim().startsWith(duplicateMsg), "Assertion failed.");
	  }

	  public void save() throws Exception {
			try {
				click("btn_Save");
				waitElementExists("titlebar_message");
				
				if(elementExists(By.xpath("//label[text()='System Message']"))) {
					Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty("titlebar_message"))).getText().trim().contains(saveMsg), "Assertion failed.");
		        }
				Thread.sleep(2000);
			} catch(AssertionError ae){
				Log.error("Assertion failed save--- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, "save: " +ae.getMessage());
				bResult = false;
				Assert.fail();
			} catch(Exception e){
		 		Log.error("Exception save--- " + e.getMessage());
		 		extentTest.log(LogStatus.ERROR, "save: " +e.getMessage());
		 		bResult = false;
		 		Assert.fail();
		    }
	}  
	  
	public void getStoreroom() throws Exception {
		click("btn_Storeroom_chevron");
		Thread.sleep(1000);
		if (elementExists(By.xpath(".//span[contains(., 'Select Value')]"))) {
			click("lnk_SelectValue");
		}
		click("lnk_List_Code");
	}  
	
	public void getActivityType() throws Exception {
		click("btn_ActivityType");
		clickFirstValueFromList();
	}  
	 
	public void getVendor() throws Exception {
		click("btn_Vendor_chevron");
		click("lnk_SelectValue");
		click("lnk_List_Code");
	}  
		
    public void captureSystemMessage(String object, String filename) throws Exception {
			
			Log.info("Capturing the text in the System Message");
			
			WebElement divMsgBox = driver.findElement(By.xpath(".//label[@id='" + object+ "']"));

			String info = divMsgBox.getText();
			String idForLog = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
			File file = new File(Constants.extentReportPath+"//"+ filename + ".txt");
			
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
		}
	  
	  public void logMessage(String object, String msg) throws Exception {
			
			Log.info("Create Message");
			
			String code = getAttributeValue(object);
			String divMsgBox = msg;
			String idForLog = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
			File file = new File(Constants.extentReportPath+"//"+ "LoggedMessages" + ".txt");

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
		}

	  public void whereClause(String data) throws Exception {
		  String advancedSearchMenu = "//img[@alt='Advanced Search menu']";
		  String searchOption = "//span[@id='menu0_SEARCHWHER_OPTION_a_tnode']";
		  waitElementExists(By.xpath(advancedSearchMenu));
		  driver.findElement(By.xpath(advancedSearchMenu)).click();
		  waitElementExists(By.xpath(searchOption));
		  driver.findElement(By.xpath(searchOption)).click();
		  clear("txtarea_whereClause");
		  input("txtarea_whereClause", data.trim());
		  click("btn_Find");
		  wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(OR.getProperty("btn_Find"))));
      }	
	  
	  public void clearWhereClause() throws Exception {
		  waitElementExists("btn_Advanced_Search");
		  click("btn_Advanced_Search");
		  click("btn_Revise");
		  driver.findElement(By.xpath("//span[contains(.,'Clear Query and Fields')]")).click();
      }
	  
	  public static void populateDate(String object, String date) throws Exception {
		  String petsa = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		  if (date.equals("")) {
			  Log.info("populateDate" + new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()));
			  driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(petsa);
		  } else {
			  driver.findElement(By.xpath("//input[@id=(//label[contains(., '"+object+"')]/@for)]")).sendKeys(date);
		  }
     }	
	  
	  public String getAttributeValue(String element) throws Exception {
		String value = null;
		
		waitElementExists(element);
		value = driver.findElement(By.xpath(OR.getProperty(element))).getAttribute("value");

		return value;
	  }
	  
	  public String getAttributeValue(By xpath) throws Exception {
			String value = null;
		  	
			waitElementExists(xpath);
			value = driver.findElement(xpath).getAttribute("value");

			return value;
	  }
 
	  /*
	   * Close driver when calling this method and reopen after called or stay logged in as user of this method
	  */
	  public void changeWOStatusToPlan(String wonum, String worktype) throws Exception {
		  Log.info("Change WO status to PLAN = "+wonum +" " +worktype);
		  String user="mxeng1";
		  if (worktype.equals("AMREN")) {
			  user="mxsvcemgr1";
		  }

		  openBrowser("Chrome");
		  login(user);
		  action.goToWOPage(); 
		  action.quickSearch(wonum);

		  changeStatus("ready to plan");
		  changeStatus("planned");
		  save();
	   }
	  
	 /*
	 * Stored value of the object to approve and wf approver should be set before calling this method
	 * ex:  Assert.assertFalse(action.getWFApprover().isEmpty()); //calling getWFApprover() stores WFApprover
	 			action.storeValue("txtbx_AssetNum", "ASSETNUM"); 
	*/
	  public void approveWF() throws Exception{
	 	action.loginApprover();
		action.openWFAssignment();
		action.clickOK();
     }
	  
	  public void approveWOWF(String wonum) throws Exception {
		try { 
		  Log.info("Start approveWO........................"+wonum);
		  action.goToWOPage();
		  action.quickSearch(wonum);
		  
		  boolean inWorkflow = true;
		  int wfLevel = 1;
		  
		  hover("hvr_Workflow", "lnk_WFAssignment");
		 
  		  waitElementExists("tbl_WFAssignment");

		  inWorkflow = driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0");
		  Base.WFApprover = driver.findElement(By.xpath("//table[contains(@summary,'Workflow Assignments')]/tbody/tr[4]/td[2]")).getText();
		  action.storeValue("txtbx_WONUM", "WONUM");
		  
		  Log.info("IsNull table approver =" +driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0"));
		  action.clickOK();
		  
		  while (!inWorkflow) {
//			  close current driver
			  driver.close();
			  action.openBrowser("Chrome");
			  action.loginApprover();
			  
			  Log.info("WF Approval Level " +wfLevel+ " Approver = "+Base.WFApprover);
			  action.openWFAssignment();
			  action.clickOK();

			  action.hover("hvr_Workflow", "lnk_WFAssignment");
			  action.waitElementExists("tbl_WFAssignment");
			  inWorkflow = driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0");
			  if (!inWorkflow) {
				  Base.WFApprover = driver.findElement(By.xpath("//table[contains(@summary,'Workflow Assignments')]/tbody/tr[4]/td[2]")).getText();
				  Log.info("IsNull table approver =" +driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0"));
				  
			  }
			  action.clickOK();
			  wfLevel++;
		  }
		  action.assertValue("txtbx_Status", "APPR;WMATL");
		  Log.info("END approveWO........................"+wonum);
	    } catch(AssertionError ae){
			Log.error("Assertion failed approveWF--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, wonum +": approveWF--- "+ae.getMessage());
 			bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found approveWF--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, wonum +": approveWF--- "+e.getMessage());
 			bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception approveWF--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, wonum +": approveWF--- "+e.getMessage());
 			bResult = false;
 			Assert.fail();
	    }	
     }	  
	  
	  /*
	   * Returns the first approver from the list
	   */
	  public String getWFApprover() throws Exception {
		  String approver = "";
		  action.goToWorkflowAssignment();
		  if (!action.isEmpty("tbl_WFAssignment")) {
			  action.storeValue("txtbx_WFApprover", "WFApprover");
			  approver = action.getStoredValue("WFApprover");
		  } 
		  action.clickOK();
		  Log.info("Approver== "+ approver);
		  return approver;
	  }
	  
//	  TODO
//	  public String getUsersInPersonGroup(String personGroup) throws Exception {
//		  action.login("maxadmin");
//		  action.goToPersonGroupPage();
//		  
//		  action.quickSearch(personGroup);
//		  String approver = action.get
//		  		 
//		  return approver;
//	  }
	  /*
	   * Close driver when calling this method and reopen after called
	  */
	  public void deactivateVendor(String vendor, boolean status) throws Exception {
		  boolean vendorStatus = false;
		  //close current driver
		  driver.close();

		  Log.info("activateVendor Vendor..................");
		  openBrowser("Chrome");
		  login("maxadmin");
		  action.goToCompaniesPage();

		  action.quickSearch(vendor);

		  Log.info("Check chkbx_DisqualifyVendor..................");
		  action.waitElementExists("chkbx_DisqualifyVendor"); 
		  vendorStatus = action.isChecked("chkbx_DisqualifyVendor");

		  Log.info("is DisqualifyVendor checked? = "+vendor +" " +vendorStatus);

		  if (vendorStatus != status) {			
			  action.click("chkbx_DisqualifyVendor");
			  action.save();
		  }	

		  Log.info("is DisqualifyVendor checked = "+vendor +" " +action.isChecked("chkbx_DisqualifyVendor"));
		  driver.close();
	  }

 
	  public void goToHomePage() throws Exception {
  		  click("lnk_Home");
	  }

	  public void goToTicketTemplates() throws Exception {
		  scrollDown("hvr_ServiceDesk");
		  hover("hvr_ServiceDesk", "lnk_TicketTemplates");
	  }
	  
	  public void goToWorkflowAssignment() throws Exception {
		  scrollDown("hvr_Workflow");
		  hover("hvr_Workflow", "lnk_WFAssignment");
		  waitElementExists("tbl_WFAssignment");
	  }

	  public void goToWOPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_WO");
		hover("hvr_WO", "lnk_WO");
	  }

	  public void goToPMPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_PM");
		hover("hvr_PM", "lnk_PM");
	  }
	  
	  public void goToSRPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_WO");
		hover("hvr_WO", "lnk_SR");
	  }
	  
	  public void goToAssetPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_Asset");
		hover("hvr_Asset", "lnk_Asset");
	  }
	  
	  public void goConditionMonitoringPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_Asset");
		hover("hvr_Asset", "lnk_ConditionMonitoring");
	  }

	  public void goMetersPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_Asset");
		hover("hvr_Asset", "lnk_Meter");
	  }
	  
	 public void goRelationshipsPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_Asset");
		hover("hvr_Asset", "lnk_Relationship");
    }
	 
	 public void goEssentialFeaturesPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_Asset");
		hover("hvr_Asset", "lnk_EssentialFeatures");
	 }
	 
	 public void goMeterGroupPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_Asset");
		hover("hvr_Asset", "lnk_MeterGroup");
	 }
	
	 public void goQuickReportingPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_WO");
		hover("hvr_WO", "lnk_QuickReporting");
     }
	 
	 public void goFailureCodesPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_Asset");
		hover("hvr_Asset", "lnk_FailureCode");
     }
 
	  public void goToPRPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_Purchasing");
		hover("hvr_Purchasing", "lnk_PR");
	  }
	  
	  public void goToPOPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_Purchasing");
		hover("hvr_Purchasing", "lnk_PO");
	  }
	  
	  public void goToRoutesPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_Planning");
		hover("hvr_Planning", "lnk_Routes");
	  }
	  
	  public void goToJobPlansPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_Planning");
		hover("hvr_Planning", "lnk_JobPlans");
	  }

	  public void goToAddAttributes() throws Exception {
		goToHomePage();
		scrollDown("lnk_AddModifyProperties");
		hover("lnk_AddModifyProperties", "lnk_AddAttributes");
	  }
	  
	  /*
	   * index starts at 0
	   */
	  public void goToRelatedWO(int row) throws Exception {
		row = row-1;
		By btn_Related_Chevron = By.xpath("//img[contains(@id,'_tdrow_[C:1]_txt-img[R:" + row +"]')]");
		action.click(btn_Related_Chevron);
		action.click("lnk_Related_WO");
	  }
	  
	  public void goToRelatedSR(int row) throws Exception {
		By btn_Related_Chevron = By.xpath("//img[contains(@id,'_tdrow_[C:1]_txt-img[R:" + row +"]')]");
		action.click(btn_Related_Chevron);
		action.click("lnk_Related_SR");
	  }
		
	  public void goToCreateWO() throws Exception {
		scrollDown("hvr_Create");
		hover("hvr_Create", "lnk_CreateWO");
	  }

	  public void goToAddClassification() throws Exception {
		goToHomePage();
		scrollDown("lnk_AddModifyProperties");
		hover("lnk_AddModifyProperties", "lnk_AddClassification");
	  }
	  
	  public void goToFeaturePage() throws Exception {
		goToHomePage();
		scrollDown("hvr_Asset");
		hover("hvr_Asset", "lnk_Feature");
	  }
	  
	  public void goToLocationPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_Asset");
		hover("hvr_Asset", "lnk_Location");
	  }
	  
	  public void goToCompaniesPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_Purchasing");
		hover("hvr_Purchasing", "lnk_Companies");
	  }

	  public void goToClassificationsPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_Administration");
		hover("hvr_Administration", "lnk_Classifications");
	  }
	  
	  public void goToPersonGroupPage() throws Exception {
		goToHomePage();
		scrollDown("hvr_Administration");
		hover("hvr_Administration", "lnk_Resources", "lnk_PersonGroups");
	  }
		
	   public void quickSearch(String q) throws Exception {
		   Log.info("Quick search >>>>> "+q);
			input("txtbx_QuickSearch", q);
			enter("txtbx_QuickSearch");
			Thread.sleep(2000);
		}
		
		public void clickFirstValueFromList() throws Exception {
			click("lnk_List_Code");
		} 
		
		public void route() throws Exception {
			click("btn_Route");
		}
		
		public void clickNo() throws Exception {
			click("btn_No");
		}
		
		public void clickYes() throws Exception {
			click("btn_Yes");
		}
		
		public void clickOK() throws Exception {
			click("btn_OK");
		}
		
		public void clickSearch() throws Exception {
			click("btn_Search");
		}
		
		public void clickClose() throws Exception {
			click("btn_Close");
		}
		
		public void clickNewRow() throws Exception {
			click("btn_NewRow");
		}
		
		public void clickReturn() throws Exception {
			click("btn_Return");
		}
		
		public void clickReturnWithValue() throws Exception {
			click("btn_ReturnValue");
		}
		
		public void clickDeleteDetailsRow1() throws Exception {
			click("btn_DeleteDetails_Row1");
		}
		
		public void clickListViewTab() throws Exception {
			click("tab_ListView");
		}
		
		public void clickMainTab() throws Exception {
			click("tab_Main");
		}
		
		public void clickActiveElement() {
			driver.switchTo().activeElement().click();
		}	
		
		public void clickViewDetailsRow1() throws Exception {
			action.waitElementExists("btn_ViewDetails_Row1");
			String title = driver.findElement(By.xpath(OR.getProperty("btn_ViewDetails_Row1"))).getAttribute("title");
			if ((title == "View Details") || title.equals("View Details")) {
				click("btn_ViewDetails_Row1");
			} //else do nothing as it's already open
			Thread.sleep(1000);
		}
		
		public void clickCloseDetailsRow1() throws Exception {
			action.waitElementExists("btn_ViewDetails_Row1");
			String title = driver.findElement(By.xpath(OR.getProperty("btn_ViewDetails_Row1"))).getAttribute("title");
			if ((title == "Close Details") || title.equals("Close Details")) {
				click("btn_ViewDetails_Row1");
			} //else do nothing as it's already closed
			Thread.sleep(1000);
		}
		
		public void clickRelatedRecordTab() throws Exception {
			click("tab_RelatedRecord");
		}
		
		public void clickLogTab() throws Exception {
			click("tab_Log");
		}
		
		public void clickPlansTab() throws Exception {
			click("tab_Plans");
		}
		
		public void clickLabourTab() throws Exception {
			click("tab_Labour");
		}
		
		public void clickPRLinesTab() throws Exception {
			click("tab_PRLines");
		}
		
		public void clickPRTab() throws Exception {
			click("tab_PR");
		}
		
		public void clickToolsTab() throws Exception {
			click("tab_Tools");
		}
		
		public void clickMaterialsTab() throws Exception {
			click("tab_Materials");
		}
		
		public void clickServicesTab() throws Exception {
			click("tab_Services");
		}
		
		public void clickAssetsTab() throws Exception {
			click("tab_Assets");
		}
		
		public void clickLocationsTab() throws Exception {
			click("tab_Locations");
		}
		
		public void clickSpecificationsTab() throws Exception {
			click("tab_Specifications");
		}
		
		public void clickMetersTab() throws Exception {
			click("tab_Meters");
		}
		
		public void clickPOLinesTab() throws Exception {
			click("tab_POLines");
		}
		
		public void clickPOTab() throws Exception {
			click("tab_PO");
		}
		
		public void clickRelationshipsTab() throws Exception {
			click("tab_Relationships");
		}
		
		public void clickFeaturesTab() throws Exception {
			click("tab_Features");
		}
		
		public void clickSafetyTab() throws Exception {
			click("tab_Safety");
		}
		
		public void clickRelatedLocationsTab() throws Exception {
			click("tab_RelatedLocations");
		}
		
		public void clickSubAssembliesTab() throws Exception {
			click("tab_Sub-Assemblies");
		}
		
		public void clickUserTab() throws Exception {
			click("tab_User");
		}
		
		public void clickGroupsTab() throws Exception {
			click("tab_Groups");
		}
		
		/* 
		 * Wait functions
		 * 
		 */
		  
		public void waitForPageLoad(WebDriver driver) {
			    new WebDriverWait(driver, 60).until((ExpectedCondition<Boolean>) wd ->
			            ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
		}
		
	
		/*
		 * Checks if element is displayed - accepts object OR name and true/false value
		 */
		public void elementExists(String object, boolean status) throws InterruptedException { 
		    try {
		    	Log.info("Checks if element exists: "+ object);
		    	if (status) {
				    Assert.assertTrue(elementExists(By.xpath(OR.getProperty(object))));
				} else {
					Assert.assertFalse(elementExists(By.xpath(OR.getProperty(object))));
				}
		    	Thread.sleep(2000);
		    }catch(AssertionError ae){
				Log.error(object +": Assertion failed elementExists--- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, object +": elementExists--- "+ae.getMessage());
				bResult = false;
				Assert.fail();
		    } catch (NoSuchElementException e) {
		    	Log.error(object +": Element not found elementExists--- " + e.getMessage());
		    	extentTest.log(LogStatus.ERROR, object +": elementExists--- "+e.getMessage());
	 			bResult = false;
	 			Assert.fail();
		    }
		}
		
		public boolean elementExists(String object) throws Exception {
			boolean exists = true;
		    try {
		    	WebElement ane = driver.findElement(By.xpath(OR.getProperty(object)));
		    	if (ane != null && ane.isDisplayed()) {
		    		exists = true;
				}  else { exists = false; };
		    } catch (NoSuchElementException e) {
		        return false;
		    }
		    return exists;
		}
	  
		
		//to verify if element exists; for negative testing
		public static boolean existsElement(String id) throws Exception {
			 try {
//			    	driver.findElement(By.xpath(OR.getProperty(id)));
				 driver.findElement(By.xpath("//label[text()='"+id+"']"));
			 } catch (NoSuchElementException e) {
				 return false;
			 }
			 return true;
		}
					
		public boolean elementExists(By xpath) {
			boolean exists = true;
		    try {
		    	WebElement ane = driver.findElement(xpath);
		    	if (ane != null && ane.isDisplayed()) {
		    		exists = true;
				}  else { exists = false; };
		    } catch (NoSuchElementException e) {
		        return false;
		    }
		    return exists;
		}
			

		public void waitElementExists(String object) throws Exception {
			try{
				Log.info("Checks Webelement exists: "+ object);
//				WebDriverWait wait = new WebDriverWait(driver, 90);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));
				waitForElementDisplayed(object);
			} catch (NoSuchElementException e) {
		    	Log.error(object+"- Element not found waitElementExists--- " + e.getMessage());
		    	extentTest.log(LogStatus.ERROR, object +": waitElementExists--- "+e.getMessage());
	 			bResult = false;
	 			Assert.fail();	
			} catch(Exception e){
	 			Log.error(object+"- Exception waitElementExists--- " + e.getMessage());
	 			extentTest.log(LogStatus.ERROR, object +": waitElementExists--- "+e.getMessage());
	 			bResult = false;
	 			Assert.fail();
	        }
		}
		
		public void waitElementExists(By xpath) throws Exception {
			try{
				Log.info("Checks Webelement exists: "+ xpath.toString());
//				WebDriverWait wait = new WebDriverWait(driver, 90);
				wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
				elementDisplayed(xpath);
			} catch (NoSuchElementException e) {
		    	Log.error("Element not found waitElementExists--- " + e.getMessage());
		    	extentTest.log(LogStatus.ERROR, "waitElementExists: "+e.getMessage());
	 			bResult = false;
	 			Assert.fail();	
			} catch(Exception e){
	 			Log.error("Exception waitElementExists--- " + e.getMessage());
	 			extentTest.log(LogStatus.ERROR, "waitElementExists: "+e.getMessage());
	 			bResult = false;
	 			Assert.fail();
	        }
		}
		
		public void waitElementExists(WebElement element) throws Exception {
			try{
				Log.info("Checks Webelement exists: "+ element.getTagName());
//				WebDriverWait wait = new WebDriverWait(driver, 90);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(element.getAttribute("id"))));
//				elementDisplayed(By.xpath(element); //TODO implement xpath
				Thread.sleep(1000);
			} catch (NoSuchElementException e) {
		    	Log.error("Element not found waitElementExists--- " + e.getMessage());
		    	extentTest.log(LogStatus.ERROR, element.toString() +": waitElementExists--- "+e.getMessage());
	 			bResult = false;
	 			Assert.fail();	
			} catch(Exception e){
	 			Log.error("Exception waitElementExists--- " + e.getMessage());
	 			extentTest.log(LogStatus.ERROR, element.toString() +": waitElementExists--- "+e.getMessage());
	 			bResult = false;
	 			Assert.fail();
	        }
		}
		
		public boolean waitForListViewDisplayed(String tableName) throws Exception{
			int i = 0;
			Log.info("Waiting for element to display");
			while (!elementDisplayed(By.xpath("//table[contains(@summary,"+tableName+")]"))&&(i <= 60)){
				System.out.println("Searching for element..."+i);
				Thread.sleep(1000);
				i++;
			}
			
			
			if (i > 60){
				System.out.println(tableName+" list: Element not located in a reasonable timeframe");
//				extentTest.log(LogStatus.ERROR, "Element not located in a reasonable timeframe");
				throw new Exception(tableName+" list: Element not located in a reasonable timeframe");
			//	return false;
			} else{
				Thread.sleep(2000);
				return true;
			}
		}
		
//		private void waitForRefreshed(WebElement self) {
//			new FluentWait<webdriver>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS)
//			.until(new ExpectedCondition<boolean>() {
//				public Boolean apply(WebDriver d) {
//					try {
//						self.findElement(By.xpath("/self::*"));
//					}
//					catch (StaleElementReferenceException e)
//					{
//						return true;
//					}
//					return false;
//				}
//			});	
//		}
		
		
		
		/**
		 * This method verifies if an element is being displayed and returns true/false
		 */
		private boolean elementDisplayed(By xpath) throws Exception {
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
//				extentTest.log(LogStatus.ERROR, e.getMessage());
				return false;
			}
			System.out.println("Element Not Found.");	
			return false;
		}

		private boolean waitForElementDisplayed(String object) throws Exception {
			int i = 0;
			Log.info("Waiting for element to display");
			while (!elementDisplayed(By.xpath(OR.getProperty(object)))&&(i <= 60)){
				System.out.println("Searching for element..."+i);
				Thread.sleep(1000);
				i++;
			}
			
			if (i > 60){
				System.out.println(object+": Element not located in a reasonable timeframe");
		    	bResult = false;
	 			Assert.fail();
				throw new Exception(object+": Element not located in a reasonable timeframe");
			//	return false;
			} else{
				Thread.sleep(2000);
				return true;
			}
		}
		
		private boolean waitForElementDisplayed(By xpath) throws Exception {
			int i = 0;
			Log.info("Waiting for element to display");
			while (!elementDisplayed(xpath)&&(i <= 60)){
				System.out.println("Searching for element..."+i);
				Thread.sleep(1000);
				i++;
			}
			
			if (i > 60){
				System.out.println(xpath.toString()+": Element not located in a reasonable timeframe");
				bResult = false;
	 			Assert.fail();
				throw new Exception(xpath.toString()+": Element not located in a reasonable timeframe");
			} else{
				Thread.sleep(2000);
				return true;
			}
		}
		
	   private void elementClickable(String object) throws Exception {
			try{
				Log.info("Checks Webelement is clickable: "+ object);
//				WebDriverWait wait = new WebDriverWait(driver, 30);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			} catch (Throwable e) {
				Log.error("Exception elementClickable--- " + e.getMessage());
		 		extentTest.log(LogStatus.ERROR, object +": elementClickable--- "+e.getMessage());
		 		bResult = false;
		        Assert.fail("Timeout waiting for Page Load Request to complete.");
		    }
	   }	
		    
		private void elementClickable(By object) throws Exception {
			try{
				Log.info("Checks Webelement is clickable: "+ object);
//				WebDriverWait wait = new WebDriverWait(driver, 30);
				wait.until(ExpectedConditions.elementToBeClickable((object)));
			 }catch(Exception e){
				Log.error("Exception elementClickable--- " + e.getMessage());
				extentTest.log(LogStatus.ERROR, object +": elementClickable--- "+e.getMessage());
				bResult = false;
				Assert.fail();
		     }
		}
		    
}