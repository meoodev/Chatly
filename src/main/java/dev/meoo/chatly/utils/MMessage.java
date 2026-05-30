package dev.meoo.chatly.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.List;
import java.util.stream.Collectors;

public class MMessage {
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    /**
     * Applies color and formatting to the given text.
     *
     * @param text The text with MiniMessage format, e.g., "<#77b1de>Colored Text!"
     * @return Component formatted text for use in Minecraft.
     */
    public static Component applyColor(String text) {
        return miniMessage.deserialize(text);
    }


    /**
     * Applies color and formatting to a list of strings.
     *
     * @param texts A list of texts with MiniMessage format, e.g., ["<#77b1de>Text1", "<#34a853>Text2"]
     * @return A list of formatted Components for use in Minecraft.
     */
    public static List<Component> applyListColor(List<String> texts) {
        return texts.stream()
                .map(MMessage::applyColor)
                .collect(Collectors.toList());
    }
}

