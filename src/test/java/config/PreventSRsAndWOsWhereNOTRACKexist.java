package config;

import static executionEngine.DriverScript.OR;
import static executionEngine.DriverScript.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import executionEngine.DriverScript;
import utility.Log;

public class PreventSRsAndWOsWhereNOTRACKexist extends ActionKeywords {
	
	static String errorMsg = "This section of track does not physically exist, please enter valid Start and End Reference Points in the Linear Segment Details section";

	public static void preventSRsAndWOsWhereNOTRACKexist() {
		preventWO("1000067");
		preventSR("1000067");
	}
	
	static void preventWO(String asset) {
//		asset 1000067 notrack = 79-80
		try {
			Log.info("Start preventWO....");
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_WO");
			waitForElementDisplayed("btn_New", "1");
			createWO("Prevent WOs Where NOTRACK exist","CM");
//			change asset
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys(asset);
			driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
//			verify alert
			verifyAlert("msg_Popup", errorMsg); 
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			waitFor();
			
			Log.info("populate reference points - both Start and End NOTRACK");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPoint"))).sendKeys("79");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPoint"))).sendKeys("79");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).sendKeys("50");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).sendKeys("60");
			driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
//			verify alert
			verifyAlert("msg_Popup", errorMsg); 
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			waitFor();
			
//			remove and re-add asset - workaround for bug: cannot remove previous start reference value
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys(asset);
			driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).click();
	
			Log.info("populate reference points - Start value is set to NULL and the End is in the Notrack");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPoint"))).sendKeys("79");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).sendKeys("60");
			driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
			verifyAlert("msg_Popup", errorMsg); 
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			waitFor();
			
//			remove and re-add asset - workaround for bug: cannot remove previous start reference value
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys(asset);
			driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).click();
			waitFor();
			Log.info("populate reference points - Start value is inside the NOTRACK section and the End is set to NULL");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPoint"))).sendKeys("79");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).sendKeys("50");
			driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
			verifyAlert("msg_Popup", errorMsg); 
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			waitFor();
			
//			remove and re-add asset - workaround for bug: cannot remove previous start reference value
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys(asset);
			driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).click();
			waitFor();
			Log.info("populate reference points - Start is not in the NOTRACK section and the End is inside the NOTRACK section");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPoint"))).sendKeys("79");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPoint"))).sendKeys("80");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).sendKeys("176");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).sendKeys("0");
			driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
			verifyAlert("msg_Popup", errorMsg); 
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			waitFor();
			
//			TODO: can't find an asset with these criteria
//			remove and re-add asset - workaround for bug: cannot remove previous start reference value
//			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
//			driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).click();
//			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys(asset);
//			driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).click();
//			waitFor();
//			Log.info("populate reference points - Start is outside the Start of the NOTRACK section and the End is outside the End of the NOTRACK section.");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPoint"))).sendKeys("79");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPoint"))).sendKeys("80");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).sendKeys("194");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).sendKeys("0");
//			driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
//			verifyAlert("msg_Popup", errorMsg); 
//			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
//			waitFor();
			
//			remove and re-add asset - workaround for bug: cannot remove previous start reference value
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys(asset);
			driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).click();
			waitFor();
			Log.info("populate reference points - Start is not inside the Start of the NOTRACK section and the End is not inside the End of the NOTRACK section.");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPoint"))).sendKeys("79");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPoint"))).sendKeys("79");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).sendKeys("200");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).sendKeys("300");
			save("","");
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

	static void preventSR(String asset) {
		try {
			Log.info("Start prevent SR....");
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_SR");
			waitForElementDisplayed("btn_New", "1");
			Log.info("Creating SR....");
			createSR("Prevent SRs Where NOTRACK exist", "KRNETWORK");
//			change asset
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys(asset);
			driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
//			verify alert
			verifyAlert("msg_Popup", errorMsg); 
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			waitFor();
			
			Log.info("populate reference points - both Start and End NOTRACK");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPoint"))).sendKeys("79");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPoint"))).sendKeys("79");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).sendKeys("50");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).sendKeys("60");
			driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
//			verify alert
			verifyAlert("msg_Popup", errorMsg); 
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			waitFor();
			
//			remove and re-add asset - workaround for bug: cannot remove previous start reference value
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys(asset);
			driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))).click();
			waitFor();
			Log.info("populate reference points - Start value is set to NULL and the End is in the Notrack");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPoint"))).sendKeys("79");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).sendKeys("60");
			driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
			verifyAlert("msg_Popup", errorMsg); 
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			waitFor();
			
//			remove and re-add asset - workaround for bug: cannot remove previous start reference value
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys(asset);
			driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))).click();
			waitFor();
			Log.info("populate reference points - Start value is inside the NOTRACK section and the End is set to NULL");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPoint"))).sendKeys("79");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).sendKeys("50");
			driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
			verifyAlert("msg_Popup", errorMsg); 
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			waitFor();
			
//			remove and re-add asset - workaround for bug: cannot remove previous start reference value
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys(asset);
			driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))).click();
			waitFor();
			Log.info("populate reference points - Start is not in the NOTRACK section and the End is inside the NOTRACK section");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPoint"))).sendKeys("79");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPoint"))).sendKeys("80");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).sendKeys("176");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).sendKeys("0");
			driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
			verifyAlert("msg_Popup", errorMsg); 
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			waitFor();
			
//			TODO: can't find an asset with these criteria
//			remove and re-add asset - workaround for bug: cannot remove previous start reference value
//			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
//			driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))).click();
//			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys(asset);
//			driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))).click();
//			waitFor();
//			Log.info("populate reference points - Start is outside the Start of the NOTRACK section and the End is outside the End of the NOTRACK section.");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPoint"))).sendKeys("78");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPoint"))).sendKeys("79");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).sendKeys("500");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).sendKeys("176");
//			driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
//			verifyAlert("msg_Popup", errorMsg); 
//			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
//			waitFor();
			
//			remove and re-add asset - workaround for bug: cannot remove previous start reference value
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_AssetNum"))).sendKeys(asset);
			driver.findElement(By.xpath(OR.getProperty("txtbx_SRNUM"))).click();
			waitFor();
			Log.info("populate reference points - Start is not inside the Start of the NOTRACK section and the End is not inside the End of the NOTRACK section.");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPoint"))).sendKeys("79");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPoint"))).sendKeys("79");
			driver.findElement(By.xpath(OR.getProperty("txtbx_StartRefPointOffset"))).sendKeys("200");
			driver.findElement(By.xpath(OR.getProperty("txtbx_EndRefPointOffset"))).sendKeys("300");
			save("","");
			Log.info("End prevent SR....");
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
