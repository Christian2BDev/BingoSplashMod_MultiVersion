package com.heckvision.bingosplash.core;

import com.heckvision.bingosplash.utils.ShutdownAPI;
import com.heckvision.bingosplash.utils.TickAPI;
import com.heckvision.bingosplash.web.MessageManager;
import com.heckvision.bingosplash.web.WebSocketConnection;

public class ExecuteTasks {

    private final WebSocketConnection connection;

    public ExecuteTasks(){
        connection = new WebSocketConnection();
        MessageManager messageManager = connection.getMessageManager();

        //incoming messages
        messageManager.setSplashListener(this::Execute);
        messageManager.setAutomatonListener(this::Execute);

        //websocket connection
        TickAPI.registerClientTickListener(connection::keepConnection);
        ShutdownAPI.registerClientShutdownListener(connection::Shutdown);
    }

    private void Execute(String type, String Message) {
        System.out.println(type + ": " + Message);
    }
//    private void Execute(String type,String message) {
//        Text parsedMessage = Text.literal("§6§l[§r"+type+"§6§l]" +message);
//        MinecraftClient client = MinecraftClient.getInstance();
//        client.execute(() -> {
//            if (client.player  != null) {
//                ClientPlayerEntity player = client.player;
//                if (BingoConfig.enableSplashPingsChat){
//                    client.inGameHud.getChatHud().addMessage(parsedMessage);
//                }
//                if (BingoConfig.enableSplashPingsTitle){
//                    client.inGameHud.setTitle(Text.literal(type));
//                    client.inGameHud.setSubtitle(Text.literal(message));
//                }
//                if (BingoConfig.enableSplashPingsSound){
//                    player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, BingoConfig.pingsSoundVolume , 1.0f);
//                }
//
//            }
//        });
//    }
}
