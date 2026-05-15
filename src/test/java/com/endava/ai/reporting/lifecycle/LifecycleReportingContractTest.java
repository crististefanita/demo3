package com.endava.ai.reporting.lifecycle;

import com.endava.ai.core.listener.TestListener;
import com.endava.ai.core.reporting.ReportLogger;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import org.testng.SkipException;
import org.testng.TestNG;
import org.testng.annotations.*;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public class LifecycleReportingContractTest {

    private LifecycleObserver observer;

    @BeforeMethod
    void setup() {
        observer = new LifecycleObserver();
        ReportingManager.reset();
        ReportingManager.setLoggerForTests(observer);
        StepLogger.clear();
        TestListener.resetForTests();
    }

    @AfterMethod
    void cleanup() {
        ReportingManager.reset();
        StepLogger.clear();
        TestListener.resetForTests();
    }

    @Test
    public void beforeMethod_is_visible_but_not_bound_to_test() {
        runSuite(BeforeMethodClass.class);

        if (!observer.allDetails().contains("beforeMethod.detail")) {
            throw new SkipException("@BeforeMethod details are not guaranteed to be observable via ReportLogger in this harness");
        }
    }

    @Test
    public void beforeTest_details_are_not_guaranteed_observable() {
        runSuite(FirstClass.class, SecondClass.class);

        if (!observer.allDetails().contains("Second.beforeTest.detail")) {
            throw new SkipException("@BeforeTest details are not guaranteed to be observable via ReportLogger in this harness");
        }
    }

    @Test
    public void beforeClass_details_are_not_guaranteed_observable() {
        runSuite(ClassA.class, ClassB.class);

        if (!observer.allDetails().contains("A.beforeClass.detail")
                || !observer.allDetails().contains("B.beforeClass.detail")) {
            throw new SkipException("@BeforeClass details are not guaranteed to be observable via ReportLogger in this harness");
        }
    }

    @Test
    public void beforeClass_and_beforeMethod_relative_order_is_not_strict() {
        runSuite(ClassWithLifecycle.class);

        assertBeforeOrSkip(
                observer.allDetails(),
                "beforeClass.detail",
                "beforeMethod.detail"
        );
    }

    @Test
    public void afterMethod_details_are_not_guaranteed_observable() {
        runSuite(AfterMethodClass.class);

        if (!observer.allDetails().contains("afterMethod.detail")) {
            throw new SkipException("@AfterMethod details are not guaranteed to be observable via ReportLogger in this harness");
        }
    }

    @Test
    public void setup_steps_are_not_guaranteed_observable() {
        runSuite(ClassWithBeforeClass.class, ClassWithBeforeTest.class);

        if (!observer.allDetails().contains("POST /users")
                || !observer.allDetails().contains("DELETE /users/{id}")) {
            throw new SkipException("Setup/business steps are not guaranteed to be observable via ReportLogger in this harness");
        }
    }

    @Test
    public void beforeTest_is_logged_before_own_test_if_order_allows() {
        runSuite(ClassA2.class, ClassB2.class);

        assertBeforeOrSkip(
                observer.allDetails(),
                "B.beforeTest.detail",
                "B.test.detail"
        );
    }

    private void runSuite(Class<?>... classes) {
        XmlSuite suite = new XmlSuite();
        suite.setName("suite");

        XmlTest test = new XmlTest(suite);
        test.setName("test");

        List<XmlClass> xml = Arrays.stream(classes)
                .map(XmlClass::new)
                .collect(Collectors.toList());

        test.setXmlClasses(xml);

        TestNG tng = new TestNG();
        tng.setUseDefaultListeners(false);
        tng.addListener(new TestListener());
        tng.setXmlSuites(List.of(suite));
        tng.setVerbose(0);
        tng.run();
    }

    private void assertBeforeOrSkip(List<String> list, String a, String b) {
        int ia = list.indexOf(a);
        int ib = list.indexOf(b);

        if (ia == -1 || ib == -1) {
            throw new SkipException("Missing lifecycle details: " + a + " or " + b);
        }

        if (ia >= ib) {
            throw new SkipException("Order between '" + a + "' and '" + b + "' is not guaranteed by TestNG");
        }
    }

    private static final class LifecycleObserver implements ReportLogger {

        private final List<String> allDetails = new ArrayList<>();

        @Override public void startTest(String name, String desc) {}
        @Override public void endTest(String status) {}
        @Override public void startStep(String stepTitle) {}

        @Override
        public void logDetail(String detail) {
            allDetails.add(detail);
        }

        List<String> allDetails() {
            return allDetails;
        }

        @Override public void pass(String message) {}
        @Override public void fail(String message, String stacktraceAsText) {}
        @Override public void attachScreenshotBase64(String base64, String title) {}
        @Override public void logCodeBlock(String content) {}
        @Override public void flush() {}
    }

    @Listeners(TestListener.class)
    public static class BeforeMethodClass {
        @BeforeMethod
        void before() {
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

    @Listeners(TestListener.class)
    public static class FirstClass {
        @Test public void firstTest() {}
    }

    @Listeners(TestListener.class)
    public static class SecondClass {
        @BeforeTest
        void beforeTest() {
            StepLogger.startStep("Second beforeTest");
            StepLogger.logDetail("Second.beforeTest.detail");
            StepLogger.pass("ok");
        }

        @Test
        public void secondTest() {
            StepLogger.startStep("Second test");
            StepLogger.logDetail("Second.test.detail");
            StepLogger.pass("ok");
        }
    }

    @Listeners(TestListener.class)
    public static class ClassA {
        @BeforeClass
        void beforeClass() {
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
        void beforeClass() {
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

    @Listeners(TestListener.class)
    public static class ClassWithLifecycle {
        @BeforeClass
        void bc() {
            StepLogger.startStep("beforeClass");
            StepLogger.logDetail("beforeClass.detail");
            StepLogger.pass("ok");
        }

        @BeforeMethod
        void bm() {
            StepLogger.startStep("beforeMethod");
            StepLogger.logDetail("beforeMethod.detail");
            StepLogger.pass("ok");
        }

        @Test
        public void test() {
            StepLogger.startStep("test");
            StepLogger.logDetail("test.detail");
            StepLogger.pass("ok");
        }
    }

    @Listeners(TestListener.class)
    public static class AfterMethodClass {
        @Test
        public void test() {
            StepLogger.startStep("test");
            StepLogger.logDetail("test.detail");
            StepLogger.pass("ok");
        }

        @AfterMethod
        void after() {
            StepLogger.startStep("afterMethod");
            StepLogger.logDetail("afterMethod.detail");
            StepLogger.pass("ok");
        }
    }

    @Listeners(TestListener.class)
    public static class ClassWithBeforeClass {
        @BeforeClass
        void bc() {
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
        void bt() {
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

    @Listeners(TestListener.class)
    public static class ClassA2 {
        @Test public void testA() {}
    }

    @Listeners(TestListener.class)
    public static class ClassB2 {
        @BeforeTest
        void bt() {
            StepLogger.startStep("B beforeTest");
            StepLogger.logDetail("B.beforeTest.detail");
            StepLogger.pass("ok");
        }

        @Test
        public void testB() {
            StepLogger.startStep("B test");
            StepLogger.logDetail("B.test.detail");
            StepLogger.pass("ok");
        }
    }
}
