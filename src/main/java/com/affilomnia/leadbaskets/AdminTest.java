package com.affilomnia.leadbaskets;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.testng.annotations.Report;
import com.google.inject.Inject;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.CollectionCondition;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import Pages.AdminPage;
import Pages.FormLbPage;
import Pages.LoginPage;
import Pages.SellerPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import libry.BaseListener;
import libry.DataBaseConnect;
import libry.DatabaseMongoDB;
import libry.TextReport;

@Report
@Guice
@Listeners({ TextReport.class, BaseListener.class, VideoListener.class })
public class AdminTest extends BaseTest {
	@Inject
	AdminPage adminPage;
	@Inject
	LoginPage loginPage;
	@Inject
	BuyerTest buyerTest;
	@Inject
	FormLbPage formLBpage;
	@Inject
	DatabaseMongoDB mongo;
	private String email = "kirill3@gmx.com";
	private String password = "Test123456@";

	@Video
	@Feature("admin forget password")
	@Severity(SeverityLevel.NORMAL)
	@Test(enabled = false, description = "Test forget Password", groups = { "forgetPassword" }, priority = 1)
	public void forgetPasswordTest() {
		open("https://test_staff.leadsbasket.com");
		adminPage.forgetPassword();
		open("https://test_staff.leadsbasket.com/admin/login");
		loginPage.login(email, password);
		adminPage.logOut();
	}

	@Video
	@Test(enabled = false, description = "Test Accept dispute", groups = { "adminDispute" }, priority = 1)
	@Feature("Dispute")
	@Story("Admin accept Disputed leads")
	@Description("Seller send lead -> buyer dispute that lead -> admin accept disputed lead ")
	@Severity(SeverityLevel.TRIVIAL)
	public void adminAcceptDispute() throws Exception {
		String leadEmail = buyerTest.disputeLead();
		open("https://test_staff.leadsbasket.com");
		loginPage.login(email, password);
		adminPage.acceptDispute("Yes", leadEmail);
		adminPage.logOut();
	}

	@Video
	@Test(enabled = false, description = "Test Declined dispute", groups = { "adminDispute" }, priority = 2)
	@Feature("Dispute")
	@Story("Admin declined Disputed leads")
	@Description("Seller send lead -> buyer dispute that lead -> admin declined lead ")
	@Severity(SeverityLevel.TRIVIAL)
	public void adminDeclinedDispute() throws Exception {
		String leadEmail = buyerTest.disputeLead();
		open("https://test_staff.leadsbasket.com");
		loginPage.login(email, password);
		adminPage.acceptDispute("No", leadEmail);
		adminPage.logOut();
	}

	@Video
	@Feature("seller LB Revenue")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(enabled = false, description = "Test  our LB Revenue from seller 462 ", groups = {
			"adminStatistic" }, priority = 1)
	public void sellerLbRevenue() {
		open("https://test_staff.leadsbasket.com");
		loginPage.login(email, password);
		adminPage.sellerLbRevenue();
		adminPage.logOut();
	}

	@Video
	@Feature("Quality")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(enabled = false, description = "Test quality part 1 ", groups = { "Quality" }, priority = 1)
	public void QualityParOne() throws Exception {
		// ctrlv , scroll , reg_duration , reg_time , ctrlv+scroll,reg_duration
		// 5,sec,reg_time 5 sec

		open("https://test_staff.leadsbasket.com");
		loginPage.login(email, password);

		adminPage.updateQuality("95", "ctrlv", "-20");
		adminPage.checkRateScore("ctrlv", "80", "yes");

		open("https://test_staff.leadsbasket.com");
		adminPage.updateQuality("95", "scroll", "-20");
		adminPage.checkRateScore("scroll", "100", "no");

		open("https://test_staff.leadsbasket.com");
		adminPage.updateQuality("80", "ctrlv+scroll", "-20");
		adminPage.checkRateScore("ctrlv+scroll", "80", "no");

		open("https://test_staff.leadsbasket.com");
		adminPage.updateQuality("90", "ctrlv+scroll", "-20");
		adminPage.checkRateScore("ctrlv", "60", "yes");
		Reporter.log("user didn't use Scroll!", false);

		open("https://test_staff.leadsbasket.com");
		adminPage.updateQuality("90", "reg_duration", "-20");
		adminPage.checkRateScore("reg_duration", "100", "no");

		open("https://test_staff.leadsbasket.com");
		adminPage.updateQuality("85", "reg_time", "-20");
		adminPage.checkRateScore("reg_duration", "100", "no");

		open("https://test_staff.leadsbasket.com");
		adminPage.updateQuality("85", "reg_duration", "-20");
		adminPage.checkRateScore("reg_duration 5 sec", "80", "yes");

		open("https://test_staff.leadsbasket.com");
		adminPage.updateQuality("85", "reg_time", "-17");
		adminPage.checkRateScore("reg_time 5 sec", "83", "yes");

		adminPage.resetRules();
		$("div[class*='ui-notification']").waitUntil(Condition.disappears, 10000);
		adminPage.logOut();
	}

	@Video
	@Feature("Coupon")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(enabled = false, description = "Just create coupon in the system", groups = { "Coupon" }, priority = 2)
	public String couponGenerator() throws Exception {
		open("https://test_staff.leadsbasket.com");
		loginPage.login(email, password);
		String coupon = adminPage.createCoupon();
		adminPage.logOut();
		return coupon;

	}

	@Video
	@Feature("SMS Verification")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(enabled = true, description = "Test sms Verification", groups = { "Coupon" }, priority = 1)
	public void smsVerification() {
//		adminPage.turnOnOfferSMSnotification("316");
		mongo.updateQuary();
		formLBpage.smsVerification();

	}

}
