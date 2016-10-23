package com.perceivedev.xpskills.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * A player gains a SkillPoint
 */
public class SkillPointGainEvent extends CancellablePlayerEvent {

    private static HandlerList handlers = new HandlerList();

    private int skillPointAmount;

    public SkillPointGainEvent(Player who, int skillPointAmount) {
        super(who);
        this.skillPointAmount = skillPointAmount;
    }

    /**
     * Returns the amount of skill points the player got
     *
     * @return The amount of skill points the player gained
     */
    public int getSkillPointAmount() {
        return skillPointAmount;
    }

    /**
     * Sets the amount of skill points the player will get
     *
     * @param skillPointAmount The amount of skill points the player should gain
     */
    public void setSkillPointAmount(int skillPointAmount) {
        this.skillPointAmount = skillPointAmount;
    }

    @Override
    public String toString() {
        return "SkillPointGainEvent{" +
                  "cancelled=" + isCancelled() +
                  ", player=" + getPlayer() +
                  ", skillPointAmount=" + skillPointAmount +
                  '}';
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Returns the {@link HandlerList}
     * <p>
     * See the {@link Event} javadoc
     *
     * @return The {@link HandlerList}
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
