package com_affnetz_qa_Publicpages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class PublicCampaignRepo_Prod {
	
	Page page;
	
	private String campaignName="xpath=//h2";
	
	private String campaignTitle="xpath=//h2[contains(@class,'campaign-title')]";
	
	private String campaignDonateButton="//span[contains(text(),'Donate')]";
	
	private String teamSection="//div[text()='Teams']";
	
	private String teamName="//h1";
	
	private String teamTitle="//h2";
	
	private String clickTeam="//a[contains(text(),'View Team')]";
	
	private String teamDonateButton="//span[contains(text(),' Donate')]";
	
	
	public PublicCampaignRepo_Prod(Page page)
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
	
	public void goToTeamSection() {
		page.click(teamSection);
	}
	
	public String getTeamName() {
		Locator name=page.locator(teamName).first();
		name.waitFor();
		String teamName=name.textContent();
		return teamName;
	}
	
	public String getTeamTitle() {
		Locator name=page.locator(teamTitle).first();
		name.waitFor();
		String teamTitle=name.textContent();
		return teamTitle;
	}
	
	public void clickOnTeam() {
		page.click(clickTeam);
	}
	
	public void teamDonate() {
		page.click(teamDonateButton);
	}
}
