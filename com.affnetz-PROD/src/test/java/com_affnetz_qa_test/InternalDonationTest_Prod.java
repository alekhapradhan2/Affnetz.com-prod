package com_affnetz_qa_test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Random;

import org.testng.annotations.Test;


import com.microsoft.playwright.Page;

import com_affnetz_qa_InternalPages.DashboardRepo_Prod;
import com_affnetz_qa_InternalPages.HomePageRepo_Prod;
import com_affnetz_qa_InternalPages.InternalDonationRepo;
import com_affnetz_qa_InternalPages.PeerToPeerFundraisingRepo_Prod;
import com_affnetz_qa_InternalPages.SettingPageRepo;
import com_affnetz_qa_InternalPages.StakeHolderRepo;
import com_affnetz_qa_InternalPages.TributeDonationReportRepo_Prod;
import com_affnetz_qa_InternalPages.TributeRepo_Prod;
import com_affnetz_qa_factory.PlayWrightFactory_Prod;

public class InternalDonationTest_Prod {
	Page page;
	HomePageRepo_Prod hp;
	InternalDonationRepo intDon;
	DashboardRepo_Prod db;
	StakeHolderRepo sp;
	SettingPageRepo settRepo;
	TributeRepo_Prod tr;
	TributeDonationReportRepo_Prod TributeDonationRepo;
	PeerToPeerFundraisingRepo_Prod pr;
	
	String fname,lname,mail,amt;
	String tributeName,TributeDonorFirstName,TributeDonorLastName,TributeDonorMail,tributeAmt;
	String campaignFirstName,campaignLastname,campaignmail,campaignAmt,CampaignName;
	Random rm=new Random();
	int x=rm.nextInt(999);
	Random rm1=new Random();
	int y=rm1.nextInt(999);
	Random rm2=new Random();
	int w=rm2.nextInt(999);
	
	//-----------------------------------------------Direct Donation------------------------------------------------------------------//
	
	@Test(priority = 0 ,groups = {"DirectDonation","Donation"})
	public void LoginAsASuperAdmin() throws IOException {
		page=PlayWrightFactory_Prod.intitBrowser("login");
		PlayWrightFactory_Prod.login();
	}
	
	@Test(priority = 1,groups = {"DirectDonation","Donation"})
	public void clickDirectDonatteButton() throws InterruptedException {
		hp=new HomePageRepo_Prod(page);
		page.reload();
		hp.clickOnHome();
		hp.clickDonate();
	}
	
	@Test(priority = 2,groups = {"DirectDonation","Donation"},dependsOnMethods = "clickDirectDonatteButton")
	public void fillAllDonorDetails() throws InterruptedException {
		intDon=new InternalDonationRepo(page);
		fname="Ashman"+x;
		lname="Derw"+x;
		mail="engineering+ash"+x+"@affnetz.com";
		String PhNo="7873530919";
		amt=""+x+"";
		intDon.setPesonalDetails(fname,lname, PhNo,mail);
		intDon.setDonationAmount(amt);
		intDon.setAddress();
	}
	@Test(priority = 3,groups = {"DirectDonation","Donation"})
	public void setCardDetils() {
		intDon=new InternalDonationRepo(page);
		intDon.setCardDetails();
	}
	@Test(priority = 4,groups = {"DirectDonation","Donation"})
	public void submitAllDetails() throws InterruptedException {
		Thread.sleep(2000);
		intDon=new InternalDonationRepo(page);
		intDon.clickOnDonate();
	}
	@Test(priority = 5,groups = {"DirectDonation","Donation"})
	public void isDonationDone() throws InterruptedException {
		
		intDon=new InternalDonationRepo(page);
		intDon.isFormSubmit();
	}
	@Test(priority = 6,groups = {"DirectDonation","Donation"},dependsOnMethods = "isDonationDone")
	public void isReciptDownload() throws InterruptedException {
		intDon=new InternalDonationRepo(page);
		boolean flag=intDon.downloadReceipt();
		assertTrue(flag);
	}
	@Test(priority = 7,groups = {"DirectDonation","Donation"},dependsOnMethods = "isDonationDone")
	public void isDonorDetailsStoreInDonorReport() throws InterruptedException {
		page.goBack();
		db=new DashboardRepo_Prod(page);
		db.goToDashBoard();
		db.clickOnDonorReportLink();
		String donorName=fname+" "+lname;
		boolean flag=db.isDonorDetailsShownInDonorReport(donorName, mail, amt);
		assertTrue(flag);
		
	}
	@Test(priority =8 ,groups = {"DirectDonation","Donation"},dependsOnMethods = "isDonationDone")
	public void isDonorShownInScreeningProcessForApproveTheUser() throws InterruptedException {
		settRepo=new SettingPageRepo(page);
		db=new DashboardRepo_Prod(page);
		db.goToSettingPage();
		settRepo.gotToScreeningProcessPage();
		String name=fname+" "+lname;
		settRepo.isDonorDetailsShownInScreeningProcess(name, mail,"Donors");
		hp=new HomePageRepo_Prod(page);
		hp.clickOnHome();
	}
	//@Test(priority = 9,groups = {"DirectDonation","Donation"},dependsOnMethods = "isDonationDone")
	public void isNewDonorUserIsCreated() throws InterruptedException
	{
		hp=new HomePageRepo_Prod(page);
		hp.clickOnHome();
		db=new DashboardRepo_Prod(page);
		db.goToStakeHolders();
		sp=new StakeHolderRepo(page);
		Thread.sleep(2000);
		sp.searchUser(mail);
		String userName=fname+"  "+lname;
		sp.isUserCreated(userName);
		sp.verifyUserDetails(userName, mail);
		
	}
	
	//-----------------------------------------------Tribute Donation------------------------------------------------------------------//
	
	@Test(priority = 10,groups = {"Donation","Tribute"})
	public void goToTributePage() throws IOException {
		
		try {
			db=new DashboardRepo_Prod(page);
			db.clickOnTributeLink();
			assertThat(page).hasURL(PlayWrightFactory_Prod.initProp().getProperty("tributeUrl"));
		} catch (Exception e) {
			page=PlayWrightFactory_Prod.intitBrowser("login");
			PlayWrightFactory_Prod.login();
			db=new DashboardRepo_Prod(page);
			db.clickOnTributeLink();
			assertThat(page).hasURL(PlayWrightFactory_Prod.initProp().getProperty("tributeUrl"));
		}

	}
	
	@Test(priority = 11,groups = {"Donation","Tribute"})
	public void clickOneDonate() {
		tr=new TributeRepo_Prod(page);
		tributeName=tr.getTributeName();
		tr.clickOnSearchedTribute();
		String tributeTitle=tr.getTributeName();
		assertEquals(tributeName, tributeTitle);
	}
	@Test(priority = 12,groups = {"Donation","Tribute"})
	public void goToTributeDOnationPage() {
		tr=new TributeRepo_Prod(page);
		tr.goToDonationPage();
		String name=tr.getDonorNameOnDonationPage();
		assertEquals(tributeName, name);
	}
	@Test(priority = 13,groups = {"Donation","Tribute"},dependsOnMethods = "goToTributeDOnationPage")
	public void setUserDetails() {
		tr=new TributeRepo_Prod(page);
		TributeDonorFirstName="Lacki"+y+"ui";
		TributeDonorLastName="Shym"+y+"ma";
		TributeDonorMail="engineering+lacki"+y+"@affnetz.com";
		tributeAmt="50";
		tr.setPesonalDetails(TributeDonorFirstName, TributeDonorLastName, TributeDonorMail);
		tr.setDonationAmount(tributeAmt);
	}
	@Test(priority = 14,groups = {"Donation","Tribute"})
	public void setUserAddress() throws InterruptedException {
		tr=new TributeRepo_Prod(page);
		tr.setAddress();
	}
	@Test(priority = 15,groups = {"Donation","Tribute"})
	public void setCardDetails() {
		tr=new TributeRepo_Prod(page);
		tr.setCardDetails();
	}
	@Test(priority = 16,groups = {"Donation","Tribute"})
	public void submitAllDetails_Tribute() throws InterruptedException {
		Thread.sleep(2000);
		tr=new TributeRepo_Prod(page);
		tr.clickOnDonate();
	}
	@Test(priority = 17,groups = {"Donation","Tribute"})
	public void isDonationDone_Tribute() {
		tr=new TributeRepo_Prod(page);
		tr.isFormSubmit();
	}
	@Test(priority = 18,groups = {"Donation","Tribute"},dependsOnMethods = "isDonationDone_Tribute")
	public void isReciptDownload_Tribute() throws InterruptedException {
		intDon=new InternalDonationRepo(page);
		boolean flag=intDon.downloadReceipt();
		assertTrue(flag);
	}
	
	@Test(priority = 19,groups = {"Donation","Tribute"},dependsOnMethods = "isDonationDone_Tribute")
	public void isDonorDetailsStoredInTributeDonationReport() throws InterruptedException {
		page.goBack();
		db=new DashboardRepo_Prod(page);
		db.clickOnReport();
		Thread.sleep(1000);
		db.clickOnTributeDonationReport();
		TributeDonationRepo=new TributeDonationReportRepo_Prod(page);
		boolean flag=TributeDonationRepo.isTributeDonorDetailsShowing(TributeDonorFirstName+" "+TributeDonorLastName, tributeName);
		assertTrue(flag);
	}
	@Test(priority = 20,groups = {"Donation","Tribute"},dependsOnMethods = "isDonationDone_Tribute")
	public void isDonorDetailsShowInThatParticularTribute() throws InterruptedException {
		db=new DashboardRepo_Prod(page);
		db.clickOnTributeLink();
		tr=new TributeRepo_Prod(page);
		tr.searchTributeName(tributeName);
		
		assertEquals(tr.getTributeName(), tributeName);
		tr.clickOnSearchedTribute();
		tr.isDonorDetailsShowing(TributeDonorFirstName, TributeDonorMail, tributeAmt);
	}
	@Test(priority = 21,groups = {"Donation","Tribute"},dependsOnMethods = "isDonationDone_Tribute")
	public void isDonorShownInScreeningProcessForApproveTheuser_Tribute() throws InterruptedException {
		settRepo=new SettingPageRepo(page);
		db=new DashboardRepo_Prod(page);
		db.goToSettingPage();
		settRepo.gotToScreeningProcessPage();
		String name=TributeDonorFirstName+" "+TributeDonorLastName;
		settRepo.isDonorDetailsShownInScreeningProcess(name, TributeDonorMail,"Donors");
		hp=new HomePageRepo_Prod(page);
		hp.clickOnHome();
	}
	
	//-----------------------------------------------Campaign Donation------------------------------------------------------------------//
	
	@Test(priority = 22,groups = {"Donation","Campaign",})
	public void goToCampaignPage() throws IOException {
		
		try {
			db=new DashboardRepo_Prod(page);
			db.clickOnPeerToPeerFundarasing();
			assertThat(page).hasURL(PlayWrightFactory_Prod.initProp().getProperty("campaignUrl"));
			
		} catch (Exception e) {
			page=PlayWrightFactory_Prod.intitBrowser("login");
			PlayWrightFactory_Prod.login();
			db=new DashboardRepo_Prod(page);
			db.clickOnPeerToPeerFundarasing();
			assertThat(page).hasURL(PlayWrightFactory_Prod.initProp().getProperty("campaignUrl"));
		}

	}
	@Test(priority = 23,groups = {"Donation","Campaign"},dependsOnMethods = "goToCampaignPage")
	public void clickOnCampaing() {
		pr=new PeerToPeerFundraisingRepo_Prod(page);
		CampaignName=pr.getCampaignName();
		pr.clickOnOneCampaign();
		String title=pr.getCampaignName();
		assertEquals(CampaignName, title);
		
	}
	@Test(priority = 24,groups = {"Donation","Campaign"})
	public void goToCampaignDonationPage() {
		pr=new PeerToPeerFundraisingRepo_Prod(page);
		pr.goToDonationPage();
	}
	@Test(priority = 25,groups = {"Donation","Campaign"})
	public void setuserDetails_Campaign() {
		intDon=new InternalDonationRepo(page);
		campaignFirstName="Julli"+w;
		campaignLastname="dwar"+w;
		campaignmail="engineering+julli"+w+"@affnetz.com";
		campaignAmt="36";
		intDon.setPesonalDetails(campaignFirstName, campaignLastname, "6371772552", campaignmail);
		intDon.setDonationAmount(campaignAmt);
	}
	@Test(priority = 26,groups = {"Donation","Campaign"})
	public void setUserAddress_Campaign() throws InterruptedException {
		intDon=new InternalDonationRepo(page);
		intDon.setAddress();
	}
	@Test(priority = 27,groups = {"Donation","Campaign"})
	public void setCardDetails_Campaing() {
		intDon=new InternalDonationRepo(page);
		intDon.setCardDetails();
	}
	@Test(priority = 28,groups = {"Donation","Campaign"})
	public void isDonationDone_Campaing() {
		intDon=new InternalDonationRepo(page);
		intDon.clickOnDonate();
		intDon.isFormSubmit();
	}
	@Test(priority = 29,groups = {"Donation","Campaign"})
	public void isReceiptDownload_Campaign() throws InterruptedException {
		intDon=new InternalDonationRepo(page);
		boolean flag=intDon.downloadReceipt();
		assertTrue(flag);
	}
	@Test(priority = 30,groups = {"Donation","Campaign"})
	public void isDonorDetailsisStoredInDonationreport_Campaign() throws InterruptedException {
		page.goBack();
		db=new DashboardRepo_Prod(page);
		db.goToDashBoard();
		db.clickOnDonorReportLink();
		String donorName=campaignFirstName+" "+campaignLastname;
		boolean flag=db.isDonorDetailsShownInDonorReport(donorName, campaignmail, campaignAmt);
		assertTrue(flag);
	}
	@Test(priority = 31,groups = {"Donation","Campaign"})
	public void isDonorDetailsIsShowingInThatParticularCampaignDonationSection() throws InterruptedException {
		db=new DashboardRepo_Prod(page);
		db.clickOnPeerToPeerFundarasing();
		pr=new PeerToPeerFundraisingRepo_Prod(page);
		pr.searchCampaign(CampaignName);
		pr.clickOnSearchButton();
		String name=pr.getCampaignName();
		assertEquals(CampaignName, name);
		pr.clickOnSearchedCamp();
		pr.goToDonationsSection();
		pr.isDonorDetailsShowingInThisPartocularCampaing(campaignFirstName, campaignmail, campaignAmt);
	}
	@Test(priority = 32,groups = {"Donation","Campaign"})
	public void isDonorShownInScreeningProcessForApproveTheuser_Campaign() throws InterruptedException {
		settRepo=new SettingPageRepo(page);
		db=new DashboardRepo_Prod(page);
		db.goToSettingPage();
		settRepo.gotToScreeningProcessPage();
		String name=campaignFirstName+" "+campaignLastname;
		settRepo.isDonorDetailsShownInScreeningProcess(name, campaignmail,"Donors");
		hp=new HomePageRepo_Prod(page);
		hp.clickOnHome();
	}
	
	//-----------------------------------------------Team Donation------------------------------------------------------------------//
		
		String teamName,teamDonorFirstName,teamDonorLastName,teamDonorMailId,teamDonorAmt;
		Random rm3=new Random();
		int z=rm3.nextInt(999);
		@Test(priority = 33,groups = {"Donation","Team"})
		public void goToCampaignPage_Team() throws IOException {
			
			try {
				db=new DashboardRepo_Prod(page);
				db.clickOnPeerToPeerFundarasing();
				assertThat(page).hasURL(PlayWrightFactory_Prod.initProp().getProperty("campaignUrl"));
			} catch (Exception e) {
				page=PlayWrightFactory_Prod.intitBrowser("login");
				PlayWrightFactory_Prod.login();
				db=new DashboardRepo_Prod(page);
				db.clickOnPeerToPeerFundarasing();
				assertThat(page).hasURL(PlayWrightFactory_Prod.initProp().getProperty("campaignUrl"));
			}
	
		}
		@Test(priority = 34,groups = {"Donation","Team"})
		public void clickOnCampaing_Team() {
			pr=new PeerToPeerFundraisingRepo_Prod(page);
			CampaignName=pr.getCampaignName();
			pr.clickOnOneCampaign();
			String title=pr.getCampaignName();
			assertEquals(CampaignName, title);
			
		}
		
		@Test(priority = 35,groups = {"Donation","Team"})
		public void goToCampaignTeam() {
			pr=new PeerToPeerFundraisingRepo_Prod(page);
			pr.goToTeamSection();
			teamName=pr.getTeamName();
			pr.clickOnTeam();
			String teamTitle=pr.getTeamTitle();
			assertEquals(teamName, teamTitle);
		}
		
		
		@Test(priority = 36,groups = {"Donation","Team"})
		public void goToTeamDonationPage() {
			pr=new PeerToPeerFundraisingRepo_Prod(page);
			pr.teamDonate();
		}
		
		
		@Test(priority = 37,groups = {"Donation","Team"})
		public void setuserDetails_Team() {
			intDon=new InternalDonationRepo(page);
			teamDonorFirstName="JulliTeam"+z;
			teamDonorLastName="dwarTeam"+z;
			teamDonorMailId="engineering+team"+z+"@affnetz.com";
			teamDonorAmt="36";
			intDon.setPesonalDetails(teamDonorFirstName, teamDonorLastName, "6371772552", teamDonorMailId);
			intDon.setDonationAmount(teamDonorAmt);
		}
		@Test(priority = 38,groups = {"Donation","Team"})
		public void setUserAddress_Team() throws InterruptedException {
			intDon=new InternalDonationRepo(page);
			intDon.setAddress();
		}
		@Test(priority = 39,groups = {"Donation","Team"})
		public void setCardDetails_Team() {
			intDon=new InternalDonationRepo(page);
			intDon.setCardDetails();
		}
		@Test(priority = 40,groups = {"Donation","Team"})
		public void isDonationDone_Team() {
			intDon=new InternalDonationRepo(page);
			intDon.clickOnDonate();
			intDon.isFormSubmit();
		}
		@Test(priority = 41,groups = {"Donation","Team"})
		public void isReceiptDownload_Team() throws InterruptedException {
			intDon=new InternalDonationRepo(page);
			boolean flag=intDon.downloadReceipt();
			assertTrue(flag);
		}
		@Test(priority = 42,groups = {"Donation","Team"})
		public void isDonorDetailsisStoredInDonationreport_Team() throws InterruptedException {
			page.goBack();
			db=new DashboardRepo_Prod(page);
			db.goToDashBoard();
			db.clickOnDonorReportLink();
			String donorName=teamDonorFirstName+" "+teamDonorLastName;
			boolean flag=db.isDonorDetailsShownInDonorReport(donorName, teamDonorMailId, teamDonorAmt);
			assertTrue(flag);
		}
		@Test(priority = 43,groups = {"Donation","Team"})
		public void isDonorDetailsIsShowingInThatParticularCampaignTeamDonatiosSection() throws InterruptedException {
			db=new DashboardRepo_Prod(page);
			db.clickOnPeerToPeerFundarasing();
			pr=new PeerToPeerFundraisingRepo_Prod(page);
			pr.searchCampaign(CampaignName);
			pr.clickOnSearchButton();
			String campaignName=pr.getSearchedCampName();
			assertEquals(campaignName,CampaignName);
			pr.clickOnSearchedCamp();
			pr.goToTeamSection();
			pr.clickRightTeam(teamName);
			String title=pr.getTeamTitle();
			assertEquals(teamName, title);
			pr.goToDonationsSection();
			pr.isDonorDetailsShowingInThisPartocularCampaing(teamDonorFirstName, teamDonorMailId, teamDonorAmt);
			
		}
		@Test(priority = 43,groups = {"Donation","Team"})
		public void isDonorShownInScreeningProcessForApproveTheuser_Team() throws InterruptedException {
			settRepo=new SettingPageRepo(page);
			db=new DashboardRepo_Prod(page);
			db.goToSettingPage();
			settRepo.gotToScreeningProcessPage();
			String name=teamDonorFirstName+" "+teamDonorLastName;
			settRepo.isDonorDetailsShownInScreeningProcess(name, teamDonorMailId,"Donors");
		}
			

	
	
	

}
