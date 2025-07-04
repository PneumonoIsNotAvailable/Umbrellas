package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.item.Item;
import net.minecraft.registry.*;
import net.pneumono.umbrellas.registry.UmbrellasEnchantments;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class UmbrellasEnchantmentProvider extends FabricDynamicRegistryProvider {
    public static final Map<RegistryKey<Enchantment>, Function<RegistryEntryLookup<Item>, Enchantment.Builder>> ENCHANTMENT_BUILDERS = Map.of(
            UmbrellasEnchantments.GLIDING,
            itemLookup -> Enchantment.builder(
                    Enchantment.definition(
                            itemLookup.getOrThrow(UmbrellasTags.UMBRELLA_ENCHANTABLE),
                            5, 3,
                            Enchantment.leveledCost(5, 8),
                            Enchantment.leveledCost(55, 8),
                            2,
                            AttributeModifierSlot.HAND
                    )
            ).addNonListEffect(
                    UmbrellasEnchantments.SLOW_FALLING,
                    new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(1, 1))
            ),
            UmbrellasEnchantments.BILLOWING,
            itemLookup -> Enchantment.builder(
                    Enchantment.definition(
                            itemLookup.getOrThrow(UmbrellasTags.UMBRELLA_ENCHANTABLE),
                            3, 1,
                            Enchantment.leveledCost(5, 8),
                            Enchantment.leveledCost(55, 8),
                            3,
                            AttributeModifierSlot.HAND
                    )
            ).addNonListEffect(
                    UmbrellasEnchantments.SMOKE_BOOSTING,
                    new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(3, 1))
            )

    );

    public UmbrellasEnchantmentProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public static void bootstrap(Registerable<Enchantment> registry) {
        RegistryEntryLookup<Item> itemLookup = registry.getRegistryLookup(RegistryKeys.ITEM);

        registerBootstrapped(registry, UmbrellasEnchantments.GLIDING, itemLookup);
        registerBootstrapped(registry, UmbrellasEnchantments.BILLOWING, itemLookup);
    }

    private static void registerBootstrapped(Registerable<Enchantment> registry, RegistryKey<Enchantment> key, RegistryEntryLookup<Item> itemLookup) {
        registry.register(key, ENCHANTMENT_BUILDERS.get(key).apply(itemLookup).build(key.getValue()));
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);

        register(entries, UmbrellasEnchantments.GLIDING, itemLookup);
        register(entries, UmbrellasEnchantments.BILLOWING, itemLookup);
    }

    private void register(Entries entries, RegistryKey<Enchantment> key, RegistryEntryLookup<Item> itemLookup, ResourceCondition... resourceConditions) {
        entries.add(key, ENCHANTMENT_BUILDERS.get(key).apply(itemLookup).build(key.getValue()), resourceConditions);
    }

    @Override
    public String getName() {
        return "Enchantments";
    }
}
