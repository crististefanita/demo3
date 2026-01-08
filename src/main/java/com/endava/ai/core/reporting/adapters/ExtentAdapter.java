package com.endava.ai.core.reporting.adapters;

import com.aventstack.extentreports.ExtentTest;
import com.endava.ai.core.reporting.ReportLogger;
import com.endava.ai.core.reporting.adapters.extent.ExtentReporter;
import com.endava.ai.core.reporting.adapters.extent.ExtentTestContext;

import java.util.ArrayDeque;
import java.util.Deque;

public final class ExtentAdapter implements ReportLogger {

    private static final ExtentAdapter INSTANCE = new ExtentAdapter();

    private final ExtentReporter reporter = new ExtentReporter();
    private final ExtentTestContext testContext = new ExtentTestContext(reporter);

    private final ThreadLocal<Deque<ExtentTest>> stepStack =
            ThreadLocal.withInitial(ArrayDeque::new);

    private ExtentAdapter() {
    }

    public static ExtentAdapter getInstance() {
        return INSTANCE;
    }

    @Override
    public void ensureTestStarted(String testName, String description) {
        testContext.start(testName, description);
    }

    @Override
    public void startTest(String testName, String description) {
        ensureTestStarted(testName, description);
        clearSteps();
    }

    @Override
    public void endTest(String status) {
        ExtentTest test = testContext.get();

        if (test != null && "FAIL".equalsIgnoreCase(status)) {
            test.getModel().setStatus(com.aventstack.extentreports.Status.FAIL);
        }

        clearSteps();
        testContext.end();
    }

    @Override
    public void startStep(String title) {
        ExtentTest test = testContext.get();
        if (test == null) return;

        ExtentTest parent = stepStack.get().peekLast();
        ExtentTest node = (parent != null ? parent : test).createNode(title);
        stepStack.get().addLast(node);
    }

    @Override
    public void logDetail(String detail) {
        getActiveNode().info(detail);
    }

    @Override
    public void pass(String message) {
        ExtentTest test = testContext.get();
        if (test == null) return;

        if (message != null && !message.isEmpty()) {
            getActiveNode().pass(message);
        }

        popStepIfNeeded();
    }

    @Override
    public void fail(String message, String stacktraceAsText) {
        ExtentTest test = testContext.get();
        if (test == null) return;

        reporter.fail(getActiveNode(), message, stacktraceAsText);
        popStepIfNeeded();
    }

    @Override
    public void attachScreenshotBase64(String base64, String title) {
        ExtentTest test = testContext.get();
        if (test == null) return;

        reporter.attachScreenshot(test, base64, title);
    }

    @Override
    public void logCodeBlock(String content) {
        ExtentTest test = testContext.get();
        if (test == null) return;

        reporter.logCodeBlock(getActiveNode(), content);
    }

    @Override
    public void flush() {
        reporter.flush();
    }

    private ExtentTest getActiveNode() {
        Deque<ExtentTest> stack = stepStack.get();
        ExtentTest node = stack.peekLast();
        return node != null ? node : testContext.get();
    }

    private void popStepIfNeeded() {
        Deque<ExtentTest> stack = stepStack.get();
        if (!stack.isEmpty()) {
            stack.pollLast();
        }
    }

    private void clearSteps() {
        Deque<ExtentTest> stack = stepStack.get();
        stack.clear();
        stepStack.remove();
    }
}
