package com.perceivedev.xpskills.skills.implementation;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import com.perceivedev.perceivecore.util.ItemFactory;
import com.perceivedev.xpskills.skills.types.AttributeModifyingSkill;

/**
 * Increases the AttackDamage of a {@link Player}
 */
public class SkillAttackDamage extends AttributeModifyingSkill {

    /**
     * @param maxLevel The max level of the skill
     * @param increasePerLevel The increase per level. Is a percentage!
     * @param cap The amount it cap at
     */
    public SkillAttackDamage(int maxLevel, double increasePerLevel, double cap) {
        super(
                maxLevel, 
                "Attack Damage", 
                increasePerLevel, 
                cap, 
                Attribute.GENERIC_ATTACK_DAMAGE, 
                UUID.fromString("94a02ecf-3bb2-4c3d-8803-dfdb9ddfc72b"),
                "&7Strength: &6%d &7(&e+ %.2f %% &7Damage)",
                ItemFactory.builder(Material.IRON_SWORD).build()
        );
    }
}
