package com.heckvision.bingosplash.events;

import com.heckvision.bingosplash.utils.ServerJoinApi;

//#if FORGE
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//#endif

//#if FORGE && MC==10809
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
//#endif

//#if FORGE && MC>10809
//$$ import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggedInEvent;
//#endif


//#if FABRIC
//$$ import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
//#endif

public class OnServerJoinEvent {
    //#if FORGE && MC==10809
    @SubscribeEvent
    public void onPlayerLogin(ClientConnectedToServerEvent event) {
        ServerJoinAction();
    }
    //#endif

    //#if FORGE && MC>10809
    //$$ @SubscribeEvent
    //$$ public void onPlayerLogin(LoggedInEvent event) {
    //$$     ServerJoinAction();
    //$$ }
    //#endif


    public OnServerJoinEvent() {
        //#if FORGE
        MinecraftForge.EVENT_BUS.register(this);
        //#endif


        //#if FABRIC
        //$$ ClientPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
        //$$   ServerJoinAction();
        //$$ });
        //#endif

    }

    void ServerJoinAction(){
        ServerJoinApi.onServerJoin();
    }

}
