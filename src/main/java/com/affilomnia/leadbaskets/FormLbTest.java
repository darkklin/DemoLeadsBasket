package com.affilomnia.leadbaskets;


import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;

import com.google.inject.Inject;

import Pages.FormLbPage;
import Pages.LoginPage;
import Pages.SellerPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import libry.BaseListener;
import libry.TextReport;

import static com.codeborne.selenide.Selenide.*;


@Guice
@Listeners({ BaseListener.class, VideoListener.class })
public class FormLbTest  {
	@Inject
	LoginPage loginPage;
	@Inject
	SellerPage sellerPage;
	@Inject
	FormLbPage formLbPage;

	
	
	@Feature("FormLb")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true, description = "seller send one lead thought offer 543 ", groups = {
			"createLead" }, priority = 1)
	public void LoginAsSellerAndSendLead() throws Exception {
		open("https://test_app.leadsbasket.com");
		loginPage.login("kirilk+webdriver1@affilomania.com", "0546474985Ko");
		String targtingOfferLink = sellerPage.Offerlink();
		open(targtingOfferLink);
		formLbPage.regLead("@lb.com");
		open(targtingOfferLink);
		back();
		back();
		sellerPage.logOut();
	}
	@Video
	@Feature("FormLb")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true, description = "Sent lead through embedded form ", groups = { "createLead" }, priority = 2)
	public void EmbeddedForm() {
		open("http://52.17.171.159/EmbeddedOffer/");
		formLbPage.regLead("@lb.com");
	}
}
