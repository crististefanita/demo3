package com.endava.ai.reporting.tests.lifecycle;

import com.endava.ai.core.listener.TestListener;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.reporting.util.RecordingReportingLogger;
import com.endava.ai.core.reporting.StepLogger;
import org.testng.Assert;
import org.testng.TestNG;
import org.testng.annotations.*;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.List;

@SuppressWarnings("ALL")
public class BeforeClassMustAppearBeforeBeforeMethod {

    @Listeners(TestListener.class)
    public static class ClassWithLifecycle {

        @BeforeClass
        public void beforeClass() {
            StepLogger.startStep("beforeClass step");
            StepLogger.logDetail("beforeClass.detail");
            StepLogger.pass("ok");
        }

        @BeforeMethod
        public void beforeMethod() {
            StepLogger.startStep("beforeMethod step");
            StepLogger.logDetail("beforeMethod.detail");
            StepLogger.pass("ok");
        }

        @Test
        public void test() {
            StepLogger.startStep("test step");
            StepLogger.logDetail("test.detail");
            StepLogger.pass("ok");
        }
    }

    @Test
    public void beforeClass_steps_must_appear_before_beforeMethod_steps() {

        RecordingReportingLogger logger = new RecordingReportingLogger();
        ReportingManager.reset();
        ReportingManager.setLoggerForTests(logger);

        XmlSuite suite = new XmlSuite();
        suite.setName("suite");

        XmlTest test = new XmlTest(suite);
        test.setName("test");
        test.setXmlClasses(List.of(
                new XmlClass(ClassWithLifecycle.class)
        ));

        TestNG testng = new TestNG();
        testng.setXmlSuites(List.of(suite));
        testng.addListener(new TestListener());
        testng.setVerbose(0);
        testng.run();

        List<String> details = logger.detailsPerTest.get("test");

        int beforeClassIndex = details.indexOf("beforeClass.detail");
        int beforeMethodIndex = details.indexOf("beforeMethod.detail");

        Assert.assertTrue(
                beforeClassIndex != -1 && beforeMethodIndex != -1,
                "Both beforeClass and beforeMethod details must be present"
        );

        Assert.assertTrue(
                beforeClassIndex < beforeMethodIndex,
                "beforeClass steps must appear before beforeMethod steps in report"
        );
    }
}
