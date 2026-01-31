package net.pneumono.umbrellas.content.item;

import net.minecraft.world.item.Item;

//? if <1.21.6 {
/*import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.pneumono.umbrellas.content.item.component.ProvidesUmbrellaPatterns;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import java.util.List;
*///?}

public class UmbrellaPatternItem extends Item {
    public UmbrellaPatternItem(Properties properties) {
        super(properties);
    }

    //? if <1.21.6 {
    /*@Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemStack, context, list, flag);
        ProvidesUmbrellaPatterns component = itemStack.get(UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS);
        if (component != null) {
            component.addToTooltip(context, list::add, flag);
        }
    }
    *///?}
}
