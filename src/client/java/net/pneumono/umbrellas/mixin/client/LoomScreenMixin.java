package net.pneumono.umbrellas.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.LoomScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.LoomUmbrellaRendering;
import net.pneumono.umbrellas.content.UmbrellaPattern;
import net.pneumono.umbrellas.content.item.component.ProvidesUmbrellaPatterns;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import net.pneumono.umbrellas.registry.UmbrellasTags;
import net.pneumono.umbrellas.util.LoomScreenHandlerAccess;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LoomScreen.class)
public abstract class LoomScreenMixin extends AbstractContainerScreen<LoomMenu> {
    public LoomScreenMixin(LoomMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Shadow
    @Final
    private static ResourceLocation ERROR_SPRITE;
    @Shadow
    @Final
    private static ResourceLocation PATTERN_SELECTED_SPRITE;
    @Shadow
    private boolean displayPatterns;
    @Shadow
    private boolean hasMaxPatterns;
    @Shadow
    private int startRow;

    @Unique
    private static final ResourceLocation PATTERN_SPRITE = Umbrellas.id("pattern");
    @Unique
    private static final ResourceLocation PATTERN_HIGHLIGHTED_SPRITE = Umbrellas.id("pattern_highlighted");
    @Unique
    private static final ResourceLocation SPRITE = Umbrellas.id("textures/gui/loom.png");
    @Nullable
    @Unique
    private UmbrellaPatternsComponent umbrellaPatterns;
    @Unique
    private boolean isUsingUmbrellas = false;

    @WrapOperation(
            method = "totalRowCount",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;size()I"
            )
    )
    private int getRows(List<Holder<BannerPattern>> instance, Operation<Integer> original) {
        if (this.isUsingUmbrellas) {
            return ((LoomScreenHandlerAccess)this.menu).umbrellas$getUmbrellaPatterns().size();
        } else {
            return original.call(instance);
        }
    }

    @Inject(
            method = "renderBg",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.AFTER,
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V",
                    ordinal = 3
            ),
            cancellable = true
    )
    private void drawBackground(GuiGraphics graphics, float deltaTicks, int mouseX, int mouseY, CallbackInfo ci) {
        int x = this.leftPos;
        int y = this.topPos;

        Slot outputSlot = this.menu.getResultSlot();

        if (!isUsingUmbrellas) {
            if (!(this.menu.getBannerSlot().getItem().getItem() instanceof BannerItem)) {
                ci.cancel();
            }
            return;
        }

        if (this.umbrellaPatterns != null && !this.hasMaxPatterns) {
            LoomUmbrellaRendering.drawResultUmbrella(graphics, x + 135, y + 12, outputSlot.getItem());
        } else if (this.hasMaxPatterns) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ERROR_SPRITE, x + outputSlot.x - 5, y + outputSlot.y - 5, 26, 26);
        }

        if (!this.displayPatterns) return;
        int offX = x + 60;
        int offY = y + 13;
        List<Holder<UmbrellaPattern>> list = ((LoomScreenHandlerAccess)this.menu).umbrellas$getUmbrellaPatterns();

        loop:
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int row = i + this.startRow;
                int index = row * 4 + j;
                if (index >= list.size()) {
                    break loop;
                }

                int newX = offX + j * 14;
                int newY = offY + i * 14;
                boolean isHighlighted = mouseX >= newX && mouseY >= newY && mouseX < newX + 14 && mouseY < newY + 14;
                ResourceLocation texture;
                if (index == this.menu.getSelectedBannerPatternIndex()) {
                    texture = PATTERN_SELECTED_SPRITE;
                } else if (isHighlighted) {
                    texture = PATTERN_HIGHLIGHTED_SPRITE;
                } else {
                    texture = PATTERN_SPRITE;
                }

                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, texture, newX, newY, 14, 14);
                LoomUmbrellaRendering.drawPatternUmbrella(graphics, newX, newY, list.get(index));
            }
        }

        ci.cancel();
    }

    @ModifyArg(
            method = "renderBg",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"
            ),
            index = 1
    )
    private ResourceLocation useModifiedTextureIfUsingUmbrella(ResourceLocation original) {
        return this.isUsingUmbrellas ? SPRITE : original;
    }

    @Inject(
            method = "containerChanged",
            at = @At("HEAD")
    )
    private void setIsUsingUmbrellas(CallbackInfo ci) {
        ItemStack inputStack = this.menu.getBannerSlot().getItem();
        this.isUsingUmbrellas = inputStack.is(UmbrellasTags.PATTERNABLE_UMBRELLAS);
    }

    @Inject(
            method = "containerChanged",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screens/inventory/LoomScreen;resultBannerPatterns:Lnet/minecraft/world/level/block/entity/BannerPatternLayers;",
                    opcode = Opcodes.PUTFIELD,
                    ordinal = 0
            )
    )
    private void setUmbrellaPatternsNull1(CallbackInfo ci) {
        this.umbrellaPatterns = null;
    }

    @Inject(
            method = "containerChanged",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screens/inventory/LoomScreen;resultBannerPatterns:Lnet/minecraft/world/level/block/entity/BannerPatternLayers;",
                    opcode = Opcodes.PUTFIELD,
                    ordinal = 1
            )
    )
    private void setUmbrellaPatternsToOutputPatterns(CallbackInfo ci) {
        this.umbrellaPatterns = this.menu.getResultSlot().getItem()
                .getOrDefault(UmbrellasDataComponents.UMBRELLA_PATTERNS, UmbrellaPatternsComponent.DEFAULT);
    }

    @WrapOperation(
            method = "containerChanged",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screens/inventory/LoomScreen;hasMaxPatterns:Z",
                    opcode = Opcodes.PUTFIELD
            )
    )
    private void setHasTooManyPatterns(LoomScreen instance, boolean value, Operation<Void> original) {
        ItemStack inputStack = this.menu.getBannerSlot().getItem();
        UmbrellaPatternsComponent umbrellaPatternsComponent = inputStack.getOrDefault(
                UmbrellasDataComponents.UMBRELLA_PATTERNS, UmbrellaPatternsComponent.DEFAULT
        );
        original.call(instance, value || umbrellaPatternsComponent.layers().size() >= UmbrellaPatternsComponent.MAX_PATTERNS);
    }

    @Inject(
            method = "containerChanged",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screens/inventory/LoomScreen;resultBannerPatterns:Lnet/minecraft/world/level/block/entity/BannerPatternLayers;",
                    opcode = Opcodes.PUTFIELD,
                    ordinal = 2
            )
    )
    private void setUmbrellaPatternsNull2(CallbackInfo ci) {
        this.umbrellaPatterns = null;
    }

    @WrapOperation(
            method = "containerChanged",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z",
                    ordinal = 2
            )
    )
    private boolean slotHasDyeItem(ItemStack instance, Operation<Boolean> original) {
        boolean invert = false;
        if (this.isUsingUmbrellas) {
            ProvidesUmbrellaPatterns component = this.menu.getPatternSlot().getItem().get(UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS);
            if (component != null && !component.requiresDye()) {
                invert = true;
            }
        }

        if (invert) {
            return !original.call(instance);
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(
            method = "containerChanged",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;isEmpty()Z"
            )
    )
    private boolean hasPatterns(List<Holder<BannerPattern>> bannerPatterns, Operation<Boolean> original) {
        return original.call(bannerPatterns) && ((LoomScreenHandlerAccess)this.menu).umbrellas$getUmbrellaPatterns().isEmpty();
    }
}
