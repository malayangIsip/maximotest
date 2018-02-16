package config;

import static executionEngine.DriverScript.OR;
import static executionEngine.DriverScript.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import executionEngine.DriverScript;
import utility.Log;

public class RailWearMeterReadings extends ActionKeywords {

	static String tableName="Metrage and Date of Previous Reading Sets";

	public static void railWearMeterReadings(String object, String data) {
		Log.info("Start RailWear Meter Readings for Asset 1000060....");
//		verifyCheckbox(); -- app error; can enter rail wear even if not checked
		searchRailWear();
		enterRailWear();
	
	}
	
	static void verifyCheckbox() {
		try {
			Log.info("Start verifyCheckbox....");
			driver.findElement(By.xpath(OR.getProperty("btn_Advanced_Search"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_ParentClassification"))).sendKeys("TRACK");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Classification"))).sendKeys("MAINL");
			driver.findElement(By.xpath(OR.getProperty("btn_Find"))).click();
			waitForElementDisplayed("chkbx_AllowRailWearMeters", "1");

			
	//		Allow Rail Wear Meters is checked
			isChecked("chkbx_AllowRailWearMeters", "True");
			
	//		Go to Asset and search for linear asset with track/mail classification, i.e. 1000060
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_Asset", "1");
			hover("hvr_Asset","lnk_Asset");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys("1000060");
			enter("txtbx_QuickSearch","1");
			waitForElementDisplayed("lnk_EnterRailWearReadings", "1");
	
	//      Enter Rail Wear Readings link should exists
			elementExists("lnk_EnterRailWearReadings", "True");
			
	//		uncheck Allow Rail Wear Meters
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_Administration", "1");
			hover("hvr_Administration","lnk_Classifications");
			driver.findElement(By.xpath(OR.getProperty("btn_Advanced_Search"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Classification"))).sendKeys("MAINL");
			driver.findElement(By.xpath(OR.getProperty("txtbx_ParentClassification"))).sendKeys("TRACK");
			driver.findElement(By.xpath(OR.getProperty("btn_Find"))).click();
			waitForElementDisplayed("chkbx_AllowRailWearMeters", "1");
			driver.findElement(By.xpath(OR.getProperty("chkbx_AllowRailWearMeters"))).click();
			save("1","1");
			
	//		Allow Rail Wear Meters is not checked
			isChecked("chkbx_AllowRailWearMeters", "False");
						
	//		Go to Asset and search for linear asset with track/mail classification, i.e. 1000060
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_Asset", "1");
			hover("hvr_Asset","lnk_Asset");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys("1000060");
			enter("txtbx_QuickSearch","1");
			waitForElementDisplayed("lnk_EnterRailWearReadings", "1");
	
	//      Enter Rail Wear Readings link should not exist
			elementExists("lnk_EnterRailWearReadings", "False");
			
    //		check Allow Rail Wear Meters
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_Administration", "1");
			hover("hvr_Administration","lnk_Classifications");
			driver.findElement(By.xpath(OR.getProperty("btn_Advanced_Search"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Classification"))).sendKeys("MAINL");
			driver.findElement(By.xpath(OR.getProperty("txtbx_ParentClassification"))).sendKeys("TRACK");
			driver.findElement(By.xpath(OR.getProperty("btn_Find"))).click();
			waitForElementDisplayed("chkbx_AllowRailWearMeters", "1");
			driver.findElement(By.xpath(OR.getProperty("chkbx_AllowRailWearMeters"))).click();
			save("1","1");
				
		//	Allow Rail Wear Meters is checked
			isChecked("chkbx_AllowRailWearMeters", "True");
			waitFor();
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
	
	static void searchRailWear() {
		try {
			Log.info("Start searchRailWear....");
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_Asset", "1");
			hover("hvr_Asset","lnk_Asset");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys("1000060");
			enter("txtbx_QuickSearch","1");
			waitForElementDisplayed("lnk_EnterRailWearReadings", "1");
			
//			Go to ‘Enter Rail Wear Readings'
			driver.findElement(By.xpath(OR.getProperty("lnk_EnterRailWearReadings"))).click();
//			Click search without populating the search parameters
			driver.findElement(By.xpath(OR.getProperty("btn_Search"))).click();
			waitForElementDisplayed("systemMessage", "1");
			verifyAlert("msg_Popup", "No search parameters entered.");
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			waitFor();
//			Click New
			Log.info("Click New..");
			driver.findElement(By.xpath("//button[@type='button' and contains (., 'New')]")).click();
			Log.info("Wait for msg to display");
			waitForElementDisplayed("systemMessage", "1");
			Log.info("verify alert");
			verifyAlert("msg_Popup", "Please performe a search before entering new Metrage");
			Log.info("Click OK");
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			waitFor();
			
//			input values to search parameters and search
			Log.info("Input values...");
			driver.findElement(By.xpath(OR.getProperty("txtbx_KMFrom"))).sendKeys("0");
			driver.findElement(By.xpath(OR.getProperty("txtbx_KMTo"))).sendKeys("3");
			driver.findElement(By.xpath(OR.getProperty("btn_Search"))).click();
			waitFor();
			
//          Check that the table exists
			Log.info("Wait for table displayed...");
//			waitForElementDisplayed("//table[contains(@summary,'Metrage and Date of Previous Reading Sets')]", "1");
			tableExists(tableName, "1");
			Log.info("Check columns exist...");
			checkTableColumnExists("KM,Offset (M),Previous Reading Date", tableName);
		}catch (AssertionError ae) {
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
	
	static void enterRailWear() {
		try {
			String topValue = "2";
			Log.info("Start enterRailWear....");
			driver.findElement(By.xpath("//button[@type='button' and contains (., 'New')]")).click();
			waitFor();

			isRequired("txtbx_Km", "True");
			isRequired("txtbx_Offset(M)", "True");
			isRequired("txtbx_MeterInspector", "True");
			isRequired("txtbx_RailWearNewReadingDate", "True");
					
			driver.findElement(By.xpath(OR.getProperty("txtbx_Km"))).sendKeys("0");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Offset(M)"))).sendKeys("400");
			driver.findElement(By.xpath(OR.getProperty("txtbx_MeterInspector"))).sendKeys("maxadmin");
			driver.findElement(By.xpath(OR.getProperty("txtbx_RailWearReadingComment"))).sendKeys("Test Rail Wear");
			driver.findElement(By.xpath(OR.getProperty("txtbx_TopLeft"))).sendKeys(topValue);
			driver.findElement(By.xpath("//button[@type='button' and contains (., 'Save')]")).click();
			waitFor();
			WebDriverWait wait = new WebDriverWait(driver, 3);
     		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(OR.getProperty("btn_Cancel")))));
//			Log.info("Value of Top Right" + driver.findElement(By.xpath("//table/tbody/tr[text()='Previous']//following-sibling::table/tbody/tr/tr/td/input")));
			assertValue("txtbx_TopLeft", "");
			driver.findElement(By.xpath(OR.getProperty("btn_Cancel"))).click();
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
	
	static void newRailWear() {
		try {
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
	
	static void saveRailWear() {
		try {
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
