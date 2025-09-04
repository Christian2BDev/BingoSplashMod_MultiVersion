package com.heckvision.bingosplash.utils;

import java.util.ArrayList;
import java.util.List;

public class ServerJoinApi {
    public interface ServerJoinListener {
        void onServerJoin();
    }

    private static final List<ServerJoinListener> listeners = new ArrayList<>();

    public static void onServerJoin() {
        for (ServerJoinListener listener : listeners) {
            listener.onServerJoin();
        }
    }
    public static void registerServerJoinListener(ServerJoinListener listener) {
        listeners.add(listener);
    }

}
