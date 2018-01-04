package com.affilomnia.leadbaskets;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.google.inject.Inject;
import Pages.LoginPage;
import Pages.SellerPage;
import static com.codeborne.selenide.Selectors.*;
import com.codeborne.selenide.commands.ShouldBe.*;
import static com.codeborne.selenide.Selenide.*;


@Guice
public class FormLbTest {
	@Inject
	LoginPage loginPage;
	@Inject
	SellerPage sellerPage;
	
	@BeforeClass
	public void before(){
		Configuration.startMaximized = false;
		Configuration.screenshots = false;
		Configuration.driverManagerEnabled = true;
		Configuration.fastSetValue = true;
		Configuration.savePageSource = false;
		Configuration.baseUrl = "https://test_app.leadsbasket.com";
	}

	@Test
	public void login() throws Exception {
		open("");
		loginPage.login("kirilk+webdriver@affilomania.com", "Test123456@");
		sellerPage.liveOffer.click();
		$(byText("selenium Webdriver(don't use!!!)")).click();

		
		
	}
}
