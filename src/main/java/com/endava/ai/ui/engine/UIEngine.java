package com.endava.ai.ui.engine;

public interface UIEngine {
    boolean supportsAutoWait();

    void open(String url);

    void click(String cssSelector);

    void type(String cssSelector, String text);

    String getText(String cssSelector);

    boolean isVisible(String cssSelector);

    void waitForVisible(String cssSelector, int seconds);

    void waitForUrlContains(String fragment, int seconds);

    String getCurrentUrl();

    String captureScreenshotAsBase64();

    void quit();
}
