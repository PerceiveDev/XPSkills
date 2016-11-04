package com.perceivedev.xpskills.skills.types;

import java.util.function.BiFunction;

import org.bukkit.inventory.ItemStack;

import com.perceivedev.perceivecore.util.ItemFactory;
import com.perceivedev.xpskills.skills.Skill;

/**
 * An abstract base class
 */
public abstract class AbstractSkill implements Skill {

    private int                                        maxLevel;
    private double                                     increasePerLevel;
    private double                                     increaseCap;
    private String                                     name;
    private BiFunction<AbstractSkill, Integer, String> descriptionFunction;
    private ItemStack                                  icon;

    /**
     * @param maxLevel The max level of the skill
     * @param name The name of the skill
     * @param increaseCap The maximum increase
     * @param increasePerLevel The increase per level
     * @param descriptionFunction A function generating a String describing the
     *            skill.
     */
    public AbstractSkill(int maxLevel, String name, double increasePerLevel, double increaseCap,
            BiFunction<AbstractSkill, Integer, String> descriptionFunction, ItemStack icon) {
        this.maxLevel = maxLevel;
        this.name = name;
        this.increaseCap = increaseCap;
        this.increasePerLevel = increasePerLevel;
        this.descriptionFunction = descriptionFunction;
        this.icon = icon.clone();
    }

    /**
     * @return The maximum increase
     */
    protected double getIncreaseCap() {
        return increaseCap;
    }

    /**
     * @return the increase per level
     */
    protected double getIncreasePerLevel() {
        return increasePerLevel;
    }

    /**
     * Returns the modifier at the given level
     *
     * @param level The level of the skill
     *
     * @return The currently applying modifier of the skill
     */
    protected double getApplyingIncrease(int level) {
        return Math.min(level * getIncreasePerLevel(), getIncreaseCap());
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String describeYourself(int level) {
        return descriptionFunction.apply(this, level);
    }

    @Override
    public ItemFactory getIcon() {
        return ItemFactory.builder(icon);
    }
}
