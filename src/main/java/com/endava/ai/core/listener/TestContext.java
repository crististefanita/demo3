package com.endava.ai.core.listener;

import org.testng.ITestResult;

final class TestContext {

    private boolean firstTest = true;
    private ITestResult lastTest;
    private final ThreadLocal<Boolean> testEnded = new ThreadLocal<>();

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
    }

    void markTestEnded() {
        testEnded.set(Boolean.TRUE);
    }

    void clear() {
        firstTest = true;
        lastTest = null;
        testEnded.remove();
    }
}
