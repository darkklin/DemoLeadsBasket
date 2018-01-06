package Pages;

import static com.codeborne.selenide.Selenide.*;

import org.openqa.selenium.By;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.impl.SelenideElementListIterator;
import com.google.inject.Inject;
import static com.codeborne.selenide.Selectors.*;


public class BuyerPage {
	@Inject
	public BuyerPage() {
		Configuration.browser = "chrome";		
		page(this);
	}
	
	private SelenideElement LeadListPage = $("a[ui-sref='leads_list']");
	private ElementsCollection leadCollection = $$(By.xpath("//tr[@class='lead-row ng-scope']/td[5]"));
	private SelenideElement tableName = $(byText("Language"));
	
	public void calcTotalSpentAndTotalLead() throws Exception
	{
		LeadListPage.click();
		leadCollection.shouldHaveSize(leadCollection.size());
		$("div[class='loader']").shouldNot(Condition.visible);
		for (int i = 0; i < leadCollection.size(); i++) {
			
			leadCollection.get(i).shouldBe(Condition.visible);
			System.out.println(leadCollection.get(i).getText());
			
		}
	}
	


}
