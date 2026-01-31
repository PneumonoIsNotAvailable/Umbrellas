package net.pneumono.umbrellas.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.block.UmbrellaStandBlock;
import net.pneumono.umbrellas.content.block.UmbrellaStandBlockEntity;

import java.util.function.Function;

public class UmbrellasBlocks {
    public static final Block OAK_UMBRELLA_STAND = registerBlock("oak_umbrella_stand", UmbrellaStandBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).noOcclusion());
    public static final Block SPRUCE_UMBRELLA_STAND = registerBlock("spruce_umbrella_stand", UmbrellaStandBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_FENCE).noOcclusion());
    public static final Block BIRCH_UMBRELLA_STAND = registerBlock("birch_umbrella_stand", UmbrellaStandBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_FENCE).noOcclusion());
    public static final Block ACACIA_UMBRELLA_STAND = registerBlock("acacia_umbrella_stand", UmbrellaStandBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_FENCE).noOcclusion());
    public static final Block CHERRY_UMBRELLA_STAND = registerBlock("cherry_umbrella_stand", UmbrellaStandBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_FENCE).noOcclusion());
    public static final Block JUNGLE_UMBRELLA_STAND = registerBlock("jungle_umbrella_stand", UmbrellaStandBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_FENCE).noOcclusion());
    public static final Block DARK_OAK_UMBRELLA_STAND = registerBlock("dark_oak_umbrella_stand", UmbrellaStandBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_FENCE).noOcclusion());
    //? if >=1.21.6
    public static final Block PALE_OAK_UMBRELLA_STAND = registerBlock("pale_oak_umbrella_stand", UmbrellaStandBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.PALE_OAK_FENCE).noOcclusion());
    public static final Block CRIMSON_UMBRELLA_STAND = registerBlock("crimson_umbrella_stand", UmbrellaStandBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_FENCE).noOcclusion());
    public static final Block WARPED_UMBRELLA_STAND = registerBlock("warped_umbrella_stand", UmbrellaStandBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_FENCE).noOcclusion());
    public static final Block MANGROVE_UMBRELLA_STAND = registerBlock("mangrove_umbrella_stand", UmbrellaStandBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_FENCE).noOcclusion());
    public static final Block BAMBOO_UMBRELLA_STAND = registerBlock("bamboo_umbrella_stand", UmbrellaStandBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_FENCE).noOcclusion());

    public static final BlockEntityType<UmbrellaStandBlockEntity> UMBRELLA_STAND_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Umbrellas.id("umbrella_stand"),
            FabricBlockEntityTypeBuilder.create(UmbrellaStandBlockEntity::new,
                    OAK_UMBRELLA_STAND,
                    SPRUCE_UMBRELLA_STAND,
                    BIRCH_UMBRELLA_STAND,
                    ACACIA_UMBRELLA_STAND,
                    CHERRY_UMBRELLA_STAND,
                    JUNGLE_UMBRELLA_STAND,
                    DARK_OAK_UMBRELLA_STAND,
                    //? if >=1.21.6
                    PALE_OAK_UMBRELLA_STAND,
                    CRIMSON_UMBRELLA_STAND,
                    WARPED_UMBRELLA_STAND,
                    MANGROVE_UMBRELLA_STAND,
                    BAMBOO_UMBRELLA_STAND
            ).build()
    );

    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Umbrellas.id(name));
        return Registry.register(
                BuiltInRegistries.BLOCK, key,
                factory.apply(properties/*? if >=1.21.6 {*/.setId(key)/*?}*/)
        );
    }

    public static void registerUmbrellasBlocks() {

    }
}
