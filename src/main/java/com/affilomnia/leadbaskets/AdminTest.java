package com.affilomnia.leadbaskets;

import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.testng.annotations.Report;
import com.google.inject.Inject;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import Pages.AdminPage;
import Pages.LoginPage;
import Pages.SellerPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import libry.BaseListener;
import libry.TextReport;


@Report
@Guice
@Listeners({TextReport.class, BaseListener.class, VideoListener.class })
public class AdminTest extends BaseTest {
	@Inject
	AdminPage adminPage;
	@Inject
	LoginPage loginPage;
	@Inject
	BuyerTest buyerTest;

	@Video
	@Feature("admin forget password")
	@Severity(SeverityLevel.NORMAL)
	@Test(enabled = true, description = "Test forget Password", groups = { "forgetPassword" }, priority = 1)
	public void forgetPasswordTest()
	{
		open("https://test_staff.leadsbasket.com/admin/login");
		adminPage.forgetPassword();
		open("https://test_staff.leadsbasket.com/admin/login");
		loginPage.login("kirill3@gmx.com", "Test123456@");
		adminPage.logOut();
		
	}
	
	@Video
	@Feature("Accep Dispute leads")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(enabled = true, description = "admin Accept dispute", groups = { "adminDispute" }, priority = 1)
	public void adminAcceptDispute() {
		open("https://test_staff.leadsbasket.com");
		loginPage.login("kirill3@gmx.com", "Test123456@");
		adminPage.acceptDispute("Yes");		
		adminPage.logOut();
	}
	@Video
	@Feature("Dispute leads")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(enabled = true, description = "admin Declined dispute", groups = { "adminDispute" }, priority = 2)
	public void adminDeclinedDispute() throws Exception {
		buyerTest.disputeLead();
		open("https://test_staff.leadsbasket.com");
		loginPage.login("kirill3@gmx.com", "Test123456@");
		adminPage.acceptDispute("No");	
		adminPage.logOut();

	}


}
