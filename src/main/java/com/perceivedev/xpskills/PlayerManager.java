package com.perceivedev.xpskills;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.perceivedev.perceivecore.config.ConfigSerializable;

/**
 * Manages the players. Contains data about them
 */
public class PlayerManager {

    private HashMap<UUID, PlayerData> data = new HashMap<>();

    private XPSkills plugin;

    /**
     * Creates a new PlayerManager for the plugin
     *
     * @param plugin The {@link XPSkills} plugin
     */
    public PlayerManager(XPSkills plugin) {
        this.plugin = plugin;
    }

    /**
     * Returns all data about all players.
     *
     * @return the data. Unmodifiable.
     */
    public Map<UUID, PlayerData> getData() {
        return Collections.unmodifiableMap(data);
    }

    /**
     * Returns the {@link PlayerData} for the player.
     * <p>
     * Will create a new one if needed.
     *
     * @param id The {@link UUID} of the player
     *
     * @return The {@link PlayerData} for the player
     */
    public PlayerData getData(UUID id) {
        if (data.containsKey(id)) {
            return data.get(id);
        }
        data.put(id, new PlayerData());
        return data.get(id);
    }

    /**
     * A class holding data about the player
     */
    public static class PlayerData implements ConfigSerializable {

    }

}
