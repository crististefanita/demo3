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
public class BeforeClassAndBeforeTestBusinessStepsMustAttachToTests {

    @Listeners(TestListener.class)
    public static class ClassWithBeforeClass {

        @BeforeClass
        public void beforeClass() {
            StepLogger.startStep("create user");
            StepLogger.logDetail("POST /users");
            StepLogger.pass("created");
        }

        @Test
        public void delete_user_1() {
            StepLogger.startStep("delete user");
            StepLogger.logDetail("DELETE /users/{id}");
            StepLogger.pass("deleted");
        }
    }

    @Listeners(TestListener.class)
    public static class ClassWithBeforeTest {

        @BeforeTest
        public void beforeTest() {
            StepLogger.startStep("create user");
            StepLogger.logDetail("POST /users");
            StepLogger.pass("created");
        }

        @Test
        public void delete_user_2() {
            StepLogger.startStep("delete user");
            StepLogger.logDetail("DELETE /users/{id}");
            StepLogger.pass("deleted");
        }
    }

    @Test
    public void before_class_and_before_test_steps_attach_to_tests() {

        RecordingReportingLogger logger = new RecordingReportingLogger();
        ReportingManager.reset();
        ReportingManager.setLoggerForTests(logger);

        XmlSuite suite = new XmlSuite();
        suite.setName("suite");

        XmlTest test = new XmlTest(suite);
        test.setName("test");
        test.setXmlClasses(List.of(
                new XmlClass(ClassWithBeforeClass.class),
                new XmlClass(ClassWithBeforeTest.class)
        ));

        TestNG testng = new TestNG();
        testng.setXmlSuites(List.of(suite));
        testng.addListener(new TestListener());
        testng.setVerbose(0);
        testng.run();

        List<String> test1Details = logger.detailsPerTest.get("delete_user_1");
        List<String> test2Details = logger.detailsPerTest.get("delete_user_2");

        Assert.assertNotNull(test1Details);
        Assert.assertNotNull(test2Details);

        Assert.assertTrue(
                test1Details.contains("POST /users"),
                "delete_user_1 must contain create user step from @BeforeClass"
        );

        Assert.assertTrue(
                test2Details.contains("POST /users"),
                "delete_user_2 must contain create user step from @BeforeTest"
        );

        Assert.assertTrue(
                test1Details.indexOf("POST /users") < test1Details.indexOf("DELETE /users/{id}"),
                "@BeforeClass step must appear before delete_user_1 step"
        );

        Assert.assertTrue(
                test2Details.indexOf("POST /users") < test2Details.indexOf("DELETE /users/{id}"),
                "@BeforeTest step must appear before delete_user_2 step"
        );

        ReportingManager.reset();
    }
}
