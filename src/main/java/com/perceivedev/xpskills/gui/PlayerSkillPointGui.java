package com.perceivedev.xpskills.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.perceivedev.perceivecore.gui.DisplayType;
import com.perceivedev.perceivecore.gui.GUI;
import com.perceivedev.perceivecore.gui.component.Button;
import com.perceivedev.perceivecore.gui.component.Label;
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
public class PlayerSkillPointGui extends GUI {

    private ProgressPrinter printer = new ProgressPrinter("&8[", "&8]&r", "|", "|", "&a", "&7", 50);

    private UUID            playerID;

    // === COMPONENTS ===
    private Label           playerSkillPoints;
    private Label           playerStats;

    public PlayerSkillPointGui(UUID playerID) {
        super("Gui", 6, DisplayType.EMPTY);
        this.playerID = playerID;
        init();
    }

    private void init() {
        PlayerData playerData = XPSkills.getInstance().getPlayerManager().getData(playerID);

        // player free skill points
        int skillPointAmount = playerData.getFreeSkillPoints();
        playerSkillPoints = new Label(String.format("&6%d Skill Points", skillPointAmount), "&7Use Skill Points to", "&7upgrade your skills");
        playerSkillPoints.setDisplayType(DisplayType.custom(Material.BOOK, 0, skillPointAmount));

        addComponent(playerSkillPoints, 0, 0);

        // player head with stats
        playerStats = createPlayerStats(playerData);
        addComponent(playerStats, 8, 0);

        // first row skills
        List<Entry<SkillType, Skill>> skills = new ArrayList<>(XPSkills.getInstance().getSkillManager().getSkills().entrySet());

        for (int i = 0; i < skills.size(); i++) {
            Button button = createPlayerButton(playerData, skills.get(i));
            addComponent(button, i + 1, 0);
        }

    }

    private Button createPlayerButton(PlayerData playerData, Entry<SkillType, Skill> skillEntry) {
        // TODO: Make these buttons _do_ something
        return (Button) new Button(TextUtils.colorize(skillEntry.getValue().describeYourself(playerData.getSkillLevel(skillEntry.getKey()).orElse(0))))
                .setDisplayType(DisplayType.custom(skillEntry.getValue().getIcon()));
    }

    private Label createPlayerStats(PlayerData playerData) {

        Player player = getPlayer();
        ItemFactory itemFactory = ItemFactory.builder(Material.SKULL_ITEM).setDurability((short) 3).setAmount(1).setSkullOwner(getPlayer());

        itemFactory.setLore(String.format("&7Level: &6%d", playerData.getLevel()));
        itemFactory.addLore(String.format("&7Next Level: &d%d XP", getExpToNextLevel(player)));

        double percentage = player.getExp();
        itemFactory.addLore(String.format("%s &6%.0f %%", printer.generate(percentage), percentage * 100));

        itemFactory.addLore(" ");

        for (Entry<SkillType, Skill> entry : XPSkills.getPlugin(XPSkills.class).getSkillManager().getSkills().entrySet()) {
            itemFactory.addLore(entry.getValue().describeYourself(playerData.getSkillLevel(entry.getKey()).orElse(0)));
        }

        return (Label) new Label(String.format("&6%s's Stats", player.getName())).setDisplayType(DisplayType.custom(itemFactory.build()));
    }

    private int getExpToNextLevel(Player player) {
        return (int) Math.ceil(player.getExpToLevel() - (player.getExpToLevel() * player.getExp()));
    }

    private Player getPlayer() {
        return Bukkit.getPlayer(playerID);
    }
}
