package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import net.pneumono.umbrellas.registry.UmbrellasBlocks;

import java.util.concurrent.CompletableFuture;

public class UmbrellasBlockLootTableProvider extends FabricBlockLootTableProvider {
    public UmbrellasBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        this.addDrop(UmbrellasBlocks.OAK_UMBRELLA_STAND);
        this.addDrop(UmbrellasBlocks.SPRUCE_UMBRELLA_STAND);
        this.addDrop(UmbrellasBlocks.BIRCH_UMBRELLA_STAND);
        this.addDrop(UmbrellasBlocks.ACACIA_UMBRELLA_STAND);
        this.addDrop(UmbrellasBlocks.CHERRY_UMBRELLA_STAND);
        this.addDrop(UmbrellasBlocks.JUNGLE_UMBRELLA_STAND);
        this.addDrop(UmbrellasBlocks.DARK_OAK_UMBRELLA_STAND);
        this.addDrop(UmbrellasBlocks.PALE_OAK_UMBRELLA_STAND);
        this.addDrop(UmbrellasBlocks.CRIMSON_UMBRELLA_STAND);
        this.addDrop(UmbrellasBlocks.WARPED_UMBRELLA_STAND);
        this.addDrop(UmbrellasBlocks.MANGROVE_UMBRELLA_STAND);
        this.addDrop(UmbrellasBlocks.BAMBOO_UMBRELLA_STAND);
    }
}
