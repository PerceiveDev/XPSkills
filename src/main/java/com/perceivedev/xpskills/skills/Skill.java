package com.perceivedev.xpskills.skills;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.perceivedev.perceivecore.config.ConfigSerializable;

/**
 * A skill
 *
 * @author Rayzr
 */
public interface Skill extends ConfigSerializable, Listener {

    /**
     * @param level The level of the skill
     * @param player The player to apply it to
     */
    void applyEffect(int level, Player player);

    /**
     * Removes the Effect from a player
     *
     * @param player The {@link Player} to remove it from
     */
    void removeEffect(Player player);

    /**
     * Returns the name of the Skill
     *
     * @return The name of the skill
     */
    String getName();

    /**
     * Returns the max level the skill can have
     *
     * @return The max level of the skill
     */
    int getMaxLevel();

    /**
     * Describes the skill in short
     *
     * @param level The level of the skill
     *
     * @return A short explanation what the skill does at that level
     */
    String describeYourself(int level);

    /**
     * Returns the icon for this skill
     *
     * @return The icon for this Skill
     */
    ItemStack getIcon();

    default String getIdentifier() {
        return getName().toLowerCase().replace(" ", "_");
    }

}
