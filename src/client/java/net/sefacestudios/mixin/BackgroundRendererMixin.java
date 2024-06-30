package net.sefacestudios.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.entity.Entity;
import net.sefacestudios.Fogger;
import net.sefacestudios.FoggerClient;
import net.sefacestudios.fogpack.Fogpack;
import net.sefacestudios.fogpack.FogpackManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {

  @Shadow
  @Nullable
  private static BackgroundRenderer.@Nullable StatusEffectFogModifier getFogModifier(Entity entity, float tickDelta) {
    return null;
  }

  @Inject(method = "applyFog", at = @At(value = "TAIL"))
  private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
    Fogpack fogpack = FoggerClient.getFogpackManager().getAppliedFogpackInstance();

    if (fogpack.getIdentifier().equals(FogpackManager.VANILLA_FOGPACK)) return;

    int fogDistance = fogpack.getConfig().getFog().getDistance();
    if (fogDistance < 0) return;

    float fogStartMultiplier = (float) fogpack.getConfig().getFog().getStartMultiplier() / 100;

    if (camera.getSubmersionType() == CameraSubmersionType.NONE && (thickFog || fogType == BackgroundRenderer.FogType.FOG_TERRAIN)) {
      RenderSystem.setShaderFogStart(fogDistance * 16 * fogStartMultiplier);
      RenderSystem.setShaderFogEnd((fogDistance + 1) * 16);
    }
  }
}
