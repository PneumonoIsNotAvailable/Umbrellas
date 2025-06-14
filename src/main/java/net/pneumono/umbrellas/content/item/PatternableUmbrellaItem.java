package net.pneumono.umbrellas.content.item;

import net.minecraft.util.DyeColor;

public class PatternableUmbrellaItem extends UmbrellaItem {
    private final DyeColor baseColor;

    public PatternableUmbrellaItem(Settings settings, DyeColor baseColor) {
        super(settings);
        this.baseColor = baseColor;
    }

    public DyeColor getColor() {
        return baseColor;
    }
}
