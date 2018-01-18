package com.affilomnia.leadbaskets;
import static com.codeborne.selenide.Selenide.open;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.codeborne.selenide.Configuration;
import com.google.inject.Inject;

import Pages.LoginPage;
import Pages.SellerPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import libry.BaseListener;

@Guice
@Listeners({ BaseListener.class, VideoListener.class })

public class SellerTest {
	@Inject
	LoginPage loginPage;
	@Inject
	SellerPage sellerTest;
	
	@BeforeClass
	public void before() {
		Configuration.startMaximized = true;
		Configuration.screenshots = true;
		Configuration.headless = true;
		Configuration.holdBrowserOpen = true;
	}
	
	@Video
	@Feature("Seller Statistic")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true, description = "How much seller have leads and Total CPl", groups = { "Seller Statistic" }, priority = 1)
	public void testTotalLeadTotalCpl() 
	{
		open("https://test_app.leadsbasket.com");
		loginPage.login("kirilk+webdriver@affilomania.com", "Test123456@");
		sellerTest.calculateCplAndLeads();
		
		
	}
	
	


}
