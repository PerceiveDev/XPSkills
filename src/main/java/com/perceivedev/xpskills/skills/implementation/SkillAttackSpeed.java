package com.perceivedev.xpskills.skills.implementation;

import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import com.perceivedev.xpskills.skills.types.AttributeModifyingSkill;

/**
 * Increases the AttackSpeed of a {@link Player}
 */
public class SkillAttackSpeed extends AttributeModifyingSkill {

    private static final UUID MODIFIER_ID = UUID.fromString("8b28fc2e-5bc2-41a3-84b8-073e32dca245");

    /**
     * @param maxLevel The max level of the skill
     * @param increasePerLevel The increase per level
     * @param increaseCap The maximum increase
     */
    public SkillAttackSpeed(int maxLevel, double increasePerLevel, double increaseCap) {
        super(maxLevel, "Attack Speed", increasePerLevel, increaseCap, Attribute.GENERIC_ATTACK_SPEED, MODIFIER_ID);
    }
}
