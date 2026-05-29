package com.endava.ai.atf.ui.component;

import com.endava.ai.ui.engine.UIEngine;

abstract class StubUiEngine implements UIEngine {

    @Override
    public boolean supportsAutoWait() {
        return true;
    }

    @Override
    public void open(String url) {
    }

    @Override
    public void click(String cssSelector) {
    }

    @Override
    public void type(String cssSelector, String text) {
    }

    @Override
    public void select(String cssSelector, String valueOrText) {
    }

    @Override
    public String getText(String cssSelector) {
        return "";
    }

    @Override
    public String getValue(String cssSelector) {
        return "";
    }

    @Override
    public boolean isVisible(String cssSelector) {
        return false;
    }

    @Override
    public void waitForVisible(String cssSelector, int seconds) {
    }

    @Override
    public void waitForUrlContains(String fragment, int seconds) {
    }

    @Override
    public String getCurrentUrl() {
        return "";
    }

    @Override
    public void clearSession() {
    }

    @Override
    public String captureScreenshotAsBase64() {
        return "";
    }

    @Override
    public void quit() {
    }

    @Override
    public void setWindowSize(int width, int height) {
    }
}
