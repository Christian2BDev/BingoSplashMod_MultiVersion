package com.heckvision.bingosplash.core;


import com.heckvision.bingosplash.gui.BingoConfig;
import com.heckvision.bingosplash.utils.ScoreboardAPI;
import gg.essential.universal.UMinecraft;
import gg.essential.universal.wrappers.UPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

//#if MC==12105
//$$ import net.minecraft.scoreboard.ScoreboardDisplaySlot;
//#endif

public class ModStateHandler {

    public boolean ModEnabled = false;

    public void check(){
        System.out.println("Hypixel: "+ onHypixel() + " Skyblock: "+ onSkyblock() + " Bingo: "+ onBingo() + " Ironman: "+onIronman());
        if (!BingoConfig.enableBingoSplash) {
            // Mod disabled globally
            ModEnabled = false;
        } else {
            // modToggle is ON
            if (!BingoConfig.enableBingoSplashIm && !BingoConfig.enableBingoSplashBingo) {
                // No profile-specific toggle enabled: enable mod everywhere on Hypixel Skyblock
                ModEnabled = onHypixel() && onSkyblock();
            } else {
                // At least one profile-specific toggle enabled
                boolean onIronmanProfile = BingoConfig.enableBingoSplashIm  && onIronman();
                boolean onBingoProfile = BingoConfig.enableBingoSplashBingo && onBingo();

                ModEnabled = onHypixel() && onSkyblock() && (onIronmanProfile || onBingoProfile);
            }
        }
    }

    private boolean onHypixel(){
        ServerData serverData = UMinecraft.getMinecraft().getCurrentServerData();
        if (serverData !=null){
            return serverData.serverIP.contains("hypixel.net");
        }
        return false;
    }
    private boolean onSkyblock(){
        if (ScoreboardAPI.getScoreboardTitle() == null) return false;
        return ScoreboardAPI.getScoreboardTitle().toLowerCase().contains("skyblock");
    }

    private boolean onBingo(){
        if (ScoreboardAPI.getScoreboardLines() == Collections.EMPTY_LIST) return false;
        List<String> lines = ScoreboardAPI.getScoreboardLines();
        return checkListForString(lines, "bingo");
    }

    private boolean onIronman(){
        if (ScoreboardAPI.getScoreboardLines() == Collections.EMPTY_LIST) return false;
        List<String> lines = ScoreboardAPI.getScoreboardLines();
        return checkListForString(lines, "ironman");
    }

    boolean checkListForString(List<String> lines, String str){
        for (String s : lines) {
            if (s.toLowerCase().contains(str.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


}
