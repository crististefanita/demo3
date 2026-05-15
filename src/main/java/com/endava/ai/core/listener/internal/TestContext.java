package com.endava.ai.core.listener.internal;

import org.testng.ITestResult;

import java.util.IdentityHashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public final class TestContext {

    private final Map<Class<?>, Boolean> firstMethodSeen = new IdentityHashMap<>();

    private boolean firstTest = true;
    private boolean testStarted;
    private ITestResult lastTest;
    private Class<?> activeReportClass;

    public boolean isFirstMethodForClass(Class<?> cls) {
        return firstMethodSeen.putIfAbsent(cls, Boolean.TRUE) == null;
    }

    public boolean isFirstTest() {
        return firstTest;
    }

    public void markFirstTestConsumed() {
        firstTest = false;
    }

    public boolean hasTestStarted() {
        return testStarted;
    }

    public boolean isTestStarted() {
        return testStarted;
    }

    public void markTestStarted() {
        testStarted = true;
    }

    public void markTestEnded() {
        testStarted = false;
    }

    public ITestResult getLastTest() {
        return lastTest;
    }

    public void setLastTest(ITestResult lastTest) {
        this.lastTest = lastTest;
    }

    public Class<?> getActiveReportClass() {
        return activeReportClass;
    }

    public void setActiveReportClass(Class<?> cls) {
        this.activeReportClass = cls;
    }

    public void clear() {
        firstMethodSeen.clear();
        firstTest = true;
        testStarted = false;
        lastTest = null;
        activeReportClass = null;
    }
}
