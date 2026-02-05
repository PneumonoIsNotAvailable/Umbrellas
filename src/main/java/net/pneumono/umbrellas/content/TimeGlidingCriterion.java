//~ identifier_replacements

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

//? if <1.21 {
/*import net.minecraft.resources.Identifier;
import com.google.gson.JsonObject;
import net.pneumono.umbrellas.Umbrellas;
*///?}

public class TimeGlidingCriterion extends SimpleCriterionTrigger<TimeGlidingCriterion.TriggerInstance> {
    //? if <1.21
    //public static final Identifier ID = Umbrellas.id("time_gliding");

    //? if >=1.21 {
    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }
    //?}

    public void trigger(ServerPlayer player, ItemStack stack, double height) {
        super.trigger(player, triggerInstance -> triggerInstance.matches(stack, height));
    }

    //? if <1.21 {
    /*@Override
    protected TriggerInstance createInstance(JsonObject jsonObject, ContextAwarePredicate contextAwarePredicate, DeserializationContext deserializationContext) {
        return new TriggerInstance(contextAwarePredicate, ItemPredicate.fromJson(jsonObject.get("item")), MinMaxBounds.Doubles.fromJson(jsonObject.get("height")));
    }

    @Override
    public Identifier getId() {
        return ID;
    }
    *///?}

    //? if >=1.21 {
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
            return UmbrellasMisc.TIME_GLIDING_CRITERION.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), MinMaxBounds.Doubles.ANY));
        }

        public static Criterion<TriggerInstance> height(MinMaxBounds.Doubles bounds) {
            return UmbrellasMisc.TIME_GLIDING_CRITERION.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), bounds));
        }

        public static Criterion<TriggerInstance> minHeight(double min) {
            return height(MinMaxBounds.Doubles.atLeast(min));
        }

        public boolean matches(ItemStack stack, double height) {
            return (this.item.isEmpty() || this.item.get().test(stack)) && this.height.matches(height);
        }
    }
    //?} else {
    /*public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;
        private final MinMaxBounds.Doubles height;

        public TriggerInstance(ContextAwarePredicate contextAwarePredicate, ItemPredicate item, MinMaxBounds.Doubles height) {
            super(ID, contextAwarePredicate);
            this.item = item;
            this.height = height;
        }

        public static TriggerInstance create() {
            return new TriggerInstance(ContextAwarePredicate.ANY, ItemPredicate.ANY, MinMaxBounds.Doubles.ANY);
        }

        public static TriggerInstance height(MinMaxBounds.Doubles bounds) {
            return new TriggerInstance(ContextAwarePredicate.ANY, ItemPredicate.ANY, bounds);
        }

        public static TriggerInstance minHeight(double min) {
            return height(MinMaxBounds.Doubles.atLeast(min));
        }

        public boolean matches(ItemStack stack, double height) {
            return this.item.matches(stack) && this.height.matches(height);
        }
    }
    *///?}
}
