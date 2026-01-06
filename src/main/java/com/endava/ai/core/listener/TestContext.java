package com.endava.ai.core.listener;

final class TestContext {

    private Class<?> activeClass;
    private boolean reportFailed;
    private boolean testOpen;

    Class<?> getActiveClass() {
        return activeClass;
    }

    void setActiveClass(Class<?> activeClass) {
        this.activeClass = activeClass;
    }

    boolean isReportFailed() {
        return reportFailed;
    }

    void markFailed() {
        this.reportFailed = true;
    }

    void resetFailure() {
        this.reportFailed = false;
    }

    boolean isTestOpen() {
        return testOpen;
    }

    void openTest() {
        this.testOpen = true;
    }

    void closeTest() {
        this.testOpen = false;
    }

    void reset() {
        activeClass = null;
        reportFailed = false;
        testOpen = false;
    }
}
