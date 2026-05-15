package com.endava.ai.core.listener.internal;

@SuppressWarnings("ALL")
public final class TestExecutionState {

    private boolean methodGroupOpen;
    private boolean beforeOpen;
    private boolean testOpen;
    private boolean afterOpen;
    private boolean flowMode;

    public void reset() {
        methodGroupOpen = false;
        beforeOpen = false;
        testOpen = false;
        afterOpen = false;
        flowMode = false;
    }

    public boolean isMethodGroupOpen() {
        return methodGroupOpen;
    }

    public void openMethodGroup() {
        methodGroupOpen = true;
    }

    public void closeMethodGroup() {
        methodGroupOpen = false;
    }

    public boolean isBeforeOpen() {
        return beforeOpen;
    }

    public void openBefore() {
        beforeOpen = true;
    }

    public void closeBefore() {
        beforeOpen = false;
    }

    public boolean isTestOpen() {
        return testOpen;
    }

    public void openTest() {
        testOpen = true;
    }

    public void closeTest() {
        testOpen = false;
    }

    public boolean isAfterOpen() {
        return afterOpen;
    }

    public void openAfter() {
        afterOpen = true;
    }

    public void closeAfter() {
        afterOpen = false;
    }

    public boolean isFlowMode() {
        return flowMode;
    }

    public void enableFlowMode() {
        flowMode = true;
    }

    public void disableFlowMode() {
        flowMode = false;
    }
}
