package net.pneumono.umbrellas.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireworkRocketItem;
import net.pneumono.umbrellas.UmbrellasConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FireworkRocketItem.class)
public abstract class FireworkRocketItemMixin {
    @WrapOperation(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isGliding()Z"
            )
    )
    public boolean isGlidingAndBoostingEnabled(PlayerEntity instance, Operation<Boolean> original) {
        return !UmbrellasConfig.DISABLE_FIREWORK_BOOSTING.getValue() && original.call(instance);
    }
}
