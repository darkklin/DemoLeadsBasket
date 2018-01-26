package com.affilomnia.leadbaskets;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import com.codeborne.selenide.Configuration;

import io.qameta.allure.Description;
import libry.VideoRecord;

public class BaseTest {
	
	@BeforeSuite(groups = { "Regression TestSuite" })
	@Description("start")
	public void beforeTest() {
		Configuration.startMaximized = true;
		Configuration.screenshots = true;
		Configuration.headless = false;
		Configuration.holdBrowserOpen = false;
		Configuration.fastSetValue = true;
		Configuration.driverManagerEnabled = true;
	}

	@AfterMethod(groups = { "BuyerReg","buyer statistic" })
	public void video() throws Exception {
		VideoRecord.attachment();

	}
	 

}
