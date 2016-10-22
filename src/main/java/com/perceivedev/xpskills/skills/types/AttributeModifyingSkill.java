package com.perceivedev.xpskills.skills.types;

import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

/**
 * A skill that changes an attribute
 */
public class AttributeModifyingSkill extends AbstractSkill {

    private final Attribute ATTRIBUTE;
    private final UUID      MODIFIER_ID;

    /**
     * @param maxLevel The max level of the skill
     * @param name The name of the skill
     * @param increaseCap The maximum increase
     * @param increasePerLevel The increase per level
     * @param attribute The attribute to modify
     * @param modifierId The UUID of this modifier. Must be consistent.
     */
    public AttributeModifyingSkill(int maxLevel, String name, double increasePerLevel, double increaseCap, Attribute attribute, UUID modifierId) {
        super(maxLevel, name, increasePerLevel, increaseCap);
        this.ATTRIBUTE = attribute;
        this.MODIFIER_ID = modifierId;
    }

    @Override
    public void applyEffect(int level, Player player) {
        double increase = getApplyingIncrease(level);

        removeEffect(player);

        player.getAttribute(ATTRIBUTE).addModifier(
                  new AttributeModifier(
                            MODIFIER_ID,
                            getIdentifier(),
                            increase,
                            AttributeModifier.Operation.MULTIPLY_SCALAR_1   // else we decrease it
                  )
        );
        // TODO: 22.10.2016 Remove 
        player.sendMessage("[" + getIdentifier() + "] Applied level: " + level + " with an increase of " + increase);
    }

    @Override
    public void removeEffect(Player player) {
        player.getAttribute(ATTRIBUTE).getModifiers().stream()
                  .filter(attributeModifier -> attributeModifier.getUniqueId().equals(MODIFIER_ID))
                  .findAny()
                  .ifPresent(attributeModifier -> player.getAttribute(ATTRIBUTE).removeModifier(attributeModifier));
    }
}
