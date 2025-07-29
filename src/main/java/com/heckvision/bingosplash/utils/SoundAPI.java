package com.heckvision.bingosplash.utils;

import com.heckvision.bingosplash.gui.BingoConfig;
import gg.essential.universal.UMinecraft;
import java.util.HashMap;

//#if MC==12105
//$$ import net.minecraft.registry.Registries;
//$$ import net.minecraft.sound.SoundEvent;
//$$ import net.minecraft.util.Identifier;
//#endif

public class SoundAPI {

    private static HashMap<String,String> soundMap = new HashMap<String,String>(){{
        put("random.levelup","minecraft:entity.player.levelup");
        //add more sounds to mapping
        // first param is forge 1.8.9 sound, second param is fabric 1.21.5 sound
    }};

    //#if MC==12105
    //$$ public static SoundEvent getSoundFromString(String soundId) {
    //$$     Identifier id = Identifier.tryParse(soundId);
    //$$     return Registries.SOUND_EVENT.get(id);
    //$$  }
    //#endif

    public static void PlaySound(String soundName, float volume, float pitch) {
        //#if MC==10809
        UMinecraft.getMinecraft().thePlayer.playSound(soundName, BingoConfig.pingsSoundVolume *volume, pitch);
        //#endif

        //#if MC==12105
        //$$ UMinecraft.getMinecraft().player.playSound(getSoundFromString(soundMap.get(soundName)),BingoConfig.pingsSoundVolume * volume,pitch);
        //#endif

    }
}
