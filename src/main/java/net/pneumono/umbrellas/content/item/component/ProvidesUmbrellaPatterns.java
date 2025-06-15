package net.pneumono.umbrellas.content.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.pneumono.umbrellas.patterns.UmbrellaPattern;
import net.pneumono.umbrellas.registry.UmbrellaPatterns;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.function.Consumer;

public record ProvidesUmbrellaPatterns(TagKey<UmbrellaPattern> patterns, boolean requiresDye) implements TooltipAppender {
    public static final ProvidesUmbrellaPatterns DEFAULT = new ProvidesUmbrellaPatterns(UmbrellasTags.NO_ITEM_REQUIRED, true);

    public static final Codec<ProvidesUmbrellaPatterns> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            TagKey.codec(UmbrellaPatterns.UMBRELLA_PATTERN_KEY).fieldOf("patterns").forGetter(ProvidesUmbrellaPatterns::patterns),
            Codec.BOOL.optionalFieldOf("requires_dye", true).forGetter(ProvidesUmbrellaPatterns::requiresDye)
    ).apply(builder, ProvidesUmbrellaPatterns::new));

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        if (!this.requiresDye) {
            textConsumer.accept(Text.translatable("item.umbrellas.umbrella_pattern.no_dye").formatted(Formatting.GRAY));
        }
    }
}
