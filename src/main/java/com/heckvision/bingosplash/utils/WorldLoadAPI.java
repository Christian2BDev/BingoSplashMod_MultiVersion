package com.heckvision.bingosplash.utils;

import java.util.ArrayList;
import java.util.List;

public class WorldLoadAPI {
    public interface WorldLoadListener {
        void onWorldLoad();
    }

    private static final List<WorldLoadListener> listeners = new ArrayList<>();

    public static void onWorldLoad() {
        for (WorldLoadListener listener : listeners) {
            listener.onWorldLoad();
        }
    }

    public static void registerWorldLoadListener(WorldLoadListener listener) {
        listeners.add(listener);
    }
}
