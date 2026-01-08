package com.endava.ai.reporting.attachment;

import com.endava.ai.core.listener.TestListener;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.adapters.AllureAdapter;
import com.endava.ai.core.reporting.adapters.ExtentAdapter;
import com.endava.ai.core.reporting.attachment.FailureAttachmentHandler;
import com.endava.ai.core.reporting.attachment.FailureAttachmentRegistry;
import org.testng.Assert;
import org.testng.TestNG;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.List;

@SuppressWarnings("ALL")
public class FailureAttachmentRegistryTest {

    private TestFailureHandler handler;

    @BeforeMethod
    void setUp() {
        handler = new TestFailureHandler();
        FailureAttachmentRegistry.clearForTests();
        ReportingManager.reset();
        FailureAttachmentRegistry.register(handler);
    }

    @AfterMethod
    void tearDown() {
        FailureAttachmentRegistry.clearForTests();
        ReportingManager.reset();
    }

    // ---------- BASIC REGISTRY CONTRACT ----------

    @Test
    public void handler_is_called_on_failure() {
        FailureAttachmentRegistry.onTestFailure(new RuntimeException("boom"));
        Assert.assertEquals(handler.calls, 1);
    }

    @Test
    public void registry_is_safe_without_handler() {
        FailureAttachmentRegistry.clearForTests();
        FailureAttachmentRegistry.onTestFailure(new RuntimeException("boom"));
        Assert.assertTrue(true); // no exception
    }

    // ---------- ENGINE-SPECIFIC ----------

    @Test
    public void extent_triggers_failure_attachment() {
        ReportingManager.setLoggerForTests(ExtentAdapter.getInstance());
        FailureAttachmentRegistry.onTestFailure(new RuntimeException("boom"));
        Assert.assertEquals(handler.calls, 1);
    }

    @Test
    public void allure_triggers_failure_attachment() {
        ReportingManager.setLoggerForTests(AllureAdapter.getInstance());
        FailureAttachmentRegistry.onTestFailure(new RuntimeException("boom"));
        Assert.assertEquals(handler.calls, 1);
    }

    @Test
    public void multiple_failures_trigger_multiple_attachments() {
        ReportingManager.setLoggerForTests(AllureAdapter.getInstance());

        FailureAttachmentRegistry.onTestFailure(new RuntimeException("boom1"));
        FailureAttachmentRegistry.onTestFailure(new RuntimeException("boom2"));

        Assert.assertEquals(handler.calls, 2);
    }

    // ---------- REAL TestNG FLOW ----------

    @Test
    public void attachments_are_not_triggered_for_expected_exceptions() {

        ReportingManager.setLoggerForTests(AllureAdapter.getInstance());

        XmlSuite suite = new XmlSuite();
        suite.setName("suite");

        XmlTest test = new XmlTest(suite);
        test.setName("ui-tests");
        test.setXmlClasses(List.of(
                new XmlClass(ExpectedFailingTestA.class),
                new XmlClass(ExpectedFailingTestB.class)
        ));

        TestNG testng = new TestNG();
        testng.setXmlSuites(List.of(suite));
        testng.setUseDefaultListeners(false);
        testng.addListener(new TestListener());
        testng.setVerbose(0);

        testng.run();

        Assert.assertEquals(
                handler.calls,
                0,
                "Attachments must NOT be triggered for expectedExceptions"
        );
    }

    // ---------- SUPPORT ----------

    private static final class TestFailureHandler implements FailureAttachmentHandler {
        private int calls = 0;

        @Override
        public void onTestFailure() {
            calls++;
        }
    }

    public static class ExpectedFailingTestA {
        @Test(expectedExceptions = AssertionError.class)
        public void fails_as_expected() {
            Assert.fail("expected");
        }
    }

    public static class ExpectedFailingTestB {
        @Test(expectedExceptions = AssertionError.class)
        public void fails_as_expected() {
            Assert.fail("expected");
        }
    }
}

