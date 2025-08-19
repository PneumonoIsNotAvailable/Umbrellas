package net.pneumono.umbrellas.content.item.component;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.pneumono.umbrellas.content.UmbrellaPattern;

import java.util.List;
import java.util.function.Consumer;

public record UmbrellaPatternsComponent(DyeColor baseColor, List<Layer> layers) implements TooltipAppender {
    public static final UmbrellaPatternsComponent DEFAULT = new UmbrellaPatternsComponent(DyeColor.GRAY, List.of());
    public static final int MAX_PATTERNS = 8;
    public static final Codec<UmbrellaPatternsComponent> MAIN_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            DyeColor.CODEC.fieldOf("baseColor").forGetter(UmbrellaPatternsComponent::baseColor),
            Layer.CODEC.listOf().fieldOf("layers").forGetter(UmbrellaPatternsComponent::layers)
    ).apply(instance, UmbrellaPatternsComponent::new));
    public static final Codec<UmbrellaPatternsComponent> COMPAT_CODEC = Layer.CODEC.listOf().xmap(layerList -> new UmbrellaPatternsComponent(DyeColor.WHITE, layerList), UmbrellaPatternsComponent::layers);
    public static final Codec<UmbrellaPatternsComponent> CODEC = Codec.either(MAIN_CODEC, COMPAT_CODEC).xmap(either -> {
        if (either.left().isPresent()) return either.left().get();
        if (either.right().isPresent()) return either.right().get();
        return null;
    }, Either::left);
    public static final PacketCodec<RegistryByteBuf, UmbrellaPatternsComponent> PACKET_CODEC = PacketCodec.tuple(
            DyeColor.PACKET_CODEC, UmbrellaPatternsComponent::baseColor,
            Layer.PACKET_CODEC.collect(PacketCodecs.toList()), UmbrellaPatternsComponent::layers,
            UmbrellaPatternsComponent::new
    );

    public UmbrellaPatternsComponent withoutTopLayer() {
        return new UmbrellaPatternsComponent(this.baseColor, List.copyOf(this.layers.subList(0, this.layers.size() - 1)));
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        for (int i = 0; i < Math.min(this.layers().size(), MAX_PATTERNS); i++) {
            textConsumer.accept(this.layers().get(i).getTooltipText().formatted(Formatting.GRAY));
        }
    }

    public static class Builder {
        private DyeColor baseColor = DEFAULT.baseColor();
        private final ImmutableList.Builder<Layer> entries = ImmutableList.builder();

        public UmbrellaPatternsComponent.Builder add(RegistryEntry<UmbrellaPattern> pattern, DyeColor color) {
            return this.add(new Layer(pattern, color));
        }

        public UmbrellaPatternsComponent.Builder add(Layer layer) {
            this.entries.add(layer);
            return this;
        }

        public UmbrellaPatternsComponent.Builder copy(UmbrellaPatternsComponent patterns) {
            this.baseColor = patterns.baseColor;
            this.entries.addAll(patterns.layers);
            return this;
        }

        public UmbrellaPatternsComponent build() {
            return new UmbrellaPatternsComponent(this.baseColor, this.entries.build());
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
            if (this.pattern.value().dyeable()) {
                return Text.translatable(string + "." + this.color.getId());
            } else {
                return Text.translatable(string);
            }
        }
    }
}
