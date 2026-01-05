package com.endava.ai.core.reporting;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.adapters.AllureAdapter;
import com.endava.ai.core.reporting.adapters.ExtentAdapter;

import java.util.Locale;

public final class ReportingManager {

    private static ReportLogger logger;

    private ReportingManager() {}

    @SuppressWarnings("ConstantConditions")
    public static synchronized ReportLogger getLogger() {
        ReportLogger result = logger;
        if (result != null) return result;

        if (logger == null) logger = createLoggerStrict();
        return logger;
    }

    /** for unit tests / core-only usage */
    public static synchronized ReportLogger tryGetLogger() {
        if (logger != null) return logger;

        String engine = ConfigManager.get("reporting.engine", null);
        if (engine == null || engine.isBlank()) return null;

        logger = createLogger(engine);
        return logger;
    }

    public static void setLoggerForTests(ReportLogger testLogger) {
        logger = testLogger;
    }

    public static void reset() {
        logger = null;
    }

    private static ReportLogger createLoggerStrict() {
        String engine = ConfigManager.require("reporting.engine");
        return createLogger(engine);
    }

    private static ReportLogger createLogger(String engineRaw) {
        String engine = engineRaw.toLowerCase(Locale.ROOT);

        switch (engine) {
            case "extent": return ExtentAdapter.getInstance();
            case "allure": return AllureAdapter.getInstance();
            default:
                throw new IllegalArgumentException(
                        "Unsupported reporting.engine: " + engine + " (supported: extent, allure)"
                );
        }
    }
}