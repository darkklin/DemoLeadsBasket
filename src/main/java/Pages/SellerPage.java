package Pages;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.collections.Texts;
import com.google.inject.Inject;

import libry.WaitAngularPageLoaded;


public class SellerPage {
	private WaitAngularPageLoaded wait;

	@Inject
	public SellerPage() {
		Configuration.browser = "chrome";
		wait = new WaitAngularPageLoaded();
		page(this);

	}
	@Inject
	BuyerPage buyerPage;

	private SelenideElement liveOfferPage = $("a[ng-class*='live_offers']");
	private SelenideElement Offer543 = $(byText("selenium Webdriver(don't use!!!)"));
	private SelenideElement dataChar = $("div[chart-data*='offerStatistic']");
	private SelenideElement perPage200 = $(byText("200"));
			ElementsCollection saleCpl = $$(By.xpath("//tr[@class='ng-scope']//td[5]"));
	 List<String> element1;

	public void calculateCplAndLeads() 
	{
		$(byText("Reports")).click();
		wait.waitUntilAngularPageLoaded();
		buyerPage.selectDate("13/11/16");
		perPage200.click();
		wait.waitUntilAngularPageLoaded();				
		convertWebElementToNm(element1);
	}
	
	public void OpenOfferlink() throws Exception {	
		String offerlink;
		liveOfferPage.click();
		Offer543.shouldBe(Condition.appear,Condition.enabled).click();
		dataChar.shouldBe(Condition.appear,Condition.enabled);
		offerlink= executeJavaScript("return angular.element(document.getElementsByClassName('form-control ng-pristine ng-untouched ng-valid ng-not-empty')).scope().clipToClipboard");
		open(offerlink);	
		
		}
	public Float convertWebElementToNm(List<String> text) {
		List<String> element = new ArrayList<String>();
		float result = 0;
		for (int i = 0; i < text.size(); i++) {
			
			element.add(text.get(i).replace("$", ""));
			result = Float.parseFloat(element.get(i));
			System.out.println(result);
		}


//		element = element.replace("$", "").replace(",", "");
//		result = Float.parseFloat(element);
		return result;
	}
}
