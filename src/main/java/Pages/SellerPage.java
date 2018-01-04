package Pages;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.google.inject.Inject;

public class SellerPage {

	@Inject
	public SellerPage() {
		Configuration.browser ="chrome";
		page(this);
	}
	
	public SelenideElement  liveOffer = $("a[ng-class*='live_offers']");
	
}
