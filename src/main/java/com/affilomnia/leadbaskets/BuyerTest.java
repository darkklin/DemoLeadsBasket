package com.affilomnia.leadbaskets;

import static com.codeborne.selenide.Selenide.open;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.codeborne.selenide.Configuration;
import com.google.inject.Inject;
import com.codeborne.selenide.WebDriverRunner;
import Pages.BuyerPage;
import Pages.LoginPage;

@Guice
public class BuyerTest {

	@Inject
	LoginPage loginPage;
	@Inject
	BuyerPage buyerPage;
	@BeforeClass
	public void before(){
		Configuration.startMaximized = true;
		Configuration.screenshots = true;
		Configuration.headless =true;
		Configuration.holdBrowserOpen = true;
	}
	
	@Test(enabled = true,description = "1", groups = { "buyer statistic" },priority=2)
	public void testLoginTotalSpentAndLeads() throws Exception {
		open("https://test_app.leadsbasket.com");
		loginPage.login("kirilk+bidder@affilomania.com", "Test123456@@");	
		buyerPage.calcTotalSpentAndTotalLead();
		buyerPage.logOut();		
	}
	@Test(enabled = true,description = "2", groups = { "buyer statistic" },priority=1)
	public void testTotalLeadTotalSpentAvgcplParCamp() throws Exception
	{
		open("https://test_app.leadsbasket.com");
		loginPage.login("kirilk+bidder@affilomania.com", "Test123456@@");		
		buyerPage.checkStatisticPerCamp();
		buyerPage.logOut();		


	}
	@Test(enabled = false,description = "3", groups = { "BuyerReg" },priority=1)
	public void testBuyerRegistrtion() 
	{
		open("https://test_app.leadsbasket.com/register-industry");
		buyerPage.industryPage();
		buyerPage.registerPage();
		buyerPage.integrationPage();
		buyerPage.billingPage();
		buyerPage.finishPage();
	}
	@Test(enabled = true,description = "4", groups = { "BuyerReg" },priority=1)
	public void testdownloadIntegrtionPDF() throws Exception
	{
		open("https://app.leadsbasket.com/register/integration");
		buyerPage.downalodPdf();		
	}
	
	

}
