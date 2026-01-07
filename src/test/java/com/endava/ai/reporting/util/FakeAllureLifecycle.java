package com.endava.ai.reporting.util;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("All")
public final class FakeAllureLifecycle {

    public static final class Event {
        public final String type;
        public final String name;

        public Event(String type, String name) {
            this.type = type;
            this.name = name;
        }

        @Override
        public String toString() {
            return type + "(" + name + ")";
        }
    }

    private final List<Event> events = new ArrayList<>();

    public void startTest(String name) {
        events.add(new Event("START_TEST", name));
    }

    public void stopTest(String status) {
        events.add(new Event("STOP_TEST", status));
    }

    public void startFixture(String name) {
        events.add(new Event("START_FIXTURE", name));
    }

    public void stopFixture(String name) {
        events.add(new Event("STOP_FIXTURE", name));
    }

    public void startStep(String name) {
        events.add(new Event("START_STEP", name));
    }

    public void stopStep(String message) {
        events.add(new Event("STOP_STEP", message));
    }

    public void fail(String message) {
        events.add(new Event("FAIL", message));
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<String> eventsAsStrings() {
        List<String> result = new ArrayList<>();
        for (Event e : events) {
            result.add(e.toString());
        }
        return result;
    }
}
