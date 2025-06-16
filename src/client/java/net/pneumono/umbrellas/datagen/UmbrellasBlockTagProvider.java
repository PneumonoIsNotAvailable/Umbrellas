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
        getOrCreateTagBuilder(UmbrellasTags.BOOSTS_UMBRELLAS).forceAddTags(
                BlockTags.FIRE,
                BlockTags.CAMPFIRES
        ).add(
                Blocks.LAVA,
                Blocks.LAVA_CAULDRON
        );
    }
}
