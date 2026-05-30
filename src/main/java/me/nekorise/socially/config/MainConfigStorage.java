package me.nekorise.socially.config;

import me.nekorise.socially.ConsoleLogs.Log;
import me.nekorise.socially.Chatly;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class MainConfigStorage {

    public static String language = "";

    public static boolean isChatModificationEnabled = true;
    public static boolean isBubbleEnabled = true;
    public static boolean isHandshakeEnabled = true;
    public static boolean isJoinMessageEnabled = false;
    public static boolean isQuitMessageEnabled = false;

    public static String pingSound = "";
    public static char globalPrefix = '!';
    public static double localRadius = 150.0;
    public static List<String> badWords;
    public static String messageSound = "";
    public static float chatBubbleYOffset = 0.5f;
    public static String chatBubbleAlignment = "CENTER";
    public static String chatBubbleBillboard = "VERTICAL";
    public static int chatBubbleLifeTime = 100;

    public static void loadData() {
        try {
            FileConfiguration cfg = ConfigManager.getConfig("config.yml");

            language = cfg.getString("language");

            isChatModificationEnabled = cfg.getBoolean("features.chat-modification", true);
            isBubbleEnabled = cfg.getBoolean("features.chat-bubbles", true);
            isHandshakeEnabled = cfg.getBoolean("features.right-click-nickname", true);

            pingSound = cfg.getString("player-ping-sound");
            globalPrefix = cfg.getString("global-chat-prefix").toCharArray()[0];
            localRadius = cfg.getDouble("local-chat-distance");
            badWords = cfg.getStringList("blacklist-words");

            isJoinMessageEnabled = cfg.getBoolean("modify-join-message");
            isQuitMessageEnabled = cfg.getBoolean("modify-quit-message");

            messageSound = cfg.getString("message.sound");

            chatBubbleYOffset = (float) cfg.getDouble("bubble.y-offset");
            chatBubbleAlignment = cfg.getString("bubble.text-alignment");
            chatBubbleBillboard = cfg.getString("bubble.billboard");
            chatBubbleLifeTime = cfg.getInt("bubble.life-time");
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.error(Chatly.getInstance(), "Failed to load main config");
        }
    }
}

