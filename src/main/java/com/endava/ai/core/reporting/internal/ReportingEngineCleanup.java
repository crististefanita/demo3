package com.endava.ai.core.reporting.internal;

import java.io.File;

public final class ReportingEngineCleanup {

    private ReportingEngineCleanup() {
    }

    public static void onShutdown() {
        if (ReportingEngine.current().keepsAllureResults()) return;
        deleteDir(ReportingPaths.allureResultsDirectory());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void deleteDir(File dir) {
        if (dir == null || !dir.exists()) return;

        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) deleteDir(file);
                else file.delete();
            }
        }

        dir.delete();
    }
}