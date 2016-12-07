package com.perceivedev.xpskills.skills.implementation;

import java.util.OptionalInt;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerExpChangeEvent;

import com.perceivedev.perceivecore.util.ItemFactory;
import com.perceivedev.xpskills.XPSkills;
import com.perceivedev.xpskills.skills.SkillType;
import com.perceivedev.xpskills.skills.types.AbstractSkill;

/**
 * A skill that amplifies Xp gain
 */
public class SkillAmplifyXp extends AbstractSkill {

    /**
     * @param maxLevel The max level of the skill
     * 
     * @param increasePerLevel The increase per level
     * @param increaseCap The maximum increase
     */
    public SkillAmplifyXp(int maxLevel, double increasePerLevel, double increaseCap) {
        super(maxLevel,
                "Amplify XP",
                increasePerLevel,
                increaseCap,
                (abstractSkill, level) -> String.format("&7Attack Speed: &6%d &7(&e+ %.2f %% &7Attack Speed)", level, Math.min(level * increasePerLevel, increaseCap)),
                ItemFactory.builder(Material.EXP_BOTTLE).build());
    }

    /**
     * @param level The level of the skill
     * @param player The player to apply it to
     */
    @Override
    public void applyEffect(int level, Player player) {
    }

    /**
     * Removes the Effect from a player
     *
     * @param player The {@link Player} to remove it from
     */
    @Override
    public void removeEffect(Player player) {
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onXpGain(PlayerExpChangeEvent event) {
        OptionalInt level = XPSkills.getInstance().getPlayerManager().getData(event.getPlayer().getUniqueId()).getSkillLevel(SkillType.EXPERIENCE_AMPLIFIER);
        if (!level.isPresent() || level.getAsInt() < 1) {
            return;
        }

        double applyingIncrease = getApplyingIncrease(level.getAsInt());

        event.setAmount((int) Math.round(event.getAmount() * applyingIncrease));
    }
}
