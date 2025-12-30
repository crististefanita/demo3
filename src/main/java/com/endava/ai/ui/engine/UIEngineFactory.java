package com.endava.ai.ui.engine;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.ui.engine.playwright.PlaywrightEngine;
import com.endava.ai.ui.engine.selenium.SeleniumEngine;

/*
Test start
  ↓
UIEngineFactory.create()
  ↓
UIEngine.start()
    ├─ init driver / browser
    ├─ SET window size (2560×1440)
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
        String v = ConfigManager.require("ui.engine").toLowerCase().trim();
        UIEngine engine;
        switch (v) {
            case "playwright":
                engine = new PlaywrightEngine();
                break;
            case "selenium":
            case "default":
                engine = new SeleniumEngine();
                break;
            default:
                throw new IllegalArgumentException("Unsupported ui.engine: " + v);
        }
        engine.standardizeWindow();
        return engine;
    }
}
