package net.pneumono.umbrellas.mixin.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.pneumono.umbrellas.util.ArmsUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin {
    @Shadow
    public @Final ModelPart rightArm;
    @Shadow
    public @Final ModelPart leftArm;

    @Inject(method = "positionLeftArm", at = @At("HEAD"), cancellable = true)
    public void leftArmUmbrella(BipedEntityRenderState state, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {
        if (ArmsUtil.positionLeftArm(this.leftArm, state)) ci.cancel();
    }

    @Inject(method = "positionRightArm", at = @At("HEAD"), cancellable = true)
    public void rightArmUmbrella(BipedEntityRenderState state, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {
        if (ArmsUtil.positionRightArm(this.rightArm, state)) ci.cancel();
    }
}
