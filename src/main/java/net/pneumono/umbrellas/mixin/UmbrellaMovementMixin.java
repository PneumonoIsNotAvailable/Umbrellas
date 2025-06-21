package net.pneumono.umbrellas.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.pneumono.umbrellas.util.UmbrellaUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class UmbrellaMovementMixin extends Entity implements Attackable {
    public UmbrellaMovementMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract ItemStack getMainHandStack();
    @Shadow
    public abstract ItemStack getOffHandStack();

    @ModifyReturnValue(
            method = "getEffectiveGravity",
            at = @At("RETURN")
    )
    private double applyUmbrellaGravity(double gravity) {
        return UmbrellaUtils.getEffectiveGravityWithUmbrellas(this, getMainHandStack(), getOffHandStack(), gravity);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickSmokeBoost(CallbackInfo ci) {
        UmbrellaUtils.tickSmokeBoost(this, getMainHandStack(), getOffHandStack());
    }
}
