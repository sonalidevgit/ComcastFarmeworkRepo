package com.comcast.crm.orgtest;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CreateOrganizationWithIndustriesTest {

	public static void main(String[] args) throws Throwable {

		// FOR 3RD TESTCASE:

		// READ COMMON DATA FROM PROPERTY FILE
		FileInputStream fis = new FileInputStream("C:\\Users\\sonal\\OneDrive\\Desktop\\data\\commondata.properties");

		// LOAD ALL KEYS IN PROPERTY FILE
		Properties pObj = new Properties();
		pObj.load(fis);

		String BROWSER = pObj.getProperty("browser");
		String URL = pObj.getProperty("url");
		String USERNAME = pObj.getProperty("username");
		String PASSWORD = pObj.getProperty("password");

		// GENERATE THE RANDOM NUMBER
		Random random = new Random();
		int randomInt = random.nextInt(1000);

	// READ TESTSCRIPT DATA FROM EXCEL FILE
		FileInputStream fis1 = new FileInputStream("./testdata/testttScriptData.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
		Sheet sh = wb.getSheet("org");
		Row row = sh.getRow(7); // GO TO 4TH ROW //INDEX STARTS FROM 0
		String orgName = row.getCell(2).toString() + randomInt; // GO TO 2ND COLUMN
		String phoneNumber = row.getCell(3).getStringCellValue(); // GO TO 3RD COLUMN

		wb.close();

		// LAUNCH THE BROWSER
		WebDriver driver = null;

		if (BROWSER.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (BROWSER.equals("firefox")) {
			driver = new FirefoxDriver();
		} else if (BROWSER.equals("edge")) {
			driver = new EdgeDriver();
		} else {
			driver = new ChromeDriver();
		}

		// STEP 1: LOGIN TO APPLICATION
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20)); // IMPLICIT WAIT

		driver.get(URL);

		driver.manage().window().maximize();

		// enter username and password

		driver.findElement(By.name("user_name")).sendKeys(USERNAME);
		driver.findElement(By.name("user_password")).sendKeys(PASSWORD);

		// click submit button
		driver.findElement(By.id("submitButton")).click();

		// STEP 2: NAVIGATE TO ORGANIZATION MODULE

		// click Organizations link
		driver.findElement(By.linkText("Organizations")).click();

		// STEP 3: click on create organization button
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();

		// STEP 4: enter the details and create new organization
		driver.findElement(By.xpath("//input[@name='accountname']")).sendKeys(orgName);

		driver.findElement(By.id("phone")).sendKeys(phoneNumber);

	
		// click on save button
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		// VERIFY PHONE NUMBER EXPECTED RESULT
		String actPhoneNumber = driver.findElement(By.id("dtlview_Phone")).getText();

		if (actPhoneNumber.equals(phoneNumber)) // industries from excel
		{
			System.out.println(phoneNumber + " information is verified==PASS");
		}

		else {
			System.out.println(phoneNumber + " information is not verified==FAIL");
		}

		

		// STEP 5: LOGOUT
		driver.quit();

	}

}
