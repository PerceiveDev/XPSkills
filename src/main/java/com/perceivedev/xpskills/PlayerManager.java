/**
 * 
 */
package com.perceivedev.xpskills;

import java.util.HashMap;
import java.util.UUID;

import com.perceivedev.perceivecore.config.ConfigSerializable;

/**
 * @author Rayzr
 *
 */
public class PlayerManager {

    private HashMap<UUID, PlayerData> data = new HashMap<UUID, PlayerData>();

    private XPSkills                  plugin;

    public PlayerManager(XPSkills plugin) {
        this.plugin = plugin;
    }

    /**
     * @return the data
     */
    public HashMap<UUID, PlayerData> getData() {
        return data;
    }

    public PlayerData getData(UUID id) {
        return data.getOrDefault(id, new PlayerData(id));
    }

    /**
     * @return the plugin
     */
    public XPSkills getPlugin() {
        return plugin;
    }

    public class PlayerData implements ConfigSerializable {

        private UUID id;

        /**
         * @param id
         */
        public PlayerData(UUID id) {
            this.id = id;
        }

        /**
         * @return the id
         */
        public UUID getId() {
            return id;
        }

    }

}
