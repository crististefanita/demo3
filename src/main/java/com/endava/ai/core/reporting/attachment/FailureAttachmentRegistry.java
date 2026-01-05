package com.endava.ai.core.reporting.attachment;

public class FailureAttachmentRegistry {

    private static FailureAttachmentHandler handler;

    private FailureAttachmentRegistry() {
    }

    public static void register(FailureAttachmentHandler h) {
        handler = h;
    }

    public static void onTestStart() {
        if (handler != null) {
            handler.onTestStart();
        }
    }

    public static void onTestFailure() {
        if (handler != null) {
            handler.onTestFailure();
        }
    }

    public static void clearForTests() {
        handler = null;
    }
}