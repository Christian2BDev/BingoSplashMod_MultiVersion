package com.heckvision.bingosplash.gui;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.io.File;
import java.util.Arrays;


public class BingoConfig extends Vigilant {

    //region general
    @Property(
            type = PropertyType.SWITCH, name = "Bingo Mod",
            description = "Simple Toggle to disable all features.",
            category = "General", subcategory = "Main"
    )
    public static boolean enableBingoSplash = true;

    @Property(
            type = PropertyType.SWITCH, name = "Bingo specific",
            description = "Only enable the mod on Bingo Profiles.",
            category = "General", subcategory = "Main"
    )
    public static boolean enableBingoSplashBingo = false;

    @Property(
            type = PropertyType.SWITCH, name = "Ironman specific",
            description = "Only enable the mod on Ironman profiles.",
            category = "General", subcategory = "Main"
    )
    public static boolean enableBingoSplashIm = false;


    @Property(
            type = PropertyType.PERCENT_SLIDER, name = "Sound Volume",
            description = "Volume of the sounds",
            category = "General", subcategory = "Settings", maxF = 1.0f
    )
    public static float pingsSoundVolume = 1.0f;
    //endregion

    //region Splash
    @Property(
            type = PropertyType.SWITCH, name = "Splash pings",
            description = "Toggle to enable",
            category = "Splashes", subcategory = "Splash"
    )
    public static boolean enableSplashPings = true;

    @Property(
            type = PropertyType.SWITCH, name = "Chat Message",
            description = "Display a chat message when a splash is announced",
            category = "Splashes", subcategory = "Splash"
    )
    public static boolean enableSplashPingsChat = true;

    @Property(
            type = PropertyType.SWITCH, name = "Title",
            description = "Display a title when a splash is announced",
            category = "Splashes", subcategory = "Splash"
    )
    public static boolean enableSplashPingsTitle = true;

    @Property(
            type = PropertyType.SWITCH, name = "Sound",
            description = "Play a sound when a splash is announced",
            category = "Splashes", subcategory = "Splash"
    )
    public static boolean enableSplashPingsSound = true;
    //endregion

    //region Automaton
    @Property(
            type = PropertyType.SWITCH, name = "Automaton pings",
            description = "Toggle to enable pings for Automaton parts",
            category = "Mining", subcategory = "Automaton"
    )
    public static boolean enableAutomatonPings = true;

    @Property(
            type = PropertyType.SWITCH, name = "Chat message",
            description = "Display a chat message when a Automaton parts is pinged",
            category = "Mining", subcategory = "Automaton"
    )
    public static boolean enableAutomatonChat = true;

    @Property(
            type = PropertyType.SWITCH, name = "Title",
            description = "Display a title when a Automaton parts is pinged",
            category = "Mining", subcategory = "Automaton"
    )
    public static boolean enableAutomatonTitle = true;

    @Property(
            type = PropertyType.SWITCH, name = "Sound",
            description = "Play a sound when a Automaton parts is pinged",
            category = "Mining", subcategory = "Automaton"
    )
    public static boolean enableAutomatonSounds = true;
    //endregion

    //region Jungle
    @Property(
            type = PropertyType.SWITCH, name = "Jungle pings",
            description = "Toggle to enable pings for Jungle keys",
            category = "Mining", subcategory = "Jungle"
    )
    public static boolean enableJunglePings = true;

    @Property(
            type = PropertyType.SWITCH, name = "Chat message",
            description = "Display a chat message when a Jungle key is pinged",
            category = "Mining", subcategory = "Jungle"
    )
    public static boolean enableJungleChat = true;

    @Property(
            type = PropertyType.SWITCH, name = "Robot Automaton pings",
            description = "Display a title when a Jungle key is pinged",
            category = "Mining", subcategory = "Jungle"
    )
    public static boolean enableJungleTitle = true;

    @Property(
            type = PropertyType.SWITCH, name = "Robot Automaton pings",
            description = "Play a sound when a Jungle key is pinged",
            category = "Mining", subcategory = "Jungle"
    )
    public static boolean enableJungleSounds = true;
    //endregion







    public static BingoConfig INSTANCE = new BingoConfig("1.0");
    public BingoConfig(String VERSION) {
        super(new File("./config/BingoSplash.toml"), "BingoSplash (" + VERSION + ")"  );
        initialize();

        Arrays.asList(
                "enableSplashPingsChat", "enableSplashPingsTitle",
                "enableSplashPingsSound"
        ).forEach(property -> addDependency(property, "enableSplashPings"));

        Arrays.asList(
                "enableAutomatonChat", "enableAutomatonTitle",
                "enableAutomatonSounds"
        ).forEach(property -> addDependency(property, "enableAutomatonPings"));

        Arrays.asList(
                "enableJungleChat", "enableJungleTitle",
                "enableJungleSounds"
        ).forEach(property -> addDependency(property, "enableJunglePings"));



    }
}
