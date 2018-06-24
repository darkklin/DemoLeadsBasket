package com.affilomnia.leadbaskets;

import static com.codeborne.selenide.Selenide.open;

import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.codeborne.selenide.testng.annotations.Report;
import com.google.inject.Inject;

import libry.DataBaseConnect;
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
	@Inject
	AdminTest adminTest;
	@Inject
	DataBaseConnect db;

	private String buyerEmail = "kirilk+bidder@affilomania.com";
	private String password = "0546474985Ko";
	
	@Video
	@Feature("Buyer Statistic")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true, description = "Test total leads Total spent of buyer", groups = {
			"buyerStatistic" }, priority = 1)
	public void testLoginTotalSpentAndLeads() {
		open("");
		loginPage.login(buyerEmail, password);
		buyerPage.calcTotalSpentAndTotalLead();
		buyerPage.logOut();
	}
	@Video
	@Feature("Buyer Statistic")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true, description = "Test total leads/spent/avgcpl par offer ", groups = {
			"buyerStatistic" }, priority = 2)
	public void testTotalLeadTotalSpentAvgcplParCamp() {
		open("");
		loginPage.login(buyerEmail, password);
		buyerPage.checkStatisticPerCamp();
		buyerPage.logOut();
	}
	@Video
	@Feature("Buyer Registration")
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
	@Feature("Buyer Registration")
	@Severity(SeverityLevel.NORMAL)
	@Test(enabled = true, description = "Download PDF integrtion API  ", groups = { "BuyerReg" }, priority = 2)
	public void testdownloadIntegrtionPDF() throws Exception {
		open("https://app.leadsbasket.com/register/integration");
		buyerPage.downalodPdf();
	}
	@Video
	@Feature("Create Campaign")
	@Severity(SeverityLevel.NORMAL)
	@Test(enabled = true, description = "Buyer Create Campaign  ", groups = { "Campaign" }, priority = 1)
	public void creteCampaign() {
		open("");
		loginPage.login("lbdemo234+3dbd%%df@gmail.com", password);
		buyerPage.createCamp();
		buyerPage.logOut();
	}
	@Video
	@Feature("Dispute leads")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(enabled = true, description = "Buyer dispute lead  ", groups = { "dispute" }, priority = 1)
	public String disputeLead() throws Exception {
		open("http://52.17.171.159/seleniumOfferDontUse/");
		String email = formLbPage.regLead("@dispute.com","");
		System.out.println("Dispute email " + email);
		open("");
		loginPage.login(buyerEmail, password);
		buyerPage.buyerDisputeLead(email);
		buyerPage.logOut();
		return email;
	}
	@Video
	@Feature("Coupon")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(enabled = true, description = "Buyer anter Valid Coupon ", groups = { "Coupon" }, priority = 1)
	public void anterValidCoupon() throws Exception {
		String couponId = adminTest.couponGenerator();
		db.executeStatement("update billing_profile set balance = null where user_id = 617","Update balance for buyer");
		open("");
		loginPage.login("lbdemo234+3dbd%%df@gmail.com", password);
		buyerPage.buyerEnterValidCoupon(couponId);
		db.executeStatement("update coupons set buyer_id = null where buyer_id = 617","Delete buyer Coupon ");	
		buyerPage.logOut();

	}
	@Video
	@Feature("Coupon")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(enabled = true, description = "Buyer anter Invalid Coupons ", groups = { "Coupon" }, priority = 2)
	public void anterInvalidCoupon() throws Exception {
		String couponId = adminTest.couponGenerator();
		open("");
		loginPage.login("invalid@cupon.com", password);
		buyerPage.buyerEnterInvalidCoupon(couponId);
		db.executeStatement("delete from billing_profile_creditcard where profile_id = 502","Delete credit Card  ");
		buyerPage.logOut();


	}
	
	

	

}
