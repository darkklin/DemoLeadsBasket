package com.affilomnia.leadbaskets;

import org.testng.Reporter;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.testng.annotations.Report;
import com.google.inject.Inject;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import Pages.AdminPage;
import Pages.LoginPage;
import Pages.SellerPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import libry.BaseListener;
import libry.TextReport;


@Report
@Guice
@Listeners({TextReport.class, BaseListener.class, VideoListener.class })
public class AdminTest extends BaseTest {
	@Inject
	AdminPage adminPage;
	@Inject
	LoginPage loginPage;
	@Inject
	BuyerTest buyerTest;

	@Video
	@Feature("admin forget password")
	@Severity(SeverityLevel.NORMAL)
	@Test(enabled = false, description = "Test forget Password", groups = { "forgetPassword" }, priority = 1)
	public void forgetPasswordTest()
	{
		open("https://test_staff.leadsbasket.com/admin/login");
		adminPage.forgetPassword();
		open("https://test_staff.leadsbasket.com/admin/login");
		loginPage.login("kirill3@gmx.com", "Test123456@");
		adminPage.logOut();		
	}
	
	@Video
	@Feature("Accep Dispute leads")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(enabled = false, description = "admin Accept dispute", groups = { "adminDispute" }, priority = 1)
	public void adminAcceptDispute() {
		open("https://test_staff.leadsbasket.com");
		loginPage.login("kirill3@gmx.com", "Test123456@");
		adminPage.acceptDispute("Yes");		
		adminPage.logOut();
	}
	@Video
	@Feature("Dispute leads")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(enabled = false, description = "admin Declined dispute", groups = { "adminDispute" }, priority = 2)
	public void adminDeclinedDispute() throws Exception {
		buyerTest.disputeLead();
		open("https://test_staff.leadsbasket.com");
		loginPage.login("kirill3@gmx.com", "Test123456@");
		adminPage.acceptDispute("No");	
		adminPage.logOut();

	}
	@Video
	@Feature("seller LB Revenue")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(enabled = false, description = "Test  our LB Revenue from seller 462 ", groups = { "adminStatistic" }, priority =1)
	public void sellerLbRevenue()  {
		open("https://test_staff.leadsbasket.com");
		loginPage.login("kirill3@gmx.com", "Test123456@");
		adminPage.sellerLbRevenue();
		adminPage.logOut();

	}
	@Video
	@Feature("Quality")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(enabled = true, description = "Test quality part 1 ", groups = { "Quality" }, priority = 1)
	public void QualityParOne() throws Exception {
		// ctrlv , scroll , reg_duration , reg_time , ctrlv+scroll,reg_duration 5,sec,reg_time 5 sec

		open("https://test_staff.leadsbasket.com");
		loginPage.login("kirill3@gmx.com", "Test123456@");
		
		adminPage.updateQuality("95","ctrlv", "-20");	
		adminPage.checkRateScore("ctrlv","80","yes");
		
		open("https://test_staff.leadsbasket.com");		
		adminPage.updateQuality("95","scroll", "-20");	
		adminPage.checkRateScore("scroll","100","no");
		
		open("https://test_staff.leadsbasket.com");
		adminPage.updateQuality("80","ctrlv+scroll", "-20");	
		adminPage.checkRateScore("ctrlv+scroll","80","no");
		
		open("https://test_staff.leadsbasket.com");
		adminPage.updateQuality("90","ctrlv+scroll", "-20");	
		adminPage.checkRateScore("ctrlv","60","yes"); 
		Reporter.log("user didn't use Scroll!",true);
		
		open("https://test_staff.leadsbasket.com");
		adminPage.updateQuality("90","reg_duration", "-20");	
		adminPage.checkRateScore("reg_duration","100","no");
		
		open("https://test_staff.leadsbasket.com");
		adminPage.updateQuality("85","reg_time", "-20");	
		adminPage.checkRateScore("reg_duration","100","no");
		
		open("https://test_staff.leadsbasket.com");
		adminPage.updateQuality("85","reg_duration", "-20");	
		adminPage.checkRateScore("reg_duration 5 sec","80","yes");
		
		open("https://test_staff.leadsbasket.com");
		adminPage.updateQuality("85","reg_time", "-17");	
		adminPage.checkRateScore("reg_time 5 sec","83","yes");
		
		adminPage.logOut();

	}
	
	@Video
	@Feature("Coupon")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(enabled = false, description = "Just create coupon in the system", groups = { "Coupon" }, priority = 2)
	public String couponGenerator() throws Exception {
		open("https://test_staff.leadsbasket.com");
		loginPage.login("kirill3@gmx.com", "Test123456@");
		String coupon = adminPage.createCoupon();
		adminPage.logOut();
		return coupon;
		
		
	}


}
