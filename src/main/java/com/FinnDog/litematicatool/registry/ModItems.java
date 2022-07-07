package com.FinnDog.litematicatool.registry;

import com.FinnDog.litematicatool.LitematicaTool;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item THE_STICK = new Item(new Item.Settings().group(ItemGroup.TOOLS));

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(LitematicaTool.MOD_ID, "the_stick"), THE_STICK);
    }
}

