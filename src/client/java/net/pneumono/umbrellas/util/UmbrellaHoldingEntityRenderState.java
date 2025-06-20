package net.pneumono.umbrellas.util;

public interface UmbrellaHoldingEntityRenderState {
    boolean umbrellas$shouldAdjustLeftArm();
    boolean umbrellas$shouldAdjustRightArm();
    void umbrellas$setShouldAdjustArm(boolean right, boolean hasUmbrella);
}
