package net.pneumono.umbrellas.patterns;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UmbrellaPatternItem extends Item {
    private final TagKey<UmbrellaPattern> patternItemTag;
    private final String tooltipTranslationKey;

    public UmbrellaPatternItem(Settings settings, TagKey<UmbrellaPattern> patternItemTag, String tooltipTranslationKey) {
        super(settings);
        this.patternItemTag = patternItemTag;
        this.tooltipTranslationKey = tooltipTranslationKey;
    }

    @Override
    public String getTranslationKey() {
        return "item.umbrellas.umbrella_pattern";
    }

    public TagKey<UmbrellaPattern> getPatternItemTag() {
        return patternItemTag;
    }

    public String getTooltipTranslationKey() {
        return tooltipTranslationKey;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(this.getTooltipTranslationKey()).formatted(Formatting.GRAY));
    }
}
