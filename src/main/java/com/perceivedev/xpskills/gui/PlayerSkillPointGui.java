package com.perceivedev.xpskills.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.perceivedev.perceivecore.gui.Gui;
import com.perceivedev.perceivecore.gui.components.panes.AnchorPane;
import com.perceivedev.perceivecore.gui.components.simple.SimpleButton;
import com.perceivedev.perceivecore.gui.components.simple.SimpleLabel;
import com.perceivedev.perceivecore.util.ItemFactory;
import com.perceivedev.perceivecore.util.MathUtil;
import com.perceivedev.perceivecore.util.TextUtils;
import com.perceivedev.xpskills.XPSkills;
import com.perceivedev.xpskills.managment.PlayerManager.PlayerData;
import com.perceivedev.xpskills.skills.Skill;
import com.perceivedev.xpskills.skills.SkillType;

/**
 * A simple Player skill point Gui
 */
public class PlayerSkillPointGui extends Gui {

    private UUID                 playerID;

    // === COMPONENTS ===
    private SimpleLabel          playerSkillPoints;
    private PlayerStatsComponent playerStats;

    public PlayerSkillPointGui(UUID playerID) {
        // Size is based on number of Skills plus an extra slot for the player
        // head
        super("Gui", MathUtil.clamp((int) Math.ceil((XPSkills.getInstance().getSkillManager().getSkills().size() + 2) / 9), 1, 6));
        this.playerID = playerID;
        init();
    }

    private void init() {
        PlayerData playerData = XPSkills.getInstance().getPlayerManager().getData(playerID);
        Player player = playerData.getPlayer().orElse(Bukkit.getPlayer(playerID));

        playerSkillPoints = new SimpleLabel("") {
            @Override
            public void render(Inventory inventory, Player player, int offsetX, int offsetY) {
                setName(XPSkills.getInstance().tr("gui.available.name", playerData.getFreeSkillPoints()));
                setLore(XPSkills.getInstance().tr("gui.available.lore").split("\n"));
                super.render(inventory, player, offsetX, offsetY);
            }
        };
        playerSkillPoints.setDisplayType(c -> ItemFactory.builder(Material.BOOK));

        ((AnchorPane) getRootPane()).addComponent(playerSkillPoints, 0, 0);

        // player head with stats
        playerStats = new PlayerStatsComponent(player);
        ((AnchorPane) getRootPane()).addComponent(playerStats, getRootPane().getWidth() - 1, getRootPane().getHeight() - 1);

        // first row skills
        List<Entry<SkillType, Skill>> skills = new ArrayList<>(XPSkills.getInstance().getSkillManager().getSkills().entrySet());

        for (int i = 1; i <= skills.size(); i++) {
            SimpleButton button = createPlayerButton(playerData, skills.get(i - 1));
            ((AnchorPane) getRootPane()).addComponent(button, i % 9, i / 9);
        }

    }

    private SimpleButton createPlayerButton(PlayerData playerData, Entry<SkillType, Skill> skillEntry) {
        SimpleButton button = new SimpleButton("") {
            @Override
            public void render(Inventory inventory, Player player, int offsetX, int offsetY) {
                setName(TextUtils.colorize(skillEntry.getValue().describeYourself(playerData.getSkillLevel(skillEntry.getKey()).orElse(0))));
                super.render(inventory, player, offsetX, offsetY);
            }
        };
        button.setDisplayType(c -> skillEntry.getValue().getIcon());
        button.setClickHandler((e) -> {
            int amount;
            if ((amount = playerData.useSkillPoint(e, skillEntry.getKey())) > 0) {
                // TODO: I swear, we really need to set up some translations...
                // this is UGLY AS FRACK
                playerData.getPlayer().ifPresent(player -> {
                    player.sendMessage(TextUtils.colorize(String.format("&7Leveled &6%s &7up &6%d &7times", skillEntry.getValue().getName(), amount)));
                });
                update();
            }
        });
        return button;
    }

    private void update() {
        init();
        getRootPane().requestReRender();
    }

    /*
     * private SimpleLabel createPlayerStats(PlayerData playerData) {
     * 
     * XPSkills plugin = XPSkills.getInstance();
     * 
     * Player player = getPlayer().orElse(Bukkit.getPlayer(playerID));
     * ItemFactory itemFactory =
     * ItemFactory.builder(Material.SKULL_ITEM).setDurability((short)
     * 3).setAmount(1).setSkullOwner(player);
     * 
     * double percentage = player.getExp();
     * 
     * itemFactory.setLore(
     * plugin.tr("gui.stats.lore", playerData.getLevel(),
     * getExpToNextLevel(player), printer.generate(percentage), percentage *
     * 100));
     * 
     * for (Entry<SkillType, Skill> entry :
     * plugin.getSkillManager().getSkills().entrySet()) {
     * itemFactory.addLore(entry.getValue().describeYourself(playerData.
     * getSkillLevel(entry.getKey()).orElse(0)));
     * }
     * 
     * SimpleLabel label = new SimpleLabel(plugin.tr("gui.stats.name",
     * player.getName()));
     * label.setDisplayType(c -> itemFactory);
     * 
     * return label;
     * 
     * }
     */

}
