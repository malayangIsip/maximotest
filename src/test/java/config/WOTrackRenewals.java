package config;

import static executionEngine.Base.action;
import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;

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

public class WOTrackRenewals extends TestAutomation {
    static String testCase = "";	
    static String testName = "WOTrackRenewals";
	
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
    
    /*
     * Track Renewals End to End
     */
//    @Test
    public void testTrackRenewals() {
    	testCase = "testTrackRenewals";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("mxfldeng");
			action.goToWOPage();
			action.clickNew();
			
			action.input("txtbx_Description", testCase);
			action.input("txtbx_AssetNum", "1000009");
			action.input("txtbx_Worktype", "CAP");
			action.click("btn_Priority");	
			action.clickFirstValueFromList();
			action.input("txtbx_ActivityType", "Destress");
			action.isRequired("txtbx_FinancialYear", true);
			action.click("btn_FinancialYear");
			action.clickFirstValueFromList();
			action.input("txtbx_StartRefPoint", "3");
			action.input("txtbx_EndRefPoint", "3");
			action.input("txtbx_StartRefPointOffset", "0");
			action.input("txtbx_EndRefPointOffset", "1");
			action.input("txtbx_ScheduledStart", action.getDate().plusWeeks(3).toString());
			action.save();
			
			action.assertValue("txtbx_Priority", "18");
			action.assertValue("txtbx_Status", "CREATE");
			action.assertValue("txtbx_StartMetrage", "3.000");
			action.assertValue("txtbx_EndMetrage", "3.001");
//			action.isReadOnly("txtbx_OriginalJobPlan", true);
			
			action.click("lnk_ChangeStatus");
			action.click("dropdown_ChangeStatus");
			action.checkStatusExists("Canceled", true);
					
			action.clickCancel();
			action.route();
			action.verifyAlert("Track Renewal WO cannot initiate workflow unless status is PLAN");
			action.clickClose();
			
			action.storeValue("txtbx_WONUM", "WONUM");
			driver.close();
			
			action.openBrowser("Chrome");
			action.login("mxeng1");
			action.goToWOPage();
			action.input("txtbx_Search_Code", action.getStoredValue("WONUM"));
			action.click("btn_Filter1");
			action.clickFirstValueFromList();
			
			action.isReadOnly("txtbx_PlanEstimatedCost", false);
			action.isReadOnly("txtbx_FinancialYear", false);
//			action.isReadOnly("txtbx_OriginalJobPlan", false);
			action.isNull("txtbx_PlanEstimatedCost", true);
			action.isNull("txtbx_GLAccount", false);
			action.isRequired("txtbx_FinancialYear", true);
			action.input("txtbx_JobPlan", "JP92643");
			action.save();
			
			action.assertValue("txtbx_Classification", "TRACKRENEWALS \\ DESTRESS \\ PANDROL");
			
			action.click("tab_Plans");
			action.expectedRows("Children", "0");
			action.expectedRows("Tasks", "0");
			action.expectedRows("Labour", "9");
			
			action.click("tab_Materials");
			action.expectedRows("Materials", "20");
			
			action.click("tab_Services");
			action.expectedRows("Services", "1");
			
			action.click("tab_Tools");
			action.expectedRows("Tools", "0");
			
			action.route();
			action.verifyAlert("Track Renewal WO cannot initiate workflow unless status is PLAN");
			action.clickClose();
			
			action.click("lnk_ChangeStatus");
			action.click("txtbx_NewStatus");
			action.checkStatusExists("Canceled, Locked, Ready to Plan", true);
			
			action.click("dropdown_ChangeStatus_RTP");		
			action.clickOK();
			action.assertValue("txtbx_Status", "RTP");
			
			action.click("tab_Main");	
//			action.fieldValuesEqual("txtbx_JobPlan", "txtbx_OriginalJobPlan"); don't delete yet(GUI change)
			action.fieldValuesEqual("txtbx_Total_Hier_Est_Cost", "txtbx_PlanEstimatedCost");
			action.isReadOnly("txtbx_JobPlanRevision", true);
//			action.isReadOnly("txtbx_OriginalJobPlan", false);
			action.isReadOnly("txtbx_PlanEstimatedCost", false);
			
			action.click("lnk_ChangeStatus");
			action.click("dropdown_ChangeStatus");
			action.checkStatusExists("Canceled, Create, Planned", true);
			
			action.click("dropdown_ChangeStatus_PLAN");
			action.clickOK();
			
			action.assertValue("txtbx_Status", "PLAN");
			action.storeValue("txtbx_WONUM", "WONUM");
			driver.close();
			
			action.openBrowser("Chrome");
			action.login("mxfldeng");
			action.goToWOPage();
			
			action.input("txtbx_Search_Code", action.getStoredValue("WONUM"));
			action.click("btn_Filter1");
			action.clickFirstValueFromList();
			
			action.isReadOnly("txtbx_AssetNum", true);
			action.isReadOnly("txtbx_Description", true);
			action.isReadOnly("txtbx_Worktype", true);
			action.isReadOnly("txtbx_ActivityType", true);
			action.isReadOnly("txtbx_Classification", true);
			action.isReadOnly("txtbx_Discipline", true);
			action.isReadOnly("txtbx_Region", true);
			action.isReadOnly("txtbx_Area", true);
			action.isReadOnly("txtbx_Line", true);
			action.isReadOnly("txtbx_JobPlan", true);
//			action.isReadOnly("txtbx_OriginalJobPlan", true);
			action.isReadOnly("txtbx_PlanEstimatedCost", true);
			action.isReadOnly("txtbx_FinancialYear", true);
			action.isReadOnly("txtbx_StartRefPoint", true);
			action.isReadOnly("txtbx_EndRefPoint", true);
			action.isReadOnly("txtbx_StartRefPointOffset", true);
			action.isReadOnly("txtbx_EndRefPointOffset", true);
			action.isDisabled("lnk_ChangeStatus", true);
			action.isDisabled("lnk_SetSchedDate", false);
			action.routeWF("dfa");
		
			Assert.assertEquals(action.inWorkflow(), true);

	    } catch (Exception e) {
	    	Log.error("Exception testTrackRenewals--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    /*
     * Track Renewals Status and Workflow as Engineer
     */
//    @Test
    public void testTrackRenewalsAsEngineer() {
    	testCase = "testTrackRenewalsAsEngineer";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("mxeng1");
			action.goToWOPage();
			
			action.createWOwithLinear(testCase, "CAP");
			action.isReadOnly("txtbx_FinancialYear", false);
//			action.isReadOnly("txtbx_OriginalJobPlan", false);
			action.isReadOnly("txtbx_JobPlanRevision", true);
			action.isReadOnly("txtbx_Worktype", false);
			action.isReadOnly("txtbx_ActivityType", false);
			action.isReadOnly("txtbx_Discipline", false);
			action.isReadOnly("txtbx_AssetNum", false);
			action.isRequired("txtbx_FinancialYear", true);
			
			action.click("lnk_ChangeStatus");
			action.click("dropdown_ChangeStatus");
			action.checkStatusExists("Canceled, Locked, Ready to Plan", true);
			action.clickCancel();
			
			action.route();
			action.verifyAlert("Track Renewal WO cannot initiate workflow unless status is PLAN");
			action.clickClose();
			
			action.changeStatus("Ready to Plan");
			
			action.isReadOnly("txtbx_FinancialYear", false);
//			action.isReadOnly("txtbx_OriginalJobPlan", false);
			action.isReadOnly("txtbx_JobPlanRevision", true);
			action.isReadOnly("txtbx_Worktype", true);
			action.isReadOnly("txtbx_ActivityType", true);
			action.isReadOnly("txtbx_Discipline", true);
			action.isReadOnly("txtbx_AssetNum", true);
			
			action.click("lnk_ChangeStatus");
			action.click("dropdown_ChangeStatus");
			action.checkStatusExists("Canceled, Create, Planned", true);
			action.clickCancel();
			
			action.route();
			action.verifyAlert("Track Renewal WO cannot initiate workflow unless status is PLAN");
			action.clickClose();
			
			action.changeStatus("Planned");
		
			action.isReadOnly("txtbx_FinancialYear", false);
//			action.isReadOnly("txtbx_OriginalJobPlan", false);
			action.isReadOnly("txtbx_JobPlanRevision", true);
			action.isReadOnly("txtbx_Worktype", true);
			action.isReadOnly("txtbx_ActivityType", true);
			action.isReadOnly("txtbx_Discipline", true);
			action.isReadOnly("txtbx_AssetNum", true);
			
			action.click("lnk_ChangeStatus");
			action.click("dropdown_ChangeStatus");
			action.checkStatusExists("Create, Canceled", true);
			action.clickCancel();
			
			action.route();
			action.verifyAlert("The Work Order has zero planned cost. Please review.");
			action.clickClose();
		
			action.assertValue("txtbx_Status", "PLAN");

	    } catch (Exception e) {
	    	Log.error("Exception testTrackRenewalsAsEngineer--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    /*
     * Track Renewals Status and Workflow as Field Engineer
     */
    @Test
    public void testTrackRenewalsAsFieldEngineer() {
    	testCase = "testTrackRenewalsAsFieldEngineer";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("mxfldeng");
			action.goToWOPage();
			
			String[][] toSearch = new String[][] {{"txtbx_Worktype", "CAP"}, {"txtbx_Status", "CREATE"}, {"txtbx_Discipline", "TRACK"}};
			action.advancedSearch(toSearch);
			action.clickFirstValueFromList();
			
			action.isReadOnly("txtbx_FinancialYear", false);
//			action.isReadOnly("txtbx_OriginalJobPlan", true);
			action.isReadOnly("txtbx_JobPlanRevision", true);
			action.isReadOnly("txtbx_Worktype", false);
			action.isReadOnly("txtbx_ActivityType", false);
			action.isReadOnly("txtbx_Discipline", false);
			action.isReadOnly("txtbx_AssetNum", false);
			
			action.click("lnk_ChangeStatus");
			action.click("dropdown_ChangeStatus");
			action.checkStatusExists("Canceled", true);
			action.clickCancel();
			
			action.route();
			action.verifyAlert("Track Renewal WO cannot initiate workflow unless status is PLAN");
			action.clickClose();
			
			action.clickListViewTab();
			toSearch = new String[][] {{"txtbx_Worktype", "CAP"}, {"txtbx_Status", "RTP"}, {"txtbx_Discipline", "TRACK"}};
			action.advancedSearch(toSearch);
			action.clickFirstValueFromList();
			
			action.isReadOnly("txtbx_FinancialYear", true);
//			action.isReadOnly("txtbx_OriginalJobPlan", true);
			action.isReadOnly("txtbx_JobPlanRevision", true);
			action.isReadOnly("txtbx_Worktype", true);
			action.isReadOnly("txtbx_ActivityType", true);
			action.isReadOnly("txtbx_Discipline", true);
			action.isReadOnly("txtbx_AssetNum", true);
			
			action.click("lnk_ChangeStatus");
			action.click("dropdown_ChangeStatus");
			action.checkStatusExists("Planned", true);
			action.clickCancel();
			
			action.route();
			action.verifyAlert("Track Renewal WO cannot initiate workflow unless status is PLAN");
			action.clickClose();
		
			action.clickListViewTab();
			toSearch = new String[][] {{"txtbx_Worktype", "CAP"}, {"txtbx_Status", "PLAN"}, {"txtbx_Discipline", "TRACK"}};
			action.advancedSearch(toSearch);
			action.clickFirstValueFromList();
			
			action.stopWF();
			
			action.isReadOnly("txtbx_FinancialYear", true);
//			action.isReadOnly("txtbx_OriginalJobPlan", true);
			action.isReadOnly("txtbx_JobPlanRevision", true);
			action.isReadOnly("txtbx_Worktype", true);
			action.isReadOnly("txtbx_ActivityType", true);
			action.isReadOnly("txtbx_Discipline", true);
			action.isReadOnly("txtbx_AssetNum", true);
			
			action.input("txtbx_ScheduledStart", action.getDate().plusWeeks(3).toString());		
			action.input("txtbx_ScheduledFinish", action.getDate().plusWeeks(3).toString());	
			action.click("lnk_SetSchedDate");
			action.save();
			
			action.routeWF("dfa");
			action.approveWOWF(action.getAttributeValue("txtbx_WONUM"));

	    } catch (Exception e) {
	    	Log.error("Exception testTrackRenewalsAsFieldEngineer--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    /*
     * Track Renewals Status and Workflow as Service Manager
     */
    @Test
    public void testTrackRenewalsAsServiceManager() {
    	testCase = "testTrackRenewalsAsServiceManager";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("mxsvcemgr1");
			action.goToWOPage();
			
			String[][] toSearch = new String[][] {{"txtbx_AssetNum", "1000014"}, {"txtbx_Worktype", "AMREN"}, {"txtbx_Status", "CREATE"}, {"txtbx_Discipline", "TRACK"}};
			action.advancedSearch(toSearch);
			action.clickFirstValueFromList();
			
			action.isRequired("txtbx_FinancialYear", true);
			action.isReadOnly("txtbx_FinancialYear", false);
//			action.isReadOnly("txtbx_OriginalJobPlan", false);
			action.isReadOnly("txtbx_JobPlanRevision", true);
			action.isReadOnly("txtbx_Worktype", false);
			action.isReadOnly("txtbx_ActivityType", false);
			action.isReadOnly("txtbx_Discipline", false);
			action.isReadOnly("txtbx_AssetNum", false);
			action.isReadOnly("txtbx_StartRefPoint", false);
			action.isReadOnly("txtbx_EndRefPoint", false);
			
			action.click("lnk_ChangeStatus");
			action.click("dropdown_ChangeStatus");
			action.checkStatusExists("Canceled, Ready to Plan, Locked", true);
			action.clickCancel();
			
			action.route();
			action.verifyAlert("Track Renewal WO cannot initiate workflow unless status is PLAN");
			action.clickClose();
			
			action.clickListViewTab();
			toSearch = new String[][] {{"txtbx_AssetNum", "1000014"}, {"txtbx_Worktype", "AMREN"}, {"txtbx_Status", "RTP"}, {"txtbx_Discipline", "TRACK"}};
			action.advancedSearch(toSearch);
			action.clickFirstValueFromList();
			
			action.isReadOnly("txtbx_FinancialYear", false);
			action.isReadOnly("txtbx_Worktype", true);
			action.isReadOnly("txtbx_ActivityType", true);
			action.isReadOnly("txtbx_Discipline", true);
			action.isReadOnly("txtbx_AssetNum", true);
			action.isReadOnly("txtbx_StartRefPoint", true);
			action.isReadOnly("txtbx_EndRefPoint", true);
			
			action.click("lnk_ChangeStatus");
			action.click("dropdown_ChangeStatus");
			action.checkStatusExists("Planned, Create, Canceled", true);
			action.clickCancel();
			
			action.route();
			action.verifyAlert("Track Renewal WO cannot initiate workflow unless status is PLAN");
			action.clickClose();
			
			action.clickListViewTab();
			toSearch = new String[][] {{"txtbx_AssetNum", "1000000"}, {"txtbx_Worktype", "AMREN"}, {"txtbx_Status", "PLAN"}, {"txtbx_Discipline", "TRACK"}};
			action.advancedSearch(toSearch);
			action.clickFirstValueFromList();
			
			action.stopWF();
			action.clear("txtbx_ScheduledFinish");
			action.input("txtbx_ScheduledStart", action.getDate().plusWeeks(3).toString());
			action.input("txtbx_ScheduledFinish", action.getDate().plusWeeks(3).toString());	
			action.save();
			action.click("lnk_SetSchedDate");
						
			action.routeWF("dfa");
			
			action.isReadOnly("txtbx_FinancialYear", true);
			action.isReadOnly("txtbx_Worktype", true);
			action.isReadOnly("txtbx_ActivityType", true);
			action.isReadOnly("txtbx_Discipline", true);
			action.isReadOnly("txtbx_AssetNum", true);
			action.isReadOnly("txtbx_StartRefPoint", true);
			action.isReadOnly("txtbx_EndRefPoint", true);
			
			action.click("lnk_ChangeStatus");
			action.click("dropdown_ChangeStatus");
			action.checkStatusExists("Canceled, Create", true);
			action.clickCancel();
	
	    } catch (Exception e) {
	    	Log.error("Exception testTrackRenewalsAsServiceManager--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    /*
     * Track Renewals Work Order Application changes
     */
//    @Test
    public void testTrackRenewalsConfig() {
    	testCase = "testTrackRenewalsConfig";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("mxeng1");
			action.goToWOPage();
			action.clickNew();
			action.input("txtbx_Description", testCase);
			action.scrollDown("txtbx_JobPlan");
			action.click("btn_JobPlan_chevron");
			action.click("lnk_SelectValue");
			action.isChecked("chkbx_ShowJobPlansForWO", false);
			action.assertValue("txtbx_WOClass", "Work Order");
			action.checkColumnExists("Discipline, Plan Type, Area, Creator", true);
			action.checkColumnExists("Site, Organization", false);
			
			action.clickFirstValueFromList();
			action.isNull("txtbx_JobPlan", false);
			action.isNull("txtbx_JobPlanRevision", false);
//			action.isNull("txtbx_OriginalJobPlan", true);
			action.isNull("txtbx_PlanEstimatedCost", true);
			action.isReadOnly("txtbx_JobPlan", false);
			action.isReadOnly("txtbx_JobPlanRevision", true);
//			action.isReadOnly("txtbx_OriginalJobPlan", true);
			action.isReadOnly("txtbx_PlanEstimatedCost", true);
			action.isReadOnly("txtbx_TargetStart", false);
			action.isReadOnly("txtbx_TargetFinish", false);
			action.isReadOnly("txtbx_ActualStart", false);
			action.isReadOnly("txtbx_ActualFinish", false);
			action.isReadOnly("txtbx_StartNoEarlierThan", false);
			action.isReadOnly("txtbx_FinishNoLaterThan", false);
			
			action.input("txtbx_Discipline", "TRACK");
			action.input("txtbx_Worktype", "CM");
			action.click("txtbx_ActivityType");
			action.input("txtbx_ActivityType", "Destress");
			action.isReadOnly("txtbx_FinancialYear", false);
			action.isRequired("txtbx_FinancialYear", false);
			
			action.click("btn_Priority");
			action.assertValue("lookup_Row1", "1");
			
			action.click("header_Sort");
			action.assertValue("lookup_Row1", "27");
			action.clickCancel();
			
			action.input("txtbx_Worktype", "CAP");
			action.click("txtbx_ActivityType");
			action.input("txtbx_ActivityType", "Destress");
			action.isReadOnly("txtbx_FinancialYear", false);
			action.isRequired("txtbx_FinancialYear", true);
			action.click("btn_Priority");
			action.assertValue("lookup_Row1", "18");

			action.click("header_Sort");
			action.assertValue("lookup_Row1", "27");
			action.clickCancel();

			action.input("txtbx_AssetNum", "1000014");
			action.click("btn_Priority");
			action.clickFirstValueFromList();
			action.click("btn_FinancialYear");
			action.clickFirstValueFromList();
			action.save();
			
			action.changeStatus("ready to plan");
			action.assertValue("txtbx_Status", "RTP");
			action.isReadOnly("txtbx_FinancialYear", false);
			action.isRequired("txtbx_FinancialYear", true);
			action.isReadOnly("txtbx_JobPlanRevision", true);
			action.assertValue("txtbx_JobPlanRevision", "0");
//			action.isReadOnly("txtbx_OriginalJobPlan", false);
//			action.isNull("txtbx_OriginalJobPlan", false);
			action.isReadOnly("txtbx_PlanEstimatedCost", false);
			action.fieldValuesEqual("txtbx_PlanEstimatedCost", "txtbx_Total_Hier_Est_Cost");
			driver.close();
			
			action.openBrowser("Chrome");
			action.login("mxsvcemgr1");
			action.goToWOPage();
			action.clickNew();
			
			action.input("txtbx_Description", testCase);
			action.scrollDown("txtbx_JobPlan");
			action.click("btn_JobPlan_chevron");
			action.click("lnk_SelectValue");
			action.isChecked("chkbx_ShowJobPlansForWO", false);
			action.assertValue("txtbx_WOClass", "Work Order");
			action.checkColumnExists("Discipline, Plan Type, Area, Creator", true);
			action.checkColumnExists("Site, Organization", false);
			
			action.clickFirstValueFromList();
			action.isNull("txtbx_JobPlan", false);
			action.isNull("txtbx_JobPlanRevision", false);
//			action.isNull("txtbx_OriginalJobPlan", true);
			action.isNull("txtbx_PlanEstimatedCost", true);
			action.isReadOnly("txtbx_JobPlan", false);
			action.isReadOnly("txtbx_JobPlanRevision", true);
//			action.isReadOnly("txtbx_OriginalJobPlan", true);
			action.isReadOnly("txtbx_PlanEstimatedCost", true);
			action.isReadOnly("txtbx_TargetStart", false);
			action.isReadOnly("txtbx_TargetFinish", false);
			action.isReadOnly("txtbx_ActualStart", false);
			action.isReadOnly("txtbx_ActualFinish", false);
			action.isReadOnly("txtbx_StartNoEarlierThan", false);
			action.isReadOnly("txtbx_FinishNoLaterThan", false);
			
			action.input("txtbx_Discipline", "TRACK");
			action.input("txtbx_Worktype", "CM");
			action.click("txtbx_ActivityType");
			action.input("txtbx_ActivityType", "Destress");
			action.isReadOnly("txtbx_FinancialYear", false);
			action.isRequired("txtbx_FinancialYear", false);

			action.click("btn_Priority");
			action.assertValue("lookup_Row1", "1");
			
			action.click("header_Sort");
			action.assertValue("lookup_Row1", "27");
			action.clickCancel();
			
			action.input("txtbx_Worktype", "AMREN");
			action.click("txtbx_ActivityType");
			
			action.input("txtbx_ActivityType", "Destress");
			action.isReadOnly("txtbx_FinancialYear", false);
			action.isRequired("txtbx_FinancialYear", true);
	
			action.click("btn_Priority");
			action.assertValue("lookup_Row1", "1");
			
			action.click("header_Sort");
			action.assertValue("lookup_Row1", "27");
			action.clickCancel();
			
			action.input("txtbx_AssetNum", "1000014");
			action.click("btn_Priority");
			action.clickFirstValueFromList();
			action.click("btn_FinancialYear");
			action.clickFirstValueFromList();
			action.save();
			
			action.changeStatus("ready to plan");
			action.assertValue("txtbx_Status", "RTP");
			action.isReadOnly("txtbx_FinancialYear", false);
			action.isRequired("txtbx_FinancialYear", true);
			action.isReadOnly("txtbx_JobPlanRevision", true);
			action.assertValue("txtbx_JobPlanRevision", "0");
//			action.isReadOnly("txtbx_OriginalJobPlan", false);
//			action.isNull("txtbx_OriginalJobPlan", false);
			action.isReadOnly("txtbx_PlanEstimatedCost", false);
			action.fieldValuesEqual("txtbx_PlanEstimatedCost", "txtbx_Total_Hier_Est_Cost");
			driver.close();
			
			action.openBrowser("Chrome");
			action.login("mxeng1");
			action.goToWOPage();
			action.createWO(testCase, "CM");
			action.duplicate();
			Log.info("Populate Scheduled Start date");
			action.input("txtbx_ScheduledStart", action.getDate().plusWeeks(3).toString());
			action.assertValue("txtbx_WONUMDescription", testCase);
			action.save();

	    } catch (Exception e) {
	    	Log.error("Exception testTrackRenewalsConfig--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    /*
     * Track Renewals Work Orders and Job Plans
     */
//    @Test
    public void testTrackRenewalsWOAndJP() {
    	testCase = "testTrackRenewalsWOAndJP";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("mxeng1");
			action.goToWOPage();
			action.clickNew();
			
			action.input("txtbx_Description", testCase);
			action.input("txtbx_AssetNum", "1000014");
			action.input("txtbx_Worktype", "CAP");
			action.click("btn_Priority");
			action.clickFirstValueFromList();
			action.input("txtbx_ActivityType", "Destress");
			action.click("btn_FinancialYear");
			action.clickFirstValueFromList();
			action.scrollDown("txtbx_JobPlan");
			action.click("btn_JobPlan_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_Search_PlanType", "=Eng Renewal");
			action.click("btn_Filter");
			
			action.isEmpty("tbl_SelectValue", false);
			action.input("txtbx_Search_PlanType", "!=Eng Renewal");
			action.click("btn_Filter");
			action.isEmpty("tbl_SelectValue", true);
			action.clickCancel();
			action.save();
			
			action.changeStatus("Ready To Plan");
			action.assertValue("txtbx_Status", "RTP");	
			action.scrollDown("txtbx_JobPlan");
			
			action.click("btn_JobPlan_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_Search_PlanType", "Renewal");
			action.click("btn_Filter");
			action.isEmpty("tbl_SelectValue", false);
			
			action.input("txtbx_Search_PlanType", "=Eng Renewal");
			action.click("btn_Filter");
			action.isEmpty("tbl_SelectValue", false);
			
			action.input("txtbx_Search_PlanType", "=Essential Feature,=Inspection,=Maintenance,=PM,=Process,=Project");
			action.click("btn_Filter");
			action.isEmpty("tbl_SelectValue", true);
			action.clickCancel();
		
			action.changeStatus("Planned");
			action.assertValue("txtbx_Status", "PLAN");	
			action.scrollDown("txtbx_JobPlan");
			
			action.click("btn_JobPlan_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_Search_PlanType", "=Essential Feature,=Inspection,=Maintenance,=PM,=Process,=Project");
			action.click("btn_Filter");
			action.isEmpty("tbl_SelectValue", true);

			action.input("txtbx_Search_PlanType", "=Eng Renewal");
			action.click("btn_Filter");
			action.isEmpty("tbl_SelectValue", false);
			
			action.input("txtbx_Search_PlanType", "=Renewal");
			action.click("btn_Filter");
			action.isEmpty("tbl_SelectValue", false);
			action.clickFirstValueFromList();
			action.save();
		
			action.goToHomePage();
			action.goToJobPlansPage();
			action.clickNew();
			
			action.assertValue("txtbx_Creator", "mxeng1");
			action.isReadOnly("txtbx_Creator", true);
			
			action.input("txtbx_Discipline", "TRACK");
			action.click("txtbx_PlanType");
			action.isRequired("txtbx_PlanType", true);
			
			action.input("txtbx_Discipline", "Signals");
			action.click("txtbx_PlanType");
			action.isRequired("txtbx_PlanType", false);
			action.click("btn_PlanType");
			action.isEmpty("tbl_SelectValue", false);

	    } catch (Exception e) {
	    	Log.error("Exception testTrackRenewalsWOAndJP--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}

}
