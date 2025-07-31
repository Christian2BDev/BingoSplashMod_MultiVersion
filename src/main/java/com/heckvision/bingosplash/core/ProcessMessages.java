package com.heckvision.bingosplash.core;

import com.heckvision.bingosplash.classes.TypeConfig;
import com.heckvision.bingosplash.gui.BingoConfig;
import com.heckvision.bingosplash.utils.*;


import java.util.HashMap;
import java.util.Map;


public class ProcessMessages {

    private final Map<String, TypeConfig> TypeConfigs = new HashMap<>();

    public ProcessMessages() {
        // Setup configs
        TypeConfigs.put("Splash", new TypeConfig(
                () -> BingoConfig.enableSplashPings,
                () -> BingoConfig.enableSplashPingsChat,
                () -> BingoConfig.enableSplashPingsTitle,
                () -> BingoConfig.enableSplashPingsSounds,
                "6", "f","random.levelup",5,5,5,1,1
        ));

        TypeConfigs.put("Automaton", new TypeConfig(
                () -> BingoConfig.enableAutomatonPings,
                () -> BingoConfig.enableAutomatonChat,
                () -> BingoConfig.enableAutomatonTitle,
                () -> BingoConfig.enableAutomatonSounds,
                "3", "f","random.levelup",5,5,5,1,1
        ));

        TypeConfigs.put("Jungle Key", new TypeConfig(
                () -> BingoConfig.enableJunglePings,
                () -> BingoConfig.enableJungleChat,
                () -> BingoConfig.enableJungleTitle,
                () -> BingoConfig.enableJungleSounds,
                "2", "f","random.levelup",5,5,5,1,1
        ));

        for (Map.Entry<String, TypeConfig> entry : TypeConfigs.entrySet()) {
            String tag = entry.getKey();
            TypeConfig config = entry.getValue();

            ConnectionAPI.INSTANCE.messageManager.registerListener(tag, config.enabled,
                    (type, message) -> handle(tag, message, config));
        }
    }

    public void handle(String tag, String message, TypeConfig config) {
        if (tag == null || message == null || config == null) return;

        String chatMsg = "§" + config.primaryColor + "§l[§r§" + config.textColor + tag + "§" + config.primaryColor + "§l] §r§" + config.textColor + message;
        String title = "§l§" + config.primaryColor + tag;
        String subTitle = "§" + config.textColor + message;

        if (config.chat.get()) ChatAPI.SendMessage(chatMsg);
        if (config.title.get()) TitleAPI.ShowTitle(title, subTitle, config.titleFadeIn, config.titleStay, config.titleFadeOut);
        if (config.sound.get()) SoundAPI.PlaySound(config.soundName, config.soundVolume, config.soundPitch);
    }
}

