package net.pneumono.umbrellas.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.pneumono.umbrellas.util.UmbrellaUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    public LivingEntityMixin(EntityType<?> type, World world) {
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

    @Inject(
            method = "tickMovement",
            at = @At("HEAD")
    )
    private void tickUmbrellaMovement(CallbackInfo ci) {
        UmbrellaUtils.tickSmokeBoost(this, getMainHandStack(), getOffHandStack());
        UmbrellaUtils.tickGlidingStats(this, getMainHandStack(), getOffHandStack());
    }

    @WrapOperation(
            method = "computeFallDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;getAttributeValue(Lnet/minecraft/registry/entry/RegistryEntry;)D"
            )
    )
    private double computeFallDamageWithUmbrellas(LivingEntity instance, RegistryEntry<EntityAttribute> attribute, Operation<Double> original) {
        return original.call(instance, attribute) * UmbrellaUtils.getUmbrellaFallDamageMultiplier(instance);
    }
}
