package com.affilomnia.leadbaskets;

import static com.codeborne.selenide.Selenide.open;

import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.codeborne.selenide.testng.annotations.Report;
import com.google.inject.Inject;
import Pages.BuyerPage;
import Pages.LoginPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import libry.BaseListener;
import libry.TextReport;

@Report
@Guice
@Listeners({TextReport.class, BaseListener.class, VideoListener.class })
public class BuyerTest extends BaseTest {

	@Inject
	LoginPage loginPage;
	@Inject
	BuyerPage buyerPage;

	@Video
	@Feature("Buyer Statistic")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true, description = "Test total leads Total spent of buyer", groups = { "buyer statistic" }, priority = 2)
	public void testLoginTotalSpentAndLeads()  {
		open("https://test_app.leadsbasket.com");
		loginPage.login("kirilk+bidder@affilomania.com", "0546474985Ko");
		buyerPage.calcTotalSpentAndTotalLead();
		buyerPage.logOut();
	}

	@Video
	@Feature("Buyer Statistic")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true, description = "Test total leads/spent/avgcpl par offer ", groups = { "buyer statistic" }, priority = 1)
	public void testTotalLeadTotalSpentAvgcplParCamp() throws Exception {
		open("https://test_app.leadsbasket.com");
		loginPage.login("kirilk+bidder@affilomania.com", "0546474985Ko");
		buyerPage.checkStatisticPerCamp();
		buyerPage.logOut();

	}

	@Video
	@Feature("Registration")
	@Severity(SeverityLevel.BLOCKER)
	@Test(enabled = true, description = "Buyer Registration", groups = { "BuyerReg" }, priority = 1)
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
	@Feature("Registration")
	@Severity(SeverityLevel.NORMAL)
	@Test(enabled = true, description = "Download PDF integrtion API  ", groups = { "BuyerReg" }, priority = 2)
	public void testdownloadIntegrtionPDF() throws Exception {
		open("https://app.leadsbasket.com/register/integration");
		buyerPage.downalodPdf();
	}
	
}
