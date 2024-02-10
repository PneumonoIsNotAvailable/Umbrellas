package net.pneumono.umbrellas.content;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class UmbrellaStandBlock extends BlockWithEntity {
    private static final VoxelShape SHAPE = Block.createCuboidShape(7, 0, 7, 9, 16, 9);

    public static final BooleanProperty HAS_UMBRELLA = BooleanProperty.of("has_umbrella");

    public UmbrellaStandBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HAS_UMBRELLA, false));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(itemStack);
        if (nbtCompound != null && nbtCompound.contains("UmbrellaItem")) {
            world.setBlockState(pos, state.with(HAS_UMBRELLA, true), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof UmbrellaStandBlockEntity umbrellaStandBlockEntity) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (state.get(HAS_UMBRELLA)) {
                if (itemStack.isEmpty() && hand == Hand.MAIN_HAND) {
                    if (!world.isClient) {
                        player.setStackInHand(hand, umbrellaStandBlockEntity.removeStack());
                        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, state));
                    }
                    return ActionResult.success(world.isClient);
                }
            } else {
                if (itemStack.getItem() instanceof UmbrellaItem && world.getBlockState(pos.up()).isAir() && world.getBlockState(pos.up(2)).isAir()) {
                    if (!world.isClient) {
                        umbrellaStandBlockEntity.setStack(itemStack.copy());
                        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, state));

                        if (!player.isCreative()) {
                            itemStack.decrement(1);
                        }
                    }
                    return ActionResult.success(world.isClient);
                }
            }
        }
        return ActionResult.PASS;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof UmbrellaStandBlockEntity umbrellaStandBlockEntity) {
            umbrellaStandBlockEntity.dropUmbrella();
        }
        super.onStateReplaced(state, world, pos, newState, moved);
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
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HAS_UMBRELLA);
    }
}
