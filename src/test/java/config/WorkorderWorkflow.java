package config;

import static executionEngine.Base.OR;
import static executionEngine.Base.driver;
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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import executionEngine.DriverScript;
import utility.Log;

public class WorkorderWorkflow {
	static boolean runStatus = true;
    static String methodName = "";	
	
	@BeforeClass(alwaysRun = true)
    public void init() throws Exception {
    	extentTest.log(LogStatus.INFO, "Start workorderWorkflow Tests");
    }

	@AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
		extentTest.log(LogStatus.INFO, "END workorderWorkflow Tests");
    }
    
        
    @BeforeMethod(alwaysRun = true)
    void setUp() {
    	action.openBrowser(null, "Chrome");
    }
    
    @AfterMethod(alwaysRun = true)
    void logout() {
    	if (runStatus == true) {
    		extentTest.log(LogStatus.PASS, methodName);
//    		action.logout(null, null);
    		driver.close();
    	} else {
    		extentTest.log(LogStatus.FAIL, methodName);
    		driver.close();
    	}
    }
	
    
    @DataProvider
 	public Object[][] workType() {
 		return new Object[][] { { "mxfldeng", "CM"}, {"mxfldeng", "CAP"} };
 	}
    
    @Test(dataProvider = "workType")
	public void workorderWorkflow(String user, String worktype) {
    	methodName = "workorderWorkflow";
		try{  
			Log.info("Start workorderWorkflow...................." + worktype);
			  action.login(null, user);  
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
//				  driver.close();
				  action.changeWOStatusToPlan(WONUM, worktype); 
				  action.assertValue2("txtbx_Status", "PLAN");
			  }
				   			  
			  //Create workflow log
			  File file = new File("Workflow Log for WO " + WONUM + ".txt");
			  FileWriter fw = new FileWriter(file, true);
			  String date = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
			  String lineSeparator = System.getProperty("line.separator");
			  
			  fw.append("Run Date: " +date + lineSeparator);
			  fw.append("WONUM = " + WONUM+ lineSeparator);
			  fw.append("WO Area = "+area+ lineSeparator);
			  fw.append("WO Discipline = "+discipline+ lineSeparator);
			  fw.append("WO Region = "+region+ lineSeparator);
			  fw.append("WO Total Cost = "+WOCost+ lineSeparator);
			  
			  DriverScript.storedValue = driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).getAttribute("value");
			  action.route();
			  action.clickOK();
			  action.assertValue2("txtbx_Status", "PLAN");
			  
			  boolean inWorkflow = true;
			  action.hover("hvr_Workflow", "lnk_WFAssignment");
			  action.waitForElementDisplayed("tbl_WFAssignment");
			  inWorkflow = driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0");
			  DriverScript.WFApprover = driver.findElement(By.xpath("//table[contains(@summary,'Workflow Assignments')]/tbody/tr[4]/td[2]")).getText();
			  
			  Log.info("IsNull table approver =" +driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0"));
			  action.clickOK();
			  
			  while (!inWorkflow) {
			      fw.append("WF Approval Level " +wfLevel+ " Approver = "+DriverScript.WFApprover);
				  fw.append(lineSeparator);
				  driver.close();
				  action.openBrowser(null,"Chrome");
				  action.loginApprover("dummy", "dummy");
//				  waitFor();
				  Log.info("WF Approval Level " +wfLevel+ " Approver = "+DriverScript.WFApprover);
				  action.openWFAssignment("dummy", "dummy");
//				  waitFor();
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

			  fw.append("WO "+WONUM+" status = "+driver.findElement(By.xpath(OR.getProperty("txtbx_Status"))).getAttribute("value"));
			  fw.append(lineSeparator);
			  fw.append("End Workflow.");
			  fw.append(lineSeparator + lineSeparator);

			  fw.flush();
			  fw.close();
//			  driver.close();
			  Log.info("END workorderWorkflow....................");
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			runStatus = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
	    	runStatus = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
	    	runStatus = false;
 			Assert.fail();
	    }	
	}

}
