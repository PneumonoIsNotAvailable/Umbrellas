package net.pneumono.umbrellas.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pneumono.umbrellas.util.UmbrellaUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class GlidingMovementMixin extends Entity implements Attackable {
    public GlidingMovementMixin(EntityType<?> type, World world) {
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
        ItemStack stack = getMainHandStack();
        int strength = UmbrellaUtils.getSlowFallingStrength(stack, getRandom());
        if (strength == 0) {
            stack = getOffHandStack();
            strength = UmbrellaUtils.getSlowFallingStrength(stack, getRandom());
        }

        if (this.getVelocity().y <= 0.0 && strength > 0) {
            gravity = Math.min(
                    gravity,
                    0.04 - 0.01 * (
                            (4F * strength * strength * strength) / ((strength * strength * strength) + 2.24F)
                    )
            );
        }
        return gravity;
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickSmokeBoost(CallbackInfo ci) {
        ItemStack stack = getMainHandStack();
        int strength = UmbrellaUtils.getSmokeBoostingStrength(stack, getRandom());
        if (strength == 0) {
            stack = getOffHandStack();
            strength = UmbrellaUtils.getSmokeBoostingStrength(stack, getRandom());
        }

        if (strength <= 0) {
            return;
        }

        World world = getWorld();
        BlockPos pos = getBlockPos();
        if (!UmbrellaUtils.isInSmoke(world, pos)) {
            return;
        }

        double entityVelocity = getVelocity().getY();

        double smokeVelocityCap = 0.1 * (Math.pow(2, strength - 1));
        double smokeVelocityBoost = 0.01 * (strength + 8);

        if (entityVelocity < smokeVelocityCap) {
            addVelocity(0, smokeVelocityBoost, 0);
        }
    }
}
