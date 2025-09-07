package com.finndog.neoforge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import com.finndog.LitematicaTool;

@EventBusSubscriber(modid = LitematicaTool.MOD_ID, value = Dist.CLIENT)
public class LitematicaToolNeoForgeClient {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().is(LitematicaTool.THE_STICK)) {
            event.getToolTip().add(net.minecraft.network.chat.Component.translatable("item.litematicatool.the_stick.tooltip"));
            event.getToolTip().add(net.minecraft.network.chat.Component.translatable("item.litematicatool.the_stick.tooltip.line2"));
            event.getToolTip().add(net.minecraft.network.chat.Component.translatable("item.litematicatool.the_stick.tooltip.line3"));
        }
    }
}
