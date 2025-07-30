package com.heckvision.bingosplash.core;


import com.heckvision.bingosplash.gui.BingoConfig;
import com.heckvision.bingosplash.utils.ScoreboardAPI;
import gg.essential.universal.UMinecraft;
import net.minecraft.client.multiplayer.ServerData;

import java.util.List;

//#if MC==12105
//$$ import net.minecraft.scoreboard.ScoreboardDisplaySlot;
//#endif

public class ModStateHandler {

    public static void debug(){
        System.out.println("Hypixel: "+ onHypixel() + " Skyblock: "+ onSkyblock() + " Bingo: "+ onBingo() + " Ironman: "+onIronman());
        System.out.println("Main state: " + ModEnabled());
        System.out.println("sub state: " + checkSubStates());
    }

    public static boolean ModEnabled() {
        return onHypixel() && BingoConfig.enableBingoSplash;
    }

    public static boolean checkSubStates(){
        if (!BingoConfig.enableBingoSplashIm && !BingoConfig.enableBingoSplashBingo) {
            return ModEnabled() && onSkyblock();
        }

        boolean onIm = BingoConfig.enableBingoSplashIm && onIronman();
        boolean onBi = BingoConfig.enableBingoSplashBingo && onBingo();
        return ModEnabled() && onSkyblock() && (onIm || onBi);
    }


    public static boolean onHypixel(){
        ServerData serverData = UMinecraft.getMinecraft().getCurrentServerData();
        if (serverData !=null){
            return serverData.serverIP.contains("hypixel.net");
        }
        return false;
    }

    public static boolean onSkyblock() {
        String title = ScoreboardAPI.getScoreboardTitle();
        return title != null && title.toLowerCase().contains("skyblock");
    }

    public static boolean onBingo() {
        List<String> lines = ScoreboardAPI.getScoreboardLines();
        return checkListForString(lines, "bingo");
    }

    public static boolean onIronman() {
        List<String> lines = ScoreboardAPI.getScoreboardLines();
        return checkListForString(lines, "ironman");
    }

    private static boolean checkListForString(List<String> lines, String str) {
        for (String s : lines) {
            if (s != null && s.toLowerCase().contains(str.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}

