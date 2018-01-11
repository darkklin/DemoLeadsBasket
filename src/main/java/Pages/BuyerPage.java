package Pages;

import static com.codeborne.selenide.Selenide.*;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

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
	private SelenideElement openDashBoardPage = $(byText("Dashboard"));
	private SelenideElement loader = $("div[class='loader']");
	private SelenideElement datePicker = $("#date_range");
	private SelenideElement startDate = $("input[name='daterangepicker_start']");
	private SelenideElement applyDate = $(byText("Apply"));
	private SelenideElement webTotalSpent = $("div.tl_spend_ico>div>span");
	private SelenideElement webTotalLeads = $("div.tl_leads_ico>div>span");

	/**
	 * Return buyer statistic parameters
	 * 
	 * @return totalSpent,totalLead
	 */
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
			leadBuyCpl = Float.parseFloat(element);
			String leadstatus = leadStatusCollection.get(i).getText();
			if (leadstatus.equalsIgnoreCase("Paid") || leadstatus.equalsIgnoreCase("Dispute")
					|| leadstatus.equalsIgnoreCase("Dispute Declined")) {
				totaleadBuyCpl = totaleadBuyCpl + leadBuyCpl;
				totalLeads++;
				System.out.println(totaleadBuyCpl);
			}

		}
		return new Float[] { totalLeads, totaleadBuyCpl };

	}

	/**
	 * Check in buyer > DashBoard UI, if Total Spent and Total Leads is corrects
	 */
	public void checkStatisticOnDashboard(Float totalspent, Float totalLeads) throws Exception {
		openDashBoardPage.click();
		selectDate("13/11/16");
		loader.shouldBe(Condition.disappear);
		if (totalspent.compareTo(convertWebElementToNm(webTotalSpent)) == 0
				&& (totalLeads.compareTo(convertWebElementToNm(webTotalLeads)) == 0)) {
			System.out.println("total spent is correct " + totalspent);
			System.out.println("total Leads is correct " + totalLeads);

		} else {
			System.out.println("UI total spent is  " + totalspent.compareTo(convertWebElementToNm(webTotalSpent)));
			System.out.println("total spent should be " + totalspent);
			System.out.println("--------------------------------------");
			System.out.println("UI total lead is  " + totalspent.compareTo(convertWebElementToNm(webTotalLeads)));
			System.out.println("total lead should be " + totalspent);

		}

	}

	/**
	 * Check statistic par offer in buyer > dashboard > Recently Updated Campaigns
	 */
	public void checkStatisticPerCamp() {

	}

	public Float convertWebElementToNm(SelenideElement nm) {
		String element;
		Float result;
		element = nm.getText();
		element = element.replace("$", "").replace(",", "");
		result = Float.parseFloat(element);
		return result;
	}

	public void selectDate(String Date) {
		datePicker.click();
		startDate.clear();
		startDate.setValue(Date);
		applyDate.click();
	}

}
