package com.selenium.docker.zalenium;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Utils.GenUtils;
import Utils.GenericUtils;

public class AutoUserCreation {

	HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
	ChromeOptions options = new ChromeOptions();
	DesiredCapabilities cap = new DesiredCapabilities();
	
	static RemoteWebDriver driver;
	public static FluentWait<WebDriver> wait = null;
	public static String url = "https://ccf-390.wdf.sap.corp/ui#HRAdministration-import";
	public static String userName = "ADMINISTRATOR";
	public static String password = "Welcome1!";

	@BeforeClass
	public void setup()throws IOException, Exception{

		System.out.println("Running Test in Docker container <<Chrome>>");

		GenUtils.setUpDocker("Zalenium");

		long id = Thread.currentThread().getId();
		System.out.println("googleSearch Chrome, the thread count is: " + id);

		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.prompt_for_download", "true");

		chromePrefs.put("safebrowsing.enabled", "true");

		options.setExperimentalOption("prefs", chromePrefs);

		cap.setBrowserName("chrome");
		// cap.setCapability("zal:name", "Chrome Test");
		cap.setPlatform(Platform.WINDOWS);
		cap.setVersion("");
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);

		//Zalenium
		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);

		
		//Reg Docker Selenium
		//driver = new RemoteWebDriver(new URL("http://localhost:4446/wd/hub"), cap);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test
	public void googleSearch()throws Exception{

		/*
		 * driver.get("https://www.youtube.com/"); Thread.sleep(20000);
		 * driver.findElement(By.xpath("//input[@id='search']")).
		 * sendKeys("Sachin Tendulkar");
		 * driver.findElement(By.xpath("//button[@id='search-icon-legacy']")).click();
		 * 
		 * System.out.println("Search completed(Chrome)");
		 */	
		
		importEmployees();	
	
	}


	@AfterClass
	public void tearDown()throws Exception{

		if(driver!=null){
			System.out.println("Completed testing in Docker container <<chrome>>");
			driver.quit();
		}

		GenUtils.downDocker("Zalenium");
	}

	public void importEmployees() throws Exception {

		launchURL(url);
		loginApp();

		System.out.println("Inside Import Employees");

		wait("//span[contains(@id,'selectImport-arrow')]");	
		//selectByVisibleText(By.xpath("//select[contains(@id,'selectImport')]"),"Employee and Employment Import");
		click(By.xpath("//span[contains(@id,'selectImport-arrow')]"));
		click(By.xpath("//ul/li[text()='Employee and Employment Import']"));
		
		Thread.sleep(30000);
		driver.findElement(By.xpath("//input[contains(@id,'employeeUpload')]")).sendKeys(Keys.TAB);
		System.out.println("Pressed TAB");
		//click(By.xpath("//input[contains(@id,'employeeUpload')]"));
		actionsClick(By.xpath("//button//bdi[text()='Browse...']"));
		
		Runtime.getRuntime().exec("C:\\Users\\I341067\\OneDrive - SAP SE\\Documents\\Learnings\\AutoIt\\Files\\ImportEmployeeData.exe");

		click(By.xpath("//input[contains(@id,'employeeUpload')]"));

		Runtime.getRuntime().exec("C:\\Users\\I341067\\OneDrive - SAP SE\\Documents\\Learnings\\AutoIt\\Files\\ImportEmploymentData.exe");

		DateFormat dateFormat = new SimpleDateFormat("ddMMyyHHMMss");
		Date date = new Date();
		String currentTimeStamp = dateFormat.format(date);

		wait("//input[contains(@id,'ImportName')]");
		type(By.xpath("//input[contains(@id,'ImportName')]") , "Import_"+currentTimeStamp);

		click(By.xpath("//button//bdi[text()='Import']"));

		Thread.sleep(15000);
	}

	public static void launchURL(String url)throws Exception{

		driver.get(url);
		Thread.sleep(5000);
		driver.manage().window().maximize();
	}

	public static void loginApp()throws Exception{

		String headerLogo = "//a[@id='shell-header-logo']";
		wait("//input[@id='USERNAME_FIELD-inner' or @id='j_username']");
		type(By.xpath("//input[@id='USERNAME_FIELD-inner' or @id='j_username']"), userName);
		type(By.xpath("//input[@id='PASSWORD_FIELD-inner' or @id='j_password']"), password);
		click(By.xpath("//button[@id='LOGIN_LINK' or @id='logOnFormSubmit']"));

		Thread.sleep(30000);

		//Authentication Information pop-up
		if(driver.findElements(By.xpath("//button//bdi[text()='Close']")).size()==1){
			System.out.println("Authentication pop-up is displayed.Closing it");
			click(By.xpath("//button//bdi[text()='Close']"));
		}
		/*
		 * for(int i=0;i<6;i++){ if(driver.findElements(By.xpath(headerLogo)).size()!=1)
		 * Thread.sleep(10000); else break; }
		 * 
		 */	
		
		wait(headerLogo);
	}

	public static void wait(String locator)throws Exception{

		wait = new FluentWait<WebDriver>(driver);
		Thread.sleep(10000);
		wait.withTimeout(100, TimeUnit.SECONDS)
		.ignoring(NoSuchElementException.class)
		.pollingEvery(5, TimeUnit.SECONDS)
		.withMessage("checking for the element")
		.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
	}
	
	public static void type(By locator , String value)throws Exception{

		driver.findElement(locator).sendKeys(value);
		Thread.sleep(1000);
	}

	public static void click(By locator)throws Exception{

		driver.findElement(locator).click();
		Thread.sleep(10000);
	}

	public static void actionsClick(By locator)throws Exception{

		new Actions(driver).moveToElement(driver.findElement(locator)).click().perform();
		Thread.sleep(10000);
	}

	public static void selectByVisibleText(By locator, String value)throws Exception{

		Select sel = new Select(driver.findElement(locator));
		sel.selectByVisibleText(value);
		Thread.sleep(10000);
	}

}

