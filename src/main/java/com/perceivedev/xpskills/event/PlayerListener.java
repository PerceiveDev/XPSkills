package com.perceivedev.xpskills.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.perceivedev.xpskills.XPSkills;
import com.perceivedev.xpskills.api.SkillPointApplyEvent;
import com.perceivedev.xpskills.skills.Skill;

/**
 * Listens to Player related events
 */
public class PlayerListener implements Listener {

    private XPSkills plugin;

    public PlayerListener(XPSkills plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onXPGain(PlayerExpChangeEvent e) {
        Player p = e.getPlayer();

        // if player reaches next level
        if (p.getExp() * p.getExpToLevel() + e.getAmount() >= p.getExpToLevel()) {

        }
    }

    @EventHandler
    public void onHit(PlayerToggleSneakEvent event) {
        if (event.isSneaking()) {
            for (Skill skill : plugin.getSkills()) {
                skill.applyEffect(event.getPlayer().getLevel(), event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            System.out.println(event.getDamage());
        }
    }

    @EventHandler
    public void onSkillLevelUp(SkillPointApplyEvent e) {
        if (e.getPlayer().getName().equals("ZP4RKER")) {
            e.setCancelled(true);
        }
    }

}