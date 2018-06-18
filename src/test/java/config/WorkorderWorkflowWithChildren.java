package config;

import static executionEngine.Base.OR;
import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
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

public class WorkorderWorkflowWithChildren extends TestAutomation {
    static String testCase = "";	
    static String testName = "WorkorderWorkflowWithChildren";
	
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
    public void logout() {
    	logout(testName, testCase);

    }
	
	
	@DataProvider
 	public Object[][] data() {
 		return new Object[][] { {"mxfldeng", "CM", "No"}, 
 								{"mxfldeng", "CM", "Yes"},
 								{"mxfldeng", "CAP", "No"}, 
 								{"mxfldeng", "CAP", "Yes"}
 								};
 	}
	
	@Test(groups = {"workflow"}, dataProvider = "data")
	public void workorderWorkflowWithChildren(String user, String worktype, String routeChild) {
		testCase = "workorderWorkflowWithChildren - "+worktype +"; Route child WO - "+routeChild;
//		extentTest.log(LogStatus.INFO, testCase);
		Log.startTestCase(testCase);

		try {
			  action.login(user);  
			  action.goToWOPage();
			  
//			  Create Parent WO
			  action.createWOwithPlans("Parent workorderWorkflowWithChildren", worktype);
			  action.click("tab_Main"); 

			  final String WONUM = driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).getAttribute("value");
			  String WOCost = driver.findElement(By.xpath(OR.getProperty("txtbx_Total_Hier_Est_Cost"))).getAttribute("value");
			  String area = driver.findElement(By.xpath(OR.getProperty("txtbx_Area"))).getAttribute("value");
			  String discipline = driver.findElement(By.xpath(OR.getProperty("txtbx_Discipline"))).getAttribute("value");
			  String region = driver.findElement(By.xpath(OR.getProperty("txtbx_Region"))).getAttribute("value");
			  Log.info("Created Parent WO >>>>>> "+WONUM);
 			  
//			  Create child WO
			  action.createWOwithPlans("Child 1 workorderWorkflowWithChildren", worktype);
			  action.click("tab_Main"); 

			  final String WONUMchild = driver.findElement(By.xpath(OR.getProperty("txtbx_WONUM"))).getAttribute("value");
			  String WOCostchild = driver.findElement(By.xpath(OR.getProperty("txtbx_Total_Hier_Est_Cost"))).getAttribute("value");
			  Log.info("Created Child WO >>>>>>"+WONUMchild);
			  
			  Log.info("Child WONUM =" +WONUMchild);
			  Log.info("Child WO Cost =" +WOCostchild);
			  Log.info("Parent WONUM =" +WONUM);
			  Log.info("Parent WO Cost =" +WOCost);
		  
//			  Assign child WO to a Parent
			  action.input("txtbx_ParentWO", WONUM);
			  action.save();
		  
			  WOCost = action.getAttributeValue("txtbx_Total_Hier_Est_Cost");
			  Log.info("Total WO Cost =" +WOCost);
			  
//			  Route child WO
			  if (routeChild.equals("Yes")) {
				  if (worktype.equals("CAP") || worktype.equals("AMREN")) {
					  driver.close();
					  action.changeWOStatusToPlan(WONUMchild, worktype);
					  action.assertValue("txtbx_Status", "PLAN");
				  }
				  
				  action.route(); 
				  action.clickOK();
				  action.assertValue("txtbx_Status", "PLAN");
				  Log.info("Routed Child WO");
			  }
			  
			  Log.info("Change status of Parent WO for wf");
			  action.quickSearch(WONUM);
			  
			  //Create workflow log
			  File file = new File(Constants.extentReportPath+"//"+ "Workflow Log for Parent WO " + WONUM + ".txt");
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
				  driver.close();
				  action.changeWOStatusToPlan(WONUM, worktype);
				  action.assertValue("txtbx_Status", "PLAN");
				 
			  }
			  driver.close();
			  Log.info("Open Parent WO and route to WF");
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
		    } catch(AssertionError ae){
				Log.error("Assertion failed workorderWorkflowWithChildren--- " + ae.getMessage());
				extentTest.log(LogStatus.ERROR, ae.getMessage());
				Base.bResult = false;
	 			Assert.fail();
		    } catch (NoSuchElementException e) {
		    	Log.error("Element not found workorderWorkflowWithChildren--- " + e.getMessage());
		    	extentTest.log(LogStatus.ERROR, e.getMessage());
		    	Base.bResult = false;
	 			Assert.fail();
		    } catch (Exception e) {
		    	Log.error("Exception workorderWorkflowWithChildren--- " + e.getMessage());
		    	Base.bResult = false;
	 			Assert.fail();
		    }	
	}

}
