package com.endava.ai.atf.ui.component;

import com.endava.ai.core.listener.NoFrameworkReporting;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.ui.model.CustomerData;
import com.endava.ai.ui.pages.AccountPage;
import com.endava.ai.ui.pages.LoginPage;
import com.endava.ai.ui.pages.ProfilePage;
import com.endava.ai.ui.service.FavoritesService;
import com.endava.ai.ui.service.LoginService;
import com.endava.ai.ui.service.ProfileService;
import com.endava.ai.ui.validation.LoginValidation;
import com.endava.ai.ui.validation.ProfileValidation;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

@NoFrameworkReporting
public class AuthenticatedContinuationContractTest {

    @AfterMethod
    public void tearDown() {
        StepLogger.clear();
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(null);
    }

    @Test
    public void login_service_types_credentials_and_submits_login() {
        RecordingUiEngine engine = new RecordingUiEngine();
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(engine);

        new LoginService().login("user@example.com", "Secret123!");

        Assert.assertEquals(engine.typedValues.get(LoginPage.EMAIL), "user@example.com");
        Assert.assertEquals(engine.typedValues.get(LoginPage.PASSWORD), "Secret123!");
        Assert.assertEquals(engine.clickedSelector, LoginPage.LOGIN_BUTTON);
    }

    @Test
    public void authenticated_account_validation_waits_for_account_url_and_profile_navigation() {
        RecordingUiEngine engine = new RecordingUiEngine()
                .withCurrentUrl("https://practicesoftwaretesting.com/account");
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(engine);

        new LoginValidation().assertAuthenticatedAccountSurface();

        Assert.assertEquals(engine.waitedUrlFragment, "/account");
        Assert.assertEquals(engine.waitedUrlSeconds, 5);
        Assert.assertEquals(engine.waitedVisibleSelector, AccountPage.NAV_PROFILE);
        Assert.assertEquals(engine.waitedVisibleSeconds, 15);
    }

    @Test
    public void authenticated_account_failure_surfaces_login_context() {
        RecordingUiEngine engine = new RecordingUiEngine()
                .withCurrentUrl("https://practicesoftwaretesting.com/auth/login")
                .withVisibility(LoginPage.LOGIN_BUTTON, true)
                .withVisibility(AccountPage.NAV_PROFILE, false)
                .withTextSequence(LoginPage.LOGIN_ERROR, "Invalid email or password");
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(engine);

        AssertionError error = Assert.expectThrows(
                AssertionError.class,
                () -> new LoginValidation().assertAuthenticatedAccountSurface()
        );

        Assert.assertTrue(error.getMessage().contains("Current URL: https://practicesoftwaretesting.com/auth/login"));
        Assert.assertTrue(error.getMessage().contains("login error: Invalid email or password"));
    }

    @Test
    public void profile_navigation_clicks_profile_tab_and_waits_for_profile_surface() {
        RecordingUiEngine engine = new RecordingUiEngine();
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(engine);

        new ProfileService().openProfileFromAccount();

        Assert.assertEquals(engine.clickedSelector, AccountPage.NAV_PROFILE);
        Assert.assertEquals(engine.waitedVisibleSelector, ProfilePage.FIRST_NAME);
    }

    @Test
    public void change_password_workflow_types_current_new_and_confirm_then_submits() {
        RecordingUiEngine engine = new RecordingUiEngine();
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(engine);

        new ProfileService().changePassword("OldPassword!1", "NewPassword!2");

        Assert.assertEquals(engine.typedValues.get(ProfilePage.CURRENT_PASSWORD), "OldPassword!1");
        Assert.assertEquals(engine.typedValues.get(ProfilePage.NEW_PASSWORD), "NewPassword!2");
        Assert.assertEquals(engine.typedValues.get(ProfilePage.NEW_PASSWORD_CONFIRM), "NewPassword!2");
        Assert.assertEquals(engine.clickedSelector, ProfilePage.CHANGE_PASSWORD_SUBMIT);
    }

    @Test
    public void password_change_success_validation_retries_until_banner_text_is_present() {
        RecordingUiEngine engine = new RecordingUiEngine()
                .withTextSequence(ProfilePage.PASSWORD_SUCCESS, "", "Your password is successfully updated");
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(engine);

        new ProfileValidation().assertPasswordChangedSuccessfully();

        Assert.assertTrue(engine.getTextCallCount(ProfilePage.PASSWORD_SUCCESS) >= 2);
    }

    @Test
    public void profile_validation_retries_until_expected_values_materialize() {
        CustomerData customer = new CustomerData.Builder()
                .firstName("Ana")
                .lastName("Popescu")
                .street("Main Street")
                .postalCode("400100")
                .city("Cluj")
                .state("Cluj")
                .phone("0712345678")
                .email("ana@example.com")
                .build();

        RecordingUiEngine engine = new RecordingUiEngine()
                .withValueSequence(ProfilePage.FIRST_NAME, "", "Ana")
                .withValueSequence(ProfilePage.LAST_NAME, "", "Popescu")
                .withValueSequence(ProfilePage.EMAIL, "", "ana@example.com")
                .withValueSequence(ProfilePage.PHONE, "", "0712345678")
                .withValueSequence(ProfilePage.STREET, "", "Main Street")
                .withValueSequence(ProfilePage.CITY, "", "Cluj")
                .withValueSequence(ProfilePage.STATE, "", "Cluj")
                .withValueSequence(ProfilePage.POSTAL_CODE, "", "400100");
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(engine);

        new ProfileValidation().assertProfileMatches(customer);

        Assert.assertTrue(engine.getValueCallCount(ProfilePage.FIRST_NAME) >= 2);
        Assert.assertTrue(engine.getValueCallCount(ProfilePage.EMAIL) >= 2);
    }

    @Test
    public void open_profile_chain_waits_for_account_then_profile_then_loaded_value() {
        RecordingUiEngine engine = new RecordingUiEngine()
                .withValueSequence(ProfilePage.FIRST_NAME, "", "Ana");
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(engine);

        ProfileService service = new ProfileService();
        service.openProfile();
        new ProfileValidation().assertProfileLoaded();

        Assert.assertEquals(engine.openedUrl, "https://practicesoftwaretesting.com/account");
        Assert.assertEquals(engine.waitedUrlFragment, "/account");
        Assert.assertTrue(engine.clickedSelectors.contains(AccountPage.NAV_PROFILE));
        Assert.assertTrue(engine.getValueCallCount(ProfilePage.FIRST_NAME) >= 2);
    }

    @Test
    public void favorites_navigation_clicks_favorites_entry_from_account_surface() {
        RecordingUiEngine engine = new RecordingUiEngine();
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(engine);

        new FavoritesService().openFavoritesFromAccount();

        Assert.assertEquals(engine.clickedSelector, AccountPage.NAV_FAVORITES);
        Assert.assertTrue(engine.clickedSelectors.contains(AccountPage.NAV_FAVORITES));
    }

    @Test
    public void login_error_validation_retries_until_expected_error_materializes() {
        RecordingUiEngine engine = new RecordingUiEngine()
                .withTextSequence(LoginPage.LOGIN_ERROR, "", "Invalid email or password");
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(engine);

        new LoginValidation().assertLoginErrorContains("Invalid email or password");

        Assert.assertTrue(engine.getTextCallCount(LoginPage.LOGIN_ERROR) >= 2);
    }

    @Test
    public void still_on_login_assertion_uses_login_url_boundary() {
        RecordingUiEngine engine = new RecordingUiEngine()
                .withCurrentUrl("https://practicesoftwaretesting.com/auth/login");
        UiEngineThreadLocalTestSupport.setEngineForCurrentThread(engine);

        new LoginValidation().assertStillOnLogin();

        Assert.assertEquals(engine.getCurrentUrl(), "https://practicesoftwaretesting.com/auth/login");
    }

    private static final class RecordingUiEngine extends StubUiEngine {
        private final Map<String, String> typedValues = new HashMap<>();
        private final Map<String, Deque<String>> textSequences = new HashMap<>();
        private final Map<String, Integer> textCallCounts = new HashMap<>();
        private final Map<String, Deque<String>> valueSequences = new HashMap<>();
        private final Map<String, Integer> valueCallCounts = new HashMap<>();
        private final Map<String, Boolean> visibilityBySelector = new HashMap<>();
        private String currentUrl = "https://practicesoftwaretesting.com/auth/login";
        private String openedUrl;
        private String clickedSelector;
        private final Deque<String> clickedSelectors = new ArrayDeque<>();
        private String waitedUrlFragment;
        private int waitedUrlSeconds;
        private String waitedVisibleSelector;
        private int waitedVisibleSeconds;

        private RecordingUiEngine withCurrentUrl(String currentUrl) {
            this.currentUrl = currentUrl;
            return this;
        }

        private RecordingUiEngine withTextSequence(String locator, String... values) {
            Deque<String> sequence = new ArrayDeque<>();
            for (String value : values) {
                sequence.addLast(value);
            }
            textSequences.put(locator, sequence);
            return this;
        }

        private RecordingUiEngine withVisibility(String locator, boolean visible) {
            visibilityBySelector.put(locator, visible);
            return this;
        }

        private RecordingUiEngine withValueSequence(String locator, String... values) {
            Deque<String> sequence = new ArrayDeque<>();
            for (String value : values) {
                sequence.addLast(value);
            }
            valueSequences.put(locator, sequence);
            return this;
        }

        private int getTextCallCount(String locator) {
            return textCallCounts.getOrDefault(locator, 0);
        }

        private int getValueCallCount(String locator) {
            return valueCallCounts.getOrDefault(locator, 0);
        }

        @Override
        public void click(String cssSelector) {
            clickedSelector = cssSelector;
            clickedSelectors.addLast(cssSelector);
        }

        @Override
        public void type(String cssSelector, String text) {
            typedValues.put(cssSelector, text);
        }

        @Override
        public void open(String url) {
            openedUrl = url;
            currentUrl = url;
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
        public String getValue(String cssSelector) {
            valueCallCounts.merge(cssSelector, 1, Integer::sum);
            Deque<String> sequence = valueSequences.get(cssSelector);
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
            return visibilityBySelector.getOrDefault(cssSelector, true);
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
