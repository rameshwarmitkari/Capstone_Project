package adactin.base;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import adactin.pages.BookedItineraryPage;
import adactin.pages.BookingPage;
import adactin.pages.LoginPage;
import adactin.pages.SearchHotelPage;
import adactin.utils.ExtentManager;




public class BaseClass {

	public static WebDriver driver;
	protected static Properties prop;
	
	public BookingPage bookingPage;
	public SearchHotelPage searchHotelPage;
	public LoginPage loginPage;
	public BookedItineraryPage bookedItineraryPage;
	
	
	
	@BeforeClass(alwaysRun = true)
	@Parameters("Browser")
	public void setup(String brow) throws IOException {
		
		
		
		FileReader file = new FileReader(
				System.getProperty("user.dir") + "\\src\\test\\resources\\resources\\config.properties");

		
			 prop = new Properties();
			prop.load(file);
		
	

		switch (brow.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		default:
			throw new IllegalArgumentException("Invalid Browser Name: " + brow);
		}

		driver.get(prop.getProperty("URL"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		 
	}
	
	
	public static String get(String key) {
		if (prop == null) {
			throw new IllegalStateException("Properties not loaded. Make sure setup() is executed before accessing config.");
		}
		return prop.getProperty(key);
	}
	

	public static void takeScreenshot(String testName) {
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			// String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
			// Date(0));
			File destFile = new File("screenshots/" + testName + "_" + ".png");
			FileHandler.copy(screenshot, destFile);
			System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
		} catch (Exception e) {
			System.out.println("Failed to capture screenshot: " + e.getMessage());
		}
		

	}
	
	public void performLogin() {
	    loginPage = new LoginPage(driver);
	    String username = get("login.username");
	    String password = get("login.password");

	    loginPage.enterUsername(username);
	    loginPage.enterPassword(password);
	    loginPage.clickLogin();
	}
	
	@BeforeMethod(alwaysRun = true)
	public void setUpPages() {
		
		loginPage = new LoginPage(driver);
		searchHotelPage = new SearchHotelPage(driver);
		 bookingPage = new BookingPage(driver);
		bookedItineraryPage	=new BookedItineraryPage(driver); 
	}
	
	 @AfterMethod(alwaysRun = true)
	    public void screenshot(ITestResult result) {
	        if (result.getStatus() == ITestResult.FAILURE) {
	        	System.out.println("Test Failed: Capturing screenshot...");
	            String testName = result.getName();  // Get the failed test case name
	            Utility.captureScreenshot(driver, testName);
	        }
	    }

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
	@AfterTest
    public void tearDownTest() {
        ExtentManager.flush(); // Generate the report
       
    }


	

	
	
	
}
