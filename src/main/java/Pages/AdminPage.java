package Pages;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.google.inject.Inject;
import static com.codeborne.selenide.Selectors.*;

public class AdminPage {
	
	@Inject
	public AdminPage() {
//		Configuration.browser = "chrome";
		
		page(this);
	}
	private SelenideElement sellerPage = $("a[ng-class*='sellers']");
	private SelenideElement sellerPagde = $(byText("selenium Webdriver"));


}
