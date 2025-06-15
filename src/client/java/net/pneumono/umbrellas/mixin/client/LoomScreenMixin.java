package net.pneumono.umbrellas.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.LoomScreen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.LoomScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.pneumono.umbrellas.content.item.PatternableUmbrellaItem;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.patterns.UmbrellaPattern;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import net.pneumono.umbrellas.util.LoomScreenHandlerAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LoomScreen.class)
public abstract class LoomScreenMixin extends HandledScreen<LoomScreenHandler> {
    public LoomScreenMixin(LoomScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Shadow
    @Final
    private static Identifier ERROR_TEXTURE;
    @Shadow
    @Final
    private static Identifier PATTERN_SELECTED_TEXTURE;
    @Shadow
    @Final
    private static Identifier PATTERN_HIGHLIGHTED_TEXTURE;
    @Shadow
    @Final
    private static Identifier PATTERN_TEXTURE;
    @Shadow
    private boolean canApplyDyePattern;
    @Shadow
    private boolean hasTooManyPatterns;
    @Shadow
    private int visibleTopRow;

    @Nullable
    @Unique
    private UmbrellaPatternsComponent umbrellaPatterns;
    @Unique
    private boolean isUsingUmbrellas = false;

    @WrapOperation(
            method = "getRows",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;size()I"
            )
    )
    private int getRows(List<RegistryEntry<BannerPattern>> instance, Operation<Integer> original) {
        if (this.isUsingUmbrellas) {
            return ((LoomScreenHandlerAccess)this.handler).umbrellas$getUmbrellaPatterns().size();
        } else {
            return original.call(instance);
        }
    }

    @Inject(
            method = "drawBackground",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.AFTER,
                    target = "Lnet/minecraft/client/render/DiffuseLighting;disableGuiDepthLighting()V"
            ),
            cancellable = true
    )
    private void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY, CallbackInfo ci) {
        Slot outputSlot = this.handler.getOutputSlot();

        if (!isUsingUmbrellas) {
            DiffuseLighting.enableGuiDepthLighting();
            if (!(this.handler.getBannerSlot().getStack().getItem() instanceof BannerItem)) {
                ci.cancel();
            }
            return;
        }

        if (this.umbrellaPatterns != null && !this.hasTooManyPatterns) {
            /*
            context.getMatrices().push();
            context.getMatrices().translate((float)(i + 139), (float)(j + 52), 0.0F);
            context.getMatrices().scale(24.0F, 24.0F, 1.0F);
            context.getMatrices().translate(0.5F, 0.0F, 0.5F);
            float f = 0.6666667F;
            context.getMatrices().scale(0.6666667F, 0.6666667F, -0.6666667F);
            DyeColor dyeColor = ((BannerItem)slot4.getStack().getItem()).getColor();
            context.draw(
                    vertexConsumers -> BannerBlockEntityRenderer.renderCanvas(
                            context.getMatrices(), vertexConsumers, 15728880, OverlayTexture.DEFAULT_UV, this.bannerField, ModelBaker.BANNER_BASE, true, dyeColor, this.bannerPatterns
                    )
            );
            context.getMatrices().pop();
             */
        } else if (this.hasTooManyPatterns) {
            context.drawGuiTexture(RenderLayer::getGuiTextured, ERROR_TEXTURE, this.x + outputSlot.x - 5, this.y + outputSlot.y - 5, 26, 26);
        }
        if (this.canApplyDyePattern) {
            int x = this.x + 60;
            int y = this.y + 13;
            List<RegistryEntry<UmbrellaPattern>> list = ((LoomScreenHandlerAccess)this.handler).umbrellas$getUmbrellaPatterns();

            loop:
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    int row = i + this.visibleTopRow;
                    int index = row * 4 + j;
                    if (index >= list.size()) {
                        break loop;
                    }

                    int newX = x + j * 14;
                    int newY = y + i * 14;
                    boolean isHighlighted = mouseX >= newX && mouseY >= newY && mouseX < newX + 14 && mouseY < newY + 14;
                    Identifier texture;
                    if (index == this.handler.getSelectedPattern()) {
                        texture = PATTERN_SELECTED_TEXTURE;
                    } else if (isHighlighted) {
                        texture = PATTERN_HIGHLIGHTED_TEXTURE;
                    } else {
                        texture = PATTERN_TEXTURE;
                    }

                    context.drawGuiTexture(RenderLayer::getGuiTextured, texture, newX, newY, 14, 14);
                    this.drawUmbrella(context, list.get(index), newX, newY);
                }
            }
        }

        context.draw();
        DiffuseLighting.enableGuiDepthLighting();

        ci.cancel();
    }

    @Unique
    private void drawUmbrella(DrawContext context, RegistryEntry<UmbrellaPattern> pattern, int x, int y) {
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.push();
        matrixStack.translate(x + 0.5F, (float)(y + 16), 0.0F);
        matrixStack.scale(6.0F, -6.0F, 1.0F);
        matrixStack.translate(0.5F, 0.0F, 0.0F);
        matrixStack.translate(0.5F, 0.5F, 0.5F);
        float f = 0.6666667F;
        matrixStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
        UmbrellaPatternsComponent umbrellaPatternsComponent = new UmbrellaPatternsComponent.Builder().add(pattern, DyeColor.WHITE).build();
        //context.draw(
        //        vertexConsumers -> BannerBlockEntityRenderer.renderCanvas(
        //                matrixStack, vertexConsumers, 15728880, OverlayTexture.DEFAULT_UV, this.bannerField, ModelBaker.BANNER_BASE, true, DyeColor.GRAY, umbrellaPatternsComponent
        //        )
        //);
        matrixStack.pop();
        context.draw();
    }

    @Inject(
            method = "onInventoryChanged",
            at = @At("RETURN")
    )
    private void onInventoryChanged(CallbackInfo ci) {
        ItemStack outputStack = this.handler.getOutputSlot().getStack();
        if (outputStack.isEmpty()) {
            this.umbrellaPatterns = null;
        } else {
            this.umbrellaPatterns = outputStack.getOrDefault(UmbrellasDataComponents.UMBRELLA_PATTERNS, UmbrellaPatternsComponent.DEFAULT);
        }

        ItemStack inputStack = this.handler.getBannerSlot().getStack();
        this.isUsingUmbrellas = inputStack.getItem() instanceof PatternableUmbrellaItem;

        UmbrellaPatternsComponent umbrellaPatternsComponent = inputStack.getOrDefault(
                UmbrellasDataComponents.UMBRELLA_PATTERNS, UmbrellaPatternsComponent.DEFAULT
        );
        this.hasTooManyPatterns = this.hasTooManyPatterns || umbrellaPatternsComponent.layers().size() >= 6;
        if (this.hasTooManyPatterns) {
            this.umbrellaPatterns = null;
        }
    }

    @WrapOperation(
            method = "onInventoryChanged",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;isEmpty()Z"
            )
    )
    private boolean slotHasPatternItem(List<RegistryEntry<BannerPattern>> bannerPatterns, Operation<Boolean> original) {
        return original.call(bannerPatterns) && ((LoomScreenHandlerAccess)this.handler).umbrellas$getUmbrellaPatterns().isEmpty();
    }
}
