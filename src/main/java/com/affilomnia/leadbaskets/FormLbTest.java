package com.affilomnia.leadbaskets;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.codeborne.selenide.Configuration;
import com.google.inject.Inject;

import Pages.FormLbPage;
import Pages.LoginPage;
import Pages.SellerPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import static com.codeborne.selenide.Selenide.*;

@Guice
public class FormLbTest {
	@Inject
	LoginPage loginPage;
	@Inject
	SellerPage sellerPage;
	@Inject
	FormLbPage formLbPage;
	@BeforeClass
	public void before(){
		Configuration.startMaximized = true;
		Configuration.screenshots = true;
		Configuration.headless =true;
		Configuration.holdBrowserOpen = true;
		Configuration.baseUrl = "https://test_app.leadsbasket.com";
	}
	@Feature("FormLb")
	@Severity(SeverityLevel.CRITICAL)
	@Test (description = "seller send one lead thought offer 543 ")
	public void LoginAsSellerAndSendLead() throws Exception {
		open("");
		loginPage.login("kirilk+webdriver@affilomania.com", "Test123456@");
		sellerPage.OpenOfferlink();	
		formLbPage.regLead();
	}
}
