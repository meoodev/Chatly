package dev.meoo.chatly.events;

import dev.meoo.chatly.config.LanguageConfigStorage;
import dev.meoo.chatly.config.MainConfigStorage;
import dev.meoo.chatly.utils.ChatStringFormatter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class OnHandshake implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent event) {
        if (!MainConfigStorage.isHandshakeEnabled) {
            return;
        }
        if (!(event.getRightClicked() instanceof Player targetPlayer)) {
            return;
        }

        Player player = event.getPlayer();
        if (!player.hasPermission("chatly.handshake")) {
            return;
        }
        player.sendActionBar(ChatStringFormatter.getHandshakeMessage(LanguageConfigStorage.handshakeMessage, targetPlayer));
    }
}

