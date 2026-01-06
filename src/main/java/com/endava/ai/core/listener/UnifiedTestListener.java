package com.endava.ai.core.listener;

import com.endava.ai.core.reporting.ReportLogger;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import org.testng.*;

public final class UnifiedTestListener
        implements ITestListener, IInvokedMethodListener, ISuiteListener {

    private final TestContext context = new TestContext();
    private final LifecycleBuffer buffer = new LifecycleBuffer();

    @Override
    public void onStart(ISuite suite) {
        context.reset();
        buffer.clear();
        StepLogger.clear();
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult result) {
        if (!method.isConfigurationMethod()) return;
        ITestNGMethod m = method.getTestMethod();
        ReportLogger delegate = buffer.resolveBefore(m);
        if (delegate != null) StepLogger.setDelegate(delegate);
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        if (!method.isConfigurationMethod()) return;
        ITestNGMethod m = method.getTestMethod();
        ReportLogger delegate = buffer.resolveAfter(m);
        if (delegate != null) StepLogger.setDelegate(delegate);
        StepLogger.clearDelegate();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ReportLogger logger = ReportingManager.getLogger();
        Class<?> cls = result.getMethod().getRealClass();

        if (context.isTestOpen() && context.getActiveClass() != cls) {
            logger.endTest(context.isReportFailed() ? "FAIL" : "PASS");
            context.closeTest();
            context.resetFailure();
        }

        if (!context.isTestOpen()) {
            logger.startTest(cls.getSimpleName(), null);
            context.openTest();
        }

        context.setActiveClass(cls);

        logger.startStep(result.getMethod().getMethodName());
        buffer.flushBefore(logger);
        logger.startStep("TEST");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        closeTestBody();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        context.markFailed();
        if (result.getThrowable() != null)
            StepLogger.failUnhandledOutsideStep(result.getThrowable());
        closeTestBody();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result.getThrowable() != null)
            StepLogger.failUnhandledOutsideStep(result.getThrowable());
        closeTestBody();
    }

    private void closeTestBody() {
        ReportLogger logger = ReportingManager.getLogger();
        logger.pass("");
        buffer.flushAfter(logger);
        logger.pass("");
    }

    @Override
    public void onFinish(ISuite suite) {
        ReportLogger logger = ReportingManager.getLogger();
        if (context.isTestOpen()) {
            logger.endTest(context.isReportFailed() ? "FAIL" : "PASS");
        }
        logger.flush();
        context.reset();
        buffer.clear();
    }
}
