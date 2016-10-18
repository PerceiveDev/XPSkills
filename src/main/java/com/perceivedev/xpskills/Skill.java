/**
 * 
 */
package com.perceivedev.xpskills;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import com.perceivedev.perceivecore.config.ConfigSerializable;

/**
 * 
 * 
 * @author Rayzr
 */
public abstract class Skill implements ConfigSerializable, Listener {

    public abstract void applyEffect(int level, Player player);

    public abstract String getName();

}
