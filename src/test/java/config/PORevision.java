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

public class PORevision extends TestAutomation {
    static String testCase = "";	
    static String testName = "PORevision";
	
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
    
    @Test
    public void cancelPOinPNDREVStatus() {
    	testCase = "cancelPOinPNDREVStatus";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("maxadmin");
			action.goToPOPage();
			
			String toSearch = "(status = 'APPR' and ponum like '48%' and historyflag = 0 and revisionnum=0 and statusdate >=  '2015-05-14' and receipts = 'NONE') and ponum not in (select ponum from po where revisionnum > 0) and (exists (select 1 from maximo.poline where ((linetype = 'SERVICE')) and (ponum=po.ponum and revisionnum=po.revisionnum and siteid=po.siteid)))";
			action.whereClause(toSearch);
			action.clickFirstValueFromList();
			action.storeValue("txtbx_PONUM", "PONUM");

			action.click("lnk_RevisePO");
			action.input("txtbx_PODescription2", testCase);
			action.clickOK();
				
			action.assertValue("txtbx_Status", "PNDREV");
			action.assertValue("txtbx_Revision", "1");
			
			action.quickSearch(action.getStoredValue("PONUM"));
			action.clickListViewTab();
			action.rowsDisplayed(2);
			
			action.advancedSearch(new String[][] {{"txtbx_PONUM", action.getStoredValue("PONUM")}, {"txtbx_Status", "PNDREV"}});
//			action.clickFirstSearchedValue();
			
			action.clickPOLinesTab();
			action.clickNewRow();
			action.click("btn_Item_chevron");
	 		action.click("lnk_SelectValue");
	 		action.clickFirstValueFromList();
	 		
	 		action.input("txtbx_OrderUnit", "EA");
	 		action.input("txtbx_UnitCost", "1000");
	 		action.input("txtbx_RequiredDate", action.getDate().plusWeeks(3).toString());
	 		
	 		if (action.isReadOnly("txtbx_ConversionFactor") == false) {
 	 			 action.input("txtbx_ConversionFactor", "1");
 	 		}
	 		action.save();
	 		
	 		action.changeStatus("canceled");
			action.assertValue("txtbx_Status", "CAN");
			action.logMessage("txtbx_PONUM", "Created revision 1 and added new PO line with unit cost=1000.	"
					+ "Cancelled revision 1."
					+ "Verify with SAP that the added PO has not gone through.");

	    } catch (Exception e) {
	    	Log.error("Exception cancelPOinPNDREVStatus--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test
    public void cancelRevisedPOinAPPRStatus() {
    	testCase = "cancelRevisedPOinAPPRStatus";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("maxadmin");			
			action.goToPOPage();
			
			String toSearch = "(status = 'APPR' and ponum like '48%' and historyflag = 0 and revisionnum=0 and statusdate >=  '2015-05-14' and receipts = 'NONE') and ponum not in (select ponum from po where revisionnum > 0) and (exists (select 1 from maximo.poline where ((linetype = 'SERVICE')) and (ponum=po.ponum and revisionnum=po.revisionnum and siteid=po.siteid)))";
			action.whereClause(toSearch);
			action.clickFirstValueFromList();
			action.storeValue("txtbx_PONUM", "PONUM");
			
			action.click("lnk_RevisePO");
			action.input("txtbx_PODescription2", testCase);
			action.clickOK();
				
			action.assertValue("txtbx_Status", "PNDREV");
			action.assertValue("txtbx_Revision", "1");
			
			action.clickListViewTab();
			action.quickSearch(action.getStoredValue("PONUM"));
			action.clickListViewTab();
			action.rowsDisplayed(2);
			
			action.advancedSearch(new String[][] {{"txtbx_PONUM", action.getStoredValue("PONUM")}, {"txtbx_Status", "APPR"}});
//			action.clickFirstSearchedValue();
			action.assertValue("txtbx_PONUM", action.getStoredValue("PONUM"));
			action.assertValue("txtbx_Status", "APPR");

			action.clickListViewTab();
//			action.clearWhereClause();
			action.advancedSearch(new String[][] {{"txtbx_PONUM", action.getStoredValue("PONUM")}, {"txtbx_Status", "PNDREV"}});
			
			action.clickPOLinesTab();
			action.clickNewRow();
			action.click("btn_Item_chevron");
	 		action.click("lnk_SelectValue");
	 		action.clickFirstValueFromList();
	 		
	 		action.input("txtbx_OrderUnit", "EA");
	 		action.input("txtbx_UnitCost", "1000");
	 		action.input("txtbx_RequiredDate", action.getDate().plusWeeks(3).toString());
	 		
	 		if (action.isReadOnly("txtbx_ConversionFactor") == false) {
 	 			 action.input("txtbx_ConversionFactor", "1");
 	 		}
	 		action.save();
	 		
	 		action.changeStatus("approved");
			action.assertValue("txtbx_Status", "APPR");
			action.logMessage("txtbx_PONUM", "Created revision 1 and added new PO line with unit cost=1000.	"
					+ "Cancelled revision 1."
					+ "Verify with SAP that the added PO has not gone through.");

			action.clickListViewTab();
			action.quickSearch(action.getStoredValue("PONUM"));
			action.clickListViewTab();
			action.rowsDisplayed(2);

			action.clickListViewTab();
			action.clearWhereClause();
			action.advancedSearch(new String[][] {{"txtbx_PONUM", action.getStoredValue("PONUM")}, {"txtbx_Status", "REVISD"}});
		
			action.assertValue("txtbx_PONUM", action.getStoredValue("PONUM"));
			action.assertValue("txtbx_Status", "REVISD");

			action.clickListViewTab();
			action.clearWhereClause();
			action.advancedSearch(new String[][] {{"txtbx_PONUM", action.getStoredValue("PONUM")}, {"txtbx_Status", "APPR"}});
		
			action.changeStatus("canceled");
			action.assertValue("txtbx_Status", "CAN");

			action.clickListViewTab();
			action.quickSearch(action.getStoredValue("PONUM"));
			action.clickListViewTab();
			action.rowsDisplayed(2);
			
			action.clickListViewTab();
			action.clearWhereClause();
			action.advancedSearch(new String[][] {{"txtbx_PONUM", action.getStoredValue("PONUM")}, {"txtbx_Status", "REVISD"}});
		
			action.assertValue("txtbx_PONUM", action.getStoredValue("PONUM"));
			action.assertValue("txtbx_Status", "REVISD");
			
			action.clickListViewTab();
			action.clearWhereClause();
			action.advancedSearch(new String[][] {{"txtbx_PONUM", action.getStoredValue("PONUM")}, {"txtbx_Status", "CAN"}});
		
			action.assertValue("txtbx_PONUM", action.getStoredValue("PONUM"));
			action.assertValue("txtbx_Status", "CAN");

	    } catch (Exception e) {
	    	Log.error("Exception cancelRevisedPOinAPPRStatus--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    /*
     * PO Revision with less than 20% cost change should be auto-approved when routed
     */
    @Test
    public void revisePOwithCostChange() {
    	testCase = "revisePOwithCostChange";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  
			action.login("maxadmin");
			action.goToPOPage();
			
			Log.info("Less than 20% cost change by increasing quantity>>>>>>>>>>>>>>>>>>>>>>>>>>");
			String toSearch = "(status = 'APPR' and ponum like '48%' and historyflag = 0 and REVISIONNUM=0 and totalcost = 500.0 and statusdate >=  '2015-05-14'  and receipts = 'NONE') "
					+ "and ponum not in (select ponum from po where revisionnum > 0) and ponum in (select ponum from poline where linetype in ('SERVICE', 'ITEM'))";
			action.whereClause(toSearch);
			action.clickFirstValueFromList();
			action.storeValue("txtbx_PONUM", "PONUM");
			
			action.click("lnk_RevisePO");
			action.input("txtbx_PODescription2", testCase);
			action.clickOK();
				
			action.assertValue("txtbx_Status", "PNDREV");
			
			action.clickPOLinesTab();
			action.clickViewDetailsRow1();
			action.addValue("txtbx_Quantity", "0.1");
	 		action.save();
	 		
	 		action.route();
	 		Assert.assertEquals(action.inWorkflow(), false);
	 		action.clickOK();

	 		action.assertValue("txtbx_Status", "APPR");
	 		
	 		action.logMessage("txtbx_PONUM", "Created revision 1 and modified quantity to add 10%. "
	 				+ "Verify with SAP that the added PO has gone through.");

	 		
	 	 	Log.info("Less than 20% cost change >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	 	 	action.clickListViewTab();
			toSearch = "(status = 'APPR' and ponum like '48%' and historyflag = 0 and REVISIONNUM=0 and totalcost = 500.0 and statusdate >=  '2015-05-14'  and receipts = 'NONE')"
					+ "and ponum not in (select ponum from po where revisionnum > 0) and ponum in (select ponum from poline where linetype in ('SERVICE', 'ITEM'))";
			action.whereClause(toSearch);
			action.clickFirstValueFromList();
			action.storeValue("txtbx_PONUM", "PONUM");
			
			action.click("lnk_RevisePO");
			action.input("txtbx_PODescription2", testCase);
			action.clickOK();
			
			action.assertValue("txtbx_Status", "PNDREV");
			action.assertValue("txtbx_Revision", "1");
			
			action.clickListViewTab();
			action.quickSearch(action.getStoredValue("PONUM"));
			action.clickListViewTab();
			action.rowsDisplayed(2);
			
			action.advancedSearch(new String[][] {{"txtbx_PONUM", action.getStoredValue("PONUM")}, {"txtbx_Status", "PNDREV"}});

			action.clickPOLinesTab();
			action.clickNewRow();
			action.click("btn_Item_chevron");
	 		action.click("lnk_SelectValue");
	 		action.clickFirstValueFromList();
	 		
	 		action.input("txtbx_OrderUnit", "EA");
	 		action.input("txtbx_UnitCost", "50");
	 		action.getStoreroom();
	 		action.input("txtbx_RequiredDate", action.getDate().plusWeeks(3).toString());
	 		
	 		if (action.isReadOnly("txtbx_ConversionFactor") == false) {
 	 			 action.input("txtbx_ConversionFactor", "1");
 	 		}
	 		action.save();
	
	 		action.route();
	 		Assert.assertEquals(action.inWorkflow(), false);
	 		action.clickOK();

	 		action.assertValue("txtbx_Status", "APPR");
	 		action.logMessage("txtbx_PONUM", "Created revision 1 and added new PO line with unit cost=50. "
	 				+ "Verify with SAP that the added PO has gone through.");

			Log.info("More than 20% cost change>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			action.clickListViewTab();
	 		toSearch = "(status = 'APPR' and ponum like '48%' and historyflag = 0 and REVISIONNUM=0 and totalcost = 500.0 and statusdate >=  '2015-05-14'  and receipts = 'NONE')"
	 				+ "and ponum not in (select ponum from po where revisionnum > 0) and ponum in (select ponum from poline where linetype in ('SERVICE', 'ITEM'))";
			action.whereClause(toSearch);
			action.clickFirstValueFromList();

			action.click("lnk_RevisePO");
			action.input("txtbx_PODescription2", testCase);
			action.clickOK();
			
			action.storeValue("txtbx_PONUM", "PONUM");
			action.assertValue("txtbx_Status", "PNDREV");			
			action.assertValue("txtbx_Revision", "1");
			
			action.clickListViewTab();
			action.quickSearch(action.getStoredValue("PONUM"));
			action.clickListViewTab();
			action.rowsDisplayed(2);
			
//			action.clearWhereClause();
			action.advancedSearch(new String[][] {{"txtbx_PONUM", action.getStoredValue("PONUM")}, {"txtbx_Status", "PNDREV"}});

			action.clickPOLinesTab();
			action.clickNewRow();
			action.click("btn_Item_chevron");
	 		action.click("lnk_SelectValue");
	 		action.clickFirstValueFromList();
	 		
	 		action.input("txtbx_OrderUnit", "EA");
	 		action.input("txtbx_UnitCost", "200");
	 		action.input("txtbx_RequiredDate", action.getDate().plusWeeks(3).toString());
	 		action.getStoreroom();
	 		
	 		if (action.isReadOnly("txtbx_ConversionFactor") == false) {
 	 			 action.input("txtbx_ConversionFactor", "1");
 	 		}
	 		action.save();
	 		
	 		action.route();
	 	 	Assert.assertEquals(action.inWorkflow(), true);
	 	 	action.clickOK();
	 	 	Assert.assertFalse(action.getWFApprover().isEmpty());
			action.storeValue("txtbx_PONUM", "PONUM");
	 	 	action.approveWF();
	 	 	
	 	 	action.assertValue("txtbx_Status", "APPR");
	 	 	Assert.assertEquals(action.inWorkflow(), false);
	 		 	 		 	 	
	 	 	action.logMessage("txtbx_PONUM", "Created revision 1 and added new PO line with unit cost=200."
	 	 			+ " Verify with SAP that the added PO has gone through.");
			
	    } catch (Exception e) {
	    	Log.error("Exception revisePOwithCostChange--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test
    public void revisePOwithCompletedReceipt() {
    	testCase = "revisePOwithCompletedReceipt";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  

			action.login("maxadmin");
			action.goToPOPage();
			
			Log.info("Less than 20% cost change by increasing quantity>>>>>>>>>>>>>>>>>>>>>>>>>>");
			String toSearch = "(status = 'APPR' and ponum like '48%' and historyflag = 0 and REVISIONNUM=0 and totalcost >1 and upper(receipts) = 'COMPLETE' and statusdate >=  '2015-05-14' ) and ponum not in (select ponum from po where revisionnum > 0)";
			action.whereClause(toSearch);
			action.clickFirstValueFromList();
			
			action.click("lnk_RevisePO");
			action.input("txtbx_PODescription2", testCase);
			action.clickOK();
				
			action.assertValue("txtbx_Status", "PNDREV");
			
			action.clickPOLinesTab();
			action.clickViewDetailsRow1();
			action.addValue("txtbx_Quantity", "-0.1");
	 		action.click("btn_Save");
	 		action.verifyAlert("BMXAA");
	 		action.clickOK();
	 		action.addValue("txtbx_Quantity", "0.1");
	 		action.click("btn_Save");
	 		
	 		action.route();
	 		Assert.assertEquals(action.inWorkflow(), false);
	 		action.clickOK();

	 		action.assertValue("txtbx_Status", "APPR");
	 		
	 		action.clickPOTab();
	 		action.assertValue("txtbx_Receipts", "Partial");
	 		
	 		action.logMessage("txtbx_PONUM", "Created revision 1 and modified quantity to add 10%. "
	 				+ "Verify with SAP that the added PO has gone through.");
	 		
	 		Log.info("More than 20% cost change>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			action.clickListViewTab();
	 		toSearch = "(status = 'APPR' and ponum like '48%' and historyflag = 0 and REVISIONNUM=0 and totalcost = 500.0 and upper(receipts) = 'COMPLETE' and statusdate >=  '2015-05-14' ) and ponum not in (select ponum from po where revisionnum > 0)";
			action.whereClause(toSearch);
			action.clickFirstValueFromList();
			action.storeValue("txtbx_PONUM", "PONUM");

			action.click("lnk_RevisePO");
			action.input("txtbx_PODescription2", testCase);
			action.clickOK();
			
			action.assertValue("txtbx_Status", "PNDREV");			
			action.assertValue("txtbx_Revision", "1");
			
			//not allowed to delete lines
			action.clickPOLinesTab();
			action.clickDeleteDetailsRow1();
			action.verifyAlert("BMXAA3334E");
			action.clickOK();
			
			action.clickNewRow();
			action.click("btn_Item_chevron");
	 		action.click("lnk_SelectValue");
	 		action.clickFirstValueFromList();
	 		
	 		action.input("txtbx_OrderUnit", "EA");
	 		action.input("txtbx_UnitCost", "20");
	 		action.input("txtbx_RequiredDate", action.getDate().plusWeeks(3).toString());
	 		action.getStoreroom();
	 		
	 		if (action.isReadOnly("txtbx_ConversionFactor") == false) {
 	 			 action.input("txtbx_ConversionFactor", "1");
 	 		}
	 		action.save();

	 		action.route();
	 		Assert.assertEquals(action.inWorkflow(), false);
	 		action.clickOK();
	 		
	 		action.assertValue("txtbx_Status", "APPR");	
	 		action.clickPOTab();
	 		action.assertValue("txtbx_Receipts", "Partial");
	
	 		action.logMessage("txtbx_PONUM", "Created revision 1 and added new PO line with unit cost=20."
	 	 			+ " Verify with SAP that the added PO has gone through.");
		
	    } catch (Exception e) {
	    	Log.error("Exception revisePOwithCompletedReceipt--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
    
    @Test
    public void revisePOwithPartialReceipt() {
    	testCase = "revisePOwithPartialReceipt";
//    	extentTest.log(LogStatus.INFO, testCase);
    	Log.startTestCase(testCase);
		try{  

			action.login("maxadmin");
			action.goToPOPage();
			
			Log.info("Less than 20% cost change >>>>>>>>>>>>>>>>>>>>>>>>>>");
			String toSearch = "(status = 'APPR' and ponum like '48%' and historyflag = 0 and REVISIONNUM=0 and totalcost > 1 and upper(receipts) = 'PARTIAL' and statusdate >=  '2015-05-14' ) and ponum not in (select ponum from po where revisionnum > 0)";
			action.whereClause(toSearch);
			action.clickFirstValueFromList();
			
			action.click("lnk_RevisePO");
			action.input("txtbx_PODescription2", testCase);
			action.clickOK();
				
			action.assertValue("txtbx_Status", "PNDREV");
			action.assertValue("txtbx_Revision", "1");
			
			//not allowed to delete lines
			action.clickPOLinesTab();
			action.clickDeleteDetailsRow1();
			action.verifyAlert("BMXAA3334E");
			action.clickOK();
			
			action.clickNewRow();
			action.click("btn_Item_chevron");
	 		action.click("lnk_SelectValue");
	 		action.clickFirstValueFromList();
	 		
	 		action.input("txtbx_OrderUnit", "EA");
	 		action.input("txtbx_UnitCost", "20");
	 		action.input("txtbx_RequiredDate", action.getDate().plusWeeks(3).toString());
	 		action.getStoreroom();
	 		
	 		if (action.isReadOnly("txtbx_ConversionFactor") == false) {
 	 			 action.input("txtbx_ConversionFactor", "1");
 	 		}
	 		action.save();
			
	 		action.route();
	 		Assert.assertEquals(action.inWorkflow(), false);
	 		action.clickOK();
	 		
	 		action.assertValue("txtbx_Status", "APPR");	
	 		action.clickPOTab();
	 		action.assertValue("txtbx_Receipts", "Partial");
	
	 		action.logMessage("txtbx_PONUM", "Created revision 1 and added new PO line with unit cost=20."
	 	 			+ " Verify with SAP that the added PO has gone through.");

	    } catch (Exception e) {
	    	Log.error("Exception revisePOwithPartialReceipt--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}

}
