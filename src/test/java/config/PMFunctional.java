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

public class PMFunctional extends TestAutomation {
	static boolean runStatus;
    static String testCase = "";	
    static String testName = "PMFunctional";
	
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
     * Create Route
     */
    @Test(alwaysRun = true)
    public void createRoute() {
    	testCase = "createRoute";
    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			Log.info("Start "+testCase);
			action.login("maxadmin");
			action.goToRoutesPage();

			action.clickNew();
			action.input("txtbx_Description", testCase);
			action.assertValue("txtbx_Site", "NETWORK");
			action.isTicked("radio_ChildWorkOrders", true);
			action.isChecked("chkbx_RouteStopsInheritStatusChanges", true);
			
			action.clickNewRow();
			action.click("btn_Asset_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_Search_Area", "=Dunedin");
			action.enter("txtbx_Search_Area");
			action.clickFirstValueFromList();
			action.input("txtbx_Description2", "Route Stop 1");
			action.save();
			action.storeValue("txtbx_Route", "RouteNum");
			action.storeValue("txtbx_AssetNum", "ASSETNUM");

			Log.info("END "+testCase);
	    } catch (Exception e) {
	    	Log.error("Exception createRoute--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    /*
     * Create Job Plan
     */
    @Test(alwaysRun = true)
    public void createJobPlan() {
    	testCase = "createJobPlan";
    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			Log.info("Start "+testCase);
			action.login("maxadmin");
			action.goToJobPlansPage();
			
			action.clickNew();
			action.input("txtbx_Description", testCase);
			action.assertValue("txtbx_Status", "DRAFT");
			action.assertValue("txtbx_Site", "NETWORK");
			action.assertValue("txtbx_Organization", "KIWIRAIL");
			
			action.input("txtbx_PlanType", "PM");
			action.input("txtbx_Discipline", "TRACK");
			action.input("txtbx_Area", "DUNEDIN");
			
			action.click("btn_NewRow_Labour");
			action.input("txtbx_Trade", "LEVEL1");
			action.input("txtbx_Quantity", "2");
			action.input("txtbx_Hours", "2");
			action.save();
			
			action.assertValue("txtbx_Labour_Rate", "35.00");
			action.assertValue("txtbx_LineCost", "140.00");
			
			action.clickMaterialsTab();
			action.click("btn_NewRow_Materials");
			action.input("txtbx_Item", "1028752");
			action.getStoreroom();
			
			action.clickServicesTab();
			action.click("btn_NewRow_Services");
			action.input("txtbx_CommodityGroup", "1000SVC");
			action.input("txtbx_CommodityCode", "7000");
			action.input("txtbx_Vendor", "INTERNAL");
			action.input("txtbx_ServiceItem", "1001");
			action.save();
			
			action.changeStatus("active");
			action.assertValue("txtbx_Status", "Active");
			
			action.storeValue("txtbx_JobPlan", "JPNUM");
			Log.info("END "+testCase);
	    } catch (Exception e) {
	    	Log.error("Exception createJobPlan--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    /*
     * CR90_PM_MXFLDENG
     */
    @Test
    public void cr90PM() {
    	testCase = "cr90PM";
    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			Log.info("Start "+testCase);
			action.login("maxadmin");
			action.goToPMPage();
			
			String[][] toSearch = new String[][] {{"txtbx_Status", "=ACTIVE"}};
			action.advancedSearch(toSearch);
			action.clickFirstValueFromList();
			
			action.assertValue("txtbx_Status", "Active");
			action.isNull("txtbx_Worktype", false);
			action.isNull("txtbx_ActivityType", false);
			action.clear("txtbx_Worktype");
			action.clear("txtbx_ActivityType");
			action.isNull("txtbx_ActivityType", true);
			
			action.clickListViewTab();
			action.clickNo();
			
			toSearch = new String[][] {{"txtbx_Status", "=ACTIVE"}};
			action.advancedSearch(toSearch);
			action.clickFirstValueFromList();
			
			action.isNull("txtbx_Discipline", false);
			action.isNull("txtbx_ActivityType", false);
			action.clear("txtbx_Discipline");
			action.click("txtbx_ActivityType");
			action.isNull("txtbx_ActivityType", true);
	
			Log.info("END "+testCase);
	    } catch (Exception e) {
	    	Log.error("Exception cr90PM--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    /*
     * Generate WO from PM and PM generation with asset and jp
     */
    @Test(dependsOnMethods= {"createRoute", "createJobPlan"})
    public void generateWOfromPM() {
    	testCase = "generateWOfromPM";
    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			Log.info("Start "+testCase);
			Log.info("Create new PM>>>>>>>>>>>");
			action.login("maxadmin");
			action.goToPMPage();
			
			action.clickNew();
			action.assertValue("txtbx_Status", "DRAFT");
			action.assertValue("txtbx_Site", "NETWORK");
			action.isRequired("txtbx_ActivityType", true);
			action.isRequired("txtbx_PMNUM", true);
			action.isRequired("txtbx_Region", true);
			action.isRequired("txtbx_Area", true);
			action.isRequired("txtbx_Line", true);
			action.isRequired("txtbx_Worktype", true);
			action.isRequired("txtbx_Priority", true);
			action.isRequired("txtbx_GLAccount", true);
			action.input("txtbx_Description", testCase);
			action.input("txtbx_Worktype", "INSP");
			action.input("txtbx_Priority", "3");
			action.input("txtbx_GLAccount", "1000-K-21000");
			action.input("txtbx_AssetNum", "1000057");
			
			action.click("txtbx_Discipline");
			action.isNull("txtbx_Region", false);
			action.isNull("txtbx_Area", false);
			action.isNull("txtbx_Line", false);
			
			action.retrieveValue("txtbx_JobPlan", "JPNUM");
			action.retrieveValue("txtbx_Route", "RouteNum");
			action.input("txtbx_ActivityType", "Drainage");
			action.save();
			
			action.changeStatus("active");
			action.assertValue("txtbx_Discipline", "TRACK");
			action.assertValue("txtbx_Region", "Southern");
			action.assertValue("txtbx_Area", "Dunedin");
			action.assertValue("txtbx_Line", "PTCHS");

			action.click("lnk_GenerateWOs");
			action.click("chkbx_UseFrequencyCriteria");
			action.clickOK();
			
			action.waitElementExists("systemMessage");
			action.captureSystemMessage("mb_msg", "msgboxcapturedtextlog");
			action.clickClose();;
			action.storeValue("txtbx_PMNUM", "PMNUM");
			
			Log.info("User PM in WO>>>>>>>>>>>>>>>>>>");
			driver.close();
			action.openBrowser("Chrome");
			action.login("mxfldeng");
			action.goToWOPage();
			
			String[][] toSearch = new String[][] {{"txtbx_PMNUM", action.getStoredValue("PMNUM")}};
			action.advancedSearch(toSearch);

			action.assertValue("txtbx_Status", "WMATL; APPR");
			action.assertValue("txtbx_Worktype", "INSP");
			action.isNull("txtbx_ActivityType", false);
			action.assertValue("txtbx_Discipline", "TRACK");
			action.assertValue("txtbx_Region", "Southern");
			action.assertValue("txtbx_Area", "Dunedin");
			action.assertValue("txtbx_Line", "PTCHS");
			action.isNull("txtbx_JobPlan", false);
			action.isNull("txtbx_GLAccount", false);
			action.isNull("txtbx_Priority", false);
			action.assertValue("txtbx_AssetNum", "1000057");
			action.isNull("txtbx_TargetStart", false);
			action.isNull("txtbx_TargetFinish", false);
			
			action.clickPlansTab();
			action.expectedRows("Children", "1");
			action.expectedRows("Tasks", "0");
			action.expectedRows("Labour", "1");
			
			action.clickMaterialsTab();
			action.expectedRows("Materials", "1");

			action.clickServicesTab();
			action.expectedRows("Services", "1");
			
			action.clickToolsTab();
			action.expectedRows("Tools", "0");

			Log.info("Check generated child/task WO from Route");
			action.clickViewDetailsRow1();
			action.click("btn_Record_chevron");
			action.click("lnk_WO");

			action.assertValue("txtbx_Status", "WMATL; APPR");
			action.assertValue("txtbx_Worktype", "INSP");
			
			
			action.assertValue("txtbx_Status", "WMATL; APPR");
			action.assertValue("txtbx_Worktype", "INSP");
			action.assertValue("txtbx_Discipline", "TRACK");
			action.assertValue("txtbx_Region", "Southern");
			action.assertValue("txtbx_Area", "Dunedin");
			action.assertValue("txtbx_Line", "PTCHS");
			action.assertValue("txtbx_AssetNum", action.getStoredValue("ASSETNUM"));
			
			action.isNull("txtbx_ActivityType", false);
			action.isNull("txtbx_JobPlan", false);
			action.isNull("txtbx_GLAccount", false);
			action.isNull("txtbx_Priority", false);
			action.isNull("txtbx_TargetStart", false);
			action.isNull("txtbx_TargetFinish", false);
			action.isNull("txtbx_ParentWO", false);
			
			Log.info("END "+testCase);
	    } catch (Exception e) {
	    	Log.error("Exception generateWOfromPM--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    //PM generation with location
    @Test(dependsOnMethods= {"createRoute", "createJobPlan"})
    public void generatePMwithLocation() {
    	testCase = "generatePMwithLocation";
    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			Log.info("Start "+testCase);
			action.login("maxadmin");
			action.goToPMPage();

			action.clickNew();
			
			action.assertValue("txtbx_Status", "DRAFT");
			action.assertValue("txtbx_Site", "NETWORK");
			
			action.isRequired("txtbx_ActivityType", true);
			action.isRequired("txtbx_PMNUM", true);
			action.isRequired("txtbx_Discipline", true);
			action.isRequired("txtbx_Region", true);
			action.isRequired("txtbx_Area", true);
			action.isRequired("txtbx_Line", true);
			action.isRequired("txtbx_Worktype", true);
			action.isRequired("txtbx_Priority", true);
			action.isRequired("txtbx_GLAccount", true);
			action.input("txtbx_Description", testCase);
			action.input("txtbx_Worktype", "INSP");
			action.input("txtbx_Priority", "3");
			action.input("txtbx_GLAccount", "1000-K-21000");
			action.click("btn_Location_chevron");
			action.click("lnk_SelectValue");
			action.input("txtbx_Search_Code", "ABBOTSFORD");
			action.click("btn_Filter1");
			action.clickFirstValueFromList();
			
			action.assertValue("txtbx_Location", "9000000");
			action.storeValue("txtbx_Location", "LOCATION");
			
			action.click("txtbx_Discipline");
			action.isNull("txtbx_Discipline", false);
			action.isNull("txtbx_Region", false);
			action.isNull("txtbx_Area", false);
			action.isNull("txtbx_Line", false);
			action.retrieveValue("txtbx_JobPlan", "JPNUM");
			action.retrieveValue("txtbx_Route", "RouteNum");
			action.input("txtbx_ActivityType", "Drainage");
			action.save();

			action.changeStatus("active");
			action.assertValue("txtbx_Discipline", "TRACK");
			action.assertValue("txtbx_Region", "Southern");
			action.assertValue("txtbx_Area", "Dunedin");
			action.assertValue("txtbx_Line", "MSL");
			
			action.click("lnk_GenerateWOs");
			action.click("chkbx_UseFrequencyCriteria");
			action.click("btn_OK");
			
			action.waitElementExists("systemMessage");
			action.captureSystemMessage("mb_msg", "msgboxcapturedtextlog");
			action.clickClose();
			
			action.storeValue("txtbx_PMNUM", "PMNUM");
			driver.close();
			
			Log.info("Verify fields as non-admin");
			action.openBrowser("Chrome");
			action.login("mxfldeng");
			action.goToWOPage();
			
			String[][] toSearch = new String[][] {{"txtbx_PMNUM", action.getStoredValue("PMNUM")}, 
												  {"txtbx_Location", "9000000"}};
			action.advancedSearch(toSearch);

			action.assertValue("txtbx_Status", "WMATL; APPR");
			action.assertValue("txtbx_Worktype", "INSP");
			action.isNull("txtbx_ActivityType", false);	
			
			action.assertValue("txtbx_Discipline", "TRACK");
			action.assertValue("txtbx_Region", "Southern");
			action.assertValue("txtbx_Area", "Dunedin");
			action.assertValue("txtbx_Line", "MSL");
			action.isNull("txtbx_JobPlan", false);	
			action.isNull("txtbx_GLAccount", false);	
			action.isNull("txtbx_Priority", false);	
			action.isNull("txtbx_AssetNum", true);	
			action.assertValue("txtbx_WOHierarchyTotalEstCost", "6295.28");
			action.isNull("txtbx_TargetStart", false);	
			action.isNull("txtbx_TargetFinish", false);	
			
			action.click("tab_Plans");
			action.expectedRows("Children", "1");
			action.expectedRows("Tasks", "0");
			action.expectedRows("Labour", "1");
			
			action.click("tab_Materials");
			action.expectedRows("Materials", "1");
			
			action.click("tab_Services");
			action.expectedRows("Services", "1");
			
			action.click("tab_Tools");
			action.expectedRows("Tools", "0");
			
			Log.info("Check generated child/task WO from Route");
			action.clickViewDetailsRow1();
			action.click("btn_Record_chevron");
			action.click("lnk_WO");

			action.assertValue("txtbx_Status", "WMATL; APPR");
			action.assertValue("txtbx_Worktype", "INSP");
			
			action.isNull("txtbx_ActivityType", false);
			action.assertValue("txtbx_Discipline", "TRACK");
			action.assertValue("txtbx_Region", "Southern");
			action.assertValue("txtbx_Area", "Dunedin");
			action.assertValue("txtbx_Line", "PTCHS");
	
			action.isNull("txtbx_Line", false);
			action.isNull("txtbx_JobPlan", false);
			action.isNull("txtbx_GLAccount", false);
			action.isNull("txtbx_Priority", false);
			action.isNull("txtbx_Location", true);
			
			action.assertValue("txtbx_AssetNum", action.getStoredValue("ASSETNUM"));
			action.assertValue("txtbx_WOHierarchyTotalEstCost", "3147.64");
			
			action.isNull("txtbx_TargetStart", false);
			action.isNull("txtbx_TargetFinish", false);
			action.isNull("txtbx_ParentWO", false);
			
			action.clickReturn();
			
			Log.info("END "+testCase);
	    } catch (Exception e) {
	    	Log.error("Exception generatePMwithLocation--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    
//   TODO: PM generation with route and jp, send to mobile and assign to owner from pm 
//    @Test
    public void dum() {
    	testCase = "createPRwithValidWO";
    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			Log.info("Start "+testCase);
			action.login("mxfldeng");
			action.goToPRPage();
			
			
			
			Log.info("END "+testCase);
	    } catch (Exception e) {
	    	Log.error("Exception createPRwithValidWO--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}

}
