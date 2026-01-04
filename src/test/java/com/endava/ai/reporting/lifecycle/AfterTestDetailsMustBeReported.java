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
public class AfterTestDetailsMustBeReported {

    @Listeners(TestListener.class)
    public static class TestClass {

        @Test
        public void testA() {
            StepLogger.startStep("test");
            StepLogger.logDetail("test.detail");
            StepLogger.pass("ok");
        }

        @AfterTest
        public void afterTest() {
            StepLogger.startStep("afterTest");
            StepLogger.logDetail("afterTest.detail");
            StepLogger.pass("ok");
        }
    }

    @Test
    public void after_test_details_must_be_reported() {

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

        Assert.assertTrue(
                logger.infoMessages.contains("afterTest.detail"),
                "@AfterTest details are lost and not reported"
        );
    }
}
