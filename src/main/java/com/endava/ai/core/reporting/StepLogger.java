package com.endava.ai.core.reporting;

import java.util.*;

import static com.endava.ai.core.config.ConfigManager.require;
import static com.endava.ai.core.reporting.utils.ConsoleLoger.console;
import static com.endava.ai.core.reporting.utils.ConsoleLoger.formatStacktrace;

public final class StepLogger {

    private static final ThreadLocal<StepState> STATE = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> TEST_STARTED = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> TEST_FAILED = new ThreadLocal<>();

    private static final Map<Object, Buffer> BEFORE = new LinkedHashMap<>();
    private static final Map<Object, Buffer> AFTER  = new LinkedHashMap<>();
    private static final Buffer PENDING = new Buffer();

    private static boolean IN_BEFORE;
    private static boolean IN_AFTER;
    private static Object CURRENT_SCOPE;

    private StepLogger() {}

    public static void markTestStarted() {
        TEST_STARTED.set(Boolean.TRUE);
        TEST_FAILED.set(Boolean.FALSE);
    }

    public static void markBodyFinished() {
    }

    public static void markTestEnded() {
        TEST_STARTED.set(Boolean.FALSE);
        STATE.remove();
    }

    public static void markBeforePhase(Object scope) {
        IN_BEFORE = true;
        CURRENT_SCOPE = scope;
        BEFORE.computeIfAbsent(scope, k -> new Buffer());
    }

    public static void clearBeforePhase() {
        IN_BEFORE = false;
        CURRENT_SCOPE = null;
    }

    public static void markAfterPhase(Object scope) {
        IN_AFTER = true;
        CURRENT_SCOPE = scope;
        AFTER.computeIfAbsent(scope, k -> new Buffer());
    }

    public static void clearAfterPhase() {
        IN_AFTER = false;
        CURRENT_SCOPE = null;
    }

    public static void flushBefore(Object scope) {
        Buffer b = BEFORE.remove(scope);
        if (b != null) b.flush();
    }

    public static void flushAfter(Object scope) {
        Buffer b = AFTER.remove(scope);
        if (b != null) b.flush();
    }

    public static void flushPending() {
        PENDING.flush();
        PENDING.clear();
    }

    public static void clearTestStarted() {
        TEST_STARTED.remove();
        STATE.remove();
        TEST_FAILED.remove();
        IN_BEFORE = false;
        IN_AFTER = false;
        CURRENT_SCOPE = null;
        BEFORE.clear();
        AFTER.clear();
        PENDING.clear();
    }

    public static boolean testHasFailed() {
        return Boolean.TRUE.equals(TEST_FAILED.get());
    }

    public static void startStep(String title) {
        if (STATE.get() != null) {
            throw new IllegalStateException("Cannot start a new step while another step is active.");
        }

        if (isInConfigPhase()) {
            currentBuffer().startBufferedStep(title);
        } else if (Boolean.TRUE.equals(TEST_STARTED.get())) {
            ReportingManager.getLogger().startStep(title);
        } else {
            currentBuffer().startBufferedStep(title);
        }

        STATE.set(StepState.STARTED);
        console("▶ " + title);
    }

    public static void logDetail(String detail) {
        requireActiveStep();

        if (isInConfigPhase()) {
            currentBuffer().addDetail(detail);
        } else if (Boolean.TRUE.equals(TEST_STARTED.get())) {
            ReportingManager.getLogger().logDetail(detail);
        } else {
            currentBuffer().addDetail(detail);
        }

        if (getBoolean("console.details.enabled")) {
            console("  • " + detail);
        }
    }

    public static void logCodeBlock(String content) {
        requireActiveStep();

        if (isInConfigPhase()) {
            currentBuffer().addCodeBlock(content);
        } else if (Boolean.TRUE.equals(TEST_STARTED.get())) {
            ReportingManager.getLogger().logCodeBlock(content);
        } else {
            currentBuffer().addCodeBlock(content);
        }
    }

    public static void pass(String message) {
        requireActiveStep();
        STATE.set(StepState.PASSED);
        console("  ✅ PASS: " + message);
        if (isBufferingNow()) currentBuffer().endBufferedStep();
        STATE.remove();
    }

    public static void fail(String message, Throwable t) {
        fail(message, formatStacktrace(t));
    }

    public static void fail(String message, String stacktraceAsText) {
        requireActiveStep();
        TEST_FAILED.set(Boolean.TRUE);
        STATE.set(StepState.FAILED);
        console("  ❌ FAIL: " + message);
        if (isBufferingNow()) currentBuffer().endBufferedStep();
        STATE.remove();
    }

    public static void failUnhandledOutsideStep(Throwable t) {
        if (testHasFailed()) return;
        if (!Boolean.TRUE.equals(TEST_STARTED.get())) return;

        startStep("Unhandled exception outside step");
        fail("Unhandled exception outside step", t);
    }

    private static void requireActiveStep() {
        if (STATE.get() == null) {
            throw new IllegalStateException(
                    "No active step. StepLogger.logDetail/pass/fail must be called within an active step."
            );
        }
    }

    private static boolean isInConfigPhase() {
        return IN_BEFORE || IN_AFTER;
    }

    private static boolean isBufferingNow() {
        return isInConfigPhase() || !Boolean.TRUE.equals(TEST_STARTED.get());
    }

    private static Buffer currentBuffer() {
        if (IN_BEFORE) return BEFORE.get(CURRENT_SCOPE);
        if (IN_AFTER) return AFTER.get(CURRENT_SCOPE);
        return PENDING;
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(require(key));
    }

    private enum StepState {
        STARTED, PASSED, FAILED
    }

    private static final class Buffer {
        private final List<BufferedStep> steps = new ArrayList<>();
        private BufferedStep current;

        void startBufferedStep(String title) {
            current = new BufferedStep(title);
            steps.add(current);
        }

        void addDetail(String detail) {
            if (current == null) throw new IllegalStateException("No buffered step.");
            current.details.add(detail);
        }

        void addCodeBlock(String content) {
            if (current == null) throw new IllegalStateException("No buffered step.");
            current.codeBlocks.add(content);
        }

        void endBufferedStep() {
            current = null;
        }

        void flush() {
            for (BufferedStep s : steps) {
                ReportingManager.getLogger().startStep(s.title);
                s.details.forEach(ReportingManager.getLogger()::logDetail);
                s.codeBlocks.forEach(ReportingManager.getLogger()::logCodeBlock);
            }
        }

        void clear() {
            steps.clear();
            current = null;
        }
    }

    private static final class BufferedStep {
        final String title;
        final List<String> details = new ArrayList<>();
        final List<String> codeBlocks = new ArrayList<>();

        BufferedStep(String title) {
            this.title = title;
        }
    }
}
