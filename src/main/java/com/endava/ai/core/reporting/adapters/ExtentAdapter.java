package com.endava.ai.core.reporting.adapters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.endava.ai.core.reporting.ReportLogger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.endava.ai.core.config.ConfigManager.require;

public final class ExtentAdapter implements ReportLogger {

    private static final ExtentAdapter INSTANCE = new ExtentAdapter();

    private final ExtentReports extent;
    private final ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();
    private final ThreadLocal<ExtentTest> currentStep = new ThreadLocal<>();

    private ExtentAdapter() {
        String reportsDir = require("reports.dir");
        boolean tsEnabled = getBoolean("reports.timestamp.enabled");
        String tsFormat = require("reports.timestamp.format");

        String fileName = tsEnabled
                ? "ExtentReport_" + new SimpleDateFormat(tsFormat).format(new Date()) + ".html"
                : "ExtentReport.html";

        Path reportPath = Paths.get(reportsDir, fileName);

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath.toString());
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("UI Test Report");
        spark.config().setReportName("UI Test Automation Framework");
        spark.config().setCss(
                ".card-title a span { color: #f2f2f2 !important; }" +
                        ".card-title a { color: #f2f2f2 !important; }" +
                        ".fa { color: #f2f2f2 !important; }"
        );

        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    public static ExtentAdapter getInstance() {
        return INSTANCE;
    }

    @Override
    public void startTest(String testName, String description) {
        currentTest.set(extent.createTest(testName, description == null ? "" : description));
        currentStep.remove();
    }

    @Override
    public void endTest(String status) {
        currentStep.remove();
        currentTest.remove();
    }

    @Override
    public void startStep(String stepTitle) {
        currentStep.set(requireTest().createNode(stepTitle));
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
        ExtentTest step = requireStep();
        step.fail(message);
        logCodeBlock(stacktraceAsText);
        requireTest().fail(message);
    }

    @Override
    public void attachScreenshotBase64(String base64, String title) {
        requireTest().addScreenCaptureFromBase64String(
                base64,
                title == null ? "Screenshot" : title
        );
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
            throw new IllegalStateException(
                    "No active test. TestListener must start the test before steps run."
            );
        }
        return t;
    }

    private ExtentTest requireStep() {
        ExtentTest s = currentStep.get();
        if (s == null) {
            throw new IllegalStateException(
                    "No active step. StepLogger must start a step before logging details."
            );
        }
        return s;
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(require(key));
    }
}
