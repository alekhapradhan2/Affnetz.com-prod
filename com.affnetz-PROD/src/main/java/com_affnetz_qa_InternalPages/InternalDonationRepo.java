package com_affnetz_qa_InternalPages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class InternalDonationRepo {
	
	public Page page;
	
	private String first_Name="#input-78";
	
	private String last_Name="#input-81";
	
	private String phone_no="#input-84";
	
	private String email_Id="#input-87";
	
	private String donation_filed="#input-95";
	
	private String address1="#input-107";
	
	private String address2="#input-110";
	
	private String cityName="#input-113";
	
	private String stateClick="#input-116";
	
	private String allStateName="xpath=//div[contains(@id,'list-item-')]";
	
	private String zipCode="#input-126";

	private String PaymentFrame="xpath=//iframe[contains(@name,'privateStripeFrame')]";
	
	private String cardNumber="xpath=//input[@name='cardnumber']";
	
	private String expireDate="xpath=//input[@name='exp-date']";
	
	private String cvc="xpath=//input[@name='cvc']";
	
	private String postalCode="xpath=//input[@name='postal']";
	
	private String donateButton="xpath=//button[@id='donate_btn']";
	
	private String recepitDownload="xpath=//span[text()='Download Receipt']";
	
	private String receiptError="//div[@class='card-details']";
	
	private String dashboard="//a[text()='Dashboard']";
	
	
	public InternalDonationRepo(Page page)
	{
		this.page=page;
	}
	
	public boolean isDonatePageOpen()
	{
		boolean flag=false;
		String url=page.url();
		if(url.contains("donate"))
		{
			flag=true;
		}
		return flag;
	}
	
	public void setPesonalDetails(String fname,String lname,String PhNo,String mail)
	{
		page.fill(first_Name, fname);
		page.fill(last_Name, lname);
		page.fill(phone_no, PhNo);
		page.fill(email_Id, mail);
	}
	
	public void setDonationAmount(String amt)
	{
		page.fill(donation_filed, amt);
	}
	
	public void setAddress() throws InterruptedException
	{
		page.fill(address1, "New Address1");
		page.fill(address2, "new Address2");
		page.fill(cityName, "Bhubaneswar");
		page.click(stateClick);
		Locator stateName=page.locator(allStateName);
		Thread.sleep(1000);
		for(int i=0;i<stateName.count();i++)
		{
			String name=stateName.nth(i).textContent().trim();
			if(name.equals("Arizona"))
			{
				stateName.nth(i).click();
				break;
			}
		}
		
		page.fill(zipCode, "88888");
			
	}
	
	public void setCardDetails()
	{
		FrameLocator frame=page.frameLocator(PaymentFrame);
		frame.locator(cardNumber).fill("4242424242424242");
		frame.locator(expireDate).fill("0428");
		frame.locator(cvc).fill("242");
		frame.locator(postalCode).fill("88888");
	}
	
	public void clickOnDonate()
	{
		Locator donateBtn=page.locator(donateButton);
		donateBtn.waitFor();
		donateBtn.click();
	}
	
	public void isFormSubmit() {
		Locator receipt=page.locator(recepitDownload).first();
		receipt.waitFor();
		assertThat(receipt).isVisible();
	}
	
	public boolean downloadReceipt() throws InterruptedException {
		Locator receipt=page.locator(recepitDownload).first();
		receipt.waitFor();
		receipt.click();
		Thread.sleep(5000);
		Locator msg=page.locator(receiptError);
		boolean flag=false;
		if(!msg.isVisible())
		{
			flag=true;
		}
		return flag;
	}
	
	public void goToDashboard() {
		page.click(dashboard);
	}
}
