package net.pneumono.umbrellas.patterns;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.pneumono.umbrellas.registry.UmbrellaPatterns;

public record UmbrellaPattern(Identifier assetId, String translationKey, boolean dyeable) {
    public static final Codec<UmbrellaPattern> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.fieldOf("asset_id").forGetter(UmbrellaPattern::assetId),
                    Codec.STRING.fieldOf("translation_key").forGetter(UmbrellaPattern::translationKey),
                    Codec.BOOL.fieldOf("dyeable").forGetter(UmbrellaPattern::dyeable)
            ).apply(instance, UmbrellaPattern::new)
    );
    public static final PacketCodec<RegistryByteBuf, UmbrellaPattern> PACKET_CODEC = PacketCodec.tuple(
            Identifier.PACKET_CODEC,
            UmbrellaPattern::assetId,
            PacketCodecs.STRING,
            UmbrellaPattern::translationKey,
            PacketCodecs.BOOLEAN,
            UmbrellaPattern::dyeable,
            UmbrellaPattern::new
    );

    public static final Codec<RegistryEntry<UmbrellaPattern>> ENTRY_CODEC = RegistryElementCodec.of(UmbrellaPatterns.UMBRELLA_PATTERN_KEY, CODEC);
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<UmbrellaPattern>> ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(
            UmbrellaPatterns.UMBRELLA_PATTERN_KEY, PACKET_CODEC
    );
}
