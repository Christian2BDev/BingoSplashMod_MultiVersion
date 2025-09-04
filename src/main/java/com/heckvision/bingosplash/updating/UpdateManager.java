package com.heckvision.bingosplash.updating;

import com.heckvision.bingosplash.BingoSplash;
import com.heckvision.bingosplash.utils.ChatAPI;
import com.heckvision.bingosplash.utils.MainTreadAPI;
import gg.essential.universal.UMinecraft;
import gg.essential.universal.UScreen;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.*;

public class UpdateManager {

    private static final UpdateManager INSTANCE = new UpdateManager();
    private static final String API_URL =
            "https://api.github.com/repos/Christian2BDev/BingoSplashMod_MultiVersion/releases/latest";
    private boolean shownMessage = false;
    private static final List<String> TYPE_ORDER = Arrays.asList("beta", "pre-release", "stable");

    private String latestVersion;
    private String downloadUrl;
    private String fileName;



    private UpdateManager() {}

    public static UpdateManager getInstance() {
        return INSTANCE;
    }

    public void checkForUpdate(boolean autoCheck) {
        CompletableFuture.supplyAsync(() -> {
            try {
                return getJsonObject();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).thenAccept(json -> {
            if (json != null && json.has("tag_name")) {
                latestVersion = json.get("tag_name").getAsString();
                if (updateAvailable(latestVersion)) {
                    getCorrectFile(json);

                    //show chat message
                    if (!shownMessage && autoCheck && latestVersion.contains("stable"))
                        MainTreadAPI.runOnMainThread(() -> {
                            ChatAPI.SendMessage("§l§6[§r§lBingoSplash§r§l§6]§r There's a new version of the mod available");
                            shownMessage = true;
                        }, 5);
                }
                //open Popup
                if (!autoCheck) MainTreadAPI.runOnMainThread(() -> {
                    UScreen.displayScreen(new UpdatePopUp(BingoSplash.VERSION, latestVersion,!updateAvailable(latestVersion) ));
                });
            } else {
                System.out.println("Could not fetch latest release.");
            }
        });
    }

    @SuppressWarnings("deprecation")
    private static JsonObject getJsonObject() throws Exception {
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

    private boolean updateAvailable(String latestVersion ) {
        return compare(BingoSplash.VERSION, latestVersion) < 0;
    }

    public static int compare(String v1, String v2) {
        v1 = v1.startsWith("v") ? v1.substring(1) : v1;
        v2 = v2.startsWith("v") ? v2.substring(1) : v2;

        String[] parts1 = v1.split("-", 2);
        String[] parts2 = v2.split("-", 2);

        int[] nums1 = Arrays.stream(parts1[0].split("\\."))
                .mapToInt(Integer::parseInt).toArray();
        int[] nums2 = Arrays.stream(parts2[0].split("\\."))
                .mapToInt(Integer::parseInt).toArray();

        // Compare major.minor.patch
        for (int i = 0; i < 3; i++) {
            if (nums1[i] != nums2[i]) {
                return Integer.compare(nums1[i], nums2[i]);
            }
        }

        // Handle type (alpha, beta, pre-release, stable)
        String type1 = parts1.length > 1 ? parts1[1] : "";
        String type2 = parts2.length > 1 ? parts2[1] : "";

        return Integer.compare(TYPE_ORDER.indexOf(type1), TYPE_ORDER.indexOf(type2));
    }

    @SuppressWarnings("deprecation")
    public void downloadUpdate() {
        String fileUrl = downloadUrl;
        File targetFile = new File("mods", fileName);
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(fileUrl).openConnection();
            conn.setRequestProperty("User-Agent", "BingoSplashMod");

            try (InputStream in = conn.getInputStream();
                 FileOutputStream out = new FileOutputStream(targetFile)) {
                byte[] buffer = new byte[8192];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            }
            MainTreadAPI.runOnMainThread(() -> {ChatAPI.SendMessage("§l§6[§r§lBingoSplash§r§l§6]§r successfully downloaded the update, available after restart!");});
            deleteOldFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getCorrectFile(JsonObject json) {
        for (int i = 0; i < json.getAsJsonArray("assets").size(); i++) {
            JsonObject asset = json.getAsJsonArray("assets").get(i).getAsJsonObject();
            String name = asset.get("name").getAsString();

            if (name.endsWith(".jar") && name.contains(UMinecraft.getMinecraft().getVersion())) {
                downloadUrl = asset.get("browser_download_url").getAsString();
                fileName = name;
                break;
            }
        }
    }

    void deleteOldFile(){
        File oldJar = new File("mods/"+ BingoSplash.NAME+"-" +UMinecraft.getMinecraft().getVersion()+"-"+BingoSplash.VERSION+".jar");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (oldJar.exists()) {
                    if (oldJar.delete()) {
                        System.out.println("Old JAR removed: " + oldJar.getName());
                    } else {
                        System.out.println("Failed to remove old JAR: " + oldJar.getName());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }
}