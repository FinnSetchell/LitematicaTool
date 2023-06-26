package com.finndog.litematicatool;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {

	public static final String MOD_ID = "litematicatool";
	public static final Logger LOGGER = LoggerFactory.getLogger("litematicatool");

	@Override
	public void onInitialize() {

		ModItemGroup.registerItemGroups();
		ModItems.registerModItems();
	}
}
