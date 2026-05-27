package com.endava.ai.reporting.window;

import com.endava.ai.ui.engine.UIEngine;
import com.endava.ai.ui.engine.window.UIWindowConfiguration;
import com.endava.ai.ui.engine.window.UIWindowSizeResolver;
import com.endava.ai.ui.engine.window.WindowMode;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UIWindowSizeResolverContractTest {

    @Test
    public void explicit_window_size_overrides_window_mode_and_system_size() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve("1920x1080", "fullscreen", "67", () -> new int[]{3840, 2160});

        Assert.assertEquals(resolved.mode(), WindowMode.CUSTOM_SIZE);
        Assert.assertEquals(resolved.width(), 1920);
        Assert.assertEquals(resolved.height(), 1080);
        Assert.assertEquals(resolved.zoomPercent(), 67);
    }

    @Test
    public void fullscreen_mode_returns_semantic_fullscreen_configuration() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve(null, "fullscreen", "100", () -> new int[]{3000, 2000});

        Assert.assertEquals(resolved.mode(), WindowMode.FULLSCREEN);
        Assert.assertEquals(resolved.width(), 3000);
        Assert.assertEquals(resolved.height(), 2000);
        Assert.assertEquals(resolved.zoomPercent(), 100);
    }

    @Test
    public void invalid_window_size_falls_back_to_semantic_fullscreen_configuration() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve("invalid-size", "fullscreen", "100", () -> new int[]{3000, 2000});

        Assert.assertEquals(resolved.mode(), WindowMode.FULLSCREEN);
        Assert.assertEquals(resolved.width(), 3000);
        Assert.assertEquals(resolved.height(), 2000);
    }

    @Test
    public void missing_system_resolution_falls_back_to_default_fullscreen_configuration() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve(null, "fullscreen", "100", () -> null);

        Assert.assertEquals(resolved.mode(), WindowMode.FULLSCREEN);
        Assert.assertEquals(resolved.width(), UIEngine.DEFAULT_WINDOW_WIDTH);
        Assert.assertEquals(resolved.height(), UIEngine.DEFAULT_WINDOW_HEIGHT);
    }

    @Test
    public void empty_window_size_is_treated_as_missing() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve("", "fullscreen", "100", () -> new int[]{3200, 1800});

        Assert.assertEquals(resolved.mode(), WindowMode.FULLSCREEN);
        Assert.assertEquals(resolved.width(), 3200);
        Assert.assertEquals(resolved.height(), 1800);
    }

    @Test
    public void blank_window_size_is_treated_as_missing() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve("   ", "fullscreen", "100", () -> new int[]{3200, 1800});

        Assert.assertEquals(resolved.mode(), WindowMode.FULLSCREEN);
        Assert.assertEquals(resolved.width(), 3200);
        Assert.assertEquals(resolved.height(), 1800);
    }

    @Test
    public void configurable_zoom_percent_is_used_when_value_is_valid() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve(null, "fullscreen", "67", () -> new int[]{3000, 2000});

        Assert.assertEquals(resolved.mode(), WindowMode.FULLSCREEN);
        Assert.assertEquals(resolved.zoomPercent(), 67);
    }

    @Test
    public void unsupported_zoom_percent_is_mapped_to_nearest_native_browser_zoom_level() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve(null, "fullscreen", "60", () -> new int[]{3000, 2000});

        Assert.assertEquals(resolved.zoomPercent(), 67);
    }

    @Test
    public void unsupported_zoom_percent_prefers_nearest_lower_native_browser_zoom_level_when_closer() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve(null, "fullscreen", "84", () -> new int[]{3000, 2000});

        Assert.assertEquals(resolved.zoomPercent(), 80);
    }

    @Test
    public void invalid_zoom_percent_falls_back_to_default_percent() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve(null, "fullscreen", "abc", () -> new int[]{3000, 2000});

        Assert.assertEquals(resolved.zoomPercent(), 100);
    }

    @Test
    public void out_of_range_zoom_percent_falls_back_to_default_percent() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve(null, "fullscreen", "5", () -> new int[]{3000, 2000});

        Assert.assertEquals(resolved.zoomPercent(), 100);
    }

    @Test
    public void null_zoom_percent_falls_back_to_default_percent() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve(null, "fullscreen", null, () -> new int[]{3000, 2000});

        Assert.assertEquals(resolved.zoomPercent(), 100);
    }

    @Test
    public void blank_zoom_percent_falls_back_to_default_percent() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve(null, "fullscreen", "   ", () -> new int[]{3000, 2000});

        Assert.assertEquals(resolved.zoomPercent(), 100);
    }

    @Test
    public void invalid_mode_falls_back_to_semantic_fullscreen_behavior() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve(null, "unknown-mode", "100", () -> new int[]{3000, 2000});

        Assert.assertEquals(resolved.mode(), WindowMode.FULLSCREEN);
        Assert.assertEquals(resolved.width(), 3000);
        Assert.assertEquals(resolved.height(), 2000);
    }

    @Test
    public void null_mode_falls_back_to_semantic_fullscreen_behavior() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve(null, null, "100", () -> new int[]{3000, 2000});

        Assert.assertEquals(resolved.mode(), WindowMode.FULLSCREEN);
        Assert.assertEquals(resolved.width(), 3000);
        Assert.assertEquals(resolved.height(), 2000);
    }

    @Test
    public void invalid_supplier_value_falls_back_to_default_fullscreen_configuration() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve(null, "fullscreen", "100", () -> new int[]{-1, 2000});

        Assert.assertEquals(resolved.mode(), WindowMode.FULLSCREEN);
        Assert.assertEquals(resolved.width(), UIEngine.DEFAULT_WINDOW_WIDTH);
        Assert.assertEquals(resolved.height(), UIEngine.DEFAULT_WINDOW_HEIGHT);
    }

    @Test
    public void invalid_supplier_array_shape_falls_back_to_default_fullscreen_configuration() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve(null, "fullscreen", "100", () -> new int[]{3000});

        Assert.assertEquals(resolved.mode(), WindowMode.FULLSCREEN);
        Assert.assertEquals(resolved.width(), UIEngine.DEFAULT_WINDOW_WIDTH);
        Assert.assertEquals(resolved.height(), UIEngine.DEFAULT_WINDOW_HEIGHT);
    }

    @Test
    public void headless_exception_falls_back_to_default_fullscreen_configuration() {
        UIWindowConfiguration resolved =
                UIWindowSizeResolver.resolve(null, "fullscreen", "100", () -> {
                    throw new java.awt.HeadlessException("headless");
                });

        Assert.assertEquals(resolved.mode(), WindowMode.FULLSCREEN);
        Assert.assertEquals(resolved.width(), UIEngine.DEFAULT_WINDOW_WIDTH);
        Assert.assertEquals(resolved.height(), UIEngine.DEFAULT_WINDOW_HEIGHT);
    }
}
