package usingPomClass;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.OrganizationInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsPage;


public class deleteOrgTest {

	public static void main(String[] args) throws Throwable {

		// create Object
		FileUtility fLib = new FileUtility();
		ExcelUtility eLib = new ExcelUtility();
		JavaUtility jLib = new JavaUtility();
		WebDriverUtility wLib = new WebDriverUtility();
		

		// read common data from JSON file

		String BROWSER = fLib.getDataFromPropertiesFile("browser");
		String URL = fLib.getDataFromPropertiesFile("url");
		String USERNAME = fLib.getDataFromPropertiesFile("username");
		String PASSWORD = fLib.getDataFromPropertiesFile("password");

	

		// READ TESTSCRIPT DATA FROM EXCEL
		/*FileInputStream fis1 = new FileInputStream("./testdata/testttScriptData.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
		Sheet sh = wb.getSheet("org");
		Row row = sh.getRow(1); // GO TO 1ST ROW*/
		
		
		// READ TESTSCRIPT DATA FROM EXCEL and generate random no.
		String orgName = eLib.getDataFromExcel("org", 10, 2) + jLib.getRandomNumber();
		

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
		
		LoginPage lp = new LoginPage(driver);
		lp.loginToapp(USERNAME, PASSWORD);
		

		

		// STEP 2: NAVIGATE TO ORGANIZATION MODULE

		HomePage op = new HomePage(driver);
		op.getOrgLink().click(); 
		
		// STEP 3: click on "create organization" button
		OrganizationsPage cnp = new OrganizationsPage(driver);
		cnp.getCreateNewOrgBtn().click();

		// STEP 4: enter the details and create new organization

		CreatingNewOrganizationPage cnop = new CreatingNewOrganizationPage(driver);
		cnop.createOrg(orgName);

		// VERIFY HEADER MSG EXPECTED RESULT
		OrganizationInfoPage oip = new OrganizationInfoPage(driver);
		String actOrgname = oip.getHeadermsg().getText();

		if (actOrgname.contains(orgName)) {
			System.out.println(orgName + "name is verified == PASS");
		} else {
			System.out.println(orgName + "name is not verified == FAIL");
		}
		
		
		
		//STEP 5: go back to organization page
		
		
		op.getOrgLink().click(); 
		
		
		//search for organization
	cnp.getSearchEdt().sendKeys(orgName);
		wLib.select(cnp.getSearchDD(), "Organization Name");
		
		cnp.getSearchBtn().click();
		
		
		//in dynamic webtable select and delete org
		
		driver.findElement(By.xpath("//a[text()='"+orgName+"']/../../td[8]/a[text()='del']")).click();
		
		// Handle confirmation popup(OK)

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		Alert alt = wait.until(ExpectedConditions.alertIsPresent());

		// Capture alert message
		String alertMsg = alt.getText();

		System.out.println("Alert message: " + alertMsg);

		// Click on OK
		alt.accept();

		System.out.println("Organization deleted successfully");

		// STEP 6: LOGOUT

		op.logout();
		driver.quit();

	}
}
