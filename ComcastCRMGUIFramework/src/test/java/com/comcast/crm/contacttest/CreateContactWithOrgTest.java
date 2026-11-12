package com.comcast.crm.contacttest;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CreateContactWithOrgTest {

	public static void main(String[] args) throws Throwable {

		// TESTCASE 6

		// THIS IS INTEGRATION TEST (CONTACT AND ORGANIZATION MODULE)

		// PRECONDITION IS AT LEAST 1 ORGANIZATION SHOULD BE THERE SO FIRST I WILL
		// CREATE ONE ORGANIZATION

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
		int randomInt = random.nextInt(50000);

		// READ TESTSCRIPT DATA FROM EXCEL
		FileInputStream fis1 = new FileInputStream("./testdata/testttScriptData.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
		Sheet sh = wb.getSheet("contact");
		Row row = sh.getRow(7); // GO TO 7TH ROW
		String orgName = row.getCell(2).toString() + randomInt; // GO TO 2ND COLUMN
		String contactLastName = row.getCell(3).getStringCellValue(); // GO TO 2ND COLUMN

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
		
		//wLib.waitForPageToLoad(driver); //this is the replacement of below line
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

		// click on save button
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		// VERIFY HEADER MSG EXPECTED RESULT
		String headerInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();

		if (headerInfo.contains(orgName)) {
			System.out.println(orgName + "is created == PASS");
		} else {
			System.out.println(orgName + "is not created == FAIL");
		}

		// STEP 5: NAVIGATE TO CONTACT MODULE

		// click Contacts link
		driver.findElement(By.linkText("Contacts")).click();

		// STEP 6: click on "create contact" button
		driver.findElement(By.xpath("//img[@title=\"Create Contact...\"]")).click();

		// STEP 7: enter lastname
		driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys(contactLastName);

		// click on look up (+ near organization name)
		driver.findElement(By.xpath("//input[@name='account_name']/following-sibling::img")).click();

		// switch to Child Window
		
		//wLib.switchToTabOnUrl(driver , "module=Accounts&action");  //this code will be replaced by below program for switch to window
		String parent=driver.getWindowHandle();
		Set<String> set = driver.getWindowHandles();

		Iterator<String> it = set.iterator();

		while (it.hasNext()) {
			String windowID = it.next();
			driver.switchTo().window(windowID);

			String actUrl = driver.getCurrentUrl();
			if (actUrl.contains("module=Accounts&action")) {
				break;
			}
		}

		driver.findElement(By.name("search_text")).sendKeys(orgName);

		// click on search now
		driver.findElement(By.name("search")).click();

		driver.findElement(By.xpath("//a[text() = '" + orgName + "']")).click();

		// switch to parent window
		//wLib.switchToTabOnURL(driver , "Contact&action");
		driver.switchTo().window(parent);
		
			// click on save button
			driver.findElement(By.xpath("(//input[@type='submit' and contains(@value,'Save')])[1]")).click();
			
			// ---------------------------------------------------------------------------------------------//

			// VERIFY HEADER MSG EXPECTED RESULT
			headerInfo = driver.findElement(By.xpath("//input[@name='lastname']")).getAttribute("value");
			

			if (headerInfo.contains(contactLastName)) {
				System.out.println(contactLastName + "is created == PASS");
			} else {
				System.out.println(contactLastName + "is not created == FAIL");
			}

			// click on save button
			//driver.findElement(By.xpath("(//input[@type='submit' and @value='Save'])[1]")).click();

			// VERIFY HEADER orgName INFO EXPECTED RESULT
			String actOrgName = driver.findElement(By.id("mouseArea_Organization Name")).getText();
			

			if (actOrgName.contains(orgName)) {
				System.out.println(orgName + " is created == PASS");
			} else {
				System.out.println(orgName + " is not created == FAIL");
			}

			driver.quit();
		}

	}

