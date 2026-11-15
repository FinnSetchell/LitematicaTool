package com.finndog.litematicatool.fabric;

import com.finndog.litematicatool.LitematicaToolCommon;
import com.finndog.litematicatool.TheStickItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
//? if >=26.1.2 {
/*import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
*///?} else {
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
//?}

public class LitematicaToolFabric implements ModInitializer {
    //? if >=1.21.2 {
    /*public static final Item THE_STICK = new TheStickItem(new Item.Properties().setId(LitematicaToolCommon.ITEM_KEY).stacksTo(1));
    *///?} else {
    public static final Item THE_STICK = new TheStickItem(new Item.Properties().stacksTo(1));
    //?}

    @Override
    public void onInitialize() {
        Registry.register(BuiltInRegistries.ITEM, LitematicaToolCommon.ITEM_KEY, THE_STICK);
        //? if >=26.1.2 {
        /*CreativeModeTabEvents.modifyOutputEvent(LitematicaToolCommon.TOOLS_AND_UTILITIES).register(output -> output.accept(THE_STICK));
        *///?} else {
        ItemGroupEvents.modifyEntriesEvent(LitematicaToolCommon.TOOLS_AND_UTILITIES).register(entries -> entries.accept(THE_STICK));
        //?}
    }
}
