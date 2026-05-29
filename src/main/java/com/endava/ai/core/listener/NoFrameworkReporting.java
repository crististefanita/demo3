package com.endava.ai.core.listener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Opts a test class out of the framework-managed reporting lifecycle.
 * Useful for ATF self-tests that validate reporting/wait/helper contracts
 * and should not create real Allure/Extent nodes during normal execution.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NoFrameworkReporting {
}
