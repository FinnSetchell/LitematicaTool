package com.finndog.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class TheStickItem extends Item {
    public TheStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.litematicatool.the_stick.tooltip"));
        tooltip.add(Component.translatable("item.litematicatool.the_stick.tooltip.line2"));
        tooltip.add(Component.translatable("item.litematicatool.the_stick.tooltip.line3"));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
