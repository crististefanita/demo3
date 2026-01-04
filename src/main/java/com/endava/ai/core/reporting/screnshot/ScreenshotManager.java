package com.endava.ai.core.reporting.screnshot;

import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.ui.core.DriverManager;

import java.util.concurrent.atomic.AtomicBoolean;

public class ScreenshotManager {
    private static final ThreadLocal<AtomicBoolean> taken =
            ThreadLocal.withInitial(() -> new AtomicBoolean(false));

    private ScreenshotManager() {
    }

    public static void resetForTest() {
        taken.get().set(false);
    }

    public static void attachOnce(String label) {
        if (!taken.get().getAndSet(true)
                && DriverManager.hasActiveEngine()) {

            String base64 = DriverManager.getEngine()
                    .captureScreenshotAsBase64();

            ReportingManager.getLogger()
                    .attachScreenshotBase64(base64, label);
        }
    }
}
