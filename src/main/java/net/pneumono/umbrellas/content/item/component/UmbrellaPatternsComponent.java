package net.pneumono.umbrellas.content.item.component;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.pneumono.umbrellas.content.UmbrellaPattern;

import java.util.List;
import java.util.function.Consumer;

public record UmbrellaPatternsComponent(DyeColor baseColor, List<Layer> layers) implements TooltipProvider {
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
    public static final StreamCodec<RegistryFriendlyByteBuf, UmbrellaPatternsComponent> STREAM_CODEC = StreamCodec.composite(
            DyeColor.STREAM_CODEC, UmbrellaPatternsComponent::baseColor,
            Layer.STREAM_CODEC.apply(ByteBufCodecs.list()), UmbrellaPatternsComponent::layers,
            UmbrellaPatternsComponent::new
    );

    public UmbrellaPatternsComponent withoutTopLayer() {
        return new UmbrellaPatternsComponent(this.baseColor, List.copyOf(this.layers.subList(0, this.layers.size() - 1)));
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag flag, DataComponentGetter getter) {
        for (int i = 0; i < Math.min(this.layers().size(), MAX_PATTERNS); i++) {
            textConsumer.accept(this.layers().get(i).getTooltipText().withStyle(ChatFormatting.GRAY));
        }
    }

    public static class Builder {
        private DyeColor baseColor = DEFAULT.baseColor();
        private final ImmutableList.Builder<Layer> entries = ImmutableList.builder();

        public UmbrellaPatternsComponent.Builder add(Holder<UmbrellaPattern> pattern, DyeColor color) {
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

    public record Layer(Holder<UmbrellaPattern> pattern, DyeColor color) {
        public static final Codec<Layer> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                UmbrellaPattern.ENTRY_CODEC.fieldOf("pattern").forGetter(Layer::pattern),
                                DyeColor.CODEC.fieldOf("color").forGetter(Layer::color)
                        )
                        .apply(instance, Layer::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, Layer> STREAM_CODEC = StreamCodec.composite(
                UmbrellaPattern.ENTRY_PACKET_CODEC,
                Layer::pattern,
                DyeColor.STREAM_CODEC,
                Layer::color,
                Layer::new
        );

        public MutableComponent getTooltipText() {
            String string = this.pattern.value().translationKey();
            if (this.pattern.value().dyeable()) {
                return Component.translatable(string + "." + this.color.getId());
            } else {
                return Component.translatable(string);
            }
        }
    }
}
