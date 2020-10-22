package com.selenium.docker.zalenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class TestFirefoxZalenium {

	@Test
	public void googleSearch()throws InterruptedException, MalformedURLException{

		System.out.println("Running Test in Docker container <<Firefox Zalenium>>");
		long id = Thread.currentThread().getId();
		System.out.println("googleSearch Firefox, the thread count is: " + id);

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName("firefox");
		cap.setCapability("zal:name", "Firefox Test");
		cap.setPlatform(Platform.LINUX);
		cap.setVersion("");
		
		//Zalenium
		RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		driver.navigate().to("http://www.youtube.com/");
		Thread.sleep(20000);
		driver.findElement(By.xpath("//input[@id='search']")).sendKeys("Virat Kohli");
		driver.findElement(By.xpath("//button[@id='search-icon-legacy']")).click();
		System.out.println("Search completed(Firefox)");
	}
}
