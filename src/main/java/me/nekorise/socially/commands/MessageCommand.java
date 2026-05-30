package me.nekorise.socially.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.nekorise.socially.config.LanguageConfigStorage;
import me.nekorise.socially.config.MainConfigStorage;
import me.nekorise.socially.utils.ChatStringFormatter;
import me.nekorise.socially.utils.MMessage;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static me.nekorise.socially.utils.ChatStringFormatter.isContainsBlacklistedWords;

@CommandAlias("msg|message|tell|w")
@CommandPermission("socially.msg")
public class MessageCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    public void onMessage(
            Player sender,
            String targetName,
            String[] args
    ) {

        if (args.length == 0) {
            sender.sendMessage(
                    MMessage.applyColor(LanguageConfigStorage.msgUsage)
            );
            return;
        }

        if (sender.getName().equalsIgnoreCase(targetName)) {
            sender.sendMessage(
                    MMessage.applyColor(LanguageConfigStorage.msgSelfSendError)
            );
            return;
        }

        Player recipient = Bukkit.getPlayerExact(targetName);

        if (recipient == null) {
            sender.sendMessage(
                    MMessage.applyColor(LanguageConfigStorage.msgUserOffline)
            );
            return;
        }

        String message = StringUtils.join(args, ' ');

        if (isContainsBlacklistedWords(message, sender)) {
            sender.sendMessage(
                    MMessage.applyColor(LanguageConfigStorage.badWordMessage)
            );
            return;
        }

        Component finalMessage = ChatStringFormatter.getPrivateMessage(
                sender,
                recipient,
                message
        );

        recipient.sendMessage(finalMessage);
        recipient.playSound(
                recipient,
                MainConfigStorage.messageSound,
                1.0f,
                1.0f
        );

        sender.sendMessage(finalMessage);
    }
}