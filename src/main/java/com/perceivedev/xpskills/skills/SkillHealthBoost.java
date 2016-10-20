package com.perceivedev.xpskills.skills;

import org.bukkit.entity.Player;

import com.perceivedev.xpskills.Skill;

/**
 * Increases the Health of a {@link Player}
 */
public class SkillHealthBoost extends Skill {

    @Override
    public void applyEffect(int level, Player player) {
    }

    @Override
    public void removeEffect(Player player) {

    }

    @Override
    public String getName() {
        return "Health Boost";
    }

}
