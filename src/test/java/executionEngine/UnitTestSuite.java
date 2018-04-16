package executionEngine;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import config.Constants;
import utility.Log;

@RunWith(Suite.class)
@SuiteClasses({WOUnitTests.class})
public class UnitTestSuite {
protected WebDriver driver = null;
	
	
	
}
