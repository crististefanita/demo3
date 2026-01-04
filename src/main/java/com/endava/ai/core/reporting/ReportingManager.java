package com.endava.ai.core.reporting;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.adapters.AllureAdapter;
import com.endava.ai.core.reporting.adapters.ExtentAdapter;

import java.util.Locale;

public final class ReportingManager {

    private static ReportLogger logger;

    private ReportingManager() {
    }

    public static synchronized ReportLogger getLogger() {
        ReportLogger result = logger;
        if (result != null) return result;

        synchronized (ReportingManager.class) {
            if (logger == null) {
                logger = createLogger();
            }
            return logger;
        }
    }

    public static void setLoggerForTests(ReportLogger testLogger) {
        logger = testLogger;
    }

    public static void reset() {
        logger = null;
    }

    private static ReportLogger createLogger() {
        String engine = ConfigManager.require("reporting.engine")
                .toLowerCase(Locale.ROOT);

        switch (engine) {
            case "extent":
                return ExtentAdapter.getInstance();
            case "allure":
                return AllureAdapter.getInstance();
            default:
                throw new IllegalArgumentException(
                        "Unsupported reporting.engine: " + engine +
                                " (supported: extent, allure)"
                );
        }
    }


}
