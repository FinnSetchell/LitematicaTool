package com.finndog.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import com.finndog.LitematicaTool;
import com.finndog.items.TheStickItem;

public final class LitematicaToolFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // Register the item
        LitematicaTool.THE_STICK = Registry.register(BuiltInRegistries.ITEM, 
            new ResourceLocation(LitematicaTool.MOD_ID, "the_stick"), 
            new TheStickItem(new Item.Properties().stacksTo(1)));
        
        // Add items to the Tools and Utilities creative tab
        ItemGroupEvents.modifyEntriesEvent(net.minecraft.world.item.CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(LitematicaTool.THE_STICK);
        });
        
        // Initialize common code
        LitematicaTool.initFabric();
    }
}
