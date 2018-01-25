package Pages;

import static com.codeborne.selenide.Selenide.*;

import java.util.Random;

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
	private SelenideElement email = $("input[id*='email']");
	private SelenideElement phone = $("input[type='tel']");
	private SelenideElement submitBtn = $("input[type='submit']");
	private SelenideElement errorMessage = $("p[class='error']");

	
	public void regLead()
	{
		firstName.setValue("AutomationLead");
		lastName.setValue("selenide");
		email.setValue(generateEmail("ABCDEFGHIJKLMNOPQR1321STbvbvbwUVWXYZ",10)+"@lb.com");
		phone.setValue("0528896515");
		submitBtn.click();
		confirm();
		errorMessage.shouldBe(Condition.exactText(""));
	}
	public String generateEmail(String characters, int length)
	{
		Random rng = new Random();
	    char[] randomText = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	    	randomText[i] = characters.charAt(rng.nextInt(characters.length()));
	        
	    }
	    return new String(randomText);
	}

}
