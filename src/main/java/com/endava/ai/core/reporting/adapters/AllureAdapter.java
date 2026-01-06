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

    private final ThreadLocal<Deque<String>> stepStack =
            ThreadLocal.withInitial(ArrayDeque::new);
    private final ThreadLocal<Deque<List<Parameter>>> paramsStack =
            ThreadLocal.withInitial(ArrayDeque::new);

    private AllureAdapter() {}

    public static AllureAdapter getInstance() {
        return INSTANCE;
    }

    @Override
    public void startTest(String testName, String description) {
        clearContext();
    }

    @Override
    public void endTest(String status) {
        if (isInactive()) return;
        clearContext();
    }

    @Override
    public void startStep(String stepTitle) {
        if (isInactive()) return;

        String parent = stepStack.get().peekLast();
        String stepId = beginStep(stepTitle, parent);
        stepStack.get().addLast(stepId);
        paramsStack.get().addLast(new ArrayList<>());
    }

    @Override
    public void logDetail(String detail) {
        if (isInactive() || isBlank(detail)) return;
        List<Parameter> p = paramsStack.get().peekLast();
        if (p != null) p.add(param("detail", detail));
    }

    @Override
    public void pass(String message) {
        if (isInactive()) return;
        finishCurrentStep(Status.PASSED);
    }

    @Override
    public void fail(String message, String stacktrace) {
        if (isInactive()) return;

        List<Parameter> p = paramsStack.get().peekLast();
        if (p != null && !isBlank(message)) p.add(param("error", message));

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

    private boolean isInactive() {
        return !ReportingEnginePolicy.isAllure();
    }

    private String beginStep(String title, String parentStepId) {
        String stepId = UUID.randomUUID().toString();
        StepResult sr = new StepResult().setName(title).setStatus(Status.PASSED);

        if (parentStepId != null) {
            Allure.getLifecycle().startStep(parentStepId, stepId, sr);
        } else {
            Allure.getLifecycle().startStep(stepId, sr);
        }

        return stepId;
    }

    private void finishCurrentStep(Status status) {
        String stepId = stepStack.get().pollLast();
        List<Parameter> params = paramsStack.get().pollLast();
        if (stepId == null) return;

        Allure.getLifecycle().updateStep(stepId, s -> {
            if (params != null && !params.isEmpty()) {
                s.setParameters(new ArrayList<>(params));
            }
            s.setStatus(status);
        });

        Allure.getLifecycle().stopStep(stepId);
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
        stepStack.get().clear();
        paramsStack.get().clear();
        stepStack.remove();
        paramsStack.remove();
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
