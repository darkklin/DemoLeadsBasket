package com.affilomnia.leadbaskets;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.testng.annotations.Report;
import com.google.inject.Inject;
import java.net.URL;

import Pages.FormLbPage;
import Pages.LoginPage;
import Pages.SellerPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import libry.BaseListener;
import libry.TextReport;

import static com.codeborne.selenide.Selenide.*;

@Report
@Guice
@Listeners({ BaseListener.class, VideoListener.class })
public class FormLbTest  {
	@Inject
	LoginPage loginPage;
	@Inject
	SellerPage sellerPage;
	@Inject
	FormLbPage formLbPage;

//	public static final String USERNAME = "darkklin";
//	public static final String ACCESS_KEY = "7efbfcf7-2327-4098-919f-35d3bfdcb047";
//	public static final String URL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";
//	@BeforeTest
//	public void beforeTest() throws Exception {
//		 WebDriver driver;
//
//		DesiredCapabilities caps = DesiredCapabilities.chrome();
//	    caps.setCapability("platform", "Windows 10");
//	    caps.setCapability("version", "64.0");
//	    driver = new RemoteWebDriver(new URL(URL), caps);
//		WebDriverRunner.setWebDriver(driver);
//		
//	}
	
	@Feature("FormLb")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = false, description = "seller send one lead thought offer 543 ", groups = {
			"createLead" }, priority = 1)
	public void LoginAsSellerAndSendLead() throws Exception {
		open("https://test_app.leadsbasket.com");
		loginPage.login("kirilk+webdriver1@affilomania.com", "0546474985Ko"); //selelr id 710
		String targtingOfferLink = sellerPage.Offerlink();
		open(targtingOfferLink);
		formLbPage.regLead("@lb.com","");
		open(targtingOfferLink);
		back();
		back();
		sellerPage.logOut();
	}
	@Video
	@Feature("FormLb")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = false, description = "Sent lead through embedded form ", groups = { "createLead" }, priority = 2)
	public void EmbeddedForm()throws Exception {
		open("http://52.17.171.159/EmbeddedOffer/");
		formLbPage.regLead("@lb.com","");
	}
}
