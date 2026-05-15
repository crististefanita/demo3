package com.endava.ai.ui.core;

import com.endava.ai.core.reporting.attachment.FailureAttachmentRegistry;
import com.endava.ai.ui.engine.UIEngine;
import com.endava.ai.ui.engine.UIEngineFactory;
import com.endava.ai.ui.reporting.UiScreenshotFailureHandler;

/**
 * ThreadLocal engine lifecycle; hides engine specifics.
 */
public final class DriverManager {
    private static final ThreadLocal<UIEngine> ENGINE = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void initEngine() {
        if (ENGINE.get() != null) return;

        ENGINE.set(UIEngineFactory.create());

        FailureAttachmentRegistry.register(
                new UiScreenshotFailureHandler()
        );
    }

    public static UIEngine getEngine() {
        UIEngine e = ENGINE.get();
        if (e == null) {
            throw new IllegalStateException(
                    "UI engine is not initialized. Ensure BaseTestUI setup ran."
            );
        }
        return e;
    }

    public static boolean hasActiveEngine() {
        return ENGINE.get() != null;
    }

    public static void quitEngine() {
        UIEngine e = ENGINE.get();
        if (e != null) {
            try {
                e.quit();
            } finally {
                ENGINE.remove();
            }
        }
    }
}
