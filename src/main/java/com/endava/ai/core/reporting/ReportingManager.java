package com.endava.ai.core.reporting;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.internal.ReportingEngine;

import java.util.function.Supplier;

public final class ReportingManager {

    private static ReportLogger logger;

    private ReportingManager() {
    }

    public static synchronized ReportLogger getLogger() {
        return getOrCreate(() -> ConfigManager.require("reporting.engine"));
    }

    public static synchronized ReportLogger tryGetLogger() {
        return getOrCreate(() -> ConfigManager.get("reporting.engine", null));
    }

    public static void setLoggerForTests(ReportLogger testLogger) {
        logger = testLogger;
    }

    public static void reset() {
        logger = null;
    }

    private static ReportLogger getOrCreate(Supplier<String> engineSupplier) {
        if (logger != null) return logger;

        String raw = engineSupplier.get();
        if (raw == null || raw.isBlank()) return null;

        logger = ReportingEngine.from(raw).createLogger();
        return logger;
    }
}
