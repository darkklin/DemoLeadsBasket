package com.affilomnia.leadbaskets;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.html5.LocationContext;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class test {
	WebDriver driver;

	@Test
	public void testTotalLeadTotalCpl() {
		WebDriver driver;
		String serverIP = "46.252.35.74";
		String port = "8080";

		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("network.proxy.type", 1);
		profile.setPreference("network.proxy.ftp", serverIP);
		profile.setPreference("network.proxy.http", serverIP);
		profile.setPreference("network.proxy.socks", serverIP);
		profile.setPreference("network.proxy.ssl", serverIP);
		profile.setPreference("network.proxy.ftp_port", port);
		profile.setPreference("network.proxy.http_port", port);
		profile.setPreference("network.proxy.socks_port", port);
		profile.setPreference("network.proxy.ssl_port", port);
		driver = new FirefoxDriver((Capabilities) profile);

	}
}
