package com.endava.ai.reporting.tests.lifecycle;

import com.endava.ai.core.listener.TestListener;
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
public class BeforeClassMustAttachToOwnTest {

    @Listeners(TestListener.class)
    public static class ClassA {

        @BeforeClass
        public void beforeClass() {
            StepLogger.startStep("A beforeClass");
            StepLogger.logDetail("A.beforeClass.detail");
            StepLogger.pass("ok");
        }

        @Test
        public void testA() {
            StepLogger.startStep("A test");
            StepLogger.logDetail("A.test.detail");
            StepLogger.pass("ok");
        }
    }

    @Listeners(TestListener.class)
    public static class ClassB {

        @BeforeClass
        public void beforeClass() {
            StepLogger.startStep("B beforeClass");
            StepLogger.logDetail("B.beforeClass.detail");
            StepLogger.pass("ok");
        }

        @Test
        public void testB() {
            StepLogger.startStep("B test");
            StepLogger.logDetail("B.test.detail");
            StepLogger.pass("ok");
        }
    }

    @Test
    public void beforeClass_details_must_attach_to_own_test() {

        RecordingReportingLogger logger = new RecordingReportingLogger();
        ReportingManager.reset();
        ReportingManager.setLoggerForTests(logger);

        XmlSuite suite = new XmlSuite();
        suite.setName("suite");

        XmlTest test = new XmlTest(suite);
        test.setName("test");
        test.setXmlClasses(List.of(
                new XmlClass(ClassA.class),
                new XmlClass(ClassB.class)
        ));

        TestNG testng = new TestNG();
        testng.setXmlSuites(List.of(suite));
        testng.addListener(new TestListener());
        testng.setVerbose(0);
        testng.run();

        Assert.assertTrue(
                logger.detailsPerTest.get("testA").contains("A.beforeClass.detail"),
                "A.beforeClass must attach to testA"
        );

        Assert.assertFalse(
                logger.detailsPerTest.get("testA").contains("B.beforeClass.detail"),
                "B.beforeClass must NOT attach to testA"
        );

        Assert.assertTrue(
                logger.detailsPerTest.get("testB").contains("B.beforeClass.detail"),
                "B.beforeClass must attach to testB"
        );
    }
}
