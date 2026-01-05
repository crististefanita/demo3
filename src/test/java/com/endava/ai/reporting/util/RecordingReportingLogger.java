package com.endava.ai.reporting.util;

import com.endava.ai.core.reporting.ReportLogger;

import java.util.*;

public final class RecordingReportingLogger implements ReportLogger {

    public final List<String> infoMessages = new ArrayList<>();
    public final List<String> failMessages = new ArrayList<>();
    public final Map<String, List<String>> detailsPerTest = new LinkedHashMap<>();
    public final java.util.concurrent.atomic.AtomicBoolean flushCalled =
            new java.util.concurrent.atomic.AtomicBoolean(false);

    private final ThreadLocal<String> currentTest = new ThreadLocal<>();

    @Override
    public void ensureTestStarted(String testName, String description) {
        if (currentTest.get() == null) {
            currentTest.set(testName);
            detailsPerTest.putIfAbsent(testName, new ArrayList<>());
        }
    }

    @Override
    public void startTest(String testName, String description) {
        ensureTestStarted(testName, description);
    }

    @Override
    public void endTest(String status) {
        currentTest.remove();
    }

    @Override
    public void startStep(String stepTitle) {}

    @Override
    public void logDetail(String detail) {
        infoMessages.add(detail);
        if (currentTest.get() != null) {
            detailsPerTest.get(currentTest.get()).add(detail);
        }
    }

    @Override
    public void pass(String message) {}

    @Override
    public void fail(String message, String stacktraceAsText) {
        failMessages.add(message);
    }

    @Override public void attachScreenshotBase64(String base64, String title) {}
    @Override public void logCodeBlock(String content) {}
    @Override public void flush() {
        flushCalled.set(true);
    }
}
