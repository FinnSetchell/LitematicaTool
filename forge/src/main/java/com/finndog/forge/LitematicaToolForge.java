package com.finndog.forge;

import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.finndog.LitematicaToolMod;
import com.finndog.LitematicaTool;

@Mod(LitematicaToolMod.MOD_ID)
public final class LitematicaToolForge {
    // Create a DeferredRegister for items
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LitematicaToolMod.MOD_ID);
    
    // Register the item
    public static final RegistryObject<Item> THE_STICK = ITEMS.register("the_stick", () -> new Item(new Item.Properties()));
    
    public LitematicaToolForge() {
        // Register the deferred register
        ITEMS.register(net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext.get().getModEventBus());
        
        // Run our common setup (but not the item registration)
        LitematicaTool.initForge();
    }
    
    @Mod.EventBusSubscriber(modid = LitematicaToolMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            // Set the static reference for cross-platform compatibility after items are registered
            LitematicaTool.THE_STICK = THE_STICK.get();
        }
        
        @SubscribeEvent
        public static void addCreative(BuildCreativeModeTabContentsEvent event) {
            // Add items to the Tools and Utilities creative tab
            if (event.getTabKey() == net.minecraft.world.item.CreativeModeTabs.TOOLS_AND_UTILITIES) {
                event.accept(THE_STICK.get());
            }
        }
    }
}
