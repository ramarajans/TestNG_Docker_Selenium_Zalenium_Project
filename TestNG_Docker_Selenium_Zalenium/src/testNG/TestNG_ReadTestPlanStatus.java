package testNG;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestNG_ReadTestPlanStatus {

	public static String browser = null;
	public static String uRL = null;
	public static String userName = null;
	public static String password = null;
	public static String country = null;
	public static String release = null;
	public static String testPhase = null;

	public static String testPlanName = null;

	public static String driverPath = null;
	public static String excelFilePath = null;
	public static String destFilePath = null;

	public static FluentWait<WebDriver> wait = null;
	public static WebDriver driver = null;

	public static List<String> scopeItems = new ArrayList<String>();;
	public static Map<String,String> configMap = new HashMap<String,String>();
	public static List<String> columnNames = new ArrayList<String>();
	public static Map<String,String> testPlanNames = new HashMap<String,String>();;
	public static Map<String,String> testPlanStatus = new HashMap<String,String>();;


	@BeforeTest
	public static void readExcelValues()throws Exception{
		
		excelFilePath = System.getProperty("user.home") + "//Desktop//CreateAndRunTestPlans.xlsx";
		System.out.println("Excel Path : "+excelFilePath);
		readExcel(excelFilePath);
		
		browser = configMap.get("Browser");
		uRL = configMap.get("URL");
		userName = configMap.get("UserName");
		password = configMap.get("Password");
		release = configMap.get("Release");
		testPhase = configMap.get("TestPhase");
		country = configMap.get("Country");
		driverPath = System.getProperty("user.home")+"\\Desktop\\SeleniumFiles";
		browser = browser.toLowerCase();

		System.out.println("configMap : "+configMap);
	}

	@Test
	public static void test1()throws Exception{
		WebDriver driver1 = null;
		driver1 = initSettings();
		launchURL(driver1);
		loginApp(driver1);

		String tpStatus = null;
		for(int i=0;i<scopeItems.size()/5;i++){

			testPlanName = release+"_TestRun_"+testPhase+"_"+scopeItems.get(i)+"_"+country;
			//testPlanName = release+"_TestRun_"+scopeItems.get(i)+"_"+testPhase;
			System.out.println((i+1)+"."+testPlanName);
			tpStatus = readTestPlanStatus(driver1, testPlanName);

			if("NA".equals(tpStatus))
				testPlanName = "NA";

			Thread.sleep(10000);
			testPlanNames.put(scopeItems.get(i), testPlanName);
			testPlanStatus.put(scopeItems.get(i), tpStatus);
/*
			//5 minutes of wait between every 10 test plans
			if(i>0 && i%10==0){
				System.out.println("Waiting for 30 seconds");
				Thread.sleep(30000);
			}
*/		}

	}

	@Test
	public static void test2()throws Exception{
		WebDriver driver2 = null;
		driver2 = initSettings();
		launchURL(driver2);
		loginApp(driver2);

		String tpStatus = null;
		for(int i=(scopeItems.size()/5)+1;i<(scopeItems.size()/5)*2;i++){

			//testPlanName = release+"_TestRun_"+testPhase+"_"+scopeItems.get(i)+"_"+country;
			testPlanName = release+"_TestRun_"+scopeItems.get(i)+"_"+testPhase;
			System.out.println((i+1)+"."+testPlanName);
			tpStatus = readTestPlanStatus(driver2, testPlanName);

			if("NA".equals(tpStatus))
				testPlanName = "NA";

			Thread.sleep(10000);
			testPlanNames.put(scopeItems.get(i), testPlanName);
			testPlanStatus.put(scopeItems.get(i), tpStatus);

			/*writeExcel(excelFilePath);
			//5 minutes of wait between every 10 test plans
			if(i>0 && i%10==0){
				System.out.println("Waiting for 5 minutes");
				Thread.sleep(150000);
			}*/
		}

	}

	@Test
	public static void test3()throws Exception{
		WebDriver driver3 = null;
		driver3 = initSettings();
		launchURL(driver3);
		loginApp(driver3);

		String tpStatus = null;
		for(int i=(2*scopeItems.size()/5)+1;i<(3*scopeItems.size()/5);i++){

			//testPlanName = release+"_TestRun_"+testPhase+"_"+scopeItems.get(i)+"_"+country;
			testPlanName = release+"_TestRun_"+scopeItems.get(i)+"_"+testPhase;
			System.out.println((i+1)+"."+testPlanName);
			tpStatus = readTestPlanStatus(driver3, testPlanName);

			if("NA".equals(tpStatus))
				testPlanName = "NA";

			Thread.sleep(10000);
			testPlanNames.put(scopeItems.get(i), testPlanName);
			testPlanStatus.put(scopeItems.get(i), tpStatus);

			/*writeExcel(excelFilePath);
			//5 minutes of wait between every 10 test plans
			if(i>0 && i%10==0){
				System.out.println("Waiting for 5 minutes");
				Thread.sleep(150000);
			}*/
		}

	}

	@Test
	public static void test4()throws Exception{
		WebDriver driver4 = null;
		driver4 = initSettings();
		launchURL(driver4);
		loginApp(driver4);

		String tpStatus = null;
		for(int i=(3*scopeItems.size()/5)+1;i<(4*scopeItems.size()/5);i++){
			//testPlanName = release+"_TestRun_"+testPhase+"_"+scopeItems.get(i)+"_"+country;
			testPlanName = release+"_TestRun_"+scopeItems.get(i)+"_"+testPhase;
			System.out.println((i+1)+"."+testPlanName);
			tpStatus = readTestPlanStatus(driver4, testPlanName);

			if("NA".equals(tpStatus))
				testPlanName = "NA";

			Thread.sleep(10000);
			testPlanNames.put(scopeItems.get(i), testPlanName);
			testPlanStatus.put(scopeItems.get(i), tpStatus);

		/*	writeExcel(excelFilePath);
			//5 minutes of wait between every 10 test plans
			if(i>0 && i%10==0){
				System.out.println("Waiting for 5 minutes");
				Thread.sleep(150000);
			}*/
		}

	}

	@Test
	public static void test5()throws Exception{
		WebDriver driver5 = null;
		driver5 = initSettings();
		launchURL(driver5);
		loginApp(driver5);

		String tpStatus = null;
		//65-80
		for(int i=(4*scopeItems.size()/5)+1;i<scopeItems.size();i++){
				
			//testPlanName = release+"_TestRun_"+testPhase+"_"+scopeItems.get(i)+"_"+country;
			testPlanName = release+"_TestRun_"+scopeItems.get(i)+"_"+testPhase;
			System.out.println((i+1)+"."+testPlanName);
			tpStatus = readTestPlanStatus(driver5, testPlanName);

			if("NA".equals(tpStatus))
				testPlanName = "NA";

			Thread.sleep(10000);
			testPlanNames.put(scopeItems.get(i), testPlanName);
			testPlanStatus.put(scopeItems.get(i), tpStatus);

			/*writeExcel(excelFilePath);
			//5 minutes of wait between every 10 test plans
			if(i>0 && i%10==0){
				System.out.println("Waiting for 5 minutes");
				Thread.sleep(150000);
			}*/
		}

	}

	@AfterTest
	public static void afterTest()throws Exception{
		writeExcel(excelFilePath);
	}

	/*public static void main(String[] args) throws Exception {

		excelFilePath = System.getProperty("user.home") + "//Desktop//CreateAndRunTestPlans.xlsx";
		System.out.println("Excel Path : "+excelFilePath);
		destFilePath = System.getProperty("user.home") + "//Desktop//Test Plan Status//";
		readExcel(excelFilePath);
		initSettings(driver);
		launchURL(driver);
		loginApp(driver);

		String tpStatus = null;
		for(int i=0;i<scopeItems.size();i++){

			//testPlanName = release+"_TestRun_"+testPhase+"_"+scopeItems.get(i)+"_"+country;
			testPlanName = release+"_TestRun_"+scopeItems.get(i)+"_"+testPhase;
			System.out.println((i+1)+"."+testPlanName);
			tpStatus = readTestPlanStatus(driver, testPlanName);

			if("NA".equals(tpStatus))
				testPlanName = "NA";

			Thread.sleep(10000);
			testPlanNames.put(scopeItems.get(i), testPlanName);
			testPlanStatus.put(scopeItems.get(i), tpStatus);

			writeExcel(excelFilePath);
			//5 minutes of wait between every 10 test plans
			if(i>0 && i%10==0){
				System.out.println("Waiting for 5 minutes");
				Thread.sleep(150000);
			}
		}
	}
*/
	public static WebDriver initSettings()throws Exception{	

		WebDriver dr = null;
		
		if(browser.contains("chrome")){
			System.setProperty("webdriver.chrome.driver",driverPath+"\\chromedriver.exe");

			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			//chromePrefs.put("download.default_directory", downloadFilepath);
			chromePrefs.put("download.prompt_for_download", "true");

			chromePrefs.put("safebrowsing.enabled", "true"); 
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			dr = new ChromeDriver(cap);
		}else if(browser.contains("firefox")){
			System.setProperty("webdriver.gecko.driver",driverPath+"\\geckodriver.exe");  
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			dr = new FirefoxDriver(capabilities);
		}
		else if(browser.contains("ie") || browser.contains("explorer") || browser.contains("internet")){
			System.setProperty("webdriver.ie.driver",driverPath+"\\IEDriverServer.exe");
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
			caps.setCapability("EnableNativeEvents", false);
			caps.setCapability("ignoreZoomSetting", true);

			dr = new InternetExplorerDriver(caps);
			dr.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL, "0")); 
		}
		System.out.println("Browser : "+browser);

		//Defining wait
		wait = new WebDriverWait(dr, 100);
		
		return dr;
	}

	public static void launchURL(WebDriver dr)throws Exception{

		dr.get(uRL);
		Thread.sleep(5000);
		dr.manage().window().maximize();
	}

	public static void loginApp(WebDriver dr)throws Exception{

		wait(dr, "//input[@id='USERNAME_FIELD-inner' or @id='j_username']");
		type(dr, By.xpath("//input[@id='USERNAME_FIELD-inner' or @id='j_username']"), userName);
		type(dr, By.xpath("//input[@id='PASSWORD_FIELD-inner' or @id='j_password']"), password);
		click(dr, By.xpath("//button[@id='LOGIN_LINK' or @id='logOnFormSubmit']"));
		Thread.sleep(10000);
		for(int i=0;i<6;i++){
			if(dr.findElements(By.xpath("//span[text()='Test Your Processes']")).size()!=1)
				Thread.sleep(10000);
			else
				break;
		}

		wait(dr, "//span[text()='Test Your Processes']");
		click(dr, By.xpath("//span[text()='Test Your Processes']"));
	}

	public static int getColumnNumber(WebDriver dr, String tblXpath, String colName)throws Exception{

		int colNum = -1;
		int colCount = dr.findElements(By.xpath(tblXpath+"/thead/tr/th")).size();
		List<String> columnNames = new ArrayList<String>();

		for(int i=0;i<colCount;i++)
			columnNames.add(dr.findElement(By.xpath(tblXpath+"/thead/tr/th["+(i+1)+"]")).getText());

		colNum = columnNames.indexOf(colName);
		colNum = colNum+1;

		System.out.println("Column Number : "+colNum);
		return colNum;
	}

	public static boolean isAlertPresent(WebDriver dr) {
		try {
			dr.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	public static void readExcel(String filePath) throws IOException {

		String key = null, value = null;

		FileInputStream ips = new FileInputStream(new File(filePath)); 
		XSSFWorkbook workbook = new XSSFWorkbook(ips);

		Sheet sh = workbook.getSheet("ScopeItems");
		Row row = sh.getRow(0);

		Cell cell = null;

		//To get the Column Names
		for(int i=0;i<row.getLastCellNum();i++){
			cell = row.getCell(i);
			if(cell==null)
				cell=row.createCell(i);
			cell.setCellType(Cell.CELL_TYPE_STRING);	
			value = cell.getStringCellValue();

			/*if(value.isEmpty())
				break;
			else*/
			columnNames.add(value);
		}

		System.out.println("Excel columnNames List : "+columnNames);

		int rowCount = sh.getLastRowNum() - sh.getFirstRowNum();
		System.out.println("Excel rowCount : "+rowCount);

		//To get the Process Steps and Labels
		for(int j=0;j<rowCount;j++){
			row = sh.getRow(j+1);
			cell = row.getCell(columnNames.indexOf("Scope Items"));
			cell.setCellType(Cell.CELL_TYPE_STRING);

			value = cell.getStringCellValue();

			scopeItems.add(value);
		}

		System.out.println("Scope Items List : "+scopeItems);

		//To get the Configurations
		sh = workbook.getSheet("Configuration");
		cell = null;
		int n = 0;

		rowCount = sh.getLastRowNum() - sh.getFirstRowNum() + 1;
		System.out.println("Config Sheet rowCount : "+rowCount);

		for(int m=0;m<rowCount;m++){

			row = sh.getRow(m);

			cell = row.getCell(n);
			cell.setCellType(Cell.CELL_TYPE_STRING);	
			key = cell.getStringCellValue();

			cell = row.getCell(n+1);
			cell.setCellType(Cell.CELL_TYPE_STRING);	
			value = cell.getStringCellValue();

			configMap.put(key, value);
		}

		System.out.println("Config Map : "+configMap);

		ips.close();
	}

	public static void createExcel(String filePath, String sheetName) throws IOException {
		//FileOutputStream ops = new FileOutputStream(new File(filePath));
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(sheetName);
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
	}

	@SuppressWarnings("deprecation")
	public static void writeExcel(String sourceFilePath) throws IOException {

		String value = null;

		FileInputStream ips = new FileInputStream(new File(sourceFilePath)); 
		XSSFWorkbook workbook = new XSSFWorkbook(ips);
		//XSSFCellStyle style = null;

		Sheet sh = workbook.getSheet("ScopeItems");
		Row row = sh.getRow(0);

		Cell cell = null;

		int rowCount = sh.getLastRowNum() - sh.getFirstRowNum();
		System.out.println("Excel rowCount : "+rowCount);

		//style = workbook.createCellStyle();

		//Create new Column
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
		Date date = new Date();
		String today = dateFormat.format(date);
		System.out.println("Today's Date : "+today);
		String statusColumn = testPhase+" Status "+today;

		if(!columnNames.contains(statusColumn)){
			System.out.println(statusColumn+" column is not avialable in the excel");
			cell = row.createCell(columnNames.size()+1);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(statusColumn);

			columnNames.add(statusColumn);
		}

		//To search for the scope item row and write the test plan and status
		for(int i=0;i<scopeItems.size();i++){
			for(int j=0;j<rowCount;j++){
				row = sh.getRow(j+1);
				cell = row.getCell(columnNames.indexOf("Scope Items"));
				cell.setCellType(Cell.CELL_TYPE_STRING);
				value = cell.getStringCellValue();

				cell = null;

				if(value.equals(scopeItems.get(i))){

					//Writing the Test Plan Name of Scope Item
					cell = row.getCell(columnNames.indexOf("Test Plan Name"));
					if(cell==null)
						cell = row.createCell(columnNames.indexOf("Test Plan Name"));
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(testPlanNames.get(scopeItems.get(i)));

					cell = null;

					//Writing the Execution Status of Scope Item
					cell = row.getCell(columnNames.indexOf(statusColumn));
					if(cell==null)
						cell = row.createCell(columnNames.indexOf(statusColumn));
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(testPlanStatus.get(scopeItems.get(i)));
				}
			}

		}

		//Writing in excel workbook
		FileOutputStream out = new FileOutputStream(new File(sourceFilePath));
		workbook.write(out);
		out.close();

		ips.close();
	}

	//Checks for the Test Plan execution status
	public static String readTestPlanStatus(WebDriver dr, String testPlan)throws Exception{

		int testPlanPercentage = 0;
		String tpStatus = null;

		Thread.sleep(8000);
		wait(dr, "//form[@class='sapMSFF']/input");
		clear(dr, By.xpath("//form[@class='sapMSFF']/input"));
		type(dr, By.xpath("//form[@class='sapMSFF']/input"), testPlan);
		keyPress(dr, By.xpath("//form[@class='sapMSFF']/input"), Keys.TAB);
		click(dr, By.xpath("//form[@class='sapMSFF']/div[contains(@id,'searchField-search')]"));
		Thread.sleep(3000);
		click(dr, By.xpath("//form[@class='sapMSFF']/div[contains(@id,'searchField-search')]"));
		Thread.sleep(3000);

		int tpSize = dr.findElements(By.xpath("//ul[contains(@id,'list-listUl')]/li")).size();

		if(dr.findElements(By.xpath("//ul[contains(@id,'list-listUl')]/li[1]//div[text()='No data']")).size()==1){
			System.out.println("Test Plan : "+testPlan+" is not found.Hence, skipping");
			tpStatus = "NA";
		}
		else
		{
			System.out.println("No. of rows after search : "+tpSize);

			String value = null, titleUI = null;
			int i=0;

			for(i=0;i<tpSize;i++){
				value = dr.findElement(By.xpath("//ul[contains(@id,'list-listUl')]/li["+(i+1)+"]//span[contains(@id,'titleText')]/span")).getText();
				System.out.println("Selecting row : "+value);
				Thread.sleep(5000);
				click(dr, By.xpath("//ul[contains(@id,'list-listUl')]/li["+(i+1)+"]/div"));
				Thread.sleep(10000);
				titleUI = dr.findElement(By.xpath("//ul[contains(@id,'list-listUl')]/li["+(i+1)+"]/div")).getText();
				System.out.println("titleUI : "+titleUI);
				for(int j=0;j<5;j++){
					Thread.sleep(5000);
					if(!(titleUI.contains(dr.findElement(By.xpath("//h1//span[contains(@id,'titleText-inner')]")).getText())))
						click(dr, By.xpath("//ul[contains(@id,'list-listUl')]/li["+(i+1)+"]/div"));
					else
						break;
				}

				if(titleUI.contains(dr.findElement(By.xpath("//h1//span[contains(@id,'titleText-inner')]")).getText()))
					break;
			}


			tpStatus = dr.findElement(By.xpath("//div[contains(@id,'status1')]//span[contains(@id,'status1-text')]")).getText();

			if("In Process".equalsIgnoreCase(tpStatus)){
				testPlanPercentage = Integer.parseInt(titleUI.split("%")[0]);
				tpStatus = tpStatus+" - "+testPlanPercentage;
			}

			System.out.println("Test Plan Status : "+tpStatus);
		}

		return tpStatus;
	}	

	public static void doubleClick(WebDriver dr, By locator)throws Exception{

		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		new Actions(dr).moveToElement(dr.findElement(locator)).doubleClick().perform();
		Thread.sleep(3000);
	}

	public static void actionsClick(WebDriver dr, By locator)throws Exception{

		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		new Actions(dr).moveToElement(dr.findElement(locator)).click().perform();
		Thread.sleep(3000);
	}

	public static void type(WebDriver dr, By locator , String value)throws Exception{

		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		dr.findElement(locator).sendKeys(value);
	}

	public static void click(WebDriver dr, By locator)throws Exception{

		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		dr.findElement(locator).click();
		Thread.sleep(3000);
	}

	public static void clear(WebDriver dr, By locator)throws Exception{

		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		dr.findElement(locator).clear();
		Thread.sleep(2000);
	}

	public static void keyPress(WebDriver dr, By locator, Keys key)throws Exception{

		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		dr.findElement(locator).sendKeys(key);
		Thread.sleep(2000);
	}

	public static void wait(WebDriver dr,String locator)throws Exception{

		wait = new FluentWait<WebDriver>(dr);
		Thread.sleep(10000);
		wait.withTimeout(100, TimeUnit.SECONDS)
		.ignoring(NoSuchElementException.class)
		.pollingEvery(5, TimeUnit.SECONDS)
		.withMessage("checking for the element")
		.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
	}

	public static void captureScreenshot(WebDriver dr)throws Exception{
		File src= ((TakesScreenshot)dr).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File("C:/selenium/error.png"));
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}

	}

}
