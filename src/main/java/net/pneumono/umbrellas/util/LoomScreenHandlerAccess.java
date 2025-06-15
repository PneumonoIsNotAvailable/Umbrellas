package net.pneumono.umbrellas.util;

import net.minecraft.registry.entry.RegistryEntry;
import net.pneumono.umbrellas.content.UmbrellaPattern;

import java.util.List;

public interface LoomScreenHandlerAccess {
    List<RegistryEntry<UmbrellaPattern>> umbrellas$getUmbrellaPatterns();
}
