package com.heckvision.bingosplash.utils;

import gg.essential.universal.UMinecraft;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainTreadAPI {

    public static void runOnMainThread(Runnable runnable) {
        UMinecraft.getMinecraft().addScheduledTask(runnable);
    }

    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public static void runOnMainThread(Runnable task,int delaySeconds) {
        scheduler.schedule(() -> {
            runOnMainThread(task);
        }, delaySeconds, TimeUnit.SECONDS);
    }
}
