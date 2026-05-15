package com.endava.ai.reporting.adapters;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.internal.ReportingEngineCleanup;
import com.endava.ai.core.reporting.internal.ReportingPaths;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class AllureResultsCleanupTest {

    private File allureDir;

    @BeforeMethod
    void setUp() {
        allureDir = ReportingPaths.allureResultsDirectory();

        deleteDir(allureDir);

        allureDir.mkdirs();

        new File(allureDir, "dummy.txt");
    }

    @AfterMethod
    void tearDown() {
        deleteDir(allureDir);
        ConfigManager.clear("reporting.engine");
    }

    @Test
    public void deletes_allure_results_when_using_extent() {
        givenReportingEngine("extent");

        ReportingEngineCleanup.onShutdown();

        Assert.assertFalse(allureDir.exists());
    }

    @Test
    public void preserves_allure_results_when_using_allure() {
        givenReportingEngine("allure");

        ReportingEngineCleanup.onShutdown();

        Assert.assertTrue(allureDir.exists());
    }

    @Test
    public void extent_never_leaves_allure_results_directory() {
        givenReportingEngine("extent");

        ReportingEngineCleanup.onShutdown();

        Assert.assertFalse(
                ReportingPaths.allureResultsDirectory().exists(),
                "Extent must not leave allure-results behind"
        );
    }

    private static void givenReportingEngine(String engine) {
        ConfigManager.set("reporting.engine", engine);
    }

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