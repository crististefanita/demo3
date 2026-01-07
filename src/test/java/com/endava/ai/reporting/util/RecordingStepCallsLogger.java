package com.endava.ai.reporting.util;

import com.endava.ai.core.reporting.ReportLogger;

import java.util.ArrayList;
import java.util.List;

public final class RecordingStepCallsLogger implements ReportLogger {

    public final List<String> steps = new ArrayList<>();
    public final List<String> passed = new ArrayList<>();
    public final List<String> failed = new ArrayList<>();

    @Override
    public void startStep(String title) {
        steps.add(title);
    }

    @Override
    public void pass(String message) {
        passed.add(message);
    }

    @Override
    public void fail(String message, String stacktraceAsText) {
        failed.add(message);
    }

    @Override public void startTest(String testName, String description) {}
    @Override public void endTest(String status) {}
    @Override public void logDetail(String detail) {}
    @Override public void attachScreenshotBase64(String base64, String title) {}
    @Override public void logCodeBlock(String content) {}
    @Override public void flush() {}
}