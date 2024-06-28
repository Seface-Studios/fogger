package net.sefacestudios.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.biome.BiomeEffects;
import net.sefacestudios.FoggerClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(BiomeEffects.class)
public abstract class BiomeEffectsMixin {

  @ModifyReturnValue(method = "getSkyColor", at = @At("RETURN"))
  private int $getSkyColor(int original) {
    return FoggerClient.getFogpackManager().getAppliedFogpackInstance().getConfig().getSky().getColor(original);
  }

  @ModifyReturnValue(method = "getFogColor", at = @At("RETURN"))
  private int $getFogColor(int original) {
    return FoggerClient.getFogpackManager().getAppliedFogpackInstance().getConfig().getFog().getColor(original);
  }

  @ModifyReturnValue(method = "getWaterColor", at = @At("RETURN"))
  private int $getWaterColor(int original) {
    return FoggerClient.getFogpackManager().getAppliedFogpackInstance().getConfig().getWater().getColor(original);
  }

  @ModifyReturnValue(method = "getWaterFogColor", at = @At("RETURN"))
  private int $getWaterFogColor(int original) {
    return FoggerClient.getFogpackManager().getAppliedFogpackInstance().getConfig().getWaterFog().getColor(original);
  }
}
