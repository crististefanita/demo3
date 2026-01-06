package com.endava.ai.core.listener;

import com.endava.ai.core.reporting.ReportLogger;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.core.reporting.attachment.FailureAttachmentRegistry;
import com.endava.ai.core.reporting.utils.ReportingEngineCleanup;
import org.testng.*;

public final class TestListener
        implements ITestListener, ISuiteListener, IInvokedMethodListener {

    private final TestContext context = new TestContext();
    private final StepBufferRegistry buffers = new StepBufferRegistry();
    private final TestLifecycleController lifecycle =
            new TestLifecycleController(context);

    static {
        Runtime.getRuntime().addShutdownHook(
                new Thread(ReportingEngineCleanup::onShutdown)
        );
    }

    @Override
    public void onStart(ISuite suite) {
        context.clear();
        buffers.clear();
        StepLogger.clear();
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult tr, ITestContext ctx) {
        if (!method.isConfigurationMethod()) return;

        ITestNGMethod m = method.getTestMethod();
        if (m == null) return;

        StepLogger.setDelegate(resolveDelegate(m));
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult tr, ITestContext ctx) {
        if (method.isConfigurationMethod()) {
            StepLogger.clearDelegate();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        FailureAttachmentRegistry.onTestStart();
        lifecycle.startTest(result);

        ReportLogger logger = ReportingManager.tryGetLogger();
        if (logger == null) return;

        boolean hasSetup = false;

        StepBufferLogger suite = buffers.peekBeforeSuite();
        if (suite != null && !suite.isEmpty()) {
            startGroup(logger, "SETUP");
            startGroup(logger, "@BeforeSuite");
            buffers.flushBeforeSuite(logger);
            endGroup(logger);
            hasSetup = true;
        }

        StepBufferLogger cls = buffers.peekBeforeClass(result.getMethod().getRealClass());
        if (cls != null && !cls.isEmpty()) {
            if (!hasSetup) startGroup(logger, "SETUP");
            startGroup(logger, "@BeforeClass");
            buffers.flushBeforeClass(result.getMethod().getRealClass(), logger);
            endGroup(logger);
            hasSetup = true;
        }

        StepBufferLogger bm = buffers.beforeMethod();
        if (!bm.isEmpty()) {
            if (!hasSetup) startGroup(logger, "SETUP");
            startGroup(logger, "@BeforeMethod");
            flushBeforeMethod(logger);
            endGroup(logger);
            hasSetup = true;
        }

        if (hasSetup) endGroup(logger);

        startGroup(logger, "TEST BODY");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        FailureAttachmentRegistry.onTestFailure();
        lifecycle.failTest(result);

        if (result.getThrowable() != null) {
            StepLogger.failUnhandledOutsideStep(result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result.getThrowable() != null) {
            StepLogger.failUnhandledOutsideStep(result.getThrowable());
        }
        context.markTestEnded();
    }

    @Override
    public void onFinish(ISuite suite) {
        ReportLogger logger = ReportingManager.tryGetLogger();

        if (logger != null && context.getLastTest() != null) {
            boolean hasTeardown = false;

            StepBufferLogger ac =
                    buffers.peekAfterClass(context.getLastTest().getMethod().getRealClass());
            if (ac != null && !ac.isEmpty()) {
                startGroup(logger, "TEARDOWN");
                startGroup(logger, "@AfterClass");
                buffers.flushAfterClassForLastTest(
                        context.getLastTest().getMethod().getRealClass(),
                        logger
                );
                endGroup(logger);
                hasTeardown = true;
            }

            if (hasTeardown) endGroup(logger);
        }

        lifecycle.endSuite();
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
            return real;
        }

        if (m.isBeforeClassConfiguration()
                || m.isBeforeTestConfiguration()
                || m.isBeforeSuiteConfiguration()) {
            return buffers.beforeFor(m);
        }

        if (m.isAfterClassConfiguration()
                || m.isAfterTestConfiguration()
                || m.isAfterSuiteConfiguration()) {
            return buffers.afterFor(m);
        }

        return null;
    }

    private void flushBeforeMethod(ReportLogger logger) {
        StepBufferLogger b = buffers.beforeMethod();
        if (!b.isEmpty()) {
            b.flushTo(logger);
            b.clear();
        }
    }

    private static void startGroup(ReportLogger logger, String title) {
        logger.startStep(title);
    }

    private static void endGroup(ReportLogger logger) {
        logger.pass("");
    }

    public static void resetForTests() {
    }
}
