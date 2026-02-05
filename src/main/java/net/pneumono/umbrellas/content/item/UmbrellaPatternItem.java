package net.pneumono.umbrellas.content.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.pneumono.umbrellas.content.item.component.ProvidesUmbrellaPatterns;

//? if <1.21.6 {
/*import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
*///?}

//? if >=1.21 {
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import net.pneumono.umbrellas.util.data.VersionedComponents;
//?}

public class UmbrellaPatternItem extends Item {
    //? if <1.21
    //private final ProvidesUmbrellaPatterns provided;

    public UmbrellaPatternItem(ProvidesUmbrellaPatterns provided, Properties properties) {
        //? if >=1.21 {
        super(properties.component(UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS.getType(), provided));
        //?} else {
        /*super(properties);
        this.provided = provided;
        *///?}
    }

    //? if <1.21.6 {
    /*@Override
    public void appendHoverText(ItemStack stack, /^? if >=1.21 {^/TooltipContext/^?} else {^//^@Nullable Level^//^?}^/ context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(stack, context, list, flag);
        ProvidesUmbrellaPatterns component = getProvided(stack);
        if (component != null) {
            component.addToTooltip(context, list::add, flag);
        }
    }
    *///?}

    public static boolean canProvide(ItemStack stack) {
        //? if >=1.21 {
        return VersionedComponents.has(stack, UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS);
         //?} else {
        /*return stack.getItem() instanceof UmbrellaPatternItem;
        *///?}
    }

    public static ProvidesUmbrellaPatterns getProvided(ItemStack stack) {
        //? if >=1.21 {
        return VersionedComponents.getOrDefault(stack, UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS, ProvidesUmbrellaPatterns.DEFAULT);
        //?} else {
        /*if (stack.getItem() instanceof UmbrellaPatternItem item) {
            return item.provided;
        } else {
            return null;
        }
        *///?}
    }

    public static ProvidesUmbrellaPatterns getProvidedOrDefault(ItemStack stack) {
        //? if >=1.21 {
        return VersionedComponents.getOrDefault(stack, UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS, ProvidesUmbrellaPatterns.DEFAULT);
         //?} else {
        /*if (stack.getItem() instanceof UmbrellaPatternItem item) {
            return item.provided;
        } else {
            return ProvidesUmbrellaPatterns.DEFAULT;
        }
        *///?}
    }
}
