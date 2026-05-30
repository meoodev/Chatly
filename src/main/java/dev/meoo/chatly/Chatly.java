package dev.meoo.chatly;

import co.aikar.commands.PaperCommandManager;
import dev.meoo.chatly.commands.IgnoreCommand;
import dev.meoo.chatly.commands.MessageCommand;
import dev.meoo.chatly.commands.ChatlyCommand;
import dev.meoo.chatly.config.ConfigManager;
import dev.meoo.chatly.events.*;
import dev.meoo.chatly.utils.ChatBubbleManager;
import dev.meoo.chatly.utils.IgnoreManager;
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
        IgnoreManager.initialize();

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

        manager.registerCommand(new ChatlyCommand());
        manager.registerCommand(new MessageCommand());
        manager.registerCommand(new IgnoreCommand());
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
        new Metrics(this, 31673);
    }

    public static Chatly getInstance() {
        return instance;
    }
}