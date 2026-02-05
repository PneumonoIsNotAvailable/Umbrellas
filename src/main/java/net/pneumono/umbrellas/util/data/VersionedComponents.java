package net.pneumono.umbrellas.util.data;

import net.minecraft.world.item.ItemStack;

import java.util.function.UnaryOperator;

//? if <1.21 {
/*import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import java.util.Optional;
import java.util.function.BiFunction;
*///?}

public class VersionedComponents {
    public static <T> void remove(ItemStack stack, VersionedComponentType<T> type) {
        //? if >=1.21 {
        stack.remove(type.getType());
        //?} else {
        /*CompoundTag tag = stack.getTag();
        if (tag != null) tag.remove(type.getId().toString());
        *///?}
    }

    public static <T> void set(ItemStack stack, VersionedComponentType<T> type, T component) {
        //? if >=1.21 {
        stack.set(type.getType(), component);
        //?} else {
        /*CompoundTag tag = stack.getOrCreateTag();
        String key = type.getId().toString();
        Either<Tag, DataResult.PartialResult<Tag>> either = type.getCodec().encodeStart(NbtOps.INSTANCE, component).get();
        either.ifLeft(componentTag -> tag.put(key, componentTag));
        either.ifRight(result -> tag.remove(key));
        *///?}
    }

    public static <T> T get(ItemStack stack, VersionedComponentType<T> type) {
        return getOrDefault(stack, type, null);
    }

    public static <T> T getOrDefault(ItemStack stack, VersionedComponentType<T> type, T defaultValue) {
        //? if >=1.21 {
        return stack.getOrDefault(type.getType(), defaultValue);
        //?} else {
        /*CompoundTag tag = stack.getTag();
        if (tag == null) return defaultValue;
        Tag componentTag = tag.get(type.getId().toString());
        Optional<T> optional = type.getCodec().decode(NbtOps.INSTANCE, componentTag).get().left().map(Pair::getFirst);
        return optional.orElse(defaultValue);
        *///?}
    }

    public static <T> boolean has(ItemStack stack, VersionedComponentType<T> type) {
        //? if >=1.21 {
        return stack.has(type.getType());
        //?} else {
        /*CompoundTag tag = stack.getTag();
        if (tag == null) return false;
        return type.getCodec().decode(NbtOps.INSTANCE, tag.get(type.getId().toString())).get().left().isPresent();
        *///?}
    }

    public static <T> void update(ItemStack stack, VersionedComponentType<T> type, T defaultValue, UnaryOperator<T> operator) {
        //? if >=1.21 {
        stack.update(type.getType(), defaultValue, operator);
        //?} else {
        /*set(stack, type, operator.apply(getOrDefault(stack, type, defaultValue)));
        *///?}
    }
}
