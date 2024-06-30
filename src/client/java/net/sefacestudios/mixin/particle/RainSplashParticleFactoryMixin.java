package net.sefacestudios.mixin.particle;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.sefacestudios.FoggerClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RainSplashParticle.Factory.class)
public abstract class RainSplashParticleFactoryMixin {
  @Unique
  private static final int FOGGER$DEFAULT_COLOR = 4680396; // #476ACC

  @Shadow
  private final SpriteProvider spriteProvider;

  protected RainSplashParticleFactoryMixin(SpriteProvider spriteProvider) {
    this.spriteProvider = spriteProvider;
  }

  @Inject(method = "createParticle(Lnet/minecraft/particle/DefaultParticleType;Lnet/minecraft/client/world/ClientWorld;DDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("RETURN"), cancellable = true)
  public void $createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velX, double velY, double velZ, CallbackInfoReturnable<Particle> cir) {
    RainSplashParticle rainSplashParticle = new RainSplashParticle(clientWorld, x, y, z);
    rainSplashParticle.setSprite(this.spriteProvider);

    BlockPos pos = new BlockPos((int) x, (int) y, (int) z);
    BlockState state = clientWorld.getBlockState(pos);

    int biomeWaterColor = BiomeColors.getWaterColor(clientWorld, pos);
    int rgbColor = FoggerClient.getFogpackManager()
      .getAppliedFogpackInstance().getConfig().getWater().getColor(biomeWaterColor);

    Vec3d color = state.isOf(Blocks.WATER) ? Vec3d.unpackRgb(rgbColor) : Vec3d.unpackRgb(FOGGER$DEFAULT_COLOR);

    rainSplashParticle.setColor((float) color.x, (float) color.y, (float) color.z);
    cir.setReturnValue(rainSplashParticle);
  }
}
