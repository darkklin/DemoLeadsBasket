package Pages;

import static com.codeborne.selenide.Selenide.*;

import java.util.Random;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.google.inject.Inject;

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

	public String regLead(String emailDomain) throws Exception {
		String nm = generateEmail("12345554467890", 7);
		firstName.setValue("AutomationLead");
		lastName.setValue("selenide");
		String email1 = generateEmail("ABCDEFGHIJKLMNOPQR1321STbvbvbwUVWXYZ", 10) + emailDomain;
		fieldEmail.setValue(email1);
		// String placeholder = phone.getAttribute("placeholder");
		// placeholder = placeholder.replace("-", "").replace("(", "").replace(")",
		// "").replace(" ","");

		if ($$("div[title*='Israel']").size() != 0) {
			phone.setValue("531415926");
		} else {
			phone.setValue("201" + nm);

		}
		submitBtn.click();
		Thread.sleep(2000);

		String error = $("p[class='error']").text();
		while ($("p[class='error']").text().contains("You have entered an invalid phone number")) {
			String nm1 = generateEmail("12345554467890", 7);
			phone.clear();
			phone.setValue("2011" + nm1);
			submitBtn.click();
			Thread.sleep(2000);

		}
		$("H1").waitUntil(Condition.text("Registration done!"), 10000);
		return email1;
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
