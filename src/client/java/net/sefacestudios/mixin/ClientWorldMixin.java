package net.sefacestudios.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import net.sefacestudios.fogpack.FogpackManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {

    @ModifyReturnValue(method = "getCloudsColor", at = @At("RETURN"))
    private Vec3d $getCloudsColor(Vec3d original) {
        Integer rgbFogColor = FogpackManager.getAppliedFogpack().getConfig().getClouds().getColor();
        return rgbFogColor != null ? Vec3d.unpackRgb(rgbFogColor) : original;
    }
}
