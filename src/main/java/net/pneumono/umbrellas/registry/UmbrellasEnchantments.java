//~ identifier_replacements

package net.pneumono.umbrellas.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.pneumono.umbrellas.Umbrellas;

//? if >=1.21 {
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
//?} else {
/*import net.pneumono.umbrellas.content.enchantment.BillowingEnchantment;
import net.pneumono.umbrellas.content.enchantment.GlidingEnchantment;
*///?}

public class UmbrellasEnchantments {
    public static final /*? if >=1.21 {*/ResourceKey<Enchantment>/*?} else {*//*Enchantment*//*?}*/ GLIDING = enchantment("gliding"/*? if <1.21 {*//*, new GlidingEnchantment()*//*?}*/);
    public static final /*? if >=1.21 {*/ResourceKey<Enchantment>/*?} else {*//*Enchantment*//*?}*/ BILLOWING = enchantment("billowing"/*? if <1.21 {*//*, new BillowingEnchantment()*//*?}*/);

    //? if >=1.21 {
    public static final DataComponentType<EnchantmentValueEffect> SLOW_FALLING = registerEnchantmentEffectComponentType(
            "slow_falling"
    );
    public static final DataComponentType<EnchantmentValueEffect> SMOKE_BOOSTING = registerEnchantmentEffectComponentType(
            "smoke_boosting"
    );
    //?}

    private static /*? if >=1.21 {*/ResourceKey<Enchantment>/*?} else {*//*Enchantment*//*?}*/ enchantment(String path/*? if <1.21 {*//*, Enchantment enchantment*//*?}*/) {
        Identifier id = Umbrellas.id(path);
        //? if >=1.21 {
        return ResourceKey.create(Registries.ENCHANTMENT, id);
        //?} else {
        /*return Registry.register(BuiltInRegistries.ENCHANTMENT, id, enchantment);
        *///?}
    }

    //? if >=1.21 {
    private static DataComponentType<EnchantmentValueEffect> registerEnchantmentEffectComponentType(String name) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, Umbrellas.id(name), DataComponentType.<EnchantmentValueEffect>builder().persistent(EnchantmentValueEffect.CODEC).build());
    }
    //?}

    public static void registerUmbrellasEnchantments() {

    }
}
