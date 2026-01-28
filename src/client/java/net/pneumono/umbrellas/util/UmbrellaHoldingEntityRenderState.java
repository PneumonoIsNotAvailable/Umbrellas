package net.pneumono.umbrellas.util;

//? if >=1.21.6 {
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Unique;

public interface UmbrellaHoldingEntityRenderState {
    @Unique
    boolean shouldAdjustLeftArm();
    @Unique
    boolean shouldAdjustRightArm();
    @Unique
    void setShouldAdjustArm(boolean right, boolean hasUmbrella);

    default boolean shouldAdjustArm(HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? shouldAdjustLeftArm() : shouldAdjustRightArm();
    }
}
//?}
