package net.pneumono.umbrellas.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.block.UmbrellaStandBlockEntity;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
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
    public static boolean isUnderUmbrella(World world, BlockPos pos, boolean damageUmbrellas) {
        int areaWidth = 2;
        int startY = 0;
        int endY = 10;

        for (int x = -areaWidth; x <= areaWidth; ++x) {
            for (int y = startY; y <= endY; ++y) {
                for (int z = -areaWidth; z <= areaWidth; ++z) {
                    BlockPos newPos = new BlockPos(pos.getX() + x, pos.getY() + y - 1, pos.getZ() + z);
                    if (world.getBlockEntity(newPos) instanceof UmbrellaStandBlockEntity blockEntity && blockEntity.hasStack() && newPos.getSquaredDistance(pos) <= areaWidth) {
                        return true;
                    }
                }
            }
        }

        int entityAreaWidth = 3;
        Box box = new Box(
                new Vec3d(pos.getX() - entityAreaWidth, pos.getY() + startY, pos.getZ() - entityAreaWidth),
                new Vec3d(pos.getX() + entityAreaWidth, pos.getY() + endY, pos.getZ() + entityAreaWidth)
        );

        for (Entity temp : world.getOtherEntities(null, box)) {
            if (temp instanceof LivingEntity friend && friend.getBlockPos().getSquaredDistance(pos) <= entityAreaWidth) {
                ItemStack friendMainHandStack = friend.getMainHandStack();
                if (friendMainHandStack.isIn(UmbrellasTags.UMBRELLAS)) {
                    if (damageUmbrellas) damageUmbrella(friendMainHandStack, 1, world, friend, EquipmentSlot.MAINHAND);
                    return true;
                }
                ItemStack friendOffHandStack = friend.getOffHandStack();
                if (friendOffHandStack.isIn(UmbrellasTags.UMBRELLAS)) {
                    if (damageUmbrellas) damageUmbrella(friendOffHandStack, 1, world, friend, EquipmentSlot.OFFHAND);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Damages item stacks if {@link Umbrellas#DURABILITY} is enabled, with a 20 tick cooldown.<p>
     */
    public static void damageUmbrella(ItemStack stack, int amount, World world, LivingEntity entity, EquipmentSlot slot) {
        if (!Umbrellas.DURABILITY.getValue()) return;

        long time = world.getTime();

        if (
                !stack.contains(UmbrellasDataComponents.LAST_DAMAGE)
                || stack.getOrDefault(UmbrellasDataComponents.LAST_DAMAGE, time) + 20 <= time
        ) {
            stack.set(UmbrellasDataComponents.LAST_DAMAGE, time);
            stack.damage(amount, entity, slot);
        }
    }

    /**
     * Returns whether the position has "smoke" (can be used with Billowing enchanted items to boost upwards)
     */
    public static boolean isInSmoke(World world, BlockPos blockPos) {
        for (int i = 0; i < 20; ++i) {
            BlockPos pos = blockPos.down(i);
            BlockState state = world.getBlockState(pos);

            if (state.isAir() || state.isIn(UmbrellasTags.SMOKE_PASSES_THROUGH)) continue;

            if (state.isIn(UmbrellasTags.BOOSTS_UMBRELLAS) && isNotUnlit(state)) {
                if (Umbrellas.STRICT_SMOKE_BOOSTING.getValue() && state.isIn(UmbrellasTags.UMBRELLA_BOOSTING_TOGGLEABLE)) {
                    return false;
                }

                if (i > 5 && CampfireBlock.isLitCampfire(state) && state.get(CampfireBlock.SIGNAL_FIRE)) {
                    return true;
                } else {
                    return i < 6;
                }
            }

            if (state.getCollisionShape(world, pos).isEmpty()) continue;

            List<Box> collisionBoxes = state.getCollisionShape(world, blockPos).getBoundingBoxes();
            boolean intersectsFirst = false;
            boolean intersectsSecond = false;
            boolean intersectsThird = false;
            boolean intersectsFourth = false;
            for (Box collisionBox : collisionBoxes) {
                if (collisionBox.intersects(new Box(0.25, 0, 0.25, 0.25, 1, 0.25))) {
                    intersectsFirst = true;
                }
                if (collisionBox.intersects(new Box(0.25, 0, 0.75, 0.25, 1, 0.75))) {
                    intersectsSecond = true;
                }
                if (collisionBox.intersects(new Box(0.75, 0, 0.25, 0.75, 1, 0.25))) {
                    intersectsThird = true;
                }
                if (collisionBox.intersects(new Box(0.75, 0, 0.75, 0.75, 1, 0.75))) {
                    intersectsFourth = true;
                }
            }

            if (intersectsFirst && intersectsSecond && intersectsThird && intersectsFourth) {
                return false;
            }
        }
        return false;
    }

    private static boolean isNotUnlit(BlockState state) {
        return !state.isIn(BlockTags.CAMPFIRES) || (state.contains(CampfireBlock.LIT) && state.get(CampfireBlock.LIT));
    }
}
