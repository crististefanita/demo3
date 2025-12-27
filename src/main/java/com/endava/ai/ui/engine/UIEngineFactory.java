package com.endava.ai.ui.engine;

import com.endava.ai.ui.config.ConfigManager;
import com.endava.ai.ui.engine.playwright.PlaywrightEngine;
import com.endava.ai.ui.engine.selenium.SeleniumEngine;

public final class UIEngineFactory {
    private UIEngineFactory() {}

    /**
     * Supported ui.engine values: selenium, playwright, default (default maps to selenium)
     */
    public static UIEngine create() {
        String v = ConfigManager.require("ui.engine").toLowerCase().trim();
        switch (v) {
            case "playwright":
                return new PlaywrightEngine();
            case "selenium":
            case "default":
                return new SeleniumEngine();
            default:
                throw new IllegalArgumentException("Unsupported ui.engine: " + v + " (supported: selenium, playwright, default)");
        }
    }
}
