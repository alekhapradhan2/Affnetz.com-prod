package com_affnetz_qa_Publicpages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class PublicTributeRepo_Prod {
	
	Page page;
	
	private String tributeName="xpath=//h2";
	
	private String tributeTitle="xpath=//h2[contains(@class,'campaign-title')]";
	
	private String tributeNameInDonationForm="xpath=//h1";
	
	private String tributeDonateButton="xpath=//span[contains(text(),'Donate')]";
	
	public PublicTributeRepo_Prod(Page page)
	{
		this.page=page;
	}
	
	public String getTributeName()
	{
		Locator name=page.locator(tributeName).first();
		name.waitFor();
		String tname= name.textContent().trim();
		return tname;
		
	}
	
	public String getTributeTitile() {
		Locator name=page.locator(tributeTitle).first();
		name.waitFor();
		String title= name.textContent().trim();
		return title;
	}
	
	public String getTributeNameInDonationForm() {
		Locator name=page.locator(tributeNameInDonationForm);
		name.waitFor();
		String tname=name.textContent().trim();
		return tname;
	}
	
	public void clickOnTributeDonateButton() {
		Locator donateButton=page.locator(tributeDonateButton).first();
		donateButton.waitFor();;
		donateButton.click();;
	}
	
	public void clickOnTribute() {
		Locator name=page.locator(tributeName).first();
		name.waitFor();
		name.click();
	}

}
