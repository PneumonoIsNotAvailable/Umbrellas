package net.pneumono.umbrellas.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.pneumono.umbrellas.util.UmbrellaUtils;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Level.class)
public abstract class UmbrellaSkylightMixin implements LevelAccessor {
    @Override
    public boolean canSeeSky(BlockPos pos) {
        if (LevelAccessor.super.canSeeSky(pos)) {
            return !UmbrellaUtils.isUnderUmbrella((Level)(Object)this, pos, true);
        }
        return false;
    }
}
