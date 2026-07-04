package com.finndog;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import com.finndog.items.TheStickItem;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public final class LitematicaTool {
    public static final String MOD_ID = "litematicatool";
    
    // Create DeferredRegister for items
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);


    public static final ResourceKey<Item> THE_STICK_KEY = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "the_stick"));

    public static final RegistrySupplier<Item> THE_STICK = ITEMS.register("the_stick", () -> 
            new TheStickItem(new Item.Properties().setId(THE_STICK_KEY).stacksTo(1)));
    
    public static void init() {
        // Register the items deferred register
        ITEMS.register();
        
        // Add items to the Tools and Utilities creative tab
        CreativeTabRegistry.modify(CreativeTabRegistry.defer(net.minecraft.world.item.CreativeModeTabs.TOOLS_AND_UTILITIES), (flags, output, hasOp) -> {
            output.accept(THE_STICK.get());
        });
    }
    
    public static void initNeoForge() {
        init();
    }
    
    public static void initFabric() {
        init();
    }
}