package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.DamageImmunityEnchantmentEffect;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.item.Item;
import net.minecraft.loot.condition.AllOfLootCondition;
import net.minecraft.loot.condition.DamageSourcePropertiesLootCondition;
import net.minecraft.loot.condition.ValueCheckLootCondition;
import net.minecraft.loot.operator.BoundedIntUnaryOperator;
import net.minecraft.loot.provider.number.EnchantmentLevelLootNumberProvider;
import net.minecraft.predicate.TagPredicate;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.DamageTypeTags;
import net.pneumono.umbrellas.registry.UmbrellasMisc;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class UmbrellasEnchantmentProvider extends FabricDynamicRegistryProvider {
    public static final Map<RegistryKey<Enchantment>, Function<RegistryEntryLookup<Item>, Enchantment.Builder>> ENCHANTMENT_BUILDERS = Map.of(
            UmbrellasMisc.GLIDING,
            itemLookup -> Enchantment.builder(
                    Enchantment.definition(
                            itemLookup.getOrThrow(UmbrellasTags.UMBRELLA_ENCHANTABLE),
                            5, 3,
                            Enchantment.leveledCost(5, 8),
                            Enchantment.leveledCost(55, 8),
                            2,
                            AttributeModifierSlot.HAND
                    )
            ).addEffect(
                    EnchantmentEffectComponentTypes.DAMAGE_PROTECTION,
                    new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(8, 8)),
                    DamageSourcePropertiesLootCondition.builder(
                            DamageSourcePredicate.Builder.create().tag(
                                    TagPredicate.expected(DamageTypeTags.IS_FALL)
                            ).tag(
                                    TagPredicate.unexpected(DamageTypeTags.BYPASSES_INVULNERABILITY)
                            )
                    )
            ).addEffect(
                    EnchantmentEffectComponentTypes.DAMAGE_IMMUNITY,
                    new DamageImmunityEnchantmentEffect(),
                    AllOfLootCondition.builder(
                            DamageSourcePropertiesLootCondition.builder(
                                    DamageSourcePredicate.Builder.create().tag(
                                            TagPredicate.expected(DamageTypeTags.IS_FALL)
                                    ).tag(
                                            TagPredicate.unexpected(DamageTypeTags.BYPASSES_INVULNERABILITY)
                                    )
                            ),
                            ValueCheckLootCondition.builder(
                                    EnchantmentLevelLootNumberProvider.create(
                                            EnchantmentLevelBasedValue.linear(1, 1)
                                    ),
                                    BoundedIntUnaryOperator.create(
                                            3, 127
                                    )
                            )
                    )
            ).addNonListEffect(
                    UmbrellasMisc.SLOW_FALLING,
                    new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(1, 1))
            ),
            UmbrellasMisc.WIND_CATCHING,
            itemLookup -> Enchantment.builder(
                    Enchantment.definition(
                            itemLookup.getOrThrow(UmbrellasTags.UMBRELLA_ENCHANTABLE),
                            3, 3,
                            Enchantment.leveledCost(5, 8),
                            Enchantment.leveledCost(55, 8),
                            3,
                            AttributeModifierSlot.HAND
                    )
            ).addNonListEffect(
                    UmbrellasMisc.SMOKE_BOOSTING,
                    new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(1, 1))
            )

    );

    public UmbrellasEnchantmentProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public static void bootstrap(Registerable<Enchantment> registry) {
        RegistryEntryLookup<Item> itemLookup = registry.getRegistryLookup(RegistryKeys.ITEM);

        registerBootstrapped(registry, UmbrellasMisc.GLIDING, itemLookup);
        registerBootstrapped(registry, UmbrellasMisc.WIND_CATCHING, itemLookup);
    }

    private static void registerBootstrapped(Registerable<Enchantment> registry, RegistryKey<Enchantment> key, RegistryEntryLookup<Item> itemLookup) {
        registry.register(key, ENCHANTMENT_BUILDERS.get(key).apply(itemLookup).build(key.getValue()));
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);

        register(entries, UmbrellasMisc.GLIDING, itemLookup);
        register(entries, UmbrellasMisc.WIND_CATCHING, itemLookup);
    }

    private void register(Entries entries, RegistryKey<Enchantment> key, RegistryEntryLookup<Item> itemLookup, ResourceCondition... resourceConditions) {
        entries.add(key, ENCHANTMENT_BUILDERS.get(key).apply(itemLookup).build(key.getValue()), resourceConditions);
    }

    @Override
    public String getName() {
        return "Enchantments";
    }
}
