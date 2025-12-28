package com.endava.ai.core.reporting;

import com.endava.ai.core.config.ConfigManager;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exclusive step/node logging helper.
 * ONLY caller of ReportLogger.fail().
 * Enforces:
 * - exactly one failure per step
 * - StepLogger.logDetail forbidden outside active steps
 * - any step execution requires test started by TestListener
 * - stacktraces rendered once and only as code blocks (via adapter logCodeBlock)
 */
public final class StepLogger {
    private static final ThreadLocal<StepState> STATE = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> TEST_STARTED = new ThreadLocal<>();

    private StepLogger() {}

    public static void markTestStarted() {
        TEST_STARTED.set(Boolean.TRUE);
    }

    public static void clearTestStarted() {
        TEST_STARTED.remove();
        STATE.remove();
    }

    public static void startStep(String title) {
        requireTestStarted();
        ReportLogger logger = ReportingManager.getLogger();
        logger.startStep(title);
        STATE.set(StepState.STARTED);
        console("▶ " + title);
    }

    public static void logDetail(String detail) {
        requireActiveStep();
        ReportingManager.getLogger().logDetail(detail);
        // Details are intentionally not echoed to console (requirements: console logging for step start and PASS/FAIL only).

        if (ConfigManager.getBoolean("console.details.enabled")) {
            console("  • " + detail);
        }
    }

    public static void pass(String message) {
        requireActiveStep();
        if (STATE.get() == StepState.FAILED) return; // do not duplicate
        ReportingManager.getLogger().pass(message);
        STATE.set(StepState.PASSED);
        console("  ✅ PASS: " + message);
        STATE.remove();
    }

    public static void fail(String message, Throwable t) {
        fail(message, stacktraceToString(t));
    }

    public static void fail(String message, String stacktraceAsText) {
        requireActiveStep();
        if (STATE.get() == StepState.FAILED) return; // forbid duplicate failure/stacktrace rendering
        ReportingManager.getLogger().fail(message, stacktraceAsText);
        STATE.set(StepState.FAILED);
        console("  ❌ FAIL: " + message);
        // End of step context after failure
        STATE.remove();
    }

    public static void failUnhandledOutsideStep(Throwable t) {
        // For any exception outside StepLogger-controlled steps: fail the TEST at TEST level.
        // We cannot create a step node here without violating "no info outside step node", so we:
        // - create a synthetic step to host failure details
        startStep("Unhandled exception outside StepLogger-controlled step");
        fail("Unhandled exception outside step", stacktraceToString(t));
    }

    private static void requireTestStarted() {
        Boolean started = TEST_STARTED.get();
        if (started == null || !started) {
            throw new IllegalStateException("Test not started. TestListener must start the test before steps run.");
        }
    }

    private static void requireActiveStep() {
        requireTestStarted();
        StepState st = STATE.get();
        if (st == null) {
            throw new IllegalStateException("No active step. StepLogger.logDetail/pass/fail must be called within an active step.");
        }
    }

    private static void console(String msg) {
        System.out.println(msg);
    }

    private static String stacktraceToString(Throwable t) {
        if (t == null) return "";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    private enum StepState { STARTED, PASSED, FAILED }
}
