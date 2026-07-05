package com.finndog.litematicatool.neoforge;

import com.finndog.litematicatool.CommonClass;
import com.finndog.litematicatool.Constants;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Constants.MOD_ID)
public class LitematicaToolNeoForge {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MOD_ID);

    public LitematicaToolNeoForge(IEventBus modBus) {
        ITEMS.register("the_stick", () -> CommonClass.THE_STICK);
        ITEMS.register(modBus);
        CommonClass.init();
    }

    @EventBusSubscriber(modid = Constants.MOD_ID)
    public static class Events {
        @SubscribeEvent
        public static void onCreativeTabBuild(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                event.accept(CommonClass.THE_STICK);
            }
        }
    }
}
