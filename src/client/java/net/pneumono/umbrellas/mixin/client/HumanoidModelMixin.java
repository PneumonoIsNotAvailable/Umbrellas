package net.pneumono.umbrellas.mixin.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.pneumono.umbrellas.util.ArmsUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=1.21.6 {
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
//?} else {
/*import net.minecraft.world.entity.LivingEntity;
*///?}

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin {
    @Shadow
    public @Final ModelPart rightArm;
    @Shadow
    public @Final ModelPart leftArm;

    @Inject(
            method = "poseLeftArm",
            at = @At("HEAD"),
            cancellable = true
    )
    //? if >=1.21.6 {
    public void leftArmUmbrella(HumanoidRenderState state/*? if <1.21.11 {*//*, HumanoidModel.ArmPose armPose*//*?}*/, CallbackInfo ci) {
        if (ArmsUtil.positionLeftArm(this.leftArm, state)) ci.cancel();
    }
    //?} else {
    /*public void leftArmUmbrella(LivingEntity entity, CallbackInfo ci) {
        if (ArmsUtil.positionLeftArm(this.leftArm, entity)) ci.cancel();
    }
    *///?}

    @Inject(
            method = "poseRightArm",
            at = @At("HEAD"),
            cancellable = true
    )
    //? if >=1.21.6 {
    public void rightArmUmbrella(HumanoidRenderState state/*? if <1.21.11 {*//*, HumanoidModel.ArmPose armPose*//*?}*/, CallbackInfo ci) {
        if (ArmsUtil.positionRightArm(this.rightArm, state)) ci.cancel();
    }
    //?} else {
    /*public void rightArmUmbrella(LivingEntity entity, CallbackInfo ci) {
        if (ArmsUtil.positionRightArm(this.rightArm, entity)) ci.cancel();
    }
    *///?}
}
