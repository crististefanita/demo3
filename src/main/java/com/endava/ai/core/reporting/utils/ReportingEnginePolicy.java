package com.endava.ai.core.reporting.utils;

import com.endava.ai.core.config.ConfigManager;

public final class ReportingEnginePolicy {

    private ReportingEnginePolicy() {}

    public static boolean isAllure() {
        return "allure".equalsIgnoreCase(
                ConfigManager.get("reporting.engine", "extent")
        );
    }

    public static boolean isExtent() {
        return !isAllure();
    }
}