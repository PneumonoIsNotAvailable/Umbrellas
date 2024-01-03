package net.pneumono.umbrellas.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.AbilityType;
import net.pneumono.umbrellas.content.UmbrellaItem;
import net.pneumono.umbrellas.content.UmbrellasContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LivingEntity.class)
@SuppressWarnings("unused")
public abstract class GlidingMovementMixin {
    @WrapOperation(method = {"tickMovement", "travel"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z", ordinal = 0))
    private boolean tickGlidingMovement(LivingEntity instance, StatusEffect effect, Operation<Boolean> original) {
        ItemStack activeItem = instance.getMainHandStack();
        if (Umbrellas.SLOW_FALLING.getValue().shouldHaveAbility(activeItem)) {
            return true;
        } else {
            return original.call(instance, effect);
        }
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickGlidingBoost(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object) this;
        ItemStack stack = entity.getMainHandStack();
        AbilityType type = Umbrellas.CAMPFIRE_BOOSTING.getValue();
        if (type.shouldHaveAbility(stack)) {
            World world = entity.getWorld();
            int level = type == AbilityType.ALWAYS ? 3 : EnchantmentHelper.getLevel(UmbrellasContent.GLIDING, stack);
            BlockPos pos = entity.getBlockPos();
            if (stack.getItem() instanceof UmbrellaItem && level >= 1 && isInCampfireSmoke(world, pos)) {
                double entityVelocity = entity.getVelocity().getY();

                double campfireVelocityCap = 0.1 * (Math.pow(2, level - 1));
                double campfireVelocityBoost = 0.01 * (level + 8);

                if (entityVelocity < campfireVelocityCap) {
                    entity.addVelocity(0, campfireVelocityBoost, 0);
                }
            }
        }
    }

    public boolean isInCampfireSmoke(World world, BlockPos pos) {
        for (int i = 0; i <= 19; ++i) {
            BlockPos blockpos = pos.down(i);
            BlockState blockstate = world.getBlockState(blockpos);
            if (CampfireBlock.isLitCampfire(blockstate)) {
                if (i > 5 && blockstate.get(CampfireBlock.SIGNAL_FIRE)) {
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
