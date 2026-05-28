package com.endava.ai.ui.reporting;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.attachment.FailureAttachmentHandler;

public final class UiScreenshotFailureHandler
        implements FailureAttachmentHandler {

    private static final String FAILURE_SCREENSHOT = "Failure Screenshot";
    private static final String FINAL_SCREENSHOT = "Final Screenshot";

    @Override
    public void onTestStart() {
        ScreenshotManager.resetForTest();
    }

    @Override
    public void onTestFailure() {
        if (!isFailureScreenshotEnabled()) return;
        ScreenshotManager.attachOnce(FAILURE_SCREENSHOT);
    }

    public void captureFinalStateIfEligible() {
        if (!isFinalStateCaptureEnabled()) return;

        try {
            ScreenshotManager.attachOnce(FINAL_SCREENSHOT);
        } catch (RuntimeException ignored) {
        }
    }

    private boolean isFailureScreenshotEnabled() {
        return isUiSessionConfigured() && isScreenshotsEnabled();
    }

    private boolean isFinalStateCaptureEnabled() {
        return isFailureScreenshotEnabled()
                && !isFailureOnlyMode()
                && getBoolean("ui.screenshots.capture.final.state", false);
    }

    private boolean isUiSessionConfigured() {
        String engine = ConfigManager.get("ui.engine", "");
        return engine != null && !engine.isBlank();
    }

    private boolean isScreenshotsEnabled() {
        return getBoolean("ui.screenshots.enabled", true);
    }

    private boolean isFailureOnlyMode() {
        return getBoolean("ui.screenshots.on.failure.only", false);
    }

    private boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(ConfigManager.get(key, Boolean.toString(defaultValue)));
    }
}
