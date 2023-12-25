package net.pneumono.umbrellas.content;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.DyeableItem;

public class ModClientContent {
    public static void registerColorProviders() {

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                tintIndex > 0 ? -1 : ((DyeableItem)stack.getItem()).getColor(stack), ModContent.UMBRELLA
        );

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> (
                (PrideUmbrellaItem)stack.getItem()).getStripe(tintIndex),

            ModContent.UMBRELLA_ABROSEXUAL,
            ModContent.UMBRELLA_AGENDER,
            ModContent.UMBRELLA_AROACE,
            ModContent.UMBRELLA_AROMANTIC,
            ModContent.UMBRELLA_ASEXUAL,
            ModContent.UMBRELLA_BIGENDER,
            ModContent.UMBRELLA_BISEXUAL,
            ModContent.UMBRELLA_GAY,
            ModContent.UMBRELLA_GENDERFLUID,
            // Intersex not included, since it isn't stripey
            ModContent.UMBRELLA_LESBIAN,
            ModContent.UMBRELLA_NONBINARY,
            ModContent.UMBRELLA_OMNISEXUAL,
            ModContent.UMBRELLA_PANGENDER,
            ModContent.UMBRELLA_PANSEXUAL,
            ModContent.UMBRELLA_POLYSEXUAL,
            ModContent.UMBRELLA_PRIDE,
            ModContent.UMBRELLA_TRANSGENDER
        );
    }
}
