package dev.meoo.chatly.events;

import dev.meoo.chatly.Chatly;
import dev.meoo.chatly.config.MainConfigStorage;
import dev.meoo.chatly.utils.ChatBubbleManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import static dev.meoo.chatly.utils.ChatStringFormatter.isContainsBlacklistedWords;

public class OnChatBubble implements Listener {

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        if (!MainConfigStorage.isBubbleEnabled) {
            return;
        }
        
        String message = event.getMessage();
        Player player = event.getPlayer();

        if (event.isCancelled()) {
            return;
        }

        float playerScale = (float) player.getAttribute(Attribute.GENERIC_SCALE).getBaseValue();

        if (player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        
        if (isContainsBlacklistedWords(message, player)) {
            return;
        }

        Location playerLoc = player.getLocation();
        playerLoc.setPitch(0);

        TextDisplay textDisplay = player.getWorld().spawn(playerLoc, TextDisplay.class);
        player.hideEntity(Chatly.getInstance(), textDisplay);
        textDisplay.setAlignment(TextDisplay.TextAlignment.valueOf(MainConfigStorage.chatBubbleAlignment));
        textDisplay.setBillboard(Display.Billboard.valueOf(MainConfigStorage.chatBubbleBillboard));
        player.addPassenger(textDisplay);

        Transformation bubbleVector = new Transformation(
                new Vector3f(0f, MainConfigStorage.chatBubbleYOffset * playerScale, 0f),
                new AxisAngle4f(0f, 0f, 0f, 0f),
                new Vector3f(playerScale),
                new AxisAngle4f(0f, 0f, 0f, 0f));

        textDisplay.setTransformation(bubbleVector);
        textDisplay.setText(message);

        ChatBubbleManager.createBubble(player, textDisplay, MainConfigStorage.chatBubbleLifeTime);
    }

    public static void deleteAllBubblesOnStop() {
        ChatBubbleManager.removeAll();
    }
}

