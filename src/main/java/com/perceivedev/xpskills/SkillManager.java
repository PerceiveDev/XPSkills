/**
 * 
 */
package com.perceivedev.xpskills;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.bukkit.configuration.file.YamlConfiguration;

import com.perceivedev.perceivecore.config.SerializationManager;

/**
 * @author Rayzr
 *
 */
public class SkillManager {

    private Map<String, Skill> skills = new HashMap<String, Skill>();

    private XPSkills           plugin;

    /**
     * @param xpSkills
     */
    public SkillManager(XPSkills plugin) {
        this.plugin = plugin;
    }

    public void load() throws IOException {
        Path base = plugin.getDataFolder().toPath();

        for (File file : base.resolve("skills").toFile().listFiles()) {
            if (!isConfig(file)) {
                continue;
            }

            Skill skill = loadSkill(file);
            if (skill != null) {
                skills.put(skill.name, skill);
            }

        }

    }

    public Skill loadSkill(String file) {
        return loadSkill(plugin.getFile("skills/" + file));
    }

    public Skill loadSkill(File file) {
        if (!file.exists()) {
            return null;
        }
        return SerializationManager.deserialize(Skill.class, YamlConfiguration.loadConfiguration(file));
    }

    public void saveSkill(Skill skill, String file) {
        saveSkill(skill, plugin.getFile("skills/" + file));
    }

    public void saveSkill(Skill skill, File file) {
        Objects.requireNonNull(skill);
        Objects.requireNonNull(file);

        try {

            if (!file.exists()) {
                file.createNewFile();
            }

            YamlConfiguration out = new YamlConfiguration();
            Map<String, Object> data = SerializationManager.serialize(skill);
            if (data != null) {
                data.entrySet().stream().sequential().forEach(e -> out.set(e.getKey(), e.getValue()));
            }

            out.save(file);

        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save skill '" + skill.name + "':");
            e.printStackTrace();
        }
    }

    private boolean isConfig(File file) {
        return file.isFile() && file.getName().toLowerCase().endsWith(".yml");
    }

}
