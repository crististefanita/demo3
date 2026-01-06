
package com.endava.ai.core.reporting;

import java.util.Arrays;
import java.util.List;

public final class CompositeReportLogger implements ReportLogger {

    private final List<ReportLogger> delegates;

    public CompositeReportLogger(ReportLogger... delegates) {
        this.delegates = Arrays.asList(delegates);
    }

    @Override
    public void startTest(String testName, String description) {
        delegates.forEach(d -> safe(() -> d.startTest(testName, description)));
    }

    @Override
    public void endTest(String status) {
        delegates.forEach(d -> safe(() -> d.endTest(status)));
    }

    @Override
    public void startStep(String stepTitle) {
        delegates.forEach(d -> safe(() -> d.startStep(stepTitle)));
    }

    @Override
    public void logDetail(String detail) {
        delegates.forEach(d -> safe(() -> d.logDetail(detail)));
    }

    @Override
    public void pass(String message) {
        delegates.forEach(d -> safe(() -> d.pass(message)));
    }

    @Override
    public void fail(String message, String stacktraceAsText) {
        delegates.forEach(d -> safe(() -> d.fail(message, stacktraceAsText)));
    }

    @Override
    public void attachScreenshotBase64(String base64, String title) {
        delegates.forEach(d -> safe(() -> d.attachScreenshotBase64(base64, title)));
    }

    @Override
    public void logCodeBlock(String content) {
        delegates.forEach(d -> safe(() -> d.logCodeBlock(content)));
    }

    @Override
    public void flush() {
        delegates.forEach(d -> safe(d::flush));
    }

    private static void safe(Runnable r) {
        try {
            r.run();
        } catch (RuntimeException ignored) {
        }
    }
}
