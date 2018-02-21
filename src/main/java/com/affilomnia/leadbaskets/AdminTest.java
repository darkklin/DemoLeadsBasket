package com.affilomnia.leadbaskets;

import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.codeborne.selenide.testng.annotations.Report;
import com.google.inject.Inject;
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

	@Video
	@Feature("admin forget password")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true, description = "Test forget Password", groups = { "forgetPassword" }, priority = 1)
	public void forgetPasswordTest()
	{
		open("https://test_staff.leadsbasket.com/admin/login");
		adminPage.forgetPassword();
		open("https://test_staff.leadsbasket.com/admin/login");
		loginPage.login("KIRL@gmx.com", "Test123456@");
		
	}

}
