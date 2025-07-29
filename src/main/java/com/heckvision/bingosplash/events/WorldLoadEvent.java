package com.heckvision.bingosplash.events;

//always import
import com.heckvision.bingosplash.utils.WorldLoadAPI;


//#if FABRIC
//$$ import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
//$$ import net.minecraft.server.MinecraftServer;
//$$ import net.minecraft.server.world.ServerWorld;
//#endif

//#if FORGE
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//#endif

public class WorldLoadEvent {
    //#if FABRIC
    //$$ public WorldLoadEvent() { ClientWorldChangeEvents.LOAD.register((world) -> {WorldLoadAPI.onWorldLoad();});}
    //#endif

    //#if FORGE
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        WorldLoadAPI.onWorldLoad();

    }
    //#endif
}
