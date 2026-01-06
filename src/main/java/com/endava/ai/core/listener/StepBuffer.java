package com.endava.ai.core.listener;

import com.endava.ai.core.reporting.ReportLogger;
import com.endava.ai.core.reporting.ReportingManager;
import java.util.ArrayList;
import java.util.List;

final class StepBuffer implements ReportLogger {

    private final List<Runnable> steps = new ArrayList<>();

    @Override
    public void startTest(String testName, String description) {
        steps.add(() -> ReportingManager.getLogger().startTest(testName, description));
    }

    @Override
    public void endTest(String status) {
        steps.add(() -> ReportingManager.getLogger().endTest(status));
    }

    @Override
    public void startStep(String stepTitle) {
        steps.add(() -> ReportingManager.getLogger().startStep(stepTitle));
    }

    @Override
    public void logDetail(String detail) {
        steps.add(() -> ReportingManager.getLogger().logDetail(detail));
    }

    @Override
    public void pass(String message) {
        steps.add(() -> ReportingManager.getLogger().pass(message));
    }

    @Override
    public void fail(String message, String stacktraceAsText) {
        steps.add(() -> ReportingManager.getLogger().fail(message, stacktraceAsText));
    }

    @Override
    public void attachScreenshotBase64(String base64, String title) {
        steps.add(() -> ReportingManager.getLogger().attachScreenshotBase64(base64, title));
    }

    @Override
    public void logCodeBlock(String content) {
        steps.add(() -> ReportingManager.getLogger().logCodeBlock(content));
    }

    @Override
    public void flush() {
        steps.forEach(Runnable::run);
        steps.clear();
    }

    boolean isEmpty() {
        return steps.isEmpty();
    }

    @SuppressWarnings("unused")
    void flushTo(ReportLogger logger) {
        steps.forEach(Runnable::run);
    }

    void clear() {
        steps.clear();
    }
}
