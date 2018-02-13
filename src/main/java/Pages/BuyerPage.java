package Pages;

import static com.codeborne.selenide.Selenide.*;


import java.util.Random;


import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.codeborne.pdftest.PDF;
import static com.codeborne.pdftest.PDF.*;
import static org.junit.Assert.assertThat;
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
	private SelenideElement perPage200 = $("a[ng-class='{ active: perPage == 200 }']");
	private SelenideElement openDashBoardPage = $(byText("Dashboard"));
	private SelenideElement loader = $("div[class='loader']");
	private SelenideElement datePicker = $("#date_range");
	private SelenideElement startDate = $("input[name='daterangepicker_start']");
	private SelenideElement applyDate = $(byText("Apply"));
	private SelenideElement webTotalSpend = $("div.tl_spend_ico>div>span");
	private SelenideElement webTotalLeads = $("div.tl_leads_ico>div>span");
	private SelenideElement profile = $("a.profile");
	private SelenideElement logOut = $("div.drop_userInfo>ul>li:nth-child(5)>a");
	private ElementsCollection leadStatusCollection = $$(By.xpath("//tr[@class='lead-row ng-scope']/td[12]"));
	private ElementsCollection buyCplParLead = $$(By.xpath("//tr[@class='lead-row ng-scope']/td[5]"));
	private ElementsCollection leadCollection = $$("tr[ng-repeat='lead in leadsCollection']");
	private ElementsCollection campLeadBuyCpl = $$(By.xpath("//tr[@class='lead-row ng-scope']/td[3]"));
	private ElementsCollection campaignsList = $$("tr[ng-repeat='itm in campaignsList']");
	private ElementsCollection campnTotalSpend = $$(By.xpath("//tr[@class='ng-scope']//td[10]"));
	private ElementsCollection campnTotalLeads = $$(By.xpath("//tr[@class='ng-scope']//td[6]"));
	private ElementsCollection campaignsName = $$("a[ui-sref='campaign_item.month({ idCampaign: itm.id})']");
	private ElementsCollection campaignLeadStatus = $$(By.xpath("//tr[@class='lead-row ng-scope']//td[10]"));
	// -----------------------------------------------------------------------------------------------------
	private SelenideElement industryField = $("a[placeholder*='Industry']");
	private SelenideElement btnSubmit = $("button.btn");
	private SelenideElement firstName = $("input[name='firstName']");
	private SelenideElement lastName = $("input[name='lastName']");
	private SelenideElement phoneNumber = $("input[id='tel-dc']");
	private SelenideElement email = $("input[placeholder*='Email']");
	private SelenideElement password = $("input[name='pass']");
	private SelenideElement confirmPassword = $("input[name='rePass']");
	private ElementsCollection integrationOption = $$("label[class*='checkbox']");
	private SelenideElement companyName = $("input[name='companyName']");
	private SelenideElement street1 = $("input[name='companyAddress']");
	private SelenideElement city = $("input[name='city']");
	private SelenideElement state = $("input[name='state']");
	private SelenideElement country = $("a[placeholder='Select Country']");
	private SelenideElement zipCode = $("input[name='zip']");
	private ElementsCollection chooseCountry = $$("li[role='option']");

	/**
	 * calculate buyer total spend and total lead parameters
	 * 
	 */
	public void calcTotalSpentAndTotalLead() {
		Float leadBuyCpl;
		Float totalLeads = (float) 0;
		Float totaleadBuyCpl = (float) 0;
		openLeadListPage.click();
		wait.waitUntilAngularPageLoaded();
		perPage200.click();
		loader.shouldBe(Condition.visible);
		wait.waitUntilAngularPageLoaded();
		for (int i = 0; i < leadCollection.size(); i++) {
			leadBuyCpl = convertWebElementToNm(buyCplParLead.get(i));
			String leadstatus = leadStatusCollection.get(i).getText();
			if (leadstatus.equalsIgnoreCase("Paid") || leadstatus.equalsIgnoreCase("Dispute")
					|| leadstatus.equalsIgnoreCase("Dispute Declined")) {
				totaleadBuyCpl = totaleadBuyCpl + leadBuyCpl;
				totalLeads++;
			}

		}
		checkTotalSpentAndTotalLeads(totaleadBuyCpl, totalLeads);
		// return new Float[] { totalLeads, totaleadBuyCpl };
	}

	/**
	 * Buyer > DashBoard CHECK UI Total Spent and Total Leads is right?
	 */
	public void checkTotalSpentAndTotalLeads(Float totalspent, Float totalLeads) {
		openDashBoardPage.click();
		selectDate("13/11/16","Dashboard");
		loader.shouldBe(Condition.disappear);
		softAssert.assertEquals(convertWebElementToNm(webTotalSpend), totalspent, "Totalspend");
		softAssert.assertEquals(convertWebElementToNm(webTotalLeads), totalLeads, "TotalLeads");
		softAssert.assertAll();
		Reporter.log("total spent is correct " + totalspent, true);
		Reporter.log("total Leads is correct " + totalLeads, true);
	}

	/**
	 * Test total leads/total spent / avgCpl par offer in buyer > dashboard >
	 * Recently Updated Campaigns
	 */
	public void checkStatisticPerCamp()  {
		String campName, campLeadStatus;
		Float webCampSpend, webCampLeads, leadBuyCpl = (float) 0, webAvgCPL = (float) 0;
		wait.waitUntilAngularPageLoaded();
		for (int i = 0; i < campaignsList.size(); i++) {
			campName = campaignsName.get(i).getText();
			webCampSpend = convertWebElementToNm(campnTotalSpend.get(i));
			webCampLeads = convertWebElementToNm(campnTotalLeads.get(i));
			webAvgCPL = webCampSpend / webCampLeads;
			wait.waitUntilAngularPageLoaded();
			campaignsName.get(i).click();
			wait.waitUntilAngularPageLoaded();
			selectDate("13/11/16","Report");
			wait.waitUntilAngularPageLoaded();
			perPage200.click();
			wait.waitUntilAngularPageLoaded();
			Float totalLeads = (float) 0, totaleadBuyCpl = (float) 0;
			Float avgCpl = (float) 0;
			for (int j = 0; j < $$("tr[ng-repeat*='leadsCollection']").size(); j++) {
				leadBuyCpl = convertWebElementToNm(campLeadBuyCpl.get(j));
				campLeadStatus = campaignLeadStatus.get(j).getText();

				if (campLeadStatus.equalsIgnoreCase("Paid") || campLeadStatus.equalsIgnoreCase("Dispute")
						|| campLeadStatus.equalsIgnoreCase("Dispute Declined")) {
					totaleadBuyCpl = totaleadBuyCpl + leadBuyCpl;
					totalLeads++;
					avgCpl = totaleadBuyCpl / totalLeads;
				}

			}
			softAssert.assertEquals(webCampSpend, totaleadBuyCpl, campName+ " Total spent");
			softAssert.assertEquals(webCampLeads, totalLeads, campName+" Total leads");
			softAssert.assertEquals(webAvgCPL, avgCpl, campName);
			Reporter.log("campgns name : " + campName + "total spend : " + totaleadBuyCpl + " total leads: " + totalLeads
					+ "avgCpl " + avgCpl, true);
			openDashBoardPage.click();

		}
		softAssert.assertAll();

	}

	public Float convertWebElementToNm(SelenideElement nm) {
		String element;
		Float result;
		element = nm.getText();
		element = element.replace("$", "").replace(",", "");
		result = Float.parseFloat(element);
		return result;
	}

	public void selectDate(String Date,String page) {
		if(page == "Dashboard")
		{
			$("html[ng-app='leadsBasket']").scrollTo();
			datePicker.click();
			startDate.clear();
			startDate.setValue(Date);
			applyDate.click();
		}
		else {
			$$("div[class=title]").get(2).scrollIntoView(true);
			$("input[st-search='date_range']").shouldBe(Condition.enabled).click();
			$$("input[name='daterangepicker_start']").get(1).clear();
			$$("input[name='daterangepicker_start']").get(1).setValue(Date);
			$$("button[class*='btn-success']").get(1).click();
		}

	}

	public void logOut() {
		profile.click();
		logOut.shouldBe(Condition.visible).click();
	}

	/**
	 * /register-industry, test titles,Test login Link ,Choose industry > press on
	 * create account button
	 */
	public void industryPage() {
		$("div.reg>h2").shouldHave(Condition.text("Create a Free Account on LEADSBASKET"));
		$("div.choose_industry_text")
				.shouldHave(Condition.text("Get ready to be bombarded with some top quality leads!"));
		$("a.link").shouldBe(Condition.visible).click();
		confirm();
		$("div.auth>h2").shouldHave(Condition.text("Login to LeadsBasket"));
		back();
		btnSubmit.shouldBe(Condition.disabled);
		industryField.click();
		$(byText("Zoo Industry")).click();
		btnSubmit.shouldHave(Condition.text("Create Account")).click();

	}

	/**
	 * /register, test titles , test "Terms of Service" page ,test login link> fill
	 * all field with valid info and press Continue button
	 */
	public void registerPage() {
		FormLbPage rendom = new FormLbPage();
		String text = rendom.generateEmail("abcdfddDd23%2", 8);
		String rdBname = rendom.generateEmail("abcdfg", 4);
		$("h4").shouldHave(Condition.text("100% Self-service Platform for Quality Lead Generation"));
		btnSubmit.shouldBe(Condition.disabled);
		$(byText("Terms of Service")).click();
		switchTo().window(1);
		$("div.terms>h1").shouldHave(Condition.text("TERMS OF USE"));
		switchTo().window(1).close();
		switchTo().window(0);
		$(byText("Login")).shouldBe(Condition.visible).click();
		confirm();
		$("div.auth>h2").shouldHave(Condition.text("Login to LeadsBasket"));
		back();
		wait.waitUntilAngularPageLoaded();
		firstName.setValue("selenide" + rdBname);
		lastName.setValue("automtic" + rdBname);
		phoneNumber.setValue(phoneNumber.getAttribute("placeholder"));
		email.setValue("lbdemo234+" + text + "@gmail.com");
		password.setValue("D%1" + text);
		confirmPassword.setValue("D%1" + text);
		btnSubmit.shouldBe(Condition.visible).click();

	}

	/**
	 * Test /register/integration >test titles,test link "Integration with API",
	 * click on "Via Email" check box,fill valid email,press on Continue
	 * 
	 * @throws Exception
	 */
	public void integrationPage() {
		$("h2").shouldHave(Condition.text("Connect To Your Platform"));
		$("div.reg>h4").shouldHave(Condition.text("Get Leads Directly To Your Email or Platform"));
		$("h2.h2_inside").shouldHave(Condition.text("Select how you wish to receive your leads"));
		btnSubmit.shouldBe(Condition.disabled);
		integrationOption.get(0).click();
		email.setValue("test@test.com");
		btnSubmit.shouldBe(Condition.visible).click();
		$("h2").shouldHave(Condition.text("Billing Information"));
	}

	/**
	 * /register/billing, test title , TERMS OF USE , ,FILL THE FORM and press
	 * submit
	 */
	public void billingPage() {
		Random rand = new Random();
		int countryIndex = rand.nextInt(239);
		$("h2").shouldHave(Condition.text("Billing Information"));
		$("h4").shouldHave(Condition.text("Enter Billing Information & Fund Your Account"));
		$(byText("click here.")).click();
		switchTo().window(1);
		$("div.terms>h1").shouldHave(Condition.text("TERMS OF USE"));
		switchTo().window(1).close();
		switchTo().window(0);
		companyName.setValue("Company Name");
		street1.setValue("test steeen 234 -4 4333 1");
		city.setValue("tel aviv");
		btnSubmit.should(Condition.disabled);
		state.setValue("israel");
		country.click();
		chooseCountry.get(countryIndex).click();
		zipCode.setValue("464564");
		email.setValue("test@test.com");
		btnSubmit.should(Condition.enabled).click();
	}

	/**
	 * /register/finish
	 */
	public void finishPage() {
		$("h2").shouldHave(Condition.text("Congratulations!"));
		$("h4").shouldHave(Condition.text("Done"));
		$("p").shouldHave(Condition.text("You Can Start Creating Your Campaigns and Get Leads"));
		$("[class*='finish_btn']").click();
		$("h5").shouldHave(Condition.text("Please fill in all required entry setting fields and create new campaign."));
	}

	public void downalodPdf() throws Exception {
		$(byText("Integration with API")).download();
		PDF pdf = new PDF($(byText("Integration with API")).download());
		assertThat(pdf, containsText("Get leads by API"));
	}

}