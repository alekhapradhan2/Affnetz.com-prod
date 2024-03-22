package com_affnetz_qa_Publicpages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class PublicTributeDonationRepo_Prod {
	
public Page page;
	
	
	private String first_Name="#input-8";
	
	private String last_Name="#input-11";
	
	private String phone_no="#input-14";
	
	private String email_Id="#input-17";
	
	private String donation_filed="#input-24";
	
	private String address1="#input-41";
	
	private String address2="#input-44";
	
	private String cityName="#input-47";
	
	private String stateClick="#input-50";
	
	private String allStateName="xpath=//div[contains(@id,'list-item-')]";
	
	private String zipCode="#input-60";

	private String PaymentFrame="xpath=//iframe[contains(@name,'privateStripeFrame')]";
	
	private String cardNumber="xpath=//input[@name='cardnumber']";
	
	private String expireDate="xpath=//input[@name='exp-date']";
	
	private String cvc="xpath=//input[@name='cvc']";
	
	private String postalCode="xpath=//input[@name='postal']";
	
	private String donateButton="xpath=//button[@id='donate_btn']";
	
	private String recepitDownload="xpath=//span[text()='Download Receipt']";
	
	private String tributeNameIndonationForm="xpath=//h1";
	
	
	
	//Cunstroctor
	public PublicTributeDonationRepo_Prod(Page page)
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
	
	public void setAddress(String add1,String add2,String city,String state,String zip) throws InterruptedException
	{
		page.fill(address1, add1);
		page.fill(address2, add2);
		page.fill(cityName, city);
		page.click(stateClick);
		Locator stateName=page.locator(allStateName);
		Thread.sleep(1000);
		for(int i=0;i<stateName.count();i++)
		{
			String name=stateName.nth(i).textContent();
			if(name.contains(state))
			{
				stateName.nth(i).click();
				break;
			}
		}
		
		page.fill(zipCode, zip);
			
	}
	
	public void setCardDetails(String cardNo,String expDate,String cvcNo,String Pin)
	{
		FrameLocator frame=page.frameLocator(PaymentFrame);
		frame.locator(cardNumber).fill(cardNo);
		frame.locator(expireDate).fill(expDate);
		frame.locator(cvc).fill(cvcNo);
		frame.locator(postalCode).fill(Pin);
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
		Thread.sleep(2000);
		boolean flag=false;
		String url=page.url();
		if(url.contains("invoice"))
		{
			flag=true;			
		}
		
		return flag;
	}
	
	public String getTributeName() {
		Locator name=page.locator(tributeNameIndonationForm).first();
		name.waitFor();
		String tName=name.textContent();
		return tName;
	}

}
