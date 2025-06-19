package net.pneumono.umbrellas.mixin;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.DyeColor;
import net.pneumono.umbrellas.content.item.PatternableUmbrellaItem;
import net.pneumono.umbrellas.content.item.component.ProvidesUmbrellaPatterns;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import net.pneumono.umbrellas.util.LoomScreenHandlerAccess;
import net.pneumono.umbrellas.content.UmbrellaPattern;
import net.pneumono.umbrellas.registry.UmbrellaPatterns;
import net.pneumono.umbrellas.registry.UmbrellasTags;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LoomScreenHandler.class)
public abstract class LoomScreenHandlerMixin extends ScreenHandler implements LoomScreenHandlerAccess {
    protected LoomScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Shadow
    @Final
    Property selectedPattern;
    @Final
    @Shadow
    Slot bannerSlot;
    @Shadow
    @Final
    Slot dyeSlot;
    @Shadow
    @Final
    private Slot patternSlot;
    @Shadow
    @Final
    private Slot outputSlot;
    @Shadow
    private List<RegistryEntry<BannerPattern>> bannerPatterns = List.of();

    @Shadow
    protected abstract boolean isPatternIndexValid(int index);

    @Unique
    private List<RegistryEntry<UmbrellaPattern>> umbrellaPatterns = List.of();
    @Unique
    private RegistryEntryLookup<UmbrellaPattern> umbrellaPatternLookup;
    @Unique
    private boolean isUsingUmbrellas = false;

    @Inject(
            method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V",
            at = @At("RETURN")
    )
    private void setUmbrellaPatternLookup(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, CallbackInfo ci) {
        this.umbrellaPatternLookup = playerInventory.player.getRegistryManager().getOrThrow(UmbrellaPatterns.UMBRELLA_PATTERN_KEY);
    }

    @Inject(
            method = "onButtonClick",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onButtonClick(PlayerEntity player, int id, CallbackInfoReturnable<Boolean> cir) {
        if (!this.isUsingUmbrellas) return;

        if (id >= 0 && id < this.umbrellaPatterns.size()) {
            this.selectedPattern.set(id);
            this.updateOutputSlot(this.umbrellaPatterns.get(id));
            cir.setReturnValue(true);
        }
    }

    @Unique
    private List<RegistryEntry<UmbrellaPattern>> getUmbrellaPatternsFor(ItemStack stack) {
        if (stack.isEmpty()) {
            return this.umbrellaPatternLookup
                    .getOptional(UmbrellasTags.NO_ITEM_REQUIRED)
                    .map(ImmutableList::copyOf)
                    .orElse(ImmutableList.of());
        } else {
            TagKey<UmbrellaPattern> tagKey = stack.getOrDefault(
                    UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS,
                    ProvidesUmbrellaPatterns.DEFAULT
            ).patterns();
            if (tagKey == null) return List.of();
            return this.umbrellaPatternLookup.getOptional(tagKey).map(ImmutableList::copyOf).orElse(ImmutableList.of());
        }
    }

    @Inject(
            method = "onContentChanged",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onContentChanged(Inventory inventory, CallbackInfo ci) {
        ItemStack inputStack = this.bannerSlot.getStack();
        ItemStack dyeStack = this.dyeSlot.getStack();
        ItemStack patternStack = this.patternSlot.getStack();

        if (!(inputStack.getItem() instanceof PatternableUmbrellaItem)) {
            this.isUsingUmbrellas = false;
            this.umbrellaPatterns = List.of();
            return;
        }

        this.bannerPatterns = List.of();
        if (!inputStack.isEmpty()) {
            this.isUsingUmbrellas = true;
            boolean hasDye = !dyeStack.isEmpty();

            int patternIndex = this.selectedPattern.get();
            boolean indexValid = this.isPatternIndexValid(patternIndex);

            List<RegistryEntry<UmbrellaPattern>> oldPatterns = this.umbrellaPatterns;
            this.umbrellaPatterns = this.getUmbrellaPatternsFor(patternStack).stream().filter(
                    pattern -> pattern.value().dyeable() == hasDye
            ).toList();

            RegistryEntry<UmbrellaPattern> selectedPattern;
            if (this.umbrellaPatterns.size() == 1) {
                this.selectedPattern.set(0);
                selectedPattern = this.umbrellaPatterns.getFirst();

            } else if (!indexValid) {
                this.selectedPattern.set(-1);
                selectedPattern = null;

            } else {
                RegistryEntry<UmbrellaPattern> possiblePattern = oldPatterns.get(patternIndex);
                int indexOfPossible = this.umbrellaPatterns.indexOf(possiblePattern);

                if (indexOfPossible != -1) {
                    selectedPattern = possiblePattern;
                    this.selectedPattern.set(indexOfPossible);

                } else {
                    selectedPattern = null;
                    this.selectedPattern.set(-1);
                }
            }

            if (selectedPattern != null) {
                UmbrellaPatternsComponent umbrellaPatternsComponent = inputStack.getOrDefault(
                        UmbrellasDataComponents.UMBRELLA_PATTERNS,
                        UmbrellaPatternsComponent.DEFAULT
                );
                boolean tooManyLayers = umbrellaPatternsComponent.layers().size() >= UmbrellaPatternsComponent.MAX_PATTERNS;
                if (tooManyLayers) {
                    this.selectedPattern.set(-1);
                    this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
                } else {
                    this.updateOutputSlot(selectedPattern);
                }
            } else {
                this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
            }

            this.sendContentUpdates();
        } else {
            this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
            this.isUsingUmbrellas = false;
            this.umbrellaPatterns = List.of();
            this.selectedPattern.set(-1);
        }

        ci.cancel();
    }

    @Override
    public List<RegistryEntry<UmbrellaPattern>> umbrellas$getUmbrellaPatterns() {
        return umbrellaPatterns;
    }

    @WrapOperation(
            method = "quickMove",
            constant = @Constant(classValue = BannerItem.class)
    )
    private boolean slotHasBannerOrUmbrella(Object object, Operation<Boolean> original) {
        return original.call(object) || object instanceof PatternableUmbrellaItem;
    }

    @WrapOperation(
            method = "quickMove",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;contains(Lnet/minecraft/component/ComponentType;)Z"
            )
    )
    private boolean slotHasPatternItem(ItemStack itemStack, ComponentType<?> componentType, Operation<Boolean> original) {
        return original.call(itemStack, componentType) || itemStack.contains(UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS);
    }

    @Unique
    private void updateOutputSlot(RegistryEntry<UmbrellaPattern> pattern) {
        ItemStack inputStack = this.bannerSlot.getStack();
        ItemStack dyeStack = this.dyeSlot.getStack();
        ItemStack empty = ItemStack.EMPTY;
        if (!inputStack.isEmpty()) {
            empty = inputStack.copyWithCount(1);
            DyeColor dyeColor = dyeStack.getItem() instanceof DyeItem dyeItem ? dyeItem.getColor() : DyeColor.WHITE;
            empty.apply(
                    UmbrellasDataComponents.UMBRELLA_PATTERNS,
                    UmbrellaPatternsComponent.DEFAULT,
                    component -> new UmbrellaPatternsComponent.Builder().addAll(component).add(pattern, dyeColor).build()
            );
        }

        if (!ItemStack.areEqual(empty, this.outputSlot.getStack())) {
            this.outputSlot.setStackNoCallbacks(empty);
        }
    }

    @Mixin(targets = "net/minecraft/screen/LoomScreenHandler$3")
    public static abstract class BannerSlotMixin {
        @ModifyReturnValue(
                method = "canInsert(Lnet/minecraft/item/ItemStack;)Z",
                at = @At("RETURN")
        )
        private boolean canInsertWithUmbrella(boolean original, ItemStack stack) {
            return original || stack.getItem() instanceof PatternableUmbrellaItem;
        }
    }

    @Mixin(targets = "net/minecraft/screen/LoomScreenHandler$5")
    public static abstract class PatternSlotMixin {
        @ModifyReturnValue(
                method = "canInsert(Lnet/minecraft/item/ItemStack;)Z",
                at = @At("RETURN")
        )
        private boolean canInsertWithUmbrella(boolean original, ItemStack stack) {
            return original || stack.contains(UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS);
        }
    }
}
