package Pages;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.collections.Texts;
import com.google.inject.Inject;

import libry.WaitAngularPageLoaded;

public class SellerPage {
	private WaitAngularPageLoaded wait;
	private SoftAssert softAssert;

	@Inject
	public SellerPage() {
		Configuration.browser = "chrome";
		wait = new WaitAngularPageLoaded();
		this.softAssert = new SoftAssert();
		page(this);

	}

	@Inject
	BuyerPage buyerPage;

	private SelenideElement liveOfferPage = $("a[ng-class*='live_offers']");
	private SelenideElement Offer543 = $(byText("selenium Webdriver(don't use!!!)"));
	private SelenideElement dataChar = $("div[chart-data*='offerStatistic']");
	private SelenideElement perPage200 = $(byText("200"));
	private ElementsCollection saleCpl = $$(By.xpath("//tr[@class='ng-scope']//td[5]"));
	private ElementsCollection statusLead = $$(By.xpath("//tr[@class='ng-scope']//td[14]"));
	private ElementsCollection webStastic = $$("span[class*='value ng-scope']"); // idex 0 = total leads idex 1= total
																					// revenue total idex 2 =actual
																					// avgCpl

	/**
	 * Calculate seller statistic
	 * 
	 * @return buyerTotalLeads,totalRevenues,actualAvgCpl
	 * @throws Exception
	 */

	public Float[] calcTotalRevenueLeadActualAvgCpl() throws Exception {
		$(byText("Report")).click();
		perPage200.waitUntil(Condition.visible, 20000).click();
		wait.waitUntilAngularPageLoaded();
		String element;
		Float saleCpls = null;
		Float actualAvgCpl = null;

		Float totalRevenues = (float) 0;
		Float buyerTotalLeads = (float) 0;
		for (int i = 0; i < saleCpl.size(); i++) {
			element = saleCpl.get(i).getText().replace("$", " ");
			saleCpls = Float.parseFloat(element);
			String leadStatus = statusLead.get(i).getText();

			if (leadStatus.equalsIgnoreCase("Paid") || leadStatus.equalsIgnoreCase("Dispute")
					|| leadStatus.equalsIgnoreCase("Dispute Declined")) {
				System.out.println(saleCpl.size());
				totalRevenues += saleCpls;
				buyerTotalLeads++;
			}
		}
		actualAvgCpl = totalRevenues / buyerTotalLeads;
		actualAvgCpl = (float) (Math.round(actualAvgCpl * 100.0) / 100.0);
		totalRevenues = (float) (Math.round(totalRevenues * 100.0) / 100.0);
		System.out.println(totalRevenues + "  " + buyerTotalLeads + "" + actualAvgCpl);

		return new Float[] { buyerTotalLeads, totalRevenues, actualAvgCpl };

	}

	public void checkStatisticOnDashBoard(Float buyerTotalLeads, Float totalRevenues, Float actualAvgCpl) {
		$("a[ui-sref*='dashboardSeller']").click();
		wait.waitUntilAngularPageLoaded();
		buyerPage.selectDate("13/11/16");
		wait.waitUntilAngularPageLoaded();
		softAssert.assertEquals(convertWebElementToNm(webStastic.get(1)), buyerTotalLeads, "Total Leads");
		softAssert.assertEquals(convertWebElementToNm(webStastic.get(3)), totalRevenues, "total Revenues");
		softAssert.assertEquals(convertWebElementToNm(webStastic.get(5)), actualAvgCpl, "actual Avg Cpl");
		softAssert.assertAll();
		System.out.println("total Leads is correct " + buyerTotalLeads);
		System.out.println("total revenue is correct " + totalRevenues);
		System.out.println("Actual avg cpl  is correct " + actualAvgCpl);
	}

	public void checkstatParOffer() {
		String offerName;
		Float revenue, leads, clicks, avgCpl;
		$("a[ui-sref*='live_offers']").click();
		wait.waitUntilAngularPageLoaded();

		for (int i = 1; i <= $$("tr[ng-repeat*='liveOffersList']").size(); i++) {

			offerName = $("tbody.ng-scope>tr:nth-child(" + i + ")>td:nth-child(2)").getText();
			leads = convertWebElementToNm($("tbody.ng-scope>tr:nth-child(" + i + ")>td:nth-child(14)"));
			avgCpl = convertWebElementToNm($("tbody.ng-scope>tr:nth-child(" + i + ")>td:nth-child(9)"));
			System.out.println(offerName+" "+leads+" "+avgCpl);
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

	public void OpenOfferlink() throws Exception {
		String offerlink;
		liveOfferPage.click();
		Offer543.shouldBe(Condition.appear, Condition.enabled).click();
		dataChar.shouldBe(Condition.appear, Condition.enabled);
		offerlink = executeJavaScript(
				"return angular.element(document.getElementsByClassName('form-control ng-pristine ng-untouched ng-valid ng-not-empty')).scope().clipToClipboard");
		open(offerlink);

	}
}
