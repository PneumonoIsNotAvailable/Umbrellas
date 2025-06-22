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
    public static final Block OAK_UMBRELLA_STAND = registerBlock("oak_umbrella_stand", UmbrellaStandBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_FENCE).nonOpaque());
    public static final Block SPRUCE_UMBRELLA_STAND = registerBlock("spruce_umbrella_stand", UmbrellaStandBlock::new, AbstractBlock.Settings.copy(Blocks.SPRUCE_FENCE).nonOpaque());
    public static final Block BIRCH_UMBRELLA_STAND = registerBlock("birch_umbrella_stand", UmbrellaStandBlock::new, AbstractBlock.Settings.copy(Blocks.BIRCH_FENCE).nonOpaque());
    public static final Block ACACIA_UMBRELLA_STAND = registerBlock("acacia_umbrella_stand", UmbrellaStandBlock::new, AbstractBlock.Settings.copy(Blocks.ACACIA_FENCE).nonOpaque());
    public static final Block CHERRY_UMBRELLA_STAND = registerBlock("cherry_umbrella_stand", UmbrellaStandBlock::new, AbstractBlock.Settings.copy(Blocks.CHERRY_FENCE).nonOpaque());
    public static final Block JUNGLE_UMBRELLA_STAND = registerBlock("jungle_umbrella_stand", UmbrellaStandBlock::new, AbstractBlock.Settings.copy(Blocks.JUNGLE_FENCE).nonOpaque());
    public static final Block DARK_OAK_UMBRELLA_STAND = registerBlock("dark_oak_umbrella_stand", UmbrellaStandBlock::new, AbstractBlock.Settings.copy(Blocks.DARK_OAK_FENCE).nonOpaque());
    public static final Block PALE_OAK_UMBRELLA_STAND = registerBlock("pale_oak_umbrella_stand", UmbrellaStandBlock::new, AbstractBlock.Settings.copy(Blocks.PALE_OAK_FENCE).nonOpaque());
    public static final Block CRIMSON_UMBRELLA_STAND = registerBlock("crimson_umbrella_stand", UmbrellaStandBlock::new, AbstractBlock.Settings.copy(Blocks.CRIMSON_FENCE).nonOpaque());
    public static final Block WARPED_UMBRELLA_STAND = registerBlock("warped_umbrella_stand", UmbrellaStandBlock::new, AbstractBlock.Settings.copy(Blocks.WARPED_FENCE).nonOpaque());
    public static final Block MANGROVE_UMBRELLA_STAND = registerBlock("mangrove_umbrella_stand", UmbrellaStandBlock::new, AbstractBlock.Settings.copy(Blocks.MANGROVE_FENCE).nonOpaque());
    public static final Block BAMBOO_UMBRELLA_STAND = registerBlock("bamboo_umbrella_stand", UmbrellaStandBlock::new, AbstractBlock.Settings.copy(Blocks.BAMBOO_FENCE).nonOpaque());

    public static final BlockEntityType<UmbrellaStandBlockEntity> UMBRELLA_STAND_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Umbrellas.MOD_ID, "umbrella_stand"),
            FabricBlockEntityTypeBuilder.create(UmbrellaStandBlockEntity::new,
                    OAK_UMBRELLA_STAND,
                    SPRUCE_UMBRELLA_STAND,
                    BIRCH_UMBRELLA_STAND,
                    ACACIA_UMBRELLA_STAND,
                    CHERRY_UMBRELLA_STAND,
                    JUNGLE_UMBRELLA_STAND,
                    DARK_OAK_UMBRELLA_STAND,
                    PALE_OAK_UMBRELLA_STAND,
                    CRIMSON_UMBRELLA_STAND,
                    WARPED_UMBRELLA_STAND,
                    MANGROVE_UMBRELLA_STAND,
                    BAMBOO_UMBRELLA_STAND
            ).build()
    );

    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Umbrellas.id(name));
        return Registry.register(Registries.BLOCK, key, factory.apply(settings.registryKey(key)));
    }

    public static void registerUmbrellasBlocks() {
        Registries.BLOCK.addAlias(Umbrellas.id("umbrella_stand"), Umbrellas.id("spruce_umbrella_stand"));
    }
}
