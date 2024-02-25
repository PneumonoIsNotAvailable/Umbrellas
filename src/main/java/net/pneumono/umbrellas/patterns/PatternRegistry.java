package net.pneumono.umbrellas.patterns;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.pneumono.umbrellas.Umbrellas;

public class PatternRegistry {
    public static final RegistryKey<Registry<UmbrellaPattern>> UMBRELLA_PATTERNS = RegistryKey.ofRegistry(new Identifier(Umbrellas.MOD_ID, "umbrella_pattern"));
    public static final Registry<UmbrellaPattern> UMBRELLA_PATTERN = FabricRegistryBuilder.createSimple(UMBRELLA_PATTERNS).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final TagKey<UmbrellaPattern> NO_ITEM_REQUIRED = TagKey.of(UMBRELLA_PATTERNS, new Identifier(Umbrellas.MOD_ID, "no_item_required"));

    public static RegistryKey<UmbrellaPattern> keyOf(String id) {
        return RegistryKey.of(UMBRELLA_PATTERNS, new Identifier(Umbrellas.MOD_ID, id));
    }

    public static void registerPatterns() {
        UmbrellaPatterns.registerPatterns();
    }
}
