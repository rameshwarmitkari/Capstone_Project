package adactin.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


import com.aventstack.extentreports.Status;

public class ExtentTestNGITestListener extends ExtentManager implements ITestListener {

    private int passedTests = 0;
    private int failedTests = 0;
    private int skippedTests = 0;
    private int totalTests = 0;

    @Override
    public void onTestStart(ITestResult result) {
        ExtentManager.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        passedTests++;
        totalTests++;
        ExtentManager.getTest().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failedTests++;
        totalTests++;
        ExtentManager.getTest().log(Status.FAIL, "Test failed: " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skippedTests++;
        totalTests++;
        ExtentManager.getTest().log(Status.SKIP, "Test skipped: " + result.getThrowable());
    }

    @Override
    public void onStart(ITestContext context) {
        ExtentManager.getInstance();
    }

    @Override
    public void onFinish(ITestContext context) {
        // Log the summary as a separate test in the report
    	System.out.println("===== Test Execution Summary =====");
    	System.out.println("Total Tests: " + totalTests);
    	System.out.println("Passed: " + passedTests);
    	System.out.println("Failed: " + failedTests);
    	System.out.println("Skipped: " + skippedTests);
    	ExtentManager.flush();
    }
}
