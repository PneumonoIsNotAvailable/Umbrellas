package net.pneumono.umbrellas.registry;

import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.pneumono.umbrellas.Umbrellas;

public class UmbrellasEnchantments {
    public static final RegistryKey<Enchantment> GLIDING = enchantment("gliding");
    public static final RegistryKey<Enchantment> BILLOWING = enchantment("billowing");

    public static final ComponentType<EnchantmentValueEffect> SLOW_FALLING = registerEnchantmentEffectComponentType(
            "slow_falling"
    );
    public static final ComponentType<EnchantmentValueEffect> SMOKE_BOOSTING = registerEnchantmentEffectComponentType(
            "smoke_boosting"
    );

    private static RegistryKey<Enchantment> enchantment(String path) {
        Identifier id = Umbrellas.id(path);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }

    private static ComponentType<EnchantmentValueEffect> registerEnchantmentEffectComponentType(String name) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, Umbrellas.id(name), ComponentType.<EnchantmentValueEffect>builder().codec(EnchantmentValueEffect.CODEC).build());
    }

    public static void registerUmbrellasEnchantments() {

    }
}
