package com.perceivedev.xpskills.managment;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.perceivedev.xpskills.XPSkills;
import com.perceivedev.xpskills.skills.Skill;
import com.perceivedev.xpskills.skills.SkillType;
import com.perceivedev.xpskills.skills.implementation.SkillAttackDamage;
import com.perceivedev.xpskills.skills.implementation.SkillAttackSpeed;
import com.perceivedev.xpskills.skills.implementation.SkillHealthBoost;
import com.perceivedev.xpskills.skills.implementation.SkillUnarmedDamage;

/**
 * Manages all registered Skills
 */
public class SkillManager {

    private Map<SkillType, Skill> skillTypeMap = new LinkedHashMap<>();

    {
        // register default skills

        registerSkillType(SkillType.ATTACK_DAMAGE, new SkillAttackDamage(200, 0.1, 20));
        registerSkillType(SkillType.ATTACK_SPEED, new SkillAttackSpeed(200, 0.1, 20));
        registerSkillType(SkillType.UNARMED_DAMAGE, new SkillUnarmedDamage(200, 0.01, 20));
        registerSkillType(SkillType.HEALTH_BOOST, new SkillHealthBoost(200, 0.01, 10));
    }

    /**
     * Adds a skill with the given SkillType
     *
     * @param skillType The {@link SkillType} to add
     * @param skill The Skill to add for it
     *
     * @throws NullPointerException if any parameter is null
     */
    public void registerSkillType(SkillType skillType, Skill skill) {
        Objects.requireNonNull(skillType);
        Objects.requireNonNull(skill);

        skillTypeMap.put(skillType, skill);
        Bukkit.getPluginManager().registerEvents(skill, JavaPlugin.getPlugin(XPSkills.class));
    }

    /**
     * Returns a skill for the skill type
     *
     * @param skillType The {@link SkillType} to get
     *
     * @return The skill if found
     */
    public Optional<Skill> getSkill(SkillType skillType) {
        return Optional.ofNullable(skillTypeMap.get(skillType));
    }

    /**
     * Returns all registered skills
     *
     * @return All the skills in an unmodifiable map
     */
    public Map<SkillType, Skill> getSkills() {
        return Collections.unmodifiableMap(skillTypeMap);
    }

    /**
     * Applies a skill to a player
     *
     * @param type The type of the skill
     * @param player The player to apply it for
     * @param level The level of the skill
     *
     * @return True if it could be applied
     *
     * @throws NullPointerException if any parameter is null
     */
    public boolean applySkill(SkillType type, Player player, int level) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(player);

        Optional<Skill> skillOptional = getSkill(type);
        if (!skillOptional.isPresent()) {
            return false;
        }
        Skill skill = skillOptional.get();
        if (level > skill.getMaxLevel()) {
            return false;
        }
        if (level < 0) {
            return false;
        }
        skill.applyEffect(level, player);
        return true;
    }

    /**
     * Removes a skill from a player
     *
     * @param type The type of the skill
     * @param player The player to remove it for
     *
     * @return True if it could be removed
     *
     * @throws NullPointerException if any parameter is null
     */
    public boolean removeSkill(SkillType type, Player player) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(player);

        Optional<Skill> skill = getSkill(type);
        if (!skill.isPresent()) {
            return false;
        }
        skill.get().removeEffect(player);
        return true;
    }
}
