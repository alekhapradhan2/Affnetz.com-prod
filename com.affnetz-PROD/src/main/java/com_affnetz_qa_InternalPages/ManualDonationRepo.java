package com_affnetz_qa_InternalPages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ManualDonationRepo {
	
	Page page;
	
	private String campaignName="//h1";
	
	private String first_Name="#input-15";
	
	private String last_Name="#input-18";
	
	private String phone_no="#input-21";
	
	private String email_Id="#input-24";
	
	private String donation_filed="#input-31";
	
	private String paymentMode="#input-34";
	
	private String paymentModeList="#list-34";
	
	private String allPaymentMode="//div[contains(@id,'list-item-74')]";
	
	private String trnsID="#input-39";
	
	private String address1="#input-47";
	
	private String address2="#input-50";
	
	private String cityName="#input-53";
	
	private String stateClick="#input-56";
	
	private String allStateName="xpath=//div[contains(@id,'list-item-')]";
	
	private String zipCode="#input-66";
	
	private String donateButton="xpath=//button[@id='donate_btn']";
	
	private String recepitDownload="xpath=//span[text()='Download Receipt']";
	
	private String receiptError="//div[@class='card-details']";
	

	
	
	
	

	public ManualDonationRepo(Page page)
	{
		this.page=page;
	}
	
	public String getCampaignName() {
		Locator name=page.locator(campaignName).first();
		name.waitFor();
		String campname=name.textContent().trim();
		return campname;
	}
	public void setPesonalDetails(String fname,String lname,String mail)
	{
		page.fill(first_Name, fname);
		page.fill(last_Name, lname);
		page.fill(phone_no, "8888888888");
		page.fill(email_Id, mail);
	}
	
	public void setDonationAmount(String amt)
	{
		page.fill(donation_filed, amt);
	}
	
	public String selectPaymentMode() throws InterruptedException
	{
		page.click(paymentMode);
		Thread.sleep(1000);
		Locator mode=page.locator(paymentModeList);
		Locator all=mode.locator(allPaymentMode);
		String paymMode=all.nth(2).textContent().trim();
		all.nth(2).click();
		return paymMode;
	}
	
	public void transactionId() {
		page.fill(trnsID, "888888");
	}
	
	public void setAddress() throws InterruptedException
	{
		String state="Arkansas";
		page.fill(address1, "New Address1");
		page.fill(address2, "New Address2");
		page.fill(cityName, "Bhubaneswar");
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
		
		page.fill(zipCode, "88888");
			
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
