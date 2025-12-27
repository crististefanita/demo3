package com.endava.ai.api.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class StacktraceUtils {
    private StacktraceUtils() {}

    public static String toText(Throwable t) {
        if (t == null) return "";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }
}
