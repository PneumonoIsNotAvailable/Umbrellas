package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.pneumono.umbrellas.registry.UmbrellasEnchantments;
import net.pneumono.umbrellas.registry.UmbrellasTags;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class UmbrellasEnchantmentProvider extends FabricDynamicRegistryProvider {
    public static final Map<ResourceKey<Enchantment>, Function<HolderGetter<Item>, Enchantment.Builder>> ENCHANTMENT_BUILDERS = Map.of(
            UmbrellasEnchantments.GLIDING,
            itemLookup -> Enchantment.enchantment(
                    Enchantment.definition(
                            itemLookup.getOrThrow(UmbrellasTags.UMBRELLA_ENCHANTABLE),
                            5, 3,
                            Enchantment.dynamicCost(5, 8),
                            Enchantment.dynamicCost(55, 8),
                            2,
                            EquipmentSlotGroup.HAND
                    )
            ).withSpecialEffect(
                    UmbrellasEnchantments.SLOW_FALLING,
                    new AddValue(LevelBasedValue.perLevel(1, 1))
            ),
            UmbrellasEnchantments.BILLOWING,
            itemLookup -> Enchantment.enchantment(
                    Enchantment.definition(
                            itemLookup.getOrThrow(UmbrellasTags.UMBRELLA_ENCHANTABLE),
                            3, 1,
                            Enchantment.dynamicCost(5, 8),
                            Enchantment.dynamicCost(55, 8),
                            3,
                            EquipmentSlotGroup.HAND
                    )
            ).withSpecialEffect(
                    UmbrellasEnchantments.SMOKE_BOOSTING,
                    new AddValue(LevelBasedValue.perLevel(3, 1))
            )

    );

    public UmbrellasEnchantmentProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    public static void bootstrap(BootstrapContext<Enchantment> registry) {
        HolderGetter<Item> itemLookup = registry.lookup(Registries.ITEM);

        registerBootstrapped(registry, UmbrellasEnchantments.GLIDING, itemLookup);
        registerBootstrapped(registry, UmbrellasEnchantments.BILLOWING, itemLookup);
    }

    private static void registerBootstrapped(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, HolderGetter<Item> itemLookup) {
        registry.register(key, ENCHANTMENT_BUILDERS.get(key).apply(itemLookup).build(key.location()));
    }

    @Override
    protected void configure(HolderLookup.Provider provider, Entries entries) {
        HolderLookup.RegistryLookup<Item> itemLookup = provider.lookupOrThrow(Registries.ITEM);

        register(entries, UmbrellasEnchantments.GLIDING, itemLookup);
        register(entries, UmbrellasEnchantments.BILLOWING, itemLookup);
    }

    private void register(Entries entries, ResourceKey<Enchantment> key, HolderLookup<Item> itemLookup, ResourceCondition... resourceConditions) {
        entries.add(key, ENCHANTMENT_BUILDERS.get(key).apply(itemLookup).build(key.location()), resourceConditions);
    }

    @Override
    public @NotNull String getName() {
        return "Enchantments";
    }
}
