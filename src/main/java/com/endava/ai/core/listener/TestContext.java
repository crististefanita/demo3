package com.endava.ai.core.listener;

import org.testng.ITestResult;

@SuppressWarnings("unused")
final class TestContext {

    private Class<?> activeClass;
    private boolean reportFailed;
    private boolean testOpen;

    private boolean firstTest = true;
    private ITestResult lastTest;
    private final ThreadLocal<Boolean> testEnded = new ThreadLocal<>();

    Class<?> getActiveClass() {
        return activeClass;
    }

    Class<?> getActiveReportClass() {
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

    boolean isFirstTest() {
        return firstTest;
    }

    void markFirstTestConsumed() {
        firstTest = false;
    }

    ITestResult getLastTest() {
        return lastTest;
    }

    void setLastTest(ITestResult result) {
        lastTest = result;
    }

    boolean isTestStarted() {
        return !Boolean.TRUE.equals(testEnded.get());
    }

    boolean hasTestStarted() {
        return testEnded.get() != null;
    }

    void markTestStarted() {
        testEnded.set(Boolean.FALSE);
        testOpen = true;
    }

    void markTestEnded() {
        testEnded.set(Boolean.TRUE);
        testOpen = false;
    }

    void reset() {
        activeClass = null;
        reportFailed = false;
        testOpen = false;
        firstTest = true;
        lastTest = null;
        testEnded.remove();
    }

    void clear() {
        reset();
    }
}
