/**
 * 
 */
package com.perceivedev.xpskills.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

/**
 * @author Rayzr
 *
 */
public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onXPGain(PlayerExpChangeEvent e) {
        Player p = e.getPlayer();
        // int currentLevel = p.getLevel();
        if (p.getExp() * p.getExpToLevel() + e.getAmount() >= p.getExpToLevel()) {
            System.out.println("leveled up!");
        }
    }

}
