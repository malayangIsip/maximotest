package executionEngine;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import utility.Constants;
import utility.Log;

public class UnitTest {

	protected WebDriver driver = null;
	
	public void openApp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:/Users/mme9310/Documents/lib/chromedriver.exe");
		
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		 
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
	    capabilities.setBrowserName("chrome");
	    
		driver=new ChromeDriver(chromeOptions);
		DesiredCapabilities dc = new DesiredCapabilities();
		
		int implicitWaitTime=(1);
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		Log.info("Navigating to URL");
		driver.get(Constants.URL);

		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
	}
	
	@Before
	public void setUp() throws Exception {
		openApp();
	}
	
	@After
	public void quit() throws IOException, InterruptedException {
		driver.quit();
	}	
}
