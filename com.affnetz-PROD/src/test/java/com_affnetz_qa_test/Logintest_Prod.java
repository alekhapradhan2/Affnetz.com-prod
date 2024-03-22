package com_affnetz_qa_test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.microsoft.playwright.Page;

import com_affnetz_qa_Publicpages.LoginPageRepo_Prod;
import com_affnetz_qa_factory.PlayWrightFactory_Prod;



public class Logintest_Prod {
	
	
	Page page;
	LoginPageRepo_Prod lp;
	
	@BeforeTest
	public void setUp() throws IOException
	{
//		pf=new playWrightFactory();
		page=PlayWrightFactory_Prod.intitBrowser(PlayWrightFactory_Prod.initProp().getProperty("browser"),"login");
		lp=new LoginPageRepo_Prod(page);
		
	}
	
	@Test(priority =0,testName = "isLoginPageOpen")
	public void checkLoginPage()
	{
		assertThat(page).hasURL("https://ymcaorg.affnetz.com/login");
	}
	
	@Test(priority =1,testName = "Enter Cradentilas", dependsOnMethods = "checkLoginPage")
	public void doLoginWithValidCradential()
	{
		lp=new LoginPageRepo_Prod(page);
		lp.doLogin("engineering+ymca@affnetz.com", "ym34^$&*^2023");
		lp.isLogin();
		
	}
	
	@Test(priority = 2,testName="Do Logout",dependsOnMethods = "doLoginWithValidCradential")
	public void dologout() throws InterruptedException {
		lp=new LoginPageRepo_Prod(page);
		lp.doLogout();
		lp.isLogout();
	}
	
	@DataProvider (name = "invalidData")
	public Object[][] dpMethod() {
	    return new Object [][] {
	    	{"engineering+ymca@affnetz.com","sbahv"},
	    	{"fvbsdjh","ym34^$&*^2023"},
	    	{"t1admin",""},
	    	{"","%^&$T1Affnetz#$"},
	    	{"",""}
	    	
	    };
	    	
	    }
	@Test(priority = 3,testName = "Invalid Login",dataProvider = "invalidData")
	public void doLoginWithInvalidCradentilas(String uname,String pwd)
	{
		lp=new LoginPageRepo_Prod(page);
		lp.doLogin(uname, pwd);
		lp.isErrMsgShowing();
		

	}
	

	
	
	

	
	

}
