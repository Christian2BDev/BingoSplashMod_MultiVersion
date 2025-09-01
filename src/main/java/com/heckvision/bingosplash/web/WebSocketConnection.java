package com.heckvision.bingosplash.web;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;

import java.util.Timer;
import java.util.TimerTask;

public class WebSocketConnection {

    private final String serverUrl = "wss://testserver.heckvision.com";
    private final WebSocketManager manager;

    public WebSocketConnection() {
        this.manager = new WebSocketManager(serverUrl);
    }

    public void connect() {
        System.out.println("Connecting to WebSocket: " + serverUrl);

        manager.connect();
    }

    public void shutdown() {
        manager.close();
    }

    public void keepConnection() {

    }

}