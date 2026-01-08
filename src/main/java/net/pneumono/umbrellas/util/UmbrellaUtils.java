package net.pneumono.umbrellas.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.pneumono.umbrellas.UmbrellasConfig;
import net.pneumono.umbrellas.content.block.UmbrellaStandBlockEntity;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import net.pneumono.umbrellas.registry.UmbrellasEnchantments;
import net.pneumono.umbrellas.registry.UmbrellasMisc;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.List;

public class UmbrellaUtils {
    /**
     * Checks to see if the position is sheltered beneath an umbrella.<p>
     * Damaged umbrellas have a cooldown of 20 ticks before they can be damaged a second time,
     * so calling this method repeatedly with {@code damageUmbrellas = true} is fine.<p>
     * The shelter radius around umbrellas is intentionally much larger than expected,
     * because otherwise fitting under there (especially when moving with another player) would be really annoying.<p>
     */
    public static boolean isUnderUmbrella(Level level, BlockPos pos, boolean damageUmbrellas) {
        int areaWidth = 2;
        int startY = 0;
        int endY = 10;

        for (int x = -areaWidth; x <= areaWidth; ++x) {
            for (int y = startY; y <= endY; ++y) {
                for (int z = -areaWidth; z <= areaWidth; ++z) {
                    BlockPos newPos = new BlockPos(pos.getX() + x, pos.getY() + y - 1, pos.getZ() + z);
                    if (level.getBlockEntity(newPos) instanceof UmbrellaStandBlockEntity blockEntity && blockEntity.hasStack() && newPos.distSqr(pos) <= areaWidth) {
                        return true;
                    }
                }
            }
        }

        int entityAreaWidth = 3;
        AABB box = new AABB(
                new Vec3(pos.getX() - entityAreaWidth, pos.getY() + startY, pos.getZ() - entityAreaWidth),
                new Vec3(pos.getX() + entityAreaWidth, pos.getY() + endY, pos.getZ() + entityAreaWidth)
        );

        for (Entity temp : level.getEntities(null, box)) {
            if (temp instanceof LivingEntity friend && friend.getOnPos().distSqr(pos) <= entityAreaWidth) {
                ItemStack friendMainHandStack = friend.getMainHandItem();
                if (friendMainHandStack.is(UmbrellasTags.UMBRELLAS)) {
                    if (damageUmbrellas) damageUmbrella(friendMainHandStack, 1, level, friend, EquipmentSlot.MAINHAND);
                    return true;
                }
                ItemStack friendOffHandStack = friend.getOffhandItem();
                if (friendOffHandStack.is(UmbrellasTags.UMBRELLAS)) {
                    if (damageUmbrellas) damageUmbrella(friendOffHandStack, 1, level, friend, EquipmentSlot.OFFHAND);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Damages item stacks if {@link UmbrellasConfig#DURABILITY} is enabled, with a 20 tick cooldown.<p>
     */
    public static void damageUmbrella(ItemStack stack, int amount, Level level, LivingEntity entity, EquipmentSlot slot) {
        if (!UmbrellasConfig.DURABILITY.getValue()) return;

        long time = level.getGameTime();

        if (
                !stack.has(UmbrellasDataComponents.LAST_DAMAGE)
                || stack.getOrDefault(UmbrellasDataComponents.LAST_DAMAGE, time) + 20 <= time
        ) {
            stack.set(UmbrellasDataComponents.LAST_DAMAGE, time);
            stack.hurtAndBreak(amount, entity, slot);
        }
    }

    /**
     * Returns whether the position is in a "smoke column" (can be used with Billowing enchanted items to boost upwards).
     */
    @SuppressWarnings("unused")
    public static boolean isInSmoke(Level level, BlockPos blockPos) {
        return getHeightInSmoke(level, blockPos) > 0;
    }

    /**
     * Returns whether the position is in a "smoke column" (can be used with Billowing enchanted items to boost upwards).<p>
     * This method checks an additional 2 blocks below where {@link UmbrellaUtils#isInSmoke(Level, BlockPos)} checks.
     */
    public static boolean isVisuallyInSmoke(Level level, BlockPos blockPos) {
        return getHeightInSmoke(level, blockPos, 2, false) > 0;
    }

    /**
     * Returns how high up the player is in a "smoke column" (can be used with Billowing enchanted items to boost upwards).<p>
     * Returns {@code -1} if the player is not in a smoke column.
     */
    public static int getHeightInSmoke(Level level, BlockPos blockPos) {
        return getHeightInSmoke(level, blockPos, 0, false);
    }

    /**
     * Returns how high up the player is in a "smoke column" (can be used with Billowing enchanted items to boost upwards).<p>
     * Returns {@code -1} if the player is not in a smoke column.<p>
     * Checks a number of blocks equal to {@code lenience} further than by default.
     * Will only accept blocks in
     */
    public static int getHeightInSmoke(Level level, BlockPos blockPos, int lenience, boolean usingElytra) {
        for (int i = 0; i < 20 + lenience; ++i) {
            BlockPos pos = blockPos.below(i);
            BlockState state = level.getBlockState(pos);

            if (state.isAir() || state.is(UmbrellasTags.SMOKE_PASSES_THROUGH)) continue;

            if (state.is(usingElytra ? UmbrellasTags.BOOSTS_ELYTRA : UmbrellasTags.BOOSTS_UMBRELLAS) && isNotUnlit(state)) {
                if (UmbrellasConfig.STRICT_SMOKE_BOOSTING.getValue() && state.is(UmbrellasTags.UMBRELLA_BOOSTING_TOGGLEABLE)) {
                    return -1;
                }

                if (i > 5 && CampfireBlock.isLitCampfire(state) && state.getValue(CampfireBlock.SIGNAL_FIRE)) {
                    return i;
                } else {
                    return i < 6 ? i : -1;
                }
            }

            if (state.getCollisionShape(level, pos).isEmpty()) continue;

            List<AABB> collisionBoxes = state.getCollisionShape(level, blockPos).toAabbs();
            boolean intersectsFirst = false;
            boolean intersectsSecond = false;
            boolean intersectsThird = false;
            boolean intersectsFourth = false;
            for (AABB collisionBox : collisionBoxes) {
                if (collisionBox.intersects(new AABB(0.25, 0, 0.25, 0.25, 1, 0.25))) {
                    intersectsFirst = true;
                }
                if (collisionBox.intersects(new AABB(0.25, 0, 0.75, 0.25, 1, 0.75))) {
                    intersectsSecond = true;
                }
                if (collisionBox.intersects(new AABB(0.75, 0, 0.25, 0.75, 1, 0.25))) {
                    intersectsThird = true;
                }
                if (collisionBox.intersects(new AABB(0.75, 0, 0.75, 0.75, 1, 0.75))) {
                    intersectsFourth = true;
                }
            }

            if (intersectsFirst && intersectsSecond && intersectsThird && intersectsFourth) {
                return -1;
            }
        }
        return -1;
    }

    private static boolean isNotUnlit(BlockState state) {
        return !state.is(BlockTags.CAMPFIRES) || (state.hasProperty(CampfireBlock.LIT) && state.getValue(CampfireBlock.LIT));
    }

    /**
     * Calls {@link UmbrellaUtils#getSlowFallingStrength(ItemStack, RandomSource)} for {@code first}, and if that returns {@code 0}, calls it for {@code second} instead;
     * @see UmbrellaUtils#getSlowFallingStrength(ItemStack, RandomSource)
     */
    public static int getSlowFallingStrength(ItemStack first, ItemStack second, RandomSource random) {
        int strength = UmbrellaUtils.getSlowFallingStrength(first, random);
        if (strength == 0) {
            strength = UmbrellaUtils.getSlowFallingStrength(second, random);
        }
        return strength;
    }

    /**
     * Depending on what the {@link UmbrellasConfig#SLOW_FALLING} config is set to, returns the "strength" of an Umbrella's slow falling.<p>
     * When set to {@link EnchantmentAbilityType#ALWAYS}, returns {@code 3}.<p>
     * When set to {@link EnchantmentAbilityType#ENCHANTED_ONLY}, returns the sum of the levels of all slow falling enchantments.
     * (Usually, this is just the Gliding level)<p>
     * When set to {@link EnchantmentAbilityType#NEVER}, returns {@code 0}.<p>
     */
    public static int getSlowFallingStrength(ItemStack stack, RandomSource random) {
        return UmbrellasConfig.SLOW_FALLING.getValue().getStrength(stack, random, UmbrellasEnchantments.SLOW_FALLING, 3, 0);
    }

    /**
     * Depending on what the {@link UmbrellasConfig#SMOKE_BOOSTING} config is set to, returns whether an Umbrella can use smoke boosting.<p>
     * When set to {@link EnchantmentAbilityType#ALWAYS}, returns true.<p>
     * When set to {@link EnchantmentAbilityType#ENCHANTED_ONLY}, returns whether the item has a smoke boosting enchantment.
     * (Usually, this is just Billowing)<p>
     * When set to {@link EnchantmentAbilityType#NEVER}, returns false.<p>
     */
    public static boolean hasSmokeBoosting(ItemStack stack, RandomSource random) {
        return UmbrellasConfig.SMOKE_BOOSTING.getValue().getStrength(stack, random, UmbrellasEnchantments.SMOKE_BOOSTING, 1, 0) > 0;
    }

    public static double getEffectiveGravityWithUmbrellas(Entity entity, ItemStack mainhand, ItemStack offhand, double baseGravity) {
        int strength = getSlowFallingStrength(mainhand, offhand, entity.getRandom());

        if (strength > 0 && entity.getDeltaMovement().y() <= 0.0) {
            return Math.min(
                    baseGravity,
                    0.04 - 0.01 * (
                            (4F * strength * strength * strength) / ((strength * strength * strength) + 2.24F)
                    )
            );
        }
        return baseGravity;
    }

    public static float getUmbrellaFallDamageMultiplier(LivingEntity entity) {
        int strength = getSlowFallingStrength(entity.getMainHandItem(), entity.getOffhandItem(), entity.getRandom());
        if (strength == 0) return 1;

        switch (strength) {
            case 1 -> {
                return 0.666667F;
            }
            case 2 -> {
                return 0.333333F;
            }
            default -> {
                return 0;
            }
        }
    }

    public static void tickSmokeBoost(Entity entity, ItemStack mainhand, ItemStack offhand) {
        boolean usingMainHand = false;
        boolean usingUmbrella = false;
        boolean shouldTick = false;
        if (UmbrellaUtils.hasSmokeBoosting(mainhand, entity.getRandom())) {
            usingMainHand = true;
            usingUmbrella = true;
            shouldTick = true;
        } else if (UmbrellaUtils.hasSmokeBoosting(offhand, entity.getRandom())) {
            usingUmbrella = true;
            shouldTick = true;
        } else if (UmbrellasConfig.ELYTRA_SMOKE_BOOSTING.getValue() && entity instanceof LivingEntity living && living.isFallFlying()) {
            shouldTick = true;
        }

        if (!shouldTick) return;

        Level level = entity.level();
        BlockPos pos = entity.getOnPos();
        int heightInSmoke = UmbrellaUtils.getHeightInSmoke(level, pos, 0, !usingUmbrella);
        if (heightInSmoke == -1) {
            return;
        }

        if (usingUmbrella && entity instanceof ServerPlayer serverPlayer) {
            UmbrellasMisc.SMOKE_BOOST_CRITERION.trigger(serverPlayer, usingMainHand ? mainhand : offhand, heightInSmoke);
        }

        double entityVelocity = entity.getDeltaMovement().y() * 100;
        boolean isGliding = entity instanceof LivingEntity living && living.isFallFlying();

        double smokeVelocityBoost = 0.01 * (
                Math.min(
                        Math.pow((isGliding ? 20 : 2.5), -(entityVelocity - 8)),
                        (isGliding ? 100 : 20)
                ) + 8
        );

        entity.push(0, smokeVelocityBoost, 0);
    }

    public static void tickGlidingStats(Entity entity, ItemStack mainhand, ItemStack offhand) {
        if (
                !entity.onGround()
                && !(entity instanceof LivingEntity living && living.isFallFlying())
                && entity.getDeltaMovement().y() < -0.1
                && entity instanceof Player player
        ) {
            boolean usingMainHand = true;
            int strength = getSlowFallingStrength(mainhand, player.getRandom());
            if (strength == 0) {
                usingMainHand = false;
                strength = getSlowFallingStrength(offhand, player.getRandom());
            }
            if (strength == 0) return;

            player.awardStat(UmbrellasMisc.TIME_UMBRELLA_GLIDING);
            if (player instanceof ServerPlayer serverPlayer) {
                UmbrellasMisc.TIME_GLIDING_CRITERION.trigger(serverPlayer, usingMainHand ? mainhand : offhand, entity.fallDistance);
            }
        }
    }
}
