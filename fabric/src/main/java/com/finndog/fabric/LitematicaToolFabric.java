package com.finndog.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import com.finndog.LitematicaToolMod;
import com.finndog.LitematicaTool;

public final class LitematicaToolFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        LitematicaToolMod.init();
        
        // Add items to the Tools and Utilities creative tab
        ItemGroupEvents.modifyEntriesEvent(net.minecraft.world.item.CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(LitematicaTool.THE_STICK);
        });
    }
}
