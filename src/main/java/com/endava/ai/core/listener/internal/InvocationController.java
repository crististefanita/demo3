package com.endava.ai.core.listener.internal;

import com.endava.ai.core.reporting.ReportLogger;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import org.testng.IInvokedMethod;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

public final class InvocationController {

    private final StepBufferRegistry buffers;
    private final LifecycleFlusher flusher;
    private final TestExecutionState state;

    public InvocationController(StepBufferRegistry buffers, LifecycleFlusher flusher, TestExecutionState state) {
        this.buffers = buffers;
        this.flusher = flusher;
        this.state = state;
    }

    public void beforeInvocation(IInvokedMethod method) {
        if (!method.isConfigurationMethod()) return;

        ITestNGMethod m = method.getTestMethod();
        if (m == null) return;

        StepLogger.setDelegate(resolveDelegate(m));
    }

    public void afterInvocation(IInvokedMethod method, ITestResult tr) {
        if (method.isTestMethod()) {
            afterTestMethod(tr);
            StepLogger.clearDelegate();
            return;
        }

        if (!method.isConfigurationMethod()) return;

        ITestNGMethod m = method.getTestMethod();

        // TEARDOWN for @AfterMethod must be logged HERE (not in afterTestMethod)
        if (m != null && m.isAfterMethodConfiguration()) {
            ReportLogger logger = ReportingManager.tryGetLogger();
            if (logger != null && !state.isFlowMode()) {
                GroupController g = new GroupController(logger);

                if (!state.isAfterOpen()) {
                    g.open("TEARDOWN");
                    state.openAfter();
                }

                g.open("@AfterMethod");
                flusher.flushAfterMethod(logger);
                g.close(); // closes only @AfterMethod node
            }
        }

        StepLogger.clearDelegate();
    }

    /**
     * Runs right after the @Test method returns.
     * Responsibility: close TEST BODY so that @AfterMethod cannot end up under it.
     */
    @SuppressWarnings("unused")
    private void afterTestMethod(ITestResult tr) {
        if (state.isFlowMode()) return;

        ReportLogger logger = ReportingManager.tryGetLogger();
        if (logger == null) return;

        if (state.isTestOpen()) {
            GroupController g = new GroupController(logger);
            g.closeCurrent(); // closes TEST BODY
            state.closeTest();
        }
    }

    private ReportLogger resolveDelegate(ITestNGMethod m) {
        ReportLogger real = ReportingManager.tryGetLogger();
        if (real == null) return null;

        if (m.isBeforeMethodConfiguration()) {
            StepBufferLogger b = buffers.beforeMethod();
            if (b != null) b.clear();
            return b;
        }
        if (m.isAfterMethodConfiguration()) {
            StepBufferLogger a = buffers.afterMethod();
            if (a != null) a.clear();
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
}
