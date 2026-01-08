package net.pneumono.umbrellas.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.ticks.ContainerSingleItem;
import net.pneumono.umbrellas.registry.UmbrellasBlocks;
import net.pneumono.umbrellas.registry.UmbrellasMisc;
import net.pneumono.umbrellas.registry.UmbrellasTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UmbrellaStandBlockEntity extends BlockEntity implements ContainerSingleItem.BlockContainerSingleItem {
    private ItemStack umbrellaStack = ItemStack.EMPTY;

    public UmbrellaStandBlockEntity(BlockPos pos, BlockState state) {
        super(UmbrellasBlocks.UMBRELLA_STAND_BLOCK_ENTITY, pos, state);
    }

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

    @Override
    public @NotNull ItemStack getTheItem() {
        return this.umbrellaStack;
    }

    @Override
    public void setTheItem(ItemStack stack) {
        this.umbrellaStack = stack;
        this.setChanged();
        this.updateClients(stack.isEmpty());
    }

    public boolean hasStack() {
        return !this.umbrellaStack.isEmpty();
    }

    @Override
    public @NotNull ItemStack removeTheItem() {
        ItemStack stack = this.umbrellaStack;
        setTheItem(ItemStack.EMPTY);
        return stack;
    }

    @Override
    public @NotNull ItemStack splitTheItem(int count) {
        ItemStack stack = BlockContainerSingleItem.super.splitTheItem(count);
        updateClients(true);
        return stack;
    }

    public void updateClients(boolean pickup) {
        Level level = this.getLevel();
        if (level != null) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
            SoundEvent sound = pickup ? UmbrellasMisc.UMBRELLA_STAND_PICKUP_SOUND : UmbrellasMisc.UMBRELLA_STAND_INSERT_SOUND;
            level.playSound(null, getBlockPos(), sound, SoundSource.BLOCKS);
            level.gameEvent(GameEvent.BLOCK_CHANGE, getBlockPos(), GameEvent.Context.of(getBlockState()));
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

    @Override
    public @NotNull BlockEntity getContainerBlockEntity() {
        return this;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveWithoutMetadata(provider);
    }
}
