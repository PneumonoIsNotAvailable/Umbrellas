package net.pneumono.umbrellas.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.pneumono.umbrellas.util.UmbrellaUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=1.21.6 {
import net.minecraft.client.renderer.WeatherEffectRenderer;
//?} else {
/*import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
*///?}

//? if >=1.21.6 {
@Mixin(WeatherEffectRenderer.class)
//?} else {
/*@Mixin(LevelRenderer.class)
*///?}
public abstract class WeatherRendererMixin {
    //? if <1.21.6
    //@Shadow private @Nullable ClientLevel level;

    @WrapOperation(
            //? if >=1.21.9 {
            method = "extractRenderState",
            //?} else if >=1.21.6 {
            /*method = "collectColumnInstances",
            *///?} else {
            /*method = "renderSnowAndRain",
            *///?}
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getHeight(Lnet/minecraft/world/level/levelgen/Heightmap$Types;II)I"
            )
    )
    private int blockRainWithUmbrella(Level level, Heightmap.Types types, int x, int z, Operation<Integer> original) {
        return UmbrellaUtils.getShelteredHeight(level, x, original.call(level, types, x, z), z);
    }

    @WrapOperation(
            //? if >=1.21.6 {
            method = "tickRainParticles",
            //?} else {
            /*method = "tickRain",
            *///?}
            at = @At(
                    value = "INVOKE",
                    //? if >=1.21.6 {
                    target = "Lnet/minecraft/client/multiplayer/ClientLevel;getHeightmapPos(Lnet/minecraft/world/level/levelgen/Heightmap$Types;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/BlockPos;"
                    //?} else {
                    /*target = "Lnet/minecraft/world/level/LevelReader;getHeightmapPos(Lnet/minecraft/world/level/levelgen/Heightmap$Types;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/BlockPos;"
                    *///?}
            )
    )
    private BlockPos blockRainParticlesWithUmbrella(
            /*? if >=1.21.6 {*/ClientLevel/*?} else {*//*LevelReader*//*?}*/ level,
            Heightmap.Types types, BlockPos pos, Operation<BlockPos> original
    ) {
        int x = pos.getX();
        int z = pos.getZ();
        return new BlockPos(x, UmbrellaUtils.getShelteredHeight(
                /*? if >=1.21.6 {*/level/*?} else {*//*this.level*//*?}*/,
                x, original.call(level, types, pos).getY(), z), z
        );
    }
}
