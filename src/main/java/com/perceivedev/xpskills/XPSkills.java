package com.perceivedev.xpskills;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.perceivedev.xpskills.event.Events;

public class XPSkills extends JavaPlugin {

    private Logger       logger;

    private SkillManager sm;
    @SuppressWarnings("unused")
    private Events       events;

    @Override
    public void onEnable() {
        logger = getLogger();

        sm = new SkillManager(this);

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

        logger.info(versionText() + " enabled");
    }

    /**
     * 
     */
    public boolean load() {
        try {
            sm.load();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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
     * @return the skill manager
     */
    public SkillManager getSkillManager() {
        return sm;
    }

    /**
     * @param path the path to the file (from inside the plugin's data folder)
     * @return the File
     */
    public File getFile(String path) {
        return new File(getDataFolder(), path.replace("/", File.pathSeparator));
    }

}
