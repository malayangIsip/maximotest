package executionEngine;

import static executionEngine.Base.action;
import static executionEngine.Base.driver;
import static executionEngine.Base.extentTest;
import static executionEngine.Base.extent;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import utility.Log;
import utility.RetryAnalyzer;

public abstract class TestAutomation {
    static String methodName = "";
    static String testName = "";
    
    public abstract void init(); 
    
    public void tearDown() {
		driver.quit();
    }
        
    public void setUp() throws Exception {

    }
    
    public void logout() {
    	
    }
    
    public void logout(String testName, String testCase) {
    	if (Base.bResult == true) {
    		extentTest.log(LogStatus.PASS, testCase);
    		driver.close();
    	} else {
//    		Log.info("RetryAnalyzer.getRetryCounter() >>>>>>>>>>>>>"+RetryAnalyzer.getRetryCounter());
//    		Log.info("Base.retryLimit >>>>>>>>>>>>>"+Base.retryLimit);
    		if (RetryAnalyzer.getRetryCounter() == Base.retryLimit) {
//    			Log.info("Fail test...................");
    			extentTest.log(LogStatus.FAIL, testCase);
    			try {
					action.takeScreenshot(testName +" - "+testCase);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		driver.close();
    	}
    	Log.endTestCase(testCase);
    }

}
