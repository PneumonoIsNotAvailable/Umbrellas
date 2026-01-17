package net.pneumono.umbrellas.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.pneumono.umbrellas.Umbrellas;

public class UmbrellasEnchantments {
    public static final ResourceKey<Enchantment> GLIDING = enchantment("gliding");
    public static final ResourceKey<Enchantment> BILLOWING = enchantment("billowing");

    public static final DataComponentType<EnchantmentValueEffect> SLOW_FALLING = registerEnchantmentEffectComponentType(
            "slow_falling"
    );
    public static final DataComponentType<EnchantmentValueEffect> SMOKE_BOOSTING = registerEnchantmentEffectComponentType(
            "smoke_boosting"
    );

    private static ResourceKey<Enchantment> enchantment(String path) {
        Identifier id = Umbrellas.id(path);
        return ResourceKey.create(Registries.ENCHANTMENT, id);
    }

    private static DataComponentType<EnchantmentValueEffect> registerEnchantmentEffectComponentType(String name) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, Umbrellas.id(name), DataComponentType.<EnchantmentValueEffect>builder().persistent(EnchantmentValueEffect.CODEC).build());
    }

    public static void registerUmbrellasEnchantments() {

    }
}
