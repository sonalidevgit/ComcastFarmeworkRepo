package usingPomClass;

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
import org.openqa.selenium.support.PageFactory;

import com.comcast.crm.objectrepositoryutility.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.OrganizationInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsPage;

public class CreateOrganizationtest {
	
	public static void main(String[] args) throws Throwable {

	// FOR 1ST TESTCASE:

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
	Sheet sh = wb.getSheet("org");
	Row row = sh.getRow(1); // GO TO 1ST ROW
	String orgName = row.getCell(2).toString() + randomInt; // GO TO 2ND COLUMN

	wb.close();

	// LAUNCH THE BROWSER
	WebDriver driver = null;

	if(BROWSER.equals("chrome"))
	{
		driver = new ChromeDriver();
	}else if(BROWSER.equals("firefox"))
	{
		driver = new FirefoxDriver();
	}else if(BROWSER.equals("edge"))
	{
		driver = new EdgeDriver();
	}else
	{
		driver = new ChromeDriver();
	}

	// STEP 1: LOGIN TO APPLICATION
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20)); // IMPLICIT WAIT

	driver.get(URL);

	driver.manage().window().maximize();
	
	//Rule no. 03 : object initialisation
	LoginPage lp = new LoginPage(driver);
	
	//lp.usernameEdt.sendKeys("admin");
	//lp.passwordEdt.sendKeys("admin");
	
	//Rule 5: Object Utilization
	//lp.getUsernameEdt().sendKeys("admin");
	//lp.getPasswordEdt().sendKeys("admin");
	//lp.getLoginBtn().click();
	
	lp.loginToapp("admin", "admin");

	

	
	// STEP 2: NAVIGATE TO ORGANIZATION MODULE

	HomePage op = new HomePage(driver);
	op.getOrgLink().click();   //getters method provide single element access i.e. single access
	
	//op.naviagteToCampaignPage();  //BusinessLibrary method provide multiple element access
	
	
	
	// STEP 3: click on "create organization" button
	OrganizationsPage cnp = new OrganizationsPage(driver);
	cnp.getCreateNewOrgBtn().click();
	
	
	
	
	// STEP 4: enter the details and create new organization
	
	CreatingNewOrganizationPage cnop = new CreatingNewOrganizationPage(driver);
	cnop.createOrg(orgName);
	
	// VERIFY HEADER MSG EXPECTED RESULT
	OrganizationInfoPage oip = new OrganizationInfoPage(driver);
	String actOrgname = oip.getHeadermsg().getText();
	
	if(actOrgname.contains(orgName))
	{
		System.out.println(orgName + "name is verified == PASS");
	}
	else
	{
		System.out.println(orgName + "name is not verified == FAIL");	
	}
		


	// STEP 5: LOGOUT
	
	op.logout();     
	driver.quit();

}
	}
