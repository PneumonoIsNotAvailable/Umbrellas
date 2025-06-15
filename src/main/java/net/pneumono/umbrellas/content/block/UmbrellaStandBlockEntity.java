package net.pneumono.umbrellas.content.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SingleStackInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.pneumono.umbrellas.registry.UmbrellasBlocks;
import net.pneumono.umbrellas.registry.UmbrellasTags;
import org.jetbrains.annotations.Nullable;

public class UmbrellaStandBlockEntity extends BlockEntity implements SingleStackInventory.SingleStackBlockEntityInventory {
    private ItemStack umbrellaStack = ItemStack.EMPTY;

    public UmbrellaStandBlockEntity(BlockPos pos, BlockState state) {
        super(UmbrellasBlocks.UMBRELLA_STAND_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        RegistryOps<NbtElement> registryOps = registries.getOps(NbtOps.INSTANCE);
        this.umbrellaStack = nbt.get("UmbrellaItem", ItemStack.CODEC, registryOps).orElse(ItemStack.EMPTY);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        if (!this.umbrellaStack.isEmpty()) {
            RegistryOps<NbtElement> registryOps = registries.getOps(NbtOps.INSTANCE);
            nbt.put("UmbrellaItem", ItemStack.CODEC, registryOps, this.umbrellaStack);
        }
    }

    @Override
    public ItemStack getStack() {
        return this.umbrellaStack;
    }

    @Override
    public void setStack(ItemStack stack) {
        this.umbrellaStack = stack;
        this.markDirty();
    }

    public boolean hasStack() {
        return !this.umbrellaStack.isEmpty();
    }

    public ItemStack removeStack() {
        ItemStack stack = this.umbrellaStack;
        this.umbrellaStack = ItemStack.EMPTY;
        this.markDirty();
        return stack;
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

    public int getComparatorOutput() {
        return this.umbrellaStack.isEmpty() ? 0 : 15;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return stack.isIn(UmbrellasTags.UMBRELLAS) && this.getStack(slot).isEmpty();
    }

    @Override
    public boolean canTransferTo(Inventory hopperInventory, int slot, ItemStack stack) {
        return hopperInventory.containsAny(ItemStack::isEmpty);
    }

    @Override
    public BlockEntity asBlockEntity() {
        return this;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }
}
