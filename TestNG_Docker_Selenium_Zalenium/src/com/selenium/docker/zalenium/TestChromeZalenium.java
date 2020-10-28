package com.selenium.docker.zalenium;

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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Utils.GenUtils;
import testNG.GenericUtils;

public class TestChromeZalenium extends GenericUtils {

	HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
	ChromeOptions options = new ChromeOptions();
	DesiredCapabilities cap = new DesiredCapabilities();

	@BeforeClass
	public void setUp() throws IOException, Exception {

		GenUtils.setUpDocker("Zalenium");

		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.prompt_for_download", "true");

		chromePrefs.put("safebrowsing.enabled", "true");

		options.setExperimentalOption("prefs", chromePrefs);

		cap.setBrowserName("chrome");
		// cap.setCapability("zal:name", "Chrome Test");
		cap.setPlatform(Platform.LINUX);
		cap.setVersion("");
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);

	}

	@Test
	public void googleSearch() throws InterruptedException, MalformedURLException {

		System.out.println("Running Test in Docker container <<Chrome Zalenium>>");
		long id = Thread.currentThread().getId();
		System.out.println("googleSearch Chrome, the thread count is: " + id);

		cap.setBrowserName("chrome");
		// cap.setCapability("zal:name", "Chrome Test");
		cap.setPlatform(Platform.LINUX);
		cap.setVersion("");
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);

		// Zalenium
		RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		driver.get("https://www.facebook.com/");
		Thread.sleep(20000);
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("test@abc.com");

		System.out.println("Search completed(Chrome Zalenium)");
	}


	@Test(invocationCount = 1, threadPoolSize =  3 , dataProvider = "data-provider")
	public void googleSearch2(String url)throws InterruptedException, MalformedURLException{

		System.out.println("Running Test in Docker container <<Chrome Zalenium>>");
		long id = Thread.currentThread().getId();
		System.out.println("googleSearch2 Chrome, the thread count is: " + id);

		cap.setBrowserName("chrome"); //cap.setCapability("zal:name", "Chrome Test");
		cap.setPlatform(Platform.LINUX); cap.setVersion("");
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);


		//Zalenium
		RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		driver.get(url); 
		Thread.sleep(20000);
		System.out.println("URL is - "+ url);
		System.out.println("Title is - "+ driver.getTitle());

		System.out.println("Search completed(Chrome2 Zalenium)");
	}


	@AfterClass
	public void tearDown() throws IOException, Exception {
		GenUtils.downDocker("Zalenium");
	}


	@DataProvider (name = "data-provider")
	public Object[][] dpMethod(){
		return new Object[][] {{"https://www.linkedin.com/"}, {"https://www.twitter.com/"},{"https://www.instagram.com/"}};
	}
}
