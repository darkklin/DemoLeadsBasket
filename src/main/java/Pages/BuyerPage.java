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

	private SelenideElement openLeadListPage = $("a[ui-sref='leads_list']");
	private ElementsCollection leadStatusCollection = $$(By.xpath("//tr[@class='lead-row ng-scope']/td[12]"));
	private ElementsCollection leadCollection = $$(By.xpath("//tr[@class='lead-row ng-scope']/td[5]"));
	private SelenideElement perPage200 = $("a[ng-click='perPage=200']");
	private SelenideElement likes = $("a[ng-click='likeClicked(lead)']");

	public Float[] calcTotalSpentAndTotalLead() throws Exception {
		Float leadBuyCpl;
		Float totalLeads = (float) 0;
		Float totaleadBuyCpl = (float) 0;
		String element;

		openLeadListPage.click();
		perPage200.click();
		likes.shouldBe(Condition.visible);
		for (int i = 0; i < leadCollection.size(); i++) {
			element = leadCollection.get(i).getText();
			element = element.replace("$", " ");
			leadBuyCpl= Float.parseFloat(element);
			String leadstatus = leadStatusCollection.get(i).getText();
			if (leadstatus.equalsIgnoreCase("Paid") || leadstatus.equalsIgnoreCase("Dispute")
					|| leadstatus.equalsIgnoreCase("Dispute Declined")) {
				totaleadBuyCpl = totaleadBuyCpl+leadBuyCpl;
				totalLeads++;
				System.out.println(totaleadBuyCpl);
			}

		}
		return new Float[] {totalLeads,totaleadBuyCpl};
		
	}
	public void compareStatistic (Float number,Float number2)
	{
		
	}
	
	

}
