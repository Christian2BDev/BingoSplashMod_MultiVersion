package com.heckvision.bingosplash.updating;

import com.heckvision.bingosplash.utils.MainTreadAPI;
import gg.essential.universal.UMinecraft;
import gg.essential.universal.UScreen;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class UpdateManager {

    private static final UpdateManager INSTANCE = new UpdateManager();
    private static final String API_URL =
            "https://api.github.com/repos/Christian2BDev/BingoSplashMod_MultiVersion/releases/latest";
    private static final String CURRENT_VERSION = "v0.0.1-beta"; // replace with your current version

    private UpdateManager() {}

    public static UpdateManager getInstance() {
        return INSTANCE;
    }

    public void checkForUpdate() {
        System.out.println("Checking for updates...");

        // Run async to avoid blocking the client
        CompletableFuture.supplyAsync(() -> {
            try {
                return fetchLatestRelease();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).thenAccept(json -> {
            if (json != null && json.has("tag_name")) {
                String latestVersion = json.get("tag_name").getAsString();
                if (!latestVersion.equals(CURRENT_VERSION)) {
                    System.out.println("New version available: " + latestVersion);
                    // Only display popup if update exists
                    MainTreadAPI.runOnMainThread(()->{UScreen.displayScreen(new UpdatePopUp(CURRENT_VERSION, latestVersion));});
                } else {
                    System.out.println("You are up to date!");
                }
            } else {
                System.out.println("Could not fetch latest release.");
            }
        });
    }
    @SuppressWarnings("deprecation")
    private static JsonObject fetchLatestRelease() throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(API_URL).openConnection();
        conn.setRequestProperty("Accept", "application/vnd.github+json");
        conn.setRequestProperty("User-Agent", "BingoSplashMod");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            System.out.println("GitHub API returned HTTP " + responseCode);
            return null;
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) sb.append(line);

            JsonParser parser = new JsonParser();
            return parser.parse(sb.toString()).getAsJsonObject();
        }
    }
}