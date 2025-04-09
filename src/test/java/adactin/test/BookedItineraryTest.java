package adactin.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.Assert;
import org.testng.annotations.Test;

import adactin.base.BaseClass;

public class BookedItineraryTest extends BaseClass {

	private static final Logger logger = LogManager.getLogger(BookedItineraryTest.class);

	@Test(priority = 1, groups = {"smoke","regression"})
	public void testBookedItinerariLink() {
		logger.info("=====> Starting testBookedItinerariLink =====");
		performLogin();
		bookedItineraryPage.clickItinerary();
		logger.info("Clicked on Booked Itinerary link");
	}

	@Test(priority = 2, groups = {"regression"})
	public void verifyDetails() {
		logger.info("===== Starting verifyDetails =====");
		
		boolean isVisible = bookedItineraryPage.isCancelSelectedBtnVisbile();
		logger.info("Cancel Selected button visible: " + isVisible);
		Assert.assertTrue(isVisible, "Fail if details are not found");
	}

	@Test(priority = 3, groups = {"smoke","regression"})
	public void testCancelBookingWithCheckBox() {
		logger.info("===== Starting testCancelBookingWithCheckBox =====");
		
		logger.info("Before count: " + bookedItineraryPage.getCheckBoxSize());
		Assert.assertTrue(bookedItineraryPage.isCheckBoxVisible(), "Checkbox not visible");
		bookedItineraryPage.clickCheckBox();
		
		String alertMsg = bookedItineraryPage.clickCancelSelectedBtn();
		logger.info("Alert Message: " + alertMsg);
		logger.info("After count: " + bookedItineraryPage.getCheckBoxSize());
	}

	@Test(priority = 4, groups = {"regression"})
	public void testCancelWithOrderBtn() {
		logger.info("===== Starting testCancelWithOrderBtn =====");
		logger.info("Before count: " + bookedItineraryPage.getCheckBoxSize());

		String alertMsg = bookedItineraryPage.clickCancelwithOrder();
		logger.info("Alert Message: " + alertMsg);

		String expected = "The booking has been cancelled.";
		String actual = bookedItineraryPage.getbookingCancelledTxt();
		logger.info("Cancellation Text: " + actual);
		Assert.assertEquals(actual, expected);

		logger.info("After count: " + bookedItineraryPage.getCheckBoxSize());
	}

	@Test(priority = 5, groups = {"smoke"})
	public void testSearchOrder() {
		logger.info("===== Starting testSearchOrder =====");

		String order_Num = bookedItineraryPage.extractOrderNumber();
		logger.info("Extracted Order ID: " + order_Num);

		bookedItineraryPage.entrOrderID(order_Num);
		bookedItineraryPage.clickGoBtn();

		logger.info("Before cancel count: " + bookedItineraryPage.getCheckBoxSize());
		bookedItineraryPage.clickCancelwithOrder();
	}

	@Test(priority = 6,groups = {"regression"})
	public void testSelectAllCehckbox() {
		logger.info("===== Starting testSelectAllCheckbox =====");
		Assert.assertTrue(bookedItineraryPage.selectAllCehckbox(), "Select All checkbox failed");
		bookedItineraryPage.clickCancelSelectedBtn();
		logger.info("Selected all and clicked cancel");
	}

	@Test(priority = 7,groups = {"smoke","regression"})
	public void testLogout() {
		logger.info("===== Starting testLogout =====");

		bookedItineraryPage.clickLogOutLink();

		String expMsg = "You have successfully logged out. Click here to login again";
		String actualMsg = bookedItineraryPage.getlogoutmessage();
		logger.info("Logout message: " + actualMsg);
		Assert.assertEquals(actualMsg, expMsg);
	}

	@Test(priority = 8, groups = {"smoke","regression"})
	public void testLoginAgain() {
		logger.info("===== Starting testLoginAgain =====");

		bookedItineraryPage.clickLoginAgain();

		String exp_Title = "Adactin.com - Hotel Reservation System";
		String actual_Title = driver.getTitle();
		logger.info("Page Title after login again: " + actual_Title);
		Assert.assertEquals(actual_Title, exp_Title, "Fail if page title is not matching");
	}
}
