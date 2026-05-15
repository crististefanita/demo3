package com.endava.ai.core.listener.internal;

import com.endava.ai.core.reporting.ReportLogger;

import java.util.function.BooleanSupplier;

public final class GroupController {

    private final ReportLogger logger;
    private int depth;

    public GroupController(ReportLogger logger) {
        this.logger = logger;
    }

    public void open(String name) {
        logger.startStep(name);
        depth++;
    }

    public void close() {
        if (depth <= 0) return;
        logger.pass("");
        depth--;
    }

    public void openIf(BooleanSupplier condition, String name, Runnable body) {
        if (!condition.getAsBoolean()) return;
        open(name);
        body.run();
        close();
    }

    public void closeCurrent() {
        logger.pass("");
    }

    @SuppressWarnings("unused")
    public boolean isOpen() {
        return depth > 0;
    }
}
