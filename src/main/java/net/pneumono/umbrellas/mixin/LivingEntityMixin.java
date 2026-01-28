package net.pneumono.umbrellas.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.pneumono.umbrellas.util.UmbrellaUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=1.21.6 {
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
//?} else {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
*///?}

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    public LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Shadow public abstract ItemStack getMainHandItem();

    @Shadow public abstract ItemStack getOffhandItem();

    //? if >=1.21.6 {
    @ModifyReturnValue(
            method = "getEffectiveGravity",
            at = @At("RETURN")
    )
    //?} else {
    /*@ModifyExpressionValue(
            method = "travel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getGravity()D"
            )
    )
    *///?}
    private double applyUmbrellaGravity(double gravity) {
        return UmbrellaUtils.getEffectiveGravityWithUmbrellas(this, getMainHandItem(), getOffhandItem(), gravity);
    }

    @Inject(
            method = "aiStep",
            at = @At("HEAD")
    )
    private void tickUmbrellaMovement(CallbackInfo ci) {
        UmbrellaUtils.tickSmokeBoost(this, getMainHandItem(), getOffhandItem());
        UmbrellaUtils.tickGlidingStats(this, getMainHandItem(), getOffhandItem());
    }

    @WrapOperation(
            method = "calculateFallDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/core/Holder;)D"
            )
    )
    private double computeFallDamageWithUmbrellas(LivingEntity instance, Holder<Attribute> attribute, Operation<Double> original) {
        return original.call(instance, attribute) * UmbrellaUtils.getUmbrellaFallDamageMultiplier(instance);
    }
}
