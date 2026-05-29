package com.endava.ai.atf.ui.component;

import com.endava.ai.core.listener.NoFrameworkReporting;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.ui.pages.LoginPage;
import com.endava.ai.ui.pages.RegisterPage;
import com.endava.ai.ui.validation.RegistrationValidation;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

@NoFrameworkReporting
public class RegistrationValidationContractTest {

    @AfterMethod
    public void tearDown() {
        StepLogger.clear();
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(null);
    }

    @Test
    public void redirected_to_login_contract_waits_for_url_and_login_button() {
        SequencedTextUiEngine engine = new SequencedTextUiEngine()
                .withCurrentUrl("https://practicesoftwaretesting.com/auth/login");
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(engine);

        new RegistrationValidation().assertRedirectedToLogin();

        Assert.assertEquals(engine.waitedUrlFragment, "/auth/login");
        Assert.assertEquals(engine.waitedUrlSeconds, 5);
        Assert.assertEquals(engine.waitedVisibleSelector, LoginPage.LOGIN_BUTTON);
        Assert.assertEquals(engine.waitedVisibleSeconds, 15);
    }

    @Test
    public void any_error_validation_retries_until_error_text_is_present() {
        SequencedTextUiEngine engine = new SequencedTextUiEngine()
                .withTextSequence(RegisterPage.ANY_ERROR, "", "", "Email already exists");
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(engine);

        new RegistrationValidation().assertAnyErrorVisibleOrPresent();

        Assert.assertTrue(
                engine.getTextCallCount(RegisterPage.ANY_ERROR) >= 3,
                "Expected validation to poll for the generic registration error"
        );
    }

    public void password_error_validation_waits_for_expected_message_fragment() {
        SequencedTextUiEngine engine = new SequencedTextUiEngine()
                .withTextSequence(RegisterPage.PASSWORD_ERROR, "", "Password must be longer than 6 characters");
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(engine);

        new RegistrationValidation().assertPasswordErrorContains("longer than 6");

        Assert.assertTrue(
                engine.getTextCallCount(RegisterPage.PASSWORD_ERROR) >= 2,
                "Expected validation to retry until the password message contained the expected fragment"
        );
    }

    @Test
    public void phone_error_validation_surfaces_clear_assertion_when_expected_text_never_appears() {
        SequencedTextUiEngine engine = new SequencedTextUiEngine()
                .withTextSequence(RegisterPage.PHONE_ERROR, "", "");
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(engine);

        AssertionError error = Assert.expectThrows(
                AssertionError.class,
                () -> new RegistrationValidation().assertPhoneErrorContains("Phone number is invalid")
        );

        Assert.assertTrue(
                error.getMessage().contains("Phone validation message"),
                "Expected failure message to identify the phone validation contract"
        );
    }

    private static final class SequencedTextUiEngine extends StubUiEngine {
        private final Map<String, Deque<String>> textSequences = new HashMap<>();
        private final Map<String, Integer> textCallCounts = new HashMap<>();
        private String currentUrl = "https://practicesoftwaretesting.com/auth/register";
        private String waitedUrlFragment;
        private int waitedUrlSeconds;
        private String waitedVisibleSelector;
        private int waitedVisibleSeconds;

        private SequencedTextUiEngine withCurrentUrl(String currentUrl) {
            this.currentUrl = currentUrl;
            return this;
        }

        private SequencedTextUiEngine withTextSequence(String locator, String... values) {
            Deque<String> sequence = new ArrayDeque<>();
            for (String value : values) {
                sequence.addLast(value);
            }
            textSequences.put(locator, sequence);
            return this;
        }

        private int getTextCallCount(String locator) {
            return textCallCounts.getOrDefault(locator, 0);
        }

        @Override
        public String getText(String cssSelector) {
            textCallCounts.merge(cssSelector, 1, Integer::sum);
            Deque<String> sequence = textSequences.get(cssSelector);
            if (sequence == null || sequence.isEmpty()) {
                return "";
            }
            if (sequence.size() > 1) {
                return sequence.removeFirst();
            }
            return sequence.peekFirst();
        }

        @Override
        public boolean isVisible(String cssSelector) {
            return true;
        }

        @Override
        public void waitForVisible(String cssSelector, int seconds) {
            waitedVisibleSelector = cssSelector;
            waitedVisibleSeconds = seconds;
        }

        @Override
        public void waitForUrlContains(String fragment, int seconds) {
            waitedUrlFragment = fragment;
            waitedUrlSeconds = seconds;
        }

        @Override
        public String getCurrentUrl() {
            return currentUrl;
        }

    }
}
