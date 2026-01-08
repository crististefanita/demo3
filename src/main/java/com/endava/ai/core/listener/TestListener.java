package com.endava.ai.core.listener;

import com.endava.ai.core.listener.internal.*;
import com.endava.ai.core.reporting.ReportLogger;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.core.reporting.attachment.FailureAttachmentRegistry;
import com.endava.ai.core.reporting.internal.ReportingEngineCleanup;
import org.testng.*;

public final class TestListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

    private final TestContext context = new TestContext();
    private final StepBufferRegistry buffers = new StepBufferRegistry();
    private final TestLifecycleController lifecycle = new TestLifecycleController(context);
    private final FlowPolicy flowPolicy = new FlowPolicy();
    private final LifecycleFlusher flusher = new LifecycleFlusher(buffers);

    private final ThreadLocal<TestExecutionState> state =
            ThreadLocal.withInitial(TestExecutionState::new);

    private final ThreadLocal<InvocationController> invocations =
            ThreadLocal.withInitial(() -> new InvocationController(buffers, flusher, state.get()));

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(ReportingEngineCleanup::onShutdown));
    }

    @Override
    public void onStart(ISuite suite) {
        context.clear();
        buffers.clear();
        StepLogger.clear();
        flowPolicy.clear();
        state.remove();
        invocations.remove();
    }

    @Override
    public void onFinish(ISuite suite) {
        lifecycle.endSuite();
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult tr) {
        invocations.get().beforeInvocation(method);
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult tr) {
        invocations.get().afterInvocation(method, tr);
    }

    @Override
    public void onTestStart(ITestResult result) {
        StepLogger.clear();
        FailureAttachmentRegistry.onTestStart();

        lifecycle.startTest(result);

        ReportLogger logger = ReportingManager.tryGetLogger();
        if (logger == null) return;

        Class<?> cls = result.getMethod().getRealClass();
        String method = result.getMethod().getMethodName();

        TestExecutionState st = state.get();
        boolean firstForClass = context.isFirstMethodForClass(cls);

        if (firstForClass) {
            st.reset();
            if (flowPolicy.isFlowClass(result.getTestContext(), cls)) st.enableFlowMode();
        } else if (!st.isFlowMode()) {
            st.reset();
        }

        GroupController g = new GroupController(logger);

        if (st.isFlowMode()) {
            g.openIf(
                    () -> !st.isMethodGroupOpen(),
                    "FLOW",
                    () -> {
                        st.openMethodGroup();
                        flusher.flushBefore(logger, g, cls, true);
                        g.open("TEST BODY");
                        g.open(method);
                        st.openTest();
                    }
            );
            return;
        }

        g.open(method);
        st.openMethodGroup();

        flusher.flushBefore(logger, g, cls, firstForClass);

        g.open("TEST BODY");
        g.open(method);
        st.openTest();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        closeGroups(result);
        context.markTestEnded();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        endWithThrowable(result, true);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        endWithThrowable(result, false);
        context.markTestEnded();
    }

    private void endWithThrowable(ITestResult result, boolean failed) {
        Throwable t = result.getThrowable();

        FailureAttachmentRegistry.onTestFailure(t);
        if (failed) lifecycle.failTest(result);

        if (t != null) StepLogger.failUnhandledOutsideStep(t);

        closeGroups(result);
    }

    private void closeGroups(ITestResult result) {
        ReportLogger logger = ReportingManager.tryGetLogger();
        if (logger == null) return;

        TestExecutionState st = state.get();
        GroupController g = new GroupController(logger);
        Class<?> cls = result.getMethod().getRealClass();

        if (st.isTestOpen()) {
            g.closeCurrent();
            st.closeTest();
        }

        if (st.isMethodGroupOpen()) {
            g.closeCurrent();
            st.closeMethodGroup();
        }

        if (st.isAfterOpen() || flusher.hasAfterScopesContent(cls)) {
            if (!st.isAfterOpen()) {
                g.open("TEARDOWN");
                st.openAfter();
            }

            flusher.flushAfterScopes(logger, g, cls);
            g.closeCurrent();
            st.closeAfter();
        }
    }

    public static void resetForTests() {
        ReportingManager.reset();
        StepLogger.clear();
    }
}
