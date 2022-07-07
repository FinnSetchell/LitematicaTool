package com.FinnDog.litematicatool;

import com.FinnDog.litematicatool.registry.ModItems;
import net.fabricmc.api.ModInitializer;

public class LitematicaTool implements ModInitializer {

    public static final String MOD_ID = "litematicatool";

    @Override
    public void onInitialize() {
        ModItems.registerItems();

    }
}
