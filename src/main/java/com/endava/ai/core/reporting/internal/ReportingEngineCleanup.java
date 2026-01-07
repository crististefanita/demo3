package com.endava.ai.core.reporting.internal;

import java.io.File;

public final class ReportingEngineCleanup {

    private ReportingEngineCleanup() {
    }

    public static void onShutdown() {
        if (ReportingEngine.current().keepsAllureResults()) return;
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
