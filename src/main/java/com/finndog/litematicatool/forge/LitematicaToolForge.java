package com.finndog.litematicatool.forge;

import com.finndog.litematicatool.Constants;
import com.finndog.litematicatool.LitematicaToolCommon;
import com.finndog.litematicatool.TheStickItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
//? if <1.21.10 {
import net.minecraftforge.eventbus.api.IEventBus;
//?}

/**
 * Forge's entrypoint. Forge rebuilt its event bus during this version range: up to 1.21.5 the mod
 * bus is an IEventBus reached through FMLJavaModLoadingContext, and from 1.21.10 it is a BusGroup
 * handed to the mod constructor while each event carries its own static bus. The two eras get a
 * constructor each rather than one constructor with branches inside it.
 *
 * <p>FMLJavaModLoadingContext.get() is deprecated on the 1.21.x Forge builds, and so is
 * ResourceLocation's constructor on 1.20.6. Both are kept deliberately: every node builds against a
 * pinned Forge and Minecraft, so nothing can be removed underneath us, and the replacements either
 * do not exist yet on the oldest versions in the range or are nullable and worse to read.
 */
@Mod(Constants.MOD_ID)
public class LitematicaToolForge {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Constants.MOD_ID);

    //? if >=1.21.2 {
    /*public static final RegistryObject<Item> THE_STICK = ITEMS.register(LitematicaToolCommon.ITEM_NAME,
            () -> new TheStickItem(new Item.Properties().setId(LitematicaToolCommon.ITEM_KEY).stacksTo(1)));
    *///?} else {
    public static final RegistryObject<Item> THE_STICK = ITEMS.register(LitematicaToolCommon.ITEM_NAME,
            () -> new TheStickItem(new Item.Properties().stacksTo(1)));
    //?}

    //? if >=1.21.10 {
    /*public LitematicaToolForge(FMLJavaModLoadingContext context) {
        // The event's own static bus, not getBus(modBusGroup): that overload is deprecated for
        // removal at 1.21.10 and gone by 26.1.2, where this is the only way left.
        ITEMS.register(context.getModBusGroup());
        BuildCreativeModeTabContentsEvent.BUS.addListener(LitematicaToolForge::onCreativeTabBuild);
    }
    *///?} else {
    public LitematicaToolForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        modEventBus.addListener(LitematicaToolForge::onCreativeTabBuild);
    }
    //?}

    private static void onCreativeTabBuild(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == LitematicaToolCommon.TOOLS_AND_UTILITIES) {
            event.accept(THE_STICK);
        }
    }
}
