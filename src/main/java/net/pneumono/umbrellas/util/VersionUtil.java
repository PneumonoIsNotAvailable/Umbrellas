//~ identifier_replacements

package net.pneumono.umbrellas.util;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public class VersionUtil {
    public static Identifier identifier(ResourceKey<?> key) {
        //? if >=1.21.11 {
        return key.identifier();
        //?} else {
        /*return key.location();
        *///?}
    }
}
