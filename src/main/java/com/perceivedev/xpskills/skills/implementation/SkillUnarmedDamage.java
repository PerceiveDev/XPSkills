package com.perceivedev.xpskills.skills.implementation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;

import com.perceivedev.perceivecore.util.ItemFactory;
import com.perceivedev.perceivecore.util.TextUtils;
import com.perceivedev.xpskills.skills.types.AbstractSkill;

/**
 * Increases the AttackSpeed of a {@link Player}
 */
public class SkillUnarmedDamage extends AbstractSkill {

    private Map<UUID, Integer> managedPlayers = new HashMap<>();

    /**
     * @param maxLevel The max level of the skill
     * @param increasePerLevel The increase per level
     * @param increaseCap The maximum increase
     */
    public SkillUnarmedDamage(int maxLevel, double increasePerLevel, double increaseCap) {
        super(
                maxLevel, "Unarmed Damage",
                increasePerLevel,
                increaseCap,
                (skill, level) -> String.format("&7Unarmed: &6%d &7(&e+ %.2f %% &7Damage)",
                        level,
                        Math.min(level * increasePerLevel, increaseCap)),
                ItemFactory.builder(Material.SKULL_ITEM).setDurability((short) 2).build());
    }

    @Override
    public void applyEffect(int level, Player player) {
        managedPlayers.put(player.getUniqueId(), level);
    }

    @Override
    public void removeEffect(Player player) {
        managedPlayers.remove(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerHitsUnarmed(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        if (!managedPlayers.containsKey(event.getDamager().getUniqueId())) {
            return;
        }

        if (!isBareHanded((Player) event.getDamager())) {
            return;
        }

        double increase = getApplyingIncrease(managedPlayers.get(event.getDamager().getUniqueId())) + 1;

        double dmgBefore = event.getDamage();
        event.setDamage(DamageModifier.BASE, event.getDamage() * increase);

        event.getDamager().sendMessage(TextUtils.colorize(String.format(
                "&8[&7%s&8, &7%d&8] &c%.2f &8(&2+ &a%.2f&8)",
                getIdentifier(),
                managedPlayers.get(event.getDamager().getUniqueId()),
                event.getDamage(),
                event.getDamage() - dmgBefore)));
    }

    /**
     * Checks if the player attack with his bare hands
     *
     * @param player The player to check
     *
     * @return True if the player attacked with his bare hands
     */
    private boolean isBareHanded(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        return itemStack == null || itemStack.getType() == Material.AIR;
    }
}
