package net.pneumono.umbrellas.mixin;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.pneumono.umbrellas.content.PatternableUmbrellaItem;
import net.pneumono.umbrellas.patterns.PrideUmbrellaPatternItem;
import net.pneumono.umbrellas.patterns.UmbrellaPatternItem;
import net.pneumono.umbrellas.patterns.LoomScreenHandlerAccess;
import net.pneumono.umbrellas.patterns.PatternRegistry;
import net.pneumono.umbrellas.patterns.UmbrellaPattern;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LoomScreenHandler.class)
@SuppressWarnings("unused")
public abstract class LoomHandlerMixin extends ScreenHandler implements LoomScreenHandlerAccess {
    @Shadow
    @Final
    private ScreenHandlerContext context;
    @Shadow
    @Final
    private Inventory input;
    @Shadow
    @Final
    private Inventory output;
    @Shadow
    @Final
    @Mutable
    Slot bannerSlot;
    @Shadow
    @Final
    Slot dyeSlot;
    @Shadow
    long lastTakeResultTime;
    @Shadow
    @Final
    @Mutable
    @SuppressWarnings("FieldCanBeLocal")
    private Slot patternSlot;
    @Shadow
    @Final
    private Slot outputSlot;
    @Shadow
    @Final
    Property selectedPattern;

    private List<RegistryEntry<UmbrellaPattern>> umbrellaPatterns = List.of();
    private boolean usingUmbrella = false;

    protected LoomHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/screen/LoomScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;", ordinal = 0), index = 0
    )
    private Slot bannerAndUmbrellaSlot(Slot slot) {
        return new Slot(input, 0, 13, 26){

            @Override
            public boolean canInsert(ItemStack stack) {
                return slot.canInsert(stack) || stack.getItem() instanceof PatternableUmbrellaItem;
            }
        };
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/screen/LoomScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;", ordinal = 2), index = 0
    )
    private Slot bothPatternsSlot(Slot slot) {
        return new Slot(input, 2, 23, 45){

            @Override
            public boolean canInsert(ItemStack stack) {
                return slot.canInsert(stack) || stack.getItem() instanceof UmbrellaPatternItem;
            }
        };
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/screen/LoomScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;", ordinal = 3), index = 0
    )
    private Slot bothOutputSlot(Slot slot) {
        return new Slot(this.output, 0, 143, 58){

            @Override
            public boolean canInsert(ItemStack stack) {
                return slot.canInsert(stack);
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                bannerSlot.takeStack(1);
                boolean shouldTakeDye = !(patternSlot.getStack().getItem() instanceof PrideUmbrellaPatternItem);
                if (shouldTakeDye) {
                    dyeSlot.takeStack(1);
                }
                if (!bannerSlot.hasStack() || (!dyeSlot.hasStack() && shouldTakeDye)) {
                    selectedPattern.set(-1);
                }
                context.run((world, pos) -> {
                    long l = world.getTime();
                    if (lastTakeResultTime != l) {
                        world.playSound(null, pos, SoundEvents.UI_LOOM_TAKE_RESULT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        lastTakeResultTime = l;
                    }
                });
                super.onTakeItem(player, stack);
            }
        };
    }

    @Inject(method = "onButtonClick", at = @At("HEAD"), cancellable = true)
    public void onButtonClickUmbrella(PlayerEntity player, int id, CallbackInfoReturnable<Boolean> infoReturnable) {
        if (usingUmbrella && id >= 0 && id < umbrellaPatterns.size()) {
            selectedPattern.set(id);
            updateUmbrellaOutputSlot(umbrellaPatterns.get(id));
            infoReturnable.setReturnValue(true);
        }
    }

    private List<RegistryEntry<UmbrellaPattern>> getUmbrellaPatternsFor(ItemStack stack) {
        if (stack.isEmpty()) {
            return PatternRegistry.UMBRELLA_PATTERN.getEntryList(PatternRegistry.NO_ITEM_REQUIRED).map(ImmutableList::copyOf).orElse(ImmutableList.of());
        }
        Item item = stack.getItem();
        if (item instanceof UmbrellaPatternItem umbrellaPatternItem) {
            return PatternRegistry.UMBRELLA_PATTERN.getEntryList(umbrellaPatternItem.getPatternItemTag()).map(ImmutableList::copyOf).orElse(ImmutableList.of());
        }
        return List.of();
    }

    private boolean isUmbrellaPatternIndexValid(int index) {
        return index >= 0 && index < this.umbrellaPatterns.size();
    }

    @Inject(method = "onContentChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"), cancellable = true)
    private void onUmbrellaContentChanged(CallbackInfo callbackInfo, @Local(ordinal = 0) ItemStack umbrellaStack, @Local(ordinal = 1) ItemStack dyeStack, @Local(ordinal = 2) ItemStack patternStack) {
        if (umbrellaStack.getItem() instanceof PatternableUmbrellaItem) {
            if (dyeStack.isEmpty() && !(patternStack.getItem() instanceof PrideUmbrellaPatternItem)) {
                this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
                this.umbrellaPatterns = List.of();
                this.selectedPattern.set(-1);
                callbackInfo.cancel();
            }

            usingUmbrella = true;
            RegistryEntry<UmbrellaPattern> registryEntry;

            int index = selectedPattern.get();
            boolean indexValid = isUmbrellaPatternIndexValid(index);
            List<RegistryEntry<UmbrellaPattern>> list = umbrellaPatterns;
            umbrellaPatterns = getUmbrellaPatternsFor(patternStack);
            if (umbrellaPatterns.size() == 1) {
                this.selectedPattern.set(0);
                registryEntry = umbrellaPatterns.get(0);
            } else if (!indexValid) {
                this.selectedPattern.set(-1);
                registryEntry = null;
            } else {
                RegistryEntry<UmbrellaPattern> registryEntry2 = list.get(index);
                int index2 = umbrellaPatterns.indexOf(registryEntry2);
                if (index2 != -1) {
                    registryEntry = registryEntry2;
                    this.selectedPattern.set(index2);
                } else {
                    registryEntry = null;
                    this.selectedPattern.set(-1);
                }
            }
            if (registryEntry != null) {
                NbtCompound nbtCompound = umbrellaStack.getNbt();
                boolean tooManyPatterns = nbtCompound != null && nbtCompound.contains("Patterns", NbtElement.LIST_TYPE) && !umbrellaStack.isEmpty() && nbtCompound.getList("Patterns", NbtElement.COMPOUND_TYPE).size() >= 6;
                if (tooManyPatterns) {
                    this.selectedPattern.set(-1);
                    this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
                } else {
                    updateUmbrellaOutputSlot(registryEntry);
                }
            } else {
                this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
            }
            this.sendContentUpdates();
            callbackInfo.cancel();
        } else {
            usingUmbrella = false;
        }
    }

    @ModifyConstant(method = "quickMove", constant = @Constant(classValue = BannerItem.class))
    private boolean umbrellaItemCheck(Object reference, Class<?> bannerClass) {
        return reference instanceof BannerItem || reference instanceof PatternableUmbrellaItem;
    }

    @ModifyConstant(method = "quickMove", constant = @Constant(classValue = BannerPatternItem.class))
    private boolean umbrellaPatternCheck(Object reference, Class<?> bannerPatternClass) {
        return reference instanceof BannerPatternItem || reference instanceof UmbrellaPatternItem;
    }

    private void updateUmbrellaOutputSlot(RegistryEntry<UmbrellaPattern> pattern) {
        ItemStack umbrellaStack = bannerSlot.getStack();
        ItemStack dyeStack = dyeSlot.getStack();
        ItemStack newUmbrellaStack = ItemStack.EMPTY;
        if (!umbrellaStack.isEmpty() && (!dyeStack.isEmpty() || !pattern.value().colored())) {
            NbtList nbtList;
            newUmbrellaStack = umbrellaStack.copy();
            DyeColor dyeColor = dyeStack.getItem() instanceof DyeItem item ? item.getColor() : DyeColor.WHITE;

            NbtCompound nbtCompound = newUmbrellaStack.getNbt();
            if (nbtCompound != null && nbtCompound.contains("Patterns", NbtElement.LIST_TYPE)) {
                nbtList = nbtCompound.getList("Patterns", NbtElement.COMPOUND_TYPE);
            } else {
                nbtList = new NbtList();
                if (nbtCompound == null) {
                    nbtCompound = new NbtCompound();
                }
                nbtCompound.put("Patterns", nbtList);
            }
            NbtCompound newPattern = new NbtCompound();
            newPattern.putString("Pattern", pattern.value().id());
            newPattern.putInt("Color", dyeColor.getId());
            nbtList.add(newPattern);
            newUmbrellaStack.getNbt().put("Patterns", nbtList);
        }
        if (!ItemStack.areEqual(newUmbrellaStack, outputSlot.getStack())) {
            outputSlot.setStackNoCallbacks(newUmbrellaStack);
        }
    }

    @Override
    public List<RegistryEntry<UmbrellaPattern>> getUmbrellaPatterns() {
        return umbrellaPatterns;
    }
}
