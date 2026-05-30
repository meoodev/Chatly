package dev.meoo.chatly.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import dev.meoo.chatly.config.LanguageConfigStorage;
import dev.meoo.chatly.utils.IgnoreManager;
import dev.meoo.chatly.utils.MMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("ignore")
@CommandPermission("chatly.ignore")
public class IgnoreCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    public void onIgnore(Player sender, String targetName) {

        if (targetName == null || targetName.isEmpty()) {
            sender.sendMessage(
                    MMessage.applyColor(LanguageConfigStorage.ignoreUsage)
            );
            return;
        }

        Player target = Bukkit.getPlayerExact(targetName);

        if (target == null) {
            sender.sendMessage(
                    MMessage.applyColor(LanguageConfigStorage.ignorePlayerNotFound)
            );
            return;
        }

        if (target.equals(sender)) {
            sender.sendMessage(
                    MMessage.applyColor(LanguageConfigStorage.ignoreSelf)
            );
            return;
        }

        if (IgnoreManager.isIgnoring(
                sender.getUniqueId(),
                target.getUniqueId()
        )) {

            IgnoreManager.unignore(
                    sender.getUniqueId(),
                    target.getUniqueId()
            );

            sender.sendMessage(
                    MMessage.applyColor(LanguageConfigStorage.ignoreRemoved)
            );
            return;
        }

        IgnoreManager.ignore(
                sender.getUniqueId(),
                target.getUniqueId()
        );

        sender.sendMessage(
                MMessage.applyColor(LanguageConfigStorage.ignoreAdded)
        );
    }
}