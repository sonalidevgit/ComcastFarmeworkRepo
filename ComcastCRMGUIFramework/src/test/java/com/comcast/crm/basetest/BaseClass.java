package com.comcast.crm.basetest;

import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.comcast.crm.generic.databaseutility.DataBaseUtility;
import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;

public class BaseClass {

	//create object
	public DataBaseUtility dbLib = new DataBaseUtility();
	public FileUtility fLib = new FileUtility();
	public  ExcelUtility eLib = new ExcelUtility();
	public  JavaUtility jLib = new JavaUtility();
	public WebDriverUtility wlib = new WebDriverUtility();
	
	
	
	public WebDriver driver = null;
	
	@BeforeSuite  
	public void configBeforeSuite() throws SQLException {  
	
		System.out.println("==Connect to DB, Report config ==");  
		dbLib.getDbconnection();                   //from database utility
	}  
	
	  @BeforeClass  
	  public void configBeforeClass() throws Throwable {  
	    System.out.println("==Launch the Browser== "); 
	    
	   String BROWSER= fLib.getDataFromPropertiesFile("browser");      //fileutility
	   
	   // LAUNCH THE BROWSER

			if (BROWSER.equals("chrome")) {
				driver = new ChromeDriver();
			} else if (BROWSER.equals("firefox")) {
				driver = new FirefoxDriver();
			} else if (BROWSER.equals("edge")) {
				driver = new EdgeDriver();
			} else {
				driver = new ChromeDriver();
			}
	   
	  }  
	    
	  
	  
	  @BeforeMethod  
	  public void configBM() throws Throwable {  
	    System.out.println("=login= "); 
	    
	    String URL= fLib.getDataFromPropertiesFile("url");
	    String USERNAME= fLib.getDataFromPropertiesFile("username");
	    String PASSWORD= fLib.getDataFromPropertiesFile("password");
	    
	    LoginPage lp = new LoginPage(driver);
	    lp.loginToapp(URL, USERNAME, PASSWORD);     //this method is present in objectrepository(loginpage)
	    
	  }  
	  
	  @AfterMethod  
	  public void configAM() {  
	    System.out.println(" =logout= ");  
	    HomePage hp = new HomePage(driver); //logout method is present in homepage
	    hp.logout();
	  }  
	  
	  
	  
	  @AfterClass  
	  public void configAfterClass() {  
	    System.out.println("==close the Browser== ");  
	    driver.quit();
	  }  
	  
	  
	  @AfterSuite  
	  public void configAfterSuite() throws SQLException {  
	    System.out.println("==close dB, Report Backup == "); 
	    dbLib.closeDbconnection();
	
}
	  
}
