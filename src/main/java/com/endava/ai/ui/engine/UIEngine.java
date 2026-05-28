package com.endava.ai.ui.engine;

public interface UIEngine {
    int DEFAULT_WINDOW_WIDTH = 2560;
    int DEFAULT_WINDOW_HEIGHT = 1440;

    boolean supportsAutoWait();

    void open(String url);

    void click(String cssSelector);

    void type(String cssSelector, String text);

    void select(String cssSelector, String valueOrText);

    String getText(String cssSelector);

    String getValue(String cssSelector);

    boolean isVisible(String cssSelector);

    void waitForVisible(String cssSelector, int seconds);

    void waitForUrlContains(String fragment, int seconds);

    String getCurrentUrl();

    void clearSession();

    String captureScreenshotAsBase64();

    void quit();

    void setWindowSize(int width, int height);
}
