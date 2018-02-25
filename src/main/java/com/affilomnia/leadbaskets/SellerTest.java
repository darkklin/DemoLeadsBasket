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

	@Video
	@Feature("Seller Statistic")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = false, description = "How much seller have leads and Total CPl", groups = {
			"sellerStatistic" }, priority = 1)
	public void testTotalLeadTotalCpl() throws Exception {
		open("https://test_app.leadsbasket.com");
		loginPage.login("kirilk+webdriver1@affilomania.com", "0546474985Ko");
		$(byText("Report")).click();
		Float statResult[] = sellerPage.calcTotalRevenueLeadActualAvgCpl();
		sellerPage.checkStatisticOnDashBoard(statResult[0], statResult[1], statResult[2], statResult[3]);
		sellerPage.logOut();
	}

	@Video
	@Feature("Seller Statistic")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true, description = "Test Statistic Par offer ", groups = { "sellerStatistic" }, priority = 2)
	public void testStatisticParOffer() throws Exception {
		open("https://test_app.leadsbasket.com");
		loginPage.login("kirilk+webdriver1@affilomania.com", "0546474985Ko");
		sellerPage.checkstatParOffer();
		sellerPage.logOut();
	}
	@Video
	@Feature("Seller Statistic")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true, description = "Test seller accounting statistic ", groups = { "sellerStatistic" }, priority = 3)
	public void sellerAccountingStatistic() throws Exception {
		open("https://test_app.leadsbasket.com");
		loginPage.login("kirilk+webdriver1@affilomania.com", "0546474985Ko");
		sellerPage.accountingStatistic();
		sellerPage.logOut();
	}
	
	@Video
	@Feature("Seller Registrtion + Forget password")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = false, description = "Seller Registion + forget password test", groups = { "sellerRegistrtion" }, priority = 1)
	public void sellerRegistrtion() throws Exception {
		String sellerEmail = sellerPage.tenMinutEmail();
		open("https://test_app.leadsbasket.com/register-seller-start");
		sellerPage.startRegister(sellerEmail);
		sellerPage.verifyEmail();
		sellerPage.billingInformation();
		sellerPage.forgetPassword(sellerEmail);
	}

}
