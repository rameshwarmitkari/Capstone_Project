package adactin.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends Basepage {

	private static final Logger logger = LogManager.getLogger(LoginPage.class);

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//img[@class='logo']")
	private WebElement hotelLogo;

	@FindBy(css = "#username")
	private WebElement unameTxtBox;

	@FindBy(css = "#password")
	private WebElement pwdTxtBox;

	@FindBy(css = "#login")
	private WebElement loginBtn;

	@FindBy(xpath = "//td[contains(text(),'Welcome to Adactin')] ")
	private WebElement welcomeTxt;

	public boolean getHotellogo() {
		boolean isDisplayed = hotelLogo.isDisplayed();
		logger.info("Hotel logo displayed: " + isDisplayed);
		return isDisplayed;
	}

	public void enterUsername(String uname) {
		unameTxtBox.sendKeys(uname);
		logger.info("Entered username: " + uname);
	}

	public void enterPassword(String pwd) {
		pwdTxtBox.sendKeys(pwd);
		logger.info("Entered password: [HIDDEN]");
	}

	public boolean isLoginBtnClickable() {
		boolean enabled = loginBtn.isEnabled();
		logger.info("Login button clickable: " + enabled);
		return enabled;
	}

	public void clickLogin() {
		loginBtn.click();
		logger.info("Clicked login button");
	}
}
