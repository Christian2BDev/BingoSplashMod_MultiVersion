package com.heckvision.bingosplash.web;

import com.heckvision.bingosplash.core.ModStateHandler;

public class WebSocketConnection {

    private final WebSocketManager socketManager;
    private boolean prevEnabled = false;
    private final MessageManager messageManager;

    public WebSocketConnection() {
        socketManager = new WebSocketManager("wss://heckvision.com:25571");
        messageManager = new MessageManager();
        socketManager.setMessageListener(messageManager);
    }

    public void keepConnection(){
        boolean enabled = ModStateHandler.ModEnabled(); // Replace with your logic
        if (enabled != prevEnabled) {
            socketManager.setShouldConnect(enabled);
            prevEnabled = enabled;
        }
    }

    public void Shutdown(){
        socketManager.shutdown();
        System.out.println("BingoSplash is stopping!");
    }

    public MessageManager getMessageManager(){
        return messageManager;
    }




}
