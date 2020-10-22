package com.selenium.docker.zalenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class TestChromeZalenium {

	@Test
	public void googleSearch()throws InterruptedException, MalformedURLException{
		
		System.out.println("Running Test in Docker container <<Chrome Zalenium>>");
		long id = Thread.currentThread().getId();
		System.out.println("googleSearch Chrome, the thread count is: " + id);

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName("chrome");
		cap.setCapability("zal:name", "Chrome Test1");
		cap.setPlatform(Platform.LINUX);
		cap.setVersion("");
		
		//Zalenium
		RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		driver.get("https://www.facebook.com/");
		Thread.sleep(20000);
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("test@abc.com");
		
		System.out.println("Search completed(Chrome Zalenium)");
	}
}
