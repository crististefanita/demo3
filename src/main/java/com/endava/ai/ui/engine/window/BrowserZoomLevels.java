package com.endava.ai.ui.engine.window;

public final class BrowserZoomLevels {
    private static final int[] SUPPORTED_PERCENTS = {
            25, 33, 50, 67, 75, 80, 90, 100, 110, 125, 150, 175, 200, 250, 300, 400, 500
    };

    private BrowserZoomLevels() {
    }

    public static int nearestSupportedPercent(int configuredPercent) {
        int best = SUPPORTED_PERCENTS[0];
        int bestDistance = Math.abs(configuredPercent - best);

        for (int candidate : SUPPORTED_PERCENTS) {
            int distance = Math.abs(configuredPercent - candidate);
            if (distance < bestDistance || (distance == bestDistance && candidate > best)) {
                best = candidate;
                bestDistance = distance;
            }
        }

        return best;
    }
}
