package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.component.ComponentPredicateTypes;
import net.minecraft.predicate.component.ComponentsPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.EnchantmentsPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.registry.UmbrellasItems;
import net.pneumono.umbrellas.registry.UmbrellasMisc;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class UmbrellasAdvancementProvider extends FabricAdvancementProvider {
    public UmbrellasAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registries, Consumer<AdvancementEntry> consumer) {
        RegistryEntryLookup<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);
        RegistryEntryLookup<Enchantment> enchantmentLookup = registries.getOrThrow(RegistryKeys.ENCHANTMENT);

        AdvancementEntry getUmbrellaAdvancement = Advancement.Builder.create().parent(Advancement.Builder.create().build(Identifier.of("adventure/root")))
                .display(
                        UmbrellasItems.RED_UMBRELLA,
                        Text.translatable("advancements.umbrellas.get_umbrella.name"),
                        Text.translatable("advancements.umbrellas.get_umbrella.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("has_umbrella", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(itemLookup, UmbrellasTags.UMBRELLAS)))
                .build(consumer, Umbrellas.MOD_ID + ":adventure/get_umbrella");

        ItemStack glidingEnchanted = UmbrellasItems.RED_UMBRELLA.getDefaultStack();
        glidingEnchanted.addEnchantment(registries.getEntryOrThrow(UmbrellasMisc.GLIDING), 3);
        Advancement.Builder.create().parent(getUmbrellaAdvancement)
                .display(
                        glidingEnchanted,
                        Text.translatable("advancements.umbrellas.get_gliding_umbrella.name"),
                        Text.translatable("advancements.umbrellas.get_gliding_umbrella.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("has_gliding_umbrella", enchantment(enchantmentLookup, UmbrellasMisc.GLIDING))
                .build(consumer, Umbrellas.MOD_ID + ":adventure/get_gliding_umbrella");

        ItemStack billowingEnchanted = UmbrellasItems.RED_UMBRELLA.getDefaultStack();
        billowingEnchanted.addEnchantment(registries.getEntryOrThrow(UmbrellasMisc.BILLOWING), 3);
        Advancement.Builder.create().parent(getUmbrellaAdvancement)
                .display(
                        billowingEnchanted,
                        Text.translatable("advancements.umbrellas.get_billowing_umbrella.name"),
                        Text.translatable("advancements.umbrellas.get_billowing_umbrella.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("has_billowing_umbrella", enchantment(enchantmentLookup, UmbrellasMisc.BILLOWING))
                .build(consumer, Umbrellas.MOD_ID + ":adventure/get_billowing_umbrella");
    }

    private static AdvancementCriterion<InventoryChangedCriterion.Conditions> enchantment(RegistryEntryLookup<Enchantment> lookup, RegistryKey<Enchantment> enchantment) {
        return InventoryChangedCriterion.Conditions.items(
                ItemPredicate.Builder.create().components(
                        ComponentsPredicate.Builder.create().partial(
                                ComponentPredicateTypes.ENCHANTMENTS,
                                EnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(lookup.getOrThrow(enchantment), NumberRange.IntRange.atLeast(1))))
                        ).build()
                )
        );
    }
}
