package com.affilomnia.leadbaskets;

import static com.codeborne.selenide.Selenide.open;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.codeborne.selenide.Configuration;
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
@Listeners({TextReport.class, BaseListener.class, VideoListener.class })
public class SellerTest extends BaseTest  {
	@Inject
	LoginPage loginPage;
	@Inject
	SellerPage sellerPage;


	@Video
	@Feature("Seller Statistic")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = false, description = "How much seller have leads and Total CPl", groups = {
			"Seller Statistic" }, priority = 1)
	public void testTotalLeadTotalCpl() throws Exception  {

		open("https://test_app.leadsbasket.com");
		loginPage.login("kirilk+webdriver@affilomania.com", "0546474985Ko");
		Float statResult[] = sellerPage.calcTotalRevenueLeadActualAvgCpl();
		sellerPage.checkStatisticOnDashBoard(statResult[0], statResult[1], statResult[2]);
	
	}
	
	@Video
	@Feature("Seller Statistic")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true, description = "Test Stats Par offer ", groups = {
			"Seller Statistic" }, priority = 1)
	public void testStatisticParOffer() throws Exception  {

		open("https://test_app.leadsbasket.com");
		loginPage.login("kirilk+webdriver@affilomania.com", "0546474985Ko");
		sellerPage.checkstatParOffer();
	}

}
