package config;

import static executionEngine.Base.OR;
import static executionEngine.Base.driver;
import static executionEngine.Base.extentTest;
import static executionEngine.Base.action;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class WorkorderWorkflowWithChildren {
	static boolean runStatus = true;
    static String methodName = "";	
	
	@BeforeClass(alwaysRun = true)
    public void init() throws Exception {
    	extentTest.log(LogStatus.INFO, "Start WorkorderWorkflowWithChildren Tests");
    }

	@AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
		extentTest.log(LogStatus.INFO, "END WorkorderWorkflowWithChildren Tests");
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
 	public Object[][] data() {
 		return new Object[][] { {"mxfldeng", "CM", "No"}, {"mxfldeng", "CAP", "No"},
 								{"mxfldeng", "CM", "Yes"}, {"mxfldeng", "CAP", "Yes"}};
//		return new Object[][] { {"mxfldeng", "CAP", "Yes"} };
 	}
	
	@Test(dataProvider = "data")
	public void workorderWorkflowWithChildren(String user, String worktype, String routeChild) {
		methodName = "workorderWorkflowWithChildren";
		try {
			  action.login(null, user);  
			  action.goToWOPage();
			
//			  Create Parent WO
			  action.createWOwithPlans("Parent workorderWorkflowWithChildren", worktype);
			  action.click("tab_Main"); 
			  
			  final String WONUM = driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).getAttribute("value");
			  String WOCost = driver.findElement(By.xpath(OR.getProperty("txtbx_Total_Hier_Est_Cost"))).getAttribute("value");
			  String area = driver.findElement(By.xpath(OR.getProperty("txtbx_Area"))).getAttribute("value");
			  String discipline = driver.findElement(By.xpath(OR.getProperty("txtbx_Discipline"))).getAttribute("value");
			  String region = driver.findElement(By.xpath(OR.getProperty("txtbx_Region"))).getAttribute("value");
			  Log.info("Created Parent WO");
			  
			  
//			  Create child WO
			  action.createWOwithPlans("Child 1 workorderWorkflowWithChildren", worktype);
			  action.click("tab_Main"); 
			  
			  final String WONUMchild = driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).getAttribute("value");
			  String WOCostchild = driver.findElement(By.xpath(OR.getProperty("txtbx_Total_Hier_Est_Cost"))).getAttribute("value");
			  Log.info("Created Child WO");
			  
			  Log.info("Child WONUM =" +WONUMchild);
			  Log.info("Child WO Cost =" +WOCostchild);
			  Log.info("Parent WONUM =" +WONUM);
			  Log.info("Parent WO Cost =" +WOCost);
			  
//			  Assign child WO to a Parent
			  action.input("txtbx_ParentWO", WONUM);
			  action.save();
			  
			  WOCost = action.getAttributeValue("txtbx_Total_Hier_Est_Cost");
			  Log.info("Total WO Cost =" +WOCost);
			  
			  driver.close();
//			  driver.switchTo().activeElement().click();
			  
//			  Route child WO
			  if (routeChild.equals("Yes")) {
//				  change status to PLAN if Renewal WO
				  if (worktype.equals("CAP") || worktype.equals("AMREN")) {
					  action.changeWOStatusToPlan(WONUMchild, worktype);
					  action.assertValue2("txtbx_Status", "PLAN");
				  }
				  
				  action.route(); // driver.findElement(By.xpath(OR.getProperty("btn_Route"))).click();
				  action.clickOK();
				  action.assertValue2("txtbx_Status", "PLAN");
				  Log.info("Routed Child WO");
				  driver.close();
//				  driver.switchTo().activeElement().click();
			  }
			  
			  Log.info("Open Parent WO and route to WF");
//			  action.quickSearch(WONUM);
			  
//			  WOCost = driver.findElement(By.xpath(OR.getProperty("txtbx_Total_Hier_Est_Cost"))).getAttribute("value");
//			  Log.info("Total WO Cost =" +WOCost);
			  
			  //Create workflow log
			  File file = new File("Workflow Log for Parent WO " + WONUM + ".txt");
			  FileWriter fw = new FileWriter(file, true);
			  String date = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
			  String lineSeparator = System.getProperty("line.separator");
			  
			  fw.append("Run Date: " +date + lineSeparator);
			  fw.append("Parent WONUM = " + WONUM+ lineSeparator);
			  fw.append("WO Area = "+area+ lineSeparator);
			  fw.append("WO Discipline = "+discipline+ lineSeparator);
			  fw.append("WO Region = "+region+ lineSeparator);
			  fw.append("WO Total Cost = "+WOCost+ lineSeparator);
			  fw.append("Child WONUM = " + WONUMchild+ lineSeparator);
			  
//			  change status to PLAN if Renewal WO
			  if (worktype.equals("CAP") || worktype.equals("AMREN")) {
				  action.changeWOStatusToPlan(WONUM, worktype);
				  action.assertValue2("txtbx_Status", "PLAN");
			  }
			  
			  DriverScript.storedValue = action.getAttributeValue("txtbx_WONUM");
			  action.route(); // driver.findElement(By.xpath(OR.getProperty("btn_Route"))).click();
			  action.clickOK();
			  action.assertValue2("txtbx_Status", "PLAN");
			  
			  boolean inWorkflow = true;
			  action.hover("hvr_Workflow", "lnk_WFAssignment");
			  action.waitForElementDisplayed("tbl_WFAssignment");
			  inWorkflow = driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0");
			  DriverScript.WFApprover = driver.findElement(By.xpath("//table[contains(@summary,'Workflow Assignments')]/tbody/tr[4]/td[2]")).getText();
			  
			  Log.info("IsNull table approver =" +driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0"));
			  action.clickOK();
			  
//			  driver.close();
//			  driver.switchTo().activeElement().click();
			  
			  int wfLevel = 1;
			  
			  while (!inWorkflow) {
			      fw.append("WF Approval Level " +wfLevel+ " Approver = "+DriverScript.WFApprover);
				  fw.append(lineSeparator);
				  driver.close();
				  action.openBrowser(null, "Chrome");
				  action.loginApprover("dummy", "dummy");
				  Log.info("WF Approval Level " +wfLevel+ " Approver = "+DriverScript.WFApprover);
				  action.openWFAssignment("dummy", "dummy");
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
				  Log.info("inWorkflow="+inWorkflow);
			  }
			  action.assertValue2("txtbx_Status", "APPR,WMATL");
			  fw.append("Parent WO "+WONUM+" status = "+driver.findElement(By.xpath(OR.getProperty("txtbx_Status"))).getAttribute("value"));
			  fw.append(lineSeparator);
			  
//			  Open child WO and check status
			  action.quickSearch(WONUMchild); 
		  
			  fw.append("Child WO "+WONUMchild+" status = "+driver.findElement(By.xpath(OR.getProperty("txtbx_Status"))).getAttribute("value"));
			  fw.append(lineSeparator);
			  fw.append("End Workflow.");
			  fw.append(lineSeparator + lineSeparator);
	
			  fw.flush();
			  fw.close();
			  Log.info("END workorderWorkflowWithChildren....................");
		    } catch(AssertionError ae){
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
