package com.endava.ai.core.reporting.utils;

import com.endava.ai.core.config.ConfigManager;

import java.io.File;

public class ReportingSupport {

    public static boolean useExtent() {
        boolean isExtent = !"allure".equalsIgnoreCase(
                ConfigManager.get("reporting.engine", "extent")
        );
        System.out.println("ReportingEngine isExtent: " + isExtent);
        return isExtent;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteAllureResults() {
        if (!useExtent()) return;

        File dir = new File("allure-results");
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File f : files) f.delete();
        dir.delete();
    }

}
