package com.perceivedev.xpskills.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;

/**
 * A PlayerEvent that is cancellable
 */
public abstract class CancellablePlayerEvent extends PlayerEvent implements Cancellable {

    private boolean cancelled;

    public CancellablePlayerEvent(Player who) {
        super(who);
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public String toString() {
        return "CancellablePlayerEvent{" +
                "player=" + getPlayer() +
                ", cancelled=" + cancelled +
                '}';
    }
}
