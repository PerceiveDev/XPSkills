package com.perceivedev.xpskills.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.perceivedev.xpskills.skills.SkillType;

/**
 * Called when a skill point is applied
 */
public class SkillPointApplyEvent extends CancellablePlayerEvent {

    private static HandlerList handlers = new HandlerList();
    private SkillType          skill;
    private int                level;

    public SkillPointApplyEvent(Player player, SkillType skill, int level) {
        super(player);
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
