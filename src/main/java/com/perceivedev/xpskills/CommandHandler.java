/**
 *
 */
package com.perceivedev.xpskills;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.perceivedev.xpskills.gui.PlayerSkillPointGui;

/**
 * @author Rayzr
 */
public class CommandHandler implements CommandExecutor {

    @SuppressWarnings("unused")
    private XPSkills plugin;

    public CommandHandler(XPSkills plugin) {
        this.plugin = plugin;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.
     * CommandSender, org.bukkit.command.Command, java.lang.String,
     * java.lang.String[])
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            // TODO: Tell them they can't do that
            return true;
        }

        Player p = (Player) sender;

        if (args.length < 1) {
            new PlayerSkillPointGui(p.getUniqueId()).open(p);
            return true;
        }

        return true;

    }

}
