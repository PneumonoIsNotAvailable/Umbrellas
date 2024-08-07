package net.pneumono.umbrellas.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.UmbrellaItem;
import net.pneumono.umbrellas.content.UmbrellasRegistry;
import net.pneumono.umbrellas.util.WindCatchingAbilityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LivingEntity.class)
@SuppressWarnings("unused")
public abstract class GlidingMovementMixin extends Entity implements Attackable {
    public GlidingMovementMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract ItemStack getMainHandStack();

    @WrapOperation(method = {"tickMovement", "travel"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z", ordinal = 0))
    private boolean tickGlidingMovement(LivingEntity instance, StatusEffect effect, Operation<Boolean> original) {
        ItemStack activeItem = instance.getMainHandStack();
        if (effect == StatusEffects.SLOW_FALLING && Umbrellas.SLOW_FALLING.getValue().shouldHaveAbility(activeItem)) {
            return true;
        } else {
            return original.call(instance, effect);
        }
    }

    // This is dumb
    @ModifyVariable(method = "travel", at = @At(value = "STORE", ordinal = 0))
    private double port_lib$modifyGravity(double original) {
        if (Umbrellas.CREATE_LOADED && Umbrellas.SLOW_FALLING.getValue().shouldHaveAbility(this.getMainHandStack())) {
            return original * 0.125;
        }
        return original;
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickWindCatchingBoost(CallbackInfo ci) {
        ItemStack stack = getMainHandStack();
        WindCatchingAbilityType type = Umbrellas.SMOKE_BOOSTING.getValue();
        if (type.shouldHaveAbility(stack)) {
            World world = getWorld();
            int level = type == WindCatchingAbilityType.ALWAYS ? 3 : EnchantmentHelper.getLevel(UmbrellasRegistry.WIND_CATCHING, stack);
            BlockPos pos = getBlockPos();
            if (stack.getItem() instanceof UmbrellaItem && level >= 1 && isInSmoke(world, pos)) {
                double entityVelocity = getVelocity().getY();

                double smokeVelocityCap = 0.1 * (Math.pow(2, level - 1));
                double smokeVelocityBoost = 0.01 * (level + 8);

                if (entityVelocity < smokeVelocityCap) {
                    addVelocity(0, smokeVelocityBoost, 0);
                }
            }
        }
    }

    public boolean isInSmoke(World world, BlockPos pos) {
        for (int i = 0; i <= 19; ++i) {
            BlockPos blockpos = pos.down(i);
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.isIn(UmbrellasRegistry.TAG_BOOSTS_UMBRELLAS)) {
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
