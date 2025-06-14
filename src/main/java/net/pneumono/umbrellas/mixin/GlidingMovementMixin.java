package net.pneumono.umbrellas.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.registry.UmbrellasMisc;
import net.pneumono.umbrellas.registry.UmbrellasTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class GlidingMovementMixin extends Entity implements Attackable {
    public GlidingMovementMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract ItemStack getMainHandStack();

    @Shadow public abstract void remove(RemovalReason reason);

    @Inject(method = "getEffectiveGravity", at = @At("RETURN"), cancellable = true)
    private void applyUmbrellaGravity(CallbackInfoReturnable<Double> info) {
        double gravity = info.getReturnValue();
        int strength = Umbrellas.SLOW_FALLING.getValue().getStrength(getMainHandStack(), this.getRandom(), UmbrellasMisc.SLOW_FALLING, 0);
        if (this.getVelocity().y <= 0.0 && strength > 0) {
            gravity = Math.min(gravity, 0.04 - (0.01 * strength));
        }
        info.setReturnValue(gravity);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickSmokeBoost(CallbackInfo ci) {
        int strength = Umbrellas.SMOKE_BOOSTING.getValue().getStrength(getMainHandStack(), this.getRandom(), UmbrellasMisc.SMOKE_BOOSTING, 0);
        if (strength <= 0) {
            return;
        }

        World world = getWorld();
        BlockPos pos = getBlockPos();
        if (!isInSmoke(world, pos)) {
            return;
        }

        double entityVelocity = getVelocity().getY();

        double smokeVelocityCap = 0.1 * (Math.pow(2, strength - 1));
        double smokeVelocityBoost = 0.01 * (strength + 8);

        if (entityVelocity < smokeVelocityCap) {
            addVelocity(0, smokeVelocityBoost, 0);
        }
    }

    @Unique
    public boolean isInSmoke(World world, BlockPos pos) {
        for (int i = 0; i <= 19; ++i) {
            BlockPos blockpos = pos.down(i);
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.isIn(UmbrellasTags.BOOSTS_UMBRELLAS)) {
                if (i > 5 && CampfireBlock.isLitCampfire(blockstate) && blockstate.get(CampfireBlock.SIGNAL_FIRE)) {
                    return true;
                } else {
                    return i < 6;
                }
            }

            List<Box> collisionBoxes = blockstate.getCollisionShape(world, pos).getBoundingBoxes();
            boolean hasCollision = false;
            for (Box collisionBox : collisionBoxes) {
                if (collisionBox.intersects(new Box(0.375, 0, 0.625, 0.375, 1, 0.625))) {
                    hasCollision = true;
                    break;
                }
            }

            if (hasCollision) {
                return false;
            }
        }
        return false;
    }
}
