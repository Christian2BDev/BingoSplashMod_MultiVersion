package com.heckvision.bingosplash.gui;

import com.heckvision.bingosplash.utils.TickAPI;
import gg.essential.universal.UScreen;

public class BingoOpenConfig {
    private static long targetTime = -1;
    private static boolean actionDone = false;

    public static void openConfig() {
        long delayMillis = 1;
        targetTime = System.currentTimeMillis() + delayMillis;
        actionDone = false;

        TickAPI.registerClientTickListener(() -> {
            if (!actionDone && targetTime > 0 && System.currentTimeMillis() >= targetTime) {
                UScreen.displayScreen(BingoConfig.INSTANCE.gui());
                actionDone = true;
                targetTime = -1;
            }
        });
    }
}
