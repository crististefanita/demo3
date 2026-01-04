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
    private static final String FALLBACK_NAME = "Test";

    private final ExtentReports extent;
    private final ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();
    private final ThreadLocal<ExtentTest> currentStep = new ThreadLocal<>();

    private ExtentAdapter() {
        String reportsDir = require("reports.dir");
        boolean tsEnabled = Boolean.parseBoolean(require("reports.timestamp.enabled"));
        String tsFormat = require("reports.timestamp.format");

        String fileName = tsEnabled
                ? "ExtentReport_" + new SimpleDateFormat(tsFormat).format(new Date()) + ".html"
                : "ExtentReport.html";

        Path reportPath = Paths.get(reportsDir, fileName);

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath.toString());
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Test Report");
        spark.config().setReportName("Automation Framework");
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
    public void ensureTestStarted(String testName, String description) {
        ExtentTest t = currentTest.get();

        if (t == null) {
            currentTest.set(
                    extent.createTest(
                            testName != null ? testName : FALLBACK_NAME,
                            description == null ? "" : description
                    )
            );
            return;
        }

        if (testName != null && FALLBACK_NAME.equals(t.getModel().getName())) {
            t.getModel().setName(testName);
            if (description != null) {
                t.getModel().setDescription(description);
            }
        }
    }

    @Override
    public void startTest(String testName, String description) {
        ensureTestStarted(testName, description);
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
        ExtentTest step;

        if (currentStep.get() == null) {
            step = requireTest().createNode("Test failure");
            currentStep.set(step);
        } else {
            step = currentStep.get();
        }

        step.fail(message);
        logCodeBlock(stacktraceAsText);

        currentStep.remove();
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
        if (t == null) throw new IllegalStateException("No active test");
        return t;
    }

    private ExtentTest requireStep() {
        ExtentTest s = currentStep.get();
        if (s == null) throw new IllegalStateException("No active step");
        return s;
    }
}
