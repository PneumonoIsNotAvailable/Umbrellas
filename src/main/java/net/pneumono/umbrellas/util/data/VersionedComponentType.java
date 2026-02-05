//~ identifier_replacements

package net.pneumono.umbrellas.util.data;

import com.mojang.serialization.Codec;
import net.minecraft.resources.Identifier;

//? if >=1.21 {
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
//?}

public class VersionedComponentType<T> {
    private final Codec<T> codec;
    private final Identifier id;
    //? if >=1.21
    private final DataComponentType<T> type;

    public VersionedComponentType(Codec<T> codec, Identifier id) {
        this.codec = codec;
        this.id = id;
        //? if >=1.21
        this.type = DataComponentType.<T>builder().persistent(codec).build();
    }

    //? if >=1.21 {
    public VersionedComponentType(Codec<T> codec, Identifier id, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) {
        this.codec = codec;
        this.id = id;
        this.type = DataComponentType.<T>builder().persistent(codec).networkSynchronized(streamCodec).build();
    }
    //?}

    public Codec<T> getCodec() {
        return codec;
    }

    public Identifier getId() {
        return this.id;
    }

    //? if >=1.21 {
    public DataComponentType<T> getType() {
        return this.type;
    }
    //?}
}
