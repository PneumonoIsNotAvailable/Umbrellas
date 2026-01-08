package net.pneumono.umbrellas.mixin.client;

import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
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

@Mixin(ModelManager.class)
@SuppressWarnings("unused")
public abstract class ModelManagerMixin {
    @Shadow
    @Mutable
    @Final
    private static Map<ResourceLocation, ResourceLocation> VANILLA_ATLASES;

    @Inject(
            method = "<clinit>",
            at = @At("TAIL")
    )
    private static void umbrellaPattern(CallbackInfo callbackInfo) {
        VANILLA_ATLASES = new HashMap<>(VANILLA_ATLASES);
        VANILLA_ATLASES.put(UmbrellasClient.UMBRELLA_PATTERNS_ATLAS_TEXTURE, Umbrellas.id("umbrella_patterns"));
    }
}
