package config;

import static executionEngine.DriverScript.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import executionEngine.DriverScript;
import utility.Log;

public class Security {
/*MAX-1192 - Security Redesign*/
	
	static WebDriverWait wait = new WebDriverWait(driver, 3);
	
	public static void security(String object, String data) {
		
	}
	
	/*MAX-1311-Update License Type */
	static void testLicenseType() {
		try {
			String typeList = "Authorised, Express, Limited, Self-service";
			
			Log.info("Start testUpdateLicenseType..................");
			ActionKeywords.login("maxadmin", null);
			
			ActionKeywords.click("lnk_Home");
			ActionKeywords.waitForElementDisplayed("hvr_Security");
			ActionKeywords.hover("hvr_Security","lnk_Users");
			
//			Check  type in the List tab btn_Type
			ActionKeywords.click("btn_Type1");
			ActionKeywords.checkValueExistsInSelectValue(typeList, "true");
			ActionKeywords.waitForElementDisplayed("btn_Cancel");
			ActionKeywords.click("btn_Cancel");
			
//			Go to Advanced Search
			ActionKeywords.click("btn_Advanced_Search");
			ActionKeywords.waitForElementDisplayed("btn_Type");
			ActionKeywords.click("btn_Type");
			ActionKeywords.checkValueExistsInSelectValue(typeList, "true");
			ActionKeywords.waitForElementDisplayed("btn_Cancel");
			ActionKeywords.click("btn_Cancel");
			
//			Check user tab(main tab)
			ActionKeywords.click("tab_User");
			ActionKeywords.waitForElementDisplayed("btn_Type");
			ActionKeywords.click("btn_Type");
			ActionKeywords.checkValueExistsInSelectValue(typeList, "true");
			ActionKeywords.waitForElementDisplayed("btn_Cancel");
			
			ActionKeywords.logout(null, null);
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
 			DriverScript.bResult = false;
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
 			DriverScript.bResult = false;
	    }	
   }
	
	/*MAX-1310-New Domain and new Column for mapping Modules to Security Goups */
	static void testNewDomain() {
//		Login as maxadmin
//		Navigate to System Configurations -> Domains
//		Search for APPMODULESEC domain
//		The ff values should exist "HIDDEN, ASSET, SETUP, PURCHASE, INVENTOR, INT, PLANS, SD, PM, WO
		try {
			Log.info("Start testNewDomain..................");
			ActionKeywords.login("maxadmin", null);
			
			ActionKeywords.click("lnk_Home");
			ActionKeywords.waitForElementDisplayed("hvr_SystemConfiguration");
			ActionKeywords.hover("hvr_SystemConfiguration","lnk_PR");
			ActionKeywords.waitForElementDisplayed("hvr_Purchasing");
			
//			Check if new button is active
			ActionKeywords.isDisabled("btn_New", "true");
			
//			Check if save button is active
			ActionKeywords.click("tab_PR");
			ActionKeywords.isDisabled("btn_Save", "true");
			
			ActionKeywords.logout(null, null);

	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
 			DriverScript.bResult = false;
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
 			DriverScript.bResult = false;
	    }	
		
		
	}
	
	/*MAX-1310-New Domain and new Column for mapping Modules to Security Goups */
	static void testNewColumn() {
//		Login as maxadmin
//		Navigate to Security -> Security Groups
//		Select a Security Group, i.e. DATAADMIN
//		Add a module to DATAADMIN on the Groups tab, i.e. Asset module
//		Navigate to Users
//		Select a user from DATAADMIN group, i.e. Adrian Chan
//		Go to Groups tab
//		Check the module field exists and populated with value Asset
	}
	
	/*MAX-1345-New Object Structure and Enterprise Service to create new Security Groups */
	static void testObjectStructure() {
		
	}
	
	/*MAX-1345-New Object Structure and Enterprise Service to create new Security Groups */
	static void testEnterpriseService() {
		
	}
	
	/*MAX-1328-Can not add document to the document library */
	static void testAddDocumentToLibrary() {
		
	}
	
	/*MAX-1281-Review Conditional Expressions for hard coded Security Groups */
	static void testReviewConditionalExpressions() {
		
	}
	
	/*MAX-1312-User Profiles for new Security Structure */
	static void testUserProfiles() {
		
	}
	
	/*MAX-1298-Report Check for Security Groups */
	static void testReports() {
		
	}
	
	/*MAX-1306-Add new value to KRUSAGETYPE domain */
	static void testKRUSAGETYPE() {
//		Login as maxadmin
//		Navigate to System Configurations -> Domains
//		Search for KRUSAGETYPE domain
//		The ff values should exist "CONDEXP"
	}
	
	/*MAX-1307-Update and Maintain the GRPREASSIGNAUTH Table */
	static void testMaintainGRPREASSIGNAUTHTable() {
		
	}

	/*MAX-1313-Update Conditional Expression for COMP work Orders */
	static void testCondExpForCompWOs() {
//		Only engineering users can comp a WO in fieldcomp status and discipline is 'Structures'
		
	}
	
	/*MAX-1314-Create new Conditional Expression for Create 155 COMM Template */
	static void testCondExpFor155Template() {
		
	}
	
	/*MAX-1316-Cleanup Cond Exp STOPSAVEPR */
	static void testCondExpSTOPSAVEPR() {
//		TODO - replicated error in production
//		login as express user
//		Go to PR
//		Open PR that is currently routed to user for approval
//		validate that user cannot edit PR
		try {
			Log.info("Start testCondExpSTOPSAVEPR..................");
			ActionKeywords.login("MXPLAN", null);
			
			ActionKeywords.click("lnk_Home");
			ActionKeywords.waitForElementDisplayed("hvr_Purchasing");
			ActionKeywords.hover("hvr_Purchasing","lnk_PR");
			ActionKeywords.waitForElementDisplayed("hvr_Purchasing");
			
//			Check if new button is active
			ActionKeywords.isDisabled("btn_New", "true");
			
//			Check if save button is active
			ActionKeywords.click("tab_PR");
			ActionKeywords.isDisabled("btn_Save", "true");
			
			ActionKeywords.logout(null, null);

	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
 			DriverScript.bResult = false;
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
 			DriverScript.bResult = false;
	    }	
		
	}
	
	/*MAX-1321-Change to Conditional Expression for Change STATUS in WO App */
	static void testCondExpForChangeWOStatus() {
		
	}
	
	/*MAX-1322-Conditional Expression Change for Structures Engineering to COMP WOs*/
	static void testCondExpToCompWOForEngStrucs() {
		
	}
	
	/*MAX-1324-PR Approver is able to make changes to a PR and save */
	static void testPRApproverAccess() {
		
	}
	
	/*MAX-1347-Users need the ability to delete tasks after the wo has been saved */
	static void testDeleteWOTaskAccess() {
		
	}
}
