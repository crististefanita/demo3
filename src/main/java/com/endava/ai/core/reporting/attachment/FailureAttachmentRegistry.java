package com.endava.ai.core.reporting.attachment;

public final class FailureAttachmentRegistry {

    private static FailureAttachmentHandler handler;
    private static final ThreadLocal<Throwable> FAILURE = new ThreadLocal<>();

    private FailureAttachmentRegistry() {
    }

    public static void register(FailureAttachmentHandler h) {
        handler = h;
    }

    public static void onTestStart() {
        FAILURE.remove();
        if (handler != null) {
            handler.onTestStart();
        }
    }

    public static void onTestFailure(Throwable t) {
        FAILURE.set(t);
        if (handler != null) {
            handler.onTestFailure();
        }
    }

    public static Throwable getFailure() {
        return FAILURE.get();
    }

    public static void clearForTests() {
        FAILURE.remove();
        handler = null;
    }
}
