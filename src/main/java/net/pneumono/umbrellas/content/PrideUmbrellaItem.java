package net.pneumono.umbrellas.content;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.pneumono.umbrellas.Umbrellas;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PrideUmbrellaItem extends UmbrellaItem {
    private final String umbrellaIdentity;
    private final DyeColor[] dyeColors;
    private final int[] stripes;
    private String translationKey;

    public PrideUmbrellaItem(Settings settings, String identity, DyeColor[] dyeColors, int... stripes) {
        super(settings);
        this.umbrellaIdentity = identity;
        this.dyeColors = dyeColors;
        this.stripes = stripes;
    }

    @Override
    protected String getOrCreateTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = "item." + Umbrellas.MOD_ID + ".umbrella_pride";
        }

        return this.translationKey;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip." + Umbrellas.MOD_ID + ".umbrella_pride." + umbrellaIdentity).formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, context);
    }

    public DyeColor[] getDyeColors() {
        return dyeColors;
    }

    public int getStripe(int stripe) {
        if (stripe >= stripes.length) {
            return -1;
        } else {
            return stripes[stripe];
        }
    }
}