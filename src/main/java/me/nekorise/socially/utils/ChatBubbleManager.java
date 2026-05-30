package me.nekorise.socially.utils;

import me.nekorise.socially.Chatly;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChatBubbleManager {
    private static final Map<UUID, ChatBubble> bubbles = new HashMap<>();
    private static int taskId = -1;

    public static void initialize() {
        if (taskId != -1) {
            return;
        }

        taskId = Bukkit.getScheduler().runTaskTimer(Chatly.getInstance(), () -> {
            if (bubbles.isEmpty()) {
                return;
            }

            List<UUID> toRemove = new ArrayList<>();

            for (ChatBubble chatBubble : new ArrayList<>(bubbles.values())) {
                int availableTicks = chatBubble.getAvailableTicks();
                TextDisplay textDisplay = chatBubble.getTextDisplay();

                if (availableTicks <= 0) {
                    textDisplay.remove();
                    toRemove.add(chatBubble.getOwner().getUniqueId());
                    continue;
                }

                chatBubble.setAvailableTicks(availableTicks - 1);
            }

            for (UUID uuid : toRemove) {
                bubbles.remove(uuid);
            }
        }, 0L, 1L).getTaskId();
    }

    public static void createBubble(Player player, TextDisplay textDisplay, int ticks) {
        removeBubble(player);

        ChatBubble chatBubble = new ChatBubble(player, textDisplay, ticks);
        bubbles.put(player.getUniqueId(), chatBubble);
    }

    public static void removeBubble(Player player) {
        ChatBubble existing = bubbles.remove(player.getUniqueId());

        if (existing == null) {
            return;
        }

        TextDisplay textDisplay = existing.getTextDisplay();
        textDisplay.remove();
    }

    public static void removeAll() {
        if (taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = -1;
        }

        if (bubbles.isEmpty()) {
            return;
        }

        for (ChatBubble chatBubble : bubbles.values()) {
            TextDisplay textDisplay = chatBubble.getTextDisplay();
            textDisplay.remove();
        }

        bubbles.clear();
    }
}


