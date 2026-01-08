package com.endava.ai.core.listener.internal;

import com.endava.ai.core.reporting.ReportLogger;

import java.util.ArrayList;
import java.util.List;

public final class StepBufferLogger implements ReportLogger {

    private final List<Event> events = new ArrayList<>();

    @Override
    public void startTest(String testName, String description) {
    }

    @Override
    public void endTest(String status) {
    }

    @Override
    public void startStep(String stepTitle) {
        events.add(new Event(Kind.START_STEP, stepTitle, null));
    }

    @Override
    public void logDetail(String detail) {
        events.add(new Event(Kind.DETAIL, detail, null));
    }

    @Override
    public void pass(String message) {
        events.add(new Event(Kind.PASS, message, null));
    }

    @Override
    public void fail(String message, String stacktraceAsText) {
        events.add(new Event(Kind.FAIL, message, stacktraceAsText));
    }

    @Override
    public void attachScreenshotBase64(String base64, String title) {
        events.add(new Event(Kind.SCREENSHOT, base64, title));
    }

    @Override
    public void logCodeBlock(String content) {
        events.add(new Event(Kind.CODE, content, null));
    }

    @Override
    public void flush() {
    }

    public boolean isEmpty() {
        return events.isEmpty();
    }

    public void clear() {
        events.clear();
    }

    public void flushTo(ReportLogger target) {
        for (Event e : events) {
            switch (e.kind) {
                case START_STEP:
                    target.startStep(e.a);
                    break;
                case DETAIL:
                    target.logDetail(e.a);
                    break;
                case PASS:
                    target.pass(e.a);
                    break;
                case FAIL:
                    target.fail(e.a, e.b);
                    break;
                case CODE:
                    target.logCodeBlock(e.a);
                    break;
                case SCREENSHOT:
                    target.attachScreenshotBase64(e.a, e.b);
                    break;
            }
        }
    }

    private enum Kind {START_STEP, DETAIL, PASS, FAIL, CODE, SCREENSHOT}

    private static final class Event {
        final Kind kind;
        final String a;
        final String b;

        Event(Kind kind, String a, String b) {
            this.kind = kind;
            this.a = a;
            this.b = b;
        }
    }
}
