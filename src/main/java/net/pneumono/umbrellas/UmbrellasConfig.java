package net.pneumono.umbrellas;

import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.configurations.*;
import net.pneumono.pneumonocore.config_api.enums.LoadType;
import net.pneumono.umbrellas.util.EnchantmentAbilityType;

public class UmbrellasConfig {
    public static final EnumConfiguration<EnchantmentAbilityType> SLOW_FALLING = register("slow_falling", new EnumConfiguration<>(
            EnchantmentAbilityType.ENCHANTED_ONLY, new ConfigSettings().category("umbrellas").loadType(LoadType.INSTANT)
    ));
    public static final EnumConfiguration<EnchantmentAbilityType> SMOKE_BOOSTING = register("smoke_boosting", new EnumConfiguration<>(
            EnchantmentAbilityType.ENCHANTED_ONLY, new ConfigSettings().category("umbrellas").loadType(LoadType.INSTANT)
    ));
    public static final BooleanConfiguration STRICT_SMOKE_BOOSTING = register("strict_smoke_boosting", new BooleanConfiguration(
            false, new ConfigSettings().category("umbrellas").loadType(LoadType.INSTANT)
    ));
    public static final BooleanConfiguration DURABILITY = register("durability", new BooleanConfiguration(
            true, new ConfigSettings().category("umbrellas").loadType(LoadType.INSTANT)
    ));
    public static final BooleanConfiguration DISABLE_FIREWORK_BOOSTING = register("disable_firework_boosting", new BooleanConfiguration(
            false, new ConfigSettings().category("elytra_changes").loadType(LoadType.INSTANT)
    ));
    public static final BooleanConfiguration ELYTRA_SMOKE_BOOSTING = register("elytra_smoke_boosting", new BooleanConfiguration(
            false, new ConfigSettings().category("elytra_changes").loadType(LoadType.INSTANT)
    ));
    public static final BooleanConfiguration ENCHANTMENT_GLINT = register("enchantment_glint", new BooleanConfiguration(
            true, new ConfigSettings().clientSide().category("umbrellas").loadType(LoadType.INSTANT)
    ));

    public static <T extends AbstractConfiguration<?>> T register(String name, T config) {
        return ConfigApi.register(Umbrellas.id(name), config);
    }

    public static void registerUmbrellasConfigs() {
        ConfigApi.finishRegistry(Umbrellas.MOD_ID);
    }
}
