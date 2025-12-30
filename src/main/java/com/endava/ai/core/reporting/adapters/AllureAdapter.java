package com.endava.ai.core.reporting.adapters;

import com.endava.ai.core.reporting.ReportLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StatusDetails;
import io.qameta.allure.model.StepResult;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public final class AllureAdapter implements ReportLogger {

    private static final AllureAdapter INSTANCE = new AllureAdapter();

    private final ThreadLocal<String> currentStepId = new ThreadLocal<>();
    private final ThreadLocal<StringBuilder> detailsBuffer =
            ThreadLocal.withInitial(StringBuilder::new);

    private AllureAdapter() {
    }

    public static AllureAdapter getInstance() {
        return INSTANCE;
    }

    @Override
    public void startTest(String testName, String description) {
    }

    @Override
    public void endTest(String status) {
        currentStepId.remove();
        detailsBuffer.remove();
    }

    @Override
    public void startStep(String stepTitle) {
        String uuid = UUID.randomUUID().toString();

        StepResult step = new StepResult()
                .setName(stepTitle)
                .setStatus(Status.PASSED);

        Allure.getLifecycle().startStep(uuid, step);
        currentStepId.set(uuid);
        detailsBuffer.get().setLength(0);
    }

    @Override
    public void logDetail(String detail) {
        if (detail != null) {
            detailsBuffer.get().append(detail).append("\n");
        }
    }

    @Override
    public void pass(String message) {
        String stepId = currentStepId.get();
        if (stepId == null) return;

        flushDetails();

        Allure.getLifecycle().updateStep(stepId,
                s -> s.setStatus(Status.PASSED));

        Allure.getLifecycle().stopStep(stepId);
        currentStepId.remove();
    }

    @Override
    public void fail(String message, String stacktraceAsText) {
        String stepId = currentStepId.get();
        if (stepId == null) return;

        flushDetails();

        Allure.getLifecycle().updateStep(stepId, s -> {
            s.setStatus(Status.FAILED);
            s.setStatusDetails(
                    new StatusDetails().setTrace(stacktraceAsText)
            );
        });

        Allure.getLifecycle().stopStep(stepId);
        currentStepId.remove();
    }

    @Override
    public void attachScreenshotBase64(String base64, String title) {
        if (base64 == null || base64.isBlank()) return;

        byte[] bytes = Base64.getDecoder().decode(base64);

        try (InputStream is = new ByteArrayInputStream(bytes)) {
            Allure.addAttachment(
                    title != null ? title : "Failure Screenshot",
                    "image/png",
                    is,
                    ".png"
            );
        } catch (Exception ignored) {
        }
    }

    @Override
    public void logCodeBlock(String content) {
        attachText(content);
    }

    @Override
    public void flush() {
    }

    private void flushDetails() {
        String content = detailsBuffer.get().toString().trim();
        if (content.isEmpty()) {
            return;
        }

        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            Allure.getLifecycle().addAttachment(
                    "Details",
                    "text/plain",
                    ".txt",
                    is
            );
        } catch (Exception ignored) {
        } finally {
            detailsBuffer.get().setLength(0);
        }
    }

    private void attachText(String content) {
        if (content == null) return;

        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            Allure.addAttachment("Payload", "application/json", is, ".json");
        } catch (Exception ignored) {
        }
    }
}
