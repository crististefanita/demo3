package com.endava.ai.reporting.adapters;

import com.endava.ai.core.reporting.adapters.ExtentAdapter;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class ExtentAdapterTest {

    @Test
    public void extent_adapter_does_not_fail_when_reports_directory_is_missing() {
        ExtentAdapter adapter = ExtentAdapter.getInstance();

        adapter.startTest("Test Suite", "missing target reports dir");
        adapter.flush();

        assertTrue(true); // contract: no exception
    }
}
