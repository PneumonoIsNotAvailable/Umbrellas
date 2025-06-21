package net.pneumono.umbrellas;

import net.pneumono.pneumonocore.config.*;
import net.pneumono.umbrellas.util.EnchantmentAbilityType;

public class UmbrellasConfig {
    public static final String MOD_ID = Umbrellas.MOD_ID;

    public static final EnumConfiguration<EnchantmentAbilityType> SLOW_FALLING = new EnumConfiguration<>(MOD_ID, "slow_falling", ConfigEnv.SERVER, EnchantmentAbilityType.ENCHANTED_ONLY);
    public static final EnumConfiguration<EnchantmentAbilityType> SMOKE_BOOSTING = new EnumConfiguration<>(MOD_ID, "smoke_boosting", ConfigEnv.SERVER, EnchantmentAbilityType.ENCHANTED_ONLY);
    public static final BooleanConfiguration STRICT_SMOKE_BOOSTING = new BooleanConfiguration(MOD_ID, "strict_smoke_boosting", ConfigEnv.SERVER, false);
    public static final BooleanConfiguration DURABILITY = new BooleanConfiguration(MOD_ID, "durability", ConfigEnv.SERVER, true);
    public static final BooleanConfiguration DISABLE_FIREWORK_BOOSTING = new BooleanConfiguration(MOD_ID, "disable_firework_boosting", ConfigEnv.SERVER, false);
    public static final BooleanConfiguration ELYTRA_SMOKE_BOOSTING = new BooleanConfiguration(MOD_ID, "elytra_smoke_boosting", ConfigEnv.SERVER, false);
    public static final BooleanConfiguration ENCHANTMENT_GLINT = new BooleanConfiguration(MOD_ID, "enchantment_glint", ConfigEnv.CLIENT, true);

    public static void registerUmbrellasConfigs() {
        Configs.register(MOD_ID,
                SLOW_FALLING,
                SMOKE_BOOSTING,
                STRICT_SMOKE_BOOSTING,
                DURABILITY,
                DISABLE_FIREWORK_BOOSTING,
                ELYTRA_SMOKE_BOOSTING,
                ENCHANTMENT_GLINT
        );
        Configs.registerCategories(MOD_ID,
                new ConfigCategory(MOD_ID, "umbrellas",
                        SLOW_FALLING,
                        SMOKE_BOOSTING,
                        STRICT_SMOKE_BOOSTING,
                        DURABILITY,
                        ENCHANTMENT_GLINT
                ),
                new ConfigCategory(MOD_ID, "elytra_changes",
                        DISABLE_FIREWORK_BOOSTING,
                        ELYTRA_SMOKE_BOOSTING
                )
        );
    }
}
