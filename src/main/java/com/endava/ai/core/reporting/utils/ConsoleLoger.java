package com.endava.ai.core.reporting.utils;

public class ConsoleLoger {
    private static final int MAX_EXTERNAL_FRAMES = 3;
    private static final String INTERNAL_PACKAGE = "com.endava";

    public static void console(String msg) {
        System.out.println(msg);
    }

    public static String formatStacktrace(Throwable t) {
        if (t == null) return "";

        Throwable root = findRootCause(t);
        StringBuilder sb = new StringBuilder();

        appendRootMessage(sb, root);
        appendRelevantFrames(sb, root);

        return sb.toString();
    }

    private static Throwable findRootCause(Throwable t) {
        Throwable current = t;
        while (current.getCause() != null && current.getCause() != current) {
            current = current.getCause();
        }
        return current;
    }

    private static void appendRootMessage(StringBuilder sb, Throwable root) {
        String msg = root.getMessage();
        if (msg == null) return;

        int nl = msg.indexOf('\n');
        sb.append(nl > 0 ? msg.substring(0, nl) : msg)
                .append("\n\n");
    }

    private static void appendRelevantFrames(StringBuilder sb, Throwable root) {
        int externalCount = 0;

        for (StackTraceElement e : root.getStackTrace()) {
            String cls = e.getClassName();

            if (cls.startsWith(INTERNAL_PACKAGE)) {
                sb.append("at ").append(e).append("\n");
                continue;
            }

            if (externalCount < MAX_EXTERNAL_FRAMES) {
                sb.append("at ").append(e).append("\n");
                externalCount++;
            }
        }
    }

}
