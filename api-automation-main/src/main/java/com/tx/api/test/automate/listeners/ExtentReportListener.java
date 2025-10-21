package com.tx.api.test.automate.listeners;

import com.tx.api.test.automate.reports.ExtentLogger;
import com.tx.api.test.automate.reports.ExtentReport;
import com.tx.api.test.automate.utils.TestExecutionContextManager;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportListener implements ITestListener, ISuiteListener {
    private static final String MESSAGE = "Test - <b>";

    @Override
    public void onStart(ISuite suite) {
        ExtentReport.initExtentReport();
    }

    @Override
    public void onFinish(ISuite suite) {
        ExtentReport.flushExtentReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReport.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentLogger.pass(MESSAGE + TestExecutionContextManager.getTestExecutionContext().getTestName()  + "</b> is passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentLogger.logFailureDetails(MESSAGE + TestExecutionContextManager.getTestExecutionContext() + "</b> is failed ");
        ExtentLogger.logFailureDetails(result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentLogger.skip(MESSAGE + TestExecutionContextManager.getTestExecutionContext().getTestName() + "</b> is skipped");
    }
}
