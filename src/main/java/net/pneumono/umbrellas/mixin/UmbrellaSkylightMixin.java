package net.pneumono.umbrellas.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.pneumono.pneumonocore.util.PneumonoMathHelper;
import net.pneumono.umbrellas.content.UmbrellaItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(World.class)
@SuppressWarnings("unused")
public abstract class UmbrellaSkylightMixin implements WorldAccess {
    @Override
    public boolean isSkyVisible(BlockPos pos) {
        return !isUnderUmbrella(pos) && WorldAccess.super.isSkyVisible(pos);
    }

    public boolean isUnderUmbrella(BlockPos pos) {
        Box box = new Box(new BlockPos(pos.getX() - 2, pos.getY(), pos.getZ() - 2), new BlockPos(pos.getX() + 2, pos.getY() + 10, pos.getZ() + 2));
        for (Entity temp : getOtherEntities(null, box)) {
            if (temp instanceof LivingEntity friend && PneumonoMathHelper.horizontalDistanceBetween(friend.getBlockPos(), pos) <= 2 && (friend.getMainHandStack().getItem() instanceof UmbrellaItem || friend.getOffHandStack().getItem() instanceof UmbrellaItem)) {
                return true;
            }
        }
        return false;
    }
}
