package net.pneumono.umbrellas;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config.BooleanConfiguration;
import net.pneumono.pneumonocore.config.ConfigEnv;
import net.pneumono.pneumonocore.config.Configs;
import net.pneumono.pneumonocore.config.EnumConfiguration;
import net.pneumono.umbrellas.content.UmbrellaPattern;
import net.pneumono.umbrellas.registry.*;
import net.pneumono.umbrellas.util.EnchantmentAbilityType;
import net.pneumono.umbrellas.registry.UmbrellaCauldronBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Umbrellas implements ModInitializer {
	public static final String MOD_ID = "umbrellas";
    public static final Logger LOGGER = LoggerFactory.getLogger("Umbrellas");
	public static final EnumConfiguration<EnchantmentAbilityType> SLOW_FALLING = new EnumConfiguration<>(MOD_ID, "slow_falling",ConfigEnv.SERVER, EnchantmentAbilityType.ENCHANTED_ONLY);
	public static final EnumConfiguration<EnchantmentAbilityType> SMOKE_BOOSTING = new EnumConfiguration<>(MOD_ID, "smoke_boosting", ConfigEnv.SERVER, EnchantmentAbilityType.ENCHANTED_ONLY);
	public static final BooleanConfiguration DURABILITY = new BooleanConfiguration(MOD_ID, "durability", ConfigEnv.SERVER, true);
	public static final BooleanConfiguration ENCHANTMENT_GLINT = new BooleanConfiguration(MOD_ID, "enchantment_glint", ConfigEnv.CLIENT, true);

	@Override
	public void onInitialize() {
		LOGGER.info("Initialising Umbrellas");
		Configs.register(
				MOD_ID,
				SLOW_FALLING,
				SMOKE_BOOSTING,
				DURABILITY,
				ENCHANTMENT_GLINT
		);

		DynamicRegistries.registerSynced(UmbrellaPatterns.UMBRELLA_PATTERN_KEY, UmbrellaPattern.CODEC);

		UmbrellasDataComponents.registerUmbrellasDataComponents();
		UmbrellasItems.registerUmbrellasItems();
		UmbrellasBlocks.registerUmbrellasBlocks();
		UmbrellasTags.registerUmbrellasTags();
		UmbrellasMisc.registerUmbrellasMisc();
		UmbrellaPatterns.registerUmbrellasPatterns();

		UmbrellaCauldronBehavior.registerCauldronBehavior();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}