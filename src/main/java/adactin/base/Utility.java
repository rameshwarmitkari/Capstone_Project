package adactin.base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class Utility {
	
	// Method to capture screenshot
    public static void captureScreenshot(WebDriver driver, String testName) {
        // Create a timestamp to avoid overwriting screenshots
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Create a screenshot file name
        String screenshotName = testName + "_" + timestamp + ".png";

        // Define the path where the screenshot will be saved
        String path = "F:\\Software\\AdactinCapstone\\Screenshot" + File.separator + screenshotName;

        // Capture the screenshot
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Copy the screenshot to the specified location
        try {
            FileUtils.copyFile(screenshotFile, new File(path));
            System.out.println("Screenshot saved at: " + path);
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }

}
