package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.pneumono.umbrellas.registry.UmbrellasBlocks;

import java.util.concurrent.CompletableFuture;

public class UmbrellasBlockLootTableProvider extends FabricBlockLootTableProvider {
    public UmbrellasBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput/*? if >=1.21 {*/, registryLookup/*?}*/);
    }

    @Override
    public void generate() {
        this.dropSelf(UmbrellasBlocks.OAK_UMBRELLA_STAND);
        this.dropSelf(UmbrellasBlocks.SPRUCE_UMBRELLA_STAND);
        this.dropSelf(UmbrellasBlocks.BIRCH_UMBRELLA_STAND);
        this.dropSelf(UmbrellasBlocks.ACACIA_UMBRELLA_STAND);
        this.dropSelf(UmbrellasBlocks.CHERRY_UMBRELLA_STAND);
        this.dropSelf(UmbrellasBlocks.JUNGLE_UMBRELLA_STAND);
        this.dropSelf(UmbrellasBlocks.DARK_OAK_UMBRELLA_STAND);
        //? if >=1.21.6
        this.dropSelf(UmbrellasBlocks.PALE_OAK_UMBRELLA_STAND);
        this.dropSelf(UmbrellasBlocks.CRIMSON_UMBRELLA_STAND);
        this.dropSelf(UmbrellasBlocks.WARPED_UMBRELLA_STAND);
        this.dropSelf(UmbrellasBlocks.MANGROVE_UMBRELLA_STAND);
        this.dropSelf(UmbrellasBlocks.BAMBOO_UMBRELLA_STAND);
    }
}
