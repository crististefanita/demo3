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
public class BeforeMethodMustNotBleedBetweenTests {

    @Listeners(TestListener.class)
    public static class TestClass {

        @BeforeMethod
        public void beforeMethod() {
            StepLogger.startStep("beforeMethod");
            StepLogger.logDetail("beforeMethod.detail");
            StepLogger.pass("ok");
        }

        @Test
        public void testA() {
            StepLogger.startStep("testA");
            StepLogger.logDetail("testA.detail");
            StepLogger.pass("ok");
        }

        @Test
        public void testB() {
            StepLogger.startStep("testB");
            StepLogger.logDetail("testB.detail");
            StepLogger.pass("ok");
        }
    }

    @Test
    public void before_method_must_attach_exactly_once_per_test() {

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

        List<String> testADetails = logger.detailsPerTest.get("testA");
        List<String> testBDetails = logger.detailsPerTest.get("testB");

        Assert.assertNotNull(testADetails, "Missing testA");
        Assert.assertNotNull(testBDetails, "Missing testB");

        long countA = testADetails.stream()
                .filter(d -> d.equals("beforeMethod.detail"))
                .count();

        long countB = testBDetails.stream()
                .filter(d -> d.equals("beforeMethod.detail"))
                .count();

        Assert.assertEquals(
                countA,
                1,
                "beforeMethod must attach exactly once to testA"
        );

        Assert.assertEquals(
                countB,
                1,
                "beforeMethod must attach exactly once to testB"
        );
    }
}
