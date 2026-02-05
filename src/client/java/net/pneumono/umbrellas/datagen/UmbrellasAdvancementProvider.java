//~ identifier_replacements

package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.SmokeBoostCriterion;
import net.pneumono.umbrellas.content.TimeGlidingCriterion;
import net.pneumono.umbrellas.registry.UmbrellasEnchantments;
import net.pneumono.umbrellas.registry.UmbrellasItems;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

//? if >=1.21.6
import net.minecraft.world.item.Item;

//? if >=1.21 {
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
//?} else {
/*import net.minecraft.advancements.FrameType;
*///?}

public class UmbrellasAdvancementProvider extends FabricAdvancementProvider {
    public UmbrellasAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output/*? if >=1.21 {*/, registryLookup/*?}*/);
    }

    @Override
    public void generateAdvancement(
            //? if >=1.21 {
            HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer
            //?} else {
            /*Consumer<Advancement> consumer
            *///?}
    ) {
        //? if >=1.21.6
        HolderLookup.RegistryLookup<Item> itemLookup = provider.lookupOrThrow(Registries.ITEM);
        //? if >=1.21
        HolderLookup.RegistryLookup<Enchantment> enchantmentLookup = provider.lookupOrThrow(Registries.ENCHANTMENT);

        //? if >=1.21 {
        AdvancementHolder getUmbrellaAdvancement = Advancement.Builder.advancement().parent(Advancement.Builder.advancement().build(Identifier.withDefaultNamespace("adventure/root")))
                .display(
                        UmbrellasItems.RED_UMBRELLA,
                        Component.translatable("advancements.umbrellas.get_umbrella.name"),
                        Component.translatable("advancements.umbrellas.get_umbrella.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_umbrella", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(/*? if >=1.21.6 {*/itemLookup, /*?}*/UmbrellasTags.UMBRELLAS)))
                .save(consumer, Umbrellas.MOD_ID + ":adventure/get_umbrella");

        ItemStack glidingEnchanted = new ItemStack(UmbrellasItems.RED_UMBRELLA);
        glidingEnchanted.enchant(enchantmentLookup.getOrThrow(UmbrellasEnchantments.GLIDING), 3);
        Advancement.Builder.advancement().parent(getUmbrellaAdvancement)
                .display(
                        glidingEnchanted,
                        Component.translatable("advancements.umbrellas.use_gliding_umbrella.name"),
                        Component.translatable("advancements.umbrellas.use_gliding_umbrella.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("used_gliding_umbrella", TimeGlidingCriterion.TriggerInstance.minHeight(19.75))
                .save(consumer, Umbrellas.MOD_ID + ":adventure/use_gliding_umbrella");

        ItemStack billowingEnchanted = new ItemStack(UmbrellasItems.RED_UMBRELLA);
        billowingEnchanted.enchant(enchantmentLookup.getOrThrow(UmbrellasEnchantments.BILLOWING), 3);
        Advancement.Builder.advancement().parent(getUmbrellaAdvancement)
                .display(
                        billowingEnchanted,
                        Component.translatable("advancements.umbrellas.use_billowing_umbrella.name"),
                        Component.translatable("advancements.umbrellas.use_billowing_umbrella.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("used_billowing_umbrella", SmokeBoostCriterion.TriggerInstance.minHeight(19))
                .save(consumer, Umbrellas.MOD_ID + ":adventure/use_billowing_umbrella");
        //?} else {
        /*Advancement getUmbrellaAdvancement = Advancement.Builder.advancement().parent(Advancement.Builder.advancement().build(Identifier.tryParse("adventure/root")))
                .display(
                        UmbrellasItems.RED_UMBRELLA,
                        Component.translatable("advancements.umbrellas.get_umbrella.name"),
                        Component.translatable("advancements.umbrellas.get_umbrella.description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_umbrella", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UmbrellasTags.UMBRELLAS).build()))
                .save(consumer, Umbrellas.MOD_ID + ":adventure/get_umbrella");

        ItemStack glidingEnchanted = new ItemStack(UmbrellasItems.RED_UMBRELLA);
        glidingEnchanted.enchant(UmbrellasEnchantments.GLIDING, 3);
        Advancement.Builder.advancement().parent(getUmbrellaAdvancement)
                .display(
                        glidingEnchanted,
                        Component.translatable("advancements.umbrellas.use_gliding_umbrella.name"),
                        Component.translatable("advancements.umbrellas.use_gliding_umbrella.description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("used_gliding_umbrella", TimeGlidingCriterion.TriggerInstance.minHeight(19.75))
                .save(consumer, Umbrellas.MOD_ID + ":adventure/use_gliding_umbrella");

        ItemStack billowingEnchanted = new ItemStack(UmbrellasItems.RED_UMBRELLA);
        billowingEnchanted.enchant(UmbrellasEnchantments.BILLOWING, 3);
        Advancement.Builder.advancement().parent(getUmbrellaAdvancement)
                .display(
                        billowingEnchanted,
                        Component.translatable("advancements.umbrellas.use_billowing_umbrella.name"),
                        Component.translatable("advancements.umbrellas.use_billowing_umbrella.description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("used_billowing_umbrella", SmokeBoostCriterion.TriggerInstance.minHeight(19))
                .save(consumer, Umbrellas.MOD_ID + ":adventure/use_billowing_umbrella");
        *///?}
    }
}
