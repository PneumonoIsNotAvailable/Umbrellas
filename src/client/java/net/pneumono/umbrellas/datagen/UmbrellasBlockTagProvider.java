package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.pneumono.umbrellas.registry.UmbrellasBlocks;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.concurrent.CompletableFuture;

public class UmbrellasBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public UmbrellasBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        valueLookupBuilder(UmbrellasTags.BOOSTS_UMBRELLAS).forceAddTag(
                BlockTags.FIRE
        ).forceAddTag(
                BlockTags.CAMPFIRES
        ).add(
                Blocks.LAVA,
                Blocks.LAVA_CAULDRON
        );

        valueLookupBuilder(UmbrellasTags.BOOSTS_ELYTRA).forceAddTag(
                BlockTags.CAMPFIRES
        );

        valueLookupBuilder(UmbrellasTags.UMBRELLA_BOOSTING_TOGGLEABLE).add(
                Blocks.LAVA,
                Blocks.LAVA_CAULDRON
        );

        valueLookupBuilder(UmbrellasTags.SMOKE_PASSES_THROUGH).forceAddTag(
                BlockTags.LEAVES
        ).forceAddTag(
                BlockTags.WALLS
        ).add(
                Blocks.COPPER_GRATE,
                Blocks.EXPOSED_COPPER_GRATE,
                Blocks.WEATHERED_COPPER_GRATE,
                Blocks.OXIDIZED_COPPER_GRATE,
                Blocks.SCAFFOLDING
        );

        valueLookupBuilder(BlockTags.AXE_MINEABLE).add(
                UmbrellasBlocks.OAK_UMBRELLA_STAND,
                UmbrellasBlocks.SPRUCE_UMBRELLA_STAND,
                UmbrellasBlocks.BIRCH_UMBRELLA_STAND,
                UmbrellasBlocks.ACACIA_UMBRELLA_STAND,
                UmbrellasBlocks.CHERRY_UMBRELLA_STAND,
                UmbrellasBlocks.JUNGLE_UMBRELLA_STAND,
                UmbrellasBlocks.DARK_OAK_UMBRELLA_STAND,
                UmbrellasBlocks.PALE_OAK_UMBRELLA_STAND,
                UmbrellasBlocks.CRIMSON_UMBRELLA_STAND,
                UmbrellasBlocks.WARPED_UMBRELLA_STAND,
                UmbrellasBlocks.MANGROVE_UMBRELLA_STAND,
                UmbrellasBlocks.BAMBOO_UMBRELLA_STAND
        );
    }
}
