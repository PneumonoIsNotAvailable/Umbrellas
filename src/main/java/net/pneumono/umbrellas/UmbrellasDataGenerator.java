package net.pneumono.umbrellas;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.datagen.ConfigCondition;
import net.pneumono.pneumonocore.datagen.PneumonoDatagenHelper;
import net.pneumono.pneumonocore.datagen.enums.Operator;
import net.pneumono.umbrellas.content.PrideUmbrellaItem;
import net.pneumono.umbrellas.content.UmbrellasContent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class UmbrellasDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(UmbrellaTagsGenerator::new);
        pack.addProvider(RecipesGenerator::new);
        pack.addProvider(AdvancementsGenerator::new);
    }

    private static class UmbrellaTagsGenerator extends FabricTagProvider.ItemTagProvider {
        public UmbrellaTagsGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(WrapperLookup arg) {
            FabricTagBuilder umbrellaBuilder = getOrCreateTagBuilder(UmbrellasContent.TAG_UMBRELLAS);
            for (Item item : UmbrellasContent.UMBRELLAS) {
                umbrellaBuilder.add(item);
            }

            FabricTagBuilder washableUmbrellaBuilder = getOrCreateTagBuilder(UmbrellasContent.TAG_WASHABLE_UMBRELLAS);
            for (Item item : UmbrellasContent.WASHABLE_UMBRELLAS) {
                washableUmbrellaBuilder.add(item);
            }

            FabricTagBuilder prideUmbrellaBuilder = getOrCreateTagBuilder(UmbrellasContent.TAG_PRIDE_UMBRELLAS);
            for (Item item : UmbrellasContent.PRIDE_UMBRELLAS) {
                prideUmbrellaBuilder.add(item);
            }
        }
    }

    private static class RecipesGenerator extends FabricRecipeProvider {
        public RecipesGenerator(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generate(Consumer<RecipeJsonProvider> exporter) {
            ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, UmbrellasContent.UMBRELLA)
                    .pattern("LLL")
                    .pattern("LSL")
                    .pattern(" S ")
                    .input('L', Items.LEATHER)
                    .input('S', Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(Items.LEATHER), FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                    .criterion(FabricRecipeProvider.hasItem(Items.STICK), FabricRecipeProvider.conditionsFromItem(Items.STICK))
                    .offerTo(exporter);


            for (Item item : UmbrellasContent.PRIDE_UMBRELLAS) {
                ShapelessRecipeJsonBuilder builder = ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, item);

                if (item instanceof PrideUmbrellaItem umbrella) {
                    for (DyeColor color : umbrella.getDyeColors()) {
                        DyeItem dyeItem = DyeItem.byColor(color);
                        builder.input(dyeItem).criterion(FabricRecipeProvider.hasItem(dyeItem), FabricRecipeProvider.conditionsFromItem(dyeItem));
                    }
                }

                builder.input(UmbrellasContent.UMBRELLA).criterion(FabricRecipeProvider.hasItem(UmbrellasContent.UMBRELLA), FabricRecipeProvider.conditionsFromItem(UmbrellasContent.UMBRELLA))
                        .input(ItemTags.BANNERS).criterion("has_banner", FabricRecipeProvider.conditionsFromTag(ItemTags.BANNERS))
                        .group("pride_umbrellas")
                        .offerTo(this.withConditions(exporter, PneumonoDatagenHelper.configValues(new ConfigCondition(Umbrellas.PRIDE_UMBRELLAS.getID(), Operator.EQUAL, true))));
            }
        }
    }

    private static class AdvancementsGenerator extends FabricAdvancementProvider {
        protected AdvancementsGenerator(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateAdvancement(Consumer<Advancement> consumer) {
            Advancement getUmbrellaAdvancement = Advancement.Builder.create().parent(new Identifier("minecraft","adventure/root"))
                    .display(
                            UmbrellasContent.UMBRELLA,
                            Text.translatable("umbrellas.advancements.get_umbrella.name"),
                            Text.translatable("umbrellas.advancements.get_umbrella.desc"),
                            null,
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    .criterion("has_umbrella", InventoryChangedCriterion.Conditions.items(getTagPredicate(UmbrellasContent.TAG_UMBRELLAS)))
                    .build(consumer, Umbrellas.MOD_ID + ":adventure/get_umbrella");

            Advancement.Builder.create().parent(getUmbrellaAdvancement)
                    .display(
                            UmbrellasContent.UMBRELLA_PRIDE,
                            Text.translatable("umbrellas.advancements.get_pride_umbrella.name"),
                            Text.translatable("umbrellas.advancements.get_pride_umbrella.desc"),
                            null,
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    .criterion("has_pride_umbrella", InventoryChangedCriterion.Conditions.items(getTagPredicate(UmbrellasContent.TAG_PRIDE_UMBRELLAS)))
                    .build(withConditions(consumer, PneumonoDatagenHelper.configValues(new ConfigCondition(Umbrellas.PRIDE_UMBRELLAS.getID(), Operator.EQUAL, true))),
                            Umbrellas.MOD_ID + ":adventure/get_pride_umbrella");

            Advancement.Builder.create().parent(getUmbrellaAdvancement)
                    .display(
                            UmbrellasContent.UMBRELLA,
                            Text.translatable("umbrellas.advancements.get_gliding_umbrella.name"),
                            Text.translatable("umbrellas.advancements.get_gliding_umbrella.desc"),
                            null,
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    .criterion("has_gliding_umbrella", InventoryChangedCriterion.Conditions.items(getEnchantmentPredicate(new EnchantmentPredicate(UmbrellasContent.GLIDING, NumberRange.IntRange.ANY))))
                    .build(consumer, Umbrellas.MOD_ID + ":adventure/get_gliding_umbrella");
        }

        public static ItemPredicate getTagPredicate(TagKey<Item> tagKey) {
            return new ItemPredicate(tagKey, Set.of(), NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, new EnchantmentPredicate[]{}, new EnchantmentPredicate[]{}, null, null);
        }

        public static ItemPredicate getEnchantmentPredicate(EnchantmentPredicate enchantment) {
            return new ItemPredicate(null, null, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, List.of(enchantment).toArray(new EnchantmentPredicate[]{}), new EnchantmentPredicate[]{}, null, null);
        }
    }
}
