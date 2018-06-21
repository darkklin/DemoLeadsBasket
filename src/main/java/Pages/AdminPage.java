package Pages;

import static com.codeborne.selenide.Selenide.*;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.logging.Logs;
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
	FormLbPage formLbPage;

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
	private SelenideElement minimumQualityScore = $("input[name='min_score']");
	private SelenideElement welcomMassage = $("div[class*='clickable']");
	private SelenideElement disqualified = $("tr:nth-child(1)>td:nth-child(46)>small");
	private SelenideElement controlV = $("span[ng-repeat*='form_content_insert']");
	private SelenideElement acceptDispute = $("a[ng-click='modalAccept(lead)']");

	private ElementsCollection removeRows = $$("input[type='radio']");
	private ElementsCollection amoutOfRows = $$("tr[class='lead-row ng-scope']");
	private ElementsCollection updateBtns = $$("button[class='btn btn-warning']");
	private ElementsCollection resetBtn = $$("button[class='btn btn-info']");
	private ElementsCollection qualityTracking = $$("li[class*='list-group-item']");

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
//		$("pos-svg-icon[name='core_mail']").click();
		$(byText("E-mail")).click();
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

	public void acceptDispute(String YesOrNo,String leadEmail) {
		$("a[href*='system-management']").click();
		$("a[ui-sref='system.dispute']").click();
		wait.waitUntilAngularPageLoaded();
		$("input[placeholder*='Email']").setValue(leadEmail);
		wait.waitUntilAngularPageLoaded();
		$$("tr[ng-repeat*='displayedSellerList']").shouldHave(CollectionCondition.size(1));
		$("tr[ng-repeat*='displayedSellerList']").click();
		$("div[ng-if='lead.id == expandedLeadId']").shouldBe(Condition.visible);

		if (YesOrNo == "Yes") {
			acceptDispute.click();
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
		lbRevenue = (Math.ceil(lbRevenue * 100.0) / 100.0);
		softAssert.assertEquals(sellerLbrevenue, lbRevenue, "Seller lb revenue");
		Reporter.log("Seller lb revenue is correct " + lbRevenue, true);
		softAssert.assertAll();

	}

	public void updateQuality(String minScore, String roles, String roleScore ) {
		// ctrlv , scroll , reg_duration , reg_time
		resetRules();

		minimumQualityScore.clear();
		minimumQualityScore.sendKeys(minScore);
		updateBtns.get(0).click();
		welcomMassage.should(Condition.appear);
		if(roles=="ctrlv+scroll")
		{
			$("input[name='ctrlv']").clear();
			$("input[name='ctrlv']").sendKeys(roleScore);
			$("input[name='scroll']").clear();
			$("input[name='scroll']").sendKeys(roleScore);
		}
		else {
			
		$("input[name=" + roles + "]").clear();
		$("input[name=" + roles + "]").sendKeys(roleScore);
		}
		String whatTest = roles;

		switch (whatTest) {
		case "ctrlv":
			updateBtns.get(1).click();	
			Reporter.log("\nUpdate Minimum Quality to "+ minScore+", Role: "+roles+ " Role score: "+roleScore,true );
			break;
		case "scroll":
			updateBtns.get(2).click();
			Reporter.log("\nUpdate Minimum Quality to "+ minScore+", Role: "+roles+ " Role score: "+roleScore,true );

			break;
		case "ctrlv+scroll":
			updateBtns.get(1).click();
			updateBtns.get(2).click();
			Reporter.log("\nUpdate Minimum Quality to "+ minScore+", Role: "+roles+ " Role score: "+roleScore,true );
			break;
			
		case "reg_duration":
			updateBtns.get(3).click();
			Reporter.log("\nUpdate Minimum Quality to "+ minScore+", Role: "+roles+ " Role score: "+roleScore,true );

			break;
		case "reg_time":
			updateBtns.get(4).click();
			Reporter.log("\nUpdate Minimum Quality to "+ minScore+", Role: "+roles+ " Role score: "+roleScore,true );

			break;
		}
		welcomMassage.should(Condition.appear);
	}

	public void resetRules() {
		$("a[href*='system-management']").click();
		$("a[ui-sref='system.quality']").click();
		wait.waitUntilAngularPageLoaded();
		for (int i = 0; i < resetBtn.size(); i++) {
			resetBtn.get(i).click();

		}
		wait.waitUntilAngularPageLoaded();
	}

	public void checkRateScore(String rule, String qualityRate, String isDisqualified) throws Exception {

		open("http://52.17.171.159/EmbeddedOffer/");
		String email = formLbPage.regLead("@quality.com", rule);
		open("https://test_staff.leadsbasket.com");
		leadReport.click();
		wait.waitUntilAngularPageLoaded();
		$("input[st-search='email_hash']").setValue(email);
		softAssert.assertEquals(disqualified.getText(), isDisqualified);

		wait.waitUntilAngularPageLoaded();
		$$("tr[class='lead-row ng-scope']").shouldHave(CollectionCondition.size(1));

		$(byText("Lead Info")).click();
		$(byText("Lead Quality")).click();
		String rateScore = qualityTracking.get(18).getText();
		softAssert.assertEquals(rateScore, "Quality Rate: " + qualityRate + "");
		String whatTest = rule;

		switch (whatTest) {
		case "ctrlv":
			//did user used ctrlv 
			controlV.shouldHave(Condition.text("email"));
			Reporter.log("User used control + V on " + controlV.getText() +" ," + rateScore+ " Is Lead disqualified ? "+isDisqualified,true);

			break;
		case "scroll":
			//did user used scroll 
			$("i[ng-if*='use_scroll']").shouldBe(Condition.appear);
			Reporter.log("User used scroll"+ " ," + rateScore+ " Is Lead disqualified ? "+isDisqualified, true);

			break;
		case "ctrlv+scroll":
			controlV.shouldHave(Condition.text("email"));
			$("i[ng-if*='use_scroll']").shouldBe(Condition.appear);
			Reporter.log("User used scroll AND ctrl v "+ " ," + rateScore+ " Is Lead disqualified ? "+isDisqualified, true);

			break;
			
		case "reg_duration":
			String regDurtion= qualityTracking.get(11).getText();
			qualityTracking.get(11).shouldHave(Condition.text("Reg Duration: 00:10"));
			Reporter.log(regDurtion+ " ," + rateScore+ ", Is Lead disqualified ? "+isDisqualified, true);

			break;
		case "reg_duration 5 sec":
			regDurtion= qualityTracking.get(11).getText();
			qualityTracking.get(10).shouldHave(Condition.text("Time to Reg: 00:05"));
			Reporter.log(regDurtion+ " ,"  + rateScore+ ", Is Lead disqualified ? "+isDisqualified, true);
			break;
		case "reg_time":
			String timeToReg = qualityTracking.get(10).getText();
			qualityTracking.get(10).shouldHave(Condition.text("Time to Reg: 00:05"));
			Reporter.log(timeToReg+ " ," + rateScore+ ", Is Lead disqualified ? "+isDisqualified, true);
			break;
			
		case "reg_time 5 sec":
			timeToReg = qualityTracking.get(10).getText();
			qualityTracking.get(10).shouldHave(Condition.text("Time to Reg: 00:05"));

			Reporter.log(timeToReg+ " ," + rateScore+ ", Is Lead disqualified ? "+isDisqualified, true);
			break;
		}
		$("[ng-click='closeModal()']").click();
		softAssert.assertAll();

	}
	public String  createCoupon() throws Exception
	{
		$("a[href*='system-management']").click();
		$("a[ui-sref='system.coupon_generator']").click();
		wait.waitUntilAngularPageLoaded();
		$(byText("Generate Codes")).click();
		$("a[placeholder='Industry']").click();
		$(byText("9 - IndustryQA")).click();
		$(byText("Create")).click();
		$$("th[role='columnheader']").get(0).click();
		wait.waitUntilAngularPageLoaded();
		Thread.sleep(1000);
		System.out.println($$("td").get(1).getText());
		return $$("td").get(1).getText();
	}
}
