package com.endava.ai.core.reporting.attachment;

public interface FailureAttachmentHandler {

    void onTestFailure();

    default void onTestStart() {
    }
}