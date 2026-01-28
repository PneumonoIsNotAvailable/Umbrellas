package net.pneumono.umbrellas.mixin.client;

//? if >=1.21.6 {
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

@SuppressWarnings("AddedMixinMembersNamePattern")
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
        umbrellaState.setShouldAdjustArm(false, ArmsUtil.shouldAdjustArm(HumanoidArm.LEFT, entity));
        umbrellaState.setShouldAdjustArm(true, ArmsUtil.shouldAdjustArm(HumanoidArm.RIGHT, entity));
    }

    @Override
    public boolean shouldAdjustLeftArm() {
        return this.shouldAdjustLeftArm;
    }

    @Override
    public boolean shouldAdjustRightArm() {
        return this.shouldAdjustRightArm;
    }

    @Override
    public void setShouldAdjustArm(boolean right, boolean hasUmbrella) {
        if (right) {
            this.shouldAdjustRightArm = hasUmbrella;
        } else {
            this.shouldAdjustLeftArm = hasUmbrella;
        }
    }
}
//?}
