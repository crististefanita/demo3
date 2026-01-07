package com.endava.ai.core.reporting.adapters.allure;

import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.Status;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class AllureStepContext {

    private final ThreadLocal<Deque<String>> stepIds =
            ThreadLocal.withInitial(ArrayDeque::new);
    private final ThreadLocal<Deque<List<Parameter>>> stepParams =
            ThreadLocal.withInitial(ArrayDeque::new);

    private final AllureReporter reporter;

    public AllureStepContext(AllureReporter reporter) {
        this.reporter = reporter;
    }

    public void startStep(String title) {
        String parentId = stepIds.get().peekLast();
        String stepId = reporter.startStep(title, parentId);

        stepIds.get().addLast(stepId);
        stepParams.get().addLast(new ArrayList<>());
    }

    public void addParam(Parameter p) {
        List<Parameter> params = stepParams.get().peekLast();
        if (params != null) {
            params.add(p);
        }
    }

    public void finishStep(Status status) {
        String stepId = stepIds.get().pollLast();
        List<Parameter> params = stepParams.get().pollLast();
        if (stepId == null) return;

        reporter.updateAndStopStep(stepId, status, params);
    }

    public void clear() {
        stepIds.get().clear();
        stepParams.get().clear();
        stepIds.remove();
        stepParams.remove();
    }
}
