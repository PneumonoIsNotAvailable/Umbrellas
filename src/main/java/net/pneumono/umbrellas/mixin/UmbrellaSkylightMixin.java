package net.pneumono.umbrellas.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.pneumono.umbrellas.content.UmbrellaItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(World.class)
public abstract class UmbrellaSkylightMixin implements WorldAccess {
    @Override
    public boolean isSkyVisible(BlockPos pos) {
        return !UmbrellaItem.isUnderUmbrella(((World)(Object)this), pos) && WorldAccess.super.isSkyVisible(pos);
    }
}
