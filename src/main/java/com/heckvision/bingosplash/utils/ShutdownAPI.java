package com.heckvision.bingosplash.utils;


import java.util.ArrayList;
import java.util.List;

public class ShutdownAPI {

    public interface ClientShutdownListener {
        void onShutdown();
    }

    private static final List<ClientShutdownListener> listeners = new ArrayList<>();

    public static void onClientShutdown() {
        for (ClientShutdownListener listener : listeners) {
            listener.onShutdown();
        }
    }

    public static void registerClientShutdownListener(ClientShutdownListener listener) {
        listeners.add(listener);
    }
}
