package com.finndog.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import com.finndog.LitematicaTool;

@Mod(LitematicaTool.MOD_ID)
public final class LitematicaToolNeoForge {

    public LitematicaToolNeoForge(IEventBus modEventBus) {
        // Initialize common code
        LitematicaTool.initNeoForge();
        
        // Register event handlers on the MOD event bus
        modEventBus.addListener(this::onCommonSetup);
    }
    
    private void onCommonSetup(FMLCommonSetupEvent event) {
        
    }
}