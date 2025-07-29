package com.heckvision.bingosplash.utils;

import gg.essential.universal.UMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

//#if MC==12105
//$$ import net.minecraft.scoreboard.ScoreboardDisplaySlot;
//#endif

public class ScoreboardAPI {

    public static Scoreboard getScoreboard() {
        Minecraft mc = UMinecraft.getMinecraft();
        return mc.theWorld != null ? mc.theWorld.getScoreboard() : null;
    }

    public static ScoreObjective getObjective() {
        Scoreboard scoreboard = getScoreboard();
        if (scoreboard == null) return null;

        return scoreboard.getObjectiveInDisplaySlot(
                //#if MC!=12105
                1
                //#endif
                //#if MC>=12105
                //$$ ScoreboardDisplaySlot.SIDEBAR
                //#endif
        );
    }

    public static String getScoreboardTitle(){
        if(getObjective() == null) return null;
        return getObjective().getDisplayName().toString();
    }

    public static List<String> getScoreboardLines() {
        Scoreboard scoreboard = getScoreboard();
        ScoreObjective objective = getObjective();

        if (scoreboard == null || objective == null)return Collections.emptyList();


        Collection<Score> scores = scoreboard.getSortedScores(objective);
        List<String> lines = new ArrayList<>();

        for (Score score : scores) {
            //#if MC!=12105
            String entry = score.getPlayerName();
            //#endif
            //#if MC==12105
            //$$ String entry = score.owner();
            //#endif
            ScorePlayerTeam team = scoreboard.getPlayersTeam(entry);


            //#if MC<=11202
            String line = ScorePlayerTeam.formatPlayerName(team, entry);
            //#endif
            //#if MC>11202
            //$$ String line = team != null ? team.getPrefix().getString() + entry + team.getSuffix().getString() : entry;
            //#endif

            lines.add(stripWeirdChars(line));
        }

        return lines;
    }

    public static String stripWeirdChars(String input) {
        // Remove non-ASCII characters (including emojis and symbols)
        return input.replaceAll("[^\\x00-\\x7F]", "");
    }



}
