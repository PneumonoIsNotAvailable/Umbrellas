package net.pneumono.umbrellas.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.pneumono.pneumonocore.util.PneumonoMathHelper;
import net.pneumono.umbrellas.content.UmbrellaItem;
import net.pneumono.umbrellas.content.UmbrellaStandBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(World.class)
@SuppressWarnings("unused")
public abstract class UmbrellaSkylightMixin implements WorldAccess {
    @Override
    public boolean isSkyVisible(BlockPos pos) {
        return !isUnderUmbrella(pos) && WorldAccess.super.isSkyVisible(pos);
    }

    public boolean isUnderUmbrella(BlockPos pos) {
        int areaWidth = 2;
        int startY = 0;
        int endY = 10;

        Box box = new Box(new BlockPos(pos.getX() - areaWidth, pos.getY() + startY, pos.getZ() - areaWidth), new BlockPos(pos.getX() + areaWidth, pos.getY() + endY, pos.getZ() + areaWidth));
        for (Entity temp : getOtherEntities(null, box)) {
            if (temp instanceof LivingEntity friend && PneumonoMathHelper.horizontalDistanceBetween(friend.getBlockPos(), pos) <= 2 && (friend.getMainHandStack().getItem() instanceof UmbrellaItem || friend.getOffHandStack().getItem() instanceof UmbrellaItem)) {
                return true;
            }
        }
        for (int x = -areaWidth; x <= areaWidth; ++x) {
            for (int y = startY; y <= endY; ++y) {
                for (int z = -areaWidth; z <= areaWidth; ++z) {
                    BlockPos newPos = new BlockPos(pos.getX() + x, pos.getY() + y - 1, pos.getZ() + z);
                    BlockState state = getBlockState(newPos);
                    if (state.getBlock() instanceof UmbrellaStandBlock && state.get(UmbrellaStandBlock.HAS_UMBRELLA) && PneumonoMathHelper.horizontalDistanceBetween(newPos, pos) <= 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
