package com.finndog.fabric;

import net.fabricmc.api.ModInitializer;
import com.finndog.LitematicaTool;

public final class LitematicaToolFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // Initialize common code
        LitematicaTool.initFabric();
    }
}
