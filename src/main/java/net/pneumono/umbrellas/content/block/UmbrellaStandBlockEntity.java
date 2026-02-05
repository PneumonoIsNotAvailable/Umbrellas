package net.pneumono.umbrellas.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.ticks.ContainerSingleItem;
import net.pneumono.umbrellas.registry.UmbrellasBlocks;
import net.pneumono.umbrellas.registry.UmbrellasTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//? if >=1.21.6 {
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
//?} else {
/*import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
*///?}

//? if >=1.21 {
import net.minecraft.core.HolderLookup;
//?} else {
/*import java.util.Optional;
*///?}

public class UmbrellaStandBlockEntity extends BlockEntity implements ContainerSingleItem {
    private ItemStack umbrellaStack = ItemStack.EMPTY;

    public UmbrellaStandBlockEntity(BlockPos pos, BlockState state) {
        super(UmbrellasBlocks.UMBRELLA_STAND_BLOCK_ENTITY, pos, state);
    }

    //? if >=1.21.6 {
    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.umbrellaStack = input.read("UmbrellaItem", ItemStack.CODEC).orElse(ItemStack.EMPTY);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (this.umbrellaStack != null && !this.umbrellaStack.isEmpty()) {
            output.store("UmbrellaItem", ItemStack.CODEC, this.umbrellaStack);
        }
    }
    //?} else if >=1.21 {
    /*@Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        if (compoundTag.contains("UmbrellaItem")) {
            RegistryOps<Tag> ops = provider.createSerializationContext(NbtOps.INSTANCE);
            this.umbrellaStack = ItemStack.CODEC
                    .decode(ops, compoundTag.get("UmbrellaItem"))
                    .mapOrElse(Pair::getFirst, error -> ItemStack.EMPTY);
        } else {
            this.umbrellaStack = ItemStack.EMPTY;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        if (!this.umbrellaStack.isEmpty()) {
            RegistryOps<Tag> ops = provider.createSerializationContext(NbtOps.INSTANCE);
            Tag tag = ItemStack.CODEC.encodeStart(ops, this.umbrellaStack).mapOrElse(nbt -> nbt, error -> null);
            if (tag != null) {
                compoundTag.put("UmbrellaItem", tag);
            }
        }
    }
    *///?} else {
    /*@Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        if (compoundTag.contains("UmbrellaItem")) {
            Optional<ItemStack> optional = ItemStack.CODEC
                    .decode(NbtOps.INSTANCE, compoundTag.get("UmbrellaItem"))
                    .map(Pair::getFirst)
                    .get().left();
            optional.ifPresent(stack -> this.umbrellaStack = stack);
        } else {
            this.umbrellaStack = ItemStack.EMPTY;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        if (!this.umbrellaStack.isEmpty()) {
            ItemStack.CODEC
                    .encodeStart(NbtOps.INSTANCE, this.umbrellaStack)
                    .get().ifLeft(tag -> compoundTag.put("UmbrellaItem", tag));
        }
    }
    *///?}

    public @NotNull ItemStack getTheItem() {
        return getItem(0);
    }

    @Override
    public ItemStack getItem(int i) {
        return this.umbrellaStack;
    }

    public void setTheItem(ItemStack stack) {
        setItem(0, stack);
    }

    @Override
    public void setItem(int i, ItemStack stack) {
        this.umbrellaStack = stack;
        updateClients();
    }

    public boolean hasStack() {
        return !this.umbrellaStack.isEmpty();
    }

    public @NotNull ItemStack removeTheItem() {
        return removeItem(0, 1);
    }

    @Override
    public ItemStack removeItem(int i, int j) {
        if (i > 0 || j <= 0) return ItemStack.EMPTY;
        ItemStack stack = this.umbrellaStack.split(j);
        updateClients();
        return stack;
    }

    public void updateClients() {
        Level level = this.getLevel();
        if (level != null) {
            BlockPos pos = getBlockPos();
            BlockState state = getBlockState();
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
            level.updateNeighborsAt(pos, state.getBlock());
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
            setChanged(level, pos, state);
        }
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    public int getComparatorOutput() {
        return this.umbrellaStack.isEmpty() ? 0 : 15;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return stack.is(UmbrellasTags.UMBRELLAS) && this.getItem(slot).isEmpty();
    }

    @Override
    public boolean canTakeItem(Container hopperContainer, int slot, ItemStack stack) {
        return hopperContainer.hasAnyMatching(ItemStack::isEmpty);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(/*? if >=1.21 {*/HolderLookup.Provider provider/*?}*/) {
        return saveWithoutMetadata(/*? if >=1.21 {*/provider/*?}*/);
    }
}
