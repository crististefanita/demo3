package com.endava.ai.core;

import com.endava.ai.core.reporting.ReportLogger;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.core.reporting.screnshot.ScreenshotManager;
import com.endava.ai.core.reporting.utils.ReportingSupport;
import org.testng.*;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import static com.endava.ai.core.reporting.utils.ReportingSupport.useExtent;

public final class TestListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

    private static final Object SUITE_SCOPE = new Object();

    private static boolean FIRST_TEST = true;
    private static ITestResult LAST_TEST;

    private static final ThreadLocal<Boolean> TEST_ENDED = new ThreadLocal<>();
    private static final ThreadLocal<StepBufferLogger> BEFORE_METHOD = ThreadLocal.withInitial(StepBufferLogger::new);

    private static final Map<Object, StepBufferLogger> BEFORE = java.util.Collections.synchronizedMap(new IdentityHashMap<>());
    private static final Map<Object, StepBufferLogger> AFTER = java.util.Collections.synchronizedMap(new IdentityHashMap<>());
    private static final Map<Class<?>, Boolean> FIRST_SEEN_CLASS = java.util.Collections.synchronizedMap(new IdentityHashMap<>());
    private static final Map<ITestResult, Boolean> STARTED_RESULTS = java.util.Collections.synchronizedMap(new IdentityHashMap<>());

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(ReportingSupport::deleteAllureResults));
    }

    @Override
    public void onStart(ISuite suite) {
        FIRST_TEST = true;
        LAST_TEST = null;
        BEFORE.clear();
        AFTER.clear();
        FIRST_SEEN_CLASS.clear();
        STARTED_RESULTS.clear();
        TEST_ENDED.remove();
        BEFORE_METHOD.get().clear();
        StepLogger.clear();
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult tr, ITestContext ctx) {
        if (!method.isConfigurationMethod()) return;

        ITestNGMethod m = method.getTestMethod();
        if (m == null) return;

        if (m.isBeforeMethodConfiguration()) {
            StepBufferLogger b = BEFORE_METHOD.get();
            b.clear();
            StepLogger.setDelegate(b);
            return;
        }

        if (m.isAfterMethodConfiguration()) {
            StepLogger.setDelegate(ReportingManager.getLogger());
            return;
        }

        if (m.isBeforeClassConfiguration() || m.isBeforeTestConfiguration()) {
            Object scope = scopeOf(m);
            StepLogger.setDelegate(BEFORE.computeIfAbsent(scope, k -> new StepBufferLogger()));
            return;
        }

        if (m.isAfterClassConfiguration() || m.isAfterTestConfiguration() || m.isAfterSuiteConfiguration()) {
            Object scope = scopeOf(m);
            StepLogger.setDelegate(AFTER.computeIfAbsent(scope, k -> new StepBufferLogger()));
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult tr, ITestContext ctx) {
        if (!method.isConfigurationMethod()) return;
        StepLogger.clearDelegate();
    }

    @Override
    public void onTestStart(ITestResult result) {
        if (result != null && STARTED_RESULTS.putIfAbsent(result, Boolean.TRUE) != null) return;

        String name = result != null && result.getMethod() != null ? result.getMethod().getMethodName() : "unknown";
        String description = result != null && result.getMethod() != null ? result.getMethod().getDescription() : null;
        Class<?> cls = result != null && result.getMethod() != null ? result.getMethod().getRealClass() : null;

        ScreenshotManager.resetForTest();

        if (!FIRST_TEST && !Boolean.TRUE.equals(TEST_ENDED.get())) ReportingManager.getLogger().endTest("RESET");
        FIRST_TEST = false;

        LAST_TEST = result;
        TEST_ENDED.set(Boolean.FALSE);

        ReportLogger logger = ReportingManager.getLogger();
        if (useExtent()) logger.ensureTestStarted(name, description);
        StepLogger.setDelegate(logger);

        StepBufferLogger bm = BEFORE_METHOD.get();
        if (!bm.isEmpty()) {
            bm.flushTo(logger);
            bm.clear();
        }

        if (cls != null && FIRST_SEEN_CLASS.putIfAbsent(cls, Boolean.TRUE) == null) {
            StepBufferLogger b = BEFORE.remove(cls);
            if (b != null) b.flushTo(logger);
        }

        StepBufferLogger s = BEFORE.remove(SUITE_SCOPE);
        if (s != null) s.flushTo(logger);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result != null && result.getThrowable() != null) StepLogger.failUnhandledOutsideStep(result.getThrowable());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (!StepLogger.testHasFailed()) {
            ReportingManager.getLogger().fail(
                    "Test failed",
                    result != null && result.getThrowable() != null ? result.getThrowable().toString() : "null"
            );
        }

        if (!Boolean.TRUE.equals(TEST_ENDED.get())) {
            ReportingManager.getLogger().endTest("FAIL");
            TEST_ENDED.set(Boolean.TRUE);
        }
    }

    @Override
    public void onFinish(ISuite suite) {
        if (LAST_TEST != null) {
            Class<?> cls = LAST_TEST.getMethod() != null ? LAST_TEST.getMethod().getRealClass() : null;

            if (cls != null) {
                StepBufferLogger a = AFTER.remove(cls);
                if (a != null) a.flushTo(ReportingManager.getLogger());
            }

            StepBufferLogger at = AFTER.remove(SUITE_SCOPE);
            if (at != null) at.flushTo(ReportingManager.getLogger());

            if (!Boolean.TRUE.equals(TEST_ENDED.get())) {
                ReportingManager.getLogger().endTest(StepLogger.testHasFailed() ? "FAIL" : "PASS");
                TEST_ENDED.set(Boolean.TRUE);
            }
        }

        ReportingManager.getLogger().flush();
        TEST_ENDED.remove();
        BEFORE_METHOD.get().clear();
        StepLogger.clear();
    }

    private static Object scopeOf(ITestNGMethod m) {
        if (m.isBeforeClassConfiguration() || m.isAfterClassConfiguration()) return m.getRealClass();
        if (m.isBeforeTestConfiguration() || m.isAfterTestConfiguration() || m.isAfterSuiteConfiguration()) return SUITE_SCOPE;
        return SUITE_SCOPE;
    }

    private static final class StepBufferLogger implements ReportLogger {

        private final List<Event> events = new ArrayList<>();

        @Override
        public void ensureTestStarted(String testName, String description) {
        }

        @Override
        public void startTest(String testName, String description) {
        }

        @Override
        public void endTest(String status) {
        }

        @Override
        public void startStep(String stepTitle) {
            events.add(new Event(Kind.START_STEP, stepTitle, null));
        }

        @Override
        public void logDetail(String detail) {
            events.add(new Event(Kind.DETAIL, detail, null));
        }

        @Override
        public void pass(String message) {
            events.add(new Event(Kind.PASS, message, null));
        }

        @Override
        public void fail(String message, String stacktraceAsText) {
            events.add(new Event(Kind.FAIL, message, stacktraceAsText));
        }

        @Override
        public void attachScreenshotBase64(String base64, String title) {
            events.add(new Event(Kind.SCREENSHOT, base64, title));
        }

        @Override
        public void logCodeBlock(String content) {
            events.add(new Event(Kind.CODE, content, null));
        }

        @Override
        public void flush() {
        }

        boolean isEmpty() {
            return events.isEmpty();
        }

        void clear() {
            events.clear();
        }

        void flushTo(ReportLogger target) {
            for (Event e : events) {
                switch (e.kind) {
                    case START_STEP:
                        target.startStep(e.a);
                        break;
                    case DETAIL:
                        target.logDetail(e.a);
                        break;
                    case PASS:
                        target.pass(e.a);
                        break;
                    case FAIL:
                        target.fail(e.a, e.b);
                        break;
                    case CODE:
                        target.logCodeBlock(e.a);
                        break;
                    case SCREENSHOT:
                        target.attachScreenshotBase64(e.a, e.b);
                        break;
                }
            }
        }

        private enum Kind {START_STEP, DETAIL, PASS, FAIL, CODE, SCREENSHOT}

        private static final class Event {
            final Kind kind;
            final String a;
            final String b;

            Event(Kind kind, String a, String b) {
                this.kind = kind;
                this.a = a;
                this.b = b;
            }
        }
    }
}
