/**
 * 
 */
package com.perceivedev.xpskills.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.perceivedev.perceivecore.gui.components.simple.SimpleLabel;
import com.perceivedev.perceivecore.util.ItemFactory;
import com.perceivedev.xpskills.XPSkills;
import com.perceivedev.xpskills.managment.PlayerManager.PlayerData;
import com.perceivedev.xpskills.skills.Skill;
import com.perceivedev.xpskills.skills.SkillType;
import com.perceivedev.xpskills.util.ProgressPrinter;

/**
 * @author Rayzr
 *
 */
public class PlayerStatsComponent extends SimpleLabel {

    private ProgressPrinter printer = new ProgressPrinter("&8[", "&8]&r", "|", "|", "&a", "&7", 50);

    private Player          player;

    public PlayerStatsComponent(Player player) {
        super(XPSkills.getInstance().tr("gui.stats.name", player.getName()));
        this.player = player;

        ItemFactory itemFactory = ItemFactory.builder(Material.SKULL_ITEM).setDurability((short) 3).setAmount(1).setSkullOwner(player);
        setDisplayType(c -> itemFactory);
    }

    @Override
    public void render(Inventory inventory, Player unused, int offsetX, int offsetY) {
        XPSkills plugin = XPSkills.getInstance();
        PlayerData data = plugin.getPlayerManager().getData(player.getUniqueId());

        List<String> lore = new ArrayList<String>();

        double percentage = player.getExp();
        for (String str : plugin.tr("gui.stats.lore", data.getLevel(), getExpToNextLevel(player), printer.generate(percentage), percentage * 100).split("\n")) {
            lore.add(str);
        }

        lore.add(" ");

        for (Entry<SkillType, Skill> entry : plugin.getSkillManager().getSkills().entrySet()) {
            lore.add(entry.getValue().describeYourself(data.getSkillLevel(entry.getKey()).orElse(0)));
        }

        setLore(lore);

        super.render(inventory, player, offsetX, offsetY);
    }

    private int getExpToNextLevel(Player player) {
        return (int) Math.ceil(player.getExpToLevel() - (player.getExpToLevel() * player.getExp()));
    }

}
