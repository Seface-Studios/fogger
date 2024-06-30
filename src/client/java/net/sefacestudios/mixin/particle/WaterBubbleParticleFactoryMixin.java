package net.sefacestudios.mixin.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.WaterBubbleParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3d;
import net.sefacestudios.FoggerClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WaterBubbleParticle.Factory.class)
public abstract class WaterBubbleParticleFactoryMixin {
  @Unique
  private static final int FOGGER$DEFAULT_COLOR = 4680396; // #476ACC

  @Shadow
  private final SpriteProvider spriteProvider;

  protected WaterBubbleParticleFactoryMixin(SpriteProvider spriteProvider) {
    this.spriteProvider = spriteProvider;
  }

  @Inject(method = "createParticle(Lnet/minecraft/particle/DefaultParticleType;Lnet/minecraft/client/world/ClientWorld;DDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("RETURN"), cancellable = true)
  public void $createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velX, double velY, double velZ, CallbackInfoReturnable<Particle> cir) {
    WaterBubbleParticle rainSplashParticle = new WaterBubbleParticle(clientWorld, x, y, z, velX, velY, velZ);
    rainSplashParticle.setSprite(this.spriteProvider);

    int rgbColor = FoggerClient.getFogpackManager()
      .getAppliedFogpackInstance().getConfig().getWater().getColor(FOGGER$DEFAULT_COLOR);

    Vec3d color = Vec3d.unpackRgb(rgbColor);

    rainSplashParticle.setColor((float) color.x, (float) color.y, (float) color.z);
    cir.setReturnValue(rainSplashParticle);
  }
}
