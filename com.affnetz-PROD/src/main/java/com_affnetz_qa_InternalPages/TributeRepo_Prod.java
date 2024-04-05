package com_affnetz_qa_InternalPages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TributeRepo_Prod {
	
	Page page;
	
	private String searchTribute="#input-75";
	
	private String tributeSearchButton="xpath=//span[contains(text(),'Search')]";
	
	private String tributeName="//h2";
	
	private String donorDetailsTable=".v-data-table__wrapper tr";
	
	private String DonateButton="//a[contains(@href,'tributeDonate?')]";
	
	private String donorNameOnDonationPage="//h1";
	
	private String first_Name="#input-75";
	
	private String last_Name="#input-78";
	
	private String phone_no="#input-81";
	
	private String email_Id="#input-84";
	
	private String donation_filed="#input-91";
	
	private String address1="#input-108";
	
	private String address2="#input-111";
	
	private String cityName="#input-114";
	
	private String stateClick="#input-117";
	
	private String allStateName="xpath=//div[contains(@id,'list-item-')]";
	
	private String zipCode="#input-127";
	
	private String PaymentFrame="xpath=//iframe[contains(@name,'privateStripeFrame')]";
	
	private String cardNumber="xpath=//input[@name='cardnumber']";
	
	private String expireDate="xpath=//input[@name='exp-date']";
	
	private String cvc="xpath=//input[@name='cvc']";
	
	private String postalCode="xpath=//input[@name='postal']";
	
	private String donateButton="xpath=//button[@id='donate_btn']";
	
	private String recepitDownload="xpath=//span[text()='Download Receipt']";
	
	private String receiptError="//div[@class='card-details']";
	
	public TributeRepo_Prod(Page page) {
		this.page=page;
	}
	
	public void searchTributeName(String tName) {
		Locator searchBox=page.locator(searchTribute).first();
		Locator searchButton=page.locator(tributeSearchButton).first();
		searchBox.waitFor();
		searchButton.waitFor();
		searchBox.fill(tName);
		searchButton.click();	
	}
	
	public String getTributeName() {
		String name=page.locator(tributeName).first().textContent().trim();
		return name;
	}
	
	public void clickOnSearchedTribute() {
		Locator click=page.locator(tributeName).first();
		click.waitFor();
		click.click();
	}
	
	public void isDonorDetailsShowing(String fname,String email,String amount) throws InterruptedException {
		Thread.sleep(2000);
		Locator tableRow=page.locator(donorDetailsTable);
		boolean flag=false;
		String Donorname = null;
		String Donormail = null;
		
		for(int i=1;i<tableRow.count();i++)
		{
			Locator col=tableRow.nth(i).locator("//td");
			String name=col.nth(0).textContent().trim();
			String mail=col.nth(2).textContent().trim();
			String amt=col.nth(6).textContent().trim();
			
			if(name.equalsIgnoreCase(fname)&& mail.equalsIgnoreCase(email)&& amt.contains(amount))
			{
				Donorname=name;
				Donormail=mail;
				break;
			}

			
		}
		assertEquals(Donorname, fname);
		assertEquals(Donormail, email);
	}
	
	public void goToDonationPage() {
		page.click(DonateButton);
	}
	
	public String getDonorNameOnDonationPage() {
		Locator name=page.locator(donorNameOnDonationPage).first();
		name.waitFor();
		String DonorName=name.textContent().trim();
		return DonorName;
	}
	
	public void setPesonalDetails(String fname,String lname,String mail)
	{
		page.fill(first_Name, fname);
		page.fill(last_Name, lname);
		page.fill(phone_no, "7873530919");
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
	
	

	

}
