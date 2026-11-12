package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	
	//Rule 2: Object creation
	// organization link
	@FindBy(linkText = "Organizations")
	private WebElement orgLink;

	// contacts link
	@FindBy(linkText = "Contacts")
	private WebElement contactLink;
	
	//more link
	@FindBy(linkText = "More")
	private WebElement moreLink;
	
	// campaign link  (go to more dropdown , there campaign link is present)
		@FindBy(linkText = "Campaigns")
		private WebElement campaignLink;
		
		
	//inspect that icon above signout
		@FindBy(xpath = "//img[@src='themes/softed/images/user.PNG']")
		private WebElement adminImg;	
		
		//sign out link
		@FindBy(linkText="Sign Out")
		private WebElement signOutLink;	
		
	
	//Rule 3: Object Initialization (By using constructor)
	
	WebDriver driver ;
	
	public HomePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}

	
	//Rule 4: Object Encapsulation
	public WebElement getOrgLink() {
		return orgLink;
	}

	public WebElement getContactLink() {
		return contactLink;
	}

	
	public WebElement getMoreLink() {
		return moreLink;
	}


	public WebElement getCampaignLink() {
		return campaignLink;
	}
	
	
	
	//Business Library


	public void naviagteToCampaignPage()
	{    
		Actions act = new Actions(driver);  // to perform mouse hover action in campaignlink
		act.moveToElement(moreLink).perform();
		campaignLink.click();
	}
	
	
	//signout
	public void logout()
	{
		Actions act = new Actions(driver);
		act.moveToElement(adminImg).perform();
		signOutLink.click();
		
	}
	
	public void navigateToContactsPage() {

	    contactLink.click();

	}
}
