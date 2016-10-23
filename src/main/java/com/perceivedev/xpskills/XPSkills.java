package com.perceivedev.xpskills;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.perceivedev.xpskills.event.Events;
import com.perceivedev.xpskills.managment.PlayerManager;
import com.perceivedev.xpskills.managment.SkillManager;
import com.perceivedev.xpskills.skills.Skill;
import com.perceivedev.xpskills.skills.implementation.SkillAttackDamage;
import com.perceivedev.xpskills.skills.implementation.SkillAttackSpeed;
import com.perceivedev.xpskills.skills.implementation.SkillHealthBoost;
import com.perceivedev.xpskills.skills.implementation.SkillUnarmedDamage;

public class XPSkills extends JavaPlugin {

    private Logger logger;

    private Events events;
    private List<Skill> skills = Arrays.asList(
              new SkillAttackDamage(200, 0.1, 20),
              new SkillAttackSpeed(200, 0.1, 20),
              new SkillHealthBoost(200, 0.01, 10),
              new SkillUnarmedDamage(200, 0.01, 20)
    );

    @SuppressWarnings("unused")
    private CommandHandler commandHandler;

    private PlayerManager playerManager;
    private SkillManager  skillManager;

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

        skillManager = new SkillManager();
        playerManager = new PlayerManager(this);
        
        commandHandler = new CommandHandler(this);

        logger.info(versionText() + " enabled");
    }

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
     * @return The Current SkillManager
     */
    public SkillManager getSkillManager() {
        return skillManager;
    }

    /**
     * @return The current {@link PlayerManager}
     */
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    /**
     * @param path the path to the file (from inside the plugin's data folder)
     *
     * @return the File
     */
    public File getFile(String path) {
        return new File(getDataFolder(), path.replace("/", File.pathSeparator));
    }

}
