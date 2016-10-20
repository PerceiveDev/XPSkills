package com.perceivedev.xpskills;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.perceivedev.perceivecore.config.ConfigSerializable;

/**
 * A skill
 *
 * @author Rayzr
 */
public abstract class Skill implements ConfigSerializable, Listener {

    /**
     * @param level The level of the skill
     * @param player The player to apply it to
     */
    public abstract void applyEffect(int level, Player player);

    /**
     * Removes the Effect from a player
     *
     * @param player The {@link Player} to remove it from
     */
    public abstract void removeEffect(Player player);

    /**
     * Returns the name of the Skill
     *
     * @return The name of the skill
     */
    public abstract String getName();

    public String getIdentifier() {
        return getName().toLowerCase().replace(" ", "_");
    }

}
