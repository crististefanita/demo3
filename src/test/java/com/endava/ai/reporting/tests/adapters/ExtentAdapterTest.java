package com.endava.ai.reporting.tests.adapters;

import com.endava.ai.core.reporting.adapters.ExtentAdapter;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.testng.Assert.assertTrue;

public class ExtentAdapterTest {

    @Test
    public void shouldCreateReportWhenReportsDirectoryIsMissing() throws Exception {
        Path reportsDir = Path.of("target", "reports");

        if (Files.exists(reportsDir)) {
            try (Stream<Path> paths = Files.walk(reportsDir)) {
                paths.sorted(Comparator.reverseOrder())
                        .forEach(p -> {
                            try {
                                Files.deleteIfExists(p);
                            } catch (Exception ignored) {
                            }
                        });
            }
        }

        ExtentAdapter adapter = ExtentAdapter.getInstance();

        adapter.startTest("Test Suite", "missing target reports dir");
        adapter.flush();

        Path expectedReport = reportsDir.resolve("ExtentReport.html");

        assertTrue(Files.exists(expectedReport));
    }
}
