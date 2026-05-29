package com.endava.ai.atf.ui.component;

import com.endava.ai.core.listener.NoFrameworkReporting;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.ui.utils.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;

@NoFrameworkReporting
public class WaitUtilsContractTest {

    @AfterMethod
    public void tearDown() {
        StepLogger.clear();
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(null);
    }

    @Test
    public void wait_until_returns_first_matching_value_before_timeout() {
        AtomicInteger attempts = new AtomicInteger();

        Integer value = WaitUtils.waitUntil(
                attempts::incrementAndGet,
                current -> current >= 3,
                Duration.ofMillis(50),
                Duration.ofMillis(1)
        );

        Assert.assertEquals(value, Integer.valueOf(3));
        Assert.assertEquals(attempts.get(), 3);
    }

    @Test
    public void wait_until_returns_last_observed_value_when_timeout_expires() {
        AtomicInteger attempts = new AtomicInteger();

        Integer value = WaitUtils.waitUntil(
                attempts::incrementAndGet,
                current -> false,
                Duration.ofMillis(20),
                Duration.ofMillis(1)
        );

        Assert.assertTrue(attempts.get() >= 1, "Expected waitUntil to evaluate the supplier at least once");
        Assert.assertEquals(value, Integer.valueOf(attempts.get()));
    }

    @Test
    public void wait_for_input_value_retries_until_predicate_matches() {
        SequencedValueUiEngine engine = new SequencedValueUiEngine("", "ready@example.com");
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(engine);

        WaitUtils.waitForInputValue(
                "#email",
                "email field",
                value -> value.contains("@"),
                Duration.ofMillis(600)
        );

        Assert.assertEquals(engine.getValueCalls, 2);
    }

    @Test
    public void wait_for_condition_returns_when_boolean_supplier_eventually_matches() {
        AtomicInteger attempts = new AtomicInteger();

        WaitUtils.waitForCondition(
                "counter reaches two",
                () -> attempts.incrementAndGet() >= 2,
                "Expected counter to reach two"
        );

        Assert.assertEquals(attempts.get(), 2);
    }

    public void wait_for_condition_throws_assertion_error_with_custom_message_when_condition_never_matches() {
        AssertionError error = Assert.expectThrows(
                AssertionError.class,
                () -> WaitUtils.waitForCondition(
                        "condition never matches",
                        () -> false,
                        "Expected condition to match"
                )
        );

        Assert.assertEquals(error.getMessage(), "Expected condition to match");
    }

    @Test
    public void wait_for_input_value_throws_assertion_error_when_predicate_never_matches() {
        SequencedValueUiEngine engine = new SequencedValueUiEngine("", "");
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(engine);

        AssertionError error = Assert.expectThrows(
                AssertionError.class,
                () -> WaitUtils.waitForInputValue(
                        "#email",
                        "email field",
                        value -> value.contains("@"),
                        Duration.ofMillis(10)
                )
        );

        Assert.assertTrue(
                error.getMessage().contains("Timed out waiting for input value: email field"),
                "Expected failure message to mention the timed out field"
        );
        Assert.assertTrue(engine.getValueCalls >= 1, "Expected at least one value read before timing out");
    }

    private static final class SequencedValueUiEngine extends StubUiEngine {
        private final Deque<String> values = new ArrayDeque<>();
        private String lastValue = "";
        private int getValueCalls;

        private SequencedValueUiEngine(String... values) {
            for (String value : values) {
                this.values.addLast(value);
                this.lastValue = value;
            }
        }

        @Override
        public String getValue(String cssSelector) {
            getValueCalls++;
            if (values.size() > 1) {
                lastValue = values.removeFirst();
                return lastValue;
            }
            if (!values.isEmpty()) {
                lastValue = values.peekFirst();
            }
            return lastValue;
        }

    }
}
