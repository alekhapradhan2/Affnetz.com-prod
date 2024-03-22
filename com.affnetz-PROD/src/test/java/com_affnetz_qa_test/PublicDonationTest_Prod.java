package com_affnetz_qa_test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Random;

import org.testng.ITestResult;
import org.testng.annotations.Test;

import com.microsoft.playwright.Page;

import com_affnetz_qa_InternalPages.DashboardRepo_Prod;
import com_affnetz_qa_InternalPages.PeerToPeerFundraisingRepo_Prod;
import com_affnetz_qa_InternalPages.TributeDonationReportRepo_Prod;
import com_affnetz_qa_InternalPages.TributeRepo_Prod;
import com_affnetz_qa_Publicpages.LoginPageRepo_Prod;
import com_affnetz_qa_Publicpages.PublicCampaignRepo_Repo;
import com_affnetz_qa_Publicpages.PublicDonationRepo_Prod;
import com_affnetz_qa_Publicpages.PublicTributeDonationRepo_Prod;
import com_affnetz_qa_Publicpages.PublicTributeRepo_Prod;
import com_affnetz_qa_factory.PlayWrightFactory_Prod;

public class PublicDonationTest_Prod {
	
	Page page;
	PublicDonationRepo_Prod pd;
	LoginPageRepo_Prod lp;
	DashboardRepo_Prod dr;
	PublicTributeRepo_Prod pr;
	PublicTributeDonationRepo_Prod pdr;
	TributeDonationReportRepo_Prod TributeDonationRepo;
	ITestResult result;
	TributeRepo_Prod tribute;
	PublicCampaignRepo_Repo pc;
	PeerToPeerFundraisingRepo_Prod fund;
	
	
	String fname,lname,ph,amount,mailid,tributeName,TributeDonorFisrtName,TributeDonorLastName,TributeDonorMail,tributeAmt;
	String campName,campFirstName,campLastName,CampAmt,CampMail;
	Random rm=new Random();
	int x=rm.nextInt(999);
	Random rm1=new Random();
	int y=rm1.nextInt(999);
	Random rm2=new Random();
	int w=rm2.nextInt(999);
	
		
	/**Public donation with direct link 
	 * @throws IOException */
	
	@Test(priority =0,groups = {"DirectDonation","Donation"})
	public void launchBrowser() throws IOException {
		page=PlayWrightFactory_Prod.intitBrowser(PlayWrightFactory_Prod.initProp().getProperty("browser"), "donate");
	}
	
	@Test(priority = 1,groups = {"DirectDonation","Donation"})
	public void isDonationPage() throws IOException
	{
		assertThat(page).hasURL(PlayWrightFactory_Prod.initProp().getProperty("donateUrl"));
	}
	
	/**All the donor details fill 
	 * @throws InterruptedException */
	
	@Test(priority = 2,dependsOnMethods = "isDonationPage",groups = {"DirectDonation","Donation"})
	public void fillAllDonorDetails() throws InterruptedException {
		pd=new PublicDonationRepo_Prod(page);
		fname="Meth"+x+"ew";
		lname="Jac"+x+"k";
		ph="6371772552";
		amount=""+x+"";
		mailid="engineering+ap"+x+"@affnetz.com";
		pd.setPesonalDetails(fname, lname, ph, mailid);
		pd.setDonationAmount(amount);
		pd.setAddress("newadd1", "newadd2", "newCity", "Alaska", "88888");
	}
	
	/** card details fill */
	
	@Test(priority = 3,dependsOnMethods = "fillAllDonorDetails",groups = {"DirectDonation","Donation"})
	public void setCardDetils() {
		pd=new PublicDonationRepo_Prod(page);
		pd.setCardDetails("4242424242424242", "4242","424","88888");
	}
	
	
	/**Click on donate button */
	
	@Test(priority = 4,dependsOnMethods = "setCardDetils",groups = {"DirectDonation","Donation"})
	public void submitAllDetails() throws InterruptedException {
		Thread.sleep(2000);
		pd=new PublicDonationRepo_Prod(page);
		pd.clickOnDonate();
	}
	
	/**It will check is donation is done or not */
	@Test(priority = 5,dependsOnMethods = "submitAllDetails",groups = {"DirectDonation","Donation"})
	public void isDonationDone() {
		pd=new PublicDonationRepo_Prod(page);
		pd.isFormSubmit();
	}
	
	
	/**It will check user able to download the receipt after donation or not
	 * This method will only execute if the donation is done if donation got failed then this method will be skipped */
	
	@Test(priority = 6,dependsOnMethods = "isDonationDone",groups = {"DirectDonation","Donation"})
	public void isReciptDownload() throws InterruptedException {
		pd=new PublicDonationRepo_Prod(page);
		boolean flag=pd.downloadReceipt();
		assertTrue(flag);
	}
	
	
	/**It will check all the donor details is showing to super admin in montly donor list or not
	 * This method will only execute if the donation is done if donation got failed then this method will be skipped 
	 * @throws IOException */
	
	@Test(priority = 7,dependsOnMethods = "isDonationDone",groups = {"DirectDonation","Donation"})
	public void isSuperAdminSeeTheDonorDetails() throws InterruptedException, IOException {
		
		page=PlayWrightFactory_Prod.intitBrowser(PlayWrightFactory_Prod.initProp().getProperty("browser"), "login");
		lp=new LoginPageRepo_Prod(page);
		PlayWrightFactory_Prod.login();
		dr=new DashboardRepo_Prod(page);
		dr.clickOnMonthDonorLink();
		dr.searchDonorDetaills(fname+" "+lname);
		boolean flag=dr.isDonorDetailsShowing(fname+" "+lname);
		
		assertTrue(flag);
		
	}
	
	/** This method will open the public tribute page 
	 * and also validate the tribute page opened or not
	 * @throws IOException */
	
	@Test(priority = 8,groups = {"Tribute","Donation"})
	public void goToPublicTributePage() throws IOException {
		page=PlayWrightFactory_Prod.intitBrowser(PlayWrightFactory_Prod.initProp().getProperty("browser"),"login");
//		page.navigate("https://t1.affnetz.org/login");
		lp=new LoginPageRepo_Prod(page);
		lp.clickTribute();
		assertThat(page).hasURL(PlayWrightFactory_Prod.initProp().getProperty("publicTribute"));
	}
	
	/** This method will click on one public tribute
	 *  and validate the name is marching or not*/
	
	@Test(priority = 9,groups = {"Tribute","Donation"})
	public void clickOneTribute() {
		pr=new PublicTributeRepo_Prod(page);
		tributeName=pr.getTributeName();
		pr.clickOnTribute();
		
		String title=pr.getTributeTitile();
		assertEquals(tributeName, title);
		
	}
	
	/** This method will click on donate button in selected tribute and 
	 * check the tribute name is matching with the 
	 * name showing in donation page or not */
	
	@Test(priority = 10,groups = {"Tribute","Donation"})
	public void clickOnDonateButton() {
		pr= new PublicTributeRepo_Prod(page);
		pr.clickOnTributeDonateButton();
		String name=pr.getTributeNameInDonationForm();
		assertEquals(tributeName, name);
	}
	
	/** This method will fill the all donor details 
	 * @throws InterruptedException */
	
	@Test(priority = 11,groups = {"Tribute","Donation"})
	public void fillAllDonorDetails_Tribute() throws InterruptedException
	{
		pdr=new PublicTributeDonationRepo_Prod(page);
		TributeDonorFisrtName="Nami"+y+"ew";
		TributeDonorLastName="Hol"+y+"k";
		ph="6371772552";
		tributeAmt=""+y+"";
		TributeDonorMail="engineering+ap"+y+"@affnetz.com";
		pdr.setPesonalDetails(TributeDonorFisrtName,TributeDonorLastName, ph, TributeDonorMail);
		pdr.setDonationAmount(tributeAmt);
		pdr.setAddress("newadd1", "newadd2", "newCity", "Alaska", "88888");
	}
	
	/** This method will fill the card details of donor */
	@Test(priority = 12,groups = {"Tribute","Donation"})
	public void setCardDetils_Tribute() {
		pdr=new PublicTributeDonationRepo_Prod(page);
		pdr.setCardDetails("4242424242424242", "4242","424","88888");
	}
	
	/** This method will click on donateButton on donation form and submit the details */
	@Test(priority = 13,groups = {"Tribute","Donation"})
	public void submitAllDetails_Tribute() throws InterruptedException {
		Thread.sleep(2000);
		pdr= new PublicTributeDonationRepo_Prod(page);
		pdr.clickOnDonate();
	}
	
	/** This method will check is donation form is submited or not */
	@Test(priority = 14,groups = {"Tribute","Donation"},dependsOnMethods = "submitAllDetails_Tribute")
	public void isDonationDone_Tribute() {
		pdr=new PublicTributeDonationRepo_Prod(page);
		pdr.isFormSubmit();
	}
	
	/** This method will check is donation receipt is downloaded or not.
	 * This method will only execute if the donation is done if donation got failed then this method will be skipped*/
	
	@Test(priority = 15,groups = {"Tribute","Donation"},dependsOnMethods ="isDonationDone_Tribute" )
	public void isReciptDownload_Tribute() throws InterruptedException {
		Thread.sleep(2000);
		pdr=new PublicTributeDonationRepo_Prod(page);
		boolean flag=pdr.downloadReceipt();
		assertTrue(flag);
	}
	
	
	/** This method will check after donation all the donor details should shown to super admin in tribute donation report.
	 * This method will only execute if the donation is done if donation got failed then this method will be skipped 
	 * @throws IOException */
	
	@Test(priority = 16,groups = {"Tribute","Donation"},dependsOnMethods = "isDonationDone_Tribute")
	public void isPublicTributeDonorDetaiShowToSuperAdmin() throws InterruptedException, IOException {
		page=PlayWrightFactory_Prod.intitBrowser(PlayWrightFactory_Prod.initProp().getProperty("browser"), "login");
		lp=new LoginPageRepo_Prod(page);
		PlayWrightFactory_Prod.login();
		dr=new DashboardRepo_Prod(page);
		dr.clickOnReport();
		dr.clickOnTributeDonationReport();
		TributeDonationRepo=new TributeDonationReportRepo_Prod(page);
		boolean flag=TributeDonationRepo.isTributeDonorDetailsShowing(TributeDonorFisrtName+" "+TributeDonorLastName, tributeName);
		assertTrue(flag);
	}
	
	/** This method will check after donation all the donor details should store in that particular tribute in donation section
	 * This method will only execute if the donation is done if donation got failed then this method will be skipped 
	 * @throws IOException */
	@Test(priority = 17,groups = {"Tribute","Donation"},dependsOnMethods = "isDonationDone_Tribute")
	public void isDonorDetailShowInThatParticularTributeDonationSection() throws InterruptedException, IOException {
		dr=new DashboardRepo_Prod(page);
		dr.clickOnTributeLink();
		assertThat(page).hasURL(PlayWrightFactory_Prod.initProp().getProperty("publicTribute"));
		tribute=new TributeRepo_Prod(page);
		tribute.searchTributeName(tributeName);
		assertEquals(tribute.getTributeName(), tributeName);
		tribute.clickOnSearchedTribute();
		boolean flag=tribute.isDonorDetailsShowing(TributeDonorFisrtName, TributeDonorMail, tributeAmt);
		assertTrue(flag);	
	}
	
	
	@Test(priority = 18,groups = {"Campaign","Donation"})
	public void goToPublicCampaignPage() throws IOException {
		page=PlayWrightFactory_Prod.intitBrowser(PlayWrightFactory_Prod.initProp().getProperty("browser"),"login");
		lp=new LoginPageRepo_Prod(page);
		lp.clickfundRaising();
		assertThat(page).hasURL(PlayWrightFactory_Prod.initProp().getProperty("campaignUrl"));
	}
	
	@Test(priority = 19,groups = {"Campaign","Donation"})
	public void clickOnCampaign() {
		pc=new PublicCampaignRepo_Repo(page);
		campName=pc.getCampaignName();
		System.out.println(campName);
		pc.clickOnCampaign();
		String title=pc.getCampaignTitle();
		System.out.println(title);
		assertEquals(campName, title);
	}
	
	@Test(priority = 20,groups = {"Campaign","Donation"})
	public void clickOnCampDonateButton() {
		pc=new PublicCampaignRepo_Repo(page);
		pc.clickOnCampDonate();
	}
	
	@Test(priority = 21,groups = {"Campaign","Donation"})
	public void fillAllDonorDetails_Campaign() throws InterruptedException {
		pd=new PublicDonationRepo_Prod(page);
		campFirstName="Andr"+w+"ew";
		campLastName="Cheri"+w+"k";
		ph="6371772552";
		CampAmt=""+w+"";
		CampMail="engineering+cp"+w+"@affnetz.com";
		pd.setPesonalDetails(campFirstName, campLastName, ph, CampMail);
		pd.setDonationAmount(CampAmt);
		pd.setAddress("newadd1", "newadd2", "newCity", "Alaska", "88888");
	}
	
	@Test(priority = 22,groups = {"Campaign","Donation"})
	public void setCardDetils_Campaign() {
		pd=new PublicDonationRepo_Prod(page);
		pd.setCardDetails("4242424242424242", "4242","424","88888");
	}
	
	@Test(priority = 23,groups = {"Campaign","Donation"})
	public void submitAllDetails_Campaign() throws InterruptedException {
		Thread.sleep(2000);
		pd=new PublicDonationRepo_Prod(page);
		pd.clickOnDonate();
	}
	
	@Test(priority = 24,groups = {"Campaign","Donation"})
	public void isDonationDone_Campaign() {
		pd=new PublicDonationRepo_Prod(page);
		pd.isFormSubmit();
	}
	@Test(priority = 25,groups = {"Campaign","Donation"})
	public void isReciptDownload_Campaign() throws InterruptedException {
		Thread.sleep(2000);
		pdr=new PublicTributeDonationRepo_Prod(page);
		boolean flag=pdr.downloadReceipt();
		assertTrue(flag);
	}
	@Test(priority = 26,groups = {"Campaign","Donation"})
	public void isPublicCampaignDonorDetailsIsStoredInDonorReport() throws InterruptedException, IOException {
		
		page=PlayWrightFactory_Prod.intitBrowser(PlayWrightFactory_Prod.initProp().getProperty("browser"),"login");
		lp=new LoginPageRepo_Prod(page);
		PlayWrightFactory_Prod.login();
		dr=new DashboardRepo_Prod(page);
		dr.clickOnDonorReportLink();
		String DonorName=campFirstName+" "+campLastName;
		boolean flag=dr.isDonorDetailsShownInDonorReport(DonorName, CampMail, CampAmt);
		assertTrue(flag);
		
	}
	
	@Test(priority = 27,groups = {"Campaign","Donation"})
	public void isDonorDetailsIsShowingInThatParticularCampaignDonatiosSection() throws InterruptedException {
		dr=new DashboardRepo_Prod(page);
		dr.clickOnPeerToPeerFundarasing();
		fund=new PeerToPeerFundraisingRepo_Prod(page);
		fund.searchCampaign(campName);
		fund.clickOnSearchButton();
		String campaignName=fund.getSearchedCampName();
		assertEquals(campaignName,campName);
		fund.clickOnSearchedCamp();
		fund.goToDonationsSection();
		boolean flag=fund.isDonorDetailsShowingInThisPartocularCampaing(campFirstName, CampMail, CampAmt);
		assertTrue(flag);
	}
	
	
	

}
