package com.endava.ai.ui.engine.window;

public final class UIWindowConfiguration {
    public static final int DEFAULT_ZOOM_PERCENT = 100;

    private final WindowMode mode;
    private final int width;
    private final int height;
    private final int zoomPercent;

    private UIWindowConfiguration(WindowMode mode, int width, int height, int zoomPercent) {
        if (mode == null) {
            throw new IllegalArgumentException("Window mode must not be null");
        }
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Window dimensions must be positive");
        }
        if (zoomPercent <= 0) {
            throw new IllegalArgumentException("Zoom percent must be positive");
        }
        this.mode = mode;
        this.width = width;
        this.height = height;
        this.zoomPercent = zoomPercent;
    }

    public static UIWindowConfiguration fullscreen(int fallbackWidth, int fallbackHeight, int zoomPercent) {
        return new UIWindowConfiguration(WindowMode.FULLSCREEN, fallbackWidth, fallbackHeight, zoomPercent);
    }

    public static UIWindowConfiguration customSize(int width, int height, int zoomPercent) {
        return new UIWindowConfiguration(WindowMode.CUSTOM_SIZE, width, height, zoomPercent);
    }

    public WindowMode mode() {
        return mode;
    }

    public boolean isFullscreen() {
        return mode == WindowMode.FULLSCREEN;
    }

    public boolean isCustomSize() {
        return mode == WindowMode.CUSTOM_SIZE;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public int zoomPercent() {
        return zoomPercent;
    }
}
