package net.pneumono.umbrellas.util;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.function.BiConsumer;

//? if >=1.21 {
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import org.apache.commons.lang3.mutable.MutableFloat;
//?} else {
/*import net.minecraft.world.item.enchantment.EnchantmentHelper;
*///?}

public enum EnchantmentAbilityType {
    ALWAYS,
    ENCHANTED_ONLY,
    NEVER;

    public int getStrength(
            ItemStack stack, RandomSource random,
            /*? if >=1.21 {*/DataComponentType<EnchantmentValueEffect>/*?} else {*//*Enchantment*//*?}*/ type,
            int alwaysValue, int baseValue
    ) {
        return switch (this) {
            case ALWAYS -> stack.is(UmbrellasTags.UMBRELLAS) ? alwaysValue : getStrengthFromEnchantment(stack, random, type, baseValue);
            case ENCHANTED_ONLY -> getStrengthFromEnchantment(stack, random, type, baseValue);
            case NEVER -> 0;
        };
    }

    public int getStrengthFromEnchantment(
            ItemStack stack, RandomSource random,
            /*? if >=1.21 {*/DataComponentType<EnchantmentValueEffect>/*?} else {*//*Enchantment*//*?}*/ type,
            int baseValue
    ) {
        //? if >=1.21 {
        MutableFloat mutableFloat = new MutableFloat(baseValue);
        forEachEnchantment(stack, (enchantment, level) -> modifyStrength(enchantment.value(), random, type, level, mutableFloat));
        return Math.max(0, mutableFloat.intValue());
        //?} else {
        /*return EnchantmentHelper.getItemEnchantmentLevel(type, stack);
        *///?}
    }

    //? if >=1.21 {
    private static void forEachEnchantment(ItemStack stack, BiConsumer<Holder<Enchantment>, Integer> consumer) {
        ItemEnchantments itemEnchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);

        for (Object2IntMap.Entry<Holder<Enchantment>> entry : itemEnchantments.entrySet()) {
            consumer.accept(entry.getKey(), entry.getIntValue());
        }
    }

    public void modifyStrength(Enchantment enchantment, RandomSource random, DataComponentType<EnchantmentValueEffect> type, int level, MutableFloat value) {
        EnchantmentValueEffect enchantmentValueEffect = enchantment.effects().get(type);
        if (enchantmentValueEffect != null) {
            value.setValue(enchantmentValueEffect.process(level, random, value.floatValue()));
        }
    }
    //?}
}
