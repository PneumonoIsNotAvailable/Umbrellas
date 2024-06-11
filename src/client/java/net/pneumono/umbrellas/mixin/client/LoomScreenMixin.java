package net.pneumono.umbrellas.mixin.client;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.LoomScreen;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.LoomScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.pneumono.pneumonocore.util.PneumonoMathHelper;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.UmbrellasClient;
import net.pneumono.umbrellas.content.PatternableUmbrellaItem;
import net.pneumono.umbrellas.patterns.PrideUmbrellaPatternItem;
import net.pneumono.umbrellas.content.UmbrellaModel;
import net.pneumono.umbrellas.content.UmbrellasRegistry;
import net.pneumono.umbrellas.patterns.LoomScreenHandlerAccess;
import net.pneumono.umbrellas.patterns.UmbrellaPattern;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;

@Mixin(LoomScreen.class)
@SuppressWarnings("unused")
public abstract class LoomScreenMixin extends HandledScreen<LoomScreenHandler> {
    @Shadow
    private boolean hasTooManyPatterns;
    @Shadow
    private boolean canApplyDyePattern;
    @Shadow
    private int visibleTopRow;
    @Shadow
    private ItemStack banner = ItemStack.EMPTY;
    @Shadow
    private ItemStack dye = ItemStack.EMPTY;
    @Shadow
    private ItemStack pattern = ItemStack.EMPTY;
    @Shadow
    @SuppressWarnings("FieldCanBeLocal")
    private float scrollPosition;

    private boolean usingUmbrellas;
    private static final Identifier UMBRELLA_TEXTURE = new Identifier(Umbrellas.MOD_ID, "textures/gui/umbrella_loom.png");
    private int time;
    @Nullable
    private List<Pair<RegistryEntry<UmbrellaPattern>, DyeColor>> umbrellaPatterns;
    private ModelPart umbrellaField;

    public LoomScreenMixin(LoomScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void initUmbrellaField(CallbackInfo info) {
        umbrellaField = Objects.requireNonNull(this.client).getEntityModelLoader().getModelPart(UmbrellasClient.UMBRELLA);
        time = 0;
    }

    @Inject(method = "getRows", at = @At("RETURN"), cancellable = true)
    private void getRowsUmbrella(CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue(usingUmbrellas ? getUmbrellaRows() : callbackInfoReturnable.getReturnValue());
    }

    private int getUmbrellaRows() {
        return MathHelper.ceilDiv(((LoomScreenHandlerAccess)this.handler).getUmbrellaPatterns().size(), 4);
    }

    @ModifyArg(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"), index = 0)
    private Identifier drawUmbrellaTexture(Identifier texture) {
        return usingUmbrellas ? UMBRELLA_TEXTURE : texture;
    }

    @Inject(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/DiffuseLighting;disableGuiDepthLighting()V"), cancellable = true)
    private void drawUmbrellaBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo info) {
        time++;
        if (usingUmbrellas) {
            int x = this.x;
            int y = this.y;
            Slot outputSlot = this.handler.getOutputSlot();

            DiffuseLighting.disableGuiDepthLighting();
            if (this.umbrellaPatterns != null && !this.hasTooManyPatterns) {
                context.getMatrices().push();
                context.getMatrices().translate(x + 139.6, y + 16, 0);
                context.getMatrices().scale(24.0f, 24.0f, -24.0f);
                this.umbrellaField.pitch = (float)PneumonoMathHelper.toRadians(90);
                this.umbrellaField.yaw = 0f;
                this.umbrellaField.roll = 0f;
                UmbrellaModel.renderUmbrella(context.getMatrices(), context.getVertexConsumers(), 0xF000F0, OverlayTexture.DEFAULT_UV, this.umbrellaField, UmbrellasClient.UMBRELLA_BASE, this.umbrellaPatterns, false);
                context.getMatrices().pop();
                context.draw();
            } else if (this.hasTooManyPatterns) {
                context.drawTexture(UMBRELLA_TEXTURE, x + outputSlot.x - 2, y + outputSlot.y - 2, this.backgroundWidth, 17, 17, 16);
            }
            if (this.canApplyDyePattern) {
                int x2 = x + 60;
                int y2 = y + 13;
                List<RegistryEntry<UmbrellaPattern>> list = ((LoomScreenHandlerAccess)this.handler).getUmbrellaPatterns();
                block0:
                for (int n = 0; n < 4; ++n) {
                    for (int o = 0; o < 4; ++o) {
                        int q = (n + this.visibleTopRow) * 4 + o;
                        if (q >= list.size()) break block0;
                        int r = x2 + o * 14;
                        int s = y2 + n * 14;
                        boolean bl = mouseX >= r && mouseY >= s && mouseX < r + 14 && mouseY < s + 14;
                        int t = q == this.handler.getSelectedPattern() ? this.backgroundHeight + 14 : (bl ? this.backgroundHeight + 28 : this.backgroundHeight);
                        context.drawTexture(UMBRELLA_TEXTURE, r, s, 0, t, 14, 14);
                        this.drawUmbrella(context, list.get(q), r, s);
                    }
                }
            }
            DiffuseLighting.enableGuiDepthLighting();

            info.cancel();
        }
    }

    private void drawUmbrella(DrawContext context, RegistryEntry<UmbrellaPattern> pattern, int x, int y) {
        NbtList nbtList = new NbtList();

        NbtCompound patternCompound1 = new NbtCompound();
        patternCompound1.putString("Pattern", Objects.requireNonNull(UmbrellaPattern.byId("base")).value().id());
        patternCompound1.putInt("Color", DyeColor.GRAY.getId());
        nbtList.add(patternCompound1);
        NbtCompound patternCompound2 = new NbtCompound();
        patternCompound2.putString("Pattern", pattern.value().id());
        patternCompound2.putInt("Color", DyeColor.WHITE.getId());
        nbtList.add(patternCompound2);

        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.put("Patterns", nbtList);
        ItemStack itemStack = new ItemStack(UmbrellasRegistry.UMBRELLA);
        itemStack.setNbt(nbtCompound);

        MatrixStack matrixStack = new MatrixStack();
        matrixStack.push();
        matrixStack.translate(x + 4, y + 4, 0.0f);
        matrixStack.scale(6.0f, 6.0f, -6.0f);
        this.umbrellaField.pitch = (float)PneumonoMathHelper.toRadians(90);
        this.umbrellaField.yaw = 0f;
        this.umbrellaField.roll = 0f;
        List<Pair<RegistryEntry<UmbrellaPattern>, DyeColor>> list = PatternableUmbrellaItem.getPatternsFromNbt(itemStack);
        UmbrellaModel.renderUmbrella(matrixStack, context.getVertexConsumers(), 0xF000F0, OverlayTexture.DEFAULT_UV, this.umbrellaField, UmbrellasClient.UMBRELLA_BASE, list, false);
        matrixStack.pop();
        context.draw();
    }

    @Inject(method = "onInventoryChanged", at = @At("HEAD"), cancellable = true)
    private void onUmbrellaInventoryChanged(CallbackInfo info) {
        ItemStack outputStack = this.handler.getOutputSlot().getStack();
        ItemStack umbrellaStack = this.handler.getBannerSlot().getStack();
        if (umbrellaStack.getItem() instanceof PatternableUmbrellaItem) {
            usingUmbrellas = true;
            this.umbrellaPatterns = outputStack.isEmpty() ? null : PatternableUmbrellaItem.getPatternsFromNbt(outputStack);
            ItemStack dyeStack = this.handler.getDyeSlot().getStack();
            ItemStack patternStack = this.handler.getPatternSlot().getStack();
            NbtCompound nbtCompound = umbrellaStack.getNbt();
            this.hasTooManyPatterns = nbtCompound != null && nbtCompound.contains("Patterns", NbtElement.LIST_TYPE) && !umbrellaStack.isEmpty() && nbtCompound.getList("Patterns", NbtElement.COMPOUND_TYPE).size() >= 6;
            if (this.hasTooManyPatterns) {
                this.umbrellaPatterns = null;
            }
            if (!(ItemStack.areEqual(umbrellaStack, this.banner) && ItemStack.areEqual(dyeStack, this.dye) && ItemStack.areEqual(patternStack, this.pattern))) {
                boolean isPride = patternStack.getItem() instanceof PrideUmbrellaPatternItem;
                this.canApplyDyePattern = !umbrellaStack.isEmpty() && (!dyeStack.isEmpty() || isPride) && !this.hasTooManyPatterns && !((LoomScreenHandlerAccess)this.handler).getUmbrellaPatterns().isEmpty();
            }
            if (this.visibleTopRow >= this.getUmbrellaRows()) {
                this.visibleTopRow = 0;
                this.scrollPosition = 0.0f;
            }
            this.banner = umbrellaStack.copy();
            this.dye = dyeStack.copy();
            this.pattern = patternStack.copy();

            info.cancel();
        } else {
            usingUmbrellas = false;
        }
    }
}
