package config;

import static executionEngine.Base.OR;
import static executionEngine.Base.action;
import static executionEngine.Base.driver;
import static executionEngine.Base.extent;
import static executionEngine.Base.extentTest;

import org.apache.poi.ss.formula.functions.Value;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
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

public class WOClassificationAttributes extends TestAutomation {
	static String user = "maxadmin";
    static String testCase = "";
    static String testName = "WOClassificationAttributes";

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
    public void setUp() throws Exception {
    	action.openBrowser("Chrome");
    	action.login(user);
    	
    	Base.bResult = true;
    }
    
    @AfterMethod
    public void logout() throws Exception {
    	logout(testName, testCase);

    }
	
	@DataProvider
 	public Object[][] attributes() {
 		return new Object[][] { {"ATTRBTEST1", "ALN"}, {"ATTRBTEST2", "ALN"}, {"ATTRBTEST3", "NUMERIC"} };
 	}
    
   @Test(dataProvider = "attributes")
	void createAttribute(String name, String datatype)  {
	    testCase = "createAttribute";
   		extentTest.log(LogStatus.INFO, testCase);
		Log.startTestCase(testCase);
		try {
			action.goToClassificationsPage();
			action.goToAddAttributes();
			
			action.click("btn_NewRow");
			action.input("txtbx_Attribute", name);
			action.input("txtbx_DataType", datatype);
			action.clickOK();
		} catch (AssertionError ae){
			Log.error("Assertion failed createAttribute--- " + ae.getMessage());
			extentTest.log(LogStatus.ERROR, ae.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found createAttribute--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception createAttribute--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
	
   @DataProvider
	public Object[][] classifications() {
		return new Object[][] { {"MANDTST1", "Test mandatory fields - 1 attrib mandatory"}, 
			{"MANDTST2", "Test mandatory fields - no attrib mandatory"}, 
			{"MANDTST3", "Test mandatory fields - multiple attrib at least 1 mandatory"}, 
			{"MANDTST4", "required in JP only"}, 
			{"MANDTST5", "required in SR only"}, 
			{"MANDTST6", "required in WO only"},
			{"MANDTST7", "required in JP and WO"}, 
			{"MANDTST8", "required in JP and SR"}, 
			{"MANDTST9", "required in WO and SR"}, 
			{"MANDTST10", "attrib 1 required in JP; attrib 2 required in WO"}, 
			{"MANDTST11", "attrib 1 required in JP; attrib 2 required in SR"}, 
			{"MANDTST12", "attrib 1 required in WO; attrib 2 required in SR"},
			{"MANDTST13", "With Use in JP only; attr requried"}, 
			{"MANDTST14", "With Use in SR only; attr requried"}, 
			{"MANDTST15", "With Use in WO only; attr requried"}, 
			{"MANDTST16", "With Use in Asset only; attr requried"}, 
			{"MANDTST17", "With Use in Asset and WO; attr requried"},
			{"MANDTST18", "With Use in Location only; attr requried"}, 
			{"MANDTST19", "With Use in Location and WO; attr requried"}, 
			{"MANDTST20", "With Use in CM only; attr requried"}, 
			{"MANDTST21", "With Use in CM and WO; attr requried"} };
    }
   
    @Test(dataProvider = "classifications")
	void createClassifications(String name, String description)  {
    	testCase = "createClassifications";
   		extentTest.log(LogStatus.INFO, testCase);
		Log.startTestCase(testCase);
		try {
			Log.info("START Create Classification..."+name);
			action.goToClassificationsPage();
			action.goToAddClassification();
			
			action.clickNew();
			action.input("txtbx_Classification", name);
			action.input("txtbx_ClassificationDescription", description);
			action.input("txtbx_Organization", "KIWIRAIL");
			action.input("txtbx_Site", "NETWORK");
			action.clickOK();
			Log.info("END Create Classification..."+name);
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found createClassifications--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception createClassifications--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
	
/*	
    
//    @Test(dataProvider = "assignment")
	void assignClassifications(String classification, String[] useWith, String[][] attributeObject)  {
		testCase = "assignClassifications";
   		extentTest.log(LogStatus.INFO, testCase);
		Log.startTestCase(testCase);
		try {
			btn_NewRow_UseWith=//table[contains(@aria-label, 'Use With Table Button Group')]/tbody/tr/td/button[@type='button' and contains (., 'New Row')]
					btn_NewRow_Attributes=//table[contains(@aria-label, 'Attributes Table Button Group')]//button[@type='button' and contains (., 'New Row')]
					txtbx_useWithObject=//input[@id=(//label[text()='Use With Object:']/@for)]
					txtbx_Attribute=//input[@id=(//label[text()='Attribute:']/@for)]
					txtbx_DataType=//input[@id=(//label[text()='Data Type:']/@for)]
					
			Log.info("Assign Classification..."+classification);
			action.goToClassificationsPage();
			action.goToAddClassification();
			
			action.quickSearch(classification);
		
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
			extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (NoSuchElementException e) {
	    	Log.error("Element not found --- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    } catch (Exception e) {
	    	Log.error("Exception --- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
	    	Base.bResult = false;
 			Assert.fail();
	    }	
	}
*/	
	
//    String[] objectUse = {"WORKORDER","SR","JobPlan","Locations","Features","Asset"};
	String[] objectUse = {"WORKORDER"};
	
	@Test
	void checkClassificationAssignment1() {
	try {	
		testCase = "checkClassificationAssignment1";
   		extentTest.log(LogStatus.INFO, testCase);
		Log.startTestCase(testCase);
		
		String[][] attributeMANDTST1 = {{"ATTRBTEST1","ATTRBTEST2","ATTRBTEST3"},{"WORKORDER","",""}}; //attribute & priority
		
		Log.info("START checkClassificationAssignment1...");
		assignClassifications("MANDTST2", objectUse, attributeMANDTST1);
		Log.info("END checkClassificationAssignment1...");
	} catch(AssertionError ae){
		Log.error("Assertion failed assignClassifications--- " + ae.getMessage());
		extentTest.log(LogStatus.ERROR, ae.getMessage());
    	Base.bResult = false;
		Assert.fail();
    } catch (NoSuchElementException e) {
    	Log.error("Element not found assignClassifications--- " + e.getMessage());
    	extentTest.log(LogStatus.ERROR, e.getMessage());
    	Base.bResult = false;
		Assert.fail();
    } catch (Exception e) {
    	Log.error("Exception assignClassifications--- " + e.getMessage());
    	extentTest.log(LogStatus.ERROR, e.getMessage());
    	Base.bResult = false;
		Assert.fail();
    }	
	}
	
	@Test
	void checkClassificationAssignment2() {
		testCase = "checkClassificationAssignment2";
   		extentTest.log(LogStatus.INFO, testCase);
		Log.startTestCase(testCase);
	
	try {	
		String[][] attributeMANDTST2 = {{"ATTRBTEST1","ATTRBTEST2","ATTRBTEST3"},{"","",""}};
		
		Log.info("START checkClassificationAssignment2...");
		assignClassifications("MANDTST2", objectUse, attributeMANDTST2);
		Log.info("END checkClassificationAssignment2...");
	} catch(AssertionError ae){
		Log.error("Assertion failed assignClassifications--- " + ae.getMessage());
		extentTest.log(LogStatus.ERROR, ae.getMessage());
    	Base.bResult = false;
		Assert.fail();
    } catch (NoSuchElementException e) {
    	Log.error("Element not found assignClassifications--- " + e.getMessage());
    	extentTest.log(LogStatus.ERROR, e.getMessage());
    	Base.bResult = false;
		Assert.fail();
    } catch (Exception e) {
    	Log.error("Exception assignClassifications--- " + e.getMessage());
    	extentTest.log(LogStatus.ERROR, e.getMessage());
    	Base.bResult = false;
		Assert.fail();
    }
	}
	
	@Test
	void checkClassificationAssignment3() {
		testCase = "checkClassificationAssignment3";
   		extentTest.log(LogStatus.INFO, testCase);
		Log.startTestCase(testCase);
	
	try {	
		String[][] attributeMANDTST3 = {{"ATTRBTEST1","ATTRBTEST2","ATTRBTEST3"},{"WORKORDER","SR",""}};
		
		Log.info("START checkClassificationAssignment3...");
		assignClassifications("MANDTST3", objectUse, attributeMANDTST3);
		Log.info("END checkClassificationAssignment3...");
	} catch(AssertionError ae){
		Log.error("Assertion failed assignClassifications--- " + ae.getMessage());
		extentTest.log(LogStatus.ERROR, ae.getMessage());
    	Base.bResult = false;
		Assert.fail();
    } catch (NoSuchElementException e) {
    	Log.error("Element not found assignClassifications--- " + e.getMessage());
    	extentTest.log(LogStatus.ERROR, e.getMessage());
    	Base.bResult = false;
		Assert.fail();
    } catch (Exception e) {
    	Log.error("Exception assignClassifications--- " + e.getMessage());
    	extentTest.log(LogStatus.ERROR, e.getMessage());
    	Base.bResult = false;
		Assert.fail();
    }	
	}
	
	@Test
	void checkClassificationAssignment4() {
		testCase = "checkClassificationAssignment4";
   		extentTest.log(LogStatus.INFO, testCase);
		Log.startTestCase(testCase);
	
	try {	
		String[] objectUseMANDTST4 = {"JobPlan"};
		String[][] attributeMANDTST4 = {{"","",""},{"","",""}};
		
		Log.info("START checkClassificationAssignment4...");
		assignClassifications("MANDTST4", objectUseMANDTST4, attributeMANDTST4);
		Log.info("END checkClassificationAssignment4...");
	} catch(AssertionError ae){
		Log.error("Assertion failed assignClassifications--- " + ae.getMessage());
		extentTest.log(LogStatus.ERROR, ae.getMessage());
    	Base.bResult = false;
			Assert.fail();
    } catch (NoSuchElementException e) {
    	Log.error("Element not found assignClassifications--- " + e.getMessage());
    	extentTest.log(LogStatus.ERROR, e.getMessage());
    	Base.bResult = false;
			Assert.fail();
    } catch (Exception e) {
    	Log.error("Exception assignClassifications--- " + e.getMessage());
    	extentTest.log(LogStatus.ERROR, e.getMessage());
    	Base.bResult = false;
			Assert.fail();
    }	
	}
	
	void assignClassifications(String classification, String[] useWith, String[][] attributeObject)  {
		
		try {
			Log.info("START assignClassifications...");
			
			String btn_NewRow_UseWith="//table[contains(@aria-label, 'Use With Table Button Group')]/tbody/tr/td/button[@type='button' and contains (., 'New Row')]";
			String btn_NewRow_Attributes="//table[contains(@aria-label, 'Attributes Table Button Group')]//button[@type='button' and contains (., 'New Row')]";
            String useWithObject="//input[@id=(//label[text()='Use With Object:']/@for)]";
            String attribute="//input[@id=(//label[text()='Attribute:']/@for)]";
            String useWithObjectButtonLine1="//span[contains(@id,'_tdrow_[C:7]_hyperlink-lb[R:0]')]";
            String useWithObjectButtonLine2="//span[contains(@id,'_tdrow_[C:7]_hyperlink-lb[R:1]')]";
           	String useWithObjectButtonLine3="//span[contains(@id,'_tdrow_[C:7]_hyperlink-lb[R:2]')]";
			
			Log.info("Assign Classification..."+classification);
			action.waitElementExists("btn_New");
			driver.findElement(By.xpath(OR.getProperty("btn_New"))).click();
			driver.findElement(By.xpath("//input[@id=(//label[text()='Classification:']/@for)]")).sendKeys(classification);
		
			Log.info("useWith array...");
			for (int x=0; x<useWith.length; x++) {
				Log.info("useWith.length = "+useWith.length);
//				Log.info("useWith array..."+i+"="+useWith[i]);
				action.waitElementExists(By.xpath(btn_NewRow_UseWith));
				driver.findElement(By.xpath(btn_NewRow_UseWith)).click();
				action.waitElementExists(By.xpath(useWithObject));
				driver.findElement(By.xpath(useWithObject)).sendKeys(useWith[x]);
			}
		
//			3 - # of attributes
			for (int y=0; y<3; y++) {
				Log.info("attributeObject[0][y].toString()..."+y+"="+attributeObject[0][y].toString());

				action.waitElementExists(By.xpath(btn_NewRow_Attributes));
				driver.findElement(By.xpath(btn_NewRow_Attributes)).click();
				Log.info("NewRow button is clicked.....");
				Log.info("Field attribute is displayed = "+driver.findElement(By.xpath(attribute)).isDisplayed());
				action.waitElementExists(By.xpath(attribute));
				driver.findElement(By.xpath(attribute)).sendKeys(attributeObject[0][y].toString());
				
				if (attributeObject[1][y] != "") {
					driver.findElement(By.xpath("//span[contains(@id,'_tdrow_[C:7]_hyperlink-lb[R:"+y+"]')]")).click();
					Thread.sleep(2000);
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
							Thread.sleep(2000);
							Log.info("clicking checkbox.." + attributeObject[1][z].toString());
//							Log.info("checkbox value.." + driver.findElement(By.xpath("//img[contains(@id,'_tdrow_[C:3]_checkbox-cb[R:"+z+"]_img')]")).getAttribute("clicked"));
							Thread.sleep(2000);
						}
					}
					driver.findElement(By.xpath(OR.getProperty("btn_OK"))).click();
			    }
			}	
			action.save();
			
			Log.info("useWith array...");
			for (int x=0; x<useWith.length; x++) {
				Log.info("useWith.length = "+useWith.length);
//				Log.info("useWith array..."+i+"="+useWith[i]);
				driver.findElement(By.xpath(btn_NewRow_UseWith)).click();
				action.waitElementExists(By.xpath(useWithObject));
				driver.findElement(By.xpath(useWithObject)).sendKeys(useWith[x]);
			}
			
			action.save();
			
			Log.info("END assignClassifications...");
	    } catch (Exception e) {
	    	Log.error("Exception assignClassifications--- " + e.getMessage());
	    	extentTest.log(LogStatus.ERROR, e.getMessage());
 			Assert.fail();
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
