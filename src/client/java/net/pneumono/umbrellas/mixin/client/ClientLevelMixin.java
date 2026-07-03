package net.pneumono.umbrellas.mixin.client;

//? if >=26.2 {
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.pneumono.umbrellas.util.UmbrellaUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin {
    @WrapOperation(
            method = "tickWeatherEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/ClientLevel;getHeightmapPos(Lnet/minecraft/world/level/levelgen/Heightmap$Types;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/BlockPos;"
            )
    )
    private BlockPos blockRainParticlesWithUmbrella(ClientLevel level, Heightmap.Types types, BlockPos pos, Operation<BlockPos> original) {
            int x = pos.getX();
            int z = pos.getZ();
            return new BlockPos(x, UmbrellaUtils.getShelteredHeight(level, x, original.call(level, types, pos).getY(), z), z
        );
    }
}
//?}