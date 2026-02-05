package net.pneumono.umbrellas.content.item.component;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.TooltipFlag;
import net.pneumono.umbrellas.content.UmbrellaPattern;

import java.util.List;
import java.util.function.Consumer;

//? if >=1.21.6
import net.minecraft.core.component.DataComponentGetter;

//? if >=1.21 {
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.TooltipProvider;
//?} else {
/*import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
*///?}

public record UmbrellaPatternsComponent(List<Layer> layers) /*? if >=1.21 {*/implements TooltipProvider/*?}*/ {
    public static final UmbrellaPatternsComponent DEFAULT = new UmbrellaPatternsComponent(List.of());
    public static final int MAX_PATTERNS = 8;
    public static final Codec<UmbrellaPatternsComponent> CODEC = Layer.CODEC.listOf().xmap(UmbrellaPatternsComponent::new, UmbrellaPatternsComponent::layers);
    //? if >=1.21 {
    public static final StreamCodec<RegistryFriendlyByteBuf, UmbrellaPatternsComponent> STREAM_CODEC = StreamCodec.composite(
            Layer.STREAM_CODEC.apply(ByteBufCodecs.list()), UmbrellaPatternsComponent::layers,
            UmbrellaPatternsComponent::new
    );
    //?}

    public UmbrellaPatternsComponent withoutTopLayer() {
        return new UmbrellaPatternsComponent(List.copyOf(this.layers.subList(0, this.layers.size() - 1)));
    }

    //? if >=1.21
    @Override
    public void addToTooltip(
            /*? if >=1.21 {*/Item.TooltipContext/*?} else {*//*@Nullable Level*//*?}*/ context,
            Consumer<Component> textConsumer,
            TooltipFlag flag
            /*? if >=1.21.6 {*/, DataComponentGetter getter/*?}*/
    ) {
        for (int i = 0; i < Math.min(this.layers().size(), MAX_PATTERNS); i++) {
            textConsumer.accept(this.layers().get(i).getTooltipText().withStyle(ChatFormatting.GRAY));
        }
    }

    public static class Builder {
        private final ImmutableList.Builder<Layer> entries = ImmutableList.builder();

        public UmbrellaPatternsComponent.Builder add(Holder<UmbrellaPattern> pattern, DyeColor color) {
            return this.add(new Layer(pattern, color));
        }

        public UmbrellaPatternsComponent.Builder add(Layer layer) {
            this.entries.add(layer);
            return this;
        }

        public UmbrellaPatternsComponent.Builder copy(UmbrellaPatternsComponent patterns) {
            this.entries.addAll(patterns.layers);
            return this;
        }

        public UmbrellaPatternsComponent build() {
            return new UmbrellaPatternsComponent(this.entries.build());
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
        //? if >=1.21 {
        public static final StreamCodec<RegistryFriendlyByteBuf, Layer> STREAM_CODEC = StreamCodec.composite(
                UmbrellaPattern.ENTRY_PACKET_CODEC,
                Layer::pattern,
                DyeColor.STREAM_CODEC,
                Layer::color,
                Layer::new
        );
        //?}

        public MutableComponent getTooltipText() {
            String string = this.pattern.value().translationKey();
            if (this.pattern.value().dyeable()) {
                return Component.translatable(string + "." + this.color.getName());
            } else {
                return Component.translatable(string);
            }
        }
    }
}
