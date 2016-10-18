/**
 * 
 */
package com.perceivedev.xpskills.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.perceivedev.xpskills.skills.SkillType;

/**
 * @author Rayzr
 *
 */
public class SkillPointApplyEvent extends Event implements Cancellable {

    private static HandlerList handlers = new HandlerList();
    private Player             player;
    private SkillType          skill;
    private int                level;
    private boolean            cancelled;

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

    /*
     * (non-Javadoc)
     * 
     * @see org.bukkit.event.Event#getHandlers()
     */
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bukkit.event.Cancellable#isCancelled()
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bukkit.event.Cancellable#setCancelled(boolean)
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
