package com.heckvision.bingosplash.utils;

import java.util.ArrayList;
import java.util.List;

public class WorldChangeAPI {
    public interface WorldChangeListener {
        void onWorldChange();
    }

    private static final List<WorldChangeListener> listeners = new ArrayList<>();

    public static void onWorldChange() {
        for (WorldChangeListener listener : listeners) {
            listener.onWorldChange();
        }
    }

    public static void registerWorldChangeListener(WorldChangeListener listener) {
        listeners.add(listener);
    }
}
