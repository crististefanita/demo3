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

        flushBeforeScopes(result, logger);
        flushBeforeMethod(logger);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        FailureAttachmentRegistry.onTestFailure();
        lifecycle.failTest(result);
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
            buffers.flushAfterForLastTest(
                    context.getLastTest().getMethod().getRealClass(),
                    logger
            );
        }

        lifecycle.endSuite();
    }

    private ReportLogger resolveDelegate(ITestNGMethod m) {
        if (m.isBeforeMethodConfiguration()) {
            StepBufferLogger b = buffers.beforeMethod();
            b.clear();
            return b;
        }

        if (m.isAfterMethodConfiguration()) {
            return ReportingManager.tryGetLogger();
        }

        if (m.isBeforeClassConfiguration() || m.isBeforeTestConfiguration()) {
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

    private void flushBeforeScopes(ITestResult result, ReportLogger logger) {
        buffers.flushBeforeClass(
                result.getMethod().getRealClass(),
                logger
        );
        buffers.flushBeforeSuite(logger);
    }

    public static void resetForTests() {
        // only for testing purposes
    }
}
