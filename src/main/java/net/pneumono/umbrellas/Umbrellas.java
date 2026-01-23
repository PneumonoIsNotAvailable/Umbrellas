//~ identifier_replacements

package net.pneumono.umbrellas;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.resources.Identifier;
import net.pneumono.umbrellas.content.UmbrellaPattern;
import net.pneumono.umbrellas.registry.*;
import net.pneumono.umbrellas.registry.UmbrellaCauldronInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Umbrellas implements ModInitializer {
	public static final String MOD_ID = "umbrellas";
    public static final Logger LOGGER = LoggerFactory.getLogger("Umbrellas");

	@Override
	public void onInitialize() {
		LOGGER.info("Initialising Umbrellas");
		UmbrellasConfig.registerUmbrellasConfigs();

		DynamicRegistries.registerSynced(UmbrellaPatterns.UMBRELLA_PATTERN_KEY, UmbrellaPattern.CODEC);

		UmbrellasDataComponents.registerUmbrellasDataComponents();
		UmbrellasItems.registerUmbrellasItems();
		UmbrellasEnchantments.registerUmbrellasEnchantments();
		UmbrellasBlocks.registerUmbrellasBlocks();
		UmbrellasTags.registerUmbrellasTags();
		UmbrellasMisc.registerUmbrellasMisc();
		UmbrellaPatterns.registerUmbrellasPatterns();

		UmbrellaCauldronInteraction.registerCauldronBehavior();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}