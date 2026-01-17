package net.pneumono.umbrellas.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.pneumono.umbrellas.util.ArmsUtil;
import net.pneumono.umbrellas.util.UmbrellaHoldingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
//?} else {
/*import net.minecraft.client.renderer.MultiBufferSource;
*///?}

@Mixin(ItemInHandLayer.class)
public abstract class ItemInHandLayerMixin {
    @Inject(
            method = /*? if >=1.21.9 {*/"submitArmWithItem"/*?} else {*//*"renderArmWithItem"*//*?}*/,
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"
            )
    )
    public void adjustMatrixForUmbrella(
            ArmedEntityRenderState entityState,
            ItemStackRenderState itemState/*? if >=1.21.11 {*/, ItemStack stack/*?}*/,
            HumanoidArm arm, PoseStack poseStack,
            /*? if >=1.21.9 {*/SubmitNodeCollector submitNodeCollector/*?} else {*//*MultiBufferSource multiBufferSource*//*?}*/,
            int light, CallbackInfo ci
    ) {
        if (entityState instanceof UmbrellaHoldingEntityRenderState umbrellaState && ArmsUtil.shouldAdjustArm(arm, umbrellaState)) {
            poseStack.mulPose(Axis.XP.rotationDegrees(200));
            poseStack.translate(0, 0.0625, 1);
        }
    }
}
