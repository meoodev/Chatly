package dev.meoo.chatly.utils;

import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

public class ChatBubble {
    private Player owner;
    private TextDisplay textDisplay;
    private int availableTicks = 40;

    public ChatBubble(Player owner, TextDisplay textDisplay, int availableTicks) {
        this.owner = owner;
        this.textDisplay = textDisplay;
        this.availableTicks = availableTicks;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public TextDisplay getTextDisplay() {
        return textDisplay;
    }

    public void setTextDisplay(TextDisplay textDisplay) {
        this.textDisplay = textDisplay;
    }
    
    public int getAvailableTicks() {
        return availableTicks;
    }
    
    public void setAvailableTicks(int availableTicks)
    {
        this.availableTicks = availableTicks;
    }
}

