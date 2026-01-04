package com.endava.ai.reporting.util;

import com.endava.ai.core.reporting.ReportLogger;

public final class FakeReportingLogger implements ReportLogger {

    public boolean failCalled = false;
    public boolean endFailCalled = false;
    public int failCount = 0;

    @Override
    public void ensureTestStarted(String testName, String description) {
        // no-op
    }

    @Override
    public void startTest(String testName, String description) {
        // no-op
    }

    @Override
    public void endTest(String status) {
        if ("FAIL".equals(status)) {
            endFailCalled = true;
        }
    }

    @Override
    public void startStep(String stepTitle) {
        // no-op
    }

    @Override
    public void logDetail(String detail) {
        // no-op
    }

    @Override
    public void pass(String message) {
        // no-op
    }

    @Override
    public void fail(String message, String stacktraceAsText) {
        failCalled = true;
        failCount++;
    }

    @Override
    public void attachScreenshotBase64(String base64, String title) {
        // no-op
    }

    @Override
    public void logCodeBlock(String content) {
        // no-op
    }

    @Override
    public void flush() {
        // no-op
    }
}