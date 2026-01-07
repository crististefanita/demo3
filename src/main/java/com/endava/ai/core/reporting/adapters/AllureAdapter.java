package com.endava.ai.core.reporting.adapters;

import com.endava.ai.core.reporting.ReportLogger;
import com.endava.ai.core.reporting.internal.ReportingEngine;
import com.endava.ai.core.reporting.adapters.allure.AllureReporter;
import com.endava.ai.core.reporting.adapters.allure.AllureStepContext;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.Status;

import java.util.Base64;

public final class AllureAdapter implements ReportLogger {

    private static final AllureAdapter INSTANCE = new AllureAdapter();

    private final AllureReporter reporter = new AllureReporter();
    private final AllureStepContext stepContext = new AllureStepContext(reporter);

    private AllureAdapter() {}

    public static AllureAdapter getInstance() {
        return INSTANCE;
    }

    @Override
    public void startTest(String testName, String description) {
        stepContext.clear();
    }

    @Override
    public void endTest(String status) {
        if (isInactive()) return;
        stepContext.clear();
    }

    @Override
    public void startStep(String title) {
        if (isInactive()) return;
        stepContext.startStep(title);
    }

    @Override
    public void logDetail(String detail) {
        if (isInactive() || isBlank(detail)) return;
        stepContext.addParam(param("detail", detail));
    }

    @Override
    public void pass(String message) {
        if (isInactive()) return;
        stepContext.finishStep(Status.PASSED);
    }

    @Override
    public void fail(String message, String stacktrace) {
        if (isInactive()) return;

        if (!isBlank(message)) {
            stepContext.addParam(param("error", message));
        }

        stepContext.finishStep(Status.FAILED);

        if (!isBlank(stacktrace)) {
            reporter.attachText(
                    "Step failure details",
                    "text/plain",
                    ".txt",
                    stacktrace
            );
        }
    }

    @Override
    public void attachScreenshotBase64(String base64, String title) {
        if (isInactive() || isBlank(base64)) return;
        reporter.attachPng(
                title != null ? title : "Failure Screenshot",
                Base64.getDecoder().decode(base64)
        );
    }

    @Override
    public void logCodeBlock(String content) {
        if (isInactive() || content == null) return;
        reporter.attachText("Payload", "application/json", ".json", content);
    }

    @Override
    public void flush() {
    }

    private boolean isInactive() {
        return !ReportingEngine.current().isAllure();
    }

    private static Parameter param(String name, String value) {
        return new Parameter().setName(name).setValue(value);
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
