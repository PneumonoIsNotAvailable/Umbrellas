package net.pneumono.umbrellas.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FireworkRocketItem;
import net.pneumono.umbrellas.UmbrellasConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FireworkRocketItem.class)
public abstract class FireworkRocketItemMixin {
    @WrapOperation(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;isFallFlying()Z"
            )
    )
    public boolean isGlidingAndBoostingEnabled(Player instance, Operation<Boolean> original) {
        return !UmbrellasConfig.DISABLE_FIREWORK_BOOSTING.getValue() && original.call(instance);
    }
}
