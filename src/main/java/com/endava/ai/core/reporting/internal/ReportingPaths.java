package com.endava.ai.core.reporting.internal;

import java.io.File;

public final class ReportingPaths {

    public static final String ALLURE_RESULTS_DIRECTORY_PROPERTY = "allure.results.directory";
    public static final String DEFAULT_ALLURE_RESULTS_DIRECTORY = "target/allure-results";

    private ReportingPaths() {
    }

    public static File allureResultsDirectory() {
        String value = System.getProperty(ALLURE_RESULTS_DIRECTORY_PROPERTY);
        return new File(value == null || value.isBlank() ? DEFAULT_ALLURE_RESULTS_DIRECTORY : value.trim());
    }
}