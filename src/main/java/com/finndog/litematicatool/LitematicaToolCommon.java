package com.finndog.litematicatool;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

/**
 * The ids every loader registers against, in one place so the three entrypoints cannot drift.
 */
public final class LitematicaToolCommon {
    public static final String ITEM_NAME = "the_stick";

    // CreativeModeTabs.TOOLS_AND_UTILITIES is private on every version from 1.20 to 26.3. Fabric's
    // transitive access wideners open it up, but Forge and NeoForge would each need an access
    // transformer to read it, so the key is rebuilt from the tab's registry id instead, which needs
    // no loader support at all.
    private static final String TOOLS_AND_UTILITIES_ID = "tools_and_utilities";

    //? if >=1.21.1 {
    public static final ResourceLocation ITEM_ID = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, ITEM_NAME);
    private static final ResourceLocation TOOLS_AND_UTILITIES_LOCATION = ResourceLocation.withDefaultNamespace(TOOLS_AND_UTILITIES_ID);
    //?} else {
    /*public static final ResourceLocation ITEM_ID = new ResourceLocation(Constants.MOD_ID, ITEM_NAME);
    private static final ResourceLocation TOOLS_AND_UTILITIES_LOCATION = new ResourceLocation(TOOLS_AND_UTILITIES_ID);
    *///?}

    public static final ResourceKey<Item> ITEM_KEY = ResourceKey.create(Registries.ITEM, ITEM_ID);
    public static final ResourceKey<CreativeModeTab> TOOLS_AND_UTILITIES = ResourceKey.create(Registries.CREATIVE_MODE_TAB, TOOLS_AND_UTILITIES_LOCATION);

    private LitematicaToolCommon() {}
}
