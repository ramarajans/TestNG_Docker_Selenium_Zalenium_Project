package com.selenium.docker;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ParallelTestCheck_TestNG {

	static RemoteWebDriver driver;

	@BeforeTest
	public void setup()throws MalformedURLException{

		System.out.println("Running Test in Docker container <<Chrome>>");

		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setPlatform(Platform.LINUX);
		cap.setVersion("");

		driver = new RemoteWebDriver(new URL("http://10.50.25.39:4545/wd/hub"), cap);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test
	public void googleSearch3()throws InterruptedException{

		driver.get("https://www.youtube.com/");
		Thread.sleep(20000);
		driver.findElement(By.xpath("//input[@id='search']")).sendKeys("Sachin Tendulkar");
		driver.findElement(By.xpath("//button[@id='search-icon-legacy']")).click();

		System.out.println("Search completed(Chrome)");
	}

	@Test    
	public void executSessionOne() throws InterruptedException{
		//Goto guru99 site
		driver.get("http://demo.guru99.com/V4/");
		Thread.sleep(10000);
		//find user name text box and fill it
		driver.findElement(By.name("uid")).sendKeys("Driver 1");
	}

	@Test    
	public void executeSessionTwo() throws InterruptedException{
		//Goto guru99 site
		driver.get("http://demo.guru99.com/V4/");
		Thread.sleep(10000);
		//find user name text box and fill it
		driver.findElement(By.name("uid")).sendKeys("Driver 2");

	}

	@Test    
	public void executSessionThree() throws InterruptedException{
		//Goto guru99 site
		driver.get("http://demo.guru99.com/V4/");
		Thread.sleep(10000);
		//find user name text box and fill it
		driver.findElement(By.name("uid")).sendKeys("Driver 3");
	}

	@AfterTest
	public void tearDown()throws Exception{

		if(driver!=null){
			System.out.println("Completed testing in Docker container <<chrome>>");
			driver.quit();
		}
	}
}