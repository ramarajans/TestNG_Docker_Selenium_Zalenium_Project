package com.selenium.docker;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Utils.GenUtils;

public class TestChrome {

	static RemoteWebDriver driver;
	
	
	@BeforeClass
	public void setup()throws IOException, Exception{
	
		System.out.println("Running Test in Docker container <<Chrome>>");
		
	//	GenUtils.setUpDocker("Selenium");
		
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.prompt_for_download", "true");

		chromePrefs.put("safebrowsing.enabled", "true"); 

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName("chrome");
		//cap.setCapability("zal:name", "Chrome Test");
		cap.setPlatform(Platform.LINUX);
		cap.setVersion("");
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		
		
		//Zalenium
		//driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		
		//Reg Docker Selenium
		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	
	@Test(invocationCount = 5, threadPoolSize = 4)
	public void googleSearch()throws InterruptedException{
		
		long id = Thread.currentThread().getId();
		System.out.println("googleSearch Chrome, the thread count is: " + id);

		driver.get("https://ccf-390.wdf.sap.corp/ui#HRAdministration-import");
		Thread.sleep(20000);
		/*
		 * driver.findElement(By.xpath("//input[@id='search']")).
		 * sendKeys("Sachin Tendulkar");
		 * driver.findElement(By.xpath("//button[@id='search-icon-legacy']")).click();
		 */
		System.out.println("Search completed(Chrome)");
	}
	
	
	@AfterClass
	public void tearDown()throws Exception{
		
		if(driver!=null){
			System.out.println("Completed testing in Docker container <<chrome>>");
			driver.quit();
		}
		
		//GenUtils.downDocker("Selenium");
	}
}
