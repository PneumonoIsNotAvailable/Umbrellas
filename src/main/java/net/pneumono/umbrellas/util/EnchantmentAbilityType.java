package net.pneumono.umbrellas.util;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;
import net.pneumono.umbrellas.registry.UmbrellasTags;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.function.BiConsumer;

public enum EnchantmentAbilityType {
    ALWAYS,
    ENCHANTED_ONLY,
    NEVER;

    public int getStrength(ItemStack stack, Random random, ComponentType<EnchantmentValueEffect> type, int alwaysValue, int baseValue) {
        return switch (this) {
            case ALWAYS -> stack.isIn(UmbrellasTags.UMBRELLAS) ? alwaysValue : getStrengthFromEnchantment(stack, random, type, baseValue);
            case ENCHANTED_ONLY -> getStrengthFromEnchantment(stack, random, type, baseValue);
            case NEVER -> 0;
        };
    }

    public int getStrengthFromEnchantment(ItemStack stack, Random random, ComponentType<EnchantmentValueEffect> type, int baseValue) {
        MutableFloat mutableFloat = new MutableFloat(baseValue);
        forEachEnchantment(stack, (enchantment, level) -> modifyStrength(enchantment.value(), random, type, level, mutableFloat));
        return Math.max(0, mutableFloat.intValue());
    }

    private static void forEachEnchantment(ItemStack stack, BiConsumer<RegistryEntry<Enchantment>, Integer> consumer) {
        ItemEnchantmentsComponent itemEnchantmentsComponent = stack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);

        for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : itemEnchantmentsComponent.getEnchantmentEntries()) {
            consumer.accept(entry.getKey(), entry.getIntValue());
        }
    }

    public void modifyStrength(Enchantment enchantment, Random random, ComponentType<EnchantmentValueEffect> type, int level, MutableFloat value) {
        EnchantmentValueEffect enchantmentValueEffect = enchantment.effects().get(type);
        if (enchantmentValueEffect != null) {
            value.setValue(enchantmentValueEffect.apply(level, random, value.floatValue()));
        }
    }
}
