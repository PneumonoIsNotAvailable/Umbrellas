package net.pneumono.umbrellas.content.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.pneumono.umbrellas.content.UmbrellaPattern;
import net.pneumono.umbrellas.registry.UmbrellaPatterns;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.function.Consumer;

//? if >=1.21.6
import net.minecraft.core.component.DataComponentGetter;

public record ProvidesUmbrellaPatterns(TagKey<UmbrellaPattern> patterns, boolean requiresDye) implements TooltipProvider {
    public static final ProvidesUmbrellaPatterns DEFAULT = new ProvidesUmbrellaPatterns(UmbrellasTags.NO_ITEM_REQUIRED, true);

    public static final Codec<ProvidesUmbrellaPatterns> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            TagKey.codec(UmbrellaPatterns.UMBRELLA_PATTERN_KEY).fieldOf("patterns").forGetter(ProvidesUmbrellaPatterns::patterns),
            Codec.BOOL.optionalFieldOf("requires_dye", true).forGetter(ProvidesUmbrellaPatterns::requiresDye)
    ).apply(builder, ProvidesUmbrellaPatterns::new));

    @Override
    public void addToTooltip(
            Item.TooltipContext context,
            Consumer<Component> textConsumer,
            TooltipFlag flag
            /*? if >=1.21.6 {*/, DataComponentGetter getter/*?}*/
    ) {
        if (!this.requiresDye) {
            textConsumer.accept(Component.translatable("item.umbrellas.umbrella_pattern.no_dye").withStyle(ChatFormatting.GRAY));
        }
    }
}
