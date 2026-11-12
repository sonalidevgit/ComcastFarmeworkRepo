package com.comcast.crm.contacttest;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
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
import com.comcast.crm.generic.webdriverutility.JavaUtility;

public class CreateContactWithSupportDateTest {

	public static void main(String[] args) throws Throwable {
		
		JavaUtility jLib = new JavaUtility();

		// FOR 5TH TESTCASE:

		// READ COMMON DATA FROM PROPERTY FILE
		FileInputStream fis = new FileInputStream("./configAppData/commondata.properties");

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

		// READ TESTSCRIPT DATA FROM EXCEL
		FileInputStream fis1 = new FileInputStream("./testdata/testttScriptData.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
		Sheet sh = wb.getSheet("contact");
		Row row = sh.getRow(4); // GO TO 4TH ROW
		String lastName = row.getCell(2).toString() + randomInt; // GO TO 2ND COLUMN

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

		// STEP 2: NAVIGATE TO CONTACT MODULE

		// click Contacts link
		driver.findElement(By.linkText("Contacts")).click();

		// STEP 3: click on "create contact" button
		driver.findElement(By.xpath("//img[@title=\"Create Contact...\"]")).click();
		
		//ENTER ALL THE DATAILS AND CREATE NEW oRGANIZATION
		String startDate = jLib.getSystemDateYYYYMMDD();
		String endDate = jLib.getRequiredDateYYYYMMDD(30);
		//these above 2 lines for below ALL COMMENTED LINES

		
		/*Date dateObj = new Date(); // import from java.util

		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd"); // here we can get the specific format of date we
																	// want
		String startDate = sim.format(dateObj);
		
		

		// for next 30 DAYS  from today(using calender class)
		Calendar cal = sim.getCalendar();

		cal.add(Calendar.DAY_OF_MONTH, 30); // after30 DAYS
		String endDate = sim.format(cal.getTime()); */
		
		
		// STEP 4: enter lastname
		
		driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys(lastName);

		// enter support start date
		driver.findElement(By.name("support_start_date")).clear();
		driver.findElement(By.name("support_start_date")).sendKeys(startDate);

		// enter support end date
		driver.findElement(By.name("support_end_date")).clear();
		driver.findElement(By.name("support_end_date")).sendKeys(endDate);

		// click on save button
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		
		
		
		
		

		// VERIFY START DATE MSG EXPECTED RESULT
		String actStartDate = driver.findElement(By.id("dtlview_Support Start Date")).getText();

		if (actStartDate.contains(startDate)) {
			System.out.println(startDate + " is created == PASS");
		} else {
			System.out.println(startDate + " is not created == FAIL");
		}

		// VERIFY END DATE MSG EXPECTED RESULT
		String actEndDate = driver.findElement(By.id("dtlview_Support End Date")).getText();

		if (actEndDate.contains(endDate)) {
			System.out.println(endDate + " is created == PASS");
		} else {
			System.out.println(endDate + " is not created == FAIL");
		}

		// STEP 5: LOGOUT
		driver.quit();
	}

}
