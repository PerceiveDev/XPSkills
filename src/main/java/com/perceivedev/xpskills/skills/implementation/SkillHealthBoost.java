package com.perceivedev.xpskills.skills.implementation;

import java.util.UUID;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import com.perceivedev.perceivecore.util.ItemFactory;
import com.perceivedev.xpskills.skills.types.AttributeModifyingSkill;

/**
 * Increases the Health of a {@link Player}
 */
public class SkillHealthBoost extends AttributeModifyingSkill {

    /**
     * @param maxLevel The max level of the skill
     * @param increasePerLevel The increase per level
     * @param increaseCap The maximum increase
     */
    public SkillHealthBoost(int maxLevel, double increasePerLevel, double increaseCap) {
        super(
                maxLevel,
                "Health Boost",
                increasePerLevel,
                increaseCap,
                Attribute.GENERIC_MAX_HEALTH,
                UUID.fromString("fb7bd54c-a22e-4b18-8c39-4460727b2330"),
                "&7hearts: &6%d &7(&e+ %.2f %% &7Hearts)",
                ItemFactory.builder(Material.WOOL).setColour(DyeColor.RED).build());
    }
}
