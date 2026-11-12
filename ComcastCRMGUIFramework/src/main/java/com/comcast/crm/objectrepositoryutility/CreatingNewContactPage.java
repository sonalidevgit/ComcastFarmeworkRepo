package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CreatingNewContactPage {

WebDriver driver;
	
	public CreatingNewContactPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	

	//contactname
	@FindBy(name = "firstname")
	private WebElement contactNameEdt;
	
	 @FindBy(name = "lastname")
	    private WebElement lastNameEdt;
	
	//save button
	@FindBy(xpath = "//input[@title = 'Save [Alt+S]']")
	private WebElement saveBtn;
	
	//@FindBy(name = "industry")
//	private WebElement industrydd;  //dd means dropdown

	public WebElement getContactNameEdt() {
		return contactNameEdt;
	}

	public WebElement getSaveBtn() {
		return saveBtn;
	}
	
	
	
	//business library method
	public void createContact(String lastName)
	{
		
	lastNameEdt.sendKeys(lastName);
		saveBtn.click();
	}
	

	
	
	
	
	
	
}
