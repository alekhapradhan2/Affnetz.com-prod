package com_affnetz_qa_InternalPages;

import static org.testng.Assert.assertEquals;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SettingPageRepo_Repo {
	
	Page page;
	private String screenProcessPage="//div[text()='Screening Process']";
	
	private String tableRoows=".v-data-table__wrapper tr";
	
	
	public SettingPageRepo_Repo(Page page)
	{
		this.page=page;
	}
	
	public void gotToScreeningProcessPage() {
		page.click(screenProcessPage);
	}
	
	public void isDonorDetailsShownInScreeningProcess(String Dname,String Dmail,String UserType) throws InterruptedException
	{
		Thread.sleep(3000);
		Locator rows=page.locator(tableRoows);
		String DonorName=null;
		String DonorMail=null;
		String status=null;
		String Type=null;
		for(int i=1;i<=rows.count();i++)
		{
			Locator cols=rows.nth(i).locator("//td");
			String name=cols.nth(0).textContent().trim();
			String mail=cols.nth(2).textContent().trim();
			String st=cols.nth(3).textContent().trim();
			String userType=cols.nth(4).textContent().trim();
			if(name.equals(Dname) && mail.equals(Dmail))
			{
				DonorName=name;
				DonorMail=mail;
				status=st;
				Type=userType;
				break;
			}
			
		}
		
		assertEquals(DonorName, Dname);
		assertEquals(DonorMail, Dmail);
		assertEquals(Type, UserType);
		assertEquals(status, "Pending");
		
	}
	
	
	
}
