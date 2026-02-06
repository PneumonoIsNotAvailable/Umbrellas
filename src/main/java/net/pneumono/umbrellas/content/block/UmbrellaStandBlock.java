package net.pneumono.umbrellas.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.pneumono.umbrellas.registry.UmbrellasMisc;
import net.pneumono.umbrellas.registry.UmbrellasTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

//? if >=1.21.6 {
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
//?} else >=1.21 {
/*import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.level.LevelAccessor;
*///?} else {
/*import net.minecraft.world.level.LevelAccessor;
*///?}

//? if >=1.21 {
import com.mojang.serialization.MapCodec;
//?}

public class UmbrellaStandBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    //? if >=1.21
    public static final MapCodec<UmbrellaStandBlock> CODEC = simpleCodec(UmbrellaStandBlock::new);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty HAS_UMBRELLA = BooleanProperty.create("has_umbrella");
    private static final VoxelShape SHAPE = Block.box(7, 0, 7, 9, 16, 9);

    //? if >=1.21 {
    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
    //?}

    public UmbrellaStandBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(WATERLOGGED, false)
                .setValue(FACING, Direction.NORTH)
                .setValue(HAS_UMBRELLA, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, HAS_UMBRELLA);
    }

    //? if <1.21 {
    /*@Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        InteractionResult result = useItemOn(stack, state, level, pos, player, hand, hit);

        // CONSUME_PARTIAL is used here to represent PASS_TO_DEFAULT_BLOCK_INTERACTION (1.21) or TRY_WITH_EMPTY_HAND (>=1.21.6).
        // PASS therefore represents SKIP_DEFAULT_BLOCK_INTERACTION (1.21) or PASS (>=1.21.6).
        if (result == InteractionResult.CONSUME_PARTIAL && hand == InteractionHand.MAIN_HAND) {
            result = useWithoutItem(state, level, pos, player, hit);
        }

        return result;
    }
    *///?}

    //? if >=1.21
    @Override
    protected @NotNull /*? if >=1.21.6 || <1.21 {*/InteractionResult/*?} else {*//*ItemInteractionResult*//*?}*/ useItemOn(
            ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit
    ) {
        if (stack.isEmpty()) {
            //? if >=1.21.6 {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
            //?} else if >=1.21 {
            /*return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            *///?} else {
            /*return InteractionResult.CONSUME_PARTIAL;
            *///?}
        }

        if (!stack.is(UmbrellasTags.UMBRELLAS)) {
            //? if >=1.21.6 {
            return InteractionResult.PASS;
            //?} else if >=1.21 {
            /*return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
            *///?} else {
            /*return InteractionResult.PASS;
            *///?}
        }

        if (!state.getValue(HAS_UMBRELLA) && level.getBlockState(pos.above()).isAir() && level.getBlockState(pos.above(2)).isAir()) {
            if (!level.isClientSide()) {
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                //? if >=1.21 {
                ItemStack resultStack = stack.consumeAndReturn(1, player);
                //?} else {
                /*ItemStack resultStack = stack.copyWithCount(1);
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                *///?}
                if (level.getBlockEntity(pos) instanceof UmbrellaStandBlockEntity blockEntity) {
                    blockEntity.setTheItem(resultStack);
                }

                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
                level.playSound(null, pos, UmbrellasMisc.UMBRELLA_STAND_INSERT_SOUND, SoundSource.BLOCKS);
            }
            //? if >=1.21.6 {
            return InteractionResult.SUCCESS;
            //?} else if >=1.21 {
            /*return ItemInteractionResult.sidedSuccess(level.isClientSide);
            *///?} else {
            /*return InteractionResult.sidedSuccess(level.isClientSide);
            *///?}
        }

        //? if >=1.21.6 {
        return InteractionResult.FAIL;
        //?} else if >=1.21 {
        /*return ItemInteractionResult.FAIL;
        *///?} else {
        /*return InteractionResult.FAIL;
        *///?}
    }

    //? if >=1.21
    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!state.getValue(HAS_UMBRELLA)) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            if (level.getBlockEntity(pos) instanceof UmbrellaStandBlockEntity blockEntity) {
                ItemStack stack = blockEntity.removeTheItem();

                if (!player.addItem(stack)) {
                    player.drop(stack, false);
                }
            }

            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
            level.playSound(null, pos, UmbrellasMisc.UMBRELLA_STAND_PICKUP_SOUND, SoundSource.BLOCKS);
        }

        //? if >=1.21.6 {
        return InteractionResult.SUCCESS;
        //?} else {
        /*return InteractionResult.sidedSuccess(level.isClientSide);
        *///?}
    }

    //? if >=1.21.6 {
    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean moved) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }
    //?} else {
    /*@Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState state2, boolean bl) {
        //? if >=1.21 {
        Containers.dropContentsOnDestroy(state, state2, level, pos);
        //?} else {
        /^if (!state.is(state2.getBlock())) {
			if (level.getBlockEntity(pos) instanceof UmbrellaStandBlockEntity entity) {
				Containers.dropContents(level, pos, entity);
				level.updateNeighbourForOutputSignal(pos, state.getBlock());
			}
		}
        ^///?}
        super.onRemove(state, level, pos, state2, bl);
    }
    *///?}

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos /*? if >=1.21.9 {*/, Direction direction/*?}*/) {
        return state.getValue(HAS_UMBRELLA) ? 15 : 0;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new UmbrellaStandBlockEntity(pos, state);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return Objects.requireNonNull(super.getStateForPlacement(ctx))
                .setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER)
                .setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    //? if >=1.21.6 {
    @Override
    protected @NotNull BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            tickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, level, tickAccess, pos, direction, neighborPos, neighborState, random);
    }
    //?} else {
    /*@Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (state.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        return super.updateShape(state, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }
    *///?}

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
