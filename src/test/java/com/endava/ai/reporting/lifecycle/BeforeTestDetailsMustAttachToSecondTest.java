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
public class BeforeTestDetailsMustAttachToSecondTest {

    @Listeners(TestListener.class)
    public static class ClassA {

        @BeforeClass
        public void beforeClass() {
            StepLogger.startStep("A beforeClass");
            StepLogger.logDetail("A.beforeClass.detail");
            StepLogger.pass("ok");
        }

        @BeforeMethod
        public void beforeMethod() {
            StepLogger.startStep("A beforeMethod");
            StepLogger.logDetail("A.beforeMethod.detail");
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

        @BeforeTest
        public void beforeTest() {
            StepLogger.startStep("B beforeTest");
            StepLogger.logDetail("B.beforeTest.detail");
            StepLogger.pass("ok");
        }

        @BeforeMethod
        public void beforeMethod() {
            StepLogger.startStep("B beforeMethod");
            StepLogger.logDetail("B.beforeMethod.detail");
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
    public void beforeTest_details_must_be_attached_to_second_test() {

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

        int idxBeforeTest = logger.infoMessages.indexOf("B.beforeTest.detail");
        int idxTestB      = logger.infoMessages.indexOf("B.test.detail");

        Assert.assertTrue(
                idxBeforeTest != -1,
                "Missing details from @BeforeTest of ClassB"
        );

        Assert.assertTrue(
                idxTestB != -1,
                "Missing details from testB"
        );

        Assert.assertTrue(
                idxBeforeTest < idxTestB,
                "@BeforeTest details must appear BEFORE testB details"
        );
    }
}
