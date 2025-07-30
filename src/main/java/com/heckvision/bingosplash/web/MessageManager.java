package com.heckvision.bingosplash.web;

import com.heckvision.bingosplash.core.ModStateHandler;
import com.heckvision.bingosplash.gui.BingoConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Handles incoming tagged messages and dispatches them to registered listeners,
 * only if their corresponding feature flags are enabled.
 *
 * <p>Usage example:
 * <pre>
 *     handler.registerListener("Splash", () -> BingoConfig.enableSplashPings, (type, msg) -> {
 *         System.out.println(type + " -> " + msg);
 *     });
 * </pre>
 */
public class MessageManager implements MessageListener {

    /**
     * Internal container for a message listener and its enabled flag.
     */
    private static class ListenerEntry {
        Supplier<Boolean> enabled;
        BiConsumer<String, String> handler;

        ListenerEntry(Supplier<Boolean> enabled, BiConsumer<String, String> handler) {
            this.enabled = enabled;
            this.handler = handler;
        }
    }

    /**
     * Maps tag names (e.g. "Splash", "Eggs") to their listener logic and enabled flags.
     */
    private final Map<String, ListenerEntry> listeners = new HashMap<>();

    /**
     * Registers a listener for a specific tag, along with a condition to determine if it's active.
     *
     * @param tag         The tag name in square brackets, e.g., "Splash" from "[Splash]"
     * @param enabledFlag A supplier that returns {@code true} if the listener should be active
     * @param handler     A consumer that handles (typePrefix, messageContent)
     */
    public void registerListener(String tag, Supplier<Boolean> enabledFlag, BiConsumer<String, String> handler) {
        listeners.put(tag, new ListenerEntry(enabledFlag, handler));
    }

    /**
     * Processes a raw message. If it matches a registered tag and its enabled flag is {@code true},
     * the appropriate listener will be triggered.
     *
     * @param message The raw incoming message, expected to contain a tag like "[Splash] message content"
     */
    @Override
    public void onMessage(String message) {
        if (!ModStateHandler.checkSubStates()) return;

        int tagStart = message.indexOf('[');
        int tagEnd = message.indexOf(']');
        if (tagStart == -1 || tagEnd == -1 || tagEnd <= tagStart) return;

        String tag = message.substring(tagStart + 1, tagEnd);
        String parsedMessage = message.substring(tagEnd + 2);

        ListenerEntry entry = listeners.get(tag);
        if (entry != null && entry.enabled.get()) {
            entry.handler.accept(tag, parsedMessage);
        }
    }
}
