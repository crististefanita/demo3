package com.endava.ai.ui.pages;

public final class ProfilePage {

    private ProfilePage() {
    }

    public static final String ROOT = "app-profile";
    public static final String FIRST_NAME = "input[data-test='first-name']";
    public static final String LAST_NAME = "input[data-test='last-name']";
    public static final String EMAIL = "input[data-test='email']";
    public static final String PHONE = "input[data-test='phone']";
    public static final String STREET = "input[data-test='street']";
    public static final String CITY = "input[data-test='city']";
    public static final String STATE = "input[data-test='state']";
    public static final String COUNTRY = "select[data-test='country']";
    public static final String POSTAL_CODE = "input[data-test='postal_code']";
    public static final String CURRENT_PASSWORD = "input[data-test='current-password']";
    public static final String NEW_PASSWORD = "input[data-test='new-password']";
    public static final String NEW_PASSWORD_CONFIRM = "input[data-test='new-password-confirm']";
    public static final String CHANGE_PASSWORD_SUBMIT = "button[data-test='change-password-submit']";
    public static final String PASSWORD_SUCCESS = "div.alert.alert-success";
}
