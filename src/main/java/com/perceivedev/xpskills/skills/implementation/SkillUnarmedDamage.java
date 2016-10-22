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
        super(maxLevel, "Unarmed Damage", increasePerLevel, increaseCap);
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

        event.setDamage(DamageModifier.BASE, event.getDamage() * increase);

        event.getDamager().sendMessage("["
                  + getIdentifier()
                  + "] Applied level: "
                  + managedPlayers.get(event.getDamager().getUniqueId())
                  + " with an increase of "
                  + increase
                  + " totaling to "
                  + event.getDamage()
        );
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
