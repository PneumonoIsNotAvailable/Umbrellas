package net.pneumono.umbrellas.content;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pneumono.umbrellas.registry.UmbrellasMisc;

import java.util.Optional;

public class TimeGlidingCriterion extends AbstractCriterion<TimeGlidingCriterion.Conditions> {
    @Override
    public Codec<TimeGlidingCriterion.Conditions> getConditionsCodec() {
        return TimeGlidingCriterion.Conditions.CODEC;
    }

    public void trigger(ServerPlayerEntity player, ItemStack stack, double height) {
        super.trigger(player, conditions -> conditions.matches(stack, height));
    }

    public record Conditions(Optional<LootContextPredicate> player, Optional<ItemPredicate> item, NumberRange.DoubleRange height) implements AbstractCriterion.Conditions {
        public static Codec<TimeGlidingCriterion.Conditions> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(TimeGlidingCriterion.Conditions::player),
                ItemPredicate.CODEC.optionalFieldOf("item").forGetter(TimeGlidingCriterion.Conditions::item),
                NumberRange.DoubleRange.CODEC.optionalFieldOf("height", NumberRange.DoubleRange.ANY).forGetter(TimeGlidingCriterion.Conditions::height)
        ).apply(builder, TimeGlidingCriterion.Conditions::new));

        @Override
        public Optional<LootContextPredicate> player() {
            return this.player;
        }

        public static AdvancementCriterion<TimeGlidingCriterion.Conditions> create() {
            return UmbrellasMisc.TIME_GLIDING_CRITERION.create(new TimeGlidingCriterion.Conditions(Optional.empty(), Optional.empty(), NumberRange.DoubleRange.ANY));
        }

        public static AdvancementCriterion<TimeGlidingCriterion.Conditions> height(NumberRange.DoubleRange range) {
            return UmbrellasMisc.TIME_GLIDING_CRITERION.create(new TimeGlidingCriterion.Conditions(Optional.empty(), Optional.empty(), range));
        }

        public static AdvancementCriterion<TimeGlidingCriterion.Conditions> minHeight(double min) {
            return height(NumberRange.DoubleRange.atLeast(min));
        }

        public boolean matches(ItemStack stack, double height) {
            return (this.item.isEmpty() || this.item.get().test(stack)) && this.height.test(height);
        }
    }
}
