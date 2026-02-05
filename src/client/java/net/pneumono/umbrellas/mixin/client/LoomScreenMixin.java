//~ identifier_replacements

package net.pneumono.umbrellas.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.LoomScreen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.LoomUmbrellaRendering;
import net.pneumono.umbrellas.content.UmbrellaPattern;
import net.pneumono.umbrellas.content.item.UmbrellaPatternItem;
import net.pneumono.umbrellas.content.item.component.ProvidesUmbrellaPatterns;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import net.pneumono.umbrellas.registry.UmbrellasTags;
import net.pneumono.umbrellas.util.LoomScreenHandlerAccess;
import net.pneumono.umbrellas.util.data.VersionedComponents;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

//? if >=1.21.6 {
import net.minecraft.client.renderer.RenderPipelines;
//?}

//? if >=1.21
import org.spongepowered.asm.mixin.Final;

@Mixin(LoomScreen.class)
public abstract class LoomScreenMixin extends AbstractContainerScreen<LoomMenu> {
    public LoomScreenMixin(LoomMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    //? if >=1.21 {
    @Shadow
    @Final
    private static Identifier ERROR_SPRITE;
    @Shadow
    @Final
    private static Identifier PATTERN_SELECTED_SPRITE;
    //?}

    @Shadow
    private boolean displayPatterns;
    @Shadow
    private boolean hasMaxPatterns;
    @Shadow
    private int startRow;

    //? if >=1.21 {
    @Unique
    private static final Identifier PATTERN_SPRITE = Umbrellas.id("pattern");
    @Unique
    private static final Identifier PATTERN_HIGHLIGHTED_SPRITE = Umbrellas.id("pattern_highlighted");
    //?}
    @Unique
    private static final Identifier SPRITE = Umbrellas.id("textures/gui/loom.png");
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
            return ((LoomScreenHandlerAccess)this.menu).getUmbrellaPatterns().size();
        } else {
            return original.call(instance);
        }
    }

    @Inject(
            method = "renderBg",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.AFTER,
                    //? if >=1.21.6 {
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
                    //?} else if >=1.21 {
                    /*target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/Identifier;IIII)V",
                    *///?} else {
                    /*target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/Identifier;IIIIII)V",
                    *///?}
                    ordinal = /*? if >=1.21 {*/3/*?} else {*//*4*//*?}*/
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
            //? if >=1.21 {
            graphics.blitSprite(
                    /*? if >=1.21.6 {*/RenderPipelines.GUI_TEXTURED, /*?}*/
                    ERROR_SPRITE,
                    x + outputSlot.x - 5,
                    y + outputSlot.y - 5,
                    26, 26
            );
            //?} else {
            /*Slot resultSlot = this.menu.getResultSlot();
            graphics.blit(SPRITE, x + resultSlot.x - 2, y + resultSlot.y - 2, this.imageWidth, 17, 17, 16);
            *///?}
        }

        if (!this.displayPatterns) return;
        int offX = x + 60;
        int offY = y + 13;
        List<Holder<UmbrellaPattern>> list = ((LoomScreenHandlerAccess)this.menu).getUmbrellaPatterns();

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

                //? if >=1.21 {
                Identifier texture;
                if (index == this.menu.getSelectedBannerPatternIndex()) {
                    texture = PATTERN_SELECTED_SPRITE;
                } else if (isHighlighted) {
                    texture = PATTERN_HIGHLIGHTED_SPRITE;
                } else {
                    texture = PATTERN_SPRITE;
                }

                graphics.blitSprite(
                        /*? if >=1.21.6 {*/RenderPipelines.GUI_TEXTURED, /*?}*/
                        texture,
                        newX, newY,
                        14, 14
                );
                //?} else {
                /*int yOffset = this.imageHeight;
                if (index == this.menu.getSelectedBannerPatternIndex()) {
                    yOffset += 14;
                } else if (isHighlighted) {
                    yOffset += 28;
                }

                graphics.blit(SPRITE, newX, newY, 0, yOffset, 14, 14);
                *///?}

                LoomUmbrellaRendering.drawPatternUmbrella(graphics, newX, newY, list.get(index));
            }
        }

        ci.cancel();
    }

    @ModifyArg(
            method = "renderBg",
            at = @At(
                    value = "INVOKE",
                    //? if >=1.21.6 {
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V"
                    //?} else {
                    /*target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/Identifier;IIIIII)V"
                    *///?}
            ),
            index = /*? if >=1.21.6 {*/1/*?} else {*//*0*//*?}*/
    )
    private Identifier useModifiedTextureIfUsingUmbrella(Identifier original) {
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

    //? if >=1.21 {
    @Inject(
            method = "containerChanged",
            at = @At(
                    value = "FIELD",
                    //? if >=1.21 {
                    target = "Lnet/minecraft/client/gui/screens/inventory/LoomScreen;resultBannerPatterns:Lnet/minecraft/world/level/block/entity/BannerPatternLayers;",
                    //?} else {
                    /*target = "Lnet/minecraft/client/gui/screens/inventory/LoomScreen;resultBannerPatterns:Ljava/util/List;",
                    *///?}
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
        this.umbrellaPatterns = VersionedComponents.getOrDefault(
                this.menu.getResultSlot().getItem(),
                UmbrellasDataComponents.UMBRELLA_PATTERNS,
                UmbrellaPatternsComponent.DEFAULT
        );
    }
    //?} else {
    /*@WrapOperation(
            method = "containerChanged",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z",
                    ordinal = 0
            )
    )
    private boolean setUmbrellaPatternsToOutputPatterns(ItemStack stack, Operation<Boolean> original) {
        // Overrides at this point to prevent ClassCastException from assuming the ItemStack is always a BannerItem
        if (stack.getItem() instanceof BannerItem) {
            return original.call(stack);
        } else if (original.call(stack)) {
            this.umbrellaPatterns = null;
            return true;
        } else {
            this.umbrellaPatterns = VersionedComponents.getOrDefault(
                    this.menu.getResultSlot().getItem(),
                    UmbrellasDataComponents.UMBRELLA_PATTERNS,
                    UmbrellaPatternsComponent.DEFAULT
            );
            return true;
        }
    }
    *///?}

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
        UmbrellaPatternsComponent umbrellaPatternsComponent = VersionedComponents.getOrDefault(
                inputStack,
                UmbrellasDataComponents.UMBRELLA_PATTERNS,
                UmbrellaPatternsComponent.DEFAULT
        );
        original.call(instance, value || umbrellaPatternsComponent.layers().size() >= UmbrellaPatternsComponent.MAX_PATTERNS);
    }

    @Inject(
            method = "containerChanged",
            at = @At(
                    value = "FIELD",
                    //? if >=1.21 {
                    target = "Lnet/minecraft/client/gui/screens/inventory/LoomScreen;resultBannerPatterns:Lnet/minecraft/world/level/block/entity/BannerPatternLayers;",
                    //?} else {
                    /*target = "Lnet/minecraft/client/gui/screens/inventory/LoomScreen;resultBannerPatterns:Ljava/util/List;",
                    *///?}
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
                    ordinal = /*? if >=1.21 {*/2/*?} else {*//*3*//*?}*/
            )
    )
    private boolean slotHasDyeItem(ItemStack instance, Operation<Boolean> original) {
        if (this.isUsingUmbrellas) {
            ProvidesUmbrellaPatterns component = UmbrellaPatternItem.getProvided(this.menu.getPatternSlot().getItem());
            if (component != null && !component.requiresDye()) {
                return !original.call(instance);
            }
        }

        return original.call(instance);
    }

    @WrapOperation(
            method = "containerChanged",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;isEmpty()Z"
            )
    )
    private boolean hasPatterns(List<Holder<BannerPattern>> bannerPatterns, Operation<Boolean> original) {
        return original.call(bannerPatterns) && ((LoomScreenHandlerAccess)this.menu).getUmbrellaPatterns().isEmpty();
    }
}
