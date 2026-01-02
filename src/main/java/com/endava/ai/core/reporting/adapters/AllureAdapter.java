package com.endava.ai.core.reporting.adapters;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.ReportLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public final class AllureAdapter implements ReportLogger {

    private static final AllureAdapter INSTANCE = new AllureAdapter();

    private final ThreadLocal<String> currentStepId = new ThreadLocal<>();
    private final ThreadLocal<List<Parameter>> stepParams =
            ThreadLocal.withInitial(ArrayList::new);

    private AllureAdapter() {}

    public static AllureAdapter getInstance() {
        return INSTANCE;
    }

    private boolean disabled() {
        return !"allure".equalsIgnoreCase(
                ConfigManager.get("reporting.engine", "")
        );
    }

    @Override
    public void startTest(String testName, String description) {
    }

    @Override
    public void endTest(String status) {
        if (disabled()) return;
        clearStepContext();
    }

    @Override
    public void startStep(String stepTitle) {
        if (disabled()) return;

        String stepId = UUID.randomUUID().toString();
        StepResult step = new StepResult()
                .setName(stepTitle)
                .setStatus(Status.PASSED);

        Allure.getLifecycle().startStep(stepId, step);
        currentStepId.set(stepId);
        stepParams.get().clear();
    }

    @Override
    public void logDetail(String detail) {
        if (disabled() || detail == null || detail.isBlank()) return;
        stepParams.get().add(
                new Parameter().setName("detail").setValue(detail)
        );
    }

    @Override
    public void pass(String message) {
        if (disabled()) return;

        String stepId = currentStepId.get();
        if (stepId == null) return;

        List<Parameter> params = stepParams.get();
        Allure.getLifecycle().updateStep(stepId, s -> {
            if (!params.isEmpty()) {
                s.setParameters(new ArrayList<>(params));
            }
            s.setStatus(Status.PASSED);
        });

        Allure.getLifecycle().stopStep(stepId);
        clearStepContext();
    }

    @Override
    public void fail(String message, String stacktraceAsText) {
        if (disabled()) return;

        String stepId = currentStepId.get();
        if (stepId == null) return;

        List<Parameter> params = stepParams.get();
        if (message != null && !message.isBlank()) {
            params.add(new Parameter().setName("error").setValue(message));
        }

        Allure.getLifecycle().updateStep(stepId, s -> {
            if (!params.isEmpty()) {
                s.setParameters(new ArrayList<>(params));
            }
            s.setStatus(Status.FAILED);
        });

        attachFailureDetails(params, stacktraceAsText);
        Allure.getLifecycle().stopStep(stepId);
        clearStepContext();
    }

    @Override
    public void attachScreenshotBase64(String base64, String title) {
        if (disabled() || base64 == null || base64.isBlank()) return;

        byte[] bytes = Base64.getDecoder().decode(base64);
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            Allure.addAttachment(
                    title != null ? title : "Failure Screenshot",
                    "image/png",
                    is,
                    ".png"
            );
        } catch (Exception ignored) {}
    }

    @Override
    public void logCodeBlock(String content) {
        if (disabled() || content == null) return;
        attachText("Payload", "application/json", ".json", content);
    }

    @Override
    public void flush() {
    }

    private void attachFailureDetails(List<Parameter> params, String stacktrace) {
        StringBuilder sb = new StringBuilder();
        for (Parameter p : params) {
            sb.append(p.getName()).append('=').append(p.getValue()).append('\n');
        }
        if (stacktrace != null && !stacktrace.isBlank()) {
            sb.append("\nSTACKTRACE:\n").append(stacktrace);
        }
        attachText("Step failure details", "text/plain", ".txt", sb.toString());
    }

    private void attachText(String name, String mime, String ext, String content) {
        if (content == null) return;

        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            Allure.addAttachment(name, mime, is, ext);
        } catch (Exception ignored) {}
    }

    private void clearStepContext() {
        currentStepId.remove();
        stepParams.get().clear();
    }
}
