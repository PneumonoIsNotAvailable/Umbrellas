package net.pneumono.umbrellas.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.UmbrellaPattern;

public class UmbrellasTags {
    public static final TagKey<Item> REPAIRS_UMBRELLAS = item("repairs_umbrellas");
    public static final TagKey<Item> UMBRELLAS = item("umbrellas");
    public static final TagKey<Item> UMBRELLA_ENCHANTABLE = item("enchantable/umbrella");

    public static final TagKey<Block> BOOSTS_UMBRELLAS = block("boosts_umbrellas");
    public static final TagKey<Block> UMBRELLA_BOOSTING_TOGGLEABLE = block("umbrella_boosting_toggleable");
    public static final TagKey<Block> SMOKE_PASSES_THROUGH = block("smoke_passes_through");

    public static final TagKey<UmbrellaPattern> NO_ITEM_REQUIRED = pattern("no_item_required");
    public static final TagKey<UmbrellaPattern> PRIDE = pattern("pattern_item/pride");
    public static final TagKey<UmbrellaPattern> FLOWER = pattern("pattern_item/flower");
    public static final TagKey<UmbrellaPattern> CREEPER = pattern("pattern_item/creeper");
    public static final TagKey<UmbrellaPattern> SKULL = pattern("pattern_item/skull");
    public static final TagKey<UmbrellaPattern> MOJANG = pattern("pattern_item/mojang");
    public static final TagKey<UmbrellaPattern> GLOBE = pattern("pattern_item/globe");
    public static final TagKey<UmbrellaPattern> PIGLIN = pattern("pattern_item/piglin");
    public static final TagKey<UmbrellaPattern> FLOW = pattern("pattern_item/flow");
    public static final TagKey<UmbrellaPattern> GUSTER = pattern("pattern_item/guster");
    public static final TagKey<UmbrellaPattern> FIELD_MASONED = pattern("pattern_item/field_masoned");
    public static final TagKey<UmbrellaPattern> BORDURE_INDENTED = pattern("pattern_item/bordure_indented");

    public static TagKey<Item> item(String name) {
        return TagKey.of(RegistryKeys.ITEM, Umbrellas.id(name));
    }

    public static TagKey<Block> block(String name) {
        return TagKey.of(RegistryKeys.BLOCK, Umbrellas.id(name));
    }

    public static TagKey<UmbrellaPattern> pattern(String name) {
        return TagKey.of(UmbrellaPatterns.UMBRELLA_PATTERN_KEY, Umbrellas.id(name));
    }

    public static void registerUmbrellasTags() {

    }
}
