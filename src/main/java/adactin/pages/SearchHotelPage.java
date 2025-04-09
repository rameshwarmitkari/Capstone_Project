package adactin.pages;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchHotelPage extends Basepage {

	private static final Logger logger = LogManager.getLogger(SearchHotelPage.class);
	WebDriverWait wait;

	public SearchHotelPage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	@FindBy(css = "#username_show")
	private WebElement hellouserTxt;

	@FindBy(xpath = "//a[text()='Search Hotel']")
	private WebElement searchHotelLink;

	@FindBy(xpath = "//a[text()='Booked Itinerary']")
	private WebElement itinerarylLink;

	@FindBy(xpath = "//select[@id='location']")
	private WebElement clickLocation;

	@FindBy(xpath = "//select[@id='location']")
	private WebElement selectLocation;

	@FindBy(xpath = "//select[@id='hotels']")
	private WebElement clickHotelsDropdown;

	@FindBy(xpath = "//select[@id='hotels']/option")
	private List<WebElement> selectHotel;

	@FindBy(xpath = "//select[@id='room_type']")
	private WebElement clickRoomType;

	@FindBy(xpath = "//select[@id='room_type']/option")
	private List<WebElement> selectRoomtype;

	@FindBy(xpath = "//select[@id='room_nos']")
	private WebElement clickRoomNo;

	@FindBy(xpath = "//select[@id='room_nos']/option")
	private List<WebElement> selectRoomNo;

	@FindBy(xpath = "//input[@id='datepick_in']")
	private WebElement checkInDate;

	@FindBy(xpath = "//input[@id='datepick_out']")
	private WebElement checkOutDate;

	@FindBy(css = "#Submit")
	private WebElement searchBtn;

	@FindBy(xpath = "//input[@value='Continue']")
	private WebElement continueBtn;

	@FindBy(xpath = "//input[@value='AUD $ 275']")
	private WebElement perNightCharge;

	@FindBy(xpath = "//span[text()='Please Select a Location']")
	private WebElement locationErrorTxt;

	@FindBy(xpath = "//input[@type='radio']")
	private WebElement radioBtn;

	@FindBy(xpath = "//label[text()='Please Select a Hotel']")
	private WebElement pleaseSelectHotelErr;

	@FindBy(xpath = "//table[@class='login']/tbody/tr[2]/td/table/tbody/tr")
	private List<WebElement> countOfHotels;

//----------------------------------------------------------------------------

	public String getHellouserTxt() {
		String helloText = hellouserTxt.getText();
		logger.info("Username text fetched: " + helloText);
		return helloText;
	}

	public boolean testSeachHotelLink() {
		boolean status = searchHotelLink.isEnabled();
		logger.info("Search Hotel link enabled: " + status);
		return status;
	}

	public void clicksearchHotelLink() {
		wait.until(ExpectedConditions.elementToBeClickable(searchHotelLink));
		searchHotelLink.click();
		logger.info("Clicked on Search Hotel link");
	}

	public boolean testBookedItinerary() {
		boolean status = itinerarylLink.isEnabled();
		logger.info("Booked Itinerary link enabled: " + status);
		return status;
	}

	public void clickLocationDropdown() {
		clickLocation.click();
		logger.info("Clicked Location dropdown");
	}

	public void selectLocation() {
		try {
			wait.until(ExpectedConditions.visibilityOf(selectLocation));
			selectLocation.click();
			Select select = new Select(selectLocation);
			select.selectByVisibleText("Brisbane");
			logger.info("Selected location: Brisbane");
		} catch (TimeoutException t) {
			logger.error("Timeout selecting location", t);
		}
	}

	public void selectHotelDropdown() {
		wait.until(ExpectedConditions.visibilityOf(clickHotelsDropdown));
		clickHotelsDropdown.click();
		Select select = new Select(clickHotelsDropdown);
		select.selectByVisibleText("Hotel Sunshine");
		logger.info("Selected hotel: Hotel Sunshine");
	}

	public void selectRoomtype() {
		String exp_room = "Double";
		for (WebElement str_room : selectRoomtype) {
			String actroom = str_room.getText();
			if (actroom.equals(exp_room)) {
				str_room.click();
				logger.info("Selected room type: " + exp_room);
				break;
			}
		}
	}

	public void enterInDate() {
		checkInDate.clear();
		checkInDate.sendKeys("20/04/2025");
		logger.info("Entered check-in date: 20/04/2025");
	}

	public void enterOutDate() {
		checkOutDate.clear();
		checkOutDate.sendKeys("21/04/2025");
		logger.info("Entered check-out date: 21/04/2025");
	}

//	public void pastInDate() {
//		checkInDate.clear();
//		checkInDate.sendKeys("10/02/2025");
//		logger.info("Entered past check-in date: 10/02/2025");
//	}
//
//	public void pastOutDate() {
//		checkOutDate.clear();
//		checkOutDate.sendKeys("11/02/2025");
//		logger.info("Entered past check-out date: 11/02/2025");
//	}
	
	public void enterPastDates(String PastInDate, String PastOutDate) {
	    checkInDate.clear();
	    checkInDate.sendKeys(PastInDate);
	    logger.info("Entered past check-in date: " + PastInDate);

	    checkOutDate.clear();
	    checkOutDate.sendKeys(PastOutDate);
	    logger.info("Entered past check-out date: " + PastOutDate);
	}

	public void clickSearch() {
		searchBtn.click();
		logger.info("Clicked Search button");
	}

	public boolean isContinueBtnDisplay() {
		try {
			wait.until(ExpectedConditions.visibilityOf(continueBtn));
			logger.info("Continue button displayed");
			return continueBtn.isDisplayed();
		} catch (NoSuchElementException e) {
			logger.error("Continue button not found", e);
		}
		return false;
	}

	public String isPerNightChargeVisible() {
		wait.until(ExpectedConditions.visibilityOf(perNightCharge));
		String charge = perNightCharge.getText();
		logger.info("Per night charge: " + charge);
		return charge;
	}

	public boolean isLocationErrorDispayed() {
		wait.until(ExpectedConditions.visibilityOf(locationErrorTxt));
		boolean status = locationErrorTxt.isDisplayed();
		logger.info("Location error message displayed: " + status);
		return status;
	}

	public boolean isRadioClick() {
		if (!radioBtn.isSelected()) {
			radioBtn.click();
			logger.info("Radio button clicked");
			return true;
		}
		logger.info("Radio button already selected");
		return false;
	}

	public void clickContinuebtn() {
		continueBtn.click();
		logger.info("Clicked Continue button");
	}

	public String getPageTitle() {
		wait.until(ExpectedConditions.titleContains("Book"));
		String title = driver.getTitle();
		logger.info("Page title fetched: " + title);
		return title;
	}

	public String getPleaseSelectHotelErrorMsg() {
		String msg = pleaseSelectHotelErr.getText();
		logger.info("Error message: " + msg);
		return msg;
	}

	public int getHotelCOunt() {
		wait.until(ExpectedConditions.visibilityOfAllElements(countOfHotels));
		int count = countOfHotels.size();
		logger.info("Number of hotels displayed: " + count);
		return count;
	}
}
