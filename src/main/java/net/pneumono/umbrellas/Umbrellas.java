package net.pneumono.umbrellas;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config.BooleanConfiguration;
import net.pneumono.pneumonocore.config.ConfigEnv;
import net.pneumono.pneumonocore.config.Configs;
import net.pneumono.pneumonocore.config.EnumConfiguration;
import net.pneumono.umbrellas.registry.*;
import net.pneumono.umbrellas.util.EnchantmentAbilityType;
import net.pneumono.umbrellas.registry.UmbrellaCauldronBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Umbrellas implements ModInitializer {
	public static final String MOD_ID = "umbrellas";
    public static final Logger LOGGER = LoggerFactory.getLogger("Umbrellas");
	public static final EnumConfiguration<EnchantmentAbilityType> SLOW_FALLING = Configs.register(new EnumConfiguration<>(MOD_ID, "slow_falling",ConfigEnv.SERVER, EnchantmentAbilityType.ENCHANTED_ONLY));
	public static final EnumConfiguration<EnchantmentAbilityType> SMOKE_BOOSTING = Configs.register(new EnumConfiguration<>(MOD_ID, "smoke_boosting", ConfigEnv.SERVER, EnchantmentAbilityType.ENCHANTED_ONLY));
	public static final BooleanConfiguration DURABILITY = Configs.register(new BooleanConfiguration(MOD_ID, "durability", ConfigEnv.SERVER, true));
	public static final BooleanConfiguration ENCHANTMENT_GLINT = Configs.register(new BooleanConfiguration(MOD_ID, "enchantment_glint", ConfigEnv.CLIENT, false));

	@Override
	public void onInitialize() {
		LOGGER.info("Initialising Umbrellas");

		UmbrellasItems.registerUmbrellasItems();
		UmbrellasBlocks.registerUmbrellasBlocks();
		UmbrellasTags.registerUmbrellasTags();
		UmbrellasMisc.registerUmbrellasMisc();
		UmbrellasPatterns.registerUmbrellasPatterns();

		UmbrellaCauldronBehavior.registerCauldronBehavior();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}