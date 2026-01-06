package com.endava.ai.core.listener;

import com.endava.ai.core.reporting.ReportLogger;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.core.reporting.attachment.FailureAttachmentRegistry;
import com.endava.ai.core.reporting.utils.ReportingEngineCleanup;
import org.testng.*;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

public final class TestListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

    private final TestContext context = new TestContext();
    private final StepBufferRegistry buffers = new StepBufferRegistry();
    private final TestLifecycleController lifecycle = new TestLifecycleController(context);

    private final Map<Class<?>, Boolean> firstMethodSeen = new IdentityHashMap<>();
    private final Map<Class<?>, Set<String>> methodsWithDependents = new IdentityHashMap<>();

    private final ThreadLocal<State> state = ThreadLocal.withInitial(State::new);

    private static final class State {
        boolean methodGroupOpen;
        boolean beforeOpen;
        boolean testOpen;
        boolean afterOpen;
        boolean flowMode;

        void reset() {
            methodGroupOpen = false;
            beforeOpen = false;
            testOpen = false;
            afterOpen = false;
            flowMode = false;
        }
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(ReportingEngineCleanup::onShutdown));
    }

    @Override
    public void onStart(ISuite suite) {
        context.clear();
        buffers.clear();
        StepLogger.clear();
        firstMethodSeen.clear();
        methodsWithDependents.clear();
        state.remove();
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult tr) {
        if (!method.isConfigurationMethod()) return;
        ITestNGMethod m = method.getTestMethod();
        if (m == null) return;
        StepLogger.setDelegate(resolveDelegate(m));
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult tr) {
        ITestNGMethod m = method.getTestMethod();

        if (method.isTestMethod()) {
            ReportLogger logger = ReportingManager.tryGetLogger();
            State st = state.get();

            if (logger != null && st.testOpen && !st.flowMode) {
                endGroup(logger);
                st.testOpen = false;
            }

            if (logger != null && !st.afterOpen && !st.flowMode && hasAfterContent(tr.getMethod().getRealClass())) {
                startGroup(logger, "AFTER");
                st.afterOpen = true;
            }

            return;
        }

        if (!method.isConfigurationMethod()) return;

        if (m != null && m.isAfterMethodConfiguration()) {
            ReportLogger logger = ReportingManager.tryGetLogger();
            if (logger != null) {
                startGroup(logger, "@AfterMethod");
                flushAfterMethod(logger);
                endGroup(logger);
            }
        }

        StepLogger.clearDelegate();
    }

    @Override
    public void onTestStart(ITestResult result) {
        FailureAttachmentRegistry.onTestStart();
        ReportLogger logger = ReportingManager.tryGetLogger();
        Class<?> cls = result.getMethod().getRealClass();

        if (logger != null && context.getActiveReportClass() != null && !context.getActiveReportClass().equals(cls)) {
            flushClassTeardown(logger, context.getActiveReportClass());
        }

        lifecycle.startTest(result);
        if (logger == null) return;

        State st = state.get();

        boolean firstForClass = firstMethodSeen.putIfAbsent(cls, Boolean.TRUE) == null;
        if (firstForClass) {
            st.reset();
            st.flowMode = classHasDependentChain(result.getTestContext(), cls);
        }

        if (st.flowMode) {
            if (!st.methodGroupOpen) {
                startGroup(logger, "FLOW");
                st.methodGroupOpen = true;

                startGroup(logger, "BEFORE");
                flushBeforeSuite(logger);
                flushBeforeScopes(cls, logger);
                endGroup(logger);

                startGroup(logger, "TEST");
                st.testOpen = true;
            }

            startGroup(logger, result.getMethod().getMethodName());
            return;
        }

        st.reset();
        startGroup(logger, result.getMethod().getMethodName());
        st.methodGroupOpen = true;

        startGroup(logger, "BEFORE");
        st.beforeOpen = true;

        flushBeforeSuite(logger);
        if (firstForClass) flushBeforeScopes(cls, logger);
        flushBeforeMethod(logger);

        endGroup(logger);
        st.beforeOpen = false;

        startGroup(logger, "TEST");
        st.testOpen = true;
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        closeMethodGroups(result);
        context.markTestEnded();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        FailureAttachmentRegistry.onTestFailure();
        lifecycle.failTest(result);
        if (result.getThrowable() != null) StepLogger.failUnhandledOutsideStep(result.getThrowable());
        closeMethodGroups(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result.getThrowable() != null) StepLogger.failUnhandledOutsideStep(result.getThrowable());
        closeMethodGroups(result);
        context.markTestEnded();
    }

    @Override
    public void onFinish(ISuite suite) {
        System.out.println("TestListener.onFinish ISuite: " + suite.getName() + " @" + System.identityHashCode(this));

        ReportLogger logger = ReportingManager.tryGetLogger();
        if (logger != null && context.getActiveReportClass() != null) {
            flushClassTeardown(logger, context.getActiveReportClass());
        }
        lifecycle.endSuite();
    }

    private void closeMethodGroups(ITestResult result) {
        ReportLogger logger = ReportingManager.tryGetLogger();
        if (logger == null) return;

        State st = state.get();

        if (st.flowMode) {
            endGroup(logger);
            if (isLastInFlow(result)) {
                if (st.testOpen) {
                    endGroup(logger);
                    st.testOpen = false;
                }
                if (st.methodGroupOpen) {
                    endGroup(logger);
                    st.methodGroupOpen = false;
                }
                st.flowMode = false;
            }
            return;
        }

        if (st.afterOpen) {
            endGroup(logger);
            st.afterOpen = false;
        }
        if (st.methodGroupOpen) {
            endGroup(logger);
            st.methodGroupOpen = false;
        }
    }

    @SuppressWarnings("ConstantConditions")
    private boolean classHasDependentChain(ITestContext ctx, Class<?> cls) {
        if (ctx == null) return false;
        Set<String> dependents = methodsWithDependents.computeIfAbsent(cls, k -> computeDependents(ctx, cls));
        return dependents != null && !dependents.isEmpty();
    }

    private static Set<String> computeDependents(ITestContext ctx, Class<?> cls) {
        Set<String> s = new HashSet<>();
        for (ITestNGMethod m : ctx.getAllTestMethods()) {
            if (m == null || m.getRealClass() == null || !cls.equals(m.getRealClass())) continue;
            String[] deps = m.getMethodsDependedUpon();
            if (deps == null) continue;
            for (String d : deps) {
                if (d != null && !d.isBlank()) s.add(d);
            }
        }
        return s;
    }

    private boolean isLastInFlow(ITestResult result) {
        Class<?> cls = result.getMethod().getRealClass();
        Set<String> dependents = methodsWithDependents.get(cls);
        if (dependents == null) return true;
        return !dependents.contains(result.getMethod().getMethodName());
    }

    private void flushBeforeSuite(ReportLogger logger) {
        StepBufferLogger suite = buffers.peekBeforeSuite();
        if (suite != null && !suite.isEmpty()) {
            startGroup(logger, "@BeforeSuite");
            buffers.flushBeforeSuite(logger);
            endGroup(logger);
        }
    }

    private void flushBeforeScopes(Class<?> cls, ReportLogger logger) {
        StepBufferLogger bt = buffers.peekBeforeTest(cls);
        if (bt != null && !bt.isEmpty()) {
            startGroup(logger, "@BeforeTest");
            buffers.flushBeforeTest(cls, logger);
            endGroup(logger);
        }

        StepBufferLogger bc = buffers.peekBeforeClass(cls);
        if (bc != null && !bc.isEmpty()) {
            startGroup(logger, "@BeforeClass");
            buffers.flushBeforeClass(cls, logger);
            endGroup(logger);
        }
    }

    private void flushBeforeMethod(ReportLogger logger) {
        StepBufferLogger bm = buffers.beforeMethod();
        if (!bm.isEmpty()) {
            startGroup(logger, "@BeforeMethod");
            bm.flushTo(logger);
            bm.clear();
            endGroup(logger);
        }
    }

    private void flushAfterMethod(ReportLogger logger) {
        StepBufferLogger am = buffers.afterMethod();
        if (!am.isEmpty()) {
            am.flushTo(logger);
            am.clear();
        }
    }

    private void flushClassTeardown(ReportLogger logger, Class<?> cls) {
        StepBufferLogger at = buffers.peekAfterTest(cls);
        StepBufferLogger ac = buffers.peekAfterClass(cls);
        StepBufferLogger as = buffers.peekAfterSuite();

        boolean has = (at != null && !at.isEmpty()) || (ac != null && !ac.isEmpty()) || (as != null && !as.isEmpty());
        if (!has) return;

        startGroup(logger, "AFTER");

        if (at != null && !at.isEmpty()) {
            startGroup(logger, "@AfterTest");
            buffers.flushAfterTestForLastReport(cls, logger);
            endGroup(logger);
        }
        if (ac != null && !ac.isEmpty()) {
            startGroup(logger, "@AfterClass");
            buffers.flushAfterClassForLastReport(cls, logger);
            endGroup(logger);
        }
        if (as != null && !as.isEmpty()) {
            startGroup(logger, "@AfterSuite");
            buffers.flushAfterSuiteForLastReport(logger);
            endGroup(logger);
        }

        endGroup(logger);
    }

    private ReportLogger resolveDelegate(ITestNGMethod m) {
        ReportLogger real = ReportingManager.tryGetLogger();
        if (real == null) return null;

        if (m.isBeforeMethodConfiguration()) {
            StepBufferLogger b = buffers.beforeMethod();
            b.clear();
            return b;
        }
        if (m.isAfterMethodConfiguration()) {
            StepBufferLogger a = buffers.afterMethod();
            a.clear();
            return a;
        }
        if (m.isBeforeClassConfiguration() || m.isBeforeTestConfiguration() || m.isBeforeSuiteConfiguration()) {
            return buffers.beforeFor(m);
        }
        if (m.isAfterClassConfiguration() || m.isAfterTestConfiguration() || m.isAfterSuiteConfiguration()) {
            return buffers.afterFor(m);
        }

        return real;
    }

    private static void startGroup(ReportLogger logger, String title) {
        logger.startStep(title);
    }

    private static void endGroup(ReportLogger logger) {
        logger.pass("");
    }

    private boolean hasAfterContent(Class<?> cls) {
        StepBufferLogger at = buffers.peekAfterTest(cls);
        StepBufferLogger ac = buffers.peekAfterClass(cls);
        StepBufferLogger as = buffers.peekAfterSuite();
        StepBufferLogger am = buffers.afterMethod();
        return (am != null && !am.isEmpty())
                || (at != null && !at.isEmpty())
                || (ac != null && !ac.isEmpty())
                || (as != null && !as.isEmpty());
    }

    public static void resetForTests() {
        ReportingManager.reset();
        StepLogger.clear();
    }
}
