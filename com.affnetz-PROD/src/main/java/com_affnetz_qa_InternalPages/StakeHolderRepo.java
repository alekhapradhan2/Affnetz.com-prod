package com_affnetz_qa_InternalPages;

import static org.testng.Assert.assertEquals;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class StakeHolderRepo {
	
	Page page;
	
	private String searchUser="#input-76";
	
	private String userBox="//h2";
	
	private String userType=".v-chip__content";
	
	private String userProfileName="//h4";
	
	private String userProfileMail="(//a[contains(@href,'mailto:')])[1]";
	
	public StakeHolderRepo(Page page)
	{
		this.page=page;
	}
	
	
	public void searchUser(String mail) throws InterruptedException {
		Locator input=page.locator(searchUser).first();
		input.waitFor();
		input.fill(mail);
	}
	
	public void isUserCreated(String userName) throws InterruptedException {
		
		Locator name=page.locator(userBox).first();
		name.waitFor();
		Thread.sleep(2000);
		String username=name.textContent().trim();
	}
	
	public void verifyUserDetails(String Name,String userMail) {
		page.click(userBox);
		Locator type=page.locator(userType).first();
		type.waitFor();
		String usertype=type.textContent().trim();
		
		Locator name=page.locator(userProfileName).first();
		name.waitFor();
		String userName=name.textContent().trim();
		
		Locator mail=page.locator(userProfileMail).first();
		mail.waitFor();
		String mailId=mail.textContent().trim();
		String donorUserType=usertype;
		String donorName=userName;
		String donorMail=mailId;
		
		
		assertEquals(donorUserType,usertype);
		assertEquals(donorName, Name);
		assertEquals(donorMail, userMail);
	}

}
