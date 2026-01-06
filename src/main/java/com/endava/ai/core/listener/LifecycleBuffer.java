package com.endava.ai.core.listener;

import com.endava.ai.core.reporting.ReportLogger;
import org.testng.ITestNGMethod;

final class LifecycleBuffer {

    private final StepBuffer beforeSuite = new StepBuffer();
    private final StepBuffer beforeTest = new StepBuffer();
    private final StepBuffer beforeClass = new StepBuffer();
    private final StepBuffer beforeMethod = new StepBuffer();
    private final StepBuffer afterMethod = new StepBuffer();
    private final StepBuffer afterClass = new StepBuffer();
    private final StepBuffer afterTest = new StepBuffer();
    private final StepBuffer afterSuite = new StepBuffer();

    ReportLogger resolveBefore(ITestNGMethod method) {
        if (method.isBeforeSuiteConfiguration()) return beforeSuite;
        if (method.isBeforeTestConfiguration()) return beforeTest;
        if (method.isBeforeClassConfiguration()) return beforeClass;
        if (method.isBeforeMethodConfiguration()) return beforeMethod;
        return null;
    }

    ReportLogger resolveAfter(ITestNGMethod method) {
        if (method.isAfterMethodConfiguration()) return afterMethod;
        if (method.isAfterClassConfiguration()) return afterClass;
        if (method.isAfterTestConfiguration()) return afterTest;
        if (method.isAfterSuiteConfiguration()) return afterSuite;
        return null;
    }

    void flushBefore(ReportLogger logger) {
        flush(logger, "BEFORE", "@BeforeSuite", beforeSuite);
        flush(logger, null, "@BeforeTest", beforeTest);
        flush(logger, null, "@BeforeClass", beforeClass);
        flush(logger, null, "@BeforeMethod", beforeMethod);
    }

    void flushAfter(ReportLogger logger) {
        flush(logger, "AFTER", "@AfterMethod", afterMethod);
        flush(logger, null, "@AfterClass", afterClass);
        flush(logger, null, "@AfterTest", afterTest);
        flush(logger, null, "@AfterSuite", afterSuite);
    }

    private void flush(ReportLogger logger, String group, String name, StepBuffer buffer) {
        if (buffer.isEmpty()) return;
        if (group != null) logger.startStep(group);
        logger.startStep(name);
        buffer.flushTo(logger);
        logger.pass("");
        if (group != null) logger.pass("");
    }

    void clear() {
        beforeSuite.clear();
        beforeTest.clear();
        beforeClass.clear();
        beforeMethod.clear();
        afterMethod.clear();
        afterClass.clear();
        afterTest.clear();
        afterSuite.clear();
    }
}
