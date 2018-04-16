package pageObjects;


import static executionEngine.DriverScript.OR;
import static executionEngine.DriverScript.driver;
import static executionEngine.DriverScript.extentTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;

import executionEngine.DriverScript;
import utility.Log;

import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class WOPage {
	public WOPage(WebDriver driver) {
	    PageFactory.initElements(driver, this);
	}

	@FindBy(name = "Work Order:")
    @CacheLookup
    public WebElement txtbx_WONUM;
	
	@FindBy(name = "Status:")
	public WebElement txtbx_Status;
	
	@FindBy(name = "Scheduled Start:")
	public WebElement txtbx_ScheduledStart;
	
	@FindBy(xpath = "//span[contains(@id,'_INV_REQ_DATE_OPTION_a_tnode')]")
	public WebElement lnk_SetSchedDate;
	
	@FindBy(xpath = "//input[@id=(//label[text()=\"Storeroom:\"]/@for)]")
	public WebElement txtbx_Storeroom;	
	
	@FindBy(xpath = "//img[contains(@id,'INSERT-tbb_')]")
	public WebElement btn_New;
	
	public void getStoreroom() {
		try {
			driver.findElement(By.xpath(OR.getProperty("btn_Storeroom_chevron"))).click();
			driver.findElement(By.xpath(OR.getProperty("lnk_SelectValue"))).click();
			waitFor();
			driver.findElement(By.xpath(OR.getProperty("lnk_List_Code"))).click();
			waitFor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void waitFor() throws Exception{
		try{
			Log.info("Wait...");
			Thread.sleep(1000);
		}catch(Exception e){
			 Log.error("Not able to Wait --- " + e.getMessage());
			 extentTest.log(LogStatus.ERROR, e.getMessage());
			 DriverScript.bResult = false;
        }
	}

}
