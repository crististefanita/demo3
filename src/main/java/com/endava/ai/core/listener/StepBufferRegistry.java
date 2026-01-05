package com.endava.ai.core.listener;

import com.endava.ai.core.reporting.ReportLogger;
import org.testng.ITestNGMethod;

import java.util.IdentityHashMap;
import java.util.Map;


final class StepBufferRegistry {

    private static final Object SUITE_SCOPE = new Object();

    private final Map<Object, StepBufferLogger> before = new IdentityHashMap<>();
    private final Map<Object, StepBufferLogger> after = new IdentityHashMap<>();
    private final ThreadLocal<StepBufferLogger> beforeMethod =
            ThreadLocal.withInitial(StepBufferLogger::new);
    private final Map<Class<?>, Boolean> firstSeenClass = new IdentityHashMap<>();

    StepBufferLogger beforeMethod() {
        return beforeMethod.get();
    }

    StepBufferLogger beforeFor(ITestNGMethod m) {
        return before.computeIfAbsent(scopeOf(m), k -> new StepBufferLogger());
    }

    StepBufferLogger afterFor(ITestNGMethod m) {
        return after.computeIfAbsent(scopeOf(m), k -> new StepBufferLogger());
    }

    void flushBeforeClass(Class<?> cls, ReportLogger logger) {
        if (firstSeenClass.putIfAbsent(cls, Boolean.TRUE) == null) {
            StepBufferLogger b = before.remove(cls);
            if (b != null) b.flushTo(logger);
        }
    }

    void flushBeforeSuite(ReportLogger logger) {
        StepBufferLogger s = before.remove(SUITE_SCOPE);
        if (s != null) s.flushTo(logger);
    }

    void flushAfterForLastTest(Class<?> cls, ReportLogger logger) {
        StepBufferLogger a = after.remove(cls);
        if (a != null) a.flushTo(logger);

        StepBufferLogger s = after.remove(SUITE_SCOPE);
        if (s != null) s.flushTo(logger);
    }

    void clear() {
        before.clear();
        after.clear();
        firstSeenClass.clear();
        beforeMethod.get().clear();
    }

    private static Object scopeOf(ITestNGMethod m) {
        if (m.isBeforeClassConfiguration() || m.isAfterClassConfiguration())
            return m.getRealClass();

        if (m.isBeforeTestConfiguration() || m.isAfterTestConfiguration())
            return m.getRealClass();

        if (m.isAfterSuiteConfiguration())
            return SUITE_SCOPE;

        return SUITE_SCOPE;
    }
}
