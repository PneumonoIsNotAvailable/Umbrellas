package net.pneumono.umbrellas.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.block.UmbrellaStandBlock;
import net.pneumono.umbrellas.content.block.UmbrellaStandBlockEntity;

import java.util.function.Function;

public class UmbrellasBlocks {
    public static final Block UMBRELLA_STAND = registerBlock("umbrella_stand", UmbrellaStandBlock::new, AbstractBlock.Settings.copy(Blocks.SPRUCE_FENCE).nonOpaque());

    public static final BlockEntityType<UmbrellaStandBlockEntity> UMBRELLA_STAND_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Umbrellas.MOD_ID, "umbrella_stand"),
            FabricBlockEntityTypeBuilder.create(UmbrellaStandBlockEntity::new, UMBRELLA_STAND).build()
    );

    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Umbrellas.id(name));
        return Registry.register(Registries.BLOCK, key, factory.apply(settings.registryKey(key)));
    }

    public static void registerUmbrellasBlocks() {

    }
}
