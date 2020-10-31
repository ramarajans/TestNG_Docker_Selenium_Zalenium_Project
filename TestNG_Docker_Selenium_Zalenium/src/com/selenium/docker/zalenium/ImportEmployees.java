package com.selenium.docker.zalenium;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ImportEmployees {

	public static String GenericFolderPath = null;
	public static String FLPUserFolderPath = null;
	public static String driverPath = null;
	public static WebDriver driver = null;
	public static FluentWait<WebDriver> wait = null;
	public static Map<String,String> clientUsersMap = new HashMap<String,String>();
	public static String system = null;
	public static List<String> clients = new ArrayList<String>();


	public static String excelFilePath = null, configFile = null;
	public static List<String> scopeItems = new ArrayList<String>();
	public static String testPlanName = null;
	public static List<String> columnNames = new ArrayList<String>();

	public static void main(String[] args) throws Exception {

		GenericFolderPath = System.getProperty("user.home") + "//OneDrive - SAP SE//Documents//Generic User Creation";

		configFile = GenericFolderPath+"//UserCreationConfig.xlsx";
		System.out.println("Config File : "+configFile);

		readExcel(configFile);

		for(int i=0;i<clients.size();i++) {

			initSettings();

			String currentClient = clients.get(i);
			//https://ccf-715.wdf.sap.corp/ui#HRAdministration-import
			String systemURL = "https://"+system+"-"+currentClient+".wdf.sap.corp/ui#HRAdministration-import";
			launchURL(systemURL);
			loginApp("ADMINISTRATOR","Welcome1!");
			String CSVFilesPath = GenericFolderPath+"\\"+clientUsersMap.get(currentClient)+"\\"+currentClient;
			System.out.println("CSV Files Path --> "+CSVFilesPath);
			importEmployees(CSVFilesPath);
			Thread.sleep(15000);
			driver.quit();
		}
	}

	public static void initSettings()throws Exception{	

		driverPath = System.getProperty("user.home")+"\\OneDrive - SAP SE\\Desktop\\SeleniumFiles";
		//driverPath = System.getProperty("user.dir");

		System.setProperty("webdriver.chrome.driver",driverPath+"\\chromedriver.exe");

		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.prompt_for_download", "true");

		chromePrefs.put("safebrowsing.enabled", "true"); 
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(cap);

		//Defining wait
		wait = new WebDriverWait(driver, 200);
	}
	
	public static void launchURL(String url)throws Exception{

		driver.get(url);
		waitForPageLoaded();
		driver.manage().window().maximize();
	}

	public static void loginApp(String user, String pwd)throws Exception{

		String headerLogo = "//a[@id='shell-header-logo']";
		wait("//input[@id='USERNAME_FIELD-inner' or @id='j_username']");
		type(By.xpath("//input[@id='USERNAME_FIELD-inner' or @id='j_username']"), user);
		type(By.xpath("//input[@id='PASSWORD_FIELD-inner' or @id='j_password']"), pwd);
		click(By.xpath("//button[@id='LOGIN_LINK' or @id='logOnFormSubmit']"));

		waitForPageLoaded();
		System.out.println("Page loaded successfully");
		//Authentication Information pop-up
		if(driver.findElements(By.xpath("//button//bdi[text()='Close']")).size()==1){
			System.out.println("Authentication pop-up is displayed.Closing it");
			click(By.xpath("//button//bdi[text()='Close']"));
		}

		wait(headerLogo);
	}	

	@SuppressWarnings("deprecation")
	public static void readExcel(String configFilePath) throws IOException {

		String key = null, value = null;

		FileInputStream ips = new FileInputStream(new File(configFilePath)); 
		XSSFWorkbook workbook = new XSSFWorkbook(ips);
		Cell cell = null;

		Sheet sh = workbook.getSheet("Config");

		Row row = sh.getRow(0);
		cell = row.getCell(1);

		cell.setCellType(Cell.CELL_TYPE_STRING);	
		system = cell.getStringCellValue();

		row = sh.getRow(1);

		//To get the Column Names
		for(int i=0;i<row.getLastCellNum();i++){
			cell = row.getCell(i);
			cell.setCellType(Cell.CELL_TYPE_STRING);	
			value = cell.getStringCellValue();

			if(value.isEmpty())
				break;
			else
				columnNames.add(value);
		}

		System.out.println("columnNames List : "+columnNames);

		int rowCount = sh.getLastRowNum() - sh.getFirstRowNum();
		System.out.println("Excel rowCount : "+rowCount);

		//To get the Configuration
		for(int j=1;j<rowCount;j++){
			row = sh.getRow(j+1);
			cell = row.getCell(columnNames.indexOf("Client"));
			cell.setCellType(Cell.CELL_TYPE_STRING);

			key = cell.getStringCellValue();

			cell = row.getCell(columnNames.indexOf("User Name"));
			cell.setCellType(Cell.CELL_TYPE_STRING);
			value = cell.getStringCellValue();

			clients.add(key);
			clientUsersMap.put(key, value);
		}

		System.out.println("System --> "+system);
		System.out.println("clients --> "+clients);
		System.out.println("clientUsersMap --> "+clientUsersMap);

		ips.close();
	}


	public static void importEmployees(String folderPath) throws Exception {

		System.out.println("-------Import Employees App-------");

		wait("//span[contains(@id,'selectImport-arrow')]");	
		//click(By.xpath("//span[contains(@id,'selectImport-arrow')]"));
		//click(By.xpath("//ul/li[text()='Employee and Employment Import']"));

		//waitForPageLoaded();
		keyPress(By.xpath("//input[contains(@id,'employeeUpload')]"), Keys.TAB);

		actionsClick(By.xpath("//button//bdi[text()='Browse...']"));

		String employeeDataFilePath = "\""+folderPath+"\\EmployeeDataTemplate.csv"+"\"";
		employeeDataFilePath  = employeeDataFilePath.replace("//", "\\");

		String autoITExe = System.getProperty("user.dir")+"\\ImportCSV.exe";
		System.out.println("autoITExe : "+autoITExe);
		Runtime.getRuntime().exec(autoITExe+" "+employeeDataFilePath);

		actionsClick(By.xpath("//input[contains(@id,'employmentUpload')]"));

		String employmentDataFilePath = "\""+folderPath+"\\EmploymentDataTemplate.csv"+"\"";
		employmentDataFilePath = employmentDataFilePath.replace("//", "\\");

		Runtime.getRuntime().exec(autoITExe+" "+employmentDataFilePath);

		DateFormat dateFormat = new SimpleDateFormat("ddMMyyHHMMss");
		Date date = new Date();
		String currentTimeStamp = dateFormat.format(date);

		wait("//input[contains(@id,'ImportName')]");
		type(By.xpath("//input[contains(@id,'ImportName')]") , "Import_"+currentTimeStamp);

		click(By.xpath("//button//bdi[text()='Import']"));

		Thread.sleep(15000);
		
		click(By.xpath("//button//bdi[text()='OK']"));
				
		System.out.println("User uploaded successfully!!!");
	}

	public static int getColumnCount(String tblXpath)throws Exception{

		int colCount = driver.findElements(By.xpath(tblXpath+"/thead/tr/th")).size();

		return colCount;
	}
	public static void keyPress(By locator, Keys key)throws Exception{

		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		driver.findElement(locator).sendKeys(key);
		Thread.sleep(2000);
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


	public static void doubleClick(By locator)throws Exception{

		new Actions(driver).moveToElement(driver.findElement(locator)).doubleClick().perform();
		Thread.sleep(10000);
	}

	public static void actionsClick(By locator)throws Exception{

		new Actions(driver).moveToElement(driver.findElement(locator)).click().perform();
		Thread.sleep(10000);
	}

	public static void type(By locator , String value)throws Exception{

		driver.findElement(locator).sendKeys(value);
		Thread.sleep(1000);
	}

	public static void click(By locator)throws Exception{

		driver.findElement(locator).click();
		Thread.sleep(10000);
	}

	public static void clear(By locator)throws Exception{

		driver.findElement(locator).clear();
		Thread.sleep(2000);
	}

	public static void waitForPageLoaded() {
		ExpectedCondition<Boolean> expectation = new
				ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver1) {
				return ((JavascriptExecutor) driver1).executeScript("return document.readyState").toString().equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		} catch (Throwable error) {
			System.out.println("Timeout waiting for Page Load Request to complete.");
		}
	}
}
