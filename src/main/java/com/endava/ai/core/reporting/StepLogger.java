package com.endava.ai.core.reporting;

import static com.endava.ai.core.config.ConfigManager.require;
import static com.endava.ai.core.reporting.utils.ConsoleLoger.console;
import static com.endava.ai.core.reporting.utils.ConsoleLoger.formatStacktrace;

public final class StepLogger {

    private static final ThreadLocal<Integer> STEP_DEPTH = ThreadLocal.withInitial(() -> 0);
    private static final ThreadLocal<Boolean> TEST_FAILED = new ThreadLocal<>();
    private static final ThreadLocal<ReportLogger> DELEGATE = new ThreadLocal<>();

    private StepLogger() {
    }

    public static void setDelegate(ReportLogger logger) {
        DELEGATE.set(logger);
    }

    public static void clearDelegate() {
        DELEGATE.remove();
    }

    public static void clear() {
        STEP_DEPTH.remove();
        TEST_FAILED.remove();
        DELEGATE.remove();
    }

    public static boolean testHasFailed() {
        return Boolean.TRUE.equals(TEST_FAILED.get());
    }

    public static void startStep(String title) {
        STEP_DEPTH.set(STEP_DEPTH.get() + 1);
        console("▶ " + title);

        ReportLogger l = DELEGATE.get();
        if (l != null) l.startStep(title);
    }

    public static void logDetail(String detail) {
        requireActiveStep();

        ReportLogger l = DELEGATE.get();
        if (l != null) l.logDetail(detail);

        if (getBoolean("console.details.enabled")) console("  • " + detail);
    }

    public static void info(String detail) {
        logDetail(detail);
    }

    public static void logCodeBlock(String content) {
        requireActiveStep();
        ReportLogger l = DELEGATE.get();
        if (l != null) l.logCodeBlock(content);
    }

    public static void pass(String message) {
        requireActiveStep();
        console("  ✅ PASS: " + message);

        ReportLogger l = DELEGATE.get();
        if (l != null) l.pass(message);

        STEP_DEPTH.set(STEP_DEPTH.get() - 1);
    }

    public static void fail(String message, Throwable t) {
        fail(message, formatStacktrace(t));
    }

    public static void fail(String message, String stacktraceAsText) {
        requireActiveStep();
        TEST_FAILED.set(Boolean.TRUE);
        console("  ❌ FAIL: " + message);

        ReportLogger l = DELEGATE.get();
        if (l != null) l.fail(message, stacktraceAsText);

        STEP_DEPTH.set(STEP_DEPTH.get() - 1);
    }

    public static void failUnhandledOutsideStep(Throwable t) {
        if (testHasFailed()) return;

        TEST_FAILED.set(Boolean.TRUE);

        ReportLogger l = DELEGATE.get();
        if (l != null) {
            l.fail("Unhandled exception outside step", formatStacktrace(t));
            console("  ❌ FAIL: Unhandled exception outside step");
        }
    }

    private static void requireActiveStep() {
        if (STEP_DEPTH.get() <= 0)
            throw new IllegalStateException("No active step.");
    }

    @SuppressWarnings("ALL")
    private static boolean getBoolean(String key) {
        return Boolean.parseBoolean(require(key));
    }
}
