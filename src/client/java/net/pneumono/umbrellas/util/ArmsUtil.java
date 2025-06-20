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

    public static boolean shouldAdjustArm(Arm arm, LivingEntity entity) {
        ItemStack stack = entity.getStackInArm(arm);
        if (!stack.isIn(UmbrellasTags.UMBRELLAS)) return false;

        World world = entity.getWorld();
        BlockPos pos = entity.getBlockPos();

        for (int i = 0; i < 2; ++i) {
            BlockState state = world.getBlockState(pos.down(i));
            if (!state.getCollisionShape(world, pos).isEmpty()) {
                return false;
            }
        }

        if (UmbrellaUtils.getSlowFallingStrength(stack, entity.getRandom()) > 0) {
            if (entity.fallDistance > 1.25) return true;
        }
        if (UmbrellaUtils.getSmokeBoostingStrength(stack, entity.getRandom()) > 0) {
            if (UmbrellaUtils.isInSmoke(world, pos)) return true;
        }
        return entity.fallDistance > 2.25;
    }

    public static boolean shouldAdjustArm(Arm arm, UmbrellaHoldingEntityRenderState state) {
        return arm == Arm.LEFT ? state.umbrellas$shouldAdjustLeftArm() : state.umbrellas$shouldAdjustRightArm();
    }
}
