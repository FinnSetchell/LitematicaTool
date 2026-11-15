package com.finndog.litematicatool.neoforge;

import com.finndog.litematicatool.Constants;
import com.finndog.litematicatool.LitematicaToolCommon;
import com.finndog.litematicatool.TheStickItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod(Constants.MOD_ID)
public class LitematicaToolNeoForge {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MOD_ID);
    public static final Supplier<Item> THE_STICK = ITEMS.registerItem(LitematicaToolCommon.ITEM_NAME, props -> new TheStickItem(props.stacksTo(1)));

    public LitematicaToolNeoForge(IEventBus modBus) {
        ITEMS.register(modBus);
        modBus.addListener(LitematicaToolNeoForge::onCreativeTabBuild);
    }

    private static void onCreativeTabBuild(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == LitematicaToolCommon.TOOLS_AND_UTILITIES) {
            event.accept(THE_STICK.get());
        }
    }
}
