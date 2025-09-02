package com.heckvision.bingosplash.utils;

import gg.essential.universal.UMinecraft;

public class MainTreadAPI {

    public static void runOnMainThread(Runnable runnable) {
        //#if MC==10809
        UMinecraft.getMinecraft().addScheduledTask(runnable);
        //#endif
    }
}
