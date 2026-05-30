package dev.meoo.chatly.events;

import dev.meoo.chatly.utils.ChatBubbleManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.List;

public class OnChatBubbleRemove implements Listener {
    private final List<PlayerTeleportEvent.TeleportCause> blackListedTeleportCauses = List.of(
            PlayerTeleportEvent.TeleportCause.SPECTATE,
            PlayerTeleportEvent.TeleportCause.COMMAND,
            PlayerTeleportEvent.TeleportCause.END_GATEWAY,
            PlayerTeleportEvent.TeleportCause.END_PORTAL,
            PlayerTeleportEvent.TeleportCause.PLUGIN,
            PlayerTeleportEvent.TeleportCause.UNKNOWN,
            PlayerTeleportEvent.TeleportCause.NETHER_PORTAL
    );

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (!event.isCancelled()) {
            Player player = event.getPlayer();
            ChatBubbleManager.removeBubble(player);
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        for (PlayerTeleportEvent.TeleportCause cause : blackListedTeleportCauses) {
            if (cause == event.getCause()) {
                ChatBubbleManager.removeBubble(player);
                return;
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ChatBubbleManager.removeBubble(player);
    }
}
