package com.endava.ai.core.reporting.adapters.extent;

import com.aventstack.extentreports.ExtentTest;

public final class ExtentTestContext {

    private final ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();
    private final ExtentReporter reporter;

    public ExtentTestContext(ExtentReporter reporter) {
        this.reporter = reporter;
    }

    public ExtentTest get() {
        return currentTest.get();
    }

    public void start(String name, String description) {
        if (currentTest.get() == null) {
            currentTest.set(reporter.createTest(name, description));
        }
    }

    public void end() {
        currentTest.remove();
    }
}
