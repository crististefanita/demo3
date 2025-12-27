package com.endava.ai.ui.reporting;

public interface ReportLogger {
    void startTest(String testName, String description);
    void endTest(String status);            // PASS, FAIL, SKIP
    void startStep(String stepTitle);
    void logDetail(String detail);
    void pass(String message);
    void fail(String message, String stacktraceAsText);
    void attachScreenshotBase64(String base64, String title);
    void logCodeBlock(String content);
    void flush();
}
