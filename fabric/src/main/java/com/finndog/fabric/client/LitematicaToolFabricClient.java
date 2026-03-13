package com.finndog.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.network.chat.Component;
import com.finndog.LitematicaTool;

public final class LitematicaToolFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register tooltip callback for The Stick item
        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> {
            if (stack.getItem().equals(LitematicaTool.THE_STICK.get())) {
                lines.add(Component.translatable("item.litematicatool.the_stick.tooltip"));
                lines.add(Component.translatable("item.litematicatool.the_stick.tooltip.line2"));
                lines.add(Component.translatable("item.litematicatool.the_stick.tooltip.line3"));
            }
        });
    }
}
