package net.pneumono.umbrellas.util;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
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
        if (stack.isIn(UmbrellasTags.UMBRELLAS)) {
            return entity.fallDistance > 2;
        }
        return false;
    }

    public static boolean shouldAdjustArm(Arm arm, UmbrellaHoldingEntityRenderState state) {
        return arm == Arm.LEFT ? state.umbrellas$shouldAdjustLeftArm() : state.umbrellas$shouldAdjustRightArm();
    }
}
