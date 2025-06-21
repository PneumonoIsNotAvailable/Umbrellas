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

public class SmokeBoostCriterion extends AbstractCriterion<SmokeBoostCriterion.Conditions> {
    @Override
    public Codec<Conditions> getConditionsCodec() {
        return Conditions.CODEC;
    }

    public void trigger(ServerPlayerEntity player, ItemStack stack, int height) {
        super.trigger(player, conditions -> conditions.matches(stack, height));
    }

    public record Conditions(Optional<LootContextPredicate> player, Optional<ItemPredicate> item, NumberRange.IntRange height) implements AbstractCriterion.Conditions {
        public static Codec<Conditions> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(Conditions::player),
                ItemPredicate.CODEC.optionalFieldOf("item").forGetter(Conditions::item),
                NumberRange.IntRange.CODEC.optionalFieldOf("height", NumberRange.IntRange.ANY).forGetter(Conditions::height)
        ).apply(builder, Conditions::new));

        @Override
        public Optional<LootContextPredicate> player() {
            return this.player;
        }

        public static AdvancementCriterion<Conditions> create() {
            return UmbrellasMisc.SMOKE_BOOST_CRITERION.create(new Conditions(Optional.empty(), Optional.empty(), NumberRange.IntRange.ANY));
        }

        public static AdvancementCriterion<Conditions> height(NumberRange.IntRange range) {
            return UmbrellasMisc.SMOKE_BOOST_CRITERION.create(new Conditions(Optional.empty(), Optional.empty(), range));
        }

        public static AdvancementCriterion<Conditions> minHeight(int min) {
            return height(NumberRange.IntRange.atLeast(min));
        }

        public boolean matches(ItemStack stack, int height) {
            return (this.item.isEmpty() || this.item.get().test(stack)) && this.height.test(height);
        }
    }
}
