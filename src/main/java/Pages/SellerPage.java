package Pages;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
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
	private SelenideElement profile = $("a.profile");
	private SelenideElement logOut = $("div.drop_userInfo>ul>li:nth-child(5)>a");
	private SelenideElement liveOfferPage = $("a[ng-class*='live_offers']");
	private SelenideElement Offer543 = $(byText("selenium Webdriver(don't use!!!)"));
	private SelenideElement dataChar = $("div[chart-data*='offerStatistic']");
	private SelenideElement perPage200 = $(byText("200"));
	private ElementsCollection saleCpl = $$(By.xpath("//tr[@class='ng-scope']//td[5]"));
	private ElementsCollection webMinSaleCpl = $$(By.xpath("//tr[@class='ng-scope']//td[6]"));
	private ElementsCollection statusLead = $$(By.xpath("//tr[@class='ng-scope']//td[14]"));
	private ElementsCollection webStastic = $$("span[class*='value ng-scope']"); // idex 0 = total leads idex 1= total//
	private SelenideElement btnSubmit = $("button.btn");
	private SelenideElement firstName = $("input[name='firstName']");
	private SelenideElement lastName = $("input[name='lastName']");
	private SelenideElement phoneNumber = $("input[id='tel-dc']");
	private SelenideElement companyName = $("input[name='company']");
	private SelenideElement email = $("input[placeholder*='Email']");
	private SelenideElement password = $("input[name='pass']");
	private SelenideElement confirmPassword = $("input[name='rePass']");
	private SelenideElement bankNamee = $("input[name='bank_name']");
	private SelenideElement bankCountryField = $("select[name='bank_country']");
	private ElementsCollection chooseBankCountry = $$("select[name='bank_country']>option");
	private SelenideElement bankDetailNote = $("textarea[name='bank_note']");
	private SelenideElement swiftt = $("input[name='bank_swift']");
	private SelenideElement bankAccNumber = $("input[name='bank_account_number']");

	/**
	 * Calculate seller statistic
	 * 
	 * @return buyerTotalLeads,totalRevenues,actualAvgCpl
	 * @throws Exception
	 * @throws Exception
	 */

	public Float[] calcTotalRevenueLeadActualAvgCpl() throws Exception {
		perPage200.waitUntil(Condition.visible, 20000).click();
		wait.waitUntilAngularPageLoaded();
		Thread.sleep(3000);
		String element, element2;
		Float saleCpls, minSaleCpl = null, actualAvgCpl = null;
		Float totalminSaleCpl = (float) 0;
		Float totalRevenues = (float) 0;
		Float buyerTotalLeads = (float) 0;
		for (int i = 0; i < $$(By.xpath("//tr[@class='ng-scope']//td[5]")).size(); i++) {
			element = saleCpl.get(i).getText().replace("$", " ");
			element2 = webMinSaleCpl.get(i).getText().replace("$", " ");
			saleCpls = Float.parseFloat(element);
			minSaleCpl = Float.parseFloat(element2);
			String leadStatus = statusLead.get(i).getText();
			if (leadStatus.equalsIgnoreCase("Paid") || leadStatus.equalsIgnoreCase("Dispute")
					|| leadStatus.equalsIgnoreCase("Dispute Declined")) {
				totalRevenues += saleCpls;
				totalminSaleCpl += minSaleCpl;
				buyerTotalLeads++;
			}
		}
		minSaleCpl = totalminSaleCpl / buyerTotalLeads;
		actualAvgCpl = totalRevenues / buyerTotalLeads;
		minSaleCpl = (float) (Math.round(minSaleCpl * 100.0) / 100.0);
		actualAvgCpl = (float) (Math.round(actualAvgCpl * 100.0) / 100.0);
		totalRevenues = (float) (Math.round(totalRevenues * 100.0) / 100.0);
		return new Float[] { buyerTotalLeads, totalRevenues, actualAvgCpl, minSaleCpl };
	}

	public void checkStatisticOnDashBoard(Float buyerTotalLeads, Float totalRevenues, Float actualAvgCpl,
			Float minSaleCpl) {
		$("a[ui-sref*='dashboardSeller']").click();
		buyerPage.selectDate("13/11/16", "Dashboard");
		wait.waitUntilAngularPageLoaded();
		softAssert.assertEquals(convertWebElementToNm(webStastic.get(1)), buyerTotalLeads, "Total Leads");
		softAssert.assertEquals(convertWebElementToNm(webStastic.get(3)), totalRevenues, "total Revenues");
		softAssert.assertEquals(convertWebElementToNm(webStastic.get(5)), actualAvgCpl, "actual Avg Cpl");
		softAssert.assertEquals(convertWebElementToNm(webStastic.get(4)), minSaleCpl, "Avg. Min CPL");
		softAssert.assertAll();
		Reporter.log("\n" + "total Leads is  " + buyerTotalLeads, true);
		Reporter.log("\n" + "total revenue is  " + totalRevenues, true);
		Reporter.log("\n" + "Actual avg cpl  is  " + actualAvgCpl, true);
		Reporter.log("\n" + "Avg. Min CPL  is  " + minSaleCpl, true);

	}

	/**
	 * checking amount of leads,avgcpl,revenue,clicks,EPC par offer. and check total
	 * clicks and Avg. EPC par on dashboard
	 * 
	 * @throws Exception
	 */
	public void checkstatParOffer() throws Exception {
		String offerName;
		Float totalClicks = (float) 0;
		Float revenue = null, leads, clicks, avgCpl, webEpc, rEpc;
		$("a[ui-sref*='live_offers']").click();
		wait.waitUntilAngularPageLoaded();
		int liveOfferSIze = $$("tr[ng-repeat*='liveOffersList']").size();
		for (int i = 1; i <= liveOfferSIze; i++) {
			offerName = $("tbody.ng-scope>tr:nth-child(" + i + ")>td:nth-child(2)").getText();
			leads = convertWebElementToNm($("tbody.ng-scope>tr:nth-child(" + i + ")>td:nth-child(7)"));
			avgCpl = convertWebElementToNm(
					$("tbody.ng-scope>tr:nth-child(" + i + ")>td:nth-child(9)").text().replace("$", ""));
			revenue = convertWebElementToNm($("tbody.ng-scope>tr:nth-child(" + i + ")>td:nth-child(14)"));
			clicks = convertWebElementToNm($("tbody.ng-scope>tr:nth-child(" + i + ")>td:nth-child(6)"));
			webEpc = convertWebElementToNm($("tbody.ng-scope>tr:nth-child(" + i + ")>td:nth-child(15)"));
			$("tbody.ng-scope>tr:nth-child(" + i + ")>td:nth-child(2)").click();
			wait.waitUntilAngularPageLoaded();
			Float statResult[] = calcTotalRevenueLeadActualAvgCpl();
			rEpc = statResult[1] / clicks;
			totalClicks += clicks;
			rEpc = (float) (Math.round(rEpc * 100.0) / 100.0);
			$$("a[ui-sref*='live_offers']").get(0).click();
			softAssert.assertEquals(leads, statResult[0], "Leads");
			softAssert.assertEquals(revenue, statResult[1], "Revenue");
			softAssert.assertEquals(avgCpl, statResult[2], "avgCpl");
			softAssert.assertEquals(webEpc, rEpc, "Epc per offer");
			Reporter.log("\n" + "OfferName: " + offerName + ">Number leads:" + statResult[0] + ">Revenues: "
					+ statResult[1] + ">Avg. CPL: " + statResult[2] + "EPC: " + rEpc, true);

		}
		$$("a[ui-sref*='dashboardSeller']").get(0).click();
		buyerPage.selectDate("13/11/16", "Dashboard");
		wait.waitUntilAngularPageLoaded();
		Float avgEpc = convertWebElementToNm(webStastic.get(3)) / totalClicks;
		avgEpc = (float) (Math.round(avgEpc * 100.0) / 100.0);
		softAssert.assertEquals(convertWebElementToNm(webStastic.get(0)), totalClicks, "DashBoard Total clicks");
		softAssert.assertEquals(convertWebElementToNm(webStastic.get(6)), avgEpc, "DashBoard Avg. EPC");
		softAssert.assertAll();
		Reporter.log("\n" + "Total clicks: " + totalClicks + "Avg. EPC " + avgEpc, true);

	}

	public void accountingStatistic() {
		Float saleCpl, totalInvoice = (float) 0, nmLeads = (float) 0;
		$("a[ui-sref*='accounting']").click();
		wait.waitUntilAngularPageLoaded();
		$(byText("Current invoice breakdown")).click();
		wait.waitUntilAngularPageLoaded();
		for (int i = 1; i <= $$("tr[ng-repeat='itm in breakdown']").size(); i++) {
			saleCpl = convertWebElementToNm(
					$("table.scrolling-table>tbody>tr:nth-child(" + i + ")>td:nth-child(4)").text().replace("$", ""));
			totalInvoice += saleCpl;
			nmLeads++;
		}
		totalInvoice = (float) (Math.round(totalInvoice * 100.0) / 100.0);
		Float popUpwebTotalSaleCpl = convertWebElementToNm($$("p[class='ng-binding']").get(0).getText());
		Float popUpwebTotalLeads = convertWebElementToNm($$("p[class='ng-binding']").get(1).getText());
		softAssert.assertEquals(totalInvoice, popUpwebTotalSaleCpl, "popUp total invoice");
		softAssert.assertEquals(nmLeads, popUpwebTotalLeads, "popUP total leads");
		softAssert.assertAll();
		$(byText("Close")).click();

		for (int i = 1; i <= $$("[ng-repeat*='invoiceCollection']").size(); i++) {
			String statusInvoice = $("table.detailTable>tbody>tr:nth-child(" + i + ")>td:nth-child(6)").getText();
			if (statusInvoice.equalsIgnoreCase("open")) {
				Float InvoiceAmount = convertWebElementToNm(
						$(By.xpath("//tr[@ng-repeat='item in invoiceCollection'][" + i + "]//td[4]")).getText());
				Float invoiceLeads = convertWebElementToNm(
						$(By.xpath("//tr[@ng-repeat='item in invoiceCollection'][" + i + "]//td[2]")).getText());

				softAssert.assertEquals(totalInvoice, InvoiceAmount, " total Amount invoice leads accounting page ");
				softAssert.assertEquals(nmLeads, invoiceLeads, " total Amount  leads accounting page");
				softAssert.assertAll();
				Reporter.log("total Amount invoice leads accounting page" + totalInvoice, true);
				Reporter.log("total Amount  leads accounting page" + invoiceLeads, true);

			}
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

	public Float convertWebElementToNm(String nm) {
		String element;
		Float result;
		if (nm.contains(":")) {
			element = nm.split(":")[1];

		} else {
			element = nm.split(" ")[0];

		}
		element = element.replace("$", "").replace(" ", "").replace(",", "");

		result = Float.parseFloat(element);

		return result;
	}

	public String Offerlink() throws Exception {
		String offerlink;
		liveOfferPage.click();
		wait.waitUntilAngularPageLoaded();
		Offer543.waitUntil(Condition.visible, 10000).click();
		;
		wait.waitUntilAngularPageLoaded();
		dataChar.shouldBe(Condition.appear, Condition.enabled);
		offerlink = executeJavaScript(
				"return angular.element(document.getElementsByClassName('form-control ng-pristine ng-untouched ng-valid ng-not-empty')).scope().clipToClipboard");
		return offerlink;

	}

	public void logOut() {
		profile.click();
		logOut.shouldBe(Condition.visible).click();
	}

	public void startRegister() {
		FormLbPage rendom = new FormLbPage();
		String text = rendom.generateEmail("abcdfddDd23%2", 8);
		String rdBname = rendom.generateEmail("abcdfg", 4);

		$("h2").shouldHave(Condition.text("Create a Free Account on LEADSBASKET"));
		$(byText("Login")).click();
		$("h2").shouldHave(Condition.text("Login to LeadsBasket"));
		back();
		$(byText("Get Started")).click();

		$(byText("Terms of Services")).click();
		switchTo().window(1);
		$("div.terms>h1").shouldHave(Condition.text("TERMS OF USE"));
		switchTo().window(1).close();
		switchTo().window(0);
		$(byText("Login")).shouldBe(Condition.visible).click();
		$("div.auth>h2").shouldHave(Condition.text("Login to LeadsBasket"));
		back();
		wait.waitUntilAngularPageLoaded();
		btnSubmit.shouldBe(Condition.disabled);
		firstName.setValue("selenide" + rdBname);
		lastName.setValue("automtic" + rdBname);
		phoneNumber.setValue(phoneNumber.getAttribute("placeholder"));
		companyName.setValue("selenide");
		email.setValue("lbdemo234+" + text + "@gmail.com");
		password.setValue("D%1" + text);
		confirmPassword.setValue("D%1" + text);
		btnSubmit.shouldBe(Condition.visible).click();

		$("h4").shouldHave(Condition.text("YOU'RE ALMOST THERE!"));
		$(byText("Done")).shouldBe(Condition.visible).click();
		String url = WebDriverRunner.url();
		if (WebDriverRunner.url().contains("https://leadsbasket.com/")) {
			Reporter.log("New seller Create", true);
		}

	}

	public void verifyEmail() {
		open("https://mail.google.com/mail/u/0/h/1qjzsgv9p5fzq/?f=1");
		$("input[type='email']").setValue("lbdemo234@gmail.com");
		$("span[class='RveJvd snByac']").click();
		$("input[type='password']").setValue("A#aaaaaa");
		$("span[class='RveJvd snByac']").click();
		$("div[class='qclxzb']").click();
		$$("[role='option']").get(101).scrollIntoView(true).click();
		$("input[type='tel']").setValue("0528895514");
		$("span[class='RveJvd snByac']").click();
		$("input[title='Search']").waitUntil(Condition.visible, 20000).setValue("verification required");
		$("input[type='submit']").click();
		$$("table[class='th']>tbody>tr[bgcolor='#ffffff']").get(0).click();
		$("img[src*='New-Email-Template_approved.jpg']").shouldBe(Condition.visible);
		String verifyLink = $(byText("Click Here")).getAttribute("href");
		$(byText("Delete")).click();
		open(verifyLink);
		$("h4").shouldHave(Condition.text("Email Verified Successfully!"));

	}

	public void billingInformation() {
		Random rand = new Random();
		int bankNm = rand.nextInt(239);
		String bankName = "bankSelenide", bankCountry, bankDetailsNote = "Test this Shit 123", swift = "BOtfIIE2DXXX",
				accountNm = "ABCD2343512", iban = "IE89BOFI43201724454323", companyName = "selenide",
				stretAdress1 = "test 1243 Bin", state = "state hahaha", zipCode = "432dsfsd", city = "tel aviv";

		bankNamee.setValue(bankName);
		bankCountryField.click();
		bankCountry = chooseBankCountry.get(bankNm).getText();
		chooseBankCountry.get(bankNm).click();
		bankDetailNote.setValue(bankDetailsNote);
		swiftt.setValue(swift);
		bankAccNumber.setValue(accountNm);
		$("input[name='bank_iban']").setValue(iban);
		$("select[name='receiver_country']").click();
		$$("select[name='receiver_country']>option").get(bankNm).click();
		$("input[name*='company_name']").setValue(companyName);
		$$("input[name*='receiver_street']").get(0).setValue(stretAdress1);
		$("input[name*='receiver_state']").setValue(state);
		$("input[name*='zip']").setValue(zipCode);
		$("input[name*='city']").setValue(city);
		btnSubmit.shouldBe(Condition.enabled).click();
		$("h4").waitUntil(Condition.text("Hold on!"), 10000);
		$("a.btn").waitUntil(Condition.visible,10000).click();;
	}
}
