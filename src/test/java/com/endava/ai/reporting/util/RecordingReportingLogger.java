package com.endava.ai.reporting.util;

import com.endava.ai.core.reporting.ReportLogger;

import java.util.*;

public class RecordingReportingLogger implements ReportLogger {

    public final List<String> infoMessages = new ArrayList<>();
    public final List<String> failMessages = new ArrayList<>();

    public final Map<String, List<String>> detailsPerTest = new LinkedHashMap<>();

    private String currentTest;

    @Override
    public void ensureTestStarted(String testName, String description) {
        currentTest = testName;
        detailsPerTest.putIfAbsent(testName, new ArrayList<>());
        System.out.println("Ensured test started: " + testName);
    }

    @Override
    public void startTest(String testName, String description) {
        currentTest = testName;
        detailsPerTest.putIfAbsent(testName, new ArrayList<>());
        System.out.println("Started test: " + testName);
    }

    @Override
    public void endTest(String status) {
        currentTest = null;
    }

    @Override
    public void startStep(String stepTitle) {
    }

    @Override
    public void logDetail(String detail) {
        infoMessages.add(detail);
        if (currentTest != null) {
            detailsPerTest.get(currentTest).add(detail);
        }
    }

    @Override
    public void pass(String message) {
    }

    @Override
    public void fail(String message, String stacktraceAsText) {
        failMessages.add(message);
    }

    @Override
    public void attachScreenshotBase64(String base64, String title) {
    }

    @Override
    public void logCodeBlock(String content) {
    }

    @Override
    public void flush() {
    }
}
