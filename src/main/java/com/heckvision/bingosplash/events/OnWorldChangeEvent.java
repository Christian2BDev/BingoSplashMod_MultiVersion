package com.heckvision.bingosplash.events;


import com.heckvision.bingosplash.utils.WorldChangeAPI;
import gg.essential.universal.UMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;


public class OnWorldChangeEvent {
    private static World lastWorld = null;
    public static void WorldChangeCheck(){

        Minecraft mc = UMinecraft.getMinecraft();
        if (mc.theWorld == null) return;

        if (lastWorld != mc.theWorld) {
            lastWorld = mc.theWorld;
            WorldChangeAPI.onWorldChange();
        }
    }

}
