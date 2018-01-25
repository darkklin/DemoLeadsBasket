package Pages;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.google.inject.Inject;

public class LoginPage {
	@Inject
	public LoginPage() {
//		Configuration.browser = "chrome";
		page(this);
	}

	private SelenideElement email = $("input[name='email']");
	private SelenideElement submit = $("button[type='submit']");
	private SelenideElement password = $("#password");
	private SelenideElement welcomMassage = $("div[class*='clickable']");

	public SelenideElement getWelcomMassage() {
		return welcomMassage;
	}

	public void login(String useremail, String userpassword) {
		email.sendKeys(useremail);
		password.sendKeys(userpassword);
		submit.pressEnter();
	}

}
