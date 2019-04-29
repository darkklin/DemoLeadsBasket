package com.affilomnia.leadbaskets;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.testng.Reporter;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.codeborne.selenide.testng.annotations.Report;
import com.google.inject.Inject;
import libry.TextReport;

import Pages.LoginPage;
import Pages.SellerPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import libry.BaseListener;

@Report
@Guice
@Listeners({ TextReport.class, BaseListener.class, VideoListener.class })
public class SellerTest extends BaseTest {
	@Inject
	LoginPage loginPage;
	@Inject
	SellerPage sellerPage;

	private String email = "mvx09567@qiaua.com";
	private String password = "0546474985ko";

	@Video
	@Feature("Seller Statistic")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = false, description = "How much seller have leads and Total CPl", groups = {
			"sellerStatistic" }, priority = 1)
	public void testTotalLeadTotalCpl() {
		open("");
		loginPage.login(email, password);
		$(byText("Report")).click();
		Double statResult[] = sellerPage.calcTotalRevenueLeadActualAvgCpl();
		sellerPage.checkStatisticOnDashBoard(statResult[0], statResult[1], statResult[2], statResult[3]);
		sellerPage.logOut();
	}

	@Video
	@Feature("Seller Statistic")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = false, description = "Test Statistic Par offer ", groups = { "sellerStatistic" }, priority = 3)
	public void testStatisticParOffer()  {
		open("");
		loginPage.login(email, password);
		sellerPage.checkstatParOffer();
		sellerPage.logOut();
	}
	@Video
	@Feature("Seller Statistic")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = false, description = "Test seller accounting statistic ", groups = { "sellerStatistic" }, priority = 2)
	public void sellerAccountingStatistic() {
		open("");
		loginPage.login(email, password);
		sellerPage.accountingStatistic();
		sellerPage.logOut();
	}
	@Video
	@Feature("Seller Registrtion")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = false, description = "Seller Registion", groups = { "sellerRegistrtion" }, priority = 1)
	public void sellerRegistrtion() throws Exception {
		open("/register-seller-start?r=lb");
		sellerPage.startRegister();
		sellerPage.verifyEmail();
		sellerPage.billingInformation();
	}
	@Video
	@Feature("Forget password")
	@Severity(SeverityLevel.NORMAL)
	@Test(enabled = true, description = " forget password test", groups = { "sellerRegistrtion" }, priority = 1)
	public void sellerRestorPasword() throws Exception {
		open("");
		sellerPage.forgetPassword("lbdemo234+d2d3adbf@gmail.com");
	}
	
}
