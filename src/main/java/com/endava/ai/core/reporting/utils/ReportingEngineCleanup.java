package com.endava.ai.core.reporting.utils;

import java.io.File;

public final class ReportingEngineCleanup {

    private ReportingEngineCleanup() {
    }

    public static void onShutdown() {
        if (ReportingEnginePolicy.isAllure()) return;
        deleteAllureResults();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void deleteAllureResults() {
        File dir = new File("allure-results");
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File f : files) f.delete();
        dir.delete();
    }
}
