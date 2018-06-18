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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import executionEngine.Base;
import executionEngine.TestAutomation;
import utility.Log;

public class AssetPageConfigurations extends TestAutomation {
	String testName = "AssetPageConfigurations Tests";
    static String testCase = "";	
	
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
    public void loginAsAreaManager() {
    	testCase = "loginAsAreaManager";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("mxareamgr1");
			action.goToAssetPage();
			
			String[][] toSearch = new String[][] {{"txtbx_Status", "UNDERCON"}};
			action.advancedSearch(toSearch);
			action.clickFirstValueFromList();
			
			action.click("lnk_ChangeStatus");
			action.click("dropdown_ChangeStatus");
			action.checkStatusExists("Disposed, Mothballed, Not Commissioned", true);
			action.checkStatusExists("Active", false);
			action.clickCancel();
			
			action.clickListViewTab();
			toSearch = new String[][] {{"txtbx_Status", "NOTCOM"}};
			action.advancedSearch(toSearch);
			action.clickFirstValueFromList();
			
			action.click("lnk_ChangeStatus");
			action.click("dropdown_ChangeStatus");
			action.checkStatusExists("Disposed, Mothballed", true);
			action.checkStatusExists("Active", false);
			action.clickCancel();
	    } catch (Exception e) {
	    	Log.error("Exception loginAsAreaManager--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    
    @Test
    public void loginAsEngineer() {
    	testCase = "loginAsEngineer";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("mxeng1");
			action.goToAssetPage();

			action.click("btn_Advanced_Search");
			action.click("btn_Discipline");
			action.checkValueExists("ELECTRICAL, SIGNALS, STRUCTURES, TELECOMS, TRACK, TRACTION", true);
			action.clickOK();
			
			action.click("btn_Area");
			action.checkValueExists("Auckland Metro, Greater Auckland, East & West, Christchurch, Dunedin, Greymouth, Hamilton East, Hamilton South, Multiple, Palmerston North, Wellington", true);
			action.clickOK();
			
			action.click("btn_Status");
			action.checkValueExists("ACTIVE, DISPOSE, MOTHBALL, NOTCOM, PROPOSED, UNDERCON, IMPORTED", true);
			action.clickOK();
			action.clickCancel();
			
			action.clickNew();
			action.click("btn_Save");
			action.verifyAlert("BMXAA7998E");
			action.clickOK();
			
			action.click("btn_Discipline");
			action.checkValueExists("ELECTRICAL, SIGNALS, STRUCTURES, TELECOMS, TRACK, TRACTION", true);
			action.clickCancel();
			
			action.click("btn_Region");
			action.checkValueExists("Central, Northern, Southern, Multiple", true);
			action.clickCancel();
			
			action.click("btn_Area");
			action.checkValueExists("Multiple", true);
			action.clickCancel();
			
			action.clickSubAssembliesTab();
			action.isReadOnly("txtbx_Parent", true);
			action.isReadOnly("txtbx_AssetNum", true);

			action.clickMetersTab();
			action.isReadOnly("txtbx_MeterGroup", false);
			action.click("btn_MeterGroup_chevron");
			action.click("lnk_SelectValue");
			action.isEmpty("tbl_SelectValue", false);
			action.input("txtbx_Search_Code", "ANTENNA");
			action.click("btn_Filter");
			action.clickFirstValueFromList();
			action.assertValue("txtbx_MeterGroup", "ANTENNA");
			
			action.clickNewRow();
			action.isReadOnly("txtbx_Meter", false);
			action.isReadOnly("txtbx_MeterType", true);
			action.isRequired("txtbx_Meter", true);
			action.isReadOnly("txtbx_Point", true);
			action.isReadOnly("txtbx_LastReading", false);
			action.isReadOnly("txtbx_LastReadingDate", false);
			action.isReadOnly("txtbx_LastReadingInspector", false);
			action.isChecked("chkbx_Active", true);
			action.isReadOnly("chkbx_Active", false);

			action.click("btn_Meter_chevron");
			action.click("lnk_SelectValue");
			action.isEmpty("tbl_SelectValue", false);
			action.input("txtbx_Search_Code", "ABUTMENTS");
			action.click("btn_Filter");
			action.clickFirstValueFromList();
				
			action.click("btn_LastReading");
			action.clickFirstValueFromList();
			action.assertValue("txtbx_LastReading", "1");
			action.isNull("txtbx_LastReadingDate", false);
			action.assertValue("txtbx_LastReadingInspector", "MXENG1");

			action.click("btn_LastReadingInspector_chevron");
			action.click("lnk_SelectValue");
			action.isEmpty("tbl_SelectValue", false);
			
			action.input("txtbx_Lookup_Value_Search", "MME9310");
			action.click("btn_Filter");
			action.clickFirstValueFromList();
			action.assertValue("txtbx_LastReadingInspector", "MME9310");

			action.clickSafetyTab();
			action.clickFeaturesTab();
			action.clickRelationshipsTab();
			action.clickSpecificationsTab();
			action.isReadOnly("txtbx_Classification", false);
			action.isReadOnly("txtbx_ClassDescription", false);
			
			action.click("btn_Classification_chevron");
			action.click("lnk_Classify");
			action.click("tree_Classification_ACFilter");
			action.assertValue("txtbx_Classification", "ACFILTER");
			action.assertValue("txtbx_ClassDescription", "AC Filter");
			action.isEmpty("tbl_Specifications", false);
			action.assertValue("txtbx_Related_WO_row1", "ACFILTERTYPE");
			
			action.click("btn_ClassDescription");
			action.input("txtbx_Search_Code", "bridge");
			action.click("btn_Filter");
			action.clickFirstValueFromList();
			action.assertValue("txtbx_ClassDescription", "BRIDGE");
			action.clickRelatedLocationsTab();
			driver.close();

			Log.info("Add new asset");
			action.openBrowser("Chrome");
			action.login("mxeng1");
			action.goToAssetPage();
			
			action.clickNew();
			
			action.isNull("txtbx_BillToCustomer", true);
			action.isNull("txtbx_CustomerName", true);
			action.isNull("txtbx_CustomerPhone", true);
			
			action.click("btn_BillToCustomer_chevron");
			action.click("lnk_SelectValue");
			action.clickFirstValueFromList();
			
			action.isNull("txtbx_BillToCustomer", false);
			action.isNull("txtbx_CustomerName", false);
			
			action.isReadOnly("txtbx_CustomerName", true);
			action.isReadOnly("txtbx_CustomerPhone", true);
			
	    } catch (Exception e) {
	    	Log.error("Exception loginAsEngineer--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @DataProvider
 	public Object[][] asset() {
 		return new Object[][] { { "maxadmin", "1000067", true}, 
 								{"maxadmin", "2000017", false} };
 	}
    
    @Test(dataProvider = "asset")
    public void loginAsMaxadmin(String user, String asset, boolean isLinear) {
    	testCase = "loginAsMaxadmin-- Asset is Linear? "+isLinear;
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login(user);
			action.goToAssetPage();
			
			action.checkColumnExists1("Asset, Name, Classification, Location, Name, Area, Line, Start Metrage, End Metrage, Discipline, Status", true);
			action.checkColumnExists1("Description, Loop Location, Parent, Is M&TE, Calibration", false);

			String[][] toSearch = new String[][] {{"txtbx_AssetNum", asset}};
			action.advancedSearch(toSearch);
			action.fieldExists("Discipline", true);
			action.fieldExists("Area", true);
			action.fieldExists("Region", true);
			action.fieldExists("Line", true);
			action.fieldExists("Status", true);
			action.fieldExists("Asset Template", true);
			action.fieldExists("Recoverable", true);
			action.fieldExists("Customer Phone", true);
			action.fieldExists("Customer Name", true);
			action.fieldExists("Bill To Customer #", true);
			action.fieldExists("Start Metrage", true);
			action.fieldExists("End Metrage", true);
			action.fieldExists("Offset Rounded", true);
			action.fieldExists("Label for Corridor Log", true);
			
			action.fieldExists("Linear", true);
			action.fieldExists("Site", true);
			action.fieldExists("Type", true);
			action.fieldExists("Parent", true);
			action.fieldExists("Location", true);
			action.fieldExists("Failure Class", true);
			action.fieldExists("GL Account", true);
			action.fieldExists("GIS Layer", true);
			action.fieldExists("Meter Group", true);
			action.fieldExists("Asset Up", true);
			action.fieldExists("Changed By", true);
			action.fieldExists("Changed Date", true);
			action.fieldExists("Site", true);
			action.fieldExists("Common Name", true);
			
			if (isLinear) {
				action.fieldExists("Direction", true);
				action.fieldExists("LRM", true);
			}
		
			action.clickMetersTab();
			if (isLinear) {
				action.checkTableColumnExists("Sequence, Meter, Description, Meter Type, Unit of Measure, Start Reference Point, End Reference Point, End Reference Point Offset, Active?, Start Reference Point Offset", "Meters");
				action.fieldExists("From", true);
				action.fieldExists("To", true);
			} else {
				action.checkTableColumnExists("Sequence, Meter, Description, Meter Type, Unit of Measure", "Meters");
				action.checkTableColumnNotExists("Start Reference Point, End Reference Point, End Reference Point Offset, Start Reference Point Offset", "Meters");
				action.fieldExists("From", false);
				action.fieldExists("To", false);
			}
			action.elementExists("btn_NewRow", true);
			action.fieldExists("Asset", true);
			action.fieldExists("Meter Group", true);
			
			action.clickSpecificationsTab();
			if (isLinear) {
				action.checkTableColumnExists("Attribute, Description, Section, Data Type, Alphanumeric Value, Numeric Value, Unit of Measure, Table Value, Start Reference Point, Start Reference Point Offset, End Reference Point, End Reference Point Offset", "Specifications");
				action.fieldExists("To", true);
				action.fieldExists("From", true);
			} else {
				action.checkTableColumnExists("Attribute, Description, Section, Data Type, Alphanumeric Value, Numeric Value, Unit of Measure, Table Value", "Specifications");
				action.checkTableColumnNotExists("Start Reference Point, Start Reference Point Offset, End Reference Point, End Reference Point Offset", "Specifications");
				action.fieldExists("To", false);
				action.fieldExists("From",false);
			}
			action.elementExists("btn_NewRow", true);
			action.fieldExists("Asset", true);
			action.fieldExists("Classification", true);
			action.fieldExists("Class Description", true);
			
			action.clickFeaturesTab();
			if (isLinear) {
				action.checkTableColumnExists("Feature, Description, Label, Type, Start Reference Point, Start Reference Point Offset, End Reference Point, End Reference Point Offset", "Features");
				action.elementExists("radio_KMPost", true);
				action.elementExists("radio_AbsoluteMeasure", true);
				action.fieldExists("Asset", true);
				action.fieldExists("From", true);
				action.fieldExists("To", true);
			} else {
				action.checkTableColumnExists("Feature, Description, Label, Type, Start Reference Point, Start Reference Point Offset, End Reference Point, End Reference Point Offset", "Features");
				action.checkTableColumnExists("Attribute, Description, Section, Data Type, Alphanumeric Value, Numeric Value, Unit of Measure", "Attributes");
			}
			action.elementExists("btn_NewRow", true);
						
			action.clickRelationshipsTab();
			if (isLinear) {
				action.checkTableColumnExists("Sequence, Relationship, Source Asset, Source Asset Description, Target Asset, Target Asset Description, Start Ref Point, Start Offset, End Ref Point, End Offset, Source Location, Target Location", "Relationships");
				action.elementExists("radio_KMPost", true);
				action.elementExists("radio_AbsoluteMeasure", true);
				action.fieldExists("From", true);
				action.fieldExists("To", true);
			} else {
				action.checkTableColumnExists("Sequence, Relationship, Source Asset, Source Asset Description, Target Asset, Target Asset Description, Start Ref Point, Start Offset, End Ref Point, End Offset, Source Location, Target Location", "Relationships");
			}
			action.elementExists("btn_NewRow", true);
			action.fieldExists("Asset", true);

			action.clickRelatedLocationsTab();
			if (isLinear) {
				action.checkTableColumnExists("Sequence, Relationship, Source Location, Name, Target Location, Name, Start Ref Point, Start Offset, End Ref Point, End Offset", "Related Locations");
				action.elementExists("radio_KMPost", true);
				action.elementExists("radio_AbsoluteMeasure", true);
				action.fieldExists("From", true);
				action.fieldExists("To", true);
			} else {
				action.checkTableColumnExists("Sequence, Relationship, Source Location, Name, Target Location, Name, Start Ref Point, Start Offset, End Ref Point, End Offset", "Related Locations");
			}
			action.elementExists("btn_NewRow", true);
			action.fieldExists("Asset", true);
	    } catch (Exception e) {
	    	Log.error("Exception loginAsMaxadmin--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}

}
