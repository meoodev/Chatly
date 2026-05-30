package me.nekorise.socially;

import co.aikar.commands.PaperCommandManager;
import me.nekorise.socially.commands.MessageCommand;
import me.nekorise.socially.commands.SociallyCommand;
import me.nekorise.socially.config.ConfigManager;
import me.nekorise.socially.events.*;
import me.nekorise.socially.utils.ChatBubbleManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Chatly extends JavaPlugin {

    private static Chatly instance;

    @Override
    public void onEnable() {
        instance = this;

        ConfigManager.loadConfig();
        ChatBubbleManager.initialize();

        registerCommands();
        registerEvents();
        registerBStats();
    }

    @Override
    public void onDisable() {
        OnChatBubble.deleteAllBubblesOnStop();
    }

    private void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);

        manager.registerCommand(new SociallyCommand());
        manager.registerCommand(new MessageCommand());
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new OnAsyncChat(), this);
        pm.registerEvents(new OnQuitJoin(), this);
        pm.registerEvents(new OnChatBubble(), this);
        pm.registerEvents(new OnHandshake(), this);
        pm.registerEvents(new OnChatBubbleRemove(), this);
    }

    private void registerBStats() {
        new Metrics(this, 28849);
    }

    public static Chatly getInstance() {
        return instance;
    }
}