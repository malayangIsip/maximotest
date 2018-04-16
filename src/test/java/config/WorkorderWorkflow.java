package config;

import static executionEngine.DriverScript.OR;
import static executionEngine.DriverScript.driver;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

import executionEngine.DriverScript;
import utility.Log;

public class WorkorderWorkflow extends ActionKeywords {

	public static void workorderWorkflow(String object, String data) {
		try{  
			  createWOwithPlans(object, data);
			  driver.findElement(By.xpath(OR.getProperty("tab_Main"))).click(); 
			  String WONUM = driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).getAttribute("value");
			  String WOCost = driver.findElement(By.xpath(OR.getProperty("txtbx_Total_Hier_Est_Cost"))).getAttribute("value");
			  String area = driver.findElement(By.xpath(OR.getProperty("txtbx_Area"))).getAttribute("value");
			  String discipline = driver.findElement(By.xpath(OR.getProperty("txtbx_Discipline"))).getAttribute("value");
			  String region = driver.findElement(By.xpath(OR.getProperty("txtbx_Region"))).getAttribute("value");
			  int wfLevel = 1;
			  
			  Log.info("WONUM =" +WONUM);
			  Log.info("WO Cost =" +WOCost);
			  
//			  change status to PLAN if Renewal WO
			  if (data.equals("CAP") || data.equals("AMREN")) {
				  driver.close();
				  changeWOStatusToPlan(WONUM,data);
				  waitFor();
				  assertValue2("txtbx_Status", "PLAN");
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
			  click("btn_Route", null);
			  click("btn_OK", null);
			  waitFor();
			  assertValue2("txtbx_Status", "PLAN");
			  
			  boolean inWorkflow = true;
			  hover("hvr_Workflow", "lnk_WFAssignment");
			  waitForElementDisplayed("tbl_WFAssignment");
			  inWorkflow = driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0");
			  DriverScript.WFApprover = driver.findElement(By.xpath("//table[contains(@summary,'Workflow Assignments')]/tbody/tr[4]/td[2]")).getText();
			  
			  Log.info("IsNull table approver =" +driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0"));
			  click("btn_OK", null);
			  
			  while (!inWorkflow) {
			      fw.append("WF Approval Level " +wfLevel+ " Approver = "+DriverScript.WFApprover);
				  fw.append(lineSeparator);
				  driver.close();
				  openBrowser("1","Mozilla");
				  loginApprover("1","1");
				  waitFor();
				  Log.info("WF Approval Level " +wfLevel+ " Approver = "+DriverScript.WFApprover);
				  openWFAssignment("1","1");
				  waitFor();
				  click("btn_OK", null);
				  Log.info("clicked image....");
				  waitFor();
				  
				  hover("hvr_Workflow", "lnk_WFAssignment");
				  waitForElementDisplayed("tbl_WFAssignment");
				  inWorkflow = driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0");
				  if (!inWorkflow) {
					  DriverScript.WFApprover = driver.findElement(By.xpath("//table[contains(@summary,'Workflow Assignments')]/tbody/tr[4]/td[2]")).getText();
					  Log.info("IsNull table approver =" +driver.findElement(By.xpath(OR.getProperty("tbl_WFAssignment"))).getAttribute("displayrows").equals("0"));
					  
				  }
				  click("btn_OK", null);
				  wfLevel++;
			  }
			  assertValue2("txtbx_Status", "APPR,WMATL");

			  fw.append("WO "+WONUM+" status = "+driver.findElement(By.xpath(OR.getProperty("txtbx_Status"))).getAttribute("value"));
			  fw.append(lineSeparator);
			  fw.append("End Workflow.");
			  fw.append(lineSeparator + lineSeparator);

			  fw.flush();
			  fw.close();
			  driver.close();
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
