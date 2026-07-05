package com.finndog.litematicatool;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class CommonClass {
    public static final ResourceKey<Item> THE_STICK_KEY = ResourceKey.create(
        Registries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "the_stick")
    );
    public static final Item THE_STICK = new Item(new Item.Properties().setId(THE_STICK_KEY).stacksTo(1));

    public static void init() {}
}
