package com.perceivedev.xpskills.gui;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.perceivedev.perceivecore.guisystem.Scene;
import com.perceivedev.perceivecore.guisystem.implementation.components.Label;
import com.perceivedev.perceivecore.guisystem.implementation.panes.AnchorPane;
import com.perceivedev.perceivecore.guisystem.util.Dimension;
import com.perceivedev.perceivecore.util.ItemFactory;
import com.perceivedev.xpskills.XPSkills;

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
            {
                int skillPointAmount = XPSkills.getPlugin(XPSkills.class).getPlayerManager().getData(playerID).getFreeSkillPoints();
                ItemStack skillPointsItemStack = ItemFactory
                          .builder(Material.BOOK)
                          .setAmount(skillPointAmount)
                          .setName(String.format("&6%d Skill Points", skillPointAmount))
                          .setLore("&7Use Skill Points to", "&7upgrade your skills")
                          .build();
                playerSkillPoints = new Label(skillPointsItemStack, new Dimension(1, 1));

                addComponent(playerSkillPoints, 0, 0);
            }
        }
    }
}
