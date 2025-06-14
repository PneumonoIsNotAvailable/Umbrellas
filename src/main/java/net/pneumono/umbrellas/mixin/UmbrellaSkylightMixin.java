package net.pneumono.umbrellas.mixin;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.pneumono.pneumonocore.util.PneumonoMathHelper;
import net.pneumono.umbrellas.content.block.UmbrellaStandBlockEntity;
import net.pneumono.umbrellas.content.item.UmbrellaItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(World.class)
public abstract class UmbrellaSkylightMixin implements WorldAccess {
    @Shadow public abstract @Nullable BlockEntity getBlockEntity(BlockPos pos);

    @Override
    public boolean isSkyVisible(BlockPos pos) {
        return WorldAccess.super.isSkyVisible(pos) && !isUnderUmbrella(pos);
    }

    @Unique
    public boolean isUnderUmbrella(BlockPos pos) {
        int areaWidth = 2;
        int startY = 0;
        int endY = 10;

        Box box = new Box(new Vec3d(pos.getX() - areaWidth, pos.getY() + startY, pos.getZ() - areaWidth), new Vec3d(pos.getX() + areaWidth, pos.getY() + endY, pos.getZ() + areaWidth));
        for (Entity temp : getOtherEntities(null, box)) {
            if (temp instanceof LivingEntity friend && PneumonoMathHelper.horizontalDistanceBetween(friend.getBlockPos(), pos) <= 2) {
                ItemStack friendMainHandStack = friend.getMainHandStack();
                ItemStack friendOffHandStack = friend.getOffHandStack();
                if (friendMainHandStack.getItem() instanceof UmbrellaItem) {
                    UmbrellaItem.damageUmbrella(friendMainHandStack, (World)(Object)this, friend, EquipmentSlot.MAINHAND);
                    return true;
                } else if (friendOffHandStack.getItem() instanceof UmbrellaItem) {
                    UmbrellaItem.damageUmbrella(friendOffHandStack, (World)(Object)this, friend, EquipmentSlot.OFFHAND);
                    return true;
                }
            }
        }
        for (int x = -areaWidth; x <= areaWidth; ++x) {
            for (int y = startY; y <= endY; ++y) {
                for (int z = -areaWidth; z <= areaWidth; ++z) {
                    BlockPos newPos = new BlockPos(pos.getX() + x, pos.getY() + y - 1, pos.getZ() + z);
                    if (getBlockEntity(newPos) instanceof UmbrellaStandBlockEntity blockEntity && blockEntity.hasStack() && PneumonoMathHelper.horizontalDistanceBetween(newPos, pos) <= 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
