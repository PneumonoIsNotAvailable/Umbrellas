package net.pneumono.umbrellas.content.item;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

//? if >=1.21 {
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import java.util.List;
//?}

public class PatternableUmbrellaItem extends UmbrellaItem {
    public final DyeColor color;

    public PatternableUmbrellaItem(DyeColor color, Properties properties) {
        //? if >=1.21 {
        super(properties.component(UmbrellasDataComponents.UMBRELLA_PATTERNS.getType(), new UmbrellaPatternsComponent(List.of())));
        //?} else {
        /*super(properties);
        *///?}
        this.color = color;
    }

    public DyeColor getColor() {
        return color;
    }

    public static DyeColor getColor(ItemStack stack) {
        return stack.getItem() instanceof PatternableUmbrellaItem umbrella ? umbrella.getColor() : DyeColor.WHITE;
    }
}
