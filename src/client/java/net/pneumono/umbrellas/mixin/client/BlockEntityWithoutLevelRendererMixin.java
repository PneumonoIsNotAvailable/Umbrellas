package net.pneumono.umbrellas.mixin.client;

//? if <1.21.6 {
/*import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.pneumono.umbrellas.UmbrellasClient;
import net.pneumono.umbrellas.content.UmbrellaRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityWithoutLevelRenderer.class)
public abstract class BlockEntityWithoutLevelRendererMixin {
    @Shadow
    @Final
    private EntityModelSet entityModelSet;

    @Inject(
            method = "onResourceManagerReload",
            at = @At("HEAD")
    )
    private void reloadUmbrellaModels(ResourceManager resourceManager, CallbackInfo ci) {
        UmbrellasClient.UMBRELLA_RENDERER = new UmbrellaRenderer(this.entityModelSet);
    }
}
*///?}