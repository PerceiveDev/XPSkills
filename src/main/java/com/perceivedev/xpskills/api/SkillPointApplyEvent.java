package com.perceivedev.xpskills.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.perceivedev.xpskills.skills.SkillType;

/**
 * Called when a skill point is applied
 */
public class SkillPointApplyEvent extends Event implements Cancellable {

    private static HandlerList handlers = new HandlerList();
    private Player    player;
    private SkillType skill;
    private int       level;
    private boolean   cancelled;

    public SkillPointApplyEvent(Player player, SkillType skill, int level) {
        this.player = player;
        this.skill = skill;
        this.level = level;
    }

    /**
     * @return the skill
     */
    public SkillType getSkill() {
        return skill;
    }

    /**
     * @param skill the skill to set
     */
    public void setSkill(SkillType skill) {
        this.skill = skill;
    }

    /**
     * @return the level that the skill would be if this event is not cancelled
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    // TODO: 20.10.2016 Quite sure this was needed in the past 

    /**
     * Returns the {@link HandlerList}
     *
     * @return The {@link HandlerList}
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
