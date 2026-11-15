package com.finndog.litematicatool.fabric;

import com.finndog.litematicatool.LitematicaToolItemSetup;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class LitematicaToolFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Why a tick event rather than something during mod loading, and why it is cheap to call
        // every tick, are both explained on LitematicaToolItemSetup.
        ClientTickEvents.END_CLIENT_TICK.register(client -> LitematicaToolItemSetup.apply());
    }
}
