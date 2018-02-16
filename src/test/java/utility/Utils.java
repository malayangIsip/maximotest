package utility;

import static executionEngine.DriverScript.OR;
import static executionEngine.DriverScript.driver;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.Constants;
import executionEngine.DriverScript;

public class Utils {
	
//	public static WebDriver driver = null;
	
	public static void waitForElement(WebElement element){
		 
		 WebDriverWait wait = new WebDriverWait(driver, 10);
	     wait.until(ExpectedConditions.elementToBeClickable(element));
	 	}
		
	 public static void takeScreenshot(WebDriver driver, String sTestCaseName) throws Exception{
			try{
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(Constants.Path_Screenshot + sTestCaseName +".jpg"));	
			} catch (Exception e){
				Log.error("Class Utils | Method takeScreenshot | Exception occured while capturing ScreenShot : "+e.getMessage());
				throw new Exception();
			}
		}
	 
	 public static void elementExists(String object){
			try{
				Log.info("Checks Webelement exists: "+ object);
				WebDriverWait wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath(OR.getProperty(object)))));
			 }catch(Exception e){
	 			Log.error("Element does not exists --- " + e.getMessage());
	 			DriverScript.bResult = false;
	         }
		}
		
	 public static void elementClickable(String object){
			try{
				Log.info("Checks Webelement is clickable: "+ object);
				WebDriverWait wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			 }catch(Exception e){
	 			Log.error("Element does not exists --- " + e.getMessage());
	 			DriverScript.bResult = false;
	         }
		}
		

}
