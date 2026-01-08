package com.endava.ai.core.listener.internal;

import com.endava.ai.core.reporting.ReportLogger;

public final class LifecycleFlusher {

    private final StepBufferRegistry buffers;

    public LifecycleFlusher(StepBufferRegistry buffers) {
        this.buffers = buffers;
    }

    public boolean hasBeforeContent(Class<?> cls) {
        StepBufferLogger bs = buffers.peekBeforeSuite();
        StepBufferLogger bt = buffers.peekBeforeTest(cls);
        StepBufferLogger bc = buffers.peekBeforeClass(cls);
        StepBufferLogger bm = buffers.beforeMethod();

        return (bs != null && !bs.isEmpty())
                || (bt != null && !bt.isEmpty())
                || (bc != null && !bc.isEmpty())
                || (bm != null && !bm.isEmpty());
    }

    @SuppressWarnings("unused")
    public boolean hasAfterContent(Class<?> cls) {
        StepBufferLogger at = buffers.peekAfterTest(cls);
        StepBufferLogger ac = buffers.peekAfterClass(cls);
        StepBufferLogger as = buffers.peekAfterSuite();
        StepBufferLogger am = buffers.afterMethod();

        return (am != null && !am.isEmpty())
                || (at != null && !at.isEmpty())
                || (ac != null && !ac.isEmpty())
                || (as != null && !as.isEmpty());
    }

    public boolean hasAfterScopesContent(Class<?> cls) {
        StepBufferLogger at = buffers.peekAfterTest(cls);
        StepBufferLogger ac = buffers.peekAfterClass(cls);
        StepBufferLogger as = buffers.peekAfterSuite();

        return (at != null && !at.isEmpty())
                || (ac != null && !ac.isEmpty())
                || (as != null && !as.isEmpty());
    }

    public void flushBefore(ReportLogger logger, GroupController g, Class<?> cls, boolean firstForClass) {
        if (!hasBeforeContent(cls)) return;

        g.open("SETUP");

        flushBeforeSuite(logger, g);

        if (firstForClass) {
            flushBeforeScopes(cls, logger, g);
        }

        flushBeforeMethod(logger, g);

        g.close();
    }

    public void flushBeforeSuite(ReportLogger logger, GroupController group) {
        StepBufferLogger suite = buffers.peekBeforeSuite();
        if (suite == null || suite.isEmpty()) return;

        group.open("@BeforeSuite");
        buffers.flushBeforeSuite(logger);
        group.close();
    }

    public void flushBeforeScopes(Class<?> cls, ReportLogger logger, GroupController group) {
        StepBufferLogger bt = buffers.peekBeforeTest(cls);
        if (bt != null && !bt.isEmpty()) {
            group.open("@BeforeTest");
            buffers.flushBeforeTest(cls, logger);
            group.close();
        }

        StepBufferLogger bc = buffers.peekBeforeClass(cls);
        if (bc != null && !bc.isEmpty()) {
            group.open("@BeforeClass");
            buffers.flushBeforeClass(cls, logger);
            group.close();
        }
    }

    public void flushBeforeMethod(ReportLogger logger, GroupController group) {
        StepBufferLogger bm = buffers.beforeMethod();
        if (bm == null || bm.isEmpty()) return;

        group.open("@BeforeMethod");
        bm.flushTo(logger);
        bm.clear();
        group.close();
    }

    public void flushAfterMethod(ReportLogger logger) {
        StepBufferLogger am = buffers.afterMethod();
        if (am != null && !am.isEmpty()) {
            am.flushTo(logger);
            am.clear();
        }
    }

    public void flushAfterScopes(ReportLogger logger, GroupController g, Class<?> cls) {
        StepBufferLogger at = buffers.peekAfterTest(cls);
        if (at != null && !at.isEmpty()) {
            g.open("@AfterTest");
            buffers.flushAfterTestForLastReport(cls, logger);
            g.close();
        }

        StepBufferLogger ac = buffers.peekAfterClass(cls);
        if (ac != null && !ac.isEmpty()) {
            g.open("@AfterClass");
            buffers.flushAfterClassForLastReport(cls, logger);
            g.close();
        }

        StepBufferLogger as = buffers.peekAfterSuite();
        if (as != null && !as.isEmpty()) {
            g.open("@AfterSuite");
            buffers.flushAfterSuiteForLastReport(logger);
            g.close();
        }
    }
}
