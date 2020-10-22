package com.selenium.docker;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class TestChrome2 {

	/*@BeforeTest
	public void setup()throws MalformedURLException{
	
		System.out.println("Running Test in Docker container <<Chrome2>>");
		
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName("chrome");
		cap.setCapability("zal:name", "Chrome Test2");
		cap.setPlatform(Platform.LINUX);
		cap.setVersion("");
		
		//Zalenium
		RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		
		//Reg Docker Selenium
		//driver = new RemoteWebDriver(new URL("http://10.50.25.39:4545/wd/hub"), cap);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}*/
	
	@Test
	public void googleSearch()throws InterruptedException, MalformedURLException{
		
		System.out.println("Running Test in Docker container <<Chrome>>");
		long id = Thread.currentThread().getId();
		System.out.println("googleSearch Successful, the thread count is: " + id);

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName("chrome");
		cap.setCapability("zal:name", "Chrome Test1");
		cap.setPlatform(Platform.LINUX);
		cap.setVersion("");
		
		//Zalenium
		RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		
		//Reg Docker Selenium
		//driver = new RemoteWebDriver(new URL("http://10.50.25.39:4545/wd/hub"), cap);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		driver.get("https://www.facebook.com/");
		Thread.sleep(20000);
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("test@abc.com");
		
		System.out.println("Search completed(Chrome)");
	}
	
	@Test
	public void googleSearch2()throws InterruptedException, MalformedURLException{
		
		System.out.println("Running Test in Docker container <<Chrome2>>");
		long id = Thread.currentThread().getId();
		System.out.println("googleSearch2 Successful, the thread count is: " + id);

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName("chrome");
		cap.setCapability("zal:name", "Chrome Test2");
		cap.setPlatform(Platform.LINUX);
		cap.setVersion("");
		
		//Zalenium
		RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		
		//Reg Docker Selenium
		//driver = new RemoteWebDriver(new URL("http://10.50.25.39:4545/wd/hub"), cap);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		driver.get("https://www.linkedin.com/");
		Thread.sleep(20000);
		driver.findElement(By.xpath("//input[@id='email-or-phone']")).sendKeys("test@abc.com");
		
		System.out.println("Search completed(Chrome2)");
	}
	
	@Test
	public void googleSearch3()throws InterruptedException, MalformedURLException{
		
		System.out.println("Running Test in Docker container <<Firefox3>>");
		long id = Thread.currentThread().getId();
		System.out.println("googleSearch3 Successful, the thread count is: " + id);

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName("firefox");
		cap.setCapability("zal:name", "Firefox Test3");
		cap.setPlatform(Platform.LINUX);
		cap.setVersion("");
		
		//Zalenium
		RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		
		//Reg Docker Selenium
		//driver = new RemoteWebDriver(new URL("http://10.50.25.39:4545/wd/hub"), cap);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.get("https://www.instagram.com/");
		Thread.sleep(20000);
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("test@insta.com");
		
		System.out.println("Search completed(Firefox3)");
	}
	
	@Test
	public void googleSearch4()throws InterruptedException, MalformedURLException{
		
		System.out.println("Running Test in Docker container <<Chrome4>>");
		long id = Thread.currentThread().getId();
		System.out.println("googleSearch4 Successful, the thread count is: " + id);

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName("chrome");
		cap.setCapability("zal:name", "Chrome Test4");
		cap.setPlatform(Platform.LINUX);
		cap.setVersion("");
		
		//Zalenium
		RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		
		//Reg Docker Selenium
		//driver = new RemoteWebDriver(new URL("http://10.50.25.39:4545/wd/hub"), cap);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		driver.get("https://www.twitter.com/");
		Thread.sleep(20000);
		driver.findElement(By.xpath("//span[text()='Sign up']")).click();
		
		System.out.println("Search completed(Chrome4)");
	}
	
	/*@AfterTest
	public void tearDown()throws Exception{
		
		if(driver!=null){
			System.out.println("Completed testing in Docker container <<chrome2>>");
			driver.quit();
		}
	}*/
}
