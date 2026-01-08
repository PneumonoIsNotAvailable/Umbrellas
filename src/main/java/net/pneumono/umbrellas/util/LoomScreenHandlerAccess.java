package net.pneumono.umbrellas.util;

import net.minecraft.core.Holder;
import net.pneumono.umbrellas.content.UmbrellaPattern;

import java.util.List;

public interface LoomScreenHandlerAccess {
    List<Holder<UmbrellaPattern>> getUmbrellaPatterns();
}
