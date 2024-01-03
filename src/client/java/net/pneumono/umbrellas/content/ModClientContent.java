package net.pneumono.umbrellas.content;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.DyeableItem;

public class ModClientContent {
    public static void registerColorProviders() {

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                tintIndex > 0 ? -1 : ((DyeableItem)stack.getItem()).getColor(stack), UmbrellasContent.UMBRELLA
        );

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> (
                (PrideUmbrellaItem)stack.getItem()).getStripe(tintIndex),

            UmbrellasContent.UMBRELLA_ABROSEXUAL,
            UmbrellasContent.UMBRELLA_AGENDER,
            UmbrellasContent.UMBRELLA_AROACE,
            UmbrellasContent.UMBRELLA_AROMANTIC,
            UmbrellasContent.UMBRELLA_ASEXUAL,
            UmbrellasContent.UMBRELLA_BIGENDER,
            UmbrellasContent.UMBRELLA_BISEXUAL,
            UmbrellasContent.UMBRELLA_GAY,
            UmbrellasContent.UMBRELLA_GENDERFLUID,
            // Intersex not included, since it isn't stripey
            UmbrellasContent.UMBRELLA_LESBIAN,
            UmbrellasContent.UMBRELLA_NONBINARY,
            UmbrellasContent.UMBRELLA_OMNISEXUAL,
            UmbrellasContent.UMBRELLA_PANGENDER,
            UmbrellasContent.UMBRELLA_PANSEXUAL,
            UmbrellasContent.UMBRELLA_POLYSEXUAL,
            UmbrellasContent.UMBRELLA_PRIDE,
            UmbrellasContent.UMBRELLA_TRANSGENDER
        );
    }
}
