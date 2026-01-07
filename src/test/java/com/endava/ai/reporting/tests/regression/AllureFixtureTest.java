package com.endava.ai.reporting.tests.regression;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.reporting.util.AllureReportLogger;
import com.endava.ai.reporting.util.FakeAllureLifecycle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class AllureFixtureTest {

    private FakeAllureLifecycle allure;
    private AllureReportLogger logger;

    @BeforeMethod
    public void setUp() {
        ConfigManager.set("reporting.engine", "allure");
        allure = new FakeAllureLifecycle();
        logger = new AllureReportLogger(allure);
        StepLogger.setDelegate(logger);
    }

    @AfterMethod
    public void tearDown() {
        StepLogger.clear();
        ConfigManager.clear("reporting.engine");
    }

    @Test
    public void before_fixture_is_started_and_closed() {

        logger.startBeforeFixture("beforeMethod");
        logger.stopBeforeFixture("beforeMethod");

        assertEquals(
                List.of(
                        "START_FIXTURE(before:beforeMethod)",
                        "STOP_FIXTURE(before:beforeMethod)"
                ),
                allure.eventsAsStrings()
        );
    }

    @Test
    public void after_fixture_is_started_and_closed() {

        logger.startTest("test", null);
        logger.endTest("PASSED");

        logger.startAfterFixture("afterMethod");
        logger.stopAfterFixture("afterMethod");

        assertEquals(
                List.of(
                        "START_TEST(test)",
                        "STOP_TEST(PASSED)",
                        "START_FIXTURE(after:afterMethod)",
                        "STOP_FIXTURE(after:afterMethod)"
                ),
                allure.eventsAsStrings()
        );
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void nested_steps_are_not_allowed_even_in_fixture() {

        logger.startBeforeFixture("beforeMethod");
        StepLogger.startStep("outer");
        StepLogger.startStep("inner");
    }

    @Test
    public void context_is_cleared_between_tests() {

        ConfigManager.set("reporting.engine", "allure");

        FakeAllureLifecycle allure = new FakeAllureLifecycle();
        AllureReportLogger logger = new AllureReportLogger(allure);
        StepLogger.setDelegate(logger);

        logger.startTest("test1", null);
        logger.endTest("PASSED");

        logger.startTest("test2", null);
        logger.endTest("PASSED");

        assertEquals(
                List.of(
                        "START_TEST(test1)",
                        "STOP_TEST(PASSED)",
                        "START_TEST(test2)",
                        "STOP_TEST(PASSED)"
                ),
                allure.eventsAsStrings()
        );
    }

}
