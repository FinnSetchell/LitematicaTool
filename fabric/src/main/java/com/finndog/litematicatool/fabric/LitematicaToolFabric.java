package com.finndog.litematicatool.fabric;

import com.finndog.litematicatool.CommonClass;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;

public class LitematicaToolFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Registry.register(BuiltInRegistries.ITEM, CommonClass.THE_STICK_KEY, CommonClass.THE_STICK);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> entries.accept(CommonClass.THE_STICK));
        CommonClass.init();
    }
}
