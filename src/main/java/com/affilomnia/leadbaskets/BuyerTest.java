package com.affilomnia.leadbaskets;

import static com.codeborne.selenide.Selenide.open;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.codeborne.selenide.Configuration;
import com.google.inject.Inject;

import Pages.BuyerPage;
import Pages.LoginPage;

@Guice
public class BuyerTest {
	@Inject
	LoginPage loginPage;
	@Inject
	BuyerPage buyerPage;
	@BeforeClass
	public void before(){
		Configuration.startMaximized = true;
		Configuration.screenshots = true;
		Configuration.headless =true;
		Configuration.holdBrowserOpen = true;
		open("https://test_app.leadsbasket.com");
	}

	@Test
	public void LoginAsAbuyerAndTestkStatistic() throws Exception {
		loginPage.login("kirilk+bidder@affilomania.com", "Test123456@@");		
		Float result[] = buyerPage.calcTotalSpentAndTotalLead();
		buyerPage.checkTotalSpentAndTotalLeads(result[1], result[0]);
		
		
	}

}