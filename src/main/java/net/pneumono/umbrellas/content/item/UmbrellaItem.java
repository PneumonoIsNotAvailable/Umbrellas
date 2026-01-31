package net.pneumono.umbrellas.content.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.pneumono.umbrellas.UmbrellasConfig;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import net.pneumono.umbrellas.registry.UmbrellasItems;

import java.util.Map;

//? if >=1.21.6 {
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.Nullable;
//?} else {
/*import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import java.util.List;
*///?}

public class UmbrellaItem extends Item {
    public UmbrellaItem(Properties properties) {
        super(properties);
    }

    //? if <1.21.6 {
    /*@Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemStack, context, list, flag);
        UmbrellaPatternsComponent component = itemStack.get(UmbrellasDataComponents.UMBRELLA_PATTERNS);
        if (component != null) {
            component.addToTooltip(context, list::add, flag);
        }
    }
    *///?}

    @Override
    public boolean isFoil(ItemStack stack) {
        return super.isFoil(stack) && UmbrellasConfig.ENCHANTMENT_GLINT.getValue();
    }

    @Override
    public boolean allowComponentsUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        ItemStack oldWithoutIgnored = oldStack.copy();
        oldWithoutIgnored.remove(UmbrellasDataComponents.LAST_DAMAGE);
        oldWithoutIgnored.remove(DataComponents.DAMAGE);
        ItemStack newWithoutIgnored = oldStack.copy();
        newWithoutIgnored.remove(UmbrellasDataComponents.LAST_DAMAGE);
        newWithoutIgnored.remove(DataComponents.DAMAGE);
        return !ItemStack.isSameItemSameComponents(oldWithoutIgnored, newWithoutIgnored);
    }

    // Very scuffed, only exists temporarily as a backwards compatibility feature
    //? if >=1.21.6 {
    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, level, entity, slot);
    //?} else {
    /*@Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean bl) {
        super.inventoryTick(stack, level, entity, i, bl);
    *///?}
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