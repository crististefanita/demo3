package com.endava.ai.reporting.lifecycle;

import com.endava.ai.core.TestListener;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.reporting.util.RecordingReportingLogger;
import org.testng.Assert;
import org.testng.TestNG;
import org.testng.annotations.*;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.List;

@SuppressWarnings("ALL")
public class AfterMethodDetailsMustAttachToSameTest {

    @Listeners(TestListener.class)
    public static class TestClass {

        @Test
        public void testA() {
            StepLogger.startStep("test");
            StepLogger.logDetail("test.detail");
            StepLogger.pass("ok");
        }

        @AfterMethod
        public void afterMethod() {
            StepLogger.startStep("afterMethod");
            StepLogger.logDetail("afterMethod.detail");
            StepLogger.pass("ok");
        }
    }

    @Test
    public void after_method_details_must_be_attached_to_test() {

        RecordingReportingLogger logger = new RecordingReportingLogger();
        ReportingManager.reset();
        ReportingManager.setLoggerForTests(logger);

        XmlSuite suite = new XmlSuite();
        suite.setName("suite");

        XmlTest test = new XmlTest(suite);
        test.setName("test");
        test.setXmlClasses(List.of(new XmlClass(TestClass.class)));

        TestNG testng = new TestNG();
        testng.setXmlSuites(List.of(suite));
        testng.addListener(new TestListener());
        testng.setVerbose(0);
        testng.run();

        int idxTest = logger.infoMessages.indexOf("test.detail");
        int idxAfter = logger.infoMessages.indexOf("afterMethod.detail");

        Assert.assertTrue(idxTest != -1, "Missing test details");
        Assert.assertTrue(idxAfter != -1, "Missing @AfterMethod details");

        Assert.assertTrue(
                idxAfter > idxTest,
                "@AfterMethod details must appear after test details"
        );
    }
}
