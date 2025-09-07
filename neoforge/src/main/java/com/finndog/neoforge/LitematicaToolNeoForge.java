package com.finndog.neoforge;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.finndog.LitematicaTool;
import com.finndog.items.TheStickItem;

@Mod(LitematicaTool.MOD_ID)
public final class LitematicaToolNeoForge {
    // Create a DeferredRegister for items
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(net.minecraft.core.registries.BuiltInRegistries.ITEM, LitematicaTool.MOD_ID);
    
    // Register the item
    public static final net.neoforged.neoforge.registries.DeferredHolder<Item, Item> THE_STICK = ITEMS.register("the_stick", () -> new TheStickItem(new Item.Properties().stacksTo(1)));

    public LitematicaToolNeoForge(IEventBus modEventBus) {
        // Register the deferred register
        ITEMS.register(modEventBus);
        
        // Register event handlers on the MOD event bus
        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::addCreative);
    }
    
    private void onCommonSetup(FMLCommonSetupEvent event) {
        // Set the static reference for cross-platform compatibility
        LitematicaTool.THE_STICK = THE_STICK.get();
        
        // Initialize common code
        LitematicaTool.initNeoForge();
    }
    
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // Add items to the Tools and Utilities creative tab
        if (event.getTabKey() == net.minecraft.world.item.CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(THE_STICK.get());
        }
    }
}