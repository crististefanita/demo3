package com.endava.ai.reporting.util;

import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.internal.TestResult;

import static org.mockito.Mockito.*;

public final class FakeTestResult {

    private FakeTestResult() {
    }

    public static ITestResult success() {
        TestResult result = TestResult.newEmptyTestResult();
        result.setStatus(ITestResult.SUCCESS);
        result.setMethod(fakeMethod());
        return result;
    }

    public static ITestResult failure(Throwable throwable) {
        TestResult result = TestResult.newEmptyTestResult();
        result.setStatus(ITestResult.FAILURE);
        result.setThrowable(throwable);
        result.setMethod(fakeMethod());
        return result;
    }

    private static ITestNGMethod fakeMethod() {
        ITestNGMethod method = mock(ITestNGMethod.class);
        when(method.getMethodName()).thenReturn("fakeTest");
        when(method.getDescription()).thenReturn("fake description");
        return method;
    }
}
