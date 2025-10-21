package com.tx.api.test.automate.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.tx.api.test.automate.exception.ReportInitializationException;
import com.tx.api.test.automate.utils.TestExecutionContextManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class ExtentReport {

  private static final ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter("C:\\reports");
  private static ExtentReports extentReports;

  /**
   * This method is to initialize the Extent Report
   */
  public static void initExtentReport() {
    try {
      log.info("$$$$$$$$$$$ initExtentReport $$$$$$$$$$$$");
      if (Objects.isNull(extentReports)) {
        log.info("$$$$$$$$$$$ has reports $$$$$$$$$$$$");
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        InetAddress ip = InetAddress.getLocalHost();
        String hostname = ip.getHostName();
        extentReports.setSystemInfo("Host Name", hostname);
        extentReports.setSystemInfo("Environment", "API Automation - Test");
        extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
        extentSparkReporter.config().setDocumentTitle("HTML Report");
        extentSparkReporter.config().setReportName("API Automation Test");
        extentSparkReporter.config().setTheme(Theme.DARK);
      }
    } catch (Exception e) {
      throw new ReportInitializationException("Failed to initialize extent report - " + e.getMessage());
    }
  }

  public static void createTest(String testCaseName) {
    ExtentManager.setExtentTest(extentReports.createTest(testCaseName));
  }

  public static void flushExtentReport() {
    if (Objects.nonNull(extentReports)) {
      extentReports.flush();
    }
    ExtentManager.unload();
    TestExecutionContextManager.unload();
    try {
      log.info("$$$$$$$$$$$ Opening Folder $$$$$$$$$$$$");
      Desktop.getDesktop().browse(new File("C:\\reports").toURI());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}