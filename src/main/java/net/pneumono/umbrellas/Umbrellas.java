package net.pneumono.umbrellas;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config.BooleanConfiguration;
import net.pneumono.pneumonocore.config.ConfigEnv;
import net.pneumono.pneumonocore.config.Configs;
import net.pneumono.pneumonocore.config.EnumConfiguration;
import net.pneumono.umbrellas.content.AbilityType;
import net.pneumono.umbrellas.content.UmbrellaCauldronBehavior;
import net.pneumono.umbrellas.content.UmbrellasContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class Umbrellas implements ModInitializer {
	public static final String MOD_ID = "umbrellas";
    public static final Logger LOGGER = LoggerFactory.getLogger("Umbrellas");

	public static final BooleanConfiguration PRIDE_UMBRELLAS = Configs.register(new BooleanConfiguration(MOD_ID, "pride_umbrellas", ConfigEnv.SERVER, false));
	public static final BooleanConfiguration GLIDING = Configs.register(new BooleanConfiguration(MOD_ID, "gliding", ConfigEnv.SERVER, true));
	public static final EnumConfiguration<AbilityType> SLOW_FALLING = Configs.register(new EnumConfiguration<>(MOD_ID, "slow_falling",ConfigEnv.SERVER, AbilityType.GLIDING_ONLY));
	public static final EnumConfiguration<AbilityType> CAMPFIRE_BOOSTING = Configs.register(new EnumConfiguration<>(MOD_ID, "campfire_boosting", ConfigEnv.SERVER, AbilityType.GLIDING_ONLY));
	public static final BooleanConfiguration ENCHANTMENT_GLINT = Configs.register(new BooleanConfiguration(MOD_ID, "enchantment_glint", ConfigEnv.CLIENT, false));

	@Override
	public void onInitialize() {
		LOGGER.info("Initialising Umbrellas");
		Configs.reload(MOD_ID);

		UmbrellasContent.registerModContent();
		UmbrellaCauldronBehavior.registerCauldronBehavior();

		ResourceConditions.register(new Identifier(MOD_ID, "pride_umbrellas_enabled"), jsonObject -> {
			Supplier<Boolean> booleanSupplier = PRIDE_UMBRELLAS::getValue;
			return booleanSupplier.get();
		});
	}
}