package com.automationExercise.listeners;

import com.automationExercise.FileUtils;
import com.automationExercise.drivers.UITest;
import com.automationExercise.drivers.WebDriverProvider;
import com.automationExercise.media.ScreenRecordManager;
import com.automationExercise.media.ScreenshotsManager;
import com.automationExercise.report.AllureAttachmentManager;
import com.automationExercise.report.AllureConstants;
import com.automationExercise.report.AllureEnvironmentManager;
import com.automationExercise.report.AllureReportGenerator;
import com.automationExercise.utils.logsmanager.LogsManager;
import com.automationExercise.utils.readers.PropertyReader;
import com.automationExercise.validations.SoftAssertion;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.util.List;

public class TestNGListeners implements ITestListener, IExecutionListener, IInvokedMethodListener {

    @Override
    public void onExecutionStart() {
        LogsManager.info("Test Execution started");

        // Logs whether the suite is running in Parallel or Sequentially at boot time
        logParallelExecutionSettings();

        cleanTestOutputDirectories();
        LogsManager.info("Directories cleaned");
        createTestOutputDirectories();
        LogsManager.info("Directories created");
        PropertyReader.loadProperties();
        LogsManager.info("Properties loaded");
        AllureEnvironmentManager.setEnvironmentVariables();
        LogsManager.info("Allure environment set");
    }

    /**
     * Extracts and logs whether the framework is running in Parallel or Sequentially.
     */
    private void logParallelExecutionSettings() {
        try {
            XmlSuite rootSuite = Reporter.getCurrentTestResult().getTestContext().getSuite().getXmlSuite();
            List<XmlSuite> childSuites = rootSuite.getChildSuites();

            if (childSuites == null || childSuites.isEmpty()) {
                printExecutionDetails(rootSuite);
            } else {
                for (XmlSuite suite : childSuites) {
                    printExecutionDetails(suite);
                }
            }
        } catch (NullPointerException | UnsupportedOperationException e) {
            // Fallback strategy checking Maven properties directly if TestNG contexts are waking up slowly
            String mavenParallelProp = System.getProperty("parallel.mode", "none");
            String mavenThreadsProp = System.getProperty("thread.count", "1");

            LogsManager.info("==========================================================");
            if ("none".equalsIgnoreCase(mavenParallelProp)) {
                LogsManager.info(" Execution Mode: SEQUENTIAL (Sequential Execution)");
            } else {
                LogsManager.info(" Execution Mode: PARALLEL (By " + mavenParallelProp.toUpperCase() + ")");
                LogsManager.info(" Active Threads  : " + mavenThreadsProp);
            }
            LogsManager.info("==========================================================");
        }
    }

    private void printExecutionDetails(XmlSuite suite) {
        XmlSuite.ParallelMode parallelMode = suite.getParallel();
        int threadCount = suite.getThreadCount();

        LogsManager.info("==========================================================");
        LogsManager.info(" Target Execution Suite: " + suite.getName());

        // FIXED: Removed the invalid .FALSE check. Checking for null or NONE is perfect.
        if (parallelMode == null || parallelMode == XmlSuite.ParallelMode.NONE) {
            LogsManager.info(" Execution Mode        : SEQUENTIAL (Sequential Run)");
        } else {
            LogsManager.info(" Execution Mode        : PARALLEL (By " + parallelMode.toString().toUpperCase() + ")");
            LogsManager.info(" Active Threads        : " + threadCount);
        }
        LogsManager.info("==========================================================");
    }

    @Override
    public void onExecutionFinish() {
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateReports(false);
        AllureReportGenerator.generateReports(true);
        AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
        LogsManager.info("Test Execution Finished");
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            if (testResult.getInstance() instanceof UITest) {
                ScreenRecordManager.startRecording();
            }
            LogsManager.info("Test Case " + testResult.getName() + " started");
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        WebDriver driver = null;
        if (method.isTestMethod()) {
            if (testResult.getInstance().getClass().isAnnotationPresent(UITest.class)) {
                ScreenRecordManager.stopRecording(testResult.getName());
                if (testResult.getInstance() instanceof WebDriverProvider provider)
                    driver = provider.getWebDriver();
                switch (testResult.getStatus()) {
                    case ITestResult.SUCCESS ->
                            ScreenshotsManager.takeFullPageScreenshot(driver, "passed-" + testResult.getName());
                    case ITestResult.FAILURE ->
                            ScreenshotsManager.takeFullPageScreenshot(driver, "failed-" + testResult.getName());
                    case ITestResult.SKIP ->
                            ScreenshotsManager.takeFullPageScreenshot(driver, "skipped-" + testResult.getName());
                }
                AllureAttachmentManager.attachRecords(testResult.getName());
            }

            SoftAssertion.assertAll(testResult);
            AllureAttachmentManager.attachLogs();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogsManager.info("Test Case " + result.getName() + " passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogsManager.info("Test Case " + result.getName() + " failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogsManager.info("Test Case " + result.getName() + " skipped");
    }

    private void cleanTestOutputDirectories() {
        FileUtils.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
        FileUtils.cleanDirectory(new File(ScreenshotsManager.SCREENSHOTS_PATH));
        FileUtils.cleanDirectory(new File(ScreenRecordManager.RECORDINGS_PATH));
        FileUtils.cleanDirectory(new File("src/test/resources/downloads/"));
        FileUtils.forceDelete(new File(LogsManager.LOGS_PATH + "logs.log"));
    }

    private void createTestOutputDirectories() {
        FileUtils.createDirectory(ScreenshotsManager.SCREENSHOTS_PATH);
        FileUtils.createDirectory(ScreenRecordManager.RECORDINGS_PATH);
        FileUtils.createDirectory("src/test/resources/downloads/");
    }
}