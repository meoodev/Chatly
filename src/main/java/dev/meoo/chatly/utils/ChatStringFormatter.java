package dev.meoo.chatly.utils;

import dev.meoo.chatly.config.LanguageConfigStorage;
import dev.meoo.chatly.config.MainConfigStorage;
import dev.meoo.chatly.events.OnAsyncChat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.PlaceholderAPI;

public class ChatStringFormatter {

    private static Player sender;

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer LEGACY_SERIALIZER =
            LegacyComponentSerializer.legacySection();

    private static Component parseFormat(String format, Player player, TagResolver... resolvers) {
        Component miniMessageComponent = MINI_MESSAGE.deserialize(format, resolvers);
        String serialized = LEGACY_SERIALIZER.serialize(miniMessageComponent);
        String withPlaceholders = PlaceholderAPI.setPlaceholders(player, serialized);

        return LEGACY_SERIALIZER.deserialize(withPlaceholders);
    }

    public static Component getFinalMessage(Player sender, String message, boolean isGlobal) {
        ChatStringFormatter.sender = sender;

        String format = isGlobal
                ? LanguageConfigStorage.chatFormatGlobal
                : LanguageConfigStorage.chatFormatLocal;

        if (isGlobal && !message.isEmpty()) {
            message = message.substring(1);
        }

        Component messageComponent = getMessageWithMention(message, isGlobal);

        return parseFormat(
                format,
                sender,
                Placeholder.component("sender", sender.displayName()),
                Placeholder.component("message", messageComponent)
        );
    }

    public static Component getHandshakeMessage(String format, Player target) {
        return parseFormat(
                format,
                target,
                Placeholder.component("target_player", target.displayName())
        );
    }

    private static Component getMessageWithMention(String message, boolean isGlobal) {
        Component result = Component.empty();
        String remaining = message;

        for (Player player : Bukkit.getOnlinePlayers()) {
            String name = player.getName();

            if (!remaining.contains(name)) {
                continue;
            }

            int index = remaining.indexOf(name);

            result = result.append(Component.text(remaining.substring(0, index)));

            String pingFormat = LanguageConfigStorage.chatPlayerPing;

            Component pingComponent = parseFormat(
                    pingFormat,
                    player,
                    Placeholder.component("pinged_player", player.displayName())
            );

            result = result.append(pingComponent);
            remaining = remaining.substring(index + name.length());

            if (OnAsyncChat.getIsGlobal()) {
                player.playSound(player, MainConfigStorage.pingSound, 1.0f, 1.0f);
            } else {
                for (Player local : OnAsyncChat.getLocalRecipients(sender, MainConfigStorage.localRadius)) {
                    if (local.equals(player)) {
                        player.playSound(player, MainConfigStorage.pingSound, 1.0f, 1.0f);
                    }
                }
            }
        }

        return result.append(Component.text(remaining));
    }

    public static Component getPrivateMessage(Player sender, Player recipient, String message) {
        String format = LanguageConfigStorage.msgMessage;

        Component messageComponent = sender.hasPermission("chatly.color")
                ? MINI_MESSAGE.deserialize(message)
                : Component.text(message);

        Component miniMessageComponent = MINI_MESSAGE.deserialize(
                format,
                Placeholder.component("sender", sender.displayName()),
                Placeholder.component("recipient", recipient.displayName()),
                Placeholder.component("message", messageComponent)
        );

        String serialized = LEGACY_SERIALIZER.serialize(miniMessageComponent);
        String withPlaceholders = PlaceholderAPI.setPlaceholders(sender, serialized);

        return LEGACY_SERIALIZER.deserialize(withPlaceholders);
    }

    public static Component getJoinQuitMessage(Player player, String format) {
        return parseFormat(
                format,
                player,
                Placeholder.component("player", player.displayName())
        );
    }

    public static boolean isContainsBlacklistedWords(String message, Player sender) {
        if (sender.hasPermission("chatly.badwordbypass")) {
            return false;
        }
        for (String word : MainConfigStorage.badWords) {
            if (message.toLowerCase().contains(word)) {
                return true;
            }
        }
        return false;
    }
}