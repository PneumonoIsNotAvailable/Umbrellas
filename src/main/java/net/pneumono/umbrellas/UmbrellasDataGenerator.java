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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.pneumono.pneumonocore.datagen.PneumonoDatagenHelper;
import net.pneumono.umbrellas.content.PatternableUmbrellaItem;
import net.pneumono.umbrellas.content.UmbrellasRegistry;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class UmbrellasDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ItemTagsGenerator::new);
        pack.addProvider(BlockTagsGenerator::new);
        pack.addProvider(RecipesGenerator::new);
        pack.addProvider(AdvancementsGenerator::new);
    }

    private static class ItemTagsGenerator extends FabricTagProvider.ItemTagProvider {
        public ItemTagsGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(WrapperLookup arg) {
            FabricTagBuilder umbrellasBuilder = getOrCreateTagBuilder(UmbrellasRegistry.TAG_UMBRELLAS);
            for (Item item : UmbrellasRegistry.UMBRELLAS) {
                umbrellasBuilder.add(item);
            }
        }
    }

    private static class BlockTagsGenerator extends FabricTagProvider.BlockTagProvider {
        public BlockTagsGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(WrapperLookup arg) {
            FabricTagBuilder umbrellaBoostingBuilder = getOrCreateTagBuilder(UmbrellasRegistry.TAG_BOOSTS_UMBRELLAS);
            umbrellaBoostingBuilder.forceAddTag(BlockTags.FIRE);
            umbrellaBoostingBuilder.forceAddTag(BlockTags.CAMPFIRES);
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

            ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, UmbrellasRegistry.UMBRELLA_STAND)
                    .pattern("P")
                    .pattern("S")
                    .pattern("P")
                    .input('P', ItemTags.PLANKS)
                    .input('S', Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(Items.STICK), FabricRecipeProvider.conditionsFromItem(Items.STICK))
                    .offerTo(exporter);

            for (Item umbrella : UmbrellasRegistry.PATTERNABLE_UMBRELLAS) {
                if (umbrella instanceof PatternableUmbrellaItem patternable) {
                    Item banner = Registries.ITEM.get(new Identifier(patternable.getBaseColor().getName() + "_banner"));
                    ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, patternable)
                            .input(UmbrellasRegistry.UMBRELLA)
                            .input(banner)
                            .group("patternable_umbrellas")
                            .criterion(FabricRecipeProvider.hasItem(UmbrellasRegistry.UMBRELLA), FabricRecipeProvider.conditionsFromItem(UmbrellasRegistry.UMBRELLA))
                            .offerTo(exporter);
                }
            }

            List<Pair<Item, Item>> patterns = List.of(
                    new Pair<>(Items.GLOBE_BANNER_PATTERN, UmbrellasRegistry.GLOBE_UMBRELLA_PATTERN),
                    new Pair<>(Items.CREEPER_BANNER_PATTERN, UmbrellasRegistry.CREEPER_UMBRELLA_PATTERN),
                    new Pair<>(Items.SKULL_BANNER_PATTERN, UmbrellasRegistry.SKULL_UMBRELLA_PATTERN),
                    new Pair<>(Items.FLOWER_BANNER_PATTERN, UmbrellasRegistry.FLOWER_UMBRELLA_PATTERN),
                    new Pair<>(Items.MOJANG_BANNER_PATTERN, UmbrellasRegistry.MOJANG_UMBRELLA_PATTERN),
                    new Pair<>(Items.PIGLIN_BANNER_PATTERN, UmbrellasRegistry.PIGLIN_UMBRELLA_PATTERN)
            );

            for (Pair<Item, Item> pair : patterns) {
                ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, pair.getRight())
                        .input(pair.getLeft())
                        .criterion(FabricRecipeProvider.hasItem(pair.getLeft()), FabricRecipeProvider.conditionsFromItem(pair.getLeft()))
                        .group("banner_to_umbrella")
                        .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, pair.getLeft())
                        .input(pair.getRight())
                        .criterion(FabricRecipeProvider.hasItem(pair.getRight()), FabricRecipeProvider.conditionsFromItem(pair.getRight()))
                        .group("umbrella_to_banner")
                        .offerTo(exporter);
            }

            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, UmbrellasRegistry.PRIDE_UMBRELLA_PATTERN)
                    .input(Items.PAPER)
                    .input(Items.GLOW_INK_SAC)
                    .criterion(FabricRecipeProvider.hasItem(Items.GLOW_INK_SAC), FabricRecipeProvider.conditionsFromItem(Items.GLOW_INK_SAC))
                    .offerTo(exporter);
        }
    }

    private static class AdvancementsGenerator extends FabricAdvancementProvider {
        protected AdvancementsGenerator(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateAdvancement(Consumer<Advancement> consumer) {
            Advancement getUmbrellaAdvancement = Advancement.Builder.create().parent(PneumonoDatagenHelper.getDummyAdvancement(new Identifier("minecraft","adventure/root")))
                    .display(
                            UmbrellasRegistry.UMBRELLA,
                            Text.translatable("advancements.umbrellas.get_umbrella.name"),
                            Text.translatable("advancements.umbrellas.get_umbrella.desc"),
                            null,
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    .criterion("has_umbrella", InventoryChangedCriterion.Conditions.items(PneumonoDatagenHelper.getTagItemPredicate(UmbrellasRegistry.TAG_UMBRELLAS)))
                    .build(consumer, Umbrellas.MOD_ID + ":adventure/get_umbrella");

            ItemStack glidingEnchanted = UmbrellasRegistry.UMBRELLA.getDefaultStack();
            glidingEnchanted.addEnchantment(UmbrellasRegistry.GLIDING, 3);
            Advancement.Builder.create().parent(getUmbrellaAdvancement)
                    .display(
                            glidingEnchanted,
                            Text.translatable("advancements.umbrellas.get_gliding_umbrella.name"),
                            Text.translatable("advancements.umbrellas.get_gliding_umbrella.desc"),
                            null,
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    .criterion("has_gliding_umbrella", InventoryChangedCriterion.Conditions.items(PneumonoDatagenHelper.getEnchantmentItemPredicate(new EnchantmentPredicate(UmbrellasRegistry.GLIDING, NumberRange.IntRange.ANY))))
                    .build(consumer, Umbrellas.MOD_ID + ":adventure/get_gliding_umbrella");

            ItemStack windCatchingEnchanted = UmbrellasRegistry.UMBRELLA.getDefaultStack();
            windCatchingEnchanted.addEnchantment(UmbrellasRegistry.GLIDING, 3);
            Advancement.Builder.create().parent(getUmbrellaAdvancement)
                    .display(
                            windCatchingEnchanted,
                            Text.translatable("advancements.umbrellas.get_wind_catching_umbrella.name"),
                            Text.translatable("advancements.umbrellas.get_wind_catching_umbrella.desc"),
                            null,
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    .criterion("has_wind_catching_umbrella", InventoryChangedCriterion.Conditions.items(PneumonoDatagenHelper.getEnchantmentItemPredicate(new EnchantmentPredicate(UmbrellasRegistry.WIND_CATCHING, NumberRange.IntRange.ANY))))
                    .build(consumer, Umbrellas.MOD_ID + ":adventure/get_wind_catching_umbrella");
        }
    }
}
