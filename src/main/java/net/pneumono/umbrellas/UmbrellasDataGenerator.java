package net.pneumono.umbrellas;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.ImpossibleCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.predicate.NbtPredicate;
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
import net.pneumono.umbrellas.content.UmbrellasRegistry;

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
            FabricTagBuilder umbrellaBuilder = getOrCreateTagBuilder(UmbrellasRegistry.TAG_UMBRELLAS);
            for (Item item : UmbrellasRegistry.UMBRELLAS) {
                umbrellaBuilder.add(item);
            }

            FabricTagBuilder washableUmbrellaBuilder = getOrCreateTagBuilder(UmbrellasRegistry.TAG_WASHABLE_UMBRELLAS);
            for (Item item : UmbrellasRegistry.WASHABLE_UMBRELLAS) {
                washableUmbrellaBuilder.add(item);
            }

            FabricTagBuilder prideUmbrellaBuilder = getOrCreateTagBuilder(UmbrellasRegistry.TAG_PRIDE_UMBRELLAS);
            for (Item item : UmbrellasRegistry.PRIDE_UMBRELLAS) {
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
            ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, UmbrellasRegistry.UMBRELLA)
                    .pattern("LLL")
                    .pattern("LSL")
                    .pattern(" S ")
                    .input('L', Items.LEATHER)
                    .input('S', Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(Items.LEATHER), FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                    .criterion(FabricRecipeProvider.hasItem(Items.STICK), FabricRecipeProvider.conditionsFromItem(Items.STICK))
                    .offerTo(exporter);


            for (Item item : UmbrellasRegistry.PRIDE_UMBRELLAS) {
                ShapelessRecipeJsonBuilder builder = ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, item);

                if (item instanceof PrideUmbrellaItem umbrella) {
                    for (DyeColor color : umbrella.getDyeColors()) {
                        DyeItem dyeItem = DyeItem.byColor(color);
                        builder.input(dyeItem).criterion(FabricRecipeProvider.hasItem(dyeItem), FabricRecipeProvider.conditionsFromItem(dyeItem));
                    }
                }

                builder.input(UmbrellasRegistry.UMBRELLA).criterion(FabricRecipeProvider.hasItem(UmbrellasRegistry.UMBRELLA), FabricRecipeProvider.conditionsFromItem(UmbrellasRegistry.UMBRELLA))
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
            Advancement getUmbrellaAdvancement = Advancement.Builder.create().parent(getDummy(new Identifier("minecraft","adventure/root")))
                    .display(
                            UmbrellasRegistry.UMBRELLA,
                            Text.translatable("umbrellas.advancements.get_umbrella.name"),
                            Text.translatable("umbrellas.advancements.get_umbrella.desc"),
                            null,
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    .criterion("has_umbrella", InventoryChangedCriterion.Conditions.items(getTagPredicate(UmbrellasRegistry.TAG_UMBRELLAS)))
                    .build(consumer, Umbrellas.MOD_ID + ":adventure/get_umbrella");

            Advancement.Builder.create().parent(getUmbrellaAdvancement)
                    .display(
                            UmbrellasRegistry.UMBRELLA_PRIDE,
                            Text.translatable("umbrellas.advancements.get_pride_umbrella.name"),
                            Text.translatable("umbrellas.advancements.get_pride_umbrella.desc"),
                            null,
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    .criterion("has_pride_umbrella", InventoryChangedCriterion.Conditions.items(getTagPredicate(UmbrellasRegistry.TAG_PRIDE_UMBRELLAS)))
                    .build(withConditions(consumer, PneumonoDatagenHelper.configValues(new ConfigCondition(Umbrellas.PRIDE_UMBRELLAS.getID(), Operator.EQUAL, true))),
                            Umbrellas.MOD_ID + ":adventure/get_pride_umbrella");

            Advancement.Builder.create().parent(getUmbrellaAdvancement)
                    .display(
                            UmbrellasRegistry.UMBRELLA,
                            Text.translatable("umbrellas.advancements.get_gliding_umbrella.name"),
                            Text.translatable("umbrellas.advancements.get_gliding_umbrella.desc"),
                            null,
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    .criterion("has_gliding_umbrella", InventoryChangedCriterion.Conditions.items(getEnchantmentPredicate(new EnchantmentPredicate(UmbrellasRegistry.GLIDING, NumberRange.IntRange.ANY))))
                    .build(consumer, Umbrellas.MOD_ID + ":adventure/get_gliding_umbrella");
        }

        public static ItemPredicate getTagPredicate(TagKey<Item> tagKey) {
            return new ItemPredicate(tagKey, null, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, EnchantmentPredicate.ARRAY_OF_ANY, EnchantmentPredicate.ARRAY_OF_ANY, null, NbtPredicate.ANY);
        }

        public static ItemPredicate getEnchantmentPredicate(EnchantmentPredicate enchantment) {
            return new ItemPredicate(null, null, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, new EnchantmentPredicate[]{enchantment}, EnchantmentPredicate.ARRAY_OF_ANY, null, NbtPredicate.ANY);
        }

        public static Advancement getDummy(Identifier advancementID) {
            return Advancement.Builder.create()
                    .display(
                            Items.BARRIER,
                            Text.literal("Uh oh"),
                            Text.literal("If you're reading this, something has gone very, very wrong"),
                            null,
                            AdvancementFrame.CHALLENGE,
                            false,
                            false,
                            false
                    )
                    .criterion("impossible", new ImpossibleCriterion.Conditions())
                    .build(advancementID);
        }
    }
}
