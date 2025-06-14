package net.pneumono.umbrellas.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.patterns.UmbrellaPattern;

public class UmbrellasTags {
    public static final TagKey<Item> REPAIRS_UMBRELLAS = item("repairs_umbrellas");
    public static final TagKey<Item> UMBRELLAS = item("umbrellas");
    public static final TagKey<Item> UMBRELLA_ENCHANTABLE = item("enchantable/umbrella");

    public static final TagKey<Block> BOOSTS_UMBRELLAS = block("boosts_umbrellas");

    public static final TagKey<UmbrellaPattern> NO_ITEM_REQUIRED = pattern("no_item_required");

    public static TagKey<Item> item(String name) {
        return TagKey.of(RegistryKeys.ITEM, Umbrellas.id(name));
    }

    public static TagKey<Block> block(String name) {
        return TagKey.of(RegistryKeys.BLOCK, Umbrellas.id(name));
    }

    public static TagKey<UmbrellaPattern> pattern(String name) {
        return TagKey.of(UmbrellasPatterns.UMBRELLA_PATTERN_KEY, Umbrellas.id(name));
    }

    public static void registerUmbrellasTags() {

    }
}
