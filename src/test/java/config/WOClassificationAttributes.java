package config;

import static executionEngine.DriverScript.OR;
import static executionEngine.DriverScript.driver;

import org.apache.poi.ss.formula.functions.Value;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;

import executionEngine.DriverScript;
import utility.Log;

public class WOClassificationAttributes extends ActionKeywords {

	public static void woClassificationAttributes(String object, String data)  {
//		createAttribute("ATTRBTEST1", "ALN");
//		createAttribute("ATTRBTEST2", "ALN");
//		createAttribute("ATTRBTEST3", "NUMERIC");
//		createClassifications("MANDTST1", "Test mandatory fields - 1 attrib mandatory");
		createClassifications("MANDTST2", "Test mandatory fields - no attrib mandatory");
//		createClassifications("MANDTST3", "Test mandatory fields - multiple attrib at least 1 mandatory");
//		createClassifications("MANDTST4", "required in JP only");
//		createClassifications("MANDTST5", "required in SR only");
//		createClassifications("MANDTST6", "required in WO only");
//		createClassifications("MANDTST7", "required in JP and WO");
//		createClassifications("MANDTST8", "required in JP and SR");
//		createClassifications("MANDTST9", "required in WO and SR");
//		createClassifications("MANDTST10", "attrib 1 required in JP; attrib 2 required in WO");
//		createClassifications("MANDTST11", "attrib 1 required in JP; attrib 2 required in SR");
//		createClassifications("MANDTST12", "attrib 1 required in WO; attrib 2 required in SR");
//		createClassifications("MANDTST13", "With Use in JP only; attr requried");
//		createClassifications("MANDTST14", "With Use in SR only; attr requried");
//		createClassifications("MANDTST15", "With Use in WO only; attr requried");
//		createClassifications("MANDTST16", "With Use in Asset only; attr requried");
//		createClassifications("MANDTST17", "With Use in Asset and WO; attr requried");
//		createClassifications("MANDTST18", "With Use in Location only; attr requried");
//		createClassifications("MANDTST19", "With Use in Location and WO; attr requried");
//		createClassifications("MANDTST20", "With Use in CM only; attr requried");
//		createClassifications("MANDTST21", "With Use in CM and WO; attr requried");
		
//		String[] objectUse = {"WORKORDER","SR","JobPlan","Locations","Features","Asset"};
		String[] objectUse = {"WORKORDER"};
		String[][] attributeMANDTST1 = {{"ATTRBTEST1","ATTRBTEST2","ATTRBTEST3"},{"WORKORDER","",""}}; //attribute & priority
		assignClassificationsTODO("MANDTST2", objectUse, attributeMANDTST1);
		String[][] attributeMANDTST2 = {{"ATTRBTEST1","ATTRBTEST2","ATTRBTEST3"},{"","",""}};
//		assignClassifications("MANDTST2", objectUse, attributeMANDTST2);
		String[][] attributeMANDTST3 = {{"ATTRBTEST1","ATTRBTEST2","ATTRBTEST3"},{"WORKORDER","SR",""}};
//		assignClassifications("MANDTST3", objectUse, attributeMANDTST3);
		String[] objectUseMANDTST4 = {"JobPlan"};
		String[][] attributeMANDTST4 = {{"","",""},{"","",""}};
//		assignClassifications("MANDTST4", objectUseMANDTST4, attributeMANDTST4);
	}
	
	static void createAttribute(String name, String datatype)  {
		try {
//			hover("hvr_Administration", "lnk_Classifications");
//			waitForElementDisplayed("lnk_AddModifyProperties", "1");
			Log.info("Create attribute..."+name);
			waitForElementDisplayed("lnk_AddModifyProperties", "1");
			hover("lnk_AddModifyProperties", "lnk_AddAttributes");
			waitForElementDisplayed("btn_NewRow", "1");
			driver.findElement(By.xpath(OR.getProperty("btn_NewRow"))).click();
			driver.findElement(By.xpath("//input[@id=(//label[text()='Attribute:']/@for)]")).sendKeys(name);
			driver.findElement(By.xpath("//input[@id=(//label[text()='Data Type:']/@for)]")).sendKeys(datatype);
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			waitFor();
		} catch (AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			DriverScript.bResult = false;
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
 			DriverScript.bResult = false;
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
 			DriverScript.bResult = false;
	    }	
	}
	
	
	static void createClassifications(String name, String description)  {
		try {
//			waitForElementDisplayed("lnk_AddModifyProperties", "1");
			Log.info("Create Classification..."+name);
			hover("lnk_AddModifyProperties", "lnk_AddClassification");
			waitForElementDisplayed("btn_NewRow", "1");
			driver.findElement(By.xpath(OR.getProperty("btn_NewRow"))).click();
			driver.findElement(By.xpath("//input[@id=(//label[text()='Classification:']/@for)]")).sendKeys(name);
			driver.findElement(By.xpath("//input[contains(@aria-label, 'Classification description')]")).sendKeys(description);
			driver.findElement(By.xpath("//input[@id=(//label[text()='Organization:']/@for)]")).sendKeys("KIWIRAIL");
			driver.findElement(By.xpath("//input[@id=(//label[text()='Site:']/@for)]")).sendKeys("NETWORK");
			driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			waitFor();
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			DriverScript.bResult = false;
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
				DriverScript.bResult = false;
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
				DriverScript.bResult = false;
	    }	
	}
	
	static void assignClassifications(String classification, String[] useWith, String[][] attributeObject)  {
		try {
			Log.info("START assignClassifications...");
			
			String btn_NewRow_UseWith="//table[contains(@aria-label, 'Use With Table Button Group')]/tbody/tr/td/button[@type='button' and contains (., 'New Row')]";
			String btn_NewRow_Attributes="//table[contains(@aria-label, 'Attributes Table Button Group')]//button[@type='button' and contains (., 'New Row')]";
            String useWithObject="//input[@id=(//label[text()='Use With Object:']/@for)]";
            String attribute="//input[@id=(//label[text()='Attribute:']/@for)]";
			
			Log.info("Assign Classification..."+classification);
			waitForElementDisplayed("btn_New", "1");
			driver.findElement(By.xpath(OR.getProperty("btn_New"))).click();
			driver.findElement(By.xpath("//input[@id=(//label[text()='Classification:']/@for)]")).sendKeys(classification);
		
			Log.info("useWith array..."+useWith.length);
			for (int i=0; i<useWith.length; i++) {
				Log.info("useWith array..."+i+"="+useWith[i]);
//				waitForElementDisplayed("btn_NewRow_UseWith", "1");
				driver.findElement(By.xpath(btn_NewRow_UseWith)).click();
				waitFor();
				driver.findElement(By.xpath(useWithObject)).sendKeys(useWith[i]);
				waitFor();
			
//				3 - # of attributes
				for (int y=0; y<3; y++) {
					Log.info("attributeObject[0][y].toString()..."+y+"="+attributeObject[0][y].toString());
//					waitForElementDisplayed("btn_NewRow_Attributes", "1");
					driver.findElement(By.xpath(btn_NewRow_Attributes)).click();
					waitFor();
					driver.findElement(By.xpath(attribute)).sendKeys(attributeObject[0][y].toString());
					Thread.sleep(1000);
					driver.findElement(By.xpath("//span[contains(@id,'_tdrow_[C:7]_hyperlink-lb[R:"+y+"]')]")).click();
					Thread.sleep(4000);
					driver.findElement(By.xpath("//img[contains(@id,'_tdrow_[C:3]_checkbox-cb[R:"+y+"]_img')]")).click();
					Thread.sleep(4000);
					driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
				}
			}	
			save("1", "1");
			
			Log.info("END assignClassifications...");
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			DriverScript.bResult = false;
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
				DriverScript.bResult = false;
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
				DriverScript.bResult = false;
	    }	
	}
	
	static void assignClassificationsTODO(String classification, String[] useWith, String[][] attributeObject)  {
		try {
			Log.info("START assignClassifications...");
			
			String btn_NewRow_UseWith="//table[contains(@aria-label, 'Use With Table Button Group')]/tbody/tr/td/button[@type='button' and contains (., 'New Row')]";
			String btn_NewRow_Attributes="//table[contains(@aria-label, 'Attributes Table Button Group')]//button[@type='button' and contains (., 'New Row')]";
            String useWithObject="//input[@id=(//label[text()='Use With Object:']/@for)]";
            String attribute="//input[@id=(//label[text()='Attribute:']/@for)]";
//            String organization="//input[@id=(//label[text()='Organization:']/@for)]";
//            String site="//input[@id=(//label[text()='Site:']/@for)]";
//            String unitOfMeasure="//input[@id=(//label[text()='Unit of Measure:']/@for)]";
//            String linearType="//input[@id=(//label[text()='Linear Type:']/@for)]";
            String useWithObjectButtonLine1="//span[contains(@id,'_tdrow_[C:7]_hyperlink-lb[R:0]')]";
            String useWithObjectButtonLine2="//span[contains(@id,'_tdrow_[C:7]_hyperlink-lb[R:1]')]";
           	String useWithObjectButtonLine3="//span[contains(@id,'_tdrow_[C:7]_hyperlink-lb[R:2]')]";
			
			Log.info("Assign Classification..."+classification);
			waitForElementDisplayed("btn_New", "1");
			driver.findElement(By.xpath(OR.getProperty("btn_New"))).click();
			driver.findElement(By.xpath("//input[@id=(//label[text()='Classification:']/@for)]")).sendKeys(classification);
		
			Log.info("useWith array...");
			for (int x=0; x<useWith.length; x++) {
				Log.info("useWith.length = "+useWith.length);
//				Log.info("useWith array..."+i+"="+useWith[i]);
				driver.findElement(By.xpath(btn_NewRow_UseWith)).click();
				waitFor();
				driver.findElement(By.xpath(useWithObject)).sendKeys(useWith[x]);
				waitFor5();
			}
			
//			driver.findElement(By.xpath(btn_NewRow_UseWith)).click();
//			waitFor();
//			driver.findElement(By.xpath(useWithObject)).sendKeys("WORKORDER");
//			waitFor5();
			
//			save("1", "1"); 
//			scrollDown();
						
//			driver.findElement(By.xpath(btn_NewRow_Attributes))..isDisplayed();
//			Log.info("NewRow is displayed = "+driver.findElement(By.xpath(btn_NewRow_Attributes)).isDisplayed());
//			
//			3 - # of attributes
			for (int y=0; y<3; y++) {
				Log.info("attributeObject[0][y].toString()..."+y+"="+attributeObject[0][y].toString());

				driver.findElement(By.xpath(btn_NewRow_Attributes)).click();
				Log.info("NewRow button is clicked.....");
				waitFor();
				Log.info("Field attribute is displayed = "+driver.findElement(By.xpath(attribute)).isDisplayed());
				driver.findElement(By.xpath(attribute)).sendKeys(attributeObject[0][y].toString());
				waitFor();
				if (attributeObject[1][y] != "") {
					driver.findElement(By.xpath("//span[contains(@id,'_tdrow_[C:7]_hyperlink-lb[R:"+y+"]')]")).click();
					waitFor();
//					attributeObject[i][1]
					int rowCount = (driver.findElements(By.xpath("//table[contains(@summary,'Objects')]/tbody/tr")).size());
					Log.info("rowCount.."+rowCount);
					String sValue = null;
					for (int z=0;z<rowCount-3;z++){		
						sValue = driver.findElement(By.xpath("//input[contains(@id,'_tdrow_[C:1]_txt-tb[R:"+z+"]')]")).getAttribute("value");
						Log.info("Use with object Value.."+sValue);
						Log.info("attributeObject[1][z].toString().."+attributeObject[1][z].toString());
						if(sValue.toLowerCase().equals(attributeObject[1][z].toString().toLowerCase())){
							Log.info("value of z..."+z);
							driver.findElement(By.xpath("//img[contains(@id,'_tdrow_[C:3]_checkbox-cb[R:"+z+"]_img')]")).click();
							waitFor();
							Log.info("clicking checkbox.." + attributeObject[1][z].toString());
//							Log.info("checkbox value.." + driver.findElement(By.xpath("//img[contains(@id,'_tdrow_[C:3]_checkbox-cb[R:"+z+"]_img')]")).getAttribute("clicked"));
							waitFor();
						}
					}
					driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			    }
			}	
			save("1", "1");
			
			Log.info("useWith array...");
			for (int x=0; x<useWith.length; x++) {
				Log.info("useWith.length = "+useWith.length);
//				Log.info("useWith array..."+i+"="+useWith[i]);
				driver.findElement(By.xpath(btn_NewRow_UseWith)).click();
				waitFor();
				driver.findElement(By.xpath(useWithObject)).sendKeys(useWith[x]);
				waitFor5();
			}
			
			save("1", "1");
			
			Log.info("END assignClassifications...");
		}catch(AssertionError ae){
			Log.error("Assertion failed --- " + ae.getMessage());
			DriverScript.bResult = false;
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
				DriverScript.bResult = false;
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
				DriverScript.bResult = false;
	    }	
	}
	
	static void scrollDown() throws Exception {
		  Log.info("scrolling down ............");
		  
		  JavascriptExecutor js = (JavascriptExecutor) driver;
//		  js.executeScript("javascript:window.scrollBy(250, 350)");
		  js.executeScript("window.scrollBy(0,250)", "");
		  Thread.sleep(2000); 
	  }

}
