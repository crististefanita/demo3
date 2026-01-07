package com.endava.ai.reporting.tests.attachment;


import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.adapters.ExtentAdapter;
import com.endava.ai.core.reporting.attachment.FailureAttachmentRegistry;
import com.endava.ai.reporting.util.RecordingFailureAttachmentHandler;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class ExtentFailureAttachmentTest {

    @AfterMethod
    void cleanup() {
        FailureAttachmentRegistry.clearForTests();
        ReportingManager.reset();
    }

    @Test
    public void extent_triggers_failure_attachment_handler() {
        RecordingFailureAttachmentHandler handler =
                new RecordingFailureAttachmentHandler();

        FailureAttachmentRegistry.register(handler);
        ReportingManager.setLoggerForTests(ExtentAdapter.getInstance());

        FailureAttachmentRegistry.onTestFailure(new RuntimeException("boom"));

        Assert.assertEquals(handler.calls.get(), 1);
    }
}
