package com.affilomnia.leadbaskets;

import java.util.List;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import com.codeborne.selenide.Configuration;
import libry.TextReport;

import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import libry.VideoRecord;

public class BaseTest {

	@BeforeSuite(groups = { "Regression TestSuite" })
	@Description("start")
	public void beforeTest() {
		Configuration.startMaximized = true;
		Configuration.screenshots = true;
		Configuration.headless = false;
		Configuration.holdBrowserOpen = true;
		Configuration.fastSetValue = true;
		Configuration.driverManagerEnabled = true;
		TextReport.onSucceededTest = false;
		TextReport.onFailedTest = true;
	}

	@AfterMethod(groups = { "BuyerReg", "buyer statistic", "Seller Statistic", "create Lead" })
	public void logs(ITestResult testResult) throws Exception {
		VideoRecord.attachment();
		logOutput(Reporter.getOutput(testResult));

	}

	@Attachment(value = "Events Report", type = "text/html")
	public String logOutput(List<String> outputList) {
		String output = "";
		for (String o : outputList)
			output += o + "<br>";
		return output;
	}
}
