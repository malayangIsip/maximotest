package config;

import static executionEngine.Base.OR;
import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;
import static executionEngine.Base.action;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import executionEngine.Base;
import executionEngine.TestAutomation;
import utility.Constants;
import utility.Log;

public class WorkorderWorkflow extends TestAutomation {
    static String testCase = "";	
    static String testName = "WorkorderWorkflow";
	
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
    
    @DataProvider
 	public Object[][] workType() {
 		return new Object[][] { { "mxfldeng", "CM"}, {"mxfldeng", "CAP"} };
 	}
    
    @Test(groups = {"workflow"}, dataProvider = "workType")
	public void workorderWorkflow(String user, String worktype) {
    	testCase = "workorderWorkflow - "+worktype;
//    	extentTest.log(LogStatus.INFO, testCase);
		Log.startTestCase(testCase);
		try{  

			  action.login(user);  
			  action.goToWOPage();
			
			  action.createWOwithPlans("workorderWorkflow", worktype);
			  action.click("tab_Main"); 
			  
			  final String WONUM = action.getAttributeValue("txtbx_WONUM");
			  String WOCost = action.getAttributeValue("txtbx_Total_Hier_Est_Cost");
			  String area = action.getAttributeValue("txtbx_Area");
			  String discipline = action.getAttributeValue("txtbx_Discipline");
			  String region = action.getAttributeValue("txtbx_Region");
			  int wfLevel = 1;
			  
			  Log.info("WONUM =" +WONUM);
			  Log.info("WO Cost =" +WOCost);
		  
//			  change status to PLAN if Renewal WO
			  if (worktype.equals("CAP") || worktype.equals("AMREN")) {
				  driver.close();
				  action.changeWOStatusToPlan(WONUM, worktype);
				  action.assertValue("txtbx_Status", "PLAN");
				 
			  }
			  
			  driver.close();
			  Log.info("Open WO as mxfldeng user");
			  action.openBrowser("Chrome");
			  action.login(user);  
			  action.goToWOPage();
			  action.quickSearch(WONUM);
			  
			  action.route(); 
			  action.clickOK();
			  Assert.assertEquals(action.inWorkflow(), true);
	 		  action.clickOK();
	 		  action.assertValue("txtbx_Status", "PLAN");
			  action.approveWOWF(WONUM);
				   			  
			  //Create workflow log
			  File file = new File(Constants.extentReportPath+"//"+ "Workflow Log for WO " + WONUM + ".txt");
			  FileWriter fw = new FileWriter(file, true);
			  String date = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
			  String lineSeparator = System.getProperty("line.separator");
			  
			  fw.append("Run Date: " +date + lineSeparator);
			  fw.append("WONUM = " + WONUM+ lineSeparator);
			  fw.append("WO Area = "+area+ lineSeparator);
			  fw.append("WO Discipline = "+discipline+ lineSeparator);
			  fw.append("WO Region = "+region+ lineSeparator);
			  fw.append("WO Total Cost = "+WOCost+ lineSeparator);
			  
			  fw.append("WO "+WONUM+" status = "+driver.findElement(By.xpath(OR.getProperty("txtbx_Status"))).getAttribute("value"));
			  fw.append(lineSeparator);
			  fw.append("End Workflow.");
			  fw.append(lineSeparator + lineSeparator);

			  fw.flush();
			  fw.close();
		}catch(AssertionError ae){
			Log.error("Assertion failed workorderWorkflow--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found workorderWorkflow--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception workorderWorkflow--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}

}
