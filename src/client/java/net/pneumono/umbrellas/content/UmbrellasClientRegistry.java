package net.pneumono.umbrellas.content;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.DyeableItem;

public class UmbrellasClientRegistry {
    public static void registerClientContent() {
        BlockEntityRendererFactories.register(UmbrellasRegistry.UMBRELLA_STAND_ENTITY, UmbrellaStandBlockEntityRenderer::new);
        registerColorProviders();
    }

    private static void registerColorProviders() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                tintIndex > 0 ? -1 : ((DyeableItem)stack.getItem()).getColor(stack), UmbrellasRegistry.UMBRELLA
        );

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> (
                (PrideUmbrellaItem)stack.getItem()).getStripe(tintIndex),

            UmbrellasRegistry.UMBRELLA_ABROSEXUAL,
            UmbrellasRegistry.UMBRELLA_AGENDER,
            UmbrellasRegistry.UMBRELLA_AROACE,
            UmbrellasRegistry.UMBRELLA_AROMANTIC,
            UmbrellasRegistry.UMBRELLA_ASEXUAL,
            UmbrellasRegistry.UMBRELLA_BIGENDER,
            UmbrellasRegistry.UMBRELLA_BISEXUAL,
            UmbrellasRegistry.UMBRELLA_GAY,
            UmbrellasRegistry.UMBRELLA_GENDERFLUID,
            // Intersex not included, since it isn't stripey
            UmbrellasRegistry.UMBRELLA_LESBIAN,
            UmbrellasRegistry.UMBRELLA_NONBINARY,
            UmbrellasRegistry.UMBRELLA_OMNISEXUAL,
            UmbrellasRegistry.UMBRELLA_PANGENDER,
            UmbrellasRegistry.UMBRELLA_PANSEXUAL,
            UmbrellasRegistry.UMBRELLA_POLYSEXUAL,
            UmbrellasRegistry.UMBRELLA_PRIDE,
            UmbrellasRegistry.UMBRELLA_TRANSGENDER
        );
    }
}
