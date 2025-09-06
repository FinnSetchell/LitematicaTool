package com.finndog;

import net.minecraft.world.item.Item;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import com.finndog.items.TheStickItem;

public final class LitematicaTool {
    public static final String MOD_ID = "litematicatool";

    // Items - will be initialized during registration
    public static Item THE_STICK;

    public static void init() {
        // For Fabric: Register items using the built-in registries
        THE_STICK = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "the_stick"), 
            new TheStickItem(new Item.Properties().stacksTo(1)));
    }
    
    public static void initForge() {
        // For Forge: Items are registered via DeferredRegister in the Forge module
        // This method is called after Forge's registration phase
    }
}