package net.pneumono.umbrellas.content.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.tag.TagKey;
import net.pneumono.umbrellas.patterns.UmbrellaPattern;
import net.pneumono.umbrellas.registry.UmbrellasPatterns;

public record ProvidesUmbrellaPatterns(TagKey<UmbrellaPattern> patterns, boolean requiresDye) {
    public static final Codec<ProvidesUmbrellaPatterns> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            TagKey.codec(UmbrellasPatterns.UMBRELLA_PATTERN_KEY).fieldOf("patterns").forGetter(ProvidesUmbrellaPatterns::patterns),
            Codec.BOOL.optionalFieldOf("requiresDye", true).forGetter(ProvidesUmbrellaPatterns::requiresDye)
    ).apply(builder, ProvidesUmbrellaPatterns::new));
}
