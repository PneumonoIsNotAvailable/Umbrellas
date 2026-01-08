package net.pneumono.umbrellas.util;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.function.Predicate;

public class ArmsUtil {
    public static boolean positionLeftArm(ModelPart leftArm, HumanoidRenderState state) {
        return positionArm(leftArm, state, UmbrellaHoldingEntityRenderState::umbrellas$shouldAdjustLeftArm);
    }

    public static boolean positionRightArm(ModelPart rightArm, HumanoidRenderState state) {
        return positionArm(rightArm, state, UmbrellaHoldingEntityRenderState::umbrellas$shouldAdjustRightArm);
    }

    public static boolean positionArm(ModelPart arm, HumanoidRenderState state, Predicate<UmbrellaHoldingEntityRenderState> shouldAdjust) {
        if (!(state instanceof UmbrellaHoldingEntityRenderState umbrellaState)) return false;

        if (shouldAdjust.test(umbrellaState)) {
            arm.xRot = (float)Math.toRadians(180);
            return true;
        }
        return false;
    }

    public static boolean shouldAdjustArm(HumanoidArm arm, LivingEntity entity, UmbrellaHoldingEntityRenderState state) {
        ItemStack stack = entity.getItemHeldByArm(arm);
        if (!stack.is(UmbrellasTags.UMBRELLAS)) return false;
        if (shouldAdjustArm(arm, state) && (entity.getDeltaMovement().y > 0.1 || entity.getDeltaMovement().y < -0.1)) return true;

        Level level = entity.level();
        BlockPos pos = entity.blockPosition();

        for (int i = 0; i < 4; ++i) {
            BlockState blockState = level.getBlockState(pos.below(i));
            if (!blockState.getCollisionShape(level, pos).isEmpty()) {
                return false;
            }
        }

        int slowFallStrength = UmbrellaUtils.getSlowFallingStrength(stack, entity.getRandom());
        if (slowFallStrength > 0) {
            if (entity.fallDistance > Math.max(1.25 - (slowFallStrength * 0.25), 0)) return true;
        }
        if (UmbrellaUtils.hasSmokeBoosting(stack, entity.getRandom())) {
            if (UmbrellaUtils.isVisuallyInSmoke(level, pos)) return true;
        }
        return entity.fallDistance > 1.25;
    }

    public static boolean shouldAdjustArm(HumanoidArm arm, UmbrellaHoldingEntityRenderState state) {
        return arm == HumanoidArm.LEFT ? state.umbrellas$shouldAdjustLeftArm() : state.umbrellas$shouldAdjustRightArm();
    }
}
