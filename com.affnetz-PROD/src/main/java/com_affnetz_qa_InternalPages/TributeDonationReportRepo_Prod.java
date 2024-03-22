package com_affnetz_qa_InternalPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TributeDonationReportRepo_Prod {
	
	Page page;
	
	private String tablerow=".v-data-table__wrapper tr";
	
	
	public TributeDonationReportRepo_Prod(Page page)
	{
		this.page=page;
	}
	
	
	public boolean isTributeDonorDetailsShowing(String name,String tributename) throws InterruptedException{
		Thread.sleep(3000);
		
		Locator row=page.locator(tablerow);
		boolean flag=false;
		
		for(int i=1;i<row.count();i++)
		{
			Locator col=row.nth(i).locator("//td");
			String name_mail=col.nth(0).textContent().trim();
			String tname=col.nth(4).textContent().trim();
			
			if(name_mail.contains(name) && tname.equals(tributename))
			{
				flag=true;
				break;
			}
		}
		return flag;
		
	}

}
