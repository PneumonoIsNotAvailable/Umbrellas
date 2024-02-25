package net.pneumono.umbrellas.mixin.client;

import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.util.Identifier;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.UmbrellasClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(BakedModelManager.class)
@SuppressWarnings("unused")
public abstract class LayersToLoadersMixin {
    @Shadow
    @Mutable
    @Final
    private static Map<Identifier, Identifier> LAYERS_TO_LOADERS;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void umbrellaPattern(CallbackInfo callbackInfo) {
        LAYERS_TO_LOADERS = new HashMap<>(LAYERS_TO_LOADERS);
        LAYERS_TO_LOADERS.put(UmbrellasClient.UMBRELLA_PATTERNS_ATLAS_TEXTURE, new Identifier(Umbrellas.MOD_ID, "umbrella_patterns"));
    }
}
