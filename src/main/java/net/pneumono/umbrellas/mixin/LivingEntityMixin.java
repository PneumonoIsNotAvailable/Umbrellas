package net.pneumono.umbrellas.mixin;

import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.pneumono.umbrellas.util.UmbrellaUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=1.21.6 {
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
//?} else if >=1.21 {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
*///?} else {
/*import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
*///?}

//? if >=1.21 {

//?}

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
    //?} else if >=1.21 {
    /*@ModifyExpressionValue(
            method = "travel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getGravity()D"
            )
    )
    *///?} else {
    /*@ModifyConstant(
            method = "travel",
            constant = @Constant(doubleValue = 0.08, ordinal = 0)
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

    @ModifyArg(
            method = "calculateFallDamage",
            at = @At(
                    value = "INVOKE",
                    //? if >=1.21.6 {
                    target = "Lnet/minecraft/util/Mth;floor(D)I"
                    //?} else if >=1.21 {
                    /*target = "Lnet/minecraft/util/Mth;ceil(D)I"
                    *///?} else {
                    /*target = "Lnet/minecraft/util/Mth;ceil(F)I"
                    *///?}
            )
    )
    private /*? if >=1.21 {*/double/*?} else {*//*float*//*?}*/ calculateFallDamageWithUmbrellas(/*? if >=1.21 {*/double/*?} else {*//*float*//*?}*/ fallDamage) {
        return (float) UmbrellaUtils.modifyFallDamage((LivingEntity)(Object)this, fallDamage);
    }
}
