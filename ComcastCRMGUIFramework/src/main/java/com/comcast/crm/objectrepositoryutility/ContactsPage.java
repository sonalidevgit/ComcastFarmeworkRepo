package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactsPage {

	WebDriver driver;

	public ContactsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//img[@alt='Create Contact...']")
	private WebElement createNewContactBtn;

	public WebElement getCreateNewContactBtn() {
		return createNewContactBtn;
	}

	// Search for text box
	@FindBy(name = "search_text")
	private WebElement searchEdt;

	public WebElement getSearchEdt() {
		return searchEdt;
	}

	public WebElement getSearchDD() {
		return searchDD;
	}

	// Search for text box
	@FindBy(name = "search_field")
	private WebElement searchDD;
	
	//search now btn
	@FindBy(name="submit")
	private WebElement searchBtn;

	public WebElement getSearchBtn() {
		return searchBtn;
	}
	
	// Business Library Method
	public void clickCreateNewContactBtn() {

	    createNewContactBtn.click();

	}
	
	
	
}
