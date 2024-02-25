package net.pneumono.umbrellas.patterns;

import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;

public record UmbrellaPattern(String id, boolean colored) {
    @Nullable
    public static RegistryEntry<UmbrellaPattern> byId(String id) {
        return PatternRegistry.UMBRELLA_PATTERN.streamEntries().filter(pattern -> pattern.value().id.equals(id)).findAny().orElse(null);
    }
}
