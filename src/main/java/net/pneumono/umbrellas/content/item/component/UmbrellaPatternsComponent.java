package net.pneumono.umbrellas.content.item.component;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.patterns.UmbrellaPattern;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record UmbrellaPatternsComponent(List<Layer> layers) implements TooltipAppender {
    public static final UmbrellaPatternsComponent DEFAULT = new UmbrellaPatternsComponent(List.of());
    public static final Codec<UmbrellaPatternsComponent> CODEC = Layer.CODEC
            .listOf()
            .xmap(UmbrellaPatternsComponent::new, UmbrellaPatternsComponent::layers);
    public static final PacketCodec<RegistryByteBuf, UmbrellaPatternsComponent> PACKET_CODEC = Layer.PACKET_CODEC
            .collect(PacketCodecs.toList())
            .xmap(UmbrellaPatternsComponent::new, UmbrellaPatternsComponent::layers);

    public UmbrellaPatternsComponent withoutTopLayer() {
        return new UmbrellaPatternsComponent(List.copyOf(this.layers.subList(0, this.layers.size() - 1)));
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        for (int i = 0; i < Math.min(this.layers().size(), 6); i++) {
            textConsumer.accept(this.layers().get(i).getTooltipText().formatted(Formatting.GRAY));
        }
    }

    public static class Builder {
        private final ImmutableList.Builder<Layer> entries = ImmutableList.builder();

        @Deprecated
        public UmbrellaPatternsComponent.Builder add(RegistryEntryLookup<UmbrellaPattern> patternLookup, RegistryKey<UmbrellaPattern> pattern, DyeColor color) {
            Optional<RegistryEntry.Reference<UmbrellaPattern>> optional = patternLookup.getOptional(pattern);
            if (optional.isEmpty()) {
                Umbrellas.LOGGER.warn("Unable to find umbrella pattern with id: '{}'", pattern.getValue());
                return this;
            } else {
                return this.add(optional.get(), color);
            }
        }

        public UmbrellaPatternsComponent.Builder add(RegistryEntry<UmbrellaPattern> pattern, DyeColor color) {
            return this.add(new Layer(pattern, color));
        }

        public UmbrellaPatternsComponent.Builder add(Layer layer) {
            this.entries.add(layer);
            return this;
        }

        public UmbrellaPatternsComponent.Builder addAll(UmbrellaPatternsComponent patterns) {
            this.entries.addAll(patterns.layers);
            return this;
        }

        public UmbrellaPatternsComponent build() {
            return new UmbrellaPatternsComponent(this.entries.build());
        }
    }

    public record Layer(RegistryEntry<UmbrellaPattern> pattern, DyeColor color) {
        public static final Codec<Layer> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                UmbrellaPattern.ENTRY_CODEC.fieldOf("pattern").forGetter(Layer::pattern),
                                DyeColor.CODEC.fieldOf("color").forGetter(Layer::color)
                        )
                        .apply(instance, Layer::new)
        );
        public static final PacketCodec<RegistryByteBuf, Layer> PACKET_CODEC = PacketCodec.tuple(
                UmbrellaPattern.ENTRY_PACKET_CODEC,
                Layer::pattern,
                DyeColor.PACKET_CODEC,
                Layer::color,
                Layer::new
        );

        public MutableText getTooltipText() {
            String string = this.pattern.value().translationKey();
            return Text.translatable(string + "." + this.color.getId());
        }
    }
}
