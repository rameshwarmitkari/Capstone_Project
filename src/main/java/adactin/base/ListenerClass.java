package adactin.base;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerClass implements ITestListener {

	    @Override
	    public void onTestFailure(ITestResult result) {
	        System.out.println("Test Failed: " + result.getName());
	        BaseClass.takeScreenshot(result.getName());
	    }
	}

