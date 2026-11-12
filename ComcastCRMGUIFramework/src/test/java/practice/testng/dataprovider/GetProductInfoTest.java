package practice.testng.dataprovider;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.comcast.crm.generic.fileutility.ExcelUtility;

public class GetProductInfoTest {

	@Test(dataProvider = "getData")  
	  public void getProductInfoTest(String brandName, String productName) {  
	    WebDriver driver = new ChromeDriver();  
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20)); 
	    
	    driver.get("https://www.amazon.in/");
	    driver.manage().window().maximize();  
	   
    
	      
	    //search product  
	    driver.findElement(By.id("twotabsearchtextbox")).sendKeys(brandName, Keys.ENTER);  
	      
	    //capture product info  
	    String x = "//span[text()='"+productName+"']/../../../../div[3]/div[1]/div/div[1]/div[1]/div[1]/a/span/span[2]/span[2]";  //dynamix xpath bcoz we are not hardcoding brandname
	   String price=driver.findElement(By.xpath(x)).getText();  
	   System.out.println(price);
	   
	   driver.quit();
	    
	  }

	@DataProvider  
	  public Object[][] getData() throws Throwable{ 
		
		ExcelUtility eLib = new ExcelUtility();
		int rowCount = eLib.getRowCount("product");
		
	    Object[][] objArr = new Object[rowCount][2]; 
	    
	    //without hardcoding below data, we can use for loop
	    for(int i=0;i<rowCount;i++)
	    {
	    	objArr[i][0]=eLib.getDataFromExcel("product", i+1, 0); //why i+1 here bcoz in excel index starts from 0 and in 1 index data is there
	    	objArr[i][1]=eLib.getDataFromExcel("product", i+1, 1);
	    }
	    
	    //objArr[0][0]="iphone";  
	    //objArr[0][1]="Apple iPhone 15 (256 GB) - Blue";  //it is not detecting this element(future)
	      
	    /*objArr[0][0]="iphone";  
	    objArr[0][1]="iPhone 18 Pro (1 TB) - Burgundy";  
	      
	    objArr[1][0]="iphone";  
	    objArr[1][1]="iPhone 18 Pro Max (256 GB) - Black";*/
	    
		return objArr;  
	
}
	
}
