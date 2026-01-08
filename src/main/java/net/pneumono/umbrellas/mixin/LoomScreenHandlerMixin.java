package net.pneumono.umbrellas.mixin;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPattern;
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

@Mixin(LoomMenu.class)
public abstract class LoomScreenHandlerMixin extends AbstractContainerMenu implements LoomScreenHandlerAccess {
    protected LoomScreenHandlerMixin(@Nullable MenuType<?> type, int syncId) {
        super(type, syncId);
    }

    @Shadow
    @Final
    DataSlot selectedBannerPatternIndex;
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
    private Slot resultSlot;
    @Shadow
    private List<Holder<BannerPattern>> selectablePatterns = List.of();

    @Unique
    private List<Holder<UmbrellaPattern>> selectableUmbrellaPatterns = List.of();
    @Unique
    private Registry<UmbrellaPattern> umbrellaPatternLookup;
    @Unique
    private boolean isUsingUmbrellas = false;

    @Inject(
            method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V",
            at = @At("RETURN")
    )
    private void setUmbrellaPatternLookup(int syncId, Inventory inventory, ContainerLevelAccess access, CallbackInfo ci) {
        this.umbrellaPatternLookup = inventory.player.registryAccess().lookupOrThrow(UmbrellaPatterns.UMBRELLA_PATTERN_KEY);
    }

    @Inject(
            method = "clickMenuButton",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onButtonClick(Player player, int id, CallbackInfoReturnable<Boolean> cir) {
        if (!this.isUsingUmbrellas) return;

        if (id >= 0 && id < this.selectableUmbrellaPatterns.size()) {
            this.selectedBannerPatternIndex.set(id);
            this.setupResultSlot(this.selectableUmbrellaPatterns.get(id));
            cir.setReturnValue(true);
        }
    }

    @Unique
    private List<Holder<UmbrellaPattern>> getSelectablePatterns(ItemStack stack) {
        if (stack.isEmpty()) {
            return this.umbrellaPatternLookup
                    .get(UmbrellasTags.NO_ITEM_REQUIRED)
                    .map(ImmutableList::copyOf)
                    .orElse(ImmutableList.of());
        } else {
            TagKey<UmbrellaPattern> tagKey = stack.getOrDefault(
                    UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS,
                    ProvidesUmbrellaPatterns.DEFAULT
            ).patterns();
            if (tagKey == null) return List.of();
            return this.umbrellaPatternLookup
                    .get(tagKey)
                    .map(ImmutableList::copyOf)
                    .orElse(ImmutableList.of());
        }
    }

    @Unique
    private boolean isUmbrellaPatternIndexValid(int index) {
        return index >= 0 && index < this.selectableUmbrellaPatterns.size();
    }

    @Inject(
            method = "slotsChanged",
            at = @At("HEAD"),
            cancellable = true
    )
    public void slotsChanged(Container container, CallbackInfo ci) {
        ItemStack inputStack = this.bannerSlot.getItem();
        ItemStack dyeStack = this.dyeSlot.getItem();
        ItemStack patternStack = this.patternSlot.getItem();

        if (!(inputStack.is(UmbrellasTags.PATTERNABLE_UMBRELLAS))) {
            this.isUsingUmbrellas = false;
            this.selectableUmbrellaPatterns = List.of();
            return;
        }

        this.selectablePatterns = List.of();
        if (!inputStack.isEmpty()) {
            this.isUsingUmbrellas = true;
            boolean hasDye = !dyeStack.isEmpty();

            int patternIndex = this.selectedBannerPatternIndex.get();
            boolean indexValid = this.isUmbrellaPatternIndexValid(patternIndex);

            List<Holder<UmbrellaPattern>> oldPatterns = this.selectableUmbrellaPatterns;
            this.selectableUmbrellaPatterns = this.getSelectablePatterns(patternStack).stream().filter(
                    pattern -> pattern.value().dyeable() == hasDye
            ).toList();

            Holder<UmbrellaPattern> selectedPattern;
            if (this.selectableUmbrellaPatterns.size() == 1) {
                this.selectedBannerPatternIndex.set(0);
                selectedPattern = this.selectableUmbrellaPatterns.getFirst();

            } else if (!indexValid) {
                this.selectedBannerPatternIndex.set(-1);
                selectedPattern = null;

            } else {
                Holder<UmbrellaPattern> possiblePattern = oldPatterns.get(patternIndex);
                int indexOfPossible = this.selectableUmbrellaPatterns.indexOf(possiblePattern);

                if (indexOfPossible != -1) {
                    selectedPattern = possiblePattern;
                    this.selectedBannerPatternIndex.set(indexOfPossible);

                } else {
                    selectedPattern = null;
                    this.selectedBannerPatternIndex.set(-1);
                }
            }

            if (selectedPattern != null) {
                UmbrellaPatternsComponent umbrellaPatternsComponent = inputStack.getOrDefault(
                        UmbrellasDataComponents.UMBRELLA_PATTERNS,
                        UmbrellaPatternsComponent.DEFAULT
                );
                boolean tooManyLayers = umbrellaPatternsComponent.layers().size() >= UmbrellaPatternsComponent.MAX_PATTERNS;
                if (tooManyLayers) {
                    this.selectedBannerPatternIndex.set(-1);
                    this.resultSlot.setByPlayer(ItemStack.EMPTY);
                } else {
                    this.setupResultSlot(selectedPattern);
                }
            } else {
                this.resultSlot.setByPlayer(ItemStack.EMPTY);
            }

            this.broadcastChanges();
        } else {
            this.resultSlot.setByPlayer(ItemStack.EMPTY);
            this.isUsingUmbrellas = false;
            this.selectableUmbrellaPatterns = List.of();
            this.selectedBannerPatternIndex.set(-1);
        }

        ci.cancel();
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Unique
    @Override
    public List<Holder<UmbrellaPattern>> getUmbrellaPatterns() {
        return selectableUmbrellaPatterns;
    }

    @WrapOperation(
            method = "quickMoveStack",
            constant = @Constant(classValue = BannerItem.class)
    )
    private boolean slotHasBannerOrUmbrella(Object object, Operation<Boolean> original, @Local(ordinal = 1) ItemStack stack) {
        return original.call(object) || stack.is(UmbrellasTags.PATTERNABLE_UMBRELLAS);
    }

    @WrapOperation(
            method = "quickMoveStack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;has(Lnet/minecraft/core/component/DataComponentType;)Z"
            )
    )
    private boolean slotHasPatternItem(ItemStack itemStack, DataComponentType<?> componentType, Operation<Boolean> original) {
        return original.call(itemStack, componentType) || itemStack.has(UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS);
    }

    @Unique
    private void setupResultSlot(Holder<UmbrellaPattern> pattern) {
        ItemStack inputStack = this.bannerSlot.getItem();
        ItemStack dyeStack = this.dyeSlot.getItem();
        ItemStack empty = ItemStack.EMPTY;
        if (!inputStack.isEmpty()) {
            empty = inputStack.copyWithCount(1);
            DyeColor dyeColor = dyeStack.getItem() instanceof DyeItem dyeItem ? dyeItem.getDyeColor() : DyeColor.WHITE;
            empty.update(
                    UmbrellasDataComponents.UMBRELLA_PATTERNS,
                    UmbrellaPatternsComponent.DEFAULT,
                    component -> new UmbrellaPatternsComponent.Builder().copy(component).add(pattern, dyeColor).build()
            );
        }

        if (!ItemStack.isSameItemSameComponents(empty, this.resultSlot.getItem())) {
            this.resultSlot.setByPlayer(empty);
        }
    }

    @Mixin(targets = "net/minecraft/world/inventory/LoomMenu$3")
    public static abstract class BannerSlotMixin {
        @ModifyReturnValue(
                method = "mayPlace",
                at = @At("RETURN")
        )
        private boolean canInsertWithUmbrella(boolean original, ItemStack stack) {
            return original || stack.is(UmbrellasTags.PATTERNABLE_UMBRELLAS);
        }
    }

    @Mixin(targets = "net/minecraft/world/inventory/LoomMenu$5")
    public static abstract class PatternSlotMixin {
        @ModifyReturnValue(
                method = "mayPlace",
                at = @At("RETURN")
        )
        private boolean canInsertWithUmbrella(boolean original, ItemStack stack) {
            return original || stack.has(UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS);
        }
    }
}
