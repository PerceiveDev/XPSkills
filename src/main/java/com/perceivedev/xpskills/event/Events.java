/**
 * 
 */
package com.perceivedev.xpskills.event;

import org.bukkit.event.Listener;

import com.perceivedev.xpskills.Skill;
import com.perceivedev.xpskills.XPSkills;

/**
 * @author Rayzr
 *
 */
public class Events {

    private XPSkills plugin;

    /**
     * @param xpSkills
     */
    public Events(XPSkills plugin) {
        this.plugin = plugin;

        registerListener(new PlayerListener());
    }

    public void registerListener(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    /**
     * @param <T>
     * @param skillAttackDamage
     */
    public void registerSkill(Skill skill) {
        // TODO: Do something more here?
        registerListener(skill);
    }

}
