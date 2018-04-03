package executionEngine;


import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtils;
import utility.Log;
import utility.Utils;

public class DriverScript {

	public static WebDriver driver;
	public static Properties OR;
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static Method method[];

	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static String sData;
	public static boolean bResult;
	public static int counter = 0;

	public static String storedValue;
	public static String WONUM = null;
	public static String ASSETNUM = null;
	public static String PRNUM = null;
	public static String PONUM = null;
	public static String Storeroom = null;
	public static String PMNUM = null;
	public static String RouteNum= null;
	public static String JPNum= null;
	public static String Template = null;
	public static String SRNUM = null;
	public static String LOCATION = null;
	public static String POINT = null;
	public static String WFApprover = null;

	
	public DriverScript() throws NoSuchMethodException, SecurityException {
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();
	}
	
	@Test
	public void main() throws Exception {
		ExcelUtils.setExcelFile(Constants.Path_TestData);
		DOMConfigurator.configure("log4j.xml");
		String Path_OR = Constants.Path_OR;
		FileInputStream fs = new FileInputStream(Path_OR);
		OR = new Properties(System.getProperties());
		OR.load(fs);

		DriverScript startEngine = new DriverScript();
		
		int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
		for (int iTestcase = 1; iTestcase < iTotalTestCases; iTestcase++) {
			bResult = true;
			sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases);
			sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode, Constants.Sheet_TestCases);
			if (sRunMode.equals("Yes")) {
				Log.startTestCase(sTestCaseID);
				Log.info("sTestCaseID="+sTestCaseID);
//				Log.info("iTestcase="+iTestcase);
//				Log.info("sRunMode="+sRunMode);
		        startEngine.execute_TestCase(iTestcase);
//		        Log.info("bResult="+bResult);
//		        if (bResult == true) {
//					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestcase, Constants.Col_Result,
//							Constants.Sheet_TestCases, Constants.Path_TestData);
//					Log.endTestCase(sTestCaseID);
//				} 
			}
		}	
	}

	private void execute_TestCase(int testCase) throws Exception {
		ExcelUtils.setExcelFile(Constants.Path_TestFiles+"//"+sTestCaseID+".xlsx");
//		Log.info("Input file path ... "+Constants.Path_TestFiles+"//"+sTestCaseID+".xlsx");
		iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
		iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
		bResult = true;
//		counter = 1;
		for (; iTestStep < iTestLastStep; iTestStep++) {
			sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,
				Constants.Sheet_TestSteps);
			Log.info("sActionKeyword 1: " + sActionKeyword);
			sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject,
				Constants.Sheet_TestSteps);
			Log.info("sPageObject 1: " + sPageObject);
			sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
			Log.info("sData 1: " + sData);
			execute_Actions();
			if (bResult == false) {
			    ExcelUtils.setExcelFile(Constants.Path_TestData);
			    ExcelUtils.setCellData(Constants.KEYWORD_FAIL, testCase, Constants.Col_Result,
				    Constants.Sheet_TestCases, Constants.Path_TestData);
			    Log.endTestCase(sTestCaseID);
			    break;
			}
		}
		if (bResult == true) {
			ExcelUtils.setExcelFile(Constants.Path_TestData);
			ExcelUtils.setCellData(Constants.KEYWORD_PASS, testCase, Constants.Col_Result,
				Constants.Sheet_TestCases, Constants.Path_TestData);
			Log.endTestCase(sTestCaseID);
		}
	}	
	
	
	private static void execute_Actions() throws Exception {
		for (int i = 0; i < method.length; i++) {
			if (method[i].getName().equals(sActionKeyword)) {
//				Log.info("method[i].getName(): " + method[i].getName());
//				Log.info("sActionKeyword 2: " + sActionKeyword.toString());
				method[i].invoke(actionKeywords, sPageObject, sData);
				if (bResult == true) {
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult,
							Constants.Sheet_TestSteps, Constants.Path_TestFiles+"//"+sTestCaseID+".xlsx");
					break;
				} else {
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult,
							Constants.Sheet_TestSteps, Constants.Path_TestFiles+"//"+sTestCaseID+".xlsx");
					Utils.takeScreenshot(driver, sTestCaseID);
					Log.error("Error executing: " + sActionKeyword);
					ActionKeywords.closeBrowser("","");
					break;
				}
			}
		}
	}

	@AfterSuite
    public void tearDown() throws Exception {
        driver.quit();
    }
}