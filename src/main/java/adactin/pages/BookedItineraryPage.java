package adactin.pages;

import java.time.Duration;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.Alert;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookedItineraryPage extends Basepage {

	private static final Logger logger = LogManager.getLogger(BookedItineraryPage.class);
	WebDriverWait wait;

	public BookedItineraryPage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(25));
	}

	@FindBy(xpath = "//a[text()='Booked Itinerary']")
	private WebElement itineraryLink;

	@FindBy(xpath = "//table[@class='login']/tbody//table/tbody/tr/td")
	private List<WebElement> bookedItinarytable;

	@FindBy(xpath = "//table[@class='login']/tbody//table/tbody/tr[2]/td[2]")
	private WebElement bookedOrderID;

	@FindBy(xpath = "//input[@value='Cancel Selected']")
	private WebElement cancelSelectedBtn;

	@FindBy(xpath = "(//input[@type='checkbox'])[2]")
	private WebElement checkbox;

	@FindBy(xpath = "//table[@class='login']/tbody//table/tbody/tr/td[1]/input[@type='checkbox']")
	private List<WebElement> checkbox_size;

	@FindBy(xpath = "//table[@class='login']/tbody//table/tbody/tr[2]/td[3]")
	private WebElement order_cancelBtn;

	@FindBy(xpath = "//label[contains(text(),'cancelled')]")
	private WebElement booking_cancelledtxt;

	@FindBy(xpath = "//input[@id='order_id_text']")
	private WebElement searchtxt;

	@FindBy(xpath = "//input[@value='Go']")
	private WebElement goBtn;

	@FindBy(xpath = "//label[text()='1 result(s) found.']")
	private WebElement resultTxt;

	@FindBy(xpath = "//a[text()='Logout']")
	private WebElement logOutLink;

	@FindBy(xpath = "//table[@class='content']/tbody//table/tbody/tr[1]/td[@class='reg_success']")
	private WebElement logoutSuccessMsg;

	@FindBy(xpath = "//table/tbody/tr/td/div/input[@type='checkbox']")
	private WebElement selectAllCheckBox;

	@FindBy(xpath = "//a[text()='Click here to login again']")
	private WebElement loginAgain;

//--------------------------------------------------------------

	public void clickItinerary() {
		try {
			itineraryLink.click();
			logger.info("Clicked on 'Booked Itinerary' link");
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementException while clicking 'Booked Itinerary'", e);
		}
	}

	public String getBooKedID() {
		String id = bookedOrderID.getText();
		logger.info("Fetched booked order ID: " + id);
		return id;
	}

	public boolean getBookingDetails() {
		String hotelName = "Hotel Sunshine";
		String locationName = "Brisbane";
		String firstName = "Adam";

		wait.until(ExpectedConditions.visibilityOfAllElements(bookedItinarytable));
		for (WebElement details : bookedItinarytable) {
			String str = details.getText();
			if (str.equals(hotelName) || str.equals(locationName) || str.equals(firstName)) {
				logger.info("Booking detail found: " + str);
				return true;
			}
		}
		logger.warn("Booking details not found");
		return false;
	}

	public boolean isCancelSelectedBtnVisbile() {
		boolean visible = cancelSelectedBtn.isDisplayed();
		logger.info("'Cancel Selected' button visible: " + visible);
		return visible;
	}

	public boolean isCheckBoxVisible() {
		boolean visible = checkbox.isDisplayed();
		logger.info("Single booking checkbox visible: " + visible);
		return visible;
	}

	public void clickCheckBox() {
		checkbox.click();
		logger.info("Clicked on booking checkbox");
	}

	public String clickCancelSelectedBtn() {
		cancelSelectedBtn.click();
		logger.info("Clicked on 'Cancel Selected' button");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		String alertText = alert.getText();
		alert.accept();
		logger.info("Alert handled. Text: " + alertText);
		return alertText;
	}

	public int getCheckBoxSize() {
		try {
			wait.until(ExpectedConditions.visibilityOfAllElements(checkbox_size));
		} catch (TimeoutException e) {
			logger.warn("TimeoutException on checkbox size fetch. Retrying...");
			wait.until(ExpectedConditions.visibilityOfAllElements(checkbox_size));
		}
		int size = checkbox_size.size();
		logger.info("Number of checkboxes (bookings): " + size);
		return size;
	}

	public String clickCancelwithOrder() {
		order_cancelBtn.click();
		logger.info("Clicked cancel button for specific order");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		String alertText = alert.getText();
		alert.accept();
		logger.info("Order cancel alert handled. Text: " + alertText);
		return alertText;
	}

	public String getbookingCancelledTxt() {
		String txt = booking_cancelledtxt.getText();
		logger.info("Cancellation confirmation text: " + txt);
		return txt;
	}

	public void entrOrderID(String id) {
		try {
			driver.navigate().refresh();
			searchtxt.sendKeys(id);
			logger.info("Entered order ID in search field: " + id);
		} catch (Exception e) {
			logger.error("Error entering order ID", e);
		}
	}

	public void clickGoBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(goBtn));
		goBtn.click();
		logger.info("Clicked Go button to search order");
	}

	public String extractOrderNumber() {
		order_cancelBtn.click();
		logger.info("Clicked order cancel to trigger alert");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		String alertText = alert.getText();
		alert.dismiss();
		logger.info("Dismissed alert after extracting order number. Text: " + alertText);

		Pattern pattern = Pattern.compile("Order no\\.\\s+(\\w+)");
		Matcher matcher = pattern.matcher(alertText);

		if (matcher.find()) {
			String orderId = matcher.group(1);
			logger.info("Extracted order number: " + orderId);
			return orderId;
		}

		logger.warn("Order number not found in alert text");
		return null;
	}

	public void clickLogOutLink() {
		logOutLink.click();
		logger.info("Clicked on Logout link");
	}

	public String getlogoutmessage() {
		String msg = logoutSuccessMsg.getText();
		logger.info("Logout success message: " + msg);
		return msg;
	}

	public boolean selectAllCehckbox() {
		if (selectAllCheckBox.isSelected()) {
			logger.info("'Select All' checkbox already selected");
			return false;
		} else {
			selectAllCheckBox.click();
			logger.info("Selected 'Select All' checkbox");
			return true;
		}
	}

	public void clickLoginAgain() {
		loginAgain.click();
		logger.info("Clicked on 'Login Again' link");
	}
}
