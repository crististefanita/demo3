package com.endava.ai.ui.reporting;

import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.ui.core.DriverManager;

public final class ScreenshotManager {

    private static final ThreadLocal<Boolean> taken = ThreadLocal.withInitial(() -> false);

    private ScreenshotManager() {
    }

    @SuppressWarnings("unused")
    public static void resetForTest() {
        taken.set(false);
    }

    public static void attachOnce(String label) {
        if (!taken.get() && DriverManager.hasActiveEngine()) {

            taken.set(true);

            String base64 = DriverManager.getEngine()
                    .captureScreenshotAsBase64();

            ReportingManager.getLogger()
                    .attachScreenshotBase64(base64, label);
        }
    }

    @SuppressWarnings("unused")
    static void clear() {
        taken.remove();
    }
}
