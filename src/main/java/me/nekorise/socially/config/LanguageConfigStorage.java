package me.nekorise.socially.config;

import me.nekorise.socially.ConsoleLogs.Log;
import me.nekorise.socially.Chatly;
import org.bukkit.configuration.file.FileConfiguration;

public class LanguageConfigStorage {
    public static String chatFormatGlobal = "";
    public static String chatFormatLocal = "";
    public static String chatPlayerPing = "";
    public static String joinMessage = "";
    public static String quitMessage = "";
    public static String badWordMessage = "";
    public static String reloadUsage = "";
    public static String reloadDone = "";
    public static String msgUsage = "";
    public static String msgSelfSendError = "";
    public static String msgMessage = "";
    public static String msgUserOffline = "";
    public static String handshakeMessage = "";

    public static void loadData() {
        try {
            FileConfiguration cfg = ConfigManager.getConfig("langs/" + MainConfigStorage.language + ".yml");

            chatFormatGlobal = cfg.getString("chat.global");
            chatFormatLocal = cfg.getString("chat.local");
            chatPlayerPing = cfg.getString("chat.ping");

            joinMessage = cfg.getString("join-message");
            quitMessage = cfg.getString("quit-message");

            badWordMessage = cfg.getString("bad-word-message");

            reloadUsage = cfg.getString("reload-command.usage");
            reloadDone = cfg.getString("reload-command.done");

            msgUsage = cfg.getString("msg.usage");
            msgSelfSendError = cfg.getString("msg.self-send");
            msgMessage = cfg.getString("msg.message");
            msgUserOffline = cfg.getString("msg.user-offline");

            handshakeMessage = cfg.getString("handshake-message");
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.error(Chatly.getInstance(), "Failed to load language config");
        }
    }
}

