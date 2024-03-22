package com_affnetz_qa_InternalPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class PeerToPeerFundraisingRepo_Prod {
	
	Page page;
	
	private String campSearchBox="#input-75";
	
	private String campSearchButton="//span[contains(text(),'Search')]";
	
	private String searchedCampName=".af-pt-2  h2";
	
	private String donationsSection="//div[text()='Donations']";
	
	private String donorDetailsTable=".v-data-table__wrapper tr";
	
	public PeerToPeerFundraisingRepo_Prod(Page page)
	{
		this.page=page;
	}
	
	public void searchCampaign(String campName)
	{
		Locator search=page.locator(campSearchBox);
		search.waitFor();
		search.fill(campName);
	}
	
	public void clickOnSearchButton() {
		page.click(campSearchButton);
	}
	
	public void goToDonationsSection() {
		page.click(donationsSection);
	}
	
	public String getSearchedCampName() {
		Locator name=page.locator(searchedCampName).first();
		name.waitFor();
		String campName=name.textContent().trim();
		return campName;
	}
	
	public void clickOnSearchedCamp() {
		Locator name=page.locator(searchedCampName).first();
		name.waitFor();
		name.click();
	}
	
	public boolean isDonorDetailsShowingInThisPartocularCampaing(String fName,String mailid,String amount) throws InterruptedException {
		Thread.sleep(2000);
		boolean flag=false;
		Locator rows=page.locator(donorDetailsTable);
		for(int i=1;i<rows.count();i++)
		{
			Locator cols=rows.nth(i).locator("//td");
			String fname=cols.nth(0).textContent().trim();
			String mail=cols.nth(2).textContent().trim();
			String amt=cols.nth(6).textContent().trim();
			System.out.println(fname+" "+mail+" "+amt);
			if(fname.equals(fName)&& mail.equals(mailid)&& amt.contains(amount))
			{
				flag=true;
				break;
			}
		}
		
		return flag;
	}

}
