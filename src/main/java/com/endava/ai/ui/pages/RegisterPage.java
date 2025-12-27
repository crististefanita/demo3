package com.endava.ai.ui.pages;

public final class RegisterPage {
    private RegisterPage() {}

    // Inputs
    public static final String FIRST_NAME = "input[data-test='first-name']";
    public static final String LAST_NAME  = "input[data-test='last-name']";
    public static final String DOB        = "input[data-test='dob']";
    public static final String STREET     = "input[data-test='street']";
    public static final String POSTAL     = "input[data-test='postal_code']";
    public static final String CITY       = "input[data-test='city']";
    public static final String STATE      = "input[data-test='state']";
    public static final String COUNTRY    = "select[data-test='country']";
    public static final String PHONE      = "input[data-test='phone']";
    public static final String EMAIL      = "input[data-test='email']";
    public static final String PASSWORD   = "input[data-test='password']";

    // Submit
    public static final String REGISTER_BUTTON =
            "button[data-test='register-submit']";

    // Errors (generic + specific if needed)
    public static final String ANY_ERROR =
            "div.alert.alert-danger";

    public static final String FIRST_NAME_ERROR =
            "div[data-test='first-name-error']";
    public static final String LAST_NAME_ERROR =
            "div[data-test='last-name-error']";
    public static final String EMAIL_ERROR =
            "div[data-test='email-error']";
    public static final String PASSWORD_ERROR =
            "div[data-test='password-error']";
}
