package com.endava.ai.reporting.util;

import com.endava.ai.core.reporting.ReportLogger;

import java.util.ArrayList;
import java.util.List;

public final class RecordingTestNamesLogger implements ReportLogger {

    public final List<String> startedTests = new ArrayList<>();

    private static final String FALLBACK_NAME = "Test";
    private final ThreadLocal<String> currentTest = new ThreadLocal<>();

    @Override
    public void ensureTestStarted(String testName, String description) {
        if (currentTest.get() != null) {
            if (testName != null && FALLBACK_NAME.equals(currentTest.get())) {
                int last = startedTests.lastIndexOf(FALLBACK_NAME);
                if (last >= 0) startedTests.set(last, testName);
                currentTest.set(testName);
            }
            return;
        }

        String name = testName != null ? testName : FALLBACK_NAME;
        currentTest.set(name);
        startedTests.add(name);
    }

    @Override
    public void startTest(String testName, String description) {
        ensureTestStarted(testName, description);
    }

    @Override
    public void endTest(String status) {
        currentTest.remove();
    }

    @Override public void startStep(String stepTitle) {}
    @Override public void logDetail(String detail) {}
    @Override public void pass(String message) {}
    @Override public void fail(String message, String stacktraceAsText) {}
    @Override public void attachScreenshotBase64(String base64, String title) {}
    @Override public void logCodeBlock(String content) {}
    @Override public void flush() {}
}
