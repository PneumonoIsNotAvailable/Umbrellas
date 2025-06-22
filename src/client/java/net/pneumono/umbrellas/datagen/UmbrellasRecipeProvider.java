package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Pair;
import net.pneumono.umbrellas.content.item.PatternableUmbrellaItem;
import net.pneumono.umbrellas.registry.UmbrellasItems;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UmbrellasRecipeProvider extends FabricRecipeProvider {
    public UmbrellasRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new UmbrellasRecipeGenerator(registryLookup, exporter);
    }

    @Override
    public String getName() {
        return "Recipes";
    }

    private static class UmbrellasRecipeGenerator extends RecipeGenerator {
        private static final String HAS_UMBRELLA = "has_umbrella";

        protected UmbrellasRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
            super(registries, exporter);
        }

        @Override
        public void generate() {
            createPatternableUmbrella(UmbrellasItems.WHITE_UMBRELLA);
            createPatternableUmbrella(UmbrellasItems.ORANGE_UMBRELLA);
            createPatternableUmbrella(UmbrellasItems.MAGENTA_UMBRELLA);
            createPatternableUmbrella(UmbrellasItems.LIGHT_BLUE_UMBRELLA);
            createPatternableUmbrella(UmbrellasItems.YELLOW_UMBRELLA);
            createPatternableUmbrella(UmbrellasItems.LIME_UMBRELLA);
            createPatternableUmbrella(UmbrellasItems.PINK_UMBRELLA);
            createPatternableUmbrella(UmbrellasItems.GRAY_UMBRELLA);
            createPatternableUmbrella(UmbrellasItems.LIGHT_GRAY_UMBRELLA);
            createPatternableUmbrella(UmbrellasItems.CYAN_UMBRELLA);
            createPatternableUmbrella(UmbrellasItems.PURPLE_UMBRELLA);
            createPatternableUmbrella(UmbrellasItems.BLUE_UMBRELLA);
            createPatternableUmbrella(UmbrellasItems.BROWN_UMBRELLA);
            createPatternableUmbrella(UmbrellasItems.GREEN_UMBRELLA);
            createPatternableUmbrella(UmbrellasItems.RED_UMBRELLA);
            createPatternableUmbrella(UmbrellasItems.BLACK_UMBRELLA);

            createUmbrellaStand(UmbrellasItems.OAK_UMBRELLA_STAND, Items.OAK_PLANKS);
            createUmbrellaStand(UmbrellasItems.SPRUCE_UMBRELLA_STAND, Items.SPRUCE_PLANKS);
            createUmbrellaStand(UmbrellasItems.BIRCH_UMBRELLA_STAND, Items.BIRCH_PLANKS);
            createUmbrellaStand(UmbrellasItems.ACACIA_UMBRELLA_STAND, Items.ACACIA_PLANKS);
            createUmbrellaStand(UmbrellasItems.CHERRY_UMBRELLA_STAND, Items.CHERRY_PLANKS);
            createUmbrellaStand(UmbrellasItems.JUNGLE_UMBRELLA_STAND, Items.JUNGLE_PLANKS);
            createUmbrellaStand(UmbrellasItems.DARK_OAK_UMBRELLA_STAND, Items.DARK_OAK_PLANKS);
            createUmbrellaStand(UmbrellasItems.PALE_OAK_UMBRELLA_STAND, Items.PALE_OAK_PLANKS);
            createUmbrellaStand(UmbrellasItems.CRIMSON_UMBRELLA_STAND, Items.CRIMSON_PLANKS);
            createUmbrellaStand(UmbrellasItems.WARPED_UMBRELLA_STAND, Items.WARPED_PLANKS);
            createUmbrellaStand(UmbrellasItems.MANGROVE_UMBRELLA_STAND, Items.MANGROVE_PLANKS);
            createUmbrellaStand(UmbrellasItems.BAMBOO_UMBRELLA_STAND, Items.BAMBOO_PLANKS);

            createShaped(RecipeCategory.TOOLS, UmbrellasItems.ANIMALS_UMBRELLA)
                    .pattern("RAR")
                    .pattern("RUR")
                    .pattern(" S ")
                    .input('R', Items.RED_WOOL)
                    .input('A', Items.AXOLOTL_BUCKET)
                    .input('U', UmbrellasTags.UMBRELLAS)
                    .input('S', Items.STICK)
                    .group("extra_umbrellas")
                    .criterion(HAS_UMBRELLA, conditionsFromTag(UmbrellasTags.UMBRELLAS))
                    .offerTo(exporter);
            createShaped(RecipeCategory.TOOLS, UmbrellasItems.AZALEA_UMBRELLA)
                    .pattern("MFM")
                    .pattern("MUM")
                    .pattern(" S ")
                    .input('M', Items.MOSS_BLOCK)
                    .input('F', Items.FLOWERING_AZALEA)
                    .input('U', UmbrellasTags.UMBRELLAS)
                    .input('S', Items.STICK)
                    .group("extra_umbrellas")
                    .criterion(HAS_UMBRELLA, conditionsFromTag(UmbrellasTags.UMBRELLAS))
                    .offerTo(exporter);
            createShaped(RecipeCategory.TOOLS, UmbrellasItems.GALACTIC_UMBRELLA)
                    .pattern("PEP")
                    .pattern("PUP")
                    .pattern(" S ")
                    .input('P', Items.PURPLE_WOOL)
                    .input('E', Items.ENDER_PEARL)
                    .input('U', UmbrellasTags.UMBRELLAS)
                    .input('S', Items.STICK)
                    .group("extra_umbrellas")
                    .criterion(HAS_UMBRELLA, conditionsFromTag(UmbrellasTags.UMBRELLAS))
                    .offerTo(exporter);
            createShaped(RecipeCategory.TOOLS, UmbrellasItems.GOTHIC_UMBRELLA)
                    .pattern("BIB")
                    .pattern("BUB")
                    .pattern(" S ")
                    .input('B', Items.BLACK_WOOL)
                    .input('I', Items.IRON_INGOT)
                    .input('U', UmbrellasTags.UMBRELLAS)
                    .input('S', Items.STICK)
                    .group("extra_umbrellas")
                    .criterion(HAS_UMBRELLA, conditionsFromTag(UmbrellasTags.UMBRELLAS))
                    .offerTo(exporter);
            createShaped(RecipeCategory.TOOLS, UmbrellasItems.JELLYFISH_UMBRELLA)
                    .pattern("LKL")
                    .pattern("LUL")
                    .pattern(" S ")
                    .input('L', Items.LIGHT_BLUE_STAINED_GLASS)
                    .input('K', Items.KELP)
                    .input('U', UmbrellasTags.UMBRELLAS)
                    .input('S', Items.STICK)
                    .group("extra_umbrellas")
                    .criterion(HAS_UMBRELLA, conditionsFromTag(UmbrellasTags.UMBRELLAS))
                    .offerTo(exporter);

            List<Pair<Item, Item>> patterns = List.of(
                    new Pair<>(Items.GLOBE_BANNER_PATTERN, UmbrellasItems.GLOBE_UMBRELLA_PATTERN),
                    new Pair<>(Items.CREEPER_BANNER_PATTERN, UmbrellasItems.CREEPER_UMBRELLA_PATTERN),
                    new Pair<>(Items.SKULL_BANNER_PATTERN, UmbrellasItems.SKULL_UMBRELLA_PATTERN),
                    new Pair<>(Items.FLOWER_BANNER_PATTERN, UmbrellasItems.FLOWER_UMBRELLA_PATTERN),
                    new Pair<>(Items.MOJANG_BANNER_PATTERN, UmbrellasItems.MOJANG_UMBRELLA_PATTERN),
                    new Pair<>(Items.PIGLIN_BANNER_PATTERN, UmbrellasItems.PIGLIN_UMBRELLA_PATTERN),
                    new Pair<>(Items.FLOW_BANNER_PATTERN, UmbrellasItems.FLOW_UMBRELLA_PATTERN),
                    new Pair<>(Items.GUSTER_BANNER_PATTERN, UmbrellasItems.GUSTER_UMBRELLA_PATTERN),
                    new Pair<>(Items.FIELD_MASONED_BANNER_PATTERN, UmbrellasItems.FIELD_MASONED_UMBRELLA_PATTERN),
                    new Pair<>(Items.BORDURE_INDENTED_BANNER_PATTERN, UmbrellasItems.BORDURE_INDENTED_UMBRELLA_PATTERN)
            );

            for (Pair<Item, Item> pair : patterns) {
                createShapeless(RecipeCategory.MISC, pair.getRight())
                        .input(pair.getLeft())
                        .criterion(hasItem(pair.getLeft()), conditionsFromItem(pair.getLeft()))
                        .group("banner_to_umbrella")
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, pair.getLeft())
                        .input(pair.getRight())
                        .criterion(hasItem(pair.getRight()), conditionsFromItem(pair.getRight()))
                        .group("umbrella_to_banner")
                        .offerTo(exporter);
            }

            createShapeless(RecipeCategory.MISC, UmbrellasItems.PRIDE_UMBRELLA_PATTERN)
                    .input(Items.PAPER)
                    .input(Items.GLOW_INK_SAC)
                    .criterion(hasItem(Items.GLOW_INK_SAC), conditionsFromItem(Items.GLOW_INK_SAC))
                    .offerTo(exporter);
        }
        
        private void createPatternableUmbrella(PatternableUmbrellaItem umbrella) {
            createShaped(RecipeCategory.TOOLS, umbrella)
                    .pattern("WLW")
                    .pattern("LSL")
                    .pattern(" S ")
                    .input('W', getWool(umbrella.getColor()))
                    .input('L', Items.LEATHER)
                    .input('S', Items.STICK)
                    .group("umbrella")
                    .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                    .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                    .offerTo(exporter);
        }

        private void createUmbrellaStand(Item stand, Item planks) {
            createShaped(RecipeCategory.DECORATIONS, stand)
                    .pattern("P")
                    .pattern("S")
                    .pattern("P")
                    .input('P', planks)
                    .input('S', Items.STICK)
                    .group("umbrella_stand")
                    .criterion(hasItem(planks), conditionsFromItem(planks))
                    .offerTo(exporter);
        }
    }

    private static Item getWool(DyeColor color) {
        return switch (color) {
            case WHITE -> Items.WHITE_WOOL;
            case ORANGE -> Items.ORANGE_WOOL;
            case MAGENTA -> Items.MAGENTA_WOOL;
            case LIGHT_BLUE -> Items.LIGHT_BLUE_WOOL;
            case YELLOW -> Items.YELLOW_WOOL;
            case LIME -> Items.LIME_WOOL;
            case PINK -> Items.PINK_WOOL;
            case GRAY -> Items.GRAY_WOOL;
            case LIGHT_GRAY -> Items.LIGHT_GRAY_WOOL;
            case CYAN -> Items.CYAN_WOOL;
            case BLUE -> Items.BLUE_WOOL;
            case BROWN -> Items.BROWN_WOOL;
            case GREEN -> Items.GREEN_WOOL;
            case RED -> Items.RED_WOOL;
            case BLACK -> Items.BLACK_WOOL;
            case PURPLE -> Items.PURPLE_WOOL;
        };
    }
}
