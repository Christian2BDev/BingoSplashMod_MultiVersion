package com.heckvision.bingosplash.classes;

import java.util.function.Supplier;

/**
 * Represents the configurable behavior for a specific message type (e.g., Splash, Automaton).
 *
 * <p>This configuration allows dynamic control over whether a message type is enabled,
 * and what actions should be performed when triggered, such as sending chat messages,
 * showing titles, or playing sounds. Each configuration also supports per-type formatting
 * like color codes and title/sound settings.</p>
 */
public class TypeConfig {

    /**
     * Whether this message type is globally enabled.
     */
    public final Supplier<Boolean> enabled;

    /**
     * Whether to send a formatted chat message when triggered.
     */
    public final Supplier<Boolean> chat;

    /**
     * Whether to show a title and subtitle on the screen when triggered.
     */
    public final Supplier<Boolean> title;

    /**
     * Whether to play a sound when triggered.
     */
    public final Supplier<Boolean> sound;

    /**
     * The Minecraft color code (e.g., "6" for gold) for the main tag label.
     */
    public final String primaryColor;

    /**
     * The color code used for the message text portion.
     */
    public final String textColor;

    /**
     * The name of the Minecraft sound to play (e.g., "random.levelup").
     */
    public final String soundName;

    /**
     * The number of ticks the title takes to fade in.
     */
    public final int titleFadeIn;

    /**
     * The number of ticks the title stays visible.
     */
    public final int titleStay;

    /**
     * The number of ticks the title takes to fade out.
     */
    public final int titleFadeOut;

    /**
     * The volume at which the sound should play (1.0 = normal volume).
     */
    public final float soundVolume;

    /**
     * The pitch at which the sound should play (1.0 = normal pitch).
     */
    public final float soundPitch;

    /**
     * Constructs a new {@code TypeConfig} instance.
     *
     * @param enabled       Whether this tag is enabled (e.g. [Splash] )
     * @param chat          Whether to send a chat message
     * @param title         Whether to show a title
     * @param sound         Whether to play a sound
     * @param primaryColor  The main color code for the tag
     * @param textColor     The color code for the message content
     * @param soundName     The sound to play (e.g., "random.levelup")
     * @param titleFadeIn   Duration (seconds) for the title to fade in
     * @param titleStay     Duration (seconds) for the title to stay on screen
     * @param titleFadeOut  Duration (seconds) for the title to fade out
     * @param soundVolume   Volume of the sound
     * @param soundPitch    Pitch of the sound
     */
    public TypeConfig(Supplier<Boolean> enabled, Supplier<Boolean> chat,
                      Supplier<Boolean> title, Supplier<Boolean> sound,
                      String primaryColor, String textColor, String soundName, int titleFadeIn, int titleStay, int titleFadeOut, float soundVolume, float soundPitch) {
        this.enabled = enabled;
        this.chat = chat;
        this.title = title;
        this.sound = sound;
        this.primaryColor = primaryColor;
        this.textColor = textColor;
        this.soundName = soundName;
        this.titleFadeIn = titleFadeIn;
        this.titleStay = titleStay;
        this.titleFadeOut = titleFadeOut;
        this.soundVolume = soundVolume;
        this.soundPitch = soundPitch;
    }
}
