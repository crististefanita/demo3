package com.endava.ai.ui.reporting;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.attachment.FailureAttachmentHandler;

public final class UiScreenshotFailureHandler
        implements FailureAttachmentHandler {

    @Override
    public void onTestStart() {
        ScreenshotManager.resetForTest();
    }

    @Override
    public void onTestFailure() {
        if (!isUiEnabled()) return;
        ScreenshotManager.attachOnce("Failure Screenshot");
    }

    private boolean isUiEnabled() {
        String engine = ConfigManager.get("ui.engine", "");
        return engine != null && !engine.isBlank();
    }
}
