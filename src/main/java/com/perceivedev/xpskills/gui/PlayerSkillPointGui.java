package com.perceivedev.xpskills.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.perceivedev.perceivecore.guisystem.Scene;
import com.perceivedev.perceivecore.guisystem.implementation.components.Button;
import com.perceivedev.perceivecore.guisystem.implementation.components.Label;
import com.perceivedev.perceivecore.guisystem.implementation.panes.AnchorPane;
import com.perceivedev.perceivecore.guisystem.implementation.panes.GridPane;
import com.perceivedev.perceivecore.guisystem.util.Dimension;
import com.perceivedev.perceivecore.util.ItemFactory;
import com.perceivedev.perceivecore.util.TextUtils;
import com.perceivedev.xpskills.XPSkills;
import com.perceivedev.xpskills.managment.PlayerManager.PlayerData;
import com.perceivedev.xpskills.skills.Skill;
import com.perceivedev.xpskills.skills.SkillType;
import com.perceivedev.xpskills.util.ProgressPrinter;

/**
 * A simple Player skill point Gui
 */
public class PlayerSkillPointGui extends Scene {

    public PlayerSkillPointGui(UUID playerID) {
        super(new PlayerSkillPointPane(new Dimension(9, 6), playerID), 6 * 9, "Gui");
    }

    private static class PlayerSkillPointPane extends AnchorPane {

        private ProgressPrinter printer = new ProgressPrinter("&8[", "&8]&r", "|", "|", "&a", "&7", 50);

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

            // player free skill points
            {
                int skillPointAmount = playerData.getFreeSkillPoints();
                ItemStack skillPointsItemStack = ItemFactory
                          .builder(Material.BOOK)
                          .setAmount(skillPointAmount)
                          .setName(String.format("&6%d Skill Points", skillPointAmount))
                          .setLore("&7Use Skill Points to", "&7upgrade your skills")
                          .build();
                playerSkillPoints = new Label(skillPointsItemStack, Dimension.ONE);

                addComponent(playerSkillPoints, 0, 0);
            }

            // player head with stats
            {
                playerStats = createPlayerStats(playerData);
                addComponent(playerStats, 8, 0);
            }

            // first row skills
            {
                GridPane skillRowOne = new GridPane(new Dimension(9, 1), 9, 1);
                List<Entry<SkillType, Skill>> skills = new ArrayList<>(XPSkills.getPlugin(XPSkills.class).getSkillManager().getSkills().entrySet());

                for (int i = 0; i < skills.size(); i++) {
                    Button button = createPlayerButton(playerData, skills.get(i));
                    skillRowOne.addComponent(button, i + 1, 0);
                }

                addComponent(skillRowOne, 0, 2);
            }
        }

        private Button createPlayerButton(PlayerData playerData, Entry<SkillType, Skill> skillEntry) {
            ItemFactory itemFactory = ItemFactory.builder(skillEntry.getValue().getIcon());

            // TODO: 23.10.2016 Make this nicer 
            itemFactory.setDisplayName(TextUtils.colorize(skillEntry.getValue().describeYourself(playerData.getSkillLevel(skillEntry.getKey()).orElse(0))));
            return new Button(itemFactory.build(), Dimension.ONE);
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
            {
                double percentage = player.getExp();
                itemFactory.addLore(String.format("%s &6%.0f %%", printer.generate(percentage), percentage * 100));
            }

            itemFactory.addLore(" ");

            for (Entry<SkillType, Skill> entry : XPSkills.getPlugin(XPSkills.class).getSkillManager().getSkills().entrySet()) {
                itemFactory.addLore(entry.getValue().describeYourself(
                          playerData.getSkillLevel(entry.getKey()).orElse(0))
                );
            }
            return new Label(itemFactory.build(), Dimension.ONE);
        }

        private int getExpToNextLevel(Player player) {
            return (int) Math.ceil(player.getExpToLevel() - (player.getExpToLevel() * player.getExp()));
        }

        private Player getPlayer() {
            return Bukkit.getPlayer(playerID);
        }
    }
}
