package com.endava.ai.core.reporting.adapters.allure;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public final class AllureReporter {

    public String startStep(String title, String parentStepId) {
        String stepId = UUID.randomUUID().toString();
        StepResult result = new StepResult()
                .setName(title)
                .setStatus(Status.PASSED);

        if (parentStepId != null) {
            Allure.getLifecycle().startStep(parentStepId, stepId, result);
        } else {
            Allure.getLifecycle().startStep(stepId, result);
        }

        return stepId;
    }

    public void updateAndStopStep(String stepId, Status status, List<Parameter> params) {
        Allure.getLifecycle().updateStep(stepId, step -> {
            if (params != null && !params.isEmpty()) {
                step.setParameters(params);
            }
            step.setStatus(status);
        });
        Allure.getLifecycle().stopStep(stepId);
    }

    public void attachText(String name, String mime, String ext, String content) {
        if (content == null) return;
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            Allure.addAttachment(name, mime, is, ext);
        } catch (Exception ignored) {
        }
    }

    public void attachPng(String title, byte[] bytes) {
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            Allure.addAttachment(title, "image/png", is, ".png");
        } catch (Exception ignored) {
        }
    }
}
