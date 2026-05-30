package me.nekorise.socially.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.CommandPermission;

import me.nekorise.socially.config.ConfigManager;
import me.nekorise.socially.config.LanguageConfigStorage;
import me.nekorise.socially.utils.MMessage;

import org.bukkit.command.CommandSender;

@CommandAlias("socially")
public class SociallyCommand extends BaseCommand {

    @Default
    public void onDefault(CommandSender sender) {
        sender.sendMessage(
                MMessage.applyColor(LanguageConfigStorage.reloadUsage)
        );
    }

    @Subcommand("reload")
    @CommandPermission("socially.reload")
    public void onReload(CommandSender sender) {
        ConfigManager.loadConfig();

        sender.sendMessage(
                MMessage.applyColor(LanguageConfigStorage.reloadDone)
        );
    }
}