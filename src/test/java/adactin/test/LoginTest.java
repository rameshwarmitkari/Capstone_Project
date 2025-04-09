package adactin.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;

import adactin.base.BaseClass;


public class LoginTest extends BaseClass {
	
	private static final Logger logger = LogManager.getLogger(LoginTest.class);
	
	@Test(priority = 1, groups = {"smoke","regression"})
	public void testLogo() {
		logger.info("--- Starting Test: testLogo -----");
		try {
			Assert.assertTrue(loginPage.getHotellogo(), "Hotel logo should be visible");
			logger.info("Hotel logo is present");
		} catch (NoSuchElementException e) {
			logger.error("Hotel logo not found", e);
		}
	}

	@Test(priority = 2, groups = {"regression"})
	public void isLoginBtnEnabled() {
		logger.info("--- Starting Test: isLoginBtnEnabled ---");
		try {
			Assert.assertTrue(loginPage.isLoginBtnClickable(), "Login button should be clickable");
			logger.info("Login button is clickable");
		} catch (NoSuchElementException e) {
			logger.error("Login button not found or not clickable", e);
		}
	}
}