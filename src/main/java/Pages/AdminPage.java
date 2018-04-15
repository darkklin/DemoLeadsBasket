package Pages;

import static com.codeborne.selenide.Selenide.*;

import java.util.Random;

import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.google.inject.Inject;

import libry.WaitAngularPageLoaded;

import static com.codeborne.selenide.Selectors.*;

public class AdminPage {
	private WaitAngularPageLoaded wait;
	private SoftAssert softAssert;

	@Inject
	public AdminPage() {
		Configuration.browser = "chrome";
		wait = new WaitAngularPageLoaded();
		this.softAssert = new SoftAssert();
		page(this);
	}

	@Inject
	SellerPage sellerPage;
	private SelenideElement sPage = $("a[ng-class*='sellers']");
	private SelenideElement leadReport = $("a[ng-class*='admin_leads']");
	private SelenideElement perPage200 = $(byText("200"));

	private SelenideElement searchField = $("input[placeholder='Search']");
	private SelenideElement profile = $("a.profile");
	private SelenideElement logOut = $("a[class='logout_ico']");
	private SelenideElement webSellerRevenue = $("[ng-if='Sellers.revenue']");
	private SelenideElement searchSellerID = $("input[placeholder='Seller ID']");
	private ElementsCollection removeRows = $$("input[type='radio']");
	private ElementsCollection amoutOfRows = $$("tr[class='lead-row ng-scope']");

	public void forgetPassword() {
		$(byText("Forgot Password?")).waitUntil((Condition.visible), 15000).click();
		wait.waitUntilAngularPageLoaded();
		$("input[type='email']").setValue("kirill3@gmx.com");
		$("button[type='submit']").click();
		$("h2").shouldHave(Condition.text("Reset Your Password"));
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		open("https://www.gmx.com");
		$("a[id='login-button']").click();
		$("input[name='username']").setValue("kirill3@gmx.com");
		$("input[name='password']").setValue("0546474985");
		$("button[type='submit']").click();
		$("pos-svg-icon[name='core_mail']").click();
		switchTo().frame("thirdPartyFrame_mail");

		$("tr[class='new']").waitUntil((Condition.visible), 20000).click();
		Reporter.log("Email reset password sent", true);
		switchTo().frame("mail-detail");
		$("a[href]").waitUntil(Condition.appear, 30000).click();
		switchTo().window(1);
		$("h2").waitUntil(Condition.text("Reset Your Password"), 20000);
		$$("input[type='password']").get(0).setValue("Test123456@");
		$$("input[type='password']").get(1).setValue("Test123456@");
		$("button[type='submit']").click();
		$(byText("Password was reset successfully!"));

	}

	public void acceptDispute(String YesOrNo) {
		$("a[href*='system-management']").click();
		$("a[ui-sref='system.dispute']").click();
		wait.waitUntilAngularPageLoaded();
		$("input[placeholder*='Email']").setValue("dispute.com");
		wait.waitUntilAngularPageLoaded();
		$$("tr[ng-repeat*='displayedSellerList']").shouldHave(CollectionCondition.size(1));
		$("tr[ng-repeat*='displayedSellerList']").click();
		if (YesOrNo == "Yes") {
			$(byText("Accept")).click();
			$(byText("Yes")).click();
			Reporter.log("Admin accepted dispute", true);
			$$("tr[ng-repeat*='displayedSellerList']").shouldHave(CollectionCondition.size(0));

		} else {
			Random rd = new Random();
			int reson = rd.nextInt(5);
			$(byText("Decline")).click();
			$("div[name='status']").click();
			$$("li[role='option']").get(reson).click();
			$(byText("Decline and send message")).click();
			$$("tr[ng-repeat*='displayedSellerList']").shouldHave(CollectionCondition.size(0));

		}

	}

	public void logOut() {
		profile.click();
		logOut.shouldBe(Condition.visible).click();
	}

	public void sellerLbRevenue() {
		Double totatSellerrCpl = (double) 0;
		Double totalBuyerCpl = (double) 0;
		sPage.click();
		searchField.sendKeys("462");
		$$("button[type='submit']").get(0).click();
		wait.waitUntilAngularPageLoaded();
		Double sellerLbrevenue = sellerPage.convertWebElementToNm(webSellerRevenue);
		leadReport.click();
		$(byText("Columns")).click();
		removeRows.get(1).click();
		removeRows.get(3).click();
		$(byText("Close")).click();
		searchSellerID.setValue("462");
		wait.waitUntilAngularPageLoaded();
		perPage200.waitUntil(Condition.visible, 20000).click();
		wait.waitUntilAngularPageLoaded();
		for (int i = 1; i <= $$("tr[class='lead-row ng-scope']").size(); i++) {

			Double buyerScpl = sellerPage.convertWebElementToNm($("tr:nth-child(" + i + ")>td:nth-child(12)>small"));
			totalBuyerCpl += buyerScpl;
			Double sellerScpl = sellerPage.convertWebElementToNm($("tr:nth-child(" + i + ")>td:nth-child(10)>small"));
			totatSellerrCpl += sellerScpl;
		}
		Double lbRevenue = totalBuyerCpl - totatSellerrCpl;
		lbRevenue =  (Math.ceil(lbRevenue * 100.0) / 100.0);
		softAssert.assertEquals(sellerLbrevenue, lbRevenue,"Seller lb revenue");
		Reporter.log("Seller lb revenue is correct "+ lbRevenue ,true);
		softAssert.assertAll();

	}
}
