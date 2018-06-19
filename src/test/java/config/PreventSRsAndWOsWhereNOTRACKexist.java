package config;

import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;
import static executionEngine.Base.action;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import executionEngine.Base;
import executionEngine.TestAutomation;
import utility.Log;

public class PreventSRsAndWOsWhereNOTRACKexist extends TestAutomation {
	static String user = "mxplan";
	static String asset = "1000067";
	static String testName = "PreventSRsAndWOsWhereNOTRACKexist";
	
    static String testCase = "";

	static String errorMsg = "This section of track does not physically exist, please enter valid Start and End Reference Points in the Linear Segment Details section";
	
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
    	action.login(user);
    	
    	Base.bResult = true;
    }
    
    @Override  
    @AfterMethod
    public void logout() throws Exception {
    	logout(testName, testCase);

    }
    
    @Test
	void preventWO() {
    	testCase = "preventWO";
//    	extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
			action.goToWOPage();
			
			action.createWO("Prevent WOs Where NOTRACK exist","CM");
			
			Log.info("Change asset....");
			action.clear("txtbx_AssetNum");
			action.input("txtbx_AssetNum", asset);
			action.click("btn_Save");
			
			Log.info("verify alert....");
			action.verifyAlert(errorMsg);  
			action.clickOK();
			
			Log.info("populate reference points - both Start and End NOTRACK");
			action.input("txtbx_StartRefPoint", "79");
			action.input("txtbx_EndRefPoint", "79");
			action.input("txtbx_StartRefPointOffset", "50");
			action.input("txtbx_EndRefPointOffset", "60");
			action.click("btn_Save");
			
			Log.info("verify alert....");
			action.verifyAlert(errorMsg); 
			action.clickOK();
			
			Log.info("Remove and re-add asset - workaround for bug: cannot remove previous start reference value...");
			action.clear("txtbx_AssetNum");
			action.click("txtbx_WONUM");
			action.input("txtbx_AssetNum", asset);
			action.click("txtbx_WONUM");
	
			Log.info("populate reference points - Start value is set to NULL and the End is in the Notrack");
			action.input("txtbx_EndRefPoint", "79");
			action.click("txtbx_EndRefPointOffset");
			action.input("txtbx_EndRefPointOffset", "60");
			action.click("btn_Save");
			action.verifyAlert(errorMsg); 
			action.clickOK();
			
			Log.info("Remove and re-add asset - workaround for bug: cannot remove previous start reference value...");
			action.clear("txtbx_AssetNum");
			action.click("txtbx_WONUM");
			action.input("txtbx_AssetNum", asset);
			action.click("txtbx_WONUM");

			Log.info("populate reference points - Start value is inside the NOTRACK section and the End is set to NULL");
			action.input("txtbx_StartRefPoint", "79");
			action.click("txtbx_StartRefPointOffset");
			action.input("txtbx_StartRefPointOffset", "50");
			action.click("btn_Save");
			action.verifyAlert(errorMsg); 
			action.clickOK();
			
			Log.info("Remove and re-add asset - workaround for bug: cannot remove previous start reference value...");
			action.clear("txtbx_AssetNum");
			action.click("txtbx_WONUM");
			action.input("txtbx_AssetNum", asset);
			action.click("txtbx_WONUM");

			Log.info("populate reference points - Start is not in the NOTRACK section and the End is inside the NOTRACK section");
			action.input("txtbx_StartRefPoint", "79");
			action.input("txtbx_EndRefPoint", "80");
			action.click("txtbx_StartRefPointOffset");
			action.input("txtbx_StartRefPointOffset", "176");
			action.click("txtbx_EndRefPointOffset");
			action.input("txtbx_EndRefPointOffset", "0");
			action.click("btn_Save");
			action.verifyAlert(errorMsg); 
			action.clickOK();
			
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
			
			Log.info("Remove and re-add asset - workaround for bug: cannot remove previous start reference value...");
			action.clear("txtbx_AssetNum");
			action.click("txtbx_WONUM");
			action.input("txtbx_AssetNum", asset);
			action.click("txtbx_WONUM");
			
			Log.info("populate reference points - Start is not inside the Start of the NOTRACK section and the End is not inside the End of the NOTRACK section.");
			action.input("txtbx_StartRefPoint", "79");
			action.input("txtbx_EndRefPoint", "79");
			action.input("txtbx_StartRefPointOffset", "200");
			action.input("txtbx_EndRefPointOffset", "300");
			action.save();
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
	void preventSR() {
		testCase = "preventSR";
//		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
		try {
			action.goToSRPage();
			
			Log.info("Creating SR....");
			action.createSR("Prevent SRs Where NOTRACK exist", "KRNETWORK");
			Log.info("Change asset...");
			action.input("txtbx_AssetNum", asset);
			action.click("btn_Save");
			
			Log.info("Verify alert...");
			action.verifyAlert(errorMsg); 
			action.clickOK();
			
			Log.info("populate reference points - both Start and End NOTRACK");
			action.input("txtbx_StartRefPoint", "79");
			action.input("txtbx_EndRefPoint", "79");
			action.input("txtbx_StartRefPointOffset", "50");
			action.input("txtbx_EndRefPointOffset", "60");
			action.click("btn_Save");
			
			Log.info("Verify alert...");
			action.verifyAlert(errorMsg); 
			action.clickOK();
			
			Log.info("Remove and re-add asset - workaround for bug: cannot remove previous start reference value...");
			action.clear("txtbx_AssetNum");
			action.click("txtbx_SRNUM");
			action.input("txtbx_AssetNum", asset);
			action.click("txtbx_SRNUM");
			
			Log.info("populate reference points - Start value is set to NULL and the End is in the Notrack");
			action.input("txtbx_EndRefPoint", "79");
			action.input("txtbx_EndRefPointOffset", "60");
			action.click("btn_Save");
			action.verifyAlert(errorMsg); 
			action.clickOK();
			
			Log.info("Remove and re-add asset - workaround for bug: cannot remove previous start reference value...");
			action.clear("txtbx_AssetNum");
			action.click("txtbx_SRNUM");
			action.input("txtbx_AssetNum", asset);
			action.click("txtbx_SRNUM");

			Log.info("populate reference points - Start value is inside the NOTRACK section and the End is set to NULL");
			action.input("txtbx_StartRefPoint", "79");
			action.input("txtbx_StartRefPointOffset", "50");
			action.click("btn_Save");
			action.verifyAlert(errorMsg); 
			action.clickOK();
			
			Log.info("Remove and re-add asset - workaround for bug: cannot remove previous start reference value...");
			action.clear("txtbx_AssetNum");
			action.click("txtbx_SRNUM");
			action.input("txtbx_AssetNum", asset);
			action.click("txtbx_SRNUM");
			
			Log.info("populate reference points - Start is not in the NOTRACK section and the End is inside the NOTRACK section");
			action.input("txtbx_StartRefPoint", "79");
			action.input("txtbx_EndRefPoint", "80");
			action.input("txtbx_StartRefPointOffset", "176");
			action.input("txtbx_EndRefPointOffset", "0");
			action.click("btn_Save");
			action.verifyAlert(errorMsg); 
			action.clickOK();
			
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
			
			Log.info("Remove and re-add asset - workaround for bug: cannot remove previous start reference value...");
			action.clear("txtbx_AssetNum");
			action.click("txtbx_SRNUM");
			action.input("txtbx_AssetNum", asset);
			action.click("txtbx_SRNUM");
			
			Log.info("populate reference points - Start is not inside the Start of the NOTRACK section and the End is not inside the End of the NOTRACK section.");
			action.input("txtbx_StartRefPoint", "79");
			action.input("txtbx_EndRefPoint", "79");
			action.input("txtbx_StartRefPointOffset", "200");
			action.input("txtbx_EndRefPointOffset", "300");
			action.save();

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

}
