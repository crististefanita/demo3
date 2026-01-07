package com.endava.ai.reporting.util;

import com.endava.ai.core.reporting.ReportLogger;

public final class AllureReportLogger implements ReportLogger {

    private final FakeAllureLifecycle lifecycle;

    public AllureReportLogger(FakeAllureLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    @Override
    public void startTest(String testName, String description) {
        lifecycle.startTest(testName);
    }

    @Override
    public void endTest(String status) {
        lifecycle.stopTest(status);
    }

    @Override
    public void startStep(String stepTitle) {
        lifecycle.startStep(stepTitle);
    }

    @Override
    public void pass(String message) {
        lifecycle.stopStep(message);
    }

    @Override
    public void fail(String message, String stacktraceAsText) {
        lifecycle.fail(message);
    }

    // fixture mapping (IMPORTANT)
    public void startBeforeFixture(String name) {
        lifecycle.startFixture("before:" + name);
    }

    public void stopBeforeFixture(String name) {
        lifecycle.stopFixture("before:" + name);
    }

    public void startAfterFixture(String name) {
        lifecycle.startFixture("after:" + name);
    }

    public void stopAfterFixture(String name) {
        lifecycle.stopFixture("after:" + name);
    }

    @Override public void logDetail(String detail) {}
    @Override public void attachScreenshotBase64(String base64, String title) {}
    @Override public void logCodeBlock(String content) {}
    @Override public void flush() {}
}