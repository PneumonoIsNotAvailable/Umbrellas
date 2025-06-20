package net.pneumono.umbrellas.mixin.client;

import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Arm;
import net.pneumono.umbrellas.util.UmbrellaHoldingEntityRenderState;
import net.pneumono.umbrellas.util.ArmsUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmedEntityRenderState.class)
public abstract class ArmedEntityRenderStateMixin implements UmbrellaHoldingEntityRenderState {
    @Unique
    public boolean shouldAdjustLeftArm = false;
    @Unique
    public boolean shouldAdjustRightArm = false;

    @Inject(
            method = "updateRenderState",
            at = @At("RETURN")
    )
    private static void updateRenderStateWithUmbrella(LivingEntity entity, ArmedEntityRenderState state, ItemModelManager itemModelManager, CallbackInfo ci) {
        if (!(state instanceof UmbrellaHoldingEntityRenderState umbrellaState)) return;
        umbrellaState.umbrellas$setShouldAdjustArm(false, ArmsUtil.shouldAdjustArm(Arm.LEFT, entity));
        umbrellaState.umbrellas$setShouldAdjustArm(true, ArmsUtil.shouldAdjustArm(Arm.RIGHT, entity));
    }

    @Override
    public boolean umbrellas$shouldAdjustLeftArm() {
        return this.shouldAdjustLeftArm;
    }

    @Override
    public boolean umbrellas$shouldAdjustRightArm() {
        return this.shouldAdjustRightArm;
    }

    @Override
    public void umbrellas$setShouldAdjustArm(boolean right, boolean hasUmbrella) {
        if (right) {
            this.shouldAdjustRightArm = hasUmbrella;
        } else {
            this.shouldAdjustLeftArm = hasUmbrella;
        }
    }
}
