package com.finndog.litematicatool.neoforge.client;

import com.finndog.litematicatool.CommonClass;
import com.finndog.litematicatool.Constants;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class LitematicaToolNeoForgeClient {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() == CommonClass.THE_STICK) {
            event.getToolTip().add(Component.translatable("item.litematicatool.the_stick.tooltip"));
            event.getToolTip().add(Component.translatable("item.litematicatool.the_stick.tooltip.line2"));
            event.getToolTip().add(Component.translatable("item.litematicatool.the_stick.tooltip.line3"));
        }
    }
}
