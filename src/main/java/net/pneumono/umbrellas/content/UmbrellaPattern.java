package net.pneumono.umbrellas.content;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.pneumono.umbrellas.registry.UmbrellaPatterns;

public record UmbrellaPattern(ResourceLocation assetId, String translationKey, boolean dyeable) {
    public static final Codec<UmbrellaPattern> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("asset_id").forGetter(UmbrellaPattern::assetId),
                    Codec.STRING.fieldOf("translation_key").forGetter(UmbrellaPattern::translationKey),
                    Codec.BOOL.fieldOf("dyeable").forGetter(UmbrellaPattern::dyeable)
            ).apply(instance, UmbrellaPattern::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, UmbrellaPattern> PACKET_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC,
            UmbrellaPattern::assetId,
            ByteBufCodecs.STRING_UTF8,
            UmbrellaPattern::translationKey,
            ByteBufCodecs.BOOL,
            UmbrellaPattern::dyeable,
            UmbrellaPattern::new
    );

    public static final Codec<Holder<UmbrellaPattern>> ENTRY_CODEC = RegistryFileCodec.create(UmbrellaPatterns.UMBRELLA_PATTERN_KEY, CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<UmbrellaPattern>> ENTRY_PACKET_CODEC = ByteBufCodecs.holder(
            UmbrellaPatterns.UMBRELLA_PATTERN_KEY, PACKET_CODEC
    );
}
