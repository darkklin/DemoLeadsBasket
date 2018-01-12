package Pages;

import static com.codeborne.selenide.Selenide.*;

import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.google.inject.Inject;
import static com.codeborne.selenide.Selectors.*;

public class BuyerPage {
	private SoftAssert softAssert;

	@Inject
	public BuyerPage() {
		Configuration.browser = "chrome";
		this.softAssert = new SoftAssert();
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
	private SelenideElement webTotalSpend = $("div.tl_spend_ico>div>span");
	private SelenideElement webTotalLeads = $("div.tl_leads_ico>div>span");
	private ElementsCollection campaignsList = $$("tr[ng-repeat='itm in campaignsList']");
	private ElementsCollection campnTotalSpend = $$(By.xpath("//tr[@class='ng-scope'][1]//td[10]"));
	private ElementsCollection campnTotalLeads = $$(By.xpath("//tr[@class='ng-scope'][1]//td[6]"));
	private ElementsCollection campaignsName = $$(By.xpath("//tr[@class='ng-scope'][1]//td[3]"));
	private ElementsCollection campaignLeadStatus = $$(By.xpath("//tr[@class='ng-scope'][1]//td[3]"));


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
			leadBuyCpl = convertWebElementToNm(leadCollection.get(i));
			String leadstatus = leadStatusCollection.get(i).getText();
			if (leadstatus.equalsIgnoreCase("Paid") || leadstatus.equalsIgnoreCase("Dispute")
					|| leadstatus.equalsIgnoreCase("Dispute Declined")) {
				totaleadBuyCpl = totaleadBuyCpl + leadBuyCpl;
				totalLeads++;
			}

		}
		return new Float[] { totalLeads, totaleadBuyCpl };

	}

	/**
	 * Buyer > DashBoard CHECK UI  Total Spent and Total Leads is right?
	 */
	public void checkTotalSpentAndTotalLeads(Float totalspent, Float totalLeads) throws Exception {
		openDashBoardPage.click();
		selectDate("13/11/16");
		loader.shouldBe(Condition.disappear);
		softAssert.assertEquals(totalspent, convertWebElementToNm(webTotalSpend), "Totalspend");
		softAssert.assertEquals(totalLeads, convertWebElementToNm(webTotalLeads), "TotalLeads");
		softAssert.assertAll();
		System.out.println("total spent is correct " + totalspent);
		System.out.println("total Leads is correct " + totalLeads);
	}

	/**
	 * Check statistic par offer in buyer > dashboard > Recently Updated Campaigns
	 */
	public void checkStatisticPerCamp() {
		String campName,campLeadStatus;
		Float webCampSpend, webCampLeads,totalLeads = (float) 0,totaleadBuyCpl = (float) 0,leadBuyCpl = (float) 0;
		for (int i = 1; i < campaignsList.size(); i++) {
			campName = campaignsName.get(i).getText();
			webCampSpend = convertWebElementToNm(campnTotalSpend.get(i));
			webCampLeads = convertWebElementToNm(campnTotalLeads.get(i));
			campaignsName.get(i).click();
			likes.shouldBe(Condition.visible);
			campLeadStatus = campaignLeadStatus.get(i).getText();
			if (campLeadStatus.equalsIgnoreCase("Paid") || campLeadStatus.equalsIgnoreCase("Dispute")
					|| campLeadStatus.equalsIgnoreCase("Dispute Declined")) {			
			}
				totaleadBuyCpl = totaleadBuyCpl + leadBuyCpl;
				totalLeads++;
		}

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
