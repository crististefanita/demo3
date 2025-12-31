package com.endava.ai.core.reporting;

import static com.endava.ai.core.config.ConfigManager.require;

public final class StepLogger {

    private static final ThreadLocal<StepState> STATE = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> TEST_STARTED = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> TEST_FAILED = new ThreadLocal<>();

    private static final int MAX_EXTERNAL_FRAMES = 3;
    private static final String INTERNAL_PACKAGE = "com.endava";

    private StepLogger() {
    }

    public static void markTestStarted() {
        TEST_STARTED.set(Boolean.TRUE);
        TEST_FAILED.set(Boolean.FALSE);
    }

    public static void clearTestStarted() {
        TEST_STARTED.remove();
        STATE.remove();
        TEST_FAILED.remove();
    }

    public static boolean testHasFailed() {
        return Boolean.TRUE.equals(TEST_FAILED.get());
    }

    public static void startStep(String title) {
        requireTestStarted();
        if (STATE.get() != null) {
            throw new IllegalStateException("Cannot start a new step while another step is active.");
        }
        ReportingManager.getLogger().startStep(title);
        STATE.set(StepState.STARTED);
        console("▶ " + title);
    }

    public static void logDetail(String detail) {
        requireActiveStep();
        ReportingManager.getLogger().logDetail(detail);

        if (getBoolean("console.details.enabled")) {
            console("  • " + detail);
        }
    }

    public static void pass(String message) {
        requireActiveStep();
        if (STATE.get() == StepState.FAILED) return;

        ReportingManager.getLogger().pass(message);
        STATE.set(StepState.PASSED);
        console("  ✅ PASS: " + message);
        STATE.remove();
    }

    public static void fail(String message, Throwable t) {
        fail(message, formatStacktrace(t));
    }

    public static void fail(String message, String stacktraceAsText) {
        requireActiveStep();
        if (STATE.get() == StepState.FAILED) return;

        TEST_FAILED.set(Boolean.TRUE);

        ReportingManager.getLogger().logDetail(message);
        ReportingManager.getLogger().fail(message, stacktraceAsText);
        STATE.set(StepState.FAILED);
        console("  ❌ FAIL: " + message);
        STATE.remove();
    }

    public static void failUnhandledOutsideStep(Throwable t) {
        if (testHasFailed()) return;

        startStep("Unhandled exception outside step");
        fail("Unhandled exception outside step", t);
    }

    private static void requireTestStarted() {
        Boolean started = TEST_STARTED.get();
        if (started == null || !started) {
            throw new IllegalStateException(
                    "Test not started. TestListener must start the test before steps run."
            );
        }
    }

    private static void requireActiveStep() {
        requireTestStarted();
        if (STATE.get() == null) {
            throw new IllegalStateException(
                    "No active step. StepLogger.logDetail/pass/fail must be called within an active step."
            );
        }
    }

    private static void console(String msg) {
        System.out.println(msg);
    }

    private static String formatStacktrace(Throwable t) {
        if (t == null) return "";

        Throwable root = findRootCause(t);
        StringBuilder sb = new StringBuilder();

        appendRootMessage(sb, root);
        appendRelevantFrames(sb, root);

        return sb.toString();
    }

    private static Throwable findRootCause(Throwable t) {
        Throwable current = t;
        while (current.getCause() != null && current.getCause() != current) {
            current = current.getCause();
        }
        return current;
    }

    private static void appendRootMessage(StringBuilder sb, Throwable root) {
        String msg = root.getMessage();
        if (msg == null) return;

        int nl = msg.indexOf('\n');
        sb.append(nl > 0 ? msg.substring(0, nl) : msg)
                .append("\n\n");
    }

    private static void appendRelevantFrames(StringBuilder sb, Throwable root) {
        int externalCount = 0;

        for (StackTraceElement e : root.getStackTrace()) {
            String cls = e.getClassName();

            if (cls.startsWith(INTERNAL_PACKAGE)) {
                sb.append("at ").append(e).append("\n");
                continue;
            }

            if (externalCount < MAX_EXTERNAL_FRAMES) {
                sb.append("at ").append(e).append("\n");
                externalCount++;
            }
        }
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(require(key));
    }

    private enum StepState {
        STARTED, PASSED, FAILED
    }
}
