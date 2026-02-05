package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.pneumono.umbrellas.registry.UmbrellasBlocks;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.concurrent.CompletableFuture;

//? if >=1.21.6
import net.minecraft.data.tags.TagAppender;

public class UmbrellasBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public UmbrellasBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        create(UmbrellasTags.BOOSTS_UMBRELLAS).forceAddTag(
                BlockTags.FIRE
        ).forceAddTag(
                BlockTags.CAMPFIRES
        ).add(
                Blocks.LAVA,
                Blocks.LAVA_CAULDRON
        );

        create(UmbrellasTags.BOOSTS_ELYTRA).forceAddTag(
                BlockTags.CAMPFIRES
        );

        create(UmbrellasTags.UMBRELLA_BOOSTING_TOGGLEABLE).add(
                Blocks.LAVA,
                Blocks.LAVA_CAULDRON
        );

        create(UmbrellasTags.SMOKE_PASSES_THROUGH).forceAddTag(
                BlockTags.LEAVES
        ).forceAddTag(
                BlockTags.WALLS
        ).add(
                //? if >=1.21 {
                Blocks.COPPER_GRATE,
                Blocks.EXPOSED_COPPER_GRATE,
                Blocks.WEATHERED_COPPER_GRATE,
                Blocks.OXIDIZED_COPPER_GRATE,
                //?}
                Blocks.SCAFFOLDING
        );

        create(BlockTags.MINEABLE_WITH_AXE).add(
                UmbrellasBlocks.OAK_UMBRELLA_STAND,
                UmbrellasBlocks.SPRUCE_UMBRELLA_STAND,
                UmbrellasBlocks.BIRCH_UMBRELLA_STAND,
                UmbrellasBlocks.ACACIA_UMBRELLA_STAND,
                UmbrellasBlocks.CHERRY_UMBRELLA_STAND,
                UmbrellasBlocks.JUNGLE_UMBRELLA_STAND,
                UmbrellasBlocks.DARK_OAK_UMBRELLA_STAND,
                //? if >=1.21.6
                UmbrellasBlocks.PALE_OAK_UMBRELLA_STAND,
                UmbrellasBlocks.CRIMSON_UMBRELLA_STAND,
                UmbrellasBlocks.WARPED_UMBRELLA_STAND,
                UmbrellasBlocks.MANGROVE_UMBRELLA_STAND,
                UmbrellasBlocks.BAMBOO_UMBRELLA_STAND
        );
    }

    private /*? if >=1.21.6 {*/TagAppender<Block, Block>/*?} else {*//*BlockTagProvider.FabricTagBuilder*//*?}*/ create(TagKey<Block> tag) {
        //? if >=1.21.6 {
        return valueLookupBuilder(tag);
         //?} else {
        /*return getOrCreateTagBuilder(tag);
        *///?}
    }
}
