package net.pneumono.umbrellas.mixin.client;

import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HeldItemFeatureRenderer.class)
public abstract class HeldItemFeatureRendererMixin {
    /*
    @Inject(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"))
    public void adjustMatrixForUmbrella(LivingEntity entity, ItemStack stack, ModelTransformationMode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
        if (UmbrellasClient.shouldChangeArms(entity, arm)) {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(200));
            matrices.translate(0, 0.0625, 1);
        }
    }
     */
}
