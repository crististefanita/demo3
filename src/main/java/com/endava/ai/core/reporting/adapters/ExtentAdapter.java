package com.endava.ai.core.reporting.adapters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.ReportLogger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Extent adapter MUST:
 * - use a singleton ExtentReports instance
 * - instantiate ExtentReports exactly once per JVM run
 * - use ExtentSparkReporter with Theme.DARK
 * - apply CSS overrides exactly
 * - render stacktraces exclusively via MarkupHelper.createCodeBlock()
 * - never log raw HTML or Throwable objects directly
 * - attach screenshots at test level only
 */
public final class ExtentAdapter implements ReportLogger {
    private static final ExtentAdapter INSTANCE = new ExtentAdapter();

    private final ExtentReports extent;
    private final ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();
    private final ThreadLocal<ExtentTest> currentStep = new ThreadLocal<>();

    private ExtentAdapter() {
        ConfigManager.load();

        String reportsDir = ConfigManager.require("reports.dir");
        boolean tsEnabled = ConfigManager.getBoolean("reports.timestamp.enabled");
        String tsFormat = ConfigManager.require("reports.timestamp.format");

        String fileName;
        if (tsEnabled) {
            String ts = new SimpleDateFormat(tsFormat).format(new Date());
            fileName = "ExtentReport_" + ts + ".html";
        } else {
            fileName = "ExtentReport.html";
        }

        Path reportPath = Paths.get(reportsDir, fileName);
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath.toString());
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("UI Test Report");
        spark.config().setReportName("UI Test Automation Framework");

        // Apply CSS overrides exactly (as required).
        String css = ""
                + ".card-title a span { color: #f2f2f2 !important; }\n"
                + ".card-title a { color: #f2f2f2 !important; }\n"
                + ".fa { color: #f2f2f2 !important; }\n";
        spark.config().setCss(css);

        this.extent = new ExtentReports();
        this.extent.attachReporter(spark);
    }

    public static ExtentAdapter getInstance() {
        return INSTANCE;
    }

    @Override
    public void startTest(String testName, String description) {
        ExtentTest t = extent.createTest(testName, description == null ? "" : description);
        currentTest.set(t);
        currentStep.remove();
    }

    @Override
    public void endTest(String status) {
        // Extent status is inferred from logged events; keep API parity.
        currentStep.remove();
        currentTest.remove();
    }

    @Override
    public void startStep(String stepTitle) {
        ExtentTest test = requireTest();
        ExtentTest step = test.createNode(stepTitle);
        currentStep.set(step);
    }

    @Override
    public void logDetail(String detail) {
        requireStep().info(detail);
    }

    @Override
    public void pass(String message) {
        requireStep().pass(message);
    }

    @Override
    public void fail(String message, String stacktraceAsText) {
        // Do not accept Throwable; stacktrace is text.
        ExtentTest step = requireStep();
        step.fail(message);
        logCodeBlock(stacktraceAsText); // stacktrace in step only
        // Fail the corresponding test node too, but do NOT place stacktrace in test node.
        requireTest().fail(message);
    }

    @Override
    public void attachScreenshotBase64(String base64, String title) {
        requireTest().addScreenCaptureFromBase64String(base64, title == null ? "Screenshot" : title);
    }

    @Override
    public void logCodeBlock(String content) {
        requireStep().info(MarkupHelper.createCodeBlock(content == null ? "" : content));
    }

    @Override
    public void flush() {
        extent.flush();
    }

    private ExtentTest requireTest() {
        ExtentTest t = currentTest.get();
        if (t == null) {
            throw new IllegalStateException("No active test. TestListener must start the test before steps run.");
        }
        return t;
    }

    private ExtentTest requireStep() {
        ExtentTest s = currentStep.get();
        if (s == null) {
            throw new IllegalStateException("No active step. StepLogger must start a step before logging details.");
        }
        return s;
    }
}
