package com.endava.ai.core.reporting.adapters.extent;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.endava.ai.core.config.ConfigManager.require;

public final class ExtentReporter {

    private final ExtentReports extent;

    public ExtentReporter() {
        this.extent = createExtentReports();
    }

    ExtentTest createTest(String name, String description) {
        return extent.createTest(
                name != null ? name : "Test",
                description != null ? description : ""
        );
    }

    public void attachScreenshot(ExtentTest test, String base64, String title) {
        test.addScreenCaptureFromBase64String(
                base64,
                title != null ? title : "Screenshot"
        );
    }

    public void logCodeBlock(ExtentTest target, String content) {
        target.info(
                MarkupHelper.createCodeBlock(content != null ? content : "")
        );
    }

    public void fail(ExtentTest target, String message, String stacktrace) {
        if (target == null) return;

        String content = (stacktrace != null && !stacktrace.isBlank())
                ? stacktrace
                : message;

        if (content != null && !content.isBlank()) {
            target.fail(MarkupHelper.createCodeBlock(content));
        }
    }

    public void flush() {
        extent.flush();
    }

    private ExtentReports createExtentReports() {
        String reportsDir = require("reports.dir");
        boolean tsEnabled = Boolean.parseBoolean(require("reports.timestamp.enabled"));
        String tsFormat = require("reports.timestamp.format");

        String fileName = tsEnabled
                ? "ExtentReport_" + new SimpleDateFormat(tsFormat).format(new Date()) + ".html"
                : "ExtentReport.html";

        Path reportPath = Paths.get(reportsDir, fileName);

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath.toString());
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Test Report");
        spark.config().setReportName("Automation Framework");
        spark.config().setCss(
                ".card-title a span { color: #f2f2f2 !important; }" +
                        ".card-title a { color: #f2f2f2 !important; }" +
                        ".fa { color: #f2f2f2 !important; }"
        );

        ExtentReports reports = new ExtentReports();
        reports.attachReporter(spark);
        return reports;
    }
}
