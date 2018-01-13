package Pages;

import static com.codeborne.selenide.Selenide.*;

import java.awt.Scrollbar;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.google.inject.Inject;

import libry.WaitAngularPageLoaded;

import static com.codeborne.selenide.Selectors.*;

public class BuyerPage {
	private SoftAssert softAssert;
	private WaitAngularPageLoaded wait;

	@Inject
	public BuyerPage() {
		Configuration.browser = "chrome";
		this.softAssert = new SoftAssert();
		wait = new WaitAngularPageLoaded();
		page(this);
	}

	private SelenideElement openLeadListPage = $("a[ui-sref='leads_list']");
	private ElementsCollection leadStatusCollection = $$(By.xpath("//tr[@class='lead-row ng-scope']/td[12]"));
	private ElementsCollection buyCplParLead = $$(By.xpath("//tr[@class='lead-row ng-scope']/td[5]"));
	private ElementsCollection leadCollection = $$("tr[ng-repeat='lead in leadsCollection']");
	private ElementsCollection campLeadBuyCpl = $$(By.xpath("//tr[@class='lead-row ng-scope']/td[3]"));
	private SelenideElement perPage200 = $("a[ng-class='{ active: perPage == 200 }']");
	private SelenideElement openDashBoardPage = $(byText("Dashboard"));
	private SelenideElement loader = $("div[class='loader']");
	private SelenideElement datePicker = $("#date_range");
	private SelenideElement startDate = $("input[name='daterangepicker_start']");
	private SelenideElement applyDate = $(byText("Apply"));
	private SelenideElement webTotalSpend = $("div.tl_spend_ico>div>span");
	private SelenideElement webTotalLeads = $("div.tl_leads_ico>div>span");
	private SelenideElement profile = $("a.profile");
	private SelenideElement logOut = $("div.drop_userInfo>ul>li:nth-child(6)>a");

	private ElementsCollection campaignsList = $$("tr[ng-repeat='itm in campaignsList']");
	private ElementsCollection leadPerCampg = $$("tr[ng-repeat*='leadsCollection']");
	private ElementsCollection campnTotalSpend = $$(By.xpath("//tr[@class='ng-scope']//td[10]"));
	private ElementsCollection campnTotalLeads = $$(By.xpath("//tr[@class='ng-scope']//td[6]"));
	private ElementsCollection campaignsName = $$("a[ui-sref='campaign_item.month({ idCampaign: itm.id})']");
	private ElementsCollection campaignLeadStatus = $$(By.xpath("//tr[@class='lead-row ng-scope']//td[10]"));

	/**
	 * Return buyer statistic parameters
	 * 
	 * @return totalSpent,totalLead
	 * @throws Exception 
	 */
	public void calcTotalSpentAndTotalLead() throws Exception {
		Float leadBuyCpl;
		Float totalLeads = (float) 0;
		Float totaleadBuyCpl = (float) 0;
		String element;
		openLeadListPage.click();
		wait.waitUntilAngularPageLoaded();
		perPage200.click();
		loader.shouldBe(Condition.visible);
		wait.waitUntilAngularPageLoaded();
		System.out.println(leadCollection.size());
		for (int i = 0; i < leadCollection.size(); i++) {
			leadBuyCpl = convertWebElementToNm(buyCplParLead.get(i));
			String leadstatus = leadStatusCollection.get(i).getText();
			if (leadstatus.equalsIgnoreCase("Paid") || leadstatus.equalsIgnoreCase("Dispute")
					|| leadstatus.equalsIgnoreCase("Dispute Declined")) {
				totaleadBuyCpl = totaleadBuyCpl + leadBuyCpl;
				totalLeads++;
			}

		}
		checkTotalSpentAndTotalLeads(totaleadBuyCpl,totalLeads);
//		return new Float[] { totalLeads, totaleadBuyCpl };

	}

	/**
	 * Buyer > DashBoard CHECK UI Total Spent and Total Leads is right?
	 */
	public void checkTotalSpentAndTotalLeads(Float totalspent, Float totalLeads) throws Exception {
		openDashBoardPage.click();
		selectDate("13/11/16");
		Thread.sleep(200);
		loader.shouldBe(Condition.disappear);
		softAssert.assertEquals(convertWebElementToNm(webTotalSpend), totalspent, "Totalspend");
		softAssert.assertEquals(convertWebElementToNm(webTotalLeads), totalLeads, "TotalLeads");
		softAssert.assertAll();
		System.out.println("total spent is correct " + totalspent);
		System.out.println("total Leads is correct " + totalLeads);
	}

	/**
	 * Check statistic par offer in buyer > dashboard > Recently Updated Campaigns
	 * 
	 * @throws Exception
	 * @throws Exception
	 */
	public void checkStatisticPerCamp() throws Exception {
		String campName, campLeadStatus;
		Float webCampSpend, webCampLeads, totalLeads = (float) 0, totaleadBuyCpl = (float) 0, leadBuyCpl = (float) 0,
				avgCPL = (float) 0;
		wait.waitUntilAngularPageLoaded();

		for (int i = 0; i < campaignsList.size();i++) {
			campName = campaignsName.get(i).getText();
			webCampSpend = convertWebElementToNm(campnTotalSpend.get(i));
			webCampLeads = convertWebElementToNm(campnTotalLeads.get(i));
			avgCPL = webCampSpend / webCampLeads;
			wait.waitUntilAngularPageLoaded();
			campaignsName.get(i).click();
			perPage200.click();
			loader.shouldBe(Condition.visible);
			loader.shouldNot(Condition.visible);
			wait.waitUntilAngularPageLoaded();
			for (int j = 0; j < $$("tr[ng-repeat*='leadsCollection']").size(); j++) {
				leadBuyCpl = convertWebElementToNm(campLeadBuyCpl.get(j));
				campLeadStatus = campaignLeadStatus.get(j).getText();

				if (campLeadStatus.equalsIgnoreCase("Paid") || campLeadStatus.equalsIgnoreCase("Dispute")
						|| campLeadStatus.equalsIgnoreCase("Dispute Declined")) {
					totaleadBuyCpl = totaleadBuyCpl + leadBuyCpl;
					totalLeads++;
				}

			}
			softAssert.assertEquals(webCampSpend, totaleadBuyCpl, campName);
			softAssert.assertEquals(webCampLeads, totalLeads, campName);
			System.out.println("total spend : " + totaleadBuyCpl + "total leads: " + totalLeads);
			openDashBoardPage.click();

		}
		softAssert.assertAll();

	}

	public Float convertWebElementToNm(SelenideElement nm) {
		String element;
		Float result;
		System.out.println(nm.text());
		element = nm.getText();
		element = element.replace("$", "").replace(",", "");
		result = Float.parseFloat(element);
		return result;
	}

	public void selectDate(String Date) {
		datePicker.scrollTo().click();
		startDate.clear();
		startDate.setValue(Date);
		applyDate.click();
	}

	public void logOut() {
		profile.click();
		logOut.shouldBe(Condition.visible).click();
	}

}
