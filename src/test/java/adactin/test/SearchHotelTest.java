package adactin.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import adactin.base.BaseClass;
import adactin.pages.SearchHotelPage;
import adactin.utils.ExcelUtils;

public class SearchHotelTest extends BaseClass {
	private static final Logger logger = LogManager.getLogger(SearchHotelTest.class);
	public static WebDriver driver;

	@Test(priority = 1, groups = {"smoke","regression"})
	public void testLinksDispay() throws InterruptedException {
		logger.info("**** Starting Test: testLinksDispay ****");
		performLogin();

		Assert.assertTrue(searchHotelPage.testSeachHotelLink(), "Search Hotel Link not displayed");
		logger.info("Search Hotel Link is displayed");

		Assert.assertTrue(searchHotelPage.testBookedItinerary(), "Booked Itinerary Link not displayed");
		logger.info("Booked Itinerary Link is displayed");
	}

	@Test(priority = 2, groups = {"smoke","regression"})
	public void testSearchHotel() {
		logger.info("**** Starting Test: testSearchHotel ****");
		SearchHotelPage searchHotelPage = new SearchHotelPage(BaseClass.driver);

		searchHotelPage.selectLocation();
		searchHotelPage.selectHotelDropdown();
		searchHotelPage.selectRoomtype();
		searchHotelPage.enterInDate();
		searchHotelPage.enterOutDate();
		searchHotelPage.clickSearch();
		logger.info("Clicked search button");

		Assert.assertTrue(searchHotelPage.isContinueBtnDisplay(), "Continue button not displayed");
	}

	@DataProvider(name = "pastDateData")
	public Object[][] pastDateData() {
	    return ExcelUtils.getPastDates("F:\\Software\\AdactinCapstone\\src\\test\\resources\\TestData.xlsx", "Sheet1");
	}
	
	
	
	@Test(priority = 3, groups = {"regression"},dataProvider = "pastDateData")
	public void bookingWithPastDate(String PastInDate, String PastOutDate) {      //neagtive test case -------------------
		
		logger.info("**** Starting Test: bookingWithPastDate ****");

		searchHotelPage.clicksearchHotelLink();
		searchHotelPage.selectLocation();
		searchHotelPage.selectHotelDropdown();
		searchHotelPage.selectRoomtype();
		 searchHotelPage.enterPastDates(PastInDate, PastOutDate);
		searchHotelPage.clickSearch();
		logger.info("Searched with past date");

		Assert.assertTrue(searchHotelPage.isContinueBtnDisplay(), "Continue button should not be displayed"); //neagtive test case
	}

	@Test(priority = 4,groups = {"regression"})
	public void searchWithoutLocation() {
		logger.info("**** Starting Test: searchWithoutLocation ****");

		searchHotelPage.clicksearchHotelLink();
		searchHotelPage.clickSearch();
		logger.info("Searched without selecting location");

		Assert.assertTrue(searchHotelPage.isLocationErrorDispayed(), "Location error not displayed");
	}

	
	@DataProvider(name = "userData")
	public Object[][] userData() {
	    return ExcelUtils.getUserDetails("F:\\Software\\AdactinCapstone\\src\\test\\resources\\TestData.xlsx", "Sheet1");
	}
	
	
	
	
	
	@Test(priority = 5, invocationCount = 5, groups = {"smoke","regression"},dataProvider = "userData")
	public void testSelectHotel(String fname, String lname, String address, String cardNum, String cvv) {
		logger.info("**** Starting Test: testSelectHotel ****");

		searchHotelPage.clicksearchHotelLink();
		testSearchHotel();

		Assert.assertTrue(searchHotelPage.isRadioClick(), "Radio button not clickable");
		searchHotelPage.clickContinuebtn();

		String expectedTitle = "Adactin.com - Book A Hotel";
		Assert.assertEquals(searchHotelPage.getPageTitle(), expectedTitle, "Page title mismatch");

		bookingPage.enterFirstName(fname);
	    bookingPage.enterLastName(lname);
	    bookingPage.enterAddress(address);
	    bookingPage.enterCardNumber(cardNum);
		bookingPage.selectCardType();
		bookingPage.selectMonth();
		bookingPage.selectYear();
		bookingPage.enterCVV(cvv);
		bookingPage.clickBookNOw();

		Assert.assertTrue(bookingPage.isMyItineraryDispalyed(), "My Itinerary not displayed");
	}

	@Test(priority = 6, groups = {"regression"})
	public void testWithoutSelectHotel() {
		logger.info("**** Starting Test: testWithoutSelectHotel ****");

		searchHotelPage.clicksearchHotelLink();
		testSearchHotel();
		searchHotelPage.clickContinuebtn();

		String expectedMsg = "Please Select a Hotel";
		Assert.assertEquals(searchHotelPage.getPleaseSelectHotelErrorMsg(), expectedMsg, "Error message mismatch");
	}

	@Test(priority = 7, groups = {"smoke"})
	public void testWithoutOptionalFields() {
		logger.info("**** Starting Test: testWithoutOptionalFields ****");

		searchHotelPage.clicksearchHotelLink();
		searchHotelPage.selectLocation();
		searchHotelPage.clickSearch();
		logger.info("Searched with only mandatory fields");

		Assert.assertEquals(searchHotelPage.getHotelCOunt(), 5, "Hotel count mismatch");
	}
}
