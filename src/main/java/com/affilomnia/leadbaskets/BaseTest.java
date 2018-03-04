package com.affilomnia.leadbaskets;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.close;

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

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import libry.TextReport;

import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import libry.VideoRecord;

public class BaseTest {

	@BeforeSuite(alwaysRun=true)
	@Description("start")
	public void beforeTest() throws Exception {
//		String urlToRemoteWD = "http://localhost:4446/wd/hub";
//		RemoteWebDriver driver =new RemoteWebDriver(new URL(urlToRemoteWD),DesiredCapabilities.chrome());
//		WebDriverRunner.setWebDriver(driver);
		
		Configuration.startMaximized = true;
		Configuration.screenshots = true;
		Configuration.headless = false;
		Configuration.holdBrowserOpen = false;
		Configuration.fastSetValue = true;
		Configuration.driverManagerEnabled = true;
		TextReport.onSucceededTest = false;
		TextReport.onFailedTest = true;
		
	}

	@AfterMethod(alwaysRun=true)
	public void logs(ITestResult testResult) throws Exception {
		logOutput(Reporter.getOutput(testResult));
		VideoRecord.attachment();
		
		if(!testResult.isSuccess())
		{
			if(profile.shouldBe(Condition.visible) != null)
			{
				logOut();
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
