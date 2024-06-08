package net.pneumono.umbrellas.mixin.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Arm;
import net.pneumono.pneumonocore.util.PneumonoMathHelper;
import net.pneumono.umbrellas.UmbrellasClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin {
    @Shadow
    public @Final ModelPart rightArm;
    @Shadow
    public @Final ModelPart leftArm;

    @Inject(method = "positionLeftArm", at = @At("HEAD"), cancellable = true)
    public void leftArmUmbrella(LivingEntity entity, CallbackInfo ci) {
        Arm mainArm = entity.getMainArm();
        if (UmbrellasClient.shouldChangeArms(entity, Arm.LEFT)) {
            this.leftArm.pitch = (float)PneumonoMathHelper.toRadians(180);
            ci.cancel();
        }
    }

    @Inject(method = "positionRightArm", at = @At("HEAD"), cancellable = true)
    public void rightArmUmbrella(LivingEntity entity, CallbackInfo ci) {
        Arm mainArm = entity.getMainArm();
        if (UmbrellasClient.shouldChangeArms(entity, Arm.RIGHT)) {
            this.rightArm.pitch = (float)PneumonoMathHelper.toRadians(180);
            ci.cancel();
        }
    }

    private boolean isFalling(LivingEntity entity) {
        return entity.fallDistance > 2;
    }
}
