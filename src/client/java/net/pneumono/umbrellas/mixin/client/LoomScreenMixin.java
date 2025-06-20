package net.pneumono.umbrellas.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.LoomScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.LoomScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.LoomUmbrellaRendering;
import net.pneumono.umbrellas.content.UmbrellaPattern;
import net.pneumono.umbrellas.content.item.PatternableUmbrellaItem;
import net.pneumono.umbrellas.content.item.component.ProvidesUmbrellaPatterns;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
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
    private boolean canApplyDyePattern;
    @Shadow
    private boolean hasTooManyPatterns;
    @Shadow
    private int visibleTopRow;

    @Unique
    private static final Identifier PATTERN_TEXTURE = Umbrellas.id("pattern");
    @Unique
    private static final Identifier PATTERN_HIGHLIGHTED_TEXTURE = Umbrellas.id("pattern_highlighted");
    @Unique
    private static final Identifier TEXTURE = Umbrellas.id("textures/gui/loom.png");
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
                    target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V",
                    ordinal = 3
            ),
            cancellable = true
    )
    private void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY, CallbackInfo ci) {
        Slot outputSlot = this.handler.getOutputSlot();

        if (!isUsingUmbrellas) {
            if (!(this.handler.getBannerSlot().getStack().getItem() instanceof BannerItem)) {
                ci.cancel();
            }
            return;
        }

        if (this.umbrellaPatterns != null && !this.hasTooManyPatterns) {
            LoomUmbrellaRendering.drawResultUmbrella(context, x + 135, y + 12, outputSlot.getStack());
        } else if (this.hasTooManyPatterns) {
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, ERROR_TEXTURE, this.x + outputSlot.x - 5, this.y + outputSlot.y - 5, 26, 26);
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

                    context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, texture, newX, newY, 14, 14);
                    LoomUmbrellaRendering.drawPatternUmbrella(context, newX, newY, list.get(index));
                }
            }
        }

        ci.cancel();
    }

    @ModifyArg(
            method = "drawBackground",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIFFIIII)V"
            ),
            index = 1
    )
    private Identifier useModifiedTextureIfUsingUmbrella(Identifier original) {
        return this.isUsingUmbrellas ? TEXTURE : original;
    }

    @Inject(
            method = "onInventoryChanged",
            at = @At("HEAD")
    )
    private void setIsUsingUmbrellas(CallbackInfo ci) {
        ItemStack inputStack = this.handler.getBannerSlot().getStack();
        this.isUsingUmbrellas = inputStack.getItem() instanceof PatternableUmbrellaItem;
    }

    @Inject(
            method = "onInventoryChanged",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screen/ingame/LoomScreen;bannerPatterns:Lnet/minecraft/component/type/BannerPatternsComponent;",
                    opcode = Opcodes.PUTFIELD,
                    ordinal = 0
            )
    )
    private void setUmbrellaPatternsNull1(CallbackInfo ci) {
        this.umbrellaPatterns = null;
    }

    @Inject(
            method = "onInventoryChanged",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screen/ingame/LoomScreen;bannerPatterns:Lnet/minecraft/component/type/BannerPatternsComponent;",
                    opcode = Opcodes.PUTFIELD,
                    ordinal = 1
            )
    )
    private void setUmbrellaPatternsToOutputPatterns(CallbackInfo ci) {
        this.umbrellaPatterns = this.handler.getOutputSlot().getStack()
                .getOrDefault(UmbrellasDataComponents.UMBRELLA_PATTERNS, UmbrellaPatternsComponent.DEFAULT);
    }

    @WrapOperation(
            method = "onInventoryChanged",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screen/ingame/LoomScreen;hasTooManyPatterns:Z",
                    opcode = Opcodes.PUTFIELD
            )
    )
    private void setHasTooManyPatterns(LoomScreen instance, boolean value, Operation<Void> original) {
        ItemStack inputStack = this.handler.getBannerSlot().getStack();
        UmbrellaPatternsComponent umbrellaPatternsComponent = inputStack.getOrDefault(
                UmbrellasDataComponents.UMBRELLA_PATTERNS, UmbrellaPatternsComponent.DEFAULT
        );
        original.call(instance, value || umbrellaPatternsComponent.layers().size() >= UmbrellaPatternsComponent.MAX_PATTERNS);
    }

    @Inject(
            method = "onInventoryChanged",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screen/ingame/LoomScreen;bannerPatterns:Lnet/minecraft/component/type/BannerPatternsComponent;",
                    opcode = Opcodes.PUTFIELD,
                    ordinal = 2
            )
    )
    private void setUmbrellaPatternsNull2(CallbackInfo ci) {
        this.umbrellaPatterns = null;
    }

    @WrapOperation(
            method = "onInventoryChanged",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;isEmpty()Z",
                    ordinal = 2
            )
    )
    private boolean slotHasDyeItem(ItemStack instance, Operation<Boolean> original) {
        boolean invert = false;
        if (this.isUsingUmbrellas) {
            ProvidesUmbrellaPatterns component = this.handler.getPatternSlot().getStack().get(UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS);
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
            method = "onInventoryChanged",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;isEmpty()Z"
            )
    )
    private boolean hasPatterns(List<RegistryEntry<BannerPattern>> bannerPatterns, Operation<Boolean> original) {
        return original.call(bannerPatterns) && ((LoomScreenHandlerAccess)this.handler).umbrellas$getUmbrellaPatterns().isEmpty();
    }
}
