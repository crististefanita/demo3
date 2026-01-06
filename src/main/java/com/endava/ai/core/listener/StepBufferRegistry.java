package com.endava.ai.core.listener;

import com.endava.ai.core.reporting.ReportLogger;
import org.testng.ITestNGMethod;

import java.util.IdentityHashMap;
import java.util.Map;

final class StepBufferRegistry {
    private static final Object SUITE_SCOPE = new Object();
    private final Map<Object, StepBufferLogger> beforeSuite = new IdentityHashMap<>();
    private final Map<Class<?>, StepBufferLogger> beforeTest = new IdentityHashMap<>();
    private final Map<Class<?>, StepBufferLogger> beforeClass = new IdentityHashMap<>();
    private final Map<Class<?>, StepBufferLogger> afterTest = new IdentityHashMap<>();
    private final Map<Class<?>, StepBufferLogger> afterClass = new IdentityHashMap<>();
    private final Map<Object, StepBufferLogger> afterSuite = new IdentityHashMap<>();
    private final ThreadLocal<StepBufferLogger> beforeMethod = ThreadLocal.withInitial(StepBufferLogger::new);
    private final ThreadLocal<StepBufferLogger> afterMethod = ThreadLocal.withInitial(StepBufferLogger::new);

    StepBufferLogger beforeMethod() {
        return beforeMethod.get();
    }

    StepBufferLogger afterMethod() {
        return afterMethod.get();
    }

    StepBufferLogger beforeFor(ITestNGMethod m) {
        if (m.isBeforeSuiteConfiguration())
            return beforeSuite.computeIfAbsent(SUITE_SCOPE, k -> new StepBufferLogger());
        if (m.isBeforeClassConfiguration())
            return beforeClass.computeIfAbsent(m.getRealClass(), k -> new StepBufferLogger());
        if (m.isBeforeTestConfiguration())
            return beforeTest.computeIfAbsent(m.getRealClass(), k -> new StepBufferLogger());
        return beforeSuite.computeIfAbsent(SUITE_SCOPE, k -> new StepBufferLogger());
    }

    StepBufferLogger afterFor(ITestNGMethod m) {
        if (m.isAfterSuiteConfiguration()) return afterSuite.computeIfAbsent(SUITE_SCOPE, k -> new StepBufferLogger());
        if (m.isAfterClassConfiguration())
            return afterClass.computeIfAbsent(m.getRealClass(), k -> new StepBufferLogger());
        if (m.isAfterTestConfiguration())
            return afterTest.computeIfAbsent(m.getRealClass(), k -> new StepBufferLogger());
        return afterSuite.computeIfAbsent(SUITE_SCOPE, k -> new StepBufferLogger());
    }

    StepBufferLogger peekBeforeSuite() {
        return beforeSuite.get(SUITE_SCOPE);
    }

    StepBufferLogger peekBeforeTest(Class<?> cls) {
        return beforeTest.get(cls);
    }

    StepBufferLogger peekBeforeClass(Class<?> cls) {
        return beforeClass.get(cls);
    }

    StepBufferLogger peekAfterTest(Class<?> cls) {
        return afterTest.get(cls);
    }

    StepBufferLogger peekAfterClass(Class<?> cls) {
        return afterClass.get(cls);
    }

    StepBufferLogger peekAfterSuite() {
        return afterSuite.get(SUITE_SCOPE);
    }

    void flushBeforeSuite(ReportLogger logger) {
        StepBufferLogger s = beforeSuite.remove(SUITE_SCOPE);
        if (s != null) s.flushTo(logger);
    }

    void flushBeforeTest(Class<?> cls, ReportLogger logger) {
        StepBufferLogger b = beforeTest.remove(cls);
        if (b != null) b.flushTo(logger);
    }

    void flushBeforeClass(Class<?> cls, ReportLogger logger) {
        StepBufferLogger b = beforeClass.remove(cls);
        if (b != null) b.flushTo(logger);
    }

    void flushAfterTestForLastReport(Class<?> cls, ReportLogger logger) {
        StepBufferLogger a = afterTest.remove(cls);
        if (a != null) a.flushTo(logger);
    }

    void flushAfterClassForLastReport(Class<?> cls, ReportLogger logger) {
        StepBufferLogger a = afterClass.remove(cls);
        if (a != null) a.flushTo(logger);
    }

    void flushAfterSuiteForLastReport(ReportLogger logger) {
        StepBufferLogger s = afterSuite.remove(SUITE_SCOPE);
        if (s != null) s.flushTo(logger);
    }

    void clear() {
        beforeSuite.clear();
        beforeTest.clear();
        beforeClass.clear();
        afterTest.clear();
        afterClass.clear();
        afterSuite.clear();
        beforeMethod.get().clear();
        afterMethod.get().clear();
    }
}
