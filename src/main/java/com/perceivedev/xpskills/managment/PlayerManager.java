package com.perceivedev.xpskills.managment;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.perceivedev.perceivecore.config.ConfigSerializable;
import com.perceivedev.xpskills.XPSkills;
import com.perceivedev.xpskills.skills.Skill;
import com.perceivedev.xpskills.skills.SkillType;

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
        data.put(id, new PlayerData(id));
        return data.get(id);
    }

    /**
     * A class holding data about the player
     */
    public static class PlayerData implements ConfigSerializable {
        private UUID playerID;
        private Map<SkillType, Integer> skillMap = new HashMap<>();

        private int freeSkillPoints;
        private int level;

        /**
         * @param playerID The {@link UUID} of the player
         */
        public PlayerData(UUID playerID) {
            this.playerID = playerID;
        }

        //<editor-fold desc="Skill adding, removing and querying">

        /**
         * Sets the skill level for the player. Ignores skill points.
         *
         * @param skillType The {@link SkillType} of the skill
         * @param level The level of the skill
         *
         * @return True if it could be applied
         */
        public boolean setSkill(SkillType skillType, int level) {
            Optional<Skill> skillOptional = XPSkills.getPlugin(XPSkills.class).getSkillManager().getSkill(skillType);
            if (!skillOptional.isPresent()) {
                return false;
            }
            if (level > skillOptional.get().getMaxLevel()) {
                return false;
            }
            if (level < 0) {
                return false;
            }
            if (level == 0) {
                removeSkill(skillType);
                return true;
            }
            skillMap.put(skillType, level);

            // apply if player is online
            getPlayer().ifPresent(player -> XPSkills.getPlugin(XPSkills.class).getSkillManager().applySkill(skillType, player, level));
            return true;
        }

        /**
         * Increases the skill by the given level and decreases the {@link #getFreeSkillPoints()} accordingly.
         *
         * @param skillType The {@link SkillType} of the skill
         * @param levelIncrease The level of the skill
         *
         * @return True if the skill could be increased by the given amount.
         */
        public boolean increaseSkill(SkillType skillType, int levelIncrease) {
            if (getFreeSkillPoints() < levelIncrease) {
                return false;
            }
            if (setSkill(skillType, levelIncrease)) {
                freeSkillPoints -= levelIncrease;
                return true;
            }
            return false;
        }

        /**
         * Removes a skill from a player
         *
         * @param skillType The {@link SkillType} of the skill
         */
        public void removeSkill(SkillType skillType) {
            if (!hasSkill(skillType)) {
                return;
            }
            skillMap.remove(skillType);

            getPlayer().ifPresent(player -> XPSkills.getPlugin(XPSkills.class).getSkillManager().removeSkill(skillType, player));
        }

        /**
         * Checks if the player has a certain skill
         *
         * @param skillType The {@link SkillType} of the skill
         *
         * @return True if the player has the skill
         */
        public boolean hasSkill(SkillType skillType) {
            return skillMap.containsKey(skillType);
        }

        /**
         * Returns all skills of the player and their level
         *
         * @return All skills of the player with their level
         */
        public Map<SkillType, Integer> getAllSkills() {
            return Collections.unmodifiableMap(skillMap);
        }

        /**
         * Returns the skill level
         *
         * @param skillType The {@link SkillType} of the skill
         *
         * @return The level of the skill, if the player has the skill
         */
        public OptionalInt getSkillLevel(SkillType skillType) {
            if (hasSkill(skillType)) {
                return OptionalInt.of(skillMap.get(skillType));
            }
            return OptionalInt.empty();
        }
        //</editor-fold>

        //<editor-fold desc="Skill point handling">

        /**
         * Returns the amount of skill points the player can spend
         *
         * @return The skill points the player can spend
         */
        public int getFreeSkillPoints() {
            return freeSkillPoints;
        }

        /**
         * Sets the new amount of free skill points
         *
         * @param freeSkillPoints The new amount of free skill points
         */
        public void setFreeSkillPoints(int freeSkillPoints) {
            this.freeSkillPoints = freeSkillPoints;
        }

        /**
         * Increases the {@link #getFreeSkillPoints()} by the given amount
         *
         * @param amount The amount of free skill points to give
         */
        public void giveFreeSkillPoints(int amount) {
            this.freeSkillPoints += amount;
        }

        /**
         * Resets all skill points.
         * <p>
         * This means it removes all skills and sets the {@link #getFreeSkillPoints()} to what it was according to the applied skill levels
         */
        public void resetSkillPoints() {
            int totalSkillPoints = skillMap.values()
                      .stream()
                      .mapToInt(Integer::intValue)
                      .max()
                      .orElse(0);

            for (SkillType skillType : SkillType.values()) {
                removeSkill(skillType);
            }

            freeSkillPoints = totalSkillPoints;
        }
        //</editor-fold>

        /**
         * Returns the level of the player
         *
         * @return The current level of the Player
         */
        public int getLevel() {
            return level;
        }

        /**
         * Sets the new level of the player
         *
         * @param level The new level of the player
         */
        public void setLevel(int level) {
            this.level = level;
        }

        // TODO: 23.10.2016 Give Skill points here? 
        // TODO: 23.10.2016 LEVEL!!! 

        /**
         * Increases the level
         *
         * @param amount The amount to increase the level by
         */
        public void increaseLevel(int amount) {
            level += amount;
        }

        /**
         * @return The player if he is online
         */
        protected Optional<Player> getPlayer() {
            return Optional.ofNullable(Bukkit.getPlayer(playerID));
        }
    }

}
