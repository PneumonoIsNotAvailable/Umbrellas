package net.pneumono.umbrellas.registry;

import com.mojang.serialization.Codec;
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
            "slow_falling", EnchantmentValueEffect.CODEC
    );
    public static final ComponentType<EnchantmentValueEffect> SMOKE_BOOSTING = registerEnchantmentEffectComponentType(
            "smoke_boosting", EnchantmentValueEffect.CODEC
    );

    private static RegistryKey<Enchantment> enchantment(String path) {
        Identifier id = Umbrellas.id(path);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }

    private static <T> ComponentType<T> registerEnchantmentEffectComponentType(String name, Codec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, Umbrellas.id(name), ComponentType.<T>builder().codec(codec).build());
    }

    public static void registerUmbrellasEnchantments() {

    }
}
