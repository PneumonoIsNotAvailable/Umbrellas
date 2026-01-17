package net.pneumono.umbrellas.mixin.client;

import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
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
            method = "extractArmedEntityRenderState",
            at = @At("RETURN")
    )
    private static void updateRenderStateWithUmbrella(LivingEntity entity, ArmedEntityRenderState state, ItemModelResolver itemModelResolver/*? if >=1.21.11 {*/, float f/*?}*/, CallbackInfo ci) {
        if (!(state instanceof UmbrellaHoldingEntityRenderState umbrellaState)) return;
        umbrellaState.umbrellas$setShouldAdjustArm(false, ArmsUtil.shouldAdjustArm(HumanoidArm.LEFT, entity, umbrellaState));
        umbrellaState.umbrellas$setShouldAdjustArm(true, ArmsUtil.shouldAdjustArm(HumanoidArm.RIGHT, entity, umbrellaState));
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
