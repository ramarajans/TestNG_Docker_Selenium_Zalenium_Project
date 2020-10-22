package com.selenium.docker;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Utils.GenUtils;

public class TestFirefox {

	static RemoteWebDriver driver;

	@BeforeClass
	public void setup()throws MalformedURLException{

		System.out.println("Running Test in Docker container <<Firefox>>");

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName("firefox");
		cap.setCapability("zal:name", "Firefox Test");
		cap.setPlatform(Platform.LINUX);
		cap.setVersion("");

		//Zalenium
		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);

		//Reg Docker Selenium
		//driver = new RemoteWebDriver(new URL("http://10.50.25.39:4545/wd/hub"), cap);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test
	public void googleSearch()throws InterruptedException{

		driver.navigate().to("http://www.youtube.com/");
		Thread.sleep(20000);
		driver.findElement(By.xpath("//input[@id='search']")).sendKeys("Virat Kohli");
		driver.findElement(By.xpath("//button[@id='search-icon-legacy']")).click();
		System.out.println("Search completed(Firefox)");
	}

	@AfterClass
	public void tearDown()throws Exception{

		if(driver!=null){
			System.out.println("Completed testing in Docker container <<firefox>>");
			driver.quit();
		}
	}
}
