package com.finndog.litematicatool;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.function.Consumer;
//? if >=1.21.5 {
/*import net.minecraft.world.item.component.TooltipDisplay;
*///?} else {
import java.util.List;
//?}
//? if <1.20.6 {
/*import net.minecraft.world.level.Level;
*///?}

/**
 * The stick, whose only behaviour is the three tooltip lines explaining what Litematica does with
 * it. The tooltip lives on the item rather than in each loader's tooltip event so that one override
 * covers Fabric, Forge and NeoForge alike; only the vanilla signature changes between versions.
 *
 * <p>From 1.21.5 vanilla marks appendHoverText deprecated, but not for removal: it is still the
 * method the game calls to let an item add its own lines, and its body in Item is empty for
 * exactly that purpose. The override stays.
 */
public class TheStickItem extends Item {
    private static final String[] TOOLTIP_KEYS = {
            "item.litematicatool.the_stick.tooltip",
            "item.litematicatool.the_stick.tooltip.line2",
            "item.litematicatool.the_stick.tooltip.line3",
    };

    public TheStickItem(Properties properties) {
        super(properties);
    }

    private static void addTooltip(Consumer<Component> out) {
        for (String key : TOOLTIP_KEYS) {
            out.accept(Component.translatable(key));
        }
    }

    //? if >=1.21.5 {
    /*@Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> out, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, out, flag);
        addTooltip(out);
    }
    *///?}
    //? if >=1.20.6 <1.21.5 {
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> lines, TooltipFlag flag) {
        super.appendHoverText(stack, context, lines, flag);
        addTooltip(lines::add);
    }
    //?}
    //? if <1.20.6 {
    /*@Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> lines, TooltipFlag flag) {
        super.appendHoverText(stack, level, lines, flag);
        addTooltip(lines::add);
    }
    *///?}
}
