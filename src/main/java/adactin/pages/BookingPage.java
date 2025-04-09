package adactin.pages;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookingPage extends Basepage {

	private static final Logger logger = LogManager.getLogger(BookingPage.class);
	WebDriverWait wait;

	public BookingPage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(25));
	}

	@FindBy(css = "#first_name")
	private WebElement firstNameTxtBox;

	@FindBy(css = "#last_name")
	private WebElement lastNameTxtBox;

	@FindBy(css = "#address")
	private WebElement addresstxtBox;

	@FindBy(css = "#cc_num")
	private WebElement cardTxtBox;

	@FindBy(xpath = "//select[@id='cc_type']/option")
	private List<WebElement> cardType;

	@FindBy(xpath = "//select[@id='cc_exp_month']/option")
	private List<WebElement> month;

	@FindBy(xpath = "//select[@id='cc_exp_year']/option")
	private List<WebElement> year;

	@FindBy(xpath = "//input[@id='cc_cvv']")
	private WebElement cvv_num;

	@FindBy(xpath = "//input[@value='Continue']")
	private WebElement continueBtn;

	@FindBy(xpath = "//input[@type='radio']")
	private WebElement radioBtn;

	@FindBy(xpath = "//input[@id='book_now']")
	private WebElement bookNowBtn;

	@FindBy(xpath = "//input[@id='my_itinerary']")
	private WebElement myItinerary;

	@FindBy(xpath = "//table[@class='login']/tbody/tr[18]/td[2]/input[@id='order_no']")
	private WebElement orderNum;

	@FindBy(xpath = "//table[@class='login']/tbody/tr[4]/td[2]/input[@id='room_type']")
	private WebElement room_type;

	@FindBy(xpath = "//table[@class='login']/tbody/tr[16]/td[2]/input[@id='last_name']")
	private WebElement lastName;

	@FindBy(xpath = "//table[@class='login']/tbody/tr[15]/td[2]/input[@id='first_name']")
	private WebElement firstName;
	
	@FindBy(xpath = "//label[contains(text(),'Please Enter your 16 Digit')]")
	private WebElement cardErrorTxt;
	

	// ----------------------------------------

	public void enterFirstName(String fname) {
		firstNameTxtBox.sendKeys(fname);
		logger.info("Entered first name: " + fname);
	}

	public void enterLastName(String lname) {
		lastNameTxtBox.sendKeys(lname);
		logger.info("Entered last name: " + lname);
	}

	public void enterAddress(String addr) {
		addresstxtBox.sendKeys(addr);
		logger.info("Entered address: " + addr);
	}

	public void enterCardNumber(String cardnum) {
		cardTxtBox.sendKeys(cardnum);
		logger.info("Entered card number: " + cardnum);
	}

	public void selectCardType() {
		String exp_type = "VISA";
		try {
			for (WebElement str : cardType) {
				if (str.getText().equals(exp_type)) {
					str.click();
					logger.info("Selected card type: " + exp_type);
					break;
				}
			}
		} catch (Exception e) {
			logger.error("Error selecting card type", e);
		}
	}

	public void clickContinuebtn() {
		continueBtn.click();
		logger.info("Clicked Continue button");
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

	public void selectMonth() {
		String expmonth = "May";
		for (WebElement str_month : month) {
			if (str_month.getText().equals(expmonth)) {
				str_month.click();
				logger.info("Selected expiry month: " + expmonth);
				break;
			}
		}
	}

	public void selectYear() {
		String exp_year = "2025";
		for (WebElement str_year : year) {
			if (str_year.getText().equals(exp_year)) {
				str_year.click();
				logger.info("Selected expiry year: " + exp_year);
				break;
			}
		}
	}

	public void enterCVV(String cvvnum) {
		cvv_num.sendKeys(cvvnum);
		logger.info("Entered CVV: " + cvvnum);
	}

	public void clickBookNOw() {
		bookNowBtn.click();
		logger.info("Clicked Book Now button");
	}

	public boolean isMyItineraryDispalyed() {
		try {
			wait.until(ExpectedConditions.visibilityOf(myItinerary));
			logger.info("My Itinerary button is displayed");
			return myItinerary.isDisplayed();
		} catch (TimeoutException t) {
			logger.error("My Itinerary button not displayed in time", t);
		}
		return false;
	}

	public String getOrderID() {
		wait.until(ExpectedConditions.visibilityOf(orderNum));
		String orderId = orderNum.getAttribute("value");
		logger.info("Order ID retrieved: " + orderId);
		return orderId;
	}

	public String getRoomType() {
		wait.until(ExpectedConditions.visibilityOf(room_type));
		String roomType = room_type.getAttribute("value");
		logger.info("Room type retrieved: " + roomType);
		return roomType;
	}

	public String getLastName() {
		wait.until(ExpectedConditions.visibilityOf(lastName));
		String lname = lastName.getAttribute("value");
		logger.info("Last name retrieved: " + lname);
		return lname;
	}

	public String getfirstName() {
		wait.until(ExpectedConditions.visibilityOf(firstName));
		String fname = firstName.getAttribute("value");
		logger.info("First name retrieved: " + fname);
		return fname;
	}
	
	public String getCardErrorMsg() {
		return cardErrorTxt.getText();
		
	}

	
	
	
}
