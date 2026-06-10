package com.endava.ai.ui.engine;

import java.util.List;

public interface UIEngine {
    int DEFAULT_WINDOW_WIDTH = 2560;
    int DEFAULT_WINDOW_HEIGHT = 1440;

    boolean supportsAutoWait();

    void open(String url);

    void click(String cssSelector);

    default void clickVisible(String cssSelector, int oneBasedIndex) {
        throw new UnsupportedOperationException("Visible-index click is not supported by this UI engine.");
    }

    void type(String cssSelector, String text);

    default void pressKey(String cssSelector, String key) {
        throw new UnsupportedOperationException("Key press is not supported by this UI engine.");
    }

    void select(String cssSelector, String valueOrText);

    default void uploadFile(String cssSelector, String absolutePath) {
        throw new UnsupportedOperationException("File upload is not supported by this UI engine.");
    }

    String getText(String cssSelector);

    default List<String> getTexts(String cssSelector) {
        return List.of(getText(cssSelector));
    }

    default List<String> getVisibleTexts(String cssSelector) {
        return getTexts(cssSelector);
    }

    String getValue(String cssSelector);

    default String getAttribute(String cssSelector, String attributeName) {
        throw new UnsupportedOperationException("Attribute lookup is not supported by this UI engine.");
    }

    boolean isVisible(String cssSelector);

    default boolean isEnabled(String cssSelector) {
        return isVisible(cssSelector);
    }

    void waitForVisible(String cssSelector, int seconds);

    void waitForUrlContains(String fragment, int seconds);

    String getCurrentUrl();

    void clearSession();

    String captureScreenshotAsBase64();

    void quit();

    void setWindowSize(int width, int height);
}
