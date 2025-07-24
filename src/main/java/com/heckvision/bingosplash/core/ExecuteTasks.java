package com.heckvision.bingosplash.core;

import com.heckvision.bingosplash.utils.*;
import gg.essential.universal.UMinecraft;


public class ExecuteTasks {

    public ExecuteTasks(){

        //incoming messages
        ConnectionAPI.INSTANCE.messageManager.setSplashListener(this::Execute);
        ConnectionAPI.INSTANCE.messageManager.setAutomatonListener(this::Execute);
    }

    private void Execute(String type, String Message) {
        TitleAPI.ShowTitle(type,Message,5,5,5);
        ChatAPI.SendMessage(Message);
        SoundAPI.PlaySound("random.levelup",10,10);
        //String serverIP = UMinecraft.getMinecraft().getCurrentServerData().serverIP;

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
