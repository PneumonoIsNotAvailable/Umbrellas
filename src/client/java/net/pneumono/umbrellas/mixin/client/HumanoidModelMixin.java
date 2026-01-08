package net.pneumono.umbrellas.mixin.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.pneumono.umbrellas.util.ArmsUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
    public void leftArmUmbrella(HumanoidRenderState state, HumanoidModel.ArmPose armPose, CallbackInfo ci) {
        if (ArmsUtil.positionLeftArm(this.leftArm, state)) ci.cancel();
    }

    @Inject(
            method = "poseRightArm",
            at = @At("HEAD"),
            cancellable = true
    )
    public void rightArmUmbrella(HumanoidRenderState state, HumanoidModel.ArmPose armPose, CallbackInfo ci) {
        if (ArmsUtil.positionRightArm(this.rightArm, state)) ci.cancel();
    }
}
