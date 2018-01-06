package Pages;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.google.inject.Inject;


public class SellerPage {

	@Inject
	public SellerPage() {
		Configuration.browser = "chrome";
		
		
		page(this);
	}

	private SelenideElement liveOfferPage = $("a[ng-class*='live_offers']");
	private SelenideElement Offer543 = $(byText("selenium Webdriver(don't use!!!)"));
	private SelenideElement dataChar = $("div[chart-data*='offerStatistic']");

	
	public void OpenOfferlink() throws Exception {	
		String offerlink;
		liveOfferPage.click();
		Offer543.shouldBe(Condition.appear,Condition.enabled).click();
		dataChar.shouldBe(Condition.appear,Condition.enabled);
		offerlink= executeJavaScript("return angular.element(document.getElementsByClassName('form-control ng-pristine ng-untouched ng-valid ng-not-empty')).scope().clipToClipboard");
		open(offerlink);	
		
		}
}
