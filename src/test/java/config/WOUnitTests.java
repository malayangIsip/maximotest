package config;

import static executionEngine.DriverScript.OR;
import static executionEngine.DriverScript.driver;
import static executionEngine.DriverScript.extentTest;
import static org.junit.Assert.assertFalse;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import executionEngine.DriverScript;
import utility.Log;

public class WOUnitTests extends ActionKeywords {
    static WebDriverWait wait = new WebDriverWait(driver, 3);
    
    static LocalDate ngayon = getDate();
    //add 2 week to the current date
//    LocalDate next2Week = ngayon.plus(2, ChronoUnit.WEEKS);
		
	public static void unitTests() throws Exception {
//		commTemplateEmail1("SLEEPERS");
//		validateVendor();
//		editReservations("mxplan", "CM");
//		schedDateButton("maxadmin", "CAP");
		schedDateWF("maxadmin","CAP");
	}
	
	//Scenario 1 - user has primary email
//	static void commTemplateEmail1(String feature) {
//		try {
//			Log.info("Start commTemplateEmail..................");
//			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
//			waitForElementDisplayed("hvr_Asset", "1");
//			hover("hvr_Asset","lnk_Feature");
//			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(feature);
//			enter("txtbx_QuickSearch","1");
//			waitForElementDisplayed("txtbx_Feature", "1");
//
//			boolean checked = driver.findElement(By.xpath(OR.getProperty("chkbx_ApplyGapsandOverlaps"))).getAttribute("aria-checked").equals("true");
//			if (!checked) {
//				driver.findElement(By.xpath(OR.getProperty("chkbx_ApplyGapsandOverlaps"))).click();
//			}
//	    } catch (NoSuchElementException e) {
//	    	Log.error("Element not found --- " + e.getMessage());
// 			DriverScript.bResult = false;
//	    } catch (Exception e) {
//	    	Log.error("Exception --- " + e.getMessage());
// 			DriverScript.bResult = false;
//	    }	
//	}
	
	//Scenario 2 - user has no primary email
//		static void commTemplateEmail2(String feature) {
//			try {
//				Log.info("Start commTemplateEmail..................");
//				driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
//				waitForElementDisplayed("hvr_Asset", "1");
//				hover("hvr_Asset","lnk_Feature");
//				driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(feature);
//				enter("txtbx_QuickSearch","1");
//				waitForElementDisplayed("txtbx_Feature", "1");
//
//				boolean checked = driver.findElement(By.xpath(OR.getProperty("chkbx_ApplyGapsandOverlaps"))).getAttribute("aria-checked").equals("true");
//				if (!checked) {
//					driver.findElement(By.xpath(OR.getProperty("chkbx_ApplyGapsandOverlaps"))).click();
//				}
//		    } catch (NoSuchElementException e) {
//		    	Log.error("Element not found --- " + e.getMessage());
//	 			DriverScript.bResult = false;
//		    } catch (Exception e) {
//		    	Log.error("Exception --- " + e.getMessage());
//	 			DriverScript.bResult = false;
//		    }	
//		}
	
	static void validateVendor() {
		try {
//			Should only validate Vendor status if WO is in unapproved status
			Log.info("Start validateVendor..................");
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_WO");
			waitFor();
			createWOwithMaterialPlans("Test validate Vendor", "CM");
			storeValue("txtbx_WONUM", "WONUM");
//			Route the WO
			changeStatus("", "PLANNED");
			routeWF("", "DFA");
			stopWF("", "");
//			Deactivate Vendor and try to save WO - should generate error
			Log.info("Disqualify Vendor..................");
			driver.findElement(By.xpath(OR.getProperty("btn_Vendor_chevron"))).click();
			Log.info("Click btn_Vendor_chevron..................");
			waitFor(); 
			driver.findElement(By.xpath("//span[text()='Go To Companies']")).click();
			Log.info("Click Go To Companies..................");
			waitFor(); 
			driver.findElement(By.xpath(OR.getProperty("chkbx_DisqualifyVendor"))).click();
			Log.info("Click chkbx_DisqualifyVendor..................");
			save("1","1");
			driver.findElement(By.xpath(OR.getProperty("btn_ReturnValue"))).click();
			Log.info("Click btn_ReturnValue..................");
			waitForElementDisplayed("txtbx_WONUM", "1");
//			Try to save edit the WO and save
			Log.info("Click btn_Save..................");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Description"))).sendKeys(" - inactivate Vendor");
			driver.findElement(By.xpath(OR.getProperty("btn_Save"))).click();
			verifyAlert("msg_Popup", "is not valid");
			click("btn_OK", null);
//			Try to route the WO
			Log.info("Click btn_Route..................");
			driver.findElement(By.xpath(OR.getProperty("btn_Route"))).click();
			verifyAlert("msg_Popup", "is not valid");
			click("btn_OK", null);
			waitFor();
//			Reactivate Vendor
			Log.info("Reactivate Vendor..................");
			driver.findElement(By.xpath(OR.getProperty("btn_Vendor_chevron"))).click();
			waitFor();
			driver.findElement(By.xpath("//span[text()='Go To Companies']")).click();
//			waitForElementDisplayed("txtbx_company", "1");
			waitFor(); 
			driver.findElement(By.xpath(OR.getProperty("chkbx_DisqualifyVendor"))).click();
			Log.info("Click chkbx_DisqualifyVendor to reactivate..................");
			save("1","1");
			driver.findElement(By.xpath(OR.getProperty("btn_ReturnValue"))).click();
			waitForElementDisplayed("txtbx_WONUM", "1");
			Log.info("Reopen WO, the app does not recognize the new Vendor status...");
			logout("1","1");
			openBrowser("1", "Mozilla");
			login("1","maxadmin");
			Log.info("Start validateVendor..................");
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_WO");
			waitFor();
			waitForElementDisplayed("txtbx_QuickSearch", "1");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(DriverScript.WONUM);
			enter("txtbx_QuickSearch", "1");
			waitFor();
//			Approved WO should not validate Vendor Status
			changeStatus("", "approved");
//			Deactivate Vendor and try to save WO - should generate error
			Log.info("Click tab_Plans..................");
			driver.findElement(By.xpath(OR.getProperty("tab_Plans"))).click();
  		    waitForElementDisplayed("tab_Materials");
  		    Log.info("Click tab_Materials.................."); 		   
			driver.findElement(By.xpath(OR.getProperty("tab_Materials"))).click();
			Log.info("Click details arrowdown..................");
			waitForElementDisplayed("btn_ViewDetails_Row1");
			driver.findElement(By.xpath(OR.getProperty("btn_ViewDetails_Row1"))).click();
			waitForElementDisplayed("btn_Vendor_chevron");
			Log.info("Deactivate Vendor..................");
			driver.findElement(By.xpath(OR.getProperty("btn_Vendor_chevron"))).click();
			waitFor();
			driver.findElement(By.xpath("//span[text()='Go To Companies']")).click();
//			waitForElementDisplayed("txtbx_company", "1");
			waitFor();
			driver.findElement(By.xpath(OR.getProperty("chkbx_DisqualifyVendor"))).click();
			save("1","1");
			driver.findElement(By.xpath(OR.getProperty("btn_Return"))).click();
			Log.info("Reopen WO, the app does not recognize the new Vendor status...");
			logout("1","1");
			openBrowser("1", "Mozilla");
			login("1","maxadmin");
			Log.info("Start validateVendor..................");
			driver.findElement(By.xpath(OR.getProperty("lnk_Home"))).click();
			waitForElementDisplayed("hvr_WO", "1");
			hover("hvr_WO","lnk_WO");
			waitFor();
			waitForElementDisplayed("txtbx_QuickSearch", "1");
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).click();
			driver.findElement(By.xpath(OR.getProperty("txtbx_QuickSearch"))).sendKeys(DriverScript.WONUM);
			enter("txtbx_QuickSearch", "1");
			waitFor();
//			Try to save edit the WO and save
			waitForElementDisplayed("txtbx_Description");
			driver.findElement(By.xpath(OR.getProperty("txtbx_Description"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txtbx_Description"))).sendKeys("Test validate Vendor - reactivate Vendor");
			save("1","1");
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
 			DriverScript.bResult = false;
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
 			DriverScript.bResult = false;
	    }	
	}
	
//	[MAX-1080] Select Action > Edit Reservations: Date
	static void editReservations(String user, String workType) throws Exception {
		try {
			Log.info("Start _editReservations");
			login(null, user);
			
//			click("lnk_Home", null);
			//Search for an approved WO with material resevation
			String sqlData = "((status = 'WMATL' and (woclass = 'WORKRESULT' or woclass = 'WORKORDER' or woclass = 'ACTIVITY') and historyflag = 0 and istask = 0)) and ((upper(kr_discipline) = 'TRACK'))";
			whereClause(null, sqlData);
			waitForElementDisplayed("lnk_List_Code");
			click("lnk_List_Code", null);
//			check if  edit reservation link is active
			isDisabled("lnk_EditReservations", "FALSE");
			click("lnk_EditReservations", null);
			tableNotEmpty("tbl_Reservations", "TRUE");
			//check required date fields are editable
			isDisabled("txtbx_RequiredDateViewReservation", "FALSE");

			
		} catch(AssertionError ae){
			Log.error("JUNIT _editReservations --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
		} catch(Exception e){
	 		Log.error("JUNIT editReservations ---- " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, e.getMessage());
	 		DriverScript.bResult = false;
	    }
	}
	
	
//	@Test
    //	[MAX-1012] Inventory Item required Dates should synch with Scheduled Start Date 
	static void schedDateButton(String user, String workType) throws Exception {
		try {
			Log.info("Start _schedDateButton");
			login(null, user);
			
//			click("lnk_Home", null);
			waitForElementDisplayed("hvr_WO", "1");
			scrollDown("hvr_WO", "");
			hover("hvr_WO","lnk_WO");
			
			createWOwithPlans("Test schedDateButton", workType);
			waitFor();
			
			if (workType.equals("CAP")) {
				changeStatus("dummy", "planned");
				waitFor();
			}
			
			click("tab_Main", null);
			scrollDown("lnk_SetSchedDate", "");
			isDisabled("lnk_SetSchedDate", "FALSE");
		
			Log.info("If Scheduled Start date is empty the button is greyed out");
			clear("txtbx_ScheduledStart", null);
			save("1","1");
			scrollDown("lnk_SetSchedDate", "");
			isDisabled("lnk_SetSchedDate", "TRUE");
			
			Log.info("If scheduled start date is in the past and the work order is not approved, the button is available but when pressed an error is displayed");
//			input("txtbx_ScheduledStart", ngayon.minus(2, ChronoUnit.DAYS).toString());
//			manual input as input method enter date + 2 weeks automatically for excel feeds
			driver.findElement(By.xpath(OR.getProperty("txtbx_ScheduledStart"))).sendKeys(ngayon.minus(2, ChronoUnit.DAYS).toString());
			save("1","1");
			scrollDown("lnk_SetSchedDate", "");
			isDisabled("lnk_SetSchedDate", "FALSE");
			click("lnk_SetSchedDate", null);
			verifyAlert("msg_Popup", "The required date cannot be in the past.");
			driver.switchTo().activeElement().click();
									
			Log.info("if scheduled start date is in the future and the work order is not approved, the button is available and when pressed updates all material required dates to the scheduled start date, the screen refreshes on save. If there are no materials to update, no changes occur");
//			input("txtbx_ScheduledStart", ngayon.plus(2, ChronoUnit.WEEKS).toString());
//			manual input as input method enter date + 2 weeks automatically for excel feeds
			driver.findElement(By.xpath(OR.getProperty("txtbx_ScheduledStart"))).sendKeys(ngayon.plus(2, ChronoUnit.WEEKS).toString());
			scrollDown("lnk_SetSchedDate", "");
			click("lnk_SetSchedDate", null);
			save("1","1");
			String WOSchedStartDate = getAttributeValue("txtbx_ScheduledStart");
			//Goto Plan Materials to get the value of materials required date
			click("tab_Plans", null);
			click("tab_Materials", null);
			String materialsRequiredDate = getAttributeValue("txtbx_RequiredDate");
			Assert.assertEquals(materialsRequiredDate, WOSchedStartDate);
			
			Log.info("Change value of schedStartDate and check materialsRequiredDate updates");
			click("tab_Main", null);
//			input("txtbx_ScheduledStart", ngayon.plus(3, ChronoUnit.WEEKS).toString());
//			manual input as input method enter date + 2 weeks automatically for excel feeds
			driver.findElement(By.xpath(OR.getProperty("txtbx_ScheduledStart"))).sendKeys(ngayon.plus(3, ChronoUnit.WEEKS).toString());
			save("1","1");
			scrollDown("lnk_SetSchedDate", "");
			click("lnk_SetSchedDate", null);
			save("1","1");
			WOSchedStartDate = getAttributeValue("txtbx_ScheduledStart");
			//Goto Plan Materials to get the value of materials required date
			click("tab_Plans", null);
			materialsRequiredDate = getAttributeValue("txtbx_RequiredDate");
			Assert.assertEquals(materialsRequiredDate, WOSchedStartDate);
			logout(null, null);
			Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> END _schedDateButton");
		} catch(AssertionError ae){
			Log.error("JUNIT _schedDateButton --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
		} catch(Exception e){
	 		Log.error("JUNIT schedDateButton ---- " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, e.getMessage());
	 		DriverScript.bResult = false;
	    }
	}
	
//	[MAX-1012] Inventory Item required Dates should synch with Scheduled Start Date 
	static void schedDateWF(String user, String workType) throws Exception {
		try {
			Log.info("Start _schedDateWF");
			login(null, user);
			
//			click("lnk_Home", null);
			waitForElementDisplayed("hvr_WO", "1");
			scrollDown("hvr_WO", "");
			hover("hvr_WO","lnk_WO");
			
			createWOwithPlans("Test _schedDateWF", workType);
			waitFor();
			click("tab_Main", null);
//			scrollDown("lnk_SetSchedDate", "");
			
			if (workType.equals("CAP")) {
				changeStatus("dummy", "planned");
			}
			
			Log.info("If Scheduled Start date is empty, when work flow is initiated, an error is displayed and workflow stops");
			clear("txtbx_ScheduledStart", null);
			save("1","1");
			click("btn_Route", null);
			verifyAlert("msg_Popup", "The Scheduled Start Date cannot be empty");
			driver.switchTo().activeElement().click();
			Assert.assertEquals(inWorkflow(), false);
			click("btn_OK", null);
		
			Log.info("If Scheduled Start date is in the past, when work flow is initiated, an error is displayed and work flow stops");
//			input("txtbx_ScheduledStart", ngayon.minus(1, ChronoUnit.DAYS).toString());
//			manual input as input method enter date + 2 weeks automatically for excel feeds
			clear("txtbx_ScheduledStart", null);
			driver.findElement(By.xpath(OR.getProperty("txtbx_ScheduledStart"))).sendKeys(ngayon.minus(1, ChronoUnit.DAYS).toString());
			save("1","1");
			click("btn_Route", null);
			verifyAlert("msg_Popup", "The Scheduled Start Date cannot be empty");
			driver.switchTo().activeElement().click();
			Assert.assertEquals(inWorkflow(), false);
			click("btn_OK", null);
			
			Log.info("If any inventory required dates are in the past, when work flow is initiated, an error is displayed and work flow stops");
//			input("txtbx_ScheduledStart", ngayon.toString());
//			manual input as input method enter date + 2 weeks automatically for excel feeds
			clear("txtbx_ScheduledStart", null);
			driver.findElement(By.xpath(OR.getProperty("txtbx_ScheduledStart"))).sendKeys(ngayon.toString());
			scrollDown("lnk_SetSchedDate", "");
			click("lnk_SetSchedDate", null);
			save("1","1");
			//change sked date to future and leave matreqdate to past
			clear("txtbx_ScheduledStart", null);
			driver.findElement(By.xpath(OR.getProperty("txtbx_ScheduledStart"))).sendKeys(ngayon.plus(1, ChronoUnit.DAYS).toString());
			save("1","1");
			click("btn_Route", null);
			if (workType.equals("CAP")) {
				verifyAlert("msg_Popup", "The Scheduled Start Date cannot be empty");
			} else {
				verifyAlert("msg_Popup", "There is an Inventory Required Date in the past. Please rectify and resubmit to workflow");
			}
			driver.switchTo().activeElement().click();
			Assert.assertEquals(inWorkflow(), false);
			click("btn_OK", null);
			
			Log.info("If Scheduled Start date is in the future(at least 2 weeks for CAP/AMREN), when work flow is initiated work flow passes to the next check");
			Log.info("If all inventory required dates are in the future, when work flow is initiated work flow passes to the next check");
//			input("txtbx_ScheduledStart", ngayon.plus(1, ChronoUnit.WEEKS).toString());
//			manual input as input method enter date + 2 weeks automatically for excel feeds
			clear("txtbx_ScheduledStart", null);
			driver.findElement(By.xpath(OR.getProperty("txtbx_ScheduledStart"))).sendKeys(ngayon.plus(1, ChronoUnit.WEEKS).toString());
			scrollDown("lnk_SetSchedDate", "");
			click("lnk_SetSchedDate", null);
			save("1","1");
			click("btn_Route", null);
			if (workType.equals("CAP")) {
				verifyAlert("msg_Popup", "The Scheduled Start Date cannot be empty");
				driver.switchTo().activeElement().click();
				clear("txtbx_ScheduledStart", null);
				driver.findElement(By.xpath(OR.getProperty("txtbx_ScheduledStart"))).sendKeys(ngayon.plus(3, ChronoUnit.WEEKS).toString());
				scrollDown("lnk_SetSchedDate", "");
				click("lnk_SetSchedDate", null);
				save("1","1");
				click("btn_Route", null);
			} 
			click("btn_OK", null);
			Assert.assertEquals(inWorkflow(), true);
			click("btn_OK", null);
			assertValue2("txtbx_Status", "PLAN");
						
			Log.info("If Work Order is approved the button is greyed out");
			changeStatus("dummy", "approved");
//			String sqlData = "((status = 'WMATL' and (woclass = 'WORKRESULT' or woclass = 'WORKORDER' or woclass = 'ACTIVITY') and historyflag = 0 and istask = 0)) and ((upper(kr_discipline) = 'TRACK'))";
//			whereClause(null, sqlData);
//			waitForElementDisplayed("lnk_List_Code");
//			click("lnk_List_Code", null);
			isDisabled("lnk_SetSchedDate", "TRUE");
			logout(null, null);
			Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> END _schedDateWF");
		} catch(AssertionError ae){
			Log.error("JUNIT schedDateWF --- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
		} catch(Exception e){
	 		Log.error("JUNIT schedDateWF: " + e.getMessage());
	 		extentTest.log(LogStatus.ERROR, e.getMessage());
	 		DriverScript.bResult = false;
	    }
	}
}
