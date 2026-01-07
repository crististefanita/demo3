package com.endava.ai.reporting.tests.attachment;

import com.endava.ai.core.reporting.attachment.FailureAttachmentRegistry;
import com.endava.ai.reporting.util.RecordingFailureAttachmentHandler;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class FailureScreenshotRegistryTest {

    @AfterMethod
    void cleanup() {
        FailureAttachmentRegistry.clearForTests();
    }

    @Test
    public void handler_is_called_on_test_failure() {
        RecordingFailureAttachmentHandler handler =
                new RecordingFailureAttachmentHandler();

        FailureAttachmentRegistry.register(handler);

        FailureAttachmentRegistry.onTestFailure(new RuntimeException("boom"));

        Assert.assertEquals(handler.calls.get(), 1);
    }

    @Test
    public void registry_is_safe_without_handler() {
        FailureAttachmentRegistry.clearForTests();

        FailureAttachmentRegistry.onTestFailure(new RuntimeException("boom"));
    }
}
