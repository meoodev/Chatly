package dev.meoo.chatly.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.CommandPermission;

import dev.meoo.chatly.config.ConfigManager;
import dev.meoo.chatly.config.LanguageConfigStorage;
import dev.meoo.chatly.utils.MMessage;

import org.bukkit.command.CommandSender;

@CommandAlias("chatly")
public class ChatlyCommand extends BaseCommand {

    @Default
    public void onDefault(CommandSender sender) {
        sender.sendMessage(
                MMessage.applyColor(LanguageConfigStorage.reloadUsage)
        );
    }

    @Subcommand("reload")
    @CommandPermission("chatly.reload")
    public void onReload(CommandSender sender) {
        ConfigManager.loadConfig();

        sender.sendMessage(
                MMessage.applyColor(LanguageConfigStorage.reloadDone)
        );
    }
}