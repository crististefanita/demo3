package com.endava.ai.reporting.util;

import com.endava.ai.core.reporting.attachment.FailureAttachmentHandler;
import org.testng.Reporter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class RecordingFailureAttachmentHandler implements FailureAttachmentHandler {

    public final AtomicInteger calls = new AtomicInteger();

    public final Set<String> failureContexts =
            Collections.synchronizedSet(new HashSet<>());

    @Override
    public void onTestFailure() {
        calls.incrementAndGet();

        String testName = Reporter.getCurrentTestResult() != null
                ? Reporter.getCurrentTestResult()
                .getMethod()
                .getMethodName()
                : "<no-test>";

        failureContexts.add(testName);
    }
}