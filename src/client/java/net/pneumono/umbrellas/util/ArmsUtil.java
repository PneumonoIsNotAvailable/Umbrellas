package net.pneumono.umbrellas.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.pneumono.umbrellas.registry.UmbrellasTags;

//? if >=1.21.6
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

public class ArmsUtil {
    //? if >=1.21.6 {
    public static boolean positionLeftArm(ModelPart leftArm, HumanoidRenderState state) {
        return positionArm(HumanoidArm.LEFT, leftArm, state);
    }

    public static boolean positionRightArm(ModelPart rightArm, HumanoidRenderState state) {
        return positionArm(HumanoidArm.RIGHT, rightArm, state);
    }

    public static boolean positionArm(HumanoidArm arm, ModelPart modelPart, HumanoidRenderState state) {
        if (!(state instanceof UmbrellaHoldingEntityRenderState umbrellaState)) return false;

        if (umbrellaState.shouldAdjustArm(arm)) {
            modelPart.xRot = (float)Math.toRadians(180);
            return true;
        }
        return false;
    }
    //?} else {
    /*public static boolean positionLeftArm(ModelPart leftArm, LivingEntity entity) {
        return positionArm(HumanoidArm.LEFT, leftArm, entity);
    }

    public static boolean positionRightArm(ModelPart rightArm, LivingEntity entity) {
        return positionArm(HumanoidArm.RIGHT, rightArm, entity);
    }

    public static boolean positionArm(HumanoidArm arm, ModelPart modelPart, LivingEntity entity) {
        if (ArmsUtil.shouldAdjustArm(arm, entity)) {
            modelPart.xRot = (float)Math.toRadians(180);
            return true;
        }
        return false;
    }
    *///?}

    public static boolean shouldAdjustArm(HumanoidArm arm, LivingEntity entity) {
        ItemStack stack = entity.getMainArm() == arm ? entity.getMainHandItem() : entity.getOffhandItem();
        if (!stack.is(UmbrellasTags.UMBRELLAS)) return false;

        int slowFallStrength = UmbrellaUtils.getSlowFallingStrength(stack, entity.getRandom());
        if (entity.fallDistance > Math.max(4.25 - slowFallStrength, 0)) return true;

        return UmbrellaUtils.hasSmokeBoosting(stack, entity.getRandom()) && UmbrellaUtils.isVisuallyInSmoke(entity.level(), entity.blockPosition());
    }

    public static void adjustArm(PoseStack poseStack) {
        poseStack.mulPose(Axis.XP.rotationDegrees(200));
        poseStack.translate(0, 0.0625, 1);
    }
}
