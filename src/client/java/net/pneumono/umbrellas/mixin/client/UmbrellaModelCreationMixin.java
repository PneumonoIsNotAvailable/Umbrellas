package net.pneumono.umbrellas.mixin.client;

import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.pneumono.umbrellas.content.UmbrellaModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BuiltinModelItemRenderer.class)
@SuppressWarnings("unused")
public abstract class UmbrellaModelCreationMixin {
    @Shadow
    @Final
    private EntityModelLoader entityModelLoader;

    @Inject(method = "reload", at = @At("TAIL"))
    private void initUmbrellaModel(CallbackInfo ci) {
        UmbrellaModel.loadModel(entityModelLoader);
    }
}
