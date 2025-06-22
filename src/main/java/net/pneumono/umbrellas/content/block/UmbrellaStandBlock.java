package net.pneumono.umbrellas.content.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.registry.UmbrellasTags;

public class UmbrellaStandBlock extends BlockWithEntity {
    public static final MapCodec<UmbrellaStandBlock> CODEC = createCodec(UmbrellaStandBlock::new);
    private static final VoxelShape SHAPE = Block.createCuboidShape(7, 0, 7, 9, 16, 9);

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    public UmbrellaStandBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!(world.getBlockEntity(pos) instanceof UmbrellaStandBlockEntity blockEntity) || !player.getMainHandStack().isEmpty()) return ActionResult.PASS;

        ItemStack stack = blockEntity.removeStack();
        if (stack.isEmpty()) return ActionResult.PASS;

        if (!player.giveItemStack(stack)) {
            player.dropItem(stack, false);
        }

        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        return ActionResult.SUCCESS;
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!(world.getBlockEntity(pos) instanceof UmbrellaStandBlockEntity blockEntity)) return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        if (blockEntity.hasStack()) return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;

        ItemStack itemStack = player.getStackInHand(hand);
        if (!itemStack.isIn(UmbrellasTags.UMBRELLAS)) return ActionResult.PASS;

        if (world.getBlockState(pos.up()).isAir() && world.getBlockState(pos.up(2)).isAir()) {
            if (!world.isClient()) {
                blockEntity.setStack(itemStack.copyWithCount(1));
                world.updateListeners(pos, state, state, 0);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, state));

                if (!player.isCreative()) {
                    itemStack.decrement(1);
                }
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    @Override
    protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
        ItemScatterer.onStateReplaced(state, world, pos);
    }

    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return world.getBlockEntity(pos) instanceof UmbrellaStandBlockEntity blockEntity ? blockEntity.getComparatorOutput() : 0;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new UmbrellaStandBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
