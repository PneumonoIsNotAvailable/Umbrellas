package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
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
    }
}
