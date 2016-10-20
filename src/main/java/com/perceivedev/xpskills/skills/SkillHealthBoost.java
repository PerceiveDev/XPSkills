/**
 * 
 */
package com.perceivedev.xpskills.skills;

import org.bukkit.entity.Player;

import com.perceivedev.xpskills.Skill;

/**
 * @author Rayzr
 *
 */
public class SkillHealthBoost extends Skill {

    /*
     * (non-Javadoc)
     * 
     * @see com.perceivedev.xpskills.skills.Skill#applyEffect(int,
     * org.bukkit.entity.Player)
     */
    @Override
    public void applyEffect(int level, Player player) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.perceivedev.xpskills.skills.Skill#getName()
     */
    @Override
    public String getName() {
        return "Health Boost";
    }

}
