package com.endava.ai.core.reporting.adapters;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.endava.ai.core.reporting.ReportLogger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;

import static com.endava.ai.core.config.ConfigManager.require;

public final class ExtentAdapter implements ReportLogger {

    private static final ExtentAdapter INSTANCE = new ExtentAdapter();
    private static final String FALLBACK_NAME = "Test";

    private final ExtentReports extent;
    private final ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();
    private final ThreadLocal<Deque<ExtentTest>> stepStack =
            ThreadLocal.withInitial(ArrayDeque::new);

    private ExtentAdapter() {
        this.extent = createExtent();
    }

    public static ExtentAdapter getInstance() {
        return INSTANCE;
    }

    @Override
    public void ensureTestStarted(String testName, String description) {
        ExtentTest test = currentTest.get();

        if (test == null) {
            currentTest.set(createTest(testName, description));
            return;
        }

        renameFallbackTestIfNeeded(test, testName, description);
    }

    @Override
    public void startTest(String testName, String description) {
        ensureTestStarted(testName, description);
        stepStack.get().clear();
    }

    @Override
    public void endTest(String status) {
        stepStack.get().clear();
        stepStack.remove();
        currentTest.remove();
    }

    @Override
    public void startStep(String stepTitle) {
        ExtentTest parent = stepStack.get().peekLast();
        ExtentTest node = (parent != null ? parent : requireTest()).createNode(stepTitle);
        stepStack.get().addLast(node);
    }

    @Override
    public void logDetail(String detail) {
        getActiveNode().info(detail);
    }

    @Override
    public void pass(String message) {
        ExtentTest node = getActiveNode();
        node.pass(message);
        popIfStep();
    }

    @Override
    public void fail(String message, String stacktraceAsText) {
        ensureTestStartedIfNeeded();
        ExtentTest node = getActiveNode();
        node.fail(message);

        if (stacktraceAsText != null) {
            node.fail(MarkupHelper.createCodeBlock(stacktraceAsText));
        }

        popIfStep();
    }

    @Override
    public void attachScreenshotBase64(String base64, String title) {
        requireTest().addScreenCaptureFromBase64String(
                base64,
                title != null ? title : "Screenshot"
        );
    }

    @Override
    public void logCodeBlock(String content) {
        getActiveNode().info(
                MarkupHelper.createCodeBlock(content != null ? content : "")
        );
    }

    @Override
    public void flush() {
        extent.flush();
    }

    private void popIfStep() {
        Deque<ExtentTest> st = stepStack.get();
        if (!st.isEmpty()) st.pollLast();
    }

    private ExtentReports createExtent() {
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

        ExtentReports reports = new ExtentReports();
        reports.attachReporter(spark);
        return reports;
    }

    private ExtentTest createTest(String testName, String description) {
        return extent.createTest(
                testName != null ? testName : FALLBACK_NAME,
                description != null ? description : ""
        );
    }

    private static void renameFallbackTestIfNeeded(
            ExtentTest test,
            String testName,
            String description
    ) {
        if (testName == null) return;
        if (!FALLBACK_NAME.equals(test.getModel().getName())) return;

        test.getModel().setName(testName);
        if (description != null) {
            test.getModel().setDescription(description);
        }
    }

    private ExtentTest requireTest() {
        ExtentTest test = currentTest.get();
        if (test == null) {
            throw new IllegalStateException("No active test");
        }
        return test;
    }

    private ExtentTest getActiveNode() {
        Deque<ExtentTest> st = stepStack.get();
        ExtentTest node = st.peekLast();
        return node != null ? node : requireTest();
    }

    private void ensureTestStartedIfNeeded() {
        if (currentTest.get() == null) {
            currentTest.set(createTest(FALLBACK_NAME, null));
        }
    }
}
