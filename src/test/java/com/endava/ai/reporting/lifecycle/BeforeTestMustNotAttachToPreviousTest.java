package com.endava.ai.reporting.lifecycle;

import com.endava.ai.core.TestListener;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.reporting.util.RecordingReportingLogger;
import org.testng.Assert;
import org.testng.TestNG;
import org.testng.annotations.*;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.List;

@SuppressWarnings("ALL")
public class BeforeTestMustNotAttachToPreviousTest {

    @Listeners(TestListener.class)
    public static class FirstClass {

        @BeforeMethod
        public void beforeMethod() {
        }

        @Test
        public void firstTest() {
        }
    }

    @Listeners(TestListener.class)
    public static class SecondClass {

        @BeforeTest
        public void beforeTest() {
            com.endava.ai.core.reporting.StepLogger.startStep("Second beforeTest");
            com.endava.ai.core.reporting.StepLogger.logDetail("Second.beforeTest.detail");
            com.endava.ai.core.reporting.StepLogger.pass("ok");
        }

        @Test
        public void secondTest() {
            com.endava.ai.core.reporting.StepLogger.startStep("Second test");
            com.endava.ai.core.reporting.StepLogger.logDetail("Second.test.detail");
            com.endava.ai.core.reporting.StepLogger.pass("ok");
        }
    }

    @Test
    public void beforeTest_details_must_be_logged_before_own_test() {

        RecordingReportingLogger logger = new RecordingReportingLogger();
        ReportingManager.reset();
        ReportingManager.setLoggerForTests(logger);

        XmlSuite suite = new XmlSuite();
        suite.setName("suite");

        XmlTest test = new XmlTest(suite);
        test.setName("test");
        test.setXmlClasses(List.of(
                new XmlClass(FirstClass.class),
                new XmlClass(SecondClass.class)
        ));

        TestNG testng = new TestNG();
        testng.setXmlSuites(List.of(suite));
        testng.addListener(new TestListener());
        testng.setVerbose(0);
        testng.run();

        int idxBeforeTest = logger.infoMessages.indexOf("Second.beforeTest.detail");
        int idxSecondTest = logger.infoMessages.indexOf("Second.test.detail");

        Assert.assertTrue(idxBeforeTest != -1, "Missing @BeforeTest details");
        Assert.assertTrue(idxSecondTest != -1, "Missing test details");

        Assert.assertTrue(
                idxBeforeTest < idxSecondTest,
                "@BeforeTest details must appear before the test of the same class"
        );
    }
}
