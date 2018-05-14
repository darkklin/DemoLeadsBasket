package com.affilomnia.leadbaskets;

import static com.codeborne.selenide.Selenide.open;

import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.codeborne.selenide.testng.annotations.Report;
import com.google.inject.Inject;

import Pages.AdminPage;
import Pages.BuyerPage;
import Pages.FormLbPage;
import Pages.LoginPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import libry.BaseListener;
import libry.TextReport;

@Report
@Guice
@Listeners({ TextReport.class, BaseListener.class, VideoListener.class })
public class BuyerTest extends BaseTest {

	@Inject
	LoginPage loginPage;
	@Inject
	BuyerPage buyerPage;
	@Inject
	FormLbPage formLbPage;
	@Inject
	AdminPage adminPage;

	@Video
	@Feature("Buyer Statistic")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true, description = "Test total leads Total spent of buyer", groups = {
			"buyerStatistic" }, priority = 1)
	public void testLoginTotalSpentAndLeads() {
		open("https://test_app.leadsbasket.com");
		loginPage.login("kirilk+bidder@affilomania.com", "0546474985Ko");
		buyerPage.calcTotalSpentAndTotalLead();
		buyerPage.logOut();
	}

	@Video
	@Feature("Buyer Statistic")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true, description = "Test total leads/spent/avgcpl par offer ", groups = {
			"buyerStatistic" }, priority = 2)
	public void testTotalLeadTotalSpentAvgcplParCamp() {
		open("https://test_app.leadsbasket.com");
		loginPage.login("kirilk+bidder@affilomania.com", "0546474985Ko");
		buyerPage.checkStatisticPerCamp();
		buyerPage.logOut();
	}

	@Video
	@Feature("Buyer Registration")
	@Severity(SeverityLevel.BLOCKER)
	@Test(enabled = false, description = "Buyer Registration", groups = { "BuyerReg" }, priority = 1)
	public void testBuyerRegistrtion() {
		open("https://test_app.leadsbasket.com/register-industry");
		buyerPage.industryPage();
		buyerPage.registerPage();
		buyerPage.integrationPage();
		buyerPage.billingPage();
		buyerPage.finishPage();
		buyerPage.logOut();
	}

	@Video
	@Feature("Buyer Registration")
	@Severity(SeverityLevel.NORMAL)
	@Test(enabled = false, description = "Download PDF integrtion API  ", groups = { "BuyerReg" }, priority = 2)
	public void testdownloadIntegrtionPDF() throws Exception {
		open("https://app.leadsbasket.com/register/integration");
		buyerPage.downalodPdf();
	}

	@Video
	@Feature("Create Campaign")
	@Severity(SeverityLevel.NORMAL)
	@Test(enabled = false, description = "Buyer Create Campaign  ", groups = { "Campaign" }, priority = 1)
	public void creteCampaign() {
		open("https://test_app.leadsbasket.com");
		loginPage.login("lbdemo234+3dbd%%df@gmail.com", "0546474985Ko");
		buyerPage.createCamp();
		buyerPage.logOut();

	}

	@Video
	@Feature("Dispute leads")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(enabled = false, description = "Buyer dispute lead  ", groups = { "dispute" }, priority = 1)
	public void disputeLead() throws Exception {
		open("http://52.17.171.159/seleniumOfferDontUse/");
		String email = formLbPage.regLead("@dispute.com","");
		System.out.println("Dispute email" + email);
		open("https://test_app.leadsbasket.com");
		loginPage.login("kirilk+bidder@affilomania.com", "0546474985Ko");
		buyerPage.buyerDisputeLead(email);
		buyerPage.logOut();

	}

	

}
