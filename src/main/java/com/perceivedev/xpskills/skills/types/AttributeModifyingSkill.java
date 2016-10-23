package com.perceivedev.xpskills.skills.types;

import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.perceivedev.perceivecore.util.TextUtils;

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
     * @param formatString The format String for {@link #describeYourself(int)}. First param is the level, second the increase
     * @param icon The Icon for this Skill
     */
    public AttributeModifyingSkill(int maxLevel, String name, double increasePerLevel, double increaseCap, Attribute attribute, UUID modifierId,
              String formatString, ItemStack icon) {
        super(maxLevel,
                  name,
                  increasePerLevel,
                  increaseCap,
                  (abstractSkill, level) -> String.format(formatString, level, Math.min(level * increasePerLevel, increaseCap)),
                  icon);

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
        player.sendMessage(TextUtils.colorize(String.format(
                  "&8[&7%s&8, &7%d&8] &c%.2f &8(&2+ &a%.2f&8)",
                  getIdentifier(),
                  level,
                  player.getAttribute(ATTRIBUTE).getValue(),
                  increase
        )));
    }

    @Override
    public void removeEffect(Player player) {
        player.getAttribute(ATTRIBUTE).getModifiers().stream()
                  .filter(attributeModifier -> attributeModifier.getUniqueId().equals(MODIFIER_ID))
                  .findAny()
                  .ifPresent(attributeModifier -> player.getAttribute(ATTRIBUTE).removeModifier(attributeModifier));
    }
}
