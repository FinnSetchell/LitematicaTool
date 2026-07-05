package com.finndog.litematicatool.fabric;

import com.finndog.litematicatool.CommonClass;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.network.chat.Component;

public class LitematicaToolFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> {
            if (stack.getItem() == CommonClass.THE_STICK) {
                lines.add(Component.translatable("item.litematicatool.the_stick.tooltip"));
                lines.add(Component.translatable("item.litematicatool.the_stick.tooltip.line2"));
                lines.add(Component.translatable("item.litematicatool.the_stick.tooltip.line3"));
            }
        });
    }
}
