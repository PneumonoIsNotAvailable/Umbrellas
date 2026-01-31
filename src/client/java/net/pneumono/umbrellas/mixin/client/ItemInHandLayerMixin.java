package net.pneumono.umbrellas.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.pneumono.umbrellas.util.ArmsUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
//?} else {
/*import net.minecraft.client.renderer.MultiBufferSource;
*///?}

//? if >=1.21.6 {
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.pneumono.umbrellas.util.UmbrellaHoldingEntityRenderState;
//?} else {
/*import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
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
    //? if >=1.21.11 {
    public void adjustMatrixForUmbrella(
            ArmedEntityRenderState entityState,
            ItemStackRenderState itemState, ItemStack stack,
            HumanoidArm arm, PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            int light, CallbackInfo ci
    ) {
        if (entityState instanceof UmbrellaHoldingEntityRenderState umbrellaState && umbrellaState.shouldAdjustArm(arm)) {
            ArmsUtil.adjustArm(poseStack);
        }
    }
    //?} else if >=1.21.9 {
    /*public void adjustMatrixForUmbrella(
            ArmedEntityRenderState entityState,
            ItemStackRenderState itemState,
            HumanoidArm arm, PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            int light, CallbackInfo ci
    ) {
        if (entityState instanceof UmbrellaHoldingEntityRenderState umbrellaState && umbrellaState.shouldAdjustArm(arm)) {
            ArmsUtil.adjustArm(poseStack);
        }
    }
    *///?} else if >=1.21.6 {
    /*public void adjustMatrixForUmbrella(
            ArmedEntityRenderState entityState,
            ItemStackRenderState itemState,
            HumanoidArm arm, PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int light, CallbackInfo ci
    ) {
        if (entityState instanceof UmbrellaHoldingEntityRenderState umbrellaState && umbrellaState.shouldAdjustArm(arm)) {
            ArmsUtil.adjustArm(poseStack);
        }
    }
    *///?} else {
    /*public void adjustMatrixForUmbrella(
            LivingEntity livingEntity,
            ItemStack itemStack,
            ItemDisplayContext itemDisplayContext,
            HumanoidArm arm, PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int i, CallbackInfo ci
    ) {
        if (ArmsUtil.shouldAdjustArm(arm, livingEntity)) {
            ArmsUtil.adjustArm(poseStack);
        }
    }
    *///?}
}
