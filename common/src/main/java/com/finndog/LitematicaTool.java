package com.finndog;

import net.minecraft.world.item.Item;

public final class LitematicaTool {
    public static final String MOD_ID = "litematicatool";
    
    // Items - will be initialized during registration
    public static Item THE_STICK;
    
    public static void init() {
        // This will be called by each platform's initialization
        // The actual registration happens in platform-specific code
    }
    
    public static void initNeoForge() {
        // NeoForge-specific initialization
        init();
    }
    
    public static void initFabric() {
        // Fabric-specific initialization
        init();
    }
}