package config;

import static executionEngine.Base.action;
import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;

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

public class HEATAlarmsActivation extends TestAutomation {
    static String testCase = "";	
    static String testName = "HEATAlarmsActivation";
    
    static String assetnum = "2030569";
    static String template = null;
    static String srnum = null;
	
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
    public void setUp() {
    	action.openBrowser("Chrome");
    	
    	Base.bResult = true;
    }
    
    @Override  
    @AfterMethod
    public void logout() throws Exception {
    	logout(testName, testCase);

    }
    
    @Test
    public void createHeatAlarmTemplate() {
    	testCase = "createHeatAlarmTemplate";
    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("maxadmin");
			action.goToTicketTemplates();
	
			action.clickNew();
			
			action.input("txtbx_Description", "HEAT Alarms Activation");
			action.input("txtbx_Class", "SR");
			action.assertValue("txtbx_Status", "DRAFT");
			action.input("txtbx_AssetNum", assetnum);
			action.input("txtbx_Priority", "5");
			action.input("txtbx_Gang", "AREAMGEW");
			action.input("txtbx_ActivityType", "HEAT Alarm Activation Callout");
			action.input("txtbx_Classification", "HEAT");
			action.input("txtbx_Discipline", "SIGNALS");
			action.click("txtbx_ClassDescription");
			action.assertValue("txtbx_ClassDescription", "Heat Site Alarm Activation");
			action.changeStatus("active");
			action.assertValue("txtbx_Status", "ACTIVE");
			
			template = action.getAttributeValue("txtbx_Template");
			Log.info("Template >>>>"+template);
		}catch(AssertionError ae){
			Log.error("Assertion failed createHeatAlarmTemplate--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found createHeatAlarmTemplate--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception createHeatAlarmTemplate--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test(dependsOnMethods={"createHeatAlarmTemplate"})
    public void verifyNon155Users() {
    	testCase = "verifyNon155Users";
    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try {  

			action.login("mxfldeng");
			action.goToSRPage();
			action.clickNew();
			action.click("btn_ReporterType");
			action.checkValueExists("HEAT", false);
			
		} catch(AssertionError ae){
			Log.error("Assertion failed verifyNon155Users--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found verifyNon155Users--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception verifyNon155Users--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test(dependsOnMethods={"createHeatAlarmTemplate"})
    public void createHeatSR() {
    	testCase = "createHeatSR";
    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  

			action.login("mx155");
			action.goToSRPage();
			action.clickNew();
		
			action.input("txtbx_ReporterType", "HEAT");
			action.click("txtbx_Source");
			action.elementExists("HEAT Alarm Activation", true);
			action.isRequired("txtbx_AlarmSite", true);
			action.input("txtbx_AlarmSite", template);
			action.isRequired("txtbx_HeatAlarmActivation", true);
			action.click("btn_HEATActivation_date");
			action.click("btn_OK_Calendar");
			action.click("txtbx_Classification");

			action.assertValue("txtbx_Classification", "HEAT");
			action.assertValue("txtbx_ClassDescription", "Heat Site Alarm Activation");

			action.input("txtbx_ActivityType", "Heat Alarm Activation Callout");
			action.assertValue("txtbx_AssetNum", assetnum);
			action.assertValue("txtbx_Status", "NEW");
			action.assertValue("txtbx_Discipline", "SIGNALS");
			action.input("txtbx_Priority", "4");
			action.save();

			srnum = action.getAttributeValue("txtbx_SRNUM");
			
			action.route();
			action.verifyAlert("Heat Alarm Activation SRs cannot be entered into Workflow");

		}catch(AssertionError ae){
			Log.error("Assertion failed createHeatSR--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found createHeatSR--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception createHeatSR--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test(dependsOnMethods={"createHeatSR"})
    public void resolveHeatSR() {
    	testCase = "resolveHeatSR";
    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("maxadmin");
			action.goToSRPage();
			action.quickSearch(srnum);

			action.changeStatus("resolved");
			action.assertValue("txtbx_Status", "RESOLVED");
		} catch(AssertionError ae){
			Log.error("Assertion failed resolveHeatSR--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found resolveHeatSR--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception resolveHeatSR--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
}
