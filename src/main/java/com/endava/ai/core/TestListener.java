package com.endava.ai.core;

import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.core.reporting.screnshot.ScreenshotManager;
import com.endava.ai.core.reporting.utils.ReportingSupport;
import org.testng.*;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

import static com.endava.ai.core.reporting.utils.ReportingSupport.useExtent;

public final class TestListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

    private static boolean FIRST_TEST = true;
    private static ITestResult LAST_TEST;

    private static final Set<Class<?>> FIRST_SEEN_CLASS = Collections.newSetFromMap(new IdentityHashMap<>());
    private static final Set<ITestResult> STARTED_RESULTS = Collections.newSetFromMap(new IdentityHashMap<>());

    static {
        Runtime.getRuntime().addShutdownHook(
                new Thread(ReportingSupport::deleteAllureResults)
        );
    }

    @Override
    public void onStart(ISuite suite) {
        FIRST_TEST = true;
        LAST_TEST = null;
        FIRST_SEEN_CLASS.clear();
        STARTED_RESULTS.clear();
        StepLogger.clearTestStarted();
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult tr, ITestContext ctx) {
        if (!method.isConfigurationMethod()) return;

        ITestNGMethod m = method.getTestMethod();
        if (m == null || m.getRealClass() == null) return;

        Class<?> scope = m.getRealClass();
        if (scope == null) return;

        if (m.isBeforeClassConfiguration() || m.isBeforeTestConfiguration()) StepLogger.markBeforePhase(scope);
        if (m.isAfterClassConfiguration() || m.isAfterTestConfiguration() || m.isAfterSuiteConfiguration()) StepLogger.markAfterPhase(scope);
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult tr, ITestContext ctx) {
        if (!method.isConfigurationMethod()) return;

        ITestNGMethod m = method.getTestMethod();
        if (m == null) return;

        if (m.isBeforeClassConfiguration() || m.isBeforeTestConfiguration()) StepLogger.clearBeforePhase();
        if (m.isAfterClassConfiguration() || m.isAfterTestConfiguration() || m.isAfterSuiteConfiguration()) StepLogger.clearAfterPhase();
    }

    @Override
    public void onTestStart(ITestResult result) {
        if (result != null && !STARTED_RESULTS.add(result)) return;

        String name = result != null && result.getMethod() != null ? result.getMethod().getMethodName() : "unknown";
        String description = result != null && result.getMethod() != null ? result.getMethod().getDescription() : null;
        System.out.println("onTestStart: " + name + " @" + System.identityHashCode(this));

        Class<?> cls = null;
        if (result != null && result.getMethod() != null && result.getMethod().getRealClass() != null) {
            cls = result.getMethod().getRealClass();
        }

        ScreenshotManager.resetForTest();

        StepLogger.markTestEnded();
        if (!FIRST_TEST) ReportingManager.getLogger().endTest("RESET");
        FIRST_TEST = false;

        LAST_TEST = result;
        StepLogger.markTestStarted();

        if (!useExtent()) return;

        ReportingManager.getLogger().ensureTestStarted(name, description);

        if (cls != null && FIRST_SEEN_CLASS.add(cls)) StepLogger.flushBefore(cls);

        StepLogger.flushPending();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        StepLogger.markBodyFinished();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result != null && result.getThrowable() != null) StepLogger.failUnhandledOutsideStep(result.getThrowable());
        StepLogger.markBodyFinished();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (!StepLogger.testHasFailed()) {
            ReportingManager.getLogger().fail(
                    "Test failed",
                    result != null && result.getThrowable() != null ? result.getThrowable().toString() : "null"
            );
        }
        StepLogger.markBodyFinished();
    }

    @Override
    public void onFinish(ISuite suite) {
        if (LAST_TEST != null) {
            Class<?> cls = null;
            if (LAST_TEST.getMethod() != null && LAST_TEST.getMethod().getRealClass() != null) {
                cls = LAST_TEST.getMethod().getRealClass();
            }

            if (cls != null) {
                StepLogger.flushAfter(cls);
            }

            ReportingManager.getLogger().endTest(
                    StepLogger.testHasFailed() ? "FAIL" : "PASS"
            );
        }

        ReportingManager.getLogger().flush();
        StepLogger.clearTestStarted();
    }
}
