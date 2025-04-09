package adactin.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import adactin.base.BaseClass;
import adactin.utils.ExcelUtils;

public class BookingTest extends BaseClass {

	public static WebDriver driver;
	SearchHotelTest searchhoteltest;
	 private static final Logger logger = LogManager.getLogger(BookingTest.class);

	 
	 
	 
		@DataProvider(name = "userData")
		public Object[][] userData() {
		    return ExcelUtils.getUserDetails("F:\\Software\\AdactinCapstone\\src\\test\\resources\\TestData.xlsx", "Sheet1");
		}
	 
	 
	 
	 
	@Test(priority = 1, groups = {"smoke","regression"},dataProvider = "userData")
	public void testBookHotel(String fname, String lname, String address, String cardNum, String cvv) {
		logger.info("***** Starting testBookHotel *****");

		performLogin();
		logger.info("Login successful");

		searchhoteltest = new SearchHotelTest();
		searchhoteltest.testSearchHotel();
		logger.info("Search hotel executed");

		bookingPage.isRadioClick();
		logger.info("Radio button selected");

		bookingPage.clickContinuebtn();
		logger.info("Clicked Continue button");

		bookingPage.enterFirstName(fname);
	    bookingPage.enterLastName(lname);
	    bookingPage.enterAddress(address);
	    bookingPage.enterCardNumber(cardNum);
		bookingPage.selectCardType();
		bookingPage.selectMonth();
		bookingPage.selectYear();
		bookingPage.enterCVV(cvv);
		bookingPage.clickBookNOw();
		logger.info("Booking submitted with valid details");
	}

	@Test(priority = 2, dependsOnMethods = "testBookHotel", groups = {"smoke"})
	public void testMyItinerary() {
		logger.info("***** Starting testMyItinerary *****");

		Assert.assertTrue(bookingPage.isMyItineraryDispalyed(), "My Itinerary button not displayed");
		logger.info("My Itinerary button is displayed");
	}

	@Test(priority = 3, dependsOnMethods = "testBookHotel", groups = {"smoke","regression"})
	public void testDetails() {
		logger.info("***** Starting testDetails *****");

		SoftAssert softAssert = new SoftAssert();
		try {
			String exp_roomtype = "Double";
			String actualRoomType = bookingPage.getRoomType();
			logger.info("Room Type: " + actualRoomType);
			softAssert.assertEquals(actualRoomType, exp_roomtype, "Room type mismatch");

			String exp_lastname = "Marcus";
			String actualLastName = bookingPage.getLastName();
			logger.info("Last Name: " + actualLastName);
			softAssert.assertEquals(actualLastName, exp_lastname, "Last name mismatch");

			String exp_firstname = "Adam";
			String actualFirstName = bookingPage.getfirstName();
			logger.info("First Name: " + actualFirstName);
			softAssert.assertEquals(actualFirstName, exp_firstname, "First name mismatch");

		} catch (Exception e) {
			logger.error("Exception during testDetails", e);
		} finally {
			softAssert.assertAll(); 
			logger.info("Soft assertions completed in testDetails");
		}
	}

	@Test(priority = 4, dependsOnMethods = "testBookHotel")
	public void testGetOrderID() {
		logger.info("***** Starting testGetOrderID *****");
		String orderId = bookingPage.getOrderID();
		logger.info("Order ID: " + orderId);
	}
	
	
	
	@DataProvider(name = "invalidUserData")
	public Object[][] invalidUserData() {
	    return ExcelUtils.getInvalidUserDetails("F:\\Software\\AdactinCapstone\\src\\test\\resources\\TestData.xlsx", "Sheet2");
	}
	

	@Test(priority = 5, groups = {"regression"},dataProvider = "invalidUserData")
	public void testBookingWithInvalidCardDetails(String fname1, String lname1, String address1, String cardNum1, String cvv1) {
		logger.info("***** Starting testBookingWithInvalidDetails *****");

		searchHotelPage.clicksearchHotelLink();
		logger.info("Clicked on Search Hotel link");

		searchhoteltest.testSearchHotel();

		bookingPage.isRadioClick();
		bookingPage.clickContinuebtn();

		bookingPage.enterFirstName(fname1);
	    bookingPage.enterLastName(lname1);
	    bookingPage.enterAddress(address1);
	    bookingPage.enterCardNumber(cardNum1);
		bookingPage.selectCardType();
		bookingPage.selectMonth();
		bookingPage.selectYear();
		bookingPage.enterCVV(cvv1);
		bookingPage.clickBookNOw();
		
		Assert.assertEquals(bookingPage.getCardErrorMsg(), BaseClass.get("invalidCardMsg"));
		logger.info("Booking failed as expected with invalid card details-"+bookingPage.getCardErrorMsg());

	}
}
