package Pages;
import static com.codeborne.selenide.Selenide.*;

import org.testng.Reporter;

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

	
	public void forgetPassword()
	{
		$(byText("Forgot Password?")).waitUntil((Condition.visible), 15000).click();
		wait.waitUntilAngularPageLoaded();
		$("input[type='email']").setValue("KIRL@gmx.com");
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
		$("input[name='username']").setValue("KIRL@gmx.com");
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
}
