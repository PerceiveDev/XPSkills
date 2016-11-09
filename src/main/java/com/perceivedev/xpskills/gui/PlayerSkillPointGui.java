package com.perceivedev.xpskills.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.perceivedev.perceivecore.gui.Gui;
import com.perceivedev.perceivecore.gui.components.implementation.component.simple.SimpleButton;
import com.perceivedev.perceivecore.gui.components.implementation.component.simple.SimpleLabel;
import com.perceivedev.perceivecore.gui.components.implementation.pane.AnchorPane;
import com.perceivedev.perceivecore.util.ItemFactory;
import com.perceivedev.perceivecore.util.MathUtil;
import com.perceivedev.perceivecore.util.TextUtils;
import com.perceivedev.xpskills.XPSkills;
import com.perceivedev.xpskills.managment.PlayerManager.PlayerData;
import com.perceivedev.xpskills.skills.Skill;
import com.perceivedev.xpskills.skills.SkillType;
import com.perceivedev.xpskills.util.ProgressPrinter;

/**
 * A simple Player skill point Gui
 */
public class PlayerSkillPointGui extends Gui {

    private ProgressPrinter printer = new ProgressPrinter("&8[", "&8]&r", "|", "|", "&a", "&7", 50);

    private UUID            playerID;

    // === COMPONENTS ===
    private SimpleLabel     playerSkillPoints;
    private SimpleLabel     playerStats;

    public PlayerSkillPointGui(UUID playerID) {
        // Size is based on number of Skills plus an extra slot for the player
        // head
        super("Gui", MathUtil.clamp((int) Math.ceil((XPSkills.getInstance().getSkillManager().getSkills().size() + 2) / 9), 1, 6));
        this.playerID = playerID;
        init();
    }

    private void init() {
        PlayerData playerData = XPSkills.getInstance().getPlayerManager().getData(playerID);

        // player free skill points
        int skillPointAmount = playerData.getFreeSkillPoints();
        playerSkillPoints = new SimpleLabel(String.format("&6%d Skill Points", skillPointAmount), "&7Use Skill Points to", "&7upgrade your skills");
        playerSkillPoints.setDisplayType(c -> ItemFactory.builder(Material.BOOK));

        ((AnchorPane) getRootPane()).addComponent(playerSkillPoints, 0, 0);

        // player head with stats
        playerStats = createPlayerStats(playerData);
        ((AnchorPane) getRootPane()).addComponent(playerStats, getRootPane().getWidth() - 1, getRootPane().getHeight() - 1);

        // first row skills
        List<Entry<SkillType, Skill>> skills = new ArrayList<>(XPSkills.getInstance().getSkillManager().getSkills().entrySet());

        for (int i = 1; i <= skills.size(); i++) {
            SimpleButton button = createPlayerButton(playerData, skills.get(i - 1));
            ((AnchorPane) getRootPane()).addComponent(button, i % 9, i / 9);
        }

    }

    private SimpleButton createPlayerButton(PlayerData playerData, Entry<SkillType, Skill> skillEntry) {
        SimpleButton button = new SimpleButton(TextUtils.colorize(skillEntry.getValue().describeYourself(playerData.getSkillLevel(skillEntry.getKey()).orElse(0))));
        button.setDisplayType(c -> skillEntry.getValue().getIcon());
        button.setClickHandler((e) -> {
            int amount;
            if ((amount = playerData.useSkillPoint(e, skillEntry.getKey())) < 1) {
                // TODO: I swear, we really need to set up some translations...
                // this is UGLY AS FRACK
                playerData.getPlayer().ifPresent(player -> player.sendMessage(TextUtils.colorize(String.format("&7Leveled &6%s &7up &6%d &7times", skillEntry.getValue().getName(), amount))));
            }
        });
        return button;
    }

    private SimpleLabel createPlayerStats(PlayerData playerData) {

        Player player = getPlayer().orElse(Bukkit.getPlayer(playerID));
        ItemFactory itemFactory = ItemFactory.builder(Material.SKULL_ITEM).setDurability((short) 3).setAmount(1).setSkullOwner(player);

        itemFactory.setLore(String.format("&7Level: &6%d", playerData.getLevel()));
        itemFactory.addLore(String.format("&7Next Level: &d%d XP", getExpToNextLevel(player)));

        double percentage = player.getExp();
        itemFactory.addLore(String.format("%s &6%.0f %%", printer.generate(percentage), percentage * 100));

        itemFactory.addLore(" ");

        for (Entry<SkillType, Skill> entry : XPSkills.getInstance().getSkillManager().getSkills().entrySet()) {
            itemFactory.addLore(entry.getValue().describeYourself(playerData.getSkillLevel(entry.getKey()).orElse(0)));
        }

        SimpleLabel label = new SimpleLabel(String.format("&6%s's Stats", player.getName()));
        label.setDisplayType(c -> itemFactory);

        return label;

    }

    private int getExpToNextLevel(Player player) {
        return (int) Math.ceil(player.getExpToLevel() - (player.getExpToLevel() * player.getExp()));
    }

}
