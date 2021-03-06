package com.affilomnia.leadbaskets;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import static com.codeborne.selenide.Selenide.*;

import libry.TextReport;

import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import libry.VideoRecord;

public class BaseTest {

	@BeforeSuite(alwaysRun=true)
	@Description("start")
	public void beforeTest() throws Exception {
		Configuration.browser = "chrome";
		Configuration.baseUrl = "https://test_app.leadsbasket.com";
		Configuration.startMaximized = true;
		Configuration.screenshots = true;
		Configuration.headless = false;
		Configuration.holdBrowserOpen = false;
		Configuration.fastSetValue = true;
		Configuration.driverManagerEnabled = true;
		TextReport.onSucceededTest = true;
		TextReport.onFailedTest = true;
		
	}
	@AfterMethod(alwaysRun=true)
	public void logs(ITestResult testResult) throws Exception {
		logOutput(Reporter.getOutput(testResult));
		VideoRecord.attachment();
		
		if(!testResult.isSuccess())
		{
			if( $$("a.profile").size() != 0)
			{
				closeWebDriver();
				}
			else
			{
				close();

			}
		}

	}

	@Attachment(value = "Events Report", type = "text/html")
	public String logOutput(List<String> outputList) {
		String output = "";
		for (String o : outputList)
			output += o + "<br>";
		return output;
	}
	
	private SelenideElement profile = $("a.profile");
	private SelenideElement logOut = $("div.drop_userInfo>ul>li:nth-child(5)>a");
	public void logOut() {
		profile.click();
		logOut.shouldBe(Condition.visible).click();
	}
}
