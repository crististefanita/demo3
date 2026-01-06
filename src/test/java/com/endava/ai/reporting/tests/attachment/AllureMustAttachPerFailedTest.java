package com.endava.ai.reporting.tests.attachment;

import com.endava.ai.core.listener.TestListener;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.adapters.AllureAdapter;
import com.endava.ai.core.reporting.attachment.FailureAttachmentRegistry;
import com.endava.ai.reporting.util.RecordingFailureAttachmentHandler;
import org.testng.Assert;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.List;

@SuppressWarnings("ALL")
public class AllureMustAttachPerFailedTest {

    @Test
    public void failure_attachments_are_triggered_only_for_real_failures() {

        RecordingFailureAttachmentHandler handler =
                new RecordingFailureAttachmentHandler();

        FailureAttachmentRegistry.clearForTests();
        FailureAttachmentRegistry.register(handler);

        ReportingManager.reset();
        ReportingManager.setLoggerForTests(AllureAdapter.getInstance());

        XmlSuite suite = new XmlSuite();
        suite.setName("suite");

        XmlTest test = new XmlTest(suite);
        test.setName("ui-tests");
        test.setXmlClasses(List.of(
                new XmlClass(FailingTestA.class),
                new XmlClass(FailingTestB.class)
        ));

        TestNG testng = new TestNG();
        testng.setXmlSuites(List.of(suite));
        testng.setUseDefaultListeners(false);
        testng.addListener(new TestListener());
        testng.setVerbose(0);

        testng.run();

        Assert.assertEquals(
                handler.calls.get(),
                0,
                "Failure attachments must NOT be triggered for tests with expectedExceptions"
        );
    }

    public static class FailingTestA {

        @Test(expectedExceptions = AssertionError.class)
        public void ui_test_a_fails() {
            Assert.fail("UI failure A");
        }
    }

    public static class FailingTestB {

        @Test(expectedExceptions = AssertionError.class)
        public void ui_test_b_fails() {
            Assert.fail("UI failure B");
        }
    }
}
