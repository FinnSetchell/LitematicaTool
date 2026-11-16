package com.finndog.litematicatool.fabric;

import com.finndog.litematicatool.Constants;
import com.finndog.litematicatool.LitematicaToolItemSetup;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;

public class LitematicaToolFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // The loader is the only part that knows where the config directory is.
        LitematicaToolItemSetup.rememberIn(
                FabricLoader.getInstance().getConfigDir().resolve(Constants.MOD_ID + "-tool-item-set.txt"));

        // Why a tick event rather than something during mod loading, and why it is cheap to call
        // every tick, are both explained on LitematicaToolItemSetup.
        ClientTickEvents.END_CLIENT_TICK.register(client -> LitematicaToolItemSetup.apply());
    }
}
