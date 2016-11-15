package com.perceivedev.xpskills;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.perceivedev.perceivecore.language.I18N;
import com.perceivedev.xpskills.event.PlayerListener;
import com.perceivedev.xpskills.managment.PlayerManager;
import com.perceivedev.xpskills.managment.SkillManager;

public class XPSkills extends JavaPlugin {

    private static XPSkills instance = null;

    private Logger          logger;

    @SuppressWarnings("unused")
    private CommandHandler  commandHandler;

    private PlayerManager   playerManager;
    private SkillManager    skillManager;

    private I18N            language;

    @Override
    public void onEnable() {
        logger = getLogger();

        instance = this;

        setupLanguage();

        skillManager = new SkillManager();
        playerManager = new PlayerManager(this);

        // Ensure the data loads properly
        if (!load()) {
            logger.severe("---------------------------");
            logger.severe("| FAILED TO LOAD XPSKILLS |");
            logger.severe("---------------------------");
            logger.severe("XPSkills will now be disabled");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        commandHandler = new CommandHandler(this);

        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        logger.info(versionText() + " enabled");
    }

    private void setupLanguage() {
        Path output = getDataFolder().toPath().resolve("language");

        if (Files.notExists(output)) {
            try {
                Files.createDirectories(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        I18N.copyDefaultFiles("language", output, false, getFile());

        language = new I18N(this, "language");
    }

    public boolean load() {
        try {
            playerManager.load();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean save() {
        try {
            playerManager.save();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void onDisable() {
        save();

        instance = null;

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

    /**
     * @return the current instance of this plugin
     */
    public static XPSkills getInstance() {
        return instance;
    }

    /**
     * @param key
     * @param formattingObjects
     * @return
     */
    public String tr(String key, Object... formattingObjects) {
        return language.tr(key, formattingObjects);
    }

}
