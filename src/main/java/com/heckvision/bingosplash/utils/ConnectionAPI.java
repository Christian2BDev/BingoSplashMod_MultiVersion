package com.heckvision.bingosplash.utils;

import com.heckvision.bingosplash.web.MessageManager;
import com.heckvision.bingosplash.web.WebSocketConnection;

public class ConnectionAPI {
    private final WebSocketConnection connection;
    public MessageManager messageManager;
    private ConnectionAPI() {

        messageManager = new MessageManager();
        connection = new WebSocketConnection(messageManager);
        TickAPI.registerClientTickListener(connection::keepConnection);
        ShutdownAPI.registerClientShutdownListener(connection::shutdown);
    }

    public static ConnectionAPI INSTANCE = new ConnectionAPI();
}
