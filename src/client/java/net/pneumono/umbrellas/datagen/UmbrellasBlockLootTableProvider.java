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
        this.addDrop(UmbrellasBlocks.UMBRELLA_STAND);
    }
}
