package com.finndog.litematicatool.fabric;

import com.finndog.litematicatool.Constants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public class LitematicaToolFabric implements ModInitializer {
    public static final ResourceKey<Item> THE_STICK_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "the_stick"));
    public static final Item THE_STICK = new Item(new Item.Properties().setId(THE_STICK_KEY).stacksTo(1));

    @Override
    public void onInitialize() {
        Registry.register(BuiltInRegistries.ITEM, THE_STICK_KEY, THE_STICK);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> entries.accept(THE_STICK));
    }
}
