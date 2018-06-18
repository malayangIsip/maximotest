package utility;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import executionEngine.Base;

public class RetryAnalyzer implements IRetryAnalyzer {
	private static int count = 1;
	private static int maxTry = 3;
	 
	int counter = 1;
	int retryLimit = Base.retryLimit;
	/*
	 * (non-Javadoc)
	 * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
	 * 
	 * This method decides how many times a test needs to be rerun.
	 * TestNg will call this method every time a test fails. So we 
	 * can put some code in here to decide when to rerun the test.
	 * 
	 * Note: This method will return true if a tests needs to be retried
	 * and false it not.
	 *
	 */
 
	@Override
	public boolean retry(ITestResult result) {
		Log.info("Checking retry");
		Log.info("retryLimit"+retryLimit);
		Log.info("counter"+counter);
		
		count = counter;
		if(counter < retryLimit)
		{
			counter++;
			
			return true;
		}
		return false;
	}
	
	
	public static int getRetryCounter( ) {
		return count;
	}

}
