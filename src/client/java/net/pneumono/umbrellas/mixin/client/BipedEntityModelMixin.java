package net.pneumono.umbrellas.mixin.client;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin {
    // why mojang why did you have to fuck up custom arm poses so badly

    /*
    @Shadow
    public @Final ModelPart rightArm;
    @Shadow
    public @Final ModelPart leftArm;

    @Inject(method = "positionLeftArm", at = @At("HEAD"), cancellable = true)
    public void leftArmUmbrella(BipedEntityRenderState state, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {
        Arm mainArm = entity.getMainArm();
        if (UmbrellasClient.shouldChangeArms(entity, Arm.LEFT)) {
            this.leftArm.pitch = (float)PneumonoMathHelper.toRadians(180);
            ci.cancel();
        }
    }

    @Inject(method = "positionRightArm", at = @At("HEAD"), cancellable = true)
    public void rightArmUmbrella(BipedEntityRenderState state, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {
        Arm mainArm = entity.getMainArm();
        if (UmbrellasClient.shouldChangeArms(entity, Arm.RIGHT)) {
            this.rightArm.pitch = (float)PneumonoMathHelper.toRadians(180);
            ci.cancel();
        }
    }

    private boolean isFalling(LivingEntity entity) {
        return entity.fallDistance > 2;
    }
     */
}
