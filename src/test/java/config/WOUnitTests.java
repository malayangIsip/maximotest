package config;

import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;
import static executionEngine.Base.action;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.relevantcodes.extentreports.LogStatus;

import executionEngine.Base;
import executionEngine.TestAutomation;
import utility.Log;

public class WOUnitTests extends TestAutomation {
    final LocalDate ngayon = action.getDate();
    static String testCase = "";
    static String testName = "WOUnitTests";
    
    static String WONUM = null;
    
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
 	public Object[][] user() {
 		return new Object[][] { {"maxadmin"}, {"mxplan"} };
 	}
	
    
    @Test(groups = {"unitTest"}, dataProvider = "user")
// 	[MAX-1080] Select Action > Edit Reservations: Date
 	public void _editReservations(String user) throws Exception {
 		testCase = "_editReservations() as "+ user;
// 		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
 		
 		try {
 			action.login(user);
 			action.goToWOPage();
 			
 			Log.info("Search for an approved WO with material resevation");
 			String sqlData = "((status = 'WMATL' and (woclass = 'WORKRESULT' or woclass = 'WORKORDER' or woclass = 'ACTIVITY') and historyflag = 0 and istask = 0)) and ((upper(kr_discipline) = 'TRACK'))";
 			action.whereClause(sqlData);
 			action.clickFirstValueFromList();

 			Log.info("check edit reservation link is active");
 			action.waitElementExists("lnk_EditReservations");
 			action.scrollDown("lnk_EditReservations");
 			action.isDisabled("lnk_EditReservations", false);
 			action.click("lnk_EditReservations");
 			action.checkTableNotEmpty("Items", true);
 			
 			Log.info("Check required date fields is editable");
 			action.elementExists("txtbx_RequiredDateViewReservation", true); 
 			action.isReadOnly("txtbx_RequiredDateViewReservation", false); 
 			action.clickOK();
 		} catch(AssertionError ae){
 			Log.error("JUNIT _editReservations --- ");
 			extentTest.log(LogStatus.ERROR, ae.getMessage());
 			Base.bResult = false;
 			Assert.fail();
 		} catch(Exception e){
 	 		Log.error("JUNIT editReservations ---- ");
 	 		extentTest.log(LogStatus.ERROR, e.getMessage());
 	 		Base.bResult = false;
 	 		Assert.fail();
 	    }
 	}
 	
 	
    @DataProvider
 	public Object[][] data() {
 		return new Object[][] { {"mxplan", "CAP"}, {"mxplan", "CM"} };
    }
    
    @Test(groups = {"unitTest"}, dataProvider = "data")
//     [MAX-1012] Inventory Item required Dates should synch with Scheduled Start Date 
 	public void _schedDateButton(String user, String worktype) throws Exception {
 		testCase = "_schedDateButton "+worktype;
// 		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
 		try {
 			action.login(user);
 			action.goToWOPage();
 		
 			action.createWOwithPlans(testCase, worktype);

 			action.storeValue("txtbx_WONUM", "WONUM");
			WONUM = action.getStoredValue("WONUM");
			
 			action.clickMainTab();
 			action.scrollDown("lnk_SetSchedDate");
 			action.isDisabled("lnk_SetSchedDate", false);
 		
 			Log.info("If Scheduled Start date is empty the button is greyed out");
 			action.clear("txtbx_ScheduledStart");
 			action.save();
 			action.scrollDown("lnk_SetSchedDate");
 			action.isDisabled("lnk_SetSchedDate", true);
 			
 			Log.info("If scheduled start date is in the past and the work order is not approved, the button is available but when pressed an error is displayed");
 			action.input("txtbx_ScheduledStart", ngayon.minusDays(2).toString());
 			action.save();
 			action.scrollDown("lnk_SetSchedDate");
 			action.isDisabled("lnk_SetSchedDate", false);
 			action.click("lnk_SetSchedDate");
 			action.verifyAlert("The required date cannot be in the past.");
 			action.clickOK();
 									
 			Log.info("if scheduled start date is in the future and the work order is not approved, the button is available and when pressed updates all material required dates to the scheduled start date, the screen refreshes on save. If there are no materials to update, no changes occur");
 			action.input("txtbx_ScheduledStart", ngayon.plus(2, ChronoUnit.WEEKS).toString());
 			action.scrollDown("lnk_SetSchedDate");
 			action.click("lnk_SetSchedDate");
 			action.save();
 			String WOSchedStartDate = action.getAttributeValue("txtbx_ScheduledStart");
 			
 			Log.info("Goto Plan Materials to get the value of materials required date.");
 			action.clickPlansTab();
 			action.clickMaterialsTab();
 			action.clickViewDetailsRow1();
 			String materialsRequiredDate = action.getAttributeValue("txtbx_RequiredDate");
 			Assert.assertEquals(materialsRequiredDate, WOSchedStartDate);
 			
 			Log.info("Change value of schedStartDate and check materialsRequiredDate updates");
 			action.clickMainTab();
 			action.input("txtbx_ScheduledStart", ngayon.plus(3, ChronoUnit.WEEKS).toString());
 			action.save();
 			action.scrollDown("lnk_SetSchedDate");
 			action.click("lnk_SetSchedDate");
 			action.save();
 			WOSchedStartDate = action.getAttributeValue("txtbx_ScheduledStart");
 			
 			Log.info("Goto Plan Materials to get the value of materials required date.");
 			action.clickPlansTab();
 			action.clickMaterialsTab();
 			action.clickViewDetailsRow1();
 			materialsRequiredDate = action.getAttributeValue("txtbx_RequiredDate");
 			Assert.assertEquals(materialsRequiredDate, WOSchedStartDate);
 		} catch(AssertionError ae){
 			Log.error("JUNIT _schedDateButton --- ");
 			extentTest.log(LogStatus.ERROR, ae.getMessage());
 			Base.bResult = false;
 			Assert.fail();
 		} catch(Exception e){
 	 		Log.error("Exception JUNIT schedDateButton ---- ");
 	 		extentTest.log(LogStatus.ERROR, e.getMessage());
 	 		Base.bResult = false;
 	 		Assert.fail();
 	    }
 	}

    @DataProvider
 	public Object[][] sql() {
 		return new Object[][] { {"((status = 'CREATE' and (woclass = 'WORKRESULT' or woclass = 'WORKORDER' or woclass = 'ACTIVITY') and historyflag = 0 and istask = 0 and worktype = 'CM')) and ((upper(description) like '%SCHEDDATEBUTTON CM%'))"}, 
 			                    {"((status = 'CREATE' and (woclass = 'WORKRESULT' or woclass = 'WORKORDER' or woclass = 'ACTIVITY') and historyflag = 0 and istask = 0 and worktype = 'CAP')) and ((upper(description) like '%SCHEDDATEBUTTON CAP%'))"} };
    }
    
    @Test(groups = {"unitTest"}, dataProvider = "sql", dependsOnMethods={"_schedDateButton"})
// 	[MAX-1012] Inventory Item required Dates should synch with Scheduled Start Date 
 	public void _schedDateWF(String sql) throws Exception {
 		String testCase = "_schedDateWF()";
 		String user="mxfldeng";
// 		extentTest.log(LogStatus.INFO, testCase);
 		Log.startTestCase(testCase);
 		try {
 			action.login(user);
			action.goToWOPage();

			action.whereClause(sql);
 			action.clickFirstValueFromList();
 			
			action.waitElementExists("txtbx_WONUM");
			String WONUM = action.getAttributeValue("txtbx_WONUM");
			String worktype = action.getAttributeValue("txtbx_Worktype");	
			testCase = "_schedDateWF() "+worktype;
			
			Log.info("change status to PLAN if Renewal WO");
			if (worktype.equals("CAP") || worktype.equals("AMREN")) {
				driver.close();
				action.changeWOStatusToPlan(WONUM, worktype); 
				action.assertValue("txtbx_Status", "PLAN");
			}

 			Log.info("If Scheduled Start date is empty, when work flow is initiated, an error is displayed and workflow stops");
 			action.clear("txtbx_ScheduledStart");
 			action.save();
 			action.route();
 			action.verifyAlert("The Scheduled Start Date cannot be empty");
 			action.clickClose();
 			Assert.assertEquals(action.inWorkflow(), false);
 			action.clickOK();
 		
 			Log.info("If Scheduled Start date is in the past, when work flow is initiated, an error is displayed and work flow stops");
 			action.input("txtbx_ScheduledStart", ngayon.minusDays(1).toString());
 			action.save();
 			action.route();
 			action.verifyAlert("The Scheduled Start Date cannot be empty");
 			action.clickClose();
 			Assert.assertEquals(action.inWorkflow(), false);
 			action.clickOK();
 			
 			Log.info("If any inventory required dates are in the past, when work flow is initiated, an error is displayed and work flow stops");
 			action.clear("txtbx_ScheduledStart");
 			action.input("txtbx_ScheduledStart", ngayon.toString()); //considered in the past because of seconds
 			action.scrollDown("lnk_SetSchedDate");
 			action.click("lnk_SetSchedDate");
 			action.save();
 			
 			Log.info("change sked date to future and leave matreqdate to past");
 			action.clear("txtbx_ScheduledStart");
 			action.input("txtbx_ScheduledStart", ngayon.plus(1, ChronoUnit.DAYS).toString());
 			action.save();
 			action.route();
 			
 			if (worktype.equals("CAP")) {
 				action.verifyAlert("The Scheduled Start Date cannot be empty");
 			} else {
 				action.verifyAlert("There is an Inventory Required Date in the past. Please rectify and resubmit to workflow");
 			}
 			
 			action.clickClose();
 			Assert.assertEquals(action.inWorkflow(), false);
 			action.clickOK();
 			
 			Log.info("If Scheduled Start date is in the future(at least 2 weeks for CAP/AMREN), when work flow is initiated work flow passes to the next check");
 			Log.info("If all inventory required dates are in the future, when work flow is initiated work flow passes to the next check");
 			action.clear("txtbx_ScheduledStart");
 			action.input("txtbx_ScheduledStart", ngayon.plus(1, ChronoUnit.WEEKS).toString());
 			action.scrollDown("lnk_SetSchedDate");
 			action.click("lnk_SetSchedDate");
 			action.save();
 			action.route();
 			
 			if (worktype.equals("CAP")) {
 				action.verifyAlert("The Scheduled Start Date cannot be empty");
 				action.clickClose();
 				action.clear("txtbx_ScheduledStart");
 				action.input("txtbx_ScheduledStart", ngayon.plus(3, ChronoUnit.WEEKS).toString());
 				action.scrollDown("lnk_SetSchedDate");
 				action.click("lnk_SetSchedDate");
 				action.save();
 				action.route();
 			} 
 			action.clickOK();
 			Assert.assertEquals(action.inWorkflow(), true);
 			action.clickOK();
 			action.assertValue("txtbx_Status", "PLAN");
 		} catch(AssertionError ae){
 			Log.error("JUNIT schedDateWF --- ");
 			extentTest.log(LogStatus.ERROR, ae.getMessage());
 			Base.bResult = false;
 			Assert.fail();
 		} catch(Exception e){
 	 		Log.error("Exception JUNIT schedDateWF: ");
 	 		extentTest.log(LogStatus.ERROR, e.getMessage());
 	 		Base.bResult = false;
 	 		Assert.fail();
 	    }
 	}
    
    @Test(groups = {"unitTest"})
//  [MAX-1012] If Work Order is approved the button is greyed out
	public void _inactiveSchedDateButton() throws Exception {
		testCase = "_inactiveSchedDateButton()";
		String user = "mxplan";
//		extentTest.log(LogStatus.INFO, testCase);
		Log.startTestCase(testCase);
		try {
			action.login(user);
			action.goToWOPage();
			
			String sql = "(status = 'APPR' and (woclass = 'WORKRESULT' or woclass = 'WORKORDER' or woclass = 'ACTIVITY') and upper(description) like '%TEST%' and historyflag = 0 and istask = 0 and worktype = 'CAP')";
			action.whereClause(sql);
 			action.clickFirstValueFromList();
 			
			action.waitElementExists("txtbx_WONUM");
		
			action.isDisabled("lnk_SetSchedDate", true);

		} catch(AssertionError ae){
			Log.error("JUNIT _inactiveSchedDateButton --- "+ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
			Base.bResult = false;
			Assert.fail();
		} catch(Exception e){
	 		Log.error("Exception JUNIT _inactiveSchedDateButton ---- "+e.getMessage());
	 		extentTest.log(LogStatus.ERROR, e.getMessage());
	 		Base.bResult = false;
	 		Assert.fail();
	    }
	}
 	
}
