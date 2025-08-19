package net.pneumono.umbrellas.content.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.pneumono.umbrellas.UmbrellasConfig;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import net.pneumono.umbrellas.registry.UmbrellasItems;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class UmbrellaItem extends Item {
    public UmbrellaItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return super.hasGlint(stack) && UmbrellasConfig.ENCHANTMENT_GLINT.getValue();
    }

    @Override
    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        ItemStack oldWithoutIgnored = oldStack.copy();
        oldWithoutIgnored.remove(UmbrellasDataComponents.LAST_DAMAGE);
        oldWithoutIgnored.remove(DataComponentTypes.DAMAGE);
        ItemStack newWithoutIgnored = oldStack.copy();
        newWithoutIgnored.remove(UmbrellasDataComponents.LAST_DAMAGE);
        newWithoutIgnored.remove(DataComponentTypes.DAMAGE);
        return !ItemStack.areItemsAndComponentsEqual(oldWithoutIgnored, newWithoutIgnored);
    }

    // Very scuffed, only exists temporarily as a backwards compatibility feature
    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);
        UmbrellaPatternsComponent component = stack.get(UmbrellasDataComponents.UMBRELLA_PATTERNS);
        if (component == null) return;
        DyeColor color = createBackwardsCompatColorMap().get(stack.getItem());
        if (color != null) {
            stack.set(UmbrellasDataComponents.UMBRELLA_PATTERNS, new UmbrellaPatternsComponent(color, component.layers()));
        }
    }
    
    private static Map<Item, DyeColor> createBackwardsCompatColorMap() {
        return Map.ofEntries(
                Map.entry(UmbrellasItems.WHITE_UMBRELLA, DyeColor.WHITE),
                Map.entry(UmbrellasItems.ORANGE_UMBRELLA, DyeColor.ORANGE),
                Map.entry(UmbrellasItems.MAGENTA_UMBRELLA, DyeColor.MAGENTA),
                Map.entry(UmbrellasItems.LIGHT_BLUE_UMBRELLA, DyeColor.LIGHT_BLUE),
                Map.entry(UmbrellasItems.YELLOW_UMBRELLA, DyeColor.YELLOW),
                Map.entry(UmbrellasItems.LIME_UMBRELLA, DyeColor.LIME),
                Map.entry(UmbrellasItems.PINK_UMBRELLA, DyeColor.PINK),
                Map.entry(UmbrellasItems.GRAY_UMBRELLA, DyeColor.GRAY),
                Map.entry(UmbrellasItems.LIGHT_GRAY_UMBRELLA, DyeColor.LIGHT_GRAY),
                Map.entry(UmbrellasItems.CYAN_UMBRELLA, DyeColor.CYAN),
                Map.entry(UmbrellasItems.PURPLE_UMBRELLA, DyeColor.PURPLE),
                Map.entry(UmbrellasItems.BLUE_UMBRELLA, DyeColor.BLUE),
                Map.entry(UmbrellasItems.BROWN_UMBRELLA, DyeColor.BROWN),
                Map.entry(UmbrellasItems.GREEN_UMBRELLA, DyeColor.GREEN),
                Map.entry(UmbrellasItems.RED_UMBRELLA, DyeColor.RED),
                Map.entry(UmbrellasItems.BLACK_UMBRELLA, DyeColor.BLACK)
        );
    }
}