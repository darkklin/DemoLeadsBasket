package Pages;
import static com.codeborne.selenide.Selenide.*;

import java.util.Random;

import org.testng.Reporter;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.google.inject.Inject;

import libry.WaitAngularPageLoaded;

import static com.codeborne.selenide.Selectors.*;

public class AdminPage {
	private WaitAngularPageLoaded wait;

	@Inject
	public AdminPage() {
		
		Configuration.browser = "chrome";
		wait = new WaitAngularPageLoaded();
		page(this);
	}
	private SelenideElement sellerPage = $("a[ng-class*='sellers']");
	private SelenideElement sellerPagde = $(byText("selenium Webdriver"));
	private SelenideElement profile = $("a.profile");
	private SelenideElement logOut = $("a[class='logout_ico']");
	
	public void forgetPassword()
	{
		$(byText("Forgot Password?")).waitUntil((Condition.visible), 15000).click();
		wait.waitUntilAngularPageLoaded();
		$("input[type='email']").setValue("");
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
		$("input[name='username']").setValue("");
		$("input[name='password']").setValue("0546474985");
		$("button[type='submit']").click();
		$("pos-svg-icon[name='core_mail']").click();
		switchTo().frame("thirdPartyFrame_mail");

		$("tr[class='new']").waitUntil((Condition.visible), 20000).click();
		Reporter.log("Email reset password sent",true);
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
}
