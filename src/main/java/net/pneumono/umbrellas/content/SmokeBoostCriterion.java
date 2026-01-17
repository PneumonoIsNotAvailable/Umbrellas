package net.pneumono.umbrellas.content;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.pneumono.umbrellas.registry.UmbrellasMisc;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SmokeBoostCriterion extends SimpleCriterionTrigger<SmokeBoostCriterion.TriggerInstance> {
    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack stack, int height) {
        super.trigger(player, triggerInstance -> triggerInstance.matches(stack, height));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item, MinMaxBounds.Doubles height) implements SimpleCriterionTrigger.SimpleInstance {
        public static Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                ItemPredicate.CODEC.optionalFieldOf("item").forGetter(TriggerInstance::item),
                MinMaxBounds.Doubles.CODEC.optionalFieldOf("height", MinMaxBounds.Doubles.ANY).forGetter(TriggerInstance::height)
        ).apply(builder, TriggerInstance::new));

        @Override
        public @NotNull Optional<ContextAwarePredicate> player() {
            return this.player;
        }

        public static Criterion<TriggerInstance> create() {
            return UmbrellasMisc.SMOKE_BOOST_CRITERION.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), MinMaxBounds.Doubles.ANY));
        }

        public static Criterion<TriggerInstance> height(MinMaxBounds.Doubles bounds) {
            return UmbrellasMisc.SMOKE_BOOST_CRITERION.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), bounds));
        }

        public static Criterion<TriggerInstance> minHeight(int min) {
            return height(MinMaxBounds.Doubles.atLeast(min));
        }

        public boolean matches(ItemStack stack, int height) {
            return (this.item.isEmpty() || this.item.get().test(stack)) && this.height.matches(height);
        }
    }
}
