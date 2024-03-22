package com_affnetz_qa_Publicpages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class PublicCampaignRepo_Repo {
	
	Page page;
	
	private String campaignName="xpath=//h2";
	
	private String campaignTitle="xpath=//h2[contains(@class,'campaign-title')]";
	
	private String campaignDonateButton="//span[contains(text(),'Donate')]";
	
	
	public PublicCampaignRepo_Repo(Page page)
	{
		this.page=page;
	}
	
	public String getCampaignName() {
		Locator name=page.locator(campaignName).first();
		name.waitFor();
		String campName=name.textContent().trim();
		return campName;
		
	}
	
	public String getCampaignTitle() {
		Locator name=page.locator(campaignTitle).first();
		name.waitFor();
		String campTitle=name.textContent().trim();
		return campTitle;
	}
	
	public void clickOnCampaign() {
		Locator cl=page.locator(campaignName).first();
		cl.waitFor();
		cl.click();
	}
	
	public void clickOnCampDonate() {
		page.locator(campaignDonateButton).first().click();
	}
}
