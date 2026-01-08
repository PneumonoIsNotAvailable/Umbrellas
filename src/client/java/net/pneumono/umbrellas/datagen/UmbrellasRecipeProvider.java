package net.pneumono.umbrellas.datagen;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.pneumono.umbrellas.registry.UmbrellasItems;
import net.pneumono.umbrellas.registry.UmbrellasTags;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UmbrellasRecipeProvider extends FabricRecipeProvider {
    public UmbrellasRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        return new UmbrellasRecipeGenerator(provider, output);
    }

    @Override
    public @NotNull String getName() {
        return "Recipes";
    }

    private static class UmbrellasRecipeGenerator extends RecipeProvider {
        private static final String HAS_UMBRELLA = "has_umbrella";

        protected UmbrellasRecipeGenerator(HolderLookup.Provider provider, RecipeOutput output) {
            super(provider, output);
        }

        @Override
        public void buildRecipes() {
            createPatternableUmbrella(UmbrellasItems.WHITE_UMBRELLA, Items.WHITE_WOOL);
            createPatternableUmbrella(UmbrellasItems.ORANGE_UMBRELLA, Items.ORANGE_WOOL);
            createPatternableUmbrella(UmbrellasItems.MAGENTA_UMBRELLA, Items.MAGENTA_WOOL);
            createPatternableUmbrella(UmbrellasItems.LIGHT_BLUE_UMBRELLA, Items.LIGHT_BLUE_WOOL);
            createPatternableUmbrella(UmbrellasItems.YELLOW_UMBRELLA, Items.YELLOW_WOOL);
            createPatternableUmbrella(UmbrellasItems.LIME_UMBRELLA, Items.LIME_WOOL);
            createPatternableUmbrella(UmbrellasItems.PINK_UMBRELLA, Items.PINK_WOOL);
            createPatternableUmbrella(UmbrellasItems.GRAY_UMBRELLA, Items.GRAY_WOOL);
            createPatternableUmbrella(UmbrellasItems.LIGHT_GRAY_UMBRELLA, Items.LIGHT_GRAY_WOOL);
            createPatternableUmbrella(UmbrellasItems.CYAN_UMBRELLA, Items.CYAN_WOOL);
            createPatternableUmbrella(UmbrellasItems.PURPLE_UMBRELLA, Items.PURPLE_WOOL);
            createPatternableUmbrella(UmbrellasItems.BLUE_UMBRELLA, Items.BLUE_WOOL);
            createPatternableUmbrella(UmbrellasItems.BROWN_UMBRELLA, Items.BROWN_WOOL);
            createPatternableUmbrella(UmbrellasItems.GREEN_UMBRELLA, Items.GREEN_WOOL);
            createPatternableUmbrella(UmbrellasItems.RED_UMBRELLA, Items.RED_WOOL);
            createPatternableUmbrella(UmbrellasItems.BLACK_UMBRELLA, Items.BLACK_WOOL);

            List<Item> umbrellas = List.of(
                    UmbrellasItems.WHITE_UMBRELLA,
                    UmbrellasItems.ORANGE_UMBRELLA,
                    UmbrellasItems.MAGENTA_UMBRELLA,
                    UmbrellasItems.LIGHT_BLUE_UMBRELLA,
                    UmbrellasItems.YELLOW_UMBRELLA,
                    UmbrellasItems.LIME_UMBRELLA,
                    UmbrellasItems.PINK_UMBRELLA,
                    UmbrellasItems.GRAY_UMBRELLA,
                    UmbrellasItems.LIGHT_GRAY_UMBRELLA,
                    UmbrellasItems.CYAN_UMBRELLA,
                    UmbrellasItems.PURPLE_UMBRELLA,
                    UmbrellasItems.BLUE_UMBRELLA,
                    UmbrellasItems.BROWN_UMBRELLA,
                    UmbrellasItems.GREEN_UMBRELLA,
                    UmbrellasItems.RED_UMBRELLA,
                    UmbrellasItems.BLACK_UMBRELLA
            );
            colorItemWithDye(Arrays.stream(DyeColor.values()).map(color -> (Item) DyeItem.byColor(color)).toList(), umbrellas, "umbrella_dye", RecipeCategory.TOOLS);

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

            shaped(RecipeCategory.TOOLS, UmbrellasItems.ANIMALS_UMBRELLA)
                    .pattern("RAR")
                    .pattern("RUR")
                    .pattern(" S ")
                    .define('R', Items.RED_WOOL)
                    .define('A', Items.AXOLOTL_BUCKET)
                    .define('U', UmbrellasTags.UMBRELLAS)
                    .define('S', Items.STICK)
                    .group("extra_umbrellas")
                    .unlockedBy(HAS_UMBRELLA, has(UmbrellasTags.UMBRELLAS))
                    .save(output);
            shaped(RecipeCategory.TOOLS, UmbrellasItems.AZALEA_UMBRELLA)
                    .pattern("MFM")
                    .pattern("MUM")
                    .pattern(" S ")
                    .define('M', Items.MOSS_BLOCK)
                    .define('F', Items.FLOWERING_AZALEA)
                    .define('U', UmbrellasTags.UMBRELLAS)
                    .define('S', Items.STICK)
                    .group("extra_umbrellas")
                    .unlockedBy(HAS_UMBRELLA, has(UmbrellasTags.UMBRELLAS))
                    .save(output);
            shaped(RecipeCategory.TOOLS, UmbrellasItems.GALACTIC_UMBRELLA)
                    .pattern("PEP")
                    .pattern("PUP")
                    .pattern(" S ")
                    .define('P', Items.PURPLE_WOOL)
                    .define('E', Items.ENDER_PEARL)
                    .define('U', UmbrellasTags.UMBRELLAS)
                    .define('S', Items.STICK)
                    .group("extra_umbrellas")
                    .unlockedBy(HAS_UMBRELLA, has(UmbrellasTags.UMBRELLAS))
                    .save(output);
            shaped(RecipeCategory.TOOLS, UmbrellasItems.GOTHIC_UMBRELLA)
                    .pattern("BIB")
                    .pattern("BUB")
                    .pattern(" S ")
                    .define('B', Items.BLACK_WOOL)
                    .define('I', Items.IRON_INGOT)
                    .define('U', UmbrellasTags.UMBRELLAS)
                    .define('S', Items.STICK)
                    .group("extra_umbrellas")
                    .unlockedBy(HAS_UMBRELLA, has(UmbrellasTags.UMBRELLAS))
                    .save(output);
            shaped(RecipeCategory.TOOLS, UmbrellasItems.JELLYFISH_UMBRELLA)
                    .pattern("LKL")
                    .pattern("LUL")
                    .pattern(" S ")
                    .define('L', Items.LIGHT_BLUE_STAINED_GLASS)
                    .define('K', Items.KELP)
                    .define('U', UmbrellasTags.UMBRELLAS)
                    .define('S', Items.STICK)
                    .group("extra_umbrellas")
                    .unlockedBy(HAS_UMBRELLA, has(UmbrellasTags.UMBRELLAS))
                    .save(output);

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
                shapeless(RecipeCategory.MISC, pair.getSecond())
                        .requires(pair.getFirst())
                        .unlockedBy(getItemName(pair.getFirst()), has(pair.getFirst()))
                        .group("banner_to_umbrella")
                        .save(output);

                shapeless(RecipeCategory.MISC, pair.getFirst())
                        .requires(pair.getSecond())
                        .unlockedBy(getItemName(pair.getSecond()), has(pair.getSecond()))
                        .group("umbrella_to_banner")
                        .save(output);
            }

            shapeless(RecipeCategory.MISC, UmbrellasItems.PRIDE_UMBRELLA_PATTERN)
                    .requires(Items.PAPER)
                    .requires(Items.GLOW_INK_SAC)
                    .unlockedBy(getItemName(Items.GLOW_INK_SAC), has(Items.GLOW_INK_SAC))
                    .save(output);
        }
        
        private void createPatternableUmbrella(Item umbrella, Item wool) {
            shaped(RecipeCategory.TOOLS, umbrella)
                    .pattern("WLW")
                    .pattern("LSL")
                    .pattern(" S ")
                    .define('W', wool)
                    .define('L', Items.LEATHER)
                    .define('S', Items.STICK)
                    .group("umbrella")
                    .unlockedBy(getItemName(Items.LEATHER), has(Items.LEATHER))
                    .unlockedBy(getItemName(Items.STICK), has(Items.STICK))
                    .save(output);
        }

        private void createUmbrellaStand(Item stand, Item planks) {
            shaped(RecipeCategory.DECORATIONS, stand)
                    .pattern("P")
                    .pattern("S")
                    .pattern("P")
                    .define('P', planks)
                    .define('S', Items.STICK)
                    .group("umbrella_stand")
                    .unlockedBy(getItemName(planks), has(planks))
                    .save(output);
        }
    }
}
