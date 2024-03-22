package com_affnetz_qa_Publicpages;

import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertTrue;

import com.microsoft.playwright.Locator;


public class LoginPageRepo_Prod {
	public Page page;
	
	//WebElements
	private String userName="xpath=//input[@id='txtEmail']";
	
	private String password="xpath=//input[@id='txtPassword']";
	
	private String loginButton="xpath=//button[@type='submit']";
	
	private String logoutButton="xpath=//a[@title='logout']";
	
	private String errMsg="xpath=//div[contains(@style,'text-align: left')]";
	
	private String registerLink="xpath=//span[text()='Register']";
	
	private String calenderLink="xpath=//span[text()='Calendar']";
	
	private String eventsLink="xpath=//span[text()='Events']";
	
	private String fundRaisingLink="xpath=//span[text()='Peer-to-Peer Fundraising']";
	
	private String volunteerLink="xpath=//span[text()='Volunteer']";
	
	private String entitesLink="xpath=//span[text()='Entites']";
	
	private String tributeLink="xpath=//span[text()='Tributes']";
	
	private String campLink="xpath=//span[text()='Camps']";
	
	private String forgotPasswordLink="xpath=//a[contains(text(),'Forgot Your Password')]";
	
	
	//Cunstructor
	
	public LoginPageRepo_Prod(Page page)
	{
		this.page=page;
	}
	
	
	//Methods
	
	public String getLoginPageTitle()
	{
		String title=page.title();
		return title;
	}
	
	public void doLogin(String uname, String pwd)
	{
		page.fill(userName, uname);
		page.fill(password, pwd);
		page.locator(loginButton).first().click();;
		
	}
	
	public void isLogin()
	{
		Locator logout=page.locator(logoutButton);
		logout.waitFor();
		
		assertThat(logout).isVisible();
		
	}
	
	public void doLogout() throws InterruptedException
	{
		Thread.sleep(2000);
		Locator logout=page.locator(logoutButton);
		logout.waitFor();
		logout.click();
	}
	
	public void isLogout()
	{
		Locator login=page.locator(loginButton).first();
		login.waitFor();
		assertThat(login).isVisible();
		
		
	}
	public void isErrMsgShowing()
	{
		Locator err=page.locator(errMsg).first();
		err.waitFor();
		assertThat(err).isVisible();
		
	}
	
	public void clickRegister()
	{
		page.click(registerLink);
	}
	
	public void clickCalender()
	{
		page.click(calenderLink);
	}
	
	public void clickEvent()
	{
		page.click(eventsLink);
	}
	
	public void clickfundRaising()
	{
		page.click(fundRaisingLink);
		
	}
	
	public void clickVolunteer()
	{
		page.click(volunteerLink);
	}
	
	public void clickEntites()
	{
		page.click(entitesLink);
	}
	
	public void clickTribute()
	{
		page.click(tributeLink);
	}
	
	public void clickCamp()
	{
		page.click(campLink);
	}
	
	public void clickForgotPassword()
	{
		page.click(forgotPasswordLink);
	}
	
	
	

}
