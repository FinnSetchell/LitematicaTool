package com.finndog.litematicatool.neoforge;

import com.finndog.litematicatool.Constants;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod(Constants.MOD_ID)
public class LitematicaToolNeoForge {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MOD_ID);
    public static final Supplier<Item> THE_STICK = ITEMS.registerItem("the_stick", props -> new Item(props.stacksTo(1)));

    public LitematicaToolNeoForge(IEventBus modBus) {
        ITEMS.register(modBus);
    }

    @EventBusSubscriber(modid = Constants.MOD_ID)
    public static class Events {
        @SubscribeEvent
        public static void onCreativeTabBuild(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                event.accept(THE_STICK.get());
            }
        }
    }
}
