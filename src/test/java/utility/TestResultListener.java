package utility;

import static executionEngine.Base.action;
import static executionEngine.Base.driver;
import static executionEngine.Base.extentTest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.relevantcodes.extentreports.LogStatus;

import executionEngine.Base;

public class TestResultListener extends TestListenerAdapter {
	
	@Override
	public void onTestFailure(ITestResult result) {
		if (RetryAnalyzer.getRetryCounter() == Base.retryLimit) {
			Log.info("Fail test...................");
			extentTest.log(LogStatus.FAIL, result.getName());
			try {
				action.takeScreenshot(result.getTestClass() +" - "+result.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		driver.close();
		Log.endTestCase(result.getTestName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.log(LogStatus.PASS, result.getName());
		driver.close();
		Log.endTestCase(result.getTestName());
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.log(LogStatus.SKIP, result.getName());
		driver.close();
	}
}
