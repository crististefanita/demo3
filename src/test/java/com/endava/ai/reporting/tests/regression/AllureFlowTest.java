package com.endava.ai.reporting.tests.regression;

import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.reporting.util.AllureReportLogger;
import com.endava.ai.reporting.util.FakeAllureLifecycle;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.testng.AssertJUnit.assertEquals;

public class AllureFlowTest {

    @Test
    public void allure_reporting_respects_fixtures_order() {

        FakeAllureLifecycle allure = new FakeAllureLifecycle();
        AllureReportLogger logger = new AllureReportLogger(allure);

        StepLogger.setDelegate(logger);

        // simulate @BeforeMethod
        logger.startBeforeFixture("beforeMethod");
        StepLogger.startStep("before");
        StepLogger.pass("ok");
        logger.stopBeforeFixture("beforeMethod");

        // simulate @Test
        logger.startTest("testMethod", "fake description");
        StepLogger.startStep("test");
        StepLogger.pass("ok");
        logger.endTest("PASSED");

        // simulate @AfterMethod
        logger.startAfterFixture("afterMethod");
        StepLogger.startStep("after");
        StepLogger.pass("ok");
        logger.stopAfterFixture("afterMethod");

        assertEquals(
                allure.getEvents()
                        .stream()
                        .map(Object::toString)
                        .collect(java.util.stream.Collectors.toList()),
                List.of(
                        "START_FIXTURE(before:beforeMethod)",
                        "START_STEP(before)",
                        "STOP_STEP(ok)",
                        "STOP_FIXTURE(before:beforeMethod)",

                        "START_TEST(testMethod)",
                        "START_STEP(test)",
                        "STOP_STEP(ok)",
                        "STOP_TEST(PASSED)",

                        "START_FIXTURE(after:afterMethod)",
                        "START_STEP(after)",
                        "STOP_STEP(ok)",
                        "STOP_FIXTURE(after:afterMethod)"
                )
        );
    }

    @Test
    public void fail_in_before_method_is_reported_as_fixture_failure() {

        FakeAllureLifecycle allure = new FakeAllureLifecycle();
        AllureReportLogger logger = new AllureReportLogger(allure);
        StepLogger.setDelegate(logger);

        // simulate @BeforeMethod
        logger.startBeforeFixture("beforeMethod");
        StepLogger.startStep("before");
        StepLogger.fail("boom", "stacktrace");
        logger.stopBeforeFixture("beforeMethod");

        // TestNG would NOT run @Test
        logger.startTest("testMethod", null);
        logger.endTest("FAILED");

        assertEquals(
                allure.getEvents().stream().map(Object::toString).collect(Collectors.toList()),
                List.of(
                        "START_FIXTURE(before:beforeMethod)",
                        "START_STEP(before)",
                        "FAIL(boom)",
                        "STOP_FIXTURE(before:beforeMethod)",

                        "START_TEST(testMethod)",
                        "STOP_TEST(FAILED)"
                )
        );
    }

    @Test
    public void fail_in_after_method_is_reported_as_fixture_failure() {

        FakeAllureLifecycle allure = new FakeAllureLifecycle();
        AllureReportLogger logger = new AllureReportLogger(allure);
        StepLogger.setDelegate(logger);

        // simulate test body
        logger.startTest("testMethod", null);
        StepLogger.startStep("test");
        StepLogger.pass("ok");
        logger.endTest("PASSED");

        // simulate @AfterMethod failure
        logger.startAfterFixture("afterMethod");
        StepLogger.startStep("after");
        StepLogger.fail("after boom", "stacktrace");
        logger.stopAfterFixture("afterMethod");

        assertEquals(
                allure.getEvents().stream().map(Object::toString).collect(Collectors.toList()),
                List.of(
                        "START_TEST(testMethod)",
                        "START_STEP(test)",
                        "STOP_STEP(ok)",
                        "STOP_TEST(PASSED)",

                        "START_FIXTURE(after:afterMethod)",
                        "START_STEP(after)",
                        "FAIL(after boom)",
                        "STOP_FIXTURE(after:afterMethod)"
                )
        );
    }

}
