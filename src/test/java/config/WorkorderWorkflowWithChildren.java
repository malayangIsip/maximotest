package config;

import static executionEngine.DriverScript.OR;
import static executionEngine.DriverScript.driver;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import executionEngine.DriverScript;
import utility.Log;

public class WorkorderWorkflowWithChildren extends ActionKeywords {
	
	public static void workorderWorkflowWithChildren(String object, String data) {
		try {
//			  Create Parent WO
			  createWOwithPlans("Parent workorderWorkflowWithChildren", data);
			  driver.findElement(By.xpath(OR.getProperty("tab_Main"))).click(); 
			  String WONUM = driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).getAttribute("value");
			  String WOCost = driver.findElement(By.xpath(OR.getProperty("txtbx_Total_Hier_Est_Cost"))).getAttribute("value");
			  String area = driver.findElement(By.xpath(OR.getProperty("txtbx_Area"))).getAttribute("value");
			  String discipline = driver.findElement(By.xpath(OR.getProperty("txtbx_Discipline"))).getAttribute("value");
			  String region = driver.findElement(By.xpath(OR.getProperty("txtbx_Region"))).getAttribute("value");
			  Log.info("Created Parent WO");
			  
			  
//			  Create child WO
			  createWOwithPlans("Child 1 workorderWorkflowWithChildren", data);
			  driver.findElement(By.xpath(OR.getProperty("tab_Main"))).click(); 
			  String WONUMchild = driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).getAttribute("value");
			  String WOCostchild = driver.findElement(By.xpath(OR.getProperty("txtbx_Total_Hier_Est_Cost"))).getAttribute("value");
			  Log.info("Created Child WO");
			  
			  Log.info("Child WONUM =" +WONUMchild);
			  Log.info("Child WO Cost =" +WOCostchild);
			  Log.info("Parent WONUM =" +WONUM);
			  Log.info("Parent WO Cost =" +WOCost);
			  
//			  Assign child WO to a Parent
			  driver.findElement(By.xpath(OR.getProperty("txtbx_ParentWO"))).sendKeys(WONUM);
			  save("1","1");
			  
			  WOCost = driver.findElement(By.xpath(OR.getProperty("txtbx_Total_Hier_Est_Cost"))).getAttribute("value");
			  Log.info("Total WO Cost =" +WOCost);
			  
			  driver.close();
			  driver.switchTo().activeElement().click();
			  
//			  Route child WO
			  if (object.equals("Yes")) {
//				  change status to PLAN if Renewal WO
				  if (data.equals("CAP") || data.equals("AMREN")) {
					  changeWOStatusToPlan(WONUMchild,data);
					  waitFor();
					  assertValue2("txtbx_Status", "PLAN");
				  }
				  
				  driver.findElement(By.xpath(OR.getProperty("btn_Route"))).click();
				  driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
				  waitFor();
				  assertValue2("txtbx_Status", "PLAN");
				  Log.info("Routed Child WO");
				  driver.close();
				  driver.switchTo().activeElement().click();
			  }
			  
//			  Open Parent WO and route to WF
//			  driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(WONUM);
//			  enter("txtbx_QuickSearch","1");
//			  waitFor();
			  
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
			  if (data.equals("CAP") || data.equals("AMREN")) {
				  changeWOStatusToPlan(WONUM,data);
				  waitFor();
				  assertValue2("txtbx_Status", "PLAN");
			  }
			  
			  DriverScript.storedValue = driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).getAttribute("value");
			  driver.findElement(By.xpath(OR.getProperty("btn_Route"))).click();
			  driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			  waitFor();
			  assertValue2("txtbx_Status", "PLAN");
			  
			  boolean inWorkflow = true;
			  hover("hvr_Workflow", "lnk_WFAssignment");
			  waitForElementDisplayed("tbl_WFAssignment");
			  inWorkflow = driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0");
			  DriverScript.WFApprover = driver.findElement(By.xpath("//table[contains(@summary,'Workflow Assignments')]/tbody/tr[4]/td[2]")).getText();
			  
			  Log.info("IsNull table approver =" +driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0"));
			  driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			  
			  driver.close();
			  driver.switchTo().activeElement().click();
			  
			  int wfLevel = 1;
			  
			  while (!inWorkflow) {
			      fw.append("WF Approval Level " +wfLevel+ " Approver = "+DriverScript.WFApprover);
				  fw.append(lineSeparator);
				  openBrowser("1","Mozilla");
				  loginApprover("1","1");
				  Log.info("WF Approval Level " +wfLevel+ " Approver = "+DriverScript.WFApprover);
				  openWFAssignment("1","1");
				  driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
				  waitFor();
				  
				  hover("hvr_Workflow", "lnk_WFAssignment");
				  waitForElementDisplayed("tbl_WFAssignment");
				  inWorkflow = driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0");
				  if (!inWorkflow) {
					  DriverScript.WFApprover = driver.findElement(By.xpath("//table[contains(@summary,'Workflow Assignments')]/tbody/tr[4]/td[2]")).getText();
					  Log.info("IsNull table approver =" +driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0"));
				  }
				  driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
				  wfLevel++;
				  Log.info("inWorkflow="+inWorkflow);
			  }
			  assertValue2("txtbx_Status", "APPR,WMATL");
			  fw.append("Parent WO "+WONUM+" status = "+driver.findElement(By.xpath(OR.getProperty("txtbx_Status"))).getAttribute("value"));
			  fw.append(lineSeparator);
			  
//			  Open child WO and check status
			  driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(WONUMchild);
			  enter("txtbx_QuickSearch","1");
		  
			  fw.append("Child WO "+WONUMchild+" status = "+driver.findElement(By.xpath(OR.getProperty("txtbx_Status"))).getAttribute("value"));
			  fw.append(lineSeparator);
			  fw.append("End Workflow.");
			  fw.append(lineSeparator + lineSeparator);
	
			  fw.flush();
			  fw.close();
		  } catch (Exception e){
				 Log.error("createWOwithChildren --- " +e.getMessage());
				 DriverScript.bResult = false;
		  }
	}

}
