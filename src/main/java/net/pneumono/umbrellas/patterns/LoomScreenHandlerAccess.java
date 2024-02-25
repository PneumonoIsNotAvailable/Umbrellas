package net.pneumono.umbrellas.patterns;

import net.minecraft.registry.entry.RegistryEntry;

import java.util.List;

public interface LoomScreenHandlerAccess {
    List<RegistryEntry<UmbrellaPattern>> getUmbrellaPatterns();
}
