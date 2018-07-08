package Pages;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

import java.util.Random;

import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.google.inject.Inject;
import com.google.inject.Key;

public class FormLbPage {

	@Inject
	public FormLbPage() {
		Configuration.browser = "chrome";
		page(this);
	}

	private SelenideElement firstName = $("input[id*='first_name']");
	private SelenideElement lastName = $("input[id*='last_name']");
	private SelenideElement fieldEmail = $("input[id*='email']");
	private SelenideElement phone = $("input[type='tel']");
	private SelenideElement submitBtn = $("input[type='submit']");
	private SelenideElement errorMessage = $("p[class='error']");

	public String regLead(String emailDomain, String ruleTest) {
		String nm = generateEmail("12345554467890", 7);
		firstName.setValue("AutomationLead");
		lastName.setValue("selenide");
		String email1 = generateEmail("ABCDEFGHIJKLMNOPQR1321STbvbvbwUVWXYZ", 10) + emailDomain;
		fieldEmail.setValue(email1);

		qualityTest(ruleTest);
		
		if ($$("div[title*='Israel']").size() != 0) {
			phone.setValue("531415926");
		} else {
			phone.setValue("201" + nm);

		}
		submitBtn.click();
		sleep(2000);

		String error = $("p[class='error']").text();
		while ($("p[class='error']").text().contains("You have entered an invalid phone number")) {
			String nm1 = generateEmail("12345554467890", 7);
			phone.clear();
			phone.setValue("201" + nm1);
			submitBtn.click();
			sleep(2000);

		}
		$("H1").waitUntil(Condition.text("Registration done!"), 10000);
		return email1;
	}

	public void copyPaste() {
		fieldEmail.sendKeys(Keys.CONTROL + "a");
		fieldEmail.sendKeys(Keys.CONTROL + "c");
		fieldEmail.sendKeys(Keys.CONTROL + "v");
	}

	public void qualityTest(String ruleTest)  {
		String whatTest = ruleTest;

		switch (whatTest) {
		case "ctrlv":
			copyPaste();
			break;
		case "scroll":
			$(byText("scroll here")).scrollTo();
			firstName.scrollTo();
			break;
		case "ctrlv+scroll":
			copyPaste();

			$(byText("scroll here")).scrollTo();
			firstName.scrollTo();
			break;
		case "reg_duration":
			sleep(10000);
			break;
		case "reg_duration 5 sec":
			sleep(5000);
			break;
		case "reg_time":
			sleep(10000);
			break;
		case "reg_time 5 sec":
			sleep(5000);
			break;
		}
	}

	public String generateEmail(String characters, int length) {
		Random rng = new Random();
		char[] randomText = new char[length];
		for (int i = 0; i < length; i++) {
			randomText[i] = characters.charAt(rng.nextInt(characters.length()));

		}
		return new String(randomText);
	}

}
