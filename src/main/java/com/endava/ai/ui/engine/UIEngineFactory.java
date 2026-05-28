package com.endava.ai.ui.engine;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.ui.engine.playwright.PlaywrightEngine;
import com.endava.ai.ui.engine.selenium.SeleniumEngine;
import com.endava.ai.ui.engine.window.UIWindowConfiguration;
import com.endava.ai.ui.engine.window.UIWindowSizeResolver;

/*
Test start
  ↓
UIEngineFactory.create()
  ↓
UIEngine.start()
    ├─ init driver / browser
    ├─ APPLY semantic window configuration
    └─ engine ready
  ↓
Test steps
  ↓
Screenshots
 */
public final class UIEngineFactory {
    private UIEngineFactory() {}

    /**
     * Supported ui.engine values: selenium, playwright, default (default maps to selenium)
     */
    public static UIEngine create() {
        String v = ConfigManager.require("ui.engine").toLowerCase();
        UIWindowConfiguration windowConfiguration = UIWindowSizeResolver.resolve();
        switch (v) {
            case "playwright":
                return new PlaywrightEngine(windowConfiguration);
            case "selenium":
            case "default":
                return new SeleniumEngine(windowConfiguration);
            default:
                throw new IllegalArgumentException("Unsupported ui.engine: " + v);
        }
    }
}
