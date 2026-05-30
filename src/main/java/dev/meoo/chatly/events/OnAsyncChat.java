package dev.meoo.chatly.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import dev.meoo.chatly.utils.MMessage;
import dev.meoo.chatly.config.LanguageConfigStorage;
import dev.meoo.chatly.config.MainConfigStorage;
import dev.meoo.chatly.utils.ChatStringFormatter;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class OnAsyncChat implements Listener {

    private static boolean isGlobal;

    @EventHandler
    public void onAsyncChat(AsyncChatEvent event) {
        if (!MainConfigStorage.isChatModificationEnabled) {
            return;
        }

        Player sender = event.getPlayer();
        String plainMessage = PlainTextComponentSerializer.plainText()
                .serialize(event.message());

        if (plainMessage.isEmpty()) {
            event.setCancelled(true);
            return;
        }

        isGlobal = plainMessage.charAt(0) == MainConfigStorage.globalPrefix;

        if (ChatStringFormatter.isContainsBlacklistedWords(plainMessage, sender)) {
            event.setCancelled(true);
            sender.sendMessage(
                    MMessage.applyColor(LanguageConfigStorage.badWordMessage)
            );
            return;
        }

        event.renderer((source, sourceDisplayName, message, viewer) ->
                ChatStringFormatter.getFinalMessage(
                        source,
                        plainMessage,
                        isGlobal
                )
        );

        if (!isGlobal) {
            event.viewers().clear();
            event.viewers().addAll(
                    getLocalRecipients(sender, MainConfigStorage.localRadius)
            );
        }
    }

    public static Set<Player> getLocalRecipients(Player sender, double radius) {
        Set<Player> recipients = new HashSet<>();
        double radiusSquared = radius * radius;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().equals(sender.getWorld())
                    && player.getLocation().distanceSquared(sender.getLocation()) <= radiusSquared) {
                recipients.add(player);
            }
        }
        return recipients;
    }

    public static boolean getIsGlobal() {
        return isGlobal;
    }
}

