package com.perceivedev.xpskills;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.perceivedev.xpskills.event.Events;
import com.perceivedev.xpskills.skills.SkillAttackDamage;
import com.perceivedev.xpskills.skills.SkillAttackSpeed;
import com.perceivedev.xpskills.skills.SkillHealthBoost;
import com.perceivedev.xpskills.skills.SkillUnarmedDamage;

public class XPSkills extends JavaPlugin {

    private Logger         logger;

    private Events         events;
    private List<Skill>    skills = Arrays.asList(new SkillAttackDamage(), new SkillAttackSpeed(), new SkillHealthBoost(), new SkillUnarmedDamage());

    @SuppressWarnings("unused")
    private CommandHandler commandHandler;

    @Override
    public void onEnable() {
        logger = getLogger();

        // Ensure the data loads properly
        if (!load()) {
            logger.severe("---------------------------");
            logger.severe("| FAILED TO LOAD XPSKILLS |");
            logger.severe("---------------------------");
            logger.severe("XPSkills will now be disabled");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        events = new Events(this);
        skills.forEach(skill -> events.registerSkill(skill));

        commandHandler = new CommandHandler(this);

        logger.info(versionText() + " enabled");
    }

    /**
     * 
     */
    public boolean load() {
        return true;
    }

    @Override
    public void onDisable() {
        logger.info(versionText() + " disabled");
    }

    public String versionText() {
        return getName() + " v" + getDescription().getVersion();
    }

    /**
     * @return the skills
     */
    public List<Skill> getSkills() {
        return skills;
    }

    /**
     * @param path the path to the file (from inside the plugin's data folder)
     * @return the File
     */
    public File getFile(String path) {
        return new File(getDataFolder(), path.replace("/", File.pathSeparator));
    }

}
