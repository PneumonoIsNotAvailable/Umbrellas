package net.pneumono.umbrellas;

import net.fabricmc.api.ModInitializer;

import net.pneumono.umbrellas.content.ModContent;
import net.pneumono.umbrellas.content.UmbrellaCauldronBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Umbrellas implements ModInitializer {
	public static final String MOD_ID = "umbrellas";
    public static final Logger LOGGER = LoggerFactory.getLogger("Umbrellas");

	@Override
	public void onInitialize() {
		LOGGER.info("Initialising Umbrellas");

		ModContent.registerModContent();
		UmbrellaCauldronBehavior.registerCauldronBehavior();
	}
}