package com.perceivedev.xpskills.gui;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.perceivedev.perceivecore.guisystem.Scene;
import com.perceivedev.perceivecore.guisystem.implementation.components.Label;
import com.perceivedev.perceivecore.guisystem.implementation.panes.AnchorPane;
import com.perceivedev.perceivecore.guisystem.util.Dimension;
import com.perceivedev.perceivecore.util.ItemFactory;
import com.perceivedev.xpskills.XPSkills;
import com.perceivedev.xpskills.managment.PlayerManager.PlayerData;

/**
 * A simple Player skill point Gui
 */
public class PlayerSkillPointGui extends Scene {

    public PlayerSkillPointGui(UUID playerID) {
        super(new PlayerSkillPointPane(new Dimension(9, 6), playerID), 6 * 9, "Gui");
    }

    private static class PlayerSkillPointPane extends AnchorPane {

        private UUID playerID;

        // === COMPONENTS ===
        private Label playerSkillPoints;
        private Label playerStats;

        // === SKILLS ===

        private PlayerSkillPointPane(Dimension size, UUID playerID) {
            super(size);
            this.playerID = playerID;

            init();
        }

        private void init() {
            PlayerData playerData = XPSkills.getPlugin(XPSkills.class).getPlayerManager().getData(playerID);
            {
                int skillPointAmount = playerData.getFreeSkillPoints();
                ItemStack skillPointsItemStack = ItemFactory
                          .builder(Material.BOOK)
                          .setAmount(skillPointAmount)
                          .setName(String.format("&6%d Skill Points", skillPointAmount))
                          .setLore("&7Use Skill Points to", "&7upgrade your skills")
                          .build();
                playerSkillPoints = new Label(skillPointsItemStack, new Dimension(1, 1));

                addComponent(playerSkillPoints, 0, 0);
            }

            {
                playerStats = createPlayerStats(playerData);
                addComponent(playerStats, 8, 0);
            }
        }

        private Label createPlayerStats(PlayerData playerData) {
            Player player = getPlayer();
            ItemFactory itemFactory = ItemFactory.builder(Material.SKULL_ITEM)
                      .setDurability((short) 3)
                      .setName(String.format("&6%s's Stats", player.getName()))
                      .setAmount(1)
                      .setSkullOwner(getPlayer());

            itemFactory.setLore(String.format("&7Level: &6%d", playerData.getLevel()));
            itemFactory.addLore(String.format("&7Next Level: &d%d XP",
                      getExpToNextLevel(player)
            ));
            return new Label(itemFactory.build(), new Dimension(1, 1));
        }

        private int getExpToNextLevel(Player player) {
            return (int) Math.ceil(player.getExpToLevel() - (player.getExpToLevel() * player.getExp()));
        }

        private Player getPlayer() {
            return Bukkit.getPlayer(playerID);
        }
    }
}
