package net.pneumono.umbrellas.content.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.pneumono.umbrellas.UmbrellasConfig;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import net.pneumono.umbrellas.util.data.VersionedComponents;

//? if <1.21.6 {
/*import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
*///?}

//? if >=1.21
import net.minecraft.core.component.DataComponents;

public class UmbrellaItem extends Item {
    public UmbrellaItem(Properties properties) {
        super(properties);
    }

    //? if <1.21.6 {
    /*@Override
    public void appendHoverText(ItemStack itemStack, /^? if >=1.21 {^/TooltipContext/^?} else {^//^@Nullable Level^//^?}^/ context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemStack, context, list, flag);
        UmbrellaPatternsComponent component = VersionedComponents.get(itemStack, UmbrellasDataComponents.UMBRELLA_PATTERNS);
        if (component != null) {
            component.addToTooltip(context, list::add, flag);
        }
    }
    *///?}

    @Override
    public boolean isFoil(ItemStack stack) {
        return super.isFoil(stack) && UmbrellasConfig.ENCHANTMENT_GLINT.getValue();
    }

    //? if >=1.21 {
    @Override
    public boolean allowComponentsUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        ItemStack oldWithoutIgnored = oldStack.copy();
        VersionedComponents.remove(oldWithoutIgnored, UmbrellasDataComponents.LAST_DAMAGE);
        oldWithoutIgnored.remove(DataComponents.DAMAGE);
        ItemStack newWithoutIgnored = newStack.copy();
        VersionedComponents.remove(newWithoutIgnored, UmbrellasDataComponents.LAST_DAMAGE);
        newWithoutIgnored.remove(DataComponents.DAMAGE);
        return !ItemStack.isSameItemSameComponents(oldWithoutIgnored, newWithoutIgnored);
    }
    //?} else {
    /*@Override
    public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        ItemStack oldWithoutIgnored = oldStack.copy();
        VersionedComponents.remove(oldWithoutIgnored, UmbrellasDataComponents.LAST_DAMAGE);
        oldWithoutIgnored.setDamageValue(0);
        ItemStack newWithoutIgnored = newStack.copy();
        VersionedComponents.remove(newWithoutIgnored, UmbrellasDataComponents.LAST_DAMAGE);
        newWithoutIgnored.setDamageValue(0);
        return !ItemStack.isSameItemSameTags(oldWithoutIgnored, newWithoutIgnored);
    }
    *///?}
}