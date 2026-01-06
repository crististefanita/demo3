package com.endava.ai.core.reporting.adapters;

import com.endava.ai.core.reporting.ReportLogger;
import com.endava.ai.core.reporting.utils.ReportingEnginePolicy;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Base64;
public final class AllureAdapter implements ReportLogger {

    private static final AllureAdapter INSTANCE = new AllureAdapter();

    private final ThreadLocal<String> currentStepId = new ThreadLocal<>();
    private final ThreadLocal<List<Parameter>> stepParams =
            ThreadLocal.withInitial(ArrayList::new);

    private AllureAdapter() {}

    public static AllureAdapter getInstance() {
        return INSTANCE;
    }

    @Override
    public void startTest(String testName, String description) {
    }

    @Override
    public void endTest(String status) {
        if (isInactive()) return;
        clearContext();
    }

    @Override
    public void startStep(String stepTitle) {
        if (isInactive()) return;

        String stepId = beginStep(stepTitle);
        currentStepId.set(stepId);
        stepParams.get().clear();
    }

    @Override
    public void logDetail(String detail) {
        if (isInactive() || isBlank(detail)) return;
        stepParams.get().add(param("detail", detail));
    }

    @Override
    public void pass(String message) {
        if (isInactive()) return;
        finishCurrentStep(Status.PASSED);
    }

    @Override
    public void fail(String message, String stacktrace) {
        if (isInactive()) return;

        addErrorParam(message);
        finishCurrentStep(Status.FAILED);
        attachFailure(stacktrace);
    }

    @Override
    public void attachScreenshotBase64(String base64, String title) {
        if (isInactive() || isBlank(base64)) return;
        attachPng(titleOrDefault(title), decode(base64));
    }

    @Override
    public void logCodeBlock(String content) {
        if (isInactive() || content == null) return;
        attachText("Payload", "application/json", ".json", content);
    }

    @Override
    public void flush() {
    }

    /* ================= helpers ================= */

    private boolean isInactive() {
        return !ReportingEnginePolicy.isAllure();
    }

    private String beginStep(String title) {
        String stepId = UUID.randomUUID().toString();
        Allure.getLifecycle().startStep(
                stepId,
                new StepResult().setName(title).setStatus(Status.PASSED)
        );
        return stepId;
    }

    private void finishCurrentStep(Status status) {
        String stepId = currentStepId.get();
        if (stepId == null) return;

        Allure.getLifecycle().updateStep(stepId, s -> {
            if (!stepParams.get().isEmpty()) {
                s.setParameters(new ArrayList<>(stepParams.get()));
            }
            s.setStatus(status);
        });

        Allure.getLifecycle().stopStep(stepId);
        clearContext();
    }

    private void addErrorParam(String message) {
        if (!isBlank(message)) {
            stepParams.get().add(param("error", message));
        }
    }

    private void attachFailure(String stacktrace) {
        if (isBlank(stacktrace)) return;
        attachText("Step failure details", "text/plain", ".txt", stacktrace);
    }

    private static void attachText(String name, String mime, String ext, String content) {
        if (content == null) return;

        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            Allure.addAttachment(name, mime, is, ext);
        } catch (Exception ignored) {
        }
    }

    private void clearContext() {
        currentStepId.remove();
        stepParams.get().clear();
    }

    private static Parameter param(String name, String value) {
        return new Parameter().setName(name).setValue(value);
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    private static byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    private static String titleOrDefault(String title) {
        return title != null ? title : "Failure Screenshot";
    }

    private static void attachPng(String title, byte[] bytes) {
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            Allure.addAttachment(title, "image/png", is, ".png");
        } catch (Exception ignored) {
        }
    }
}