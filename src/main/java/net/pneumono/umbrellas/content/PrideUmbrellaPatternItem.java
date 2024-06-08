package net.pneumono.umbrellas.content;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.pneumono.umbrellas.patterns.UmbrellaPattern;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PrideUmbrellaPatternItem extends UmbrellaPatternItem {
    public PrideUmbrellaPatternItem(Settings settings, TagKey<UmbrellaPattern> patternItemTag, String tooltipTranslationKey) {
        super(settings, patternItemTag, tooltipTranslationKey);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.umbrellas.no_dye").formatted(Formatting.GRAY));
    }
}
