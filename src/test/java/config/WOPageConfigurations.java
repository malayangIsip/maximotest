package config;

import static executionEngine.Base.OR;
import static executionEngine.Base.action;
import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import executionEngine.Base;
//import executionEngine.Base;
import executionEngine.TestAutomation;
import utility.Log;
import utility.TestResultListener;

public class WOPageConfigurations extends TestAutomation {
    static String testCase = "";	
    static String testName = "WOPageConfigurations";
	
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
    
    @AfterMethod
    public void logout() {
    	logout(testName, testCase);
    }
    
    
    @Test
    public void testWOPage() throws Exception {
    	testCase = "testWOPage";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("maxadmin");
			action.goToWOPage();
			
			action.checkColumnExistsListView("Work Order, Description, Location, Asset, Status, Scheduled Start, Priority, Field Engineer, Discipline, Gang, Area, Line, Start Metrage, End Metrage, Offset Rounded??", true);

			action.click("btn_Advanced_Search");
			action.click("btn_Worktype");
			action.input("txtbx_AdvancedSearch_Code", "CM");
			action.click("btn_Filter_SelectValue");
			action.clickFirstValueFromList();
			
			action.click("btn_Status");
			action.input("txtbx_AdvancedSearch_Code", "CREATE");
			action.click("btn_Filter_SelectValue");
			action.clickFirstValueFromList();
			
			action.click("btn_Region");
			action.input("txtbx_AdvancedSearch_Code", "CENTRAL");
			action.click("btn_Filter_SelectValue");
			action.clickFirstValueFromList();
			
			action.click("btn_Area");
			action.input("txtbx_AdvancedSearch_Code", "WELLINGTON");
			action.click("btn_Filter_SelectValue");
			action.clickFirstValueFromList();
			
			
			action.click("btn_Find");
			action.clickFirstValueFromList();

//			TODO check WO tab here
	
	    } catch (Exception e) {
	    	Log.error("Exception testWOPage--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test
    public void testNewWOPage() {
    	testCase = "testNewWOPage";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("maxadmin");
			action.goToWOPage();
			action.clickNew();
			
			action.isNull("txtbx_Discipline", true);
			action.isNull("txtbx_Region", true);
			action.isNull("txtbx_Area", true);
			action.isNull("txtbx_Line", true);
			action.isNull("txtbx_StartMetrage", true);
			action.isNull("txtbx_EndMetrage", true);
			action.isRequired("txtbx_FinancialYear", false);
			
			action.click("btn_Asset_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_Lookup_Value_Search", "1000001");
			action.click("btn_Filter");
			action.clickFirstValueFromList();
			
			action.isNull("txtbx_Discipline", false);
			action.isNull("txtbx_Region", false);
			action.isNull("txtbx_Area", false);
			action.isNull("txtbx_Line", false);
			action.isNull("txtbx_StartMetrage", false);
			action.isNull("txtbx_EndMetrage", false);
			action.isNull("txtbx_BillToCustomer", true);
			action.isNull("txtbx_CustomerName", true);
			action.isNull("txtbx_Worktype", true);
			action.isNull("txtbx_ActivityType", true);
			action.isNull("txtbx_GLAccount", true);
			action.isRequired("txtbx_ActivityType", false);
			action.isRequired("txtbx_FinancialYear", false);
			action.isReadOnly("txtbx_ActivityType", true);
			action.isReadOnly("txtbx_GLAccount", true);

			action.click("btn_BillToCustomer_chevron");
			action.click("lnk_SelectValue");
			action.clickFirstValueFromList();
			
			action.isReadOnly("txtbx_Discipline", false);
			action.isReadOnly("txtbx_Region", false);
			action.isReadOnly("txtbx_Area", false);
			action.isReadOnly("txtbx_Line", false);
			action.isReadOnly("txtbx_StartMetrage", true);
			action.isReadOnly("txtbx_EndMetrage", true);
			action.isReadOnly("txtbx_BillToCustomer", false);
			action.isReadOnly("txtbx_CustomerName", true);
			action.isNull("txtbx_BillToCustomer", false);
			action.isNull("txtbx_CustomerName", false);
				
			action.click("btn_Worktype");
			action.input("txtbx_Search_Code", "CAP");
			
			action.click("btn_Filter");
			action.clickFirstValueFromList();
			
			action.isReadOnly("txtbx_ActivityType", false);
			action.isRequired("txtbx_ActivityType", true);
			action.isRequired("txtbx_FinancialYear", true);
			action.assertValue("txtbx_Discipline", "TRACK");
			action.assertValue("txtbx_Worktype", "CAP");

	    } catch (Exception e) {
	    	Log.error("Exception testNewWOPage--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test
    public void testWOfromPM() {
    	testCase = "testWOfromPM";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  

			action.login("maxadmin");
			action.goToPMPage();
			
			String[][] toSearch = new String[][] {{"txtbx_PMNUM", "PM10010"}};
			action.advancedSearch(toSearch);
			action.assertValue("txtbx_Status", "ACTIVE");
			
			action.click("lnk_GenerateWOs");
			action.click("chkbx_UseFrequencyCriteria");
			action.clickOK();
			
			action.waitElementExists("systemMessage");
			action.clickClose();
			
			action.goToWOPage();
			action.advancedSearch(toSearch);
			action.clickFirstValueFromList();
			action.assertValue("txtbx_Status", "APPR");
						
			action.assertValue("txtbx_PMNUM", "PM10010");
			action.isNull("txtbx_WONUM", false);
			action.isNull("txtbx_Discipline", false);
			action.isNull("txtbx_Region", false);
			action.isNull("txtbx_Area", false);
			action.isNull("txtbx_Line", false);
			action.isNull("txtbx_Worktype", false);
			action.isNull("txtbx_ActivityType", false);
			action.isNull("txtbx_GLAccount", false);
			action.isNull("txtbx_Priority", false);
			action.isNull("txtbx_TargetStart", false);
			action.isNull("txtbx_TargetFinish", false);
			action.isNull("txtbx_Reportedby", false);
			action.isNull("txtbx_ReportedDate", false);
			action.isNull("txtbx_Gang", false);
			action.isNull("txtbx_Supervisor", false);
		

	    } catch (Exception e) {
	    	Log.error("Exception testWOfromPM--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test
    public void testWOfromSR() {
    	testCase = "testWOfromSR";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  

			action.login("maxadmin");
			action.goToSRPage();
			action.createSR(testCase, "KRNETWORK");
			
			action.click("hvr_Create");
			action.click("lnk_CreateWO");
			action.click("tab_RelatedRecord");
			action.isEmpty("tbl_Related_WO", false);
			
			action.click("btn_Related_Chevron_Row1");
			action.click("lnk_Related_WO");
			
			action.assertValue("txtbx_Status", "CREATE");
			action.isNull("txtbx_WONUM", false);
			action.isNull("txtbx_ActivityType", false);
			action.isNull("txtbx_Discipline", false);
			action.isNull("txtbx_Region", false);
			action.isNull("txtbx_Area", false);
			action.isNull("txtbx_Line", false);
			action.isNull("txtbx_Priority", false);
//			removed for GUI change
//			action.isNull("txtbx_SRSource", false);
//			action.isReadOnly("txtbx_SRSource", true);
			action.isReadOnly("txtbx_WONUM", true);
			action.isReadOnly("txtbx_ActivityType", true);
			action.isReadOnly("txtbx_GLAccount", true);
			action.isReadOnly("txtbx_Discipline", false);
			action.isReadOnly("txtbx_Region", false);
			action.isReadOnly("txtbx_Area", false);
			action.isReadOnly("txtbx_Line", false);
			action.isReadOnly("txtbx_Priority", false);

	    } catch (Exception e) {
	    	Log.error("Exception testWOfromSR--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test
    public void testWOAdvancedSearchButtons() {
    	testCase = "testWOAdvancedSearchButtons";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("maxadmin");
			action.goToWOPage();
			
			action.click("btn_Advanced_Search");
			action.click("btn_Gang_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_AdvancedSearch_Code", "AREAMGEW");
			action.enter("txtbx_AdvancedSearch_Code");
			action.clickFirstValueFromList();
			action.assertValue("txtbx_Gang", "=AREAMGEW");

			action.click("btn_FieldEngineer_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_AdvancedSearch_Code", "mme9310");
			action.enter("txtbx_AdvancedSearch_Code");
			action.clickFirstValueFromList();
			action.assertValue("txtbx_FieldEngineer", "=MME9310");
			
			action.click("btn_Discipline");
			action.checkValueExists("ELECTRICAL, SIGNALS, STRUCTURES, TELECOMS, TRACK, TRACTION", true);
			action.clickOK();
			
			action.click("btn_Region");
			action.checkValueExists("Central, Northern, Southern", true);
			action.clickOK();
		
			action.click("btn_Area");
			action.checkValueExists("Auckland Metro, Greater Auckland, East & West, Christchurch, Dunedin, Greymouth, Hamilton East, Hamilton South, Palmerston North, Wellington", true);
			action.clickOK();
			action.clickCancel();
			
			action.clickNew();
			action.click("btn_Save");
			action.verifyAlert("BMXAA7998E");
			action.clickOK();
			action.clickListViewTab();
			action.clickNo();
			
			action.createWO(testCase, "CM");
			action.click("lnk_ChangeStatus");
			action.click("dropdown_ChangeStatus");
			action.checkStatusExists("Approved, Canceled, Closed, Field Complete, On Hold, Park, Planned, Ready To Plan, Waiting on Material", true);
			action.clickCancel();

	    } catch (Exception e) {
	    	Log.error("Exception testWOAdvancedSearchButtons--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test
    public void testWOQuickReporting() {
    	testCase = "testWOQuickReporting";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("maxadmin");
			action.goQuickReportingPage();
			
			action.click("btn_Advanced_Search");
			action.click("btn_Gang_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_AdvancedSearch_Code", "AREAMGEW");
			action.enter("txtbx_AdvancedSearch_Code");
			action.clickFirstValueFromList();			
			action.assertValue("txtbx_Gang", "=AREAMGEW");
			
			action.click("btn_FieldEngineer_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_AdvancedSearch_Code", "mme9310");
			action.enter("txtbx_AdvancedSearch_Code");
			action.clickFirstValueFromList();
			action.assertValue("txtbx_FieldEngineer", "=MME9310");

			action.click("btn_Discipline");
			action.checkValueExists("ELECTRICAL, SIGNALS, STRUCTURES, TELECOMS, TRACK, TRACTION", true);
			action.clickOK();
			action.clickCancel();

	    } catch (Exception e) {
	    	Log.error("Exception testWOQuickReporting--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test
    public void testWOAdvancedSearchFields() {
    	testCase = "testWOAdvancedSearchFields";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("maxadmin");
			action.goToWOPage();
			
			action.click("btn_Advanced_Search");
			action.fieldExists("Work Order", true);
			action.fieldExists("Description", true);
			action.fieldExists("Parent WO", true);
			action.fieldExists("Location", true);
			action.fieldExists("Asset", true);
			action.fieldExists("Common Name", true);
			action.fieldExists("PM", true);
			action.fieldExists("Classification", true);
			action.fieldExists("Activity Type", true);
			action.fieldExists("Discipline", true);
			action.fieldExists("Region", true);
			action.fieldExists("Area", true);
			action.fieldExists("Line", true);
			action.fieldExists("Start Metrage", true);
			action.fieldExists("End Metrage", true);
			action.fieldExists("Recoverable", true);
			action.fieldExists("Problem Code", true);
			action.fieldExists("Failure Class", true);
			action.fieldExists("Service", true);
			action.fieldExists("Vendor", true);
			action.fieldExists("Gang", true);
			action.fieldExists("Work Type", true);
			action.fieldExists("Status", true);
			action.fieldExists("History", true);
			action.fieldExists("Priority", true);
			action.fieldExists("Financial Year", true);
			action.fieldExists("Is Task", true);
			action.fieldExists("Field Engineer", true);
			action.fieldExists("Owner", true);
			action.fieldExists("Reported Date", true);
			action.fieldExists("Target Start", true);
			action.fieldExists("Target Finish", true);
			action.fieldExists("Scheduled Start", true);
			action.fieldExists("Scheduled Finish", true);
			action.fieldExists("Job Plan", true);
			action.fieldExists("Supervisor", true);
			action.fieldExists("Reported By", true);
			action.fieldExists("On Behalf Of", true);
			action.fieldExists("Protection Assist", true);
			action.fieldExists("Traction Assist", true);
			action.fieldExists("Signals Assist", true);
			action.fieldExists("Telecoms Assist", true);
			action.fieldExists("Track Assist", true);
			action.fieldExists("Contractor Assist", true);
			action.elementExists("btn_Find", true);
			action.elementExists("btn_Cancel", true);
			action.elementExists("btn_Revise", true);
			action.clickCancel();

	    } catch (Exception e) {
	    	Log.error("Exception testWOAdvancedSearchFields--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test
    public void testWOListView() {
    	testCase = "testWOListView";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  

			action.login("maxadmin");
			action.goToWOPage();
			
			action.checkColumnExistsListView("Work Order, Description, Location, Asset, Status, Scheduled Start, Priority, Field Engineer, Discipline, Gang, Area, Line, Start Metrage, End Metrage, Offset Rounded??", true);
			action.elementExists("btn_SaveQuery", true);
			action.elementExists("btn_Bookmarks", true);
			action.elementExists("btn_Advanced_Search", true);
			
			action.clickNew();
			action.waitElementExists("tab_Main");
			action.fieldExists("Work Order", true);
			action.fieldExists("Parent WO", true);
			action.fieldExists("Location", true);
			action.fieldExists("Asset", true);
			action.fieldExists("Common Name", true);
			action.fieldExists("Work Type", true);
			action.fieldExists("Activity Type", true);
			action.fieldExists("GL Account", true);
			action.fieldExists("Classification", true);
			action.fieldExists("Class Description", true);
//			removed for GUI change
//			action.fieldExists("Failure Class", true);
//			action.fieldExists("Problem Code", true);
			action.fieldExists("Status", true);
			action.fieldExists("Status Date", true);
			action.fieldExists("Inherit Status Changes?", true);
			action.fieldExists("Is Task", true);
//			action.fieldExists("Under Flow Control?", true);
//			action.fieldExists("Flow Action:", true);
			action.fieldExists("Storeroom Material Status:", true);
			action.fieldExists("Direct Issue Material Status:", true);
			action.fieldExists("Work Package Material Status:", true);
			action.fieldExists("Material Status Last Updated:", true);
			action.fieldExists("Discipline", true);
			action.fieldExists("Region", true);
			action.fieldExists("Area", true);
			action.fieldExists("Line", true);
			action.fieldExists("Start Metrage", true);
			action.fieldExists("End Metrage", true);
			action.fieldExists("Offset Rounded?", true);
			action.fieldExists("Delivery Address", true);
			action.fieldExists("Protection", true);
			action.fieldExists("Traction", true);
			action.fieldExists("Signals", true);
			action.fieldExists("Telecoms", true);
			action.fieldExists("Track", true);
			action.fieldExists("Structures", true);
			action.fieldExists("Contractor", true);
			action.fieldExists("CSB / BOL", true);
			action.fieldExists("Incur / Remove", true);
			action.fieldExists("TSR Details", true);
			action.fieldExists("Work Planned Day/Night", true);
			action.fieldExists("Job Plan", true);
			action.fieldExists("Job Plan Revision #", true);
//			removed for GUI change
//			action.fieldExists("Original Job Plan", true);
//			action.fieldExists("Original Job Plan Revision #", true);
			action.fieldExists("Plan Estimated Cost (RTP)", true);
			action.fieldExists("PM", true);
			action.fieldExists("Code", true);
//			removed for GUI change
//			action.fieldExists("SR Source", true);
			action.fieldExists("Total Planned Cost", true);
			action.fieldExists("Actual Cost", true);
			action.fieldExists("Recoverable?", true);
			action.fieldExists("Bill To Customer #", true);
			action.fieldExists("Customer Name", true);
			action.fieldExists("Customer Phone", true);
//			action.fieldExists("Asset Up?", true);
//			action.fieldExists("Warranties Exist?", true);
//			action.fieldExists("Charge to Store?", true);
//			action.fieldExists("Safety Plan", true);
//			action.fieldExists("Contract", true);
			action.fieldExists("Priority", true);
			action.fieldExists("Financial Year", true);
			action.fieldExists("Priority Justification", true);
//			action.fieldExists("Risk Assessment", true);
//			action.fieldExists("Originating Record", true);
//			action.fieldExists("Originating Record Class", true);
//			action.fieldExists("Has Follow-up Work?", true);
//			action.fieldExists("Interruptible", true);
			action.fieldExists("Target Start", true);
			action.fieldExists("Target Finish", true);
			action.fieldExists("Scheduled Start", true);
			action.fieldExists("Scheduled Finish", true);
			action.fieldExists("Start No Earlier Than", true);
			action.fieldExists("Finish No Later Than", true);
//			action.fieldExists("Requires Asset Downtime?", true);
//			action.fieldExists("Requires Location Downtime?", true);
			action.fieldExists("Actual Start", true);
			action.fieldExists("Actual Finish", true);
			action.fieldExists("Duration", true);
//			action.fieldExists("Time Remaining", true);
			action.fieldExists("Include Tasks in Schedule?", true);
			action.fieldExists("Reported By", true);
			action.fieldExists("Reported Date", true);
//			action.fieldExists("On Behalf Of", true);
//			action.fieldExists("Phone", true);
			action.fieldExists("Supervisor", true);
			action.fieldExists("Field Engineer", true);
			action.fieldExists("Gang", true);
			action.fieldExists("Crew Work Group", true);
			action.fieldExists("Crew", true);
			action.fieldExists("Owner", true);
//			action.fieldExists("Owner Group", true);
//			action.fieldExists("Service", true);
//			action.fieldExists("Service Group", true);
//			action.fieldExists("Vendor", true);
			action.tableExists("Multiple Assets,Locations and CIs");

	    } catch (Exception e) {
	    	Log.error("Exception testWOListView--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test
    public void testWOPlansTab() throws Exception {
    	testCase = "testWOPlansTab";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  

			action.login("maxadmin");
			action.goToWOPage();
			action.clickNew();
			action.clickPlansTab();
			
			action.fieldExists("Work Order", true);
			action.fieldExists("Parent WO", true);
			action.fieldExists("Status", true);
			
			
			Log.info("Check Children for Work Order table.......................");
			action.tableExists("Children of Work Order");
			action.checkTableColumnExists("Sequence, Record, Record Class, Summary, Location, Asset, Status", "Children of Work Order");

			action.click("btn_NewRow_ChildrenWO");
			action.fieldExists("Record", true);
			action.fieldExists("Sequence", true);
			action.fieldExists("Record Class", true);
			action.fieldExists("Location", true);
			action.fieldExists("Asset", true);
			action.fieldExists("Job Plan", true);
			action.fieldExists("Route", true);
			action.fieldExists("Route Stop", true);
			action.fieldExists("Priority", true);
			action.fieldExists("Status", true);
			action.fieldExists("GL Account", true);
			action.fieldExists("Inherit Status Changes?", true);
			action.fieldExists("Target Start", true);
			action.fieldExists("Target Finish", true);
			action.fieldExists("Scheduled Start", true);
			action.fieldExists("Scheduled Finish", true);
			action.fieldExists("Start No Earlier Than", true);
			action.fieldExists("Finish No Later Than", true);
			action.fieldExists("Include Tasks in Schedule?", true);
			action.fieldExists("Actual Start", true);
			action.fieldExists("Actual Finish", true);
			action.fieldExists("Estimated Duration", true);
			action.fieldExists("Include Tasks in Schedule", true);
			action.isChecked("chkbx_IncludeTasksInSchedule", true);
			action.buttonExists("Select Assets", true);
			action.buttonExists("Select Locations", true);
			action.buttonExists("Select Work Orders", true);
			action.clickCloseDetailsRow1();
			
			Log.info("Check Tasks for Work Order table..........................");
			action.tableExists("Tasks for Work Order");
			action.checkTableColumnExists("Task, Summary, Estimated Duration, Status, Owner, Scheduled Start, Scheduled Finish, Include in Schedule?", "Tasks for Work Order");

			action.click("btn_NewRow_TasksWO");
			action.fieldExists("Task", true);
			action.fieldExists("Sequence", true);
			action.fieldExists("Status", true);
			action.fieldExists("Accepts Charges?", true);
			action.fieldExists("Inherit Status Changes?", true);
			action.fieldExists("Appointment Required?", true);
			action.fieldExists("Owner", true);
			action.fieldExists("Crew Work Group", true);
			action.fieldExists("Route", true);
			action.fieldExists("Route Stop", true);
			action.fieldExists("Reference WO:", true);
			action.fieldExists("Location", true);
			action.fieldExists("Asset", true);
			action.fieldExists("Observation", true);
//			action.fieldExists("Person Responsible", true);
			action.fieldExists("Measurement Point", true);
			action.fieldExists("Measurement Value", true);
			action.fieldExists("Measurement Date", true);
			action.fieldExists("Target Start", true);
			action.fieldExists("Target Finish", true);
			action.fieldExists("Scheduled Start", true);
			action.fieldExists("Scheduled Finish", true);
			action.fieldExists("Start No Earlier Than", true);
			action.fieldExists("Finish No Later Than", true);
			action.fieldExists("Actual Start", true);
			action.fieldExists("Actual Finish", true);
			action.fieldExists("Estimated Duration", true);
			action.fieldExists("Include in Schedule?", true);
			
			Log.info("Check Plans ..................... table");
//			action.clickPlansTab();
			action.clickMaterialsTab();
			
			action.click("btn_NewRow_Materials");
			action.isReadOnly("txtbx_OrderUnit", true);
			action.isReadOnly("txtbx_UnitCost", true);
			action.isReadOnly("txtbx_LineCost", true);
			action.isReadOnly("txtbx_Vendor", true);
			action.isReadOnly("txtbx_RequestedBy", true);
			action.assertValue("txtbx_LineType", "Item");
			action.assertValue("txtbx_UnitCost", "0.00");
			action.assertValue("txtbx_LineCost", "0.00");
			action.assertValue("txtbx_ExcludeFromCalc", "YES");
			action.assertValue("txtbx_Quantity", "1.00");
			action.assertValue("txtbx_ReservationType", "AUTOMATIC");
			action.assertValue("txtbx_RequestedBy", "MAXADMIN");
			action.assertValue("txtbx_CommodityGroup", "1000MAT");
			action.isRequired("txtbx_Item", true);
			action.isRequired("txtbx_LineType", true);
			action.isRequired("txtbx_ExcludeFromCalc", true);
			action.isRequired("txtbx_Quantity", true);
			action.isRequired("txtbx_Storeroom", true);
			action.isRequired("txtbx_ReservationType", true);
			action.isRequired("txtbx_CommodityGroup", true);
			action.isRequired("txtbx_CommodityCode", true);
			action.isRequired("txtbx_RequiredDate", true);
		
			action.click("btn_LineType");
			action.click("option_Material");
			
			action.isReadOnly("txtbx_Item", true);
			action.isReadOnly("txtbx_OrderUnit", false);
			action.isReadOnly("txtbx_UnitCost", false);
			action.isReadOnly("txtbx_LineCost", true);
			action.isReadOnly("txtbx_Vendor", false);
			action.isReadOnly("txtbx_Storeroom", true);
			action.isReadOnly("txtbx_ReservationType", true);			
			action.assertValue("txtbx_UnitCost", "0.00");
			action.assertValue("txtbx_Quantity", "1.00");
			action.assertValue("txtbx_LineCost", "0.00");
			action.assertValue("txtbx_ReservationType", "AUTOMATIC");
			action.assertValue("txtbx_RequestedBy", "MAXADMIN");
			action.isRequired("txtbx_Quantity", true);
			action.isRequired("txtbx_LineType", true);
			action.isRequired("txtbx_ExcludeFromCalc", true);
			action.isRequired("txtbx_Quantity", true);
			action.isRequired("txtbx_OrderUnit", true);
			action.isRequired("txtbx_UnitCost", true);
			action.isRequired("txtbx_CommodityGroup", true);
			action.isRequired("txtbx_CommodityCode", true);
			action.isRequired("txtbx_RequiredDate", true);
			action.isRequired("txtbx_Vendor", true);
			
			action.click("tab_Labour");
			action.click("btn_NewRow_Labour");
			
			action.isRequired("txtbx_Quantity", true);
			action.isRequired("txtbx_PersonHours", true);

			action.click("tab_Services");
			action.click("btn_NewRow_Services");
			
			action.assertValue("txtbx_LineType", "Service");
			action.isReadOnly("txtbx_Service", true);
			action.isRequired("txtbx_Service", false);
			action.isRequired("txtbx_ServiceDescription", true);
			action.assertValue("txtbx_ExcludeFromCalc1", "YES");
			action.assertValue("txtbx_Quantity", "1.00");
			action.assertValue("txtbx_UnitCost", "0.00");
			action.assertValue("txtbx_RequestedBy", "MAXADMIN");
			action.assertValue("txtbx_CommodityGroup", "1000SVC");
			action.isReadOnly("txtbx_RequiredDate", false);
			action.isRequired("txtbx_Vendor", true);
			action.isRequired("txtbx_CommodityGroup", true);
			action.isRequired("txtbx_CommodityCode", true);
			action.isRequired("txtbx_ExcludeFromCalc1", true);
			action.isRequired("txtbx_Quantity", true);
			action.isRequired("txtbx_OrderUnit", true);
			action.isRequired("txtbx_UnitCost", true);
		
			action.click("btn_LineType");
			action.click("option_StandardService");
			
			action.assertValue("txtbx_LineType", "Standard Service");
			action.isRequired("txtbx_Service", true);
			action.isReadOnly("txtbx_Service", false);
			action.isReadOnly("txtbx_ServiceDescription", true);
			action.isRequired("txtbx_ServiceDescription", false);
			action.assertValue("txtbx_ExcludeFromCalc1", "YES");
			action.assertValue("txtbx_Quantity", "1.00");
			action.assertValue("txtbx_UnitCost", "0.00");
			action.assertValue("txtbx_RequestedBy", "MAXADMIN");
			action.assertValue("txtbx_CommodityGroup", "1000SVC");
			action.isReadOnly("txtbx_RequiredDate", false);
			action.isRequired("txtbx_Vendor", true);
			action.isRequired("txtbx_CommodityGroup", true);
			action.isRequired("txtbx_CommodityCode", true);
			action.isRequired("txtbx_ExcludeFromCalc1", true);
			action.isRequired("txtbx_Quantity", true);
			action.isRequired("txtbx_OrderUnit", true);
			action.isRequired("txtbx_UnitCost", true);
/*
			action.click("btn_SelectStandardService");
			action.waitElementExists("icon_Filter");
			driver.findElement(By.xpath(OR.getProperty("icon_Filter"))).click();
//			action.click("icon_Filter"); for some reason this does not work maybe because element's aria-disabled property is set to true
			action.input("txtbx_Search_Code", "hirail4");
			action.clickFilter();
			action.click("lst_SelectStandardService");
			action.clickOK();
			
			action.isRequired("txtbx_Service", true);
			action.isRequired("txtbx_Vendor", true);
			action.isRequired("txtbx_CommodityGroup", true);
			action.isRequired("txtbx_CommodityCode", true);
			action.isRequired("txtbx_ExcludeFromCalc1", true);
			action.isRequired("txtbx_Quantity", true);
			action.isRequired("txtbx_OrderUnit", true);
			action.isRequired("txtbx_UnitCost", true);
			action.isNull("txtbx_Service", false);
			action.isNull("txtbx_ServiceDescription", false);
			action.isNull("txtbx_ExcludeFromCalc1", false);
			action.isNull("txtbx_Quantity", false);
			action.isNull("txtbx_OrderUnit", false);
			action.isNull("txtbx_UnitCost", false);
			action.isNull("txtbx_CommodityGroup", false);
			action.isNull("txtbx_CommodityCode", false);
			action.isNull("txtbx_Vendor", false);
	*/	
			action.click("tab_Tools");
			action.click("btn_NewRow_Tools");
			
			action.isRequired("txtbx_Tool", true);
			action.isRequired("txtbx_ExcludeFromCalc1", true);
			action.isRequired("txtbx_Quantity", true);
			action.isRequired("txtbx_ToolHours", true);
			action.assertValue("txtbx_ExcludeFromCalc1", "YES");
			action.assertValue("txtbx_ToolHours", "0:00");
			action.assertValue("txtbx_Quantity", "1.00");
			action.assertValue("txtbx_ReservationType", "AUTOMATIC");
			action.isReadOnly("txtbx_ReservationType", true);
			action.isReadOnly("txtbx_RequiredDate", true);
			action.isReadOnly("chkbx_ReservationRequired", false);
			action.isChecked("chkbx_ReservationRequired", false);
	    } catch (Exception e) {
	    	Log.error("Exception testWOPlansTab--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test
    public void testLinearSegmentDetails() {
    	testCase = "testLinearSegmentDetails";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  

			action.login("maxadmin");
			action.goToWOPage();
			action.clickNew();
			
			Log.info("Linear details be hidden when asset is non-linear...................");
			action.input("txtbx_AssetNum", "2000000");
			action.click("txtbx_Location");  //just to refresh
			action.fieldExists("Linear Segment Details", false);
			action.elementExists("txtbx_StartRefPoint", false);
			action.elementExists("txtbx_EndRefPoint", false);
			action.elementExists("txtbx_StartRefPointOffset", false);
			action.elementExists("txtbx_EndRefPointOffset", false);
			action.elementExists("txtbx_StartMeasure", false);
			action.elementExists("txtbx_EndMeasure", false);
			
			Log.info("Linear details displays when asset is linear.......................");
			action.input("txtbx_AssetNum", "1000014");
			action.click("txtbx_Location");  //just to refresh
			action.fieldExists("Linear Segment Details", true);
			action.elementExists("txtbx_StartRefPoint", true);
			action.elementExists("txtbx_EndRefPoint", true);
			action.elementExists("txtbx_StartRefPointOffset", true);
			action.elementExists("txtbx_EndRefPointOffset", true);
			action.elementExists("txtbx_StartMeasure", true);
			action.elementExists("txtbx_EndMeasure", true);
			
	    } catch (Exception e) {
	    	Log.error("Exception testLinearSegmentDetails--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, "testLinearSegmentDetails--- " +e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
}


//    TODO
/*
    public void testWorkOrderTab() 
    public void testWORelatedRecodsTab()
    public void testActualsTab()
    public void testSafetyTab()
    public void testLogTab()
    public void testFailureReportingTab()
    public void testSpecificationsTab()
*/