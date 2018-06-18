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

public class AssetFunctional extends TestAutomation {
    static String testCase = "";	
    static String testName = "AssetFunctional";
	
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
    
    /*
     * Will test fields and field values
     */
    @Test
    public void generalFunctionalTests() {
    	testCase = "generalFunctionalTests";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
//			Logon as admin
			action.login("maxadmin");
			action.goToAssetPage();
			
			String[][] toSearch = new String[][] {{"txtbx_Status", "PROPOSED"}};
			action.advancedSearch(toSearch);
			action.clickFirstValueFromList();

			action.click("lnk_ChangeStatus");
			action.click("dropdown_ChangeStatus");
			action.checkStatusExists("Disposed, Mothballed, Active, Not Commissioned, Under Construction", true);
			action.clickCancel();
			action.changeStatus("not commissioned");
			
			action.assertValue("txtbx_Status", "NOTCOM");

//			login as non-admin
			driver.close();
			action.openBrowser("Chrome");
			action.login("mxfldeng");
			action.goToAssetPage();
	
			toSearch = new String[][] {{"txtbx_Status", "PROPOSED"}}; //TODO remove String[][] for testing only
			action.advancedSearch(toSearch);
			action.clickFirstValueFromList();
			
			action.click("lnk_ChangeStatus");
			action.click("dropdown_ChangeStatus");
			action.checkStatusExists("Disposed, Mothballed, Not Commissioned, Under Construction", true);
			action.clickCancel();
			action.changeStatus("not commissioned");
			
			action.assertValue("txtbx_Status", "NOTCOM");

			action.clickListViewTab();
			toSearch = new String[][] {{"txtbx_Status", "NOTCOM"}};
			action.advancedSearch(toSearch);
			action.clickFirstValueFromList();
			
			action.click("lnk_ChangeStatus");
			action.click("dropdown_ChangeStatus");
			action.checkStatusExists("Disposed, Mothballed", true);
			action.clickCancel();
			
			action.clickListViewTab();
			toSearch = new String[][] {{"txtbx_Status", "UNDERCON"}};
			action.advancedSearch(toSearch);
			action.clickFirstValueFromList();
			
			action.click("lnk_ChangeStatus");
			action.click("dropdown_ChangeStatus");
			action.checkStatusExists("Disposed, Mothballed, Not Commissioned", true);
			action.clickCancel();

			//create new asset
			action.clickNew();
			action.input("txtbx_Description", testCase);
			action.assertValue("txtbx_Status", "PROPOSED");
			action.isNull("txtbx_BillToCustomer", true);
			action.isNull("txtbx_CustomerName", true);
			action.isNull("txtbx_CustomerPhone", true);
			
			action.click("btn_BillToCustomer_chevron");
			action.click("lnk_SelectValue");
			action.click("lnk_List_Code");
			action.isNull("txtbx_BillToCustomer", false);
			action.isNull("txtbx_CustomerName", false);
			action.isNull("txtbx_CustomerPhone", false);
			action.click("btn_Save");
			action.verifyAlert("BMXAA7998E");
			action.clickOK();
			
			action.click("btn_Line");
			action.rowsPerPage(20);

			action.input("txtbx_Lookup_Desc_Search", "mid");
			action.click("btn_Filter1");
			action.assertValue("lookup_Row1", "MDLND");
			action.clickFirstValueFromList();
			action.assertValue("txtbx_Line", "MDLND");
			action.click("btn_Discipline");
			action.checkValueExists("ELECTRICAL, SIGNALS, STRUCTURES, TELECOMS, TRACK, TRACTION", true);
			
			action.input("txtbx_Lookup_Value_Search", "tele");
			action.click("btn_Filter1");
			action.assertValue("lookup_Row1", "Telecoms");
			
			action.clear("txtbx_Lookup_Value_Search");
			action.input("txtbx_Lookup_Desc_Search", "sign");
			action.click("btn_Filter1");
			action.assertValue("lookup_Row1", "SIGNALS");
			action.clickFirstValueFromList();
			action.assertValue("txtbx_Discipline", "SIGNALS");

			action.click("btn_Area");
			action.checkValueExists("Multiple", true);
			action.clickCancel();
			
			action.click("btn_Region");
			action.checkValueExists("Central, Multiple, Northern, Southern", true);
			
			action.input("txtbx_Lookup_Value_Search", "South");
			action.click("btn_Filter1");
			action.assertValue("lookup_Row1", "Southern");
			action.clickFirstValueFromList();
			action.assertValue("txtbx_Region", "Southern");
			
			action.click("btn_Area");
			action.checkValueExists("Christchurch, Dunedin, Greymouth, Multiple", true);
			
			action.input("txtbx_Lookup_Value_Search", "Christ");
			action.click("btn_Filter1");
			action.assertValue("lookup_Row1", "Christchurch");
			action.clickFirstValueFromList();
			action.assertValue("txtbx_Area", "Christchurch");
			
			action.click("txtbx_Linear");
			action.input("txtbx_LRM", "METRES");
			action.input("txtbx_AssetStartMeasure", "1");
			action.input("txtbx_AssetEndMeasure", "100");
			action.save();
			
			action.click("btn_MeterGroup");
			action.click("lnk_SelectValue");
			action.clickFirstValueFromList();
			action.save();

			action.click("lnk_EnterMeterReadings");
			action.clickViewDetailsRow1();
			action.input("txtbx_NewReading", "1");
			action.click("txtbx_NewReading");
			action.input("txtbx_Inspector", "mxfldeng");
			action.clickOK();
			action.save();
			
			action.clickMetersTab();
			action.clickViewDetailsRow1();
			action.assertValue("txtbx_LastReading", "1");
			action.assertValue("txtbx_LastReadingInspector", "mxfldeng");
			Assert.assertTrue(action.isChecked("chkbx_Active"));
			action.save();
			action.storeValue("txtbx_AssetNum", "ASSETNUM");
			
			Log.info("Change asset status to active");
			driver.close();
			action.openBrowser("Chrome");
			action.login("maxadmin");
			action.goToAssetPage();

			action.quickSearch(action.getStoredValue("ASSETNUM"));
			action.changeStatus("active");
			action.assertValue("txtbx_Status", "ACTIVE");
			
			Log.info("Login as non-admin");
			driver.close();
			action.openBrowser("Chrome");
			action.login("mxfldeng");
			action.goToAssetPage();
			
			action.quickSearch(action.getStoredValue("ASSETNUM"));
			
			action.goToCreateWO();
			action.clickOK();
			
			action.clickSpecificationsTab();
			action.input("txtbx_Classification", "BRIDGE \\ ROAD");
			action.click("txtbx_ClassDescription");
			action.assertValue("txtbx_ClassDescription", "Road bridge");
			Thread.sleep(20000);
			action.save();
			
			action.clickListViewTab();
			action.whereClause("(upper(status) != 'DISPOSE') and (exists (select 1 from maximo.classancestor where ((ancestor = '1134')) and (classstructureid=asset.classstructureid)))");
			action.clickFirstValueFromList();
			action.storeValue("txtbx_Parent", "ASSETNUM");
			
			action.click("lnk_OpenDrilldown");
			action.clickAssetsTab();
			action.click("btn_ShowPathtoTop");
			action.checkTopMostDrilldown("Assets", action.getStoredValue("ASSETNUM"));
			
			action.clickLocationsTab();
			action.click("btn_ShowPathtoTop");
			action.checkTopMostDrilldown("Locations", "KIWIRAIL");
			action.clickCancel();

	    } catch (Exception e) {
	    	Log.error("Exception generalFunctionalTests--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    /*
     * Will test Duplicate/Delete/Associate Users/Custodians functionalities
     */
    @Test
    public void functionalTests() {
    	testCase = "functionalTests";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("maxadmin");
			action.goToAssetPage();
			
			String[][] toSearch = new String[][] {{"txtbx_AssetNum", "1000000"}};
			action.advancedSearch(toSearch);
			
			action.duplicate();
			action.assertValue("txtbx_Description", "NIMT MainL, Wellington - Auckland");
			action.assertValue("txtbx_Status", "PROPOSED");
			action.storeValue("txtbx_AssetNum", "ASSETNUM");
			action.save();
			
			action.clickListViewTab();
			toSearch = new String[][] {{"txtbx_AssetNum", action.getStoredValue("ASSETNUM")}, {"txtbx_Status", "PROPOSED"}};
			action.advancedSearch(toSearch);
			
			action.click("lnk_Delete");
			action.click("btn_Yes");
			
			toSearch = new String[][] {{"txtbx_AssetNum", action.getStoredValue("ASSETNUM")}, {"txtbx_Status", "PROPOSED"}};
			action.advancedSearch(toSearch);
			action.verifyAlert("BMXAA4186E");
			action.clickOK();
			
			toSearch = new String[][] {{"txtbx_AssetNum", "1000000"}, {"txtbx_Status", "ACTIVE"}};
			action.advancedSearch(toSearch);
			action.click("lnk_Delete");
			action.verifyAlert("Asset 1000000 cannot be deleted because it has children");
			action.clickOK();
			
			action.click("lnk_AssociateUsersCustodians");
			action.click("btn_NewRow_AssociateUsersCustodians");
			action.input("txtbx_Person", "mme9310");
			action.click("chkbx_Custodian");
			action.clickOK();
			
			action.click("lnk_AssociateUsersCustodians");
			action.isEmpty("tbl_AssociateUsersCustodians", false);
			action.clickDeleteDetailsRow1();
			action.clickOK();

			action.click("lnk_AssociateUsersCustodians");
			action.click("btn_NewRow_AssociateGroup");
			action.click("btn_Group_chevron");
			action.click("lnk_SelectValue");
			action.click("lnk_List_Code");
			action.clickOK();
			
			action.click("lnk_AssociateUsersCustodians");
			action.isEmpty("tbl_AssociateGroup", false);
			action.clickDeleteDetailsRow1();
			action.clickOK();

	    } catch (Exception e) {
	    	Log.error("Exception functionalTests--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}

}
