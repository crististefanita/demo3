package com.endava.ai.core.reporting;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.adapters.ExtentAdapter;
import com.endava.ai.core.reporting.adapters.AllureAdapter;

public final class ReportingManager {

    private static volatile ReportLogger logger;

    private ReportingManager() {}

    public static ReportLogger getLogger() {
        if (logger != null) return logger;

        synchronized (ReportingManager.class) {
            if (logger != null) return logger;

            String engine = ConfigManager
                    .require("reporting.engine")
                    .toLowerCase()
                    .trim();

            switch (engine) {
                case "extent":
                    logger = ExtentAdapter.getInstance();
                    break;

                case "allure":
                    logger = AllureAdapter.getInstance();
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Unsupported reporting.engine: " + engine +
                                    " (supported: extent, allure)"
                    );
            }
            return logger;
        }
    }
}
