package com.perceivedev.xpskills.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import com.perceivedev.xpskills.XPSkills;
import com.perceivedev.xpskills.api.SkillPointApplyEvent;

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
        plugin.getPlayerManager().getData(p);
        if (p.getExp() * p.getExpToLevel() + e.getAmount() >= p.getExpToLevel()) {

        }
    }

    @EventHandler
    public void onLevelUp(SkillPointApplyEvent e) {
        if (e.getPlayer().getName().equals("ZP4RKER")) {
            e.setCancelled(true);
        }
    }

}