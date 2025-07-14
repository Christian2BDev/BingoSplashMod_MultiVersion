package com.heckvision.bingosplash.utils;


import java.util.ArrayList;
import java.util.List;

public class TickAPI {

    public interface ClientTickListener {
        void onTick();
    }

    private static final List<ClientTickListener> listeners = new ArrayList<>();

    public static void onClientTick() {
        for (ClientTickListener listener : listeners) {
            listener.onTick();
        }
    }

    public static void registerClientTickListener(ClientTickListener listener) {
        listeners.add(listener);
    }
}
