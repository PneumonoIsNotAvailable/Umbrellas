package net.pneumono.umbrellas.util;

import net.minecraft.block.BlockState;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.function.Predicate;

public class ArmsUtil {
    public static boolean positionLeftArm(ModelPart leftArm, BipedEntityRenderState state) {
        return positionArm(leftArm, state, UmbrellaHoldingEntityRenderState::umbrellas$shouldAdjustLeftArm);
    }

    public static boolean positionRightArm(ModelPart rightArm, BipedEntityRenderState state) {
        return positionArm(rightArm, state, UmbrellaHoldingEntityRenderState::umbrellas$shouldAdjustRightArm);
    }

    public static boolean positionArm(ModelPart arm, BipedEntityRenderState state, Predicate<UmbrellaHoldingEntityRenderState> shouldAdjust) {
        if (!(state instanceof UmbrellaHoldingEntityRenderState umbrellaState)) return false;

        if (shouldAdjust.test(umbrellaState)) {
            arm.pitch = (float)Math.toRadians(180);
            return true;
        }
        return false;
    }

    public static boolean shouldAdjustArm(Arm arm, LivingEntity entity, UmbrellaHoldingEntityRenderState state) {
        ItemStack stack = entity.getStackInArm(arm);
        if (!stack.isIn(UmbrellasTags.UMBRELLAS)) return false;
        if (shouldAdjustArm(arm, state) && (entity.getVelocity().y > 0.1 || entity.getVelocity().y < -0.1)) return true;

        World world = entity.getWorld();
        BlockPos pos = entity.getBlockPos();

        for (int i = 0; i < 4; ++i) {
            BlockState blockState = world.getBlockState(pos.down(i));
            if (!blockState.getCollisionShape(world, pos).isEmpty()) {
                return false;
            }
        }

        int slowFallStrength = UmbrellaUtils.getSlowFallingStrength(stack, entity.getRandom());
        if (slowFallStrength > 0) {
            if (entity.fallDistance > Math.max(1.25 - (slowFallStrength * 0.25), 0)) return true;
        }
        if (UmbrellaUtils.hasSmokeBoosting(stack, entity.getRandom())) {
            if (UmbrellaUtils.isInSmoke(world, pos)) return true;
        }
        return entity.fallDistance > 1.25;
    }

    public static boolean shouldAdjustArm(Arm arm, UmbrellaHoldingEntityRenderState state) {
        return arm == Arm.LEFT ? state.umbrellas$shouldAdjustLeftArm() : state.umbrellas$shouldAdjustRightArm();
    }
}
