package com.comcast.crm.contacttest;

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
import org.testng.annotations.Test;

import com.comcast.crm.basetest.BaseClass;
import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.objectrepositoryutility.ContactsPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewContactPage;
import com.comcast.crm.objectrepositoryutility.HomePage;

public class CreateContactTest extends BaseClass {

	@Test
	public void createContactTest() throws Throwable {
		
		
		// READ COMMON DATA FROM PROPERTY FILE
		//FileUtility flib = new FileUtility();
		
		
		
		/*String BROWSER = fLib.getDataFromPropertiesFile("browser");
		String URL = fLib.getDataFromPropertiesFile("url");
		String USERNAME = fLib.getDataFromPropertiesFile("username");
		String PASSWORD = fLib.getDataFromPropertiesFile("password"); */
		

		// GENERATE THE RANDOM NUMBER
		//Random random = new Random();
		//int randomInt = random.nextInt(5000);
		
		

		// READ TESTSCRIPT DATA FROM EXCEL
		//ExcelUtility elib = new ExcelUtility();
		
		//String lastName = elib.getDataFromExcel("contact", 1, 2) + randomInt;
		String lastName = eLib.getDataFromExcel("contact", 1, 2) + jLib.getRandomNumber();

		// LAUNCH THE BROWSER
		/*WebDriver driver = null;

		if (BROWSER.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (BROWSER.equals("firefox")) {
			driver = new FirefoxDriver();
		} else if (BROWSER.equals("edge")) {
			driver = new EdgeDriver();
		} else {
			driver = new ChromeDriver();
		}*/

		// STEP 1: LOGIN TO APPLICATION
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20)); // IMPLICIT WAIT

		//driver.get(URL);

		//driver.manage().window().maximize();

		// enter username and password

		//driver.findElement(By.name("user_name")).sendKeys(USERNAME);
		//driver.findElement(By.name("user_password")).sendKeys(PASSWORD);

		// click submit button
		//driver.findElement(By.id("submitButton")).click();

		// STEP 2: NAVIGATE TO CONTACT MODULE
		HomePage hp = new HomePage(driver);
		hp.getContactLink().click();;
		

		// click Contacts link
		ContactsPage cp = new ContactsPage(driver);
		cp.getCreateNewContactBtn().click();
		
		//driver.findElement(By.linkText("Contacts")).click();

		// STEP 3: click on "create contact" button
		
		//driver.findElement(By.xpath("//img[@title=\"Create Contact...\"]")).click();

		// STEP 4: enter lastname
		//driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys(lastName);

		// click on save button
		//driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		
		
		//enter all the details and create new Contact
		CreatingNewContactPage ccp = new CreatingNewContactPage(driver);
		ccp.createContact(lastName);
		

		// VERIFY HEADER CONTACT MSG EXPECTED RESULT
		String actLastName = driver.findElement(By.id("dtlview_Last Name")).getText();
		

		if (actLastName.contains(lastName)) {
			System.out.println(lastName + " is created == PASS");
		} else {
			System.out.println(lastName + " is not created == FAIL");
		}

		

		// STEP 5: LOGOUT
	//	driver.quit();
	}

}
