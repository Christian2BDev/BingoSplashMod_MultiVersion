package com.heckvision.bingosplash.web;

import com.heckvision.bingosplash.core.ModStateHandler;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

public class WebSocketConnection {

    private final String serverUrl = "wss://testserver.heckvision.com";
    private final WebSocketManager manager;
    private boolean lastState = false;
    private final ReentrantLock connectionLock = new ReentrantLock();

    public WebSocketConnection(MessageListener messageListener) {
        this.manager = new WebSocketManager(serverUrl,messageListener);
    }

    public void connect() {
        connectionLock.lock();
        try {
            System.out.println("Connecting to WebSocket: " + serverUrl);
            manager.connect();
        } finally {
            connectionLock.unlock();
        }
    }

    public void shutdown() {
        connectionLock.lock();
        try {
            manager.shutdown();
        } finally {
            connectionLock.unlock();
        }
    }

    public void keepConnection() {
        connectionLock.lock();
        try {
            boolean currentState = ModStateHandler.ModEnabled();
            if (lastState != currentState) {
                if (currentState) {
                    manager.connect();
                } else {
                    manager.close();
                }
                lastState = currentState;
            }
        } finally {
            connectionLock.unlock();
        }
    }
}